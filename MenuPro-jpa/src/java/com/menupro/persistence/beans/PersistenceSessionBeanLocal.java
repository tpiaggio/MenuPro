/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.persistence.beans;

import com.menupro.persistence.entities.*;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pepe
 */
@Local
public interface PersistenceSessionBeanLocal {

    void addUser(User user);

    void deleteTokens(User user);

    void editUser(User user);

    void deleteUser(User user);

    User getUser(Long id);

    User getUser(String userName);

    List<User> searchContacts(User user);

    void addPlate(Plate plate);

    void editPlate(Plate plate);

    void deletePlate(Plate plate);

    Plate getPlate(Long id);
    
    Plate getPlate(String name);

    List<Plate> getPlates();

    List<Plate> getPlatesFromCategory(String category);

    void addMenu(Menu menu);

    void editMenu(Menu menu);

    void deleteMenu(Menu menu);

    Menu getMenu(Long id);
    
    Menu getMenu(String name, User owner);

    List<Menu> getMenus(User owner);

    List<User> getUsersFromMenu(String name, User owner);
    
    void addOrder(Order order);

    void editOrder(Order order);

    void deleteOrder(Order order);
    
    Order getOrder(Long id);
    
    List<Order> getOrders(User buyer);

    List<User> getUsersFromOrder(Long id);

    void addToken(Token token);

    void deleteToken(Token token);

    Token getToken(String token);

    List<Plate> getPlatesFromMenu(Long id);

    List<User> searchSharedUsersFromMenu(Long id);
}
