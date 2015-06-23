/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.transformers;


import javax.ejb.Local;

import com.menupro.dtos.DTOUser;
import com.menupro.dtos.DTOMenu;
import com.menupro.dtos.DTOPlate;
import com.menupro.dtos.DTOOrder;
import com.menupro.persistence.entities.*;

/**
 *
 * @author User
 */
@Local
public interface DtoToEntityTransformerLocal {

    User convertUser(DTOUser dUser);

    Order convertOrder(DTOOrder dOrder);

    Menu convertMenu(DTOMenu dMenu);

    Plate convertPlate(DTOPlate dPlate);
    
}
