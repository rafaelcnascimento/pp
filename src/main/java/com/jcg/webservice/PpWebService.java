package com.jcg.webservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

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
    @Path("/jason")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response testeJ(InputStream incomingData) {
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
		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
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
		
		
		if (user.cadastro(nome,email,cc,senha)) {
			return Response.status(200).entity(crunchifyBuilder.toString()).build();
		} else {
			return Response.status(500).build();
		}
		
		
	}
}
	


