package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.R;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.User;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public void testDB(){
		
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());        
		mDbHelper.createDatabase();      
		mDbHelper.open();

		ArrayList<DatabaseType> users = mDbHelper.getVotes();
		TextView text01 = (TextView) findViewById(R.id.text01);
		String text="";
		for(DatabaseType u : users) {
			text+=u.toString()+" ";
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
