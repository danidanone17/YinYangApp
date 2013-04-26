package com.example.yinyangapp;

import java.util.ArrayList;

import com.example.yinyangapp.controller.Controller;
import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.Tag;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TabSearchActivity extends Activity {
	
	/**
	 * List of tags that are currently selected for search
	 */
	private ArrayList<String> selectedTags = new ArrayList<String>();
	
	/**
	 * Maximum number of tags that can be selected for search
	 */
	private final int MAXIMUM_NO_SELECTED_TAGS = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_search);
		
		// change style of centered tag button
		Button button = (Button)this.findViewById(R.id.button_center);
		button.getBackground().setColorFilter(0xA0149EE3, PorterDuff.Mode.MULTIPLY);

		DatabaseAdapter mDbHelper = new DatabaseAdapter(getBaseContext());        
		mDbHelper.createDatabase();      
		mDbHelper.open();
		
		TagMapping tagMapping = new TagMapping();
		tagMapping.createTagsTable(mDbHelper);
		tagMapping.createEmptyMappingTable(mDbHelper);		
		tagMapping.insertTagMaaping(mDbHelper);		
		
		// fill tab buttons
		this.prefillTagButtons();
	}

	@Override
	protected void onResume(){
		super.onResume();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_search, menu);
		return true;
	}
	
	/**
	 * Fills tag buttons with content
	 */
	private void prefillTagButtons()
	{
		// get tags in alphabetical order
		Controller controller = new Controller();
		ArrayList<Tag> tags = controller.getTagsInAlphabeticalOrder(getBaseContext());
		
		// fill button_west
		if (tags.get(0) != null)
		{
			Button buttonWest = (Button)this.findViewById(R.id.button_west);
			buttonWest.setText(tags.get(0).getTag());
		}
		
		// fill button_center
		String centerTag = tags.get(1).getTag();
		if (tags.get(1) != null)
		{
			Button buttonWest = (Button)this.findViewById(R.id.button_center);
			buttonWest.setText(tags.get(1).getTag());
		}
		
		// fill button_east
		if (tags.get(2) != null)
		{
			Button buttonWest = (Button)this.findViewById(R.id.button_east);
			buttonWest.setText(tags.get(2).getTag());
		}
		
		// get tags related to tag displayed on button_center
		ArrayList<String> relatedTags = controller.getTopRelatedTags(getBaseContext(), centerTag);
		
		Button buttonNorthWest = (Button)this.findViewById(R.id.button_northwest);
		buttonNorthWest.setText(relatedTags.get(0));
		
		Button buttonNorthEast = (Button)this.findViewById(R.id.button_northeast);
		buttonNorthEast.setText(relatedTags.get(1));
		
		Button buttonSouthWest = (Button)this.findViewById(R.id.button_southwest);
		buttonSouthWest.setText(relatedTags.get(2));
		
		Button buttonSouthEast = (Button)this.findViewById(R.id.button_southeast);
		buttonSouthEast.setText(relatedTags.get(3));
		
	}
	
	/**
	 * Handles event when button with id button_east is pressed
	 * - Text on button button_west will be replaced by text on button button_center
	 * - Text on button button_center will be replaced by text on button button_east
	 * - Text on button button_east will be replaced by text of 'next' tag
	 * @param view the button that invoked the onClick method
	 */
	public void moveForwards(View view){
		Button button = (Button)view;
		button.setText("Hej");
	}
	
	/**
	 * Handles event when button with id button_west is pressed
	 * - Text on button button_west will be replaced by text of 'previous' tag
	 * - Text on button button_center will be replaced by text on button button_west
	 * - Text on button button_east will be replaced by text on button button_center
	 * @param view the button that invoked the onClick method
	 */
	public void moveBackwards(View view){
		Button button = (Button)view;
		button.setText("Ho");
	}
	
	/**
	 * Handles event when any of the related tag buttons is pressed
	 * - Text on button button_center will be replaced by text on button that has been pressed
	 * - Text of buttons left and right to button button_center will be replaced by text of 'previous' and 'next' tag
	 * - Text of the related tag buttons will be replaced by the text of top-related tags
	 * @param view the button that invoked the onClick method
	 */
	public void moveToCenter(View view){
		// get text of pressed button
		Button button = (Button)view;
		String text = button.getId() + "";
		
		// replace text of button_center with new text
		Button centerButton = (Button)this.findViewById(R.id.button_center);
		centerButton.setText(text);
	}
	
	/**
	 * Handles event when button button_center is pressed
	 * - If tag has not been selected for search yet and 
	 *   the maximum number of tags selected for search has not been exceeded,
	 *   the tag will be added to the tags selected for search
	 * - If tag has already been selected for search,
	 *   the tag will be removed from the tags selected for search
	 * - If the maximum number of tags selected for search has been exceeded,
	 *   a toast will be displayed
	 * @param view the button that invoked the onClick method
	 */
	public void selectForSearch(View view){
		Button centerButton = (Button)view;		
		addTagToSelected(centerButton.getText().toString());
	}
	
	/**
	 * Helper method that adds tag to tags selected for search
	 * - If tag has not been selected for search yet and 
	 *   the maximum number of tags selected for search has not been exceeded,
	 *   the tag will be added to the tags selected for search
	 * - If tag has already been selected for search,
	 *   the tag will be removed from the tags selected for search
	 * - If the maximum number of tags selected for search has been exceeded,
	 *   a toast will be displayed
	 * @param tag item to add to tags selected for search
	 */
	private void addTagToSelected(String tag){
		// If the maximum number of tags selected for serach has been exceeded,
	    // a toast will be displayed
		if(!selectedTags.contains(tag)){
			if(selectedTags.size() >= MAXIMUM_NO_SELECTED_TAGS)
			{
				Toast toast = Toast.makeText(this.getApplicationContext(), "Too many tags!", Toast.LENGTH_LONG);
				toast.show();	
			}
			// If tag has not been selected for search yet and 
			// the maximum number of tags selected for search has not been exceeded,
			// the tag will be added to the tags selected for search
			else {
				// create tag button to add to tags selected for search
				Button button = (Button) getLayoutInflater().inflate(R.layout.selected_tag_button, null);
				//Button button = new Button(this.getApplicationContext());
				button.setText(tag);
				button.setTag(tag);
				button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Button tagButton = (Button)view;
					removeTagFromSelected(tagButton.getText().toString());					
				}
				});
			
				// add created tag button to layout
				PredicateLayout layout = (PredicateLayout)this.findViewById(R.id.layout_selectedtags);
				layout.addView(button);
			
				selectedTags.add(tag);		
			}
		}
		else{
			// If tag has already been selected for search,
			// the tag will be removed from the tags selected for search
			removeTagFromSelected(tag);
		}
	}
	
	/**
	 * Helper method that removes tag from tags selected for search
	 * @param tag item to remove from tags selected for search
	 */
	private void removeTagFromSelected(String tag){
		PredicateLayout layout = (PredicateLayout)this.findViewById(R.id.layout_selectedtags);	
		layout.removeView(layout.findViewWithTag(tag));
		
		selectedTags.remove(tag);
	}

	/**
	 * Handles event when button button_search is pressed
	 * @param view the button that invoked the onClick method
	 */
	public void performSearch(View view){
		// get search free text
		EditText editView = (EditText)this.findViewById(R.id.edit_search);
		
		// parse tags selected for search together with search free text
		SearchController searchController = new SearchController();
		String returnedText = searchController.parseTagSearchString(selectedTags, editView.getText().toString());
		
		Toast toast = Toast.makeText(this.getApplicationContext(), returnedText, Toast.LENGTH_LONG);
		toast.show();
	}
	
}
