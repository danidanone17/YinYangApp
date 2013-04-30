/**
 * 
 */
package com.example.yinyangapp.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.example.yinyangapp.database.DatabaseAdapter;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.User;

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
	public ArrayList<DatabaseType> getAnswers(int questionId) {
		dbAdapter.open();
		ArrayList<DatabaseType> answers = dbAdapter.getAnswers(questionId);
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

}
