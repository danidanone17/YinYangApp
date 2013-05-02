package com.yinyang.so.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yinyang.so.R;
import com.yinyang.so.controllers.UserProfileController;

public class UserProfileActivity extends Activity {

	public final static String EXTRA_USERID = "com.example.YingYangApp.USERID";
	public final static String EXTRA_DB_HELPER = "com.example.YingYangApp.DBHELPER";
	private UserProfileController controller;
	private int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		Intent intent = getIntent();
		userId = intent.getIntExtra(EXTRA_USERID, -1);
		try {
			controller = new UserProfileController(this, userId);
		} catch (NullPointerException e) {
			String text = "No user with id: " + userId;
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
			toast.show();
			finish();
		}
		if (controller != null) {
			setContentView(R.layout.activity_user_profile);
			updateView();
		}	

	}
	@Override
	protected void onResume(){
		super.onResume();
		//if (controller == null)
			//this.finish();
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
		textView.setText("" + controller.getReputationText());
	}

	private void setUserNameText() {
		TextView textView = (TextView) findViewById(R.id.userName);
		textView.setText(controller.getUserName());
	}

	private void setCreationDateText() {
		TextView textView = (TextView) findViewById(R.id.membershipDuration);
		textView.setText(controller.getTimeSinceCreation());

	}

	private void setLastAccessDate() {
		TextView textView = (TextView) findViewById(R.id.lastSeen);
		textView.setText(controller.getTimeSinceAccess());
	}

	private void setWebsite() {
		TextView textView = (TextView) findViewById(R.id.website);
		textView.setText(controller.getWebsite());
	}

	private void setLocation() {
		TextView textView = (TextView) findViewById(R.id.location);
		textView.setText(controller.getLocation());
	}

	private void setAge() {
		TextView textView = (TextView) findViewById(R.id.age);
		textView.setText(Integer.toString(controller.getAge()));
	}

	private void setDescription() {
		TextView textView = (TextView) findViewById(R.id.userDescription);
		textView.setText(Html.fromHtml(controller.getDescriptionInHtml()));
	}

	private void setProfileViews() {
		TextView textView = (TextView) findViewById(R.id.profileViews);
		textView.setText(Integer.toString(controller.getProfileViews()));
	}

	public void favoriteQuestionsView(View view) {
		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}

	public void reputationView(View view) {

		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}

	public void answersView(View view) {

		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}

	public void questionsView(View view) {

		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}

	public void tagsView(View view) {
		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}

	public void badgesView(View view) {

		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}

	public void activitiesView(View view) {

		Intent intent = new Intent(this, NotImplementedActivity.class);
		startActivity(intent);
	}
}