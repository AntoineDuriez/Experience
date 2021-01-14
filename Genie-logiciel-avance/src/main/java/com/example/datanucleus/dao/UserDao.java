package com.example.datanucleus.dao;

import java.util.List;

public interface UserDao {
	
	/* @return this list of users*/
	public List<User> getUser();
	
	/*To search a user from your FriendList*/
	public List<User> searchFriend(String nS);
	
	/*To add a user to your FriendList*/ 
	public boolean addFriend(User u);
	
	/*To remove a user from your FriendList*/
	public boolean deleteFriend(User u);
	
	public List<User> getFriendList();
	
	public boolean isPasswordOk(String ps);
}
