package com.example.yinyangapp.databaseentities;

import android.database.Cursor;

public class User {

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
		this.setId(cursor.getInt(0));
		this.setReputation(cursor.getInt(1));
		this.setCreationDate(cursor.getString(2));
		this.setDisplayName(cursor.getString(3));
		this.setEmailHash(cursor.getString(4));
		this.setLastAccessDate(cursor.getString(5));
		this.setWebsiteUrl(cursor.getString(6));
		this.setLocation(cursor.getString(7));
		this.setAge(cursor.getInt(8));
		this.setAboutMe(cursor.getString(9));
		this.setViews(cursor.getInt(10));
		this.setUpVotes(cursor.getInt(11));
		this.setDownVotes(cursor.getInt(12));
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
