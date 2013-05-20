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

String email = params.getString("email");

List<String> errors = new ArrayList<String>(0);

if (params.isPost()) {

	if (!RecaptchaUtil.hasValidSolution(request)) {
		errors.add(dialog.get("recaptcha.invalid"));
	}
	
	if(email == null) {
		errors.add(dialog.get("email.required"));
	}
	else {
		email = email.toLowerCase();
		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) errors.add(dialog.get("email.invalid"));
	}
	
	EntityManager em = null;	
	
	try {
		em = ServiceUtil.getEntityManager();
		
		if (errors.isEmpty()) {
			if (email != null) {
				UserService svc = new UserService(em);
				User user = svc.findByEmail(email);
				if (user != null) {
					user.initReset();
					
			        EntityTransaction transaction = em.getTransaction();
			        transaction.begin();	        
					svc.update(user);			
			        transaction.commit();
					
			        String resetUrl = WebUtil.baseUrl(request) + "/reset.jsp?token="+user.getResetToken();
			        
					EmailUtil.send(user.getEmail(), dialog.get("token.email.subject"), dialog.getSubVars("token.email.body", "username", user.getUsername(), "resetUrl", resetUrl));
				}
			}
		}
	}
	finally {
		if (em != null) {
			em.close();
		}
	}
}

request.setAttribute("email", email);
request.setAttribute("recaptcha", RecaptchaUtil.generateWidget());
request.setAttribute("pageTitle", dialog.get("reset.title"));

%>
<jsp:include page="/WEB-INF/views/forgot.jsp" />