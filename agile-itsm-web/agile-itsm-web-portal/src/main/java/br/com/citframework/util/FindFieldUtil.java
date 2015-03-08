package br.com.citframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.citframework.dto.ItemValorDescricaoDTO;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class FindFieldUtil {
	private XmlReadLookup xml;
	private LookupInfo lookupInfo;

	private static Properties props;
	private static String habilitaPesquisa;

	public FindFieldUtil() {
		xml = XmlReadLookup.getInstance();
		lookupInfo = null;
	}

	public StringBuilder generate(ServletRequest request, String nameLookup, String id, int top, int left, int tam, int larg, String nomeForm, String value, String text, String javascript,
			String html, String disabled, String checkbox) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		Collection colCamposPesq = getCamposPesquisa(nameLookup);
		Iterator itCamposPesq = colCamposPesq.iterator();
		Campo cp;
		String separaCampos = "N";
		Collection colCamposTextGenerateLimpar = new ArrayList();
		Collection colCamposComboGenerateLimpar = new ArrayList();
		String nomeLookupExec = nameLookup;
		if (id != null) { // Caso venha com ID, o nome fisico é do ID, caso contrario fica com o do LookupName.
			nameLookup = id;
		}
		HttpSession session = ((HttpServletRequest) request).getSession();
		if (session.getAttribute("locale") != null)
			getProperties(new Locale(session.getAttribute("locale").toString()));
		else
			getProperties(new Locale(""));
		String idAux = id;
		if (idAux == null) {
			idAux = "";
		}
		if(checkbox == null || checkbox.equals("")){
	        	checkbox = "false";
	        }
		int largAux = larg - 250;
		Object[] objs = null;
		Object formAttr = request.getAttribute(nomeForm);
		if (formAttr != null) {
			Method metodo1 = null;
			Method metodo2 = null;
			String aux = value;
			if (value != null) {
				try {
					aux = "get" + aux.substring(0, 1).toUpperCase() + aux.substring(1);
					metodo1 = formAttr.getClass().getMethod(aux, null);
					value = (String) metodo1.invoke(formAttr, objs);
				} catch (Exception e) {
					aux = (String) request.getAttribute(value);
					value = aux;
				}
			}
			aux = text;
			if (text != null) {
				try {
					aux = "get" + aux.substring(0, 1).toUpperCase() + aux.substring(1);
					metodo2 = formAttr.getClass().getMethod(aux, null);
					text = (String) metodo2.invoke(formAttr, objs);
				} catch (Exception e) {
					aux = (String) request.getAttribute(text);
					text = aux;
				}
			}
		}
		if (value == null) {
			value = "";
		}
		if (text == null) {
			text = "";
		}
		StringBuilder strBuffer = new StringBuilder();
		if (lookupInfo != null) {
			if (lookupInfo.getColScriptRef() != null) {
				Iterator itScriptRef = lookupInfo.getColScriptRef().iterator();
				String strScriptRef = "";
				while (itScriptRef.hasNext()) {
					strScriptRef = (String) itScriptRef.next();
					strBuffer.append("<script type='text/javascript' src='" + strScriptRef + "'></script>\n");
				}
			}
			if (lookupInfo.getScript() != null) {
				String strScript = geraScript(lookupInfo.getScript(), colCamposPesq, nameLookup, null);
				strBuffer.append("<script type=\"text/javascript\">");
				strBuffer.append(strScript.replaceAll("\\{id=LOOKUPNAME\\}", nameLookup));
				strBuffer.append("</script>");
			}
			separaCampos = lookupInfo.getSeparaCampos();
		}
		if (javascript.equalsIgnoreCase("true")) {
			strBuffer.append("<script type=\"text/javascript\">\n");
			strBuffer.append("var req" + nameLookup + ";\n");
			strBuffer.append("var url" + nameLookup + ";\n");
			strBuffer.append("function mostrar_" + nameLookup + "() {\n");
			cp = (Campo) itCamposPesq.next();
			// String[] trataNome = cp.getNomeFisico().split("\\.");
			// if(trataNome.length > 1){
			// cp.setNomeFisico(trataNome[1]);
			// }
			strBuffer.append("   " + nameLookup + "_ajustaDivResultado(document.getElementById('retPesq" + nameLookup + "'));\n");
			strBuffer.append("} \n");
			strBuffer.append("function " + nameLookup + "_ajustaDivResultado (dvResult) {\n");
			strBuffer.append("   if (isMozilla){\n");
			strBuffer.append("   	var dvResultTop = dvResult.offsetTop;\n");
			strBuffer.append("  	var dvLookup = document.getElementById('divInt" + nameLookup + "');\n");
			strBuffer.append("   	var h = dvResultTop + 2;\n");
			strBuffer.append("   	dvResult.style.height = " + larg + "- h;\n");
			strBuffer.append("   }else{\n");
			strBuffer.append("   	var dvResultTop = dvResult.offsetTop;\n");
			strBuffer.append("  	var dvLookup = document.getElementById('divInt" + nameLookup + "');\n");
			strBuffer.append("   	var h = dvResultTop + 2;\n");
			strBuffer.append("   	dvResult.style.height = " + larg + "- h;\n");
			strBuffer.append("   }\n");
			strBuffer.append("} \n");
			strBuffer.append("addEvent(window, \"load\", mostrar_" + nameLookup + ", false);");
			strBuffer.append("</script>\n");
		}
		
		int i = 1;
		String nomeFisicoAux = "";
		/* Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 15:30 - ID Citsmart: 120948 - 
		* Motivo/Comentário: Alinhamento dos campos de pesquisa, alterado para div no novo padrão */	
		/* Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 16:57 - ID Citsmart: 120948 - 
		* Motivo/Comentário: Removido alinhamento */
		/* Desenvolvedor: Pedro Lino - Data: 31/10/2013 - Horário: 18:43 - ID Citsmart: 120948 - 
		* Motivo/Comentário: Alterado width da table para 50%  */
		if (html.equalsIgnoreCase("true")) {
			strBuffer.append("<div class='lockupEstrutura' id='divInt" + nameLookup + "'>");
			strBuffer.append("<table width=\"50%\">");
			itCamposPesq = colCamposPesq.iterator();
			String asteristica = "";
			while (itCamposPesq.hasNext()) {
				cp = (Campo) itCamposPesq.next();
				if(i == 1){
					nomeFisicoAux = cp.getNomeFisico();
					i++;
				}
				if (!cp.getMesmalinha().equalsIgnoreCase("fim") && !cp.getMesmalinha().equalsIgnoreCase("meio")) {
					strBuffer.append("<tr>");
					strBuffer.append("<td style=\"font-weight:bold\">");
				}
				strBuffer.append("<label class='pesqLockupDesc'><strong>");
				if (cp.getType().equalsIgnoreCase("HIDDEN")) {
					strBuffer.append("");
				} else {
					strBuffer.append(cp.getDescricao());
				}

				if (cp.isObrigatorio()) {
					asteristica = "<font color='red'>*</font>";
					strBuffer.append(asteristica);
				}
				if (cp.getType().equalsIgnoreCase("HIDDEN")) {
					strBuffer.append("");
				} else {
					strBuffer.append(":");
				}

				strBuffer.append("</strong></label>");
				if (!cp.getMesmalinha().equalsIgnoreCase("fim") && !cp.getMesmalinha().equalsIgnoreCase("meio")) {
					strBuffer.append("</td>");
					strBuffer.append("<td style=\"font-weight:bold\">");
				}
				habilitaPesquisa = (String)((HttpServletRequest)request).getSession().getAttribute("habilitaPesquisa");
				strBuffer.append(geraCampo(cp.getType(), cp.getTamanho(), cp.getNomeFisico(), cp.getScriptLostFocus(), nameLookup, colCamposPesq, colCamposTextGenerateLimpar,
						colCamposComboGenerateLimpar, cp.getColValores(), cp.isObrigatorio(), cp.getDescricao()));
				if (!cp.getMesmalinha().equalsIgnoreCase("inicio") && !cp.getMesmalinha().equalsIgnoreCase("meio")) {
					strBuffer.append("</td>");
					strBuffer.append("</tr>");
				}
				asteristica = "";
			}
			((HttpServletRequest)request).getSession().setAttribute("habilitaPesquisa", "");
			habilitaPesquisa = "";
			strBuffer.append("</table>");
			strBuffer.append("<hr />");
			strBuffer.append("<div class='lockupCampoBotoes'>");
			strBuffer.append("<input title='" + UtilI18N.internacionaliza((HttpServletRequest) request, "citcorpore.ui.botao.rotulo.Pesquisar") + "' type='button' name='btn" + nameLookup
					+ "' id='btnPesquisar' class='ui-button ui-widget ui-corner-all ui-button-text-only btn btn-primary' value='"
					+ UtilI18N.internacionaliza((HttpServletRequest) request, "citcorpore.ui.botao.rotulo.Pesquisar") + "' onclick='pesq_" + nameLookup + "()' />");
			// strBuffer.append("<input title='"+UtilI18N.internacionaliza((HttpServletRequest) request, "citcorpore.ui.botao.rotulo.Listar_Todos")+"' type='button' name='btnTodos" + nameLookup +
			// "' id='btnTodos' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' name='btnTodos" + nameLookup + "' value='"+UtilI18N.internacionaliza((HttpServletRequest)
			// request, "citcorpore.ui.botao.rotulo.Listar_Todos")+"' onclick='pesqTodos_" + nameLookup + "()' />");
			strBuffer.append("<input title='" + UtilI18N.internacionaliza((HttpServletRequest) request, "citcorpore.ui.botao.rotulo.Limpar") + "' type='button' name='btnLimpar" + nameLookup
					+ "'  id='btnLimpar' class='ui-button ui-widget ui-corner-all ui-button-text-only btn btn-primary' value='"
					+ UtilI18N.internacionaliza((HttpServletRequest) request, "citcorpore.ui.botao.rotulo.Limpar") + "' onclick='limpar_" + nameLookup + "()' />");
			// caso seja checkbox aparece o botão de enviar checkados
			if (checkbox != null && checkbox.equals("true")){
			strBuffer.append("<input type='button' name='btnEnviar' id='btnEnviar' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only btn-primary' value='" + getInternacionalizado("citSmart.comum.enviar") + "' title='"+getInternacionalizado("citSmart.comum.enviar")+"' onclick='addAllTabela"+ nameLookup +"();' /></div>");
			}
			strBuffer.append("</div>");
			strBuffer.append("<div class='ui-dialog-content ui-widget-content ui-corner-all lockupResPesq' style='overflow: auto' id='retPesq" + nameLookup + "' >");
			strBuffer.append("</div>");
			strBuffer.append("</div>");
		}
		/* Desenvolvedor: Riubbe Oliveira - Data: 30/10/2013 - Horário: 16:25 - ID Citsmart: 120407 - 
		* Motivo/Comentário: Feito ajuste para dar focus no primeiro input ao clicar em limpar */	
		if (javascript.equalsIgnoreCase("true")) {
			strBuffer.append("<script>\n");
			strBuffer.append("function limpar_" + nameLookup + "(){\n");
			strBuffer.append("   HTMLUtils.clearFormExceptHidden(document." + nomeForm + ");\n");
			strBuffer.append("   if(document.getElementById('retPesq" + nameLookup + "') != null){\n");
			strBuffer.append("        document.getElementById('retPesq" + nameLookup + "').innerHTML = '&nbsp;';\n");
			strBuffer.append("   }\n");
			strBuffer.append("   document." + nomeForm + ".pesqLockup" + nameLookup + "_" + nomeFisicoAux + ".focus();\n");
			strBuffer.append("}\n");

			strBuffer.append("function pesqTodos_" + nameLookup + " (){");
			strBuffer.append("  limpar_" + nameLookup + "();");
			strBuffer.append("  document.getElementById('retPesq" + nameLookup + "').innerHTML = '" + getInternacionalizado("citcorpore.comum.processando_pesquisa") + "'; ");
			strBuffer.append("  pesq_" + nameLookup + "();");
			strBuffer.append("}\n");
			strBuffer.append("var dadosUrl = '';\n");
			strBuffer.append("function pesq_" + nameLookup + "(){\n");
			strBuffer.append("document.getElementById('retPesq" + nameLookup + "').innerHTML = '" + getInternacionalizado("citcorpore.comum.processando_pesquisa") + "'; ");
			strBuffer.append("req" + nameLookup + " = AjaxUtils.defineBrowserAJAX(); ");
			strBuffer.append("req" + nameLookup + ".onreadystatechange = process_" + nameLookup + "; ");
			// --- Processa os campos de pesquisa e atribui os valores p/ chamar
			// o Action
			int count = 1;
			itCamposPesq = colCamposPesq.iterator();
			while (itCamposPesq.hasNext()) {
				cp = (Campo) itCamposPesq.next();
				if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())
						|| cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim()) || cp.getType().equalsIgnoreCase("NUMBER") || cp.getType().equalsIgnoreCase("DATE")
						|| cp.getType().equalsIgnoreCase("HIDDEN")) {
					strBuffer.append("var valor" + nameLookup + count + " = "
							+ "processaCaracteresEspeciais(" 
								+"document." + nomeForm + "." + geraNomeCampo(cp.getType(), cp.getNomeFisico(), nameLookup) + ".value"
							+ ");");
					// strBuffer.append("valor" + nameLookup + count + "= " + "valor" + nameLookup + count + ".replace(/[\'\"]*/g, \'\');");
				}
				count++;
			}
			// ---

			strBuffer.append("  var nocache" + nameLookup + " = new Date();");
			strBuffer.append("	var url" + nameLookup + " = 'ProcessarLookup.find?acao=process&paginacao=0&nomeLookup=" + nomeLookupExec + "&id=" + idAux + "&checkbox="+ checkbox + "");
			for (int iAux = 1; iAux < count; iAux++) {
				strBuffer.append("&parm" + iAux + "='+encodeURIComponent(valor" + nameLookup + iAux + ")+'");
			}
			int icAux = count - 1;
			strBuffer.append("&parmCount=" + icAux + "");
			strBuffer.append("&nocache=' + nocache" + nameLookup + "; \n");
			// strBuffer.append("dadosUrl = url" + nameLookup + ";\n ");
			strBuffer.append("	req" + nameLookup + ".open('GET', url" + nameLookup + ", true); ");

			strBuffer.append("	req" + nameLookup + ".setRequestHeader('Cache-Control', 'no-store, no-cache, must-revalidate'); ");
			strBuffer.append("	req" + nameLookup + ".setRequestHeader('Cache-Control', 'post-check=0, pre-check=0'); ");
			strBuffer.append("	req" + nameLookup + ".setRequestHeader('Pragma', 'no-cache'); ");

			strBuffer.append("  req" + nameLookup + ".send(''); ");
			strBuffer.append("} \n");
			strBuffer.append("</script>\n");
			
			strBuffer.append("<script>\n");
			strBuffer.append("function process_" + nameLookup + " (){\n");
			strBuffer.append("	if (req" + nameLookup + ".readyState == 4){\n");
			strBuffer.append("		if (req" + nameLookup + ".status == 200){\n");
			strBuffer.append("			document.getElementById('retPesq" + nameLookup + "').innerHTML = req" + nameLookup + ".responseText;\n");
			//chamar a função de verificação de checkados após a consulta do paginação chegar ao estado 4(completa)
			strBuffer.append("			memorizarCheckados"+ nameLookup +"();\n" );
			strBuffer.append("		}\n");
			strBuffer.append("	}\n");
			strBuffer.append("} \n");
			strBuffer.append("function semAspas(ie, ff) { if (ie) { tecla = ie; } else { tecla = ff;} \n");
			// strBuffer.append("if (tecla != 34 && tecla != 39 && tecla != 13) { return true; }else { return false; }} \n");
			strBuffer.append("if (tecla != 13) { return true; }else { return false; }} \n");
			strBuffer.append("</script>\n");

			// funcao paginacao
			strBuffer.append("<script>\n");
			strBuffer.append("function paginacao" + nameLookup + "(pag){\n");
			// strBuffer.append("function paginacao(pag){\n");
			strBuffer.append("document.getElementById('retPesq" + nameLookup + "').innerHTML = '" + getInternacionalizado("citcorpore.comum.processando_paginacao") + "'; ");
			strBuffer.append("req" + nameLookup + " = AjaxUtils.defineBrowserAJAX(); ");
			strBuffer.append("req" + nameLookup + ".onreadystatechange = process_" + nameLookup + "; ");
			count = 1;
			itCamposPesq = colCamposPesq.iterator();
			while (itCamposPesq.hasNext()) {
				cp = (Campo) itCamposPesq.next();
				if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())
						|| cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim()) || cp.getType().equalsIgnoreCase("NUMBER") || cp.getType().equalsIgnoreCase("DATE")
						|| cp.getType().equalsIgnoreCase("HIDDEN")) {
					strBuffer.append("var valor" + nameLookup + count + "=document." + nomeForm + "." + geraNomeCampo(cp.getType(), cp.getNomeFisico(), nameLookup) + ".value; ");
				}
				count++;
			}
			strBuffer.append("  var nocache" + nameLookup + " = new Date();");
			strBuffer.append("	var url" + nameLookup + " = 'ProcessarLookup.find?acao=process&paginacao='+pag+'&nomeLookup=" + nomeLookupExec + "&id=" + idAux + "&checkbox="+ checkbox + "");
			for (int iAux = 1; iAux < count; iAux++) {
				strBuffer.append("&parm" + iAux + "='+valor" + nameLookup + iAux + "+'");
			}
			icAux = count - 1;
			strBuffer.append("&parmCount=" + icAux + "");
			strBuffer.append("&nocache=' + nocache" + nameLookup + "; \n");
			strBuffer.append("	req" + nameLookup + ".open('GET', url" + nameLookup + ", true); ");
			strBuffer.append("	req" + nameLookup + ".setRequestHeader('Cache-Control', 'no-store, no-cache, must-revalidate'); ");
			strBuffer.append("	req" + nameLookup + ".setRequestHeader('Cache-Control', 'post-check=0, pre-check=0'); ");
			strBuffer.append("	req" + nameLookup + ".setRequestHeader('Pragma', 'no-cache'); ");
			strBuffer.append("  req" + nameLookup + ".send(''); ");
			strBuffer.append("}\n");
			strBuffer.append("</script>\n");
			//montando a função que marca todos os checkbox da poppup que tem class=check
			strBuffer.append("<script type=\"text/javascript\">");
	        strBuffer.append("function " + nameLookup + "_marcarTodosCheckbox(selecionado) {");
	        strBuffer.append("	var classe = 'check';");
	        strBuffer.append("	if (!$(selecionado).is(':checked')) {");
	        strBuffer.append("		$('.' + classe).each(function() {");
	        strBuffer.append("			$(this).attr('checked', false);");
	        strBuffer.append("			concatenarValoresCheckados_"+  nameLookup  +"(this);");
	        strBuffer.append("		});");
	        strBuffer.append("	}else {");
	        strBuffer.append("		$('.' + classe).each(function() {");
	        strBuffer.append("			$(this).attr('checked', true);");
	        strBuffer.append("			concatenarValoresCheckados_"+  nameLookup  +"(this);");
	        strBuffer.append("		});");
	        strBuffer.append("		};");
	        strBuffer.append("};\n");
	        strBuffer.append("</script>");
			strBuffer.append("<script>\n");
			String retParms = "";
			//função que concatena e desconcatena os checkbox que marcados ou desmarcados
			strBuffer.append("var checkados_" + nameLookup + " = '' ;\n");
			strBuffer.append("function concatenarValoresCheckados_"+  nameLookup  +"(elemento) {\n");
			strBuffer.append("	if (!$(elemento).is(':checked')) {\n");
			strBuffer.append("		checkadosArray = checkados_" + nameLookup + ".split(\";\");\n");
			strBuffer.append("		checkados_"+ nameLookup +" = '' \n");
			strBuffer.append("		for(var i = 0; i < checkadosArray.length; i++){\n");
			strBuffer.append("			if (checkadosArray[i] == elemento.value){\n");
			strBuffer.append("				checkadosArray[i] = '';\n");
			strBuffer.append("			}\n");
			strBuffer.append("		}\n");
			strBuffer.append("		for (var i = 0; i < checkadosArray.length; i++){\n");
			strBuffer.append("			if(checkadosArray[i] != ''){\n");
			strBuffer.append("				checkados_"+ nameLookup +" +=checkadosArray[i]+';';\n");
			strBuffer.append("			}\n");
			strBuffer.append("		}\n");
			strBuffer.append("	}else {\n");
			strBuffer.append("		checkados_"+ nameLookup +" += elemento.value+';';\n");
			strBuffer.append("		}\n");
			strBuffer.append("}\n");
			//Função que enviar todos os checkbox checkados e chama um método requisicaoMudanca.java
			String[] nomePopup = nameLookup.split("LOOKUP");
		    strBuffer.append("addAllTabela"+ nameLookup +" = function(){\n");
			strBuffer.append("	document.form.colAll"+ nameLookup +".value = checkados_" + nameLookup+";\n");
			strBuffer.append("	document.form.fireEvent(\"adicionaTabela"+ nameLookup +"\");\n");
			strBuffer.append("	fecharPopup"+ nomePopup[1] +"();\n");
			strBuffer.append(" }\n");
			strBuffer.append("function fecharPopup"+ nomePopup[1] +"(){");
			strBuffer.append(" 	$('#POPUP"+ nomePopup[1]+"').dialog('close');");
			strBuffer.append("checkados_" + nameLookup + "=''");
			strBuffer.append("}");
			//Função necessária para quando o usuario mudar de página o checkbox continue marcado.
			strBuffer.append("function memorizarCheckados"+ nameLookup +"(){");
			strBuffer.append("	table = document.getElementById('topoRetorno');");
			strBuffer.append("	itens = document.getElementsByName('sel');");
			strBuffer.append("	checkTodosPagina = document.getElementsByName('selTodosPagina');");
			strBuffer.append("	count = 0;");
			strBuffer.append("	var ids = checkados_" + nameLookup + ".split(';');");
			strBuffer.append("	for(i=0; i< itens.length; i++){");
			strBuffer.append("		for (y=0; y< ids.length; y++){");
			strBuffer.append("			if(itens[i].value == ids[y]){");
			strBuffer.append("				itens[i].checked = true;");
			strBuffer.append("				count += 1;");
			strBuffer.append("				break;");
			strBuffer.append("			}");
			strBuffer.append("		}");
			strBuffer.append("	}");
			strBuffer.append("	if (itens.length == count? $(checkTodosPagina).attr('checked', true) : $(checkTodosPagina).attr('checked', false));");
			strBuffer.append("}");
			if ("S".equalsIgnoreCase(separaCampos)) {
				strBuffer.append("function setRetorno_" + nameLookup + " (id,");
				Collection colRet = getCamposRetorno(nameLookup);
				for (int x = 1; x < colRet.size(); x++) {
					retParms += ",ret" + x;
				}
				strBuffer.append(retParms);
				strBuffer.append("){\n");
			} else {
				strBuffer.append("function setRetorno_" + nameLookup + " (id,ret1){");
			}
			if ("S".equalsIgnoreCase(separaCampos)) {
				strBuffer.append("if (" + nameLookup + "_select.length==2){");
				strBuffer.append(nameLookup + "_select(id,ret1);");
				strBuffer.append("}else{");
				strBuffer.append(nameLookup + "_select(id" + retParms + ");");
				strBuffer.append("}");
				strBuffer.append("  limpar_" + nameLookup + "();");
			} else {
				strBuffer.append(nameLookup + "_select(id,ret1);");
				strBuffer.append("  limpar_" + nameLookup + "();");
			}
			strBuffer.append("} ");
			strBuffer.append("</script>\n");
			/**
			 * Função para processar caracteres na string
			 * 22/12/2014
			 * @author thyen.chang
			 */
			strBuffer.append("<script>\n");
			strBuffer.append("function processaCaracteresEspeciais(texto){\n");
			strBuffer.append("	var aux = \"\";\n");
			strBuffer.append("	var caracteresEspeciais = \"%\";\n");
			strBuffer.append("	if(texto !== null && texto !== undefined)\n");
			strBuffer.append("		for(var i = 0; i < texto.length; i++)\n");
			strBuffer.append("			if(caracteresEspeciais.indexOf(texto[i]) > -1)\n");
			strBuffer.append("				aux += \"\\\\\" + texto[i];\n");
			strBuffer.append("			else\n");
			strBuffer.append("				aux += texto[i];\n");
			strBuffer.append("	return aux;\n");
			strBuffer.append("}\n");
			strBuffer.append("</script>\n");
		}
		return strBuffer;
	}

	public Collection getCamposPesquisa(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getColCamposPesquisa();
		/*
		 * Collection col = new ArrayList(); col.add(new Campo("idImposto","Identificação",false,Constantes.FIELDTYPE_TEXT,10)); col.add(new
		 * Campo("Descricao","Descrição",true,Constantes.FIELDTYPE_TEXT,50)); return col;
		 */
	}

	public Collection getCamposRetorno(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getColCamposRetorno();
		/*
		 * Collection col = new ArrayList(); col.add(new Campo("idImposto","Identificação",false,Constantes.FIELDTYPE_TEXT,10)); col.add(new
		 * Campo("Descricao","Descrição",true,Constantes.FIELDTYPE_TEXT,50)); return col;
		 */
	}

	public Collection getCamposOrdenacao(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getColCamposOrdenacao();
		/*
		 * Collection col = new ArrayList(); col.add(new Campo("Descricao","Descrição",true,Constantes.FIELDTYPE_TEXT,50)); return col;
		 */
	}

	public Collection getCamposChave(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getColCamposChave();
		/*
		 * Collection col = new ArrayList(); col.add(new Campo("idImposto","Identificação",false,Constantes.FIELDTYPE_TEXT,10)); return col;
		 */
	}

	public String getDaoProcessor(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getDaoProcessor();
	}

	public String getTabela(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getTabela();
	}

	public String getSeparaCampos(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getSeparaCampos();
	}

	public String getWhere(String nameLookup) {
		if (lookupInfo == null) {
			lookupInfo = xml.getLookup(nameLookup);
		}
		return lookupInfo.getWhere();
	}

	public String geraCampo(String type, int tam, String nome, String scriptLostFocus, String nameLookup, Collection colCamposPesq, Collection colCamposGeradosText, Collection colCamposGeradosCombo,
			Collection colValores, boolean valid, String validDesc) {
		String result = "";
		String[] trataNome = nome.split("\\.");
		String classObrigatorio = "";
		if (trataNome.length > 1) {
			nome = trataNome[0] + "_" + trataNome[1];
		}
		if (valid) {
			classObrigatorio = " Valid[Required] Description[" + validDesc + "] ";
		}
		if (type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || type.equalsIgnoreCase("NUMBER") || type.equalsIgnoreCase("DATE")) {
			if (type.equalsIgnoreCase("NUMBER")) {
				classObrigatorio += " Format[numero] ";
			}
			if (type.equalsIgnoreCase("DATE")) {
				classObrigatorio += " Format[data] datepiker ";
			}
			if (scriptLostFocus == null)
				scriptLostFocus = "";
			String functionName = "";
			if (!scriptLostFocus.trim().equalsIgnoreCase("")) {
				functionName = "scriptLostFocus_" + nameLookup + "_" + nome;
				result += "<script>";
				result += "self." + functionName + " = function(objeto, id){";
				result += geraScript(scriptLostFocus, colCamposPesq, nameLookup, nome);
				result += "}";
				result += "</script>";
			}		
			String disabled = "";
			if(habilitaPesquisa != null && habilitaPesquisa.equalsIgnoreCase("S")){
				disabled = "disabled = 'disabled' ";
			}		
			if (!functionName.equalsIgnoreCase("")) {
				result += "<input onkeydown=\"if ( event.keyCode == 13 ) pesq_" + nameLookup + "();\" class='ui-widget ui-state-default texto " + classObrigatorio + "' type='text' name='pesqLockup"
						+ nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' size='" + tam + "' maxlength='" + tam
						+ "' onkeypress='return semAspas(event.keyCode, event.which);' onblur='" + functionName + "(this, \"pesqLockup" + nameLookup + "_" + nome + "\");' "+disabled+">";
			} else {
				result += "<input onkeydown=\"if ( event.keyCode == 3 || event.keyCode == 13 ){event.keyCode = 3; pesq_" + nameLookup + "();}\" class='ui-widget ui-state-default texto "
						+ classObrigatorio + "' type='text' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' size='" + tam + "' maxlength='" + tam
						+ "' onkeypress='return semAspas(event.keyCode, event.which);' "+disabled+">";
			}
			if (type.equalsIgnoreCase("DATE")) {
				result += "\n<script>\n";
				result += "window.setTimeout(function(){";
				result += "addEvent(document.getElementById('pesqLockup" + nameLookup + "_" + nome + "'),\n";
				result += "		\"keydown\",\n";
				result += "		DEFINEALLPAGES_formataData,\n";
				result += "		false);}, 3000);\n";
				result += "window.setTimeout(function(){";
				result += "addEvent(document.getElementById('pesqLockup" + nameLookup + "_" + nome + "'),\n";
				result += "		\"blur\",\n";
				result += "		DEFINEALLPAGES_formataData,\n";
				result += "		false);\n";
				result += "addEvent(document.getElementById('pesqLockup" + nameLookup + "_" + nome + "'),\n";
				result += "		\"blur\",\n";
				result += "		function(){ValidacaoUtils.validaData(document.getElementById('pesqLockup" + nameLookup + "_" + nome + "'), 'Campo de pesquisa: ')},\n";
				result += "		false);\n";
				result += "		}, 3000);\n";
				result += "</script>\n";
			}
		}
		if (type.equalsIgnoreCase("HIDDEN")) {
			result += "<input type='hidden' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "'>";

			// Nao gera o campo hidden como para limpar.
			// colCamposGeradosText.add("pesqLockup" + nameLookup + "_" + nome);
		}
		if (type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
			if (scriptLostFocus == null)
				scriptLostFocus = "";
			String functionName = "";
			if (!scriptLostFocus.trim().equalsIgnoreCase("")) {
				functionName = "scriptLostFocus_" + nameLookup + "_" + nome;
				result += "<script>\n";
				result += "function " + functionName + "(objeto, id){\n";
				result += geraScript(scriptLostFocus, colCamposPesq, nameLookup, nome);
				result += "\n}\n";
				result += "</script>\n";
			}
			if (!functionName.equalsIgnoreCase("")) {
				result += "<select class='ui-widget ui-state-default texto " + classObrigatorio + "' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome
						+ "' onkeypress='" + nameLookup + "_validaEnter(this,event)' onchange='" + functionName + "(this, \"pesqLockup" + nameLookup + "_" + nome + "\");'>";
			} else {
				result += "<select class='ui-widget ui-state-default texto " + classObrigatorio + "' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome
						+ "' onkeypress='" + nameLookup + "_validaEnter(this,event)'>";
			}
			if (colValores != null) {
				for (Iterator it = colValores.iterator(); it.hasNext();) {
					ItemValorDescricaoDTO itemValorDescricaoDTO = (ItemValorDescricaoDTO) it.next();
					result += "<option value=\"" + itemValorDescricaoDTO.getValor() + "\">" + getInternacionalizado(itemValorDescricaoDTO.getDescricao()) + "</option>";
				}
			}
			result += "</select>";
			colCamposGeradosCombo.add("pesqLockup" + nameLookup + "_" + nome);
		}
		return result;
	}

	public String geraNomeCampo(String type, String nome, String nameLookup) {
		String nomeAux = nome;
		String[] trataNome = nome.split("\\.");
		String classObrigatorio = "";
		if (trataNome.length > 1) {
			nomeAux = trataNome[0] + "_" + trataNome[1];
		}

		String result = "";
		if (type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim()) || type.equalsIgnoreCase("HIDDEN")
				|| type.equalsIgnoreCase("NUMBER") || type.equalsIgnoreCase("DATE")) {
			result = "pesqLockup" + nameLookup + "_" + nomeAux + "";
		}
		return result;
	}

	public String geraScript(String script, Collection colCamposPesq, String nameLookup, String nome) {
		if (colCamposPesq == null)
			return script;
		Iterator it = colCamposPesq.iterator();
		Campo campo;
		String scriptRetorno = script;
		while (it.hasNext()) {
			campo = (Campo) it.next();
			// scriptRetorno = scriptRetorno.replaceAll("\\{id=" + campo.getNomeFisico() + "\\}", "'pesqLockup" + nameLookup + "_" + campo.getNomeFisico() + "'");
			scriptRetorno = scriptRetorno.replaceAll("\\{id=" + campo.getNomeFisico() + "\\}", "'pesqLockup" + nameLookup + "_" + nome + "'");
		}
		scriptRetorno = scriptRetorno.replaceAll("\\{id=LOOKUPNAME\\}", nameLookup);
		scriptRetorno = scriptRetorno.replaceAll("SUBMITLOOKUP", "pesq_" + nameLookup + "()");
		scriptRetorno = scriptRetorno.replaceAll("\\\\n", "" + '\n');
		return scriptRetorno;
	}

	private String getInternacionalizado(String texto) {
		String traduzido = props.getProperty(texto);
		if ((traduzido == null) || (traduzido.length() <= 0)) {
			traduzido = texto;
		}
		return traduzido;
	}

	private Properties getProperties(Locale locale) {
		String fileName = "";
		try {
			if (locale != null && !locale.toString().equals("") && !locale.toString().equals("pt_BR"))
				fileName = "Mensagens_" + locale.toString() + ".properties";
			else
				fileName = "Mensagens.properties";

			props = new Properties();
			ClassLoader load = Mensagens.class.getClassLoader();
			InputStream is = load.getResourceAsStream(fileName);
			if (is == null)
				is = ClassLoader.getSystemResourceAsStream(fileName);
			if (is == null)
				is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);

			try {
				if (is != null) {
					props.load(is);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return props;
	}

}