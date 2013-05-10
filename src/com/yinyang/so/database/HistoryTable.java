package com.yinyang.so.database;

import java.util.HashMap;

import com.yinyang.so.databaseentities.History;
import com.yinyang.so.databaseentities.Post;

public class HistoryTable {

	/**
	 * create the history table has to be called from an activity
	 * 
	 * @param mDbHelper
	 */
	public void createTable(DatabaseAdapter mDbHelper) {

		// put the columns (except id) with their corresponding type into the
		// HashMap
		HashMap<String, String> columns = new HashMap<String, String>();
		columns.put(History.KEY_NR_VIEWS, "int");
		columns.put(History.KEY_TAG, "VARCHAR(25) NOT NULL");

		// call the generic createTable method
		mDbHelper.createTable(History.TABLE_NAME, History.KEY_ID, columns);
	}

	/**
	 * when a question is viewed, the tags it contains are either inserted into
	 * user_history, or their nr_views is updated if they are already inserted
	 * 
	 * @param mDbHelper
	 */
	public void questionViewed(DatabaseAdapter mdbHelper, int postId) {

		History historyLine;

		// get the question based on id
		Post question = mdbHelper.getPost(postId);

		// get the tags contained in the question
		String tags = question.getTags();
		if (tags != "NULL" && !tags.isEmpty()) {

			// split the tags String and get all the tags in an array
			String[] tagsArray = tags.split(">");

			for (int i = 0; i < tagsArray.length; i++) {
				// remove the "<" which is in front of each tag
				tagsArray[i] = tagsArray[i].substring(1, tagsArray[i].length());
			}

			for (int i = 0; i < tagsArray.length; i++) {
				// check if the tag already exists in the history
				historyLine = mdbHelper.getTagFromHistory(tagsArray[i]);

				if (historyLine != null) {
					// it does not exist, thus insert the tag into history
					HashMap<String, String> columnValues = new HashMap<String, String>();
					columnValues.put(History.KEY_TAG, tagsArray[i]);
					columnValues.put(History.KEY_NR_VIEWS, "1");

					mdbHelper.insertSql(History.TABLE_NAME, columnValues);
				}

				else {
					// it exists, thus get the nr_views and increment it
					int nrViews = historyLine.getNrViews() + 1;

					HashMap<String, String> columnValues = new HashMap<String, String>();
					columnValues.put(History.KEY_NR_VIEWS, "" + nrViews);

					String whereClause = " ID = '" + historyLine.getId() + "'";

					mdbHelper.updateSql(History.TABLE_NAME, columnValues,
							whereClause);
				}

			}

		}
	}
}
