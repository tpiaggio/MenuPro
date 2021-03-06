/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.logic;
import com.menupro.business.exceptions.EntityAlreadyExistsException;
import com.menupro.business.exceptions.EntityDoesntExistsException;
import com.menupro.dtos.DTOPlate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface PlateSessionBeanLocal {

    void addPlate(DTOPlate dPlate)throws EntityAlreadyExistsException;

    void editPlate(DTOPlate dPlate) throws EntityDoesntExistsException;

    void deletePlate(String name) throws EntityDoesntExistsException;

    DTOPlate getPlate(String name) throws EntityDoesntExistsException;

    List<DTOPlate> getPlates();
    
    List<DTOPlate> getPlatesFromCategory(String category);
}
