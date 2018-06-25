package com.jcg.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbWeb {
    
    public static Connection conectar() {

        Connection conn = null;

        String url = "jdbc:mysql://localhost:3306/pagpal?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(dbWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(dbWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(dbWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }
    
    public static String receberJson(InputStream incomingData){
        
        StringBuilder crunchifyBuilder = new StringBuilder();
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error Parsing: - ");
        }

        return crunchifyBuilder.toString();
    }

   public static int getId(String email) throws SQLException{
        
       int id = 0;
       
       Connection conn = dbWeb.conectar();
    
       String sql = "SELECT id FROM users WHERE email = ?";
        
       PreparedStatement stmt = conn.prepareStatement(sql);  
       stmt.setString(1, email);  
        
       ResultSet rs = stmt.executeQuery();
       while (rs.next()) {
          id = rs.getInt("id");
       }
       return id;
   }

}


