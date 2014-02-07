import java.io.File;
import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;


public class db_access {
	public static void main(String [] args) throws IOException{
		AWSCredentials credentials = 
				new PropertiesCredentials(new File("../AwsCredentials.properties"));
		AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
		
	}
}
