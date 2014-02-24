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

public class CategoryEventsActivity extends Activity{
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_category);
		addListenerOnButton();
	}

	private void addListenerOnButton() {
	
		
		//final Intent i = new Intent(getApplicationContext(), com.ucevents.events.EventsListActivity.class);
		final Bundle bundle = new Bundle();
		
		ibSport = (ImageButton) findViewById(R.id.ibSport);
		ibSport.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				//bundle.putString(key, "sport");
				//i.putExtras(bundle);
				Intent i2 = new Intent(CategoryEventsActivity.this, com.ucevents.events.EventsListActivity.class);
				i2.putExtra("key", "sport");
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
				i.putExtra("key", "club");
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
}
