package br.com.centralit.citcorpore.tld;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class UploadControlList extends BodyTagSupport {

	/**
	 * 
	 */

	private String id;
	private String style;
	private String title;
	private String form;
	private String action;
	private String disabled;
	
	public int doStartTag() throws JspException {
		try {
			//String urlIframe = "../../include/vazio.jsp";
			String urlIframe;
			try {
				urlIframe = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + 
						((HttpServletRequest) pageContext.getRequest()).getContextPath() +
						"/pages/refresh" + getId() + "List/refresh" + getId() + "List.load";
			} catch (Exception e1) {
				throw new JspException(e1);
			}
			
			JspWriter out = pageContext.getOut();
			out.println("<div style='border:1px solid black;' id='divUpload_" + getId() + "'>\n");			
			out.println("<div style='display:none;background:#E3F0FD;' id='divMostraResultadoUpload_" + getId() + "'></div>\n");
			out.println("<iframe name='fraUpload_" + getId() + "' id='fraUpload_" + getId() + "' style='" + getStyle() + ";width:100%; border: none;' src='" + urlIframe + "'></iframe>\n");
			out.println("</div>\n");
			
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
