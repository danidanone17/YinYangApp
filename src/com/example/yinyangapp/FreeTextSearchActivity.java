package com.example.yinyangapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FreeTextSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_text_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_text_search, menu);
		return true;
	}

}
