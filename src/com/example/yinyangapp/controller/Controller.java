package com.example.yinyangapp.controller;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;

import com.example.yinyangapp.UserProfileActivity;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.database.MeanOfSearch;
import com.example.yinyangapp.database.SearchEntity;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.User;

public class Controller {

	public Controller() {
		// TODO Auto-generated constructor stub
	}
/*
	public Intent testUserProfile() {
		Intent intent = new Intent(this, UserProfileActivity.class);
		intent.putExtra(UserProfileActivity.EXTRA_USERID, 106);
		return intent;
	}
*/
	public ArrayList<DatabaseType> testSearch(Context con) {

		DatabaseAdapter mDbHelper = new DatabaseAdapter(con);
		mDbHelper.createDatabase();
		mDbHelper.open();

		String table = User.TABLE_NAME;

		ArrayList<SearchEntity> searchCriteria = new ArrayList<SearchEntity>();

		SearchEntity searchEntity1 = new SearchEntity(User.KEY_AGE, "33",
				MeanOfSearch.exact);
		SearchEntity searchEntity2 = new SearchEntity(User.KEY_LOCATION,
				"Canada", MeanOfSearch.contained);

		searchCriteria.add(searchEntity1);
		searchCriteria.add(searchEntity2);

		ArrayList<DatabaseType> users = mDbHelper.getDataByCriteria(table,
				searchCriteria);
		mDbHelper.close();
		return users;

	}

	public String getDateDifference(String startDate) {
		Calendar current = Calendar.getInstance();

		int year = current.get(Calendar.YEAR)
				- Integer.parseInt(startDate.split("-")[0]);
		int month = current.get(Calendar.MONTH)
				- Integer.parseInt(startDate.split("-")[1]);
		int day = current.get(Calendar.DAY_OF_MONTH)
				- Integer.parseInt(startDate.split("-")[2]);
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
