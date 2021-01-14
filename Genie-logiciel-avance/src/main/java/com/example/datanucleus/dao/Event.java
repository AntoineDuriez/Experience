package com.example.datanucleus.dao;

import java.util.List;

/**
 * event class to manage events on maps
 * extends from marker
 * @author GLA group 2
 */
public class Event extends Marker {
	//duration ? je ne sais aps comment l'impl�menter, DateTime ?
	//constructeur
	/**
	 * Event constructor
	 * Same parameters than marker class
	 */
	public Event(String n, String d, float lon, float lat, String cat, String ds, String de) {
		super(n, d, lon, lat, cat, ds, de);
	}
	
	
}
