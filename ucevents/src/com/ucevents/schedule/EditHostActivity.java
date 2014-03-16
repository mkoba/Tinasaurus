package com.ucevents.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.android.ucevents.UCEvents_App;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.events.Events;
import com.ucevents.events.IndividualEventsActivity;
import com.ucevents.menu.MenuActivity;
import com.ucevents.signup.signupActivity;

public class EditHostActivity extends MenuActivity {

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
	TextView tvName;
	ImageView ivIconID;
	
	private TextView eventName;
	private TimePicker eventTime;
	private EditText eventLocation;
	private DatePicker eventDate;
	private EditText eventDescription;

	//updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
   // int month, int day, int year, String description, String name, String ucsd_email)
	String location;
	int hour;
	int minute;
	boolean pm;
	int month;
	int day;
	int year;
	String description;
	String name;
	String ucsd_email;
	String host;
	
	List<String> categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hostedit);

		eventName = (TextView) findViewById(R.id.tvName);	
		eventTime = (TimePicker) findViewById(R.id.eventTime);
		eventDate = (DatePicker) findViewById(R.id.datePicker1);
		eventLocation = (EditText) findViewById(R.id.location);
		eventDescription = (EditText) findViewById(R.id.description);
		
		Bundle b = this.getIntent().getExtras();
		Events e = b.getParcelable("chosenEvent");

		

		
		try{
			Log.d("name is...", e.getName());
			eventName.setText(e.getName());
			
			Log.d("display", e.getTimeDisplay());
			
			Log.d("get hours is", ""+e.getHour());
			Log.d("get minutes is", ""+e.getMinutes());
			eventTime.setCurrentHour(e.getHour());
			eventTime.setCurrentMinute(e.getMinutes());
			
			eventDate.updateDate(e.getYear(), e.getMonth(), e.getDate());
			//Log.d("location is", (e.getLocation()));
			//Log.d("description is", (e.getDescription()));
			eventLocation.setText(e.getLocation());
			eventDescription.setText(e.getDescription());
						
		}
		catch(NullPointerException e2){
			e2.printStackTrace();
		}
		
		addListenerOnButton();
	}

	//updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
    //int month, int day, int year, String description, String name, String ucsd_email)
	private void sendPostRequest(String location, String hour, String minute, String interests, String pm, String month, String day, String year, String description, String name, String ucsd_email) {
		class scheduleTask extends AsyncTask<String, Void, String>{
			protected String doInBackground(String[] args){
				Log.d("HOUR3", "" + args[2]);
				//Parsing the continuous string of interests
				String delims = "[+]";
				String[] tokens = args[5].split(delims);
				
				//concatenating each interest into url form
				String interestUrl = "";
				for(int i = 0; i < tokens.length; i++){
					String tempStr = "&interests=" + tokens[i];
					interestUrl += tempStr;
				}
				Log.d("TITLEinSendPost", "" + args[0]);
				Log.d("LOCATIONinSendPost", "" + args[1]);
				Log.d("HOURinSendPost", "" + args[2]);
				Log.d("MINUTEinSendPost", "" + args[3]);
				Log.d("PMinSendPost", "" + args[4]);
				Log.d("INTERESTinSendPost", "" + interestUrl);
				Log.d("MONTHinSendPost", "" + args[6]);
				Log.d("DAYinSendPost", "" + args[7]);
				Log.d("YEARinSendPost", "" + args[8]);
				Log.d("DESCRIPTIONinSendPost", "" + args[9]);
				Log.d("HOSTinSendPost", "" + args[10]);
				//updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
			    //int month, int day, int year, String description, String name, String ucsd_email)
				Log.d("URLinSendPost", "http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/updateQuery.jsp?method=updateEvents&location="
						+encodeHTML(args[1])+"&hour="+encodeHTML(args[2])+"&min="+encodeHTML(args[3])+interestUrl+"&pm="+encodeHTML(args[4])+						
						"&month="+encodeHTML(args[6])+"&day="+encodeHTML(args[7])+"&year="+encodeHTML(args[8])+"&description="+encodeHTML(args[9])+
						"&name="+encodeHTML(args[0])+"&ucsd_email="+encodeHTML(args[10]));
				String postURL = "http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/updateQuery.jsp?method=updateEvents&location="
						+encodeHTML(args[1])+"&hour="+encodeHTML(args[2])+"&min="+encodeHTML(args[3])+interestUrl+"&pm="+encodeHTML(args[4])+						
						"&month="+encodeHTML(args[6])+"&day="+encodeHTML(args[7])+"&year="+encodeHTML(args[8])+"&description="+encodeHTML(args[9])+
						"&name="+encodeHTML(args[0])+"&ucsd_email="+encodeHTML(args[10]);
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(postURL);
				HttpResponse response;
				try {
					response = client.execute(post);
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
					Log.d("RESULT IN HTML", "RESULT");
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
				Log.d("da...","moooo");
				return "FAILED";
				
			}
			protected void onPostExecute(String result){
				if(result != null){
					Log.d("RESULT*************", "" + result);
					Intent i= new Intent(EditHostActivity.this, com.ucevents.schedule.EventsHostActivity.class);
					startActivity(i);
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
					return;
				}
			}
		}
		scheduleTask sendPostReqAsyncTask = new scheduleTask();
	    sendPostReqAsyncTask.execute(name, location, hour, minute, "false", interests, month, day, year, description, host);
	    Log.d("HERE", "AFTER CALL TO EXECUTE");
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
					Toast.makeText(EditHostActivity.this, "Career", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditHostActivity.this, "Food", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditHostActivity.this, "organization", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditHostActivity.this, "social", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditHostActivity.this, "Sport", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditHostActivity.this, "study", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditHostActivity.this, "other", Toast.LENGTH_SHORT).show();
					interests.set(6,"other");
				}
				else {
					interests.set(6, "false");
				}
			}
		});
		
		
	}
	private void getInput(){
		System.out.println("SUP DOG");
		System.out.println("SUP DOG");
		System.out.println("SUP DOG");
		System.out.println("SUP DOG");
		System.out.println("SUP DOG");

		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		host = appState.getUserId();
		name = host + "_" + (eventName.getText().toString());
		Log.d("eventName",eventName.getText().toString());
		location = ((EditText)findViewById(R.id.location)).getText().toString();
		description = ((EditText)findViewById(R.id.description)).getText().toString();
		minute = ((TimePicker)findViewById(R.id.eventTime)).getCurrentMinute();
		hour = ((TimePicker)findViewById(R.id.eventTime)).getCurrentHour();
		Log.d("HOUR", "" + hour);
		month = ((DatePicker)findViewById(R.id.datePicker1)).getMonth();
		day = ((DatePicker)findViewById(R.id.datePicker1)).getDayOfMonth();
		year = ((DatePicker)findViewById(R.id.datePicker1)).getYear();
		categories = new ArrayList<String>();


		if (((CheckBox)findViewById(R.id.career_event)).isChecked()){
			System.out.println("career");

			categories.add("career");
		}
		if (((CheckBox)findViewById(R.id.food_event)).isChecked()){
			System.out.println("food");

			categories.add("food");
		}
		if (((CheckBox)findViewById(R.id.organization_event)).isChecked()){
			System.out.println("org");

			categories.add("organization");
		}
		if (((CheckBox)findViewById(R.id.sport_event)).isChecked()){
			System.out.println("sport");

			categories.add("sport");
		}	
		if (((CheckBox)findViewById(R.id.study_event)).isChecked()){
			System.out.println("study");

			categories.add("study");
		}
		if (((CheckBox)findViewById(R.id.other_event)).isChecked()){
			System.out.println("other");

			categories.add("other");
		}
		if (((CheckBox)findViewById(R.id.social_event)).isChecked()){
			System.out.println("social");

			categories.add("social");
		}

		String concatString = "";

		//Combining each category into a single string
		if(categories.size() == 0) {
			Log.d("yo dog", "it went to the right place");
			Toast.makeText(getApplicationContext(), "Please select at least one interest" ,
					Toast.LENGTH_LONG).show();
			Log.d("yo dog", "it went to the right place");

		}
		else{
			//Combining each category into a single string
			concatString += categories.get(0);
			for(int i = 1; i < categories.size(); i++) {
			String tempStr = "+" + categories.get(i);
				concatString += tempStr;
			}
			Log.d("concatString", "" + concatString);
		}
		//updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
		//int month, int day, int year, String description, String name, String ucsd_email)
		Boolean pm = false;
		pm = false;

		
		sendPostRequest(location, Integer.toString(hour), Integer.toString(minute), concatString, pm.toString(), Integer.toString(month), Integer.toString(day), Integer.toString(year), description, name, host);
	}

	public void addListenerOnButton() {
		Button updateEvent;
		updateEvent = (Button) findViewById(R.id.Done);
		updateEvent.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
					Log.d("get", "in here");
				try{
					getInput();
				}
				catch(NullPointerException e){
					e.printStackTrace();
				}
				System.out.println("prob here?");
				//grab list of interest to add to db
			   	 for(int i = 0; i < interests.size(); i++) {
					 if(!interests.get(i).equals("false")) {
						 System.out.println("interests: " + interests.get(i));
						 interestsFinal.add(interests.get(i));
					 }
				 }
					System.out.println("hope not");

			   	/****
			   	  * DB: grab the data you need here and insert into db
			   	  * to update profile info
			   	  *  email, interestsFinal  
			   	  */
			   	System.out.println("sup sup sup");
				Intent i= new Intent(EditHostActivity.this, EventsHostActivity.class);
				System.out.print("plz no go");
				Bundle b = new Bundle();
				System.out.println("here??? perhaps");
				//startActivity(i);
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
