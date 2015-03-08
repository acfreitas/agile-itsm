package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadesAccServicosService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ReclassificarSolicitacao extends SolicitacaoServicoMultiContratos {
	ContratoDTO contratoDtoAux = new ContratoDTO();

	private Boolean acao = false;

	public boolean validaParametrosUpload() {
		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "");
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equals("")) {
			return false;
		}
		File pastaGed = new File(PRONTUARIO_GED_DIRETORIO);
		if (!pastaGed.exists()) {
			return false;
		}
		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if (DISKFILEUPLOAD_REPOSITORYPATH == null || DISKFILEUPLOAD_REPOSITORYPATH.trim().equals("")) {
			return false;
		}
		File pastaUpload = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if (!pastaUpload.exists()) {
			return false;
		}
		return true;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("parametrosUploadValidos", validaParametrosUpload());
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		super.load(document, request, response);

		String VisualizarPasso = request.getParameter("visualizarPasso");
		VisualizarPasso = UtilStrings.nullToVazio(VisualizarPasso);
		if (VisualizarPasso != null && VisualizarPasso.equalsIgnoreCase("C")) {
			// Reclassificar a solicitação
			document.executeScript("visualizaCollapse3()");
		} else {
			document.executeScript("preparaCollapse1()");
		}
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		HTMLSelect urgencia = (HTMLSelect) document.getSelectById("urgencia");
		urgencia.removeAllOptions();
		urgencia.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		HTMLSelect impacto = (HTMLSelect) document.getSelectById("impacto");
		impacto.removeAllOptions();
		impacto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (!getCalcularDinamicamente().trim().equalsIgnoreCase("S")) {
			urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
			urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
			urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

			impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
			impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
			impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
		} else {
			Collection<UrgenciaDTO> urgenciaDTO = getPrioridadeSolicitacoesService().consultaUrgencia();
			for (UrgenciaDTO urgenciaTemp : urgenciaDTO) {
				urgencia.addOption(urgenciaTemp.getSiglaUrgencia().toString(), urgenciaTemp.getNivelUrgencia());
			}
			Collection<ImpactoDTO> impactoDTO = getPrioridadeSolicitacoesService().consultaImpacto();
			for (ImpactoDTO impactoTemp : impactoDTO) {
				impacto.addOption(impactoTemp.getSiglaImpacto().toString(), impactoTemp.getNivelImpacto());
			}
		}

		Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

		if (lstGrupos != null) {
			for (GrupoDTO g : lstGrupos) {
				if (g.getAbertura() != null && g.getAbertura().trim().equals("S"))
					document.getElementById("enviaEmailCriacao").setDisabled(true);
				if (g.getEncerramento() != null && g.getEncerramento().trim().equals("S"))
					document.getElementById("enviaEmailFinalizacao").setDisabled(true);
				if (g.getAndamento() != null && g.getAndamento().trim().equals("S"))
					document.getElementById("enviaEmailAcoes").setDisabled(true);
			}
		}

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
		Collection colContratos = contratoService.list();

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		Collection colContratosColab = null;
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}
		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
		if (colContratos != null) {
			if (colContratos.size() > 0) {
				((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			} else {
				acao = true;
			}
			for (Iterator it = colContratos.iterator(); it.hasNext();) {
				ContratoDTO contratoDto = (ContratoDTO) it.next();
				if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
					if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se parametro de colaboradores por contrato ativo, entao filtra.
						if (colContratosColab == null) {
							continue;
						}
						if (!isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
							continue;
						}
					}
					String nomeCliente = "";
					String nomeForn = "";
					ClienteDTO clienteDto = new ClienteDTO();
					clienteDto.setIdCliente(contratoDto.getIdCliente());
					clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
					if (clienteDto != null) {
						nomeCliente = clienteDto.getNomeRazaoSocial();
					}
					FornecedorDTO fornecedorDto = new FornecedorDTO();
					fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
					fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
					if (fornecedorDto != null) {
						nomeForn = fornecedorDto.getRazaoSocial();
					}
					contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
					if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ")";
						((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), nomeContrato);
						contratoDto.setNome(nomeContrato);
						listaContratos.add(contratoDto);
					}
				}
			}
		}
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getUrgencia() != null && StringUtils.isNotBlank(solicitacaoServicoDto.getUrgencia())) {
			document.getElementById("urgencia").setValue(solicitacaoServicoDto.getUrgencia().trim());
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getImpacto() != null && StringUtils.isNotBlank(solicitacaoServicoDto.getImpacto())) {
			document.getElementById("impacto").setValue(solicitacaoServicoDto.getImpacto().trim());
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null) {
			document.getElementById("idContrato").setValue("" + solicitacaoServicoDto.getIdContrato());
		}

		if (request.getParameter("idContrato") != null && !request.getParameter("idContrato").equalsIgnoreCase("")) {
			Integer idContrato = 0;
			idContrato = Integer.parseInt(request.getParameter("idContrato"));

			if (idContrato != null) {
				document.getElementById("idContrato").setValue("" + idContrato);
			}
		}

		document.executeScript("desativarBotaoAvancar1();");
		document.executeScript("desativarBotaoAvancar2();");
		document.executeScript("desativarBotaoAvancar3();");

		if(solicitacaoServicoDto != null){
			solicitacaoServicoDto.getIdContrato();
		}
		String tarefaAssociada = "N";
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdTarefa() != null)
			tarefaAssociada = "S";
		request.setAttribute("tarefaAssociada", tarefaAssociada);

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null) {
			verificaGrupoExecutor(document, request, response);
			document.getSelectById("idGrupoAtual").setValue("" + solicitacaoServicoDto.getIdGrupoAtual());
		}
		if (acao) {
			if (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
				this.verificaGrupoExecutor(document, request, response);
				this.verificaImpactoUrgencia(document, request, response);
				this.carregaServicosMulti(document, request, response);
				this.carregaUnidade(document, request, response);
			}

		}
		// document.executeScript("$('#loading_overlay').hide();");

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() != 0) {
			// document.getElementById("divSLAPrevisto").setVisible(false);
			// document.getElementById("divTipoSolicitacaoServico").setClassName("col_50");
		} else {
			// document.getElementById("divSLAPrevisto").setVisible(true);
			// document.getElementById("divTipoSolicitacaoServico").setClassName("col_30");
		}

		document.executeScript("parent.fecharJanelaAguarde();");
		solicitacaoServicoDto = null;
	}

	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}

	public void sincronizaAD(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getFiltroADPesq() == null) {
			solicitacaoServicoDto.setFiltroADPesq(document.getElementById("filtroADPesq").getValue());
		}

		ContratoDTO contratoDto = new ContratoDTO();

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());

		contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

		Collection<ADUserDTO> listUsuariosADDto = LDAPUtils.consultaEmpregado(solicitacaoServicoDto.getFiltroADPesq(), contratoDto.getIdGrupoSolicitante());

		if (listUsuariosADDto != null && !listUsuariosADDto.isEmpty()) {

			for (ADUserDTO usuarioADDto : listUsuariosADDto) {

				document.getElementById("POPUP_SINCRONIZACAO_DETALHE").setInnerHTML("Sincronização concluída com sucesso. Favor fazer a busca na lookup de colaborador.");
			}

		} else {

			document.getElementById("POPUP_SINCRONIZACAO_DETALHE").setInnerHTML("Nenhum resultado encontrado.");

		}
		document.executeScript("fechar_aguarde();");

		solicitacaoServicoDto = null;

		contratoDto = null;
	}

	public void carregaServicosMulti(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		document.getElementById("divScript").setInnerHTML(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.scriptservico"));
		HTMLSelect idServico = (HTMLSelect) document.getSelectById("idServico");
		idServico.removeAllOptions();

		if (solicitacaoServicoDto.getIdTipoDemandaServico() == null)
			return;
		if (solicitacaoServicoDto.getIdContrato() == null) {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.contrato"));
			return;
		}

		String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");

		if (!UtilStrings.isNotVazio(controleAccUnidade)) {
			controleAccUnidade = "N";
		}

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		int idUnidade = -999;
		if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
			EmpregadoDTO empregadoDto = new EmpregadoDTO();
			empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
			if (solicitacaoServicoDto.getIdSolicitante() != null) {
				empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
				if (empregadoDto != null && empregadoDto.getIdUnidade() != null) {
					idUnidade = empregadoDto.getIdUnidade().intValue();
				}
			}
		}

		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		UnidadesAccServicosService unidadeAccService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
		idServico.removeAllOptions();
		Collection col = servicoService.findByIdTipoDemandaAndIdContrato(solicitacaoServicoDto.getIdTipoDemandaServico(), solicitacaoServicoDto.getIdContrato(),
				solicitacaoServicoDto.getIdCategoriaServico());

		int cont = 0;
		Integer idServicoCasoApenas1 = null;
		if (col != null) {
			// this.verificaImpactoUrgencia(document, request, response);
			/* if (col.size() > 1) */

			// idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

			for (Iterator it = col.iterator(); it.hasNext();) {
				ServicoDTO servicoDTO = (ServicoDTO) it.next();
				if (servicoDTO.getDeleted() == null || servicoDTO.getDeleted().equalsIgnoreCase("N")) {
					if (servicoDTO.getIdSituacaoServico().intValue() == 1) { // ATIVO
						if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
							UnidadesAccServicosDTO unidadesAccServicosDTO = new UnidadesAccServicosDTO();
							unidadesAccServicosDTO.setIdServico(servicoDTO.getIdServico());
							unidadesAccServicosDTO.setIdUnidade(idUnidade);
							unidadesAccServicosDTO = (UnidadesAccServicosDTO) unidadeAccService.restore(unidadesAccServicosDTO);
							if (unidadesAccServicosDTO != null) {// Se existe acesso
								// idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
								idServicoCasoApenas1 = servicoDTO.getIdServico();
								cont++;
							}
						} else {
							// idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
							idServicoCasoApenas1 = servicoDTO.getIdServico();
							cont++;
						}
					}
				}
			}
			// --- RETITRADO POR EMAURI EM 16/07 - TRATAMENTO DE DELETED --> idServico.addOptions(col, "idServico", "nomeServico", null);
		}
		if (cont == 1) { // Se for apenas um servico encontrado, ja executa o carrega contratos.
			solicitacaoServicoDto.setIdServico(idServicoCasoApenas1);
			carregaBaseConhecimentoAssoc(document, request, response);
		}
		// document.executeScript("geraAutoCompleteServico()");

		solicitacaoServicoDto = null;
	}

	public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		if (solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() > 0) {

			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

			solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);

			ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
			servicoContratoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());
			if (solicitacaoServicoDto.getIdServicoContrato() != null) {
				servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
			} else {
				servicoContratoDTO = null;
			}
			if (servicoContratoDTO != null) {
				solicitacaoServicoDto.setIdServico(servicoContratoDTO.getIdServico());
				solicitacaoServicoDto.setIdContrato(servicoContratoDTO.getIdContrato());
			}

		}

		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		HTMLSelect comboUnidadeMultContratos = (HTMLSelect) document.getSelectById("idUnidade");
		inicializarCombo(comboUnidadeMultContratos, request);
		if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
			Integer idContrato = solicitacaoServicoDto.getIdContrato();
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidadeMultContratos.addOption(unidade.getIdUnidade().toString(), unidade.getNomeNivel());
					}

				}
			}
		} else {
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidadeMultContratos.addOption(unidade.getIdUnidade().toString(), unidade.getNomeNivel());
					}
				}
			}
		}

		solicitacaoServicoDto = null;

	}

	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void carregaServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		carregaServicosMulti(document, request, response);
	}

	public void verificaImpactoUrgencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		document.getSelectById("impacto").setDisabled(false);
		document.getSelectById("urgencia").setDisabled(false);
		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

		if (servicoContratoDto != null) {
			AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
			AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
			if (acordoNivelServicoDto == null) {
				// Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja vinculado ao servicocontrato.
				AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
				AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
				if (acordoServicoContratoDTO == null) {
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
					return;
				}
				// Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
				acordoNivelServicoDto = new AcordoNivelServicoDTO();
				acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
				acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao().restore(acordoNivelServicoDto);
				if (acordoNivelServicoDto == null) {
					// Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
					return;
				}
			}
			if (acordoNivelServicoDto.getImpacto() != null) {
				document.getSelectById("impacto").setValue("" + acordoNivelServicoDto.getImpacto());
				if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
					document.getSelectById("impacto").setDisabled(true);
				}
			} else {
				document.getSelectById("impacto").setValue("B");
			}
			if (acordoNivelServicoDto.getUrgencia() != null) {
				document.getSelectById("urgencia").setValue("" + acordoNivelServicoDto.getUrgencia());
				if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
					document.getSelectById("urgencia").setDisabled(true);
				}
			} else {
				document.getSelectById("urgencia").setValue("B");
			}
		} else {
			document.getSelectById("impacto").setValue("B");
			document.getSelectById("urgencia").setValue("B");
		}

		servicoContratoDto = null;

		solicitacaoServicoDto = null;
	}

	@Override
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.restore(document, request, response);
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getEditar() == null) {
			solicitacaoServicoDto.setEditar("S");
		}
		if (solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
			// document.getElementById("tdListServicos").setVisible(false);
			// document.getElementById("tdLimparServicos").setVisible(false);
			document.getTextBoxById("servicoBusca").setDisabled(true);
		} else {
			if (solicitacaoServicoDto.getReclassificar() == null || solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("N")) {
				// document.getElementById("tdListServicos").setVisible(false);
				// document.getElementById("tdLimparServicos").setVisible(false);
				document.getTextBoxById("servicoBusca").setDisabled(true);
			}
		}

		solicitacaoServicoDto = null;
	}

	public void verificaGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");
			idGrupoAtual.removeAllOptions();
			idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			Collection colGrupos = grupoSegurancaService.listGruposServiceDeskByIdContrato(solicitacaoServicoDto.getIdContrato());
			if (colGrupos != null)
				idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
		}

		verificaGrupoExecutorInterno(document, solicitacaoServicoDto);

		solicitacaoServicoDto = null;
	}

	public void verificaGrupoExecutorInterno(DocumentHTML document, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
		if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null)
			document.getElementById("idGrupoAtual").setValue("" + servicoContratoDto.getIdGrupoExecutor());
		else
			document.getElementById("idGrupoAtual").setValue("");
	}

	public void carregarModalDuplicarSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

		solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);

		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

		servicoContratoDto.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());

		servicoContratoDto = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDto);

		solicitacaoServicoDto.setIdContrato(servicoContratoDto.getIdContrato());

		this.carregaComboOrigem(document, request);

		this.carregaUnidade(document, request, response);

		super.preencherComboLocalidade(document, request, response);

		HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
		formSolicitacaoServico.setValues(solicitacaoServicoDto);

		solicitacaoServicoDto = null;

		servicoContratoDto = null;

	}

	public void duplicarSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		SolicitacaoServicoDTO novaSolicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		UsuarioDTO usuarioDto = new UsuarioDTO();

		usuarioDto = WebUtil.getUsuario(request);

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoOrigem = new SolicitacaoServicoDTO();
		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

		solicitacaoServicoOrigem.setIdSolicitacaoServico(novaSolicitacaoServicoDto.getIdSolicitacaoServico());

		solicitacaoServicoOrigem = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoOrigem);

		servicoContratoDto.setIdServicoContrato(solicitacaoServicoOrigem.getIdServicoContrato());

		servicoContratoDto = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDto);

		novaSolicitacaoServicoDto.setIdSolicitacaoServico(null);
		novaSolicitacaoServicoDto.setIdSolicitacaoPai(solicitacaoServicoOrigem.getIdSolicitacaoServico());
		novaSolicitacaoServicoDto.setIdContatoSolicitacaoServico(null);

		novaSolicitacaoServicoDto.setIdServico(servicoContratoDto.getIdServico());

		novaSolicitacaoServicoDto.setUsuarioDto(usuarioDto);
		novaSolicitacaoServicoDto.setDescricao(solicitacaoServicoOrigem.getDescricao());
		novaSolicitacaoServicoDto.setSituacao(solicitacaoServicoOrigem.getSituacao());
		novaSolicitacaoServicoDto.setRegistroexecucao("");
		novaSolicitacaoServicoDto.setEnviaEmailCriacao("S");

		novaSolicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(novaSolicitacaoServicoDto);

		document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.duplicadacomsucesso"));

		document.executeScript("fecharPopup(\"#formInformacoesContato\")");

		novaSolicitacaoServicoDto = null;
		solicitacaoServicoOrigem = null;
		servicoContratoDto = null;

	}

	public void restauraSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

		if (empregadoDto != null) {
			solicitacaoServicoDto.setSolicitante(empregadoDto.getNome());
			solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
			solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
			solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
			solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
			this.preencherComboLocalidade(document, request, response);
		}

		UsuarioDTO usuarioDto = new UsuarioDTO();
		if(empregadoDto != null){
			usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
		}

		if (usuarioDto != null) {
			String login = usuarioDto.getLogin();

			SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService.retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

			if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
				solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getIdItemConfiguracao());
				solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getItemConfiguracao());
				document.executeScript("exibeCampos()");
			}
		}

		HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
		formSolicitacaoServico.setValues(solicitacaoServicoDto);
		document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

		solicitacaoServicoDto = null;

	}

	private void carregaComboOrigem(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception, LogicException {
		OrigemAtendimentoService origemService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);

		HTMLSelect origem = (HTMLSelect) document.getSelectById("idOrigem");

		origem.removeAllOptions();
		inicializarCombo(origem, request);

		Collection listOrigem = origemService.list();

		if (listOrigem != null) {

			origem.addOptions(listOrigem, "idOrigem", "descricao", null);
		}
	}

	public void preenchePorEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		empregadoDTO = empregadoService.listEmpregadoContrato(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getEmailcontato());
		if (empregadoDTO != null) {
			document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
			document.getElementById("nomecontato").setValue(empregadoDTO.getNome());
			document.getElementById("telefonecontato").setValue(empregadoDTO.getTelefone());
			document.getElementById("idUnidade").setValue(empregadoDTO.getIdUnidade().toString());
			document.getElementById("solicitante").setValue(empregadoDTO.getNome());
			document.getElementById("idOrigem").setValue("3");
		}

		solicitacaoServicoDto = null;
		empregadoDTO = null;
	}

	public void calculaSLA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
			document.executeScript("startLoading()");
			if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0)
				throw new Exception("Contrato não encontrado");
			if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdServico().intValue() == 0)
				throw new Exception("Serviço não encontrado");
			if (solicitacaoServicoDto.getUrgencia() == null || solicitacaoServicoDto.getUrgencia().isEmpty())
				throw new Exception("Urgência não encontrada");
			if (solicitacaoServicoDto.getImpacto() == null || solicitacaoServicoDto.getImpacto().isEmpty())
				throw new Exception("Impacto não encontrado");

			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			String sla = solicitacaoServicoService.calculaSLA(solicitacaoServicoDto, request);
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '" + sla + "';");
			document.executeScript("stopLoading()");
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').style.display='block'");
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto1').style.display='none'");
		} catch (Exception e) {
			document.executeScript("stopLoading()");
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '';");
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto1').style.display='none'");
		}
	}

	public void atualizaGridProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ProblemaDTO problemaDTO = new ProblemaDTO();

		problemaDTO.setIdProblema(bean.getIdProblema());

		problemaDTO = (ProblemaDTO) problemaService.restore(problemaDTO);
		if (problemaDTO == null) {
			return;
		}
		HTMLTable tblProblema = document.getTableById("tblProblema");

		if (problemaDTO.getSequenciaProblema() == null) {
			tblProblema.addRow(problemaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idProblema" }, "Problema já cadastrado!!", new String[] { "exibeIconesProblema" },
					"buscaProblema", null);

		} else {
			tblProblema.updateRow(problemaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idProblema" }, "Problema já cadastrado!!", new String[] { "exibeIconesProblema" },
					"buscaProblema", null, problemaDTO.getSequenciaProblema());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
		document.executeScript("fecharModalProblema();");

		bean = null;
	}

	public void atualizaGridMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();

		requisicaoMudancaDTO.setIdRequisicaoMudanca(bean.getIdRequisicaoMudanca());

		requisicaoMudancaDTO = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);

		HTMLTable tblMudanca = document.getTableById("tblMudanca");

		if (requisicaoMudancaDTO.getSequenciaMudanca() == null) {
			tblMudanca.addRow(null, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idRequisicaoMudanca" }, "Mudança já cadastrado!!", new String[] { "exibeIconesMudanca" },
					"abreMudanca", null);
		} else {
			tblMudanca.updateRow(requisicaoMudancaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idRequisicaoMudanca" }, "Mudança já cadastrado!!",
					new String[] { "exibeIconesMudanca" }, "abreMudanca", null, requisicaoMudancaDTO.getSequenciaMudanca());
		}
		document.getElementById("teste").setValue(tblMudanca.toString());
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
		document.executeScript("fecharModalMudanca();");

		bean = null;
	}

	public void atualizaGridBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();

		if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
			baseConhecimentoDTO.setIdBaseConhecimento(solicitacaoServicoDTO.getIdItemBaseConhecimento());
			baseConhecimentoDTO = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDTO);

			HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");

			if (baseConhecimentoDTO.getSequenciaBaseConhecimento() == null) {
				tblBaseConhecimento.addRow(baseConhecimentoDTO, new String[] { "", "", "idBaseConhecimento", "titulo" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null);
			} else {
				tblBaseConhecimento.updateRow(baseConhecimentoDTO, new String[] { "", "", "idBaseConhecimento", "titulo" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null,
						baseConhecimentoDTO.getSequenciaBaseConhecimento());
			}
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");
			document.executeScript("fecharBaseConhecimento();");
		}
	}

	public void atualizaGridItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		ItemConfiguracaoService baseConhecimentoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

		if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
			itemConfiguracaoDTO.setIdItemConfiguracao(solicitacaoServicoDTO.getIdItemBaseConhecimento());
			itemConfiguracaoDTO = (ItemConfiguracaoDTO) baseConhecimentoService.restore(itemConfiguracaoDTO);

			HTMLTable tblBaseConhecimento = document.getTableById("tblIC");

			if (itemConfiguracaoDTO.getSequenciaIC() == null) {
				tblBaseConhecimento.addRow(itemConfiguracaoDTO, new String[] { "", "", "idItemConfiguracao", "descricao" }, new String[] { "idItemConfiguracao" }, "Item Configuração já cadastrado!!",
						new String[] { "exibeIconesMudanca" }, "abreItemConfiguracao", null);

			} else {
				tblBaseConhecimento.updateRow(itemConfiguracaoDTO, new String[] { "", "", "idBaseConhecimento", "titulo" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null,
						itemConfiguracaoDTO.getSequenciaIC());
			}
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");
			document.executeScript("fecharModalItemConfiguracao();");
		}
	}
}
