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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Customised Overlay to display restaurant marker on map view.
 * 
 */

// TODO: Check out how to use <T> with ItemizedOverlay
@SuppressWarnings("unchecked")
public class RestaurantOverlay extends ItemizedOverlay {

	// Fields
	private ArrayList<OverlayItem> mOverlay;
	private Context mContext;

	/**
	 * @param Drawable
	 *            -marker icon
	 * 
	 *            Constructor to create RestaurantOverlay with Drawable
	 */
	public RestaurantOverlay(Drawable deafultMarker) {
		super(boundCenterBottom(deafultMarker));
		mOverlay = new ArrayList<OverlayItem>();
	}

	/**
	 * @param deafaultMarker
	 *            -marker icon
	 * @param context
	 *            -Context reference
	 * 
	 *            Constructor to create RestaurantOverlay with Drawable & Context
	 */
	public RestaurantOverlay(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		mContext = context;
	}

	/**
	 * @param overlay
	 *            -OverlayItem pass in
	 * 
	 * @see new OverlayItem added
	 * 
	 *      Override method to add Overlay
	 */
	public void addOverlay(OverlayItem overlay) {
		mOverlay.add(overlay);
		populate();// Important, Never Forget populate!
	}

	/**
	 * @param i
	 *            -OverlayItem position
	 * 
	 * @return new OverlayItem
	 * 
	 *         Override method to create Item
	 */
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlay.get(i);
	}

	/**
	 * @return size of Overlay
	 * 
	 *         Override method to get Overlay size
	 */
	@Override
	public int size() {
		return mOverlay.size();
	}

	/**
	 * @param index
	 *            -OverlayItem position
	 * 
	 * @return is the on tap action successful or not
	 * 
	 *         Override method to set onTap action
	 */
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlay.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setPositiveButton("BACK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.show();
		return true;
	}
}
