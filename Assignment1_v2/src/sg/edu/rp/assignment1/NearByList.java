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
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Display HungrayGoWhere Search Result in ListView
 * 
 */
public class NearByList extends ListActivity {
	// Fields
	ListView restaurantList;
	ArrayAdapter<Restaurant> adapter;
	ArrayList<Restaurant> allRestaurants;
	private DataHelper dataHelper;
	private final CharSequence[] actions = { "View On Map", "Write Review",
			"Call Restaurant", "View Restaurant Website" };
	static final int VIEW_MAP = 0;
	static final int WRITE_REVIEW = 1;
	static final int PHONE = 2;
	static final int WEB = 3;
	static final private int SORT = Menu.FIRST;
	static final private int DOWNLOAD = Menu.FIRST + 1;
	Context mContext = this;

	/**
	 * Called when the NearByList activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_list);
		this.restaurantList = getListView();
		this.dataHelper = new DataHelper("YOUR_API_KEY",
				"eabc298f-8815-4ef9-a2d6-fe4cde3ceaba");
	}

	/**
	 * 
	 * Called when the NearByList activity is started.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (restaurantList.getChildCount() <= 0) {
			this.dataHelper.execute("1.3", "103.85", "2000");
			this.restaurantList
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> av, View v,
								final int index, long arg3) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									mContext);
							builder.setTitle("Pick an action:");
							builder.setItems(actions,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int position) {
											menuHandler(index, position);
										}
									});
							AlertDialog alert = builder.create();
							alert.show();
						}

					});

		}
	}

	/**
	 * @param index
	 *            -index of Restaurant in arrayList allRestaurants
	 * 
	 * @param position
	 *            -menu action position
	 * 
	 *            Helper method handle the action chosen by user
	 */
	private void menuHandler(int index, int position) {
		switch (position) {
		// Go to Map View Activity
		case (VIEW_MAP): {
			Intent mapView = new Intent(mContext, RestaurantMapView.class);
			mapView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mapView.putExtra("index", index);
			mapView.putExtra("Rlist", allRestaurants);
			startActivity(mapView);
			break;
		}
			// Go to Write Review Activity
		case (WRITE_REVIEW): {
			Intent review = new Intent(mContext, Review.class);
			review.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			review.putExtra("rName", allRestaurants.get(index).getName());
			startActivity(review);
			break;
		}
			// Go to Dial Screen with restaurant phone number if available
		case (PHONE): {
			if (allRestaurants.get(index).isPhoneEmpty()) {
				Toast toast = Toast.makeText(mContext,
						"Sorry, no phone information.", 3);
				toast.show();

			} else {
				dial("tel:" + allRestaurants.get(index).getPhone());
			}
			break;
		}
			// Go to restaurant web page if available
		case (WEB): {
			if (allRestaurants.get(index).isWebEmpty()) {
				Toast toast = Toast.makeText(mContext,
						"Sorry, no web information.", 3);
				toast.show();
			} else {
				Uri uri = Uri.parse(allRestaurants.get(index).getWeb());
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
			break;
		}
		}
	}

	/**
	 * 
	 * @param number
	 *            -restaurant phone number
	 * 
	 *            HelperMethod to dial restaurant
	 */
	private void dial(String number) {
		try {
			Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse(number));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("Call Function", "Call failed", e);
		}
	}

	/** Called when the options menu is created. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem itemSort = menu.add(0, SORT, Menu.CATEGORY_SYSTEM,
				R.string.sort);
		MenuItem itemRem = menu.add(0, DOWNLOAD, Menu.CATEGORY_SYSTEM,
				R.string.refresh);
		itemSort.setIcon(R.drawable.sort_btn);
		itemRem.setIcon(R.drawable.refresh_btn);
		return true;
	}

	/** Called when the option in menu is selected. */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (SORT): {
			Collections.sort(allRestaurants, new Comparator<Restaurant>() {
				public int compare(Restaurant r1, Restaurant r2) {
					double r1D = Double.parseDouble(r1.getRating());
					double r2D = Double.parseDouble(r2.getRating());
					if (r1D > r2D) {
						return -1;
					} else if (r1D < r2D) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			adapter.notifyDataSetChanged();
			return true;
		}
		case (DOWNLOAD): {
			this.dataHelper = new DataHelper("YOUR_API_KEY",
					"eabc298f-8815-4ef9-a2d6-fe4cde3ceaba");
			this.dataHelper.execute("1.3", "103.85", "2000");
			return true;
		}
		}
		return false;
	}

	/**
	 * 
	 * DataHelper make HTTP request to get and format data from ProjectNimbus
	 * 
	 */
	private class DataHelper extends
			AsyncTask<String, Integer, ArrayList<Restaurant>> {

		// Fields
		private String accountKey;
		private String uniqueId;
		private ProgressDialog dialog;
		private boolean running = true;
		private String baseURL = "https://api.projectnimbus.org/hgwodataservice.svc/RestaurantSet?";

		/**
		 * @param accountKey
		 *            -ProjectNimbus account key
		 * 
		 * @param uniqueId
		 *            -Unique user id
		 * 
		 * 
		 *            Constructor to create DataHelper
		 * 
		 */
		public DataHelper(String accountKey, String uniqueId) {
			super();
			this.accountKey = accountKey;
			this.uniqueId = uniqueId;
			this.dialog = new ProgressDialog(NearByList.this);
			this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			this.dialog.setCancelable(true);
			this.dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface e) {
					running = false;
					cancel(true);
					e.dismiss();
					restaurantList.clearChoices();
					Toast toast = Toast.makeText(getApplicationContext(),
							"Download Cancelled", 3);
					toast.show();
				}
			});
		}

		/**
		 * @param src
		 *            -XML string result
		 * 
		 * @param start
		 *            -start point of node
		 * 
		 * @param end
		 *            -end point of node
		 * 
		 * @return String -node text
		 * 
		 *         Helper method to get node text based on XML string given
		 * 
		 */
		private String getStringBetween(String src, String start, String end) {
			StringBuilder sb = new StringBuilder();
			int startIdx = src.indexOf(start) + start.length();
			int endIdx = src.indexOf(end);
			while (startIdx < endIdx) {
				sb.append("" + String.valueOf(src.charAt(startIdx)));
				startIdx++;
			}
			return sb.toString();
		}

		/**
		 * @param String
		 *            [] -params: latitude, longitude and distance
		 * 
		 * @return ArrayList<Restaurants> -A list of Restaurants objects
		 * 
		 *         Background thread to get Restaurants information based on
		 *         params
		 * 
		 */
		@Override
		protected ArrayList<Restaurant> doInBackground(String... params) {
			ArrayList<Restaurant> restaurantsList = new ArrayList<Restaurant>();
			try {
				int progress = 0;
				StringBuilder strBuilder;
				URL _url = new URL(this.baseURL + "Latitude=" + params[0]
						+ "&Longitude=" + params[1] + "&Distance=" + params[2]);
				progress += 10;
				this.publishProgress(progress);
				if (!running) {
					return null;
				}
				URLConnection _urlConn = _url.openConnection();
				_urlConn.setRequestProperty("accept", "*/*");
				_urlConn.addRequestProperty("AccountKey", accountKey);
				_urlConn.addRequestProperty("UniqueUserID", uniqueId);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						_urlConn.getInputStream()));
				String line = null;
				strBuilder = new StringBuilder();
				while ((line = br.readLine()) != null) {
					strBuilder.append(line);
					if (!running) {
						return null;
					}
				}
				progress += 20;
				this.publishProgress(progress);
				String[] IProperties = strBuilder.toString().split(
						"<m:properties>");
				for (String str : IProperties) {
					Restaurant restaurant = new Restaurant();
					restaurant.setLatitude(this.getStringBetween(str,
							"<d:Latitude m:type=\"Edm.Double\">",
							"</d:Latitude>"));
					restaurant.setLongitude(this.getStringBetween(str,
							"<d:Longitude m:type=\"Edm.Double\">",
							"</d:Longitude>"));
					restaurant.setWeb(this.getStringBetween(str, "<d:Web>",
							"</d:Web>"));
					restaurant.setPhone(this.getStringBetween(str,
							"<d:PhoneNumber>", "</d:PhoneNumber>"));
					restaurant.setSummary(this.getStringBetween(str,
							"<d:Summary>", "</d:Summary>"));
					restaurant.setName(this.getStringBetween(str, "<d:POI>",
							"</d:POI>"));
					restaurant.setRating(this.getStringBetween(str,
							"<d:OverallRating m:type=\"Edm.Double\">",
							"</d:OverallRating>"));
					Log.d("restaurant", restaurant.toString());
					if (!restaurant.isNameEmpty()) {
						restaurantsList.add(restaurant);
					}
					progress += 70 / IProperties.length;
					this.publishProgress(progress);
					if (!running) {
						return null;
					}
				}
				Log.d("finished", String.valueOf(restaurantsList.size()));
			} catch (MalformedURLException ex) {
				Log.e("MalformedURLException", ex.getMessage());
			} catch (IOException ex) {
				Log.e("IOException", ex.getMessage());
			} catch (Exception ex) {
				Log.e("Exception", ex.getMessage());
			}
			return restaurantsList;
		}

		/**
		 * @see Show Progress Dialog
		 * 
		 *      Before execute task show the progress dialog
		 * 
		 */
		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Downloading Restaurants Information ... ");
			this.dialog.show();
		}

		/**
		 * @see Update Progress Dialog
		 * 
		 *      Update the progress state
		 */
		@Override
		protected void onProgressUpdate(Integer... progress) {
			this.dialog.setProgress(progress[0]);
		}

		/**
		 * @see Dismiss Progress Dialog & ListView update
		 * 
		 *      Put data in the ListView after get Restaurants information
		 */
		@Override
		protected void onPostExecute(ArrayList<Restaurant> result) {
			super.onPostExecute(result);
			this.dialog.dismiss();
			allRestaurants = result;
			adapter = new RestaurantsArrayAdapter(NearByList.this, result);
			restaurantList.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

		/**
		 * @see Dismiss Progress Dialog & Stop Download Process
		 * 
		 *      Reset ListView
		 */
		@Override
		protected void onCancelled() {
			super.onCancelled();
			running = false;
		}

	}

}