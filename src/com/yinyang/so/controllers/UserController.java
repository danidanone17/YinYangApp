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
	 * @return users
	 */
	public ArrayList<User> getUsersOrderedByReputation(){
		dbAdapter.open();
		return dbAdapter.getUsersOrderedByReputation("");
	}
	
	/**
	 * Returns users
	 * @param name if not empty only users that have the given are returned
	 * @return users
	 */
	public ArrayList<User> getUsersOrderedByReputation(String name){
		dbAdapter.open();
		return dbAdapter.getUsersOrderedByReputation(name);
	}
}
