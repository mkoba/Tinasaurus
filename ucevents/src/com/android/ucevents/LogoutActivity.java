package com.android.ucevents;

import com.facebook.Session;
import com.ucevents.events.CategoryEventsActivity;
import com.ucevents.menu.MenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LogoutActivity extends MenuActivity {
	private Button logout;
	private Button cancel;
	
	@Override 
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.logout_popup);
		
		logout = (Button)findViewById(R.id.logout);
		cancel = (Button)findViewById(R.id.cancel);
		
		logout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
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
			
		});
		
		
		cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
				Intent i = new Intent(LogoutActivity.this, com.ucevents.events.EventsActivity.class );
				startActivity(i);
			}
			
		});	
	}
}
