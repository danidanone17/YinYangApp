package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class User extends DatabaseType {

	public static final String KEY_ID = "id";
	public static final String KEY_REPUTATION = "reputation";
	public static final String KEY_CREATION_DATE = "creation_date";
	public static final String KEY_DISPLAY_NAME = "display_name";
	public static final String KEY_EMAIL_HASH = "email_hash";
	public static final String KEY_LAST_ACCESS_DATE = "last_access_date";
	public static final String KEY_WEBSITE_URL = "website_url";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_AGE = "age";
	public static final String KEY_ABOUT_ME = "about_me";
	public static final String KEY_VIEWS = "views";
	public static final String KEY_UP_VOTES = "up_votes";
	public static final String KEY_DOWN_VOTES = "down_votes";
	
	private int id;
	private int reputation;
	private String creationDate;
	private String displayName;
	private String emailHash;
	private String lastAccessDate;
	private String websiteUrl;
	private String location;
	private int age;
	private String aboutMe;
	private int views;
	private int upVotes;
	private int downVotes;
	
	public int getId() {
		return id;
	}
	
	public User(Cursor cursor){
		super(cursor);
		this.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
		this.setReputation(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_REPUTATION)));
		this.setCreationDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATION_DATE)));
		this.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DISPLAY_NAME)));
		this.setEmailHash(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL_HASH)));
		this.setLastAccessDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_ACCESS_DATE)));
		this.setWebsiteUrl(cursor.getString(cursor.getColumnIndexOrThrow(KEY_WEBSITE_URL)));
		this.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION)));
		this.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AGE)));
		this.setAboutMe(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ABOUT_ME)));
		this.setViews(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VIEWS)));
		this.setUpVotes(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_VOTES)));
		this.setDownVotes(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DOWN_VOTES)));
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getReputation() {
		return reputation;
	}
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmailHash() {
		return emailHash;
	}
	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}
	public String getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(String lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getUpVotes() {
		return upVotes;
	}
	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}
	public int getDownVotes() {
		return downVotes;
	}
	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}

	@Override
	public String toString() {
		return this.id+" "+this.displayName+" "+this.location;
		
	}
	
}
