package com.mycompany.chatappbackend;

import com.google.gson.Gson;
import com.mycompany.chatappbackend.App.AuthenticationError;
import com.mycompany.chatappbackend.App.DataBaseError;
import com.mycompany.chatappbackend.DataBase.MessageRepository;
import com.mycompany.chatappbackend.DataBase.MessagesPostgresRepository;
import com.mycompany.chatappbackend.DataBase.UserPostgresRepository;
import com.mycompany.chatappbackend.DataBase.UserRepository;
import com.mycompany.chatappbackend.Models.Message;
import com.mycompany.chatappbackend.Models.User;
import com.mycompany.chatappbackend.Utility.Authentication;
import com.mycompany.chatappbackend.Utility.PGConnection;
import java.util.ArrayList;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        
        
        PGConnection conn = new PGConnection();
        UserRepository users = new UserPostgresRepository(conn.getConnection());
        MessageRepository messages = new MessagesPostgresRepository(conn.getConnection());
        Authentication auth = new Authentication("secret");
        Gson gson = new Gson();
        port(Integer.valueOf(System.getenv("PORT")));

        System.out.println("*************** The App just started *************** ");
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }
                    

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin","*"));
        before((request, response) -> response.header("Access-Control-Expose-Headers", "token"));
        //**********************Users Api's*****************//
        //login
        post("/login", (req, res) -> {
            try {
                String userinfo = req.body();
                User realUser = auth.AuthenticateWithJson(users, userinfo);
                String token = auth.generateToken(userinfo);
                res.header("token", token);
                return gson.toJson(realUser);

            } catch (AuthenticationError | DataBaseError ex) {
                return ex;
            }

        });

        //signup
        post("/signup", (req, res) -> {
            try {
                String userinfo = req.body();
                User fakeUser = gson.fromJson(userinfo, User.class);
                User newUser = users.addUser(fakeUser.getUserName(), fakeUser.getPassword(), fakeUser.getE_mail(), fakeUser.getMobileNum());

                String token = auth.generateToken(userinfo);
                res.header("token", token);
                newUser.setToken(token);
                return gson.toJson(newUser);
            } catch (DataBaseError ex) {
                return ex;
            }
        });

        //delete user by useer name
        delete("/user/:username", (req, res) -> {

            try {
                String username = req.params("username");
                String token = req.headers("Authentication");
                User isUser = auth.AuthenticateWithToken(users, token);
                if (isUser.getUserName().equals(username)) {
                    User DeletedUser = users.removeUserByUserName(username);
                    return gson.toJson(DeletedUser);
                }
            } catch (DataBaseError | AuthenticationError e) {

                return e;
            }
            return null;
        });

        //get allusers
        get("/users", (req, res) -> {
            try {
                String token = req.headers("Authentication");
                User isUser = auth.AuthenticateWithToken(users, token);
                ArrayList<User> allUsers = users.getAllUsers();
                return gson.toJson(allUsers);
            } catch (DataBaseError | AuthenticationError e) {
                return e;
            }

        });
        //**********************************Messages Api's*************************************************//

        // get all messages
        get("/messages", (req, res) -> {
            try {
                String token = req.headers("Authentication");
                User isUser = auth.AuthenticateWithToken(users, token);
                String userName = isUser.getUserName();
                ArrayList<Message> allMessages = messages.getAllMessages(userName);
                System.out.print("messages" + gson.toJson(allMessages));
                return gson.toJson(allMessages);

            } catch (AuthenticationError | DataBaseError e) {
                return e;
            }

        });
        
        // send message 
        post("/message", (req, res) -> {
            try {
                String token = req.headers("Authentication");
                String newMessage = req.body();
                Message sendedMessage = gson.fromJson(newMessage, Message.class);
                User isuser = auth.AuthenticateWithToken(users,token);
                User validReceiver = users.getUserByUsername(sendedMessage.getReciver());
                Message sendMessage = messages.sendMessages(sendedMessage.getMessageBody(), isuser.getUserName(), sendedMessage.getReciver());
                return gson.toJson(sendMessage);
            } catch (DataBaseError | AuthenticationError e) {
                return e;
            }
        });
        
       //get messages by sender 
        get("Msgs/:username", (req, res) -> {
            ArrayList<Message> sendedMessages = null;
            try {
                String token = req.headers("Authentication");
                String isSender = req.params("username");
                User isUser = auth.AuthenticateWithToken(users, token);
                if (isSender.equals(isUser.getUserName())) {
                    sendedMessages = messages.getMessageBySender(isSender);
                }
                return gson.toJson(sendedMessages);

            } catch (DataBaseError | AuthenticationError e) {
                return e;
            }

        });
//
        //remove messages by sender 
        delete("messages/:sender", (req, res) -> {
            ArrayList<Message> deletedMsgs = null;
            try {
                String token = req.headers("Authentication");
                User isUser = auth.AuthenticateWithToken(users, token);
                String sender = req.params("sender");
                if (sender.equals(isUser.getUserName())) {
                    deletedMsgs = messages.removeMessageBySender(sender);
                    return gson.toJson(deletedMsgs);
                }
            } catch (DataBaseError | AuthenticationError e) {
                return e;
            }
           return deletedMsgs;
        });
        //get all messages by user name
        get("/Messages", (req, res) -> {
            try {
                String token = req.headers("Authentication");
                User isUser = auth.AuthenticateWithToken(users, token);
                String userName = isUser.getUserName();
                ArrayList<Message> allMessages = messages.getAllMessages(userName);
                return gson.toJson(allMessages);
            } catch (DataBaseError | AuthenticationError e) {
                return e;
            }

        });

    }

}
