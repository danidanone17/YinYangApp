package com.example.yinyangapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.*;
import android.webkit.WebView;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

	public final static String EXTRA_USERID = "com.example.YingYangApp.USERID";

	private int userId;
	private int reputation = 63611;
	private String name = "Chris Jester-Young";
	private Date creationDate;
	private Date lastAccessDate;
	private String website = "http://about.me/cky";
	private String location = "Raleigh, NC";
	private int age = 34;
	private String description = "hello, my name is Klas";
	private int profileViews = 4345;
	private int downVotes;
	private int upVotes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		//Intent intent = getIntent();
		//userId = intent.getIntExtra(EXTRA_USERID, -1);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		creationDate = sdf.parse("2008-08-01");
		lastAccessDate = sdf.parse("2012-07-31");
		description =  "\n\n<p>Quick links:</p>\n\n<ul>\n<li><a href=\"http://twitter.com/cky944\" rel=\"nofollow\">@cky944</a>, <a href=\"http://ckjy.tumblr.com/+\" rel=\"nofollow\">Google+</a></li>\n<li><a href=\"http://dyscour.se/ask\" rel=\"nofollow\">Ask me anything</a></li>\n<li><a href=\"http://careers.stackoverflow.com/cky\">SO Careers profile</a></li>\n<li><a href=\"http://dyscour.se/\" rel=\"nofollow\">Programming blog</a> (<a href=\"http://cky.posterous.com/\" rel=\"nofollow\">mirror</a>)</li>\n</ul>";
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
	
	private String getDateDifference(Date startDate) {
		Calendar current = Calendar.getInstance();
		Calendar creation = Calendar.getInstance();
		creation.setTime(startDate);
		if (current.compareTo(creation) < 0) {
			return null;
		}
		int year = current.get(Calendar.YEAR) - creation.YEAR;
		int month = current.get(Calendar.MONTH) - creation.MONTH;
		int day = current.get(Calendar.DAY_OF_MONTH) - creation.DAY_OF_MONTH;
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
