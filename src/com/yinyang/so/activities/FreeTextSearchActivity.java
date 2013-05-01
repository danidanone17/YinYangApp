package com.yinyang.so.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yinyang.so.R;
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

	public void searchFreeText(View view) {
		EditText editText = (EditText) findViewById(R.id.stringSearch);
		textSearch = editText.getText().toString();
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());
		mDbHelper.createDatabase();
		mDbHelper.open();
		
		ArrayList<Post> postsFound = mDbHelper.getQuestionsByFreeText(textSearch.split(" "));
		testSearch(postsFound);
	}

	// for testing, append on the text view the result, each on a different line
	public void testSearch(ArrayList<Post> postsFound) {
		TextView textView = (TextView) findViewById(R.id.searchResults);
		textView.setText("");

		for (Post post : postsFound) {
			textView.append(post.getTitle() + "\n");
		}
	}
}
