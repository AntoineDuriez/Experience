package com.example.jetty_jersey.ws.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.User;
import com.example.datanucleus.dao.stub.UserDaoStub;
/**
 * Real implementation of UserRessource with Dao stub calls
 * @author GLA group 2
 */
@Path("/userimpl")
public class UserRessourceImplStub {
	/**
	 * getUser implementation with dao stub
	 * @return return a list of User
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getuser")
	public List<User> getUser() {
		UserDaoStub uds = new UserDaoStub(); //appel à l'implémentation bouchon
		return uds.getUser();
	}
	/**
	 * searchFriend implementation with dao stub
	 * @param n String which is the name of the person the user wants to look for
	 * @return return a list of User
	 *//**
	 * getUser implementation with dao stub
	 * @return return a list of User
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getfriendlist")
	public List<User> getFriendList() {
		UserDaoStub uds = new UserDaoStub(); //appel à l'implémentation bouchon
		return uds.getFriendList();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchfriend")
	public List<User> searchFriend(String n) {
		UserDaoStub uds = new UserDaoStub(); //appel à l'implémentation bouchon
		return uds.searchFriend(n);
	}
	/**
	 * addFriend implementation with dao stub
	 * @param n	A User which will be add as a friend in the friend list
	 * @return	return true if the user is added to the friend list, else return false
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addfriend")
	public boolean addFriend(User n) {
		UserDaoStub uds = new UserDaoStub(); //appel à l'implémentation bouchon
		return uds.addFriend(n);
	}
	/**
	 * deleteFriend implementation with dao stub
	 * @param u	A user in the friend list who will be delete from it
	 * @return return true if the user is deleted from the friend list, else return false
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deletefriend")
	public boolean deleteFriend(User u) {
		UserDaoStub uds = new UserDaoStub(); //appel à l'implémentation bouchon
		return uds.deleteFriend(u);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ispasswordok")
	public boolean isPasswordOk(String ps) {
		UserDaoStub uds = new UserDaoStub(); //appel à l'implémentation bouchon
		return uds.isPasswordOk(ps);
	}
}
