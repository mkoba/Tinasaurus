package com.ucevents.tab;

import java.util.ArrayList;
import java.util.List;

import com.android.ucevents.MainActivity;
import com.android.ucevents.R;
import com.android.ucevents.R.layout;
import com.ucevents.events.Events;
import com.ucevents.signup.signupActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragSchedule extends Fragment {

	
	Button addEvent;
	private List<Events> schList = new ArrayList<Events>();
	
	
	public FragSchedule() {
		// Required empty public constructor
	}

	Button createBut; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.frag_schedule, container, false);
		createBut = (Button) view.findViewById(R.id.addEvent);
		createBut.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), com.ucevents.schedule.AddEvent.class);
				getActivity().startActivity(intent);
			}
		});
		
	//	populateScheduleList();
	//	populateListView();
		
		return view;
	}
	

/*	private void populateScheduleList() {
		schList.add(new Events(1, "Roaming", 300, "CENTER 200", 2, 14, 2014, "Valentine Fest", 1, R.drawable.play_icon));
		schList.add(new Events(2, "BOBALAND", 400, "CENTER 200", 2, 14, 2014, "Milk Tea Fest", 1,R.drawable.play_icon));

	}
	
	private void populateListView() {
		ArrayAdapter<Events> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.lvallEvents);
		list.setAdapter(adapter);
		
	}
	
	private class MyListAdapter extends ArrayAdapter<Events> {
		public MyListAdapter() {
			super(allEventsActivity.this, R.layout.eventslistview, schlist);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			//make sure we have a view to work with (may have been given null)
			if(itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.eventslistview, parent, false);
			}
			
			// find event to work with
			Events currEvent = schlist.get(position);
			
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
		
*/
}
