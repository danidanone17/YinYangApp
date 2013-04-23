package com.example.yinyangapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.User;

//import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		Intent intent = getIntent();
		userId = intent.getIntExtra(EXTRA_USERID, -1);
		//mDbHelper = intent.getParcelableExtra(EXTRA_DB_HELPER);
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

	private void getDbInformation() throws java.text.ParseException {
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());        
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
		//creationDate = sdf.parse(user.getCreationDate());
		//lastAccessDate = sdf.parse(user.getLastAccessDate());
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
		textView.setText(""+reputation);
	}

	private void setUserNameText() {
		TextView textView = (TextView) findViewById(R.id.userName);
		textView.setText(name);
	}

	private void setCreationDateText() {
		TextView textView = (TextView) findViewById(R.id.membershipDuration);
		textView.setText(getDateDifference(creationDate));

	}
	private void setLastAccessDate(){
		TextView textView = (TextView) findViewById(R.id.lastSeen);
		textView.setText(getDateDifference(lastAccessDate));
	}
	
	private void setWebsite(){
		TextView textView = (TextView) findViewById(R.id.website);
		textView.setText(website);
	}
	
	private void setLocation(){
		TextView textView = (TextView) findViewById(R.id.location);
		textView.setText(location);
	}
	
	private void setAge(){
		TextView textView = (TextView) findViewById(R.id.age);
		textView.setText(""+age);
	}
	

	private void setDescription(){
		TextView textView = (TextView) findViewById(R.id.userDescription);
		textView.setText(Html.fromHtml(description));
		//textView.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	private void setProfileViews(){
		TextView textView = (TextView) findViewById(R.id.profileViews);
		textView.setText(""+profileViews);
	}
	
	private String getDateDifference(String startDate) {
		Calendar current = Calendar.getInstance();
		
		int year = current.get(Calendar.YEAR) - Integer.parseInt(startDate.split("-")[0]);
		int month = current.get(Calendar.MONTH) - Integer.parseInt(startDate.split("-")[1]);
		int day = current.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(startDate.split("-")[2]);
		if (year > 1) {
			if (month == 0) {
				return year + " years";
			}
			if (month > 1) {
				return year + " years, " + month + " months";
			}
			if (month == 1) {
				return year + " years, " + month + " month";
			}
			if (month < 0) {
				year--;
				month = month + 12;
				if (year == 1) {
					if (month > 1) {
						return year + " year, " + month + " months";
					} else {
						return year + " year, " + month + " month";
					}
				}
				if (year > 1) {
					if (month > 1) {
						return year + " years, " + month + " months";
					} else {
						return year + " years, " + month + " month";
					}
				}

			}
		}
		if (year == 1) {
			if (month == 0) {
				return year + " year";
			}
			if (month > 1) {
				return year + " year, " + month + " months";
			}
			if (month == 1) {
				return year + " year, " + month + " month";
			}
			if (month < 0) {
				month = month + 12;
				if (month > 1) {
					return month + " months";
				} else {
					return month + " month";
				}
			}
		}
		if (year == 0) {
			if (month > 1) {
				return month + " months";
			} else {
				if (day > 1) {
					return day + "days";
				}
				if (day == 0) {
					return "today";
				} else {
					return day + "day";
				}
			}
		}
		return null;
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