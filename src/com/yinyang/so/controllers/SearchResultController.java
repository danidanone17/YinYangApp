package com.yinyang.so.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
import com.yinyang.so.databaseentities.Post;

public class SearchResultController {
	private DatabaseAdapter dbAdapter;

	/**
	 * Red
	 */
	private int threshold1 = 0;

	/**
	 * Light red
	 */
	private int threshold2 = 0;

	/**
	 * Yellow
	 */
	private int threshold3 = 0;

	/**
	 * Light green
	 */
	private int threshold4 = 0;

	/**
	 * Green
	 */
	private int threshold5 = 0;

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
	 *            the posts currently displayed, null if none
	 * @param nextOrPrev
	 *            0 for next and 1 for previous, -1 if none
	 * @return questions
	 * 
	 */
	public ArrayList<Post> getQuestionsByFreeTextAndTagsWithLimits(
			String sFreeText, ArrayList<String> oTags,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm,
			ArrayList<Post> displayedPosts, int nextOrPrev) {
		dbAdapter.open();
		return dbAdapter.getQuestionsByFreeTextAndTagsWithLimits(
				sFreeText.split(" "), oTags, eSearchResultSortingAlgorithm,
				displayedPosts, nextOrPrev);
	}

	/**
	 * Initializes thresholds
	 * 
	 * @param textSearch
	 *            text that has been search for
	 * @param selectedTags
	 *            tags that the posts should be related to
	 */
	public void intitializeThresholds(String textSearch,
			ArrayList<String> selectedTags) {
		// initialize thresholds
		threshold5 = getMaxPostScoreForFreeTextAndTagSearch(textSearch,
				selectedTags);
		threshold4 = (threshold5 / 5) * 4;
		threshold3 = (threshold5 / 5) * 3;
		threshold2 = (threshold5 / 5) * 2;
		threshold1 = threshold5 / 5;
	}

	/**
	 * Augment posts by heat
	 * 
	 * @param textSearch
	 *            text that has been search for
	 * @param selectedTags
	 *            tags that the posts should be related to
	 * @param posts
	 *            post to augment by heat
	 * @return list of posts augmented by heat
	 */
	public ArrayList<Post> augmentPostsByHeat(String textSearch,
			ArrayList<String> selectedTags, ArrayList<Post> posts) {
		ArrayList<Post> augmPosts = new ArrayList<Post>();

		intitializeThresholds(textSearch, selectedTags);

		if (posts != null) {
			// set heat for posts
			for (Post post : posts) {
				int heat = 0;
				if (post.getScore() < threshold1) {
					heat = 1;
				} else if (post.getScore() < threshold2) {
					heat = 2;
				} else if (post.getScore() < threshold3) {
					heat = 3;
				} else if (post.getScore() < threshold4) {
					heat = 4;
				} else {
					heat = 5;
				}
				post.setHeat(heat);
				augmPosts.add(post);
			}
		}
		return augmPosts;
	}

	/**
	 * Gets maximum score of posts that fulfill the search criteria
	 * 
	 * @param sFreeText
	 *            free text to search for in posts
	 * @param eSearchResultSortingAlgorithm
	 *            chosen search result algorithm
	 * @return maximum score of all posts
	 */
	public int getMaxPostScoreForFreeTextAndTagSearch(String sFreeText,
			ArrayList<String> oTags) {
		dbAdapter.open();
		return dbAdapter.getMaxPostScoreForFreeTextAndTagSearch(
				sFreeText.split(" "), oTags);
	}
}
