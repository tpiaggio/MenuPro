/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.dtos.DTOMenu;
import com.menupro.dtos.DTOOrder;
import com.menupro.dtos.DTOUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface OrderSessionBeanLocal {

    void addOrder(DTOOrder dOrder) throws EntityAlreadyExistsException;

    void editOrder(DTOOrder dOrder) throws EntityDoesntExistsException;

    void deleteOrder(Long id, String buyer) throws EntityDoesntExistsException;

    DTOOrder getOrder(Long id, String buyer) throws EntityDoesntExistsException;
    
    List<DTOMenu> getMenusFromOrder(Long id, String buyer) throws EntityDoesntExistsException;
    
    List<DTOUser> getUsersFromOrder(Long id, String buyer) throws EntityDoesntExistsException;
    
    void shareOrder(String userName, Long id, String buyer) throws EntityDoesntExistsException, EntityAlreadyExistsException;
    
    List<DTOOrder> getOrdersFromUser(String userName) throws EntityDoesntExistsException;
    
    void addMenuToOrder(String menuName, String userName, Long id) throws EntityDoesntExistsException, EntityAlreadyExistsException;
}
