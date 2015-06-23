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
    
    void modifyUser(DTOUser dtoUser) throws EntityDoesntExistsException;
    
}
