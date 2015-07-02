/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author User
 */
public class DTOMenu {
    private Long code;
    private String name;
    private DTOUser owner;
    private List<DTOPlate> plates;
    private List<DTOUser> sharedUsers;
    private String token;
    
    public DTOMenu() {
        this.plates = new ArrayList<DTOPlate>();
        this.sharedUsers = new ArrayList<DTOUser>();
    }
    
    public DTOMenu(Long id, String name, DTOUser owner, List<DTOPlate> plates) {
        this.code = id;
        this.name = name;
        this.owner = owner;
        this.plates = plates;
        this.sharedUsers = new ArrayList<DTOUser>();
    }

    public DTOMenu(Long id, String name, DTOUser owner, List<DTOUser> sharedUsers, String token) {
        this.code = id;
        this.name = name;
        this.owner = owner;
        this.sharedUsers = sharedUsers;
        this.token = token;
    }
    
    public Long getId() {
        return code;
    }

    public void setId(Long id) {
        this.code = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DTOUser getOwner() {
        return owner;
    }

    public void setOwner(DTOUser owner) {
        this.owner = owner;
    }

    public List<DTOPlate> getPlates() {
        return plates;
    }

    public void setPlates(List<DTOPlate> plates) {
        this.plates = plates;
    }

    public List<DTOUser> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<DTOUser> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.code);
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
        final DTOMenu other = (DTOMenu) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
    
    
}
