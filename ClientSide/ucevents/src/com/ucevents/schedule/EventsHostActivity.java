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

import com.android.ucevents.R;
import com.ucevents.events.Events;
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.MenuActivity;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsHostActivity extends MenuActivity{
	String value;
	String userid;
	
	private List<Events> eventList = new ArrayList<Events>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allevents);
		Bundle bundle = getIntent().getExtras();
		
		try{
			value = bundle.getString("key");
		}catch(NullPointerException e){
			e.printStackTrace();
		}

		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		userid = appState.getUserId();

		populateEventList();
		
		populateListView();
		registerClickCallback();

	}

	// this will be later populated from db
	private void populateEventList() {
		sendPostRequest();
	}


	
	private void sendPostRequest() {
		class scheduleTask extends AsyncTask<String, Void, String>{
			protected String doInBackground(String[] args){
				JSONObject json = null;
				HttpClient client = new DefaultHttpClient();
				HttpGet get;
				get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getEventsUserHosting&param="+encodeHTML(userid));
			
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

					try{
						json = new JSONObject(result);
					}catch(JSONException e){
						e.printStackTrace();
						return null;
					}
				}catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return null;
				}

				JSONArray listOfEvents = null;
				try{
					listOfEvents = json.getJSONArray("events");
					for(int i = 0; i < listOfEvents.length(); i++){
						JSONObject event = listOfEvents.getJSONObject(i);
						int iconid = 0;
						JSONArray categories = new JSONArray();
						String[] categoryList = new String[categories.length()];
						try{
							categories = event.getJSONArray("category");
							categoryList = new String[categories.length()];
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
							for(int j = 0; j < categories.length(); j++){
								categoryList[j] = categories.getString(j);
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
								event.getInt("year"), event.getString("description"), event.getString("host"), iconid, attendees, event.getBoolean("attending"), categoryList));
					}
					Collections.sort(eventList);
				}catch(JSONException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				return "SUCCESS";
			}
			protected void onPostExecute(String result){
				if(result != null){
					populateListView();
					registerClickCallback();
					return;
				}
				else{
					return;
				}
			}
		}
		scheduleTask sendPostReqAsyncTask = new scheduleTask();
		sendPostReqAsyncTask.execute();
		return;
	}


	private void populateListView() {
		ArrayAdapter<Events> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.lvallEvents);
		list.setAdapter(adapter);



	}

	private class MyListAdapter extends ArrayAdapter<Events> {
		public MyListAdapter() {
			super(EventsHostActivity.this, R.layout.eventslistview, eventList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			//make sure we have a view to work with (may have been given null)
			if(itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.eventslistview, parent, false);
			}

			// find event to work with
			Events currEvent = eventList.get(position);

			// fill the view
			ImageView imageView = (ImageView) itemView.findViewById(R.id.item_ivevent_icon);
			imageView.setImageResource(currEvent.getIconid());

			// Name:
			TextView nameText = (TextView) itemView.findViewById(R.id.item_tvName);
			nameText.setText(currEvent.getName());

			// Time:
			TextView timeText = (TextView) itemView.findViewById(R.id.item_txtTime);
			timeText.setText(currEvent.getTimeDisplay());

			// Date:
			TextView dateText = (TextView) itemView.findViewById(R.id.item_txtDate);
			dateText.setText(currEvent.getEventDate());

			// Location:
			TextView locationText = (TextView) itemView.findViewById(R.id.item_txtLocation);
			locationText.setText(currEvent.getLocation());

			return itemView;
		}


	}


	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.lvallEvents);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// position: place in list
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				Events clickedEvent = eventList.get(position);
				//Toast.makeText(getApplicationContext(), clickedEvent.getDescription(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(EventsHostActivity.this, com.ucevents.events.IndividualEventsActivity.class);
				Bundle b = new Bundle();
				b.putString("FROM","HOST");
				b.putParcelable("chosenEvent", clickedEvent);
				i.putExtras(b);
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

	public String encodeHTML(String s)
	{
		s = s.replaceAll(" ", "%20");
		s = s.replaceAll("!", "%21");
		s = s.replaceAll("'", "%27");
		return s;
	}

}


