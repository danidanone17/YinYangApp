package com.example.yinyangapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

public class UserProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
	
	public void favoriteQuestionsView(View view){}
	public void reputationView(View view){}
	public void answersView(View view){}
	public void questionsView(View view){}
	public void tagsView(View view){}
	public void badgesView(View view){}
	public void activitiesView(View view){}


}
