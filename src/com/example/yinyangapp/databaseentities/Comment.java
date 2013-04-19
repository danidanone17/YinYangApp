package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class Comment extends DatabaseType {

	public static final String TABLE_NAME = "comments";
	
	public static final String KEY_ID = "id";
	public static final String KEY_POST_ID = "post_id";
	public static final String KEY_SCORE = "score";
	public static final String KEY_TEXT = "text";
	public static final String KEY_CREATION_DATE = "creation_date";
	public static final String KEY_USER_ID = "user_id";
	
	
	private int id;
	private int postId;
	private int score;
	private String text;
	private String creationDate;
	private int userId;
	
	public Comment(Cursor cursor){
		super(cursor);
		this.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
		this.setPostId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POST_ID)));
		this.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCORE)));
		this.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEXT)));
		this.setCreationDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATION_DATE)));
		this.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID)));
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.text + " " + this.userId;
	}
	
}
