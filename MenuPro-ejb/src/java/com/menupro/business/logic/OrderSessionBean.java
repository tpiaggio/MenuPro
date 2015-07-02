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
import com.menupro.dtos.DTOMenu;
import com.menupro.dtos.DTOOrder;
import com.menupro.dtos.DTOUser;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
import com.menupro.persistence.entities.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Pepe
 */
@Stateless
public class OrderSessionBean implements OrderSessionBeanLocal {

    @EJB
    private PersistenceSessionBeanLocal persistence;
    
    @EJB
    private DtoToEntityTransformerLocal toEntity;
    
    @EJB
    private EntityToDtoTransformerLocal toDto;
    
    @Override
    public void addOrder(DTOOrder dOrder)throws EntityAlreadyExistsException {
        try {
            Order order = toEntity.convertOrder(dOrder);
            User user = persistence.getUser(order.getBuyer().getUserName());
            order.setBuyer(user);
            List<Menu> menus = new ArrayList<Menu>();
            for(DTOMenu m : dOrder.getMenus()){
                menus.add(persistence.getMenu(m.getName(), user));
            }
            order.setMenus(menus);
            order.setDate(new Date());
            persistence.addOrder(order);
        } catch (EntityExistsException e) {
            e.printStackTrace();
            throw new EntityAlreadyExistsException("An error occurred, please try again later.");
        }catch (Exception e) {
            e.printStackTrace();
            throw new EntityAlreadyExistsException("An unexpected error occurred, please try again later.");
        }
    }
    
    @Override
    public void editOrder(DTOOrder dOrder) throws EntityDoesntExistsException{
        Order o;
        try {
            Order order = toEntity.convertOrder(dOrder);
            try {
                o = persistence.getOrder(order.getId());
                User user = persistence.getUser(order.getBuyer().getUserName());
                order.setBuyer(user);
                List<Menu> menus = persistence.searchMenusFromOrder(o.getId());
                order.setMenus(menus);
                List<User> sideBuyers = persistence.getUsersFromOrder(o.getId());
                order.setSideBuyers(sideBuyers);
            } catch (Exception e) {
                throw new EntityDoesntExistsException("The order with code "+dOrder.getCode()+" was not found.");
            }
            try {
                persistence.editOrder(order);
            } catch (Exception e) {
                throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void deleteOrder(Long id, String buyer)throws EntityDoesntExistsException{
        try {
            Order order = persistence.getOrder(id);
            if(order.getBuyer().getUserName().equals(buyer)){
                persistence.deleteOrder(order);
            } else {
                throw new EntityDoesntExistsException("You can only delete your own orders");
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The order with code "+id+" was not found.");
        }
    }

    @Override
    public DTOOrder getOrder(Long id, String buyer) throws EntityDoesntExistsException{
        try {
            Order order = persistence.getOrder(id);
            if(!order.getBuyer().getUserName().equals(buyer)){
                throw new EntityDoesntExistsException("Access denied, you can only view your own orders");
            }
            DTOOrder dOrder = toDto.convertOrder(order);
            return dOrder;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The order with code "+id+" was not found.");
        }     
    }    

    @Override
    public List<DTOMenu> getMenusFromOrder(Long id, String buyer) throws EntityDoesntExistsException {
        try {
            Order order = persistence.getOrder(id);
            if(!order.getBuyer().getUserName().equals(buyer)){
                throw new EntityDoesntExistsException("Access denied, you can only view your own orders");
            }
            List<DTOMenu> menus = new ArrayList<DTOMenu>();
            for(Menu m : persistence.searchMenusFromOrder(id)) {
                menus.add(toDto.convertMenu(m));
            }
            return menus;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The order with code "+id+" was not found.");
        }
    }

    @Override
    public List<DTOUser> getUsersFromOrder(Long id, String buyer) throws EntityDoesntExistsException {
        try {
            Order order = persistence.getOrder(id);
            if(!order.getBuyer().getUserName().equals(buyer)){
                throw new EntityDoesntExistsException("Access denied, you can only view your own orders");
            }
            List<DTOUser> users = new ArrayList<DTOUser>();
            for(User u : persistence.getUsersFromOrder(id)) {
                users.add(toDto.convertUser(u));
            }
            return users;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The order with code "+id+" was not found.");
        }
    }

    @Override
    public void shareOrder(String userName, Long id, String buyer) throws EntityDoesntExistsException, EntityAlreadyExistsException {
        try {
            User shared;
            try{
               shared = persistence.getUser(userName);
            } catch(Exception e){
                throw new EntityDoesntExistsException("The user " +userName + " was not found.");
            }
            Order order = persistence.getOrder(id);
            if(!order.getBuyer().getUserName().equals(buyer)){
                throw new EntityDoesntExistsException("Access denied, you can only share your own orders");
            }
            if(order.getBuyer().getUserName().equals(userName)){
                throw new EntityAlreadyExistsException("It is not possible to add yourself as a side buyer");
            }
            order.getBuyer().setContacts(persistence.searchContacts(order.getBuyer()));
            if(!order.getBuyer().getContacts().contains(shared)){
                throw new EntityDoesntExistsException("The user " + shared.getUserName() + 
                        " is not one of your contacts");
            }
            if(order.getSideBuyers().contains(shared)){
                throw new EntityAlreadyExistsException("The order " + id + 
                        " is already being shared with " + shared.getUserName());
            }
            order.getSideBuyers().add(shared);
            persistence.editOrder(order);
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The order with code "+id+" was not found.");
        } 
    }

    @Override
    public List<DTOOrder> getOrdersFromUser(String userName) throws EntityDoesntExistsException {
        try {
            List<DTOOrder> orders = new ArrayList<DTOOrder>();
            User user = persistence.getUser(userName);
            for (Order o : persistence.getOrdersFromUser(user)) {
                orders.add(toDto.convertOrder(o));
            }
            return orders;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The user " +userName + " was not found.");
        }
    }

    @Override
    public void addMenuToOrder(String menuName, String userName, Long id) throws EntityDoesntExistsException {
        try {
            User user = persistence.getUser(userName);
            Menu menu;
            try{
                menu = persistence.getMenu(menuName, user);
            } catch(Exception e){
                throw new EntityDoesntExistsException("The menu " +menuName + " was not found.");
            }
            Order order = persistence.getOrder(id);
            if(!order.getBuyer().getUserName().equals(userName)){
                throw new EntityDoesntExistsException("Access denied, you can only add menus to your own orders");
            }
            if(order.getMenus().contains(menu)){
                throw new EntityAlreadyExistsException("This order already contains the menu " + menu.getName());
            }
            order.getMenus().add(menu);
            persistence.editOrder(order);
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The order with code " +id + " was not found.");
        }
    }

}
