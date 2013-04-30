package com.yinyang.so.activities;

import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.R.layout;
import com.yinyang.so.R.menu;
import com.yinyang.so.databaseentities.Post;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class SearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		Post post = new Post();
		
		post.setAnswerCount(2);
		post.setTitle("MySQL");
		post.setTags("<mysql><index>");
		
		ArrayList<Post> arrayPost = new ArrayList<Post>();
		
		arrayPost.add(post);
		
		ArrayAdapter adapter = new ArrayAdapter<Post>(this,
				R.layout.search_result_layout, arrayPost);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
