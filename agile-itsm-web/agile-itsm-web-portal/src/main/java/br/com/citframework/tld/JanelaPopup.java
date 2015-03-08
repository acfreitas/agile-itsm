package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.citframework.util.Constantes;

public class JanelaPopup extends BodyTagSupport {

    private static final long serialVersionUID = -7427569352751397116L;

    private String id;
    private String style;
    private String title;
    private String modal;
    private String zIndex;
    private String backgroundColorTitle;
    private String backgroundColor;
    private String maximize;

    @Override
    public int doStartTag() throws JspException {
        try {
            int zIndexInt = -1;
            if (this.getZIndex() != null && !this.getZIndex().equalsIgnoreCase("")) {
                zIndexInt = Integer.parseInt(this.getZIndex());
            }
            String caminhoIconeMaxPopup = "/imagens/max.gif";
            if (Constantes.getValue("JANELAPOPUP_CAMINHO_ICONE_MAXIMIZE") != null) {
                caminhoIconeMaxPopup = Constantes.getValue("JANELAPOPUP_CAMINHO_ICONE_MAXIMIZE");
            }
            String caminhoIconeMinPopup = "/imagens/min.gif";
            if (Constantes.getValue("JANELAPOPUP_CAMINHO_ICONE_MINIMIZE") != null) {
                caminhoIconeMinPopup = Constantes.getValue("JANELAPOPUP_CAMINHO_ICONE_MINIMIZE");
            }

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("var " + this.getId() + "_TOP = 0;\n");
            pageContext.getOut().println("var " + this.getId() + "_LEFT = 0;\n");
            pageContext.getOut().println("var " + this.getId() + "_HEIGHT = 0;\n");
            pageContext.getOut().println("var " + this.getId() + "_WIDTH = 0;\n");
            pageContext.getOut().println("var " + this.getId() + "_MAXMIN = false;\n");

            pageContext.getOut().println("function " + this.getId() + "() { };\n");
            pageContext.getOut().println("</script>\n");

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("" + this.getId() + "_apenasNumeros = function(str){\n");
            pageContext.getOut().println("	if (str == null) return '';\n");
            pageContext.getOut().println("	var strReturn = '';\n");
            pageContext.getOut().println("	for (var i = 0; i < str.length; i++){\n");
            pageContext.getOut().println("		if (str.charAt(i) == '0' || str.charAt(i) == '1' || str.charAt(i) == '2' || str.charAt(i) == '3' || str.charAt(i) == '4' ||\n");
            pageContext.getOut().println("			str.charAt(i) == '5' || str.charAt(i) == '6' || str.charAt(i) == '7' || str.charAt(i) == '8' || str.charAt(i) == '9'){\n");
            pageContext.getOut().println("			strReturn += str.charAt(i);\n");
            pageContext.getOut().println("		}\n");
            pageContext.getOut().println("	}\n");
            pageContext.getOut().println("	return strReturn;\n");
            pageContext.getOut().println("};\n");

            pageContext.getOut().println("" + this.getId() + ".show = function(){\n");
            pageContext.getOut().println("   try{");
            pageContext.getOut().println("   	" + this.getId() + "_onshow();");
            pageContext.getOut().println("   }catch(ex){");
            pageContext.getOut().println("   }");

            pageContext.getOut().println("   if (MOUSE_PosY == null || MOUSE_PosY == undefined || MOUSE_PosY < 0){\n");
            pageContext.getOut().println("   	MOUSE_PosY = 0;");
            pageContext.getOut().println("   }");

            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.top = MOUSE_PosY + 'px';\n");
            // pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='block';\n");
            pageContext.getOut().println("   " + this.getId() + "_Ajusta_JanelaPopup();\n");
            if ("true".equalsIgnoreCase(modal)) {
                pageContext.getOut().println("  F" + this.getId() + "_atualizaZIndexMaior('divBloqueiaTela_" + this.getId() + "');\n");
                pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + this.getId() + "').style.display='block';\n");

                pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + this.getId() + "').style.left = '0px';");
                pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + this.getId() + "').style.top = '0px';");
                pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + this.getId() + "').style.width = document.body.scrollWidth + 'px';");
                pageContext.getOut().println("	if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight){");
                pageContext.getOut().println(
                        "		document.getElementById('divBloqueiaTela_" + this.getId() + "').style.height = (document.body.clientHeight + document.body.clientTop) + 'px';");
                pageContext.getOut().println("	}else{");
                pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + this.getId() + "').style.height = document.body.scrollHeight + 'px';");
                pageContext.getOut().println("	}");
                // pageContext.getOut().println("   alert(document.getElementById('" + getId() + "').style.height);");
                // pageContext.getOut().println("   alert(document.getElementById('" + getId() + "').style.width);");
            }
            if (zIndexInt == -1) {
                pageContext.getOut().println("   F" + this.getId() + "_atualizaZIndexMaior('" + this.getId() + "');\n");
            }

            pageContext.getOut().println("   document.getElementById('divJanelaPopup_" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('divIntJanelaPopup_" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "_title').style.display='inline';\n");
            pageContext.getOut().println("   document.getElementById('divCorpoJanelaPopup_" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.display='block';\n");

            pageContext.getOut().println("};\n\n");

            pageContext.getOut().println("</script>\n");

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("" + this.getId() + ".showInYPosition = function(objPos){\n");
            pageContext.getOut().println("   try{");
            pageContext.getOut().println("   	" + this.getId() + "_onshow();");
            pageContext.getOut().println("   }catch(ex){");
            pageContext.getOut().println("   }");
            pageContext.getOut().println("   if (objPos != null){");
            pageContext.getOut().println("   	document.getElementById('" + this.getId() + "').style.top = objPos.top + 'px';\n");
            pageContext.getOut().println("	 }");
            // pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='block';\n");
            pageContext.getOut().println("   " + this.getId() + "_Ajusta_JanelaPopup();\n");
            if ("true".equalsIgnoreCase(modal)) {
                pageContext.getOut().println("  F" + this.getId() + "_atualizaZIndexMaior('divBloqueiaTela_" + this.getId() + "');\n");
                pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + this.getId() + "').style.display='block';\n");
                pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + this.getId() + "').style.left = '0px';");
                pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + this.getId() + "').style.top = '0px';");
                pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + this.getId() + "').style.width = document.body.scrollWidth + 'px';");
                pageContext.getOut().println("	if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight){");
                pageContext.getOut().println(
                        "		document.getElementById('divBloqueiaTela_" + this.getId() + "').style.height = (document.body.clientHeight + document.body.clientTop) + 'px';");
                pageContext.getOut().println("	}else{");
                pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + this.getId() + "').style.height = document.body.scrollHeight + 'px';");
                pageContext.getOut().println("	}");
            }
            if (zIndexInt == -1) {
                pageContext.getOut().println("  F" + this.getId() + "_atualizaZIndexMaior('" + this.getId() + "');\n");
            }

            pageContext.getOut().println("   document.getElementById('divJanelaPopup_" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('divIntJanelaPopup_" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "_title').style.display='inline';\n");
            pageContext.getOut().println("   document.getElementById('divCorpoJanelaPopup_" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.display='block';\n");

            pageContext.getOut().println("};\n\n");
            pageContext.getOut().println("</script>\n");

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("" + this.getId() + ".maximize = function(){\n");
            pageContext.getOut().println("    var divToMax = document.getElementById('" + this.getId() + "');\n");
            pageContext.getOut().println("    if (!" + this.getId() + "_MAXMIN){\n");
            pageContext.getOut().println("         " + this.getId() + "_TOP = " + this.getId() + "_apenasNumeros(divToMax.style.top);\n");
            pageContext.getOut().println("         " + this.getId() + "_LEFT = " + this.getId() + "_apenasNumeros(divToMax.style.left);\n");
            pageContext.getOut().println("         " + this.getId() + "_HEIGHT = " + this.getId() + "_apenasNumeros(divToMax.style.height);\n");
            pageContext.getOut().println("         " + this.getId() + "_WIDTH = " + this.getId() + "_apenasNumeros(divToMax.style.width);\n");
            pageContext.getOut().println("    }\n");
            pageContext.getOut().println("    if (!" + this.getId() + "_MAXMIN){\n");
            // Atencao - abaixo deve realmente chamar 2 vezes a funcao, pois somente assim ela ignora as barras de rolagem!.
            pageContext.getOut().println("        " + this.getId() + "_MaximizaJanela();\n");
            pageContext.getOut().println("        " + this.getId() + "_MaximizaJanela();\n");
            pageContext.getOut().println(
                    "        document.getElementById('IMG_" + this.getId() + "_MAXMIN').src = '" + Constantes.getValue("SERVER_ADDRESS")
                            + ((HttpServletRequest) pageContext.getRequest()).getContextPath() + caminhoIconeMinPopup + "'");
            pageContext.getOut().println("        try{");
            pageContext.getOut().println("   	      " + this.getId() + "_onmaximize();");
            pageContext.getOut().println("        }catch(ex){");
            pageContext.getOut().println("        }");
            pageContext.getOut().println("    }else{\n");
            pageContext.getOut().println("        " + this.getId() + "_MinimizaJanela();\n");
            pageContext.getOut().println(
                    "        document.getElementById('IMG_" + this.getId() + "_MAXMIN').src = '" + Constantes.getValue("SERVER_ADDRESS")
                            + ((HttpServletRequest) pageContext.getRequest()).getContextPath() + caminhoIconeMaxPopup + "'");
            pageContext.getOut().println("        try{");
            pageContext.getOut().println("   	      " + this.getId() + "_onminimize();");
            pageContext.getOut().println("        }catch(ex){");
            pageContext.getOut().println("        }");
            pageContext.getOut().println("    }\n");
            pageContext.getOut().println("    " + this.getId() + "_MAXMIN = !" + this.getId() + "_MAXMIN;\n");
            pageContext.getOut().println("};\n\n");
            pageContext.getOut().println("" + this.getId() + "_MaximizaJanela = function(){\n");
            pageContext.getOut().println("    var htmlheight = document.body.clientHeight;\n");
            pageContext.getOut().println("    var htmlwidth = document.body.clientWidth;\n");
            pageContext.getOut().println("    var divToMax = document.getElementById('" + this.getId() + "');\n");
            pageContext.getOut().println("    divToMax.style.top = '0px';\n");
            pageContext.getOut().println("    divToMax.style.left = '0px';\n");
            pageContext.getOut().println("    divToMax.style.height = htmlheight + 'px';\n");
            pageContext.getOut().println("    divToMax.style.width = htmlwidth + 'px';\n");
            pageContext.getOut().println("    " + this.getId() + "_Ajusta_JanelaPopup();\n");
            pageContext.getOut().println("};\n\n");
            pageContext.getOut().println("" + this.getId() + "_MinimizaJanela = function(){\n");
            pageContext.getOut().println("    var htmlheight = " + this.getId() + "_HEIGHT;\n");
            pageContext.getOut().println("    var htmlwidth = " + this.getId() + "_WIDTH;\n");
            pageContext.getOut().println("    var divToMax = document.getElementById('" + this.getId() + "');\n");
            pageContext.getOut().println("    divToMax.style.top = " + this.getId() + "_TOP + 'px';\n");
            pageContext.getOut().println("    divToMax.style.left = " + this.getId() + "_LEFT + 'px';\n");
            pageContext.getOut().println("    divToMax.style.height = htmlheight + 'px';\n");
            pageContext.getOut().println("    divToMax.style.width = htmlwidth + 'px';\n");
            pageContext.getOut().println("    " + this.getId() + "_Ajusta_JanelaPopup();\n");
            pageContext.getOut().println("};\n\n");
            pageContext.getOut().println("" + this.getId() + "_CloseButtonPress = function(){\n");
            pageContext.getOut().println("        var b = true;\n");
            pageContext.getOut().println("        try{\n");
            pageContext.getOut().println("   	      b = " + this.getId() + "_onCloseButton();\n");
            pageContext.getOut().println("        }catch(ex){");
            pageContext.getOut().println("            b = true;\n");
            pageContext.getOut().println("        }\n");
            pageContext.getOut().println("        if (b){\n");
            pageContext.getOut().println("            " + this.getId() + ".hide();\n");
            pageContext.getOut().println("        }\n");
            pageContext.getOut().println("};\n\n");

            pageContext.getOut().println("" + this.getId() + ".hide = function(){\n");
            // pageContext.getOut().println("   " + getId() + "_Ajusta_JanelaPopup();\n");
            if ("true".equalsIgnoreCase(modal)) {
                pageContext.getOut().println("   document.getElementById('divBloqueiaTela_" + this.getId() + "').style.display='none';\n");
            }
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.display='none';\n");
            pageContext.getOut().println("   try{");
            pageContext.getOut().println("   	" + this.getId() + "_onhide();");
            pageContext.getOut().println("   }catch(ex){");
            pageContext.getOut().println("   }");
            pageContext.getOut().println("};\n\n");
            pageContext.getOut().println("</script>\n");

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("" + this.getId() + ".setTitle = function(title){\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "_title').innerHTML = title;");
            pageContext.getOut().println("};\n\n");
            pageContext.getOut().println("</script>\n");

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("" + this.getId() + "_Ajusta_JanelaPopup = function(){\n");
            pageContext.getOut().println("   var dvInterna = document.getElementById('divIntJanelaPopup_" + this.getId() + "');\n");
            pageContext.getOut().println("   var dvCorpo = document.getElementById('divCorpoJanelaPopup_" + this.getId() + "');\n");
            pageContext.getOut().println("   var dvLookup = document.getElementById('divJanelaPopup_" + this.getId() + "');\n");
            // pageContext.getOut().println("   var fraLookup = document.getElementById('fraJanelaPopup_" + getId() + "');\n");
            pageContext.getOut().println("   dvLookup.style.height = document.getElementById('" + this.getId() + "').style.height;\n");
            // pageContext.getOut().println("   if (isMozilla){");
            // pageContext.getOut().println("   	fraLookup.style.display='none';");
            // pageContext.getOut().println("   }else{");
            // Nao ha razao para mostrar o iframe no mozilla e firefox.
            // pageContext.getOut().println("   	fraLookup.style.height = document.getElementById('" + getId() + "').style.height;\n");
            // /pageContext.getOut().println("   	fraLookup.style.width = document.getElementById('" + getId() + "').style.width;\n");
            // pageContext.getOut().println("   }");
            pageContext.getOut().println("   dvLookup.style.width = document.getElementById('" + this.getId() + "').style.width;\n");
            pageContext.getOut().println("   dvInterna.style.height = document.getElementById('" + this.getId() + "').style.height;\n");
            pageContext.getOut().println("   dvInterna.style.width = document.getElementById('" + this.getId() + "').style.width;\n");

            pageContext.getOut().println("   dvInterna.style.top = '0px'");
            pageContext.getOut().println("   dvCorpo.style.top = '28px'");
            pageContext.getOut().println("};\n");
            pageContext.getOut().println("</script>\n");

            if (zIndexInt == -1) {
                // pageContext.getOut().println("<div id='" + getId() + "' style='" + getStyle() + ";z-index:3001;' class='dragme' onclick=\"F" + getId() + "_atualizaZIndexMaior('"
                // + getId() + "')\">\n");
                pageContext.getOut().println(
                        "<div id='" + this.getId() + "' style='" + this.getStyle()
                                + ";z-index:3001; background: #DAE0E3; box-shadow: 0 0 20px rgba(0, 0, 0, 0.3);' class='dragme'>\n");
            } else {
                pageContext.getOut().println(
                        "<div id='" + this.getId() + "' style='" + this.getStyle() + ";z-index:" + this.getZIndex() + ";  background: #DAE0E3;' class='dragme'>\n");
            }
            // pageContext.getOut().println("<div style='z-index:2;' id='divJanelaPopup_" + getId() + "'><iframe style='overflow: hidden' id='fraJanelaPopup_" + getId() + "' src='"
            // + urlIframe + "'></iframe></div>\n");
            pageContext.getOut().println("<div style='z-index:2;' class='corpoexternopopup ui-corner-all' id='divJanelaPopup_" + this.getId() + "'></div>\n");
            String backColor = "";
            if (this.getBackgroundColor() != null) {
                backColor = this.getBackgroundColor();
            } else {
                backColor = Constantes.getValue("JANELAPOPUP_BACKGROUND");
            }
            pageContext.getOut().println(
                    "<div style='position:absolute;background:" + backColor + ";border:2px solid #E4E4E4' id='divIntJanelaPopup_" + this.getId()
                            + "' class='corpointernopopup ui-corner-all'>\n");

            if (this.getBackgroundColorTitle() != null) {
                this.getBackgroundColorTitle();
            } else {
                Constantes.getValue("JANELAPOPUP_TITLE_BACKCOLOR");
            }
            if (this.getMaximize() != null) {
                if (this.getMaximize().trim().equalsIgnoreCase("true")) {}
            }
            pageContext.getOut().println("<div class='tituloPopup' >");
            pageContext.getOut().println("<h4 id='" + this.getId() + "_title'>");
            pageContext.getOut().println(this.getTitle());
            pageContext.getOut().println("</h4>");
            pageContext.getOut().println("<div>");;
            String caminhoIconeFecharPopup = "/imagens/fecharLookup.gif";
            if (Constantes.getValue("JANELAPOPUP_CAMINHO_ICONE_FECHAR") != null) {
                caminhoIconeFecharPopup = Constantes.getValue("JANELAPOPUP_CAMINHO_ICONE_FECHAR");
            }
            pageContext.getOut().println("<a href='#' onclick='" + this.getId() + ".maximize();' >");
            pageContext.getOut().println(
                    "<img id='IMG_" + this.getId() + "_MAXMIN' alt='Maximizar janela' src='" + Constantes.getValue("SERVER_ADDRESS")
                            + ((HttpServletRequest) pageContext.getRequest()).getContextPath() + caminhoIconeMaxPopup + "'/>");
            pageContext.getOut().println("</a>");
            pageContext.getOut().println("<a href='#' onclick='" + this.getId() + "_CloseButtonPress();'>");
            pageContext.getOut().println(
                    "<img alt='Fechar janela' src='" + Constantes.getValue("SERVER_ADDRESS") + ((HttpServletRequest) pageContext.getRequest()).getContextPath()
                            + caminhoIconeFecharPopup + "'/>");
            pageContext.getOut().println("</a>");
            pageContext.getOut().println("</div>");
            pageContext.getOut().println("</div>");
            pageContext.getOut().println("<div id='divCorpoJanelaPopup_" + this.getId() + "' style='position:absolute;'>");

        } catch (final IOException e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;

    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().println("</div>\n");
            pageContext.getOut().println("</div>\n");
            pageContext.getOut().println("</div>\n");

            pageContext.getOut().println("<script>");
            pageContext.getOut().println("F" + this.getId() + "_atualizaZIndexMaior = function(idDiv){");
            pageContext.getOut().println("	var divs = document.getElementsByTagName('div');");
            pageContext.getOut().println("	var maiorZIndex = 0;");
            pageContext.getOut().println("	if (divs == null || divs == undefined) return;");
            pageContext.getOut().println("	for(var i = 0; i < divs.length; i++){");
            pageContext
                    .getOut()
                    .println(
                            "	  if (divs[i].id != 'divBarraFerramentas' && divs[i].id != 'divBoxOver' && divs[i].id != 'divBarraFerramentasAdicionais' && divs[i].id != 'divImpressosAdicionais' && divs[i].id != 'divBarraHorario' && divs[i].id != 'divBarra2' && divs[i].id != 'divBarra2Adicionais' ");
            pageContext.getOut().println("	    && divs[i].id != 'dialog' && divs[i].id != 'dialog-mask' && divs[i].id != 'divListaNomesAgendaEspera'){");
            pageContext.getOut().println("		if (divs[i].style.display != 'none'){");
            pageContext.getOut().println("			if (divs[i].style.zIndex != null && divs[i].style.zIndex != undefined){");
            pageContext.getOut().println("				if (maiorZIndex < divs[i].style.zIndex){");
            pageContext.getOut().println("					if (divs[i].style.zIndex < 11000){"); // Este item eh para evitar sobrepor o FCKEditor (Janelas de serviços).
            pageContext.getOut().println("						maiorZIndex = parseInt(divs[i].style.zIndex);");
            pageContext.getOut().println("					}else{");
            pageContext.getOut().println("						maiorZIndex = 11000;");
            pageContext.getOut().println("					}");
            pageContext.getOut().println("				}");
            pageContext.getOut().println("			}");
            pageContext.getOut().println("		}");
            pageContext.getOut().println("	  }");
            pageContext.getOut().println("	}");
            pageContext.getOut().println("	document.getElementById(idDiv).style.zIndex = parseInt(maiorZIndex) + 1;");
            pageContext.getOut().println("};");
            pageContext.getOut().println("</script>");

            if ("true".equalsIgnoreCase(modal)) {
                // Gera a DIV de Bloqueio da Tela.
                pageContext.getOut().println(
                        "<div id='divBloqueiaTela_" + this.getId()
                                + "' style='z-index:3000;position:absolute; CURSOR: wait; BACKGROUND-COLOR:#676B70; filter:alpha(opacity=70);-moz-opacity:.70;opacity:.70;'>");
                pageContext.getOut().println("</div>");
            }
        } catch (final IOException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

    @Override
    public BodyContent getBodyContent() {
        final BodyContent b = super.getBodyContent();
        return b;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(final String modal) {
        this.modal = modal;
    }

    public String getBackgroundColorTitle() {
        return backgroundColorTitle;
    }

    public void setBackgroundColorTitle(final String backgroundColorTitle) {
        this.backgroundColorTitle = backgroundColorTitle;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getZIndex() {
        return zIndex;
    }

    public void setZIndex(final String index) {
        zIndex = index;
    }

    public String getMaximize() {
        return maximize;
    }

    public void setMaximize(final String maximize) {
        this.maximize = maximize;
    }

}
