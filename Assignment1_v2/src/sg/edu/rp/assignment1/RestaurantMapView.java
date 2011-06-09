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

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Display Map View based on Restaurant information passed by
 *          NearByList Activity
 * 
 */
public class RestaurantMapView extends MapActivity {
	// Fields
	private boolean myLocationShowed = false;
	private GcoderHelper gcoderHelper;
	private ArrayList<Restaurant> Restaurants;
	MapView map;
	MapController controller;
	MyLocationOverlay myLocationOverlay;
	List<Overlay> mapOverlays;
	LoadMapItemHelper loader;

	static final private int MAP_VIEW = Menu.FIRST;
	static final private int SATELLITE_VIEW = Menu.FIRST + 1;
	static final private int MY_LOCATION = Menu.FIRST + 2;

	/**
	 * 
	 * Called when the RestaurantMapView activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.gcoderHelper = new GcoderHelper(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);
		map = (MapView) findViewById(R.id.map);
		loader = new LoadMapItemHelper();
	}

	/**
	 * 
	 * Called when the RestaurantMapView activity is started.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		this.Restaurants = getIntent().getExtras().getParcelableArrayList(
				"Rlist");
		int index = getIntent().getExtras().getInt("index");
		loader.execute(index);
	}

	/**
	 * 
	 * Override method to tell GOOGLE map use of Route or not.
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/**
	 * 
	 * Menu Item: Normal View, Satellite View and Show My Location
	 * 
	 * Called when the OptionsMenu is first created.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create and add new menu items.
		menu.add(1, MAP_VIEW, Menu.CATEGORY_SYSTEM, R.string.mapmenu1);
		menu.add(1, SATELLITE_VIEW, Menu.CATEGORY_SYSTEM, R.string.mapmenu2);
		menu.add(0, MY_LOCATION, Menu.CATEGORY_SECONDARY, R.string.mapmenu3);
		return true;
	}

	/**
	 * 
	 * Called when the OptionsItem is selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (MAP_VIEW): {
			map.setSatellite(false);
			return true;
		}
		case (SATELLITE_VIEW): {
			map.setSatellite(true);
			return true;
		}
		case (MY_LOCATION): {
			if (!myLocationShowed) {
				myLocationOverlay = new MyLocationOverlay(this, map);
				myLocationOverlay.enableMyLocation();
				map.getOverlays().add(myLocationOverlay);
				myLocationShowed = true;
			} else {
				map.getOverlays().remove(myLocationOverlay);
				myLocationShowed = false;
			}
			return true;
		}
		}
		return false;
	}

	/**
	 * 
	 * LoadMapItemHelper help to show loading screen while adding Map Overlay Items
	 * 
	 */
	private class LoadMapItemHelper extends AsyncTask<Integer,Integer,List<RestaurantOverlay>> {
		//Fields
		private ProgressDialog dialog;
		
		/**
		 * @param Integer
		 *            [] -params: selected restaurant index
		 * 
		 * @return List<RestaurantOverlay> -A list of RestaurantOverlay objects
		 * 
		 *         Background thread to add RestaurantOverlay items on map
		 * 
		 */
		@Override
		protected List<RestaurantOverlay> doInBackground(Integer... params) {
			List<RestaurantOverlay> items = new ArrayList<RestaurantOverlay>();
			Drawable other = RestaurantMapView.this.getResources().getDrawable(R.drawable.restaurant_small);
			Drawable current = RestaurantMapView.this.getResources().getDrawable(
					R.drawable.restaurant);
			RestaurantOverlay restaurantCOverlay = new RestaurantOverlay(
					current, RestaurantMapView.this);
			RestaurantOverlay restaurantOOverlay = new RestaurantOverlay(
					other, RestaurantMapView.this);
			for (int i = 0; i < Restaurants.size(); i++) {
				GeoPoint point = gcoderHelper.getGeoPoint(Restaurants
						.get(i).getLatitude(), Restaurants.get(i)
						.getLongitude());
				OverlayItem overlayItem = new OverlayItem(point, Restaurants.get(i).getName(),
						Restaurants.get(i).getSummary());
				if (i == params[0]) {
					restaurantCOverlay.addOverlay(overlayItem);
				} else {
					restaurantOOverlay.addOverlay(overlayItem);
				}		
			}
			Log.d("size",String.valueOf(restaurantOOverlay.size()));
			items.add(restaurantOOverlay);
			items.add(restaurantCOverlay);
			return items;
		}


		/**
		 * @see Dismiss Progress Dialog & ListView update
		 * 
		 *      Put data in the ListView after get Restaurants information
		 */
		@Override
		protected void onPostExecute(List<RestaurantOverlay> result) {
			super.onPostExecute(result);
			mapOverlays.addAll(result);
			map.invalidate();
			this.dialog.dismiss();
		}

		/**
		 * @see Dismiss Progress Dialog
		 * 
		 */
		@Override
		protected void onCancelled() {
			super.onCancelled();
			this.dialog.dismiss();
		}

		/**
		 * @see Show Progress Dialog
		 * 
		 *      Before execute task show the progress dialog and set map view
		 * 
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			map.setClickable(true);
			map.setStreetView(false);
			map.setSatellite(false);
			map.setTraffic(false);
			map.setBuiltInZoomControls(true);
			controller = map.getController();
			controller.setZoom(14);
			mapOverlays = map.getOverlays();
			this.dialog = ProgressDialog.show(RestaurantMapView.this, "Map", 
                    "Loading Map. Please wait...", true);
		}
		
		
	}
}
