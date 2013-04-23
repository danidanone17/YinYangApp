package com.example.yinyangapp.controller;

import java.util.ArrayList;

import android.content.Context;
import android.widget.TextView;

import com.example.yinyangapp.R;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.database.MeanOfSearch;
import com.example.yinyangapp.database.SearchEntity;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.User;

public class Controller {

	public Controller() {
		// TODO Auto-generated constructor stub
	}
	
public ArrayList<DatabaseType> testSearch(Context con){
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
		mDbHelper.createDatabase();      
		mDbHelper.open();

		String table = User.TABLE_NAME;
		
		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();
		
		SearchEntity searchEntity1 = new SearchEntity(User.KEY_AGE,"33",MeanOfSearch.exact);
		SearchEntity searchEntity2 = new SearchEntity(User.KEY_LOCATION, "Canada", MeanOfSearch.contained);
		
		searchCriteria.add(searchEntity1);
		searchCriteria.add(searchEntity2);
		
		ArrayList<DatabaseType> users = mDbHelper.getDataByCriteria(table, searchCriteria);
		mDbHelper.close();
		return users;
		
	}


}
