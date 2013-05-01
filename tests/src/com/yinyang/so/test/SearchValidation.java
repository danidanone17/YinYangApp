package com.yinyang.so.test;

import java.util.ArrayList;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.Post;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SearchValidation extends TestCase {

	public SearchValidation(String name) {
		super(name);
	}
	
	/**
	 * Tests whether free text search returns the expected amount of questions
	 */
	@Test
	public testFreeTextSearch()
	{
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());
		mDbHelper.createDatabase();
		mDbHelper.open();
		
		String sFreeText = "convert long to string";
		ArrayList<Post> postsFound = mDbHelper.getQuestionsByFreeText(sFreeText.split(" "));
		
		Assert.assertEquals(15, postsFound.size());
	}
}
