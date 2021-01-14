package com.example.datanucleus.dao.stub;

import java.util.ArrayList;
import java.util.List;

import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.User;
import com.example.datanucleus.dao.UserDao;
/**
 * Fake implmentation of User Dao
 * @author GLA group 2
 */
public class UserDaoStub implements UserDao {
	/**
	 * Fake implementation of getUser
	 */
   @Override
   public List<User> getUser() {
       List<User> listUser = new ArrayList<User>();
       User u1 = new User("Boony", "1234");
       User u2 = new User("Tchoupi", "4321");
       List<Map> listMap = new ArrayList<Map>();
       Map m1 = new Map("Alderande");
       Map m2 = new Map("Endor");
       listMap.add(m1);
       listMap.add(m2);
       u2.setPersonalMapList(listMap);
       List<User> friend = new ArrayList<User>();
       friend.add(u2);
       u1.setFriendList(friend);
       listUser.add(u1);
       listUser.add(u2);
       return listUser;
   }
   /**
    * Fake implementation of getFriendList
    */
   @Override
   public List<User> getFriendList() {
       User u1 = new User("Adrien","12");
       User u2 = new User("Antoine","34");
       User u3 = new User("tit","36");
       User u4 = new User("toto","35");
       User u5 = new User("tutu","14");
       List<User> uList = new ArrayList<User>(); //fausse liste pour test
       uList.add(u1);
       uList.add(u2);
       uList.add(u3);
       uList.add(u4);
       uList.add(u5);
       return uList;
   }
   /**
    * Fake implementation of searchFriend
    */
   @Override
   public List<User> searchFriend(String n) {
       User u1 = new User("Adrien","12");
       User u2 = new User("Antoine","34");
       List<User> uList = new ArrayList<User>(); //fausse liste pour test
       uList.add(u1);
       uList.add(u2);
       return uList;
   }
	
	/**
	 * Fake implementation of addFriend
	 */
	@Override
	public boolean addFriend(User u) {
		return true;
	}
	/**
	 * Fake implementation of deleteFriend
	 */
	@Override
	public boolean deleteFriend(User u) {
		return true;
	}

	@Override
	public boolean isPasswordOk(String ps) {
		return true;
	}
	
}
