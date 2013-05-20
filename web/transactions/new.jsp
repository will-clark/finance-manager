<%@ page language="java"
    import="org.willclark.finance.Dialog"
    import="org.willclark.finance.Flash"
    import="org.willclark.finance.models.User"
    import="org.willclark.finance.models.Transaction"
    import="org.willclark.finance.services.TransactionService"
    import="org.willclark.finance.models.Type"
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

List<String> errors = new ArrayList<String>(0);

List<Account> accounts = new ArrayList<Account>(0);

EntityManager em = null;

try {
	em = ServiceUtil.getEntityManager();
	accounts.addAll(new AccountService(em).findAll(user));
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

Transaction txn = new Transaction();

if (params.isPost()) {

	txn.setActive(true);

	long accountId = params.getLong("accountId");
	
	Account account = null;
	for(Account each : accounts) {
		if (each.getId() == accountId) {
			account = each;
			break;
		}
	}
	
	if (account == null) errors.add(dialog.get("txn.account.invalid"));
	txn.setAccount(account);
	
	Date date = params.getDateTime("date");
	if (date == null) errors.add(dialog.get("txn.date.invalid"));
	txn.setDate(date);
	
	Type type = Type.valueOf(params.getString("type"));
	if (type == null) errors.add(dialog.get("txn.type.invalid"));
	txn.setType(type);	
	
	BigDecimal amount = params.getBigDecimal("amount");
	if (amount.longValue() < 0) errors.add(dialog.get("txn.amount.invalid"));
	txn.setAmount(amount);
	
	if (errors.isEmpty()) {
		try {
			em = ServiceUtil.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
	        transaction.begin();     
			new TransactionService(em).create(user, account, txn);
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
		
		new Flash(session).message(Dialog.getInstance().get("txn.new.success"));
		response.sendRedirect("transactions/index.jsp?="+account.getId());
		return;
	}
}

request.setAttribute("accounts", accounts);
request.setAttribute("types", Type.values());

request.setAttribute("errors", errors);
request.setAttribute("txn", txn);
request.setAttribute("action", "new");
request.setAttribute("pageTitle", dialog.get("txn.new.title"));

%>
<jsp:include page="/WEB-INF/views/transactions/form.jsp" />