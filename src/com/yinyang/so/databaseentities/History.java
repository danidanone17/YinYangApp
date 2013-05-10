package com.yinyang.so.databaseentities;

import android.database.Cursor;

public class History extends DatabaseType{
	
	public static final String TABLE_NAME = "user_history";
	
	public static final String KEY_ID = "id";
	public static final String KEY_TAG = "tag";
	public static final String KEY_NR_VIEWS = "nr_views";
	
	private int id;
	private String tag;
	private int nrViews;
	
	public History(Cursor cursor){
		super(cursor);
		this.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
		this.setTag(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TAG)));
		this.setNrViews(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_NR_VIEWS)));
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

	public int getNrViews() {
		return nrViews;
	}

	public void setNrViews(int nrViews) {
		this.nrViews = nrViews;
	}
	
	public String toString(){
		return "user-history --- ID: " + this.getId() + "Tag: " + this.getTag() + ", Nr Views: " + this.getNrViews();
	}
}
