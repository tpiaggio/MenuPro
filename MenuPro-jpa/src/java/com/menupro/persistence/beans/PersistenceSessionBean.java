/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.persistence.beans;

import com.menupro.persistence.entities.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 *
 * @author Pepe
 */
@Stateless
public class PersistenceSessionBean implements PersistenceSessionBeanLocal {

    
    @PersistenceContext(unitName = "MenuPro-jpaPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public PersistenceSessionBean(){
        
    }

    // <editor-fold defaultstate="collapsed" desc=" User ">
    
    @Override
    public void deleteTokens(User user) {
        List<Token> tokens = em.createNamedQuery("searchTokensFromUser", Token.class)
                .setParameter("userId", user.getId()).getResultList();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            deleteToken(tokens.get(i));
        }
    }
    
    @Override
    public void addUser(User user) {
        try {
            em.persist(user);
        } catch (PersistenceException e) {
            throw new EntityExistsException("The username " + user.getUserName() + " already exists.", e);
        } catch (Exception e) {
            throw new EntityExistsException("An error occurred, please try again later.", e);
        }
    }

    @Override
    public void editUser(User user) {
        try {
            if (user.getId() != null) {
                em.merge(user);
            } else {
                em.persist(user);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("The user "+user.getUserName() + " was not found.");
        } catch (Exception e) {
            throw new EntityNotFoundException("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteUser(User user) {
        if (user.getId() != null) {
            user = em.merge(user);
            deleteTokens(user);
            em.remove(user);
        } else {
            throw new EntityNotFoundException("The user "+user.getUserName() + " was not found.");
        }
    }

    @Override
    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUser(String userName) {
        try {
            return (User) em.createNamedQuery("searchUser")
                    .setParameter("name", userName).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The user "+userName + " was not found.");
        }
    }

    @Override
    public List<User> searchContacts(User user) {
        try {
            return em.createNamedQuery("searchContacts", User.class)
                    .setParameter("user", user).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> searchSharedUsersFromMenu(Long id) {
        try {
            return em.createNamedQuery("searchBuyersFromMenu", User.class)
                    .setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Plate ">

    @Override
    public void addPlate(Plate plate) {
        try {
            em.persist(plate);
        } catch (PersistenceException e) {
            throw new EntityExistsException("The plate " + plate.getName() + " already exists.", e);
        } catch (Exception e) {
            throw new EntityExistsException("An error occurred, please try again later.", e);
        }
    }

    @Override
    public void editPlate(Plate plate) {
        try {
            if (plate.getId() != null) {
                em.merge(plate);
            } else {
                em.persist(plate);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("The plate "+plate.getName() + " was not found.");
        } catch (Exception e) {
            throw new EntityNotFoundException("An error occurred, please try again later.");
        }
    }

    @Override
    public void deletePlate(Plate plate) {
        if (plate.getId() != null) {
            em.remove(plate);
        } else {
            throw new EntityNotFoundException("The plate "+plate.getName() + " was not found.");
        }
    }

    @Override
    public Plate getPlate(Long id) {
        return em.find(Plate.class, id);
    }
    
    @Override
    public Plate getPlate(String name) {
        try {
            return (Plate) em.createNamedQuery("searchPlate")
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The plate "+ name + " was not found.");
        }
    }

    @Override
    public List<Plate> getPlates() {
        try {
            return em.createQuery("SELECT p from Plate p").getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<Plate> getPlatesFromCategory(String category) {
        try {
            return em.createNamedQuery("searchPlatesFromCategory", Plate.class)
                    .setParameter("category", category).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<Plate> getPlatesFromMenu(Long id) {
        try {
            return em.createNamedQuery("searchPlatesFromMenu", Plate.class)
                    .setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }      
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Menu ">

    @Override
    public void addMenu(Menu menu) {
        try {
            em.persist(menu);
        } catch (PersistenceException e) {
            throw new EntityExistsException("The menu " + menu.getName() + " already exists.", e);
        } catch (Exception e) {
            throw new EntityExistsException("An error occurred, please try again later.", e);
        }
    }

    @Override
    public void editMenu(Menu menu) {
        try {
            if (menu.getId() != null) {
                em.merge(menu);
            } else {
                em.persist(menu);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("The menu "+menu.getName() + " was not found.");
        } catch (Exception e) {
            throw new EntityNotFoundException("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteMenu(Menu menu) {
        if (menu.getId() != null) {
            em.remove(menu);
        } else {
            throw new EntityNotFoundException("The menu "+menu.getName() + " was not found.");
        }
    }

    @Override
    public Menu getMenu(Long id) {
        return em.find(Menu.class, id);
    }
    
    @Override
    public Menu getMenu(String name, User owner) {
        try {
            return (Menu) em.createNamedQuery("searchMenu")
                    .setParameter("name", name).setParameter("owner", owner)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The menu "+ name + " was not found.");
        }
    }

    @Override
    public List<Menu> getMenus(User owner) {
        try {
            return em.createNamedQuery("searchMenus", Menu.class)
                    .setParameter("owner", owner).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<User> getUsersFromMenu(String name, User owner) {
        try {
            return em.createNamedQuery("searchUsersFromMenu", User.class)
                    .setParameter("name", name).setParameter("owner", owner)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<Menu> searchMenusFromOrder(Long id) {
        try {
            return em.createNamedQuery("searchMenusFromOrder", Menu.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Order ">

    @Override
    public void addOrder(Order order) {
        try {
            em.persist(order);
        } catch (Exception e) {
            throw new EntityExistsException("An error occurred, please try again later.", e);
        }
    }

    @Override
    public void editOrder(Order order) {
        try {
            if (order.getId() != null) {
                em.merge(order);
            } else {
                em.persist(order);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("The order was not found.");
        } catch (Exception e) {
            throw new EntityNotFoundException("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteOrder(Order order) {
     if (order.getId() != null) {
            em.remove(order);
        } else {
            throw new EntityNotFoundException("The order was not found.");
        }
    }

    @Override
    public Order getOrder(Long id) {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> getOrders(User buyer) {
        try {
            return em.createNamedQuery("searchOrdersFromBuyer", Order.class)
                    .setParameter("buyer", buyer).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getUsersFromOrder(Long id) {
        try {
            return em.createNamedQuery("searchUsersFromOrder", User.class)
                    .setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Token ">
    
    @Override
    public void addToken(Token token) {
        try {
            em.persist(token);
        }catch (Exception e) {
            throw new EntityExistsException("An error occurred, please try again later.", e);
        }
    }

    @Override
    public void deleteToken(Token token) {
        if (token.getId() != null) {
            em.remove(token);
        } else {
            throw new EntityNotFoundException("An error occurred while logging out");
        }
    }

    @Override
    public Token getToken(String token) {
        try {
            return (Token) em.createNamedQuery("searchToken")
                    .setParameter("token", token).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The Token " + token + " was not found.");
        }
    }
    
    // </editor-fold>

    

    

   
    
}
