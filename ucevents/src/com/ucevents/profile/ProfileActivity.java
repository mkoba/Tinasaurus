package com.ucevents.profile;


import java.util.ArrayList;
import java.util.List;

import com.android.ucevents.R;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;


public class ProfileActivity extends MenuActivity {

	Button bEdit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		addListenerOnButton();
			
		

	 
	    TextView firstName = (TextView)findViewById(R.id.f_name);
	    firstName.setText("Leon");
	    
	    TextView lastName = (TextView)findViewById(R.id.l_name);
	    lastName.setText("is");
	    
	    TextView email = (TextView)findViewById(R.id.email);
	    email.setText("a");

	    
	    TextView interest = (TextView)findViewById(R.id.interest);
	    interest.setText("Simba");
	}
	
	

	
	public void addListenerOnButton() {
		bEdit = (Button) findViewById(R.id.edit);
		bEdit.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(ProfileActivity.this, ProfileEditActivity.class);
			
				startActivity(i);
				}
			});

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
