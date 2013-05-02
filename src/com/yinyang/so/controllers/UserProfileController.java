/**
 * 
 */
package com.yinyang.so.controllers;

import java.util.Calendar;
import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.User;

/**
 * @author Fredrik
 * 
 */
public class UserProfileController {
	private DatabaseAdapter mDbHelper;
	private String timeSinceLastAccess;
	private String timeSinceCreated;
	private String descriptionInHtml;
	private User user;
	
	/**
	 * 
	 * @param context
	 * @param userId
	 */
	public UserProfileController(Context context, int userId){
		mDbHelper = new DatabaseAdapter(context);
		fetchUserFromDb(userId);
		if (user == null){
			throw new NullPointerException();
		}
	
		descriptionInHtml = updateHtmlDescription(user.getAboutMe());
		
		timeSinceCreated = getTimeSinceDate(user.getCreationDate());
		timeSinceLastAccess = getTimeSinceDate(user.getLastAccessDate());
		
	}
	
	private void fetchUserFromDb(int userId){
		mDbHelper.createDatabase();
		mDbHelper.open();
		user = mDbHelper.getUser(userId);
		mDbHelper.close();
		
	}
		
	private String updateHtmlDescription(String text){
		return text.replaceAll("\"","\\\"");
	}
	
	public int getReputationText(){
		return user.getReputation();
	}
	
	public String getUserName(){
		return user.getDisplayName();
	}

	public int getUserId() {
		return user.getId();
	}

	public String getTimeSinceCreation() {
		return timeSinceCreated;
	}

	public String getTimeSinceAccess() {
		return timeSinceLastAccess;
	}

	public String getWebsite() {
		return user.getWebsiteUrl();
	}

	public String getLocation() {
		return user.getLocation();
	}

	public int getAge() {
		return user.getAge();
	}

	public String getDescriptionInHtml() {
		return descriptionInHtml;
	}

	public int getProfileViews() {
		return user.getViews();
	}
	
	/**
	 * 
	 * @param date String with date on "yyyy-MM-dd"
	 * @return
	 */

	public String getTimeSinceDate(String date) {
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
