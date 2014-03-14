package com.ucevents.profile;


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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.ucevents.R;
import com.android.ucevents.UCEvents_App;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		
		addListenerOnButton();
		UCEvents_App appState = ((UCEvents_App)getApplicationContext());	
		useremail = appState.getUserId();
		
		String id= appState.getUserFbId();
		System.out.println("user id: " + id);
		 profilePictureView.setProfileId(appState.getUserFbId());
		 
	    /*TextView firstName = (TextView)findViewById(R.id.f_name);
	    firstName.setText("Leon" + " is");*/
	    
	    /*TextView lastName = (TextView)findViewById(R.id.l_name);
	    lastName.setText("is");*/
		getUserName();
	    TextView email = (TextView)findViewById(R.id.email);
	    email.setText(useremail);

	    
	    //TextView interest = (TextView)findViewById(R.id.interest);
	    //interest.setText("Simba");
	    
	    grabInterests();
	}
	
	

	
	public void addListenerOnButton() {
	/*	bEdit = (Button) findViewById(R.id.edit);
		bEdit.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(ProfileActivity.this, ProfileEditActivity.class);
			
				startActivity(i);
				}
			});
*/
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
						Toast.makeText(getApplicationContext(), result+" is no longer an interest" ,
								Toast.LENGTH_LONG).show();
						return;
					}
					else{
						Log.d("FAILURE", "FAILURE");
						Toast.makeText(getApplicationContext(), "Unable to remove interest. Please try again later.",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute(interest);
			Log.d("HERE", "AFTER CALL TO EXECUTE");
			return;
		}

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
						Log.d("HTML RESULT: ", result);
						if (result.contains("Success")){
							return args[0];
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
						Toast.makeText(getApplicationContext(), result + " added to interests.",
								Toast.LENGTH_LONG).show();
						return;
					}
					else{
						Log.d("FAILURE", "FAILURE");
						Toast.makeText(getApplicationContext(), "Unable to add interest. Please try again later.",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute(interest);
			Log.d("HERE", "AFTER CALL TO EXECUTE");
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
						Log.d("RESULT: ", result);
						try {
							json = new JSONObject(result);
							String fName=json.getString("fname");
							String lName=json.getString("lname");
							TextView firstName = (TextView)findViewById(R.id.f_name);
						    firstName.setText(fName + " " + lName);
							/*JSONArray interests = json.getJSONArray("interests");
							result = "";
							for (int i = 0; i < interests.length(); i++){
								result += interests.getString(i)+"_";
							}*/
							return result;
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
						/*if (result.length() > 0){
							dbInterests = result.split("_");
							updateUserInfo();
						}*/
						return;
					}
					else{
						Log.d("FAILURE", "FAILURE");
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute();
			Log.d("HERE", "AFTER CALL TO EXECUTE GETRSVPDB");
			return;
		}


		// Get RSVP value from DB
		private void getUserInterests(){
			class profileTask extends AsyncTask<String, Void, String> {
				protected String doInBackground(String[] args){
					JSONObject json = null;
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getUserInterests&param="+encodeHTML(useremail));
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
							JSONArray interests = json.getJSONArray("interests");
							result = "";
							for (int i = 0; i < interests.length(); i++){
								result += interests.getString(i)+"_";
							}
							return result;
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
						if (result.length() > 0){
							dbInterests = result.split("_");
							updateUserInfo();
						}
						return;
					}
					else{
						Log.d("FAILURE", "FAILURE");
						return;
					}
				}
			}
			profileTask sendPostReqAsyncTask = new profileTask();
			sendPostReqAsyncTask.execute();
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
