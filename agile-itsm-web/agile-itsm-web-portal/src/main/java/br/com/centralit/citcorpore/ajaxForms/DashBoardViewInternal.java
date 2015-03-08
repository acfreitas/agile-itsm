package br.com.centralit.citcorpore.ajaxForms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.bean.BIDashBoardDTO;
import br.com.centralit.citcorpore.bean.BIItemDashBoardDTO;
import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.RuntimeScript;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.centralit.citcorpore.negocio.BIDashBoardService;
import br.com.centralit.citcorpore.negocio.BIItemDashBoardService;
import br.com.centralit.citcorpore.negocio.FormulaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.bean.GerencialOptionDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionsDTO;
import br.com.centralit.citgerencial.bean.GerencialParameterDTO;
import br.com.centralit.citgerencial.negocio.GerencialGenerate;
import br.com.centralit.citgerencial.util.WebUtilGerencial;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class DashBoardViewInternal extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		BIDashBoardService biDashBoardService = (BIDashBoardService) ServiceLocator.getInstance().getService(BIDashBoardService.class, null);
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		BIItemDashBoardService biItemDashBoardService = (BIItemDashBoardService) ServiceLocator.getInstance().getService(BIItemDashBoardService.class, null);
		//--
		BIDashBoardDTO biDashBoardParm = (BIDashBoardDTO)document.getBean();
		if (biDashBoardParm.getIdDashBoard() == null){
			if (biDashBoardParm.getIdentificacao() != null && !biDashBoardParm.getIdentificacao().trim().equalsIgnoreCase("")){ //Recupera pela identificacao
				BIDashBoardDTO biDashBoardAux = (BIDashBoardDTO) biDashBoardService.getByIdentificacao(biDashBoardParm.getIdentificacao());
				if (biDashBoardAux != null){
					biDashBoardParm.setIdDashBoard(biDashBoardAux.getIdDashBoard());
					request.setAttribute("idDashBoard", "" + biDashBoardAux.getIdDashBoard());
				}else{
					return;
				}
			}else{
				return;
			}
		}

		int iAux = 0;
		BIDashBoardDTO biDashBoardDTO = null;

		if(biDashBoardParm.getIdDashBoard()!= null && biDashBoardParm.getIdDashBoard()>0){
			try{
				biDashBoardDTO = (BIDashBoardDTO) biDashBoardService.restore(biDashBoardParm);
			}catch(Exception e){
				return;
			}
		} else{
			return;
		}
		HashMap hashParametros = null;
		if (biDashBoardDTO.getParametros() != null && !biDashBoardDTO.getParametros().trim().equalsIgnoreCase("")){
			biDashBoardDTO.setListParameters(getSubTreeParameters(biDashBoardDTO.getParametros()));
			hashParametros = getParametrosInformados(request);
		}
		if (!biDashBoardParm.isParametersPreenchidos() && biDashBoardDTO.getListParameters() != null && biDashBoardDTO.getListParameters().size() > 0) {
			HTMLElement divParametros = document.getElementById("divParametros");
			divParametros.setInnerHTML(geraParametrosPainel(biDashBoardDTO.getListParameters(), hashParametros, user, true, request));
			document.executeScript("DEFINEALLPAGES_atribuiCaracteristicasCitAjax()");
			// document.executeScript("$('#POPUP_PARAM').attr('title','" + gerencialPainelDto.getDescription() + "')");
			document.executeScript("HTMLUtils.focusInFirstActivateField(document.formParametros)");
			document.executeScript("pageLoad();");
			document.executeScript("$('#POPUP_PARAM').dialog('open')");
			return;
		}else{
			HTMLElement divParametros = document.getElementById("divParametros");
			divParametros.setInnerHTML(geraParametrosPainel(biDashBoardDTO.getListParameters(), hashParametros, user, true, request));
		}
		Collection col = biItemDashBoardService.findByIdDashBoard(biDashBoardDTO.getIdDashBoard());

		String queryParmGeral = "";
		if (hashParametros != null){
			Set<String> chaves = hashParametros.keySet();
	        for (String chave : chaves)
	        {
	            if(chave != null) {
	                //System.out.println(chave + hashParametros.get(chave));
	            	if (!queryParmGeral.trim().equalsIgnoreCase("")){
	            		queryParmGeral += "&";
	            	}
	            	queryParmGeral += chave;
	            	queryParmGeral += "=";
	            	queryParmGeral += hashParametros.get(chave);
	            }
	        }
		}
    	if (!queryParmGeral.trim().equalsIgnoreCase("")){
    		queryParmGeral = "&" + queryParmGeral + "&parmOK=S&dashPart=S";
    	}
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				BIItemDashBoardDTO biItemDashBoardDTO = (BIItemDashBoardDTO)it.next();
				document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.top = '" + biItemDashBoardDTO.getItemTop() + "px'");
				document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.left = '" + biItemDashBoardDTO.getItemLeft() + "px'");
				document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.width = '" + biItemDashBoardDTO.getItemWidth() + "px'");
				document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.height = '" + biItemDashBoardDTO.getItemHeight() + "px'");

				if (biItemDashBoardDTO.getIdConsulta() != null){
					String queryParm = "";
					if (biItemDashBoardDTO.getParmsSubst() != null && !biItemDashBoardDTO.getParmsSubst().trim().equalsIgnoreCase("")){
						//Se o item de DashBoard tiver substituicao de paramtros, entao usa estes.
						queryParm = geraSubstituicaoParametrosString(biItemDashBoardDTO.getParmsSubst());
						queryParm = "&" + queryParm + "&parmOK=S&dashPart=S";
					}else{
						queryParm = queryParmGeral;
					}
					BIConsultaDTO biConsultaDTO = new BIConsultaDTO();
					biConsultaDTO.setIdConsulta(biItemDashBoardDTO.getIdConsulta());
					biConsultaDTO = (BIConsultaDTO) biConsultaService.restore(biConsultaDTO);
					if (biConsultaDTO != null){
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("C")){ //Cruzado
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraInfoPivotTable/geraInfoPivotTable.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm + "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("D")){ //DataTable
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraDataTable/geraDataTable.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("T")){ //Template
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraTemplateReport/geraTemplateReport.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("1")){ //Grafico de Pizza
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraGraficoPizzaJS/geraGraficoPizzaJS.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("2")){ //Grafico de Barra
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraGraficoBarraJS/geraGraficoBarraJS.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("J")){ //JSP
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraJSP/geraJSP.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("X")){ //XML do CITGerencial
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraXML/geraXML.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("#")){ //URL Externa ou Interna
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraURL/geraURL.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("S")){ //Script
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraScript/geraScript.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
						if (biConsultaDTO.getTipoConsulta() != null && biConsultaDTO.getTipoConsulta().trim().equalsIgnoreCase("R")){ //Retorno de Classe
							String str = "<iframe src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") +
									"/pages/geraRetornoClasse/geraRetornoClasse.load?idConsulta=" + biConsultaDTO.getIdConsulta() + queryParm +  "' frameborder='0' width='100%' height='" + biItemDashBoardDTO.getItemHeight() + "px' style='height:" + biItemDashBoardDTO.getItemHeight() + "px;width:100%'></iframe>";
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
						}
					}
				}
				iAux++;
			}
		}
		for (int i = iAux + 1; i <= 20; i++){
			document.executeScript("document.getElementById('testdiv_" + i + "').style.display = 'none'");
		}
		if (biDashBoardDTO.getTempoRefresh() != null){
			if (biDashBoardDTO.getTempoRefresh().intValue() > 0){
				document.executeScript("setaRefresh(" + (biDashBoardDTO.getTempoRefresh().intValue() * 1000) + ")");
			}
		}
	}

	public String geraSubstituicaoParametrosString(String parmsSubst){
		HashMap hash = geraSubstituicaoParametros(parmsSubst);
		String parms = "";
		if (hash != null){
			Set<String> chaves = hash.keySet();
	        for (String chave : chaves)
	        {
	            if(chave != null) {
	                //System.out.println(chave + hashParametros.get(chave));
	            	if (!parms.trim().equalsIgnoreCase("")){
	            		parms += "&";
	            	}
	            	parms += chave;
	            	parms += "=";
	            	parms += hash.get(chave);
	            }
	        }
		}
		return parms;
	}
	public HashMap geraSubstituicaoParametros(String parmsSubst){
		HashMap hash = new HashMap();
		String sAux = parmsSubst;
		if (sAux == null){
			sAux = "";
		}
		sAux = sAux.replaceAll(";", "\n");
		sAux = sAux.replaceAll("\r", "\n");
		String[] arrayStr = sAux.split("\n");
		if (arrayStr != null){
			for (int i = 0; i < arrayStr.length; i++){
				String linha = arrayStr[i];
				String[] linhaArray = linha.split("=");
				if (linhaArray != null && linhaArray.length > 1){
					if (linhaArray[1] != null){
						if (linhaArray[1].trim().startsWith("Citsmart")){
							hash.put(linhaArray[0].trim(), processaFormulaReport(linhaArray[1].trim()));
						}else{
							hash.put(linhaArray[0].trim(), linhaArray[1].trim());
						}
					}
				}
			}
		}
		return hash;
	}
	/*
	public static void main(String[] args) {
		DashBoardViewInternal dashView = new DashBoardViewInternal();
		System.out.println(">>>>1: " + dashView.processaFormulaReport("Citsmart.dataAtual"));
		System.out.println(">>>>2: " + dashView.processaFormulaReport("Citsmart.dataAtual(1)"));
		System.out.println(">>>>3: " + dashView.processaFormulaReport("Citsmart.dataAtual(-1)"));
		System.out.println(">>>>4: " + dashView.processaFormulaReport("Citsmart.dataAtual(10)"));
		System.out.println(">>>>5: " + dashView.processaFormulaReport("Citsmart.dataAtual(-10)"));
	}
	*/
	public String processaFormulaReport(String itemFormula){
		if (itemFormula.trim().equalsIgnoreCase("Citsmart.dataAtual")){
			return UtilDatas.dateToSTR(UtilDatas.getDataAtual());
		}
		if (itemFormula.trim().startsWith("Citsmart.dataAtual(")){
			Date data1 = UtilDatas.getDataAtual();
			String sAux = itemFormula.trim().substring("Citsmart.dataAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int numDias = 0;
			try{
				numDias = Integer.parseInt(sAux);
			}catch(Exception e){}
			java.util.Date dataAux = UtilDatas.alteraData(data1, numDias, Calendar.DAY_OF_MONTH);
			return UtilDatas.dateToSTR(new java.sql.Date(dataAux.getTime()));
		}
		if (itemFormula.trim().equalsIgnoreCase("Citsmart.anoAtual")){
			return "" + UtilDatas.getYear(UtilDatas.getDataAtual());
		}
		if (itemFormula.trim().startsWith("Citsmart.anoAtual(")){
			int ano1 = UtilDatas.getYear(UtilDatas.getDataAtual());
			String sAux = itemFormula.trim().substring("Citsmart.anoAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int num = 0;
			try{
				num = Integer.parseInt(sAux);
			}catch(Exception e){}
			return "" + (ano1 + num);
		}
		if (itemFormula.trim().equalsIgnoreCase("Citsmart.mesAtual")){
			return "" + UtilDatas.getMonth(UtilDatas.getDataAtual());
		}
		if (itemFormula.trim().startsWith("Citsmart.mesAtual(")){
			int mes1 = UtilDatas.getMonth(UtilDatas.getDataAtual());
			String sAux = itemFormula.trim().substring("Citsmart.mesAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int num = 0;
			try{
				num = Integer.parseInt(sAux);
			}catch(Exception e){}
			if (num > 0){
				for (int i = 1; i <= num; i++){
					mes1++;
					if (mes1 > 12){
						mes1 = 1;
					}
				}
			}else{
				for (int i = 1; i <= Math.abs(num); i++){
					mes1--;
					if (mes1 <= 0){
						mes1 = 12;
					}
				}
			}
			return "" + mes1;
		}
		if (itemFormula.trim().equalsIgnoreCase("Citsmart.diaAtual")){
			return "" + UtilDatas.getDay(UtilDatas.getDataAtual());
		}
		if (itemFormula.trim().startsWith("Citsmart.diaAtual(")){
			String sAux = itemFormula.trim().substring("Citsmart.diaAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int num = 0;
			try{
				num = Integer.parseInt(sAux);
			}catch(Exception e){}
			java.util.Date dataAux = UtilDatas.alteraData(UtilDatas.getDataAtual(), num, Calendar.DAY_OF_MONTH);
			return "" + UtilDatas.getDay(dataAux);
		}
		if (itemFormula.trim().equalsIgnoreCase("Citsmart.primeiraDataMesAtual")){
			java.util.Date data = null;
			try {
				data = UtilDatas.getPrimeiraDataMes(UtilDatas.getDataAtual());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return UtilDatas.dateToSTR(new java.sql.Date(data.getTime()));
		}
		if (itemFormula.trim().startsWith("Citsmart.primeiraDataMesAtual(")){
			int mes1 = UtilDatas.getMonth(UtilDatas.getDataAtual());
			String sAux = itemFormula.trim().substring("Citsmart.primeiraDataMesAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int num = 0;
			try{
				num = Integer.parseInt(sAux);
			}catch(Exception e){}
			java.util.Date data = null;
			try {
				data = UtilDatas.acrescentaSubtraiMesesData(UtilDatas.getDataAtual(), num);
			} catch (LogicException e) {
				e.printStackTrace();
				return null;
			}
			try {
				data = UtilDatas.getPrimeiraDataMes(data);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return UtilDatas.dateToSTR(new java.sql.Date(data.getTime()));
		}
		if (itemFormula.trim().equalsIgnoreCase("Citsmart.ultimaDataMesAtual")){
			java.util.Date data = null;
			try {
				data = UtilDatas.getUltimaDataMes(UtilDatas.getDataAtual());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return UtilDatas.dateToSTR(new java.sql.Date(data.getTime()));
		}
		if (itemFormula.trim().startsWith("Citsmart.ultimaDataMesAtual(")){
			int mes1 = UtilDatas.getMonth(UtilDatas.getDataAtual());
			String sAux = itemFormula.trim().substring("Citsmart.ultimaDataMesAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int num = 0;
			try{
				num = Integer.parseInt(sAux);
			}catch(Exception e){}
			java.util.Date data = null;
			try {
				data = UtilDatas.acrescentaSubtraiMesesData(UtilDatas.getDataAtual(), num);
			} catch (LogicException e) {
				e.printStackTrace();
				return null;
			}
			try {
				data = UtilDatas.getUltimaDataMes(data);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return UtilDatas.dateToSTR(new java.sql.Date(data.getTime()));
		}
		if (itemFormula.trim().startsWith("Citsmart.anoFromMesAtual(")){
			int mes1 = UtilDatas.getMonth(UtilDatas.getDataAtual());
			int ano1 = UtilDatas.getYear(UtilDatas.getDataAtual());
			String sAux = itemFormula.trim().substring("Citsmart.anoFromMesAtual(".length());
			sAux = sAux.replaceAll("\\)", "");
			int num = 0;
			try{
				num = Integer.parseInt(sAux);
			}catch(Exception e){}
			if (num > 0){
				for (int i = 1; i <= num; i++){
					mes1++;
					if (mes1 > 12){
						mes1 = 1;
						ano1++;
					}
				}
			}else{
				for (int i = 1; i <= Math.abs(num); i++){
					mes1--;
					if (mes1 <= 0){
						mes1 = 12;
						ano1--;
					}
				}
			}
			return "" + ano1;
		}
		if (itemFormula.trim().startsWith("Citsmart.returnFromFormula(")){
			String sAux = itemFormula.trim().substring("Citsmart.returnFromFormula(".length());
			sAux = sAux.replaceAll("\\)", "").trim();
			FormulaService formulaService = null;
			try {
				formulaService = (FormulaService) ServiceLocator
						.getInstance().getService(
								FormulaService.class, null);
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (formulaService != null){
					Collection col = formulaService.findByIdentificador(sAux);
					if (col != null && col.size() > 0){
						FormulaDTO formulaDTO = (FormulaDTO) col.iterator().next();
						if (formulaDTO != null){
	        		   		String strScript = formulaDTO.getConteudo();
	        		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
	        		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
	        		   			RuntimeScript runtimeScript = new RuntimeScript();
	        		   			Context cx = Context.enter();
	        		   			String retorno = "";
	        		   			Scriptable scope = cx.initStandardObjects();
	        		   			scope.put("formulaName", scope, sAux);
	        		   			scope.put("retorno", scope, retorno);
	        		   			scope.put("this", scope, this);
	        		   			scope.put("object", scope, this);
	        		   			scope.put("out", scope, System.out);
	        		   			scope.put("RuntimeScript", scope, runtimeScript);
	        		   			String retornoAux = (String) scriptExecute.processScript(cx, scope, strScript, this.getClass().getName() + "_" + sAux);
	        		   			return retornoAux;
	        		   		}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return "";
		}

		return null;
	}

	@Override
	public Class getBeanClass() {
		return BIDashBoardDTO.class;
	}

	private String getTypeParametro(Collection colDefinicaoParametros, String nameParm) {
		if (colDefinicaoParametros == null) {
			return "";
		}
		for (Iterator it = colDefinicaoParametros.iterator(); it.hasNext();) {
			GerencialParameterDTO gerencialParameterDTO = (GerencialParameterDTO) it.next();
			String nomeParmAux = "PARAM." + gerencialParameterDTO.getName().trim();
			if (nomeParmAux.equalsIgnoreCase(nameParm)) {
				return gerencialParameterDTO.getType();
			}
		}
		return "";
	}
	private String geraParametrosPainel(List listParameters, HashMap hashParametros, Usuario user, boolean reload, HttpServletRequest request) throws ServiceException, Exception {
		if (listParameters == null){
			return "";
		}
		String strRetorno = "<table>";
		for (Iterator it = listParameters.iterator(); it.hasNext();) {
			GerencialParameterDTO gerencialParameterDto = (GerencialParameterDTO) it.next();

			strRetorno += "<tr>";
			strRetorno += "<td>";
			strRetorno += UtilI18N.internacionaliza(request, gerencialParameterDto.getDescription());
			if (gerencialParameterDto.isMandatory()) {
				strRetorno += "*";
			}
			strRetorno += "</td>";
			strRetorno += "<td>";
			strRetorno += geraCampo(gerencialParameterDto, listParameters, hashParametros, user, reload, request);
			strRetorno += "</td>";
			strRetorno += "</tr>";
		}
		strRetorno += "</table>";

		return strRetorno;
	}
	private String geraCampo(GerencialParameterDTO gerencialParameterDto, List listParameters, HashMap hashParametros, Usuario user, boolean reload, HttpServletRequest request)
			throws ServiceException, Exception {
		String strRetorno = "";
		String strValid = "";

		if (gerencialParameterDto.isMandatory()) {
			strValid = "Required";
		}

		// Verifica se o campo tem o tipo HTML como select.
		if (gerencialParameterDto.getTypeHTML() != null && gerencialParameterDto.getTypeHTML().equalsIgnoreCase("select")) {
			String strValidCompleta = "";

			if (!strValid.trim().equalsIgnoreCase("")) {
				strValidCompleta = " Valid[" + strValid + "]";
			}
			strRetorno += "<select size='" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName()
					+ "' class='Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

			if (gerencialParameterDto.isReload()) {
				// strRetorno += " onchange='recarrega(this)'";
			}

			String value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%"; // Valor absurdo!!!! So sera usado se nao foi informado o parametro.
			if (reload) {
				value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value == null) {
					value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%";
				}
			}

			strRetorno += ">";
			if (gerencialParameterDto.getColOptions() != null) {
				for (Iterator it = gerencialParameterDto.getColOptions().iterator(); it.hasNext();) {
					Object obj = it.next();
					if (GerencialOptionDTO.class.isInstance(obj)) {
						GerencialOptionDTO option = (GerencialOptionDTO) obj;
						strRetorno += "<option value='" + option.getValue() + "'";
						if (reload) {
							if (option.getValue().equalsIgnoreCase(value)) {
								strRetorno += " selected ";
							}
						}
						strRetorno += ">" + UtilI18N.internacionaliza(request, option.getText()) + "</option>";
					}
					if (GerencialOptionsDTO.class.isInstance(obj)) {
						GerencialOptionsDTO options = (GerencialOptionsDTO) obj;
						if (options.isOnload() || reload) {
							GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));
							Collection col = gerencialGenerateService.executaSQLOptions(options, listParameters, hashParametros, user);
							for (Iterator itOptions = col.iterator(); itOptions.hasNext();) {
								GerencialOptionDTO option = (GerencialOptionDTO) itOptions.next();
								strRetorno += "<option value='" + option.getValue() + "'";
								if (reload) {
									if (option.getValue().equalsIgnoreCase(value)) {
										strRetorno += " selected ";
									}
								}
								strRetorno += ">" + option.getText() + "</option>";
							}
						}
					}
				}
			}
			strRetorno += "</select>";
		}

		// Verifica se o campo tem o tipo HTML como checkbox.
		if (gerencialParameterDto.getTypeHTML() != null && gerencialParameterDto.getTypeHTML().equalsIgnoreCase("checkbox")) {
			String strValidCompleta = "";

			if (!strValid.trim().equalsIgnoreCase("")) {
				strValidCompleta = " Valid[" + strValid + "]";
			}

			String value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%"; // Valor absurdo!!!! So sera usado se nao foi informado o parametro.
			if (reload) {
				value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value == null) {
					value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%";
				}
			}

			if (gerencialParameterDto.getColOptions() != null) {
				String strRetornoAux = "";
				int qtdeOpcoes = 0;
				for (Iterator it = gerencialParameterDto.getColOptions().iterator(); it.hasNext();) {
					Object obj = it.next();
					if (GerencialOptionDTO.class.isInstance(obj)) {
						qtdeOpcoes++;
						GerencialOptionDTO option = (GerencialOptionDTO) obj;
						strRetornoAux += "<input type='checkbox' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName() + "' class='Description["
								+ gerencialParameterDto.getDescription() + "] " + strValidCompleta + "' value='" + option.getValue() + "'";
						if (reload) {
							if (option.getValue().equalsIgnoreCase(value)) {
								strRetornoAux += " checked='checked' ";
							}
						}
						if (gerencialParameterDto.isReload()) {
							// strRetornoAux += " onclick='recarrega(this)'";
						}
						strRetornoAux += "/>" + option.getText() + "<br/>";
					}
					if (GerencialOptionsDTO.class.isInstance(obj)) {
						GerencialOptionsDTO options = (GerencialOptionsDTO) obj;
						if (options.isOnload() || reload) {
							GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));
							Collection col = gerencialGenerateService.executaSQLOptions(options, listParameters, hashParametros, user);
							for (Iterator itOptions = col.iterator(); itOptions.hasNext();) {
								qtdeOpcoes++;
								GerencialOptionDTO option = (GerencialOptionDTO) itOptions.next();
								strRetornoAux += "<input type='checkbox' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName() + "' class='Description["
										+ gerencialParameterDto.getDescription() + "] " + strValidCompleta + "' value='" + option.getValue() + "'";
								if (reload) {
									if (option.getValue().equalsIgnoreCase(value)) {
										strRetornoAux += " checked='checked' ";
									}
								}
								if (gerencialParameterDto.isReload()) {
									// strRetornoAux += " onclick='recarrega(this)'";
								}
								strRetornoAux += "/>" + option.getText() + "<br/>";
							}
						}
					}
				}
				if (!strRetornoAux.equalsIgnoreCase("")) {
					if (qtdeOpcoes > 5) {
						strRetorno += "<div style='height:100px; overflow:auto; border: 1px solid black'>" + strRetornoAux + "</div>";
					} else {
						strRetorno += strRetornoAux;
					}
				}
			}
		}

		// Campo Date
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.sql.Date")) {
			strValid += ",Date";

			strRetorno += "<input type='text' size='0" + gerencialParameterDto.getSize() + "' maxlength='0" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
					+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
			if (!reload) {
				strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
			}
			strRetorno += " class='Format[Date] Description[" + gerencialParameterDto.getDescription() + "] Valid[" + strValid + "] datepicker' ";

			if (gerencialParameterDto.isReload()) {
				// strRetorno += " onblur='recarrega(this)'";
			}

			if (reload) {
				String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value != null) {
					strRetorno += " value='" + value + "'";
				}
			}

			strRetorno += "/>";
		}

		// Campo Inteiro - Nao ha casas decimais
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.Integer")) {
			if (gerencialParameterDto.getTypeHTML() == null || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("") || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("text")) {
				String strValidCompleta = "";

				if (!strValid.trim().equalsIgnoreCase("")) {
					strValidCompleta = " Valid[" + strValid + "]";
				}
				strRetorno += "<input type='text' size='0" + gerencialParameterDto.getSize() + "' maxlength='0" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
						+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
				if (!reload) {
					strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
				}
				strRetorno += " class='Format[Numero] Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

				if (gerencialParameterDto.isReload()) {
					// strRetorno += " onblur='recarrega(this)'";
				}

				if (reload) {
					String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
					if (value != null) {
						strRetorno += " value='" + value + "'";
					}
				}

				strRetorno += "/>";
			}
		}

		// Campo Duplo - Com casas decimais
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.Double")) {
			String strValidCompleta = "";

			if (!strValid.trim().equalsIgnoreCase("")) {
				strValidCompleta = " Valid[" + strValid + "]";
			}
			strRetorno += "<input type='text' size='0" + gerencialParameterDto.getSize() + "' maxlength='0" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
					+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
			if (!reload) {
				strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
			}
			strRetorno += " class='Format[Money] Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

			if (gerencialParameterDto.isReload()) {
				// strRetorno += " onblur='recarrega(this)'";
			}

			if (reload) {
				String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value != null) {
					strRetorno += " value='" + value + "'";
				}
			}

			strRetorno += "/>";
		}

		// Campo String
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.String")) {
			if (gerencialParameterDto.getTypeHTML() == null || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("") || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("text")) {
				String strValidCompleta = "";

				if (!strValid.trim().equalsIgnoreCase("")) {
					strValidCompleta = " Valid[" + strValid + "]";
				}
				strRetorno += "<input type='text' size='0" + gerencialParameterDto.getSize() + "' maxlength='0" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
						+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
				if (!reload) {
					strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
				}
				strRetorno += " class='Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

				if (gerencialParameterDto.isReload()) {
					// strRetorno += " onblur='recarrega(this)'";
				}

				if (reload) {
					String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
					if (value != null) {
						strRetorno += " value='" + value + "'";
					}
				}

				strRetorno += "/>";
			}
		}
		// Campo StringBuilder
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.StringBuilder")) {
			if (gerencialParameterDto.getTypeHTML() == null || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("") || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("text")
					|| gerencialParameterDto.getTypeHTML().equalsIgnoreCase("textarea")) {
				String strValidCompleta = "";

				if (!strValid.trim().equalsIgnoreCase("")) {
					strValidCompleta = " Valid[" + strValid + "]";
				}
				strRetorno += "<textarea rows='" + gerencialParameterDto.getSize() + "' cols='70' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName()
						+ "'";
				strRetorno += " class='Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

				if (gerencialParameterDto.isReload()) {
					// strRetorno += " onblur='recarrega(this)'";
				}

				String value = "";
				if (reload) {
					value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				} else {
					value = gerencialParameterDto.getDefaultValue();
				}

				strRetorno += ">";
				if (!reload) {
					if (value == null) {
						value = "";
					}
					strRetorno += value;
				}
				strRetorno += "</textarea>";
			}
		}

		return strRetorno;
	}

	public List getSubTreeParameters(String parametros){
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			if (parametros == null){
				return null;
			}
			InputStream is = new ByteArrayInputStream(parametros.getBytes());
			doc = builder.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (doc == null) return null;
		Node noItem = doc.getChildNodes().item(0);
		if (noItem == null){
            return null;
		}
		List colParameters = new ArrayList();
		GerencialParameterDTO gerencialParameter;
        if (noItem.getChildNodes() != null){
            for (int i = 0; i < noItem.getChildNodes().getLength(); i++){
            	Node noSubItem = noItem.getChildNodes().item(i);
            	if(noSubItem.getNodeName().equals("#text")) continue;
            	if(noSubItem.getNodeName().equals("#comment")) continue;

            	if (noSubItem.getNodeName().equalsIgnoreCase("PARAM")){
            		gerencialParameter = new GerencialParameterDTO();

                    NamedNodeMap map = noSubItem.getAttributes();

                    gerencialParameter.setType(map.getNamedItem("type").getNodeValue());
                    if (map.getNamedItem("typeHTML") != null){
                    	gerencialParameter.setTypeHTML(map.getNamedItem("typeHTML").getNodeValue());
                    }
                    gerencialParameter.setValue(map.getNamedItem("value").getNodeValue());
                    gerencialParameter.setName(map.getNamedItem("name").getNodeValue());
                    gerencialParameter.setDescription(map.getNamedItem("description").getNodeValue());

                    String size = map.getNamedItem("size").getNodeValue();
                    if (size == null || size.trim().equalsIgnoreCase("")){
                    	size = "0";
                    }
                    gerencialParameter.setSize(new Integer(Integer.parseInt(size)));

                    String defaultValue = null;
                    if (map.getNamedItem("default") != null){
                    	defaultValue = map.getNamedItem("default").getNodeValue();
                    }
                    if (defaultValue == null){
                    	defaultValue = "";
                    }
                    if (defaultValue.equalsIgnoreCase("{TODAY}") || defaultValue.equalsIgnoreCase("{DATAATUAL}")){
                    	defaultValue = UtilDatas.dateToSTR(UtilDatas.getDataAtual());
                    }
                    if (defaultValue.equalsIgnoreCase("{MESATUAL}")){
                    	defaultValue = "" + UtilDatas.getMonth(UtilDatas.getDataAtual());
                    }
                    if (defaultValue.equalsIgnoreCase("{ANOATUAL}")){
                    	defaultValue = "" + UtilDatas.getYear(UtilDatas.getDataAtual());
                    }
                    gerencialParameter.setDefaultValue(defaultValue);

                    gerencialParameter.setFix(Boolean.valueOf(map.getNamedItem("fix").getNodeValue()).booleanValue());
                    gerencialParameter.setMandatory(Boolean.valueOf(map.getNamedItem("mandatory").getNodeValue()).booleanValue());
                    if (map.getNamedItem("reload") != null){
                    	if (map.getNamedItem("reload").getNodeValue() != null && !map.getNamedItem("reload").getNodeValue().equalsIgnoreCase("")){
                    		gerencialParameter.setReload(Boolean.valueOf(map.getNamedItem("reload").getNodeValue()).booleanValue());
                    	}else{
                    		gerencialParameter.setReload(false);
                    	}
                    }else{
                    	gerencialParameter.setReload(false);
                    }

                    if ("select".equalsIgnoreCase(gerencialParameter.getTypeHTML()) ||
                    		"checkbox".equalsIgnoreCase(gerencialParameter.getTypeHTML()) ||
                    		"radio".equalsIgnoreCase(gerencialParameter.getTypeHTML())){
                    	gerencialParameter.setColOptions(getSubTreeOptions(noSubItem));
                    }

                    colParameters.add(gerencialParameter);
            	}
            }
        }
        return colParameters;
	}

	public Collection getSubTreeOptions(Node noItem){
		if (noItem == null) return null;

		Collection colRetorno = new ArrayList();
        if (noItem.getChildNodes() != null){
            for (int i = 0; i < noItem.getChildNodes().getLength(); i++){
            	Node noSubItem = noItem.getChildNodes().item(i);
            	if(noSubItem.getNodeName().equals("#text")) continue;
            	if(noSubItem.getNodeName().equals("#comment")) continue;

            	if (noSubItem.getNodeName().equalsIgnoreCase("OPTION")){
            		NamedNodeMap map = noSubItem.getAttributes();

            		GerencialOptionDTO gerencialOptionDTO = new GerencialOptionDTO();
            		gerencialOptionDTO.setValue(map.getNamedItem("value").getNodeValue());
            		gerencialOptionDTO.setText(map.getNamedItem("text").getNodeValue());

            		colRetorno.add(gerencialOptionDTO);
            	}

            	if (noSubItem.getNodeName().equalsIgnoreCase("OPTIONS")){
            		NamedNodeMap map = noSubItem.getAttributes();

            		GerencialOptionsDTO gerencialOptionsDTO = new GerencialOptionsDTO();
            		String onLoad = UtilStrings.nullToVazio(map.getNamedItem("onload").getNodeValue());
            		if (onLoad.equalsIgnoreCase("true")){
            			gerencialOptionsDTO.setOnload(true);
            		}else{
            			gerencialOptionsDTO.setOnload(false);
            		}
            		gerencialOptionsDTO.setType(UtilStrings.nullToVazio(map.getNamedItem("type").getNodeValue()));
            		if (gerencialOptionsDTO.getType().equalsIgnoreCase("CLASS_GENERATE_SQL") ||
            				gerencialOptionsDTO.getType().equalsIgnoreCase("SERVICE")){
            			gerencialOptionsDTO.setClassExecute(UtilStrings.nullToVazio(noSubItem.getChildNodes().item(0).getNodeValue()).trim());
            		}else{
            			gerencialOptionsDTO.setType("SQL");
            			gerencialOptionsDTO.setSql(noSubItem.getChildNodes().item(0).getNodeValue());
            		}

            		colRetorno.add(gerencialOptionsDTO);
            	}
            }
        }
        return colRetorno;
	}
	public HashMap getParametrosInformados(HttpServletRequest request) {
		Enumeration x = request.getParameterNames();
		HashMap hashRetorno = new HashMap();
		String[] aux;
		while (x.hasMoreElements()) {
			String nameElement = (String) x.nextElement();

			// System.out.println("Parametro vindo no request: " + nameElement + "    ---> Valor: " + request.getParameter(nameElement));

			if (nameElement.startsWith("PARAM.")) {
				String[] strValores = request.getParameterValues(nameElement);
				if (strValores.length == 0 || strValores.length == 1) {
					String value = request.getParameter(nameElement);
					hashRetorno.put(nameElement, value);
				} else {
					aux = new String[strValores.length];
					for (int i = 0; i < strValores.length; i++) {
						aux[i] = strValores[i];
					}
					hashRetorno.put(nameElement, aux);
				}
			}
		}

		//Usuario user = WebUtilGerencial.getUsuario(request);
		//hashRetorno.put("USER", user);

		return hashRetorno;
	}

}
