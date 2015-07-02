/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Pepe
 */
@NamedQueries({
    @NamedQuery(name = "searchUser",
            query = "select u from User u where u.userName = :name"
    ),
    @NamedQuery(name = "searchContacts",
            query = "select u.contacts from User u where u = :user"
    )
})
@Entity
@Table(name = "Users")
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(unique = true)
    private String userName;
    
    @NotNull
    private String name;
    
    @NotNull
    private String password;
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Contacts",
           joinColumns = @JoinColumn(name = "userId"),
           inverseJoinColumns = @JoinColumn(name = "contactId")
    )
    private List<User> contacts;

    public User() {
        this.contacts = new ArrayList<User>();
    }

    public User(Long id, String userName, String name, String password) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.contacts = new ArrayList<User>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
