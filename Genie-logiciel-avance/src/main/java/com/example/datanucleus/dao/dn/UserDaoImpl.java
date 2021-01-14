package com.example.datanucleus.dao.dn;

import java.util.ArrayList; 
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.UserDao;
import com.example.datanucleus.dao.Marker;
import com.example.datanucleus.dao.User;

public class UserDaoImpl implements UserDao {

	private PersistenceManagerFactory pmf;
	
	public UserDaoImpl(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUser() {
		List<User> users = null;
		List<User> detached = new ArrayList<User>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery(Map.class);
			users = (List<User>) q.execute();
			detached = (List<User>) pm.detachCopyAll(users);
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

	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFriendList() {
		List<User> users = null;
		List<User> detached = new ArrayList<User>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery(Map.class);
			users = (List<User>) q.execute();
			detached = (List<User>) pm.detachCopyAll(users);
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
	
	//Methode POST a implementer
	
	@Override
	public List<User> searchFriend(String nS){
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//Mettre le user dans firendlist
	public boolean addFriend(User u) {
//	public boolean addFriend(User u,String n, String ps) {
//		User m = new User(n,ps);
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx = pm.currentTransaction();
//		try {
//			tx.begin();
//			System.out.println(m.getFriendList());
//			pm.makePersistent(m);
//
//			tx.commit();
//		} finally {
//			if (tx.isActive()) {
//				tx.rollback();
//			}
//			pm.close();
//		}
		return true;
	}

	
	//Cela va supprimer le User dans le meilleur cas et pas le user de la friendList
	public boolean deleteFriend(User u) {  
//	public boolean deleteFriend(String userName) {
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx = pm.currentTransaction();
//		try {
//			tx.begin();
//			Query q = pm.newQuery(User.class);
//			q.declareParameters("String userName");
//			q.setFilter("name == userName");
//			
//			Long number = (Long)q.deletePersistentAll(userName);
//			System.out.println(number);
//			
//			tx.commit();
//		} finally {
//			if (tx.isActive()) {
//				tx.rollback();    
//			}
//			pm.close();
//		}
		return true;
	}

	//Methode POST a implementer
	
	@Override
	public boolean isPasswordOk(String ps) {
		// TODO Auto-generated method stub
		return false;
	}
}