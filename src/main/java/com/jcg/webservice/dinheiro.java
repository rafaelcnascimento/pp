package com.jcg.webservice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class dinheiro {

    public static String transferir(String remetente,int sender_id, String destinatario, Float quantidade) throws SQLException{
        
        String sql;

        Connection conn = db.conectar();
        
        try {
            sql = "UPDATE users SET balanco = balanco + ? WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, quantidade);
            stmt.setString(2, destinatario);
            int resultado = stmt.executeUpdate();
            
            if(resultado == 0){
                throw new SQLException("Destinatario inexistente");
            }
            
            int receiver_id = db.getId(destinatario);
            
            sql = "UPDATE users SET balanco = balanco - ? WHERE email = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, quantidade);
            stmt.setString(2, remetente);
            stmt.execute();
            
            sql = "UPDATE users SET balanco = balanco - ? WHERE email = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, quantidade);
            stmt.setString(2, remetente);
            stmt.execute();
            
            sql = "INSERT INTO historico(sender_id,receiver_id,valor) VALUES(?,?,?)";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sender_id);
            stmt.setInt(2, receiver_id);
            stmt.setFloat(3, quantidade);
            
            stmt.execute();

            return "ok";

            } catch (SQLException e) {
              return e.getMessage();
            }
        //}
    }
    
    public static String depositar(String email,int id, float quantidade) throws SQLException {
        
        String sql = "UPDATE users SET balanco = balanco + ? WHERE email = ?";

        Connection conn = db.conectar();
         
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, quantidade);
            stmt.setString(2, email);
            
            stmt.execute();
            
            sql = "INSERT INTO historico(sender_id,receiver_id,valor) VALUES(?,?,?)";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setFloat(3, quantidade);
            
            stmt.execute();
            
            return "ok";
        } catch (SQLException e) {
          return e.getMessage();
        
     }

//    public static void historico(user usuario, db DB) throws SQLException {
//        
//        Scanner scan = new Scanner(System.in);
//        
//        Connection conn = DB.conn;
//        
//        String sql = "SELECT p.id,u1.nome as sender,u2.nome as receiver ,p.valor,p.data \n" +
//                     "FROM pagamentos p\n" +
//                     "INNER JOIN users u1 on u1.id = p.sender_id\n" +
//                     "INNER JOIN users u2 on u2.id = p.receiver_id\n" +
//                     "WHERE sender_id = ? OR receiver_id = ?";  
//          
//        PreparedStatement stmt = conn.prepareStatement(sql);  
//        stmt.setInt(1, usuario.id);  
//        stmt.setInt(2, usuario.id); 
//        ResultSet rs  = stmt.executeQuery();
//        
//        while (rs.next()) {
//            System.out.println("------------------------------");
//            System.out.println("ID= "+ rs.getInt("id"));
//            if (rs.getString("sender").equals(rs.getString("receiver"))) {
//                System.out.println("Tipo: Depósito");
//                System.out.println("Data: "+rs.getString("data"));
//                System.out.println("Valor: R$"+rs.getString("valor"));
//            } else if (rs.getString("sender").equals(usuario.nome)){
//                System.out.println("Tipo: Transferencia");
//                System.out.println("Data: "+rs.getString("data"));
//                System.out.println("Destinatário: "+rs.getString("receiver"));
//                System.out.println("Valor: -R$"+rs.getString("valor"));
//            } else {
//                System.out.println("Tipo: Transferencia");
//                System.out.println("Data: "+rs.getString("data"));
//                System.out.println("Remetente: "+rs.getString("sender"));
//                System.out.println("Valor: R$"+rs.getString("valor"));
//            }
//        }
//        System.out.println("------------------------------");
//        
//        System.out.println("Pressione enter para continuar...");
//        scan.nextLine();
//    }
//    
//    public static void solicitacoes(user usuario, db DB) throws SQLException {
//        
//        Scanner scan = new Scanner(System.in);
//        
//        int opcao;
//        boolean ordem_feita = false;
//        boolean ordem_recebida = false;
//        
//        Connection conn = DB.conn;
//        
//        String sql = "SELECT o.id,u1.id as requisitante_id,u1.nome as requisitante,u2.id as requisitado_id,u2.nome as requisitado ,o.valor,o.data,o.mensagem     " +
//                     "FROM ordens o\n" +
//                     "INNER JOIN users u1 on u1.id = o.requisitante_id\n" +
//                     "INNER JOIN users u2 on u2.id = o.requisitado_id\n" +
//                     "WHERE requisitante_id = ? or requisitado_id = ?";  
//          
//        PreparedStatement stmt = conn.prepareStatement(sql);  
//        stmt.setInt(1, usuario.id);  
//        stmt.setInt(2, usuario.id); 
//        ResultSet rs  = stmt.executeQuery();
//        
//        
//        if (!rs.next()) {
//            System.err.println("Nenhuma solicitação");
//        } else {
//            System.out.println("Solicitações feitas:");
//            while (rs.next()) {
//                System.out.println("------------------------------");
//                if (rs.getInt("requisitante_id") == usuario.id) {
//                    System.out.println("ID= "+ rs.getInt("id"));
//                    System.out.println("Destinatário "+rs.getString("requisitado"));
//                    System.out.println("Data: "+rs.getString("data"));
//                    System.out.println("Valor: R$"+rs.getString("valor"));
//                    System.out.println("Mensagem: "+rs.getString("mensagem"));
//                    
//                    ordem_feita = true;
//                }
//            }
//            System.out.println("------------------------------");
//            
//            if (ordem_feita) {
//                System.out.println("Se deseja cancelar uma solicitação digite o ID respectivo ou 0 para continuar");
//
//                opcao = scan.nextInt();
//            
//                if (opcao != 0) {
//                    DB.deleteOrdem(opcao);
//                }
//            }
//            
//            rs.beforeFirst();
//            
//            System.out.println("Solicitações recebidas:");
//            while (rs.next()) {
//                System.out.println("------------------------------");
//                if (rs.getInt("requisitado_id") == usuario.id) {
//                    System.out.println("ID= "+ rs.getInt("id"));
//                    System.out.println("Pedida por: "+rs.getString("requisitado"));
//                    System.out.println("Data: "+rs.getString("data"));
//                    System.out.println("Valor: R$"+rs.getString("valor"));
//                    System.out.println("Mensagem: "+rs.getString("mensagem"));
//                    
//                    ordem_recebida = true;
//                }
//                
//                 if (ordem_recebida) {
//                    System.out.println("Para aceitar ou recusar uma ordem digite o seu ID ou 0 para continuar");
//
//                    opcao = scan.nextInt();
//            
//                    if (opcao != 0) {
//                        DB.aceitarOrdem(opcao,DB,usuario);
//                    }
//                }
//            }
//            System.out.println("------------------------------");
//       }
//    }
}
}