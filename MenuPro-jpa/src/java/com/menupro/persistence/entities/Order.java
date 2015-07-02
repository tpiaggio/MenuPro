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
@NamedQueries({
    @NamedQuery(name = "searchOrdersFromBuyer",
            query = "select o from Order o where o.buyer = :buyer"
    ),
    @NamedQuery(name = "searchMenusFromOrder",
            query = "select menus from Order o join o.menus menus where o.id = :id"
    ),
    @NamedQuery(name = "searchUsersFromOrder",
            query = "select users from Order o join o.sideBuyers users where o.id = :id"
    )
})
@Entity
@Table(name = "Orders")
public class Order implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @ManyToMany
    private List<Menu> menus;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer")
    private User buyer;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> sideBuyers;
    
    private String comments;

    public Order() {
        sideBuyers=new ArrayList<User>();
        menus= new ArrayList<Menu>();
    }

    public Order(Long id, List<Menu> menus, User buyer, Date date, String comments) {
        this.id = id;
        this.menus = menus;
        this.buyer = buyer;
        this.date = date;
        this.comments = comments;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
