package com.yinyang.so.activities;

import com.yinyang.so.R;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
/**
 * An activity handling the menu events for settings selection.
 * Extend if activity should be able to go to settings.
 */
public class ShowSettingsActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_settings, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Goto settings
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;			
		}
		return super.onOptionsItemSelected(item);
	}
}
