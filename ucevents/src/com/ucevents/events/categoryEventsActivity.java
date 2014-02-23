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

public class categoryEventsActivity extends Activity{
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
	
		key="sport";
		final Intent i = new Intent(getApplicationContext(), com.ucevents.events.EventsListActivity.class);
		final Bundle bundle = new Bundle();
		
		ibSport = (ImageButton) findViewById(R.id.ibSport);
		ibSport.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				//bundle.putString(key, "sport");
				//i.putExtras(bundle);
				Intent i2 = new Intent(categoryEventsActivity.this, com.ucevents.events.EventsListActivity.class);
				i2.putExtra(key, "sport");
				startActivity(i2);
			}
			
		});
		
		key="food";
		ibFood = (ImageButton) findViewById(R.id.ibFood);
		ibFood.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibFood);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		key="study";
		ibStudy = (ImageButton) findViewById(R.id.ibStudy);
		ibStudy.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibStudy);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		key="career";
		ibCareer = (ImageButton) findViewById(R.id.ibCareer);
		ibCareer.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibCareer);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		key="club";
		ibClub = (ImageButton) findViewById(R.id.ibClub);
		ibClub.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibClub);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		key="social";
		ibSocial = (ImageButton) findViewById(R.id.ibSocial);
		ibSocial.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibSocial);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		key="other";
		ibOther = (ImageButton) findViewById(R.id.ibOther);
		ibOther.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibOther);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		
	}
}
