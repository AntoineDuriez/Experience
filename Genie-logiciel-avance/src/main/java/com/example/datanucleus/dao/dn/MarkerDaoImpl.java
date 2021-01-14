package com.example.datanucleus.dao.dn;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.MapDao;
import com.example.datanucleus.dao.Marker;
import com.example.datanucleus.dao.MarkerDao;

public class MarkerDaoImpl implements MarkerDao {
	
private PersistenceManagerFactory pmf;
	
	public MarkerDaoImpl(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> getMarker() {
		List<Marker> markers = null;
		List<Marker> detached = new ArrayList<Marker>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery(Marker.class);
			/*q.declareParameters("String mapName");
			q.setFilter("name.startsWith(mapName)");*/

			markers = (List<Marker>) q.execute();
			detached = (List<Marker>) pm.detachCopyAll(markers);
			
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();    
			}
			pm.close();
		}
		return detached;
	}

	@Override
	public boolean addMarker(String n, String d, double lon, double lat, String cat, String ds, String de) {
		Marker mark = new Marker(n,d,lon,lat,cat,ds,de);
		List<Marker> list = new ArrayList<Marker>();
		list.add(mark);
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(mark);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return true;
		
	}

	@Override
	public boolean modifyMarker(Marker m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMarker(String markerName) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery(Marker.class);
			q.declareParameters("String markerName");
			q.setFilter("name == markerName");
			
			Long number = (Long)q.deletePersistentAll(markerName);
			System.out.println(number);
			
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();    
			}
			pm.close();
		}
		return true;
	}
}
