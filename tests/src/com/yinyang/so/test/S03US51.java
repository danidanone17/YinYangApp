package com.yinyang.so.test;

import java.util.ArrayList;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import com.yinyang.so.controllers.SearchController;

public class S03US51 extends InstrumentationTestCase {
	
	public void testExactlyTwo() {
		ArrayList<String> getNextAndPrevious = new ArrayList<String>();
		SearchController sc = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		getNextAndPrevious = sc.getNextAndPreviousTags(".htaccess");
		
		Assert.assertTrue( getNextAndPrevious.size() == 2 );
	}
	
	public void testGetNextAndPreviousFirst() {
		ArrayList<String> getNextAndPrevious = new ArrayList<String>();
		SearchController sc = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		getNextAndPrevious = sc.getNextAndPreviousTags(".htaccess");
		
		Assert.assertEquals(".net", getNextAndPrevious.get(0));
		Assert.assertEquals("zxing", getNextAndPrevious.get(1));
	}

	public void testGetNextAndPreviousLast() {
		ArrayList<String> getNextAndPrevious = new ArrayList<String>();
		SearchController sc = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		getNextAndPrevious = sc.getNextAndPreviousTags("zxing");
		
		Assert.assertEquals(".htaccess", getNextAndPrevious.get(0));
		Assert.assertEquals("zoom", getNextAndPrevious.get(1));
	}
	
	public void testGetNextAndPreviousMiddle() {
		ArrayList<String> getNextAndPrevious = new ArrayList<String>();
		SearchController sc = new SearchController(getInstrumentation().getTargetContext().getApplicationContext());
		getNextAndPrevious = sc.getNextAndPreviousTags("jau");
		
		Assert.assertEquals("java", getNextAndPrevious.get(0));
		Assert.assertEquals("jasperserver", getNextAndPrevious.get(1));
	}
}
