package com.example.yinyangapp;

import java.text.SimpleDateFormat;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.*;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

	public final static String EXTRA_USERID = "com.example.YingYangApp.USERID";
	public final static String EXTRA_DB_HELPER = "com.example.YingYangApp.DBHELPER";
	private int userId = 304;
	private DatabaseAdapter mDbHelper;
	private int reputation;
	private String name;
	private String creationDate;
	private String lastAccessDate;
	private String website;
	private String location;
	private int age;
	private String description;
	private int profileViews;
	private int downVotes;
	private int upVotes;
	private Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		setupActionBar();
		Intent intent = getIntent();
		userId = intent.getIntExtra(EXTRA_USERID, -1);
		mDbHelper = new DatabaseAdapter(getBaseContext());
		controller = new Controller();
		try {
			getDbInformation();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void getDbInformation() throws java.text.ParseException {
		mDbHelper.createDatabase();
		mDbHelper.open();

		User user = mDbHelper.getUser(userId);

		reputation = user.getReputation();
		name = user.getDisplayName();
		website = user.getWebsiteUrl();
		location = user.getLocation();
		age = user.getAge();
		description = user.getAboutMe();
		profileViews = user.getViews();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		creationDate = user.getCreationDate();
		lastAccessDate = user.getLastAccessDate();

		description.replaceAll("\"", "\\\"");

		System.out.println(description);

		mDbHelper.close();
	}

	private void updateView() {
		setReputationText();
		setUserNameText();
		setCreationDateText();
		setLastAccessDate();
		setLocation();
		setWebsite();
		setDescription();
		setProfileViews();
		setAge();

	}

	private void setReputationText() {
		TextView textView = (TextView) findViewById(R.id.reputationScore);
		textView.setText("" + reputation);
	}

	private void setUserNameText() {
		TextView textView = (TextView) findViewById(R.id.userName);
		textView.setText(name);
	}

	private void setCreationDateText() {
		TextView textView = (TextView) findViewById(R.id.membershipDuration);
		textView.setText(controller.getDateDifference(creationDate));

	}

	private void setLastAccessDate() {
		TextView textView = (TextView) findViewById(R.id.lastSeen);
		textView.setText(controller.getDateDifference(lastAccessDate));
	}

	private void setWebsite() {
		TextView textView = (TextView) findViewById(R.id.website);
		textView.setText(website);
	}

	private void setLocation() {
		TextView textView = (TextView) findViewById(R.id.location);
		textView.setText(location);
	}

	private void setAge() {
		TextView textView = (TextView) findViewById(R.id.age);
		textView.setText("" + age);
	}

	private void setDescription() {
		TextView textView = (TextView) findViewById(R.id.userDescription);
		textView.setText(Html.fromHtml(description));
		// textView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void setProfileViews() {
		TextView textView = (TextView) findViewById(R.id.profileViews);
		textView.setText("" + profileViews);
	}

	// Do things when the buttons is pressed
	public void favoriteQuestionsView(View view) {
	}

	public void reputationView(View view) {
	}

	public void answersView(View view) {
	}

	public void questionsView(View view) {
	}

	public void tagsView(View view) {
	}

	public void badgesView(View view) {
	}

	public void activitiesView(View view) {
	}

}