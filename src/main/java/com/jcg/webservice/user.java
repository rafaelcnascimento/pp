package com.jcg.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class user {
    
    public static int id;
    public static String nome;
    public static String email;
    public static String cc;
    public static float balanco;
    public static String senha;
    
    /*public user (String email) throws SQLException {
        
        db DB = new db();
        Connection conn = DB.conn;
        
        String sql = "SELECT * FROM users WHERE email = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email); 
 
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            user.id = rs.getInt("id");
            user.nome = rs.getString("nome");
            user.email = rs.getString("email");
            user.cc = rs.getString("cc");
            user.balanco = rs.getFloat("balanco");
        }
    }*/
    
    public static boolean cadastro( String nome, String email, String cc, String senha) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
            
            Connection conn = db.conectar();
            
                    
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
                
        Connection conn = db.conectar();
        
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
        }
           
        return resposta;
    }
    
//    public static void editar(user usuario,db DB ) throws SQLException{
//        
//        String texto;
//        String novoNome;
//        String novoCC;
//        String novoEmail;
//        String novoSenha;
//        
//        Scanner scan = new Scanner(System.in);
//        
//        System.out.println("Digite o novo valor do campo ou enter para não modificar");
//        System.out.println("Nome:");
//        texto = scan.nextLine();
//        if (!texto.equals("")) {
//            novoNome = texto;
//        } else {
//            novoNome = usuario.nome;
//        }
//        
//        System.out.println("Email:");
//        texto = scan.nextLine();
//        if (!texto.equals("")) {
//           novoEmail = texto;
//        } else {
//            novoEmail = usuario.email;
//        }
//        
//        System.out.println("Numero do Cartão de Crédito:");
//        texto = scan.nextLine();
//        if (!texto.equals("")) {
//            novoCC = texto;
//        } else {
//            novoCC = usuario.cc;
//        }
//        
//        System.out.println("Senha:");
//        texto = scan.nextLine();
//        if (!texto.equals("")) {
//            novoSenha = texto;
//        } else {
//            novoSenha = usuario.senha;
//        }
//                 
//        Connection conn = DB.conn;
//        
//        String sql = "UPDATE users SET nome = ?, email = ?, cc = ?, senha = ? WHERE email = ?";
//
//        PreparedStatement stmt = conn.prepareStatement(sql);  
//        stmt.setString(1,novoNome);  
//        stmt.setString(2,novoEmail);  
//        stmt.setString(3,novoCC);  
//        stmt.setString(4,novoSenha);  
//        stmt.setString(5,usuario.email);  
//        stmt.execute();  
//        stmt.close();  
//        
//        System.out.println("\nCadastro editado com sucesso!");
//    }    
}
