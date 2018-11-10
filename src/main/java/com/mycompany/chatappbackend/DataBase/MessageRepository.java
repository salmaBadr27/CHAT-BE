/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappbackend.DataBase;

import com.mycompany.chatappbackend.App.DataBaseError;
import com.mycompany.chatappbackend.Models.Message;
import java.util.ArrayList;

/**
 *
 * @author Salma
 */
abstract public class MessageRepository {
   abstract public ArrayList getAllMessages(String username) throws DataBaseError;

    abstract public Message sendMessages(String body, String sender, String receiver)throws DataBaseError;

  abstract public ArrayList getMessageBySender(String sender)throws DataBaseError;

   abstract public ArrayList getMessageByReceiver(String receiver)throws DataBaseError;

   abstract public ArrayList removeMessageBySender(String sender)throws DataBaseError;
}