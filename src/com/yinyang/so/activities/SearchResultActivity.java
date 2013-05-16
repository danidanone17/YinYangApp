package com.yinyang.so.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.extras.PredicateLayout;

public class SearchResultActivity extends Activity {
	
	private PostArrayAdapter postArrayAdapter;
	private ArrayList<Post> posts;
	private Intent mIntent;
	private ListView mQuestionList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		// Show the Up button in the action bar.
		setupActionBar();
		
		mQuestionList = (ListView) findViewById(R.id.activity_search_result);

		// get posts to display
		mIntent = getIntent();
		getPostByOrder("POSTS_QUESTION_SCORE");
		setTitle(getTitle() + " " + mIntent.getStringExtra("TEXT_SEARCH"));
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
		getMenuInflater().inflate(R.menu.search_result, menu);
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

	private void initPostArrayAdapter() {
		postArrayAdapter = new PostArrayAdapter(this, R.layout.search_result_layout, posts);
		mQuestionList.setAdapter(postArrayAdapter);
	}
	// User one of POSTS_USER_REPUTATION, POSTS_QUESTION_SCORE, POSTS_CREATION_DATE, POSTS_ANSWER_COUNT
	private void getPostByOrder(String order) {
		posts = mIntent.getParcelableArrayListExtra(order);
		initPostArrayAdapter();
	}

	public void sortByUserReputation(View view) {
		getPostByOrder("POSTS_USER_REPUTATION");
	}

	public void sortByQuestionScore(View view) {
		getPostByOrder("POSTS_QUESTION_SCORE");
	}

	public void sortByCreationDate(View view) {
		getPostByOrder("POSTS_CREATION_DATE");
	}

	public void sortByAnswerCount(View view) {
		getPostByOrder("POSTS_ANSWER_COUNT");
	}

	private class PostArrayAdapter extends ArrayAdapter<Post> {

		private ArrayList<Post> posts;

		public PostArrayAdapter(Context context, int resource,
				ArrayList<Post> posts) {
			super(context, resource, posts);
			this.posts = posts;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = vi.inflate(R.layout.search_result_layout, parent, false);
			
			Post o = posts.get(position);

			if (o != null) {
				// set answer count
				TextView tt = (TextView) v.findViewById(R.id.a_c);
				if (tt != null) {
					tt.setText(Integer.toString(o.getAnswerCount()));
				}

				// set title
				TextView title = (TextView) v.findViewById(R.id.title);
				if (title != null) {
					title.setText(o.getTitle());
					Log.e("TEST", "ID: " + o.getId());
					title.setId(o.getId());
					
					title.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							TextView title = (TextView) view;
							Intent oIntent = new Intent(
									SearchResultActivity.this,
									QuestionActivity.class);
							Log.e("", "id onClick: " + title.getId());
							oIntent.putExtra(
									com.yinyang.so.activities.QuestionActivity.EXTRA_QUESTIONID,
									title.getId());
							startActivity(oIntent);
						}
					});
				}

				// dynamically add tag buttons
				PredicateLayout layout = (PredicateLayout) v
						.findViewById(R.id.tag_button_layout);
				ArrayList<String> oTags = this.convertTagStringToList(o
						.getTagString());
				for (String tag : oTags) {
					Button button = new Button(getContext());
					button.setText(tag);
					layout.addView(button);
				}
			}

			return v;
		}

		/**
		 * Converts a string of tags into an array list of tags
		 * 
		 * @param sTags
		 *            string of tags in the following format <tag1><tags2>
		 * @return array list of tags
		 */
		private ArrayList<String> convertTagStringToList(String sTags) {
			ArrayList<String> oTags = new ArrayList<String>();
			for (String sTag : sTags.split(">")) {
				oTags.add(sTag.replace("<", ""));
			}
			return oTags;
		}
	}

}
