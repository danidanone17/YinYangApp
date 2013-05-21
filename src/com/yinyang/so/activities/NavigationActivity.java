package com.yinyang.so.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yinyang.so.R;

public class NavigationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("TEST", "-------------------------------1");
		super.onCreate(savedInstanceState);
		Log.e("TEST", "-------------------------------2");
		createNavigationDrawer();
		Log.e("TEST", "-------------------------------3");
		
	}
	
	private void createNavigationDrawer(){
		Log.e("TEST", "-------------------------------2.1");
		String[] listOfItems = {"one", "two", "three"};
		Log.e("TEST", "-------------------------------2.2");
		ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
		Log.e("TEST", "-------------------------------2.3");
		
		//set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.id.menu_item, listOfItems));
		Log.e("TEST", "-------------------------------2.4");
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		Log.e("TEST", "-------------------------------2.5");
		
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    
		@Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.e("TEST", "Position is: " + position);
	    }    

	}

}
