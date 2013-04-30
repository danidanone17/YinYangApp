package com.yinyang.so.databaseentities;

import android.database.Cursor;

public class Post extends DatabaseType {

	public static final String TABLE_NAME = "posts";

	public static final String KEY_ID = "id";
	public static final String KEY_POST_TYPE_ID = "post_type_id";
	public static final String KEY_PARENT_ID = "parent_id";
	public static final String KEY_ACCEPTED_ANSWER_ID = "accepted_answer_id";
	public static final String KEY_CREATION_DATE = "creation_date";
	public static final String KEY_SCORE = "score";
	public static final String KEY_VIEW_COUNT = "view_count";
	public static final String KEY_BODY = "body";
	public static final String KEY_OWNER_USER_ID = "owner_user_id";
	public static final String KEY_LAST_EDITOR_USER_ID = "last_editor_user_id";
	public static final String KEY_LAST_EDITOR_DISPLAY_NAME = "last_editor_display_name";
	public static final String KEY_LAST_EDIT_DATE = "last_edit_date";
	public static final String KEY_LAST_ACTIVITY_DATE = "last_activity_date";
	public static final String KEY_COMMUNITY_OWNED_DATE = "community_owned_date";
	public static final String KEY_CLOSED_DATE = "closed_date";
	public static final String KEY_TITLE = "title";
	public static final String KEY_TAGS = "tags";
	public static final String KEY_ANSWER_COUNT = "answer_count";
	public static final String KEY_COMMENT_COUNT = "comment_count";
	public static final String KEY_FAVORITE_COUNT = "favorite_count";

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

	public Post(){
		super(null);
		
	}
	
	public Post(Cursor cursor) {
		super(cursor);
		this.id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
		this.postTypeId = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_POST_TYPE_ID));
		this.parentId = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_PARENT_ID));
		this.acceptedAnswerId = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_ACCEPTED_ANSWER_ID));
		this.creationDate = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_CREATION_DATE));
		this.score = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCORE));
		this.viewCount = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_VIEW_COUNT));
		this.body = cursor.getString(cursor.getColumnIndexOrThrow(KEY_BODY));
		this.ownerUserId = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_OWNER_USER_ID));
		this.lastEditorUserId = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_LAST_EDITOR_USER_ID));
		this.lastEditorDisplayName = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_LAST_EDITOR_DISPLAY_NAME));
		this.lastEditDate = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_LAST_EDIT_DATE));
		this.lastActivityDate = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_LAST_ACTIVITY_DATE));
		this.communityOwnedDate = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_COMMUNITY_OWNED_DATE));
		this.closedDate = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_CLOSED_DATE));
		this.title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
		this.tags = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TAGS));
		this.answerCount = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_ANSWER_COUNT));
		this.commentCount = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_COMMENT_COUNT));
		this.favoriteCount = cursor.getInt(cursor
				.getColumnIndexOrThrow(KEY_FAVORITE_COUNT));
	}

	@Override
	public String toString() {
		return this.id + " " + this.title + " " + this.score + this.tags;
	}

	public int getId() {
		return id;
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
