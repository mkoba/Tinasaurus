package com.ucevents.schedule;

import android.os.Bundle;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;


import com.android.ucevents.MainActivity;
import com.android.ucevents.R;
import com.ucevents.menu.MenuActivity;
import com.ucevents.signup.signupActivity;

public class AddEvent extends MenuActivity{
	Button createBut;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		createBut = (Button) findViewById(R.id.Done);
		createBut.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(AddEvent.this, com.ucevents.tab.Tabs.class);
			
				startActivity(i);
				}
			});

	}
	
		
}
