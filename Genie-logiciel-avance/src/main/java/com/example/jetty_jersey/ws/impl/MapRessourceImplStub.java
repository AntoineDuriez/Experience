package com.example.jetty_jersey.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.MapDao;
import com.example.datanucleus.dao.dn.MapDaoImpl;

//import com.example.datanucleus.dao.stub.MapDaoStub;

/**
 * Real implementation of MapRessource with Dao stub calls
 * @author GLA group 2
 */
@Path("/mapimpl")
public class MapRessourceImplStub {
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Pour l'implémentation bdd finale, comme dans l'exemple MapDaoImplTest.java
	/*PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Example");
	MapDao mdi = new MapDaoImpl(pmf);*/
	public MapDao setPropertiesDataBase() {
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
		MapDao mdi = new MapDaoImpl(pmf);
		return (mdi);
	}
	
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * getMaps implementation with dao stub
	 * @return return a list of Maps
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmap")
	public List<Map> getMaps() {		
		//MapDaoStub mds = new MapDaoStub(); //appel à l'implémentation bouchon
		/*Map m = new Map("test");
		Map n = new Map("test");
		List<Map> l = new ArrayList<Map>();
		l.add(n);
		l.add(m);*/
		MapDao mdi = this.setPropertiesDataBase();
		return mdi.getMaps();
	}
	/**
	 * addMap implementation with dao stub
	 * @return return true if a new map is added to the map list, else return false
	 */
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//Essai de addMap pour l'implémentation de la bdd
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addmap/{name}/{latitude}/{longitude}")
	public boolean addMap(@PathParam("name")String name,
			@PathParam("latitude")double latitude,
			@PathParam("longitude")double longitude) {
		MapDao mdi = this.setPropertiesDataBase();
		return mdi.addMap(name, latitude, longitude);
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * modifyMap implementation with dao stub
	 * @param m A map which will be modify
	 * @return return true if the map has been modified, else return false
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/modifymap")
	public boolean modifyMap(Map m) {
		//MapDaoStub mds = new MapDaoStub(); //appel à l'implémentation bouchon
		return true;
	}
	
	/**
	 * deleteMap implementation with dao stub
	 * @param m A map which will be delete
	 * @return return true if the map has been deleted, else return false
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deletemap/{name}")
	public boolean deleteMap(@PathParam("name")String name) {
		//MapDaoStub mds = new MapDaoStub(); //appel à l'implémentation bouchon
		MapDao mdi = this.setPropertiesDataBase();
		return mdi.deleteMap(name);
	}
	/**
	 * shareMap implementation with dao stub
	 * @param m A map which will be share with user's friend
	 * @return return true if the map has been shared, else return false
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sharemap")
	public boolean shareMap(Map m) {
		//MapDaoStub mds = new MapDaoStub(); //appel à l'implémentation bouchon
		return true;
	}
	/**
	 * getMyMap implementation with dao stub
	 * @param s A string which is the name of a map 
	 * @return return the map to get information about it
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmymap")
	public Map getMyMap(String s) {
		//MapDaoStub mds = new MapDaoStub(); //appel à l'implémentation bouchon
		Map m = new Map("test", 0, 3.0212);
		return m;
	}
}
