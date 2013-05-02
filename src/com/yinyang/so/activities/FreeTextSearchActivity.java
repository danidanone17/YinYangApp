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

	/**
	 * Handles event when search button is clicked
	 * @param view the search button
	 */

	@SuppressWarnings("unchecked")

	public void searchFreeText(View view) {
		// free text to search for
		EditText editText = (EditText) findViewById(R.id.stringSearch);
		textSearch = editText.getText().toString();
		
		// execute search
		SearchController oSearchController = new SearchController(getBaseContext());	
		ArrayList<Post> postsFound = oSearchController.freeTextSearch(textSearch);
		
		// present search result
		testSearch(postsFound);
		ArrayList<DatabaseType> oPosts = performSearch(textSearch);

		// invoke search result activity
		Intent oIntent = new Intent(FreeTextSearchActivity.this,SearchResultActivity.class);
		oIntent.putParcelableArrayListExtra("POSTS", (ArrayList<? extends Parcelable>) oPosts);
		startActivity(oIntent);
	}

	// for testing, append on the text view the result, each on a different line
	public void testSearch(ArrayList<Post> postsFound) {
		TextView textView = (TextView) findViewById(R.id.searchResults);
		textView.setText("");

		for (Post post : postsFound) {
			textView.append(post.getTitle() + "\n");
		}
	}
	
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
