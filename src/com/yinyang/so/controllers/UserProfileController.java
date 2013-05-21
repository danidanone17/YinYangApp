package com.yinyang.so.controllers;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.yinyang.so.activities.SearchResultActivity;
import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.User;

/**
 * Controller for the UserProfileActivity. Handles the connection with the
 * datebase adapter. Provide information about one user to the
 * UserProfileActivity.
 */
public class UserProfileController {
	private DatabaseAdapter mDbHelper;
	private String timeSinceLastAccess;
	private String timeSinceCreated;
	private String descriptionInHtml;
	private User user;
	private static final String EMPTY_FIELD = "(N/A)";

	/**
	 * Fetches information from the database about a specified user.
	 * 
	 * @param context
	 *            The Context associated with the controller
	 * @param userId
	 *            The userId for the user
	 */
	public UserProfileController(Context context, int userId) {
		mDbHelper = new DatabaseAdapter(context);
		fetchUserFromDb(userId);
		if (user == null) {
			throw new NullPointerException();
		}

		String desc = user.getAboutMe();
		if (desc.equals("NULL")) {
			descriptionInHtml = EMPTY_FIELD;
		}
		
		else
			descriptionInHtml = updateHtmlDescription(desc);

		timeSinceCreated = getTimeSinceDate(user.getCreationDate());
		timeSinceLastAccess = getTimeSinceDate(user.getLastAccessDate());

	}

	private void fetchUserFromDb(int userId) {
		mDbHelper.createDatabase();
		mDbHelper.open();
		user = mDbHelper.getUser(userId);
		mDbHelper.close();

	}
	
	//Escapes all citaions marks in the text 
	private String updateHtmlDescription(String text) {
		return text.replaceAll("\"", "\\\"");
	}
	
	/**
	 * Returns the reputation for the specified user
	 * @return An int
	 */
	public int getReputationText() {
		return user.getReputation();
	}
	
	/**
	 * Returns the name for the specified user
	 * @return A String
	 */
	public String getUserName() {
		return user.getDisplayName();
	}
	
	/**
	 * Returns the id of a specified user 
	 * @return An int
	 */
	public int getUserId() {
		return user.getId();
	}
	
	/**
	 * Returns the time since the user created the profile
	 * E.g. "today" or "two years" 
	 * @return A String
	 */
	public String getTimeSinceCreation() {
		return timeSinceCreated;
	}

	/**
	 * Returns the time since the user last accessed the profile
	 * E.g. "today", "4 hours" or "2 month"
	 * @return A String
	 */
	public String getTimeSinceAccess() {
		return timeSinceLastAccess;
	}
	
	/**
	 * Returns the associated user's website. Returns an empty string if it is "NULL" in the database.
	 * 
	 * @return A String
	 */
	public String getWebsite() {
		String web = user.getWebsiteUrl();
		if (web.equals("NULL"))
			return EMPTY_FIELD;
		else
			return web;
	}
	
	/**
	 * Returns the associated user's location. Returns an empty string if it is "NULL" in the database.
	 * 
	 * @return A String
	 */
	public String getLocation() {
		String loc = user.getLocation();
		if (loc.equals("NULL"))
			return EMPTY_FIELD;
		else
			return loc;
	}

	/**
	 * Returns the string representation of the user's age. Returns an empty string if the field is 0 in the database
	 * 
	 * @return A String
	 */
	public String getAge() {
		int age = user.getAge();
		if (age == 0)
			return EMPTY_FIELD;
		else
			return Integer.toString(age);
	}

	public String getDescriptionInHtml() {
		return descriptionInHtml;
	}

	public int getProfileViews() {
		return user.getViews();
	}
	
	/**
	 * Shows the question posted by a user in the SearchResultActivity
	 * @param context
	 */
	public void gotoUserQuestionView(Context context){
		mDbHelper.open();
		ArrayList<Post> posts = mDbHelper.getQuestionsByUser(user.getId());
		mDbHelper.close();
		if(posts.isEmpty()){
			String text = "No question written by user";
			Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.show();
		}
		else{
			Intent intent = new Intent(context, SearchResultActivity.class);
			intent.putParcelableArrayListExtra("POSTS", posts);		
			context.startActivity(intent);
		}
	}

	/**
	 * Calculates the difference between a given date and today. Outputs the difference in text like "2 year" or "4 days".
	 * 
	 * @param date
	 *            String with date on "yyyy-MM-dd"
	 * @return A String
	 */

	private String getTimeSinceDate(String date) {
		Calendar today = Calendar.getInstance();

		int year = today.get(Calendar.YEAR)
				- Integer.parseInt(date.split("-")[0]);
		int month = today.get(Calendar.MONTH)
				- Integer.parseInt(date.split("-")[1]);
		int day = today.get(Calendar.DAY_OF_MONTH)
				- Integer.parseInt(date.split("-")[2]);
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
}
