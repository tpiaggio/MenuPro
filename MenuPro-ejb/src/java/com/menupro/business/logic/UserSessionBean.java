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
import com.menupro.persistence.entities.Token;
import com.menupro.persistence.entities.User;
import java.util.List;
import java.util.UUID;
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
            throw new EntityAlreadyExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void editUser(DTOUser dtoUser) throws EntityDoesntExistsException {
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
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void deleteUser(String userName) throws EntityDoesntExistsException {
        try {
            User user = persistence.getUser(userName);
            persistence.deleteUser(user);
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public void addContact(String userName, String contactName) throws EntityDoesntExistsException, EntityAlreadyExistsException {
        if(userName.equals(contactName)){
            throw new EntityAlreadyExistsException("Error: It is not possible to add yourself as a contact.");
        }
        try {
            User user = persistence.getUser(userName);
            User contact = persistence.getUser(contactName);
            if (user.getContacts().contains(contact)) {
                throw new EntityAlreadyExistsException("The user "+contactName+" already belongs to your contacts");
            }
            user.getContacts().add(contact);
            persistence.editUser(user);
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public DTOUser getUser(String userName) throws EntityDoesntExistsException {
        try {
            DTOUser dtoUser = null;//toDto.convertUser(persistence.getUser(userName));
            return dtoUser;
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public String signIn(String userName, String password) throws EntityDoesntExistsException {
        String token = "";
        try {
            User user = persistence.getUser(userName);
            if (user.getPassword().equals(password)) {
                token = UUID.randomUUID().toString();
                Token t = new Token();
                t.setToken(token);
                t.setUser(user);
                persistence.addToken(t);
            }
            else{
                throw new InvalidDataException("The username and password you entered don't match.");
            }
            return token;
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public void signOut(String token) throws EntityDoesntExistsException {
        try {
            Token t = persistence.getToken(token);
            persistence.deleteToken(t);
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public boolean isLoggedIn(String token, String userName) {
        try {
            Token t = persistence.getToken(token);
            return t.getUser().getUserName().equals(userName);
        } catch (Exception e) {
            return false;
        }
    }
    
    
    
    
}
