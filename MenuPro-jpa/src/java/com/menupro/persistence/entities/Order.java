/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.persistence.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author User
 */
@Entity
public class Order implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private List<Menu> menus;
    
    @NotNull
    @ManyToOne
    private User buyer;
    
    @NotNull
    private Date date;
    
    @ManyToMany
    private List<User> sideBuyers;

    public Order() {
        sideBuyers=new ArrayList<User>();
        menus= new ArrayList<Menu>();
    }

    public Order(Long id, List<Menu> menus, User buyer, Date date) {
        this.id = id;
        this.menus = menus;
        this.buyer = buyer;
        this.date = date;
        sideBuyers=new ArrayList<User>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getSideBuyers() {
        return sideBuyers;
    }

    public void setSideBuyers(List<User> sideBuyers) {
        this.sideBuyers = sideBuyers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
