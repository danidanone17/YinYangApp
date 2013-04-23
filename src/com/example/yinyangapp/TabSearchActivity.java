package com.example.yinyangapp;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TabSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_search);
		
		Button button = (Button)this.findViewById(R.id.button_center);
		button.getBackground().setColorFilter(0xA0149EE3, PorterDuff.Mode.MULTIPLY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_search, menu);
		return true;
	}
	
	public void moveForwards(View view){
		Button button = (Button)view;
		button.setText("Hej");
	}
	
	public void moveBackwards(View view){
		Button button = (Button)view;
		button.setText("Ho");
	}
	
	public void moveToCenter(View view){
		Button button = (Button)view;
		
		String text = button.getId() + "";
		
		Button centerButton = (Button)this.findViewById(R.id.button_center);
		centerButton.setText(text);
		

	}

}
