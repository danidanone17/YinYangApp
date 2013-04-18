package com.example.yinyangapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class FreeTextSearchActivity extends Activity {

	private String textSearch;

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
	
	public void searchFreeText(View view){
		EditText editText = (EditText) findViewById(R.id.stringSearch);
		textSearch = editText.getText().toString();
	}

}
