<%@ page import="database.*, java.util.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
	<%
		String method = request.getParameter("method");
		String param = request.getParameter("param");
		List<String> parameters = new ArrayList<String>();
		if(param.contains("+")){
			while(param.indexOf("+") != -1){
				parameters.add(param.substring(0, param.indexOf("+")));
				param = param.substring(0, param.indexOf("+")+1);
				System.out.println(param);
			}
		}
		else{
			parameters.add(param);
		}
		System.out.println("method: " + method);
		System.out.println("param: " + parameters.size());
		database.DB_Access db = new database.DB_Access();
		List<String> result;
		if(method.equals("getUserInformation")){
			result = db.getUserInformation(parameters.get(0));
			for(int i = 0; i < result.size(); i++){
			%>
				<%=i %>: <%=result.get(i)%>
			<%
			}
		}
		else if(method.equals("")){
			
		}
	%>
</body>
</html>