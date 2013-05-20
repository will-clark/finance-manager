<%@ page language="java"
    import="org.willclark.finance.models.User"
    import="org.willclark.finance.models.Account"
    import="org.willclark.finance.services.AccountService"
    import="org.willclark.finance.models.Transaction"    
    import="org.willclark.finance.services.TransactionService"
    import="org.willclark.finance.utils.*"
    import="javax.persistence.EntityManager"
    import="java.util.*"
%>
<%

User user = (User) session.getAttribute("user");
if (user == null) {
	response.setStatus(403);
}

ParamUtil params = new ParamUtil(request);

long id = params.getLong("id");
if (id == -1) {
	response.setStatus(404);
}

EntityManager em = null;

try {
	em = ServiceUtil.getEntityManager();

	AccountService accountSvc = new AccountService(em);
	Account account = accountSvc.find(user, id);
	if (account == null) {
		response.setStatus(404);
	}
	request.setAttribute("account", account);
	
	TransactionService txnSvc = new TransactionService(em);
	List<Transaction> transactions = txnSvc.findAll(user, account);
	request.setAttribute("transactions", transactions);	
}
finally {
	if (em != null) {
		em.close();
	}
}

%>
<jsp:include page="/WEB-INF/views/transactions/list.jsp" />