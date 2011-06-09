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

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Write Review Activity on selected Restaurant
 * 
 */
public class Review extends Activity {
	
	//Fields
	RatingBar rb1;
	RatingBar rb2;
	RatingBar rb3;
	TextView rName;
	TextView result;
	Button save;
	Context mContext;
	private float rb1_star = 0.0f;
	private float rb2_star = 0.0f;
	private float rb3_star = 0.0f;

	/**
	 * Called when the Review activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
		mContext = this;
		rb1 = (RatingBar) findViewById(R.id.rb1);
		rb1.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if (fromUser) {
					rb1_star = rating;
				}
				result.setText(getAverage());
			}

		});
		rb2 = (RatingBar) findViewById(R.id.rb2);
		rb2.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if (fromUser) {
					rb2_star = rating;
				}
				result.setText(getAverage());
			}

		});
		rb3 = (RatingBar) findViewById(R.id.rb3);
		rb3.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if (fromUser) {
					rb3_star = rating;
				}
				result.setText(getAverage());
			}

		});
		rName = (TextView) findViewById(R.id.rName);
		result = (TextView) findViewById(R.id.result);
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
				dialog.setTitle("Confirmation Box");
				dialog.setMessage("Do you want to submit your review?");
				dialog.setPositiveButton("YES",
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent main = new Intent(mContext,
										NearByList.class);
								main
										.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								startActivity(main);
							}

						});
				dialog.setNegativeButton("NO",
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}

						});
				dialog.show();
			}

		});
	}

	/**
	 * Called when the Review activity is started.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		rName.setText(getIntent().getExtras().getString("rName"));
	}

	/**
	 *  @return -average rating of 3 rating bars.
	 *  
	 *  	Helper method to get average rating of the 3 rating bars.
	 */
	private String getAverage() {

		float ave = (this.rb1_star + this.rb2_star + this.rb3_star) / 3;

		return "Average Rating: " + new DecimalFormat("#.#").format(ave);
	}

}
