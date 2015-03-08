package br.com.citframework.tld;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.com.citframework.util.Label;

public class PrintLabel extends TagSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2748514436529792606L;
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int doStartTag() throws JspException {
		
		String valor =  Label.getValue(key);
		if(valor==null || valor.trim().length()==0){
			InputStream is = this.pageContext.getServletContext().getResourceAsStream("/WEB-INF/classes/" + Label.fileName);
			
			Label.setProp(is);
			valor =  Label.getValue(key);
			
			if(valor==null || valor.trim().length()==0){
				throw new JspException("Não foi encontrada a chave "+key+" no arquivo Label.properties" );
			}
		}
		
		try {
			pageContext.getOut().print(valor.trim());
		} catch (IOException e) {
             throw new JspException(e);
		}
		return SKIP_BODY;
	}
	
	
	
	
	

}
