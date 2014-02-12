import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public void updateEvents(int id, String title, String location, int hour, int minute, boolean pm,
            int month, int day, int year, String description) throws SQLException{
		location=escapeAp(location);
		p_stmt = connection.prepareStatement("UPDATE events SET title='"+title+"' AND location='"+location+"' AND hour="+hour+" AND minute="+minute
											+ "AND pm="+pm+" AND month="+month+" AND day="+day+" AND year="+year+" AND description='"+description
											+ "' WHERE id="+id);
		p_stmt.executeUpdate();
	}

	public void updateUser(int id, String fname, String lname, String ucsd_email) throws SQLException{
		p_stmt = connection.prepareStatement("UPDATE user_information SET fname='"+fname+"' AND lname='"+lname+"' AND ucsd_email='"+ucsd_email
				+ "' WHERE id="+id);
		p_stmt.executeUpdate();
	}
	
	///////////////////////////////// DELETE /////////////////////////////////
	
	public void deleteUserInterests(String userID, String interestID) throws SQLException{
	userID = escapeAp(userID);
	interestID = escapeAp(interestID);
	p_stmt = connection.preparedStatement("DELETE " +
					      "FROM user_interests e " +
					      "WHERE ? = e.userid AND ? = e.interestid;");
	p_stmt.setString(1, userID);
	p_stmt.setString(2, interestID);
	p_stmt.executeQuery();
}

	public void deleteEvent(String eventID, String userID) throws SQLException{
	userID = escapeAp(eventID);
	userID = escapeAp(userID);
	p_stmt = connection.preparedStatement("DELETE " + 
					      "FROM attendees a, events e " +
					      "WHERE ? = a.eventid AND "
					      "? = e.hostid;");
	p_stmt.setString(1, eventID);
	p_stmt.setString(2, userID);
	p_stmt.executeQuery();

	p_stmt = connection.preparedStatement("DELETE " +
					      "FROM events e " +
					      "WHERE (eventID) = e.id AND " + 
					      "(userID) = e.hostid);");
	p_stmt.setString(1, eventID);
	p_stmt.setString(2, userID);
	p_stmt.executeQuery();
}
	
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
			db.insertUser("Leon", "Cam", "lcam@ucsd.edu", interests);
			db.insertAttendee("mkoba@ucsd.edu", "jclin06@ucsd.edu+Dinner with Judy");
			//db.insertEvent("jclin06@ucsd.edu+Dinner with Judy", "Bistro", 6, 30, true, event_category, 2, 15, 2014, "I want to eat dinner at the bistro! Let's eat together :)", false, "jclin06@ucsd.edu");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED :(");
			System.exit(1);
		}
	}
}
