package com.example.yinyangapp;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.Comment;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.User;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FreeTextSearchActivity extends Activity {

	private String textSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_text_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_text_search, menu);
		return true;
	}
	
	public void searchFreeText(View view){
		EditText editText = (EditText) findViewById(R.id.stringSearch);
		TextView textView = (TextView) findViewById(R.id.searchResults);
		textView.setText("");
		
		textSearch = editText.getText().toString();
		ArrayList<DatabaseType> postsFound = performSearch(textSearch);
		
		//for testing, append on the text view the result, each on a different line
		for (DatabaseType post : postsFound) {
			
			textView.append(post.toString()+"\n");		}
	}
	
	//get an array of posts based on each word in textSearch
	//at this point, the search will return only the posts which contain all the words in their post
	public ArrayList<DatabaseType> performSearch(String textSearch){
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());        
		mDbHelper.createDatabase();      
		mDbHelper.open();
		HashMap<String, String> hash = new HashMap<String, String>();
		
		String [] textSearchSplited = new String[200];
		textSearchSplited = textSearch.split(" ");
		for(String textSearchS : textSearchSplited){
			//add each word to the hash to search into the text of the posts
			hash.put(Comment.KEY_TEXT, textSearchS);
		}
		//search only in questions
		hash.put(Comment.KEY_POST_ID, "1");
		String table = Comment.TABLE_NAME;
		
		ArrayList<DatabaseType> posts = mDbHelper.getDataByCriteria(table, hash);
		return posts;
	}

}
