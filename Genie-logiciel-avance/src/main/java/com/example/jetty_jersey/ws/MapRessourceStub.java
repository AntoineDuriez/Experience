package com.example.jetty_jersey.ws;

import java.util.ArrayList;  
import java.util.List;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.datanucleus.dao.*;

import com.example.jetty_jersey.auth.*;
//Bouchon webservice Map
@Path("/map")
public class MapRessourceStub {
	
	@RolesAllowed("admin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmap")
	public List<Map> getMaps() {
		List<Map> listMap = new ArrayList<Map>();
		Map m1 = new Map("Hosnian Prime");
		Map m2 = new Map("Endor");
		Marker n1 = new Marker("Boony", "Boony au Brésil", 36, 45);
		Marker n2 = new Marker("Pépito", "Pépito à la plage", 2, 13);
		List<Marker> markList = new ArrayList<Marker>();
		markList.add(n1);
		markList.add(n2);
		m1.setMarkerList(markList);
		listMap.add(m1);
		listMap.add(m2);
		return listMap;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addmap")
	public Response addMap(Map m) {
		return Response.ok().build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/modifymap")
	public Response modifyMap(Map m) {
		return Response.ok().build();
	}
 	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deletemap")
	public Response deleteMap(Map m) {
		return Response.ok().build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sharemap")
	public Response shareMap(Map m) {
		return Response.ok().build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmymap")
	public Map getMyMap(String n) {
		Map m = new Map("Boony map");
		return m;
	}
}
