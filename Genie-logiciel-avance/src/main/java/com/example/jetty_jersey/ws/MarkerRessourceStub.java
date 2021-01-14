package com.example.jetty_jersey.ws;

import java.util.ArrayList; 
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.datanucleus.dao.*;
//JAVADOC
@Path("/marker")
public class MarkerRessourceStub {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmarker")
	public List<Marker> getMarker() {
		List<Marker> listMarker = new ArrayList<Marker>();
		Marker m1 = new Marker("Hotel Boony", "Boony a la plage", 90, 90);
		Marker m2 = new Marker("Universite", "la greve", 10, 30);
		listMarker.add(m1);
		listMarker.add(m2);
		return listMarker;
	}

	@PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/addmarker")
	public Response addMarker(Marker m) {
		Marker n = new Marker("marker", "marker", 89, 45);
		return Response.ok().build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/modifymarker")
	public Response modifyMarker(Marker m) {
		return Response.ok().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deletemarker")
	public Response deleteMarker(Marker m) {
		return Response.ok().build();
	}

}
