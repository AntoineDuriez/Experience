package com.example.datanucleus.dao;

import java.util.List;

public interface MapDao {
	
	/* @return the of maps starting by the name given in parameters*/
	public List<Map> getMaps();
	
	/*To create a new map*/
	public boolean addMap(String mapName, double mapLatitude, double mapLongitude);
	
	/*To modify a map which already exists*/
	public boolean modifyMap(Map m, String newName);
	
	public boolean deleteMap(String mapName);
	
	/*To set access of a user map, to his FriendList, Public or Private*/
	public boolean shareMap(Map m);
	
	public Map getMyMap(String s);
}