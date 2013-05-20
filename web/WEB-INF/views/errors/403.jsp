<%@ page
	isErrorPage="true"
%>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<center>
<finance:dialog name="error.403"/>
<br/>
<a href="javascript:history.go(-1)"><finance:dialog name="back.button"/></a>
</center>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
