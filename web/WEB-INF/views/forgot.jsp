<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance" %>
<jsp:include page="/WEB-INF/views/header.jsp" />
<c:choose>
<c:when test="${not empty email}">
<finance:dialog name="reset.init" />
</c:when>
<c:otherwise>
<c:if test="${not empty errors}">
<h2><finance:dialog name="errors.heading"/></h2>
<ul>
	<c:forEach var="each" items="${errors}">
		<li>${each}</li>
	</c:forEach>
</ul>
</c:if>
<form method="POST" action="forgot.jsp">
	<fieldset>
	<label><finance:dialog name="email.label"/></label>
	<input type="text" name="email" value="${email}" />
	<br/>
	${recaptcha}
	<input type="submit" value="<finance:dialog name="reset.submit"/>" />
	</fieldset>
</form>
</c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/views/footer.jsp" />