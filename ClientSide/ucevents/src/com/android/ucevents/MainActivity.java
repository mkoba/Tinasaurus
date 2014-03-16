package com.android.ucevents;

import com.google.analytics.tracking.android.*;
import com.ucevents.signup.signupActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.crashlytics.android.Crashlytics;


import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	private TextView userInfoTextView;
	private String firstname;
	private String lastname;
	private String email;
	private String userid;
	private boolean ucsdNetwork = false;


	//create a new UiLifecycleHelper, passing in the callback var
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityfb_login);
		Crashlytics.start(this);
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("email", "basic_info"));

		//textview to add info
		//userInfoTextView = (TextView) findViewById(R.id.userInfoTextView);

		uiHelper = new UiLifecycleHelper(MainActivity.this, callback);
		uiHelper.onCreate(savedInstanceState);
	}

	// controls the UI, responds to session state changes
	@SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			//Log.i(TAG, "Logged in...");
			//userInfoTextView.setVisibility(View.VISIBLE);
			// Request user data and show the results
			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						// Display the parsed user info
						//userInfoTextView.setText(buildUserInfoDisplay(user));
						System.out.println("firstname " + user.getFirstName()
								+ " lastname " + user.getLastName() + " id " 
								+ user.getId() );
						email = user.asMap().get("email").toString();
						// System.out.println("email: " + email);
						firstname = user.getFirstName();
						lastname = user.getLastName();
						userid = user.getId();


						// checking if fb user is part of UCSD network
						String fqlQuery = "SELECT name FROM user WHERE uid = me() AND 'UCSD' in affiliations";

						Bundle params = new Bundle();
						params.putString("q", fqlQuery);
						Session session2 = Session.getActiveSession();
						Request request = new Request(session2,"/fql",params,HttpMethod.GET,new Request.Callback(){
							public void onCompleted(Response response){

								//Log.i("H", "Result: " + response.toString() +  " " + response.getGraphObject() );
								GraphObject graphObject = response.getGraphObject();
								//  String nameVal = graphObject.getProperty("name").toString();
								System.out.println("graph name" + graphObject.getInnerJSONObject());
								JSONObject network = graphObject.getInnerJSONObject();
								// Add the language name to a list. Use JSON
								// methods to get access to the name field. 
								// try {
								try {
									System.out.println("Network  " + network.getString("data"));
									JSONArray data = network.getJSONArray("data");
									JSONObject inner = data.getJSONObject(0);
									System.out.println( "grabbed value: " + inner.getString("name"));
									ucsdNetwork=true;

								} catch (JSONException e) {
									ucsdNetwork=false;
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								ucsdUpdated();
							}
						});

						Request.executeBatchAsync(request);





					}
				}
			});
		} else if (state.isClosed()) {
			//Log.i(TAG, "Logged out...");
			//userInfoTextView.setVisibility(View.INVISIBLE);
		}
	}


	public void ucsdUpdated() {
		if(ucsdNetwork) {
			/* DB access here to see if user is first time user 
			 * If first time user: go to signupActivity.java
			 * else go to EventsActivity.java
			 */
			// new user
			checkIfUserExists(email);
			
		}
		else {
			// if not part of UCSD network, then logout and reprompt for login
			Toast.makeText(getApplicationContext(), "Not part of UCSD network. Please login as valid user", 
					Toast.LENGTH_LONG).show();
			//Intent i = new Intent(MainActivity.this, com.android.ucevents.LogoutActivity.class);
			//startActivity(i);
		}
	}

	//listen for changes
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	/*
	 * call uiHelper for these methods
	 */
	@Override
	public void onResume() {
		super.onResume();

		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}


	//parses through Json for info
	private String buildUserInfoDisplay(GraphUser user) {
		System.out.println("user info? : " + user);
		StringBuilder userInfo = new StringBuilder("");

		// Example: typed access (name)
		// - no special permissions required
		userInfo.append(String.format("Name: %s\n\n", 
				user.getName()));

		email = user.asMap().get("email").toString();
		// System.out.println("email: " + email);
		firstname = user.getFirstName();
		lastname = user.getLastName();

		userInfo.append(String.format("Email: %s\n\n", email));
		userInfo.append(String.format("First: %s\n\n", firstname));
		userInfo.append(String.format("Last: %s\n\n", lastname));

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

	private void checkIfUserExists(String fbemail){
		class eventsTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getIfUserExists&param="+encodeHTML(email));
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
					if (result.contains("True")){
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
				return "NO USER";
			}
			protected void onPostExecute(String result){
				if(result != null){
					boolean userStatus = result.equals("NO USER");
					Intent i;
					if(userStatus) { // new user
						i = new Intent(MainActivity.this, com.ucevents.signup.signupActivity.class);
					}
					else { // returning user
						i = new Intent(MainActivity.this, com.ucevents.events.EventsActivity.class);
					}

					Bundle b = new Bundle();
					b.putString("firstname", firstname);
					b.putString("lastname", lastname);
					b.putString("email", email);

					UCEvents_App appState = ((UCEvents_App)getApplicationContext());
					appState.setUserId(email);
					appState.setFirstName(firstname);
					appState.setLastName(lastname);
					appState.setFbId(userid);
					
				
					i.putExtras(b);
					//Intent i = new Intent(MainActivity.this, LogoutActivity.class);
					startActivity(i);
					return;
				}
				else{
					Log.d("FAILURE", "FAILURE");
					Toast.makeText(getApplicationContext(), "Error determining if you have an account. Please try again later.",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}
		eventsTask sendPostReqAsyncTask = new eventsTask();
		sendPostReqAsyncTask.execute(email);
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
}
