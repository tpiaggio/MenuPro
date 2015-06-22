/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.business.exceptions;

/**
 *
 * @author Pepe
 */
public class EntityDoesntExistsException extends Exception{
    
    public EntityDoesntExistsException() {
        super();
    }
    
    public EntityDoesntExistsException(String message){
        super(message);
    }
    
    public EntityDoesntExistsException(String message, Throwable cause){
        super(message, cause);
    }
    
    public EntityDoesntExistsException(Throwable cause){
        super(cause);
    }
}
