package com.yinyang.so.activities;

import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.controllers.SearchController;
import com.yinyang.so.controllers.UserController;
import com.yinyang.so.databaseentities.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UserListActivity extends Activity {
	private UserController userController;
	private ArrayList<User> users;
	private UserArrayAdapter userArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		
		userController = new UserController(this);		
		users = userController.getUsersOrderedByReputation();
		
		// initialize user array adapter
		userArrayAdapter = new UserArrayAdapter(this,
				R.layout.user_list_layout, users);
		
		// set post array adapter for list view
		ListView listView = (ListView) findViewById(R.id.user_list);
		listView.setAdapter(userArrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_list, menu);
		return true;
	}

	private class UserArrayAdapter extends ArrayAdapter<User> {

		private ArrayList<User> users;
		
		public UserArrayAdapter(Context context, int resource, ArrayList<User> users) {
			super(context, resource, users);
			this.users = users;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			if (v == null) {	
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.user_list_layout, null);
			} 
			
			User o = users.get(position);

			if (o != null) {
				// set name
				TextView name = (TextView) v.findViewById(R.id.name);
				if (name != null) {
					name.setText(o.getDisplayName());
					name.setId(o.getId());
					name.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							TextView name = (TextView)view;
							Intent intent = new Intent(UserListActivity.this,UserProfileActivity.class);
							intent.putExtra(UserProfileActivity.EXTRA_USERID, name.getId());
							startActivity(intent);
						}
						});
				}
			
				// set location
				TextView location = (TextView) v.findViewById(R.id.location);
				if (location != null) {
					location.setText(o.getLocation());
				}
				
				// set location
				TextView reputation = (TextView) v.findViewById(R.id.reputation);
				if (reputation != null) {
					reputation.setText(Integer.toString(o.getReputation()));
				}				
			}
				
			return v;
		}
	}
	
	/**
	 * Handles event when search user button is pressed
	 * @param view the button that invoked the onClick method
	 */
	public void performUserSearch(View view){
		// get user search text
		EditText editView = (EditText)this.findViewById(R.id.edit_user_search);
		
		users = userController.getUsersOrderedByReputation(editView.getText().toString());
	}
}
