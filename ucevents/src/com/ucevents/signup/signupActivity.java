package com.ucevents.signup;

import com.android.ucevents.R;
import com.android.ucevents.R.layout;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class signupActivity extends Activity{
     Button signUp;
     
     public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_signup);
    	
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
