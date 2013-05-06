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

	/**
	 * Communicates with the database through a DatabaseAdapter Fetches
	 * information for the question model
	 */
	public QuestionController(Context con) {
		dbAdapter = new DatabaseAdapter(con);
		dbAdapter.createDatabase();
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

}
