package com.example.yinyangapp;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	private TextView textViewQuestionScore;
	private TextView textViewAnswerScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		this.completeUIByDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}
	
	/**
	 * Handles event when the ImageButton up_vote_question has been pressed
	 * - increases score of question by 1
	 * @param view the view that caused the event
	 */
	public void upVoteQuestion(View view) {
	    String sScore = this.textViewQuestionScore.getText().toString();
	    Integer iScore = Integer.parseInt(sScore);
	    iScore = iScore + 1;
	    this.textViewQuestionScore.setText(iScore.toString());
	}
	
	/**
	 * Handles event when the ImageButton up_vote_question has been pressed
	 * - decreases score of question by 1
	 * @param view the view that caused the event
	 */
	public void downVoteQuestion(View view) {
	    String sScore = this.textViewQuestionScore.getText().toString();
	    Integer iScore = Integer.parseInt(sScore);
	    iScore = iScore - 1;
	    this.textViewQuestionScore.setText(iScore.toString());
	}
	
	/**
	 * Handles event when the ImageButton up_vote_answer has been pressed
	 * - increases score of answer by 1
	 * @param view the view that caused the event
	 */
	public void upVoteAnswer(View view) {
	    String sScore = this.textViewAnswerScore.getText().toString();
	    Integer iScore = Integer.parseInt(sScore);
	    iScore = iScore + 1;
	    this.textViewAnswerScore.setText(iScore.toString());
	}
	
	/**
	 * Handles event when the ImageButton down_vote_answer has been pressed
	 * - decreases score of answer by 1
	 * @param view the view that caused the event
	 */
	public void downVoteAnswer(View view) {
	    String sScore = this.textViewAnswerScore.getText().toString();
	    Integer iScore = Integer.parseInt(sScore);
	    iScore = iScore - 1;
	    this.textViewAnswerScore.setText(iScore.toString());
	}
	
	/**
	 * Fills/completes TextViews of activity with dynamic content by the help of the database
	 */
	private void completeUIByDatabase() {		
		// fill question title
		TextView textViewTemp = (TextView) findViewById(R.id.question_title);
		textViewTemp.setText("Question title");		
		
		// fill question content
		textViewTemp = (TextView) findViewById(R.id.question_content);
		textViewTemp.setText("Question content");
		
		// fill question score
		this.textViewQuestionScore = (TextView) findViewById(R.id.question_score);
		this.textViewQuestionScore.setText("0");
		
		// fill date question was asked at
		textViewTemp = (TextView) findViewById(R.id.asked_at);
		textViewTemp.setText("Date");
		
		// fill name of asking user
		textViewTemp = (TextView) findViewById(R.id.question_user_name);
		textViewTemp.setText("User");
		
		// fill score of asking user
		textViewTemp = (TextView) findViewById(R.id.question_user_score);
		textViewTemp.setText("0");
		
		// add number of answers
		textViewTemp = (TextView) findViewById(R.id.nr_of_answers);
		textViewTemp.setText("1 " + textViewTemp.getText());
		
		// fill answer content
		textViewTemp = (TextView) findViewById(R.id.answer_content);
		textViewTemp.setText("Answer content");
		
		// fill answer score
		this.textViewAnswerScore = (TextView) findViewById(R.id.answer_score);
		this.textViewAnswerScore.setText("0");
		
		// fill date question was answered at
		textViewTemp = (TextView) findViewById(R.id.answered_at);
		textViewTemp.setText("Date");
		
		// fill name of answering user
		textViewTemp = (TextView) findViewById(R.id.answer_user_name);
		textViewTemp.setText("User");
		
		// fill score of answering user
		textViewTemp = (TextView) findViewById(R.id.answer_user_score);
		textViewTemp.setText("0");
	}

}
