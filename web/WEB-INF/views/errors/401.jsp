<%@ page isErrorPage="true" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<center>
<finance:dialog name="error.401"/>
<br/>
<a href="javascript:history.go(-1)">Go back</a> to try again.
</center>
<jsp:include page="/WEB-INF/views/footer.jsp"/>