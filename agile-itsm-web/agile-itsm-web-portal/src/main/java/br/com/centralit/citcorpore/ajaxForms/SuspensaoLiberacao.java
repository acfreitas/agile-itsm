package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")
public class SuspensaoLiberacao  extends AjaxFormAction  {

	
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
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO)document.getBean();
		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		JustificativaRequisicaoLiberacaoService justificativaRequisicaoLiberacaoService = (JustificativaRequisicaoLiberacaoService) ServiceLocator.getInstance().getService(JustificativaRequisicaoLiberacaoService.class, null);
		if(requisicaoLiberacaoDto.getIdRequisicaoLiberacao()!=null){
			requisicaoLiberacaoDto = requisicaoLiberacaoService.restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		}
		
		request.setAttribute("dataHoraSolicitacao", requisicaoLiberacaoDto.getDataHoraSolicitacaoStr());
		
		Collection<JustificativaRequisicaoLiberacaoDTO> colJustificativas = justificativaRequisicaoLiberacaoService.listAtivasParaSuspensao();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idJustificativa");
		document.getSelectById("idJustificativa").removeAllOptions();
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colJustificativas != null){
			for(JustificativaRequisicaoLiberacaoDTO justificativa : colJustificativas){
				comboJustificativa.addOption(justificativa.getIdJustificativaLiberacao().toString(), justificativa.getDescricaoJustificativa());
			}
		}
		form.setValues(requisicaoLiberacaoDto);
		
	}

	 public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
			RequisicaoLiberacaoDTO requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO)document.getBean();	
			if (requisicaoLiberacaoDto.getIdRequisicaoLiberacao() == null)
				return;

			if (requisicaoLiberacaoDto.getIdJustificativa() == null) {
				document.alert(UtilI18N.internacionaliza(request,"gerenciaservico.suspensaosolicitacao.validacao.justificanaoinformada"));
				return;
			}

			RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
			RequisicaoLiberacaoDTO requisicaoLiberacaoDtoAuxiliar = requisicaoLiberacaoService.restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
			requisicaoLiberacaoDtoAuxiliar.setIdJustificativa(requisicaoLiberacaoDto.getIdJustificativa());
			requisicaoLiberacaoDtoAuxiliar.setComplementoJustificativa(requisicaoLiberacaoDto.getComplementoJustificativa());
			requisicaoLiberacaoService.suspende(usuario, requisicaoLiberacaoDtoAuxiliar);
	    	document.executeScript("fechar();");
	    }
	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return RequisicaoLiberacaoDTO.class;
	}

}
