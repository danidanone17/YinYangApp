package com.example.yinyangapp.databaseentities;

import android.content.Context;
import android.database.Cursor;

import com.example.yinyangapp.database.DatabaseAdapter;

public class MapTags extends DatabaseType{
	
public static final String TABLE_NAME = "mapping_tags";
	
	public static final String KEY_ID = "id";
	public static final String KEY_TAG1 = "tag1";
	public static final String KEY_TAG2 = "tag2";
	public static final String KEY_COUNT_APPEARANCE = "countAppearance";
	
	private int id;
	private String tag1;
	private String tag2;
	private int countAppearance;
	
	public MapTags(Cursor cursor) {
		super(cursor);
		this.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
		this.setTag1(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TAG1)));
		this.setTag2(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TAG2)));
		this.setCountAppearance(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COUNT_APPEARANCE)));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag1() {
		return tag1;
	}

	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}

	public String getTag2() {
		return tag2;
	}

	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

	public int getCountAppearance() {
		return countAppearance;
	}

	public void setCountAppearance(int countAppearance) {
		this.countAppearance = countAppearance;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id: " + this.id + ", tag1: " + this.tag1 + ", tag2: "
				+ this.tag2 + ", countAppearance: "+ this.countAppearance;
	}
		
}
