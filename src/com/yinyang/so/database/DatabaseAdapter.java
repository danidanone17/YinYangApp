package com.yinyang.so.database;

import java.io.IOException;
import java.lang.reflect.Array;
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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Columns;
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

	private ArrayList<DatabaseType> cursorToArrayList(Cursor cursor) {
		ArrayList<DatabaseType> list = new ArrayList<DatabaseType>();
		if (cursor != null) {

			// returns different classes depending on the number of
			// columns in the cursor
			switch (cursor.getColumnCount()) {

			// users table has 13 fields
			case 13:
				while (cursor.moveToNext()) {
					User user = new User(cursor);
					list.add(user);
				}
				break;

			// comments table has 6 fields
			case 6:
				while (cursor.moveToNext()) {
					Comment comment = new Comment(cursor);
					list.add(comment);
				}
				break;

			// posts table has 20 fields
			case 20:
				while (cursor.moveToNext()) {
					Post post = new Post(cursor);
					list.add(post);
				}
				break;

			// votes table has 4 fields
			case 4:
				String[] columnNames = cursor.getColumnNames();
				for (String columnName : columnNames) {
					if (columnName.equals("post_id")) {
						while (cursor.moveToNext()) {
							Vote vote = new Vote(cursor);
							list.add(vote);
						}
					}
					if (columnName.equals("tag1")) {
						while (cursor.moveToNext()) {
							MapTags mapTags = new MapTags(cursor);
							list.add(mapTags);
						}
					}
				}

				break;

			case 2:
				while (cursor.moveToNext()) {
					Tag vote = new Tag(cursor);
					list.add(vote);
				}
				break;

			}
		}
		return list;
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

	public ArrayList<DatabaseType> getUsers() {
		Cursor cursor = this.getCursor("SELECT * FROM users LIMIT 20");
		return cursorToArrayList(cursor);
	}

	/*
	 * old version, changed 04/19/2013 public ArrayList<DatabaseType>
	 * getDataByCriteria(String tableName, HashMap<String, String> criteria) {
	 * String where = " WHERE "; Iterator<Entry<String,String>> iterator =
	 * criteria.entrySet().iterator(); while (iterator.hasNext()) {
	 * Entry<String,String> criterion = iterator.next(); where+=
	 * criterion.getKey()+" LIKE '%"+criterion.getValue()+"%'"; if
	 * (iterator.hasNext()) {where+= " and ";} } String sqlQuery =
	 * "SELECT * FROM " + tableName + where; Log.e("", "SQL QUERY : " +
	 * sqlQuery); Cursor cursor = this.getCursor(sqlQuery); return
	 * cursorToArrayList(cursor); }
	 */

	public ArrayList<DatabaseType> getDataByCriteria(String tableName,
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

		String sqlQuery = "SELECT * FROM " + tableName + where;
		Log.e("", "SQL QUERY : " + sqlQuery);
		Cursor cursor = this.getCursor(sqlQuery);
		return cursorToArrayList(cursor);
	}

	public ArrayList<DatabaseType> getTable(String tableName) {
		String sqlMessage;

		sqlMessage = "SELECT * FROM " + tableName;

		Cursor cursor = this.getCursor(sqlMessage);
		return cursorToArrayList(cursor);
	}

	// HashMap shall contain: key = columnName and value=columnType
	public void createTable(String tableName, String idName,
			HashMap<String, String> columns) {
		String sqlMessage;

		sqlMessage = "CREATE TABLE " + tableName + " ( ";
		sqlMessage = sqlMessage + idName + " int PRIMARY KEY ";

		Iterator it = columns.entrySet().iterator();
		Map.Entry pairs;

		while (it.hasNext()) {
			pairs = (Map.Entry) it.next();
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

		Iterator it = columnValues.entrySet().iterator();
		Map.Entry pairs;

		while (it.hasNext()) {
			pairs = (Map.Entry) it.next();
			sqlMessage += ", " + pairs.getKey();
			values += ", '" + pairs.getValue() + "'";
		}
		
		values += ")";

		sqlMessage += values;

		try {
			System.out.println("INSERT STATEMENT: " + sqlMessage);
			mDb.execSQL(sqlMessage);
		} catch (Exception e) {
			Log.e("DatabaseAdaptor","INSERT ERROR: " + e.getMessage());
		}

	}

	// for columnValues: key = columnName and values = columnValue
	public void updateSql(String tableName,
			HashMap<String, String> columnValues, String whereClause) {
		String sqlMessage;

		sqlMessage = "UPDATE " + tableName + " SET ";

		Iterator it = columnValues.entrySet().iterator();
		Map.Entry pairs;

		while (it.hasNext()) {
			pairs = (Map.Entry) it.next();
			sqlMessage += pairs.getKey() + "='" + pairs.getValue() + "'";

			if (it.hasNext()) {
				sqlMessage += ", ";
			}
		}

		sqlMessage += " WHERE " + whereClause;

		try {
			mDb.execSQL(sqlMessage);
			System.out.println("(after update) UPDATE MESSAGE: " + sqlMessage);
		} catch (Exception e) {
			Log.e("ERROR IN sqlUPDATE","MESSAGE: " + e.getMessage());
		}
	}

	public User getUser(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM users WHERE (id='" + id
				+ "')");
		return (User) cursorToArrayList(cursor).get(0);
	}

	public ArrayList<DatabaseType> getComments() {
		Cursor cursor = this.getCursor("SELECT * FROM comments LIMIT 20");
		return cursorToArrayList(cursor);
	}

	public Comment getComment(int id) {

		Cursor cursor = this.getCursor("SELECT * FROM comments WHERE (id='"
				+ id + "')");
		return (Comment) cursorToArrayList(cursor).get(0);
	}

	public ArrayList<DatabaseType> getPosts() {
		Cursor cursor = this.getCursor("SELECT * FROM posts LIMIT 20");
		return cursorToArrayList(cursor);
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
		return cursorToArrayList(cursor);
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
			return (Post) cursorToArrayList(cursor).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<DatabaseType> getVotes() {
		Cursor cursor = this.getCursor("SELECT * FROM votes LIMIT 20");
		return cursorToArrayList(cursor);
	}

	public Vote getVote(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM votes WHERE (id='" + id
				+ "')");
		return (Vote) cursorToArrayList(cursor).get(0);
	}

	public MapTags getMapTags(int id) {
		Cursor cursor = this.getCursor("SELECT * FROM " + MapTags.TABLE_NAME
				+ " WHERE (id=" + id + ")");
		try {
			return (MapTags) cursorToArrayList(cursor).get(0);
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

		sqlMessage = "SELECT id FROM posts WHERE tags <> 'NULL' ORDER BY id DESC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public int getFirstIndexFromPostsWithTagsNotNull() {
		String sqlMessage;

		sqlMessage = "SELECT id FROM posts WHERE tags <> 'NULL' ORDER BY id ASC LIMIT 1";

		Cursor cursor = this.getCursor(sqlMessage);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public int getNextPostIdWithTagNotNull(int id) {
		String sqlMessage;

		sqlMessage = "SELECT id FROM posts WHERE tags <> 'NULL' AND id > " + id + " ORDER BY ID ASC LIMIT 1";

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
		ArrayList<DatabaseType> tagsDBType = cursorToArrayList(cursor);
		
		
		for (DatabaseType tagDBType : tagsDBType) {
			relatedMapTag = (MapTags) tagDBType; 
			
			if(referenceTag.equals(relatedMapTag.getTag2())){
				relatedTags.add(relatedMapTag.getTag1());
			}
			else{
				relatedTags.add(relatedMapTag.getTag2());
			}
		}
		if (relatedTags == null){
			Log.v("DEBUG", "relatedTags");
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
		ArrayList<DatabaseType> tagsDBType = cursorToArrayList(cursor);
		
		for (DatabaseType tagDBType : tagsDBType) {
			posts.add((Post) tagDBType); 
		}
		
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
	public ArrayList<DatabaseType> getAnswers(int questionId) {
		String sqlQuery = "SELECT * FROM posts WHERE ('parent id'='"
				+ questionId + "')";
		Cursor cursor = this.getCursor(sqlQuery);
		return cursorToArrayList(cursor);
	}
	
	public Tag getTag(int id) {
		Tag tag = null;
		String sqlQuery = "SELECT * FROM " + Tag.TABLE_NAME + " WHERE id=" + id;
		try {
			Cursor cursor = this.getCursor(sqlQuery);
			tag = (Tag) cursorToArrayList(cursor).get(0);
		} catch (Exception e) {
			Log.e("Method getTag in DatabaseAdaptor", "Tag not found" + e.getMessage());
			//e.printStackTrace();
		}
		return tag;
	}
	
}
