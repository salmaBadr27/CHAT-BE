/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappbackend.App;

/**
 *
 * @author Salma
 */
public class AuthenticationError  extends Error {
     public AuthenticationError(String errorMessage) {
        super(errorMessage, "BAD_JWT", "Something Unexpected Happened");
    }
     
    
}
