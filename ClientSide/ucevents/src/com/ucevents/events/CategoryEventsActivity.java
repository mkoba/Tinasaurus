package com.ucevents.events;

import com.android.ucevents.R;

import com.ucevents.menu.MenuActivity;

import com.google.analytics.tracking.android.EasyTracker;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class CategoryEventsActivity extends MenuActivity{
	ImageButton ibSport;
	ImageButton ibFood;
	ImageButton ibStudy;
	ImageButton ibCareer;
	ImageButton ibClub;
	ImageButton ibSocial;
	ImageButton ibOther;
	String key;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_category);
		addListenerOnButton();
	}

	private void addListenerOnButton() {
		ibSport = (ImageButton) findViewById(R.id.ibSport);
		ibSport.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				Intent i2 = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class);
				i2.putExtra("key", "sports");
				startActivity(i2);
			}
			
		});
		
		key="food";
		ibFood = (ImageButton) findViewById(R.id.ibFood);
		ibFood.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
				Intent i = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class );
				i.putExtra("key", "food");
				startActivity(i);
			}
			
		});
		
		key="study";
		ibStudy = (ImageButton) findViewById(R.id.ibStudy);
		ibStudy.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				Intent i = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class );
				i.putExtra("key", "study");
				startActivity(i);
			}
			
		});
		
		key="career";
		ibCareer = (ImageButton) findViewById(R.id.ibCareer);
		ibCareer.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				Intent i = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class );
				i.putExtra("key", "career");
				startActivity(i);
			}
			
		});
		
		key="club";
		ibClub = (ImageButton) findViewById(R.id.ibClub);
		ibClub.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				Intent i = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class );
				i.putExtra("key", "organization");
				startActivity(i);
			}
			
		});
		
		key="social";
		ibSocial = (ImageButton) findViewById(R.id.ibSocial);
		ibSocial.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				Intent i = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class );
				i.putExtra("key", "social");
				startActivity(i);
			}
			
		});
		
		key="other";
		ibOther = (ImageButton) findViewById(R.id.ibOther);
		ibOther.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				Intent i = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class );
				i.putExtra("key", "other");
				startActivity(i);
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
