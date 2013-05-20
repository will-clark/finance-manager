package org.willclark.finance.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.willclark.finance.Dialog;

public class DialogTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
		
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		try {
			out.print(Dialog.getInstance().get(name));
		}
		catch(IOException e) {
			// do nothing?
		}
		
        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
	
}