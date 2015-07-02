/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;

import com.menupro.business.exceptions.*;
import com.menupro.business.transformers.DtoToEntityTransformerLocal;
import com.menupro.business.transformers.EntityToDtoTransformerLocal;
import com.menupro.dtos.DTOPlate;
import com.menupro.persistence.beans.PersistenceSessionBeanLocal;
import com.menupro.persistence.entities.Plate;
import java.util.ArrayList;
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
    private DtoToEntityTransformerLocal toEntity;
    
    @EJB
    private EntityToDtoTransformerLocal toDto;
    
    @Override
    public void addPlate(DTOPlate dPlate) throws EntityAlreadyExistsException{
        try {
            Plate plate = toEntity.convertPlate(dPlate);
            persistence.addPlate(plate);
        } catch (EntityExistsException e) {
            throw new EntityAlreadyExistsException("The plate " + dPlate.getName() + " already exists.");
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
                plate = persistence.getPlate(p.getName());            
            } catch (Exception e) {
                throw new EntityDoesntExistsException(e.getMessage());
            }            
            try {
                p.setId(plate.getId());
                persistence.editPlate(p);
            } catch (Exception e) {
                throw new EntityDoesntExistsException("The plate "+ plate.getName() + " was not found.");
            }
        } catch (Exception e) {
            throw new EntityDoesntExistsException("An unexpected error occurred, please try again later.");
        }
    }

    @Override
    public void deletePlate(String name) throws EntityDoesntExistsException {
        try {
            Plate plate = persistence.getPlate(name);
            persistence.deletePlate(plate);
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The plate "+ name + " was not found.");
        }
    }

    @Override
    public DTOPlate getPlate(String name) throws EntityDoesntExistsException{
        try {
            DTOPlate dtoUser = toDto.convertPlate(persistence.getPlate(name));
            return dtoUser;
        } catch (Exception e) {
            throw new EntityDoesntExistsException("The plate "+ name + " was not found.");
        }
    }

    @Override
    public List<DTOPlate> getPlates() {
        List<DTOPlate> plates = new ArrayList<DTOPlate>();
        for (Plate p : persistence.getPlates()) {
            plates.add(toDto.convertPlate(p));
        }
        return plates;
    }
    
    @Override
    public List<DTOPlate> getPlatesFromCategory(String category){
        List<DTOPlate> plates = new ArrayList<DTOPlate>();
        for (Plate p : persistence.getPlatesFromCategory(category)) {
            plates.add(toDto.convertPlate(p));
        }
        return plates;
    }
    
    
}
