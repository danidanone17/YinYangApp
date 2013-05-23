package com.yinyang.so.database;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.yinyang.so.databaseentities.Comment;
import com.yinyang.so.databaseentities.DatabaseType;
import com.yinyang.so.databaseentities.MapTags;
import com.yinyang.so.databaseentities.Post;
import com.yinyang.so.databaseentities.Tag;
import com.yinyang.so.databaseentities.User;
import com.yinyang.so.databaseentities.Vote;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private SoData mDbHelper;

	/**
	 * Enumeration that defines possible search result sorting algorithms
	 */
	public enum SearchResultSortingAlgorithm {
		QuestionScoreAlgorithm, CreationDateAlgorithm, AnswerCountAlgotithm, UserReputationAlgorithm
	}

	public DatabaseAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new SoData(mContext);
	}

	// ------- BASE METHODS -------

	public DatabaseAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public DatabaseAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	// ------- GETTER METHODS -------

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Posts
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @return an ArrayList of <Post>
	 **/
	private ArrayList<Post> getPostsFromCursor(Cursor cursor) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.posts)) {
			posts.add((Post) db);
		}
		return posts;
	}

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Users
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @return an ArrayList of <User>
	 **/
	private ArrayList<User> getUsersFromCursor(Cursor cursor) {
		ArrayList<User> users = new ArrayList<User>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.users)) {
			users.add((User) db);
		}
		return users;
	}

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Comments
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @return an ArrayList of <Comment>
	 **/
	private ArrayList<Comment> getCommentsFromCursor(Cursor cursor) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.comments)) {
			comments.add((Comment) db);
		}
		return comments;
	}

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Votes
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @return an ArrayList of <Vote>
	 **/
	private ArrayList<Vote> getVotesFromCursor(Cursor cursor) {
		ArrayList<Vote> votes = new ArrayList<Vote>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.votes)) {
			votes.add((Vote) db);
		}
		return votes;
	}

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Tags
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @return an ArrayList of <Tag>
	 **/
	private ArrayList<Tag> getTagsFromCursor(Cursor cursor) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.tags)) {
			tags.add((Tag) db);
		}
		return tags;
	}

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of MappingTags
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @return an ArrayList of <MapTags>
	 **/
	private ArrayList<MapTags> getMappingTagsFromCursor(Cursor cursor) {
		ArrayList<MapTags> mapTags = new ArrayList<MapTags>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.mapping_tags)) {
			mapTags.add((MapTags) db);
		}
		return mapTags;
	}

	/**
	 * Converts cursor to ArrayList of elements depending on a given TableType
	 * 
	 * @param cursor
	 *            A cursor pointing to the db
	 * @param ttype
	 *            TableType enum
	 * @return an ArrayList of DatabaseType
	 **/
	private ArrayList<DatabaseType> cursorToArrayList(Cursor cursor,
			TableType ttype) {
		ArrayList<DatabaseType> list = new ArrayList<DatabaseType>();
		if (cursor != null) {

			// returns different classes depending on the table type
			switch (ttype) {

			case users:
				while (cursor.moveToNext()) {
					User user = new User(cursor);
					list.add(user);
				}
				break;

			case comments:
				while (cursor.moveToNext()) {
					Comment comment = new Comment(cursor);
					list.add(comment);
				}
				break;

			case posts:
				while (cursor.moveToNext()) {
					Post post = new Post(cursor);
					list.add(post);
				}
				break;

			case votes:
				while (cursor.moveToNext()) {
					Vote vote = new Vote(cursor);
					list.add(vote);
				}
				break;

			case mapping_tags:
				while (cursor.moveToNext()) {
					MapTags mapTags = new MapTags(cursor);
					list.add(mapTags);
				}
				break;

			case tags:
				while (cursor.moveToNext()) {
					Tag vote = new Tag(cursor);
					list.add(vote);
				}
				break;

			}
		}
		return list;
	}

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of Posts
	 * 
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <Post>
	 **/
	public ArrayList<Post> getPostsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (DatabaseType db : getDataByCriteria(TableType.posts, criteria)) {
			posts.add((Post) db);
		}
		return posts;
	}

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of Users
	 * 
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <User>
	 **/
	public ArrayList<User> getUsersByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<User> users = new ArrayList<User>();
		for (DatabaseType db : getDataByCriteria(TableType.users, criteria)) {
			users.add((User) db);
		}
		return users;
	}

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of Tags
	 * 
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <Tag>
	 **/
	public ArrayList<Tag> getTagsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (DatabaseType db : getDataByCriteria(TableType.tags, criteria)) {
			tags.add((Tag) db);
		}
		return tags;
	}

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of MappingTags
	 * 
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <MapTags>
	 **/
	public ArrayList<MapTags> getMappingTagsByCriteria(
			ArrayList<SearchEntity> criteria) {
		ArrayList<MapTags> mappingTags = new ArrayList<MapTags>();
		for (DatabaseType db : getDataByCriteria(TableType.mapping_tags,
				criteria)) {
			mappingTags.add((MapTags) db);
		}
		return mappingTags;
	}

	/**
	 * Return data based on table name and criteria Not to be called directly,
	 * use wrappers instead
	 * 
	 * @param ttype
	 *            TableType enum
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <DatabaseType>
	 **/
	private ArrayList<DatabaseType> getDataByCriteria(TableType ttype,
			ArrayList<SearchEntity> criteria) {
		String sqlQuery = "SELECT * FROM " + ttype.toString()
				+ getSQLWhereFromCriteria(criteria);
		Cursor cursor = this.getCursor(sqlQuery);
		return cursorToArrayList(cursor, ttype);
	}

	/**
	 * Construct the "WHERE" part of query given SearchEntity criteria
	 * 
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return a SQL string " WHERE X LIKE '%a%...'
	 **/
	private String getSQLWhereFromCriteria(ArrayList<SearchEntity> criteria) {
		String where = " WHERE ";
		int i = 0;
		for (SearchEntity searchEntity : criteria) {
			if (i > 0) {
				where += " AND ";
			}
			if (searchEntity.getMeanOfSearch().equals(MeanOfSearch.contained)) {
				where += searchEntity.getKey() + " LIKE '%"
						+ searchEntity.getValue() + "%'";
			}
			if (searchEntity.getMeanOfSearch().equals(MeanOfSearch.exact)) {
				where += searchEntity.getKey() + " LIKE '"
						+ searchEntity.getValue() + "'";
			}
			i++;
		}
		return where;
	}

	/**
	 * Return a single User from the database by id Return null if no user with
	 * given id can be found
	 * 
	 * @param id
	 * @return a single User
	 **/
	public User getUser(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM users WHERE (id='" + id
				+ "')");
		ArrayList<User> users = getUsersFromCursor(cursor);
		if (users.isEmpty())
			return null;
		else
			return users.get(0);
	}

	/**
	 * Return a single Comment from the database by id
	 * 
	 * @param id
	 * @return a single Comment
	 **/
	public Comment getComment(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM comments WHERE (id='"
				+ id + "')");
		return getCommentsFromCursor(cursor).get(0);
	}

	/**
	 * Return SQL COUNT from criteria
	 * 
	 * @param ttype
	 *            TableType enum
	 * @param criteria
	 *            An ArrayList of SearchEntity criteria
	 * @return int
	 **/
	public int getCountByCriteria(TableType ttype,
			ArrayList<SearchEntity> criteria) {
		String sqlMessage;
		sqlMessage = "SELECT * FROM " + ttype.toString();
		sqlMessage += this.getSQLWhereFromCriteria(criteria);
		Cursor cursor = this.getCursor(sqlMessage);
		return cursor.getCount();
	}

	/**
	 * Return a single Post from the database by id
	 * 
	 * @param id
	 * @return a single Post
	 **/
	public Post getPost(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM posts WHERE (id='" + id
				+ "')");
		try {
			return getPostsFromCursor(cursor).get(0);
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getPost(id) : " + e);
			return null;
		}
	}

	/**
	 * Return a single Vote from the database by id
	 * 
	 * @param id
	 * @return a single Vote
	 **/
	public Vote getVote(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM votes WHERE (id='" + id
				+ "')");
		return getVotesFromCursor(cursor).get(0);
	}

	/**
	 * Return a single MapTag from the database by id
	 * 
	 * @param id
	 * @return a single MapTags
	 **/
	public MapTags getMapTags(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM " + MapTags.TABLE_NAME
				+ " WHERE (id=" + id + ")");
		try {
			return getMappingTagsFromCursor(cursor).get(0);
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getMapTags(id) : " + e);
			return null;
		}
	}

	/**
	 * Return the last index of a given table
	 * 
	 * @param ttype
	 *            TableType enum
	 * @return int
	 **/
	public int getLastIndex(TableType ttype) {
		String sqlMessage;
		sqlMessage = "SELECT id FROM " + ttype.toString()
				+ " ORDER BY id DESC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Return the first index of a given table
	 * 
	 * @param ttype
	 *            TableType enum
	 * @return int
	 **/
	public int getFirstIndex(TableType ttype) {
		String sqlMessage;
		sqlMessage = "SELECT id FROM " + ttype.toString()
				+ " ORDER BY id ASC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Return the last index from Posts with non-null tags
	 * 
	 * @return int
	 **/
	public int getLastIndexFromPostsWithTagsNotNull() {
		String sqlMessage;
		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL ORDER BY id DESC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Return the first index from Posts with non-null tags
	 * 
	 * @return int
	 **/
	public int getFirstIndexFromPostsWithTagsNotNull() {
		String sqlMessage;
		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL ORDER BY id ASC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Return the next post id with non-null tag
	 * 
	 * @param int
	 * @return int
	 **/
	public int getNextPostIdWithTagNotNull(int id) {
		String sqlMessage;
		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL AND id > "
				+ id + " ORDER BY ID ASC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Get the four top-related tags of a tag (US39) Returns tag name and number
	 * of occurences in a HashMap
	 * 
	 * @param refrerenceTag
	 *            tag
	 * @return HashMap of Strings and integers
	 **/
	public ArrayList<KeyValuePair> getTopRelatedTags(String referenceTag) {

		String sqlQuery;
		ArrayList<KeyValuePair> tagNamesOccurences = new ArrayList<KeyValuePair>();

		sqlQuery = "SELECT * FROM " + TableType.mapping_tags
				+ " WHERE (TAG1 LIKE '" + referenceTag + "' "
				+ "OR TAG2 LIKE '" + referenceTag
				+ "') AND TAG1 NOT LIKE TAG2 ORDER BY "
				+ MapTags.KEY_COUNT_APPEARANCE + " DESC LIMIT 4";

		try {
			Cursor cursor = this.getCursor(sqlQuery);
			ArrayList<MapTags> tagsDBType = getMappingTagsFromCursor(cursor);

			for (MapTags relatedMapTag : tagsDBType) {
				if (referenceTag.equals(relatedMapTag.getTag2())) {
					tagNamesOccurences.add(new KeyValuePair(relatedMapTag
							.getTag1(), relatedMapTag.getCountAppearance()));
				} else {
					tagNamesOccurences.add(new KeyValuePair(relatedMapTag
							.getTag2(), relatedMapTag.getCountAppearance()));
				}
			}
			Log.v("DEBUG", "Tag_1.4.1");
			return tagNamesOccurences;
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION",
					"DatabaseAdapter.getTopRelatedTags(String) : " + sqlQuery
							+ ", ERROR: " + e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * Return Posts by tag or tag combination (US1)
	 * 
	 * @param ArrayList
	 *            <String> of tags
	 * @return ArrayList<Post>
	 **/
	public ArrayList<Post> getPostsByTags(ArrayList<String> tags) {
		ArrayList<Post> posts = new ArrayList<Post>();
		String sqlMessage;
		int i = 0;

		sqlMessage = "SELECT * FROM " + TableType.posts + " WHERE ";

		for (String tag : tags) {
			if (i > 0) {
				sqlMessage += " AND ";
			}
			sqlMessage += Post.KEY_TAGS + " LIKE '%<" + tag + ">%'";
			i++;
		}
		Cursor cursor = this.getCursor(sqlMessage);
		posts = getPostsFromCursor(cursor);
		return posts;
	}

	/**
	 * Returns all associated answers to a question/post
	 * 
	 * @param questionId
	 *            the post id
	 * @return an ArrayList<Post> with answers
	 */
	public ArrayList<Post> getAnswers(int questionId) {
		String sqlQuery = "SELECT * FROM " + TableType.posts + " WHERE "
				+ Post.KEY_PARENT_ID + " = " + questionId;
		Cursor cursor = this.getCursor(sqlQuery);
		return getPostsFromCursor(cursor);
	}

	/**
	 * Get questions where - the given words are contained in either the title
	 * or the body and - there is a relation to all of the provided tags
	 * Currently ordered by descending order and limited the results to 100
	 * posts only
	 * 
	 * @param oWords
	 *            the words that have to be contained in a question's title or
	 *            body to be returned by this method
	 * @param oTags
	 *            tags the returned questions should be related to
	 * @param eSearchResultSortingAlgorithm
	 *            chosen search result algorithm
	 * @return questions
	 * 
	 */
	public ArrayList<Post> getQuestionsByFreeTextAndTags(String[] oWords,
			ArrayList<String> oTags,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm) {
		return this.getQuestionsByFreeTextAndTagsWithLimits(oWords, oTags,
				eSearchResultSortingAlgorithm, null, -1);
	}

	/**
	 * Get questions where - the given words are contained in either the title
	 * or the body and - there is a relation to all of the provided tags
	 * Currently ordered by descending order and limited the results to 100
	 * posts only
	 * 
	 * @param oWords
	 *            the words that have to be contained in a question's title or
	 *            body to be returned by this method
	 * @param oTags
	 *            tags the returned questions should be related to
	 * @param eSearchResultSortingAlgorithm
	 *            chosen search result algorithm
	 * @param displayedPosts
	 *            the posts currently displayed, null if none
	 * @param nextOrPrev
	 *            0 for next and 1 for previous, -1 if none
	 * @return questions
	 * 
	 */
	public ArrayList<Post> getQuestionsByFreeTextAndTagsWithLimits(
			String[] oWords, ArrayList<String> oTags,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm,
			ArrayList<Post> displayedPosts, int nextOrPrev) {

		String lastSortingElement = null;
		int lastQuestionId = -1;
		if (displayedPosts != null && displayedPosts.size() > 0) {
			if (nextOrPrev == 0) {
				lastSortingElement = this.getLastSortingElement(displayedPosts,
						eSearchResultSortingAlgorithm);
			} else {
				lastSortingElement = this.getFirstSortingElement(
						displayedPosts, eSearchResultSortingAlgorithm);
			}
			lastQuestionId = this.getQuestionsWithMinOrMaxSortingElementId(
					displayedPosts, nextOrPrev);
		}

		// create sql statement
		// first select is for setting the limit based on the last displayed
		// element (first we order and then we limit)
		String sSqlMessage = "SELECT * FROM (SELECT * FROM " + TableType.posts;

		// if UserReputationAlgorithm is set
		// join with users table
		if (eSearchResultSortingAlgorithm == SearchResultSortingAlgorithm.UserReputationAlgorithm) {
			sSqlMessage += " JOIN " + TableType.users + " u ON "
					+ Post.KEY_OWNER_USER_ID + " = u." + User.KEY_ID;
		}
		sSqlMessage += " WHERE " + Post.KEY_POST_TYPE_ID + " = '1'";
		for (int i = 0; i < oWords.length; i++) {
			sSqlMessage += " AND (" + Post.KEY_TITLE + " LIKE '%" + oWords[i]
					+ "%'";
			sSqlMessage += " OR " + Post.KEY_BODY + " LIKE '%" + oWords[i]
					+ "%')";
		}

		if (oTags != null) {
			for (String sTag : oTags) {
				sSqlMessage += " AND " + Post.KEY_TAGS + " LIKE '%<" + sTag
						+ ">%'";
			}
		}

		// add order by statement
		switch (eSearchResultSortingAlgorithm) {
		case QuestionScoreAlgorithm:
			sSqlMessage += " ORDER BY " + Post.KEY_SCORE + " DESC";
			break;
		case CreationDateAlgorithm:
			sSqlMessage += " ORDER BY " + Post.KEY_CREATION_DATE + " DESC";
			break;
		case AnswerCountAlgotithm:
			sSqlMessage += " ORDER BY " + Post.KEY_ANSWER_COUNT + " DESC";
			break;
		case UserReputationAlgorithm:
			sSqlMessage += " ORDER BY " + User.KEY_REPUTATION + " DESC";
			break;
		default:
			sSqlMessage += " ORDER BY " + Post.KEY_SCORE + " DESC";
			break;
		}// end of the first select

		sSqlMessage += ", " + Post.KEY_ID + " DESC)";

		// start condition for the second select
		// add condition for limiting based on the type of search if we are not
		// on the first page
		if (lastSortingElement != null) {
			switch (eSearchResultSortingAlgorithm) {
			case QuestionScoreAlgorithm:
				sSqlMessage += " WHERE " + Post.KEY_SCORE;

				if (nextOrPrev == 0) {
					sSqlMessage += " <= ";
				}
				if (nextOrPrev == 1) {
					sSqlMessage += " >= ";
				}

				sSqlMessage += lastSortingElement;
				break;
			case CreationDateAlgorithm:
				sSqlMessage += " WHERE " + Post.KEY_CREATION_DATE;
				if (nextOrPrev == 0) {
					sSqlMessage += " <= ";
				}
				if (nextOrPrev == 1) {
					sSqlMessage += " >= ";
				}

				sSqlMessage += "'" + lastSortingElement + "'";
				break;
			case AnswerCountAlgotithm:
				sSqlMessage += " WHERE " + Post.KEY_ANSWER_COUNT;
				if (nextOrPrev == 0) {
					sSqlMessage += " <= ";
				}
				if (nextOrPrev == 1) {
					sSqlMessage += " >= ";
				}

				sSqlMessage += lastSortingElement;
				break;
			case UserReputationAlgorithm:
				sSqlMessage += " WHERE " + User.KEY_REPUTATION;
				if (nextOrPrev == 0) {
					sSqlMessage += " <= ";
				}
				if (nextOrPrev == 1) {
					sSqlMessage += " >= ";
				}

				sSqlMessage += lastSortingElement;
				break;
			default:
				sSqlMessage += " WHERE " + Post.KEY_SCORE;
				if (nextOrPrev == 0) {
					sSqlMessage += " <= ";
				}
				if (nextOrPrev == 1) {
					sSqlMessage += " >= ";
				}

				sSqlMessage += lastSortingElement;
				break;
			}

			if (lastQuestionId != -1) {
				if (nextOrPrev == 0) {
					sSqlMessage += " AND " + Post.KEY_ID + " < "
							+ lastQuestionId + " ";
				} else {
					sSqlMessage += " AND " + Post.KEY_ID + " > "
							+ lastQuestionId + " ";
				}
			}
		}

		// add search result limit
		sSqlMessage += " LIMIT 10";

		try {
			// execute sql statement
			Cursor oCursor = this.getCursor(sSqlMessage);

			// convert result to an array list of Posts
			ArrayList<Post> oQuestions = this.getPostsFromCursor(oCursor);

			return oQuestions;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the last (minimum) value of the sorting element from a list of posts
	 * 
	 * @param displayedPosts
	 * @param eSearchResultSortingAlgorithm
	 * @return
	 */
	public String getLastSortingElement(ArrayList<Post> displayedPosts,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm) {
		String lastSortingElement = null;

		switch (eSearchResultSortingAlgorithm) {

		// get the last minimum score our of the currently displayed posts
		// usage of common min algorithm
		case QuestionScoreAlgorithm:
			int minScore = displayedPosts.get(0).getScore();
			for (int i = 1; i < displayedPosts.size(); i++) {
				if (minScore > displayedPosts.get(i).getScore()) {
					minScore = displayedPosts.get(i).getScore();
				}
			}

			lastSortingElement = "" + minScore;
			break;

		// get the last minimum creation date out of the currently displayed
		// posts
		// usage of common min algorithm with parsing the String creationDate
		case CreationDateAlgorithm:
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date minDate = sdf.parse(displayedPosts.get(0)
						.getCreationDate());
				for (int i = 1; i < displayedPosts.size(); i++) {
					if (minDate.compareTo(sdf.parse(displayedPosts.get(i)
							.getCreationDate())) > 0) {
						minDate = sdf.parse(displayedPosts.get(i)
								.getCreationDate());
					}
				}

				lastSortingElement = sdf.format(minDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// get the last minimum answer count of the currently displayed posts
		// usage of common min algorithm
		case AnswerCountAlgotithm:
			int minAnswerCount = displayedPosts.get(0).getAnswerCount();

			for (int i = 1; i < displayedPosts.size(); i++) {
				if (minAnswerCount > displayedPosts.get(i).getAnswerCount()) {
					minAnswerCount = displayedPosts.get(i).getAnswerCount();
				}
			}

			lastSortingElement = "" + minAnswerCount;
			break;

		// get last lowest user reputation of the displayed posts
		// usage of common min algorithm
		case UserReputationAlgorithm:
			int minUserReputation = this.getUser(
					displayedPosts.get(0).getOwnerUserId()).getReputation();

			for (int i = 1; i < displayedPosts.size(); i++) {
				if (minUserReputation > this.getUser(
						displayedPosts.get(i).getOwnerUserId()).getReputation()) {
					minUserReputation = this.getUser(
							displayedPosts.get(i).getOwnerUserId())
							.getReputation();
				}
			}

			lastSortingElement = "" + minUserReputation;
			break;
		default:
			lastSortingElement = null;
			break;
		}

		return lastSortingElement;
	}

	/**
	 * get the first (maximum) value of the sorting element from a list of posts
	 * 
	 * @param displayedPosts
	 * @param eSearchResultSortingAlgorithm
	 * @return
	 */
	public String getFirstSortingElement(ArrayList<Post> displayedPosts,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm) {
		String lastSortingElement = null;

		switch (eSearchResultSortingAlgorithm) {

		// get the first maximum score our of the currently displayed posts
		// usage of common max algorithm
		case QuestionScoreAlgorithm:
			int maxScore = displayedPosts.get(0).getScore();
			for (int i = 1; i < displayedPosts.size(); i++) {
				if (maxScore < displayedPosts.get(i).getScore()) {
					maxScore = displayedPosts.get(i).getScore();
				}
			}

			lastSortingElement = "" + maxScore;
			break;

		// get the first, maximum creation date out of the currently displayed
		// posts
		// usage of common max algorithm with parsing the String creationDate
		case CreationDateAlgorithm:
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date maxDate = sdf.parse(displayedPosts.get(0)
						.getCreationDate());
				for (int i = 1; i < displayedPosts.size(); i++) {
					if (maxDate.compareTo(sdf.parse(displayedPosts.get(i)
							.getCreationDate())) < 0) {
						maxDate = sdf.parse(displayedPosts.get(i)
								.getCreationDate());
					}
				}

				lastSortingElement = sdf.format(maxDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// get the first, maximum answer count of the currently displayed posts
		// usage of common max algorithm
		case AnswerCountAlgotithm:
			int maxAnswerCount = displayedPosts.get(0).getAnswerCount();

			for (int i = 1; i < displayedPosts.size(); i++) {
				if (maxAnswerCount < displayedPosts.get(i).getAnswerCount()) {
					maxAnswerCount = displayedPosts.get(i).getAnswerCount();
				}
			}

			lastSortingElement = "" + maxAnswerCount;
			break;

		// get first, highest user reputation of the displayed posts
		// usage of common max algorithm
		case UserReputationAlgorithm:
			int maxUserReputation = this.getUser(
					displayedPosts.get(0).getOwnerUserId()).getReputation();

			for (int i = 1; i < displayedPosts.size(); i++) {
				if (maxUserReputation < this.getUser(
						displayedPosts.get(i).getOwnerUserId()).getReputation()) {
					maxUserReputation = this.getUser(
							displayedPosts.get(i).getOwnerUserId())
							.getReputation();
				}
			}

			lastSortingElement = "" + maxUserReputation;
			break;
		default:
			lastSortingElement = null;
			break;
		}

		return lastSortingElement;
	}

	/**
	 * get the id of the first or last element of the displayed elements
	 * 
	 * @param displayedPosts
	 * @int minOrMax is 0 for min and 1 for max
	 * @return
	 */
	public int getQuestionsWithMinOrMaxSortingElementId(
			ArrayList<Post> displayedPosts, int minOrMax) {

		int postId = 0;

		// for next, we get the last element
		if (minOrMax == 0) {
			postId = displayedPosts.get(displayedPosts.size() - 1).getId();
		}

		// for previous, we get the first element
		if (minOrMax == 1) {
			postId = displayedPosts.get(0).getId();
		}

		return postId;
	}

	/**
	 * Get questions where - the given words are contained in either the title
	 * or the body
	 * 
	 * @param oWords
	 *            the words that have to be contained in a question's title or
	 *            body to be returned by this method
	 * @param eSearchResultSortingAlgorithm
	 *            chosen search result algorithm
	 * @return questions
	 */
	public ArrayList<Post> getQuestionsByFreeText(String[] oWords,
			SearchResultSortingAlgorithm eSearchResultSortingAlgorithm) {
		return this.getQuestionsByFreeTextAndTagsWithLimits(oWords,
				new ArrayList<String>(), eSearchResultSortingAlgorithm, null,
				-1);
	}

	/**
	 * Get the first 100 questions posted by a given user
	 * 
	 * @param userId
	 * @return An ArrayList of Posts
	 */
	public ArrayList<Post> getQuestionsByUser(int userId) {

		// create sql statement
		String sSqlMessage = "SELECT * FROM " + TableType.posts;
		sSqlMessage += " WHERE " + Post.KEY_POST_TYPE_ID + " = '1'";
		sSqlMessage += " AND " + Post.KEY_OWNER_USER_ID + " = '" + userId + "'";
		sSqlMessage += " ORDER BY " + Post.KEY_SCORE + " DESC LIMIT 100";
		// execute sql statement
		Cursor oCursor = this.getCursor(sSqlMessage);
		// convert result to an array list of Posts
		ArrayList<Post> oQuestions = this.getPostsFromCursor(oCursor);
		return oQuestions;
	}

	/**
	 * Get the alphabetically next and previous tags (US51)
	 * 
	 * @param string
	 *            tag
	 * @return an ArrayList<String> with tags
	 */
	public ArrayList<String> getNextAndPreviousTags(String searchTag) {
		ArrayList<Tag> dbNextTag = new ArrayList<Tag>();
		ArrayList<Tag> dbPreviousTag = new ArrayList<Tag>();
		ArrayList<String> nextAndPreviousTags = new ArrayList<String>();
		String sqlQuery = "";
		Cursor tagCursor = null;

		// Selecting next tag, alphabetically
		sqlQuery = "SELECT * FROM " + "(SELECT * FROM " + TableType.tags
				+ " ORDER BY " + Tag.KEY_TAG + ")" + " WHERE " + Tag.KEY_TAG
				+ " > '" + searchTag + "' LIMIT 1";

		try {
			// Executing the SQL-query
			tagCursor = this.getCursor(sqlQuery);
			// Nothing is returned as "next" for the last element of the table,
			// fetch first element
			if (tagCursor.getCount() == 0) {
				sqlQuery = "SELECT * FROM (SELECT * FROM " + TableType.tags
						+ " ORDER BY " + Tag.KEY_TAG + ") LIMIT 1";
				tagCursor = this.getCursor(sqlQuery);
			}
			// Saving the cursor as a DatabaseType ArrayList
			dbNextTag = getTagsFromCursor(tagCursor);
		} catch (NullPointerException npe) {
			Log.e("SQLite EXCEPTION",
					"DatabaseAdapter.getNextAndPreviousTags(String) : a NullPointer, next tag : "
							+ npe.getLocalizedMessage());
		} catch (ArrayIndexOutOfBoundsException aie) {
			Log.e("SQLite EXCEPTION",
					"DatabaseAdapter.getNextAndPreviousTags(String) : an IndexOutOfBounds, next tag : "
							+ aie.getLocalizedMessage());
		}

		// Selecting previous tag, alphabetically
		sqlQuery = "SELECT * FROM " + "(SELECT * FROM " + TableType.tags
				+ " ORDER BY " + Tag.KEY_TAG + " DESC)" + " WHERE "
				+ Tag.KEY_TAG + " < '" + searchTag + "' LIMIT 1";

		try {
			// Executing the SQL-query
			tagCursor = this.getCursor(sqlQuery);
			// Nothing is returned as "previous" for the first element of the
			// table, fetch last element
			if (tagCursor.getCount() == 0) {
				sqlQuery = "SELECT * FROM (SELECT * FROM " + TableType.tags
						+ " ORDER BY " + Tag.KEY_TAG + " DESC) LIMIT 1";
				tagCursor = this.getCursor(sqlQuery);
			}
			// Saving the cursor as a DatabaseType ArrayList
			dbPreviousTag = getTagsFromCursor(tagCursor);
		} catch (NullPointerException npe) {
			Log.e("SQLite EXCEPTION",
					"DatabaseAdapter.getNextAndPreviousTags(String) : a NullPointer, previous tag : "
							+ npe.getLocalizedMessage());
		} catch (ArrayIndexOutOfBoundsException aie) {
			Log.e("SQLite EXCEPTION",
					"DatabaseAdapter.getNextAndPreviousTags(String) : an IndexOutOfBounds, previous tag : "
							+ aie.getLocalizedMessage());
		}

		// Adding to String ArrayList
		nextAndPreviousTags.add(dbNextTag.get(0).getTag());
		nextAndPreviousTags.add(dbPreviousTag.get(0).getTag());

		return nextAndPreviousTags;
	}

	/**
	 * Returns the tag that's name match the given string
	 * 
	 * @param sName
	 *            string the tag name should match
	 * @return the tag that's name match the given string
	 */
	public String getTagByName(String sName) {
		String sSqlMessage;

		sSqlMessage = "SELECT " + Tag.KEY_TAG + " FROM " + TableType.tags;
		sSqlMessage += " WHERE " + Tag.KEY_TAG + " LIKE '" + sName
				+ "' LIMIT 1";

		Cursor oCursor = this.getCursor(sSqlMessage);
		oCursor.moveToFirst();
		if (oCursor.getCount() > 0) {
			return oCursor.getString(0);
		} else {
			return "";
		}
	}

	/**
	 * Returns a tag by id
	 * 
	 * @param int id
	 * @return String tag
	 */
	public String getTag(int id) {
		String tag = null;
		String sqlQuery = "SELECT " + Tag.KEY_TAG + " FROM " + TableType.tags
				+ " WHERE (id='" + id + "')";
		try {
			Cursor cursor = this.getCursor(sqlQuery);
			cursor.moveToFirst();
			tag = cursor.getString(0);
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION",
					"DatabaseAdapter.getTag(int) : Tag not found : "
							+ e.getMessage());
			// e.printStackTrace();
		}
		return tag;
	}

	// ------- SQL METHODS -------

	private Cursor getCursor(String sqlString) {
		try {
			Cursor mCur = mDb.rawQuery(sqlString, null);
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	private void nonReturnSqlStatement(String sqlString) {
		try {
			mDb.execSQL(sqlString);
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	// HashMap shall contain: key = columnName and value=columnType
	public void createTable(String tableName, String idName,
			HashMap<String, String> columns) {
		String sqlMessage;

		sqlMessage = "CREATE TABLE " + tableName + " ( ";
		sqlMessage = sqlMessage + idName + " INTEGER PRIMARY KEY ";

		Iterator<Entry<String, String>> it = columns.entrySet().iterator();
		Map.Entry<String, String> pairs;

		while (it.hasNext()) {
			pairs = (Map.Entry<String, String>) it.next();
			sqlMessage = sqlMessage + ", ";
			sqlMessage = sqlMessage + pairs.getKey() + " " + pairs.getValue();
		}

		sqlMessage = sqlMessage + ")";

		System.out.println(sqlMessage);

		this.nonReturnSqlStatement(sqlMessage);
	}

	public void dropTable(String tableName) {
		String sqlMessage;
		sqlMessage = "DROP TABLE IF EXISTS " + tableName;
		mDb.execSQL(sqlMessage);
	}

	public void emptyTable(String tableName) {
		String sqlMessage;
		sqlMessage = "DELETE FROM " + tableName;

		mDb.execSQL(sqlMessage);
	}

	// for columnValues: key = columnName and values = columnValue
	public boolean insertSql(String tableName,
			HashMap<String, String> columnValues) {
		String sqlMessage, values;
		int id;

		try {
			id = this.getLastIndex(TableType.valueOf(tableName)) + 1;
		} catch (Exception e) {
			id = 1;
		}

		sqlMessage = "INSERT INTO " + tableName;
		sqlMessage += " ( id";
		values = ") VALUES (" + id;

		Iterator<Entry<String, String>> it = columnValues.entrySet().iterator();
		Map.Entry<String, String> pairs;

		while (it.hasNext()) {
			pairs = (Map.Entry<String, String>) it.next();
			sqlMessage += ", " + pairs.getKey();
			values += ", '" + pairs.getValue() + "'";
		}

		values += ")";

		sqlMessage += values;

		try {
			System.out.println("INSERT STATEMENT: " + sqlMessage);
			mDb.execSQL(sqlMessage);
			return true;
		} catch (Exception e) {
			System.out.println("INSERT ERROR: " + e.getMessage());
			return false;
		}

	}

	// for columnValues: key = columnName and values = columnValue
	public boolean updateSql(String tableName,
			HashMap<String, String> columnValues, String whereClause) {
		String sqlMessage;

		sqlMessage = "UPDATE " + tableName + " SET ";

		Iterator<Entry<String, String>> it = columnValues.entrySet().iterator();
		Map.Entry<String, String> pairs;

		while (it.hasNext()) {
			pairs = (Map.Entry<String, String>) it.next();
			sqlMessage += pairs.getKey() + "='" + pairs.getValue() + "'";

			if (it.hasNext()) {
				sqlMessage += ", ";
			}
		}

		sqlMessage += " WHERE " + whereClause;

		try {
			System.out.println("UPDATE MESSAGE: " + sqlMessage);
			mDb.execSQL(sqlMessage);
			return true;
		} catch (Exception e) {
			System.out.println("ERROR IN UPDATE: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Returns tags in alphabetical order
	 * 
	 * @param iLimit
	 *            limits number of returned tags
	 * @return tags in alphabetical order
	 */
	public ArrayList<String> getTagsInAlphabeticalOrder(int iLimit) {
		String sSqlMessage;

		sSqlMessage = "SELECT " + Tag.KEY_TAG + " FROM " + TableType.tags;
		sSqlMessage += " ORDER BY " + Tag.KEY_TAG + " ASC";
		if (iLimit > 0) {
			sSqlMessage += " LIMIT " + iLimit;
		}

		Cursor oCursor = this.getCursor(sSqlMessage);

		ArrayList<String> oTags = new ArrayList<String>();
		while (oCursor.moveToNext()) {
			oTags.add(oCursor.getString(0));
		}

		return oTags;
	}

	public Tag getTagObjectByName(String tagName) {
		Tag tag = null;
		String sqlQuery = "SELECT * FROM " + Tag.TABLE_NAME + " WHERE (tag='"
				+ tagName + "')";
		try {
			Cursor cursor = this.getCursor(sqlQuery);
			ArrayList<Tag> tagDBTs = getTagsFromCursor(cursor);

			tag = tagDBTs.get(0);
		} catch (Exception e) {
			Log.e("Method getTag in DatabaseAdaptor",
					"Tag not found" + e.getMessage());
			// e.printStackTrace();
		}
		return tag;
	}

	/**
	 * Returns users
	 * 
	 * @param limit
	 *            the maximum number of users that are returned
	 * @param searchName
	 *            if not empty only users that have the given are returned
	 * @param lastUserReputation
	 *            reputation of user who is currently last in list
	 * @param lastUserName
	 *            name of user who is currently last in list
	 * @return users
	 */
	public ArrayList<User> getUsersOrderedByReputation(int limit,
			String searchName, int lastUserReputation, String lastUserName) {
		ArrayList<User> users = new ArrayList<User>();
		String sqlMessage;

		sqlMessage = "SELECT * FROM " + User.TABLE_NAME;

		if (!searchName.isEmpty()) {
			sqlMessage += " WHERE " + User.KEY_DISPLAY_NAME + " = '"
					+ searchName + "'";

			if (lastUserReputation > 0 && !lastUserName.equals("")) {
				sqlMessage += " AND " + User.KEY_REPUTATION + " <= "
						+ lastUserReputation;
				sqlMessage += " AND " + User.KEY_DISPLAY_NAME + " NOT LIKE '"
						+ lastUserName + "'";
			}
		} else {
			if (lastUserReputation > 0 && !lastUserName.equals("")) {
				sqlMessage += " WHERE " + User.KEY_REPUTATION + " <= "
						+ lastUserReputation;
				sqlMessage += " AND " + User.KEY_DISPLAY_NAME + " NOT LIKE '"
						+ lastUserName + "'";
			}
		}
		sqlMessage += " ORDER BY " + User.KEY_REPUTATION + " DESC";
		sqlMessage += " LIMIT " + limit;

		Cursor cursor = this.getCursor(sqlMessage);
		users = getUsersFromCursor(cursor);
		return users;
	}

	/**
	 * Gets maximum score of all posts
	 * 
	 * @param oWords
	 *            the words that have to be contained in a question's title or
	 *            body to be returned by this method
	 * @param oTags
	 *            tags the returned questions should be related to
	 * @return maximum score of all posts
	 */
	public int getMaxPostScoreForFreeTextAndTagSearch(String[] words,
			ArrayList<String> tags) {
		String sqlMessage = "select max(" + Post.KEY_SCORE + ") from "
				+ Post.TABLE_NAME;
		sqlMessage += " WHERE " + Post.KEY_POST_TYPE_ID + " = '1'";
		for (int i = 0; i < words.length; i++) {
			sqlMessage += " AND (" + Post.KEY_TITLE + " LIKE '%" + words[i]
					+ "%'";
			sqlMessage += " OR " + Post.KEY_BODY + " LIKE '%" + words[i]
					+ "%')";
		}

		for (String tag : tags) {
			sqlMessage += " AND " + Post.KEY_TAGS + " LIKE '<%" + tag + "%>'";
		}

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
}
