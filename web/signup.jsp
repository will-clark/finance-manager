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

String username = params.getString("username");
String password1 = params.getString("password_1");
String password2 = params.getString("password_2");
String email = params.getString("email");

List<String> errors = new ArrayList<String>(0);

if (params.isPost()) {
	
	if (!RecaptchaUtil.hasValidSolution(request)) {
		errors.add(dialog.get("recaptcha.invalid"));
	}

	EntityManager em = null;
	
	try {
		em = ServiceUtil.getEntityManager();
		UserService svc = new UserService(em);
				
		if(username == null) {
			errors.add(dialog.get("user.username.required"));
		}
		else {
			if (!username.matches("^[a-zA-Z0-9]*$")) errors.add(dialog.get("user.username.alphanum"));
			if (username.length() < 4 || username.length() > 255) errors.add(dialog.get("user.username.length"));			
			if (svc.find(username) != null) errors.add(dialog.get("user.username.exists"));
		}
				
		if(password1 == null) {
			errors.add(dialog.get("user.password.required"));
		}
		else {
			if (password1.length() < 4 || password1.length() > 255) errors.add(dialog.get("user.password.length"));
		}
				
		if(password2 == null || !password1.equals(password2)) {
			errors.add(dialog.get("user.password.mismatch"));
		}
		
		if(email == null) {
			errors.add(dialog.get("user.email.required"));
		}
		else {
			email = email.toLowerCase();
			if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) errors.add(dialog.get("user.email.invalid"));
			if (svc.findByEmail(email) != null) errors.add(dialog.get("user.email.exists"));
		}
		
		if (errors.isEmpty()) {
			User user = new User();
			user.setActive(true);
			user.setEmail(email);
			user.setPassword(password1);
			user.setUsername(username);
			
	        EntityTransaction transaction = em.getTransaction();
	        transaction.begin();	        
			svc.create(user);			
	        transaction.commit();
	        
	        EmailUtil.send(email, dialog.get("user.welcome.email.subject"), dialog.getSubVars("user.welcome.email.body", "username", user.getUsername()));
	        	        
			session.setAttribute("user", user);
			%>
			<jsp:include page="/WEB-INF/views/welcome.jsp" />
			<%
			return;
		}	
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
}

request.setAttribute("username", username);
request.setAttribute("email", email);
request.setAttribute("errors", errors);
request.setAttribute("recaptcha", RecaptchaUtil.generateWidget());
request.setAttribute("pageTitle", dialog.get("user.signup.title"));

%>
<jsp:include page="/WEB-INF/views/signup.jsp" />