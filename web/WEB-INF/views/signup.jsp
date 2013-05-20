<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<c:if test="${not empty errors}">
<h2><finance:dialog name="errors.heading"/></h2>
<ul>
	<c:forEach var="each" items="${errors}">
		<li>${each}</li>
	</c:forEach>
</ul>
</c:if>
<form method="POST" action="signup.jsp">
	<fieldset>
	<label><finance:dialog name="user.username.label"/></label>
	<input type="text" name="username" value="${username}" />
	<br/>
	<label><finance:dialog name="user.password.label"/></label>
	<input type="password" name="password_1" value="" />
	<br/>
	<label><finance:dialog name="user.password2.label"/></label>
	<input type="password" name="password_2" value="" />
	<br/>
	<label><finance:dialog name="user.email.label"/></label>
	<input type="email" name="email" value="${email}" />
	<br/>
	${recaptcha}
	<input type="submit" value="<finance:dialog name="user.signup.submit"/>" />
	</fieldset>
</form>
<jsp:include page="/WEB-INF/views/footer.jsp" />