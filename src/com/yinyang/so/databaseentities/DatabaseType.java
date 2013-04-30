package com.yinyang.so.databaseentities;

import android.database.Cursor;

public abstract class DatabaseType {
	
	public DatabaseType(Cursor cursor) {
		
	}
	
	@Override
	public abstract String toString();
	
}
