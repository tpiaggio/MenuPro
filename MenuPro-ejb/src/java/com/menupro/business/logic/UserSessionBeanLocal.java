/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.*;
import com.menupro.dtos.DTOUser;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface UserSessionBeanLocal {

    void addUser(DTOUser dtoUser) throws EntityAlreadyExistsException;
    
    void editUser(DTOUser dtoUser) throws EntityDoesntExistsException;

    void deleteUser(String userName) throws EntityDoesntExistsException;

    void addContact(String userName, String contactName) throws EntityDoesntExistsException, EntityAlreadyExistsException;

    DTOUser getUser(String userName) throws EntityDoesntExistsException;

    String signIn(String userName, String password) throws EntityDoesntExistsException, InvalidDataException;

    void signOut(String token) throws EntityDoesntExistsException;

    boolean isLoggedIn(String token, String userName);
    
}
