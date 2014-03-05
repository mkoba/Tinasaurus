package com.ucevents.schedule;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

public class ScheduleActivity extends MenuActivity {
	Button addEvent;
	Button createBut; 
	
	String value; 
	private List<Events> schList = new ArrayList<Events>();
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		addOnClickListener();
		
		
		Bundle bundle = getIntent().getExtras();
		value = bundle.getString("key");
		Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
		populateEventList();
		populateListView();
		registerClickCallback();
	}

	// this will be later populated from db
	private void populateEventList() {
		schList.add(new Events(1, "Roaming", 300, "CENTER 200", 2, 14, 2014, "Valentine Fest", 1, R.drawable.play_icon));
		schList.add(new Events(2, "BOBALAND", 400, "CENTER 200", 2, 14, 2014, "Milk Tea Fest", 1,R.drawable.play_icon));
		schList.add(new Events(3, "ADVENture time", 300, "CENTER 202", 2, 14, 2014, "Dinosaurs Fest", 1, R.drawable.schedule_icon));
		schList.add(new Events(4, "SLEEP", 300, "CENTER 200", 2, 14, 2014, "Pillows Fest", 1, R.drawable.calendar_icon));
		schList.add(new Events(5, "EATTING PARTY", 600, "WLH 200", 2, 14, 2014, "Pizza Fest", 1, R.drawable.study_icon));
		schList.add(new Events(6, "EATTING PARTY", 300, "LALA 200", 2, 14, 2014, "Pizza Fest", 1, R.drawable.study_icon));
		schList.add(new Events(7, "MUSIC", 300, "CENTER 200", 2, 14, 2014, "Pizza Fest", 1, R.drawable.schedule_icon));
	}

	private void populateListView() {
		ArrayAdapter<Events> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.lvallEvents);
		list.setAdapter(adapter);
		
	}
	
	private class MyListAdapter extends ArrayAdapter<Events> {
		public MyListAdapter() {
			super(ScheduleActivity.this, R.layout.eventslistview, schList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			//make sure we have a view to work with (may have been given null)
			if(itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.schedulelistview, parent, false);
			}
			
			// find event to work with
			Events currEvent = schList.get(position);
			
			// fill the view
			ImageView imageView = (ImageView) itemView.findViewById(R.id.item_ivevent_icon);
			imageView.setImageResource(currEvent.getIconid());
			
			// Name:
			TextView nameText = (TextView) itemView.findViewById(R.id.item_tvName);
			nameText.setText(currEvent.getName());
			
			// Description:
			TextView descriptionText = (TextView) itemView.findViewById(R.id.item_txtDescription);
			descriptionText.setText(currEvent.getDescription());
			
			// Time:
			TextView timeText = (TextView) itemView.findViewById(R.id.item_txtTime);
			timeText.setText(""+ currEvent.getTime());
			
			// Date:
			TextView dateText = (TextView) itemView.findViewById(R.id.item_txtDate);
			dateText.setText(""+currEvent.getMonth() + " " + currEvent.getDate() + " " + currEvent.getYear());
			
			// Location:
			TextView locationText = (TextView) itemView.findViewById(R.id.item_txtLocation);
			locationText.setText(currEvent.getLocation());
			
			return itemView;
		}
	}	
		
	private void registerClickCallback() {
			ListView list = (ListView) findViewById(R.id.lvallEvents);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				// position: place in list
				@Override
				public void onItemClick(AdapterView<?> parent, View viewClicked,
				int position, long id) {
					
					Events clickedEvent = schList.get(position);
					Intent i = new Intent(ScheduleActivity.this, com.ucevents.schedule.IndividualScheduleActivity.class);
					Bundle b = new Bundle();
					b.putParcelable("chosenEvent", clickedEvent);
					i.putExtras(b);
					startActivity(i);
				}
			});
			
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
