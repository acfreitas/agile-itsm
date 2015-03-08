package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")
public class SuspensaoMudanca  extends AjaxFormAction  {

	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		HTMLForm form = document.getForm("form");
		form.clear();	
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO)document.getBean();
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
		JustificativaRequisicaoMudancaService justificativaRequisicaoMudancaService = (JustificativaRequisicaoMudancaService) ServiceLocator.getInstance().getService(JustificativaRequisicaoMudancaService.class, null);
		if(requisicaoMudancaDto.getIdRequisicaoMudanca()!=null){
			requisicaoMudancaDto = requisicaoMudancaService.restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca());
		}
		
		request.setAttribute("dataHoraSolicitacao", requisicaoMudancaDto.getDataHoraSolicitacaoStr());
		
		Collection<JustificativaRequisicaoMudancaDTO> colJustificativas = justificativaRequisicaoMudancaService.listAtivasParaSuspensao();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idJustificativa");
		document.getSelectById("idJustificativa").removeAllOptions();
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colJustificativas != null){
			for(JustificativaRequisicaoMudancaDTO justificativa : colJustificativas){
				comboJustificativa.addOption(justificativa.getIdJustificativaMudanca().toString(), justificativa.getDescricaoJustificativa());
			}
		}
		form.setValues(requisicaoMudancaDto);
		
	}

	 public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
			RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO)document.getBean();	
			if (requisicaoMudancaDto.getIdRequisicaoMudanca() == null)
				return;

			if (requisicaoMudancaDto.getIdJustificativa() == null) {
				document.alert(UtilI18N.internacionaliza(request,"gerenciaservico.suspensaosolicitacao.validacao.justificanaoinformada"));
				return;
			}

			RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
			RequisicaoMudancaDTO requisicaoMudancaDtoAuxiliar = requisicaoMudancaService.restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca());
			requisicaoMudancaDtoAuxiliar.setIdJustificativa(requisicaoMudancaDto.getIdJustificativa());
			requisicaoMudancaDtoAuxiliar.setComplementoJustificativa(requisicaoMudancaDto.getComplementoJustificativa());
			requisicaoMudancaService.suspende(usuario, requisicaoMudancaDtoAuxiliar);
	    	document.executeScript("fechar();");
	    }
	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return RequisicaoMudancaDTO.class;
	}

}
