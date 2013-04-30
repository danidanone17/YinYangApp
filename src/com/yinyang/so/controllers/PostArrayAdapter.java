package com.yinyang.so.controllers;

import java.util.ArrayList;

import com.yinyang.so.R;
import com.yinyang.so.databaseentities.Post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PostArrayAdapter extends ArrayAdapter<Post> {

	private ArrayList<Post> posts;
	
	public PostArrayAdapter(Context context, int resource, ArrayList<Post> posts) {
		super(context, resource, posts);
		// TODO Auto-generated constructor stub
		this.posts = posts;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {

		//To be fixed.	
		// LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// v = vi.inflate(R.layout.search_result_layout, null);

		 //} 
		
		Post o = posts.get(position);

		if (o != null) {

		 TextView tt = (TextView) v.findViewById(R.id.a_c);

		if (tt != null) {
		 tt.setText(o.getAnswerCount());
			 }
		
		TextView title = (TextView) v.findViewById(R.id.title);
		if (title != null) {
			 title.setText(o.getTitle());
				 }
		
		Button tag = (Button) v.findViewById(R.id.tag1);
		if (tag != null) {
			 tag.setText(o.getTags());
				 }
		 }

		Button tag = (Button) v.findViewById(R.id.tag2);
		if (tag != null) {
			 tag.setText(o.getTags());
		}}
		return v;
		}
}
