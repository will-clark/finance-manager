<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/finance" prefix="finance"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<finance:dialog name="user.signup.message" />
<input type="button" value="<finance:dialog name="continue.button"/>" onclick="redirect('/index.jsp')"/>
<jsp:include page="/WEB-INF/views/footer.jsp" />