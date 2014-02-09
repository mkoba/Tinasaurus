import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;


public class db_access {
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
		String DNS = "http://ec2-54-200-149-123.us-west-2.compute.amazonaws.com/";
		String myDBname = "test";
		String MYSQLUSER = "frank.m.chao@jacobs.ucsd.edu";
		String MYSQLPW = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class.forName");
			e.printStackTrace();
			System.exit(1);
		}
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:mysql://ec2-54-200-149-123.us-west-2.compute.amazonaws.com:3306/test?user=ec2-user&password=root");
		} catch (SQLException e){
			System.out.println("Driver Manager failed");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("HI :)");
	}
}