import java.sql.*;

public class ucEventsJDBC{
	public static void main(String[] args){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}

		catch{
   			System.out.println("Where is your mySQL JDBC Driver?");
    			e.printStackTrace();
    			return;
		}

	Connection conn = null;

// try getting connection using try/catches
// then try queries, executeUpdates etc
	}


}
