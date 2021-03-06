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
import org.apache.http.HttpStatus;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ucevents.R;
import com.ucevents.login.UCEvents_App;
import com.ucevents.menu.MenuActivity;

public class ScheduleActivity extends MenuActivity {
	Button addEvent;
	Button createBut; 

	String value; 
	private List<Schedule> schList = new ArrayList<Schedule>();

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		populateEventList();
	}

	// this will be later populated from db
	private void populateEventList() {
		//TODO: Replace email with userid
		String userid = ((UCEvents_App)getApplicationContext()).getUserId();
		sendPostRequest(userid);
	}

	private void populateListView() {
		ArrayAdapter<Schedule> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.lvallEvents);
		list.setAdapter(adapter);

	}

	private class MyListAdapter extends ArrayAdapter<Schedule> {
		public MyListAdapter() {
			super(ScheduleActivity.this, R.layout.schedulelistview, schList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			//make sure we have a view to work with (may have been given null)
			if(itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.schedulelistview, parent, false);
			}

			// find event to work with
			Schedule currEvent = schList.get(position);

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

				Schedule clickedEvent = schList.get(position);
				Intent i = new Intent(ScheduleActivity.this, com.ucevents.schedule.IndividualScheduleActivity.class);
				Bundle b = new Bundle();
				b.putParcelable("chosenEvent", clickedEvent);
				i.putExtras(b);
				startActivity(i);
			}
		});

	}

	private void sendPostRequest(String user){
		class scheduleTask extends AsyncTask<String, Void, String> {
			protected String doInBackground(String[] args){
				JSONObject json = null;
				HttpClient client = new DefaultHttpClient();
				//********** HOW TO GET USER ID?? TINAAAAA ****************//
				HttpGet get = new HttpGet("http://ucevents-mjs7wmrfmz.elasticbeanstalk.com/get_query.jsp?method=getEventsUserAttending&param="+encodeHTML(args[0]));
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();

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
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
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
				JSONArray listOfEvents = null;
				try {
					listOfEvents = json.getJSONArray("events");
					for (int i = 0; i < listOfEvents.length(); i++){
						JSONObject event = listOfEvents.getJSONObject(i);
						int iconid = 0;
						JSONArray categories = new JSONArray();
						try{
							categories = event.getJSONArray("category");
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
						} catch (JSONException e){
							iconid = R.drawable.other_icon;
						}
						JSONArray json_attendees = event.getJSONArray("attendees");
						String[] attendees = new String[json_attendees.length()];
						for (int j = 0; j < json_attendees.length(); j++){
							attendees[j] = json_attendees.getString(j);
						}
						String eventName = event.getString("name").substring(event.getString("name").indexOf("_") + 1);
						while(eventName.contains("_")){
							eventName = eventName.substring(eventName.indexOf("_")+1);
						}
						schList.add(new Schedule(event.getString("name"), eventName,
								event.getInt("hour")*100+event.getInt("min"), event.getString("location"),event.getInt("month"),event.getInt("date"),
								event.getInt("year"), event.getString("description"), event.getString("host"), iconid, attendees, event.getBoolean("attending")));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				return "SUCCESS";
			}
			protected void onPostExecute(String result){
				if(result != null){
					Collections.sort(schList);
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
		sendPostReqAsyncTask.execute(user);
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
