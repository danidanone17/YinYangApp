package com.yinyang.so.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.Post;

public class SearchController {
	private DatabaseAdapter dbAdapter;

	/**
	 * Communicates with the database through a DatabaseAdapter Fetches
	 * information for the question model
	 */
	public SearchController(Context con) {
		dbAdapter = new DatabaseAdapter(con);
		dbAdapter.createDatabase();
	}
	
	/**
	 * Parses tags selected as search criteria with search free text
	 * @param tags the tags selected as search criteria
	 * @param freeText the search free text
	 * @return parsed tags selected as search criteria with search free text
	 */
	public String parseTagSearchString(ArrayList<String> tags, String freeText){ 
		String searchString = "";
		
		// add tags selected as search criteria to beginning of parsed string surrounded by []
		for(String tag : tags){
			searchString += "[" + tag + "] ";
		}
		
		// add search free text to parsed string
		searchString += freeText;
		return searchString;
	}
	
	/**
	 * Searches for posts where the title or body contains the given free text
	 * @param sFreeText free text to search for in posts
	 * @return an array list of posts
	 */
	public ArrayList<Post> freeTextSearch(String sFreeText)
	{
		dbAdapter.open();
		return dbAdapter.getQuestionsByFreeText(sFreeText.split(" "));
	}
	
}
