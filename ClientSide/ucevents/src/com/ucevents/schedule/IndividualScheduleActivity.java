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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.events.Events;
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.MenuActivity;

public class IndividualScheduleActivity extends MenuActivity {
	ImageView ivIconID;
	TextView tvName;
	TextView tvLocation;
	TextView tvDate;
	TextView tvTime;
	TextView tvDescription;
	TextView tvRSVPCount;
	CheckBox cbRSVP;
	String eventid;
	String userid;

	Button bDecline;
	Button bList; 
	
	ListView attList; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indschedule);
		Bundle b = this.getIntent().getExtras();

		// grab event clicked from bundle
		Schedule chosenEvent = b.getParcelable("chosenEvent");
		eventid = chosenEvent.getEventid();

		ivIconID = (ImageView) findViewById(R.id.ivIconID);
		ivIconID.setImageResource(chosenEvent.getIconid());

		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(chosenEvent.getName());

		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvLocation.setText(chosenEvent.getLocation());

		tvDate = (TextView) findViewById(R.id.tvDate);
		tvDate.setText(chosenEvent.getEventDate());

		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setText(chosenEvent.getTimeDisplay());

		tvDescription = (TextView) findViewById(R.id.tvDescription);
		tvDescription.setText(chosenEvent.getDescription());

		userid = ((UCEvents_App)getApplicationContext()).getUserId();
		
		tvRSVPCount = (TextView) findViewById(R.id.textRSVPCount);
		tvRSVPCount.setText("People attending("+String.valueOf(chosenEvent.getAttendees().size()) +"): " ); 
		
		//List population 
		attList = (ListView) findViewById(R.id.allAttend);
		
		ArrayList<String> listAttend = new ArrayList<String>();
		ArrayList<String> listView= new ArrayList<String>();
		
		listAttend.addAll(chosenEvent.getAttendees());

		listView.addAll(chosenEvent.getAttendees()); 
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                listView );

		attList.setAdapter(arrayAdapter); 
		
		//list stops here

		cbRSVP = (CheckBox) findViewById(R.id.checkBoxRSVP);
		cbRSVP.setChecked(true);
		if(chosenEvent.getHostId().equals(userid)){
			cbRSVP.setEnabled(false);
		}

		cbRSVP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cbRSVP.isChecked()){
					insertAttendee(userid, eventid);
				}
				else {
					deleteAttendee(userid, eventid);
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
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	private void deleteAttendee(String user, String event){
		class eventsTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/delete_query.jsp?method=deleteAttendee&attendee="+encodeHTML(args[0])+"&event="+encodeHTML(args[1]));
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
					Toast.makeText(getApplicationContext(), "You are no longer attending.",
							Toast.LENGTH_LONG).show();
					return;
				}
				else{
					Toast.makeText(getApplicationContext(), "Unable to remove you from attendees. Please try again.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(userid, eventid);
		return;
	}

	private void insertAttendee(String user, String event){
		class eventsTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/insert_query.jsp?method=insertAttendee&user="+encodeHTML(args[0])+"&event="+encodeHTML(args[1]));
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
					return null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return null;
				}
				return "FAILED";
			}
			protected void onPostExecute(String result){
				if(result != null){
					Toast.makeText(getApplicationContext(), "You are attending!",
							Toast.LENGTH_LONG).show();
					return;
				}
				else{
					Toast.makeText(getApplicationContext(), "Unable to add you as an attendee. Please try again.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(userid, eventid);
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