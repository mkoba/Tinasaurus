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
		if (method.equals("insertUser")){
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String ucsd_email = request.getParameter("ucsd_email");
			String[] interests = request.getParameterValues("interest");
			System.out.println(interests.length);
			try{
				db.insertUser(fname, lname, ucsd_email, interests);
				%>
				Success
				<%
			} catch(Exception e){
				%>
				Failed
				<%
				e.printStackTrace();
			}
		}
		else if (method.equals("insertEvent")){
			String title = request.getParameter("title");
			String location = request.getParameter("location");
			int hour = Integer.parseInt(request.getParameter("hour"));
			int minute = Integer.parseInt(request.getParameter("minute"));
			boolean pm = request.getParameter("pm").equals("true");
			String[] interests = request.getParameterValues("interest");
			int month = Integer.parseInt(request.getParameter("month"));
			int day = Integer.parseInt(request.getParameter("day"));
			int year = Integer.parseInt(request.getParameter("year"));
			String description = request.getParameter("description");
			boolean public_flag = true;
			String host = request.getParameter("host");
			try{
				db.insertEvent(title, location, hour, minute, pm, interests, month, day, year, description, public_flag, host);
				%>
				Success
				<%
			} catch(Exception e){
				%>
				Failed 
				<%
				e.printStackTrace();
			}
		}
		else if (method.equals("insertAttendee")){
			/*insertAttendee(String user, String event)*/
			String user = request.getParameter("user");
			String event = request.getParameter("event");
			try{
				db.insertAttendee(user, event);
				%>
				Success
				<%
			} catch(Exception e){
				%>
				Failed
				<%
				e.printStackTrace();
			}
		}
	%>
</body>
</html>