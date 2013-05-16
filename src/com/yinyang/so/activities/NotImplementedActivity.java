package com.yinyang.so.activities;

import com.yinyang.so.R;
import com.yinyang.so.R.layout;
import com.yinyang.so.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NotImplementedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not_implemented);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//No menu used
		return false;
	}

}
