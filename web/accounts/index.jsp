<%@ page language="java"
    import="org.willclark.finance.models.User"
    import="org.willclark.finance.models.Account"
    import="org.willclark.finance.services.AccountService"
    import="org.willclark.finance.utils.*"
    import="javax.persistence.EntityManager"
    import="java.util.*"
%>
<%

User user = (User) session.getAttribute("user");
if (user == null) {
	response.setStatus(403);
}

EntityManager em = null;

try {
	em = ServiceUtil.getEntityManager();

	AccountService svc = new AccountService(em);
	List<Account> accounts = svc.findAll(user);
	request.setAttribute("accounts", accounts);
	
}
finally {
	if (em != null) {
		em.close();
	}
}

%>
<jsp:include page="/WEB-INF/views/accounts/list.jsp" />