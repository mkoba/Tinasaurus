
//Two Deletes: Delete Events and Delete interests



public void deleteUserInterests(int userID, int interestID) throws SQLException{
	p_stmt = connection.preparedStatement("DELETE " +
					      "FROM user_interests e " +
					      "WHERE ? = e.userid AND ? = e.interestid;");
	p_stmt.setString(1, userID);
	p_stmt.setString(2, interestID);
	p_stmt.executeQuery();
}

public void deleteEvent(int eventID, int userID) throws SQLException{
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
