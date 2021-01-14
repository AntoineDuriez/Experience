package com.example.jetty_jersey.ws.impl;

import java.util.List;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.Marker;
import com.example.datanucleus.dao.MarkerDao;
import com.example.datanucleus.dao.dn.MarkerDaoImpl;
import com.example.datanucleus.dao.stub.MarkerDaoStub;
/**
 * Real implementation of MarkerRessource with Dao stub calls
 * @author GLA group 2
 */
//maj 17/03 : Mise en commentaire du code pour réglage problème no injection.. sur MarkerRessourceStub
@Path("/markerimpl")
public class MarkerRessourceImplStub {
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
		public MarkerDao setPropertiesDataBase() {
			Properties properties = new Properties();
			properties.setProperty("javax.jdo.PersistenceManagerFactoryClass","");
			properties.setProperty("javax.jdo.option.ConnectionDriverName","com.mysql.jdbc.Driver");
			properties.setProperty("javax.jdo.option.ConnectionURL","jdbc:mysql://127.0.0.1/example-gla?useServerPrepStmts=false&amp;useSSL=false");
			properties.setProperty("javax.jdo.option.ConnectionUserName","root");
			properties.setProperty("javax.jdo.option.ConnectionPassword","");
			properties.setProperty("javax.jdo.option.Mapping","mysql");
			properties.setProperty("datanucleus.schema.autoCreateAll","true");
			properties.setProperty("datanucleus.schema.validateAll","false");
			properties.setProperty("datanucleus.metadata.defaultInheritanceStrategy", "TABLE_PER_CLASS");
			properties.setProperty("datanucleus.maxFetchDepth","10");
			properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(properties);
			MarkerDao mdi = new MarkerDaoImpl(pmf);
			return (mdi);
		}
		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * getMarker implementation with dao stub
	 * @return return a list of User
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmarker")
	public List<Marker> getMarker() {
		MarkerDao mds = this.setPropertiesDataBase(); 
		return mds.getMarker();
	}
	
	/**
	 * addMarker implementation with dao stub
	 * @return return true if the marker is added, else return false
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addmarker/{name}/{descrpt}/{latitude}/{longitude}/{category}/{dateStart}/{dateEnd}")
	public boolean addMarker(@PathParam("name")String name,
			@PathParam("descrpt")String descrpt,
			@PathParam("latitude")double latitude,
			@PathParam("longitude")double longitude,
			@PathParam("category")String category,
			@PathParam("dateStart")String dateStart,
			@PathParam("dateEnd")String dateEnd) {
		MarkerDao mds = this.setPropertiesDataBase();
		return mds.addMarker(name, descrpt, longitude, latitude, category, dateStart, dateEnd);
	}
	/**
	 * modifyMarker implementation with dao stub
	 * @param m A marker in the marker list of the user who will have to be modify
	 * @return return true if the marker is modified, else return false
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/modifymarker")
	public boolean modifyMarker(Marker m) {
		MarkerDaoStub mds = new MarkerDaoStub();  
		return mds.modifyMarker(m);
	}
	/**
	 * deleteMarker implementation with dao stub
	 * @param m A marker in the marker list of the user who will be delete from it
	 * @return return true if the marker is deleted from the marker list, else return false
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deletemarker/{name}")
	public boolean deleteMarker(@PathParam("name")String m) {
		MarkerDao mds = this.setPropertiesDataBase();
		return mds.deleteMarker(m);
	}
}
