package com.ucevents.profile;


import java.util.ArrayList;
import java.util.List;

import com.android.ucevents.R;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;


public class ProfileActivity extends MenuActivity {

	Button bsignUp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	//	addListenerOnButton();
	}
	
/*

	
	public void addListenerOnButton() {
		bsignUp = (Button) findViewById(R.id.signUp);
		bsignUp.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(MainActivity.this, signupActivity.class);
			
				startActivity(i);
				}
			});
		
		blogin = (Button) findViewById(R.id.login);
		blogin.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(MainActivity.this, com.ucevents.login.loginActivity.class);
			
				startActivity(i);
				}
			});
	}*/
	
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
