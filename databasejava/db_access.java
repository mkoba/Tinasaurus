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
	
	public int insertUser(String fname, String lname, String email, String[] interests) throws SQLException{
		fname = escapeAp(fname);
		lname = escapeAp(lname);
		email = escapeAp(email);

		p_stmt = connection.prepareStatement("INSERT INTO user_information (fname, lname, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		p_stmt.setString(1, fname);
		p_stmt.setString(2, lname);
		p_stmt.setString(3, email);
		p_stmt.executeUpdate();
		int interestid = 0;
		int userid = 0;
		rs = stmt.executeQuery("SELECT id FROM user_information WHERE fname = " + fname + " AND lname = " + lname + " AND email = " + email);
		while (rs.next()){
			userid = rs.getInt(1);
		}
		for(int i = 0; i < interests.length; i++){
			interests[i] = escapeAp(interests[i]);
			rs = stmt.executeQuery("SELECT id FROM interests WHERE name = " + interests[i]);
			while(rs.next()){
				interestid = rs.getInt(1);
			}
			p_stmt = connection.prepareStatement("INSERT INTO user_interests (userid, interestid) VALUES (?, ?)");
			p_stmt.setInt(1, userid);
			p_stmt.setInt(2, interestid);
			p_stmt.executeUpdate();
		}
		return userid;
	}

	public void insertInterest(String interest) throws SQLException{
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
	// hostid --> given
	public int insertEvent(String title, String location, int hour, int minute, boolean pm, 
						   String[] interests, int month, int day, int year, String description, 
						   boolean public_flag, int hostid) throws SQLException{
		int eventid = 0;
		
		if (pm){
			hour += 12;
		}
		
		title = escapeAp(title);
		description = escapeAp(description);
		
		p_stmt = connection.prepareStatement("INSERT INTO events (name, location, hour, min, month, date, year, description, hostid, public) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		p_stmt.setString(1, title);
		p_stmt.setString(2, location);
		p_stmt.setInt(3, hour);
		p_stmt.setInt(4, minute);
		p_stmt.setInt(5, month);
		p_stmt.setInt(6, day);
		p_stmt.setInt(7, year);
		p_stmt.setString(8, description);
		p_stmt.setInt(9, hostid);
		p_stmt.setBoolean(10, public_flag);
		p_stmt.executeUpdate();
		
		
		rs = stmt.executeQuery("SELECT id FROM events WHERE name = '" + title + "' AND location = '" + location + "' AND hour = " + hour + " AND min = " + minute
								+ " AND month = " + month + " AND date = " + day + " AND year = " + year + " AND description = '" + description 
								+ "' AND hostid = " + hostid);
		while(rs.next()){
			eventid = rs.getInt(1);
			System.out.println("eventid: " + eventid);
		}
		
		System.out.println("eventid: " + eventid);
		
		int interestid = 0;
		for(int i = 0; i < interests.length; i++){
			interests[i] = escapeAp(interests[i]);
			rs = stmt.executeQuery("SELECT id FROM interests WHERE name = '" + interests[i] + "'");
			while(rs.next()){
				interestid = rs.getInt(1);
			}
			p_stmt = connection.prepareStatement("INSERT INTO event_category (eventid, interestid) VALUES (?, ?)");
			p_stmt.setInt(1, eventid);
			p_stmt.setInt(2, interestid);
			p_stmt.executeUpdate();
		}
		
		return eventid;
	}
	
	public void insertAttendee(int userid, int eventid) throws SQLException{
		p_stmt = connection.prepareStatement("INSERT INTO attendees (eventid, attendeeid) VALUES (?, ?)");
		p_stmt.setInt(1, eventid);
		p_stmt.setInt(2, userid);
		p_stmt.executeUpdate();
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
		/*
		try {
			db.insertEvent("Dinner with Judy", "Bistro", 6, 30, true, event_category, 2, 15, 2014, "I want to eat dinner at the bistro! Let's eat together :)", false, 3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED :(");
			System.exit(1);
		}*/
	}
}