package com.yinyang.so.controllers;

import java.util.ArrayList;
import java.util.Set;

import java.util.HashMap;


import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.KeyValuePair;
import com.yinyang.so.database.MeanOfSearch;
import com.yinyang.so.database.SearchEntity;
import com.yinyang.so.database.TableType;
import com.yinyang.so.databaseentities.MapTags;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;
import com.yinyang.so.databaseentities.User;

public class Controller {

	public Controller() {
		// TODO Auto-generated constructor stub
	}

	// test the provided database
	public ArrayList<Post> testSearch(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(Post.KEY_TAGS,
				"multicore", MeanOfSearch.contained);
		searchCriteria.add(searchEntity1);
		ArrayList<Post> posts = mDbHelper.getPostsByCriteria(searchCriteria);
		mDbHelper.close();

		return posts;
	}

	public ArrayList<User> testSearchUser(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(User.KEY_AGE, "33",
				MeanOfSearch.exact);
		SearchEntity searchEntity2 = new SearchEntity(User.KEY_LOCATION,
				"Canada", MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);

		searchCriteria.add(searchEntity2);

		ArrayList<User> users = mDbHelper.getUsersByCriteria(searchCriteria);

		mDbHelper.close();

		return users;

	}

	public ArrayList<Tag> testSearchTags(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(Tag.KEY_TAG, "java",
				MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);

		ArrayList<Tag> tag = mDbHelper.getTagsByCriteria(searchCriteria);
		mDbHelper.close();
		return tag;

	}
	
	public ArrayList<MapTags> testSearchMapTags(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(MapTags.KEY_TAG1, "php",
				MeanOfSearch.contained);
		SearchEntity searchEntity2 = new SearchEntity(MapTags.KEY_TAG2, "mysql",
				MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);
		searchCriteria.add(searchEntity2);
		
		ArrayList<MapTags> tag = mDbHelper.getMappingTagsByCriteria(searchCriteria);
		mDbHelper.close();
		return tag;

	}

	public void testInsert(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		HashMap<String, String> columnValuesForInsert = new HashMap<String, String>();

		columnValuesForInsert.put(MapTags.KEY_TAG1, "mysql");
		columnValuesForInsert.put(MapTags.KEY_TAG2, "php");

		mDbHelper.insertSql(MapTags.TABLE_NAME, columnValuesForInsert);

		mDbHelper.close();
	}

	public void testUpdate(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		HashMap<String, String> columnValuesForUpdate = new HashMap<String, String>();

		int postTableLastId = mDbHelper.getLastIndex(TableType.mapping_tags);

		MapTags tagCombination = mDbHelper.getMapTags(postTableLastId);

		SearchEntity searchEntity1 = new SearchEntity(Post.KEY_TAGS,
				tagCombination.getTag1(), MeanOfSearch.contained);
		SearchEntity searchEntity2 = new SearchEntity(Post.KEY_TAGS,
				tagCombination.getTag2(), MeanOfSearch.contained);

		ArrayList<SearchEntity> searchInMappingTableCriteria1 = new ArrayList<SearchEntity>();

		searchInMappingTableCriteria1.add(searchEntity1);
		searchInMappingTableCriteria1.add(searchEntity2);

		int countAppearance = mDbHelper.getCountByCriteria(TableType.posts,
				searchInMappingTableCriteria1);

		columnValuesForUpdate.put(MapTags.KEY_COUNT_APPEARANCE, ""
				+ countAppearance);

		System.out.println("TAGS: " + tagCombination.getTag1() + ", "
				+ tagCombination.getTag2() + ", COUNT: " + countAppearance);

		mDbHelper.updateSql(MapTags.TABLE_NAME, columnValuesForUpdate, "ID = "
				+ tagCombination.getId());

	}

	public ArrayList<String> testGetTopRelatedTags(Context con) {
		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String referenceTag;
		ArrayList<String> relatedTags = new ArrayList<String>();

		referenceTag = "mysql";
		for (KeyValuePair kvp : mDbHelper.getTopRelatedTags(referenceTag)) {
			relatedTags.add(kvp.getKey());
		}
		return relatedTags;
	}

	public ArrayList<Post> testGetPostsByTags(Context con) {
		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		ArrayList<String> referenceTags = new ArrayList<String>();
		ArrayList<Post> posts;

		referenceTags.add("php");
		referenceTags.add("mysql");
		posts = mDbHelper.getPostsByTags(referenceTags);

		return posts;
	}

	public void testInsertTags(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		ArrayList<SearchEntity> searchInTagsTableCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(Tag.KEY_TAG, "mysql",
				MeanOfSearch.exact);

		searchInTagsTableCriteria.add(searchEntity1);

		if (mDbHelper.getTagsByCriteria(searchInTagsTableCriteria).isEmpty()) {
			HashMap<String, String> columnValuesForInsert = new HashMap<String, String>();

			columnValuesForInsert.put(Tag.KEY_TAG, "mysql");

			mDbHelper.insertSql(Tag.TABLE_NAME, columnValuesForInsert);

		}
	}

}