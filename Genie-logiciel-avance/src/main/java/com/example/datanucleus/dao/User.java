package com.example.datanucleus.dao;

import java.util.ArrayList;
import java.util.List;
/**
 * User class to caracterize users of the application
 * @author GLA group 2
 */
public class User {
	//champs
	private int id;
	private String name;	//nom de l'utilisateur
	private String password; //mot de passe utilisateur !!ne doit pas apparaître dans le get!!
	private List<User> friendList;	//liste des amis de l'utilisateur, NULL si pas d'amis enregistr�s
	private List<Map> personalMapList;	//Liste des map personnelle de l'utilisateur, NULL si pas de maps enregistr�es
	
	static int idGlob = 0;
	
	//constructor
	/**
	 * User constructor
	 * @param n	A string which represents the name of the user
	 * @param u	A list of user which represent all the friend of the user
	 * @param m	A list of maps which represent all personal maps of the user
	 */
	public User(String n, String ps) {
		this.name = n;
		this.password = ps;
		this.friendList = new ArrayList<User>();
		this.personalMapList = new ArrayList<Map>();
		this.id = idGlob;
		idGlob++;
	}
	
	//guetteur
	/**
	 * Name guetteur
	 * @return	return the name of the user
	 * @author GLA group 2
	 */
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return id;
	}

	/**
	 * FriendList guetteur
	 * @return	return the friend list of the user
	 * @author GLA group 2
	 */
	public List<User> getFriendList(){
		return this.friendList;
	}
	
	/**
	 * PersonalMapList guetteur
	 * @return	return this personal map list of the user
	 * @author GLA group 2
	 */
	public List<Map> getPersonalMapList(){
		return this.personalMapList;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public void setPassword(String ps) {
		this.password = ps;
	}

	public void setFriendList(List<User> friendList) {
		this.friendList = friendList;
	}

	public void setPersonalMapList(List<Map> personalMapList) {
		this.personalMapList = personalMapList;
	}
	

}
