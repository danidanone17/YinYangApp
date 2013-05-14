package com.yinyang.so.test;

import java.util.ArrayList;

import junit.framework.Assert;

import com.yinyang.so.controllers.QuestionController;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.Post;

public class QuestionControllerValidation extends 
	android.test.InstrumentationTestCase {

	private QuestionController qc;
	
	protected void setUp() throws Exception {
	    super.setUp();
	    qc = new QuestionController(this.getInstrumentation().getTargetContext().
	    		getApplicationContext());
	}
	
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
	// Tests getPosts()
	// Note: HARDCODED VALUES... If this test fails,
	// it may depend on changes in the db...
	// TODO: Make it independent
	public void testGetPost() {
		assertEquals(qc.getPost(8414075).getAcceptedAnswerId(), 8414247);
		assertEquals(qc.getPost(8414075).getViewCount(), 65);
		assertEquals(qc.getPost(8414075).getAnswerCount(), 2);
	}
	
	// Tests getUser()
	// Note: HARDCODED VALUES... If this test fails,
	// it may depend on changes in the db...
	// TODO: Make it independent
	public void testGetUser() {
		assertEquals(qc.getUser(13).getAge(), 32);
		assertEquals(qc.getUser(13).getReputation(), 63611);
		assertEquals(qc.getUser(13).getViews(), 3347);
	}
	
	// Tests getAcceptedAnswer()
	// Note: HARDCODED VALUES... If this test fails,
	// it may depend on changes in the db...
	// TODO: Make it independent
	public void testGetAcceptedAnswer() {
		assertEquals(qc.getAcceptedAnswer(8414075).getId(), 8414247);
	}
	
	/**
	 * Tests up-voting a post
	 */
	public void testUpVotePost(){
		int postId = 386341;
		Post post = qc.getPost(postId);
		
		int newScore = post.getScore() + 1;
		qc.updatePostScore(newScore, postId);
		
		post = qc.getPost(postId);
		Assert.assertEquals(newScore, post.getScore());
	}
	
	/**
	 * Tests down-voting a post
	 */
	public void testDownVotePost(){
		int postId = 386341;
		Post post = qc.getPost(postId);
		
		int newScore = post.getScore() - 1;
		qc.updatePostScore(newScore, postId);
		
		post = qc.getPost(postId);
		Assert.assertEquals(newScore, post.getScore());
	}

}
