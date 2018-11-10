/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappbackend.Utility;

import com.google.gson.Gson;
import com.mycompany.chatappbackend.App.AuthenticationError;
import com.mycompany.chatappbackend.App.DataBaseError;
import com.mycompany.chatappbackend.DataBase.UserRepository;
import com.mycompany.chatappbackend.Models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Salma
 */
public class Authentication {
     private final String secret;
    Gson gson = new Gson();

    public Authentication(String secretKey) {
        this.secret = secretKey;
    }

    public String generateToken(String data) {
        String compactJws = Jwts.builder()
                .setPayload(data)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return compactJws;
    }

    public User AuthenticateWithJson(UserRepository ur, String userinfo) throws AuthenticationError, DataBaseError {

        try {
            User fakeUser = gson.fromJson(userinfo, User.class);
            User realUser = ur.getUserByUsername(fakeUser.getUserName());
            if (realUser.getPassword().equals(fakeUser.getPassword())) {
                return realUser;
            } else {
                throw new AuthenticationError("Password not valid");
            }

        } catch (DataBaseError | AuthenticationError e) {
            System.out.print("caught in auth " + e);
            throw e;
        }
    }

    public User AuthenticateWithToken(UserRepository ur, String Token) throws AuthenticationError, DataBaseError {
        try {
            String[] split_string = Token.split("\\.");
            String Header = split_string[0];
            String Payload = split_string[1];
            String Signature = split_string[2];
            Base64 base64Url = new Base64(true);
            String body = new String(base64Url.decode(Payload));
            User user = AuthenticateWithJson(ur, body);
            return user;
        } catch (AuthenticationError | DataBaseError | Exception e) {
            System.out.print("caught in auth with token " + e);
            throw e;
        }
    }


}