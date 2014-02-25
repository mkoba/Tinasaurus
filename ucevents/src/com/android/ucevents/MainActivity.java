package com.android.ucevents;

import com.google.analytics.tracking.android.*;
import com.ucevents.signup.signupActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button bsignUp;
	Button blogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//google analytics portion 
		Tracker tracker = GoogleAnalytics.getInstance(this).getTracker("UA-48367059-1");
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		addListenerOnButton();
		
	/*	// Set screen name on the tracker to be sent with all hits.
		MyApp.getGaTracker().set(Fields.SCREEN_NAME, "Main Activity");
		
		// Send a screen view for "Home Screen"
		tracker.send(MapBuilder
		    .createAppView()
		    .build()
		);
		
		// Clear the screen name field when we're done.
		tracker.set(Fields.SCREEN_NAME, null);*/
	}
	
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
