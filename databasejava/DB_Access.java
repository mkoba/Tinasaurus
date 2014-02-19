import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;

public class DB_Access {
	private Connection connection;
	private PreparedStatement p_stmt;
	private Statement stmt;
	private ResultSet rs;
	
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
		int eventid = 0;
		
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

		public void updateUser(String fname, String lname, String ucsd_email) throws SQLException{
			fname = escapeAp(fname);
			lname = escapeAp(lname);
			ucsd_email = escapeAp(ucsd_email);
			
			p_stmt = connection.prepareStatement("UPDATE user_information SET fname=? , lname=? WHERE ucsd_email=?");
			p_stmt.setString(1, fname);
			p_stmt.setString(2, lname);
			p_stmt.setString(3, ucsd_email);
			p_stmt.executeUpdate();
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
	
	///////////////////////////////// SELECT /////////////////////////////////
	
	// Get user's information -- fname, lname, email, interests
	public List<String> getUserInformation(String user) throws SQLException{
		List<String> result = new ArrayList<String>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT * FROM user_information WHERE ucsd_email = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.add(rs.getString("fname"));
			result.add(rs.getString("lname"));
			result.add(rs.getString("ucsd_email"));
		}
		
		p_stmt = connection.prepareStatement("SELECT * FROM user_interests WHERE user = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.add(rs.getString("interest"));
		}
		
		return result;
	}
	
	// Get user's interests
	public List<String> getUserInterests(String user) throws SQLException{
		List<String> result = new ArrayList<String>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT * FROM user_interests WHERE user = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.add(rs.getString("interest"));
		}
		
		return result;
	}
	
	// Get all events
	public List<List<String>> getAllEvents() throws SQLException{
		List<List<String>> result = new ArrayList<List<String>>();
		p_stmt = connection.prepareStatement("SELECT * FROM events");
		rs = p_stmt.executeQuery();
		while(rs.next()){
			List<String> event = new ArrayList<String>();
			event.add(rs.getString("name"));
			event.add(rs.getString("location"));
			event.add(rs.getString("hour"));
			event.add(rs.getString("min"));
			event.add(rs.getString("month"));
			event.add(rs.getString("date"));
			event.add(rs.getString("year"));
			event.add(rs.getString("description"));
			result.add(event);
		}
		return result;
	}

	// Get all events in a category
	public List<List<String>> getEventsInCategory(String category) throws SQLException{
		List<List<String>> result = new ArrayList<List<String>>();
		category = escapeAp(category);
		p_stmt = connection.prepareStatement("SELECT name, location, hour, min, month, date, year, description " +
											 "FROM events, event_category " + 
											 "WHERE events.name = event_category.event AND event_category.interest = ?");
		p_stmt.setString(1, category);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			List<String> event = new ArrayList<String>();
			event.add(rs.getString("name"));
			event.add(rs.getString("location"));
			event.add(rs.getString("hour"));
			event.add(rs.getString("min"));
			event.add(rs.getString("month"));
			event.add(rs.getString("date"));
			event.add(rs.getString("year"));
			event.add(rs.getString("description"));
			result.add(event);
		}
		return result;
	}
	
	// Get all events that suit a user's interest
	public List<List<String>> getEventsFromUserInterests(String user) throws SQLException{
		List<List<String>> result = new ArrayList<List<String>>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT * FROM user_interests WHERE user = ?");
		p_stmt.setString(1, user);
		ResultSet rs = p_stmt.executeQuery();
		
		while(rs.next()){
			String interest = rs.getString("interest");
			result.addAll(getEventsInCategory(interest));
		}
		return result;
	}
	
	// Get all events a user is attending
	public List<List<String>> getEventsUserAttending(String user) throws SQLException{
		List<List<String>> result = new ArrayList<List<String>>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT event FROM attendees WHERE attendee = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			List<String> event = new ArrayList<String>();
			String event_id = rs.getString("event");
			p_stmt = connection.prepareStatement("SELECT name, location, hour, min, month, date, year, description " +
											 	 "FROM events " + 
											 	 "WHERE name = ?");
			p_stmt.setString(1, event_id);
			ResultSet rs_tmp = p_stmt.executeQuery();
			while(rs_tmp.next()){
				event.add(rs_tmp.getString("name"));
				event.add(rs_tmp.getString("location"));
				event.add(rs_tmp.getString("hour"));
				event.add(rs_tmp.getString("min"));
				event.add(rs_tmp.getString("month"));
				event.add(rs_tmp.getString("date"));
				event.add(rs_tmp.getString("year"));
				event.add(rs_tmp.getString("description"));
			}
			result.add(event);
		}
		
		return result;
	}
	
	// Get all events a user is hosting
	public List<List<String>> getEventsUserHosting(String user) throws SQLException{
		List<List<String>> result = new ArrayList<List<String>>();
		user = escapeAp(user);
		p_stmt = connection.prepareStatement("SELECT name FROM events WHERE host = ?");
		p_stmt.setString(1, user);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			List<String> event = new ArrayList<String>();
			String event_id = rs.getString("name");
			p_stmt = connection.prepareStatement("SELECT name, location, hour, min, month, date, year, description " +
											 	 "FROM events " + 
											 	 "WHERE name = ?");
			p_stmt.setString(1, event_id);
			ResultSet rs_tmp = p_stmt.executeQuery();
			while(rs_tmp.next()){
				event.add(rs_tmp.getString("name"));
				event.add(rs_tmp.getString("location"));
				event.add(rs_tmp.getString("hour"));
				event.add(rs_tmp.getString("min"));
				event.add(rs_tmp.getString("month"));
				event.add(rs_tmp.getString("date"));
				event.add(rs_tmp.getString("year"));
				event.add(rs_tmp.getString("description"));
			}
			result.add(event);
		}
		return result;
	}
	
	// Get all attendees of an event
	public List<String> getAttendees(String event) throws SQLException{
		List<String> result = new ArrayList<String>();
		event = escapeAp(event);
		p_stmt = connection.prepareStatement("SELECT attendee FROM attendees WHERE event = ?");
		p_stmt.setString(1, event);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.add(rs.getString("attendee"));
		}
		return result;
	}
	
	// Get all information about an Event (including attendees)
	public List<String> getEventInformation(String event) throws SQLException{
		List<String> result = new ArrayList<String>();
		p_stmt = connection.prepareStatement("SELECT * FROM event WHERE name = ?");
		p_stmt.setString(1, event);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.add(rs.getString("name"));
			result.add(rs.getString("location"));
			result.add(rs.getString("hour"));
			result.add(rs.getString("min"));
			result.add(rs.getString("month"));
			result.add(rs.getString("date"));
			result.add(rs.getString("year"));
			result.add(rs.getString("description"));
		}
		p_stmt = connection.prepareStatement("SELECT attendee FROM attendees WHERE event = ?");
		p_stmt.setString(1, event);
		rs = p_stmt.executeQuery();
		while(rs.next()){
			result.add(rs.getString("attendee"));
		}
		return result;
	}
	//////
	
	public static void main(String [] args) throws IOException{
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
			//db.insertUser("Leon", "Cam", "lcam@ucsd.edu", interests);
			//db.insertAttendee("mkoba@ucsd.edu", "jclin06@ucsd.edu+Dinner with Judy");
			//db.insertEvent("jclin06@ucsd.edu+Dinner with Judy", "Bistro", 6, 30, true, event_category, 2, 15, 2014, "I want to eat dinner at the bistro! Let's eat together :)", false, "jclin06@ucsd.edu");
			//db.deleteEvent("jclin06@ucsd.edu+Dinner with Judy");
			//db.deleteUserInterests("lcam@ucsd.edu", "food");
			//db.updateEvents("Tina's Apartment", 7, 30, event_category, true, 2, 16, 2014, "I want to eat dinner at the bistro! Let's eat together :)", "jclin06@ucsd.edu+Dinner with Judy", "jclin06@ucsd.edu");
			//db.updateUser("MARI", "KOBAB", "mkoba@ucsd.edu");
			//db.insertAttendee("mkoba@ucsd.edu", "jclin06@ucsd.edu+Dinner with Judy");
			//db.deleteAttendee("jclin06@ucsd.edu+Dinner with Judy", "mkoba@ucsd.edu");
			/*List<String> result = db.getUserInformation("tszutu@ucsd.edu");
			for(int i = 0; i < result.size(); i++){
				System.out.println(result.get(i));
			}
			
			System.out.println("**********************************");*/
			
			List<List<String>> r = db.getEventsUserHosting("tszutu@ucsd.edu");
			for (int i = 0; i < r.size(); i++){
				System.out.println("EVENT " + i);
				for (int j = 0; j < r.get(i).size(); j++){
					System.out.println(r.get(i).get(j));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED :(");
			System.exit(1);
		}
	}
}
