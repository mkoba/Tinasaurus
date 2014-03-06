<%@ page import="database.*, java.util.*, java.sql.*, com.amazonaws.util.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="application/json; charset=US-ASCII">
<title>Get Query Results</title>
</head>
<body>
	<%
		String method = request.getParameter("method");
		String param = request.getParameter("param");
		String user = request.getParameter("user");
		System.out.println("method: " + method);
		database.DB_Access db = new database.DB_Access();
		JSONObject result;
		if(method.equals("getUserInformation")){%>
			<%=db.getUserInformation(param)%>
		<%
		}
		else if(method.equals("getUserInterests")){%>
			<%=db.getUserInterests(param)%>
		<%
		}
		else if(method.equals("getAllEvents")){%>
			<%=db.getAllEvents(param)%>
		<%
		}
		else if(method.equals("getEventsInCategory")){%>
			<%=db.getEventsInCategory(param, user)%>
		<%
		}
		else if(method.equals("getEventsFromUserInterests")){%>
			<%=db.getEventsFromUserInterests(param)%>
		<%
		}
		else if (method.equals("getEventsUserAttending")){%>
			<%=db.getEventsUserAttending(param)%>
		<%
		}
		else if (method.equals("getEventsUserHosting")){%>
			<%=db.getEventsUserHosting(param)%>
		<%
		}
		else if (method.equals("getAttendees")){%>
			<!--  db.getAttendees(param)-->
		<%
		}
		else if (method.equals("getEventInformation")){%>
			<%=db.getEventInformation(param)%>
		<%
		}
	%>
</body>
</html>