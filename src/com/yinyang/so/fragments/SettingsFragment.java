package com.yinyang.so.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.yinyang.so.R;

/**
 * A fragment to show the preference/settings
 */
public class SettingsFragment extends PreferenceFragment{
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
	
}