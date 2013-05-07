package com.yinyang.so.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.User;

public class UserController {
	private DatabaseAdapter dbAdapter;

	/**
	 * Communicates with the database through a DatabaseAdapter Fetches
	 * information for the question model
	 */
	public UserController(Context con) {
		dbAdapter = new DatabaseAdapter(con);
		dbAdapter.createDatabase();
	}
	
	/**
	 * Returns users
	 * @param searchName if not empty only users that have the given are returned
	 * @param limit the maximum number of users that are returned
	 * @return users
	 */
	public ArrayList<User> getUsersOrderedByReputation(int limit, String searchName){
		return getUsersOrderByReputation(limit, searchName, -1, "");
	}
	
	/**
	 * Returns users
	 * @param searchName if not empty only users that have the given are returned
	 * @param limit the maximum number of users that are returned
	 * @param lastUserReputation reputation of user who is currently last in list
	 * @param lastUserName name of user who is currently last in list
	 * @return users
	 */
	public ArrayList<User> getUsersOrderByReputation(int limit, String searchName, int lastUserReputation, String lastUserName){
		dbAdapter.open();
		return dbAdapter.getUsersOrderedByReputation(limit, searchName, lastUserReputation, lastUserName);
	}
}
