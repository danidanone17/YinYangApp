package com.yinyang.so.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
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
	 * Searches for posts where the title or body contains the given free text
	 * @param sFreeText free text to search for in posts
	 * @param eSearchResultSortingAlgorithm chosen search result algorithm
	 * @return an array list of posts
	 */
	public ArrayList<Post> freeTextSearch(String sFreeText, SearchResultSortingAlgorithm eSearchResultSortingAlgorithm)
	{
		dbAdapter.open();
		return dbAdapter.getQuestionsByFreeText(sFreeText.split(" "), eSearchResultSortingAlgorithm);
	}

	public ArrayList<String> getNextAndPreviousTags(String searchTag){
		dbAdapter.open();
		return dbAdapter.getNextAndPreviousTags(searchTag);
	}
	
	/**
	 * Returns tag that's name match the given string
	 * @param sName string the tag name should match
	 * @return tag that's name match the given string
	 */
	public String getTagByName(String sName)
	{
		dbAdapter.open();
		return dbAdapter.getTagByName(sName);
	}	
	
	/**
	 * Returns the four top related tags
	 * @param sName tag the returned tags are top related to
	 * @return the four top related tags
	 */
	public ArrayList<String> getTopRelatedTags(String sName){
		dbAdapter.open();
		return dbAdapter.getTopRelatedTags(sName);
	}
	
	/**
	 * Returns three tags in alphabetical order
	 * @param iLimit limits number of returned tags
	 * @return three tags in alphabetical order
	 */
	public ArrayList<String> getThreeTagsInAlphabeticalOrder(){
		dbAdapter.open();
		return dbAdapter.getTagsInAlphabeticalOrder(3);	
	}
	
	/**
	 * Get questions where 
	 * - the given words are contained in either the title or the body and
	 * - there is a relation to all of the provided tags	
	 * @param oWords the words that have to be contained in a question's title or body to be returned by this method
	 * @param oTags tags the returned questions should be related to
	 * @param eSearchResultSortingAlgorithm chosen search result algorithm
	 * @return questions
	 */
	public ArrayList<Post> freeTextAndTagSearch(String sFreeText, ArrayList<String> oTags, SearchResultSortingAlgorithm eSearchResultSortingAlgorithm){
		dbAdapter.open();
		
		if(!"".equals(sFreeText)){
			return dbAdapter.getQuestionsByFreeTextAndTags(sFreeText.split(" "), oTags, eSearchResultSortingAlgorithm);
		}
		else{
			return dbAdapter.getPostsByTags(oTags);
		}			
	}
}
