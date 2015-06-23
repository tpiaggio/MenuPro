/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.transformers;

import com.menupro.dtos.*;
import com.menupro.persistence.entities.*;
import javax.ejb.Stateless;


/**
 *
 * @author User
 */
@Stateless
public class DtoToEntityTransformer implements DtoToEntityTransformerLocal {

    @Override
    public User convertUser(DTOUser dUser) {
        return null;
    }
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Order convertOrder(DTOOrder dOrder) {
        return null;
    }

    @Override
    public Menu convertMenu(DTOMenu dMenu) {
        return null;
    }

    @Override
    public Plate convertPlate(DTOPlate dPlate) {
        return null;
    }

    
}
