package com.yinyang.so.test;

import java.util.ArrayList;

import junit.framework.Assert;

import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.controllers.SearchResultController;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
import com.yinyang.so.databaseentities.Post;

import android.test.InstrumentationTestCase;


public class SearchResultControllerValidation extends InstrumentationTestCase {


	public void testAugmentPostsByHeat(){
		String sFreeText = "java";
		
		SearchResultController oSearchResultController = new SearchResultController(getInstrumentation().getTargetContext().getApplicationContext());
		SearchController oSearchController = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		ArrayList<Post> oPosts = oSearchController.freeTextSearch(sFreeText, SearchResultSortingAlgorithm.QuestionScoreAlgorithm);
		oPosts = oSearchResultController.augmentPostsByHeat(sFreeText, new ArrayList<String>(), oPosts);
		
		boolean bProperlyHeated = isProperlyHeated(oPosts);
		Assert.assertEquals(true, bProperlyHeated);
	}
	
	/**
	 * Checks whether the given posts are properly heated
	 * @param oPosts list of posts
	 * @return true if the given posts are properly heated
	 */
	private boolean isProperlyHeated(ArrayList<Post> oPosts){
		int heat1 = 0, heat2 = 0, heat3 = 0, heat4 = 0, heat5 = 0;
		for(Post oPost : oPosts){
			switch (oPost.getHeat()){
				case 1:
					heat1++;
					break;
				case 2:
					heat2++;
					break;
				case 3:
					heat3++;
					break;
				case 4:
					heat4++;
					break;
				case 5:
					heat5++;	
					break;
			}
		}
		return (heat1 == 0 && heat2 == 0 && heat3 == 3 && heat4 == 4 && heat5 == 3);
	}
}
