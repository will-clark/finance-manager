<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="../header.jsp"/>
<div class="subNavigation">
<input type="button" value="Back" onclick="back()"/>
</div>
<c:if test="${not empty errors}">
<h2><finance:dialog name="errors.heading"/></h2>
<ul>
	<c:forEach var="each" items="${errors}">
		<li>${each}</li>
	</c:forEach>
</ul>
</c:if>
<form method="POST" action="accounts/${action}.jsp">
	<input type="hidden" name="id" value="${account.id}" />
	<label><finance:dialog name="account.name.label"/>*</label>
	<input type="text" name="name" value="${account.name}" />
	<br/>
	<label><finance:dialog name="account.number.label"/></label>
	<input type="text" name="number" value="${account.number}" />
	<br/>
	<label><finance:dialog name="account.balance.label"/></label>
	<c:choose>
	<c:when test="${action == 'new'}">
	<input type="text" name="balance" value="${account.balance}" />
	</c:when>
	<c:otherwise>
	<finance:money amount="${account.balance}" />
	</c:otherwise>
	</c:choose>
	<br/>
	<label><finance:dialog name="account.notes.label"/></label>
	<textarea name="notes"></textarea>
	<br/>
	<input type="submit" value="<finance:dialog name="account.new.submit"/>" />
</form>
<jsp:include page="../footer.jsp"/>