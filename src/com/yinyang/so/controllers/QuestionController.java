/**
 * 
 */
package com.yinyang.so.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.User;

public class QuestionController {
	private DatabaseAdapter dbAdapter;
	private Post question;
	private User questionAuthor;
	private User answerAuthor;
	private Post activeAnswer;
	private int questionScore = 0;
	private int answerScore = 0;
	private ArrayList<Post> answers = new ArrayList<Post>();
	
	
	/**
	 * Communicates with the database through a DatabaseAdapter Fetches
	 * information for the question model
	 */
	public QuestionController(Context con, int questionId) {
		dbAdapter = new DatabaseAdapter(con);
		dbAdapter.createDatabase();
		
		question = this.getPost(questionId);
		questionScore = question.getScore();
		fetchAnswer();
		fetchAuthors();
		fetchAnswers();		
		
	}

	/**
	 * Fetches a post from the database based on its id Opens and closes the
	 * database
	 * 
	 * @param id
	 *            an int representing the id for a post
	 * @return a Post
	 */
	public Post getPost(int id) {
		dbAdapter.open();
		Post post = dbAdapter.getPost(id);
		dbAdapter.close();
		return post;
	}

	/**
	 * Fetches a user from the database based on its id Opens and closes the
	 * database
	 * 
	 * @param id
	 *            an int representing the user id
	 * @return a User
	 */
	public User getUser(int id) {
		dbAdapter.open();
		User user = dbAdapter.getUser(id);
		dbAdapter.close();
		return user;
	}

	/**
	 * Fetches all answers to a question from the database based on the question
	 * id Opens and closes the database
	 * 
	 * @param questionId
	 *            an int representing the question id
	 * @return an ArrayList with Posts (DatabaseType)
	 */
	public ArrayList<Post> getAnswers(int questionId) {
		dbAdapter.open();
		ArrayList<Post> answers = dbAdapter.getAnswers(questionId);
		dbAdapter.close();
		return answers;
	}

	/**
	 * Fetches the accepted answer to a question from the database based on the
	 * answer id Opens and closes the database
	 * 
	 * @param answerId
	 *            an int representing the id of the post
	 * @return a Post
	 */
	public Post getAcceptedAnswer(int answerId) {
		dbAdapter.open();
		Post post = dbAdapter.getPost(answerId);
		dbAdapter.close();
		return post;
	}
	
	/**
	 * Updates the score of a post
	 * @param newScore new score
	 * @param postId post id
	 */
	public void updatePostScore(int newScore, int postId){
		dbAdapter.open();
		
		HashMap<String, String> columnValues = new HashMap<String, String>();
		columnValues.put(Post.KEY_SCORE, Integer.toString(newScore));
		String whereClause = Post.KEY_ID + " = " + postId;
		
		dbAdapter.updateSql(Post.TABLE_NAME, columnValues, whereClause);
	}

	/**
	 * Get the author/user from the database
	 */
	private void fetchAuthors() {
		questionAuthor = this.getUser(question.getOwnerUserId());
		if (activeAnswer != null)
			answerAuthor = this.getUser(activeAnswer.getOwnerUserId());
	}

	/**
	 * Get the accepted answer to a question from the database
	 */
	private void fetchAnswer() {
		int id = question.getAcceptedAnswerId();
		if (id > 0) {
			activeAnswer = this.getAcceptedAnswer(id);
			answerScore = activeAnswer.getScore();
		}
	}

	/**
	 * Get all answers to a question from the database
	 */
	private void fetchAnswers() {
		answers = this.getAnswers(question.getId());
	}

	/**
	 * Change the value of the current question score
	 * 
	 * @param i
	 *            change the score by this value
	 * @return an int representing the updated score
	 */
	public int voteQuestion(int i) {
		questionScore = questionScore + i;
		this.updatePostScore(questionScore, question.getId());
		return questionScore;
	}

	/**
	 * Change the value of the current answer score
	 * 
	 * @param i
	 *            change the score by this value
	 * @return an int representing the updated score
	 */
	public int voteActiveAnswer(int i) {
		answerScore = answerScore + i;
		this.updatePostScore(answerScore, activeAnswer.getId());
		return answerScore;
	}

	/**
	 * Get the question title
	 * 
	 * @return a String
	 */
	public String getQuestionTitle() {
		return question.getTitle();
	}

	/**
	 * Get the Question content/body
	 * 
	 * @return a String
	 */
	public String getQuestionBody() {
		return question.getBody();
	}

	/**
	 * Get the question score
	 * 
	 * @return an int
	 */
	public int getQuestionScore() {
		return questionScore;
	}

	/**
	 * Get the published date for the question
	 * 
	 * @return a String
	 */
	public String getQuestionDate() {
		return question.getCreationDate();
	}

	/**
	 * Get the question tags
	 * 
	 * @return a String
	 */
	public String[] getQuestionTags() {
		return question.getTags();
	}
	
	/**
	 * Get the authors name
	 * 
	 * @return a String
	 */
	public String getAuthorName() {
		return questionAuthor.getDisplayName();
	}

	/**
	 * Get the authors reputation
	 * 
	 * @return an int
	 */
	public int getAuthorReputation() {
		return questionAuthor.getReputation();
	}

	/**
	 * Get the number of given answers to a question
	 * 
	 * @return an int
	 */
	public int getNrOfAnswers() {
		return question.getAnswerCount();
	}

	/**
	 * Get he content to the accepted answer
	 * 
	 * @return a String
	 */
	public String getAnswerContent() {
		return activeAnswer.getBody();
	}

	/**
	 * Get the score for the accepted answer
	 * 
	 * @return an int
	 */
	public int getAnswerScore() {
		return activeAnswer.getScore();
	}

	/**
	 * Get the date when the answer were published
	 * 
	 * @return
	 */
	public String getAnswerDate() {
		return activeAnswer.getCreationDate();
	}

	/**
	 * Get the name of the author of the answer
	 * 
	 * @return
	 */
	public String getAnswerAuthorName() {
		return answerAuthor.getDisplayName();
	}

	/**
	 * Get teh reputation of the author of the answer
	 * 
	 * @return
	 */
	public int getAnswerAuthorReputation() {
		return answerAuthor.getReputation();
	}

	/**
	 * Does an answer exists. True if there is an accepted answer, otherwise
	 * false.
	 * 
	 * @return Boolean
	 */
	public Boolean existsAnswer() {
		if (activeAnswer == null)
			return false;
		else
			return true;
	}	
}
