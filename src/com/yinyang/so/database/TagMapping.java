package com.yinyang.so.database;

import com.yinyang.so.database.*;
import com.yinyang.so.databaseentities.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TagMapping {

	String tag1;
	String tag2;
	ArrayList<String> postTagLines;
	ArrayList<String> mappingTagLines;

	public static void createEmptyMappingTable(DatabaseAdapter mDbHelper) {
		String tableName = "mapping_tags";
		String idColumn = "id";
		HashMap<String, String> columns = new HashMap<String, String>();
		columns.put("tag1", "VARCHAR(25) NOT NULL");
		columns.put("tag2", "VARCHAR(25) NOT NULL");
		columns.put("countAppearance", "INTEGER");

		mDbHelper.createTable(tableName, idColumn, columns);
	}

	public static void createTagsTable(DatabaseAdapter mDbHelper) {
		String tableName = Tag.TABLE_NAME;
		String idColumn = Tag.KEY_ID;
		HashMap<String, String> columns = new HashMap<String, String>();
		columns.put(Tag.KEY_TAG, "varchar(255) NOT NULL");
		columns.put(Tag.KEY_COUNT_APPEARANCE, "INTEGER");

		mDbHelper.createTable(tableName, idColumn, columns);
	}

	// create, fill mapping_tags and tags tables
	public static void insertTagMaaping(DatabaseAdapter mDbHelper) {

		mDbHelper.emptyTable(MapTags.TABLE_NAME);
		System.out.println("EmptiedMapTagsTable");

		Post post;
		String tagLine;
		ArrayList<SearchEntity> searchInMappingTableCriteria1 = new ArrayList<SearchEntity>();
		ArrayList<SearchEntity> searchInMappingTableCriteria2 = new ArrayList<SearchEntity>();
		ArrayList<SearchEntity> searchInTagsTableCriteria;
		SearchEntity searchEntity1;
		SearchEntity searchEntity2;
		HashMap<String, String> columnValuesForInsert;

		/*
		 * Get the first and the last index of POSTS table and loop through the
		 * table
		 */

		int postTableFirstId = mDbHelper
				.getFirstIndexFromPostsWithTagsNotNull();
		int postTableLastId = mDbHelper.getLastIndexFromPostsWithTagsNotNull();

		while (postTableFirstId <= postTableLastId) {

			System.out.println("postTableFirstId: " + postTableFirstId);

			// get the post for postTableFirstId
			post = mDbHelper.getPost(postTableFirstId);

			/*
			 * Get the id for the next post which does not have the column TAGS
			 * empty (NULL) and put it in postTableFirstId
			 */

			try {
				postTableFirstId = mDbHelper
						.getNextPostIdWithTagNotNull(postTableFirstId);
			} catch (Exception e) {
				System.out.println("FINISHED GOING THROUGH POSTS");
				postTableFirstId = postTableLastId + 1;
			}

			if (post != null) {
				// get the content of the column tags for the selected row
				tagLine = post.getTags();

				if (!tagLine.isEmpty() && tagLine != "" && tagLine != "NULL") {
					tagLine.replace("<", "");

					System.out.println("POST " + tagLine);

					// split to get each tag
					String[] tags = tagLine.split(">");

					/*
					 * we take the tags 2 by 2 and search in the mapping table
					 * to see if we have inserted the combination already
					 */

					for (int i = 0; i < tags.length; i++) {
						tags[i] = tags[i].substring(1, tags[i].length());
					}

					for (int i = 0; i < tags.length - 1; i++)
						for (int j = 1; j < tags.length; j++) {
							if (!tags[i].isEmpty() && tags[i] != ""
									&& !tags[j].isEmpty() && tags[j] != ""
									&& tags[i] != tags[j]) {

								/*
								 * we search tag1 = current_tag1 and tag2 =
								 * current_tag2, as well as tag1 = current_tag2
								 * and tag2 = current_tag1) the combination is
								 * not inserted, we insert it now. If it is, we
								 * loop again
								 */

								searchInMappingTableCriteria1 = new ArrayList<SearchEntity>();
								searchInMappingTableCriteria2 = new ArrayList<SearchEntity>();

								searchEntity1 = new SearchEntity(
										MapTags.KEY_TAG1, tags[i],
										MeanOfSearch.exact);
								searchEntity2 = new SearchEntity(
										MapTags.KEY_TAG2, tags[j],
										MeanOfSearch.exact);

								searchInMappingTableCriteria1
										.add(searchEntity1);
								searchInMappingTableCriteria1
										.add(searchEntity2);

								searchEntity1 = new SearchEntity(
										MapTags.KEY_TAG2, tags[i],
										MeanOfSearch.exact);

								searchEntity2 = new SearchEntity(
										MapTags.KEY_TAG1, tags[j],
										MeanOfSearch.exact);

								searchInMappingTableCriteria2
										.add(searchEntity1);
								searchInMappingTableCriteria2
										.add(searchEntity2);

								if (mDbHelper.getDataByCriteria(
										MapTags.TABLE_NAME,
										searchInMappingTableCriteria1)
										.isEmpty()
										&& mDbHelper.getDataByCriteria(
												MapTags.TABLE_NAME,
												searchInMappingTableCriteria2)
												.isEmpty()) {

									System.out.println("It should insert: "
											+ tags[i] + ", " + tags[j]);
									columnValuesForInsert = new HashMap<String, String>();

									columnValuesForInsert.put(MapTags.KEY_TAG1,
											tags[i]);
									columnValuesForInsert.put(MapTags.KEY_TAG2,
											tags[j]);

									mDbHelper.insertSql(MapTags.TABLE_NAME,
											columnValuesForInsert);
								}

								System.out.println("tag[" + i + "]" + "="
										+ tags[i] + ", tag[" + j + "]" + "="
										+ tags[j]);
							}
						}

				}
			}

		}
		// update countAppearance (which is currently 0 in all the table)
		// insertCount(mDbHelper);
	}

	public static void insertTags(DatabaseAdapter mDbHelper) {

		mDbHelper.emptyTable(Tag.TABLE_NAME);

		Post post;
		String tagLine;
		ArrayList<SearchEntity> searchInTagsTableCriteria = new ArrayList<SearchEntity>();
		SearchEntity searchEntity;
		HashMap<String, String> columnValuesForInsert;

		/*
		 * Get the first and the last index of POSTS table and loop through the
		 * table
		 */

		int postTableFirstId = mDbHelper
				.getFirstIndexFromPostsWithTagsNotNull();
		int postTableLastId = mDbHelper.getLastIndexFromPostsWithTagsNotNull();

		while (postTableFirstId <= postTableLastId) {

			System.out.println("postTableFirstId: " + postTableFirstId);

			// get the post for postTableFirstId
			post = mDbHelper.getPost(postTableFirstId);

			/*
			 * Get the id for the next post which does not have the column TAGS
			 * empty (NULL) and put it in postTableFirstId
			 */

			try {
				postTableFirstId = mDbHelper
						.getNextPostIdWithTagNotNull(postTableFirstId);
			} catch (Exception e) {
				System.out.println("FINISHED GOING THROUGH POSTS");
				postTableFirstId = postTableLastId + 1;
			}

			if (post != null) {
				// get the content of the column tags for the selected row
				tagLine = post.getTags();

				if (!tagLine.isEmpty() && tagLine != ""
						&& tagLine.equals("NULL") != true) {

					System.out.println("TagLine: " + tagLine);

					// split to get each tag
					String[] tags = tagLine.split(">");

					/*
					 * we take the tags 2 by 2 and search in the mapping table
					 * to see if we have inserted the combination already
					 */

					for (int i = 0; i < tags.length; i++) {
						tags[i] = tags[i].substring(1, tags[i].length());
					}

					/*
					 * For each tag found above, we search in the table tab to
					 * check if it has already been inserted If the result is
					 * not found, the tag is inserted into the tag table If not,
					 * go to the next step
					 */

					for (int i = 0; i < tags.length; i++) {

						/*
						 * SEARCH IN THE DB see if tag has already been inserted
						 */

						searchInTagsTableCriteria = new ArrayList<SearchEntity>();

						searchEntity = new SearchEntity(Tag.KEY_TAG, tags[i],
								MeanOfSearch.exact);

						searchInTagsTableCriteria.add(searchEntity);

						if (mDbHelper.getDataByCriteria(Tag.TABLE_NAME,
								searchInTagsTableCriteria).size() == 0) {

							columnValuesForInsert = new HashMap<String, String>();

							columnValuesForInsert.put(Tag.KEY_TAG, tags[i]);
							columnValuesForInsert.put(Tag.KEY_COUNT_APPEARANCE,
									"NULL");

							mDbHelper.insertSql(Tag.TABLE_NAME,
									columnValuesForInsert);

						}
					}

				}
			}

		}
	}

	/*
	 * For each combination in mapping_table, we select the number of times the
	 * two tags appear together in a row in the posts table (tag column) and add
	 * it to the countAppearance field in mapping_table
	 */

	public static void insertCountMapTags(DatabaseAdapter mDbHelper) {
		int countAppearance;

		MapTags tagCombination;
		HashMap<String, String> columnValuesForUpdate = new HashMap<String, String>();

		int postTableFirstId = mDbHelper.getFirstIndex(MapTags.TABLE_NAME);
		int postTableLastId = mDbHelper.getLastIndex(MapTags.TABLE_NAME);

		while (postTableFirstId <= postTableLastId) {

			System.out.println("postTableFirstId: " + postTableFirstId);

			tagCombination = mDbHelper.getMapTags(postTableFirstId);
			postTableFirstId++;

			SearchEntity searchEntity1 = new SearchEntity(Post.KEY_TAGS, "<"
					+ tagCombination.getTag1() + ">", MeanOfSearch.contained);
			SearchEntity searchEntity2 = new SearchEntity(Post.KEY_TAGS, "<"
					+ tagCombination.getTag2() + ">", MeanOfSearch.contained);

			ArrayList<SearchEntity> searchInMappingTableCriteria1 = new ArrayList<SearchEntity>();
			searchInMappingTableCriteria1.add(searchEntity1);
			searchInMappingTableCriteria1.add(searchEntity2);

			countAppearance = mDbHelper.getCountByCriteria(Post.TABLE_NAME,
					searchInMappingTableCriteria1);

			columnValuesForUpdate.put(MapTags.KEY_COUNT_APPEARANCE, ""
					+ countAppearance);

			System.out.println("UPDATE - ID: " + tagCombination.getId()
					+ ", TAG1: " + tagCombination.getTag1() + ", TAG2: "
					+ tagCombination.getTag2());

			mDbHelper.updateSql(MapTags.TABLE_NAME, columnValuesForUpdate,
					"ID = " + tagCombination.getId());
		}
	}

	public static void insertCountTags(DatabaseAdapter mDbHelper) {
		int countAppearance;

		Tag tag;
		HashMap<String, String> columnValuesForUpdate = new HashMap<String, String>();
		SearchEntity searchEntity;
		ArrayList<SearchEntity> searchInMappingTableCriteria;

		int tagTableFirstId = mDbHelper.getFirstIndex(Tag.TABLE_NAME);
		int tagTableLastId = mDbHelper.getLastIndex(Tag.TABLE_NAME);

		while (tagTableFirstId <= tagTableLastId) {

			System.out.println("tagTableFirstId: " + tagTableFirstId);

			tag = mDbHelper.getTag(tagTableFirstId);
			tagTableFirstId++;

			if (tag != null) {
				searchEntity = new SearchEntity(Post.KEY_TAGS,
						"<" + tag.getTag() + ">", MeanOfSearch.contained);

				searchInMappingTableCriteria = new ArrayList<SearchEntity>();
				searchInMappingTableCriteria.add(searchEntity);

				countAppearance = mDbHelper.getCountByCriteria(Post.TABLE_NAME,
						searchInMappingTableCriteria);

				columnValuesForUpdate.put(Tag.KEY_COUNT_APPEARANCE, ""
						+ countAppearance);

				System.out.println("UPDATE - ID: " + tag.getId() + ", TAG: "
						+ tag.getTag() + "COUNT: " + countAppearance);

				mDbHelper.updateSql(Tag.TABLE_NAME, columnValuesForUpdate,
						"ID = " + tag.getId());
			}
		}
	}

}
