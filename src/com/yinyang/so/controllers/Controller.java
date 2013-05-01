package com.yinyang.so.controllers;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Calendar;

import android.content.Context;
import android.util.Log;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.MeanOfSearch;
import com.yinyang.so.database.SearchEntity;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.MapTags;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;
import com.yinyang.so.databaseentities.User;

public class Controller {

	public Controller() {
		// TODO Auto-generated constructor stub
	}

	// test the provided database
	public ArrayList<DatabaseType> testSearch(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String table = Post.TABLE_NAME;

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(Post.KEY_TAGS,
				"multicore", MeanOfSearch.contained);
		searchCriteria.add(searchEntity1);
		ArrayList<DatabaseType> posts = mDbHelper.getDataByCriteria(table,
				searchCriteria);
		mDbHelper.close();

		return posts;
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

	public ArrayList<DatabaseType> testSearchTags(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String table = Tag.TABLE_NAME;

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(Tag.KEY_TAG, "a",
				MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);

		ArrayList<DatabaseType> tag = mDbHelper.getDataByCriteria(table,
				searchCriteria);
		mDbHelper.close();
		return tag;

	}
	
	public ArrayList<DatabaseType> testSearchMapTags(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String table = MapTags.TABLE_NAME;

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(MapTags.KEY_TAG1, "php",
				MeanOfSearch.contained);
		SearchEntity searchEntity2 = new SearchEntity(MapTags.KEY_TAG2, "mysql",
				MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);
		searchCriteria.add(searchEntity2);
		
		ArrayList<DatabaseType> tag = mDbHelper.getDataByCriteria(table,
				searchCriteria);
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
		ArrayList<String> relatedTags;

		referenceTag = "mysql";
		relatedTags = mDbHelper.getTopRelatedTags(referenceTag);
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

		if (mDbHelper.getDataByCriteria(Tag.TABLE_NAME,
				searchInTagsTableCriteria).isEmpty()) {
			HashMap<String, String> columnValuesForInsert = new HashMap<String, String>();

			columnValuesForInsert.put(Tag.KEY_TAG, "mysql");

			mDbHelper.insertSql(Tag.TABLE_NAME, columnValuesForInsert);

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
