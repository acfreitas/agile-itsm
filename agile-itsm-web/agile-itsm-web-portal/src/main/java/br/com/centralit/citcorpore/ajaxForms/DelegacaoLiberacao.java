package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class DelegacaoLiberacao extends AjaxFormAction {

	private EmpregadoService empregadoService;
	private RequisicaoLiberacaoService requisicaoLiberacaoService;

	@Override
	public Class getBeanClass() {
		return RequisicaoLiberacaoDTO.class;
	}

	private EmpregadoService getEmpregadoService() throws ServiceException, Exception{
		if(empregadoService == null){
			empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		}
		return empregadoService;
	}

	private RequisicaoLiberacaoService getRequisicaoLiberacaoService(HttpServletRequest request) throws ServiceException, Exception{
		if(requisicaoLiberacaoService == null){
			requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		}
		return requisicaoLiberacaoService;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		RequisicaoLiberacaoDTO requisicaoLiberacao = (RequisicaoLiberacaoDTO)document.getBean();
		if (requisicaoLiberacao.getIdTarefa() == null)
			return;

		HTMLForm form = document.getForm("form");
		form.clear();

		HTMLSelect idGrupoDestino = (HTMLSelect) document.getSelectById("idGrupoDestino");
		idGrupoDestino.removeAllOptions();
		idGrupoDestino.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupos = grupoSegurancaService.listGruposServiceDesk();
		if (colGrupos != null)
			idGrupoDestino.addOptions(colGrupos, "idGrupo", "nome", null);

		Integer idTarefa = requisicaoLiberacao.getIdTarefa();
		request.setAttribute("nomeTarefa", requisicaoLiberacao.getNomeTarefa());

		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		requisicaoLiberacao = requisicaoLiberacaoService.restoreAll(requisicaoLiberacao.getIdRequisicaoLiberacao());
		request.setAttribute("dataHoraSolicitacao", requisicaoLiberacao.getDataHoraSolicitacaoStr());
		requisicaoLiberacao.setIdTarefa(idTarefa);
		requisicaoLiberacao.setAcaoFluxo(Enumerados.ACAO_DELEGAR);

		carregaUsuarios2(document, requisicaoLiberacao, request);
    	form.setValues(requisicaoLiberacao);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		RequisicaoLiberacaoDTO requisicaoLiberacao = (RequisicaoLiberacaoDTO)document.getBean();
		if (requisicaoLiberacao.getIdTarefa() == null || (requisicaoLiberacao.getIdUsuarioDestino() == null && requisicaoLiberacao.getIdGrupoDestino() == null))
			return;

		String usuarioDestino = null;
		String grupoDestino = null;

		if (requisicaoLiberacao.getIdUsuarioDestino() != null) {
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDestinoDto = new UsuarioDTO();
			usuarioDestinoDto.setIdUsuario(requisicaoLiberacao.getIdUsuarioDestino());
			usuarioDestinoDto = (UsuarioDTO) usuarioService.restore(usuarioDestinoDto);
			if (usuarioDestinoDto != null)
				usuarioDestino = usuarioDestinoDto.getLogin();
		}

		GrupoDTO grupoDestinoDto = null;
		if (requisicaoLiberacao.getIdGrupoDestino() != null) {
			GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			grupoDestinoDto = new GrupoDTO();
			grupoDestinoDto.setIdGrupo(requisicaoLiberacao.getIdGrupoDestino());
			grupoDestinoDto = (GrupoDTO) grupoService.restore(grupoDestinoDto);
			if (grupoDestinoDto != null)
				grupoDestino = grupoDestinoDto.getSigla();
		}

		try{
			String enviarNotificacao =  ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO , "N");
			//if(enviarNotificacao.equalsIgnoreCase("S")){
				//enviaEmailGrupo(request ,Integer.parseInt(ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO, null)), grupoDestinoDto, requisicaoLiberacao);
			//}
		}catch(NumberFormatException e){
			System.out.println("Não há modelo de e-mail setado nos parâmetros.");
		}

		ExecucaoLiberacaoService execucaoFluxoService = (ExecucaoLiberacaoService) ServiceLocator.getInstance().getService(ExecucaoLiberacaoService.class, null);
		execucaoFluxoService.delegaTarefa(usuario.getLogin(), requisicaoLiberacao.getIdTarefa(), usuarioDestino, grupoDestino);
		document.executeScript("fechar();");
	}

	/**
	 * @param idModeloEmail
	 * @throws Exception
	 */
	public void enviaEmailGrupo(HttpServletRequest request, Integer idModeloEmail, GrupoDTO grupoDestino, RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
		if (grupoDestino == null){
			return;
		}

		if (idModeloEmail == null){
			return;
		}

		/*String enviaEmail = ParametroUtil.getValor(ParametroSistema.EnviaEmailFluxo, "N");
		if (!enviaEmail.equalsIgnoreCase("S")){
			return;
		}*/

		ArrayList<EmpregadoDTO> empregados = (ArrayList<EmpregadoDTO>) getEmpregadoService().listEmpregadosByIdGrupo(grupoDestino.getIdGrupo());

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		if (remetente == null)
			throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));

		requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) getRequisicaoLiberacaoService(request).restore(requisicaoLiberacaoDto);
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, WebUtil.getUsuarioSistema(request));
		TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
		tipoDemandaServicoDTO.setIdTipoDemandaServico(requisicaoLiberacaoDto.getIdTipoDemandaServico());
		tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
		requisicaoLiberacaoDto.setDemanda(tipoDemandaServicoDTO.getNomeTipoDemandaServico());

		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] {requisicaoLiberacaoDto});
		try {

			EmpregadoDTO aux = null;
			for(EmpregadoDTO e : empregados){

				aux = (EmpregadoDTO) getEmpregadoService().restore(e);
				if(aux != null && aux.getEmail() != null && !aux.getEmail().trim().equalsIgnoreCase("") ){
					mensagem.envia(aux.getEmail(), "", remetente);
				}
			}
		} catch (Exception e) {
		}
	}


	public void carregaUsuarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacao = (RequisicaoLiberacaoDTO)document.getBean();
		carregaUsuarios2(document, requisicaoLiberacao, request);
	}

	/*ESTAVA DUPLICANDO OS DADOS*/

	/*public void carregaUsuarios2(DocumentHTML document, RequisicaoLiberacaoDTO requisicaoLiberacao , HttpServletRequest request) throws Exception {
		HTMLSelect idUsuarioDestino = (HTMLSelect) document.getSelectById("idUsuarioDestino");
		idUsuarioDestino.removeAllOptions();

		Collection<GrupoEmpregadoDTO> colGrupos = null;
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		if (requisicaoLiberacao.getIdGrupoDestino() == null) {
			GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			Collection<GrupoDTO> col = grupoSegurancaService.listGruposServiceDesk();
			if (col != null) {
				colGrupos = new ArrayList();
				for (GrupoDTO grupoSegurancaDto : col) {
					Collection<GrupoEmpregadoDTO> colAux = grupoEmpregadoService.findByIdGrupo(grupoSegurancaDto.getIdGrupo());
					if (colAux != null)
						colGrupos.addAll(colAux);
				}
			}
		}else
			colGrupos = grupoEmpregadoService.findByIdGrupo(requisicaoLiberacao.getIdGrupoDestino());

		if (colGrupos != null) {
			idUsuarioDestino.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
		    	UsuarioDTO usuarioDto = usuarioService.restoreByIdEmpregado(grupoEmpregadoDto.getIdEmpregado());
		    	if (usuarioDto != null)
		    		idUsuarioDestino.addOption(""+usuarioDto.getIdUsuario(), usuarioDto.getLogin() + " - " + usuarioDto.getNomeUsuario());
			}
    	}
   }*/

	public void carregaUsuarios2(DocumentHTML document, RequisicaoLiberacaoDTO requisicaoLiberacao , HttpServletRequest request) throws Exception {
		HTMLSelect idUsuarioDestino = (HTMLSelect) document.getSelectById("idUsuarioDestino");
		idUsuarioDestino.removeAllOptions();
		Collection<GrupoEmpregadoDTO> colGrupos = null;
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		if (requisicaoLiberacao.getIdGrupoDestino() == null) {
			colGrupos = grupoEmpregadoService.findUsariosGrupo();
		}else
			colGrupos = grupoEmpregadoService.findByIdGrupo(requisicaoLiberacao.getIdGrupoDestino());

		if (colGrupos != null) {
			idUsuarioDestino.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
		    	UsuarioDTO usuarioDto = usuarioService.restoreByIdEmpregado(grupoEmpregadoDto.getIdEmpregado());
		    	if (usuarioDto != null){
		    		idUsuarioDestino.addOption(""+usuarioDto.getIdUsuario(), usuarioDto.getLogin() + " - " + usuarioDto.getNomeUsuario());
		    	}
			}
    	}
   }
}
