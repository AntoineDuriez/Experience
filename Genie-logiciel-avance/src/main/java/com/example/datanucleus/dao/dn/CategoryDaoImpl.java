package com.example.datanucleus.dao.dn;

import java.util.ArrayList; 
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;


import com.example.datanucleus.dao.Category;
import com.example.datanucleus.dao.CategoryDao;
import com.example.datanucleus.dao.Map;

public class CategoryDaoImpl implements CategoryDao {

private PersistenceManagerFactory pmf;

public CategoryDaoImpl(PersistenceManagerFactory pmf) {
	this.pmf = pmf;
}


@SuppressWarnings("unchecked")
@Override
public List<Category> getCategory() { 
	List<Category> categories = null;
	List<Category> detached = new ArrayList<Category>();
	PersistenceManager pm = pmf.getPersistenceManager();
	Transaction tx = pm.currentTransaction();
	try {
		tx.begin();
		Query q = pm.newQuery(Category.class);
		categories = (List<Category>) q.execute();
		detached = (List<Category>) pm.detachCopyAll(categories);
		
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