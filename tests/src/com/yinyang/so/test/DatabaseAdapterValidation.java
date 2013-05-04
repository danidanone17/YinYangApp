package com.yinyang.so.test;

import java.util.ArrayList;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.MeanOfSearch;
import com.yinyang.so.database.SearchEntity;
import com.yinyang.so.database.TableType;
import com.yinyang.so.databaseentities.Post;


public class DatabaseAdapterValidation extends 
	android.test.InstrumentationTestCase {

	private DatabaseAdapter db;
	
	protected void setUp() throws Exception {
	    super.setUp();
	    db = new DatabaseAdapter(this.getInstrumentation().getTargetContext().
	    		getApplicationContext());
		db.createDatabase();
		db.open();
	}
	
	protected void tearDown() throws Exception {
	    super.tearDown();
		db.close();
	}
	
		// get posts from getDataByCriteria
	public void testGetDataByCriteria() {
		// TODO: insert data into database
		// TODO: test if data inserted
	}
		
	// tests the getCountByCriteria() method, searches for java in post body
	// fails if under 1 entry found
	public void testGetCountByCriteria() {
		SearchEntity se = new SearchEntity(Post.KEY_BODY, "java",
				MeanOfSearch.contained);
		ArrayList<SearchEntity> searchEntities = new ArrayList<SearchEntity>();
		searchEntities.add(se);
		int index = db.getCountByCriteria(TableType.posts,
				searchEntities);
		assertTrue(index > 0);
	}
	
	// tests the getLastIndex() and getFirstIndex() methods
	// comparing the result between them
	public void testGetFirstLastIndex() {
		int lastIndex = db.getLastIndex(TableType.posts);
		int firstIndex = db.getFirstIndex(TableType.posts);
		assertTrue(firstIndex < lastIndex);
	}
	
}
