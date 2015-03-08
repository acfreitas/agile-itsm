package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.HistoricoExecucaoDTO;
import br.com.centralit.citcorpore.negocio.HistoricoExecucaoService;
import br.com.citframework.service.ServiceLocator;

public class HistoricoExecucao extends AjaxFormAction {

	public Class getBeanClass() {
		return HistoricoExecucaoDTO.class;
	}

	public void load(DocumentHTML arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
	}

	public void consultarHistorico(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HistoricoExecucaoService historicoExecucaoService = (HistoricoExecucaoService) ServiceLocator.getInstance().getService(HistoricoExecucaoService.class, null);
		HistoricoExecucaoDTO historicoExecucaoBean = (HistoricoExecucaoDTO)document.getBean();
		
		Collection col = historicoExecucaoService.findByDemanda(historicoExecucaoBean.getIdDemanda());
		
		HTMLTable tabelaConsultaHist = document.getTableById("tabelaConsultaHist");
		
		tabelaConsultaHist.deleteAllRows();
		tabelaConsultaHist.addRowsByCollection(col, 
				new String[] {"dataStr", "horaStr", "nomeEmpregado", "detalhamentoConv", "situacaoDesc"}, 
				null, 
				"Já existe registrado este registro na tabela", 
				null, 
				null, 
				null);
		tabelaConsultaHist.applyStyleClassInAllCells("tamanho10");		
	}	
}
