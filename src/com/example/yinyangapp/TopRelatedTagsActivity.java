package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.MapTags;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.Tag;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TopRelatedTagsActivity extends Activity {
	
	public void testInsertTagMapping(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		TagMapping.insertTagMaaping(mDbHelper);
		
		mDbHelper.close();
	}
	
	public void testWhatWasInserted(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		//mDbHelper.emptyTable(MapTags.TABLE_NAME);
		//mDbHelper.emptyTable(Tag.TABLE_NAME);
		
		Controller controller = new Controller();

		
		//controller.testInsertTags(getBaseContext());
		
		ArrayList<DatabaseType> tag = controller.testSearch(getBaseContext());
		//ArrayList<DatabaseType> tag = controller.testSearchTags(getBaseContext());
		
		System.out.println("toString(result): " + tag.toString());
		
		TextView textView = (TextView) findViewById(R.id.textView1);

		String text="";
		for(DatabaseType u : tag) {
			//Tag tagObj = (Tag) u;
			//text+="TAG:" + tagObj.getTag() + "\n";
			MapTags tagObj = (MapTags) u;
			text+="TAG1:" + tagObj.getTag1() + ", TAG2: " + tagObj.getTag2() + ", COUNT: " + tagObj.getCountAppearance() + "\n";
		}
		textView.setText("Test what was imported (us38): " + text);
		
		mDbHelper.close();
	}
	
	public void displayRelatedTags(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		Controller controller = new Controller();
		ArrayList<String> tags = controller.testGetTopRelatedTags(getBaseContext());
		
		TextView textView = (TextView) findViewById(R.id.textView2);
		String text="";
		for(String tag : tags) {
			text+="TAG:" + tag + "\n";
		}
		textView.setText("Display Related Tags (us39): " + text);
		
		mDbHelper.close();
	}
	
	public void displayPostsSearchedByTags(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		Controller controller = new Controller();
		ArrayList<Post> posts = controller.testGetPostsByTags(getBaseContext());
		
		TextView textView = (TextView) findViewById(R.id.textView3);
		String text="";
		for(Post post : posts) {
			text+="Post ---- title:" + post.getTitle() + "\n";
		}
		textView.setText("Display Posts Searched by Tag Combination (us1): " + text);
		
		mDbHelper.close();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_related_tags);
		displayRelatedTags();
		//testInsertTagMapping();
		testWhatWasInserted();
		displayPostsSearchedByTags();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_related_tags, menu);
		return true;
	}

}
