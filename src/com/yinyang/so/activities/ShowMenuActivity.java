package com.yinyang.so.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yinyang.so.R;

public class ShowMenuActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private int selectedItem = 0;
	
	protected final int IN_USER_LIST = 1;
	protected final int IN_SEARCH = 0;
	protected final int NOT_IN_SEARCH_OR_USER_LIST = 100;
	
	// Have to set layout first
	protected void addDrawer(){	
		addDrawer(NOT_IN_SEARCH_OR_USER_LIST);
	}
	
	protected void addDrawer(int position) {
		Log.e("", "DRAWER_1");
		String[] menuItems = { "Search", "Users", "Settings" };
		Log.e("", "DRAWER_2");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		Log.e("", "DRAWER_3");
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		Log.e("", "DRAWER_4");
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, menuItems));
		Log.e("", "DRAWER_5");
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		Log.e("", "DRAWER_6");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Log.e("", "DRAWER_7");
		getActionBar().setHomeButtonEnabled(true);
		Log.e("", "DRAWER_8");
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
			mDrawerLayout, /* DrawerLayout object */
			R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
			R.string.drawer_open, /* "open drawer" description for accessibility */
			R.string.drawer_close /* "close drawer" description for accessibility */
			) {
				public void onDrawerClosed(View view) {
					invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				}

				public void onDrawerOpened(View drawerView) {
					invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				}
		};
		Log.e("", "DRAWER_9");
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (position != NOT_IN_SEARCH_OR_USER_LIST){
			mDrawerList.setItemChecked(position, true);
			selectedItem = position;
		}
		Log.e("", "DRAWER_10");
		
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position != selectedItem) {
				selectItem(position);
			}
			else{
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
	}

	private void selectItem(int position) {
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
		mDrawerList.setItemChecked(selectedItem, true);
		switch (position) {
		// Goto settings
		case 0:
			startActivity(new Intent(this, TagSearchActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, UserListActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
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
