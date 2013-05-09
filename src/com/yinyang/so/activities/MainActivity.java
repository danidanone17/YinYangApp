package com.yinyang.so.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.controllers.Controller;
import com.yinyang.so.databaseentities.Post;

public class MainActivity extends Activity {

	// called when clicked Test Search Top Tags
	public void testSearchTopTags(View view) {
		Intent intent = new Intent(this, TopRelatedTagsActivity.class);
		startActivity(intent);
	}

	// Called when you click the testSearch-Button
	// Displays a test search performed in the MainController

	public void testSearch(View view) {

		Controller controller = new Controller();
		ArrayList<Post> posts = controller.testSearch(this);
		TextView text01 = (TextView) findViewById(R.id.text01);

		String text = "";
		for (Post p : posts) {
			text += p.getTitle() + "\n";
		}
		text01.setText(text);
	}	
	
	// Called when you click the testUserProfileButton
	// Go to the user profile view for the specified user id 769366
	public void testUserProfile1(View _) {
		try {
			Intent intent = new Intent(this, UserProfileActivity.class);
			intent.putExtra(UserProfileActivity.EXTRA_USERID, 769366);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Called when you click the testUserProfileButton
	// Try to go to the user profile view for the non-existing user id 3 
	public void testUserProfile2(View _) {
		try {
			Intent intent = new Intent(this, UserProfileActivity.class);
			intent.putExtra(UserProfileActivity.EXTRA_USERID, 3);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void freeTextSearch(View _) {
		Intent intent = new Intent(this, FreeTextSearchActivity.class);
		startActivity(intent);
	}

	public void showQuestionView1(View _) {
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTIONID, 8471536);
		startActivity(intent);
	}

	public void showQuestionView2(View _) {
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
	
	public void searchResult(View _){
		Intent intent = new Intent(this, SearchResultActivity.class);
		startActivity(intent);
	}
	
	public void showUserList(View _){
		Intent intent = new Intent(this, UserListActivity.class);
		startActivity(intent);
	}
	
}
