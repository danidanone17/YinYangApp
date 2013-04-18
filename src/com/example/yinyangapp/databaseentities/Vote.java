package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class Vote extends DatabaseType {

	public static final String KEY_ID = "id";
	public static final String KEY_POST_ID = "post_id";
	public static final String KEY_VOTE_TYPE_ID = "vote_type_id";
	public static final String KEY_CREATION_DATE = "creation_date";
	
	private int id;
	private int postId;
	private int voteTypeId;
	private String creationDate;
	
	public Vote(Cursor cursor) {
		super(cursor);
		this.id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
		this.postId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POST_ID));
		this.voteTypeId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VOTE_TYPE_ID));
		this.creationDate = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATION_DATE));
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
