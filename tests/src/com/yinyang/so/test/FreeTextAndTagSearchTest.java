package com.yinyang.so.test;

import java.util.ArrayList;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.Post;

import android.test.InstrumentationTestCase;

public class FreeTextAndTagSearchTest extends InstrumentationTestCase{
	
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
	
	public void testReturnedQuestionsById(){
		String[] oWords = {"how", "convert", "string", "column"};
		String[] tags = {"java", "excel"};
		
		int returnedId = 8417315;
		
		ArrayList<Post> questions = db.getQuestionsByFreeSearchAndTagCombination(oWords, tags);
		
		assertEquals(returnedId, questions.get(0).getId());	
		
	}
	
}
