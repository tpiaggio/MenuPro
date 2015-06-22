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
public class InvalidDataException extends Exception{
     
    public InvalidDataException() {
        super();
    }
    
    public InvalidDataException(String message){
        super(message);
    }
    
    public InvalidDataException(String message, Throwable cause){
        super(message, cause);
    }
    
    public InvalidDataException(Throwable cause){
        super(cause);
    }
}
