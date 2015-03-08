package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
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

public class DelegacaoTarefa extends AjaxFormAction {

	private EmpregadoService empregadoService;
	private SolicitacaoServicoService solicitacaoServicoService;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	private EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		if (empregadoService == null) {
			empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		}
		return empregadoService;
	}

	private SolicitacaoServicoService getSolicitacaoServicoService(HttpServletRequest request) throws ServiceException, Exception {
		if (solicitacaoServicoService == null) {
			solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		}
		return solicitacaoServicoService;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getIdTarefa() == null)
			return;

		/* Recebendo os itens antes do restore */
		Integer idTarefa = solicitacaoServicoDto.getIdTarefa();
		request.setAttribute("nomeTarefa", solicitacaoServicoDto.getNomeTarefa());

		HTMLForm form = document.getForm("form");
		form.clear();

		HTMLSelect idGrupoDestino = (HTMLSelect) document.getSelectById("idGrupoDestino");
		idGrupoDestino.removeAllOptions();
		idGrupoDestino.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());

		Collection listGruposContrato = null;
		
		if (solicitacaoServicoDto != null) {

			if (solicitacaoServicoDto.getIdContrato() != null) {
				listGruposContrato = grupoSegurancaService.listGruposPorUsuario(usuario.getIdUsuario());
			}

			if (listGruposContrato != null) {
				idGrupoDestino.addOptions(listGruposContrato, "idGrupo", "nome", null);
			}

			request.setAttribute("dataHoraSolicitacao", solicitacaoServicoDto.getDataHoraSolicitacaoStr());
			solicitacaoServicoDto.setIdTarefa(idTarefa);
			solicitacaoServicoDto.setAcaoFluxo(Enumerados.ACAO_DELEGAR);

			carregaUsuarios2(document, solicitacaoServicoDto, request);
			form.setValues(solicitacaoServicoDto);
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		
		
		
		if (solicitacaoServicoDto.getIdTarefa() == null || (solicitacaoServicoDto.getIdUsuarioDestino() == null && solicitacaoServicoDto.getIdGrupoDestino() == null))
			return;

		String usuarioDestino = null;
		String grupoDestino = null;
		UsuarioDTO usuarioDestinoDto = new UsuarioDTO();

		if (solicitacaoServicoDto.getIdUsuarioDestino() != null) {
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			usuarioDestinoDto.setIdUsuario(solicitacaoServicoDto.getIdUsuarioDestino());
			usuarioDestinoDto = (UsuarioDTO) usuarioService.restore(usuarioDestinoDto);
			if (usuarioDestinoDto != null)
				usuarioDestino = usuarioDestinoDto.getLogin();
		}

		GrupoDTO grupoDestinoDto = null;
		if (solicitacaoServicoDto.getIdGrupoDestino() != null) {
			GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			grupoDestinoDto = new GrupoDTO();
			grupoDestinoDto.setIdGrupo(solicitacaoServicoDto.getIdGrupoDestino());
			grupoDestinoDto = (GrupoDTO) grupoService.restore(grupoDestinoDto);
			if (grupoDestinoDto != null)
				grupoDestino = grupoDestinoDto.getSigla();
		}

		try {
			String enviarNotificacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, "N");
			if (enviarNotificacao.equalsIgnoreCase("S") && grupoDestinoDto != null) {
				
				enviaEmailGrupo(request, Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO, null)), grupoDestinoDto, solicitacaoServicoDto);
			}
			
			//Caso não tenha um grupo e tenha apenas o usuário de destino
			else if(solicitacaoServicoDto.getIdUsuarioDestino() != null)
			{
				
				String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
				if (remetente == null)
					throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));

				solicitacaoServicoDto = (SolicitacaoServicoDTO) getSolicitacaoServicoService(request).restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
				TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, WebUtil.getUsuarioSistema(request));
				TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();

				if (solicitacaoServicoDto.getIdTipoDemandaServico() != null) {
					tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoDto.getIdTipoDemandaServico());
					tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
					solicitacaoServicoDto.setDemanda(tipoDemandaServicoDTO.getNomeTipoDemandaServico());
				}
				solicitacaoServicoDto.setServico(solicitacaoServicoService.listaServico(solicitacaoServicoDto.getIdSolicitacaoServico()));
				
				MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO, null)), new IDto[] { solicitacaoServicoDto });
				try {

					EmpregadoDTO aux = null;
					aux = (EmpregadoDTO) getEmpregadoService().restoreByIdEmpregado(usuarioDestinoDto.getIdEmpregado());
					if (aux != null && aux.getEmail() != null && !aux.getEmail().trim().equalsIgnoreCase("")) {
						mensagem.envia(aux.getEmail(), "", remetente);
					
					}
				} catch (Exception e) {
					System.out.println("Não foi possível enviar o e-mail. \n" + e.getMessage());
				}
			}
			
			
			
		} catch (NumberFormatException e) {
			System.out.println("Não há modelo de e-mail setado nos parâmetros.");
		}

		ExecucaoSolicitacaoService execucaoFluxoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		execucaoFluxoService.delegaTarefa(usuario.getLogin(), solicitacaoServicoDto.getIdTarefa(), usuarioDestino, grupoDestino);

		document.executeScript("parent.refreshTelaGerenciamento();");
	}

	/**
	 * @param idModeloEmail
	 * @throws Exception
	 */
	public void enviaEmailGrupo(HttpServletRequest request, Integer idModeloEmail, GrupoDTO grupoDestino, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (grupoDestino == null) {
			return;
		}

		if (idModeloEmail == null) {
			return;
		}

		/*
		 * String enviaEmail = ParametroUtil.getValue(ParametroSistema.EnviaEmailFluxo, "N"); if (!enviaEmail.equalsIgnoreCase("S")){ return; }
		 */

		ArrayList<EmpregadoDTO> empregados = (ArrayList<EmpregadoDTO>) getEmpregadoService().listEmpregadosByIdGrupo(grupoDestino.getIdGrupo());

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		if (remetente == null)
			throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));

		solicitacaoServicoDto = (SolicitacaoServicoDTO) getSolicitacaoServicoService(request).restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, WebUtil.getUsuarioSistema(request));
		TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();

		if (solicitacaoServicoDto.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoDto.getIdTipoDemandaServico());
			tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
			solicitacaoServicoDto.setDemanda(tipoDemandaServicoDTO.getNomeTipoDemandaServico());
		}
		solicitacaoServicoDto.setServico(solicitacaoServicoService.listaServico(solicitacaoServicoDto.getIdSolicitacaoServico()));

		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { solicitacaoServicoDto });
		try {

			EmpregadoDTO aux = null;
			for (EmpregadoDTO e : empregados) {

				aux = (EmpregadoDTO) getEmpregadoService().restore(e);
				if (aux != null && aux.getEmail() != null && !aux.getEmail().trim().equalsIgnoreCase("")) {
					mensagem.envia(aux.getEmail(), "", remetente);
				}
			}
		} catch (Exception e) {
		}
	}

	public void carregaUsuarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		carregaUsuarios2(document, solicitacaoServicoDto, request);
	}

	public void carregaUsuarios2(DocumentHTML document, SolicitacaoServicoDTO solicitacaoServicoDto, HttpServletRequest request) throws Exception {
		HTMLSelect idUsuarioDestino = (HTMLSelect) document.getSelectById("idUsuarioDestino");
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		idUsuarioDestino.removeAllOptions();
		
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		
		ArrayList<GrupoEmpregadoDTO> listGrupoEmpregado = new ArrayList<GrupoEmpregadoDTO>();
		Collection<GrupoDTO> listGruposContrato = null;
		Collection<GrupoEmpregadoDTO> listGruposContratoAux = null;
		if (solicitacaoServicoDto.getIdContrato() != null) {
			listGruposContrato = grupoSegurancaService.listGruposPorUsuario(usuario.getIdUsuario());
		}
		
		Set<GrupoEmpregadoDTO> listGrupoEmpregadoAux= new HashSet<GrupoEmpregadoDTO>();
		if (solicitacaoServicoDto != null && listGruposContrato!= null) {
			
			for (GrupoDTO grupoDTO : listGruposContrato) {
				listGruposContratoAux =  grupoEmpregadoService.findByIdGrupo(grupoDTO.getIdGrupo());
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : listGruposContratoAux) {
					listGrupoEmpregadoAux.add(grupoEmpregadoDTO);
				}
			} 			
			
			listGrupoEmpregado.clear();
			listGrupoEmpregado.addAll(listGrupoEmpregadoAux);
			
			if (listGrupoEmpregado != null) {

				idUsuarioDestino.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
				UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
				
				for (GrupoEmpregadoDTO grupoEmpregadoDto : listGrupoEmpregado) {
					
					UsuarioDTO usuarioDto = usuarioService.restoreByIdEmpregado(grupoEmpregadoDto.getIdEmpregado());
					if (usuarioDto != null) {
						idUsuarioDestino.addOption("" + usuarioDto.getIdUsuario(), usuarioDto.getLogin() + " - " + usuarioDto.getNomeUsuario());
					}
				}
			}
		}
	}
}
