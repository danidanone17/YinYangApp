package com.yinyang.so.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yinyang.so.R;
import com.yinyang.so.controllers.QuestionController;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.User;

@SuppressLint("NewApi")
public class QuestionActivity extends Activity implements OnClickListener {

	public final static String EXTRA_QUESTIONID = "com.example.YingYangApp.QUESTIONID";
	private TextView textViewQuestionScore;
	private TextView textViewAnswerScore;
	private QuestionController qController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		// Sets up the profile picture listeners
		setupProfilePictures();
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		int questionId = intent.getIntExtra(EXTRA_QUESTIONID, -1);
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
	
	private void setupProfilePictures() {
		ImageButton questionProfilePicture = (ImageButton)findViewById(R.id.question_user_image);
		questionProfilePicture.setOnClickListener(this);
		
		ImageButton answerProfilePicture = (ImageButton)findViewById(R.id.answer_user_image);
		answerProfilePicture.setOnClickListener(this);
	}

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
		this.textViewQuestionScore.setText(Integer.toString(this.qController
				.voteQuestion(1)));
	}

	/**
	 * Handles event when the ImageButton up_vote_question has been pressed -
	 * decreases score of question by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void downVoteQuestion(View view) {
		this.textViewQuestionScore.setText(Integer.toString(this.qController
				.voteQuestion(-1)));
	}

	/**
	 * Handles event when the ImageButton up_vote_answer has been pressed -
	 * increases score of answer by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void upVoteAnswer(View view) {
		this.textViewAnswerScore.setText(Integer.toString(this.qController
				.voteActiveAnswer(1)));
	}

	/**
	 * Handles event when the ImageButton down_vote_answer has been pressed -
	 * decreases score of answer by 1
	 * 
	 * @param view
	 *            the view that caused the event
	 */
	public void downVoteAnswer(View view) {
		this.textViewAnswerScore.setText(Integer.toString(this.qController
				.voteActiveAnswer(-1)));
	}
	/**
	 * Show the views associated with an answer to the given question
	 */
	private void enableAnswerView(){
		findViewById(R.id.answer_content).setVisibility(View.VISIBLE);
		findViewById(R.id.answer_score).setVisibility(View.VISIBLE);
		findViewById(R.id.answered_at).setVisibility(View.VISIBLE);
		findViewById(R.id.answer_user_name).setVisibility(View.VISIBLE);
		findViewById(R.id.answer_user_score).setVisibility(View.VISIBLE);
		findViewById(R.id.up_vote_answer).setVisibility(View.VISIBLE);
		findViewById(R.id.down_vote_answer).setVisibility(View.VISIBLE);
		findViewById(R.id.answer_user_image).setVisibility(View.VISIBLE);
	}
	/**
	 * Hide the view associated with an answer to the given question
	 */
	private void disableAnswerView(){
		findViewById(R.id.answer_content).setVisibility(View.GONE);
		findViewById(R.id.answer_score).setVisibility(View.GONE);
		findViewById(R.id.answered_at).setVisibility(View.GONE);
		findViewById(R.id.answer_user_name).setVisibility(View.GONE);
		findViewById(R.id.answer_user_score).setVisibility(View.GONE);
		findViewById(R.id.up_vote_answer).setVisibility(View.GONE);
		findViewById(R.id.down_vote_answer).setVisibility(View.GONE);
		findViewById(R.id.answer_user_image).setVisibility(View.GONE);
	}
	
	/**
	 * Fill the views associated with an answer with data
	 */
	private void fillAnswerView() {
		// fill answer content
		TextView textViewTemp = (TextView) findViewById(R.id.answer_content);
		textViewTemp.setText(Html.fromHtml(qController.getAnswerContent()));
		
		// fill answer score
		this.textViewAnswerScore = (TextView) findViewById(R.id.answer_score);
		this.textViewAnswerScore.setText(Integer.toString(qController
				.getAnswerScore()));
		
		// fill date question was answered at
		textViewTemp = (TextView) findViewById(R.id.answered_at);
		textViewTemp.setText(qController.getAnswerDate());
		
		// fill name of answering user
		textViewTemp = (TextView) findViewById(R.id.answer_user_name);
		textViewTemp.setText(qController.getAnswerAuthorName());
		
		// fill score of answering user
		textViewTemp = (TextView) findViewById(R.id.answer_user_score);
		textViewTemp.setText(Integer.toString(qController
				.getAnswerAuthorReputation()));
		
	}

	/**
	 * Fills/completes TextViews of activity with dynamic content by the help of
	 * the database
	 */
	private void updateUI() {

		// fill question title
		TextView textViewTemp = (TextView) findViewById(R.id.question_title);
		textViewTemp.setText(qController.getQuestionTitle());
		
		// fill question content
		textViewTemp = (TextView) findViewById(R.id.question_content);
		textViewTemp.setText(Html.fromHtml(qController.getQuestionBody()));
		
		// fill question score
		textViewQuestionScore = (TextView) findViewById(R.id.question_score);
		textViewQuestionScore.setText(Integer.toString(qController
				.getQuestionScore()));
		
		// fill date question was asked at
		textViewTemp = (TextView) findViewById(R.id.asked_at);
		textViewTemp.setText(qController.getQuestionDate());
		
		// fill name of asking user
		textViewTemp = (TextView) findViewById(R.id.question_user_name);
		textViewTemp.setText(qController.getAuthorName());
		
		// fill score of asking user
		textViewTemp = (TextView) findViewById(R.id.question_user_score);
		textViewTemp.setText(Integer.toString(qController.getAuthorReputation()));
		
		// add number of answers
		textViewTemp = (TextView) findViewById(R.id.nr_of_answers);
		textViewTemp.setText(Integer.toString(qController.getNrOfAnswers()) + " "
				+ R.string.nr_of_answers);
		
		if (qController.existsAnswer()) {
			enableAnswerView();
			fillAnswerView();
		} else {
			disableAnswerView();
		}
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
}
