package br.com.centralit.citajax.html;

public class HTMLJanelaPopup extends HTMLElement{

	public HTMLJanelaPopup(String idParm, DocumentHTML documentParm) {
		super(idParm, documentParm);
	}
	public String getType() {
		return JANELAPOPUP;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
		
		if (this.visible){
			show();
		}else{
			hide();
		}
	}	
	public void hide() {
		setCommandExecute(this.id + ".hide()");
	}
	public void show() {
		setCommandExecute(this.id + ".show()");
	}
	public void showInYPosition(int yPos) {
		setCommandExecute(this.id + ".showInYPosition({top:" + yPos + "})");
	}	
}
