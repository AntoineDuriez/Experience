package com.example.datanucleus.dao.stub;

import java.util.ArrayList;
import java.util.List;

import com.example.datanucleus.dao.Marker;
import com.example.datanucleus.dao.MarkerDao;

public class MarkerDaoStub implements MarkerDao{

	@Override
	public List<Marker> getMarker() {
		List<Marker> listMarker = new ArrayList<Marker>();
		Marker m1 = new Marker("Hotel Boony", "Boony a la plage",48.3905283, -4.4860088);
		Marker m2 = new Marker("Universite", "la greve", 48.8566969, 2.3514616);
		listMarker.add(m1);
		listMarker.add(m2);
		return listMarker;
	}

	@Override
	public boolean addMarker() {
		return true;
	}

	@Override
	public boolean modifyMarker(Marker m) {
		return true;
	}

	@Override
	public boolean deleteMarker(Marker m) {
		return true;		
	}

}
