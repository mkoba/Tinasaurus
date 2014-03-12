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
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.login.loginActivity;
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
				insertUser(firstname, lastname, email);
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.d("CLIENTPROTOCAL", e1.toString());
					return null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.d("IOEXCEPTION", e1.toString());
					return null;
				}
				Log.d("RETURN FAILURE", get.getURI().getPath());
				return "FAILED";
			}
			protected void onPostExecute(String result){
				if(result != null){
					Log.d("SUCCESS", result);
					Toast.makeText(getApplicationContext(), "Account created!",
							Toast.LENGTH_LONG).show();
					Intent i= new Intent(signupActivity.this, com.ucevents.events.EventsActivity.class);
					startActivity(i);
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
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
		//s = s.replaceAll("%", "%25");
		s = s.replaceAll(" ", "%20");
		s = s.replaceAll("!", "%21");
		//s = s.replaceAll("\"", "%22");
		//s = s.replaceAll("#", "%23");
		//s = s.replaceAll("$", "%24");
		//s = s.replaceAll("&", "%26");
		s = s.replaceAll("'", "%27");
		//s = s.replaceAll("(", "%28");
		//s = s.replaceAll(")", "%29");
		//s = s.replaceAll("*", "%2A");
		//s = s.replaceAll("+", "%2B");
		//s = s.replaceAll(",", "%2C");
		//s = s.replaceAll("-", "%2D");
		//s = s.replaceAll(".", "%2E");
		//s = s.replaceAll("/", "%2F");
		return s;
	}
}
