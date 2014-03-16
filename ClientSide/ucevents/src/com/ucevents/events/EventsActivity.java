package com.ucevents.events;
import com.android.ucevents.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

//Google Analytics
import android.app.Activity;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.menu.MenuActivity;

public class EventsActivity extends MenuActivity{

	Button ballEvents;
	Button bbyCategory;
	Button bUserInterest;
	Button bHost; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_events);
		addOnClickListener();
	}
	private void addOnClickListener() {
		ballEvents = (Button) findViewById(R.id.ballEvents);
		ballEvents.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EventsActivity.this, com.ucevents.events.EventsListActivity.class);
				intent.putExtra("key", "all");
				startActivity(intent);
			}
		});

		bbyCategory  = (Button)findViewById(R.id.bbyCategory);
		bbyCategory.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EventsActivity.this, com.ucevents.events.CategoryEventsActivity.class);
				startActivity(intent);
			}
		});

		bUserInterest = (Button) findViewById(R.id.bUserInterest);
		bUserInterest.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EventsActivity.this, com.ucevents.events.EventsListActivity.class);
				intent.putExtra("key", "interest");
				startActivity(intent);
			}
		});	


	}
	/**
	 * An example Activity using Google Analytics and EasyTracker.
	 */
	  @Override
	  public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);

	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this);
	}

}
