package com.example.datanucleus.dao.dn;

import java.util.ArrayList; 
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.example.datanucleus.dao.Message;
import com.example.datanucleus.dao.MessageDao;

public class MessageDaoImpl implements MessageDao {

	private PersistenceManagerFactory pmf;
	
	public MessageDaoImpl(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessage() {
		List<Message> messages = null;
		List<Message> detached = new ArrayList<Message>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery(Message.class);
			messages = (List<Message>) q.execute();
			detached = (List<Message>) pm.detachCopyAll(messages);
			
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