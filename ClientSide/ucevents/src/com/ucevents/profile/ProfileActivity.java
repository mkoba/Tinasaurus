package com.ucevents.profile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.ucevents.R;
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.MenuActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.widget.ProfilePictureView;
import com.google.analytics.tracking.android.EasyTracker;


public class ProfileActivity extends MenuActivity {
	private ProfilePictureView profilePictureView;
	Button bEdit;
	private CheckBox cbCareer;
	private CheckBox cbFood;
	private CheckBox cbOrganization;
	private CheckBox cbSocial;
	private CheckBox cbSport;
	private CheckBox cbStudy;
	private CheckBox cbOther;
	private String[] dbInterests = new String[0];
	private String useremail;
	private String fName;
	private String lName;
	TextView firstName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());	
		useremail = appState.getUserId();
		
		String id= appState.getUserFbId();
		profilePictureView.setProfileId(appState.getUserFbId());
		
		getUserName();
	    TextView email = (TextView)findViewById(R.id.email);
	    email.setText(useremail);
	    
	    grabInterests();
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

	  // delete interests
		private void deleteInterest(String interest){
			class profileTask extends AsyncTask<String, Void, String> {
				protected String doInBackground(String[] args){
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/delete_query.jsp?method=deleteUserInterests&user="+encodeHTML(useremail)+"&interest="+encodeHTML(args[0]));
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
							return args[0];
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
						Toast.makeText(getApplicationContext(), result+" is no longer an interest" ,
								Toast.LENGTH_LONG).show();
						return;
					}
					else{
						Toast.makeText(getApplicationContext(), "Unable to remove interest. Please try again later.",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute(interest);
			return;
		}

		// insert modified interests
		private void insertInterest(String interest){
			class profileTask extends AsyncTask<String, Void, String> {
				protected String doInBackground(String[] args){
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/insert_query.jsp?method=insertUserInterest&user="+encodeHTML(useremail)+"&interest="+encodeHTML(args[0]));
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
							return args[0];
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
						Toast.makeText(getApplicationContext(), result + " added to interests.",
								Toast.LENGTH_LONG).show();
						return;
					}
					else{
						Toast.makeText(getApplicationContext(), "Unable to add interest. Please try again later.",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute(interest);
			return;
		}
		
		// Get RSVP value from DB
		private void getUserName(){
			class profileTask extends AsyncTask<String, Void, String> {
				protected String doInBackground(String[] args){
					
					JSONObject json = null;
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getUserInformation&param="+encodeHTML(useremail));
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
						try {
							json = new JSONObject(result);
							fName=json.getString("fname");
							lName=json.getString("lname");
							
							return result;
						} catch (JSONException e) {
							e.printStackTrace();
							return null;
						}
					} catch (ClientProtocolException e1) {
						e1.printStackTrace();
						return null;
					} catch (IOException e1) {
						e1.printStackTrace();
						return null;
					}

				}
				protected void onPostExecute(String result){
					if(result != null){
						updateUserName();
						return;
					}
					else{
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute();
			return;
		}


		// Get RSVP value from DB
		private void getUserInterests(){
			class profileTask extends AsyncTask<String, Void, String> {
				protected String doInBackground(String[] args){
					JSONObject json = null;
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getUserInterests&param="+encodeHTML(useremail));
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
						try {
							json = new JSONObject(result);
							JSONArray interests = json.getJSONArray("interests");
							result = "";
							for (int i = 0; i < interests.length(); i++){
								result += interests.getString(i)+"_";
							}
							return result;
						} catch (JSONException e) {
							e.printStackTrace();
							return null;
						}
					} catch (ClientProtocolException e1) {
						e1.printStackTrace();
						return null;
					} catch (IOException e1) {
						e1.printStackTrace();
						return null;
					}
				}
				protected void onPostExecute(String result){
					if(result != null){
						if (result.length() > 0){
							dbInterests = result.split("_");
							updateUserInfo();
						}
						return;
					}
					else{ // fail
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute();
			return;
		}

		public String encodeHTML(String s)
		{
			s = s.replaceAll(" ", "%20");
			s = s.replaceAll("!", "%21");
			s = s.replaceAll("'", "%27");
			return s;
		}
		
		public void updateUserName() {
			firstName = (TextView)findViewById(R.id.f_name);
			firstName.setText(fName + " " + lName);
		}
		
		public void updateUserInfo() {
			/* DB: populate interest list here 
			 *  to dbInterests
			 */

			// temp values  (hard-coded)
			for( int i = 0; i < dbInterests.length; i++) {
				if(dbInterests[i].equals("career")) {
					cbCareer = (CheckBox) findViewById(R.id.career);
					cbCareer.setChecked(true);
				}
				else if(dbInterests[i].equals("food")) {
					cbFood = (CheckBox) findViewById(R.id.food);
					cbFood.setChecked(true);
				}
				else if(dbInterests[i].equals("organization")) {
					cbOrganization = (CheckBox)findViewById(R.id.organization);
					cbOrganization.setChecked(true);
				}
				else if(dbInterests[i].equals("social")) {
					cbSocial = (CheckBox)findViewById(R.id.social);
					cbSocial.setChecked(true);
				}
				else if(dbInterests[i].equals("sports")) {
					cbSport = (CheckBox)findViewById(R.id.sport);
					cbSport.setChecked(true);
				}
				else if(dbInterests[i].equals("study")) {
					cbStudy = (CheckBox) findViewById(R.id.study);
					cbStudy.setChecked(true);
				}
				else if(dbInterests[i].equals("other")) {
					cbOther = (CheckBox) findViewById(R.id.other);
					cbOther.setChecked(true);
				}
			}

		}

		private void grabInterests() {
			cbCareer = (CheckBox) findViewById(R.id.career);
			cbCareer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						insertInterest("career");
					}
					else {
						deleteInterest("career");
					}
				}
			});
			cbFood = (CheckBox) findViewById(R.id.food);
			cbFood.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						insertInterest("food");
					}
					else {
						deleteInterest("food");
					}
				}
			});
			cbOrganization = (CheckBox)findViewById(R.id.organization);
			cbOrganization.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						insertInterest("organization");
					}
					else {
						deleteInterest("organization");
					}
				}
			});
			cbSocial = (CheckBox)findViewById(R.id.social);
			cbSocial.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						insertInterest("social");
					}
					else {
						deleteInterest("social");
					}
				}
			});
			cbSport = (CheckBox) findViewById(R.id.sport);
			cbSport.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						insertInterest("sports");
					}
					else {
						deleteInterest("sports");
					}
				}
			});
			cbStudy = (CheckBox) findViewById(R.id.study);
			cbStudy.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						insertInterest("study");
					}
					else {
						deleteInterest("study");
					}
				}
			});
			cbOther = (CheckBox) findViewById(R.id.other);
			cbOther.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox) v).isChecked()) {
						Toast.makeText(ProfileActivity.this, "other", Toast.LENGTH_SHORT).show();
						insertInterest("other");
					}
					else {
						deleteInterest("other");
					}
				}
			});
			getUserInterests();

		}

}
