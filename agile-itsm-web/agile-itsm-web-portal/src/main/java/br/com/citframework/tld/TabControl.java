package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TabControl extends BodyTagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7095283995872329494L;
	private String tabs;
	private String descs;
	private String width;
	private String abaAtiva;
	private String raizImagens;
	private String name;
	
	public int doStartTag() throws JspException {
		int abaAtivaAux = 1;
		int qtdeAbas = 0;
		String[] abas;
		String[] titulos;
		StringBuilder strBuff = new StringBuilder();
		if (this.getTabs().indexOf(";")>0){
			abas = this.getTabs().split(";");
			titulos = this.getDescs().split(";");
			if (titulos.length != abas.length){
				throw new JspException("Numero de Parametros em [tabs] é diferente de [desc]");
			}
		}else{
			abas = new String[1];
			abas[0] = this.getTabs();
			titulos = new String[1];
			titulos[0] = this.getDescs();
		}
		strBuff.append("<table border='0' cellspacing='0' cellpadding='0' width='"+getWidth()+"'>\n");
		strBuff.append("    <tr>\n");
		qtdeAbas = abas.length;
		abaAtivaAux = Integer.parseInt("0" + getAbaAtiva());
		abaAtivaAux = abaAtivaAux - 1;
		if (abaAtivaAux < 0) abaAtivaAux = 0;
		for (int i=0; i<qtdeAbas; i++){
			strBuff.append("<!-- Aba "+i+" -->\n");
			strBuff.append("<td><img id='qE"+getName()+abas[i]+"' src='"+getRaizImagens()+"/");
			if (i == abaAtivaAux){
				strBuff.append("quinaEsqAtiva.png");
			}else{
				strBuff.append("quinaEsqDesat.png");
			}
			strBuff.append("' border='0'>");
			strBuff.append("</td>\n");
			strBuff.append("<td id='qM"+getName()+abas[i]+"' style='cursor:hand;FONT-WEIGHT: normal;FONT-SIZE: 9px;COLOR: black;FONT-FAMILY: Tahoma, Arial, Helvetica, sans-serif' nowrap background='"+getRaizImagens()+"/");
			if (i == abaAtivaAux){
				strBuff.append("meioBgAtiva.png");
			}else{
				strBuff.append("meioBgDesat.png");
			}
			strBuff.append("' onclick=\"javascript:ABA_"+getName()+"_AtivarAba('"+abas[i]+"');\">");
			strBuff.append(titulos[i]);
			strBuff.append("</td>");
			strBuff.append("<td><img id='qD"+getName()+abas[i]+"' src='"+getRaizImagens()+"/");
			if (i == abaAtivaAux){
				strBuff.append("quinaDirAtiva.png");
			}else{
				strBuff.append("quinaDirDesat.png");
			}
			strBuff.append("' border='0'>");	
			strBuff.append("</td>\n");
		}
		strBuff.append("<td align='right' width='100%' background='"+getRaizImagens()+"/compTop.png'></td>\n");
		strBuff.append("</tr>\n");
		strBuff.append("</table>\n"); 
		
		strBuff.append("<script>\n");
			strBuff.append("function ABA_"+getName()+"_AtivarAba(abaAtivar){\n");
			for (int i=0; i<qtdeAbas; i++){
				strBuff.append("document.getElementById('"+abas[i]+"').style.display='none';\n");
				strBuff.append("document.getElementById('qE"+getName()+abas[i]+"').src='"+getRaizImagens()+"/quinaEsqDesat.png';\n");
				strBuff.append("document.getElementById('qM"+getName()+abas[i]+"').background='"+getRaizImagens()+"/meioBgDesat.png';\n");
				strBuff.append("document.getElementById('qD"+getName()+abas[i]+"').src='"+getRaizImagens()+"/quinaDirDesat.png';\n");
			}
			strBuff.append("document.getElementById(abaAtivar).style.display='block';\n");
			strBuff.append("document.getElementById('qE"+getName()+"'+abaAtivar).src='"+getRaizImagens()+"/quinaEsqAtiva.png';\n");
			strBuff.append("document.getElementById('qM"+getName()+"'+abaAtivar).background='"+getRaizImagens()+"/meioBgAtiva.png';\n");
			strBuff.append("document.getElementById('qD"+getName()+"'+abaAtivar).src='"+getRaizImagens()+"/quinaDirAtiva.png';\n");
			strBuff.append("}\n");
		strBuff.append("</script>\n");
		try {
			pageContext.getOut().println(strBuff.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getTabs() {
		return tabs;
	}

	public void setTabs(String tabs) {
		this.tabs = tabs;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getAbaAtiva() {
		if (this.abaAtiva == null){
			this.abaAtiva = "";
		}
		return abaAtiva;
	}

	public void setAbaAtiva(String abaAtiva) {
		this.abaAtiva = abaAtiva;
	}

	public String getRaizImagens() {
		return raizImagens;
	}

	public void setRaizImagens(String raizImagens) {
		this.raizImagens = raizImagens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
