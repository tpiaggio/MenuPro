/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.dtos.DTOOrder;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface OrderSessionBeanLocal {

    void addOrder(DTOOrder dOrder) throws EntityAlreadyExistsException;

    void editOrder(DTOOrder dOrder) throws EntityDoesntExistsException;

    void deleteOrder(Long id) throws EntityDoesntExistsException;

    DTOOrder getOrder(Long id) throws EntityDoesntExistsException;
    
}
