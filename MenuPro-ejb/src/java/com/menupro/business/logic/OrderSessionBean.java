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
import com.menupro.dtos.DTOOrder;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
import com.menupro.persistence.entities.*;
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
    private DtoToEntityTransformer toEntity;
    
    @EJB
    private EntityToDtoTransformer toDto;
    
    @Override
    public void addOrder(DTOOrder dOrder)throws EntityAlreadyExistsException {
        try {
            Order order = toEntity.convertOrder(dOrder);
            persistence.addOrder(order);
        } catch (EntityExistsException e) {
            throw new EntityAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
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
                List<Menu> menus = persistence.searchMenusFromOrder(o.getId());
                order.setMenus(menus);
                List<User> sideBuyers = persistence.getUsersFromOrder(o.getId());
                order.setSideBuyers(sideBuyers);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
            Long id = o.getId();
            order.setId(id);
            try {
                persistence.editOrder(order);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void deleteOrder(Long id)throws EntityDoesntExistsException{
        try {
            Order order = persistence.getOrder(id);
            persistence.deleteOrder(order);
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public DTOOrder getOrder(Long id) throws EntityDoesntExistsException{
        try {
            DTOOrder dOrder = toDto.convertOrder(persistence.getOrder(id));
            return dOrder;
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }     
    }    
}
