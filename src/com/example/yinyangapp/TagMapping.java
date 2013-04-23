package com.example.yinyangapp;
import com.example.yinyangapp.database.*;
import com.example.yinyangapp.databaseentities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TagMapping {
	
	String tag1;
	String tag2;
	ArrayList<String> postTagLines;
	ArrayList<String> mappingTagLines;
	
	public static void insertTagMaaping(DatabaseAdapter mDbHelper){
		
		Post post;
		String tagLine;
		ArrayList<DatabaseType> lines = mDbHelper.getColumnsFromPosts(new ArrayList<String>(Arrays.asList("tag")));
		
		for (DatabaseType databaseTypeLine : lines) {
			post = (Post) databaseTypeLine;
			tagLine = post.getTags();
			
			tagLine.replace("<","");
			String [] tags = tagLine.split(">");
			
			for (int i = 0; i < tags.length - 1; i++)
				for(int j = 1; j < tags.length; j++){
					
				}
			
		}
		
	}

}
