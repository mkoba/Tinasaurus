package com.ucevents.signup;

import java.util.ArrayList;

import com.android.ucevents.R;
import com.android.ucevents.R.layout;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.login.loginActivity;
import com.ucevents.menu.LogoutMenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class signupActivity extends LogoutMenuActivity{
    Button bsignUp;
    //Button bBack;
    private TextView userInfoTextView; 
	private String firstname;
	private String lastname;
	private String email;
	private ArrayList<String> interests = new ArrayList<String>(7);
	private ArrayList<String> interestsFinal = new ArrayList<String>();
	private CheckBox cbCareer;
	private CheckBox cbFood;
	private CheckBox cbOrganization;
	private CheckBox cbSocial;
	private CheckBox cbSport;
	private CheckBox cbStudy;
	private CheckBox cbOther;
	private StringBuilder userInfo = new StringBuilder("");
	
     public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_signup);
    	 
    	 Bundle b = this.getIntent().getExtras();
    	 firstname = b.getString("firstname");
    	 lastname = b.getString("lastname");
    	 email = b.getString("email");
    	 userInfoTextView = (TextView) findViewById(R.id.userInfoTextView);
    	 userInfoTextView.setText(buildUserInfoDisplay());
    	 
    	 //initialize interests array
    	 for(int i = 0; i < 7; i++) {
    		 interests.add("false");
    	 }
    	 //grab interests and add to db
    	 grabInterests();
    	 addListenerOnButton();

     }
     
     
 	private void grabInterests() {
		cbCareer = (CheckBox) findViewById(R.id.career);
		cbCareer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()) {
					Toast.makeText(signupActivity.this, "Career", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(signupActivity.this, "Food", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(signupActivity.this, "organization", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(signupActivity.this, "social", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(signupActivity.this, "Sport", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(signupActivity.this, "study", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(signupActivity.this, "other", Toast.LENGTH_SHORT).show();
					interests.set(6,"other");
				}
				else {
					interests.set(6, "false");
				}
			}
		});
		
		
	}


	private void addListenerOnButton() {
		bsignUp = (Button) findViewById(R.id.signup);
		bsignUp.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
				
				//grab list of interest to add to db
			   	 for(int i = 0; i < interests.size(); i++) {
					 if(!interests.get(i).equals("false")) {
						 System.out.println("interests: " + interests.get(i));
						 interestsFinal.add(interests.get(i));
					 }
				 }
			   	 
			   	 /*for(int i=0; i < interestsFinal.size(); i++) {
			   		
					System.out.print(interestsFinal.get(i));
				}*/
				//Intent i= new Intent(loginActivity.this, com.ucevents.tab.Tabs.class);
				
			   	 /****
			   	  * DB: grab the data you need here and insert into db
			   	  * firstname, lastname, email, interestsFinal  
			   	  */
			   	 
			   	 
			   	 Intent i= new Intent(signupActivity.this, com.ucevents.events.EventsActivity.class);
				startActivity(i);
				}
			});
		
		

		
		/*bBack = (Button) findViewById(R.id.back);
		bBack.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				//Intent i= new Intent(loginActivity.this, com.ucevents.tab.Tabs.class);
				Intent i= new Intent(signupActivity.this, com.android.ucevents.MainActivity.class);
				startActivity(i);
				}
			});*/
		
	}
	
	/*
	 *  set user info to text view
	 */
	private String buildUserInfoDisplay() {
	    userInfo = new StringBuilder("");

	    // Example: typed access (name)
	    // - no special permissions required
	    userInfo.append(String.format("First Name: %s\n\n", 
	       firstname));

	    // Example: typed access (birthday)
	    // - requires user_birthday permission
	    userInfo.append(String.format("Last Name: %s\n\n", 
	        lastname));
	    
	    userInfo.append(String.format("Email: %s\n\n", email));
	    
	    return userInfo.toString();
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
