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

	private ArrayList<Post> getPostsFromCursor(Cursor cursor) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.posts)) {
			posts.add((Post) db); }
		return posts; }
	
	private ArrayList<User> getUsersFromCursor(Cursor cursor) {
		ArrayList<User> users = new ArrayList<User>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.users)) {
			users.add((User) db); }
		return users; }
	
	private ArrayList<Comment> getCommentsFromCursor(Cursor cursor) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.comments)) {
			comments.add((Comment) db); }
		return comments; }

	private ArrayList<Vote> getVotesFromCursor(Cursor cursor) {
		ArrayList<Vote> votes = new ArrayList<Vote>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.votes)) {
			votes.add((Vote) db); }
		return votes; }

	private ArrayList<Tag> getTagsFromCursor(Cursor cursor) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.tags)) {
			tags.add((Tag) db); }
		return tags; }

	private ArrayList<MapTags> getMappingTagsFromCursor(Cursor cursor) {
		ArrayList<MapTags> mapTags = new ArrayList<MapTags>();
		for (DatabaseType db : cursorToArrayList(cursor, TableType.mapping_tags)) {
			mapTags.add((MapTags) db); }
		return mapTags; }

	
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

	// wrapper for getDataByCriteria<Post>
	public ArrayList<Post> getPostsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (DatabaseType db : getDataByCriteria(TableType.posts, criteria)) {
			posts.add((Post) db); }
		return posts; }

	// wrapper for getDataByCriteria<User>
	public ArrayList<User> getUsersByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<User> users = new ArrayList<User>();
		for (DatabaseType db : getDataByCriteria(TableType.users, criteria)) {
			users.add((User) db); }
		return users; }

	// wrapper for getDataByCriteria<Tag>
	public ArrayList<Tag> getTagsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (DatabaseType db : getDataByCriteria(TableType.tags, criteria)) {
			tags.add((Tag) db); }
		return tags; }

	// wrapper for getDataByCriteria<MappingTags>
	public ArrayList<MapTags> getMappingTagsByCriteria(ArrayList<SearchEntity> criteria) {
		ArrayList<MapTags> mappingTags = new ArrayList<MapTags>();
		for (DatabaseType db : getDataByCriteria(TableType.mapping_tags, criteria)) {
			mappingTags.add((MapTags) db); }
		return mappingTags; }

	private ArrayList<DatabaseType> getDataByCriteria(TableType ttype,
			ArrayList<SearchEntity> criteria) {
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
		String sqlQuery = "SELECT * FROM " + ttype.getValue() + where;
		Log.e("", "SQL QUERY : " + sqlQuery);
		Cursor cursor = this.getCursor(sqlQuery);
		return cursorToArrayList(cursor, ttype);
	}
	
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

	public ArrayList<User> getUsers() {
		Cursor cursor = this.getCursor("SELECT * FROM users LIMIT 20");
		return getUsersFromCursor(cursor);
	}

	public ArrayList<DatabaseType> getTable(String tableName) {
		String sqlMessage;

		sqlMessage = "SELECT * FROM " + tableName;

		Cursor cursor = this.getCursor(sqlMessage);
		return cursorToArrayList(cursor, TableType.valueOf(tableName));
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

	// for columnValues: key = columnName and values = columnValue
	public void insertSql(String tableName, HashMap<String, String> columnValues) {
		String sqlMessage, values;
		int id;

		try {
			id = this.getLastIndex(tableName) + 1;
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

	public User getUser(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM users WHERE (id='" + id
				+ "')");
		return getUsersFromCursor(cursor).get(0);
	}

	public ArrayList<Comment> getComments() {
		Cursor cursor = this.getCursor("SELECT * FROM comments LIMIT 20");
		return getCommentsFromCursor(cursor);
	}

	public Comment getComment(int id) {

		Cursor cursor = this.getCursor("SELECT * FROM comments WHERE (id='"
				+ id + "')");
		return getCommentsFromCursor(cursor).get(0);
	}

	public ArrayList<Post> getPosts() {
		Cursor cursor = this.getCursor("SELECT * FROM posts LIMIT 20");
		return getPostsFromCursor(cursor);
	}

	// constructs the SQL statement for getting specific columns from a table
	public ArrayList<DatabaseType> getColumnsFromTable(String tabelName,
			ArrayList<String> columnNames) {

		String message = "SELECT * ";
		// commented because it does not work (the return
		// ArrayList<DatabaseType>
		/*
		 * int i=0; for (String columnName : columnNames) { if(i>0){ message +=
		 * ", "; } message += " " + columnName; i++; }
		 */
		message += " FROM " + tabelName;

		System.out.println("COLUMNS SELECT: " + message);
		Cursor cursor = this.getCursor(message);
		return cursorToArrayList(cursor, TableType.valueOf(tabelName));
	}

	public int getCountByCriteria(String tableName,
			ArrayList<SearchEntity> searchCriteria) {
		String sqlMessage;

		sqlMessage = "SELECT * FROM " + tableName;

		sqlMessage += " WHERE ";

		int i = 0;

		for (SearchEntity searchEntity : searchCriteria) {
			if (i > 0) {
				sqlMessage += " AND ";
			}

			if (searchEntity.getMeanOfSearch().equals(MeanOfSearch.contained)) {
				sqlMessage += searchEntity.getKey() + " LIKE '%"
						+ searchEntity.getValue() + "%'";
			}
			if (searchEntity.getMeanOfSearch().equals(MeanOfSearch.exact)) {
				sqlMessage += searchEntity.getKey() + " LIKE '"
						+ searchEntity.getValue() + "'";
			}

			i++;
		}

		Cursor cursor = this.getCursor(sqlMessage);
		return cursor.getCount();
	}

	public Post getPost(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM posts WHERE (id='" + id
				+ "')");
		try {
			return getPostsFromCursor(cursor).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Vote> getVotes() {
		Cursor cursor = this.getCursor("SELECT * FROM votes LIMIT 20");
		return getVotesFromCursor(cursor);
	}

	public Vote getVote(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM votes WHERE (id='" + id
				+ "')");
		return getVotesFromCursor(cursor).get(0);
	}

	public MapTags getMapTags(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM " + MapTags.TABLE_NAME
				+ " WHERE (id=" + id + ")");
		try {
			return getMappingTagsFromCursor(cursor).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getLastIndex(String table) {
		String sqlMessage;

		sqlMessage = "SELECT id FROM " + table + " ORDER BY id DESC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	public int getFirstIndex(String table) {
		String sqlMessage;

		sqlMessage = "SELECT id FROM " + table + " ORDER BY id ASC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}


	public int getLastIndexFromPostsWithTagsNotNull() {
		String sqlMessage;

		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL ORDER BY id DESC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public int getFirstIndexFromPostsWithTagsNotNull() {
		String sqlMessage;

		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL ORDER BY id ASC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public int getNextPostIdWithTagNotNull(int id) {
		String sqlMessage;

		sqlMessage = "SELECT id FROM posts WHERE tags IS NOT NULL AND id > " + id + " ORDER BY ID ASC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public void dropTable(String tableName) {
		String sqlMessage;
		sqlMessage = "DROP TABLE IF EXISTS " + tableName;

		mDb.execSQL(sqlMessage);
	}

	//get 4 top related tags to a reference tag --- User Story 39
	public ArrayList<String> getTopRelatedTags(String referenceTag){
		String sqlMessage;
		MapTags relatedMapTag;
		ArrayList<String> relatedTags = new ArrayList<String>();
		
		sqlMessage = "SELECT * FROM " + MapTags.TABLE_NAME + " WHERE (TAG1 LIKE '" + referenceTag + "' " +
				"OR TAG2 LIKE '" + referenceTag + "') AND TAG1 NOT LIKE TAG2 ORDER BY " + MapTags.KEY_COUNT_APPEARANCE + " DESC LIMIT 4";

		try{
		Cursor cursor = this.getCursor(sqlMessage);
		ArrayList<MapTags> tagsDBType = getMappingTagsFromCursor(cursor);
		
		
		for (DatabaseType tagDBType : tagsDBType) {
			relatedMapTag = (MapTags) tagDBType; 
			
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
			System.out.println("ERROR IN GETTING THE RELATED TAGS --- SQL: " + sqlMessage + ", ERROR: "+ e.getLocalizedMessage());
		}
		return null;
	}

	//get Posts by tag or tag combination -- User Story 1
	public ArrayList<Post> getPostsByTags(ArrayList<String> tags){
		ArrayList<Post> posts = new ArrayList<Post>();
		String sqlMessage;
		int i=0;
		
		sqlMessage = "SELECT * FROM " + Post.TABLE_NAME + " WHERE ";
		
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
	
	
	public void emptyTable(String tableName) {
		String sqlMessage;
		sqlMessage = "DELETE FROM " + tableName;

		mDb.execSQL(sqlMessage);
	}

	/**
	 * Returns all associated answers to a question/post
	 * 
	 * @param questionId
	 *            the post id
	 * @return an ArrayList<DatabaseType> with Post
	 */
	public ArrayList<Post> getAnswers(int questionId) {
		String sqlQuery = "SELECT * FROM posts WHERE ('parent id'='"
				+ questionId + "')";
		Cursor cursor = this.getCursor(sqlQuery);
		return getPostsFromCursor(cursor);
	}
	
	/**
	 * Gets questions where the given words are contained in either the title or the body
	 * @param oWords the words that have to be contained in a question's title or body to be returned by this method
	 */
	public ArrayList<Post> getQuestionsByFreeText(String[] oWords)
	{
		// create sql statement
		String sSqlMessage = "SELECT * FROM " + Post.TABLE_NAME;
		sSqlMessage += " WHERE " + Post.KEY_POST_TYPE_ID + " = '1'";
		for(int i = 0; i < oWords.length; i++)
		{ 
			sSqlMessage += " AND (" + Post.KEY_TITLE + " LIKE '%" + oWords[i] + "%'";
			sSqlMessage += " OR " + Post.KEY_BODY + " LIKE '%" + oWords[i] + "%')";
		}
		
		// execute sql statement
		Cursor oCursor = this.getCursor(sSqlMessage);
		
		// convert result to  an array list of Posts
		ArrayList<Post> oQuestions = new ArrayList<Post>();		
		for (DatabaseType tagDBType : getPostsFromCursor(oCursor)) {
			oQuestions.add((Post) tagDBType); 
		}
		
		return oQuestions;
	}
	

	//User story 51 - Get next and previous tag alphabetically
	public ArrayList<String> getNextAndPreviousTags(String searchTag) {		
		ArrayList<Tag> dbNextTag = new ArrayList<Tag>();
		ArrayList<Tag> dbPreviousTag = new ArrayList<Tag>();		
		ArrayList<String> nextAndPreviousTags = new ArrayList<String>();
		String sqlQuery = "";
		Cursor tagCursor= null;

		//Selecting next tag, alphabetically
		sqlQuery = "SELECT * FROM " +
				"(SELECT * FROM "+ Tag.TABLE_NAME  +
				" ORDER BY "+ Tag.KEY_TAG +")" + 
				" WHERE "+ Tag.KEY_TAG+" > '"+ searchTag +"' LIMIT 1";

		try {	
			//Executing the SQL-query
			tagCursor = this.getCursor(sqlQuery);
			//Nothing is returned as "next" for the last element of the table, fetch first element
			if(tagCursor.getCount() == 0) {
				sqlQuery = "SELECT * FROM (SELECT * FROM " + Tag.TABLE_NAME +
						" ORDER BY " + Tag.KEY_TAG + ") LIMIT 1";
				tagCursor = this.getCursor(sqlQuery);
			}
			//Saving the cursor as a DatabaseType ArrayList
			dbNextTag = getTagsFromCursor(tagCursor);					
		}
		catch(NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("!!! NullPointerException in getNextAndPreviousTags - next tag, error:" + npe.getMessage());
		}
		catch(ArrayIndexOutOfBoundsException aie) {
			aie.printStackTrace();
			System.out.println("!!! ArrayIndexOutOfBoundsException in getNextAndPreviousTags - next tag, error:" + aie.getMessage());			
		}

		//Selecting previous tag, alphabetically
		sqlQuery = "SELECT * FROM " +
				"(SELECT * FROM "+ Tag.TABLE_NAME +
				" ORDER BY "+ Tag.KEY_TAG +" DESC)" + 
				" WHERE "+ Tag.KEY_TAG+" < '"+ searchTag +"' LIMIT 1";

		try {
			//Executing the SQL-query
			tagCursor = this.getCursor(sqlQuery);
			//Nothing is returned as "previous" for the first element of the table, fetch last element
			if(tagCursor.getCount() == 0) {
					sqlQuery = "SELECT * FROM (SELECT * FROM "+ Tag.TABLE_NAME  +
							" ORDER BY "+ Tag.KEY_TAG +" DESC) LIMIT 1";
					tagCursor = this.getCursor(sqlQuery);
			}
			//Saving the cursor as a DatabaseType ArrayList
			dbPreviousTag = getTagsFromCursor(tagCursor);
		}
		catch(NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("!!! NullPointerException in getNextAndPreviousTags - previous tag, error:" + npe.getMessage());
		}
		catch(ArrayIndexOutOfBoundsException aie) {
			aie.printStackTrace();
			System.out.println("!!! ArrayIndexOutOfBoundsException in getNextAndPreviousTags - previous tag, error:" + aie.getMessage());			
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

		sSqlMessage = "SELECT " + Tag.KEY_TAG + " FROM " + Tag.TABLE_NAME;
		sSqlMessage += " WHERE " +  Tag.KEY_TAG + " LIKE '" + sName + "' LIMIT 1";

		Cursor oCursor = this.getCursor(sSqlMessage);
		oCursor.moveToFirst();
		if(oCursor.getCount() > 0)
			{return oCursor.getString(0);}
		else
			{return "";}
	}

	public String getTag(int id) {
		String tag = null;
		String sqlQuery = "SELECT " + Tag.KEY_TAG + " FROM " + Tag.TABLE_NAME + " WHERE (id='" + id + "')";
		try {
			Cursor cursor = this.getCursor(sqlQuery);
			cursor.moveToFirst();

			tag = cursor.getString(0);
		} catch (Exception e) {
			Log.e("Method getTag in DatabaseAdaptor", "Tag not found" + e.getMessage());
			//e.printStackTrace();
		}
		return tag;
	}
}
