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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Restaurants Data Model Stores Restaurant information
 * 
 */
public class Restaurant implements Parcelable {
	// Fields
	private String latitude;
	private String longitude;
	private String web;
	private String phone;
	private String summary;
	private String name;
	private String rating;

	/**
	 * 
	 * default Constructor to create Restaurant
	 * 
	 */
	public Restaurant() {

	}

	/**
	 * @param source
	 *            -constructor used by Parcelable
	 * 
	 *            Parcelable Constructor to create Restaurant
	 * 
	 */
	public Restaurant(Parcel source) {
		latitude = source.readString();
		longitude = source.readString();
		web = source.readString();
		phone = source.readString();
		name = source.readString();
		summary = source.readString();
		rating = source.readString();
	}

	/**
	 * Override toString method for Restaurant object
	 */
	@Override
	public String toString() {
		return "Name: " + name + ",latitude: " + latitude + ",longitude: "
				+ longitude + ",phone: " + phone + ",web: " + web;
	}

	/**
	 * Check Restaurant object has name or not
	 * 
	 * @return boolean
	 */
	public boolean isNameEmpty() {
		return name.trim().equalsIgnoreCase("") || name == null;
	}

	/**
	 * Check Restaurant object has phone number or not
	 * 
	 * @return boolean
	 */
	public boolean isPhoneEmpty() {
		return phone.trim().equalsIgnoreCase("") || phone == null;
	}

	/**
	 * Check Restaurant object has web link or not
	 * 
	 * @return boolean
	 */
	public boolean isWebEmpty() {
		return web.trim().equalsIgnoreCase("") || web == null;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getWeb() {
		return web;
	}

	public String getPhone() {
		return phone;
	}

	public String getSummary() {
		return summary;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	// Setters
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRating() {
		return rating;
	}

	/**
	 * 
	 * Override method used by Parcelable
	 * 
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * @param dest
	 *            -writer used by Parcelable
	 * @param flag
	 *            -flag used by Parcelable
	 * 
	 *            Override method used by Parcelable
	 * 
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.latitude);
		dest.writeString(this.longitude);
		dest.writeString(this.web);
		dest.writeString(this.phone);
		dest.writeString(this.name);
		dest.writeString(this.summary);
		dest.writeString(this.rating);
	}

	/**
	 * 
	 * Parcelable Creator for Restaurant object
	 * 
	 */
	public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {

		/**
		 * @param source
		 *            -reader used by Parcelable
		 * @return -Restaurant object
		 * 
		 *         Override method used by Parcelable Creator
		 */
		@Override
		public Restaurant createFromParcel(Parcel source) {
			return new Restaurant(source);
		}

		/**
		 * @param source
		 *            -reader used by Parcelable
		 * 
		 * @return -Restaurant array
		 * 
		 *         Override method used by Parcelable Creator
		 */
		@Override
		public Restaurant[] newArray(int size) {
			return new Restaurant[size];
		}
	};

}
