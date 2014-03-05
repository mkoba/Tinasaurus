package com.ucevents.signup;

import com.android.ucevents.R;
import com.android.ucevents.R.layout;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.login.loginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class signupActivity extends Activity{
     Button bsignUp;
     Button bBack;
     
     public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_signup);
    	 addListenerOnButton();
    	
     }
     
     
 	private void addListenerOnButton() {
		bsignUp = (Button) findViewById(R.id.signup);
		bsignUp.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				//Intent i= new Intent(loginActivity.this, com.ucevents.tab.Tabs.class);
				Intent i= new Intent(signupActivity.this, com.ucevents.events.EventsActivity.class);
				startActivity(i);
				}
			});
		
		bBack = (Button) findViewById(R.id.back);
		bBack.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				//Intent i= new Intent(loginActivity.this, com.ucevents.tab.Tabs.class);
				Intent i= new Intent(signupActivity.this, com.android.ucevents.MainActivity.class);
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
