package com.ucevents.signup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.android.ucevents.R;
import com.android.ucevents.R.layout;
import com.facebook.widget.ProfilePictureView;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.LogoutMenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class signupActivity extends LogoutMenuActivity{
	Button bsignUp;
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
	private ProfilePictureView profilePictureView;
	private String useremail;
	private TextView tvEmail;
	private boolean interestExist = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		Bundle b = this.getIntent().getExtras();
		firstname = b.getString("firstname");
		lastname = b.getString("lastname");
		email = b.getString("email");
		userInfoTextView = (TextView) findViewById(R.id.f_name);
		userInfoTextView.setText(firstname + " " + lastname);
		
		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());	
		useremail = appState.getUserId();
		tvEmail = (TextView) findViewById(R.id.email);
		tvEmail.setText(useremail);
		profilePictureView.setProfileId(appState.getUserFbId());
		
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
					interests.set(4,"sports");
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
						interestExist = true;
						interestsFinal.add(interests.get(i));
					}
				}
				//Forcing users to pick at least 1 interest when signing up
				if(!interestExist) {
					Toast.makeText(getApplicationContext(), "Please select at least one interest" ,
							Toast.LENGTH_LONG).show();
				}
				else {

					/****
					 * DB: grab the data you need here and insert into db
					 * firstname, lastname, email, interestsFinal  
					 */
					insertUser(firstname, lastname, email);
				}
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

	// insert user to db 
	private void insertUser(String first, String last, String email){
		class eventsTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				HttpClient client = new DefaultHttpClient();
				String url = "http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/insert_query.jsp?method=insertUser&fname="+encodeHTML(firstname)
		                 +"&lname="+encodeHTML(lastname)+"&ucsd_email="+encodeHTML(args[2]);
				for (int i = 0; i < interestsFinal.size(); i++){
					url += "&interest="+encodeHTML(interestsFinal.get(i));
				}
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					String result = null;
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while((line = reader.readLine()) != null){
						sb.append(line);
					}
					result = sb.toString();
					result = result.substring(result.indexOf("<body>")+6, result.indexOf("</body>"));
					if (result.contains("Success")){
						return "SUCCESS";
					}
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
					return null;
				} catch (IOException e1) {
					e1.printStackTrace();
					return null;
				}
				return "FAILED";
			}
			protected void onPostExecute(String result){
				if(result != null){
					Toast.makeText(getApplicationContext(), "Account created!",
							Toast.LENGTH_LONG).show();
					Intent i= new Intent(signupActivity.this, com.ucevents.events.EventsActivity.class);
					startActivity(i);
					return;
				}
				else{
					Toast.makeText(getApplicationContext(), "Unable to create account. Please try again later.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(first, last, email);
		return;
	}
	

	public String encodeHTML(String s)
	{
		s = s.replaceAll(" ", "%20");
		s = s.replaceAll("!", "%21");
		s = s.replaceAll("'", "%27");
		return s;
	}
}
