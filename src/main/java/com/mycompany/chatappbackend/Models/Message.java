/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappbackend.Models;

import java.sql.Timestamp;

/**
 *
 * @author Salma
 */
public class Message {
    // instances

    private  String  messageId;
    private String messageBody;
    private  String sender;
    private  String receiver;
    private Timestamp created_at;


    //Constructors
    public Message( String messageBody, String msgSender, String msgReciver,Timestamp created_at) {

        this.messageBody = messageBody;
        this.sender = msgSender;
        this.receiver = msgReciver;
        this.created_at = created_at;
    }

    public Message(String messageId, String messageBody, String sender, String receiver,Timestamp created_at) {
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.sender = sender;
        this.receiver = receiver;
         this.created_at = created_at;
    }
    
 public Message( String messageBody, String sender, String receiver) {
      
        this.messageBody = messageBody;
        this.sender = sender;
        this.receiver = receiver;
         
    }


 
    public Timestamp getCreated_at() {
        return created_at;
    }

   
 
    
    //Getters and Setters
    public String getMessageId() {
        return this.messageId;

    }

    public String getMessageBody() {
        return this.messageBody;

    }

    public void setMessageBody(String msgBody) {
        this.messageBody = msgBody;
    }

    public String getSender () {
        return this.sender;
    }

    public String getReciver () {
        return this.receiver;
    }

    // Message.toString()
    @Override
    public String toString() {
        return "From:" + sender + "\n" + "To:" + receiver + "\n" + "Subject::" + messageBody;
    }

}
