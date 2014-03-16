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
		System.out.println("method: " + method);
		database.DB_Access db = new database.DB_Access();
		JSONObject result;
		if(method.equals("updateEvents")){
			String location = request.getParameter("location");
			int hour = Integer.parseInt(request.getParameter("hour"));
			int min = Integer.parseInt(request.getParameter("min"));
			String[] interests = request.getParameterValues("interests");
			boolean pm = Boolean.valueOf(request.getParameter("pm"));
			int month = Integer.parseInt(request.getParameter("month"));
			int day = Integer.parseInt(request.getParameter("day"));
			int year = Integer.parseInt(request.getParameter("year"));
			String description = request.getParameter("description");
			String name = request.getParameter("name");
			String ucsd_email = request.getParameter("ucsd_email");
			try{
				db.updateEvents(location, hour, min, interests, pm, month, day, year, description, name, ucsd_email);
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
		else if(method.equals("updateUser")){
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String ucsd_email = request.getParameter("ucsd_email");
			String[] interests = request.getParameterValues("interests");
			try{
				db.updateUser(fname, lname, ucsd_email, interests);
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