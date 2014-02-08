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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_category);
		addListenerOnButton();
	}

	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		final String key="categoryType";
		
		final Intent i = new Intent(getApplicationContext(), com.ucevents.events.eventsActivity.class);
		final Bundle bundle = new Bundle();
		
		ibSport = (ImageButton) findViewById(R.id.ibSport);
		ibSport.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				bundle.putInt(key, R.id.ibSport);
				i.putExtras(bundle);
				startActivity(i);
			}
			
		});
		
		ibFood = (ImageButton) findViewById(R.id.ibFood);
		ibStudy = (ImageButton) findViewById(R.id.ibStudy);
		ibCareer = (ImageButton) findViewById(R.id.ibCareer);
		ibClub = (ImageButton) findViewById(R.id.ibClub);
		ibSocial = (ImageButton) findViewById(R.id.ibSocial);
		ibOther = (ImageButton) findViewById(R.id.ibOther);
		
		
		
	}
}
