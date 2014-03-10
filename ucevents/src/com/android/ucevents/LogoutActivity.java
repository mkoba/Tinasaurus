package com.android.ucevents;

import com.facebook.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LogoutActivity extends Activity {
	@Override 
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		
		Session session = Session.getActiveSession();
		if (session != null) {
			System.out.println("got a session");
	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	            Intent i = new Intent(LogoutActivity.this, MainActivity.class);
	            startActivity(i);
	            //clear your preferences if saved
	        }
	    } else {

	    	System.out.println("cannot find session");

	    }
	}
}
