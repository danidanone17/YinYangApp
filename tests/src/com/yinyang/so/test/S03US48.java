package com.yinyang.so.test;

import java.util.ArrayList;
import com.yinyang.*;

import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.*;

import android.test.InstrumentationTestCase;
import junit.framework.Assert;
import junit.framework.TestCase;

public class S03US48 extends InstrumentationTestCase {
	
	/**
	 * Test if a tag which exists in the tags column from the post table also exists and has the correct count in the tags table
	 */
	public void testInsertedTagAndTagCount(){
		String tagName = "java";
		int nrAppearances = 409;
		
		DatabaseAdapter da = new DatabaseAdapter(this.getInstrumentation().getTargetContext().getApplicationContext());
		da.createDatabase();
		da.open();
		
		//get the tag object based on the provided tag name
		Tag oTag = da.getTagObjectByName(tagName);
		
		//verify if the oTag object contains the same nr of appearances as the provided nr of appearances
		Assert.assertTrue(oTag.getCountAppearance()==nrAppearances);
		da.close();
	}
}
