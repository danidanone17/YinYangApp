package com.yinyang.so.activities;

import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.controllers.Controller;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.TagMapping;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.MapTags;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_related_tags, menu);
		return true;
	}

}
