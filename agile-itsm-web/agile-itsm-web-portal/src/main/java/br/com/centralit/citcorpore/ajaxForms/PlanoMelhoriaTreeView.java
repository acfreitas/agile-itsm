package br.com.centralit.citcorpore.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.bean.PlanoMelhoriaDTO;
import br.com.centralit.citcorpore.negocio.AcaoPlanoMelhoriaService;
import br.com.centralit.citcorpore.negocio.ObjetivoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.ObjetivoPlanoMelhoriaService;
import br.com.centralit.citcorpore.negocio.PlanoMelhoriaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class PlanoMelhoriaTreeView extends AjaxFormAction{
	private static boolean DEBUG = true;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap hashValores = getFormFields(request);
		if (DEBUG){
			debugValuesFromRequest(hashValores);
		}
		String idStr = (String) hashValores.get("ID");
		int id = -1;
		try{
			id = Integer.parseInt(UtilStrings.apenasNumeros(idStr));
		}catch(Exception e){}
		PlanoMelhoriaService planoMelhoriaService = (PlanoMelhoriaService) ServiceLocator.getInstance().getService(PlanoMelhoriaService.class, null);
		ObjetivoPlanoMelhoriaService objetivoPlanoMelhoriaService = (ObjetivoPlanoMelhoriaService) ServiceLocator.getInstance().getService(ObjetivoPlanoMelhoriaService.class, null);
		AcaoPlanoMelhoriaService acaoPlanoMelhoriaService = (AcaoPlanoMelhoriaService) ServiceLocator.getInstance().getService(AcaoPlanoMelhoriaService.class, null);
		ObjetivoMonitoramentoService objetivoMonitoramentoService = (ObjetivoMonitoramentoService) ServiceLocator.getInstance().getService(ObjetivoMonitoramentoService.class, null);
		String strCab = "";
		String tit = "";
		if (id == -1){
			strCab += "[{";
			strCab += "\"id\":-1,";
			strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "planoMelhoria.list") + "\",";
			strCab += "\"children\":[";
			Collection colsPlanos = planoMelhoriaService.list();
			if (colsPlanos != null){
				boolean bPrim = true;
				for (Iterator it = colsPlanos.iterator(); it.hasNext();){
					PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO)it.next();
					if (!bPrim){
						strCab += ",";
					}
					tit = planoMelhoriaDTO.getTitulo();
					tit = tit.replaceAll("\"", "");
					strCab += "{";
					strCab += "\"id\":" + planoMelhoriaDTO.getIdPlanoMelhoria() + ",";
					strCab += "\"text\":\"" + tit + "\",";
					strCab += "\"state\":\"closed\",";
					strCab += "\"iconCls\":\"icon-melhoria\"";
					strCab += "}";
					bPrim = false;
				}
			}
			strCab += "]";
			strCab += "}]";
		}else{
			if (idStr.indexOf("EDITAR-") > -1){
				document.executeScript("editaContrato('" + id + "')");
			}else if (idStr.indexOf("OBJ-") > -1){
				strCab += "[";
				strCab += "{";
				strCab += "\"id\":\"NOVOOBJ-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.novo") + "\"";
				strCab += "}";
				Collection colObjetivos = objetivoPlanoMelhoriaService.findByIdPlanoMelhoria(id);
				if (colObjetivos != null){
					for (Iterator it = colObjetivos.iterator(); it.hasNext();){
						ObjetivoPlanoMelhoriaDTO objetivoPlanoMelhoriaDTO = (ObjetivoPlanoMelhoriaDTO)it.next();
						tit = objetivoPlanoMelhoriaDTO.getTituloObjetivo();
						tit = tit.replaceAll("\"", "");
						strCab += ",";
						strCab += "{";
						strCab += "\"id\":\"OBJEDT-" + objetivoPlanoMelhoriaDTO.getIdObjetivoPlanoMelhoria() + "\",";
						strCab += "\"text\":\"" + tit + "\",";
						strCab += "\"iconCls\":\"icon-objetivo\",";
						strCab += "\"state\":\"closed\"";
						strCab += "}";
					}
				}
				strCab += "]";
			}else if (idStr.indexOf("OBJEDT-") > -1){
				strCab += "[";
				strCab += "{";
				strCab += "\"id\":\"EDITAROBJ-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.editar") + "\",";
				strCab += "\"iconCls\":\"icon-edit\"";
				strCab += "},";
				strCab += "{";
				strCab += "\"id\":\"ACT-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.acoes") + "\",";
				strCab += "\"iconCls\":\"icon-acoes\",";
				strCab += "\"state\":\"closed\"";
				strCab += "},";
				strCab += "{";
				strCab += "\"id\":\"MON-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.monitoramento") + "\",";
				strCab += "\"iconCls\":\"icon-eyes\",";
				strCab += "\"state\":\"closed\"";
				strCab += "}";
				strCab += "]";
			}else if (idStr.indexOf("ACT-") > -1){
				strCab += "[";
				strCab += "{";
				strCab += "\"id\":\"NOVAACT-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.nova") + "\"";
				strCab += "}";
				Collection colAcoes = acaoPlanoMelhoriaService.findByIdObjetivoPlanoMelhoria(id);
				if (colAcoes != null){
					for (Iterator it = colAcoes.iterator(); it.hasNext();){
						AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDTO = (AcaoPlanoMelhoriaDTO)it.next();
						tit = acaoPlanoMelhoriaDTO.getTituloAcao();
						tit = tit.replaceAll("\"", "");
						strCab += ",";
						strCab += "{";
						strCab += "\"id\":\"ACTEDT-" + acaoPlanoMelhoriaDTO.getIdAcaoPlanoMelhoria() + "\",";
						strCab += "\"text\":\"" + tit + "\",";
						strCab += "\"iconCls\":\"icon-acao\"";
						strCab += "}";
					}
				}
				strCab += "]";
			}else if (idStr.indexOf("MON-") > -1){
				strCab += "[";
				strCab += "{";
				strCab += "\"id\":\"NOVOMON-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.novo") + "\"";
				strCab += "}";
				Collection colAcoes = objetivoMonitoramentoService.findByIdObjetivoPlanoMelhoria(id);
				if (colAcoes != null){
					for (Iterator it = colAcoes.iterator(); it.hasNext();){
						ObjetivoMonitoramentoDTO objetivoMonitoramentoDTO = (ObjetivoMonitoramentoDTO)it.next();
						tit = objetivoMonitoramentoDTO.getTituloMonitoramento();
						tit = tit.replaceAll("\"", "");
						strCab += ",";
						strCab += "{";
						strCab += "\"id\":\"MONEDT-" + objetivoMonitoramentoDTO.getIdObjetivoMonitoramento() + "\",";
						strCab += "\"text\":\"" + tit + "\",";
						strCab += "\"iconCls\":\"icon-eye\"";
						strCab += "}";
					}
				}
				strCab += "]";
			}else{
				strCab += "[";
				strCab += "{";
				strCab += "\"id\":\"EDITAR-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.editar") + "\",";
				strCab += "\"iconCls\":\"icon-edit\"";
				strCab += "},";
				strCab += "{";
				strCab += "\"id\":\"OBJ-" + id + "\",";
				strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "plano.melhoria.objetivos") + "\",";
				strCab += "\"iconCls\":\"icon-objetivos\",";
				strCab += "\"state\":\"closed\"";
				strCab += "}";
				strCab += "]";
			}
		}

		request.setAttribute("json_retorno", strCab);
	}

	@Override
	public Class getBeanClass() {
		return PlanoMelhoriaDTO.class;
	}
	private HashMap getFormFields(HttpServletRequest req){
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
			e.printStackTrace();
		}
		HashMap formFields = new HashMap();
		Enumeration en = req.getParameterNames();
		String[] strValores;
		while(en.hasMoreElements()) {
			String nomeCampo  = (String)en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0){
				formFields.put(nomeCampo.toUpperCase(),UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1){
					formFields.put(nomeCampo.toUpperCase(),UtilStrings.decodeCaracteresEspeciais(strValores[0]));
				}else{
					formFields.put(nomeCampo.toUpperCase(),strValores);
				}
			}
		}
		return formFields;
	}

}
