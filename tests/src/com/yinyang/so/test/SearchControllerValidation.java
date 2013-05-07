package com.yinyang.so.test;

import java.util.ArrayList;

import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;

import android.test.InstrumentationTestCase;
import junit.framework.Assert;
import junit.framework.TestCase;

public class SearchControllerValidation extends InstrumentationTestCase {

	/**
	 * Tests free text search
	 */
	public void testFreeTextSearch(){
		String sFreeText = "convert string to long";
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextSearch(sFreeText);
		
		boolean bValid = isValidFreeTextSearchResult(sFreeText, oPosts);
		Assert.assertEquals(true, bValid);	
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
	 */
	public void testFreeTextAndTagSearch(){
		String sFreeText = "convert string to long";
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("c#");
		oTags.add("rsa");
		
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextAndTagSearch(sFreeText, oTags);
		
		boolean bValid = isValidFreeTextAndTagSearchResult(sFreeText, oTags, oPosts);
		Assert.assertEquals(true, bValid);	
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
