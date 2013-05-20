package org.willclark.finance;

import javax.servlet.http.HttpSession;

public class Flash {

	private HttpSession session;
	
	public Flash(HttpSession session) {
		this.session = session;		
	}
	
	public void message(String message) {
		session.setAttribute("_flash_message", message);
	}
	
	public String display() {
		String message = (String) session.getAttribute("_flash_message");
		if (message != null) {
			session.setAttribute("_flash_message", null);
			return message;
		}
		return "";
	}
	
}
