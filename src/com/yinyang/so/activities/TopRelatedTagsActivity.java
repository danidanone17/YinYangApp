package com.yinyang.so.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.controllers.Controller;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.TagMapping;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;

public class TopRelatedTagsActivity extends Activity {
	
	public void testInsertTagMapping(){
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		
		mDbHelper.createDatabase();      
		
		mDbHelper.open();
		
		//create the Tag table + fill it
		mDbHelper.dropTable(Tag.TABLE_NAME);
		TagMapping.createTagsTable(mDbHelper);
		TagMapping.insertTags(mDbHelper);
		TagMapping.insertCountTags(mDbHelper);
		
		//create the MapTag table + fill it
		/*
		mDbHelper.dropTable(MapTags.TABLE_NAME);
		TagMapping.createEmptyMappingTable(mDbHelper);
		TagMapping.insertCountMapTags(mDbHelper);
		*/
		
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
		
		//ArrayList<DatabaseType> tag = controller.testSearchMapTags(getBaseContext());
		ArrayList<Tag> tags = controller.testSearchTags(getBaseContext());
		
		System.out.println("toString(result): " + tags.toString());
		
		TextView textView = (TextView) findViewById(R.id.textView1);

		String text="";
		for(DatabaseType u : tags) {
			Tag tagObj = (Tag) u;
			text+="TAG:" + tagObj.getTag() + ", COUNT: " + tagObj.getCountAppearance() + "\n";
			//MapTags tagObj = (MapTags) u;
			//text+="TAG1:" + tagObj.getTag1() + ", TAG2: " + tagObj.getTag2() + ", COUNT: " + tagObj.getCountAppearance() + "\n";
		}
		textView.setText("Test what was imported (us38): " + text);
		
		mDbHelper.close();
	}
	
	public void displayRelatedTags(){
		Log.v("DEBUG", "Tag_1.1");
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		Log.v("DEBUG", "Tag_1.2");
		mDbHelper.createDatabase();      
		Log.v("DEBUG", "Tag_1.3");
		mDbHelper.open();
		Log.v("DEBUG", "Tag_1.4");
		Controller controller = new Controller();
		ArrayList<String> tags = controller.testGetTopRelatedTags(getBaseContext());
		Log.v("DEBUG", "Tag_1.5");
		TextView textView = (TextView) findViewById(R.id.textView2);
		Log.v("DEBUG", "Tag_1.6 size:" + tags.size());
		String text="";
		for(String tag : tags) {
			text+="TAG:" + tag + "\n";
			Log.v("DEBUG", "Tag_1.6: " + tag);
		}
		Log.v("DEBUG", "Tag_1.7");
		textView.setText("Display Related Tags (us39): " + text);
		Log.v("DEBUG", "Tag_1.8");
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
		/*Log.v("DEBUG", "Tag_1");
		displayRelatedTags();
		Log.v("DEBUG", "Tag_2");
		//testInsertTagMapping();
		Log.v("DEBUG", "Tag_3");
		testWhatWasInserted();
		Log.v("DEBUG", "Tag_4");
		displayPostsSearchedByTags();
		Log.v("DEBUG", "Tag_5");*/
		
		testInsertTagMapping();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//No menu used
		return false;
	}

}
