<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance" %>
<jsp:include page="/WEB-INF/views/header.jsp" />
<form method="POST" action="login.jsp">
	<input type="hidden" name="requestToken" value="${requestToken}" />
	<fieldset>
	<label><finance:dialog name="user.username.label"/></label>
	<input type="text" name="username" value="" />
	<label><finance:dialog name="user.password.label"/></label>
	<input type="password" name="password" value="" />
	<input type="submit" value="<finance:dialog name="login.submit"/>" />
	</fieldset>
</form>
<a href="signup.jsp"><finance:dialog name="login.new"/></a> | <a href="forgot.jsp"><finance:dialog name="login.forgot"/></a>
<jsp:include page="/WEB-INF/views/footer.jsp" />