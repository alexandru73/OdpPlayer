package com.school.tags.templates;

import java.util.Hashtable;
import java.util.Stack;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class InsertTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String template;
	private Stack<Hashtable<?, ?>> stack;

	public void setTemplate(String template) {
		this.template = template;
	}

	public int doStartTag() throws JspException {
		stack = getStack();
		stack.push(new Hashtable<String, PageParameter>());
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.include(template);
		} catch (Exception ex) { // IOException or ServletException
			throw new JspException(ex.getMessage());
		}
		stack.pop();
		return EVAL_PAGE;
	}

	public void release() {
		template = null;
		stack = null;
	}

	public Stack<Hashtable<?, ?>> getStack() {
		@SuppressWarnings("unchecked")
		Stack<Hashtable<?, ?>> s = (Stack<Hashtable<?, ?>>) pageContext.getAttribute("template-stack",
				PageContext.REQUEST_SCOPE);
		if (s == null) {
			s = new Stack<>();
			pageContext.setAttribute("template-stack", s, PageContext.REQUEST_SCOPE);
		}
		return s;
	}
}
