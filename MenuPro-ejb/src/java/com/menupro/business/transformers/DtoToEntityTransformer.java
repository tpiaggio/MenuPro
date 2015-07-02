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
        User user = new User();
        user.setId(dUser.getId());
        user.setName(dUser.getName());
        user.setPassword(dUser.getPassword());
        user.setUserName(dUser.getUserName());
        for(DTOUser dU : dUser.getContacts()){
            User listUser = new User(dU.getId(),dU.getUserName(),
                dU.getName(), dU.getPassword());
            user.getContacts().add(listUser);
        }
        return user;
    }
    
    public User converNormalUser(DTOUser dUser){
        User user = new User();
        user.setId(dUser.getId());
        user.setName(dUser.getName());
        user.setPassword(dUser.getPassword());
        user.setUserName(dUser.getUserName());
        return user;
    }
    
    @Override
    public Order convertOrder(DTOOrder dOrder) {
        Order order = new Order();
        order.setComments(dOrder.getComments());
        order.setDate(dOrder.getDate());
        order.setId(dOrder.getCode());
        User newUser = this.converNormalUser(dOrder.getBuyer());
        order.setBuyer(newUser);
        for(DTOUser dUser : dOrder.getSideBuyers()){
            User user = this.converNormalUser(dUser);
            order.getSideBuyers().add(user);
        }
        for(DTOMenu dMenu : dOrder.getMenus()){
            Menu menu = this.convertMenu(dMenu);
            order.getMenus().add(menu);
        }
        return order;
    }
    
    @Override
    public Menu convertMenu(DTOMenu dMenu) {
        Menu menu = new Menu();
        menu.setId(dMenu.getId());
        menu.setName(dMenu.getName());
        User user = this.converNormalUser(dMenu.getOwner());
        menu.setOwner(user);
        for(DTOUser dUser : dMenu.getSharedUsers()){
            User u = this.converNormalUser(dUser);
            menu.getSharedUsers().add(u);
        }
        for(DTOPlate dPlate : dMenu.getPlates()){
            Plate plate = this.convertPlate(dPlate);
            menu.getPlates().add(plate);
        }
        return menu;
    }

    @Override
    public Plate convertPlate(DTOPlate dPlate) {
        Plate plate = new Plate();
        plate.setId(dPlate.getId());
        plate.setCategory(dPlate.getCategory());
        plate.setName(dPlate.getName());
        plate.setPrice(dPlate.getPrice());    
        return plate;
    }
}
