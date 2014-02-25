package com.ucevents.menu;

import com.android.ucevents.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity{
	Button ballEvents;
	Button bbyCategory;
	Button bUserInterest;
/*	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_events);
    }*/
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	/**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_event:
            clickedEvent();
            return true;
        case R.id.action_schedule:
            clickedSchedule();
            return true;
        case R.id.action_profile:
            clickedProfile();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void clickedEvent() {
    	//Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MenuActivity.this, com.ucevents.events.EventsActivity.class);
		startActivity(intent);
    }
    
	private void clickedSchedule() {
		//Toast.makeText(getApplicationContext(), "Schedule", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MenuActivity.this, com.ucevents.schedule.ScheduleActivity.class);
		startActivity(intent);
	}
	

	private void clickedProfile() {
		//Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MenuActivity.this, com.ucevents.profile.ProfileActivity.class);
		startActivity(intent);
	}
}
