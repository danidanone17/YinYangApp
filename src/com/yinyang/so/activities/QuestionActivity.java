package com.yinyang.so.activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.controllers.QuestionController;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.extras.PredicateLayout;

@SuppressLint("NewApi")
public class QuestionActivity extends Activity implements OnClickListener {

	public final static String EXTRA_QUESTIONID = "com.example.YingYangApp.QUESTIONID";
	private ListView listViewAnswers;

	private QuestionController qController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		int questionId = intent.getIntExtra(EXTRA_QUESTIONID, -1);
		Log.e("Question ID", "Question ID : " + questionId);
		qController = new QuestionController(this,questionId);
		updateUI();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Sets up listeners for question and answer profile pictures
	 */
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Handles event when the ImageButton up_vote_question has been pressed -
	 * increases score of question by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void upVoteQuestion(View view) {
		//this.textViewQuestionScore.setText(Integer.toString(this.qController
		//		.voteQuestion(1)));
	}

	/**
	 * Handles event when the ImageButton up_vote_question has been pressed -
	 * decreases score of question by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void downVoteQuestion(View view) {
		//this.textViewQuestionScore.setText(Integer.toString(this.qController
		//		.voteQuestion(-1)));
	}

	/**
	 * Handles event when the ImageButton up_vote_answer has been pressed -
	 * increases score of answer by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void upVoteAnswer(View view) {
		//this.textViewAnswerScore.setText(Integer.toString(this.qController
		//		.voteActiveAnswer(1)));
	}

	/**
	 * Handles event when the ImageButton down_vote_answer has been pressed -
	 * decreases score of answer by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void downVoteAnswer(View view) {
		//this.textViewAnswerScore.setText(Integer.toString(this.qController
		//		.voteActiveAnswer(-1)));
	}

	/**
	 * Fills/completes TextViews of activity with dynamic content by the help of
	 * the database
	 */
	private void updateUI() {

		// create header (question view)
		View header = View.inflate(this, R.layout.layout_question_view, null);
		
		// fill question title
		TextView textViewTemp = (TextView) header.findViewById(R.id.question_title);
		textViewTemp.setText(qController.getQuestionTitle());
		
		// fill question content
		textViewTemp = (TextView) header.findViewById(R.id.question_content);
		textViewTemp.setText(Html.fromHtml(qController.getQuestionBody()));
		
		// fill question score
		textViewTemp = (TextView) header.findViewById(R.id.question_score);
		textViewTemp.setText(Integer.toString(qController.getQuestionScore()));
		
		// fill date question was asked at
		textViewTemp = (TextView) header.findViewById(R.id.asked_at);
		textViewTemp.setText(qController.getQuestionDate());
		
		// fill name of asking user
		textViewTemp = (TextView) header.findViewById(R.id.question_user_name);
		textViewTemp.setText(qController.getAuthorName());
		
		// fill score of asking user
		textViewTemp = (TextView) header.findViewById(R.id.question_user_score);
		textViewTemp.setText(Integer.toString(qController.getAuthorReputation()));

		ImageButton questionProfilePicture = (ImageButton)header.findViewById(R.id.question_user_image);
		questionProfilePicture.setOnClickListener(this);
		
		// fill tag buttons
		PredicateLayout questionTagButtons = (PredicateLayout) header.findViewById(R.id.question_tag_buttons);
		// dynamically create new buttons for each tag
		for (final String tagString : qController.getQuestionTags()) {
			Button tagButton = (Button) getLayoutInflater().inflate(R.layout.selected_tag_button, null);
			tagButton.setText(tagString);
	
			// add listener to the button (onClick) that will open the
			// TabSearchActivity using the tag string as input
			tagButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(QuestionActivity.this, TabSearchActivity.class);
				intent.putExtra(TabSearchActivity.EXTRA_TAGSTRING, tagString);
				startActivity(intent);
			}
			});
			questionTagButtons.addView(tagButton);
		}		
		
		// add number of answers
		textViewTemp = (TextView) header.findViewById(R.id.nr_of_answers);
		textViewTemp.setText(Integer.toString(qController.getNrOfAnswers()) + " "
				+ R.string.nr_of_answers);
		
		// set up list view + adapter
		List<Post> answers = qController.getAnswers();
		listViewAnswers = (ListView) findViewById(R.id.answers_list_view);
		final AnswerAdapter adapter = new AnswerAdapter(this,
		        android.R.layout.simple_list_item_1, answers);
		listViewAnswers.addHeaderView(header);
		listViewAnswers.setAdapter(adapter);
	}
	
	//Called when you click the user profile picture, switches to the user profile activity
	public void testUserProfile(int id) {
		Intent intent = new Intent(this, UserProfileActivity.class);
		intent.putExtra(UserProfileActivity.EXTRA_USERID, id);
		startActivity(intent);
	}
	
	//Clears out what profile picture is being pressed
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		  	switch (id) {
		  		case R.id.question_user_image:
		  			testUserProfile(qController.getAuthorId());
		  		break;
		  		case R.id.answer_user_image:
		  			testUserProfile(qController.getAnswerAuthorId());
		  		break;
		  	}
	}
	
	
	// an adapter for the answers list
	private class AnswerAdapter extends ArrayAdapter<Post> {
		
		private List<Post> answers;
		
		public AnswerAdapter(Context context, int resource, List<Post> answers) {
			super (context, resource, answers);
			this.answers = answers;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			View listView = view;
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listView = inflater.inflate(R.layout.layout_answer_view, parent, false);

			ImageButton answerProfilePicture = (ImageButton) listView.findViewById(R.id.answer_user_image);
			Post currentAnswer = answers.get(position);
			
			//answerProfilePicture.setOnClickListener(this);

			// fill answer content
			TextView textViewTemp = (TextView) listView.findViewById(R.id.answer_content);
			textViewTemp.setText(Html.fromHtml(currentAnswer.getBody()));
			
			// fill answer score
			TextView textViewAnswerScore = (TextView) listView.findViewById(R.id.answer_score);
			textViewAnswerScore.setText(Integer.toString(currentAnswer.getScore()));
			
			// fill date question was answered at
			textViewTemp = (TextView) listView.findViewById(R.id.answered_at);
			textViewTemp.setText(currentAnswer.getCreationDate());
			
			// fill name of answering user
			textViewTemp = (TextView) listView.findViewById(R.id.answer_user_name);
			textViewTemp.setText(currentAnswer.getLastEditorDisplayName());
			// TODO: get author name, not last editor's
			
			// fill score of answering user
			textViewTemp = (TextView) listView.findViewById(R.id.answer_user_score);
			textViewTemp.setText(Integer.toString(currentAnswer.getScore()));
			// TODO: get author reputation, not score

			return listView;
		}
	}	
}
