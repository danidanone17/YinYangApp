package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.Tag;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TopRelatedTagsActivity extends Activity {
	
	public void displayRelatedTags(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		Controller controller = new Controller();
		ArrayList<Tag> tags = controller.testGetTopRelatedTags(getBaseContext());
		
		TextView textView = (TextView) findViewById(R.id.textView);
		String text="";
		for(Tag tag : tags) {
			text+="TAG:" + tag.getTag() + "\n";
		}
		textView.setText(text);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_related_tags);
		displayRelatedTags();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_related_tags, menu);
		return true;
	}

}
