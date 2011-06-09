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

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.maps.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Display Google Map based on Restaurants information passed by
 *          NearByList Activity
 * 
 */
public class GcoderHelper {

	// Fields
	private Geocoder geocoder;
	private Context mContext;

	/**
	 * @param mContext
	 *            -Activity context
	 * 
	 *            Constructor to create GcoderHelper
	 * 
	 */
	public GcoderHelper(Context mContext) {
		super();
		this.mContext = mContext;
		this.geocoder = new Geocoder(this.mContext, Locale.getDefault());
	}

	/**
	 * @param d
	 *            -latitude or longitude
	 * 
	 *            Helper method to getE6 for latitude or longitude
	 * 
	 */
	private int getE6(double d) {
		return (int) (d * Math.pow(10, 6));
	}

	/**
	 * @param location
	 *            -String address of location
	 * @param max
	 *            - max number of Geo-coding
	 * 
	 * @return ArrayList<GeoPoint>
	 * 
	 *         Helper method to get GeoPoints based on location given
	 */
	public ArrayList<GeoPoint> getGeoPoints(String location, int max) {
		ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		try {
			List<Address> result = this.geocoder.getFromLocationName(location,
					max);
			if (result.size() >= 0) {
				for (Address address : result) {
					if (address.hasLatitude() && address.hasLongitude()) {
						double latitude = address.getLatitude();
						double longitude = address.getLongitude();
						geoPoints.add(new GeoPoint(getE6(latitude),
								getE6(longitude)));
					}
				}
			} else {
				Log.d("Result size:", "0");
			}
		} catch (IOException e) {
			Log.e("Geocoder exception:", e.getMessage());
		}
		return geoPoints;
	}

	/**
	 * @param latitude
	 *            -String latitude
	 * @param longitude
	 *            - String longitude
	 * 
	 * @return GeoPoint
	 * 
	 *         Helper method to translate latitude and longitude to GeoPoint
	 */
	public GeoPoint getGeoPoint(String latitude, String longitude) {
		double latitudeD = Double.parseDouble(latitude);
		double longitudeD = Double.parseDouble(longitude);
		return new GeoPoint(getE6(latitudeD), getE6(longitudeD));
	}
}
