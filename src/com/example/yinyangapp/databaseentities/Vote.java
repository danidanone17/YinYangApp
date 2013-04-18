package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class Vote extends DatabaseType {

	private int id;
	private int postId;
	private int voteTypeId;
	private String creationDate;
	
	public Vote(Cursor cursor) {
		super(cursor);
		this.id = cursor.getInt(0);
		this.postId = cursor.getInt(1);
		this.voteTypeId = cursor.getInt(2);
		this.creationDate = cursor.getString(3);
	}

	@Override
	public String toString() {
		return this.id + " " + this.getPostId() + " " + this.getCreationDate();
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

	public int getVoteTypeId() {
		return voteTypeId;
	}

	public void setVoteTypeId(int voteTypeId) {
		this.voteTypeId = voteTypeId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
