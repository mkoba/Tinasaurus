package com.ucevents.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.ucevents.MainActivity;
import com.android.ucevents.R;
import com.android.ucevents.UCEvents_App;
import com.ucevents.menu.MenuActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.signup.signupActivity;

public class AddEvent extends MenuActivity{
	Button createBut;
	String pretitle;
	String title;
	String location;
	int hour;
	int minute;
	boolean pm; 
	List<String> categories;
	int month;
	int day;
	int year;
	String description; 
	boolean public_flag = true;
	String host;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		addListenerOnButton();
	}
	
	private void getInput(){
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		host = appState.getUserId();
		pretitle = ((EditText)findViewById(R.id.event_name)).getText().toString();
		title = host + "_" + pretitle;
		location = ((EditText)findViewById(R.id.location)).getText().toString();
		description = ((EditText)findViewById(R.id.description)).getText().toString();
		minute = ((TimePicker)findViewById(R.id.eventTime)).getCurrentMinute();
		hour = ((TimePicker)findViewById(R.id.eventTime)).getCurrentHour();
		pm = false;
		month = ((DatePicker)findViewById(R.id.datePicker1)).getMonth();
		day = ((DatePicker)findViewById(R.id.datePicker1)).getDayOfMonth();
		year = ((DatePicker)findViewById(R.id.datePicker1)).getYear();
		categories = new ArrayList<String>();
		
		if (((CheckBox)findViewById(R.id.career_event)).isChecked()){
			System.out.println("hmm, should perhaps get through this");
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
		
		//Event title limited to 25 characters
		if(pretitle.length() > 25) {
			Toast.makeText(getApplicationContext(), "Event title is limited to 25 characters" ,
					Toast.LENGTH_LONG).show();
		}
		//Forcing hosts to select a category
		else if(categories.size() == 0) {
			Toast.makeText(getApplicationContext(), "Please select at least one interest" ,
					Toast.LENGTH_LONG).show();
		}
		else {
			//Combining each category into a single string
			String concatString = "";
			concatString += categories.get(0);
			for(int i = 1; i < categories.size(); i++) {
			String tempStr = "+" + categories.get(i);
				concatString += tempStr;
			}
			sendPostRequest(title, location, Integer.toString(hour), Integer.toString(minute), "false", concatString, Integer.toString(month), Integer.toString(day), Integer.toString(year), description, host);
		}
	}
	
	public void addListenerOnButton() {
		createBut = (Button) findViewById(R.id.Done);
		createBut.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
				getInput();
				}
			});

	}
	
	private void sendPostRequest(String title, String location, String hour, String minute, String pm, String interest,
			String month, String day, String year, String description, String host){
		class scheduleTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				Log.d("HOUR3", "" + args[2]);
				//Parsing the continuous string of interests
				String delims = "[+]";
				String[] tokens = args[5].split(delims);
				
				//concatenating each interest into url form
				String interestUrl = "";
				for(int i = 0; i < tokens.length; i++){
					String tempStr = "&interest=" + tokens[i];
					interestUrl += tempStr;
				}
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/insert_query.jsp?method=insertEvent&title="
				+encodeHTML(args[0])+"&location="+encodeHTML(args[1])+"&hour="+encodeHTML(args[2])+"&minute="+encodeHTML(args[3])+"&pm="+encodeHTML(args[4])+interestUrl
				+"&month="+encodeHTML(args[6])+"&day="+encodeHTML(args[7])+"&year="+encodeHTML(args[8])+"&description="+encodeHTML(args[9])+"&host="+encodeHTML(args[10]));
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
					Toast.makeText(getApplicationContext(), "Event Created!",
							Toast.LENGTH_LONG).show();
					Intent i= new Intent(AddEvent.this, com.ucevents.schedule.ScheduleActivity.class);
					startActivity(i);
					return;
				}
				else{
					Toast.makeText(getApplicationContext(), "Unable to create event. Please try again later.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		scheduleTask sendPostReqAsyncTask = new scheduleTask();
	    sendPostReqAsyncTask.execute(title, location, hour, minute, "false", interest, month, day, year, description, host);
	    return;
	}
	
	public String encodeHTML(String s)
	{
		s = s.replaceAll(" ", "%20");
		s = s.replaceAll("!", "%21");
		s = s.replaceAll("'", "%27");
		return s;
	}
	
	/**
	 * An example Activity using Google Analytics and EasyTracker.
	 */
	  @Override
	  public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this);
	}
		
}
