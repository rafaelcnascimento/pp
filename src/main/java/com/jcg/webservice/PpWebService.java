package com.jcg.webservice;

import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.*;
import org.json.JSONException;


@Path("/web")
public class PpWebService {

	@GET
	@Path("/teste")
	public Response getMsg(@PathParam("name") String name) {
		String output = "<html> " + "<title>" + "Java WebService Example" + "</title>"  + "<body><h1><div style='font-size: larger;'>"
				+ "Hello <span style='text-transform: capitalize; color: green;'>" + name + "</span></div></h1></body>" + "</html>";
		return Response.status(200).entity(output).build();
	}
	
	
	@POST
        @Path("/cadastro")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastro(InputStream incomingData) throws JSONException, SQLException {
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
		
		JSONObject jason = new JSONObject(crunchifyBuilder.toString());
		
		String nome = jason.getString("nome");
		String email = jason.getString("email");
		String cc = jason.getString("cc");
		String senha = jason.getString("senha");
		
		
            try {
                if (user.cadastro(nome,email,cc,senha)) {
                    return Response.status(200).entity("Cadastro efetuado com sucesso").build();
                } else {
                    return Response.status(200).entity("Erro no cadastro").build();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PpWebService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(PpWebService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PpWebService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
			
	}
        
        @POST
        @Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(InputStream incomingData) throws JSONException, SQLException {            
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
                JSONObject jason = new JSONObject(crunchifyBuilder.toString());
		
    
                String email = jason.getString("email");
                String senha = jason.getString("senha");
                
             
                JSONObject resposta = new JSONObject();
                    
                resposta = user.login(email,senha);
                    
       
                    return Response.status(200).entity(resposta.toString()).build();
                
                			
	}
        
        @POST
        @Path("/depositar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response depositar(InputStream incomingData) throws JSONException, SQLException {
            
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
                JSONObject jason = new JSONObject(crunchifyBuilder.toString());
		
    
                String email = jason.getString("email");
                float quantidade = BigDecimal.valueOf(jason.getDouble("quantidade")).floatValue();
                
                String Resultado = dinheiro.depositar(email,quantidade);
                
                if (Resultado.equals("ok")) {
                    return Response.status(200).entity("ok").build();
            } else {
                    return Response.status(200).entity(Resultado).build();
            }
                    
         
           
            
        }
}
	


