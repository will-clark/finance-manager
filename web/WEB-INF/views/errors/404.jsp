<%@ page isErrorPage="true" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<center>
<finance:dialog name="error.404"/>
<br/>
<%= (request.getHeader("Referer") != null) ? "Referer: " + request.getHeader("Referer") : "" %><br>
<%= (exception != null) ? "Resource: " + exception.getMessage() : "" %>
<br/>
<a href="javascript:history.go(-1)">Go back</a>
</center>
<jsp:include page="/WEB-INF/views/footer.jsp"/>