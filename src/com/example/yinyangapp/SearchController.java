package com.example.yinyangapp;

import java.util.ArrayList;

public class SearchController {

	public String parseTagSearchString(ArrayList<String> tags, String freeText){
		String searchString = "";
		for(String tag : tags){
			searchString += "[" + tag + "] ";
		}
		searchString += freeText;
		return searchString;
	}
}
