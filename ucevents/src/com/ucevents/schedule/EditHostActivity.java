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
/*
	//updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
    //int month, int day, int year, String description, String name, String ucsd_email)
	private void sendPostRequest(String location, String hour, String minute, String interests, String pm, String month, String day, String year, String description, String name, String ucsd_email) {
		class scheduleTask extends AsyncTask<String, Void, String>{
			protected String doInBackground(String[] args){
				JSONObject json = null;
				HttpClient client = new DefaultHttpClient();
				HttpGet get;
					get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/updateQuery.jsp?method=updateEvents&param="+encodeHTML(key)+"&user="+encodeHTML(userid));
		
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(); // to store what we get

				HttpResponse response;
				try{
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
					Log.d("RESULT: ", result);

					try{
						json = new JSONObject(result);
					}catch(JSONException e){
						e.printStackTrace();
						Log.d("JSONEXCEPTION line 113", e.toString());
						return null;
					}
				}catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.d("CLIENTPROTOCOL", e1.toString());
					return null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.d("IOEXCEPTION", e1.toString());
					return null;
				}
				Log.d("JSON RESULT", "JSON NOT NULL");

				JSONArray listOfEvents = null;
				try{
					listOfEvents = json.getJSONArray("events");
					for(int i = 0; i < listOfEvents.length(); i++){
						JSONObject event = listOfEvents.getJSONObject(i);
						int iconid = 0;
						JSONArray categories = new JSONArray();
						try{
							categories = event.getJSONArray("category");
							String category = categories.getString(0);
							if (category.equals("study")){
								iconid = R.drawable.study_icon;
							}
							else if (category.equals("food")){
								iconid = R.drawable.food_icon;
							}
							else if (category.equals("career")){
								iconid = R.drawable.career_icon;
							}
							else if (category.equals("organization")){
								iconid = R.drawable.club_icon;
							}
							else if (category.equals("sports")){
								iconid = R.drawable.sport_icon;
							}
							else if (category.equals("social")){
								iconid = R.drawable.social_icon;
							}
							else{
								iconid = R.drawable.other_icon;
							}
						} catch(JSONException e){
							
							iconid = R.drawable.other_icon;
						}
						JSONArray json_attendees = event.getJSONArray("attendees");
						String[] attendees = new String[json_attendees.length()];
						for (int j = 0; j < json_attendees.length(); j++){
							attendees[j] = json_attendees.getString(j);
						}
						eventList.add(new Events(event.getString("name"), event.getString("name").substring(event.getString("name").indexOf("_") + 1),
								event.getInt("hour")*100+event.getInt("min"), event.getString("location"),event.getInt("month"),event.getInt("date"),
								event.getInt("year"), event.getString("description"), event.getString("host"), iconid, attendees, event.getBoolean("attending")));
					}
					Collections.sort(eventList);
				}catch(JSONException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("EXCEPTION", e.toString());
					return null;
				}
				return "SUCCESS";
			}
			protected void onPostExecute(String result){
				if(result != null){
					Log.d("Result of Query", result);
					populateListView();
					Log.d("LOCATION****", "POST-POPULATELISTVIEW");
					registerClickCallback();
					Log.d("LOCATION****", "CLICKCALLBACK");
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
					return;
				}
			}
		}
		scheduleTask sendPostReqAsyncTask = new scheduleTask();
		sendPostReqAsyncTask.execute();
		Log.d("HERE", "AFTER CALL TO EXECUTE");
		return;
=======
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		email = appState.getUserId();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hostedit);
		Bundle b = this.getIntent().getExtras();

		// grab event clicked from bundle
		Events chosenEvent = b.getParcelable("chosenEvent");
		Log.d("NAME", "" + chosenEvent.getName());
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(chosenEvent.getName());
		ivIconID = (ImageView) findViewById(R.id.ivIconID);
		ivIconID.setImageResource(chosenEvent.getIconid());

		
>>>>>>> 6d51d5e9b9a321570f8727511c929197bfa2eebe
	}
*/	
	/* 		public void updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
			                     int month, int day, int year, String description, String name, String ucsd_email) 
			                     */
	
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
		System.out.println(appState.toString());

		host = appState.getUserId();
		System.out.println("host is " + host);

		System.out.println(eventName.getText().toString());
		name = host + "_" + (eventName.toString());
		System.out.println("2");

		location = ((EditText)findViewById(R.id.location)).getText().toString();
		System.out.println("3 " + location.toString());

		description = ((EditText)findViewById(R.id.description)).getText().toString();
		System.out.println("4");

		minute = ((TimePicker)findViewById(R.id.eventTime)).getCurrentMinute();
		System.out.println("5");

		hour = ((TimePicker)findViewById(R.id.eventTime)).getCurrentHour();
		System.out.println("6");

		Log.d("HOUR", "" + hour);
		System.out.println("7");

		month = ((DatePicker)findViewById(R.id.datePicker1)).getMonth();
		System.out.println("8");

		day = ((DatePicker)findViewById(R.id.datePicker1)).getDayOfMonth();
		System.out.println("9");

		year = ((DatePicker)findViewById(R.id.datePicker1)).getYear();
		System.out.println("10");

		categories = new ArrayList<String>();

		System.out.println("waetwatwatatwawteataw");
		System.out.println("waetwatwatatwawteataw");
		System.out.println("waetwatwatatwawteataw");

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
		System.out.println("test123123123");
		System.out.println("test123123123");
		System.out.println("test123123123");

		//Combining each category into a single string
		String concatString = "";
		System.out.println("asdfsafsaftest123123123");

		concatString += categories.get(0);
		System.out.println("hell no noonono");

		for(int i = 1; i < categories.size(); i++) {
		String tempStr = "+" + categories.get(i);
			concatString += tempStr;
		}
		Log.d("concatString", "" + concatString);
		//updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
   //     int month, int day, int year, String description, String name, String ucsd_email)
		Boolean pm = false;
		pm = false;
		System.out.println(" i sing the body electric ");
		System.out.println(" i sing the body electric ");
		System.out.println(" i sing the body electric ");
		System.out.println(" i sing the body electric ");
		System.out.println(" i sing the body electric ");

		//sendPostRequest(location, Integer.toString(hour), Integer.toString(minute), "false", concatString, pm.toString(), Integer.toString(month), Integer.toString(day), Integer.toString(year), description, name, host);
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
					Log.d("npe", "yeah not goot");
					Log.d("npe", "yeah not goot");
					Log.d("npe", "yeah not goot");

				}
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
			   	 
				Intent i= new Intent(EditHostActivity.this, EventsHostActivity.class);
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
