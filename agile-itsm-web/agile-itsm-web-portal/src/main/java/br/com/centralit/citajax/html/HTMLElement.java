package br.com.centralit.citajax.html;

import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citajax.util.JavaScriptUtil;

/**
 * Para utilizar o Framework CITAjax, deve-se acrescentar os seguintes arquivos de Javascript na pagina:
 * 			HTMLUtils.js
 *          ObjectUtils.js
 * @author emauri
 *
 */
public class HTMLElement {
	protected String id;
	protected boolean disabled;
	protected boolean readonly;
	protected boolean visible;
	protected String value;
	protected String innerHTML;
	protected String style;
	protected String className;
	protected DocumentHTML document; //Documento ao qual o elemento esta vinculado.
	
	//Definicao de tipos de objetos
	protected static final String UNDEFINED = "undefined";
	protected static final String TEXTBOX = "text";
	protected static final String TEXTAREA = "textarea";
	protected static final String CHECKBOX = "checkbox";
	protected static final String RADIO = "radio";
	protected static final String TABLE = "table";
	protected static final String SELECT = "select";
	protected static final String TREEVIEW = "treeview";
	protected static final String JANELAPOPUP = "janelapopup";
	
	public HTMLElement(String idParm, DocumentHTML documentParm){
		this.setId(idParm);
		this.setDocument(documentParm);
		documentParm.setElement(idParm, this); //Atribui informacoes dele no document (Se nao existir).
	}
	public String getType(){
		return UNDEFINED;
	}
	public void setFocus(){
		setCommandExecute("HTMLUtils.setFocus('" + this.id + "')");
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		setCommandExecute("document.getElementById('" + this.id + "').disabled=" + (disabled ? "true" : "false"));
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInnerHTML() {
		return innerHTML;
	}
	public void setInnerHTML(String innerHTML) {
		this.innerHTML = innerHTML;
		setCommandExecute("document.getElementById('" + this.id + "').innerHTML=ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(JavaScriptUtil.escapeJavaScript(innerHTML))) + "'))");
	}
	public boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
		setCommandExecute("document.getElementById('" + this.id + "').readOnly=" + (readonly ? "true" : "false"));
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
		setCommandExecute("HTMLUtils.setValue('" + this.id + "','" + value + "')");
	}
	public DocumentHTML getDocument() {
		return document;
	}
	public void setDocument(DocumentHTML document) {
		this.document = document;
	}
	
	protected void setCommandExecute(String comand){
		this.document.getComandsExecute().add(comand);
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
		setCommandExecute("document.getElementById('" + this.id + "').style='" + style + "'");
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
		setCommandExecute("document.getElementById('" + this.id + "').className='" + className + "'");
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
		setCommandExecute("HTMLUtils.setVisible('" + this.id + "', " + (visible ? "true" : "false") + ")");
	}
}
