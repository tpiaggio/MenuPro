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
        User newUser=new User();
        newUser.setId(dUser.getId());
        newUser.setName(dUser.getName());
        newUser.setPassword(dUser.getPassword());
        newUser.setUserName(dUser.getUserName());
        for(DTOUser dU : dUser.getContacts()){
            User listUser=new User(dU.getId(),dU.getUserName(),
                dU.getName(), dU.getPassword());
            newUser.getContacts().add(listUser);
        }
        return newUser;
    }
    public User converNormalUser(DTOUser dUser){
        User newUser=new User();
        newUser.setId(dUser.getId());
        newUser.setName(dUser.getName());
        newUser.setPassword(dUser.getPassword());
        newUser.setUserName(dUser.getUserName());
        return newUser;
    }
    @Override
    public Order convertOrder(DTOOrder dOrder) {
        Order newOrder = new Order();
        newOrder.setComments(dOrder.getComments());
        newOrder.setDate(dOrder.getDate());
        newOrder.setId(dOrder.getId());
        User newUser = this.converNormalUser(dOrder.getBuyer());
        newOrder.setBuyer(newUser);
        for(DTOUser dU : dOrder.getSideBuyers()){
            User sU = this.converNormalUser(dU);
            newOrder.getSideBuyers().add(sU);
        }
        for(DTOMenu dM : dOrder.getMenus()){
            Menu rM = this.convertMenu(dM);
            newOrder.getMenus().add(rM);
        }
        return null;
    }
    
    @Override
    public Menu convertMenu(DTOMenu dMenu) {
        Menu newMenu = new Menu();
        newMenu.setId(dMenu.getId());
        newMenu.setName(dMenu.getName());
        User newUser = this.converNormalUser(dMenu.getOwner());
        newMenu.setOwner(newUser);
        for(DTOUser dU : dMenu.getSharedUsers()){
            User sU = this.converNormalUser(dU);
            newMenu.getSharedUsers().add(sU);
        }
        for(DTOPlate dP : dMenu.getPlates()){
            Plate rP = this.convertPlate(dP);
            newMenu.getPlates().add(rP);
        }
        return null;
    }

    @Override
    public Plate convertPlate(DTOPlate dPlate) {
        Plate newPlate = new Plate();
        newPlate.setId(dPlate.getId());
        newPlate.setCategory(dPlate.getCategory());
        newPlate.setName(dPlate.getName());
        newPlate.setPrice(dPlate.getPrice());    
        return newPlate;
    }

}
