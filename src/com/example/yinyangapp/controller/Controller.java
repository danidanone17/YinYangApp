package com.example.yinyangapp.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.TextView;

import com.example.yinyangapp.R;
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
	
public ArrayList<DatabaseType> testSearch(Context con){
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);        
		mDbHelper.createDatabase();      
		mDbHelper.open();

		String table = MapTags.TABLE_NAME;
		
		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();
		
		SearchEntity searchEntity1 = new SearchEntity(MapTags.KEY_TAG2,"multicore",MeanOfSearch.contained);
		
		searchCriteria.add(searchEntity1);
		
		ArrayList<DatabaseType> tags = mDbHelper.getDataByCriteria(table, searchCriteria);
		System.out.println("ResultSet: " + tags.toString());
		mDbHelper.close();
		return tags;
		
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


}
