package com.example.yinyangapp.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.example.yinyangapp.databaseentities.Comment;
import com.example.yinyangapp.databaseentities.DatabaseType;
import com.example.yinyangapp.databaseentities.Post;
import com.example.yinyangapp.databaseentities.User;
import com.example.yinyangapp.databaseentities.Vote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter 
{
    protected static final String TAG = "DataAdapter";
    
    private final Context mContext;
    private SQLiteDatabase mDb;
    private SoData mDbHelper;

    public DatabaseAdapter(Context context) 
    {
        this.mContext = context;
        mDbHelper = new SoData(mContext);
    }

    public DatabaseAdapter createDatabase() throws SQLException 
    {
        try 
        {
            mDbHelper.createDataBase();
        } 
        catch (IOException mIOException) 
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DatabaseAdapter open() throws SQLException 
    {
        try 
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } 
        catch (SQLException mSQLException) 
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() 
    {
        mDbHelper.close();
    }
    
    private ArrayList<DatabaseType> cursorToArrayList(Cursor cursor)
    {
    		ArrayList<DatabaseType> list = new ArrayList<DatabaseType>();
    		if ( cursor != null ) {

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
    				while (cursor.moveToNext()) {
    					Vote vote = new Vote(cursor);
    			        list.add(vote);
    				}
    				break;
    				
    			}
     }
     return list;
    }
    
     private Cursor getCursor(String sqlString) {
    	 try
         {
             Cursor mCur = mDb.rawQuery(sqlString, null);
             return mCur;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     private void nonReturnSqlStatement(String sqlString) {
    	 try
         {
             mDb.execSQL(sqlString, null);
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     public ArrayList<DatabaseType> getUsers()
     {
         Cursor cursor = this.getCursor("SELECT * FROM users LIMIT 20");
         return cursorToArrayList(cursor);
     }
     
     /*old version, changed 04/19/2013
     public ArrayList<DatabaseType> getDataByCriteria(String tableName, HashMap<String, String> criteria)
     {
    	 String where = " WHERE ";
    	 Iterator<Entry<String,String>> iterator = criteria.entrySet().iterator();
    	 while (iterator.hasNext()) {
    		 Entry<String,String> criterion = iterator.next();
    		 where+= criterion.getKey()+" LIKE '%"+criterion.getValue()+"%'";
    		 if (iterator.hasNext()) {where+= " and ";}
    	 }
    	 String sqlQuery = "SELECT * FROM " + tableName + where;
    	 Log.e("", "SQL QUERY : " + sqlQuery);
    	 Cursor cursor = this.getCursor(sqlQuery);
         return cursorToArrayList(cursor);
     }*/
     
     public ArrayList<DatabaseType> getDataByCriteria(String tableName, ArrayList<SearchEntity> criteria)
     {
    	 String where = " WHERE ";
    	 int i = 0;
    	 
    	 for (SearchEntity searchEntity : criteria) {
    		 if (i > 0){
    			 where += " AND ";
    		 }
    		 
			if(searchEntity.getMeanOfSearch().equals(MeanOfSearch.contained)){
				where += searchEntity.getKey() + " LIKE '%" + searchEntity.getValue() + "%'";
			}
			if(searchEntity.getMeanOfSearch().equals(MeanOfSearch.exact)){
				where += searchEntity.getKey() + " LIKE '" + searchEntity.getValue() + "'";
			}
			i++;
		}
    	 
    	 String sqlQuery = "SELECT * FROM " + tableName + where;
    	 Log.e("", "SQL QUERY : " + sqlQuery);
    	 Cursor cursor = this.getCursor(sqlQuery);
         return cursorToArrayList(cursor);
     }
     
     public ArrayList<DatabaseType> getTable(String tableName){
    	 String sqlMessage;
    	 
    	 sqlMessage = "SELECT * FROM " + tableName;
    	
    	 Cursor cursor = this.getCursor(sqlMessage);
         return cursorToArrayList(cursor);
     }
     
     //HashMap shall contain: key = columnName and value=columnType
     public void createTable(String tableName, String idName, HashMap<String,String> columns){
    	 String sqlMessage;
    	 
    	 sqlMessage = "CREATE TABLE " + tableName + "( ";
    	 sqlMessage = sqlMessage + idName + " " + "int NOT NULL AUTO_INCREMENT PRIMARY KEY ";
    	 
    	 Iterator it = columns.entrySet().iterator();
    	 Map.Entry pairs;
    	 
    	 while (it.hasNext()) {
    	     pairs = (Map.Entry)it.next();
    	     sqlMessage = sqlMessage + ", ";
    		 sqlMessage = sqlMessage + pairs.getKey() + " " + pairs.getValue();
    	 }
    	 
    	 sqlMessage = sqlMessage + ")";
    	 
    	 this.nonReturnSqlStatement(sqlMessage);
     }
     
     
     //for columnValues: key = columnName and values = columnValue
     public void insertSql(String tableName, HashMap<String, String> columnValues){
    	 ContentValues contentValues = new ContentValues();
    	 
    	 Iterator it = columnValues.entrySet().iterator();
    	 Map.Entry pairs;
    	 
    	 while (it.hasNext()) {
    	     pairs = (Map.Entry)it.next();
    	     contentValues.put((String) pairs.getKey(),(String) pairs.getValue());
    	 }
  
    	 mDb.insertOrThrow(tableName, null, contentValues);
     }
     
   //for columnValues: key = columnName and values = columnValue
     public void updateSql(String tableName, HashMap<String, String> columnValues,
    		 String whereClause){
    	 ContentValues contentValues = new ContentValues();
    	 
    	 Iterator it = columnValues.entrySet().iterator();
    	 Map.Entry pairs;
    	 
    	 while (it.hasNext()) {
    	     pairs = (Map.Entry)it.next();
    	     contentValues.put((String) pairs.getKey(),(String) pairs.getValue());
    	 }
    	 
    	 mDb.update(tableName, contentValues, whereClause, null);
     }
     
     public User getUser(int id)
     {
    	 Cursor cursor = this.getCursor("SELECT * FROM users WHERE (id='"+id+"')");
         return (User) cursorToArrayList(cursor).get(0);
     }
     
     public ArrayList<DatabaseType> getComments()
     {
         Cursor cursor = this.getCursor("SELECT * FROM comments LIMIT 20");
         return cursorToArrayList(cursor);
     }
     
     public Comment getComment(int id)
     {
    	 
         Cursor cursor = this.getCursor("SELECT * FROM comments WHERE (id='"+id+"')");
         return (Comment) cursorToArrayList(cursor).get(0);
     }
     
     public ArrayList<DatabaseType> getPosts()
     {
         Cursor cursor = this.getCursor("SELECT * FROM posts LIMIT 20");
         return cursorToArrayList(cursor);
     }
     
     //constructs the SQL statement for getting specific columns from a table
     public ArrayList<DatabaseType> getColumnsFromTable(String tabelName, ArrayList<String> columnNames){
    	 
    	 String message = "SELECT ";
    	 int i=0;
    	 for (String columnName : columnNames) {
    		 if(i>0){
    			 message += ", ";
    		 }
			message += columnName;
			i++;
		}
    	  message += "FROM " + tabelName;
    		
    	 Cursor cursor = this.getCursor(message);
    	 return cursorToArrayList(cursor);
     }
     
     //constructs the SQL statement for getting specific columns from a table
     //+ WHERE criteria for filtering
     
     public ArrayList<DatabaseType> getSpecificColumnsByCriteria(String tableName,
    		 ArrayList<String> columnNames, ArrayList<SearchEntity> searchCriteria){
    	 
    	 String sqlMessage;
    	 
    	 sqlMessage = "SELECT ";
    	 int i=0;
    	 for (String columnName : columnNames) {
    		 if(i>0){
    			 sqlMessage += ", ";
    		 }
    		 sqlMessage += columnName;
			i++;
		}
    	 sqlMessage += "FROM " + tableName;
    	 
    	 sqlMessage = " WHERE ";
    	 
    	 i = 0;
    	 
     	 for (SearchEntity searchEntity : searchCriteria) {
     		 if (i > 0){
     			sqlMessage += ", ";
     		 }
 			if(searchEntity.getMeanOfSearch().equals(MeanOfSearch.contained)){
 				sqlMessage += searchEntity.getKey() + " LIKE '%" + searchEntity.getValue() + "%'";
 			}
 			if(searchEntity.getMeanOfSearch().equals(MeanOfSearch.exact)){
 				sqlMessage += searchEntity.getKey() + " LIKE '" + searchEntity.getValue() + "'";
 			}
 			i++;
 		}
    	
    	 Cursor cursor = this.getCursor(sqlMessage);
    	 return cursorToArrayList(cursor); 
     }
     
     public int getCountByCriteria(String tableName, ArrayList<SearchEntity> searchCriteria){
    	 String sqlMessage;
    	 
    	 sqlMessage = "SELECT COUNT(*) FROM " + tableName;
    	 
    	 sqlMessage = " WHERE ";
    	 
    	 int i = 0;
    	 
     	 for (SearchEntity searchEntity : searchCriteria) {
     		if (i > 0){
     			sqlMessage += ", ";
     		 }
     		
 			if(searchEntity.getMeanOfSearch().equals(MeanOfSearch.contained)){
 				sqlMessage += searchEntity.getKey() + " LIKE '%" + searchEntity.getValue() + "%'";
 			}
 			if(searchEntity.getMeanOfSearch().equals(MeanOfSearch.exact)){
 				sqlMessage += searchEntity.getKey() + " LIKE '" + searchEntity.getValue() + "'";
 			} 
 			
 			i++;
 		}
    	
    	 Cursor cursor = this.getCursor(sqlMessage);
    	 cursor.moveToFirst();
    	 return cursor.getInt(0);
     }
     
     public Post getPost(int id)
     {
         Cursor cursor = this.getCursor("SELECT * FROM posts WHERE (id='"+id+"')");
         return (Post) cursorToArrayList(cursor).get(0);
     }
     
     public ArrayList<DatabaseType> getVotes()
     {
         Cursor cursor = this.getCursor("SELECT * FROM votes LIMIT 20");
         return cursorToArrayList(cursor);
     }
     
     public Vote getVote(int id)
     {
         Cursor cursor = this.getCursor("SELECT * FROM votes WHERE (id='"+id+"')");
         return (Vote) cursorToArrayList(cursor).get(0);
     }
}