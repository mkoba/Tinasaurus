package com.ucevents.schedule;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

public class IndividualScheduleActivity extends MenuActivity {
	ImageView ivIconID;
	TextView tvName;
	TextView tvLocation;
	TextView tvDate;
	TextView tvTime;
	TextView tvDescription;
	TextView tvRSVPCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indschedule);
		Bundle b = this.getIntent().getExtras();
		
		// grab event clicked from bundle
		Events chosenEvent = b.getParcelable("chosenEvent");
		
		ivIconID = (ImageView) findViewById(R.id.ivIconID);
		ivIconID.setImageResource(chosenEvent.getIconid());
		
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(chosenEvent.getName());
		
		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvLocation.setText(chosenEvent.getLocation());
		
		tvDate = (TextView) findViewById(R.id.tvDate);
		tvDate.setText(String.valueOf(chosenEvent.getMonth()) + " " + 
				String.valueOf(chosenEvent.getDate()) + " " +
				String.valueOf(chosenEvent.getYear()) );
		
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setText(String.valueOf(chosenEvent.getTime()));
		
		tvDescription = (TextView) findViewById(R.id.tvDescription);
		tvDescription.setText(chosenEvent.getDescription());
		
		tvRSVPCount = (CheckBox) findViewById(R.id.checkBoxRSVP);

		
		// add code: grab rsvp count from db
	}
	
	/**
	 * An example Activity using Google Analytics and EasyTracker.
	 */
	  @Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}
}