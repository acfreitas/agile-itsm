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
import br.com.centralit.citcorpore.bean.DataManagerObjectsDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataManagerObjects extends AjaxFormAction {
	private static boolean DEBUG = true;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap hashValores = getFormFields(request);
		if (DEBUG) {
			debugValuesFromRequest(hashValores);
		}
		String idStr = (String) hashValores.get("ID");
		int id = -1;
		try {
			if(idStr != null && !idStr.isEmpty()){
				id = Integer.parseInt(idStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		String strCab = "";
		if (id == -1) {
			strCab += "[{";
			strCab += "\"id\":-1,";
			strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "dataManager.objetosNegocio") + "\",";
			strCab += "\"children\":[";
			Collection colObjsNeg = objetoNegocioService.listAtivos();
			if (colObjsNeg != null) {
				boolean bPrim = true;
				for (Iterator it = colObjsNeg.iterator(); it.hasNext();) {
					ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
					if (!bPrim) {
						strCab += ",";
					}
					strCab += "{";
					strCab += "\"id\":" + objetoNegocioDTO.getIdObjetoNegocio() + ",";
					strCab += "\"text\":\"" + objetoNegocioDTO.getNomeObjetoNegocio() + "\",";
					strCab += "\"state\":\"closed\"";
					strCab += "}";
					bPrim = false;
				}
			}
			strCab += "]";
			strCab += "}]";
		} else {
			Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(id);
			if (col != null) {
				strCab += "[";
				boolean bPrim = true;
				for (Iterator it = col.iterator(); it.hasNext();) {
					if (!bPrim) {
						strCab += ",";
					}
					CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
					// String strTam = "";
					String chave = "";
					if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
						chave = "[*]";
					}
					strCab += "{";
					strCab += "\"id\":" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + ",";
					strCab += "\"text\":\"" + chave + (camposObjetoNegocioDto.getDescricao() == null ? camposObjetoNegocioDto.getNome() : camposObjetoNegocioDto.getDescricao()) + " ("
							+ camposObjetoNegocioDto.getTipoDB() + ")" + "\"";
					strCab += "}";
					bPrim = false;
				}
				strCab += "]";
			}
		}

		request.setAttribute("json_retorno", strCab);
	}

	@Override
	public Class getBeanClass() {
		return DataManagerObjectsDTO.class;
	}

	private HashMap getFormFields(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
			e.printStackTrace();
		}
		HashMap formFields = new HashMap();
		Enumeration en = req.getParameterNames();
		String[] strValores;
		while (en.hasMoreElements()) {
			String nomeCampo = (String) en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0) {
				formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1) {
					formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(strValores[0]));
				} else {
					formFields.put(nomeCampo.toUpperCase(), strValores);
				}
			}
		}
		return formFields;
	}

}
