package com.yinyang.so.test;

import java.util.ArrayList;
import com.yinyang.*;

import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.*;

import android.test.InstrumentationTestCase;
import junit.framework.Assert;
import junit.framework.TestCase;

public class S03US48 extends android.test.InstrumentationTestCase {
	
	private DatabaseAdapter db;
	
	//setUp the DatabaseAdapter
	protected void setUp() throws Exception {
	    super.setUp();
	    db = new DatabaseAdapter(this.getInstrumentation().getTargetContext().
	    		getApplicationContext());
		db.createDatabase();
		db.open();
	}
	
	//destroy the DatabaseAdapter
	protected void tearDown() throws Exception {
	    super.tearDown();
		db.close();
	}
	
	/**
	 * Test if a tag which exists in the tags column from the post table also exists and has the correct count in the tags table
	 */
	public void testInsertedTagAndTagCount(){
		String tagName = "c++";
		int nrAppearances = 192;
		
		//get the tag object based on the provided tag name
		Tag oTag = db.getTagObjectByName(tagName);
		
		//verify if the oTag object contains the same nr of appearances as the provided nr of appearances
		System.out.println("toString - TagName: " + oTag.getTag() + ", count: " + oTag.getCountAppearance());
		Assert.assertTrue(oTag.getCountAppearance()==nrAppearances);
	}
}
