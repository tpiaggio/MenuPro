/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.persistence.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author User
 */
@NamedQueries({
    @NamedQuery(name = "searchMenu",
            query = "select m from Menu m where m.name = :name and m.owner = :owner"
    ),
    @NamedQuery(name = "searchMenus",
            query = "select m from Menu m where m.owner = :owner"
    ),
    @NamedQuery(name = "searchUsersFromMenu",
            query = "select users from Menu m join m.sharedUsers users where m.name = :name and m.owner = :owner"
    ),
    @NamedQuery(name = "searchPlatesFromMenu",
            query = "select plates from Menu m join m.plates plates where m.name = :name and m.owner = :owner"
    )
})
@Entity
@Table(name = "Menus", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "owner"})
})
public class Menu implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String name;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private User owner;
    
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Plate> plates; 
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> sharedUsers;

    public Menu() {
        sharedUsers = new ArrayList<User>();
        plates = new ArrayList<Plate>();
    }

    public Menu(Long id, String name, User owner, List<Plate> plates) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.plates = plates;
        this.sharedUsers = new ArrayList<User>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Plate> getPlates() {
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }

    public List<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Menu other = (Menu) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
