package database;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class DB_Access {
	private Connection connection;
	private PreparedStatement p_stmt;
	private Statement stmt;
	private ResultSet rs;
	private Date date;

	//Constructor :)
	public DB_Access(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class.forName");
			e.printStackTrace();
			System.exit(1);
		}
		try{
			connection = DriverManager.getConnection("jdbc:mysql://ec2-54-213-38-52.us-west-2.compute.amazonaws.com:3306/test", "cse190", "cse190");
		} catch (SQLException e){
			System.out.println("Driver Manager failed");
			e.printStackTrace();
			System.exit(1);
		}
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Statement creation failed");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private String escapeAp(String s){
		int i = -1;
		i = s.indexOf("'", i+1);

		while (i != -1){
			s = s.substring(0, i) + "\\" + s.substring(i);
			i+=2;
			i = s.indexOf("'", i);
		}

		return s;
	}
	
	public String[] parseArray(String s){
		List<String> result = new ArrayList<String>();
		int prev = 1;
		int i = 0;
		while((i = s.indexOf(",", prev)) != -1){
			result.add(s.substring(prev, i).trim());
			prev = i+1;
			i = s.indexOf(",", prev);
		}
		result.add(s.substring(prev, s.length()-1).trim());
		return result.toArray(new String[result.size()]);
	}

	public void insertUser(String fname, String lname, String email, String[] interests) throws SQLException{
		fname = escapeAp(fname);
		lname = escapeAp(lname);
		email = escapeAp(email);

		p_stmt = connection.prepareStatement("INSERT INTO user_information (fname, lname, ucsd_email) VALUES (?, ?, ?)");
		p_stmt.setString(1, fname);
		p_stmt.setString(2, lname);
		p_stmt.setString(3, email);
		p_stmt.executeUpdate();

		for(int i = 0; i < interests.length; i++){
			p_stmt = connection.prepareStatement("INSERT INTO user_interests (user, interest) VALUES (?, ?)");
			p_stmt.setString(1, email);
			p_stmt.setString(2, interests[i]);
			p_stmt.executeUpdate();
		}
	}

	public void insertInterest(String interest) throws SQLException{
		interest = escapeAp(interest);
		p_stmt = connection.prepareStatement("INSERT INTO interests (name) VALUES (?)");
		p_stmt.setString(1, interest);
		p_stmt.executeUpdate();
	}

	// Title --> String
	// Location --> String
	// Time --> int for hour, int for minute, bool for pm
	// Category --> String[] interests
	// Date --> int for month, int for day, int for year
	// Description --> String
	// public --> boolean
	// host --> given
	public void insertEvent(String title, String location, int hour, int minute, boolean pm, 
			String[] interests, int month, int day, int year, String description, 
			boolean public_flag, String host) throws SQLException{

		if (pm){
			hour += 12;
		}

		title = escapeAp(title);
		description = escapeAp(description);
		host = escapeAp(host);

		p_stmt = connection.prepareStatement("INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		p_stmt.setString(1, title);
		p_stmt.setString(2, location);
		p_stmt.setInt(3, hour);
		p_stmt.setInt(4, minute);
		p_stmt.setInt(5, month);
		p_stmt.setInt(6, day);
		p_stmt.setInt(7, year);
		p_stmt.setString(8, description);
		p_stmt.setString(9, host);
		p_stmt.setBoolean(10, public_flag);
		p_stmt.executeUpdate();

		for(int i = 0; i < interests.length; i++){
			p_stmt = connection.prepareStatement("INSERT INTO event_category (event, interest) VALUES (?, ?)");
			p_stmt.setString(1, title);
			p_stmt.setString(2, interests[i]);
			p_stmt.executeUpdate();
		}
	}

	public void insertAttendee(String user, String event) throws SQLException{
		event = escapeAp(event);
		user = escapeAp(user);

		p_stmt = connection.prepareStatement("INSERT INTO attendees (event, attendee) VALUES (?, ?)");
		p_stmt.setString(1, event);
		p_stmt.setString(2, user);
		p_stmt.executeUpdate();
	}

	///////////////////////////////// UPDATE /////////////////////////////////

	// Title --> String
	// Location --> String
	// Time --> int for hour, int for minute, bool for pm
	// Category --> String[] interests
	// Date --> int for month, int for day, int for year
	// Description --> String
	public void updateEvents(String location, int hour, int minute, String[] interests, boolean pm,
			int month, int day, int year, String description, String name, String ucsd_email) throws SQLException{
		location = escapeAp(location);
		System.out.println(location);
		description = escapeAp(description);
		name = escapeAp(name);
		ucsd_email = escapeAp(ucsd_email);
		if (pm){
			hour += 12;
		}

		//UPDATE events SET location='Tina\'s Apartment' AND date=15 WHERE name='tszutu@ucsd.edu+Basketball Game!';
		p_stmt = connection.prepareStatement("UPDATE events SET location=? , hour=? , min=? "
				+ ", month=? , date=? , year=? , description=? WHERE name=?");
		p_stmt.setString(1, location);
		p_stmt.setInt(2, hour);
		p_stmt.setInt(3, minute);
		p_stmt.setInt(4, month);
		p_stmt.setInt(5, day);
		p_stmt.setInt(6, year);
		p_stmt.setString(7, description);
		p_stmt.setString(8, name);
		System.out.println(p_stmt);
		p_stmt.executeUpdate();

		p_stmt = connection.prepareStatement("DELETE FROM event_category WHERE event=?");
		p_stmt.setString(1, name);
		p_stmt.executeUpdate();
		for(int i = 0; i < interests.length; i++){
			/*rs = stmt.executeQuery("SELECT id FROM interests WHERE name = " + interest[i]);
				while(rs.next()){
					interestid = rs.getInt(1);
				}*/
			p_stmt = connection.prepareStatement("INSERT INTO event_category (event, interest) VALUES (?, ?)");
			p_stmt.setString(1, name);
			p_stmt.setString(2, interests[i]);
			p_stmt.executeUpdate();
		}
	}

	public void updateUser(String fname, String lname, String ucsd_email, String[] interests) throws SQLException{
		fname = escapeAp(fname);
		lname = escapeAp(lname);
		ucsd_email = escapeAp(ucsd_email);

		p_stmt = connection.prepareStatement("UPDATE user_information SET fname=? , lname=? WHERE ucsd_email=?");
		p_stmt.setString(1, fname);
		p_stmt.setString(2, lname);
		p_stmt.setString(3, ucsd_email);
		p_stmt.executeUpdate();
		
		p_stmt = connection.prepareStatement("DELETE FROM user_interests WHERE user=?");
		p_stmt.setString(1, ucsd_email);
		p_stmt.executeUpdate();
		for(int i = 0; i < interests.length; i++){
			p_stmt = connection.prepareStatement("INSERT INTO user_interests (user, interest) VALUES (?, ?)");
			p_stmt.setString(1, ucsd_email);
			p_stmt.setString(2, interests[i]);
			p_stmt.executeUpdate();
		}
	}

	///////////////////////////////// DELETE /////////////////////////////////

	public void deleteUserInterests(String user, String interest) throws SQLException{
		user = escapeAp(user);
		interest = escapeAp(interest);
		p_stmt = connection.prepareStatement("DELETE " +
				"FROM user_interests " +
				"WHERE ? = user AND ? = interest;");
		p_stmt.setString(1, user);
		p_stmt.setString(2, interest);
		p_stmt.executeUpdate();
	}

	public void deleteEvent(String event) throws SQLException{
		event = escapeAp(event);
		p_stmt = connection.prepareStatement("DELETE " + 
				"FROM events" +
				" WHERE name = ?");
		p_stmt.setString(1, event);
		p_stmt.executeUpdate();
	}

	public void deleteAttendee(String event, String attendee) throws SQLException{
		event = escapeAp(event);
		attendee = escapeAp(attendee);

		p_stmt = connection.prepareStatement("DELETE " +
				"FROM attendees "+
				"WHERE ? = event and ? = attendee");
		p_stmt.setString(1, event);
		p_stmt.setString(2, attendee);
		p_stmt.executeUpdate();
	}
	
	public boolean deleteIfPastTime(Calendar c, String event) throws SQLException{
		Calendar current = Calendar.getInstance();
		System.out.println(event + " c before current? " + current.after(c));
		if (current.after(c)){
			try{
				System.out.println("DELETING EVENT, PAST TIME BY 2 HOURS");
				deleteEvent(event);
				return true;
			}catch (Exception e){
				System.out.println("CAUGHT EXCEPTION");
				return false;
			}
		}
		return false;
	}

	///////////////////////////////// SELECT /////////////////////////////////

	public String getIfUserExists(String user){
		try{
			JSONObject json = getUserInformation(user);
			json.getString("fname");
			return "True";
		} catch(Exception e){
			return "False";
		}
	}
	// Get user's information -- fname, lname, email, interests
	public JSONObject getUserInformation(String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT * FROM user_information WHERE ucsd_email = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.put("fname", rs.getString("fname"));
			result.put("lname", rs.getString("lname"));
			result.put("ucsd_email", rs.getString("ucsd_email"));
		}

		p_stmt = connection.prepareStatement("SELECT * FROM user_interests WHERE user = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		List<String> interests = new ArrayList<String>();
		while(rs.next()){
			interests.add(rs.getString("interest"));
		}
		result.put("interests", interests);
		return result;
	}

	// Get user's interests
	public JSONObject getUserInterests(String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		List<String> interests = new ArrayList<String>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT * FROM user_interests WHERE user = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			interests.add(rs.getString("interest"));
		}
		result.put("interests", interests);

		return result;
	}
	
	public String getHost(String event) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("SELECT fname, lname FROM events, user_information WHERE events.host = user_information.ucsd_email");
		ResultSet rs = ps.executeQuery();
		String host = null;
		while(rs.next()){
			host = rs.getString("fname") + " " + rs.getString("lname");
		}
		return host;
	}
	
	public JSONArray getEventCategory(String event) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM event_category WHERE event = '" + event + "'");
		ResultSet rs = ps.executeQuery();
		JSONArray categories = new JSONArray();
		while(rs.next()){
			categories.put(rs.getString(2));
		}
		return categories;
	}

	// Get all events
	public JSONObject getAllEvents(String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		List<JSONObject> eventslist = new ArrayList<JSONObject>();
		p_stmt = connection.prepareStatement("SELECT * FROM events");
		ResultSet rs = p_stmt.executeQuery();
		while(rs.next()){
			//int year, int month, int date, int hrs, int min
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH).parse(rs.getInt("year")+"/"+rs.getInt("month")+"/"+ rs.getInt("date")+ " " + (rs.getInt("hour")+9) + ":" + rs.getInt("min") + ":00"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(deleteIfPastTime(cal, rs.getString("name"))){
				continue;
			}
			else{
				JSONObject event = new JSONObject();
				event.put("name", rs.getString("name"));
				event.put("location", rs.getString("location"));
				event.put("hour", rs.getInt("hour"));
				event.put("min", rs.getInt("min"));
				event.put("month", rs.getInt("month"));
				event.put("date", rs.getInt("date"));
				event.put("year", rs.getInt("year"));
				event.put("description", rs.getString("description"));
				JSONObject attendees = getAttendees(rs.getString("name"), user);
				try{
					event.put("attendees", attendees.get("attendees"));
				} catch (JSONException e){
					event.put("attendees", new ArrayList<String>());
					//do nothing
				}
				event.put("attending", attendees.getBoolean("attending"));
				event.put("host", getHost(rs.getString("name")));
				event.put("category", getEventCategory(rs.getString("name")));
				eventslist.add(event);
			}
		}
		result.put("events", eventslist);
		return result;
	}

	// Get all events in a category
	public JSONObject getEventsInCategory(String category, String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		List<JSONObject> eventslist = new ArrayList<JSONObject>();
		category = escapeAp(category);
		p_stmt = connection.prepareStatement("SELECT name, location, hour, min, month, date, year, description " +
				"FROM events, event_category " + 
				"WHERE events.name = event_category.event AND event_category.interest = ?");
		p_stmt.setString(1, category);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH).parse(rs.getInt("year")+"/"+rs.getInt("month")+"/"+ rs.getInt("date")+ " " + (rs.getInt("hour")+9) + ":" + rs.getInt("min") + ":00"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(deleteIfPastTime(cal, rs.getString("name"))){
				continue;
			}
			else{
				JSONObject event = new JSONObject();
				event.put("name", rs.getString("name"));
				event.put("location", rs.getString("location"));
				event.put("hour", rs.getInt("hour"));
				event.put("min", rs.getInt("min"));
				event.put("month", rs.getInt("month"));
				event.put("date", rs.getInt("date"));
				event.put("year", rs.getInt("year"));
				event.put("description", rs.getString("description"));
				JSONObject attendees = getAttendees(rs.getString("name"), user);
				try{
					event.put("attendees", attendees.get("attendees"));
				} catch (JSONException e){
					//do nothing
					event.put("attendees", new ArrayList<String>());
				}
				event.put("attending", attendees.getBoolean("attending"));
				event.put("host", getHost(rs.getString("name")));
				event.put("category", getEventCategory(rs.getString("name")));
				eventslist.add(event);
			}
		}
		result.put("events", eventslist);
		return result;
	}

	// Get all events that suit a user's interest
	public JSONObject getEventsFromUserInterests(String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT * FROM user_interests WHERE user = ?");
		p_stmt.setString(1, user);
		ResultSet rs = p_stmt.executeQuery();
		List<JSONObject> eventslist = new ArrayList<JSONObject>();
		while(rs.next()){
			String interest = rs.getString("interest");
			JSONObject eventsincategory = getEventsInCategory(interest, user);
			JSONArray events = eventsincategory.getJSONArray("events");
			for(int i = 0; i < events.length(); i++){
				eventslist.add(events.getJSONObject(i));
			}
		}

		result.put("events", eventslist);
		return result;
	}
	// Get all events a user is attending
	public JSONObject getEventsUserAttending(String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		List<JSONObject> eventslist = new ArrayList<JSONObject>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT event FROM attendees WHERE attendee = ?");
		p_stmt.setString(1, user);
		ResultSet rs = p_stmt.executeQuery();
		while(rs.next()){
			String event_id = rs.getString("event");
			System.out.println(event_id);
			JSONObject event = new JSONObject();
			p_stmt = connection.prepareStatement("SELECT name, location, hour, min, month, date, year, description " +
					"FROM events " + 
					"WHERE name = ?");
			p_stmt.setString(1, event_id);
			ResultSet rs_tmp = p_stmt.executeQuery();
			while(rs_tmp.next()){
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH).parse(rs.getInt("year")+"/"+rs.getInt("month")+"/"+ rs.getInt("date")+ " " + (rs.getInt("hour")+9) + ":" + rs.getInt("min") + ":00"));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(deleteIfPastTime(cal, rs.getString("name"))){
					break;
				}
				else{
					event.put("name", rs_tmp.getString("name"));
					event.put("location", rs_tmp.getString("location"));
					event.put("hour", rs_tmp.getInt("hour"));
					event.put("min", rs_tmp.getInt("min"));
					event.put("month", rs_tmp.getInt("month"));
					event.put("date", rs_tmp.getInt("date"));
					event.put("year", rs_tmp.getInt("year"));
					event.put("description", rs_tmp.getString("description"));
					JSONObject attendees = getAttendees(rs_tmp.getString("name"), user);
					try{
						event.put("attendees", attendees.get("attendees"));
					} catch (JSONException e){
						//do nothing
						event.put("attendees", new ArrayList<String>());
					}
					event.put("attending", attendees.getBoolean("attending"));
					event.put("host", getHost(rs_tmp.getString("name")));
					event.put("category", getEventCategory(rs_tmp.getString("name")));
					eventslist.add(event);
				}
			}
		}

		result.put("events",eventslist);

		return result;
	}

	// Get all events a user is hosting
	public JSONObject getEventsUserHosting(String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		List<JSONObject> eventslist = new ArrayList<JSONObject>();
		//public List<List<String>> getEventsUserHosting(String user) throws SQLException{
		//List<List<String>> result = new ArrayList<List<String>>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT name FROM events WHERE host = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			//List<String> event = new ArrayList<String>();
			JSONObject event = new JSONObject();
			String event_id = rs.getString("name");
			p_stmt = connection.prepareStatement("SELECT name, location, hour, min, month, date, year, description " +
					"FROM events " + 
					"WHERE name = ?");
			p_stmt.setString(1, event_id);
			ResultSet rs_tmp = p_stmt.executeQuery();
			while(rs_tmp.next()){
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH).parse(rs.getInt("year")+"/"+rs.getInt("month")+"/"+ rs.getInt("date")+ " " + (rs.getInt("hour")+9) + ":" + rs.getInt("min") + ":00"));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(deleteIfPastTime(cal, rs.getString("name"))){
					continue;
				}
				else{
					event.put("name", rs_tmp.getString("name"));
					event.put("location", rs_tmp.getString("location"));
					event.put("hour", rs_tmp.getInt("hour"));
					event.put("min", rs_tmp.getInt("min"));
					event.put("month", rs_tmp.getInt("month"));
					event.put("date", rs_tmp.getInt("date"));
					event.put("year", rs_tmp.getInt("year"));
					event.put("description", rs_tmp.getString("description"));
					JSONObject attendees = getAttendees(rs.getString("name"), user);
					try{
						event.put("attendees", attendees.get("attendees"));
					} catch (JSONException e){
						//do nothing
						event.put("attendees", new ArrayList<String>());
					}
					event.put("attending", attendees.getBoolean("attending"));
					event.put("host", getHost(rs_tmp.getString("name")));
					event.put("category", getEventCategory(rs_tmp.getString("name")));
					eventslist.add(event);
				}
			}
		}
		result.put("events", eventslist);
		return result;
	}

	// Get all attendees of an event
	public JSONObject getAttendees(String event, String user) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		event = escapeAp(event);
		p_stmt = connection.prepareStatement("SELECT attendee FROM attendees WHERE event = ?");
		p_stmt.setString(1, event);
		ResultSet rs = p_stmt.executeQuery();
		List<String> attendee = new ArrayList<String>();
		boolean attending = false;
		while(rs.next()){
			String attendee_id = rs.getString("attendee");
			if (attendee_id.equals(user)){
				attending = true;
			}
			p_stmt = connection.prepareStatement("SELECT fname, lname" +
					" FROM user_information " + 
					"WHERE ucsd_email = ?");
			p_stmt.setString(1, attendee_id);
			ResultSet rs_tmp = p_stmt.executeQuery();
			while(rs_tmp.next()){
				attendee.add(rs_tmp.getString("fname") + " " + rs_tmp.getString("lname"));
				//attendee.add(rs_tmp.getString("lname"));
			}
			result.put("attendees", attendee);
		}
		result.put("attending", attending);
		return result;
	}

	// Get all information about an Event (including attendees)
	public JSONObject getEventInformation(String event) throws SQLException, JSONException{
		JSONObject result = new JSONObject();
		//public List<String> getEventInformation(String event) throws SQLException{
		//List<String> result = new ArrayList<String>();
		p_stmt = connection.prepareStatement("SELECT * FROM events WHERE name = ?");
		p_stmt.setString(1, event);
		rs = p_stmt.executeQuery();
		String hostid = "";
		while(rs.next()){
			result.put("name", rs.getString("name"));
			result.put("location", rs.getString("location"));
			result.put("hour", rs.getString("hour"));
			result.put("min", rs.getString("min"));
			result.put("month", rs.getString("month"));
			result.put("date", rs.getString("date"));
			result.put("year", rs.getString("year"));
			result.put("description", rs.getString("description"));
			hostid=rs.getString("host");
		}
		p_stmt = connection.prepareStatement("SELECT fname, lname FROM user_information WHERE ucsd_email = ?");
		p_stmt.setString(1, hostid);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.put("host", rs.getString("fname")+" "+rs.getString("lname"));
			//result.put("lname", rs.getString("lname"));
		}

		p_stmt = connection.prepareStatement("SELECT attendee FROM attendees WHERE event = ?");
		p_stmt.setString(1, event);
		rs = p_stmt.executeQuery();
		List<String> attendee = new ArrayList<String>();
		while(rs.next()){
			String attendee_id = rs.getString("attendee");
			p_stmt = connection.prepareStatement("SELECT fname, lname" +
					" FROM user_information " + 
					"WHERE ucsd_email = ?");
			p_stmt.setString(1, attendee_id);
			ResultSet rs_tmp = p_stmt.executeQuery();
			while(rs_tmp.next()){
				attendee.add(rs_tmp.getString("fname") + " " + rs_tmp.getString("lname"));
			}
			result.put("attendees", attendee);
			//result.add(attendee);
		}
		return result;
	}
	//////

	public static void main(String [] args) throws IOException, JSONException{
		//sup dudes
		AWSCredentials credentials = 
				new PropertiesCredentials(new File("src/AwsCredentials.properties"));
		AmazonEC2 amazonEC2 = new AmazonEC2Client(credentials);
		//Set to be same as Manager(??) thingie
		amazonEC2.setEndpoint("ec2.us-west-2.amazonaws.com");
		/*
		//Creating security group
		CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest();
		createSecurityGroupRequest.withGroupName("CSE190Group").withDescription("190 Security Group");
		CreateSecurityGroupResult createSecurityGroupResult = amazonEC2.createSecurityGroup(createSecurityGroupRequest);
		System.out.println("done");
		 */
		DB_Access db = new DB_Access();
		String[] event_category = {"food"};
		String[] interests = {"food", "sports"};
		try {
			JSONObject json = db.getEventsInCategory("food", "marieli530@gmail.com");
			JSONArray arr= json.getJSONArray("events");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {*/
			//db.insertUser("Leon", "Cam", "lcam@ucsd.edu", interests);
			//db.insertEvent("jclin06@ucsd.edu_Dinner with Judy", "Bistro", 6, 30, true, event_category, 2, 15, 2014, "I want to eat dinner at the bistro! Let's eat together :)", false, "jclin06@ucsd.edu");
			//db.insertAttendee("mkoba@ucsd.edu", "jclin06@ucsd.edu_Dinner with Judy");
			//db.deleteEvent("jclin06@ucsd.edu_Dinner with Judy");
			//db.deleteUserInterests("lcam@ucsd.edu", "food");
			//db.updateEvents("Tina's Apartment", 7, 30, event_category, true, 2, 16, 2014, "I want to eat dinner at the bistro! Let's eat together :)", "jclin06@ucsd.edu_Dinner with Judy", "jclin06@ucsd.edu");
			//db.updateUser("MARI", "KOBAB", "mkoba@ucsd.edu");
			//db.deleteAttendee("jclin06@ucsd.edu+Dinner with Judy", "mkoba@ucsd.edu");
			/*List<String> result = db.getUserInformation("tszutu@ucsd.edu");
			for(int i = 0; i < result.size(); i++){
				System.out.println(result.get(i));
			}

			System.out.println("**********************************");*/
			/*
			List<List<String>> r = db.getAttendees("jclin06@ucsd.edu_Dinner with Judy");
			for (int i = 0; i < r.size(); i++){
				System.out.println("EVENT " + i);
				for (int j = 0; j < r.get(i).size(); j++){
					System.out.println(r.get(i).get(j));
				}
			}*/
			/*JSONObject json = db.getEventsUserHosting("mkoba@ucsd.edu");
			System.out.println(json.toString());
			json = db.getAllEvents("mkoba@ucsd.edu");
			System.out.println(json.toString());
			
			String[] result = db.parseArray("[hello, world]");
			for (int i = 0; i < result.length; i++){
				System.out.println("i: " + result[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED :(");
			System.exit(1);
		}*/
	}
}
