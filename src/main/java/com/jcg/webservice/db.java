	package com.jcg.webservice;
    
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.Scanner;
    import java.util.logging.Level;
    import java.util.logging.Logger;
import org.json.JSONObject;


    public class db{
    
    public static Connection conectar() {

        Connection conn = null;

        String url = "jdbc:mysql://localhost:3306/pagpal?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }

        return crunchifyBuilder.toString();
    }
//   public String DB_URL = "jdbc:mysql://localhost/pagpal";
//   public String USER = "root";
//   public String PASS = "";
//   public Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//   public PreparedStatement stmt;
//   
//   public db() throws SQLException {
//       
//	   this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
//   }
//     
   public static int getId(String email) throws SQLException{
        
       int id = 0;
       
       Connection conn = db.conectar();
    
       String sql = "SELECT id FROM users WHERE email = ?";
        
       PreparedStatement stmt = conn.prepareStatement(sql);  
       stmt.setString(1, email);  
        
       ResultSet rs = stmt.executeQuery();
       while (rs.next()) {
          id = rs.getInt("id");
       }
       return id;
   }
//   
//   public void updateBalanco(Float quantidade, String email,int tipo) throws SQLException{
//          
//       if (tipo == 1) {
//            String sql = "UPDATE users SET balanco = balanco + ? WHERE email = ?";
//
//            stmt = conn.prepareStatement(sql);
//            stmt.setFloat(1, quantidade); 
//            stmt.setString(2, email); 
//            stmt.execute();
//            stmt.close();
//       } else {
//           String sql = "UPDATE users SET balanco = balanco - ? WHERE email = ?";
//
//            stmt = conn.prepareStatement(sql);
//            stmt.setFloat(1, quantidade); 
//            stmt.setString(2, email); 
//            stmt.execute();
//            stmt.close();
//       }
//   }
//   
//   public void setPagamentos(int sender_id, int receiver_id, float quantidade) throws SQLException {
//            String sql = "INSERT INTO pagamentos(sender_id,receiver_id,valor) VALUES(?,?,?)";
//        
//            stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, sender_id); 
//            stmt.setInt(2, receiver_id); 
//            stmt.setFloat(3, quantidade);
//            stmt.executeUpdate();
//   }
//           
//   public  void setOrdens(int requisitante_id, int requisitado_id, float quantidade, String mensagem, int tipo) throws SQLException{
//       if (tipo == 1) {
//            String sql = "INSERT INTO ordens(requisitante_id,requisitado_id,valor,mensagem) VALUES(?,?,?,?)";
//        
//            stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, requisitante_id); 
//            stmt.setInt(2, requisitado_id); 
//            stmt.setFloat(3, quantidade);
//            stmt.setString(4, mensagem);
//            stmt.executeUpdate();
//       }
//   }
//   
//   public ResultSet checkDestinatario(String email) throws SQLException{
//        
//        String sql = "SELECT id FROM users WHERE email = ?";
//
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setString(1, email); 
//
//        ResultSet rs = stmt.executeQuery();
//        
//        return rs;
//
//   }
//   
//   public void deleteOrdem(int id) throws SQLException{
//       
//        String sql = "DELETE FROM ordens WHERE id = ?";
//
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setInt(1, id); 
//        stmt.executeUpdate();
//        
//        System.out.println("Ordem deletada com sucesso");
//   }
//   
//   public void aceitarOrdem(int id, db DB,user usuario) throws SQLException {
//        
//       Scanner scan = new Scanner(System.in);
//        
//       PreparedStatement stmt = null;
//       
//       int opcao;
//       String sql;
//       String receiver = null;
//       String sender = null;
//       float quantidade =0;
//       
//       System.out.println("1-Aceitar");
//       System.out.println("2-Negar");
//       
//       opcao = scan.nextInt();
//       
//       switch (opcao) {
//            case 1:
//                sql = "UPDATE ordens SET aceito = 1 WHERE id = ?";
//
//                stmt = conn.prepareStatement(sql);
//                stmt.setInt(1, id); 
//                stmt.execute();
//                
//                sql = "SELECT o.id,o.requisitante_id,o.requisitado_id,u1.email as receiver,u2.email as sender,o.valor,o.aceito\n" +
//                      "FROM ordens o  \n" +
//                      "INNER JOIN users u1 on u1.id = o.requisitante_id\n" +
//                      "INNER JOIN users u2 on u2.id = o.requisitado_id\n" +
//                      "WHERE o.id = ?";
//
//                stmt = conn.prepareStatement(sql);
//                stmt.setInt(1, id); 
//                ResultSet rs = stmt.executeQuery();
//                
//                while (rs.next()) {
//                    receiver = rs.getString("receiver");
//                    sender = rs.getString("sender");
//                    quantidade = rs.getFloat("valor");
//                }
//                
//                DB.updateBalanco(quantidade, sender, 0);
//                DB.updateBalanco(quantidade, receiver, 1);
//                
//                if (receiver == usuario.email) {
//                    usuario.balanco += quantidade;
//                } else {
//                    usuario.balanco -= quantidade;
//                }
//                break;
//            case 2:
//                sql = "UPDATE ordens SET aceito = 0 WHERE id = ?";
//
//                stmt = conn.prepareStatement(sql);
//                stmt.setInt(1, id); 
//                stmt.execute();
//                break;
//            default:
//                System.out.println("Opção invalida");
//                break;
//       }
//   }
}


