package com.yinyang.so.activities;

import com.yinyang.so.extras.PredicateLayout;
import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.databaseentities.Post;

import android.os.Bundle;
import android.os.Parcelable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultActivity extends Activity {
	private PostArrayAdapter postArrayAdapter;
	private ArrayList<Post> posts;
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@SuppressLint("NewApi")
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		// get posts to display
		Intent oIntent = getIntent();
		posts = oIntent.getParcelableArrayListExtra("POSTS");
		
		// initialize post array adapter
		postArrayAdapter = new PostArrayAdapter(this,
				R.layout.search_result_layout, posts);
		
		// set post array adapter for list view
		ListView listView = (ListView) findViewById(R.id.activity_search_result);
		listView.setAdapter(postArrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

	private class PostArrayAdapter extends ArrayAdapter<Post> {

		private ArrayList<Post> posts;
		
		public PostArrayAdapter(Context context, int resource, ArrayList<Post> posts) {
			super(context, resource, posts);
			this.posts = posts;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			if (v == null) {	
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.search_result_layout, null);
			} 
			
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
					title.setId(o.getId());
					title.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							TextView title = (TextView)view;
							Intent oIntent = new Intent(SearchResultActivity.this,QuestionActivity.class);
							oIntent.putExtra(com.yinyang.so.activities.QuestionActivity.EXTRA_QUESTIONID, title.getId());
							startActivity(oIntent);
						}
						});
				}
				
				
				// dynamically add tag buttons
				PredicateLayout layout = (PredicateLayout) v.findViewById(R.id.tag_button_layout);
				ArrayList<String> oTags = this.convertTagStringToList(o.getTagString());
				for(String tag : oTags)
				{
					Button button = new Button(getContext());
					button.setText(tag);
					layout.addView(button);
				}
			}
				
			return v;
		}
		
		/**
		 * Converts a string of tags into an array list of tags
		 * @param sTags string of tags in the following format <tag1><tags2>
		 * @return array list of tags
		 */
		private ArrayList<String> convertTagStringToList(String sTags){
			ArrayList<String> oTags = new ArrayList<String>();
			for(String sTag : sTags.split(">"))
			{
				oTags.add(sTag.replace("<",""));
			}
			return oTags;
		}
	}
}
