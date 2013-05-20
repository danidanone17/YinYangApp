package com.yinyang.so.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.yinyang.so.database.DatabaseAdapter;
import com.yinyang.so.database.MeanOfSearch;
import com.yinyang.so.database.SearchEntity;
import com.yinyang.so.database.TableType;
import com.yinyang.so.database.DatabaseAdapter.SearchResultSortingAlgorithm;
import com.yinyang.so.databaseentities.Comment;
import com.yinyang.so.databaseentities.MapTags;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;
import com.yinyang.so.databaseentities.User;
import com.yinyang.so.databaseentities.Vote;

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
	 * Tests: -that the posts fetched are indeed created by the user -that the
	 * right number of questions is fetched -that the post is in descending
	 * order
	 */
	public void testGetQuestionByUser() {
		// Picked from the database
		int[] userList = { 267, 5334, 210114, 483040, 778183, 1029908, 1085934 };

		// Test for each user
		for (int userId : userList) {
			User user = db.getUser(userId);
			if (user != null) {
				ArrayList<SearchEntity> criteria = new ArrayList<SearchEntity>();
				criteria.add(new SearchEntity(Post.KEY_OWNER_USER_ID, Integer
						.toString(userId), MeanOfSearch.exact));
				criteria.add(new SearchEntity(Post.KEY_POST_TYPE_ID, "1",
						MeanOfSearch.exact));
				ArrayList<Post> postsByCriteria = db
						.getPostsByCriteria(criteria);
				ArrayList<Post> postsByQuestionByUser = db
						.getQuestionsByUser(userId);
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

				// Test that the posts is presented in descending order
				for (int i = 1; i < postsByQuestionByUser.size(); i++) {
					assertTrue(postsByQuestionByUser.get(i - 1).getScore() >= postsByQuestionByUser
							.get(i).getScore());
				}
			}
		}

	}

	public void testDesc() {
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("php");
		String[] searchWords = { "php" };
		ArrayList<Post> posts = db.getQuestionsByFreeTextAndTags(searchWords,
				tags, SearchResultSortingAlgorithm.QuestionScoreAlgorithm);

		for (int i = 1; i < posts.size(); i++) {
			assertTrue(posts.get(i - 1).getScore() >= posts.get(i).getScore());
		}
	}

	/**
	 * test for searchName = null, lastUserReputation = -1, lastUserName = ""
	 * and limit = 2 in order to get the top 2 users
	 */
	public void testGetUsersOrderedByReputation1() {
		int[] expectedUserIds = { 22656, 29407 };
		int[] resultedUserIds = new int[100];
		int i = 0;
		ArrayList<User> users = db.getUsersOrderedByReputation(2, "", -1, "");

		for (User user : users) {
			resultedUserIds[i] = user.getId();
			i++;
		}

		for (i = 0; i < expectedUserIds.length; i++) {
			assertTrue(expectedUserIds[i] == resultedUserIds[i]);
		}
	}

	/**
	 * test for searchName = null, lastUserReputation = 343191, lastUserName =
	 * "Darin Dimitrov" and limit = 2 in order to get the top 2 users
	 */
	public void testGetUsersOrderedByReputation2() {
		int[] expectedUserIds = { 23354, 157882 };
		int[] resultedUserIds = new int[2];
		int i = 0;
		ArrayList<User> users = db.getUsersOrderedByReputation(2, "", 343191,
				"Darin Dimitrov");

		for (User user : users) {
			resultedUserIds[i] = user.getId();
			i++;
		}

		for (i = 0; i < expectedUserIds.length; i++) {
			assertTrue(expectedUserIds[i] == resultedUserIds[i]);
		}
	}

	/**
	 * test for searchName = "Darin Dimitrov", lastUserReputation = -1,
	 * lastUserName = "" and limit = 1 in order to get the top 2 users
	 */
	public void testGetUsersOrderedByReputation3() {
		int[] expectedUserIds = { 29407 };
		int[] resultedUserIds = new int[1];
		int i = 0;
		ArrayList<User> users = db.getUsersOrderedByReputation(1,
				"Darin Dimitrov", -1, "");

		for (User user : users) {
			resultedUserIds[i] = user.getId();
			i++;
		}

		for (i = 0; i < expectedUserIds.length; i++) {
			assertTrue(expectedUserIds[i] == resultedUserIds[i]);
		}
	}

	/**
	 * test the getTagObjectByName(String tagName) method in DatabaseAdapter
	 */
	public void testGetTagObjectByName() {
		int expectedTagId = 54;

		Tag resultedTag = db.getTagObjectByName("java");

		assertEquals(expectedTagId, resultedTag.getId());

	}

	/**
	 * test the getTagsInAlphabeticalOrder(int iLimit) method in DatabaseAdapter
	 */
	public void testGetTagsInAlphabeticalOrder() {
		String[] expectedTags = { ".htaccess", ".net", ".net-2.0" };
		int i = 0;

		ArrayList<String> resultedTags = db.getTagsInAlphabeticalOrder(3);

		for (String resultedTag : resultedTags) {
			assertEquals(expectedTags[i], resultedTag);
			i++;
		}
	}

	/**
	 * test updateSql for table post
	 */
	public void testUpdateSqlPost() {
		int postId = 8414075;
		String tableName = Post.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		columnValues.put(Post.KEY_TITLE, "Updated Title");
		String whereClause = Post.KEY_ID + " = " + postId;

		boolean result = db.updateSql(tableName, columnValues, whereClause);
		assertEquals(true, result);
	}

	/**
	 * test updateSql for table comments
	 */
	public void testUpdateSqlComments() {
		int commentId = 8894930;
		String tableName = Comment.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		columnValues.put(Comment.KEY_SCORE, "3");
		String whereClause = Comment.KEY_ID + " = " + commentId;

		boolean result = db.updateSql(tableName, columnValues, whereClause);
		assertEquals(true, result);
	}

	/**
	 * test updateSql for table users
	 */
	public void testUpdateSqlUsers() {
		int userId = 13;
		String tableName = User.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		columnValues.put(User.KEY_AGE, "35");
		String whereClause = User.KEY_ID + " = " + userId;

		boolean result = db.updateSql(tableName, columnValues, whereClause);
		assertEquals(true, result);
	}

	/**
	 * test updateSql for table votes
	 */
	public void testUpdateSqlVotes() {
		int voteId = 1050314;
		String tableName = Vote.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		columnValues.put(Vote.KEY_CREATION_DATE, "2009-12-23");
		String whereClause = Vote.KEY_ID + " = " + voteId;

		boolean result = db.updateSql(tableName, columnValues, whereClause);
		assertEquals(true, result);
	}

	/**
	 * test updateSql for table mapping_tags
	 */
	public void testUpdateSqlMapTags() {
		int mapTagId = 3;
		String tableName = MapTags.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		columnValues.put(MapTags.KEY_COUNT_APPEARANCE, "4");
		String whereClause = MapTags.KEY_ID + " = " + mapTagId;

		boolean result = db.updateSql(tableName, columnValues, whereClause);
		assertEquals(true, result);
	}

	/**
	 * test updateSql for table tags
	 */
	public void testUpdateSqlTags() {
		int tagId = 7;
		String tableName = Tag.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		columnValues.put(Tag.KEY_COUNT_APPEARANCE, "5");
		String whereClause = Tag.KEY_ID + " = " + tagId;

		boolean result = db.updateSql(tableName, columnValues, whereClause);
		assertEquals(true, result);
	}

	/**
	 * test insertSql for table users
	 * 
	 * @throws NoSuchAlgorithmException
	 */

	public void testInsertSqlUsers() throws NoSuchAlgorithmException {
		String tableName = User.TABLE_NAME;
		HashMap<String, String> columnValues = new HashMap<String, String>();

		// encode the email
		String email = "myEmail@gmail.com";
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(email.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashEmail = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashEmail.length() < 32) {
			hashEmail = "0" + hashEmail;
		}

		columnValues.put(User.KEY_ABOUT_ME, "Some description");
		columnValues.put(User.KEY_AGE, "23");
		columnValues.put(User.KEY_CREATION_DATE, "2013-05-14");
		columnValues.put(User.KEY_DISPLAY_NAME, "My name");
		columnValues.put(User.KEY_DOWN_VOTES, "0");
		columnValues.put(User.KEY_EMAIL_HASH, hashEmail);
		columnValues.put(User.KEY_LAST_ACCESS_DATE, "2013-05-14");
		columnValues.put(User.KEY_LOCATION, "Goteburg");
		columnValues.put(User.KEY_REPUTATION, "0");
		columnValues.put(User.KEY_UP_VOTES, "0");
		columnValues.put(User.KEY_VIEWS, "0");
		columnValues.put(User.KEY_WEBSITE_URL, "www.mywebsite.org");

		boolean result = db.insertSql(tableName, columnValues);

		assertEquals(true, result);
	}

	/**
	 * test the getTag(int tagId) method when tagId exists
	 */
	public void testGetTag1() {
		int tagId = 10;
		String expectedResult = "browser";

		String actualResult = db.getTag(tagId);

		assertEquals(expectedResult, actualResult);
	}

	/**
	 * test the getTag(int tagId) method when tagId does not exist
	 */
	public void testGetTag2() {
		int tagId = 100000;
		String expectedResult = null;

		String actualResult = db.getTag(tagId);

		assertEquals(expectedResult, actualResult);
	}

	/**
	 * test ArrayList<Post> getQuestionsByUser(int userId)
	 */
	public void testGetQuestionsByUser() {
		int userId = 769366;
		int[] expectedPostIds = { 8414075 };

		ArrayList<Post> posts = db.getQuestionsByUser(userId);

		for (int i = 0; i < expectedPostIds.length; i++) {
			assertEquals(expectedPostIds[i], posts.get(i).getId());
		}
	}

	/**
	 * test ArrayList<Post> getQuestionsByFreeTextAndTags(String[] oWords,
	 * ArrayList<String> oTags) when oWords and oTags are not empty
	 */
	public void testGetQuestionByFreeTextAndTags1() {
		boolean foundPostId;
		String[] oWords = { "concatenate", "string" };
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("java");

		int[] expectedPostIds = { 8415791 };

		ArrayList<Post> resultedPosts = db.getQuestionsByFreeTextAndTags(
				oWords, oTags,
				SearchResultSortingAlgorithm.QuestionScoreAlgorithm);

		for (Post post : resultedPosts) {
			foundPostId = false;
			for (int i = 0; i < expectedPostIds.length; i++) {
				if (expectedPostIds[i] == post.getId()) {
					foundPostId = true;
				}
			}
			assertEquals(true, foundPostId);
		}
	}

	/**
	 * test ArrayList<Post> getQuestionsByFreeTextAndTags(String[] oWords,
	 * ArrayList<String> oTags) when oWords is not empty and oTags is empty
	 */
	public void testGetQuestionByFreeTextAndTags2() {
		boolean foundPostId;
		String[] oWords = { "concatenate", "string" };
		ArrayList<String> oTags = new ArrayList<String>();

		int[] expectedPostIds = { 8415791, 8469591, 8471301, 8471782, 8472413 };

		ArrayList<Post> resultedPosts = db.getQuestionsByFreeTextAndTags(
				oWords, oTags,
				SearchResultSortingAlgorithm.QuestionScoreAlgorithm);

		for (Post post : resultedPosts) {
			foundPostId = false;
			for (int i = 0; i < expectedPostIds.length; i++) {
				if (expectedPostIds[i] == post.getId()) {
					foundPostId = true;
				}
			}
			assertEquals(true, foundPostId);
		}
	}

	/**
	 * test ArrayList<Post> getQuestionsByFreeTextAndTags(String[] oWords,
	 * ArrayList<String> oTags) when oWords is empty and oTags is not empty
	 */
	public void testGetQuestionByFreeTextAndTags3() {
		boolean foundPostId;
		String[] oWords = {};
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("java");
		oTags.add("mvc");
		oTags.add("servlets");

		int[] expectedPostIds = { 8419381, 8452989 };

		ArrayList<Post> resultedPosts = db.getQuestionsByFreeTextAndTags(
				oWords, oTags,
				SearchResultSortingAlgorithm.QuestionScoreAlgorithm);

		for (Post post : resultedPosts) {
			foundPostId = false;
			for (int i = 0; i < expectedPostIds.length; i++) {
				if (expectedPostIds[i] == post.getId()) {
					foundPostId = true;
				}
			}
			assertEquals(true, foundPostId);
		}
	}

	/**
	 * test ArrayList<Post> getAnswers(int questionId)
	 */
	public void testGetAnswers() {
		boolean foundPostId;
		int questionId = 8452989;
		int[] expectedPostIds = { 8453025 };

		ArrayList<Post> resultedPosts = db.getAnswers(questionId);

		for (Post post : resultedPosts) {
			foundPostId = false;
			for (int i = 0; i < expectedPostIds.length; i++) {
				if (expectedPostIds[i] == post.getId()) {
					foundPostId = true;
				}
			}
			assertEquals(true, foundPostId);
		}

	}

	/**
	 * test ArrayList<String> getTopRelatedTags(String referenceTag)
	 */
	public void testGetTopRelatedTags() {
		boolean foundTag;
		String referenceTag = "browser";
		String[] expectedResult = { "javascript", "c++", "safari", "webkit" };

		ArrayList<String> resultedTags = db.getTopRelatedTags(referenceTag);

		for (String resultedTag : resultedTags) {
			foundTag = false;
			for (int i = 0; i < expectedResult.length; i++) {
				if (expectedResult[i].equals(resultedTag)) {
					foundTag = true;
				}
			}
			assertEquals(true, foundTag);
		}
	}

	/**
	 * test public ArrayList<Post> getQuestionsByFreeTextAndTagsWithLimits(
	 * String[] oWords, ArrayList<String> oTags, SearchResultSortingAlgorithm
	 * eSearchResultSortingAlgorithm, String lastSortingElement) test to get the
	 * first 10 result of a search by score
	 */
	public void testGetQuestionByFreeTextAndTagsWithLimitsNext1() {
		boolean foundPostId;
		String[] oWords = { "string" };
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("java");
		int[] lastQuestionIds = {};

		int[] expectedPostIds = { 8416664, 8421824, 8470563, 8421007, 8471603,
				8414452, 8417595, 8417809, 8469847, 8415628 };

		ArrayList<Post> resultedPosts = db
				.getQuestionsByFreeTextAndTagsWithLimits(oWords, oTags,
						SearchResultSortingAlgorithm.QuestionScoreAlgorithm,
						null, -1);

		for (Post post : resultedPosts) {
			foundPostId = false;
			for (int i = 0; i < expectedPostIds.length; i++) {
				if (expectedPostIds[i] == post.getId()) {
					foundPostId = true;
				}
			}
			assertEquals(true, foundPostId);
		}
	}

	/**
	 * test public ArrayList<Post> getQuestionsByFreeTextAndTagsWithLimits(
	 * String[] oWords, ArrayList<String> oTags, SearchResultSortingAlgorithm
	 * eSearchResultSortingAlgorithm, String lastSortingElement) for next
	 * functionality to go to the second 10 results of a search by count
	 */
	public void testGetQuestionByFreeTextAndTagsWithLimits2() {
		boolean foundPostId;
		String[] oWords = { "string" };
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("java");

		ArrayList<Post> displayedPosts = db
				.getQuestionsByFreeTextAndTagsWithLimits(oWords, oTags,
						SearchResultSortingAlgorithm.AnswerCountAlgotithm,
						null, -1);

		int[] expectedPostIds = { 8473148, 8415836, 8416302, 8416664, 8418236,
				8418628, 8419295, 8419415, 8469265, 8470364 };

		ArrayList<Post> resultedPosts = db
				.getQuestionsByFreeTextAndTagsWithLimits(oWords, oTags,
						SearchResultSortingAlgorithm.AnswerCountAlgotithm,
						displayedPosts, 0);

		for (Post post : resultedPosts) {
			foundPostId = false;
			for (int i = 0; i < expectedPostIds.length; i++) {
				if (expectedPostIds[i] == post.getId()) {
					foundPostId = true;
				}
			}
			assertEquals(true, foundPostId);
		}
	}

	/**
	 * test public ArrayList<Post> getQuestionsByFreeTextAndTagsWithLimits(
	 * String[] oWords, ArrayList<String> oTags, SearchResultSortingAlgorithm
	 * eSearchResultSortingAlgorithm, String lastSortingElement) for previous
	 * functionality to go to the first 10 results of a search by user when the
	 * displayed result consists of the second 10 results of the search
	 */
	public void testGetQuestionByFreeTextAndTagsWithLimits3() {
		boolean foundPostId;
		String[] oWords = { "string" };
		ArrayList<String> oTags = new ArrayList<String>();
		oTags.add("java");
		
		// get the first 10 results of the search
		ArrayList<Post> expectedPosts = db.getQuestionsByFreeTextAndTagsWithLimits(
				oWords,
				oTags,
				SearchResultSortingAlgorithm.UserReputationAlgorithm,
				null, -1);
		
		//get the second 10 results based on the first 10 results
		ArrayList<Post> displayedPosts = db
				.getQuestionsByFreeTextAndTagsWithLimits(
						oWords,
						oTags,
						SearchResultSortingAlgorithm.UserReputationAlgorithm,
						expectedPosts, 0);

		ArrayList<Post> resultedPosts = db
				.getQuestionsByFreeTextAndTagsWithLimits(oWords, oTags,
						SearchResultSortingAlgorithm.UserReputationAlgorithm,
						displayedPosts, 1);

		boolean bFound = false;
		
		for(int i = 0; i < expectedPosts.size(); i++){
			bFound = false;
			
			for (int j = 0; j < resultedPosts.size(); j++) {
				if(resultedPosts.get(j).getId() == expectedPosts.get(i).getId()){
					bFound = true;
				}
			}
			
			assertTrue(bFound);
		}
	}
}
