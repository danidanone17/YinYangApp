package com.example.yinyangapp;

import java.util.ArrayList;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class TabSearchActivity extends Activity {
	
	private ArrayList<String> selectedTags = new ArrayList<String>();

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
	
	public void selectForSearch(View view){
		Button centerButton = (Button)view;		
		addTagToSelected(centerButton.getText().toString());
	}
	
	private void addTagToSelected(String tag){
		if(!selectedTags.contains(tag)){
			Button button = new Button(this.getApplicationContext());
			button.setText(tag);
			button.setTag(tag);
			
			PredicateLayout layout = (PredicateLayout)this.findViewById(R.id.layout_selectedtags);
			layout.addView(button);
			
			selectedTags.add(tag);			
		}
		else{
			removeTagFromSelected(tag);
		}
	}
	
	private void removeTagFromSelected(String tag){
		PredicateLayout layout = (PredicateLayout)this.findViewById(R.id.layout_selectedtags);	
		layout.removeView(layout.findViewWithTag(tag));
		
		selectedTags.remove(tag);
	}

}
