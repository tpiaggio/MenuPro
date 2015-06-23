/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;
import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.dtos.DTOPlate;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface PlateSessionBeanLocal {

    void addPlate(DTOPlate dPlate)throws EntityAlreadyExistsException;

    void editPlate(DTOPlate dPlate) throws EntityDoesntExistsException;

    void deletePlate(Long id) throws EntityDoesntExistsException;

    DTOPlate getPlate(Long id) throws EntityDoesntExistsException;

    
}
