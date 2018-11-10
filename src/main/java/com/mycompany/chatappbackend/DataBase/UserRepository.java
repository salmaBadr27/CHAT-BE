/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappbackend.DataBase;

import com.mycompany.chatappbackend.App.DataBaseError;
import com.mycompany.chatappbackend.Models.User;
import java.util.ArrayList;

/**
 *
 * @author Salma
 */
public  abstract class UserRepository {
    
    abstract public User getUserByUsername(String username)throws DataBaseError ;

    abstract public User addUser(String userName, String password,String email , String phone) throws DataBaseError;

    abstract public User removeUserByUserName(String username)throws DataBaseError;
        
    abstract public ArrayList<User> getAllUsers ()throws DataBaseError;
}
