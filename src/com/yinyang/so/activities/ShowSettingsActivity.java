package com.yinyang.so.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.yinyang.so.R;

/**
 * An activity handling the menu events for settings selection. 
 * 
 * Extend if activity should be able to go to settings.
 * 
 * Method updateSettings have to be implemented to handle what to do when
 */
public abstract class ShowSettingsActivity extends Activity implements
		OnSharedPreferenceChangeListener {

	public static final String KEY_PREF_HEAT_MAPPING = "pref_heat_mapping";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Goto settings
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * The updateSettings() is called when the heat map choice is toggled
	 */
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(KEY_PREF_HEAT_MAPPING)) {
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
			boolean heatMap = sharedPref.getBoolean("pref_heat_mapping", true);
			updateSettings(heatMap);
		}
	}
	
	/**
	 * Get the user's preference of showing heat map
	 */
	protected boolean getSettings() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean heatMap = sharedPref.getBoolean("pref_heat_mapping", true);
		sharedPref.registerOnSharedPreferenceChangeListener(this);
		return heatMap;
	}

	/**
	 * Implement what to do when the heat map is toggled. The toogle of heat map is inputed
	 */
	protected abstract void updateSettings(boolean heatMap);

}
