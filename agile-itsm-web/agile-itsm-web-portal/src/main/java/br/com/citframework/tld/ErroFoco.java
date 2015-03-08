package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.citframework.util.Constantes;

public class ErroFoco extends BodyTagSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4135496558118418031L;
	private String formName;

	public int doStartTag() throws JspException {
		
		if(pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_DATA"))!=null &&
			pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_DATA")).toString().trim().length()>0){
			try {
				pageContext.getOut().println("<script> document."+getFormName()+"."+pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_DATA"))+".select(); ");
				pageContext.getOut().println("document."+getFormName()+"."+pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_DATA"))+".focus();</script>");
			} catch (IOException e) {
				
				throw new JspException(e);
			}
		}else{
			if(pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_TEXTO"))!=null &&
					pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_TEXTO")).toString().trim().length()>0){
					try {
						pageContext.getOut().println("<script> document."+getFormName()+"."+pageContext.getRequest().getAttribute(Constantes.getValue("FOCO_TEXTO"))+".focus(); </script>");
					} catch (IOException e) {
						
						throw new JspException(e);
					}
				}			
			
			
			
		}
		
		
		return SKIP_BODY;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	
	

}
