package com.example.yinyangapp;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.database.MeanOfSearch;
import com.example.yinyangapp.database.SearchEntity;
import com.example.yinyangapp.databaseentities.Comment;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.Post;
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

	public void searchFreeText(View view) {
		EditText editText = (EditText) findViewById(R.id.stringSearch);
		

		textSearch = editText.getText().toString();
		ArrayList<DatabaseType> postsFound = performSearch(textSearch);

		testSearch(postsFound);
	}

	// for testing, append on the text view the result, each on a different
	// line
	public void testSearch(ArrayList<DatabaseType> postsFound) {
		TextView textView = (TextView) findViewById(R.id.searchResults);
		textView.setText("");
		
		for (DatabaseType post : postsFound) {
			Post p = (Post) post;
			textView.append(p.getTitle() + "\n");
		}
	}

	// get an array of posts based on each word in textSearch
	// at this point, the search will return only the posts which contain all
	// the words in their post
	public ArrayList<DatabaseType> performSearch(String textSearch) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());
		mDbHelper.createDatabase();
		mDbHelper.open();
		ArrayList<SearchEntity> criteria = new ArrayList<SearchEntity>();

		String[] textSearchSplited = new String[200];
		textSearchSplited = textSearch.split(" ");
		for (String textSearchS : textSearchSplited) {
			// add each word to the hash to search into the text of the posts
			criteria.add(new SearchEntity(Post.KEY_BODY, textSearchS,
					MeanOfSearch.contained));
		}
		// search only in questions
		criteria.add(new SearchEntity(Post.KEY_POST_TYPE_ID, "1",
				MeanOfSearch.exact));
		String table = Post.TABLE_NAME;

		ArrayList<DatabaseType> posts = mDbHelper.getDataByCriteria(table,
				criteria);
		return posts;
	}

}
