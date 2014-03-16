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
	private List<String> dbInterests = new ArrayList<String>();
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
			eventName.setText(e.getName());
			eventTime.setCurrentHour(e.getHour());
			eventTime.setCurrentMinute(e.getMinutes());
			ivIconID = (ImageView) findViewById(R.id.ivIconID);
			ivIconID.setImageResource(e.getIconid());

			eventDate.updateDate(e.getYear(), e.getMonth(), e.getDate());
			eventLocation.setText(e.getLocation());
			eventDescription.setText(e.getDescription());
			dbInterests = e.getCategories();	
			updateEventInfo();
		}
		catch(NullPointerException e2){
			e2.printStackTrace();
		}
		
		addListenerOnButton();
	}

	private void sendPostRequest(String location, String hour, String minute, String interests, String pm, String month, String day, String year, String description, String name, String ucsd_email) {
		class scheduleTask extends AsyncTask<String, Void, String>{
			protected String doInBackground(String[] args){
				//Parsing the continuous string of interests
				String delims = "[+]";
				String[] tokens = args[5].split(delims);
				
				//concatenating each interest into url form
				String interestUrl = "";
				for(int i = 0; i < tokens.length; i++){
					String tempStr = "&interests=" + tokens[i];
					interestUrl += tempStr;
				}
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
					Toast.makeText(getApplicationContext(), "Event Updated!",
							Toast.LENGTH_LONG).show();
					Intent i= new Intent(EditHostActivity.this, com.ucevents.schedule.EventsHostActivity.class);
					startActivity(i);
					return;
				}
				else{
					Toast.makeText(getApplicationContext(), "Unable to Update Event. Please try again Later.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		scheduleTask sendPostReqAsyncTask = new scheduleTask();
	    sendPostReqAsyncTask.execute(name, location, hour, minute, "false", interests, month, day, year, description, host);
	    return;
	}
	public String encodeHTML(String s)
	{
		s = s.replaceAll(" ", "%20");
		s = s.replaceAll("!", "%21");
		s = s.replaceAll("'", "%27");
		return s;
	}

	public void updateEventInfo() {
		for( int i = 0; i < dbInterests.size(); i++) {
			if(dbInterests.get(i).equals("career")) {
				cbCareer = (CheckBox) findViewById(R.id.career_event);
				cbCareer.setChecked(true);
			}
			else if(dbInterests.get(i).equals("food")) {
				cbFood = (CheckBox) findViewById(R.id.food_event);
				cbFood.setChecked(true);
			}
			else if(dbInterests.get(i).equals("organization")) {
				cbOrganization = (CheckBox)findViewById(R.id.organization_event);
				cbOrganization.setChecked(true);
			}
			else if(dbInterests.get(i).equals("social")) {
				cbSocial = (CheckBox)findViewById(R.id.social_event);
				cbSocial.setChecked(true);
			}
			else if(dbInterests.get(i).equals("sport")) {
				cbSport = (CheckBox)findViewById(R.id.sport_event);
				cbSport.setChecked(true);
			}
			else if(dbInterests.get(i).equals("study")) {
				cbStudy = (CheckBox) findViewById(R.id.study_event);
				cbStudy.setChecked(true);
			}
			else { //if(dbInterests.get(i).equals("other")) {
				cbOther = (CheckBox) findViewById(R.id.other_event);
				cbOther.setChecked(true);
			}
		}
		
	}
	
	private void getInput(){
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		host = appState.getUserId();
		name = host + "_" + (eventName.getText().toString());
		location = ((EditText)findViewById(R.id.location)).getText().toString();
		description = ((EditText)findViewById(R.id.description)).getText().toString();
		minute = ((TimePicker)findViewById(R.id.eventTime)).getCurrentMinute();
		hour = ((TimePicker)findViewById(R.id.eventTime)).getCurrentHour();
		month = ((DatePicker)findViewById(R.id.datePicker1)).getMonth();
		day = ((DatePicker)findViewById(R.id.datePicker1)).getDayOfMonth();
		year = ((DatePicker)findViewById(R.id.datePicker1)).getYear();
		categories = new ArrayList<String>();

		if (((CheckBox)findViewById(R.id.career_event)).isChecked()){
			categories.add("career");
		}
		if (((CheckBox)findViewById(R.id.food_event)).isChecked()){
			categories.add("food");
		}
		if (((CheckBox)findViewById(R.id.organization_event)).isChecked()){
			categories.add("organization");
		}
		if (((CheckBox)findViewById(R.id.sport_event)).isChecked()){
			categories.add("sports");
		}	
		if (((CheckBox)findViewById(R.id.study_event)).isChecked()){
			categories.add("study");
		}
		if (((CheckBox)findViewById(R.id.other_event)).isChecked()){
			categories.add("other");
		}
		if (((CheckBox)findViewById(R.id.social_event)).isChecked()){
			categories.add("social");
		}

		String concatString = "";

		//Combining each category into a single string
		if(categories.size() == 0) {
			Toast.makeText(getApplicationContext(), "Please select at least one interest" ,
					Toast.LENGTH_LONG).show();
		}
		else{
			//Combining each category into a single string
			concatString += categories.get(0);
			for(int i = 1; i < categories.size(); i++) {
			String tempStr = "+" + categories.get(i);
				concatString += tempStr;
			}
		}
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
				try{
					getInput();
				}
				catch(NullPointerException e){
					e.printStackTrace();
				}
				//grab list of interest to add to db
			   	 for(int i = 0; i < interests.size(); i++) {
					 if(!interests.get(i).equals("false")) {
						 interestsFinal.add(interests.get(i));
					 }
				 }
				Intent i= new Intent(EditHostActivity.this, EventsHostActivity.class);
				Bundle b = new Bundle();
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
