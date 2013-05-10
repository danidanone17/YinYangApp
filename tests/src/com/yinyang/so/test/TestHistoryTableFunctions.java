package com.yinyang.so.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.test.InstrumentationTestCase;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.HistoryTable;
import com.yinyang.so.databaseentities.History;

import junit.framework.TestCase;

public class TestHistoryTableFunctions extends InstrumentationTestCase {

	private DatabaseAdapter db;

	// setUp the DatabaseAdapter
	protected void setUp() throws Exception {
		super.setUp();
		db = new DatabaseAdapter(this.getInstrumentation().getTargetContext()
				.getApplicationContext());
		db.createDatabase();
		db.open();
	}

	// destroy the DatabaseAdapter
	protected void tearDown() throws Exception {
		super.tearDown();
		db.close();
	}

	// test for no tag contained in the question is existent in the history
	// table
	public void testViewQuestionToHistory1() {
		HistoryTable ht = new HistoryTable();
		String file = "HistTable1.txt";
		
		ArrayList<String> fileLines = this.getLinesFromFile(file);
		
		//the id of the question that is to be viewed
		int postId;
		
		/**
		 * expectedOutput contains the tags that will be inserted/updated with
		 * their corresponding nrViews after questionViewed() has been called
		 */
		HashMap<String, String> expectedOutput = new HashMap<String, String>();
		
		//fill postId and expectedOutput
		postId = Integer.parseInt(fileLines.get(0));
		
		for (int i = 1; i < fileLines.size(); i++) {
			expectedOutput.put(fileLines.get(i).split(" ")[0], fileLines.get(i).split(" ")[1]);
		}

		// creation of the table - done just now (the first time) and then it
		// will remain commented
		//ht.createTable(db);
		
		//the table is emptied so that the data corresponds to the txt file
		db.emptyTable(History.TABLE_NAME);

		// call questionViewed(int postId) for the
		ht.questionViewed(db, postId);
		
		//verify results
		History hist;
		for (String tag : expectedOutput.keySet()) {
			hist = db.getTagFromHistory(tag);
			String result = "" + hist.getNrViews();
			String expectedResult = expectedOutput.get(tag);
			assertTrue(expectedResult.equals(result));
		}
	}
	
	public ArrayList<String> getLinesFromFile(String file){
		ArrayList<String> lines = new ArrayList<String>();

		try {
			// Open the file that is the first
			// command line parameter
			//FileInputStream fstream = new FileInputStream(file);
			// Get the object of DataInputStream
			InputStream in = this.getInstrumentation().getTargetContext().getApplicationContext().getAssets().open(file);//new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				lines.add(strLine);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return lines;

	}

}
