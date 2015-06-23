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
public class EntityToDtoTransformer implements EntityToDtoTransformerLocal {

    @Override
    public DTOUser convertUser(User user) {
        DTOUser dUser = new DTOUser();
        dUser.setId(user.getId());
        dUser.setName(user.getName());
        dUser.setPassword(user.getPassword());
        dUser.setUserName(user.getUserName());
        for(User u : user.getContacts()){
            DTOUser dU = this.convertNormalUser(u);
            dUser.getContacts().add(dU);
        }
        return dUser;
    }
    
    private DTOUser convertNormalUser(User user){
        DTOUser dUser = new DTOUser();
        dUser.setId(user.getId());
        dUser.setName(user.getName());
        dUser.setPassword(user.getPassword());
        dUser.setUserName(user.getUserName());
        return dUser;
    }
    
    @Override
    public DTOPlate convertPlate(Plate plate) {
        DTOPlate dPlate = new DTOPlate();
        dPlate.setCategory(plate.getCategory());
        dPlate.setId(plate.getId());
        dPlate.setName(plate.getName());
        dPlate.setPrice(plate.getPrice());
        return dPlate;
    }

    @Override
    public DTOMenu convertMenu(Menu menu) {
        DTOMenu dMenu = new DTOMenu();
        dMenu.setId(menu.getId());
        dMenu.setName(menu.getName());
        dMenu.setOwner(this.convertNormalUser(menu.getOwner()));
        for(Plate p : menu.getPlates()){
            DTOPlate dP = this.convertPlate(p);
            dMenu.getPlates().add(dP);
        }
        for(User u : menu.getSharedUsers()){
            DTOUser dU = this.convertNormalUser(u);
            dMenu.getSharedUsers().add(dU);
        }
        return dMenu;
    }

    @Override
    public DTOOrder convertOrder(Order order) {
        DTOOrder dOrder = new DTOOrder();
        dOrder.setComments(order.getComments());
        dOrder.setDate(order.getDate());
        dOrder.setId(order.getId());
        DTOUser newUser = this.convertNormalUser(order.getBuyer());
        dOrder.setBuyer(newUser);
        for(User u : order.getSideBuyers()){
            DTOUser dU = this.convertNormalUser(u);
            dOrder.getSideBuyers().add(dU);
        }
        for(Menu m : order.getMenus()){
            DTOMenu dM = this.convertMenu(m);
            dOrder.getMenus().add(dM);
        }
        return dOrder;
    }
}
