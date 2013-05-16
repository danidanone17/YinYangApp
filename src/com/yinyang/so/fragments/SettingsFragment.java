package com.yinyang.so.fragments;

import com.yinyang.so.R;
import com.yinyang.so.R.xml;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class SettingsFragment extends PreferenceFragment {
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}