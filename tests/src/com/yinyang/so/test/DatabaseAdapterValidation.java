package com.yinyang.so.test;

import android.util.Log;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.SoData;

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
	
	public void testFirstIndex() {

		int index = db.getUsers().size();
		assertEquals(index, 20);
		
	}
}
