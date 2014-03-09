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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.android.ucevents.UCEvents_App;
import com.google.analytics.tracking.android.EasyTracker;
import com.ucevents.events.Events;
import com.ucevents.login.loginActivity;
import com.ucevents.menu.MenuActivity;

public class IndividualScheduleActivity extends MenuActivity {
	ImageView ivIconID;
	TextView tvName;
	TextView tvLocation;
	TextView tvDate;
	TextView tvTime;
	TextView tvDescription;
	TextView tvRSVPCount;
	String eventid;

	Button bDecline;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indschedule);
		Bundle b = this.getIntent().getExtras();

		// grab event clicked from bundle
		Schedule chosenEvent = b.getParcelable("chosenEvent");
		Log.d("NAME", "" + chosenEvent.getName());
		eventid = chosenEvent.getEventid();

		ivIconID = (ImageView) findViewById(R.id.ivIconID);
		ivIconID.setImageResource(chosenEvent.getIconid());

		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(chosenEvent.getName());

		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvLocation.setText(chosenEvent.getLocation());

		tvDate = (TextView) findViewById(R.id.tvDate);
		tvDate.setText(String.valueOf(chosenEvent.getMonth()) + " " + 
				String.valueOf(chosenEvent.getDate()) + " " +
				String.valueOf(chosenEvent.getYear()) );

		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setText(String.valueOf(chosenEvent.getTime()));

		tvDescription = (TextView) findViewById(R.id.tvDescription);
		tvDescription.setText(chosenEvent.getDescription());

		//tvRSVPCount = (CheckBox) findViewById(R.id.checkBoxRSVP);

		addListenerOnButton();

	}

	private void addListenerOnButton() {
		bDecline = (Button) findViewById(R.id.decline);
		bDecline.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View arg0) {
				String userid = ((UCEvents_App)getApplicationContext()).getUserId();
				sendPostRequest(userid, eventid);
				//Intent i= new Intent(IndividualScheduleActivity.this, com.ucevents.schedule.ScheduleActivity.class);
				//i.putExtra("key", "all");
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

	private void sendPostRequest(String userid, String eventid){
		class scheduleTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/delete_query.jsp?method=deleteAttendee&attendee="+args[0]+"&event="+args[1]);
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
					if (result.equals("SUCCESS")){
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
					Intent i= new Intent(IndividualScheduleActivity.this, com.ucevents.schedule.ScheduleActivity.class);
					i.putExtra("key", "all");
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
		sendPostReqAsyncTask.execute(userid, eventid);
		Log.d("HERE", "AFTER CALL TO EXECUTE");
		return;
	}
}