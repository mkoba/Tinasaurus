package com.ucevents.events;

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
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.MenuActivity;
import com.ucevents.schedule.Schedule;
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

public class EventsListActivity extends MenuActivity{
	String value;
	String userid;
	private List<Events> eventList = new ArrayList<Events>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allevents);
		Bundle bundle = getIntent().getExtras();
		value = bundle.getString("key");

		UCEvents_App appState = ((UCEvents_App)getApplicationContext());
		userid = appState.getUserId();
		
		if(value.toString().equals("food") || value.toString().equals("study") || value.toString().equals("career") || value.toString().equals("sport") || value.toString().equals("sports") || value.toString().equals("organization") || value.toString().equals("social") || value.toString().equals("other")){
			if (value.toString().equals("sport")){
				populateEventList("sports");
			}
			else{
				populateEventList(value);
			}
		}
		else if(value.toString().equals("interest")){
			populateEventList(value, false);
		}
		else{
			populateEventList();
		}
		populateListView();
		registerClickCallback();

	}

	// this will be later populated from db
	private void populateEventList() {
		sendPostRequest("allEvents");
	}

	// populateEventList for category sorting
	private void populateEventList(String category){
		sendPostRequest(category);
	}
	private void populateEventList(String interest, boolean nothingElse){
		sendPostRequest(interest);
	}
	private void sendPostRequest(final String key) {
		class scheduleTask extends AsyncTask<String, Void, String>{
			protected String doInBackground(String[] args){
				JSONObject json = null;
				HttpClient client = new DefaultHttpClient();
				HttpGet get;
				if(key.equals("allEvents")){
					get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getAllEvents&param="+encodeHTML(userid));
				}
				else if(key.equals("interest")){
					get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getEventsFromUserInterests&param="+encodeHTML(userid));
				}
				else{
					get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getEventsInCategory&param="+encodeHTML(key)+"&user="+encodeHTML(userid));
				}
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
					Log.d("EXCEPTION", e.toString());
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
			super(EventsListActivity.this, R.layout.eventslistview, eventList);
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
				Intent i = new Intent(EventsListActivity.this, com.ucevents.events.IndividualEventsActivity.class);
				Bundle b = new Bundle();
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
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	public String encodeHTML(String s)
	{
		s = s.replaceAll(" ", "%20");
		s = s.replaceAll("!", "%21");
		s = s.replaceAll("'", "%27");
		return s;
	}

}


