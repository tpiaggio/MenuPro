/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.transformers;

import com.menupro.dtos.*;
import com.menupro.persistence.entities.*;
import javax.ejb.Local;

/**
 *
 * @author User
 */
@Local
public interface EntityToDtoTransformerLocal {

    DTOUser convertUser(User user);

    DTOPlate convertPlate(Plate plate);

    DTOMenu convertMenu(Menu menu);

    DTOOrder convertOrder(Order order);
    
}
