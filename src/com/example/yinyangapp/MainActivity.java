package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.R;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public void test(){
		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		//mDbHelper.emptyTable(MapTags.TABLE_NAME);
		//mDbHelper.emptyTable(Tag.TABLE_NAME);
		
		Controller controller = new Controller();
		
		//controller.testInsertTags(getBaseContext());
		
		//ArrayList<DatabaseType> tag = controller.testSearch(getBaseContext());
		ArrayList<DatabaseType> tag = controller.testSearchTags(getBaseContext());
		
		System.out.println("toString(result): " + tag.toString());
		
		TextView text01 = (TextView) findViewById(R.id.text01);
		String text="";
		for(DatabaseType u : tag) {
			Tag tagObj = (Tag) u;
			text+="TAG:" + tagObj.getTag() + "\n";
			//MapTags tagObj = (MapTags) u;
			//text+="TAG1:" + tagObj.getTag1() + ", TAG2: " + tagObj.getTag2() + ", COUNT: " + tagObj.getCountAppearance() + "\n";
		}
		text01.setText(text);
		
		/*DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());     
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		TagMapping.insertCount(mDbHelper);
		
		mDbHelper.close();*/
		
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
