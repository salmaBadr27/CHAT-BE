/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappbackend.DataBase;

import com.mycompany.chatappbackend.App.DataBaseError;
import com.mycompany.chatappbackend.Models.User;
import com.mycompany.chatappbackend.Utility.PGConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Salma
 */
public class UserPostgresRepository extends UserRepository {

    private Statement stat;
    private ResultSet rs;

    public UserPostgresRepository( Connection conn) {
        try {
            System.out.println("Database connection established");
            stat = conn.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAllUsers() throws DataBaseError {
        ArrayList<User> allUsers = new ArrayList<>();
        try {
            String query = "select * from users";
            rs = stat.executeQuery(query);
            if (rs.next() == false) {
                throw new DataBaseError("there is no users found");
            } else {
                do {
                    String userName = rs.getString("User_Name");
                    User user = new User(userName);
                    allUsers.add(user);

                } while (rs.next());
                return allUsers;
            }
        } catch (SQLException ex) {
            System.out.print(ex.getErrorCode());
            throw new DataBaseError(ex.getMessage());
        }

    }

    @Override
    public User getUserByUsername(String username) throws DataBaseError {
        String User_id = null, User_Name = null, password = null, email = null, mobile = null;
        User realUser = new User(User_id, User_Name, password, email, mobile);

        try {
            String query = "select*from users where User_Name = '" + username + "'";
            rs = stat.executeQuery(query);
            if (rs.next() == false) {
                throw new DataBaseError("there is no users found");
            } else {
                do {
                    User_id = rs.getString("user_id");
                    User_Name = rs.getString("User_Name");
                    password = rs.getString("password");
                    email = rs.getString("E_mail");
                    mobile = rs.getString("Mobile");
                    realUser = new User(User_id, User_Name, password, email, mobile);
                } while (rs.next());
            }

        } catch (DataBaseError | SQLException ex) {
            System.out.print(ex);
            throw new DataBaseError(ex.getMessage());
        }
        return realUser;

    }

    @Override
    public User addUser(String userName, String password, String email, String phone) throws DataBaseError {
        try {
            String User_id = UUID.randomUUID().toString();
            stat.executeUpdate("INSERT INTO users (user_id, User_Name , password, E_mail, Mobile) VALUES ('" + User_id + "' , '" + userName + "' , '" + password + "',  '" + email + "' , '" + phone + "')");
            User realUser = new User(User_id, userName, password, email, phone);
            return realUser;

        } catch (SQLException ex) {
            System.out.print(ex.getErrorCode() + ex.getMessage());
            throw new DataBaseError(ex.getMessage());
        }

    }

    @Override
    public User removeUserByUserName(String username) throws DataBaseError {
        String userName = null, pass = null;
        User deletedUser = new User(userName, pass);

        try {
            String query = "select *from users where User_Name = '" + username + "'";
            rs = stat.executeQuery(query);
            if (rs.next() == false) {
                throw new DataBaseError("there is no users found");
            } else {
                do {
                    userName = rs.getString("User_Name");
                    pass = rs.getString("password");
                    deletedUser = new User(userName, pass);
                    if (username.equals(userName)) {
                        stat.executeUpdate("Delete from users where User_Name = '" + username + "'");
                        return deletedUser;
                    }
                } while (rs.next());

            }
        } catch (SQLException ex) {
            System.out.print(ex.getErrorCode() + ex.getMessage());
            throw new DataBaseError(ex.getMessage());
        }
        return deletedUser;
    }

}
