package br.com.centralit.citajax.html;

public class HTMLRadio extends HTMLElement {
	private boolean checked;
	
	public HTMLRadio(String idParm, DocumentHTML documentParm){
		super(idParm, documentParm);
	}
	public HTMLRadio(String idParm, DocumentHTML documentParm, boolean checkedParm){
		super(idParm, documentParm);
		this.setChecked(checkedParm);
	}
	public String getType() {
		return RADIO;
	}
	public boolean getChecked(){
		return checked;
	}
	public void setChecked(boolean checkedParm){
		this.checked = checkedParm;
		setCommandExecute("document.getElementById('" + this.getId() + "').checked = " + (checkedParm ? "true" : "false"));
	}
}
