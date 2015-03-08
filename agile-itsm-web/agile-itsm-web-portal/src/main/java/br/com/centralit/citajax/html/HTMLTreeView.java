package br.com.centralit.citajax.html;

import br.com.citframework.util.UtilStrings;
/**
 * Esta classe faz uso do JavaScript: dtree.js
 * @author emauri
 *
 */
public class HTMLTreeView extends HTMLElement{
	private boolean generateHeader = false;
	private boolean generateIcons = false;
	
	private Boolean useIcons = new Boolean(false);
	
	private String iconRoot = "../../imagens/predio.gif";
	private String iconJoinBottom = "../../imagens/joinBottom.gif";
	private String iconMinusBottom = "../../imagens/minusBottom.gif";
	private String iconPlusBottom = "../../imagens/plusBottom.gif";
	private String iconFolderOpen = "../../imagens/folderOpen.gif";
	private String iconJoin = "../../imagens/join.gif";
	private String iconEmpty = "../../imagens/empty.gif";
	
	public HTMLTreeView(String idParm, DocumentHTML documentParm) {
		super(idParm, documentParm);
	}
	
	public String getType() {
		return TREEVIEW;
	}

	public void add(String idNode, String idNodePai, String name, String url, String title, String target, String icon, String iconOpen, Boolean open, String functionOnClick){
		if (!generateHeader){
			genHeader();
			generateHeader = true;
		}
		if (!generateIcons){
			genIcons();
			generateIcons = true;
		}
		setCommandExecute("document.trvV" + this.getId() + ".add(" + idNode + "," + idNodePai + ",'" + name + "','" + UtilStrings.nullToVazio(url) + "','" + UtilStrings.nullToVazio(title) +"','" + UtilStrings.nullToVazio(target) + "','" + UtilStrings.nullToVazio(icon) + "','" + UtilStrings.nullToVazio(iconOpen) + "', " + open.toString() + ", '" + UtilStrings.nullToVazio(functionOnClick) + "')");
	}
	
	public void renderTreeView(){
		if (!generateHeader){
			genHeader();
			generateHeader = true;
		}	
		setCommandExecute("document.getElementById('" + this.getId() + "').innerHTML = " + "document.trvV" + this.getId() + ".toString()");
	}
	
	private void genHeader(){
		setCommandExecute("document.trvV" + this.getId() + " = new dTree('" + "trvV" + this.getId() + "')");
		setCommandExecute("document.trvV" + this.getId() + ".config.useIcons = " + this.getUseIcons().toString());
	}
	private void genIcons(){
		setCommandExecute("document.trvV" + this.getId() + ".icon.root = '" + this.getIconRoot() + "'");
		setCommandExecute("document.trvV" + this.getId() + ".icon.joinBottom = '" + this.getIconJoinBottom() + "'");
		setCommandExecute("document.trvV" + this.getId() + ".icon.minusBottom = '" + this.getIconMinusBottom() + "'");
		setCommandExecute("document.trvV" + this.getId() + ".icon.plusBottom = '" + this.getIconPlusBottom() + "'");
		setCommandExecute("document.trvV" + this.getId() + ".icon.folderOpen = '" + this.getIconFolderOpen() + "'");	
		setCommandExecute("document.trvV" + this.getId() + ".icon.join = '" + this.getIconJoin() + "'");				
		setCommandExecute("document.trvV" + this.getId() + ".icon.empty = '" + this.getIconEmpty() + "'");				
	}

	public Boolean getUseIcons() {
		return useIcons;
	}

	public void setUseIcons(Boolean useIcons) {
		this.useIcons = useIcons;
	}

	public String getIconFolderOpen() {
		return iconFolderOpen;
	}

	public void setIconFolderOpen(String iconFolderOpen) {
		this.iconFolderOpen = iconFolderOpen;
		generateIcons = false;
	}

	public String getIconJoinBottom() {
		return iconJoinBottom;
	}

	public void setIconJoinBottom(String iconJoinBottom) {
		this.iconJoinBottom = iconJoinBottom;
		generateIcons = false;
	}

	public String getIconMinusBottom() {
		return iconMinusBottom;
	}

	public void setIconMinusBottom(String iconMinusBottom) {
		this.iconMinusBottom = iconMinusBottom;
		generateIcons = false;
	}

	public String getIconPlusBottom() {
		return iconPlusBottom;
	}

	public void setIconPlusBottom(String iconPlusBottom) {
		this.iconPlusBottom = iconPlusBottom;
		generateIcons = false;
	}

	public String getIconRoot() {
		return iconRoot;
	}

	public void setIconRoot(String iconRoot) {
		this.iconRoot = iconRoot;
		generateIcons = false;
	}

	public String getIconJoin() {
		return iconJoin;
	}

	public void setIconJoin(String iconJoin) {
		this.iconJoin = iconJoin;
	}

	public String getIconEmpty() {
		return iconEmpty;
	}

	public void setIconEmpty(String iconEmpty) {
		this.iconEmpty = iconEmpty;
	}
	
}
