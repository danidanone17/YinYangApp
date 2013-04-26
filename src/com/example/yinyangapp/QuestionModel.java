/**
 * 
 */
package com.example.yinyangapp;

import java.util.ArrayList;

import android.util.Log;

import com.example.yinyangapp.controller.QuestionController;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.User;

/**
 * @author Fredrik
 * 
 */
public class QuestionModel {

	private QuestionController qController;
	private Post question;
	private User questionAuthor;
	private User answerAuthor;
	private Post activeAnswer;
	private int questionScore = 0;
	private int answerScore = 0;
	private ArrayList<DatabaseType> answers;

	/**
	 * Creates a model of a question with answers and author information
	 */
	public QuestionModel(QuestionController controller, int questionId) {
		qController = controller;
		question = qController.getPost(questionId);
		questionScore = question.getScore();
		fetchAnswer();
		fetchAuthors();
		fetchAnswers();
	}

	/**
	 * Get the author/user from the database
	 */
	private void fetchAuthors() {
		questionAuthor = qController.getUser(question.getOwnerUserId());
		if (activeAnswer != null)
			answerAuthor = qController.getUser(activeAnswer.getOwnerUserId());
	}

	/**
	 * Get the accepted answer to a question from the database
	 */
	private void fetchAnswer() {
		int id = question.getAcceptedAnswerId();
		if (id > 0) {
			activeAnswer = qController.getAcceptedAnswer(id);
			answerScore = activeAnswer.getScore();
		}
	}

	/**
	 * Get all answers to a question from the database
	 */
	private void fetchAnswers() {
		answers = qController.getAnswers(question.getId());
	}

	/**
	 * Change the value of the current question score
	 * 
	 * @param i
	 *            change the score by this value
	 * @return an int representing the updated score
	 */
	public int voteQuestion(int i) {
		return questionScore = questionScore + i;
	}

	/**
	 * Change the value of the current answer score
	 * 
	 * @param i
	 *            change the score by this value
	 * @return an int representing the updated score
	 */
	public int voteActiveAnswer(int i) {
		return answerScore = answerScore + i;
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
