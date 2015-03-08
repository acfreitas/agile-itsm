package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.citframework.util.Constantes;

public class BalaoDica extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1224475531536078566L;
	private String	id;
	private String style;
	private String title;
	private String modal;
	private String imagemFundo;
	
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().println("<script type=\"text/javascript\">\n");
			
			pageContext.getOut().println("function " + getId() + "() { }");
			
			pageContext.getOut().println("" + getId() + ".show = function(){\n");
			pageContext.getOut().println("   try{");
			pageContext.getOut().println("   	" + getId() + "_onshow();");
			pageContext.getOut().println("   }catch(ex){");
			pageContext.getOut().println("   }");			
					
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.top = MOUSE_PosY;\n");
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='block';\n");
			pageContext.getOut().println("   " + getId() + "_Ajusta_JanelaPopup();\n");	
			if ("true".equalsIgnoreCase(modal)){
				pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + getId() + "').style.display='block';\n");
				
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.left = 0;");
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.top = 0;");
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.width = document.body.scrollWidth;");
				pageContext.getOut().println("	if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight){");
				pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = document.body.clientHeight + document.body.clientTop;");
				pageContext.getOut().println("	}else{");
				pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = document.body.scrollHeight;");
				pageContext.getOut().println("	}");				
			}
			pageContext.getOut().println("}\n\n");
			
			pageContext.getOut().println("" + getId() + ".showInYPosition = function(objPos){\n");
			pageContext.getOut().println("   try{");
			pageContext.getOut().println("   	" + getId() + "_onshow();");
			pageContext.getOut().println("   }catch(ex){");
			pageContext.getOut().println("   }");			
			pageContext.getOut().println("   if (objPos != null){");
			pageContext.getOut().println("   	document.getElementById('" + getId() + "').style.top = objPos.top;\n");
			pageContext.getOut().println("	 }");
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='block';\n");
			pageContext.getOut().println("   " + getId() + "_Ajusta_JanelaPopup();\n");	
			if ("true".equalsIgnoreCase(modal)){
				pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + getId() + "').style.display='block';\n");
				
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.left = 0;");
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.top = 0;");
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.width = document.body.scrollWidth;");
				pageContext.getOut().println("	if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight){");
				pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = document.body.clientHeight + document.body.clientTop;");
				pageContext.getOut().println("	}else{");
				pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = document.body.scrollHeight;");
				pageContext.getOut().println("	}");				
			}			
			pageContext.getOut().println("}\n\n");	
			
			pageContext.getOut().println("" + getId() + ".showInPosition = function(objPos){\n");
			pageContext.getOut().println("   try{");
			pageContext.getOut().println("   	" + getId() + "_onshow();");
			pageContext.getOut().println("   }catch(ex){");
			pageContext.getOut().println("   }");			
			pageContext.getOut().println("   if (objPos != null){");
			pageContext.getOut().println("   	document.getElementById('" + getId() + "').style.top = objPos.top;\n");
			pageContext.getOut().println("	 }");
			pageContext.getOut().println("   if (objPos != null){");
			pageContext.getOut().println("   	document.getElementById('" + getId() + "').style.left = objPos.left;\n");
			pageContext.getOut().println("	 }");			
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='block';\n");
			pageContext.getOut().println("   " + getId() + "_Ajusta_JanelaPopup();\n");	
			if ("true".equalsIgnoreCase(modal)){
				pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + getId() + "').style.display='block';\n");
				
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.left = 0;");
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.top = 0;");
				pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.width = document.body.scrollWidth;");
				pageContext.getOut().println("	if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight){");
				pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = document.body.clientHeight + document.body.clientTop;");
				pageContext.getOut().println("	}else{");
				pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = document.body.scrollHeight;");
				pageContext.getOut().println("	}");				
			}			
			pageContext.getOut().println("}\n\n");				
			
			pageContext.getOut().println("" + getId() + ".hide = function(){\n");
			pageContext.getOut().println("   try{");
			pageContext.getOut().println("   	" + getId() + "_onhide();");
			pageContext.getOut().println("   }catch(ex){");
			pageContext.getOut().println("   }");

			pageContext.getOut().println("   " + getId() + "_Ajusta_JanelaPopup();\n");
			if ("true".equalsIgnoreCase(modal)){
				pageContext.getOut().println("   document.getElementById('divBloqueiaTela_" + getId() + "').style.display='none';\n");
			}
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='none';\n");
			pageContext.getOut().println("}\n\n");
			
			pageContext.getOut().println("" + getId() + ".setTitle = function(title){\n");
			pageContext.getOut().println("   document.getElementById('" + getId() + "_title').innerHTML = title;");
			pageContext.getOut().println("}\n\n");
			
			pageContext.getOut().println("function " + getId() + "_Ajusta_JanelaPopup(){\n");
			pageContext.getOut().println("   var dvInterna = document.getElementById('divIntJanelaPopup_" + getId() + "');\n");			
			pageContext.getOut().println("   var dvCorpo = document.getElementById('divCorpoJanelaPopup_" + getId() + "');\n");
			pageContext.getOut().println("   var dvLookup = document.getElementById('divJanelaPopup_" + getId() + "');\n");
			pageContext.getOut().println("   var fraLookup = document.getElementById('fraJanelaPopup_" + getId() + "');\n");
			pageContext.getOut().println("   dvLookup.style.height = document.getElementById('" + getId() + "').style.height;\n");
			pageContext.getOut().println("   dvLookup.style.width = document.getElementById('" + getId() + "').style.width;\n");
			pageContext.getOut().println("   dvInterna.style.height = document.getElementById('" + getId() + "').style.height;\n");
			pageContext.getOut().println("   dvInterna.style.width = document.getElementById('" + getId() + "').style.width;\n");
			
			pageContext.getOut().println("   dvInterna.style.top = '0px'");
			pageContext.getOut().println("   dvCorpo.style.top = '25px'");
			pageContext.getOut().println("}\n");			
			
			pageContext.getOut().println("</script>\n");
			
			pageContext.getOut().println("<div id='" + getId() + "' style='" + getStyle() + ";z-index:4001;'>\n");
			pageContext.getOut().println("<div style='z-index:2;' id='divJanelaPopup_" + getId() + "'></div>\n");
			pageContext.getOut().println("<div style='position:absolute;background-image:url(" + imagemFundo + ")' id='divIntJanelaPopup_" + getId() + "'>\n");
						
			pageContext.getOut().println("<table width='100%'>\n");
			pageContext.getOut().println("<tr>\n");
			pageContext.getOut().println("<td width='95%'>\n");
			pageContext.getOut().println("&nbsp;");
			pageContext.getOut().println("</td>\n");
			pageContext.getOut().println("<td width='5%' align='center' style='cursor:pointer'>\n");
			pageContext.getOut().println("<img alt='Fechar janela' onclick='" + getId() + ".hide();' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/fecharLookup.gif'/>\n");
			pageContext.getOut().println("</td>\n");
			pageContext.getOut().println("</tr>\n");
			pageContext.getOut().println("</table>\n");
			
			pageContext.getOut().println("</div>");
			
			pageContext.getOut().println("<div id='divCorpoJanelaPopup_" + getId() + "' style='position:absolute;'>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().println("</div>\n");
			pageContext.getOut().println("</div>\n");
			pageContext.getOut().println("</div>\n");
			
			if ("true".equalsIgnoreCase(modal)){
				//Gera a DIV de Bloqueio da Tela.
				pageContext.getOut().println("<div id='divBloqueiaTela_" + getId() + "' style='z-index:3000;position:absolute; CURSOR: wait; BACKGROUND-COLOR:gray; filter:alpha(opacity=20);-moz-opacity:.25;opacity:.25;'>");
				pageContext.getOut().println("</div>");
			}
		} catch (IOException e) {
			throw new JspException(e);
		}
		return super.doEndTag();
	}

	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}	
	
	public BodyContent getBodyContent() {
		BodyContent b = super.getBodyContent();
		return b;
	}	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModal() {
		return modal;
	}
	public void setModal(String modal) {
		this.modal = modal;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getImagemFundo() {
		return imagemFundo;
	}


	public void setImagemFundo(String imagemFundo) {
		this.imagemFundo = imagemFundo;
	}
	
	
}
