package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class VerificacaoAtendimento extends AjaxFormAction {

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
		
		HTMLForm form = document.getForm("form");
		form.clear();	
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO)document.getBean();
		if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
			Integer idTarefa = solicitacaoServicoDto.getIdTarefa();
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
			solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);
			solicitacaoServicoDto.setIdTarefa(idTarefa);
			
			form.setValues(solicitacaoServicoDto);
		}
		
		HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");
		idGrupoAtual.removeAllOptions();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupos = grupoSegurancaService.listGruposServiceDesk();
		if (colGrupos != null)
			idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
		
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessï¿½o expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO)document.getBean();
		solicitacaoServicoDto.setUsuarioDto(usuario);
		solicitacaoServicoService.update(solicitacaoServicoDto);
		document.executeScript("fechar();");
	}
}
