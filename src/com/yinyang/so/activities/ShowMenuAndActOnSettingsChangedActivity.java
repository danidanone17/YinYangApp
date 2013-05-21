package com.yinyang.so.activities;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

/**
 * An activity extending the menu handling the menu events for settings selection. 
 * 
 * Extend this activity if activity should be able to listen to changes in settings.
 * 
 * Method updateSettings have to be implemented to handle what to do when a setting is changed.
 * Usually updating the user interface.
 */
public abstract class ShowMenuAndActOnSettingsChangedActivity extends ShowMenuActivity implements
		OnSharedPreferenceChangeListener {

	public static final String KEY_PREF_HEAT_MAPPING = "pref_heat_mapping";

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
