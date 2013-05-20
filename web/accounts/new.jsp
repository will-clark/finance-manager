<%@ page language="java"
    import="org.willclark.finance.Dialog"
    import="org.willclark.finance.Flash"
    import="org.willclark.finance.models.User"
    import="org.willclark.finance.models.Account"
    import="org.willclark.finance.services.AccountService"
    import="org.willclark.finance.utils.*"
    import="javax.persistence.EntityManager"
    import="javax.persistence.EntityTransaction"
    import="java.math.BigDecimal"
    import="java.util.*"
%>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	response.setStatus(403);
}

Dialog dialog = Dialog.getInstance();

ParamUtil params = new ParamUtil(request);

String name = params.getString("name");
String notes = params.getString("notes");
String number = params.getString("number");

List<String> errors = new ArrayList<String>(0);

Account account = new Account();
account.setBalance(new BigDecimal("0.00"));

if (params.isPost()) {

	account.setActive(true);	
	account.setName(name);
	account.setNumber(number);
	account.setNotes(notes);

	if(name == null) {
		errors.add(dialog.get("account.name.required"));
	}
	
	BigDecimal balance = params.getBigDecimal("balance");
	if (balance.longValue() < 0) errors.add(dialog.get("account.balance.invalid"));
	account.setBalance(balance);		
	
	if (errors.isEmpty()) {
		EntityManager em = null;
		
		try {
			em = ServiceUtil.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
	        transaction.begin();	        
			new AccountService(em).create(user, account);
	        transaction.commit();
		}
		finally {
			if (em != null) {
				try {
					em.close();
				}
				catch (Exception e) {
					// do nothing
				}
			}
		}
		
		new Flash(session).message(Dialog.getInstance().get("account.new.success"));
		response.sendRedirect("index.jsp");
		return;
	}
}

request.setAttribute("errors", errors);
request.setAttribute("account", account);
request.setAttribute("action", "new");
request.setAttribute("pageTitle", dialog.get("account.new.title"));

%>
<jsp:include page="/WEB-INF/views/accounts/form.jsp" />