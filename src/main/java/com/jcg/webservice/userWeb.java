package com.jcg.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class userWeb {

    public static int id;
    public static String nome;
    public static String email;
    public static String cc;
    public static float balanco;
    public static String senha;
    
    public static boolean cadastro( String nome, String email, String cc, String senha) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
            
            Connection conn = dbWeb.conectar();

            String sql = "INSERT INTO users(nome,email,cc,senha) VALUES(?,?,?,?)";  
          
            PreparedStatement stmt = conn.prepareStatement(sql);  
            stmt.setString(1, nome);  
            stmt.setString(2, email);  
            stmt.setString(3, cc);  
            stmt.setString(4, senha);  
            stmt.execute();  
            stmt.close();  
            
            return true;
    }
    
    public static JSONObject login( String email, String senha) throws SQLException, JSONException {
                
        Connection conn = dbWeb.conectar();
        
        JSONObject resposta = new JSONObject();
        
        String sql = "SELECT * FROM users WHERE email = ? AND senha = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, senha);  
 
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            resposta.put("id", rs.getInt("id")); 
            resposta.put("nome", rs.getString("nome")); 
            resposta.put("email", rs.getString("email")); 
            resposta.put("cc", rs.getString("cc")); 
            resposta.put("senha", rs.getString("senha")); 
            resposta.put("balanco", rs.getFloat("balanco")); 
            resposta.put("ativo",rs.getBoolean("ativo"));
        }
        
        return resposta;
    }
    
    public static String editar(String nome, String email, String cc, String senha ) throws SQLException{
    
        String sql = "UPDATE users SET nome = ?, email = ?, cc = ?, senha = ? WHERE id = ?";

        Connection conn = dbWeb.conectar();
        
        int idUser = dbWeb.getId(email);
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);  
            stmt.setString(1,nome);  
            stmt.setString(2,email);  
            stmt.setString(3,cc);  
            stmt.setString(4,senha);  
            stmt.setInt(5,idUser);  
            stmt.execute();  
            
            return "ok";
        } catch (SQLException e) {
             return "not ok";
        }        
    }    
    
    public static boolean remover(int id) throws SQLException{
        Connection conn = dbWeb.conectar();
       
        String sql = "UPDATE users SET ativo = 0 WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();  
        return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
