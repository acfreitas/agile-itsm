package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class Grid extends BodyTagSupport {

    private static final long serialVersionUID = -5264963743225526103L;

    private String id;
    private String columnHeaders;
    private String styleCells;
    private String deleteIcon;

    private String[] headers;

    @Override
    public int doStartTag() throws JspException {
        if (columnHeaders != null) {
            String locale = "";
            if (pageContext.getSession().getAttribute("locale") != null) {
                try {
                    locale = pageContext.getSession().getAttribute("locale").toString();
                } catch (final Exception e) {
                }
            } else {
            	locale = UtilI18N.PORTUGUESE_SIGLA;
            }
            final String str = UtilI18N.internacionaliza(locale, columnHeaders);
            if (str != null && str.trim().length() > 0) {
                columnHeaders = str;
            }
            headers = columnHeaders.split(";");
        }
        if (deleteIcon == null || deleteIcon.trim().equalsIgnoreCase("")) {
            deleteIcon = "true";
        }
        try {
            pageContext.getOut().println("<table id='" + this.getId() + "_tblItens' width='100%' cellpadding='0' cellspacing='0'>\n");
            pageContext.getOut().println("<tr>");
            if (headers != null) {
                for (int i = -1; i < headers.length; i++) {
                    if (i == -1) {
                        pageContext.getOut().println("<td class='linhaSubtituloGrid'>");
                        pageContext.getOut().println("&nbsp;");
                        pageContext.getOut().println("</td>");
                    } else {
                        pageContext.getOut().println("<td class='linhaSubtituloGrid'>");
                        pageContext.getOut().println(headers[i]);
                        pageContext.getOut().println("</td>");
                    }
                }
            }
            pageContext.getOut().println("</tr>");
            pageContext.getOut().println("</table>\n");

            pageContext.getOut().println("<script type=\"text/javascript\">\n");
            pageContext.getOut().println("var " + this.getId() + "_index = 0;\n");
            pageContext.getOut().println("function " + this.getId() + "() { };\n");

            pageContext.getOut().println("" + this.getId() + ".getMaxIndex = function(){\n");
            pageContext.getOut().println("    return " + this.getId() + "_index;");
            pageContext.getOut().println("};\n");

            pageContext.getOut().println("" + this.getId() + ".replaceAll = function(str, strSearch, strReplace) {\n");
            pageContext.getOut().println("	var p = str.indexOf(strSearch);\n");
            pageContext.getOut().println("	while (p != -1) {\n");
            pageContext.getOut().println("		str = str.replace(strSearch, strReplace);\n");
            pageContext.getOut().println("		p = str.indexOf(strSearch);\n");
            pageContext.getOut().println("	}\n");
            pageContext.getOut().println("	return str;\n");
            pageContext.getOut().println("};\n");

            pageContext.getOut().println("" + this.getId() + ".deleteRowByImgRef = function(objImg){\n");
            pageContext.getOut().println("    var ret = true;\n");
            pageContext.getOut().println("    try{");
            pageContext.getOut().println("       ret = " + this.getId() + "_onDeleteRowByImgRef(objImg);");
            pageContext.getOut().println("    }catch(ex){");
            pageContext.getOut().println("       ret = true;\n");
            pageContext.getOut().println("    }\n");
            pageContext.getOut().println("    if (!ret){");
            pageContext.getOut().println("      return;");
            pageContext.getOut().println("    }\n");
            pageContext.getOut().println("    if (!confirm(i18n_message('citcorpore.comum.deleta') )) return;");
            pageContext.getOut().println("    var indice = objImg.parentNode.parentNode.rowIndex;");
            pageContext.getOut().println("    HTMLUtils.deleteRow('" + this.getId() + "_tblItens', indice);");
            pageContext.getOut().println("};\n");

            pageContext.getOut().println("" + this.getId() + ".deleteRow = function(indice){\n");
            pageContext.getOut().println("    HTMLUtils.deleteRow('" + this.getId() + "_tblItens', indice);");
            pageContext.getOut().println("};\n");

            pageContext.getOut().println("" + this.getId() + ".deleteAllRows = function(){\n");
            pageContext.getOut().println("    HTMLUtils.deleteAllRows('" + this.getId() + "_tblItens');");
            pageContext.getOut().println("    " + this.getId() + "_index = 0;\n");
            pageContext.getOut().println("};\n");

            final String strPathExcluir = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/excluirPeq.gif";

            pageContext.getOut().println("" + this.getId() + ".addRow = function(){\n");
            pageContext.getOut().println("	" + this.getId() + "_index = " + this.getId() + "_index + 1;\n");
            pageContext.getOut().println("  try{");
            pageContext.getOut().println("   	" + this.getId() + "_onAddRow(" + this.getId() + "_index);");
            pageContext.getOut().println("  }catch(ex){");
            pageContext.getOut().println("  }");
            pageContext.getOut().println("	var tbl = document.getElementById('" + this.getId() + "_tblItens');\n");
            pageContext.getOut().println("	var lastRow = tbl.rows.length;\n");
            pageContext.getOut().println("	var row = tbl.insertRow(lastRow);\n");
            pageContext.getOut().println("	row.className = 'CLASS_CONTROL_ROW_TAG';\n");
            pageContext.getOut().println("	row.id = '" + this.getId() + "_TD_' + NumberUtil.zerosAEsquerda(" + this.getId() + "_index,5);\n");
            pageContext.getOut().println("	for(var i = 0; i <= " + this.getQuantidadeColunas() + "; i++){\n");
            pageContext.getOut().println("		var coluna = row.insertCell(i);\n");
            pageContext.getOut().println("		if (i == 0){\n");
            if (deleteIcon.equalsIgnoreCase("true")) { // Se for para apresentar o icone de delete!
                pageContext.getOut().println(
                        "			coluna.innerHTML = '<img src=\"" + strPathExcluir + "\" border=\"0\" onclick=\"" + this.getId()
                                + ".deleteRowByImgRef(this)\" style=\"cursor:pointer\"/>';\n");
            } else {
                pageContext.getOut().println("			coluna.innerHTML = '&nbsp;';\n");
            }
            pageContext.getOut().println("			coluna.className = 'linhaSubtituloGrid';\n");
            pageContext.getOut().println("		}else{\n");
            if (styleCells != null && !styleCells.trim().equalsIgnoreCase("")) {
                pageContext.getOut().println("			coluna.className = '" + styleCells + "';\n");
            }
            pageContext.getOut().println(
                    "			var auxContent = " + this.getId() + ".replaceAll(document.getElementById('" + this.getId()
                            + "_Coluna_' + NumberUtil.zerosAEsquerda(i,3)).innerHTML,'#SEQ#', NumberUtil.zerosAEsquerda(" + this.getId() + "_index,5));\n");
            pageContext.getOut().println("			coluna.innerHTML = auxContent;\n");
            pageContext.getOut().println("		}\n");
            pageContext.getOut().println("	}\n");
            pageContext.getOut().println("	DEFINEALLPAGES_atribuiCaracteristicasCitAjax();\n");

            pageContext.getOut().println("};\n\n");

            pageContext.getOut().println("" + this.getId() + ".getRowNumberFromObject = function(obj){\n");
            pageContext.getOut().println("	var cl = obj.className;\n");
            pageContext.getOut().println("	while (cl != 'CLASS_CONTROL_ROW_TAG' && (obj != null && obj != undefined)) {\n");
            pageContext.getOut().println("    try{\n");
            pageContext.getOut().println("		obj = obj.parentNode;\n");
            pageContext.getOut().println("		cl = obj.className;\n");
            pageContext.getOut().println("    }catch(ex){\n");
            pageContext.getOut().println("      obj = null;\n");
            pageContext.getOut().println("    }\n");
            pageContext.getOut().println("	}\n");
            pageContext.getOut().println("	if (obj != null && obj != undefined){\n");
            pageContext.getOut().println("	   return obj.rowIndex;\n");
            pageContext.getOut().println("  }else{\n");
            pageContext.getOut().println("	   return -1;\n");
            pageContext.getOut().println("  }\n");
            pageContext.getOut().println("};\n\n");

            pageContext.getOut().println("" + this.getId() + ".getRowObject = function(obj){\n");
            pageContext.getOut().println("	var cl = obj.className;\n");
            pageContext.getOut().println("	while (cl != 'CLASS_CONTROL_ROW_TAG' && (obj != null && obj != undefined)) {\n");
            pageContext.getOut().println("    try{\n");
            pageContext.getOut().println("		obj = obj.parentNode;\n");
            pageContext.getOut().println("		cl = obj.className;\n");
            pageContext.getOut().println("    }catch(ex){\n");
            pageContext.getOut().println("      obj = null;\n");
            pageContext.getOut().println("    }\n");
            pageContext.getOut().println("	}\n");
            pageContext.getOut().println("	if (obj != null && obj != undefined){\n");
            pageContext.getOut().println("	   return obj;\n");
            pageContext.getOut().println("  }else{\n");
            pageContext.getOut().println("	   return null;\n");
            pageContext.getOut().println("  }\n");
            pageContext.getOut().println("};\n\n");

            pageContext.getOut().println("</script>\n");
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    public int getQuantidadeColunas() {
        if (headers != null) {
            return headers.length;
        }
        return 0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

    public String getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(final String columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public String getStyleCells() {
        return styleCells;
    }

    public void setStyleCells(final String styleCells) {
        this.styleCells = styleCells;
    }

    public String getDeleteIcon() {
        return deleteIcon;
    }

    public void setDeleteIcon(final String deleteIcon) {
        this.deleteIcon = deleteIcon;
    }

}
