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
public class DataBaseError extends Error{
    public DataBaseError(String errorMessage ) {
        super(errorMessage, "DB_ERROR","Some Thing Unexpected Happened " );
        
    } 
}
