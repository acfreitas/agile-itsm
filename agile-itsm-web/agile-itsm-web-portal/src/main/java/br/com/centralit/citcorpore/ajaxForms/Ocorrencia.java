package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.OcorrenciaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

public class Ocorrencia extends AjaxFormAction {

	public Class getBeanClass() {
		return OcorrenciaDTO.class;
	}

	public void load(DocumentHTML arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
	}
	public void registrarRespostaOcorrencia(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OcorrenciaService ocorrenciaService = (OcorrenciaService) ServiceLocator.getInstance().getService(OcorrenciaService.class, null);
		OcorrenciaDTO ocorrenciaBean = (OcorrenciaDTO)document.getBean();
		
		if (ocorrenciaBean.getRespostaOcorrencia()==null){
			document.alert("Resposta não informada! Favor informar a resposta!");
			return;
		}
		
		ocorrenciaService.updateResposta(ocorrenciaBean);
		
		document.executeScript("POPUP_ATUALIZAR_RESPOSTA_OCORR.hide()");
		
		consultarOcorrencia(document, request, response);
		
		document.alert("Resposta registrada com sucesso!");			
	}
	
	public void registrarOcorrencia(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OcorrenciaService ocorrenciaService = (OcorrenciaService) ServiceLocator.getInstance().getService(OcorrenciaService.class, null);
		OcorrenciaDTO ocorrenciaBean = (OcorrenciaDTO)document.getBean();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		ocorrenciaBean.setIdEmpregado(new Integer(usuario.getIdUsuario()));
				
		ocorrenciaService.create(ocorrenciaBean);
		
		document.executeScript("POPUP_OCORRENCIA.hide()");
		
		document.alert("Ocorrência registrada com sucesso!");		
	}
	
	public void consultarOcorrencia(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OcorrenciaService ocorrenciaService = (OcorrenciaService) ServiceLocator.getInstance().getService(OcorrenciaService.class, null);
		OcorrenciaDTO ocorrenciaBean = (OcorrenciaDTO)document.getBean();
		
		Collection col = ocorrenciaService.findByDemanda(ocorrenciaBean.getIdDemanda());
		
		HTMLTable tabelaConsultaTimeSheet = document.getTableById("tabelaConsultaOcorr");
		
		tabelaConsultaTimeSheet.deleteAllRows();
		tabelaConsultaTimeSheet.addRowsByCollection(col, 
				new String[] {"dataStr", "tipoOcorrenciaStr", "ocorrencia", "respostaOcorrencia", "nomeEmpregado"}, 
				null, 
				"Já existe registrado este registro na tabela", 
				null, 
				"CHAMA_AtualizaResposta", 
				null);
		tabelaConsultaTimeSheet.applyStyleClassInAllCells("tamanho10");		
	}	
}
