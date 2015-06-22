/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author User
 */
public class DTOUser {

    
    private Long id;
    private String userName;
    private String name;
    private String password;
    private List<DTOUser> contacts;

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

    public List<DTOUser> getContacts() {
        return contacts;
    }

    public void setContacts(List<DTOUser> contacts) {
        this.contacts = contacts;
    }

    public DTOUser() {
        this.contacts=new ArrayList<DTOUser>();
    }

    public DTOUser(Long id, String userName, String name, String password) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.contacts=new ArrayList<DTOUser>();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final DTOUser other = (DTOUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        // TODO code application logic here
    }*/
    
}
