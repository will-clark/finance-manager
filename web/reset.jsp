<%@ page language="java"
    import="org.willclark.finance.Dialog"
    import="org.willclark.finance.models.User"
    import="org.willclark.finance.services.UserService"
    import="org.willclark.finance.utils.*"
    import="javax.persistence.EntityManager"
    import="javax.persistence.EntityTransaction"
    import="java.util.*"
%>
<%

Dialog dialog = Dialog.getInstance();

ParamUtil params = new ParamUtil(request);

String token = params.getString("token");
String password1 = params.getString("password_1");
String password2 = params.getString("password_2");

List<String> errors = new ArrayList<String>(0);

if (params.isPost()) {
	
	if (!RecaptchaUtil.hasValidSolution(request)) {
		errors.add(dialog.get("recaptcha.invalid"));
	}

	if(password1 == null) {
		errors.add(dialog.get("user.password.required"));
	}
	else {
		if (password1.length() < 4 || password1.length() > 255) errors.add(dialog.get("user.password.length"));
	}
			
	if(password2 == null || !password1.equals(password2)) errors.add(dialog.get("user.password.mismatch"));

	if (errors.isEmpty()) {

		EntityManager em = null;
		
		try {
			em = ServiceUtil.getEntityManager();
			UserService svc = new UserService(em);					
			User user = svc.findByResetToken(token);
			
			if (user == null) {
				request.setAttribute("invalid", true);
			}
			else {				
				user.setPassword(password1);
				user.setResetExpiration(null);
				user.setResetToken(null);
				
		        EntityTransaction transaction = em.getTransaction();
		        transaction.begin();	        
				svc.update(user);			
		        transaction.commit();
		        
		        EmailUtil.send(user.getEmail(), dialog.get("user.reset.email.subject"), dialog.getSubVars("user.reset.email.body", "username", user.getUsername()));
		        	        
				session.setAttribute("user", user);			
				request.setAttribute("success", true);
			}		
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}
}

request.setAttribute("errors", errors);
request.setAttribute("token", token);
request.setAttribute("recaptcha", RecaptchaUtil.generateWidget());
request.setAttribute("pageTitle", dialog.get("user.reset.title"));

%>
<jsp:include page="/WEB-INF/views/reset.jsp" />