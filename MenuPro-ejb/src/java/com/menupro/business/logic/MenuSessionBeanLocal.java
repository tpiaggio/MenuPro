/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.dtos.DTOMenu;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface MenuSessionBeanLocal {

    void addMenu(DTOMenu dMenu) throws EntityAlreadyExistsException;

    void editMenu(DTOMenu dMenu) throws EntityDoesntExistsException;

    void deleteMenu(Long id) throws EntityDoesntExistsException;

    DTOMenu getMenu(Long id) throws EntityDoesntExistsException;
    
}
