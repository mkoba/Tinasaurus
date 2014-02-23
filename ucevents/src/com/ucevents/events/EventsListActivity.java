package com.ucevents.events;

import java.util.ArrayList;
import java.util.List;

import com.android.ucevents.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EventsListActivity extends Activity{
	String value;
	private List<Events> eventlist = new ArrayList<Events>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allevents);
		Bundle bundle = getIntent().getExtras();
		value = bundle.getString("key");
		System.out.println("key value: " + value);
		populateEventList();
		populateListView();
		addListenerOnButton();
		
	}


	private void populateEventList() {
		eventlist.add(new Events(1, "Roaming", 300, "CENTER 200", 2, 14, 2014, "Valentine Fest", 1, R.drawable.play_icon));
		eventlist.add(new Events(2, "BOBALAND", 400, "CENTER 200", 2, 14, 2014, "Milk Tea Fest", 1,R.drawable.play_icon));
		eventlist.add(new Events(3, "ADVENture time", 300, "CENTER 202", 2, 14, 2014, "Dinosaurs Fest", 1, R.drawable.schedule_icon));
		eventlist.add(new Events(4, "SLEEP", 300, "CENTER 200", 2, 14, 2014, "Pillows Fest", 1, R.drawable.calendar_icon));
		eventlist.add(new Events(5, "EATTING PARTY", 600, "WLH 200", 2, 14, 2014, "Pizza Fest", 1, R.drawable.study_icon));
		eventlist.add(new Events(6, "EATTING PARTY", 300, "LALA 200", 2, 14, 2014, "Pizza Fest", 1, R.drawable.study_icon));
		eventlist.add(new Events(7, "MUSIC", 300, "CENTER 200", 2, 14, 2014, "Pizza Fest", 1, R.drawable.schedule_icon));
	}

	private void populateListView() {
		ArrayAdapter<Events> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.lvallEvents);
		list.setAdapter(adapter);
		
	}
	
	private class MyListAdapter extends ArrayAdapter<Events> {
		public MyListAdapter() {
			super(EventsListActivity.this, R.layout.eventslistview, eventlist);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			//make sure we have a view to work with (may have been given null)
			if(itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.eventslistview, parent, false);
			}
			
			// find event to work with
			Events currEvent = eventlist.get(position);
			
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

	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		
	}

}
