<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="../header.jsp"/>
<script>
$(document).ready(function() {
	$("button#newAccount").click(function(event) {
		redirect("accounts/new.jsp");
	});
	$("button#newTxn").click(function(event) {
		redirect("transactions/new.jsp");
	});

});
</script>
<button id="newAccount">New Account</button>
<button id="newTxn">New Transaction</button>
<table>
<tr>
	<td>Account</td>
	<td>Balance</td>
</tr>
<c:forEach var="account" items="${accounts}">
<tr>
	<td><a href="transactions/index.jsp?id=${account.id}">${account.name}</a></td>
	<td><finance:money amount="${account.balance}"/></td>
</tr>
</c:forEach>
</table>
<jsp:include page="../footer.jsp"/>