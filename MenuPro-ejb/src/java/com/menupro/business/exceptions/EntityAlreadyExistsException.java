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
public class EntityAlreadyExistsException extends Exception{
    
    public EntityAlreadyExistsException() {
        super();
    }
    
    public EntityAlreadyExistsException(String message){
        super(message);
    }
    
    public EntityAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
    
    public EntityAlreadyExistsException(Throwable cause){
        super(cause);
    }
}
