package com.ucevents.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.EventLog.Event;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.android.ucevents.UCEvents_App;
import com.ucevents.menu.MenuActivity;
import com.ucevents.schedule.IndividualScheduleActivity;
import com.google.analytics.tracking.android.EasyTracker;


public class IndividualEventsActivity extends MenuActivity {
	ImageView ivIconID;
	TextView tvName;
	TextView tvLocation;
	TextView tvDate;
	TextView tvTime;
	TextView tvDescription;
	CheckBox cbRSVP;
	TextView tvRSVPCount;
	String eventid;
	String userid;
	Button edit;
	Events chosenEvent;
	ListView attList; 
	String hostid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		tvRSVPCount = (TextView) findViewById(R.id.textRSVPCount);
		cbRSVP = (CheckBox) findViewById(R.id.checkBoxRSVP);

		Bundle b = this.getIntent().getExtras();
		edit = (Button) findViewById(R.id.editButton); 

		try{
			if(b.getString("FROM").equals("HOST")){
				Log.d("hmm","plz");
				edit.setVisibility(View.VISIBLE);
				cbRSVP.setEnabled(false);
			}
		}
		catch(NullPointerException e){
			cbRSVP.setVisibility(View.VISIBLE);
			edit.setEnabled(false);
			Log.d("NO BUTTON", "not event toaster");
			
		}
		// grab event clicked from bundle
		chosenEvent = b.getParcelable("chosenEvent");
		//Toast.makeText(getApplicationContext(), chosenEvent.getDescription(), Toast.LENGTH_SHORT).show();
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

		eventid = chosenEvent.getEventid();
		hostid = chosenEvent.getHostId();

		userid = ((UCEvents_App)getApplicationContext()).getUserId();
		tvRSVPCount = (TextView) findViewById(R.id.textRSVPCount);
		tvRSVPCount.setText("People attending("+String.valueOf(chosenEvent.getAttendees().size()) +"): " ); 
		
		attList = (ListView) findViewById(R.id.allAttend);
		
		ArrayList<String> listAttend = new ArrayList<String>();
		ArrayList<String> listView= new ArrayList<String>();
		
		listAttend.addAll(chosenEvent.getAttendees());
		
		//check for if less than 5 attendees
		/*int totalSize = 2;  
		if(totalSize > listAttend.size()){
			totalSize = listAttend.size();
		}
		
		for(int i = 0; i < totalSize; i++ ){
			listView.add(listAttend.get(i));
		}*/
		
		listView.addAll(chosenEvent.getAttendees()); 
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                listView );

		attList.setAdapter(arrayAdapter); 
		
		//update rsvp count w value from db

		//On state change
		cbRSVP = (CheckBox) findViewById(R.id.checkBoxRSVP);
		cbRSVP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// update db with RSVP value here
				if(cbRSVP.isChecked()){
					insertAttendee(userid, eventid);
				}
				else {
					deleteAttendee(userid, eventid);
				}
				//Toast.makeText(getApplicationContext(), "RSVP", Toast.LENGTH_SHORT).show();
			}
		});

		getRSVPDB(userid, eventid);
		addOnClickListener();
		// add code: grab rsvp count from db
	}
	private void addOnClickListener() {
		Button editButton;
		editButton = (Button) findViewById(R.id.editButton);
		editButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//view= inflater.inflate(R.layout.activity_allevents, container, false);
				Intent intent = new Intent(IndividualEventsActivity.this, com.ucevents.schedule.EditHostActivity.class);
				intent.putExtra("key", "edit");
				Bundle b = new Bundle();
				b.putParcelable("chosenEvent", chosenEvent);
				intent.putExtras(b);
				startActivity(intent);
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
					Toast.makeText(getApplicationContext(), "You are no longer attending.",
							Toast.LENGTH_LONG).show();
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
					Toast.makeText(getApplicationContext(), "Unable to remove you from attendees. Please try again.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(userid, eventid);
		Log.d("HERE", "AFTER CALL TO EXECUTE");
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
					Log.d("HTML RESULT: ", result);
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
				return "FAILED";
			}
			protected void onPostExecute(String result){
				if(result != null){
					Log.d("SUCCESS", result);
					Toast.makeText(getApplicationContext(), "You are attending!",
							Toast.LENGTH_LONG).show();
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
					Toast.makeText(getApplicationContext(), "Unable to add you as an attendee. Please try again.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(userid, eventid);
		Log.d("HERE", "AFTER CALL TO EXECUTE");
		return;
	}

	// Get RSVP value from DB
	private void getRSVPDB(String user, String event){
		class eventsTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				JSONObject json = null;
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getAttendees&user="+encodeHTML(args[0])+"&param="+encodeHTML(args[1]));
				Log.d("URL ACCESSING", get.getURI().getPath());
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
					Log.d("RESULT: ", result);
					try {
						json = new JSONObject(result);
						boolean attending = json.getBoolean("attending");
						return "" + attending;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d("JSONEXCEPTION@87", e.toString());
						return null;
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
			}
			protected void onPostExecute(String result){
				if(result != null){
					Log.d("SUCCESS", result);
					if (result.equals("true")){
						cbRSVP.setChecked(true);
						if(hostid.equals(userid)){
							cbRSVP.setEnabled(false);
						}
						/*cbRSVP.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// update db with RSVP value here
									if(cbRSVP.isChecked()){
										insertAttendee(userid, eventid);
									}
									else {
										deleteAttendee(userid, eventid);
									}
									//Toast.makeText(getApplicationContext(), "RSVP", Toast.LENGTH_SHORT).show();
								}
							});*/
					}
					else{
						cbRSVP.setChecked(false);
						/*cbRSVP.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// update db with RSVP value here
									if(cbRSVP.isChecked()){
										insertAttendee(userid, eventid);
									}
									else {
										deleteAttendee(userid, eventid);
									}
									//Toast.makeText(getApplicationContext(), "RSVP", Toast.LENGTH_SHORT).show();
								}
							});*/
					}
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(userid, eventid);
		Log.d("HERE", "AFTER CALL TO EXECUTE GETRSVPDB");
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
