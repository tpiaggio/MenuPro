/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.dtos;

import java.util.*;

/**
 *
 * @author User
 */
public class DTOOrder {
    private Long code;
    private List<DTOMenu> menus;
    private DTOUser buyer;
    private Date date;
    private List<DTOUser> sideBuyers;
    private String comments;
    private String token;
    
    public DTOOrder() {
        sideBuyers = new ArrayList<DTOUser>();
        menus = new ArrayList<DTOMenu>();
    }

    public DTOOrder(Long id, List<DTOMenu> menus, DTOUser buyer, Date date, List<DTOUser> sideBuyers, String comments) {
        this.code = id;
        this.menus = menus;
        this.buyer = buyer;
        this.date = date;
        this.sideBuyers = sideBuyers;
        this.comments = comments;
    }

    public DTOOrder(Long id, List<DTOMenu> menus, DTOUser buyer, Date date, List<DTOUser> sideBuyers, String comments, String token) {
        this.code = id;
        this.menus = menus;
        this.buyer = buyer;
        this.date = date;
        this.sideBuyers = sideBuyers;
        this.comments = comments;
        this.token = token;
    }
    
    

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public List<DTOMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<DTOMenu> menus) {
        this.menus = menus;
    }

    public DTOUser getBuyer() {
        return buyer;
    }

    public void setBuyer(DTOUser buyer) {
        this.buyer = buyer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<DTOUser> getSideBuyers() {
        return sideBuyers;
    }

    public void setSideBuyers(List<DTOUser> sideBuyers) {
        this.sideBuyers = sideBuyers;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        hash = 73 * hash + Objects.hashCode(this.code);
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
        final DTOOrder other = (DTOOrder) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
    
    
}
