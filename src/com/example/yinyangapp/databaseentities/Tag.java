package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class Tag extends DatabaseType{
	
	public static final String TABLE_NAME = "tags";
	
	public static final String KEY_ID = "id";
	public static final String KEY_TAG = "tag";
	
	private int id;
	private String tag;
	
	public Tag(Cursor cursor){
		super(cursor);
		this.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
		this.setTag(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TAG)));
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id: "+ this.id + ", tag: " + this.tag;
	}

}
