package com.example.datanucleus.dao;

import java.util.List;

public interface MarkerDao {
	
	/* @return this list of markers*/
	public List<Marker> getMarker();
	
	/*To create a new marker*/
	public boolean addMarker(String n, String d, double lon, double lat, String cat, String ds, String de);
	
	/*To modify a marker*/
	public boolean modifyMarker(Marker m);
	
	public boolean deleteMarker(String name);
	
}
