package org.willclark.finance.utils;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class ParamUtil {

	private HttpServletRequest request;
	
	public ParamUtil(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getString(String name) {
		String str = request.getParameter(name);
		if (str == null || str.equals("")) return null;
		return WebUtil.sanitize(str);
	}
	
	public long getLong(String name) {
		long temp = -1;		
		String str = request.getParameter(name);
		if (str == null || str.equals("")) return temp;
		try {
			temp = Long.parseLong(WebUtil.sanitize(str));
		}
		catch (NumberFormatException e) {
			// do nothing, don't care
		}
		return temp;
	}
	
	public BigDecimal getBigDecimal(String name) {
		BigDecimal temp = new BigDecimal("-1.00");	
		String str = request.getParameter(name);
		if (str == null || str.equals("")) return temp;
		try {
			temp = new BigDecimal(WebUtil.sanitize(str));
		}
		catch (NumberFormatException e) {
			// do nothing, don't care
		}
		return temp;
	}
	
	public Date getDateTime(String name) {
		String str = request.getParameter(name);
		if (str == null || str.equals("")) return null;
		return DateUtil.parse(str);
	}
	
	public boolean isGet() {
		return request.getMethod().equalsIgnoreCase("get");
	}
	
	public boolean isPost() {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
}
