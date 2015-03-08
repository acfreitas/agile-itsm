package br.com.centralit.citajax.html;

public class HTMLTextBox extends HTMLElement {

	public HTMLTextBox(String idParm, DocumentHTML documentParm) {
		super(idParm, documentParm);
	}
	public String getType() {
		return TEXTBOX;
	}
	public void select(){
		setCommandExecute("document.getElementById('" + this.getId() + "').select()");
	}
}
