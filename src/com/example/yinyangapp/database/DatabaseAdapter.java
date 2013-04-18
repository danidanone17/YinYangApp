package com.example.yinyangapp.database;

import java.io.IOException;
import java.util.ArrayList;

import com.example.yinyangapp.databaseentities.User;

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

     public Cursor getTestData()
     {
         try
         {
             String sql ="SELECT * FROM users";

             Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     private ArrayList<User> cursorToUsers(Cursor cursor) {
    	 ArrayList<User> users = new ArrayList<User>();
    	 if ( cursor != null)
         {
    		 while (cursor.moveToNext()) {
    		 	User user = new User(cursor);
	            users.add(user);
    		 }
         }
    	 return users;
     }
     
     public ArrayList<User> getUsers()
     {
    	 ArrayList<User> users = new ArrayList<User>();
         try
         {
             String sql ="SELECT * FROM users LIMIT 20";
             Cursor mCur = mDb.rawQuery(sql, null);
             users = cursorToUsers(mCur);
             return users;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     public User getUser(int id)
     {
         try
         {
             String sql ="SELECT * FROM users WHERE (id='"+id+"')";
             Cursor mCur = mDb.rawQuery(sql, null);
             User user = cursorToUsers(mCur).get(0);
             return user;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
}