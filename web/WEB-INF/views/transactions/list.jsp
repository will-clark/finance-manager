<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="../header.jsp"/>
<script>
$(document).ready(function() {
	$("button#newTxn").click(function(event) {
		redirect("transactions/new.jsp");
	});
});
</script>
<button id="newTxn">New Transaction</button>
<table>
<tr>
	<td>Date</td>
	<td>Amount</td>
</tr>
<c:forEach var="txn" items="${transactions}">
<tr>
	<td>${txn.date}</td>
	<td><finance:money amount="${txn.amount}"/></td>
</tr>
</c:forEach>
</table>
<jsp:include page="../footer.jsp"/>