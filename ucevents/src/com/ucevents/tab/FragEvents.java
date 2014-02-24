package com.ucevents.tab;


import com.android.ucevents.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragEvents extends Fragment {
	Button ballEvents;
	Button bbyCategory;
	Button bUserInterest;
	

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
		View view = inflater
				.inflate(R.layout.frag_events, container, false);
		ballEvents = (Button) view.findViewById(R.id.ballEvents);
		ballEvents.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(getActivity(), com.ucevents.events.EventsListActivity.class);
				intent.putExtra("key", "all");
				getActivity().startActivity(intent);
			}
		});
		
		bbyCategory  = (Button) view.findViewById(R.id.bbyCategory);
		bbyCategory.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(getActivity(), com.ucevents.events.CategoryEventsActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		bUserInterest = (Button) view.findViewById(R.id.bUserInterest);
		bUserInterest.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(getActivity(), com.ucevents.events.EventsListActivity.class);
				intent.putExtra("key", "interest");
				getActivity().startActivity(intent);
			}
		});		
		
		
		return view;

	}
	
	/*public void addListenerOnButton(View view) {
		ballEvents = (Button) view.findViewById(R.id.ballEvents);
		ballEvents.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
				System.out.println("listener added");
				Intent i= new Intent(FragEvents.this, com.ucevents.events.allEventsActivity.class);
			
				startActivity(i);
				}
			});
	}*/

}
