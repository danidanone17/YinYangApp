package com.yinyang.so.database;

import java.io.IOException;
import java.util.ArrayList;
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
	 * @param cursor	A cursor pointing to the db
	 * @return an ArrayList of <Post>
	 **/
	private ArrayList<Post> getPostsFromCursor(Cursor cursor) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.posts)) {
			posts.add((Post) db); }
		return posts; }

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Users
	 * @param cursor	A cursor pointing to the db
	 * @return an ArrayList of <User>
	 **/
	private ArrayList<User> getUsersFromCursor(Cursor cursor) {
		ArrayList<User> users = new ArrayList<User>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.users)) {
			users.add((User) db); }
		return users; }

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Comments
	 * @param cursor	A cursor pointing to the db
	 * @return an ArrayList of <Comment>
	 **/
	private ArrayList<Comment> getCommentsFromCursor(Cursor cursor) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.comments)) {
			comments.add((Comment) db); }
		return comments; }

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Votes
	 * @param cursor	A cursor pointing to the db
	 * @return an ArrayList of <Vote>
	 **/
	private ArrayList<Vote> getVotesFromCursor(Cursor cursor) {
		ArrayList<Vote> votes = new ArrayList<Vote>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.votes)) {
			votes.add((Vote) db); }
		return votes; }

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of Tags
	 * @param cursor	A cursor pointing to the db
	 * @return an ArrayList of <Tag>
	 **/
	private ArrayList<Tag> getTagsFromCursor(Cursor cursor) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.tags)) {
			tags.add((Tag) db); }
		return tags; }

	/**
	 * Wrapper for cursorToArrayList() that returns an ArrayList of MappingTags
	 * @param cursor	A cursor pointing to the db
	 * @return an ArrayList of <MapTags>
	 **/
	private ArrayList<MapTags> getMappingTagsFromCursor(Cursor cursor) {
		ArrayList<MapTags> mapTags = new ArrayList<MapTags>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.mapping_tags)) {
			mapTags.add((MapTags) db); }
		return mapTags; }

	/**
	 * Converts cursor to ArrayList of elements depending on a given TableType
	 * @param cursor	A cursor pointing to the db
	 * @param ttype		TableType enum
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
	 * @param criteria	An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <Post>
	 **/
	public ArrayList<Post> getPostsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (DatabaseType db : getDataByCriteria(TableType.posts, criteria)) {
			posts.add((Post) db); }
		return posts; }

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of Users
	 * @param criteria	An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <User>
	 **/
	public ArrayList<User> getUsersByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<User> users = new ArrayList<User>();
		for (DatabaseType db : getDataByCriteria(TableType.users, criteria)) {
			users.add((User) db); }
		return users; }

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of Tags
	 * @param criteria	An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <Tag>
	 **/
	public ArrayList<Tag> getTagsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (DatabaseType db : getDataByCriteria(TableType.tags, criteria)) {
			tags.add((Tag) db); }
		return tags; }

	/**
	 * Wrapper for getDataByCriteria() that returns an ArrayList of MappingTags
	 * @param criteria	An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <MapTags>
	 **/
	public ArrayList<MapTags> getMappingTagsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<MapTags> mappingTags = new ArrayList<MapTags>();
		for (DatabaseType db : getDataByCriteria(TableType.mapping_tags, criteria)) {
			mappingTags.add((MapTags) db); }
		return mappingTags; }

	/**
	 * Return data based on table name and criteria
	 * Not to be called directly, use wrappers instead 
	 * @param ttype		TableType enum
	 * @param criteria	An ArrayList of SearchEntity criteria
	 * @return an ArrayList of <DatabaseType>
	 **/
	private ArrayList<DatabaseType> getDataByCriteria(TableType ttype,
			ArrayList<SearchEntity> criteria) {
		String sqlQuery = "SELECT * FROM " + ttype.toString() + getSQLWhereFromCriteria(criteria);
		Cursor cursor = this.getCursor(sqlQuery);
		return cursorToArrayList(cursor, ttype);
	}

	/**
	 * Construct the "WHERE" part of query given SearchEntity criteria
	 * @param criteria	An ArrayList of SearchEntity criteria
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
	 * Return a single User from the database by id
	 * @param id
	 * @return a single User
	 **/
	public User getUser(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM users WHERE (id='" + id + "')");
		return getUsersFromCursor(cursor).get(0);
	}

	/**
	 * Return a single Comment from the database by id
	 * @param id
	 * @return a single Comment
	 **/
	public Comment getComment(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM comments WHERE (id='"	+ id + "')");
		return getCommentsFromCursor(cursor).get(0);
	}

	/**
	 * Return SQL COUNT from criteria
	 * @param ttype		TableType enum
	 * @param criteria	An ArrayList of SearchEntity criteria
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
	 * @param id
	 * @return a single Post
	 **/
	public Post getPost(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM posts WHERE (id='" + id + "')");
		try {
			return getPostsFromCursor(cursor).get(0);
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getPost(id) : "+e);
			return null;
		}
	}

	/**
	 * Return a single Vote from the database by id
	 * @param id
	 * @return a single Vote
	 **/
	public Vote getVote(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM votes WHERE (id='" + id + "')");
		return getVotesFromCursor(cursor).get(0);
	}

	/**
	 * Return a single MapTag from the database by id
	 * @param id
	 * @return a single MapTags
	 **/
	public MapTags getMapTags(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM " + MapTags.TABLE_NAME + " WHERE (id=" + id + ")");
		try {
			return getMappingTagsFromCursor(cursor).get(0);
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getMapTags(id) : "+e);
			return null;
		}
	}
	
	/**
	 * Return the last index of a given table
	 * @param ttype		TableType enum
	 * @return int
	 **/
	public int getLastIndex(TableType ttype) {
		String sqlMessage;
		sqlMessage = "SELECT id FROM " + ttype.toString() + " ORDER BY id DESC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Return the first index of a given table
	 * @param ttype		TableType enum
	 * @return int
	 **/	
	public int getFirstIndex(TableType ttype) {
		String sqlMessage;
		sqlMessage = "SELECT id FROM " + ttype.toString() + " ORDER BY id ASC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Return the last index from Posts with non-null tags
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
	 * @param int
	 * @return int
	 **/
	public int getNextPostIdWithTagNotNull(int id) {
		String sqlMessage;
		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL AND id > " + id + " ORDER BY ID ASC LIMIT 1";
		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Get the four top-related tags of a tag (US39)
	 * @param refrerenceTag	tag
	 * @return ArrayList of Strings
	 **/
	public ArrayList<String> getTopRelatedTags(String referenceTag){

		String sqlQuery;
		ArrayList<String> relatedTags = new ArrayList<String>();
		
		sqlQuery = "SELECT * FROM " + TableType.mapping_tags + " WHERE (TAG1 LIKE '" + referenceTag + "' " +
				"OR TAG2 LIKE '" + referenceTag + "') AND TAG1 NOT LIKE TAG2 ORDER BY " + MapTags.KEY_COUNT_APPEARANCE + " DESC LIMIT 4";

		try{
		Cursor cursor = this.getCursor(sqlQuery);
		ArrayList<MapTags> tagsDBType = getMappingTagsFromCursor(cursor);
				
		for (MapTags relatedMapTag : tagsDBType) { 
			if(referenceTag.equals(relatedMapTag.getTag2())){
				relatedTags.add(relatedMapTag.getTag1());
			}
			else{
				relatedTags.add(relatedMapTag.getTag2());
			}
		}
		Log.v("DEBUG", "Tag_1.4.1");
		return relatedTags;
		}catch(Exception e){
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getTopRelatedTags(String) : " 
					+ sqlQuery + ", ERROR: " + e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * Return Posts by tag or tag combination (US1)
	 * @param ArrayList<String>	of tags
	 * @return ArrayList<Post>
	 **/
	public ArrayList<Post> getPostsByTags(ArrayList<String> tags){
		ArrayList<Post> posts = new ArrayList<Post>();
		String sqlMessage;
		int i=0;
		
		sqlMessage = "SELECT * FROM " + TableType.posts + " WHERE ";
		
		for (String tag : tags) {
			if (i>0){
				sqlMessage += " AND ";
			}
			sqlMessage += Post.KEY_TAGS + " LIKE '%" + tag + "%'";
			i++;
		}
		Cursor cursor = this.getCursor(sqlMessage);
		posts = getPostsFromCursor(cursor);		
		return posts;
	}
	
	
	/**
	 * Returns all associated answers to a question/post 
	 * @param questionId	the post id
	 * @return an ArrayList<Post> with answers
	 */
	public ArrayList<Post> getAnswers(int questionId) {
		String sqlQuery = "SELECT * FROM " + TableType.posts + " WHERE ('" + Post.KEY_PARENT_ID + "'='"
				+ questionId + "')";
		Cursor cursor = this.getCursor(sqlQuery);
		return getPostsFromCursor(cursor);
	}
	
	/**
	 * Get questions where the given words are contained in either the title or the body
	 * @param oWords the words that have to be contained in a question's title or body to be returned by this method
	 */
	public ArrayList<Post> getQuestionsByFreeText(String[] oWords)
	{
		// create sql statement
		String sSqlMessage = "SELECT * FROM " + TableType.posts;
		sSqlMessage += " WHERE " + Post.KEY_POST_TYPE_ID + " = '1'";
		for(int i = 0; i < oWords.length; i++)
		{ 
			sSqlMessage += " AND (" + Post.KEY_TITLE + " LIKE '%" + oWords[i] + "%'";
			sSqlMessage += " OR " + Post.KEY_BODY + " LIKE '%" + oWords[i] + "%')";
		}
		
		// execute sql statement
		Cursor oCursor = this.getCursor(sSqlMessage);
		
		// convert result to  an array list of Posts
		ArrayList<Post> oQuestions = this.getPostsFromCursor(oCursor);		
		
		return oQuestions;
	}
	

	/**
	 * Get the alphabetically next and previous tags (US51) 
	 * @param string	tag
	 * @return an ArrayList<String> with tags
	 */
	public ArrayList<String> getNextAndPreviousTags(String searchTag) {		
		ArrayList<Tag> dbNextTag = new ArrayList<Tag>();
		ArrayList<Tag> dbPreviousTag = new ArrayList<Tag>();		
		ArrayList<String> nextAndPreviousTags = new ArrayList<String>();
		String sqlQuery = "";
		Cursor tagCursor= null;

		//Selecting next tag, alphabetically
		sqlQuery = "SELECT * FROM " +
				"(SELECT * FROM "+ TableType.tags +
				" ORDER BY "+ Tag.KEY_TAG +")" + 
				" WHERE "+ Tag.KEY_TAG+" > '"+ searchTag +"' LIMIT 1";

		try {	
			//Executing the SQL-query
			tagCursor = this.getCursor(sqlQuery);
			//Nothing is returned as "next" for the last element of the table, fetch first element
			if(tagCursor.getCount() == 0) {
				sqlQuery = "SELECT * FROM (SELECT * FROM " + TableType.tags +
						" ORDER BY " + Tag.KEY_TAG + ") LIMIT 1";
				tagCursor = this.getCursor(sqlQuery);
			}
			//Saving the cursor as a DatabaseType ArrayList
			dbNextTag = getTagsFromCursor(tagCursor);
		}
		catch(NullPointerException npe) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getNextAndPreviousTags(String) : a NullPointer, next tag : " 
					+ npe.getLocalizedMessage());
		}
		catch(ArrayIndexOutOfBoundsException aie) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getNextAndPreviousTags(String) : an IndexOutOfBounds, next tag : " 
					+ aie.getLocalizedMessage());
		}

		//Selecting previous tag, alphabetically
		sqlQuery = "SELECT * FROM " +
				"(SELECT * FROM "+ TableType.tags +
				" ORDER BY "+ Tag.KEY_TAG +" DESC)" + 
				" WHERE "+ Tag.KEY_TAG+" < '"+ searchTag +"' LIMIT 1";

		try {
			//Executing the SQL-query
			tagCursor = this.getCursor(sqlQuery);
			//Nothing is returned as "previous" for the first element of the table, fetch last element
			if(tagCursor.getCount() == 0) {
					sqlQuery = "SELECT * FROM (SELECT * FROM "+ TableType.tags +
							" ORDER BY "+ Tag.KEY_TAG +" DESC) LIMIT 1";
					tagCursor = this.getCursor(sqlQuery);
			}
			//Saving the cursor as a DatabaseType ArrayList
			dbPreviousTag = getTagsFromCursor(tagCursor);
		}
		catch(NullPointerException npe) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getNextAndPreviousTags(String) : a NullPointer, previous tag : " 
					+ npe.getLocalizedMessage());
		}
		catch(ArrayIndexOutOfBoundsException aie) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getNextAndPreviousTags(String) : an IndexOutOfBounds, previous tag : " 
					+ aie.getLocalizedMessage());
		}

		//Adding to String ArrayList
		nextAndPreviousTags.add(dbNextTag.get(0).getTag());
		nextAndPreviousTags.add(dbPreviousTag.get(0).getTag());		

		return nextAndPreviousTags;		
	}

	/**
	 * Returns the tag that's name match the given string
	 * @param sName string the tag name should match
	 * @return the tag that's name match the given string
	 */
	public String getTagByName(String sName)
	{
		String sSqlMessage;

		sSqlMessage = "SELECT " + Tag.KEY_TAG + " FROM " + TableType.tags;
		sSqlMessage += " WHERE " +  Tag.KEY_TAG + " LIKE '" + sName + "' LIMIT 1";

		Cursor oCursor = this.getCursor(sSqlMessage);
		oCursor.moveToFirst();
		if(oCursor.getCount() > 0)
			{return oCursor.getString(0);}
		else
			{return "";}
	}

	/**
	 * Returns a tag by id
	 * @param int id
	 * @return String tag
	 */
	public String getTag(int id) {
		String tag = null;
		String sqlQuery = "SELECT " + Tag.KEY_TAG + " FROM " + TableType.tags + " WHERE (id='" + id + "')";
		try {
			Cursor cursor = this.getCursor(sqlQuery);
			cursor.moveToFirst();
			tag = cursor.getString(0);
		} catch (Exception e) {
			Log.e("SQLite EXCEPTION", "DatabaseAdapter.getTag(int) : Tag not found : " + e.getMessage());
			//e.printStackTrace();
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
	public void insertSql(String tableName, HashMap<String, String> columnValues) {
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
		} catch (Exception e) {
			System.out.println("INSERT ERROR: " + e.getMessage());
		}

	}

	// for columnValues: key = columnName and values = columnValue
	public void updateSql(String tableName,
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
		} catch (Exception e) {
			System.out.println("ERROR IN UPDATE: " + e.getMessage());
		}
	}
	
	/**
	 * Returns tags in alphabetical order
	 * @param iLimit limits number of returned tags
	 * @return tags in alphabetical order
	 */
	public ArrayList<String> getTagsInAlphabeticalOrder(int iLimit){
		String sSqlMessage;

		sSqlMessage = "SELECT " + Tag.KEY_TAG + " FROM " + TableType.tags;
		sSqlMessage += " ORDER BY " +  Tag.KEY_TAG + " ASC";
		if(iLimit > 0){
			sSqlMessage += " LIMIT " +  iLimit;
		}

		Cursor oCursor = this.getCursor(sSqlMessage);
		oCursor.moveToFirst();
		ArrayList<String> oTags = new ArrayList<String>();
		while(oCursor.moveToNext()){
			oTags.add(oCursor.getString(0));
		}
		
		return oTags;
	}

	public Tag getTagObjectByName(String tagName) {
		Tag tag = null;
		String sqlQuery = "SELECT * FROM " + Tag.TABLE_NAME + " WHERE (tag='" + tagName + "')";
		try {
			Cursor cursor = this.getCursor(sqlQuery);
			ArrayList<Tag> tagDBTs = getTagsFromCursor(cursor);
			
			tag = tagDBTs.get(0);
		} catch (Exception e) {
			Log.e("Method getTag in DatabaseAdaptor", "Tag not found" + e.getMessage());
			//e.printStackTrace();
		}
		return tag;
	}
}
