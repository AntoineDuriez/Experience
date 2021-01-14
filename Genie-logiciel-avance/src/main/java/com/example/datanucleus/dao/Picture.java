package com.example.datanucleus.dao;
/**
 * Picture class to manage pictures which is tie to markers and events
 * @author GLA group 2
 */
public class Picture {
	//champs
	private String location;	//URL de r�cup�ration de l'image
	//constructeur
	/**
	 * Picture constructor
	 * @param l	A string which able to locate where the picture is
	 */
	public Picture(String l) {
		this.location = l;
	}
	//guetteur
	/**
	 * Location guetteur
	 * @return	return a string which is the location of the picture
	 * @author GLA group 2
	 */
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
