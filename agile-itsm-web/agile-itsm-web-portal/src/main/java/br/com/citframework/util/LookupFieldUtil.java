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

public class LookupFieldUtil {
    private XmlReadLookup   xml;
    private LookupInfo      lookupInfo;
    private static Properties props;

    public LookupFieldUtil() {
        xml = XmlReadLookup.getInstance();
        lookupInfo = null;
    }
    
    @SuppressWarnings({ "rawtypes", "unused" })
	public StringBuilder generate(ServletRequest request, String nameLookup, String id, int top, int left, int tam, int larg, String nomeForm, String value, String text, String javascript, String html, String disabled, String hide, String maximaze, String checkbox) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
        Collection colCamposPesq = getCamposPesquisa(nameLookup);
        if(colCamposPesq == null)
            colCamposPesq = new ArrayList();
        
        /*
    	 * Recupera a propriedade do locale
    	 * 
    	 * */
    	HttpSession session = ((HttpServletRequest) request).getSession();
    	if(session.getAttribute("locale")!=null)
			getProperties(new Locale(session.getAttribute("locale").toString()));
    	else
    		getProperties(new Locale(""));
        
        Iterator itCamposPesq = colCamposPesq.iterator();
        // Collection colCamposRet = getCamposRetorno(nameLookup);
        // Iterator itCamposRet = colCamposRet.iterator();
        Campo cp;
        String urlIframe = "";
        String separaCampos = "N";
        String hideControl = hide;
        if (hideControl == null){
            hideControl = "false";
        }
        
        if(checkbox == null || checkbox.equals("")){
        	checkbox = "false";
        }

        Collection colCamposTextGenerateLimpar = new ArrayList();
        Collection colCamposComboGenerateLimpar = new ArrayList();
        
        String nomeLookupExec = nameLookup;
        if (id != null) { // Caso venha com ID, o nome fisico é do ID, caso
                            // contrario fica com o do LookupName.
            nameLookup = id;
        }
        String idAux = id;
        if (idAux == null) {
            idAux = "";
        }
        int largAux = larg - 250;

        boolean bDisabled = false;
        if (disabled != null) {
            if ("true".equalsIgnoreCase(disabled)) {
                bDisabled = true;
            }
        }

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
        }else{
            //Se entrar aqui, eh que nao eh Struts, entao vamos ao jeito tradicional.
            if (value != null && !value.equalsIgnoreCase("")) {
                String aux = (String) request.getAttribute(value);
                if (aux == null){ //Se getAttribute for nulo, tenta pegar em getParameter.
                    aux = (String) request.getParameter(value);
                }
                if (aux == null) aux = "";
                value = aux;
            }
            
            if (text != null && !text.equalsIgnoreCase("")) {
                String aux = (String) request.getAttribute(text);
                if (aux == null){ //Se getAttribute for nulo, tenta pegar em getParameter.
                    aux = (String) request.getParameter(text);
                }
                if (aux == null) aux = "";
                text = aux;     
            }
        }

        if (value == null) {
            value = "";
        }
        if (text == null) {
            text = "";
        }

        //urlIframe = "../../include/vazio.jsp";
        urlIframe = "about:blank";
        
        StringBuilder strBuffer = new StringBuilder();
        if (lookupInfo != null){
            if (lookupInfo.getColScriptRef()!=null){
                Iterator itScriptRef = lookupInfo.getColScriptRef().iterator();
                String strScriptRef = "";
                while(itScriptRef.hasNext()){
                    strScriptRef = (String)itScriptRef.next();
                    strBuffer.append("<script type='text/javascript' src='" + strScriptRef + "'></script>\n");
                }
            }
            if (lookupInfo.getScript()!=null){
                String strScript = geraScript(lookupInfo.getScript(), colCamposPesq, nameLookup, null);
                strBuffer.append("<script type=\"text/javascript\">\n");
                strBuffer.append(strScript.replaceAll("\\{id=LOOKUPNAME\\}", nameLookup));
                strBuffer.append("</script>\n");
            }
            separaCampos = lookupInfo.getSeparaCampos();
        }
        if (javascript.equalsIgnoreCase("true")) {
            strBuffer.append("<script type=\"text/javascript\">\n");
            strBuffer.append("var req" + nameLookup + ";\n");
            strBuffer.append("var url" + nameLookup + ";\n");
            strBuffer.append("function " + nameLookup + "_validaEnter(obj, evtKeyPress){\n");
            strBuffer.append("  var nTecla;\n");
            strBuffer.append("  if (evtKeyPress == null || evtKeyPress == undefined){\n");
            strBuffer.append("      evtKeyPress = window.event;\n");
            strBuffer.append("  }\n");
            strBuffer.append("  if (evtKeyPress == null || evtKeyPress == undefined){\n");
            strBuffer.append("      return;\n");
            strBuffer.append("  }\n");
            strBuffer.append("  nTecla = evtKeyPress.charCode? evtKeyPress.charCode : evtKeyPress.keyCode;\n");
            strBuffer.append("  if (nTecla == 13){\n");
            strBuffer.append("      pesq_" + nameLookup + "();\n");
            strBuffer.append("  }\n");
            strBuffer.append("}\n");
            
            strBuffer.append("function mostrar_" + nameLookup + "(){\n");
            strBuffer.append(" try{");
            strBuffer.append("   limparMarcacaoAnterior_" + nameLookup + "();\n");
            strBuffer.append("   document.getElementById('div" + nameLookup + "').style.left='" + left + "px';\n");
            strBuffer.append("   document.getElementById('div" + nameLookup + "').style.top='" + top + "px';\n");
            strBuffer.append("   if (isMozilla){");
            //Nao ha razao para mostrar o iframe no mozilla e firefox.
            strBuffer.append("      document.getElementById('fra" + nameLookup + "').style.display = 'none';\n");
            strBuffer.append("   }else{");
            strBuffer.append("      document.getElementById('fra" + nameLookup + "').style.width = '" + tam + "px';\n");
            strBuffer.append("      document.getElementById('fra" + nameLookup + "').style.height = '" + larg + "px';\n");
            strBuffer.append("   }");
            
            strBuffer.append("   document.getElementById('divAux" + nameLookup + "').style.width = '" + tam + "px';\n");
            strBuffer.append("   document.getElementById('divAux" + nameLookup + "').style.height = '" + larg + "px';\n");

            strBuffer.append("   document.getElementById('divInt" + nameLookup + "').style.left = '0px';\n");
            strBuffer.append("   document.getElementById('divInt" + nameLookup + "').style.top = '0px';\n");
            strBuffer.append("   document.getElementById('divInt" + nameLookup + "').style.width = '" + (tam + 1) + "px';\n");
            strBuffer.append("   document.getElementById('divInt" + nameLookup + "').style.height = '" + larg + "px';\n");
            strBuffer.append("   document.getElementById('div" + nameLookup + "').style.display='block';\n");

            strBuffer.append("   " + nameLookup + "_ajustaDivResultado('retPesq" + nameLookup + "');\n");

            cp = (Campo) itCamposPesq.next();
            //strBuffer.append("   document.getElementById('" + geraNomeCampo(cp.getType(), cp.getNomeFisico(), nameLookup) + "').focus();\n");
            strBuffer.append("   document." + nomeForm + "." + geraNomeCampo(cp.getType(), cp.getNomeFisico(), nameLookup) + ".focus();\n");

            strBuffer.append(" }catch(e){");
            //strBuffer.append("alert(\"function mostrar_" + nameLookup + " - \" + e.message);");
            strBuffer.append(" }");
            strBuffer.append("}\n\n");

            strBuffer.append("function " + nameLookup + "_ajustaDivResultado(nomeDiv){\n");
            strBuffer.append(" try{");
            strBuffer.append("   var dvResult = document.getElementById(nomeDiv);");
            strBuffer.append("   if (!dvResult) return;");
            strBuffer.append("   if (isMozilla){");
            strBuffer.append("      var dvResultTop = dvResult.offsetTop;\n");
            strBuffer.append("      var dvLookup = document.getElementById('div" + nameLookup + "');\n");
            strBuffer.append("      var fraLookup = document.getElementById('FRA" + nameLookup + "');\n");
            
            strBuffer.append("      var h = dvResultTop + 2;\n");

            strBuffer.append("      dvResult.style.height = " + larg + "- h;\n");           
            strBuffer.append("   }else{");
            strBuffer.append("      var dvResultTop = dvResult.offsetTop;\n");
            strBuffer.append("      var dvLookup = document.getElementById('div" + nameLookup + "');\n");
            strBuffer.append("      var fraLookup = document.getElementById('FRA" + nameLookup + "');\n");
            
            strBuffer.append("      fraLookup.style.height = dvLookup.style.height;\n");
            strBuffer.append("      fraLookup.style.width = dvLookup.style.width;\n");          

            strBuffer.append("      var h = dvResultTop + 2;\n");
            strBuffer.append("      dvResult.style.height = " + larg + "- h;\n");
            strBuffer.append("   }");
            strBuffer.append(" }catch(e){");
            //strBuffer.append("alert(\"function " + nameLookup + "_ajustaDivResultado - \" + e.message);");
            strBuffer.append(" }");
            strBuffer.append("}\n");

            strBuffer.append("function esconder_" + nameLookup + "(){\n");
            strBuffer.append("   document.getElementById('div" + nameLookup + "').style.display='none';\n");
            strBuffer.append("}\n");
            
            //---- GERA FUNCAO LIMPAR
            if (colCamposTextGenerateLimpar.size() == 0 && colCamposComboGenerateLimpar.size() == 0){
                itCamposPesq = colCamposPesq.iterator();
                while (itCamposPesq.hasNext()) {
                    cp = (Campo) itCamposPesq.next();
                    //Faz esta chamada apenas para descobrir os campos que precisa gerar o LIMPAR.
                    geraCampo(cp.getType(), cp.getTamanho(), cp.getNomeFisico(), cp.getScriptLostFocus(),nameLookup, colCamposPesq, colCamposTextGenerateLimpar, colCamposComboGenerateLimpar, cp.getColValores());
                }   
            }
            strBuffer.append("function limpar_" + nameLookup + "(){\n");
            strBuffer.append(" try{");
                for(Iterator it = colCamposTextGenerateLimpar.iterator(); it.hasNext();){
                    String str = (String)it.next();
                    strBuffer.append("   document.getElementById('" + str + "').value='';\n");
                }
                for(Iterator it = colCamposComboGenerateLimpar.iterator(); it.hasNext();){
                    String str = (String)it.next();
                    strBuffer.append("try{");
                    strBuffer.append("   document.getElementById('" + str + "').selectedIndex = 0;\n"); 
                    strBuffer.append("}catch(ex){");
                    //strBuffer.append("alert(\"function limpar_" + nameLookup + " - \" + ex.message);");
                    strBuffer.append("}");
                }
                strBuffer.append("document.getElementById('retPesq" + nameLookup + "').innerHTML = '';\n");
                strBuffer.append(" }catch(e){");
                //strBuffer.append("alert(e.message);");
                strBuffer.append(" }");
            strBuffer.append("}\n");
            //---- FIM - GERA FUNCAO LIMPAR
            
            strBuffer.append("</script>\n");
        }

        if (html.equalsIgnoreCase("true")) {
            String titleBackcolor = Constantes.getValue("JANELAPOPUP_TITLE_BACKCOLOR");
            if(!UtilStrings.isNotVazio(titleBackcolor))
                titleBackcolor = "#E3F0FD";
            String backcolor = Constantes.getValue("JANELAPOPUP_BACKGROUND");
            if(!UtilStrings.isNotVazio(backcolor))
                backcolor = "#A6CBF0";
            
            strBuffer.append("<div id='div" + nameLookup + "' style='display:none;position:absolute;' class='dragme'>\n");
            strBuffer.append("<div id='divAux" + nameLookup + "'><iframe id='fra" + nameLookup + "' src='" + urlIframe + "'></iframe></div>\n");
            strBuffer.append("<div style='position:absolute;background:" +backcolor+ ";border:1px solid #485260' id='divInt" + nameLookup + "' class='cabecalholookup ui-corner-all'>\n");            
            strBuffer.append("<div class='titulolookup' >");
            strBuffer.append("<h4>");
            strBuffer.append(getInternacionalizado("citcorpore.comum.localizar"));
            strBuffer.append("</h4>");
            strBuffer.append("<div><a href='#' onclick='esconder_" + nameLookup + "();'>");
            strBuffer.append("<img alt='"+getInternacionalizado("citcorpore.comum.fechar_janela")+"' onclick='esconder_" + nameLookup + "();' src='" + Constantes.getValue("SERVER_ADDRESS") + ((HttpServletRequest) request).getContextPath() + "/imagens/fecharLookup.gif'/>");
            strBuffer.append("</a>");
            strBuffer.append("</div>");
            strBuffer.append("</div>");

            strBuffer.append("<table style='padding: 5px;' width=\"100%\">\n");
            itCamposPesq = colCamposPesq.iterator();
            while (itCamposPesq.hasNext()) {
                cp = (Campo) itCamposPesq.next();
                if (!cp.getType().equalsIgnoreCase("HIDDEN")){
                    if (!cp.getMesmalinha().equalsIgnoreCase("fim") && !cp.getMesmalinha().equalsIgnoreCase("meio")){
                        strBuffer.append("<tr>\n");
                        strBuffer.append("<td style=\"font:11px verdana; font-weight:bold\">\n");
                    }
                    strBuffer.append(cp.getDescricao());
                    if (cp.isObrigatorio()) {
                        strBuffer.append("*\n");
                    }
                    strBuffer.append(":\n");
                    if (!cp.getMesmalinha().equalsIgnoreCase("fim") && !cp.getMesmalinha().equalsIgnoreCase("meio")){
                        strBuffer.append("</td>\n");
                        strBuffer.append("<td style=\"font:11px verdana; font-weight:bold\">\n");
                    }
                }
                strBuffer.append(geraCampo(cp.getType(), cp.getTamanho(), cp.getNomeFisico(), cp.getScriptLostFocus(),nameLookup, colCamposPesq, colCamposTextGenerateLimpar, colCamposComboGenerateLimpar, cp.getColValores()));
                if (!cp.getType().equalsIgnoreCase("HIDDEN")){
                    if (!cp.getMesmalinha().equalsIgnoreCase("inicio") && !cp.getMesmalinha().equalsIgnoreCase("meio")){
                        strBuffer.append("</td>\n");
                        strBuffer.append("</tr>\n");
                    }
                }
            }
            strBuffer.append("<tr>\n");
            strBuffer.append("<td colspan='2'>\n");
            strBuffer.append("<div id=\"controle\" style='text-align:center'>");
            strBuffer.append("<table style='text-align:center' width='100%'>");
            strBuffer.append("<tr>");
            strBuffer.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>");

            strBuffer.append("<td><button type='button' name='btn" + nameLookup + "Pesquisar' class='light' onclick='pesq_" + nameLookup + "()' >");
            strBuffer.append("<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+ "/template_new/images/icons/small/grey/magnifying_glass.png' />");
            strBuffer.append("<span>"+getInternacionalizado("citcorpore.ui.botao.rotulo.Pesquisar")+"</span></button></td>");

//            strBuffer.append("<td><button type='button' name='btnTodos" + nameLookup + "' class='light' onclick='pesqTodos_" + nameLookup + "()' >");
//            strBuffer.append("<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+ "/template_new/images/icons/small/grey/magnifying_glass.png' />");
//            strBuffer.append("<span>"+getInternacionalizado("citcorpore.ui.botao.rotulo.Listar_Todos")+"</span></button></td>");
//            
            strBuffer.append("<td><button type='button' name='btn" + nameLookup + "Limpar' onclick='limpar_" + nameLookup + "();' class='light'>");
            strBuffer.append("<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+ "/template_new/images/icons/small/grey/clear.png' />");
            strBuffer.append("<span>"+getInternacionalizado("citcorpore.ui.botao.rotulo.Limpar")+"</span></button></td>");
            
         // caso seja checkbox aparece o botão de enviar checkados
         	if (checkbox != null && checkbox.equals("true")){
         		strBuffer.append("<input type='button' name='btnEnviar' id='btnEnviar' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' value='" + getInternacionalizado("citSmart.comum.enviar") + "' title='"+getInternacionalizado("citSmart.comum.enviar")+"' onclick='addAllTabela"+ nameLookup +"();' /></div>");
         	}
                        
            strBuffer.append("<td><button type='button' name='btn" + nameLookup + "Fechar' class='light' onclick='esconder_" + nameLookup + "();' >");
            strBuffer.append("<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+ "/template_new/images/icons/small/grey/home_2.png' />");
            strBuffer.append("<span>"+getInternacionalizado("citcorpore.comum.fechar")+"</span></button></td>");
            
            strBuffer.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
            strBuffer.append("</tr>");
            strBuffer.append("</table></div>");
            strBuffer.append("</td>\n");
            strBuffer.append("</tr>\n");

            strBuffer.append("</table>\n");

            strBuffer.append("<div id='retPesq" + nameLookup + "' class='rodapelookup' style='background:#FFFFFF;overflow:auto;z-index:3;'>\n");

            strBuffer.append("</div>\n");

            strBuffer.append("</div>\n");
            strBuffer.append("</div>\n");
            
            if (hideControl.equalsIgnoreCase("true")){
                strBuffer.append("<div style='display:none'>");
            }
            strBuffer.append("<input type=\"hidden\" name=\"id" + nameLookup + "\" id=\"id" + nameLookup + "\" value=\"" + value + "\">");
            strBuffer.append("<input type=\"text\" name=\"txtDESC" + nameLookup + "\" id=\"txtDESC" + nameLookup + "\" value=\"" + text + "\" size=\"50\" onfocus=\"javascript:mostrar_" + nameLookup + "();\" readonly>");
            String span = "";
            if (bDisabled) {
                span = "<span style=\"display:none\" id=\"spanLocalizar" + nameLookup + "\">";
            } else {
                span = "<span id=\"spanLocalizar" + nameLookup + "\">";
            }
            strBuffer.append(span + "<a href=\"javascript:mostrar_" + nameLookup + "();\">");
            strBuffer.append("<img src=\"");
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String host = "";
            try {
                host = Constantes.getValue("SERVER_ADDRESS");
            } catch (Exception e) {
                host = "";
            }
            strBuffer.append(host + httpRequest.getContextPath());
            strBuffer.append("/imagens/localizarLookup.gif");
            strBuffer.append("\" border=\"0\" alt=\"Localizar\"/>");
            strBuffer.append("</a>" + "</span>");
            if (hideControl.equalsIgnoreCase("true")){
                strBuffer.append("</div>");
            }           
        }

        if (javascript.equalsIgnoreCase("true")) {
            strBuffer.append("<script>\n\n");
            strBuffer.append("function pesqTodos_" + nameLookup + " (){");
            strBuffer.append("  limpar_" + nameLookup + "();");
            strBuffer.append("  document.getElementById('retPesq" + nameLookup + "').innerHTML = '"+getInternacionalizado("citcorpore.comum.processando_pesquisa")+"'; ");
            strBuffer.append("  pesq_" + nameLookup + "();");
            strBuffer.append("}\n");
            
            strBuffer.append("function pesq_" + nameLookup + "(){\n");
            strBuffer.append("document.getElementById('retPesq" + nameLookup + "').innerHTML = '"+getInternacionalizado("citcorpore.comum.processando_pesquisa")+"';");
            // strBuffer.append("req"+nameLookup+" = new XMLHttpRequest();");
            strBuffer.append("req" + nameLookup + " = AjaxUtils.defineBrowserAJAX();\n");
            strBuffer.append("req" + nameLookup + ".onreadystatechange = process_" + nameLookup + ";\n");
            strBuffer.append("if(req" + nameLookup + "){\n");
            // --- Processa os campos de pesquisa e atribui os valores p/ chamar
            // o Action
            int count = 1;
            itCamposPesq = colCamposPesq.iterator();
            while (itCamposPesq.hasNext()) {
                cp = (Campo) itCamposPesq.next();
                if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || 
                        cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim()) ||
                        cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim()) ||
                        cp.getType().equalsIgnoreCase("NUMBER") ||
                        cp.getType().equalsIgnoreCase("DATE") ||
                        cp.getType().equalsIgnoreCase("HIDDEN")) {
                    strBuffer.append("var valor" + nameLookup + count + " = document.getElementById('" + geraNomeCampo(cp.getType(), cp.getNomeFisico(), nameLookup) + "').value;\n");
                    //strBuffer.append("var valor" + nameLookup + count + "=document." + nomeForm + "." + geraNomeCampo(cp.getType(), cp.getNomeFisico(), nameLookup) + ".value;\n");
                }
                count++;
            }
            // ---
            strBuffer.append("  var nocache" + nameLookup + " = new Date();"); 
            strBuffer.append("  url" + nameLookup + " = 'ProcessarLookup.find?paginacao=0&checkbox="+ checkbox +"';\n");
		//	strBuffer.append("	var url" + nameLookup + " = 'ProcessarLookup.find?acao=process&paginacao=0&nomeLookup=" + nomeLookupExec + "&id=" + idAux);
            strBuffer.append("  var strQuery = 'acao=process&nomeLookup=" + nomeLookupExec + "&id=" + idAux + "");
            for (int iAux = 1; iAux < count; iAux++) {
                strBuffer.append("&parm" + iAux + "='+encodeURIComponent(" + nameLookup + "_converteCaracteresEspeciais(valor" + nameLookup + iAux + "))+'");
            }
            int icAux = count - 1;
            strBuffer.append("&parmCount=" + icAux + "");
            strBuffer.append("&nocache=' + nocache" + nameLookup + ";\n");
            strBuffer.append("  req" + nameLookup + ".open('POST', url" + nameLookup + ", true);\n");
            
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('Content-type', 'application/x-www-form-urlencoded');");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('encoding', 'UTF-8');");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('charset', 'UTF-8');");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('Cache-Control', 'no-store, no-cache, must-revalidate');");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('Cache-Control', 'post-check=0, pre-check=0');");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('Content-length', strQuery.length);");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('Connection', 'close');");
            strBuffer.append("  req" + nameLookup + ".setRequestHeader('Pragma', 'no-cache');");
            
            strBuffer.append("  req" + nameLookup + ".send(strQuery);\n");
            strBuffer.append("}\n");
            strBuffer.append("}\n");
            strBuffer.append("function process_" + nameLookup + "(){\n");
            strBuffer.append("	if (req" + nameLookup + ".readyState == 4){\n");
            strBuffer.append("  	if (req" + nameLookup + ".status == 200){\n");
            strBuffer.append("			document.getElementById('retPesq" + nameLookup + "').innerHTML = req" + nameLookup + ".responseText;\n");
            //chamar a função de verificação de checkados após a consulta do paginação chegar ao estado 4(completa)
			strBuffer.append("			memorizarCheckados"+ nameLookup +"();\n" );
            strBuffer.append("			var campo = document.getElementById('" + nameLookup + "_sel00001');\n");
            strBuffer.append("			if (campo) campo.focus()");
            strBuffer.append("  	}\n");
            strBuffer.append("	}\n");
            strBuffer.append("}\n");
            
            String retParms = "";
            if ("S".equalsIgnoreCase(separaCampos)){
                strBuffer.append("function setRetorno_" + nameLookup + "(id");
                Collection colRet = getCamposRetorno(nameLookup);
                for(int x = 1; x < colRet.size(); x++){
                    retParms += ",ret" + x;
                }
                strBuffer.append(retParms);
                strBuffer.append("){\n");
            }else{
                strBuffer.append("function setRetorno_" + nameLookup + "(id,ret1){\n");
            }
           	strBuffer.append("document.getElementById('id" + nameLookup + "').value=id;\n");
           	strBuffer.append("document.getElementById('txtDESC" + nameLookup + "').value=ret1;\n");
           	//strBuffer.append("document." + nomeForm + ".id" + nameLookup + ".value=id;\n");
            //strBuffer.append("document." + nomeForm + ".txtDESC" + nameLookup + ".value=ret1;\n");
            strBuffer.append("esconder_" + nameLookup + "();\n");
            
            if ("S".equalsIgnoreCase(separaCampos)){
                strBuffer.append("if (" + nameLookup + "_select.length==2){\n");
                    strBuffer.append(nameLookup + "_select(id,ret1);\n");
                strBuffer.append("}else{\n");
                    strBuffer.append(nameLookup + "_select(id" + retParms + ");\n");
                strBuffer.append("}\n");
            }else{
                strBuffer.append(nameLookup + "_select(id,ret1);\n");
            }
            
            strBuffer.append("}\n");
            
            strBuffer.append("var " + nameLookup + "_IdObjectRadio = '';\n");
            strBuffer.append("function destacaLinha_" + nameLookup + "(id,ret1,event){\n");
            strBuffer.append("try{\n");
            strBuffer.append("  limparMarcacaoAnterior_" + nameLookup + "();\n");
            strBuffer.append("  var evt = (event) ? event : window.event;\n");
            strBuffer.append("  var objSel = (evt.target) ? evt.target : evt.srcElement;\n");
            strBuffer.append("  objSel = document.getElementById(objSel.id);\n");
            strBuffer.append("  " + nameLookup + "_IdObjectRadio = objSel.id;\n");
            strBuffer.append("  var o2 = objSel.parentNode;\n");
            strBuffer.append("  var o3 = o2.parentNode;\n");
            strBuffer.append("  HTMLUtil_TrowOn(o3, '#00ced1');\n");
            strBuffer.append("}catch(e){\n");
            //strBuffer.append("alert(\"function destacaLinha_" + nameLookup+" - \" + e.message);");
            strBuffer.append("}\n");            
            strBuffer.append("}\n");
            
            strBuffer.append("function limparMarcacaoAnterior_" + nameLookup + "(){\n");
            strBuffer.append("try{\n");
            strBuffer.append("  if (" + nameLookup + "_IdObjectRadio != ''){\n");
            strBuffer.append("      objDeSel = document.getElementById(" + nameLookup + "_IdObjectRadio);\n");
            strBuffer.append("      var oAux2 = objDeSel.parentNode;\n");
            strBuffer.append("      var oAux3 = oAux2.parentNode;\n");
            strBuffer.append("      HTMLUtil_TrowOn(oAux3, 'white');\n");
            strBuffer.append("      " + nameLookup + "_IdObjectRadio = '';\n");
            strBuffer.append("  }\n");          
            strBuffer.append("}catch(e){\n");
            //strBuffer.append("alert(\"function limparMarcacaoAnterior_" + nameLookup+" - \" + e.message);");
            strBuffer.append("}\n");            
            strBuffer.append("}\n");
            
            
            if ("S".equalsIgnoreCase(separaCampos)){
                strBuffer.append("function keyPressLinha_" + nameLookup + "(id");
                Collection colRet = getCamposRetorno(nameLookup);
                for(int x = 1; x < colRet.size(); x++){
                    retParms += ",ret" + x;
                }
                strBuffer.append(retParms);
                strBuffer.append(",evtKeyPress){\n");
            }else{
                strBuffer.append("function keyPressLinha_" + nameLookup + "(id,ret1,evtKeyPress){\n");
            }           
            strBuffer.append("  var nTecla;");
            strBuffer.append("  if (evtKeyPress == null || evtKeyPress == undefined){");
            strBuffer.append("      evtKeyPress = window.event;");
            strBuffer.append("  }");
            strBuffer.append("  nTecla = evtKeyPress.charCode? evtKeyPress.charCode : evtKeyPress.keyCode;");
            strBuffer.append("  if (nTecla == 13){");   
            if ("S".equalsIgnoreCase(separaCampos)){
                strBuffer.append("      setRetorno_" + nameLookup + "(id");
                Collection colRet = getCamposRetorno(nameLookup);
                for(int x = 1; x < colRet.size(); x++){
                    retParms += ",ret" + x;
                }
                strBuffer.append(retParms);
                strBuffer.append(");\n");
            }else{
                strBuffer.append("      setRetorno_" + nameLookup + "(id,ret1);\n");
            }
            strBuffer.append("  }");            
            strBuffer.append("}\n");
            
            strBuffer.append("</script>\n");
          //funcao paginacao
			strBuffer.append("<script>\n");
			strBuffer.append("function paginacao" + nameLookup + "(pag){\n");
	//		strBuffer.append("function paginacao(pag){\n");
			strBuffer.append("document.getElementById('retPesq" + nameLookup + "').innerHTML = '"+getInternacionalizado("citcorpore.comum.processando_paginacao")+"'; ");
			strBuffer.append("req" + nameLookup + " = AjaxUtils.defineBrowserAJAX(); ");
			strBuffer.append("req" + nameLookup + ".onreadystatechange = process_" + nameLookup + "; ");
			count = 1;
			itCamposPesq = colCamposPesq.iterator();
			while (itCamposPesq.hasNext()) {
				cp = (Campo) itCamposPesq.next();
				if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || 
						cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim()) ||
						cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim()) ||
						cp.getType().equalsIgnoreCase("NUMBER") ||
						cp.getType().equalsIgnoreCase("DATE") ||
						cp.getType().equalsIgnoreCase("HIDDEN")) {						
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
            strBuffer.append("<script>\n\n");
          //função que concatena e desconcatena os checkbox marcados ou desmarcados
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
			
            strBuffer.append("function " + nameLookup + "() { }");

            strBuffer.append("" + nameLookup + ".value = function(){");
            strBuffer.append("    return document.getElementById('id" + nameLookup + "').value;");
            strBuffer.append("};");

            strBuffer.append("" + nameLookup + ".text = function(){");
            strBuffer.append("    return document.getElementById('txtDESC" + nameLookup + "').value;");
            strBuffer.append("};");

            strBuffer.append("" + nameLookup + ".setvalue = function(v){");
            strBuffer.append("    document.getElementById('id" + nameLookup + "').value = v;");
            strBuffer.append("};");

            strBuffer.append("" + nameLookup + ".settext = function(t){");
            strBuffer.append("    document.getElementById('txtDESC" + nameLookup + "').value = t;");
            strBuffer.append("};");

            strBuffer.append("" + nameLookup + ".setdisabled = function(t){");
            strBuffer.append("    if (t){");
            strBuffer.append("      document.getElementById('spanLocalizar" + nameLookup + "').style.display = 'none';");
            strBuffer.append("    }else{");
            strBuffer.append("      document.getElementById('spanLocalizar" + nameLookup + "').style.display = 'block';");
            strBuffer.append("    }");
            strBuffer.append("};");
            
            strBuffer.append("" + nameLookup + ".show = function(){");
            strBuffer.append("    mostrar_" + nameLookup + "();");
            strBuffer.append("};");
            
            strBuffer.append("" + nameLookup + ".hide = function(){");
            strBuffer.append("    esconder_" + nameLookup + "();");
            strBuffer.append("};");
            
            strBuffer.append("" + nameLookup + ".clear = function(){");
            strBuffer.append("    limpar_" + nameLookup + "();");
            strBuffer.append("};");
            
            strBuffer.append("" + nameLookup + ".clearLastMark = function(){");
            strBuffer.append("    limparMarcacaoAnterior_" + nameLookup + "();");
            
            strBuffer.append("};\n");
            
            String trata_carac_esp = "\n";
            trata_carac_esp += "function " + nameLookup + "_converteCaracteresEspeciais(str){";
            trata_carac_esp += "    if (str == undefined || str == null || str.length == 0) return \"\";";
            trata_carac_esp += "    var encoded = '';                         ";
            trata_carac_esp += "    var strFinal = '';                        ";
            trata_carac_esp += "    for (var i = 0; i < str.length; i++){     ";
            trata_carac_esp += "        encoded = '';                           ";
            trata_carac_esp += "        c = str.charAt(i);                      ";
            trata_carac_esp += "        if (c == 'ç')                           ";
            trata_carac_esp += "            encoded = \"[[[cedilhamin]]]\";       ";
            trata_carac_esp += "        else if (c == 'Ç')                      ";
            trata_carac_esp += "            encoded = \"[[[cedilhamai]]]\";       ";
            trata_carac_esp += "        else if (c == 'á')                      ";
            trata_carac_esp += "            encoded = \"[[[aagudomin]]]\";        ";
            trata_carac_esp += "        else if (c == 'Á')                      ";
            trata_carac_esp += "            encoded = \"[[[aagudomai]]]\";        ";
            trata_carac_esp += "        else if (c == 'à')                      ";
            trata_carac_esp += "            encoded = \"[[[acrasemin]]]\";        ";
            trata_carac_esp += "        else if (c == 'À')                      ";
            trata_carac_esp += "            encoded = \"[[[acrasemai]]]\";        ";
            trata_carac_esp += "        else if (c == 'é')                      ";
            trata_carac_esp += "            encoded = \"[[[eagudomin]]]\";        ";
            trata_carac_esp += "        else if (c == 'É')                      ";
            trata_carac_esp += "            encoded = \"[[[eagudomai]]]\";        ";
            trata_carac_esp += "        else if (c == 'í')                      ";
            trata_carac_esp += "            encoded = \"[[[iagudomin]]]\";        ";
            trata_carac_esp += "        else if (c == 'Í')                      ";
            trata_carac_esp += "            encoded = \"[[[iagudomai]]]\";        ";
            trata_carac_esp += "        else if (c == 'ó')                      ";
            trata_carac_esp += "            encoded = \"[[[oagudomin]]]\";        ";
            trata_carac_esp += "        else if (c == 'Ó')                      ";
            trata_carac_esp += "            encoded = \"[[[oagudomai]]]\";        ";
            trata_carac_esp += "        else if (c == 'ú')                      ";
            trata_carac_esp += "            encoded = \"[[[uagudomin]]]\";        ";
            trata_carac_esp += "        else if (c == 'Ú')                      ";
            trata_carac_esp += "            encoded = \"[[[uagudomai]]]\";        ";
            trata_carac_esp += "        else if (c == 'â')                      ";
            trata_carac_esp += "            encoded = \"[[[acircmin]]]\";         ";
            trata_carac_esp += "        else if (c == 'Â')                      ";
            trata_carac_esp += "            encoded = \"[[[acircmai]]]\";         ";
            trata_carac_esp += "        else if (c == 'ê')                      ";
            trata_carac_esp += "            encoded = \"[[[ecircmin]]]\";         ";
            trata_carac_esp += "        else if (c == 'Ê')                      ";
            trata_carac_esp += "            encoded = \"[[[ecircmai]]]\";         ";
            trata_carac_esp += "        else if (c == 'î')                      ";
            trata_carac_esp += "            encoded = \"[[[icircmin]]]\";         ";
            trata_carac_esp += "        else if (c == 'Î')                      ";
            trata_carac_esp += "            encoded = \"[[[icircmai]]]\";         ";
            trata_carac_esp += "        else if (c == 'ô')                      ";
            trata_carac_esp += "            encoded = \"[[[ocircmin]]]\";         ";
            trata_carac_esp += "        else if (c == 'Ô')                      ";
            trata_carac_esp += "            encoded = \"[[[ocircmai]]]\";         ";
            trata_carac_esp += "        else if (c == 'û')                      ";
            trata_carac_esp += "            encoded = \"[[[ucircmin]]]\";         ";
            trata_carac_esp += "        else if (c == 'Û')                      ";
            trata_carac_esp += "            encoded = \"[[[ucircmai]]]\";         ";
            trata_carac_esp += "        else if (c == 'ã')                      ";
            trata_carac_esp += "            encoded = \"[[[atilmin]]]\";          ";
            trata_carac_esp += "        else if (c == 'Ã')                      ";
            trata_carac_esp += "            encoded = \"[[[atilmai]]]\";          ";
            trata_carac_esp += "        else if (c == 'õ')                      ";
            trata_carac_esp += "            encoded = \"[[[otilmin]]]\";          ";
            trata_carac_esp += "        else if (c == 'Õ')                      ";
            trata_carac_esp += "            encoded = \"[[[otilmai]]]\";          ";
            trata_carac_esp += "        else if (c == '&')                      ";
            trata_carac_esp += "            encoded = \"[[[ehcomercial]]]\";     ";
            trata_carac_esp += "        else                                    ";
            trata_carac_esp += "            encoded = c;                          ";
            trata_carac_esp += "                                                  ";
            trata_carac_esp += "        strFinal += encoded;                    ";
            trata_carac_esp += "    }                                           ";
            trata_carac_esp += "                                              ";
            trata_carac_esp += "    return strFinal;                          ";
            trata_carac_esp += "}"; 
            strBuffer.append(trata_carac_esp + "\n");

            strBuffer.append("</script>\n");
        }
        return strBuffer;
    }

    @SuppressWarnings("rawtypes")
	public Collection getCamposPesquisa(String nameLookup) {
        if (lookupInfo == null) {
            lookupInfo = xml.getLookup(nameLookup);
        }
        return lookupInfo.getColCamposPesquisa();
        /*
         * Collection col = new ArrayList(); col.add(new
         * Campo("idImposto","Identificação",false,Constantes.FIELDTYPE_TEXT,10));
         * col.add(new
         * Campo("Descricao","Descrição",true,Constantes.FIELDTYPE_TEXT,50));
         * return col;
         */
    }
    @SuppressWarnings("rawtypes")
	public Collection getCamposRetorno(String nameLookup) {
        if (lookupInfo == null) {
            lookupInfo = xml.getLookup(nameLookup);
        }
        return lookupInfo.getColCamposRetorno();
        /*
         * Collection col = new ArrayList(); col.add(new
         * Campo("idImposto","Identificação",false,Constantes.FIELDTYPE_TEXT,10));
         * col.add(new
         * Campo("Descricao","Descrição",true,Constantes.FIELDTYPE_TEXT,50));
         * return col;
         */
    }
    @SuppressWarnings("rawtypes")
	public Collection getCamposOrdenacao(String nameLookup) {
        if (lookupInfo == null) {
            lookupInfo = xml.getLookup(nameLookup);
        }
        return lookupInfo.getColCamposOrdenacao();
        /*
         * Collection col = new ArrayList(); col.add(new
         * Campo("Descricao","Descrição",true,Constantes.FIELDTYPE_TEXT,50));
         * return col;
         */
    }
    @SuppressWarnings("rawtypes")
	public Collection getCamposChave(String nameLookup) {
        if (lookupInfo == null) {
            lookupInfo = xml.getLookup(nameLookup);
        }
        return lookupInfo.getColCamposChave();
        /*
         * Collection col = new ArrayList(); col.add(new
         * Campo("idImposto","Identificação",false,Constantes.FIELDTYPE_TEXT,10));
         * return col;
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String geraCampo(String type, int tam, String nome, String scriptLostFocus, String nameLookup, Collection colCamposPesq, Collection colCamposGeradosText, Collection colCamposGeradosCombo, Collection colValores) {
        String result = "";
        if (type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || type.equalsIgnoreCase("NUMBER") || type.equalsIgnoreCase("DATE")) {
            if (scriptLostFocus == null) scriptLostFocus = "";
            String functionName = "";
            if (!scriptLostFocus.trim().equalsIgnoreCase("")){
                functionName = "scriptLostFocus_" + nameLookup + "_" + nome;
                result += "<script>\n";
                result += "function " + functionName + "(objeto, id){\n";
                result += "try{\n";
                    result += geraScript(scriptLostFocus, colCamposPesq, nameLookup, nome);
                result += "}catch(e){\n";
                //result += "alert(\"scriptLostFocus_" + nameLookup+" - \" + e.message);";
                result += "}\n";    
                result += "\n}\n";
                result += "function somenteNumeros(objeto, id){\n";
                result += "var tecla=(window.event)?event.keyCode:e.which;\n";
                result += "if((tecla>47 && tecla<58)) return true;\n";
                result += "else{\n";
                result += "if (tecla==8 || tecla==0) return true;\n";
                result += "else  return false;\n";
                result += "\n}\n";
                result += "\n}\n";
                result += "</script>\n";
            }
            if (!functionName.equalsIgnoreCase("")){
            	if(type.equalsIgnoreCase("NUMBER")){
            		result += "<input type='text' class='text' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' size='" + tam + "' maxlength='" + tam + "' onkeydown='" + nameLookup + "_validaEnter(this,event)'  onkeypress='return SomenteNumero(event)' onblur='" + functionName + "(this, \"pesqLockup" + nameLookup + "_" + nome + "\");'>";
            	}else{
            		result += "<input type='text' class='text' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' size='" + tam + "' maxlength='" + tam + "' onkeydown='" + nameLookup + "_validaEnter(this,event)' onblur='" + functionName + "(this, \"pesqLockup" + nameLookup + "_" + nome + "\");'>";
            	}
            }else{
            	if(type.equalsIgnoreCase("NUMBER")){
            		result += "<input type='text' class='text' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' size='" + tam + "' maxlength='" + tam + "' onkeydown='" + nameLookup + "_validaEnter(this,event)' onkeypress='return SomenteNumero(event)'>";
            	}else{
            		result += "<input type='text' class='text' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' size='" + tam + "' maxlength='" + tam + "' onkeydown='" + nameLookup + "_validaEnter(this,event)'>";
            	}          
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
				result += "		function(){ValidacaoUtils.validaData(document.getElementById('pesqLockup" + nameLookup + "_" + nome + "'), '"+getInternacionalizado("citcorpore.comum.campo_pesquisa")+"')},\n"; 
				result += "		false);\n";				
				result += "		}, 3000);\n";
				result += "</script>\n";
			}			
            colCamposGeradosText.add("pesqLockup" + nameLookup + "_" + nome);
        }
        if (type.equalsIgnoreCase("HIDDEN")) {
            result += "<input type='HIDDEN' name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "'>";
            
            //Nao gera o campo hidden como para limpar.
            //colCamposGeradosText.add("pesqLockup" + nameLookup + "_" + nome);
        }
        if (type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
            if (scriptLostFocus == null) scriptLostFocus = "";
            String functionName = "";
            if (!scriptLostFocus.trim().equalsIgnoreCase("")){
                functionName = "scriptLostFocus_" + nameLookup + "_" + nome;
                result += "<script>\n";
                result += "function " + functionName + "(objeto, id){\n";
                    result += geraScript(scriptLostFocus, colCamposPesq, nameLookup, nome);
                result += "\n}\n";
                result += "</script>\n";
            }
            if (!functionName.equalsIgnoreCase("")){
                result += "<select name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' onkeypress='" + nameLookup + "_validaEnter(this,event)' onchange='" + functionName + "(this, \"pesqLockup" + nameLookup + "_" + nome + "\");'>";
            }else{
                result += "<select name='pesqLockup" + nameLookup + "_" + nome + "' id='pesqLockup" + nameLookup + "_" + nome + "' onkeypress='" + nameLookup + "_validaEnter(this,event)'>";
            }   
            if (colValores != null){
                for(Iterator it = colValores.iterator(); it.hasNext();){
                    ItemValorDescricaoDTO itemValorDescricaoDTO = (ItemValorDescricaoDTO)it.next();
                    result += "<option value=\"" + itemValorDescricaoDTO.getValor() + "\">" +  getInternacionalizado(itemValorDescricaoDTO.getDescricao()) + "</option>";
                }
            }
            result += "</select>";
            colCamposGeradosCombo.add("pesqLockup" + nameLookup + "_" + nome);
        }
        return result;
    }
    public String geraNomeCampo(String type, String nome, String nameLookup) {
        String result = "";
		if (type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || type.equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim()) ||
				type.equalsIgnoreCase("HIDDEN") || type.equalsIgnoreCase("NUMBER") || type.equalsIgnoreCase("DATE")) {
            result = "pesqLockup" + nameLookup + "_" + nome + "";
        }
        return result;
    }
    @SuppressWarnings("rawtypes")
	public String geraScript(String script, Collection colCamposPesq, String nameLookup, String nome){
        if (colCamposPesq == null) return script;
        Iterator it = colCamposPesq.iterator();
        Campo campo;
        String scriptRetorno = script;
        while(it.hasNext()){
            campo = (Campo)it.next();
            scriptRetorno = scriptRetorno.replaceAll("\\{id=" + campo.getNomeFisico() + "\\}", "'pesqLockup" + nameLookup + "_" + campo.getNomeFisico() + "'");
        }
        scriptRetorno = scriptRetorno.replaceAll("\\{id=LOOKUPNAME\\}", nameLookup);
        scriptRetorno = scriptRetorno.replaceAll("SUBMITLOOKUP", "pesq_" + nameLookup + "()");
        scriptRetorno = scriptRetorno.replaceAll("\\\\n", "" + '\n');
        return scriptRetorno;
    }
    
    private String getInternacionalizado(String texto){
    	String traduzido = props.getProperty(texto);
    	if ((traduzido==null)||(traduzido.length()<=0)){
    		traduzido=texto;
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
