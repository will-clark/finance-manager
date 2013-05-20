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
<form method="POST" action="transactions/${action}.jsp">
	<label><finance:dialog name="txn.type.label"/>*</label>
	<select name="type">
		<c:forEach var="type" items="${types}">
		<option value="${type}">${type.display}</option>
		</c:forEach>
	</select>
	<br/>
	<label><finance:dialog name="txn.date.label"/>*</label>	
	<div id="datetimepicker1" class="input-append date">
	   <input data-format="MM/dd/yy hh:mm a" type="datetime" name="date"></input>
	   <span class="add-on">
	     <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
	   </span>
	</div>
	<br/>
	<label><finance:dialog name="txn.account.label"/>*</label>
	<select name="accountId">	
		<option></option>
		<c:forEach var="account" items="${accounts}">
		<option value="${account.id}">${account.name}</option>
		</c:forEach>
	</select>
	<br/>
	<label><finance:dialog name="txn.amount.label"/>*</label>
	<input type="text" name="amount" value="${txn.amount}" />
	<br/>
	<input type="submit" value="<finance:dialog name="txn.new.submit"/>" />
</form>
<script type="text/javascript">
  $(function() {
    $('#datetimepicker1').datetimepicker({
      language: 'pt-BR'
    });
  });
</script>
<jsp:include page="../footer.jsp"/>