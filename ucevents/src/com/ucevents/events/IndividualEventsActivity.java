package com.ucevents.events;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;

public class IndividualEventsActivity extends Activity {
	ImageView ivIconID;
	TextView tvName;
	TextView tvLocation;
	TextView tvDate;
	TextView tvTime;
	TextView tvDescription;
	CheckBox cbRSVP;
	TextView tvRSVPCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		Bundle b = this.getIntent().getExtras();
		
		// grab event clicked from bundle
		Events chosenEvent = b.getParcelable("chosenEvent");
		//Toast.makeText(getApplicationContext(), chosenEvent.getDescription(), Toast.LENGTH_SHORT).show();
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
		tvRSVPCount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// update db with RSVP value here
				Toast.makeText(getApplicationContext(), "RSVP", Toast.LENGTH_SHORT).show();
			}
		});
		
		// add code: grab rsvp count from db
	}
}
