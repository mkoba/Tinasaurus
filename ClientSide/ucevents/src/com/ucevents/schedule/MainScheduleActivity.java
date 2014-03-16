package com.ucevents.schedule;
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

public class MainScheduleActivity extends MenuActivity{

	Button bAddEvent;
	Button bSchedule;
	Button bHost; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainschedule);
		addOnClickListener();
	}
	private void addOnClickListener() {
		bAddEvent = (Button) findViewById(R.id.addEvent);
		bAddEvent.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainScheduleActivity.this, com.ucevents.schedule.AddEvent.class);
				startActivity(intent);
			}
		});

		bSchedule = (Button)findViewById(R.id.schedule);
		bSchedule.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainScheduleActivity.this, com.ucevents.schedule.ScheduleActivity.class);
				intent.putExtra("key", "all");
				startActivity(intent);
			}
		});

	
		bHost = (Button) findViewById(R.id.hostEvent);
		bHost.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainScheduleActivity.this, com.ucevents.schedule.EventsHostActivity.class);
				intent.putExtra("key", "host");
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
