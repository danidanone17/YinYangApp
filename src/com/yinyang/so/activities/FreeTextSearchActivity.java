package com.yinyang.so.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
import com.yinyang.so.database.MeanOfSearch;
import com.yinyang.so.database.SearchEntity;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.Post;


public class FreeTextSearchActivity extends Activity {

	private String textSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_text_search);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@SuppressLint("NewApi")
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_text_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Old searchFreeText method
	 */
	public void searchFreeText(View view) {
		/*part of old search
		EditText editText = (EditText) findViewById(R.id.stringSearch);
				
		textSearch = editText.getText().toString();
		ArrayList<Post> oPosts = performSearch(textSearch);
		*/
		
		// free text to search for
		EditText editText = (EditText) findViewById(R.id.stringSearch);
		textSearch = editText.getText().toString();
		
		// invoke search result activity
		Intent oIntent = new Intent(FreeTextSearchActivity.this,SearchResultActivity.class);
				
		// pass posts sorted by question score to search result activity
		SearchController oSearchController = new SearchController(getBaseContext());	
		ArrayList<Post> postsFound = oSearchController.freeTextSearch(textSearch, SearchResultSortingAlgorithm.QuestionScoreAlgorithm);
		oIntent.putParcelableArrayListExtra("POSTS_QUESTION_SCORE", (ArrayList<? extends Parcelable>) postsFound);
		
		// pass posts sorted by creation date to search result activity
		postsFound = oSearchController.freeTextSearch(textSearch, SearchResultSortingAlgorithm.CreationDateAlgorithm);
		oIntent.putParcelableArrayListExtra("POSTS_CREATION_DATE", (ArrayList<? extends Parcelable>) postsFound);
		
		startActivity(oIntent);
	}
	
	// for testing, append on the text view the result, each on a different line
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
	public ArrayList<Post> performSearch(String textSearch) {

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

		ArrayList<Post> posts = mDbHelper.getPostsByCriteria(criteria);
		return posts;
	}

}
