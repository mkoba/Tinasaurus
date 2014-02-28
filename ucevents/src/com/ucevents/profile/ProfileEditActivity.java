package com.ucevents.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.ucevents.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.menu.MenuActivity;

public class ProfileEditActivity extends MenuActivity {

	Button pDone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profileedit);
		addListenerOnButton();
	}
	
	
	public void addListenerOnButton() {
		pDone = (Button) findViewById(R.id.Done);
		pDone.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(ProfileEditActivity.this, ProfileActivity.class);
			
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
