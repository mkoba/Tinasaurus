<%@ page import="database.*, java.util.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Query Results</title>
</head>
<body>
	<%
		String method = request.getParameter("method");
		String param = request.getParameter("param");
		List<String> parameters = new ArrayList<String>();
		if(param != null){
			parameters.add(param);
		}
		System.out.println("method: " + method);
		System.out.println("param: " + parameters);
		database.DB_Access db = new database.DB_Access();
		List<String> result;
		List<List<String>> d_result;
		if(method.equals("getUserInformation")){
			result = db.getUserInformation(parameters.get(0));
			if(result.size() > 0){
				%>
				{ fname: <%=result.get(0)%>,
				  lname: <%=result.get(1) %>,
				  ucsd_email: <%=result.get(2) %>,
				  interest: [
				  				<%
				  				for(int i = 3; i < result.size()-1; i++){
				  					%>
				  					<%=result.get(i)%>,
				  					<%
				  				}
				  				%>
				  				<%=result.get(result.size()-1) %>
				  			]
				}
				<%
			}
		}
		else if(method.equals("getUserInterests")){
			result = db.getUserInterests(parameters.get(0));
			if(result.size() > 0){
				%>
				{ interest: [
				  				<%
				  				for(int i = 0; i < result.size()-1; i++){
				  					%>
				  					<%=result.get(i)%>,
				  					<%
				  				}
				  				%>
				  				<%=result.get(result.size()-1) %>
				  			]
				}
				<%
			}
		}
		else if(method.equals("getAllEvents") || method.equals("getEventsInCategory") 
			 	|| method.equals("getEventsFromUserInterests") || method.equals("getEventsUserAttending")
			 	|| method.equals("getEventsUserHosting")){
			if(method.equals("getAllEvents")){
				d_result = db.getAllEvents();
			}
			else if(method.equals("getEventsInCategory")){
				d_result = db.getEventsInCategory(parameters.get(0));
			}
			else if(method.equals("getEventsFromUserInterests")){
				d_result = db.getEventsFromUserInterests(parameters.get(0));
			}
			else if (method.equals("getEventsUserAttending")){
				d_result = db.getEventsUserAttending(parameters.get(0));
			}
			else if (method.equals("getEventsUserHosting")){
				d_result = db.getEventsUserHosting(parameters.get(0));
			}
			else{
				d_result = new ArrayList<List<String>>();
			}
			if(d_result.size() > 0){
				%>
				{ 
					<%
					for(int i = 0; i < d_result.size()-1; i++){
						result = d_result.get(i);
						%>
						<%=result.get(0) %>:{
							name: <%=result.get(0) %>,
							location: <%=result.get(1) %>,
							hour: <%=result.get(2) %>,
							min:<%=result.get(3) %>,
							month: <%=result.get(4) %>,
							date: <%=result.get(5) %>,
							year: <%=result.get(6) %>,
							description: <%=result.get(7) %>
						},
					<%}
					result = d_result.get(d_result.size()-1);
					%>
						<%=result.get(0) %>:{
							name: <%=result.get(0) %>,
							location: <%=result.get(1) %>,
							hour: <%=result.get(2) %>,
							min:<%=result.get(3) %>,
							month: <%=result.get(4) %>,
							date: <%=result.get(5) %>,
							year: <%=result.get(6) %>,
							description: <%=result.get(7) %>
						}
					<%
				  %>
				}
				<%
			}
		}
		else if (method.equals("getAttendees")){
			d_result = db.getAttendees(parameters.get(0));
			if (d_result.size() > 0){
				%>
				{
					<%
					for (int i = 0; i < d_result.size()-1; i++){
						result = d_result.get(i);
						%>
							<%=result.get(0) %> <%=result.get(1) %>,
						<%
					}
					result = d_result.get(d_result.size()-1);
					%>
						<%=result.get(0) %> <%=result.get(1) %>
				}
				<%
			}
		}
		else if (method.equals("getEventInformation")){
			result = db.getEventInformation(parameters.get(0));
			if (result.size() > 0){
				%>
					{
						name: <%=result.get(0) %>,
						location: <%=result.get(1) %>,
						hour: <%=result.get(2) %>,
						min: <%=result.get(3) %>,
						month: <%=result.get(4) %>,
						date: <%=result.get(5) %>,
						year: <%=result.get(6) %>,
						description: <%=result.get(7) %>,
						host: <%=result.get(8) %> <%=result.get(9) %>,
						attendees: [
							<% for (int i = 10; i < result.size() - 2; i+=2){
								%>
									<%=result.get(i) %> <%=result.get(i+1) %>,
								<%
							}
							%>
							<%=result.get(result.size()-2) %> <%=result.get(result.size()-1) %>
						]
					}
				<%
			}
		}
	%>
</body>
</html>