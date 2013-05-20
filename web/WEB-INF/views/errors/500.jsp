<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<%@ page isErrorPage="true"
	language="java"
    import="java.io.Writer"
    import="java.io.PrintWriter"
    import="java.io.StringWriter"
%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<%
String url = (String) request.getAttribute("javax.servlet.error.request_uri");
String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");

String stackTrace = null;
if (exception != null) {
	Writer writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter(writer);
	exception.printStackTrace(printWriter);
	stackTrace = writer.toString();
}
%>
<center><finance:dialog name="error.500"/></center>
<br/>
<font color="white">
<p><% if (exception != null) { exception.printStackTrace(new java.io.PrintWriter(out)); } %></p>
</font>
<jsp:include page="/WEB-INF/views/footer.jsp" />