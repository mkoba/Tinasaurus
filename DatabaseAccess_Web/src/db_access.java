import java.io.File;
import java.io.IOException;

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
		AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
		//Set to be same as Manager(??) thingie
		amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
		
		//Creating security group
		CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest();
		createSecurityGroupRequest.withGroupName("CSE190Group").withDescription("190 Security Group");
		CreateSecurityGroupResult createSecurityGroupResult = amazonEC2Client.createSecurityGroup(createSecurityGroupRequest);
		System.out.println("done");
	}
}