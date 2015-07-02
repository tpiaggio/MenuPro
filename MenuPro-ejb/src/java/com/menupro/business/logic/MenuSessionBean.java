/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.business.transformers.DtoToEntityTransformerLocal;
import com.menupro.business.transformers.EntityToDtoTransformerLocal;
import com.menupro.dtos.*;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
import com.menupro.persistence.entities.Menu;
import com.menupro.persistence.entities.Plate;
import com.menupro.persistence.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Pepe
 */
@Stateless
public class MenuSessionBean implements MenuSessionBeanLocal {

    @EJB
    private PersistenceSessionBeanLocal persistence;
    
    @EJB
    private DtoToEntityTransformerLocal toEntity;
    
    @EJB
    private EntityToDtoTransformerLocal toDto;
    
    @Override
    public void addMenu(DTOMenu dMenu) throws EntityAlreadyExistsException {
        try {
            Menu menu = toEntity.convertMenu(dMenu);
            User user = persistence.getUser(menu.getOwner().getUserName());
            menu.setOwner(user);
            List<Plate> plates = new ArrayList<Plate>();
            for(DTOPlate p : dMenu.getPlates()){
                plates.add(persistence.getPlate(p.getName()));
            }
            menu.setPlates(plates);
            persistence.addMenu(menu);
        } catch (EntityExistsException e) {
            throw new EntityAlreadyExistsException("The menu " + dMenu.getName() + " already exists.");
        } catch (EntityNotFoundException e) {
            throw new EntityAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
            throw new EntityAlreadyExistsException("An unexpected error occurred, please try again later.");
        }
    }
    
    @Override
    public void editMenu(DTOMenu dMenu) throws EntityDoesntExistsException {
        if(dMenu.getId()==null){
            throw new EntityDoesntExistsException("The code of the menu has to be specified.");
        }
        Menu m;
        try {
            Menu menu = toEntity.convertMenu(dMenu);
            try {
                m = persistence.getMenu(menu.getId());
                User user = persistence.getUser(menu.getOwner().getUserName());
                menu.setOwner(user);
                List<Plate> plates = persistence.getPlatesFromMenu(m.getOwner(), m.getName());
                menu.setPlates(plates);
                List<User> sharedUsers = persistence.getUsersFromMenu(m.getName(), m.getOwner());
                menu.setSharedUsers(sharedUsers);
            } catch (Exception e) {
                throw new EntityDoesntExistsException("The menu "+menu.getName() + " (" + menu.getId() +") was not found.");
            }
            try {
                persistence.editMenu(menu);
            } catch (Exception e) {
                throw new EntityDoesntExistsException("The menu "+menu.getName() + " (" + menu.getId() +") was not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }
    
    @Override
    public void deleteMenu(String menuName, String userName, String owner) throws EntityDoesntExistsException{
        try {
            User user = persistence.getUser(userName);
            Menu menu = persistence.getMenu(menuName, user);
            if(menu.getOwner().getUserName().equals(owner)){
                persistence.deleteMenu(menu);
            } else {
                throw new EntityDoesntExistsException("You can only delete your own menus");
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The menu "+menuName + " was not found.");
        }
    }

    @Override
    public DTOMenu getMenu(Long id)throws EntityDoesntExistsException {
        try {
            DTOMenu dMenu = toDto.convertMenu(persistence.getMenu(id));
            return dMenu;
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }     
    }
    
    @Override
    public DTOMenu getMenu(String name, String owner) throws EntityDoesntExistsException {
        try {
            User user = persistence.getUser(owner);
            DTOMenu dMenu = toDto.convertMenu(persistence.getMenu(name, user));
            return dMenu;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The menu "+name + " was not found.");
        }
    }
    
    @Override
    public void shareMenu(String menuName, String ownerName, String sharedName) throws EntityDoesntExistsException, EntityAlreadyExistsException {
        try {
            if(ownerName.equals(sharedName)){
                throw new EntityAlreadyExistsException("It is not possible to share a menu with yourself");
            }
            User owner = persistence.getUser(ownerName);
            User shared;
            try {
                shared = persistence.getUser(sharedName);
            }  catch (Exception e) {
                throw new EntityDoesntExistsException("The user "+sharedName + " was not found.");
            }
            owner.setContacts(persistence.searchContacts(owner));
            if(!owner.getContacts().contains(shared)){
                throw new EntityDoesntExistsException("The user " + sharedName + 
                        " is not one of your contacts");
            }
            Menu originalMenu;
            try{
                originalMenu = persistence.getMenu(menuName, owner);
            }  catch (Exception e) {
                throw new EntityDoesntExistsException("The menu "+menuName + " was not found.");
            }
            originalMenu.setSharedUsers(persistence.getUsersFromMenu(menuName, owner));
            if(originalMenu.getSharedUsers().contains(shared)){
                throw new EntityAlreadyExistsException("The menu " + menuName + 
                        " is already being shared with " + sharedName);
            }
            Menu newMenu = new Menu();
            newMenu.setName(menuName + " (shared by: " + owner.getUserName() + ")");
            for(Menu m : persistence.getMenusFromUser(shared)){
                if(m.getName().equals(newMenu.getName())){
                    throw new EntityAlreadyExistsException("The menu " + menuName + 
                        " is already being shared with " + sharedName);
                }
            }
            newMenu.setOwner(shared);
            newMenu.setPlates(originalMenu.getPlates());
            originalMenu.getSharedUsers().add(shared);
            persistence.editMenu(originalMenu);
            persistence.addMenu(newMenu);
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The menu "+menuName + " was not found.");
        }
    }
    @Override
    public List<DTOUser> getUsersFromMenu(String name, String owner) throws EntityDoesntExistsException {
        try {
            List<DTOUser> users = new ArrayList<DTOUser>();
            User user = persistence.getUser(owner);
            for(User u : persistence.getUsersFromMenu(name, user)) {
                users.add(toDto.convertUser(u));
            }
            return users;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The menu "+name + " was not found.");
        }
    }
    
    @Override
    public List<DTOMenu> getMenusFromUser(String userName) throws EntityDoesntExistsException {
        try {
            List<DTOMenu> menus = new ArrayList<DTOMenu>();
            User user = persistence.getUser(userName);
            for(Menu m : persistence.getMenusFromUser(user)) {
                menus.add(toDto.convertMenu(m));
            }
            return menus;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The user "+userName + " was not found.");
        }
    }
    @Override
    public List<DTOPlate> getPlatesFromMenu(String menuName, String userName) throws EntityDoesntExistsException {
        try {
            User user = persistence.getUser(userName);
            List<DTOPlate> plates = new ArrayList<DTOPlate>();
            for(Plate p : persistence.getPlatesFromMenu(user, menuName)) {
                plates.add(toDto.convertPlate(p));
            }
            return plates;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The menu "+menuName + " was not found.");
        }
    }

    @Override
    public void addPlateToMenu(String name, String menuName, String userName) throws EntityDoesntExistsException, EntityAlreadyExistsException {
        try {
            User user = persistence.getUser(userName);
            Menu menu = persistence.getMenu(menuName, user);
            Plate plate = persistence.getPlate(name);
            if (menu.getPlates().contains(plate)) {
                throw new EntityAlreadyExistsException("This menu already contains the plate " + name);
            }
            menu.getPlates().add(plate);
            persistence.editMenu(menu);
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The plate "+name + " was not found.");
        }
    }
}
