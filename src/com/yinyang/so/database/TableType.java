package com.yinyang.so.database;

import com.yinyang.so.databaseentities.Comment;
import com.yinyang.so.databaseentities.History;
import com.yinyang.so.databaseentities.MapTags;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;
import com.yinyang.so.databaseentities.User;
import com.yinyang.so.databaseentities.Vote;

public enum TableType {
	posts(Post.TABLE_NAME),
	users(User.TABLE_NAME),
	comments(Comment.TABLE_NAME),
	votes(Vote.TABLE_NAME),
	tags(Tag.TABLE_NAME),
	mapping_tags(MapTags.TABLE_NAME),
	user_history(History.TABLE_NAME);
	
	private final String value;
	
	private TableType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
