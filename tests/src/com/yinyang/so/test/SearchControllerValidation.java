package com.yinyang.so.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.User;

import android.test.InstrumentationTestCase;
import junit.framework.Assert;

public class SearchControllerValidation extends InstrumentationTestCase {

	/**
	 * Tests free text search
	 * - search result should be sorted by question score
	 */
	public void testFreeTextSearchSortedByQuestionScore(){
		String sFreeText = "convert string to long";
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextSearch(sFreeText, SearchResultSortingAlgorithm.QuestionScoreAlgorithm);
		
		boolean bValid = isValidFreeTextSearchResult(sFreeText, oPosts);
		boolean bProperlySorted = isSortedByQuestionScore(oPosts);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	}
	
	/**
	 * Tests free text search
	 * - search result should be sorted by creation date
	 */
	public void testFreeTextSearchSortedByCreationDate(){
		String sFreeText = "convert string to long";
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextSearch(sFreeText, SearchResultSortingAlgorithm.CreationDateAlgorithm);
		
		boolean bValid = isValidFreeTextSearchResult(sFreeText, oPosts);
		boolean bProperlySorted = isSortedByCreationDate(oPosts);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	} 
	
	/**
	 * Tests free text search
	 * - search result should be sorted by answer count
	 */
	public void testFreeTextSearchSortedByAnswerCount(){
		String sFreeText = "convert string to long";
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextSearch(sFreeText, SearchResultSortingAlgorithm.AnswerCountAlgotithm);
		
		boolean bValid = isValidFreeTextSearchResult(sFreeText, oPosts);
		boolean bProperlySorted = isSortedByAnswerCount(oPosts);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	} 
	
	/**
	 * Tests free text search
	 * - search result should be sorted by user reputation
	 */
	public void testFreeTextSearchSortedByUserReputation(){
		String sFreeText = "convert string to long";
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextSearch(sFreeText, SearchResultSortingAlgorithm.UserReputationAlgorithm);
		
		boolean bValid = isValidFreeTextSearchResult(sFreeText, oPosts);
		boolean bProperlySorted = isSortedByUserReputation(oPosts, oSearchController);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	} 
	
	/**
	 * Tests tag search
	 */
	public void testTagSearch(){
		String sTag = "java";
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		String sFoundTag = oSearchController.getTagByName(sTag);
		
		Assert.assertEquals(true, sTag.equals(sFoundTag.toLowerCase()));
	}
	
	/**
	 * Tests free text search
	 * - search result should be sorted by question score
	 */
	public void testFreeTextAndTagSearchSortedByQuestionScore(){
		String sFreeText = "convert string to long";
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("c#");
		oTags.add("rsa");
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextAndTagSearch(sFreeText, oTags, SearchResultSortingAlgorithm.QuestionScoreAlgorithm);
		
		boolean bValid = isValidFreeTextAndTagSearchResult(sFreeText, oTags, oPosts);
		boolean bProperlySorted = isSortedByQuestionScore(oPosts);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	}
	
	/**
	 * Tests free text search
	 * - search result should be sorted by creation date
	 */
	public void testFreeTextAndTagSearchSortedByCreationDate(){
		String sFreeText = "convert string to long";
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("c#");
		oTags.add("rsa");
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextAndTagSearch(sFreeText, oTags, SearchResultSortingAlgorithm.CreationDateAlgorithm);
		
		boolean bValid = isValidFreeTextAndTagSearchResult(sFreeText, oTags, oPosts);
		boolean bProperlySorted = isSortedByCreationDate(oPosts);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	}
	
	/**
	 * Tests free text search
	 * - search result should be sorted by answer count
	 */
	public void testFreeTextAndTagSearchSortedByAnswerCount(){
		String sFreeText = "convert string to long";
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("c#");
		oTags.add("rsa");
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextAndTagSearch(sFreeText, oTags, SearchResultSortingAlgorithm.AnswerCountAlgotithm);
		
		boolean bValid = isValidFreeTextAndTagSearchResult(sFreeText, oTags, oPosts);
		boolean bProperlySorted = isSortedByAnswerCount(oPosts);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	}
	
	/**
	 * Tests free text search
	 * - search result should be sorted by user reputation
	 */
	public void testFreeTextAndTagSearchSortedByUserReputation(){
		String sFreeText = "convert string to long";
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("c#");
		oTags.add("rsa");
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextAndTagSearch(sFreeText, oTags, SearchResultSortingAlgorithm.UserReputationAlgorithm);
		
		boolean bValid = isValidFreeTextAndTagSearchResult(sFreeText, oTags, oPosts);
		boolean bProperlySorted = isSortedByUserReputation(oPosts, oSearchController);
		Assert.assertEquals(true, bValid && bProperlySorted);	
	}


	/**
	 * Checks whether the given list of posts is sorted by the question score
	 * @param oPosts list of posts that has to be checked
	 * @return true if the given list of posts is actually sorted by the question score
	 */
	private boolean isSortedByQuestionScore(ArrayList<Post> oPosts){
		for (int i = 1; i < oPosts.size(); i++){
			if(oPosts.get(i-1).getScore() < oPosts.get(i).getScore()){
				return false;
			}
		}	
		
		return true;
	}
	
	/**
	 * Checks whether the given list of posts is sorted by creation date
	 * @param oPosts list of posts that has to be checked
	 * @return true if the given list of posts is actually sorted by creation date
	 */
	private boolean isSortedByCreationDate(ArrayList<Post> oPosts){
		for (int i = 1; i < oPosts.size(); i++){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try
			{
				Date creationDatePrev = dateFormat.parse(oPosts.get(i-1).getCreationDate());
				Date creationDateCurr = dateFormat.parse(oPosts.get(i).getCreationDate());
				if(creationDatePrev.before(creationDateCurr)){
					return false;
				}
			}
			catch(ParseException ex){
				return false;
			}
		}	
		
		return true;
	}
	
	/**
	 * Checks whether the given list of posts is sorted by the answer count
	 * @param oPosts list of posts that has to be checked
	 * @return true if the given list of posts is actually sorted by the answer count
	 */
	private boolean isSortedByAnswerCount(ArrayList<Post> oPosts){
		for (int i = 1; i < oPosts.size(); i++){
			if(oPosts.get(i-1).getAnswerCount() < oPosts.get(i).getAnswerCount()){
				return false;
			}
		}	
		
		return true;
	}
	
	/**
	 * Checks whether the given list of posts is sorted by user reputation
	 * @param oPosts list of posts that has to be checked
	 * @param oSearchController the search controller
	 * @return true if the given list of posts is actually sorted by user reputation
	 */
	private boolean isSortedByUserReputation(ArrayList<Post> oPosts, SearchController oSearchController){
		for (int i = 1; i < oPosts.size(); i++){
			User userPrev = oSearchController.getUser(oPosts.get(i-1).getOwnerUserId());
			User userCurr = oSearchController.getUser(oPosts.get(i).getOwnerUserId());
			if(userPrev.getReputation() < userCurr.getReputation()){
				return false;
			}
		}	
		
		return true;
	}
	
	/**
	 * Checks whether free text search result is valid
	 * - all resulting posts have to contain all words of the free text in either the title or body
	 * @param sFreeText free text
	 * @param oPosts resulting posts
	 * @return true if free text search result is valid
	 */
	private boolean isValidFreeTextSearchResult(String sFreeText, ArrayList<Post> oPosts){
		String[] oWords = sFreeText.split(" ");
		for(Post oPost : oPosts){
			for(String sWord : oWords){
				if(!oPost.getTitle().toLowerCase().contains(sWord) && //
				   !oPost.getBody().toLowerCase().contains(sWord)){
					return false;					
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks whether free text and tag search result is valid
	 * - all resulting posts have to contain all words of the free text in either the title or body
	 * - all resulting posts are related to all provided tags
	 * @param sFreeText free text
	 * @param oTags list of tags
	 * @param oPosts resulting posts
	 * @return true if free text and tag search result is valid
	 */
	private boolean isValidFreeTextAndTagSearchResult(String sFreeText, ArrayList<String> oTags, ArrayList<Post> oPosts){
		if(isValidFreeTextSearchResult(sFreeText, oPosts)){
			for(Post oPost : oPosts){
				for(String sTag : oTags){
					if(!oPost.getTags().contains(sTag)){
						return false;
					}
				}
			}
			
			return true;
		}
		else{
			return false;
		}
	}
}
