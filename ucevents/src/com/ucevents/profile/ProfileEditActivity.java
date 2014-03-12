package com.ucevents.profile;

import java.util.ArrayList;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ucevents.R;
import com.android.ucevents.UCEvents_App;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.menu.MenuActivity;
import com.ucevents.signup.signupActivity;

public class ProfileEditActivity extends MenuActivity {

	Button pDone;
	private String email;
	private ArrayList<String> interests = new ArrayList<String>(7);
	private ArrayList<String> dbInterests = new ArrayList<String>();
	private ArrayList<String> interestsFinal = new ArrayList<String>();
	private CheckBox cbCareer;
	private CheckBox cbFood;
	private CheckBox cbOrganization;
	private CheckBox cbSocial;
	private CheckBox cbSport;
	private CheckBox cbStudy;
	private CheckBox cbOther;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profileedit);
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		email = appState.getUserId();
	   	//initialize interests array
	   	for(int i = 0; i < 7; i++) {
	   		 interests.add("false");
	   	}
	   	// grab user info from db
		updateUserInfo();
		
		// grab new/modified interests
		grabInterests();
		
		// done button
		addListenerOnButton();
	}
	
	public void updateUserInfo() {
		/* DB: populate interest list here 
		 *  to dbInterests
		 */
		
		// temp values  (hard-coded)
		dbInterests.add("other");
		dbInterests.add("social");
		for( int i = 0; i < dbInterests.size(); i++) {
			if(dbInterests.get(i).equals("career")) {
				cbCareer = (CheckBox) findViewById(R.id.career);
				cbCareer.setChecked(true);
				interests.set(0, "career");
			}
			else if(dbInterests.get(i).equals("food")) {
				cbFood = (CheckBox) findViewById(R.id.food);
				cbFood.setChecked(true);
				interests.set(1,"food");
			}
			else if(dbInterests.get(i).equals("organization")) {
				cbOrganization = (CheckBox)findViewById(R.id.organization);
				cbOrganization.setChecked(true);
				interests.set(2,"organization");
			}
			else if(dbInterests.get(i).equals("social")) {
				cbSocial = (CheckBox)findViewById(R.id.social);
				cbSocial.setChecked(true);
				interests.set(3,"social");
			}
			else if(dbInterests.get(i).equals("sport")) {
				cbSport = (CheckBox)findViewById(R.id.sport);
				cbSport.setChecked(true);
				interests.set(4,"sport");
			}
			else if(dbInterests.get(i).equals("study")) {
				cbStudy = (CheckBox) findViewById(R.id.study);
				cbStudy.setChecked(true);
				interests.set(5,"study");
			}
			else { //if(dbInterests.get(i).equals("other")) {
				cbOther = (CheckBox) findViewById(R.id.other);
				cbOther.setChecked(true);
				interests.set(6,"other");
			}
		}
		
	}
	
	private void grabInterests() {
		cbCareer = (CheckBox) findViewById(R.id.career);
		cbCareer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "Career", Toast.LENGTH_SHORT).show();
					interests.set(0,"career");
				}
				else {
					interests.set(0, "false");
				}
			}
		});
		cbFood = (CheckBox) findViewById(R.id.food);
		cbFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "Food", Toast.LENGTH_SHORT).show();
					interests.set(1,"food");
				}
				else {
					interests.set(1, "false");
				}
			}
		});
		cbOrganization = (CheckBox)findViewById(R.id.organization);
		cbOrganization.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "organization", Toast.LENGTH_SHORT).show();
					interests.set(2,"organization");
				}
				else {
					interests.set(2, "false");
				}
			}
		});
		cbSocial = (CheckBox)findViewById(R.id.social);
		cbSocial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "social", Toast.LENGTH_SHORT).show();
					interests.set(3,"social");
				}
				else {
					interests.set(3, "false");
				}
			}
		});
		cbSport = (CheckBox) findViewById(R.id.sport);
		cbSport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "Sport", Toast.LENGTH_SHORT).show();
					interests.set(4,"sport");
				}
				else {
					interests.set(4, "false");
				}
			}
		});
		cbStudy = (CheckBox) findViewById(R.id.study);
		cbStudy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "study", Toast.LENGTH_SHORT).show();
					interests.set(5,"study");
				}
				else {
					interests.set(5, "false");
				}
			}
		});
		cbOther = (CheckBox) findViewById(R.id.other);
		cbOther.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(ProfileEditActivity.this, "other", Toast.LENGTH_SHORT).show();
					interests.set(6,"other");
				}
				else {
					interests.set(6, "false");
				}
			}
		});
		
		
	}
	
	public void addListenerOnButton() {
		pDone = (Button) findViewById(R.id.done);
		pDone.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
				//grab list of interest to add to db
			   	 for(int i = 0; i < interests.size(); i++) {
					 if(!interests.get(i).equals("false")) {
						 System.out.println("interests: " + interests.get(i));
						 interestsFinal.add(interests.get(i));
					 }
				 }
			   	 
			   	/****
			   	  * DB: grab the data you need here and insert into db
			   	  * to update profile info
			   	  *  email, interestsFinal  
			   	  */
			   	 
				Intent i= new Intent(ProfileEditActivity.this, ProfileActivity.class);
				Bundle b = new Bundle();
				
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
