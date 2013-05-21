package com.yinyang.so.activities;

import com.yinyang.so.R;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class ShowMenuActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Goto settings
		case R.id.menu_goto_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.menu_goto_search:
			startActivity(new Intent(this, TagSearchActivity.class));
			return true;
		case R.id.menu_goto_user_list:
			startActivity(new Intent(this, UserListActivity.class));
			return true;			
		}
		return super.onOptionsItemSelected(item);
	}

}
