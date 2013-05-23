package com.yinyang.so.activities;

import com.yinyang.so.R;
import com.yinyang.so.fragments.SettingsFragment;

import android.app.Activity;
import android.os.Bundle;
/**
 * 
 * A screen showing only the settings fragment with available settings to toggle for the user
 *
 */
public class SettingsActivity extends Activity {
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	        
	        // Display the fragment as the main content.
	        getFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new SettingsFragment())
	                .commit();
	    } 
	  
	}