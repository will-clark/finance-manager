<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<c:choose>
<c:when test="${invalid}"><finance:dialog name="user.reset.error"/></c:when>
<c:when test="${success}"><finance:dialog name="user.reset.success"/></c:when>
<c:otherwise>
<c:if test="${not empty errors}">
<h2><finance:dialog name="errors.heading"/></h2>
<ul>
	<c:forEach var="each" items="${errors}">
		<li>${each}</li>
	</c:forEach>
</ul>
</c:if>
<form method="POST" action="reset.jsp">
	<input type="hidden" name="token" value="${token}" />
	<fieldset>
	<label><finance:dialog name="user.password.label"/></label>
	<input type="password" name="password_1" value="" />
	<br/>
	<label><finance:dialog name="user.password2.label"/></label>
	<input type="password" name="password_2" value="" />
	<br/>
	${recaptcha}
	<input type="submit" value="<finance:dialog name="user.reset.submit"/>" />
	</fieldset>
</form>
</c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/views/footer.jsp" />