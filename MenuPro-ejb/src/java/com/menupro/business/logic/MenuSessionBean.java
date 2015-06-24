/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.business.transformers.DtoToEntityTransformer;
import com.menupro.business.transformers.EntityToDtoTransformer;
import com.menupro.dtos.*;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
import com.menupro.persistence.entities.Menu;
import com.menupro.persistence.entities.Plate;
import com.menupro.persistence.entities.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Pepe
 */
@Stateless
public class MenuSessionBean implements MenuSessionBeanLocal {

    @EJB
    private PersistenceSessionBeanLocal persistence;
    
    @EJB
    private DtoToEntityTransformer toEntity;
    
    @EJB
    private EntityToDtoTransformer toDto;
    
    @Override
    public void addMenu(DTOMenu dMenu) throws EntityAlreadyExistsException {
        try {
            Menu menu = toEntity.convertMenu(dMenu);
            persistence.addMenu(menu);
        } catch (EntityExistsException e) {
            throw new EntityAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
            throw new EntityAlreadyExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void editMenu(DTOMenu dMenu) throws EntityDoesntExistsException {
        Menu m;
        try {
            Menu menu = toEntity.convertMenu(dMenu);
            try {
                m = persistence.getMenu(menu.getId());
                List<Plate> plates = persistence.getPlatesFromMenu(m.getId());
                menu.setPlates(plates);
                List<User> sharedUsers = persistence.getUsersFromMenu(m.getName(), m.getOwner());
                menu.setSharedUsers(sharedUsers);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
            Long id = m.getId();
            menu.setId(id);
            try {
                persistence.editMenu(menu);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void deleteMenu(Long id) throws EntityDoesntExistsException{
        try {
            Menu menu = persistence.getMenu(id);
            persistence.deleteMenu(menu);
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
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
}
