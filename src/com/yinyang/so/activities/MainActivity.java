package com.yinyang.so.activities;

import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.controllers.MainController;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	
	//called when clicked Test Search Top Tags
	public void testSearchTopTags(View view){
		Intent intent = new Intent(this, TopRelatedTagsActivity.class);
		Log.v("DEBUG", "main activity1");
		startActivity(intent);
		Log.v("DEBUG", "main activity2");
	}

	// Called when you click the testSearch-Button
	// Displays a test search performed in the MainController
		
	public void testSearch(View view) {

		MainController controller = new MainController();
		ArrayList<DatabaseType> posts = controller.testSearch(this);
		TextView text01 = (TextView) findViewById(R.id.text01);
		
		String text = "";
		for (DatabaseType p : posts) {
			Post post = (Post) p;
			text += post.getTitle() + "\n";
		}
		text01.setText(text);
	}
		
	// Called when you click the testUserProfileButton
	// Go to the user profile view for the specified user id 106
	public void testUserProfile(View _) {
		try{
		Intent intent = new Intent(this, UserProfileActivity.class);
		intent.putExtra(UserProfileActivity.EXTRA_USERID, 3);
		startActivity(intent);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void freeTextSearch(View _) {
		Intent intent = new Intent(this, FreeTextSearchActivity.class);
		startActivity(intent);
	}
	
	public void showQuestionView1(View _){
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTIONID, 8471536);
		startActivity(intent);
	}
	
	public void showQuestionView2(View _){
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTIONID, 8471528);
		startActivity(intent);
	}
	
	public void tagSearch(View _) {
		Intent intent = new Intent(this, TabSearchActivity.class);
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
