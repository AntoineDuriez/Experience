package com.example.datanucleus.dao.stub;

import java.util.ArrayList;
import java.util.List;

import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.MapDao;

public class MapDaoStub implements MapDao{

	@Override
	public List<Map> getMaps() {
		List<Map> listMap = new ArrayList<Map>();
		Map m1 = new Map("Aldorande");
		Map m2 = new Map("Endor");
		listMap.add(m1);
		listMap.add(m2);
		return listMap;
	}

	@Override
	public boolean addMap(String mapName) {
			return true;
	}

	@Override
	public boolean modifyMap(Map m, String newName) {
		return true;		
	}

	@Override
	public boolean deleteMap(Map m) {
		return true;		
	}

	@Override
	public boolean shareMap(Map m) {
		return true;
	}

	@Override
	public Map getMyMap(String s) {
		Map m = new Map("map de toto");
		return m;
	}

}
