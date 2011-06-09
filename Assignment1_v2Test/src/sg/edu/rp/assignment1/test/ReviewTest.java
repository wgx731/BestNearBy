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
package sg.edu.rp.assignment1.test;

import sg.edu.rp.assignment1.Review;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 
 * @author wgx731
 * 
 * @version 1.0.0.1
 * 
 *          Review Activity Test
 * 
 */
//TODO: Add NearByListTest and RestaurantMapViewTest
//TODO: Follow Activity Test for better test case
public class ReviewTest extends ActivityInstrumentationTestCase2<Review> {
	
	//Fields
	private Review mActivity;
	private TextView rName;
	private TextView rb1_lb;
	private String rb1_lb_str;
	private TextView rb2_lb;
	private String rb2_lb_str;;
	private TextView rb3_lb;
	private String rb3_lb_str;
	private TextView result;
	private RatingBar rb1;
	private RatingBar rb2;
	private RatingBar rb3;
	private Button save;
	private String save_str;
	/**
	 * 
	 * Default constructor used by Android JUnit test framework
	 * 
	 * Important do not change to other constructors.
	 * 
	 */
	public ReviewTest() {
		super("sg.edu.rp.assignment1", Review.class);
	}

	
	/**
	 * setUp method do set up before actual testing
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		rName = (TextView) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rName);
		rb1_lb = (TextView) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rb1_lb);
		rb2_lb = (TextView) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rb2_lb);
		rb3_lb = (TextView) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rb3_lb);
		result = (TextView) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.result);
		save = (Button) mActivity.findViewById(sg.edu.rp.assignment1.R.id.save);
		rb1 = (RatingBar) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rb1);
		rb2 = (RatingBar) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rb2);
		rb3 = (RatingBar) mActivity
				.findViewById(sg.edu.rp.assignment1.R.id.rb3);
		rb1_lb_str = mActivity.getString(sg.edu.rp.assignment1.R.string.rb1_lb);
		rb2_lb_str = mActivity.getString(sg.edu.rp.assignment1.R.string.rb2_lb);
		rb3_lb_str = mActivity.getString(sg.edu.rp.assignment1.R.string.rb3_lb);
		save_str = mActivity.getString(sg.edu.rp.assignment1.R.string.save);
	}

	/**
	 * Unit Test function
	 */
	public void testPreconditions() {
		assertNotNull("rName is null.", rName);
		assertNotNull("rb1_lb is null.", rb1_lb);
		assertNotNull("rb2_lb is null.", rb2_lb);
		assertNotNull("rb3_lb is null.", rb3_lb);
		assertNotNull("rb1 is null.", rb1);
		assertNotNull("rb2 is null.", rb2);
		assertNotNull("rb3 is null.", rb3);
		assertNotNull("result is null.", result);
		assertNotNull("save is null.", save);
	}

	/**
	 * Simple test method to asset text resource in the views
	 */
	public void testText() {
		assertEquals(rb1_lb_str, (String) rb1_lb.getText());
		assertEquals(rb2_lb_str, (String) rb2_lb.getText());
		assertEquals(rb3_lb_str, (String) rb3_lb.getText());
		assertEquals(save_str, (String) save.getText());
	}
}
