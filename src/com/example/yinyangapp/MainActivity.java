package com.example.yinyangapp;

import com.example.yinyangapp.R;
import com.example.yinyangapp.database.TestAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public void testDB(){
		
		TestAdapter mDbHelper = new TestAdapter(getBaseContext());        
		mDbHelper.createDatabase();      
		mDbHelper.open();

		Cursor testdata = mDbHelper.getTestData();
		TextView text01 = (TextView) findViewById(R.id.text01);
		text01.setText(testdata.getString(3));
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
