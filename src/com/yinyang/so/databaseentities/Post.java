package com.yinyang.so.databaseentities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import android.database.Cursor;
import android.net.UrlQuerySanitizer;
import android.os.Parcel;
import android.os.Parcelable;

public class Post extends DatabaseType implements Parcelable{
	private int mData;
	
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
	
	// Parcelling part
    public Post(Parcel in){
    	super(null);
        String[] data = new String[20];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.postTypeId = Integer.parseInt(data[1]);
    	this.parentId = Integer.parseInt(data[2]);
    	this.acceptedAnswerId = Integer.parseInt(data[3]);
    	this.creationDate = data[4];
    	this.score = Integer.parseInt(data[5]);
    	this.viewCount = Integer.parseInt(data[6]);
    	this.body = data[7];
    	this.ownerUserId = Integer.parseInt(data[8]);
    	this.lastEditorUserId = Integer.parseInt(data[9]);
    	this.lastEditorDisplayName = data[10];
    	this.lastEditDate = data[11];
    	this.lastActivityDate = data[12];
    	this.communityOwnedDate = data[13];
    	this.closedDate = data[14];
    	this.title = data[15];
    	this.tags = data[16];
    	this.answerCount = Integer.parseInt(data[17]);
    	this.commentCount = Integer.parseInt(data[18]);
    	this.favoriteCount = Integer.parseInt(data[19]);
  
    }

        public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] 
        		{
                Integer.toString(this.id),
                Integer.toString(this.postTypeId),
                Integer.toString(this.parentId),
                Integer.toString(this.acceptedAnswerId),
            	this.creationDate,
            	Integer.toString(this.score),
            	Integer.toString(this.viewCount),
            	this.body,
            	Integer.toString(this.ownerUserId),
            	Integer.toString(this.lastEditorUserId),
            	this.lastEditorDisplayName,
            	this.lastEditDate,
            	this.lastActivityDate,
            	this.communityOwnedDate,
            	this.closedDate,
            	this.title,
            	this.tags,
            	Integer.toString(this.answerCount),
            	Integer.toString(this.commentCount),
            	Integer.toString(this.favoriteCount)});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Post createFromParcel(Parcel in) {
            return new Post(in); 
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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
		return formatText(body);
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

	/**
	 * Returns the tag string of the post, i.e. containing all
	 * the tags, together with braces. Use Post.getTags() instead
	 * @return a dumb string
	 */
	public String getTagString() {
		return tags;
	}
	
	/**
	 * Returns the tags parsed from the tags string
	 * @return ArrayList of tags
	 */
	public ArrayList<String> getTags() {
		if (!tags.isEmpty()) {
			String regex = "[<>]+";
			String[] tagsString = tags.replaceFirst("<", "").split(regex);
			return new ArrayList<String>(Arrays.asList(tagsString));
		}
		return new ArrayList<String>();
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
	
	public String formatText(String sUnformatted) {
		return body.replace("\\n", " ");		
	}
}
