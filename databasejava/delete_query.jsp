<%@ page import="database.*, java.util.*, java.sql.*, com.amazonaws.util.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Get Query Results</title>
</head>
<body>
	<%
		String method = request.getParameter("method");
			database.DB_Access db = new database.DB_Access();
			JSONObject result;
			if(method.equals("deleteUserInterests")){
				String user = request.getParameter("user"); 
				String interest = request.getParameter("interest");				
			
			
			try{
				db.deleteUserInterests(user, interest);
				%>
				Success
				<% 
			}catch(Exception e){
				%>
				Failed
				<% e.printStackTrace();
			}
		}
			
			else if (method.equals("deleteEvent")){
				String event = request.getParameter("event");
				try{
					db.deleteEvent(event);
					%>
					Success
					<% 
				}catch(Exception e){
					%>
					Failed
					<% e.printStackTrace(); 
				}
			}
			
			else if(method.equals("deleteAttendee")){
				String event = request.getParameter("event");
				String attendee = request.getParameter("attendee");
				
				try{
					db.deleteAttendee(event, attendee);
					%>
					Success
					<% 
				}catch(Exception e){
					%> 
					Failed
					<% e.printStackTrace();
				}
			}
			
			%>
	
</body>
</html>
