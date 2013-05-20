<%@ page language="java"
    import="org.willclark.finance.Flash"
    import="org.willclark.finance.utils.*"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance" %>
<%
request.setAttribute("baseUrl", WebUtil.baseUrl(request));
%>
<!DOCTYPE html>
<head>
<title><finance:dialog name="application.title"/> <c:if test="${not empty pageTitle}"> - ${pageTitle}</c:if></title>
<base href="${baseUrl}/" />
<script>
var baseUrl = "${baseUrl}/";
</script>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="js/application.js"></script>
<script src="js/bootstrap-datetimepicker.min.js"></script>
<link href="css/application.css" media="screen" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-datepicker.min.css" media="screen" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="navigation">
<c:choose>
	<c:when test="${not empty user}"><finance:dialog name="welcome"/>&nbsp;${user.username} | <a href="logout.jsp"><finance:dialog name="logout"/></a></c:when>
	<c:otherwise><a href="login.jsp"><finance:dialog name="login"/></a></c:otherwise>
</c:choose>
</div>
<hr/>
<div id="flash"><%= new Flash(session).display() %></div>
<div id="body">