package com.example.datanucleus.dao.dn;

import java.util.ArrayList; 
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.example.datanucleus.dao.Picture;
import com.example.datanucleus.dao.PictureDao;


public class PictureDaoImpl implements PictureDao {

	private PersistenceManagerFactory pmf;
	
	public PictureDaoImpl(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Picture> getPicture() {
		List<Picture> pictures = null;
		List<Picture> detached = new ArrayList<Picture>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery(Picture.class);
			pictures = (List<Picture>) q.execute();
			detached = (List<Picture>) pm.detachCopyAll(pictures);
			
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();    
			}
			pm.close();
		}
		System.out.println(detached.toString());
		return detached;
	}
}