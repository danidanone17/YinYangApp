package com.yinyang.so.controllers;

import java.util.ArrayList;

public class SearchController {

	/**
	 * Parses tags selected as search criteria with search free text
	 * @param tags the tags selected as search criteria
	 * @param freeText the search free text
	 * @return parsed tags selected as search criteria with search free text
	 */
	public String parseTagSearchString(ArrayList<String> tags, String freeText){ 
		String searchString = "";
		
		// add tags selected as search criteria to beginning of parsed string surrounded by []
		for(String tag : tags){
			searchString += "[" + tag + "] ";
		}
		
		// add search free text to parsed string
		searchString += freeText;
		return searchString;
	}
}
