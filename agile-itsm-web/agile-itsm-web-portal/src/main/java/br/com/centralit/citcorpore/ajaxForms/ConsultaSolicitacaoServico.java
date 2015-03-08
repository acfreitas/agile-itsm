package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class ConsultaSolicitacaoServico extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO)document.getBean();
		if (solicitacaoServicoDto.getIdSolicitacaoServico() == null)
			return;
		
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (solicitacaoServicoDto != null) {
			request.setAttribute("dataHoraSolicitacao", solicitacaoServicoDto.getDataHoraSolicitacaoStr());
        	HTMLForm form = document.getForm("form");
        	form.setValues(solicitacaoServicoDto);
		}
	}

}
