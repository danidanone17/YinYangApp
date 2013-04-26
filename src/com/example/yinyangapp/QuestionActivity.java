package com.example.yinyangapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.yinyangapp.controller.QuestionController;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.User;

public class QuestionActivity extends Activity {

	public final static String EXTRA_QUESTIONID = "com.example.YingYangApp.QUESTIONID";
	private TextView textViewQuestionScore;
	private TextView textViewAnswerScore;
	private QuestionController qController;
	private QuestionModel question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		int questionId = intent.getIntExtra(EXTRA_QUESTIONID, -1);
		qController = new QuestionController(this);
		question = new QuestionModel(qController, questionId);
		updateUI();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
		this.textViewQuestionScore.setText(Integer.toString(this.question
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
		this.textViewQuestionScore.setText(Integer.toString(this.question
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
		this.textViewAnswerScore.setText(Integer.toString(this.question
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
		this.textViewAnswerScore.setText(Integer.toString(this.question
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
	 * Hide the view associated with an aswer to the given question
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
		textViewTemp.setText(Html.fromHtml(question.getAnswerContent()));
		
		// fill answer score
		this.textViewAnswerScore = (TextView) findViewById(R.id.answer_score);
		this.textViewAnswerScore.setText(Integer.toString(question
				.getAnswerScore()));
		
		// fill date question was answered at
		textViewTemp = (TextView) findViewById(R.id.answered_at);
		textViewTemp.setText(question.getAnswerDate());
		
		// fill name of answering user
		textViewTemp = (TextView) findViewById(R.id.answer_user_name);
		textViewTemp.setText(question.getAnswerAuthorName());
		
		// fill score of answering user
		textViewTemp = (TextView) findViewById(R.id.answer_user_score);
		textViewTemp.setText(Integer.toString(question
				.getAnswerAuthorReputation()));
		
	}

	/**
	 * Fills/completes TextViews of activity with dynamic content by the help of
	 * the database
	 */
	private void updateUI() {

		// fill question title
		TextView textViewTemp = (TextView) findViewById(R.id.question_title);
		textViewTemp.setText(question.getQuestionTitle());
		
		// fill question content
		textViewTemp = (TextView) findViewById(R.id.question_content);
		textViewTemp.setText(Html.fromHtml(question.getQuestionBody()));
		
		// fill question score
		textViewQuestionScore = (TextView) findViewById(R.id.question_score);
		textViewQuestionScore.setText(Integer.toString(question
				.getQuestionScore()));
		
		// fill date question was asked at
		textViewTemp = (TextView) findViewById(R.id.asked_at);
		textViewTemp.setText(question.getQuestionDate());
		
		// fill name of asking user
		textViewTemp = (TextView) findViewById(R.id.question_user_name);
		textViewTemp.setText(question.getAuthorName());
		
		// fill score of asking user
		textViewTemp = (TextView) findViewById(R.id.question_user_score);
		textViewTemp.setText(Integer.toString(question.getAuthorReputation()));
		
		// add number of answers
		textViewTemp = (TextView) findViewById(R.id.nr_of_answers);
		textViewTemp.setText(Integer.toString(question.getNrOfAnswers()) + " "
				+ R.string.nr_of_answers);
		
		if (question.existsAnswer()) {
			enableAnswerView();
			fillAnswerView();
		} else {
			disableAnswerView();
		}
	}

}
