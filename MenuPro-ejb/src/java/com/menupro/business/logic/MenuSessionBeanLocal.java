/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.dtos.DTOMenu;
import com.menupro.dtos.DTOPlate;
import com.menupro.dtos.DTOUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface MenuSessionBeanLocal {

    void addMenu(DTOMenu dMenu) throws EntityAlreadyExistsException;

    void editMenu(DTOMenu dMenu) throws EntityDoesntExistsException;

    void deleteMenu(String menuName, String userName, String owner) throws EntityDoesntExistsException;

    DTOMenu getMenu(Long id) throws EntityDoesntExistsException;
    
    void shareMenu(String menuName, String ownerName, String sharedName) throws EntityDoesntExistsException, EntityAlreadyExistsException;
    
    DTOMenu getMenu(String name, String owner)throws EntityDoesntExistsException;
    
    List<DTOUser> getUsersFromMenu(String name, String owner) throws EntityDoesntExistsException;
    
    List<DTOMenu> getMenusFromUser(String userName) throws EntityDoesntExistsException;
    
    List<DTOPlate> getPlatesFromMenu(String menuName, String userName) throws EntityDoesntExistsException;
    
    void addPlateToMenu(String plateName, String menuName, String userName) throws EntityDoesntExistsException, EntityAlreadyExistsException;
    
}
