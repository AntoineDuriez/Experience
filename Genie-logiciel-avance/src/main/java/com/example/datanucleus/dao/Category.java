package com.example.datanucleus.dao;

import java.util.ArrayList;
import java.util.List;
/**
 * Category class to manage and create markers and events categories on the application
 * @author GLA group 2
 */
public class Category {
	//champs
	private String name;	//nom de la categorie
	private String description;	//description rapide de la cat�gorie
	private List<Marker> markerList;	// liste des markers qui sont contenus dans la cat�gorie, concerne aussi les classes enfants (event)
	
	//constructor
	/**
	 * category constructor
	 * @param n	A string which is the name of the category
	 * @param d	A string which quickly describe the category
	 * @param m	A list of markers which represent all markers (and events) which are in the category
	 */
	public Category(String n, String d) {
		this.name = n;
		this.description = d;
		this.markerList = new ArrayList<Marker>();
	}
	
	//guetteur
	/**
	 * name guetteur
	 * @return	return a string which the name of the category
	 * @author GLA group 2
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * description guetteur
	 * @return	return a string which is a quick description of the category
	 * @author GLA group 2
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * markerList guetteur
	 * @return	return the list of the marker in the category
	 * @author GLA group 2
	 */
	public List<Marker> getMarkerList(){
		return this.markerList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMarkerList(List<Marker> markerList) {
		this.markerList = markerList;
	}
}
