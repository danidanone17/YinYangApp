package com.example.yinyangapp.database;

public class SearchEntity {
	
	private String key;
	private String value;
	private MeanOfSearch meanOfSearch;
	
	public SearchEntity() {
		super();
	}
	
	public SearchEntity(String key, String value, MeanOfSearch meanOfSearch) {
		super();
		this.key = key;
		this.value = value;
		this.meanOfSearch = meanOfSearch;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public MeanOfSearch getMeanOfSearch() {
		return meanOfSearch;
	}

	public void setMeanOfSearch(MeanOfSearch meanOfSearch) {
		this.meanOfSearch = meanOfSearch;
	};
	
	

}
