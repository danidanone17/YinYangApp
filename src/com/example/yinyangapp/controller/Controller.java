package com.example.yinyangapp.controller;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Calendar;


import android.content.Context;

import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.database.MeanOfSearch;
import com.example.yinyangapp.database.SearchEntity;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.MapTags;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.Tag;
import com.example.yinyangapp.databaseentities.User;

public class Controller {

	public Controller() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<DatabaseType> testSearch(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String table = MapTags.TABLE_NAME;
		
		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(MapTags.KEY_TAG2,"multicore",MeanOfSearch.contained);
		
		searchCriteria.add(searchEntity1);
		
		ArrayList<DatabaseType> tags = mDbHelper.getDataByCriteria(table, searchCriteria);
		System.out.println("ResultSet: " + tags.toString());


		ArrayList<DatabaseType> users = mDbHelper.getDataByCriteria(table,
				searchCriteria);

		mDbHelper.close();

		return tags;
	}
	
	public ArrayList<DatabaseType> testSearchUser(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String table = User.TABLE_NAME;
		
		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(User.KEY_AGE, "33",
				MeanOfSearch.exact);
		SearchEntity searchEntity2 = new SearchEntity(User.KEY_LOCATION,
				"Canada", MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);

		searchCriteria.add(searchEntity2);

		ArrayList<DatabaseType> users = mDbHelper.getDataByCriteria(table,
				searchCriteria);

		mDbHelper.close();

		return users;


	}


public ArrayList<DatabaseType> testSearchTags(Context con){
	
	DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
	mDbHelper.createDatabase();      
	mDbHelper.open();

	String table = Tag.TABLE_NAME;
	
	ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();
	
	SearchEntity searchEntity1 = new SearchEntity(Tag.KEY_TAG,"php",MeanOfSearch.exact);
	
	searchCriteria.add(searchEntity1);
	
	ArrayList<DatabaseType> tag = mDbHelper.getDataByCriteria(table, searchCriteria);
	mDbHelper.close();
	return tag;
	
}

public void testInsert(Context con){
	
	DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
	mDbHelper.createDatabase();      
	mDbHelper.open();
	
	HashMap<String, String>columnValuesForInsert = new HashMap<String, String>();

	columnValuesForInsert.put(MapTags.KEY_TAG1,
			"mysql");
	columnValuesForInsert.put(MapTags.KEY_TAG2,
			"php");

	mDbHelper.insertSql(MapTags.TABLE_NAME,
			columnValuesForInsert);
	
	mDbHelper.close();
}

public void testUpdate(Context con){
	
	DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
	mDbHelper.createDatabase();      
	mDbHelper.open();
	
	HashMap<String, String>columnValuesForUpdate = new HashMap<String, String>();
	
	int postTableLastId = mDbHelper.getLastIndex(MapTags.TABLE_NAME);

	MapTags tagCombination = mDbHelper.getMapTags(postTableLastId);
	
	SearchEntity searchEntity1 = new SearchEntity(Post.KEY_TAGS,
				tagCombination.getTag1(), MeanOfSearch.contained);
	SearchEntity searchEntity2 = new SearchEntity(Post.KEY_TAGS,
				tagCombination.getTag2(), MeanOfSearch.contained);

	ArrayList<SearchEntity> searchInMappingTableCriteria1 = new ArrayList<SearchEntity>();
	
	searchInMappingTableCriteria1.add(searchEntity1);
	searchInMappingTableCriteria1.add(searchEntity2);

	int countAppearance = mDbHelper.getCountByCriteria(Post.TABLE_NAME,
				searchInMappingTableCriteria1);

	columnValuesForUpdate.put(MapTags.KEY_COUNT_APPEARANCE, ""
				+ countAppearance);
	
	System.out.println("TAGS: " + tagCombination.getTag1() + ", " + tagCombination.getTag2() + ", COUNT: " + countAppearance);

	mDbHelper.updateSql(MapTags.TABLE_NAME, columnValuesForUpdate,
				"ID = " + tagCombination.getId());
	
}

public ArrayList<Tag> testGetTopRelatedTags (Context con){
	DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
	mDbHelper.createDatabase();      
	mDbHelper.open();
	
	String referenceTag;
	ArrayList<Tag> relatedTags;
	
	referenceTag = "php";
	relatedTags = mDbHelper.getTopRelatedTags(referenceTag);
	
	return relatedTags;
}

public void testInsertTags(Context con){
	
	DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
	mDbHelper.createDatabase();      
	mDbHelper.open();
	
	ArrayList<SearchEntity>searchInTagsTableCriteria = new ArrayList<SearchEntity>();

	SearchEntity searchEntity1 = new SearchEntity(Tag.KEY_TAG, "mysql",
			MeanOfSearch.exact);

	searchInTagsTableCriteria.add(searchEntity1);

	if (mDbHelper.getDataByCriteria(Tag.TABLE_NAME, searchInTagsTableCriteria).isEmpty()) {
		HashMap<String, String> columnValuesForInsert = new HashMap<String, String>();

		columnValuesForInsert.put(Tag.KEY_TAG, "mysql");

		mDbHelper.insertSql(Tag.TABLE_NAME,
				columnValuesForInsert);

	}
}


	public String getDateDifference(String startDate) {
		Calendar current = Calendar.getInstance();

		int year = current.get(Calendar.YEAR)
				- Integer.parseInt(startDate.split("-")[0]);
		int month = current.get(Calendar.MONTH)
				- Integer.parseInt(startDate.split("-")[1]);
		int day = current.get(Calendar.DAY_OF_MONTH)
				- Integer.parseInt(startDate.split("-")[2]);
		if (year > 1) {
			if (month == 0) {
				return year + " years";
			}
			if (month > 1) {
				return year + " years, " + month + " months";
			}
			if (month == 1) {
				return year + " years, " + month + " month";
			}
			if (month < 0) {
				year--;
				month = month + 12;
				if (year == 1) {
					if (month > 1) {
						return year + " year, " + month + " months";
					} else {
						return year + " year, " + month + " month";
					}
				}
				if (year > 1) {
					if (month > 1) {
						return year + " years, " + month + " months";
					} else {
						return year + " years, " + month + " month";
					}
				}

			}
		}
		if (year == 1) {
			if (month == 0) {
				return year + " year";
			}
			if (month > 1) {
				return year + " year, " + month + " months";
			}
			if (month == 1) {
				return year + " year, " + month + " month";
			}
			if (month < 0) {
				month = month + 12;
				if (month > 1) {
					return month + " months";
				} else {
					return month + " month";
				}
			}
		}
		if (year == 0) {
			if (month > 1) {
				return month + " months";
			} else {
				if (day > 1) {
					return day + "days";
				}
				if (day == 0) {
					return "today";
				} else {
					return day + "day";
				}
			}
		}
		return null;
	}


}
