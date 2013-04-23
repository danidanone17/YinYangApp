package com.example.yinyangapp;

import com.example.yinyangapp.database.*;
import com.example.yinyangapp.databaseentities.*;

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
		String tableName = "mapping-tags";
		String idColumn = "id";
		HashMap<String, String> columns = new HashMap<String, String>();
		columns.put("tag1", "varchar(255) NOT NULL");
		columns.put("tag2", "varchar(255) NOT NULL");
		columns.put("countAppearance", "int");

		mDbHelper.createTable(tableName, idColumn, columns);
	}

	public static void createTagsTable(DatabaseAdapter mDbHelper) {
		String tableName = "tags";
		String idColumn = "id";
		HashMap<String, String> columns = new HashMap<String, String>();
		columns.put("tag", "varchar(255) NOT NULL");

		mDbHelper.createTable(tableName, idColumn, columns);
	}

	public static void insertTagMaaping(DatabaseAdapter mDbHelper) {

		// create mapping tag table + tag table
		createEmptyMappingTable(mDbHelper);
		System.out.println("CreatedEmptyMappingTable");
		createTagsTable(mDbHelper);
		System.out.println("CreatedEmptyTagsTable");

		Post post;
		String tagLine;
		ArrayList<SearchEntity> searchInMappingTableCriteria1 = new ArrayList<SearchEntity>();
		ArrayList<SearchEntity> searchInMappingTableCriteria2 = new ArrayList<SearchEntity>();
		ArrayList<SearchEntity> searchInTagsTableCriteria;
		SearchEntity searchEntity1;
		SearchEntity searchEntity2;
		HashMap<String, String> columnValuesForInsert;
		HashMap<String, String> insertedMappedTags = new HashMap<String, String>();
		ArrayList<String> insertedTags = new ArrayList<String>();

		// get only the tag column from the table posts, with all the lines
		ArrayList<DatabaseType> lines = mDbHelper.getColumnsFromTable(
				Post.TABLE_NAME, new ArrayList<String>(Arrays.asList("tag")));

		// for each line of the result, we have a combination of tags
		for (DatabaseType databaseTypeLine : lines) {
			post = (Post) databaseTypeLine;
			tagLine = post.getTags();

			tagLine.replace("<", "");
			String[] tags = tagLine.split(">");

			/*
			 * we take the tags 2 by 2 and search in the mapping table to see if
			 * we have inserted the combination already
			 */
			for (int i = 0; i < tags.length - 1; i++)
				for (int j = 1; j < tags.length; j++) {
					/*
					 * OLD VERSION---SEARCH IN THE DB (we search tag1 =
					 * current_tag1 and tag2 = current_tag2, as well as tag1 =
					 * current_tag2 and tag2 = current_tag1) the combination is
					 * not inserted, we insert it now. If it is, we loop again
					 */
					/*
					 * searchInMappingTableCriteria1 = new
					 * ArrayList<SearchEntity>(); searchInMappingTableCriteria2
					 * = new ArrayList<SearchEntity>();
					 * 
					 * searchEntity1 = new
					 * SearchEntity(MapTags.KEY_TAG1,tags[i],
					 * MeanOfSearch.contained); searchEntity2 = new
					 * SearchEntity(MapTags.KEY_TAG2, tags[j],
					 * MeanOfSearch.contained);
					 * 
					 * searchInMappingTableCriteria1.add(searchEntity1);
					 * searchInMappingTableCriteria1.add(searchEntity2);
					 * 
					 * searchEntity1 = new
					 * SearchEntity(MapTags.KEY_TAG2,tags[i],
					 * MeanOfSearch.contained); searchEntity2 = new
					 * SearchEntity(MapTags.KEY_TAG1, tags[j],
					 * MeanOfSearch.contained);
					 * 
					 * searchInMappingTableCriteria2.add(searchEntity1);
					 * searchInMappingTableCriteria2.add(searchEntity2);
					 * 
					 * if(mDbHelper.getDataByCriteria(MapTags.TABLE_NAME,
					 * searchInMappingTableCriteria1)==null &&
					 * mDbHelper.getDataByCriteria(MapTags.TABLE_NAME,
					 * searchInMappingTableCriteria2)==null){
					 * columnValuesForInsert = new HashMap<String, String>();
					 * 
					 * columnValuesForInsert.put(MapTags.KEY_TAG1, tags[i]);
					 * columnValuesForInsert.put(MapTags.KEY_TAG2, tags[j]);
					 */

					/*
					 * NEW VERSION --- KEEP A VARIABLE TO KNOW WHAT HAS BEEN
					 * INSERTED Check in the variable if the combination of tags
					 * has been inserted Insert if the combination hasn't been
					 * already inserted
					 */
					if (!(insertedMappedTags.containsKey(tags[i]) && insertedMappedTags
							.containsValue(tags[j]))
							&& !(insertedMappedTags.containsKey(tags[j]) && insertedMappedTags
									.containsValue(tags[i]))) {

						columnValuesForInsert = new HashMap<String, String>();

						columnValuesForInsert.put(MapTags.KEY_TAG1, tags[i]);
						columnValuesForInsert.put(MapTags.KEY_TAG2, tags[j]);

						mDbHelper.insertSql(MapTags.TABLE_NAME,
								columnValuesForInsert);

						insertedMappedTags.put(tags[i], tags[j]);

					}
				}
			insertedMappedTags = null;
			/*
			 * Search through tags table for each tag found on the current line
			 * If the result is not found, the tag is inserted into the tag
			 * table If not, go to the next step
			 */
			for (int i = 0; i < tags.length; i++) {
				/*
				 * OLD VERSION -- SEARCH IN THE DB see if tag has already been
				 * inserted
				 */
				/*
				 * searchInTagsTableCriteria = new ArrayList<SearchEntity>();
				 * 
				 * searchEntity1 = new
				 * SearchEntity(Tag.KEY_TAG,tags[i],MeanOfSearch.exact);
				 * 
				 * searchInTagsTableCriteria.add(searchEntity1);
				 * 
				 * searchInTagsTableCriteria.add(searchEntity1);
				 * 
				 * if(mDbHelper.getDataByCriteria(Tag.TABLE_NAME,
				 * searchInTagsTableCriteria)==null){ columnValuesForInsert =
				 * new HashMap<String, String>();
				 * 
				 * columnValuesForInsert.put(Tag.KEY_TAG, tags[i]);
				 * 
				 * mDbHelper.insertSql(Tag.TABLE_NAME, columnValuesForInsert);
				 * 
				 * }
				 */
				
				/*
				 * NEW VERSION --- WE KEEP AN OBJECT OF INSERTED TAGS
				 * we search in it to see if the tag has already 
				 * been inserted in the table
				 */
				if (!insertedTags.contains(tags[i])) {
					columnValuesForInsert = new HashMap<String, String>();

					columnValuesForInsert.put(Tag.KEY_TAG, tags[i]);

					mDbHelper.insertSql(Tag.TABLE_NAME,
							columnValuesForInsert);

					insertedTags.add(tags[i]);
				}
			}
			insertedTags = null;
		}

		/*
		 * After all the lines (all the combinations) have been inserted into
		 * the mapping table, for each combination we select the number of times
		 * the two tags appear together in a row in the posts table (tag column)
		 */
		
		int countAppearance;
		
		MapTags tagCombination;
		HashMap<String, String> columnValuesForUpdate = new HashMap<String, String>();
		
		ArrayList<DatabaseType> tagsCombinationsTable = mDbHelper.getTable(MapTags.TABLE_NAME);
		
		for (DatabaseType tagCombinationDT : tagsCombinationsTable) {
			tagCombination = (MapTags) tagCombinationDT; 
			
			searchEntity1 = new SearchEntity(Post.KEY_TAGS, tagCombination.getTag1(), MeanOfSearch.contained);
			searchEntity2 = new SearchEntity(Post.KEY_TAGS, tagCombination.getTag2(),MeanOfSearch.contained);
			
			searchInMappingTableCriteria1.add(searchEntity1);
			searchInMappingTableCriteria1.add(searchEntity2);
			
			countAppearance = mDbHelper.getCountByCriteria(MapTags.TABLE_NAME,
					searchInMappingTableCriteria1);
			
			columnValuesForUpdate.put(MapTags.KEY_COUNT_APPEARANCE, ""+countAppearance);
			
			mDbHelper.updateSql(MapTags.TABLE_NAME, columnValuesForUpdate, "ID = " + tagCombination.getId());
		}
		

	}

}
