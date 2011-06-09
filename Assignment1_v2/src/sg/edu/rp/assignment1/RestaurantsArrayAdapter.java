/*
 * Copyright (C) 2011 wgx731 Republic Polytechnic Assignment 1 Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 */
package sg.edu.rp.assignment1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          ArrayAdapter to render Restaurants for ListView
 * 
 */
public class RestaurantsArrayAdapter extends ArrayAdapter<Restaurant> {

	// Fields
	private final List<Restaurant> list;
	private final Activity context;

	/**
	 * @param context
	 *            -Activity reference
	 * @param List
	 *            <Restaurant> -List of Restaurant objects
	 * 
	 *            Constructor to create RestaurantsArrayAdapter
	 */
	public RestaurantsArrayAdapter(Activity context, List<Restaurant> list) {
		super(context, R.layout.list_item, list);
		this.context = context;
		this.list = list;
	}

	/**
	 * class to refer view in the ListView - TextView
	 */
	static class ViewHolder {
		protected TextView text;
	}

	/**
	 * @param position
	 *            -position of item
	 * 
	 * @param convertView
	 *            -View reference of item
	 * 
	 * @param parent
	 *            -Parent View Group of item
	 * 
	 * @return View
	 * 
	 *         Override method to link data from Restaurants list to Textview in
	 *         ListView item
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = context.getLayoutInflater();
		View view = inflator.inflate(R.layout.list_item, null);
		final ViewHolder viewHolder = new ViewHolder();
		viewHolder.text = (TextView) view.findViewById(android.R.id.text1);
		view.setTag(viewHolder);
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getName()+"\nR: "+String.valueOf(list.get(position).getRating().charAt(0)));
		return view;
	}

}
