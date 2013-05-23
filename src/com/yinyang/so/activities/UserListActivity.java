package com.yinyang.so.activities;

import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.controllers.UserController;
import com.yinyang.so.databaseentities.User;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UserListActivity extends ShowMenuActivity {
	private UserController userController;
	private ArrayList<User> users;
	private UserArrayAdapter userArrayAdapter;
	private final int MAXIMUM_DISPLAYED_USERS = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_list);
				
		//Adds the navigation drawer with functionality
		addDrawer(IN_USER_LIST);
		
		userController = new UserController(this);
				
		fillListView("", false);
	}	

	private class UserArrayAdapter extends ArrayAdapter<User> {

		private ArrayList<User> users;
		private final Context context;
		
		public UserArrayAdapter(Context context, int resource, ArrayList<User> users) {
			super(context, resource, users);
			this.context = context;
			this.users = users;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
		    LayoutInflater inflater = (LayoutInflater) context
		            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View v = inflater.inflate(R.layout.user_list_layout, parent, false);

			
			final User o = users.get(position);

			if (o != null) {
				// set name
				TextView name = (TextView) v.findViewById(R.id.name);
				if (name != null) {
					name.setText(o.getDisplayName());
					name.setId(o.getId());
				}
		
				// set location
				TextView location = (TextView) v.findViewById(R.id.location);
				if (location != null) {
					location.setText(o.getLocation());
				}
				
				// set reputation
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
		
		// fill list view with users
		fillListView(editView.getText().toString(), false);
	}
	
	/**
	 * Handles event when button to show next users is clicked
	 * @param view the button that invoked the onClick method
	 */
	public void showNextUsers(View view){
		// get user search text
		// if there is any
		EditText editView = (EditText)this.findViewById(R.id.edit_user_search);
		
		// fill list view with next users
		fillListView(editView.getText().toString(), true);
	}
	
	/**
	 * Fills list view with users
	 * @param 
	 */
	private void fillListView(String userSearch, boolean showNext){
		if(!showNext){
			// either activity is opened for the first time or the search button has been pressed
			users = userController.getUsersOrderedByReputation(MAXIMUM_DISPLAYED_USERS, userSearch);
		}
		else{
			if(users != null && users.size() > 0){
				// button to show next users has been pressed
				User lastUser = users.get(users.size() - 1);
				int lastUserReputation = lastUser.getReputation();
				String lastUserName = lastUser.getDisplayName();
				users = userController.getUsersOrderByReputation(MAXIMUM_DISPLAYED_USERS, userSearch, lastUserReputation, lastUserName);
			}
		}
		
		// initialize user array adapter
		userArrayAdapter = new UserArrayAdapter(this,
				R.layout.user_list_layout, users);
		
		// set post array adapter for list view
		ListView listView = (ListView) findViewById(R.id.user_list);
		listView.setAdapter(userArrayAdapter);
		
		// add onclick event to all list elements
	    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	        	int userId = users.get(position).getId();
	        	Intent intent = new Intent(UserListActivity.this, UserProfileActivity.class);
				intent.putExtra(UserProfileActivity.EXTRA_USERID, userId);
				startActivity(intent);
	        }
			});
	}
}
