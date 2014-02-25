package com.ucevents.events;
import com.android.ucevents.R;
import com.ucevents.menu.MenuActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
public class EventsActivity  extends MenuActivity{
	Button ballEvents;
	Button bbyCategory;
	Button bUserInterest;
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
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(EventsActivity.this, com.ucevents.events.EventsListActivity.class);
				intent.putExtra("key", "all");
				startActivity(intent);
			}
		});
		
		bbyCategory  = (Button)findViewById(R.id.bbyCategory);
		bbyCategory.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(EventsActivity.this, com.ucevents.events.CategoryEventsActivity.class);
				startActivity(intent);
			}
		});
	
		bUserInterest = (Button) findViewById(R.id.bUserInterest);
		bUserInterest.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(EventsActivity.this, com.ucevents.events.EventsListActivity.class);
				intent.putExtra("key", "interest");
				startActivity(intent);
			}
		});	
		
	}

}
