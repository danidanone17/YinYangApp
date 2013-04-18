package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class Post extends DatabaseType {

	private int id;
	private int postTypeId;
	private int parentId;
	private int acceptedAnswerId;
	private String creationDate;
	private int score;
	private int viewCount;
	private String body;
	private int ownerUserId;
	private int lastEditorUserId;
	private String lastEditorDisplayName;
	private String lastEditDate;
	private String lastActivityDate;
	private String communityOwnedDate;
	private String closedDate;
	private String title;
	private String tags;
	private int answerCount;
	private int commentCount;
	private int favoriteCount;
	
	public Post(Cursor cursor){
		super(cursor);
		this.id=cursor.getInt(0);
		this.postTypeId=cursor.getInt(1);
		this.parentId=cursor.getInt(2);
		this.acceptedAnswerId=cursor.getInt(3);
		this.creationDate=cursor.getString(4);
		this.score=cursor.getInt(5);
		this.viewCount=cursor.getInt(6);
		this.body=cursor.getString(7);
		this.ownerUserId=cursor.getInt(8);
		this.lastEditorUserId=cursor.getInt(9);
		this.lastEditorDisplayName=cursor.getString(10);
		this.lastEditDate=cursor.getString(11);
		this.lastActivityDate=cursor.getString(12);
		this.communityOwnedDate=cursor.getString(13);
		this.closedDate=cursor.getString(14);
		this.title=cursor.getString(15);
		this.tags=cursor.getString(16);
		this.answerCount=cursor.getInt(17);
		this.commentCount=cursor.getInt(18);
		this.favoriteCount=cursor.getInt(19);
	}

	@Override
	public String toString() {
		return this.id + " " + this.title + " " + this.score;
	}

	public int getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(int postTypeId) {
		this.postTypeId = postTypeId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getAcceptedAnswerId() {
		return acceptedAnswerId;
	}

	public void setAcceptedAnswerId(int acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(int ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public int getLastEditorUserId() {
		return lastEditorUserId;
	}

	public void setLastEditorUserId(int lastEditorUserId) {
		this.lastEditorUserId = lastEditorUserId;
	}

	public String getLastEditorDisplayName() {
		return lastEditorDisplayName;
	}

	public void setLastEditorDisplayName(String lastEditorDisplayName) {
		this.lastEditorDisplayName = lastEditorDisplayName;
	}

	public String getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(String lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public String getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(String lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public String getCommunityOwnedDate() {
		return communityOwnedDate;
	}

	public void setCommunityOwnedDate(String communityOwnedDate) {
		this.communityOwnedDate = communityOwnedDate;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
}
