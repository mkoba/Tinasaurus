package com.ucevents.login;

import com.android.ucevents.R;
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
		setContentView(R.layout.activity_logout);
		
		logout = (Button)findViewById(R.id.logout);
		cancel = (Button)findViewById(R.id.cancel);
		
		logout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
				Session session = Session.getActiveSession();
				if (session != null) {
					// logout of fb and redirect to login
			        if (!session.isClosed()) {
			            session.closeAndClearTokenInformation();
			            Intent i = new Intent(LogoutActivity.this, MainActivity.class);
			            startActivity(i);
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
