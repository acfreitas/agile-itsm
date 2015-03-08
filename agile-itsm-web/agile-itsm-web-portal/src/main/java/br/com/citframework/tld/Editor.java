package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class Editor extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8571821424709002366L;
	private String	property;
	private String	id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public int doStartTag() throws JspException {
		

		try {
			pageContext.getOut().println("<script type='text/javascript'>");
			pageContext.getOut().println("  window.onload = function()");
			pageContext.getOut().println("  {");
			pageContext.getOut().println("     var sBasePath = '../FCKeditor/' ;");
			pageContext.getOut().println("     var oFCKeditor" + getId() + " = new FCKeditor( '" + getProperty() + "' ) ;");
			pageContext.getOut().println("     oFCKeditor" + getId() + ".BasePath	= sBasePath ;");
			pageContext.getOut().println("     oFCKeditor" + getId() + ".ReplaceTextarea() ;");
			pageContext.getOut().println(" }");

			pageContext.getOut().println("</script>");
		} catch (IOException e) {
			
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

}
