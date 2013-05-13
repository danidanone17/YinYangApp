package com.yinyang.so.test;

import java.util.ArrayList;
import java.util.Iterator;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.MeanOfSearch;
import com.yinyang.so.database.SearchEntity;
import com.yinyang.so.database.TableType;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;
import com.yinyang.so.databaseentities.User;

public class DatabaseAdapterValidation extends
		android.test.InstrumentationTestCase {

	private DatabaseAdapter db;

	protected void setUp() throws Exception {
		super.setUp();
		db = new DatabaseAdapter(this.getInstrumentation().getTargetContext()
				.getApplicationContext());
		db.createDatabase();
		db.open();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		db.close();
	}

	// get posts from getDataByCriteria
	public void testGetDataByCriteria() {
		// TODO: insert data into database
		// TODO: test if data inserted
	}

	// tests the getCountByCriteria() method, searches for java in post body
	// fails if under 1 entry found
	public void testGetCountByCriteria() {
		SearchEntity se = new SearchEntity(Post.KEY_BODY, "java",
				MeanOfSearch.contained);
		ArrayList<SearchEntity> searchEntities = new ArrayList<SearchEntity>();
		searchEntities.add(se);
		int index = db.getCountByCriteria(TableType.posts, searchEntities);
		assertTrue(index > 0);
	}

	// tests the getLastIndex() and getFirstIndex() methods
	// comparing the result between them
	public void testGetFirstLastIndex() {
		int lastIndex = db.getLastIndex(TableType.posts);
		int firstIndex = db.getFirstIndex(TableType.posts);
		assertTrue(firstIndex < lastIndex);
	}

	/**
	 * Tests:
	 * -that the posts fetched are indeed created by the user
	 * -that the right number of questions is fetched
	 * -that the post is in descending order
	 */
	public void testGetQuestionByUser() {
		//Picked from the database
		int[] userList = {267, 5334, 210114, 483040, 778183, 1029908, 1085934};
		
		//Test for each user
		for (int userId : userList) {
			User user = db.getUser(userId);
			if (user != null) {
				ArrayList<SearchEntity> criteria = new ArrayList<SearchEntity>();
				criteria.add(new SearchEntity(Post.KEY_OWNER_USER_ID, 
						Integer.toString(userId), MeanOfSearch.exact));
				criteria.add(new SearchEntity(Post.KEY_POST_TYPE_ID, "1",
						MeanOfSearch.exact));
				ArrayList<Post> postsByCriteria = db.getPostsByCriteria(criteria);
				ArrayList<Post> postsByQuestionByUser = db.getQuestionsByUser(userId);
				// check that both way of fetching information gives the same
				// number of results
				// Should only return the first 100 questions
				assertTrue(postsByCriteria.size() > 100
						|| postsByCriteria.size() == postsByQuestionByUser
								.size());
				for (Post post : postsByQuestionByUser) {
					// check that each post actually have the right creator
					assertTrue(post.getOwnerUserId() == userId);
				}
				
				//Test that the posts is presented in descending order
				for (int i=1; i < postsByQuestionByUser.size(); i++){
					assertTrue(postsByQuestionByUser.get(i-1).getScore() >= postsByQuestionByUser.get(i).getScore());
				}
			}
		}

	}
	
	public void testDesc(){
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("php");
		String[] searchWords = {"php"}; 
		ArrayList<Post> posts = db.getQuestionsByFreeTextAndTags(searchWords, tags);
		
		
		for (int i = 1; i < posts.size(); i++){
			assertTrue(posts.get(i-1).getScore() >= posts.get(i).getScore());
		}	
	}
	
	/** test for searchName = null, lastUserReputation = -1, lastUserName = "" and limit = 2
	 * in order to get the top 2 users
	 */
	public void testGetUsersOrderedByReputation1(){
		int [] expectedUserIds = {22656, 29407};
		int [] resultedUserIds = new int[100];
		int i=0;
		ArrayList<User> users = db.getUsersOrderedByReputation(2, "", -1, "");
		
		for (User user : users) {
			resultedUserIds[i] = user.getId();
			i++;
		}
		
		for (i = 0; i < expectedUserIds.length; i++) {
			assertTrue(expectedUserIds[i]==resultedUserIds[i]);
		}
	}
	
	/** test for searchName = null, lastUserReputation = 343191, lastUserName = "Darin Dimitrov" and limit = 2
	 * in order to get the top 2 users
	 */
	public void testGetUsersOrderedByReputation2(){
		int [] expectedUserIds = {23354, 157882};
		int [] resultedUserIds = new int[2];
		int i=0;
		ArrayList<User> users = db.getUsersOrderedByReputation(2, "", 343191, "Darin Dimitrov");
		
		for (User user : users) {
			resultedUserIds[i] = user.getId();
			i++;
		}
		
		for (i = 0; i < expectedUserIds.length; i++) {
			assertTrue(expectedUserIds[i]==resultedUserIds[i]);
		}
	}
	
	/** test for searchName = "Darin Dimitrov", lastUserReputation = -1, lastUserName = "" and limit = 1
	 * in order to get the top 2 users
	 */
	public void testGetUsersOrderedByReputation3(){
		int [] expectedUserIds = {29407};
		int [] resultedUserIds = new int[1];
		int i=0;
		ArrayList<User> users = db.getUsersOrderedByReputation(1, "Darin Dimitrov", -1, "");
		
		for (User user : users) {
			resultedUserIds[i] = user.getId();
			i++;
		}
		
		for (i = 0; i < expectedUserIds.length; i++) {
			assertTrue(expectedUserIds[i]==resultedUserIds[i]);
		}
	}
	
	/**
	 * test the getTagObjectByName(String tagName) method in DatabaseAdapter
	 */
	public void testGetTagObjectByName(){
		int expectedTagId = 54;
		
		Tag resultedTag= db.getTagObjectByName("java");
		
		assertEquals(expectedTagId, resultedTag.getId());
		
	}
	
	/**
	 * test the getTagsInAlphabeticalOrder(int iLimit) method in DatabaseAdapter
	 */
	//not done here yet, small error, talk to Dani
	public void testGetTagsInAlphabeticalOrder(){
		String [] expectedTags = {".htaccess", ".net", ".net-2.0"};
		int i=0;
		
		ArrayList<String> resultedTags = db.getTagsInAlphabeticalOrder(3);
		
		for (String resultedTag : resultedTags) {
			assertEquals(expectedTags[i], resultedTag);
			i++;
		}
	}
}
