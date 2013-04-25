package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.R;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public void test(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		//mDbHelper.emptyTable(MapTags.TABLE_NAME);
		//mDbHelper.emptyTable(Tag.TABLE_NAME);
		
		Controller controller = new Controller();

		
		//controller.testInsertTags(getBaseContext());
		
		//ArrayList<DatabaseType> tag = controller.testSearch(getBaseContext());
		ArrayList<DatabaseType> tag = controller.testSearchTags(getBaseContext());
		
		System.out.println("toString(result): " + tag.toString());
		
		TextView text01 = (TextView) findViewById(R.id.text01);

		String text="";
		for(DatabaseType u : tag) {
			Tag tagObj = (Tag) u;
			text+="TAG:" + tagObj.getTag() + "\n";
			//MapTags tagObj = (MapTags) u;
			//text+="TAG1:" + tagObj.getTag1() + ", TAG2: " + tagObj.getTag2() + ", COUNT: " + tagObj.getCountAppearance() + "\n";
		}
		text01.setText(text);
		
		/*DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		TagMapping.insertCount(mDbHelper);
		
		mDbHelper.close();*/
	}
	
	//called when clicked Test Search Top Tags
	public void testSearchTopTags(View view){
		Intent intent = new Intent(this, TopRelatedTagsActivity.class);
		startActivity(intent);
	}

	// Called when you click the testSearch-Button
	// Displays a test search performed in the Controller
		
	public void testSearch(View view) {

		Controller controller = new Controller();
		ArrayList<DatabaseType> users = controller.testSearch(this);
		TextView text01 = (TextView) findViewById(R.id.text01);
		
		String text = "";
		for (DatabaseType u : users) {
			User user = (User) u;
			text += user.getDisplayName() + "\n";
		}
		text01.setText(text);
	}
		
	// Called when you click the testUserProfileButton
	// Go to the user profile view for the specified user id 106
	public void testUserProfile(View _) {
		Intent intent = new Intent(this, UserProfileActivity.class);
		intent.putExtra(UserProfileActivity.EXTRA_USERID, 62);
		startActivity(intent);
	}
	
	public void freeTextSearch(View _) {
		Intent intent = new Intent(this, FreeTextSearchActivity.class);
		startActivity(intent);	
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
