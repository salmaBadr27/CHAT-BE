package com.mycompany.chatappbackend.DataBase;

import com.mycompany.chatappbackend.App.DataBaseError;
import com.mycompany.chatappbackend.Models.Message;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class MessagesPostgresRepository extends MessageRepository{

    private Statement stat;
    private ResultSet rs;

    public MessagesPostgresRepository(Connection conn) {
        try {
            System.out.println("Database connection established");
            stat = conn.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList getAllMessages(String username)throws DataBaseError {
        ArrayList<Message> totalMessages = new ArrayList<>();
        try {
            String query = " select * from messages where sender = '" + username + "' or receiver = '" + username + "' order by created_at asc ";
            rs = stat.executeQuery(query);
            while (rs.next()) {

                String msgId = rs.getString("Msg_Id");
                String msgBody = rs.getString("Msg_body");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                Timestamp created_at = rs.getTimestamp("created_at");
                Message message = new Message(msgId, msgBody, sender, receiver, created_at);
                totalMessages.add(message);
            }

        } catch (SQLException ex) {

         throw new DataBaseError(ex.getMessage());
        }
        return totalMessages;
    }

    @Override
    public Message sendMessages(String body, String msgsender, String msgreceiver)throws DataBaseError {

        try {
            String msgid = UUID.randomUUID().toString();
            Timestamp created_at = new Timestamp(System.currentTimeMillis());
            Message newMsg = new Message(msgid, body, msgsender, msgreceiver, created_at);
            stat.executeUpdate("INSERT INTO messages (Msg_Id, msg_body, sender, receiver,created_at) VALUES ('" + msgid + "','" + body + "','" + msgsender + "','" + msgreceiver + "','" + created_at + "')");
            return newMsg;
        } catch (SQLException ex) {
            throw new DataBaseError(ex.getMessage());

        }
      
    }

    @Override
    public ArrayList getMessageBySender(String sender)throws DataBaseError {
        ArrayList<Message> totalMessages = new ArrayList<>();
        try {
            String query = " select * from messages where sender = '" + sender + "'";
            rs = stat.executeQuery(query);
            if (rs.next() == false) {

                  throw new DataBaseError("no matched username found");
            } else {
                do {
                    String msgBody = rs.getString("Msg_body");
                    String msgSender = rs.getString("sender");
                    String msgReceiver = rs.getString("receiver");
                    Timestamp created_at = rs.getTimestamp("created_at");
                    Message sendedMessage = new Message(msgBody, msgSender, msgReceiver, created_at);
                    totalMessages.add(sendedMessage);
                    return totalMessages;
                } while (rs.next());
            }
        } catch (SQLException ex) {
          throw new DataBaseError(ex.getMessage());

        }
       
    }

    @Override
    public ArrayList getMessageByReceiver(String receiver)throws DataBaseError {
        ArrayList<Message> totalMessages = new ArrayList<>();
        try {
            String query = " select * from messages where sender = '" + receiver + "' ";
            rs = stat.executeQuery(query);
            if (rs.next()) {

                  throw new DataBaseError("no matched username found");
            } else {
                do {
                    String msgBody = rs.getString("Msg_body");
                    String msgSender = rs.getString("sender");
                    String msgReceiver = rs.getString("receiver");
                    Timestamp created_at = rs.getTimestamp("created_at");
                    Message receivedMessage = new Message(msgBody, msgSender, msgReceiver, created_at);
                    totalMessages.add(receivedMessage);
                    return totalMessages;
                } while (rs.next());
            }

        } catch (SQLException ex) {
          throw new DataBaseError(ex.getMessage());

        }
     
    }

    @Override
    public ArrayList removeMessageBySender(String sender)throws DataBaseError {
        ArrayList<Message> deletedMessages = new ArrayList<>();
        try {
            String qyery = " select * from messages where sender = '" + sender + "' ";
            rs = stat.executeQuery(qyery);
            if (rs.next() == false) {
                 throw new DataBaseError("no matched username found");
            } else {
                do {
                    String msgBody = rs.getString("Msg_body");
                    String msgSender = rs.getString("sender");
                    String msgReceiver = rs.getString("receiver");
                    Timestamp created_at = rs.getTimestamp("created_at");
                    Message sendedMessage = new Message(msgBody, msgSender, msgReceiver, created_at);
                    deletedMessages.add(sendedMessage);
                    stat.executeUpdate("delete from messages where sender = '" + sender + "'");
                    return deletedMessages;
                } while (rs.next());
            }

        } catch (SQLException ex) {
           throw new DataBaseError(ex.getMessage());

        }
       
    }

}
