package br.com.centralit.citajax.html;

import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citajax.util.JavaScriptUtil;

public class HTMLTextArea extends HTMLElement {
	public HTMLTextArea(String idParm, DocumentHTML documentParm) {
		super(idParm, documentParm);
	}
	public String getType() {
		return TEXTAREA;
	}
	public void select(){
		setCommandExecute("document.getElementById('" + this.getId() + "').select()");
	}
	public void setValue(String value) {
		this.value = value;
		setCommandExecute("HTMLUtils.setValue('" + this.id + "',ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(JavaScriptUtil.escapeJavaScript(value))) + "')))");
	}	
}
