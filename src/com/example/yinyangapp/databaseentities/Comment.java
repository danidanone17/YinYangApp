package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class Comment extends DatabaseType {

	private int id;
	private int postId;
	private int score;
	private String text;
	private String creationDate;
	private int userId;
	
	public Comment(Cursor cursor){
		super(cursor);
		this.setId(cursor.getInt(0));
		this.setPostId(cursor.getInt(1));
		this.setScore(cursor.getInt(2));
		this.setText(cursor.getString(3));
		this.setCreationDate(cursor.getString(4));
		this.setUserId(cursor.getInt(5));
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
