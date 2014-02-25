package com.ucevents.schedule;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.ucevents.R;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

public class ScheduleActivity extends MenuActivity {
	Button addEvent;
	Button createBut; 
	private List<Events> schList = new ArrayList<Events>();
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_schedule);
		addOnClickListener();
	}

	private void addOnClickListener() {
		createBut = (Button) findViewById(R.id.addEvent);
		createBut.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ScheduleActivity.this, com.ucevents.schedule.AddEvent.class);
				startActivity(intent);
			}
		});
		
	}
	
	
}
