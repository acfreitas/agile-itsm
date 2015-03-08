package br.com.centralit.citajax.html;

public class HTMLCheckbox extends HTMLElement {
	private boolean checked;
	
	public HTMLCheckbox(String idParm, DocumentHTML documentParm){
		super(idParm, documentParm);
	}
	public HTMLCheckbox(String idParm, DocumentHTML documentParm, boolean checkedParm){
		super(idParm, documentParm);
		this.setChecked(checkedParm);
	}
	public String getType() {
		return CHECKBOX;
	}
	public boolean getChecked(){
		return checked;
	}
	public void setChecked(boolean checkedParm){
		this.checked = checkedParm;
		setCommandExecute("document.getElementById('" + this.getId() + "').checked = " + (checkedParm ? "true" : "false"));
	}	
	public void setValue(String value) {
		this.value = value;
		setCommandExecute("HTMLUtils.setValue('" + this.id + "','" + value + "')");
	}
	public void setValue(String[] value) {
		String aux = "";
		for(int i = 0; i < value.length; i++){
			if (aux.equalsIgnoreCase("")){
				aux = "[";
			}else{
				aux += ",";
			}
			aux += "'" + value[i] + "'"; 
		}
		aux += "]";
		setCommandExecute("HTMLUtils.setValueCheckBox('" + this.id + "'," + aux + ")");
	}	
}
