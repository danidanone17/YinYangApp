package com.yinyang.so.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.extras.PredicateLayout;

/**
 * Show the search result in a list with the possibilities to sort by user
 * reputation, question score, creation date of post and by number of answers
 * 
 * Implements OnSharedPreferenceChangeListener to listen for changes in heat
 * mapping choice
 * 
 * Extends ShowSettingsActivity to show menu with settings and handle menu
 * selection
 */
public class SearchResultActivity extends ShowSettingsActivity{
	// implements OnSharedPreferenceChangeListener
//	public static final String KEY_PREF_HEAT_MAPPING = "pref_heat_mapping";
	
	private PostArrayAdapter postArrayAdapter;
	private ArrayList<Post> activePosts;
	private Intent mIntent;
	private ListView mQuestionList;
	private int selectedButtonTextColor = Color.GRAY;
	private int selectedButtonBackgroundColor = Color.LTGRAY;
	private int unSelectedButtonTextColor = Color.DKGRAY;
	private int unSelectedButtonBackgroundColor = Color.GRAY;
	private Button[] sortButtons = new Button[4];
	private boolean heatMapActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		// Show the Up button in the action bar.
		setupActionBar();

		//Init the sort buttons 
		setupButtons();
		//Setup the setting's listener and get the current toggle for heat mapping
		heatMapActive = getSettings();
		
		
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
		// Create the menu from the extended ShoWSettingsActivity
		// use menu.add() to add more items to menu
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// navigate up one level
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		// manage the settings menu in ShoWSettingsActivity
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Get the user's preference of showing heat map
	 */
/*	private void getSettings() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		heatMapActive = sharedPref.getBoolean("pref_heat_mapping", true);
		sharedPref.registerOnSharedPreferenceChangeListener(this);
	}
*/
	/**
	 * Connect the sorting selection button in the layout to sortButtons Set the
	 * "sort by question score"-button as selected
	 */
	private void setupButtons() {
		sortButtons[0] = (Button) findViewById(R.id.button_sort_by_user_reputation);
		sortButtons[1] = (Button) findViewById(R.id.button_sort_by_question_score);
		sortButtons[2] = (Button) findViewById(R.id.button_sort_by_creation_date);
		sortButtons[3] = (Button) findViewById(R.id.button_sort_by_answer_count);
		setButtonColors(1);
	}

	/**
	 * Set the inputed button as selected by changing the colors to selected
	 * colors Change the colors of the rest of the buttons to the unselected
	 * colors
	 * 
	 * @param selectedButton
	 *            id 0-3
	 */
	private void setButtonColors(int selectedButton) {
		for (int i = 0; i < sortButtons.length; i++) {
			if (selectedButton == i) {
				sortButtons[i].setTextColor(selectedButtonTextColor);
				sortButtons[i]
						.setBackgroundColor(selectedButtonBackgroundColor);
			} else {
				sortButtons[i].setTextColor(unSelectedButtonTextColor);
				sortButtons[i]
						.setBackgroundColor(unSelectedButtonBackgroundColor);
			}

		}

	}

	/**
	 * set the postArrayAdapter to the active set of posts connect it to the
	 * list view
	 */
	private void initPostArrayAdapter() {
		postArrayAdapter = new PostArrayAdapter(this,
				R.layout.search_result_layout, activePosts);
		mQuestionList.setAdapter(postArrayAdapter);
	}

	/**
	 * Fetch the the post ordering from the intent based on the inputed order
	 * 
	 * @param order
	 *            - One of POSTS_USER_REPUTATION, POSTS_QUESTION_SCORE,
	 *            POSTS_CREATION_DATE, POSTS_ANSWER_COUNT
	 */
	private void getPostByOrder(String order) {
		activePosts = mIntent.getParcelableArrayListExtra(order);
		initPostArrayAdapter();
	}

	/**
	 * Order the questions by user reputation, Called when clicking the user
	 * reputation button.
	 * 
	 * @param view
	 */
	public void sortByUserReputation(View view) {
		getPostByOrder("POSTS_USER_REPUTATION");
		setButtonColors(0);
	}

	/**
	 * Order the questions by question score. Called when clicking the question
	 * score button.
	 * 
	 * @param view
	 */
	public void sortByQuestionScore(View view) {
		getPostByOrder("POSTS_QUESTION_SCORE");
		setButtonColors(1);
	}

	/**
	 * Order the questions by creation date. Called when clicking the creation
	 * date button.
	 * 
	 * @param view
	 */
	public void sortByCreationDate(View view) {
		getPostByOrder("POSTS_CREATION_DATE");
		setButtonColors(2);
	}

	/**
	 * Order the questions by answer count. Called when clicking the answer
	 * count button.
	 * 
	 * @param view
	 */
	public void sortByAnswerCount(View view) {
		getPostByOrder("POSTS_ANSWER_COUNT");
		setButtonColors(3);
	}
	
	/**
	 * Called in extended activity when the heat map is toggled, inputs the new current toggle
	 * Updates the list view
	 * @param heatMap
	 */
	@Override
	protected void updateSettings(boolean heatMap) {
		heatMapActive = heatMap;
		postArrayAdapter.notifyDataSetChanged();
	}

	/**
	 * The adapter helps adding the posts to the list view.
	 */
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
				// set background color if heat map is activated
				if (heatMapActive) {
					RelativeLayout relativeLayout = (RelativeLayout) v
							.findViewById(R.id.listItemLayout);
					relativeLayout.setBackgroundColor(getColorByHeat(o
							.getHeat()));
				}

				// set answer count
				TextView tt = (TextView) v.findViewById(R.id.a_c);
				if (tt != null) {
					tt.setText(Integer.toString(o.getAnswerCount()));
				}

				// set title
				TextView title = (TextView) v.findViewById(R.id.title);
				if (title != null) {
					title.setText(o.getTitle());
					title.setId(o.getId());

					title.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							TextView title = (TextView) view;
							Intent oIntent = new Intent(
									SearchResultActivity.this,
									QuestionActivity.class);
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

		/**
		 * Returns color determined by the given heat
		 * 
		 * @param heat
		 *            determines the returned color
		 * @return color determined by the given heat
		 */
		public int getColorByHeat(int heat) {
			switch (heat) {
			case 1:
				return 0xA0FF0000;
			case 2:
				return 0xA0FF4500;
			case 3:
				return 0xA0FFEA00;
			case 4:
				return 0xA0ADFF2F;
			case 5:
				return 0xA000FF08;
			default:
				return 0xA0FF0000;
			}
		}
	}
}
