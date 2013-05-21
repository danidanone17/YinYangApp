package com.yinyang.so.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
import com.yinyang.so.databaseentities.Post;

public class SearchResultController {
	private DatabaseAdapter dbAdapter;
	
	/**
	 * Communicates with the database through a DatabaseAdapter Fetches
	 * information for the question model
	 */
	public SearchResultController(Context con) {
		dbAdapter = new DatabaseAdapter(con);
		dbAdapter.createDatabase();
	}
	
	/**
	 * Get questions where - the given words are contained in either the title
	 * or the body and - there is a relation to all of the provided tags
	 * Currently ordered by descending order and limited the results to 100
	 * posts only
	 * 
	 * @param sFreeText
	 *            free text to search for in posts
	 * @param oTags
	 *            tags the returned questions should be related to
	 * @param eSearchResultSortingAlgorithm
	 *            chosen search result algorithm
	 * @param displayedPosts 
	 * 			  the posts currently displayed, null if none
	 * @param nextOrPrev 
	 * 			  0 for next and 1 for previous, -1 if none
	 * @return questions
	 * 
	 */
	public ArrayList<Post> getQuestionsByFreeTextAndTagsWithLimits(
			String sFreeText, ArrayList<String> oTags,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm,
			ArrayList<Post> displayedPosts, int nextOrPrev) {
		dbAdapter.open();
		return dbAdapter.getQuestionsByFreeTextAndTagsWithLimits(sFreeText.split(" "), oTags,
				eSearchResultSortingAlgorithm, displayedPosts, nextOrPrev);
	}
}
