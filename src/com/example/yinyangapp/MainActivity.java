package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.R;
import com.example.yinyangapp.databaseentities.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public void test(){
		Controller controller = new Controller();
		ArrayList<DatabaseType> users = controller.testSearch(getBaseContext());
		TextView text01 = (TextView) findViewById(R.id.text01);
		String text="";
		for(DatabaseType u : users) {
			User user = (User) u;
			text+=user.getDisplayName()+"\n";
		}
		text01.setText(text);
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
