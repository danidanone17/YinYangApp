package com.yinyang.so.test;

import com.yinyang.so.controllers.QuestionController;

public class QuestionControllerValidation extends 
	android.test.AndroidTestCase {

	private QuestionController qc;
	
	protected void setUp() throws Exception {
	    super.setUp();
	    qc = new QuestionController(getContext());
	}
	
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
	public void testRandom() {
		assertEquals(qc.getPost(8414075).getScore(), 1);
		
	}

}
