package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class JanelaAguarde extends BodyTagSupport {

    private static final long serialVersionUID = -6496792382410987252L;
    private String id;
    private String style;
    private String title;

    @Override
    public int doStartTag() throws JspException {
        try {
            final String urlIframe = ((HttpServletRequest) pageContext.getRequest()).getContextPath() + "/include/vazio.jsp";

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("var " + this.getId() + "_time_id = 0;");

            pageContext.getOut().println("function " + this.getId() + "_ajustaPosicao() {");
            pageContext.getOut().println("    document.getElementById('" + this.getId() + "').style.top = HTMLUtils.getYOffset() + 'px';");
            pageContext.getOut().println("}");

            pageContext.getOut().println("HTMLUtils.addEvent(window, 'scroll', " + this.getId() + "_ajustaPosicao, false);");

            pageContext.getOut().println("function " + this.getId() + "() { }");

            // Cria a funcao de Show
            pageContext.getOut().println("" + this.getId() + ".setTitle = function(titulo) {\n");
            pageContext.getOut().println("    document.getElementById('" + this.getId() + "_titulo').innerHTML = '<b>' + titulo + '</b>';");
            pageContext.getOut().println("}\n\n");

            pageContext.getOut().println("" + this.getId() + ".show = function() {\n");
            pageContext.getOut().println("   " + this.getId() + "_Ajusta_JanelaAguarde();\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.display='block';\n");
            pageContext.getOut().println("   document.getElementById('divBloqueiaTela_" + this.getId() + "').style.display='block';\n");

            pageContext.getOut().println("  F" + this.getId() + "_atualizaZIndexMaior('divBloqueiaTela_" + this.getId() + "');\n");

            pageContext.getOut().println("   var leftPos = (document.body.offsetWidth - document.getElementById('" + this.getId() + "').clientWidth) / 2;");
            pageContext.getOut().println("   var topPos  = (document.body.offsetHeight - document.getElementById('" + this.getId() + "').clientHeight) / 2;");

            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.left = leftPos + 'px';");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.top = topPos + 'px';");

            pageContext.getOut().println("  F" + this.getId() + "_atualizaZIndexMaior('" + this.getId() + "');\n");

            pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + this.getId() + "').style.left = '0px';");
            pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + this.getId() + "').style.top = '0px';");
            pageContext.getOut().println("  document.getElementById('divBloqueiaTela_" + this.getId() + "').style.width = document.body.scrollWidth + 'px';");
            pageContext.getOut().println("  if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight) {");
            pageContext.getOut().println(
                    "        document.getElementById('divBloqueiaTela_" + this.getId() + "').style.height = (document.body.clientHeight + document.body.clientTop) + 'px';");
            pageContext.getOut().println("  } else {");
            pageContext.getOut().println("      document.getElementById('divBloqueiaTela_" + this.getId() + "').style.height = (document.body.scrollHeight) + 'px';");
            pageContext.getOut().println("  }");
            pageContext.getOut().println("  if (" + this.getId() + "_time_id == 0) {");
            pageContext.getOut().println("      " + this.getId() + "_time_id = setInterval(" + this.getId() + "_Tira_Foco, 20);");
            pageContext.getOut().println("  }");
            pageContext.getOut().println("   " + this.getId() + "_ajustaPosicao();\n");
            pageContext.getOut().println("}\n\n");

            // Cria a funcao de Hide
            pageContext.getOut().println("" + this.getId() + ".hide = function() {\n");
            pageContext.getOut().println("   document.getElementById('" + this.getId() + "').style.display='none';\n");
            pageContext.getOut().println("   document.getElementById('divBloqueiaTela_" + this.getId() + "').style.display='none';\n");
            pageContext.getOut().println("   if (" + this.getId() + "_time_id > 0) {");
            pageContext.getOut().println("       clearInterval(" + this.getId() + "_time_id);");
            pageContext.getOut().println("       " + this.getId() + "_time_id = 0;");
            pageContext.getOut().println("     }");
            pageContext.getOut().println("}\n\n");

            // Cria a funcao de Controle de Foco
            pageContext.getOut().println("function " + this.getId() + "_Tira_Foco() {");
            pageContext.getOut().println("     if (document.getElementById('" + this.getId() + "').style.display == 'none') {");
            pageContext.getOut().println("         if (" + this.getId() + "_time_id > 0) {");
            pageContext.getOut().println("             clearInterval(" + this.getId() + "_time_id);");
            pageContext.getOut().println("         }");
            pageContext.getOut().println("       document.getElementById('divBloqueiaTela_" + this.getId() + "').style.display='none';\n");
            pageContext.getOut().println("     }");
            pageContext.getOut().println("}");

            // Cria a funcao de Ajustar a Janela
            pageContext.getOut().println("function " + this.getId() + "_Ajusta_JanelaAguarde() {\n");
            pageContext.getOut().println("   var dvInterna = document.getElementById('divIntJanelaAguarde_" + this.getId() + "');\n");
            pageContext.getOut().println("   var dvCorpo = document.getElementById('divCorpoJanelaAguarde_" + this.getId() + "');\n");
            pageContext.getOut().println("   var dvLookup = document.getElementById('divJanelaAguarde_" + this.getId() + "');\n");
            pageContext.getOut().println("   var fraLookup = document.getElementById('fraJanelaAguarde_" + this.getId() + "');\n");
            pageContext.getOut().println("   dvLookup.style.height = '100px';\n");
            pageContext.getOut().println("   fraLookup.style.height = '100px';\n");
            pageContext.getOut().println("   dvLookup.style.width = document.getElementById('" + this.getId() + "').style.width;\n");
            pageContext.getOut().println("   fraLookup.style.width = document.getElementById('" + this.getId() + "').style.width;\n");
            pageContext.getOut().println("   dvInterna.style.height = '135px';\n");
            pageContext.getOut().println("   dvInterna.style.width = document.getElementById('" + this.getId() + "').style.width;\n");

            pageContext.getOut().println("   dvInterna.style.top = '0px'");
            pageContext.getOut().println("   dvCorpo.style.top = '25px'");

            pageContext.getOut().println("}\n");

            pageContext.getOut().println("</script>\n");

            pageContext.getOut().println("<div id='" + this.getId() + "' style='z-index:3001;" + this.getStyle() + "' class='dragme'>\n");
            pageContext.getOut().println(
                    "<div style='z-index:2;' id='divJanelaAguarde_" + this.getId() + "'><iframe id='fraJanelaAguarde_" + this.getId() + "' src='" + urlIframe
                            + "'></iframe></div>\n");
            pageContext.getOut().println("<div style='position:absolute;background:white;border:1px solid black' id='divIntJanelaAguarde_" + this.getId() + "'>\n");

            pageContext.getOut().println("<table width='100%' bgcolor='#CCCCCC'>\n");
            pageContext.getOut().println("<tr>\n");
            pageContext.getOut().println("<td width='100%' id='" + this.getId() + "_titulo' style='text-align:center; backgroundcollor: #CCCCCC;'>\n");
            pageContext.getOut().println(this.getTitle() + "\n");
            pageContext.getOut().println("</td>\n");
            pageContext.getOut().println("</tr>\n");
            pageContext.getOut().println("</table>\n");

            pageContext.getOut().println("<div id='divCorpoJanelaAguarde_" + this.getId() + "' style='position:absolute;width:100%'>");

        } catch (final IOException e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().println("&nbsp;<br/>");
            pageContext.getOut().println("<table width='100%'><tr><td style='text-align:center'>");
            final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            pageContext.getOut().println(
                    "&nbsp;<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
                            + "/novoLayout/common/include/imagens/ajax-loader.gif' border='0'> &nbsp;" + UtilI18N.internacionaliza(request, "citcorpore.comum.aguarde"));
            pageContext.getOut().println("</td></tr></table>");
            pageContext.getOut().println("</div>\n");
            pageContext.getOut().println("</div>\n");
            pageContext.getOut().println("</div>\n");

            pageContext.getOut().println("<script>");
            pageContext.getOut().println("F" + this.getId() + "_atualizaZIndexMaior = function(idDiv) {");
            pageContext.getOut().println("    var divs = document.getElementsByTagName('div');");
            pageContext.getOut().println("    var maiorZIndex = 0;");
            pageContext.getOut().println("    if (divs == null || divs == undefined) return;");
            pageContext.getOut().println("    for(var i = 0; i < divs.length; i++) {");
            pageContext.getOut().println("        if (divs[i].style.display != 'none') {");
            pageContext.getOut().println("            if (divs[i].style.zIndex != null && divs[i].style.zIndex != undefined) {");
            pageContext.getOut().println("                if (maiorZIndex < divs[i].style.zIndex) {");
            pageContext.getOut().println("                    maiorZIndex = divs[i].style.zIndex;");
            pageContext.getOut().println("                }");
            pageContext.getOut().println("            }");
            pageContext.getOut().println("        }");
            pageContext.getOut().println("    }");
            pageContext.getOut().println("    document.getElementById(idDiv).style.zIndex = maiorZIndex + 10;");
            pageContext.getOut().println("};");
            pageContext.getOut().println("</script>");

            // Gera a DIV de Bloqueio da Tela.
            pageContext.getOut().println(
                    "<div id='divBloqueiaTela_" + this.getId()
                            + "' style='z-index:3000;position:absolute; CURSOR: wait; BACKGROUND-COLOR:gray; filter:alpha(opacity=20);-moz-opacity:.25;opacity:.25;'>");
            pageContext.getOut().println("</div>");

        } catch (final IOException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
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

}
