package org.willclark.finance;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.willclark.finance.models.User;

public class Authentication implements Filter {

	private FilterConfig filterConfig = null;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;		
		HttpServletResponse httpResponse = (HttpServletResponse) response;		
		HttpSession session = httpRequest.getSession();

		String loginJsp = httpRequest.getContextPath() + "/login.jsp";

		String requestedURI = httpRequest.getRequestURI();
		if (httpRequest.getQueryString() != null) {
			requestedURI += "?" + httpRequest.getQueryString();
		}		
		session.setAttribute("requestedURI", requestedURI);
		
		User user = (User) session.getAttribute("user");		
		if (user == null && !httpRequest.getRequestURI().equals(loginJsp)) {
			httpResponse.sendRedirect(loginJsp);
			return;
		}
		
		chain.doFilter(httpRequest, httpResponse);
		return;		
	}
	
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}
	
	public void destroy() {
		this.filterConfig = null;	
	}
}