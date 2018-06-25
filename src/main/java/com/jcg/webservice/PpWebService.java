package com.jcg.webservice;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
                
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));

            String nome = jason.getString("nome");
            String email = jason.getString("email");
            String cc = jason.getString("cc");
            String senha = jason.getString("senha");
		
            try {
                if (userWeb.cadastro(nome,email,cc,senha)) {
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
            
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));

            String email = jason.getString("email");
            String senha = jason.getString("senha");

            JSONObject resposta = new JSONObject();

            resposta = userWeb.login(email, senha);
            
            if (resposta.getBoolean("ativo")) {
                return Response.status(200).entity(resposta.toString()).build();
            } else {
                return Response.status(200).entity("{}").build();
            }
            
	}
        
        @POST
        @Path("/depositar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response depositar(InputStream incomingData) throws JSONException, SQLException {
            
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));
		
            int id = jason.getInt("id");
            String email = jason.getString("email");
            float quantidade = Float.parseFloat(jason.getString("quantidade"));
            
            return Response.status(200).entity(dinheiroWeb.depositar(email,id,quantidade)).build();
        }
        
        @POST
        @Path("/transferir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response transferir(InputStream incomingData) throws JSONException, SQLException {
            
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));
            
            int sender_id = jason.getInt("id");
            String remetente = jason.getString("remetente");
            String destinatario = jason.getString("destinatario");
            float quantidade = Float.parseFloat(jason.getString("quantidade"));
            
            return Response.status(200).entity(dinheiroWeb.transferir(remetente,sender_id,destinatario,quantidade)).build();
       
        }
        
        @POST
        @Path("/historico")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response historico(InputStream incomingData) throws JSONException, SQLException {            
            
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));

            int id = jason.getInt("id");
            
            JSONArray resposta = new JSONArray();
            
            resposta = dinheiroWeb.historico(id);
            
            return Response.status(200).entity(resposta.toString()).build();
                	
	}
        
        @POST
        @Path("/remover")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(InputStream incomingData) throws JSONException, SQLException {            
            
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));

            int id = jason.getInt("id");
          
            if (userWeb.remover(id)) {
                return Response.status(200).entity("ok").build();
            } else {
                return Response.status(200).entity("not ok").build();    
            }
           	
	}
        
	@POST
        @Path("/editar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editar(InputStream incomingData) throws JSONException, SQLException {            
            
            JSONObject jason = new JSONObject(dbWeb.receberJson(incomingData));

            String nome = jason.getString("nome");
            String email = jason.getString("email");
            String cc = jason.getString("cc");
            String senha = jason.getString("senha");

            JSONObject resposta = new JSONObject();

            return Response.status(200).entity(userWeb.editar(nome,email,cc,senha)).build();
            
	}
}

