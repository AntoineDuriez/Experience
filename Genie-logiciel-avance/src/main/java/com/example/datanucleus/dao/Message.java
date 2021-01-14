package com.example.datanucleus.dao;
/**
 * Message class to manage messages on markers and events
 * @author GLA group 2
 */
public class Message {
	//champs
	private String description;	//intitul� du message/commentaire
	private String author;	//auteur du message (nom de l'utilisateur qui l'a post�)
	//constructeur
	/**
	 * Message constructor
	 * @param d	A string which is the message
	 * @param a	A string which represent the author of the message
	 */
	public Message(String d, String a) {
		this.description = d;
		this.author = a;
	}
	
	//guetteur
	/**
	 * Description guetteur
	 * @return	return a string which is the description of the message
	 * @author GLA group 2
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * Author guetteur
	 * @return	return a string which is the name of the author of the message
	 * @author GLA group 2
	 */
	public String getAuthor() {
		return this.author;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
