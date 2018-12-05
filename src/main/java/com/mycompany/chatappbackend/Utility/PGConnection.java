package com.mycompany.chatappbackend.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PGConnection {

    public Connection con;
    public static PGConnection instance;

    public PGConnection() {
        try {

            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(
                    "jdbc:postgresql://ec2-54-235-90-0.compute-1.amazonaws.com:5432/d8s37mhtu37e3h?user=gxhxtpymtmgykq&password=10f6987d9bb9a6a00a34be8def9ddd85006c7154016c1e4efcf312313f8f38a2&sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
            );
            if (con != null) {
                System.out.println("Connected to the database");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Not connected");
        }
    }

    public static PGConnection getInstance() {
        if (instance == null) {
            instance = new PGConnection();
        }
        return instance;
    }

    public Connection getConnection() {

        return this.con;
    }
}
