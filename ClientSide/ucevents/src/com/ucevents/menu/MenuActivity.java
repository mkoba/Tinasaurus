package com.ucevents.menu;

import com.android.ucevents.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity{
    
	Button ballEvents;
	Button bbyCategory;
	Button bUserInterest;
	Button logout;
	
	
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
        case R.id.action_logout:
        	clickedLogout();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void clickedEvent() {
		Intent intent = new Intent(MenuActivity.this, com.ucevents.events.EventsActivity.class);
		startActivity(intent);
    }
    
	private void clickedSchedule() {
		Intent intent = new Intent(MenuActivity.this, com.ucevents.schedule.MainScheduleActivity.class);
		startActivity(intent);
	}
	

	private void clickedProfile() {
		Intent intent = new Intent(MenuActivity.this, com.ucevents.profile.ProfileActivity.class);
		startActivity(intent);
	}
	
	private void clickedLogout() {
		Intent intent = new Intent(MenuActivity.this, com.ucevents.login.LogoutActivity.class);
		startActivity(intent);
	}
	
}
