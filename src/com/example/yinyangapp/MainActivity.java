package com.example.yinyangapp;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.yinyangapp.R;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.User;

import android.os.Bundle;
import android.app.Activity;
//import android.database.Cursor;
//import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public void testDB(){
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());        
		mDbHelper.createDatabase();      
		mDbHelper.open();
		HashMap<String, String> hash = new HashMap<String, String>();
		hash.put(User.KEY_AGE, "32");
		hash.put(User.KEY_LOCATION, "Canada");
		String table = User.TABLE_NAME;
		ArrayList<DatabaseType> users = mDbHelper.getDataByCriteria(table, hash);
		TextView text01 = (TextView) findViewById(R.id.text01);
		String text="";
		for(DatabaseType u : users) {
			text+=u.toString()+"\n";
		}
		text01.setText(text);
		mDbHelper.close();
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testDB();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
