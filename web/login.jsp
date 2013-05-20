<%@ page language="java"
	import="org.willclark.finance.Dialog"
    import="org.willclark.finance.models.User"
    import="org.willclark.finance.services.UserService"
    import="org.willclark.finance.utils.*"
    import="javax.persistence.EntityManager"
    import="java.util.*"
%>
<%
Dialog dialog = Dialog.getInstance();

ParamUtil params = new ParamUtil(request);

// sessionToken/requestToken is used to determine if session cookies are following us around correctly
String sessionToken = (String) session.getAttribute("sessionToken");
String requestToken = params.getString("requestToken");

if (requestToken == null) {
	// hitting the login page for the first time this session
	String newToken = StringUtil.generatePseudoRandomToken();
	session.setAttribute("sessionToken", newToken);
	response.sendRedirect("login.jsp?requestToken="+newToken);
	return;
}
else if (requestToken != null && !requestToken.equals(sessionToken)) {
	// session cookies aren't available (or something phishy is going on), tell the user to turn them on
	response.sendError(403);
	return;
}

Integer loginAttempts = new Integer(0);
if (session.getAttribute("loginAttempts") != null) {
	loginAttempts = (Integer) session.getAttribute("loginAttempts");
}

if (params.isPost()) {
	
	// keep track of how many times this user has tried to login
	session.setAttribute("loginAttempts", loginAttempts = loginAttempts + 1);
		
	// don't allow more than 10 attempts, they'll have to wait until the session expires before
	// they can attempt again
	if (loginAttempts > 10) {
		response.sendError(403);
		return;		
	}

	String username = params.getString("username");
	String password = params.getString("password");

	EntityManager em = null;
	
	try {
		em = ServiceUtil.getEntityManager();
		UserService svc = new UserService(em);
		User user = null;
		if (username != null && password != null && ((user = svc.find(username)) != null) && user.authenticate(password)) {
			
			// add user to session
			session.setAttribute("user", user);
			
			// reset counter
			session.setAttribute("loginAttempts", 0);
			
			String requestedURI = (String) session.getAttribute("requestedURI");
			if (requestedURI == null) {
				requestedURI = "index.jsp";
			}
			
			response.sendRedirect(requestedURI);
			return;
			
		}
		else {
			response.sendError(401);
			return;
		}
	}
	finally {
		if (em != null) {
			em.close();
		}
	}
}

request.setAttribute("requestToken", requestToken);
request.setAttribute("pageTitle", dialog.get("login.title"));

%>
<jsp:include page="/WEB-INF/views/login.jsp" />