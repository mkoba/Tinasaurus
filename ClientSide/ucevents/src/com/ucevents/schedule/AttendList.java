package com.ucevents.schedule;
import java.util.ArrayList;

import com.android.ucevents.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.login.MainActivity;
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.MenuActivity;
import com.ucevents.signup.signupActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AttendList extends MenuActivity{
	
	ListView attList; 
	Button bList; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendlist);
		
		Bundle b = this.getIntent().getExtras();
		Schedule chosenEvent = b.getParcelable("chosenEvent");
		
		attList = (ListView) findViewById(R.id.allAttend);
		
		ArrayList<String> listAttend = new ArrayList<String>();
		
		listAttend.addAll(chosenEvent.getAttendees());
			
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                listAttend );

		attList.setAdapter(arrayAdapter); 
		
		//addListenerOnButton();
	}

	/**
	 * An example Activity using Google Analytics and EasyTracker.
	 */
	  @Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}
}
