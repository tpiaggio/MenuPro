/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.*;
import com.menupro.business.transformers.DtoToEntityTransformer;
import com.menupro.business.transformers.EntityToDtoTransformer;
import com.menupro.dtos.DTOPlate;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
import com.menupro.persistence.entities.Plate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Pepe
 */
@Stateless
public class PlateSessionBean implements PlateSessionBeanLocal {

    @EJB
    private PersistenceSessionBeanLocal persistence;
    
    @EJB
    private DtoToEntityTransformer toEntity;
    
    @EJB
    private EntityToDtoTransformer toDto;
    
    @Override
    public void addPlate(DTOPlate dPlate) throws EntityAlreadyExistsException{
        try {
            Plate plate = toEntity.convertPlate(dPlate);
            persistence.addPlate(plate);
        } catch (EntityExistsException e) {
            throw new EntityAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
            throw new EntityAlreadyExistsException("An unexpected error occurred, please try again later.");
        }
    }
    
    @Override
    public void editPlate(DTOPlate dPlate) throws EntityDoesntExistsException{
        Plate plate;
        try {
            Plate p = toEntity.convertPlate(dPlate);
            try {
                plate = persistence.getPlate(p.getId());            
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }            
            try {
                persistence.editPlate(p);
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void deletePlate(Long id) throws EntityDoesntExistsException {
        try {
            Plate plate = persistence.getPlate(id);
            persistence.deletePlate(plate);
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public DTOPlate getPlate(Long id) throws EntityDoesntExistsException{
        try {
            DTOPlate dtoUser = toDto.convertPlate(persistence.getPlate(id));
            return dtoUser;
        } catch (Exception e) {
            throw new EntityDoesntExistsException(e.getMessage());
        }
    }
    
    
}
