/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.*;
import com.menupro.business.transformers.DtoToEntityTransformer;
import com.menupro.dtos.DTOUser;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
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
public class UserSessionBean implements UserSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private PersistenceSessionBeanLocal persistence;
    
    @EJB
    private DtoToEntityTransformer toEntity;
    
    @Override
    public void addUser(DTOUser dtoUser) throws EntityAlreadyExistsException {
        try {
            User user = toEntity.convertUser(dtoUser);
            persistence.addUser(user);
        } catch (EntityExistsException e) {
            throw new EntityAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
            throw new EntityAlreadyExistsException("An error unexpected occurred, please try again later.");
        }
    }

    @Override
    public void modifyUser(DTOUser dtoUser) throws EntityDoesntExistsException {
        User u;
        try {
            User user = toEntity.convertUser(dtoUser);
            try {
                u = persistence.getUser(user.getUserName());
                List<User> contacts = persistence.searchContacts(u);
                user.setContacts(contacts);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
            Long id = u.getId();
            user.setId(id);
            try {
                persistence.editUser(user);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }
    
    
    
    
}
