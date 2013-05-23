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
import android.widget.Toast;

import com.yinyang.so.R;
import com.yinyang.so.controllers.QuestionController;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.extras.PredicateLayout;

@SuppressLint("NewApi")
public class QuestionActivity extends ShowMenuActivity implements OnClickListener {

	public final static String EXTRA_QUESTIONID = "com.example.YingYangApp.QUESTIONID";
	private ListView listViewAnswers;
	private View header;
	private static final String TAG = "QuestionActivity";
	
	private QuestionController qController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		//Add navigation drawer with functionality
		addDrawer();
		
		Intent intent = getIntent();
		int questionId = intent.getIntExtra(EXTRA_QUESTIONID, -1);
		
		try{ qController = new QuestionController(this,questionId); }
		catch(NullPointerException e) {		
			Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
			t.show();
			finish();
		}
		
		if(qController != null) {
			updateUI();
		}
	}


	/**
	 * Fills/completes TextViews of activity with dynamic content by the help of
	 * the database
	 * Creates views for the question and answers
	 */
	private void updateUI() {

		// create header (question view)
		header = View.inflate(this, R.layout.layout_question_view, null);
		
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

		// upvote/downvote question
		ImageButton upVoteQuestion = (ImageButton) header.findViewById(R.id.up_vote_question);
		upVoteQuestion.setOnClickListener(this);
		ImageButton downVoteQuestion = (ImageButton) header.findViewById(R.id.down_vote_question);
		downVoteQuestion.setOnClickListener(this);
		
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
				Intent intent = new Intent(QuestionActivity.this, TagSearchActivity.class);
				intent.putExtra(TagSearchActivity.EXTRA_TAGSTRING, tagString);
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
		TextView questionScore = (TextView) header.findViewById(R.id.question_score);
		int id = v.getId();
		  	switch (id) {
		  		case R.id.question_user_image:
		  			testUserProfile(qController.getAuthorId());
		  		break;
		  		case R.id.up_vote_question:
		  			questionScore.setText(Integer.toString(this.qController.voteQuestion(1)));
	  			break;
		  		case R.id.down_vote_question:
		  			questionScore.setText(Integer.toString(this.qController.voteQuestion(-1)));
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

			
			final Post currentAnswer = answers.get(position);

			// add image
			ImageButton answerProfilePicture = (ImageButton) listView.findViewById(R.id.answer_user_image);
			answerProfilePicture.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					testUserProfile(qController.getPostAuthor(currentAnswer).getId());
				}
				});
				
			// add upp and down-voting buttons
			// fill answer score
			final TextView textViewAnswerScore = (TextView) listView.findViewById(R.id.answer_score);
			textViewAnswerScore.setText(Integer.toString(currentAnswer.getScore()));
			
			// upvote/downvote answer
			ImageButton upVoteAnswer = (ImageButton) listView.findViewById(R.id.up_vote_answer);
			upVoteAnswer.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					textViewAnswerScore.setText(Integer.toString(qController.voteAnswer(currentAnswer, 1)));
				}
			});
			ImageButton downVoteAnswer = (ImageButton) listView.findViewById(R.id.down_vote_answer);
			downVoteAnswer.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					textViewAnswerScore.setText(Integer.toString(qController.voteAnswer(currentAnswer, -1)));
				}
			});
			
			// fill answer content
			TextView textViewTemp = (TextView) listView.findViewById(R.id.answer_content);
			textViewTemp.setText(Html.fromHtml(currentAnswer.getBody()));
			
			// fill date question was answered at
			textViewTemp = (TextView) listView.findViewById(R.id.answered_at);
			textViewTemp.setText(currentAnswer.getCreationDate());
			
			// fill name of answering user
			textViewTemp = (TextView) listView.findViewById(R.id.answer_user_name);
			textViewTemp.setText(qController.getPostAuthor(currentAnswer).getDisplayName());
			
			// fill score of answering user
			textViewTemp = (TextView) listView.findViewById(R.id.answer_user_score);
			textViewTemp.setText(Integer.toString(qController.getPostAuthor(currentAnswer).getReputation()));

			return listView;
		}
	}	
}
