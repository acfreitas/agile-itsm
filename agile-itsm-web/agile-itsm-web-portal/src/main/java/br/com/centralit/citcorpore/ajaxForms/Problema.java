package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLCheckbox;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.ProblemaRelacionadoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.centralit.citcorpore.bean.SolucaoDefinitivaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaProblemaDAO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.CategoriaProblemaService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ConhecimentoProblemaService;
import br.com.centralit.citcorpore.negocio.ContatoProblemaService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmailSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoProblemaService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.JustificativaProblemaService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.OcorrenciaProblemaService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.ProblemaItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ProblemaMudancaService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoProblemaService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.SolucaoContornoService;
import br.com.centralit.citcorpore.negocio.SolucaoDefinitivaService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadesAccServicosService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoProblema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.Enumerados.TipoOrigemLeituraEmail;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class Problema extends AjaxFormAction {

	private ProblemaService problemaService;
	private EmpregadoService usuarioService;
	private CategoriaProblemaService categoriaProblemaService;
	private ProblemaItemConfiguracaoService problemaItemConfiguracaoService;
	private ItemConfiguracaoService itemConfiguracaoService;
	private PastaService pastaService;
	private SolicitacaoServicoProblemaService solicitacaoServicoProblemaService;
	private UsuarioDTO usuario;

	private ProblemaMudancaService problemaMudancaService;
	private RequisicaoMudancaService requisicaoMudancaService;

	ContratoDTO contratoDtoAux = new ContratoDTO();

	private ProblemaDTO problemaDto;
	private Boolean acao = false;

	@Override
	public Class getBeanClass() {
		return ProblemaDTO.class;
	}

	public ProblemaDTO getProblemaDto() {
		return problemaDto;
	}

	public void setProblemaDto(ProblemaDTO problemaDto) {
		this.problemaDto = problemaDto;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
		StringBuilder objeto;
		if ((UNIDADE_AUTOCOMPLETE != null) && (UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))) {
			objeto = new StringBuilder();
			objeto.append("<select  id='idContrato' name='idContrato' class='Valid[Required] Description[\"contrato.contrato\"]' ");
			objeto.append("onchange='setaValorLookup(this);'>");
			objeto.append("</select>");
			document.getElementById("divContrato").setInnerHTML(objeto.toString());

			objeto = new StringBuilder();
			objeto.append("<input type='text' name='unidadeDes' id='unidadeDes' style='width: 100%;' onkeypress='onkeypressUnidadeDes();'>");
			objeto.append("<input type='hidden' name='idUnidade' id='idUnidade' value='0'/>");
			document.getElementById("divUnidade").setInnerHTML(objeto.toString());
			document.executeScript("montaParametrosAutocompleteUnidade()");
		} else {
			objeto = new StringBuilder();
			objeto.append("<select  id='idContrato' name='idContrato' class='Valid[Required] Description[\"contrato.contrato\"]' ");
			objeto.append("onchange='setaValorLookup(this);' onclick= 'document.form.fireEvent(\"carregaUnidade\");'>");
			objeto.append("</select>");
			document.getElementById("divContrato").setInnerHTML(objeto.toString());

			objeto = new StringBuilder();
			objeto.append("<select name='idUnidade' id = 'idUnidade' onchange='document.form.fireEvent(\"preencherComboLocalidade\")' class='Valid[Required] Description[colaborador.cadastroUnidade]'></select>");
			document.getElementById("divUnidade").setInnerHTML(objeto.toString());
		}

		/**
		 * Adicionado para fazer limpeza da seção quando for gerenciamento de Serviço
		 *
		 * @author mario.junior
		 * @since 28/10/2013 08:21
		 */
		request.getSession(true).setAttribute("colUploadRequisicaoProblemaGED", null);
		problemaDto = (ProblemaDTO) document.getBean();

		document.executeScript("$('#abas').hide()");
		document.executeScript("$('#statusProblema').hide()");
		document.executeScript("$('#divResolvido').hide()");
		// document.executeScript("$('#relacionarErrosConhecidos').hide()");
		// document.executeScript("$('#abaMudancas').hide()");
		// document.executeScript("$('#abaRevisaoProblemaGrave').hide()");
		// document.executeScript("$('#divProblemaGrave').hide()");
		// document.executeScript("$('#divPrecisaMudanca').hide()");
		// document.executeScript("$('#divPrecisaSolucaoContorno').hide()");
		document.executeScript("$('#divBotaoFecharFrame').hide()");
		document.executeScript("$('#statusCancelada').hide()");

		// Início do load.
		if (problemaDto == null || problemaDto.getIdProblema() == null) {
			document.getElementById("btOcorrencias").setVisible(false);
		}

		String descricaoSolicitacao = (String) request.getSession().getAttribute("DescricaoSolicitacao");
		request.getSession().removeAttribute("DescricaoSolicitacao");
		String iframeSolicitacao = request.getParameter("solicitacaoServico");

		if (descricaoSolicitacao != null && !descricaoSolicitacao.equalsIgnoreCase("")) {
			document.getElementById("DescricaoAuxliar").setInnerHTML(descricaoSolicitacao);
			document.executeScript("setarDescricao()");
		}

		document.getElementById("enviaEmailCriacao").setDisabled(true);

		document.getElementById("enviaEmailFinalizacao").setDisabled(true);

		usuario = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);

		if (usuario == null) {

			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));

			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);

		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		alimentaComboPastasBaseConhecimento(request, document);

		HTMLSelect idGrupoAtual = document.getSelectById("idGrupo");

		idGrupoAtual.removeAllOptions();

		idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection colGrupos = grupoSegurancaService.listaGruposAtivos();

		if (colGrupos != null) {
			idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
		}

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}

		Collection colContratosColab = null;

		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}

		Collection colContratos = contratoService.list();

		document.getSelectById("idContrato").removeAllOptions();

		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();

		if (colContratos != null) {

			if (colContratos.size() > 1) {

				document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

			} else {
				acao = true;
			}

			for (Iterator it = colContratos.iterator(); it.hasNext();) {

				ContratoDTO contratoDto = (ContratoDTO) it.next();

				if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {

					// Se parametro de colaboradores por contrato ativo, entao filtra.
					if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {

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

						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request))
								+ " (" + nomeCliente + " - " + nomeForn + ")";

						document.getSelectById("idContrato").addOption("" + contratoDto.getIdContrato(), nomeContrato);

						contratoDto.setNome(nomeContrato);

						listaContratos.add(contratoDto);
					}
				}
			}
		}
		if (acao) {
			this.carregaServicosMulti(document, request, response);
			if ((UNIDADE_AUTOCOMPLETE != null) && (!UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))) {
				this.carregaUnidade(document, request, response);
			}
		}

		this.alimentaComboCategoriaProblema(request, document);

		this.preencherComboCausa(document, request, response);

		this.preencherComboCategoriaSolucao(document, request, response);

		this.preencherComboOrigem(document, request, response);

		// this.preencherComboCalendario(document, request, response);

		if (request.getParameter("chamarTelaProblema") != null && !request.getParameter("chamarTelaProblema").equalsIgnoreCase("")) {
			Integer tarefa = this.obterIdTarefa(problemaDto, request);
			if (tarefa > 0) {
				problemaDto.setIdTarefa(tarefa);
			}
		}

		String tarefaAssociada = "N";

		if (problemaDto != null && problemaDto.getIdTarefa() != null) {

			tarefaAssociada = "S";

			request.setAttribute("tarefaAssociada", tarefaAssociada);
		}

		if (problemaDto != null && problemaDto.getIdContrato() != null) {
			document.getElementById("idContrato").setValue("" + problemaDto.getIdContrato());
		}

		if (problemaDto != null && problemaDto.getIdProblema() != null) {
			this.restore(document, request, response);
		}

		this.carregarInformacaoProblemaAnaliseTendencia(document, request, response);

		document.getElementById("iframeSolicitacao").setValue(iframeSolicitacao);

		problemaDto = null;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author maycon.silva
	 *
	 *         O método carrega as informações de um problema caso haja tendencia identifcada no relatorio de Analise de Tendência
	 *
	 */
	private void carregarInformacaoProblemaAnaliseTendencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		if (request.getParameter("tendenciaProblema") != null && request.getParameter("tendenciaProblema").equalsIgnoreCase("S")) {
			String tipo = request.getParameter("tipo");

			if (request.getParameter("idContrato") != null && !request.getParameter("idContrato").equalsIgnoreCase("")) {
				this.getProblemaDto().setIdContrato(new Integer(request.getParameter("idContrato")));

				String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
				if ((UNIDADE_AUTOCOMPLETE != null) && (!UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))) {
					this.carregaUnidade(document, request, response);
				}

				document.executeScript("setaValorLookup('" + this.getProblemaDto().getIdContrato() + "');");
			}

			if (tipo != null && !tipo.trim().equalsIgnoreCase("")) {
				if (tipo.equalsIgnoreCase("servico")) {
					this.preencheServicoProblemaByTendenciaProblema(document, request, response);
				}
				if (tipo.equalsIgnoreCase("causa")) {
					this.preencheCausaProblemaByTendenciaProblema(document, request, response);
				}

				if (tipo.equalsIgnoreCase("itemConfiguracao")) {
					this.preencheItemConfiguracaoProblemaByTendenciaProblema(document, request, response);
				}
			}

			HTMLForm form = document.getForm("form");
			form.setValues(problemaDto);
		}
	}

	/**
	 * Preenche o serviço caso seja uma tendencio do tipo serviço, e seta o serviço na descrição do problema
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author maycon.silva
	 *
	 *
	 */
	private void preencheServicoProblemaByTendenciaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		if (request.getParameter("id") != null && !request.getParameter("id").equalsIgnoreCase("")) {
			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
			ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
			servicoContratoDTO.setIdServicoContrato(new Integer(request.getParameter("id")));
			servicoContratoDTO = servicoContratoService.findByIdServicoContrato(servicoContratoDTO.getIdServicoContrato(), this.getProblemaDto().getIdContrato());
			this.getProblemaDto().setDescricao("Serviço do Contrato: " + servicoContratoDTO.getNomeServico());
		}
	}

	/**
	 * Preenche a Causa caso seja uma tendencio do tipo Causa, e seta o Causa na descrição do problema
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author maycon.silva
	 *
	 *
	 */
	private void preencheCausaProblemaByTendenciaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		if (request.getParameter("id") != null && !request.getParameter("id").equalsIgnoreCase("")) {
			CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
			CausaIncidenteDTO causaIncidenteDTO = new CausaIncidenteDTO();
			causaIncidenteDTO.setIdCausaIncidente(new Integer(request.getParameter("id")));
			causaIncidenteDTO = (CausaIncidenteDTO) causaIncidenteService.restore(causaIncidenteDTO);
			this.getProblemaDto().setDescricao("Causa: " + causaIncidenteDTO.getDescricaoCausa());
			this.getProblemaDto().setIdCausa(new Integer(request.getParameter("id")));
		}
	}

	/**
	 * Preenche o Item configuração caso seja uma tendencio do tipo Item configuração, e seta o Item configuração na descrição do problema
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author maycon.silva
	 *
	 *
	 */
	private void preencheItemConfiguracaoProblemaByTendenciaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		if (request.getParameter("id") != null && !request.getParameter("id").equalsIgnoreCase("")) {
			ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
			itemConfiguracaoDto = itemConfiguracaoService.restoreByIdItemConfiguracao(new Integer(request.getParameter("id")));
			this.getProblemaDto().setDescricao("Item Configuração: " + itemConfiguracaoDto.getIdentificacao());
		}
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

	private void alimentaComboPastasBaseConhecimento(HttpServletRequest request, DocumentHTML document) throws Exception {
		HTMLSelect combo = document.getSelectById("idPastaBaseConhecimento");
		inicializaCombo(request, combo);
		Collection<PastaDTO> pastasAtivas = this.getPastaService().consultarPastasAtivas();
		ArrayList<PastaDTO> listaPastaAux = (ArrayList<PastaDTO>) this.getPastaService().listPastasESubpastas(usuario);
		for (PastaDTO pasta : listaPastaAux) {
			if (pasta.getDataFim() == null) {
				combo.addOption(pasta.getId().toString(), pasta.getNomeNivel());
			}
		}
	}

	/**
	 * Carrega combo de Origem do conhecimento
	 *
	 * @param document
	 * @param request
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboOrigem(DocumentHTML document, HttpServletRequest request) throws Exception {

		HTMLSelect combo = document.getSelectById("comboOrigem");
		combo.removeAllOptions();

		combo.addOption(BaseConhecimentoDTO.CONHECIMENTO.toString(), UtilI18N.internacionaliza(request, "baseConhecimento.conhecimento"));
		combo.addOption(BaseConhecimentoDTO.EVENTO.toString(), UtilI18N.internacionaliza(request, "justificacaoFalhas.evento"));
		combo.addOption(BaseConhecimentoDTO.MUDANCA.toString(), UtilI18N.internacionaliza(request, "requisicaMudanca.mudanca"));
		combo.addOption(BaseConhecimentoDTO.INCIDENTE.toString(), UtilI18N.internacionaliza(request, "solicitacaoServico.incidente"));
		combo.addOption(BaseConhecimentoDTO.SERVICO.toString(), UtilI18N.internacionaliza(request, "servico.servico"));
		combo.addOption(BaseConhecimentoDTO.PROBLEMA.toString(), UtilI18N.internacionaliza(request, "problema.problema"));
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		/**
		 * Adicionado para fazer limpeza da seção quando for gerenciamento de Serviço
		 *
		 * @author mario.junior
		 * @since 28/10/2013 08:21
		 */
		request.getSession(true).setAttribute("colUploadRequisicaoProblemaGED", null);
		this.setProblemaDto((ProblemaDTO) document.getBean());

		UsuarioDTO usuarioDto = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);

		ProblemaDTO problemaAuxDto = this.getProblemaDto();

		if (request.getParameter("chamarTelaProblema") != null && !request.getParameter("chamarTelaProblema").equalsIgnoreCase("")) {
			this.setProblemaDto(this.getProblemaService(request).restoreAll(problemaDto));
		} else {
			this.setProblemaDto(this.getProblemaService(request).restoreAll(problemaDto.getIdProblema()));
		}

		document.executeScript("$('#abas').show()");

		document.executeScript("$('#statusProblema').show()");
		document.executeScript("$('#divProblemaGrave').show()");
		document.executeScript("$('#divPrecisaMudanca').show()");
		document.executeScript("$('#divPrecisaSolucaoContorno').show()");

		this.atualizaGridProblema(document, request, response);

		Integer idTarefa = problemaAuxDto.getIdTarefa();

		String acaoFluxo = problemaAuxDto.getAcaoFluxo();

		String escalar = problemaAuxDto.getEscalar();

		String alterarSituacao = problemaAuxDto.getAlterarSituacao();

		String fase = problemaAuxDto.getFase();

		String editar = problemaAuxDto.getEditar();

		ContatoProblemaService contatoPloblemaService = (ContatoProblemaService) ServiceLocator.getInstance().getService(ContatoProblemaService.class, null);

		if (problemaDto.getPrecisaSolucaoContorno() != null) {
			// inicio: thiago.monteiro
			if (problemaDto.getPrecisaSolucaoContorno().equalsIgnoreCase("S")) {

				document.executeScript("$('input[type=radio][name=precisaSolucaoContorno][value=\"S\"]').attr('checked', true)");

			} else {

				document.executeScript("$('input[type=radio][name=precisaSolucaoContorno][value=\"N\"]').attr('checked', true)");

			}
		}

		if (problemaDto.getPrecisaMudanca() != null) {
			if (problemaDto.getPrecisaMudanca().equalsIgnoreCase("S")) {
				document.executeScript("$('#abaMudancas').show()");
				document.executeScript("$('input[type=radio][name=precisaMudanca][value=\"S\"]').attr('checked', true)");

			} else {

				document.executeScript("$('input[type=radio][name=precisaMudanca][value=\"N\"]').attr('checked', true)");

			}
		}

		if (problemaDto.getGrave() != null) {
			if (problemaDto.getGrave().equalsIgnoreCase("S")) {
				document.executeScript("$('#abaRevisaoProblemaGrave').show()");
				document.executeScript("$('input[type=radio][name=grave][value=\"S\"]').attr('checked', true)");

			} else {

				document.executeScript("$('input[type=radio][name=grave][value=\"N\"]').attr('checked', true)");

			}
		}

		if (problemaDto.getPrecisaSolucaoContorno() != null) {
			if (problemaDto.getPrecisaSolucaoContorno().equalsIgnoreCase("S")) {

				// Utilizando o evento click para evitar o erro relacionado ao recarregamento do form.
				document.executeScript("$('input[type=radio][name=precisaSolucaoContorno][value=\"S\"]').click()");

			} else {

				document.executeScript("$('input[type=radio][name=precisaSolucaoContorno][value=\"N\"]').click()");

			}
			// fim: thiago.monteiro
		}

		/* geber.costa */
		HTMLCheckbox checkbox = new HTMLCheckbox("acompanhamento", document);

		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);

		OrigemAtendimentoDTO origemAtendimentoDto = new OrigemAtendimentoDTO();

		if (problemaDto.getIdOrigemAtendimento() != null && problemaDto.getIdOrigemAtendimento() > 0) {

			origemAtendimentoDto.setIdOrigem(problemaDto.getIdOrigemAtendimento());

			origemAtendimentoDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemAtendimentoDto);

		}

		atribuirNomeProprietarioECriadorParaRequisicaoDto(problemaDto);

		atualizaInformacoesRelacionamentos(document);

		this.alimentaComboCategoriaProblema(request, document);

		String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
		if ((UNIDADE_AUTOCOMPLETE != null) && (UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))) {
			UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
			UnidadeDTO unidadeDTO = new UnidadeDTO();
			unidadeDTO.setIdUnidade(problemaDto.getIdUnidade());
			unidadeDTO = (UnidadeDTO) unidadeService.restore(unidadeDTO);
			if(unidadeDTO != null)
				problemaDto.setUnidadeDes(unidadeDTO.getNome());
		} else {
			this.restoreComboUnidade(problemaDto, document, request, response);
		}

		this.restoreComboLocalidade(problemaDto, document, request, response);

		this.restoreComboOrigemAtendimento(problemaDto, document, request, response);

		this.preencherComboCausa(document, request, response);

		this.carregaProblemaRelacionado(request, document, problemaAuxDto);

		/**
		 * @author geber.costa verifica se no banco o acompanhamento está setado como 'N' ou 'S'
		 * */

		if (problemaDto.getAcompanhamento() == null || (problemaDto.getAcompanhamento()).equalsIgnoreCase("N") || (problemaDto.getAcompanhamento()).equalsIgnoreCase("")) {

			document.getElementById("acompanhamento").setValue("N");

		} else if (problemaDto.getAcompanhamento().equals("S")) {

			document.getElementById("acompanhamento").setValue("S");

		}

		SolucaoContornoDTO solucaoContorno = this.verificaSolucaoContorno(request, response, document, getProblemaDto());
		SolucaoDefinitivaDTO solucaoDefinitiva = this.verificaSolucaoDefinitiva(request, response, document, getProblemaDto());

		this.getProblemaDto().setIdTarefa(idTarefa);

		this.getProblemaDto().setAcaoFluxo(acaoFluxo);

		this.getProblemaDto().setEscalar(escalar);

		this.getProblemaDto().setAlterarSituacao(alterarSituacao);

		this.getProblemaDto().setFase(fase);

		if (problemaAuxDto.getChamarTelaProblema() != null && problemaAuxDto.getChamarTelaProblema().equalsIgnoreCase("S")) {
			problemaDto.setChamarTelaProblema(problemaAuxDto.getChamarTelaProblema());
			document.executeScript("$('#divBotoes').hide()");
			document.executeScript("$('#divBotaoFecharFrame').show()");
		}

		if (solucaoContorno != null) {
			this.getProblemaDto().setIdSolucaoContorno(solucaoContorno.getIdSolucaoContorno());
			this.getProblemaDto().setTituloSolCon(solucaoContorno.getTitulo());
			this.getProblemaDto().setDescSolCon(solucaoContorno.getDescricao());
		}
		if (solucaoDefinitiva != null) {
			this.getProblemaDto().setIdSolucaoDefinitiva(solucaoDefinitiva.getIdSolucaoDefinitiva());
			this.getProblemaDto().setTituloSolDefinitiva(solucaoDefinitiva.getTitulo());
			this.getProblemaDto().setDescSolDefinitiva(solucaoDefinitiva.getDescricao());
		}

		verificaUltimaAtualizacao(document, request, problemaDto.getIdProblema());
		if (problemaDto.getCausaRaiz() != null && problemaDto.getSolucaoContorno() != null) {
			if (!problemaDto.getCausaRaiz().equalsIgnoreCase("") && !problemaDto.getSolucaoContorno().equalsIgnoreCase("")) {
				document.executeScript("$('#relacionarErrosConhecidos').show()");
			}
		}

		document.setBean(problemaDto);

		HTMLForm form = document.getForm("form");

		form.clear();

		if (problemaDto.getStatus() != null && problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Registrada.getDescricao())) {
			document.executeScript("$('#rotuloCausaRaiz').removeClass('campoObrigatorio')");
			document.executeScript("$('#rotuloSolucaoContorno').removeClass('campoObrigatorio')");
		}

		if (problemaDto.getIdContrato() != null) {
			document.getSelectById("idContrato").setDisabled(true);
		}

		if (problemaDto.getIdCategoriaProblema() != null) {
			document.getSelectById("idCategoriaProblema").setDisabled(true);
		}

		form.setValues(problemaDto);
		String statusSetado = "";
		if (problemaDto.getStatus().equalsIgnoreCase("Registrada") || problemaDto.getStatus().equalsIgnoreCase("Registrado")) {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />"
					+ UtilI18N.internacionaliza(request, "citcorpore.comum.registrada") + "";
		} else if (problemaDto.getStatus().equalsIgnoreCase("Resolvido")) {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />" + UtilI18N.internacionaliza(request, "problema.resolvido") + "";
		} else if (problemaDto.getStatus().equalsIgnoreCase("Suspensa")) {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />"
					+ UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") + "";
		} else if (problemaDto.getStatus().equalsIgnoreCase("Concluída")) {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />"
					+ UtilI18N.internacionaliza(request, "citcorpore.comum.concluida") + "";
		} else if (problemaDto.getStatus().equalsIgnoreCase("Cancelada")) {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />"
					+ UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada") + "";
		} else if (problemaDto.getStatus().equalsIgnoreCase("Registo de Erro Conhecido")) {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />"
					+ UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada") + "";
		} else {
			statusSetado = "<input type='radio' id='status' name='status' value='" + problemaDto.getStatus() + "' checked='checked' />" + problemaDto.getStatus() + "";
		}
		document.getElementById("statusSetado").setInnerHTML(statusSetado);
		document.executeScript("restaurar()");
		document.executeScript("informaNumeroSolicitacao('" + problemaDto.getIdProblema() + "')");

		if (editar == null || editar.equalsIgnoreCase("")) {
			this.getProblemaDto().setEditar("S");
		} else if (editar.equalsIgnoreCase("N")) {
			document.executeScript("$('#divBarraFerramentas').hide()");
			document.executeScript("$('#divBotoes').hide()");
			document.getForm("form").lockForm();
		}
		/*
		 * geber.costa Método listInfoRegExecucaoProblema verifica se o id do problema é válido , caso sim ele traz a lista de ocorrencias de problemas
		 */

		if (this.listInfoRegExecucaoProblema(this.getProblemaDto(), request) != null) {

			document.getElementById("tblOcorrencias").setInnerHTML(listInfoRegExecucaoProblema(problemaDto, request));

		}

		// if(problemaAuxDto.getChamarTelaProblema() == null || problemaAuxDto.getChamarTelaProblema().equalsIgnoreCase("")){
		// Verificando se o prazo para contornar/solucionar o problema expirou.
		// notificarPrazoSolucionarProblemaExpirou(document, request, response,usuarioDto);
		// }

		this.carregaInformacoesComplementares(document, request, problemaDto);

		/**
		 * Adicionado para restaurar anexo
		 *
		 * @author mario.junior
		 * @since 31/10/2013 08:21
		 */
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_PROBLEMA, problemaDto.getIdProblema());
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		request.getSession(true).setAttribute("colUploadRequisicaoProblemaGED", colAnexosUploadDTO);
		request.getSession().setAttribute("colUploadRequisicaoProblemaGED", colAnexosUploadDTO);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuarioDto.getIdEmpregado());

		if (lstGrupos != null) {
			for (GrupoDTO g : lstGrupos) {
				if (this.getProblemaService(request).verificaPermissaoGrupoCancelar(problemaDto.getIdCategoriaProblema(), g.getIdGrupo())) {
					document.executeScript("$('#statusCancelada').show()");
					break;
				}
			}
		}

	}

	private void carregaProblemaRelacionado(HttpServletRequest request, DocumentHTML document, ProblemaDTO problemaDto) throws Exception {
		HTMLTable tblProblmea = document.getTableById("tblProblema");
		tblProblmea.deleteAllRows();
		ProblemaService problemaservice = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		if (problemaDto != null) {
			ProblemaRelacionadoDTO problemaRelacionadoDTO = new ProblemaRelacionadoDTO();
			problemaRelacionadoDTO.setIdProblema(problemaDto.getIdProblema());
			Collection col = problemaservice.findByProblemaRelacionado(problemaRelacionadoDTO);
			if (col != null) {
				tblProblmea.addRowsByCollection(col, new String[] { "idProblema", "titulo", "status", " " }, new String[] { "idProblema" }, "Problema já cadastrado!!",
						new String[] { "exibeIconesProblema" }, null, null);
				document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
			}
		}

	}

	/**
	 * Popula combo categoria hierarquicamente.
	 *
	 * @param document
	 * @throws Exception
	 */
	private void alimentaComboCategoriaProblema(HttpServletRequest request, DocumentHTML document) throws Exception {
		HTMLSelect combo = document.getSelectById("idCategoriaProblema");
		inicializaCombo(request, combo);

		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);

		ArrayList<CategoriaProblemaDTO> listaCategoriaAux = (ArrayList<CategoriaProblemaDTO>) categoriaProblemaService.getAtivos();
		if (listaCategoriaAux != null) {
			for (CategoriaProblemaDTO categoriaProblemaDto : listaCategoriaAux) {
				combo.addOption(categoriaProblemaDto.getIdCategoriaProblema().toString(), StringEscapeUtils.escapeJavaScript(categoriaProblemaDto.getNomeCategoria()));

			}
		}

	}

	private void inicializaCombo(HttpServletRequest request, HTMLSelect componenteCombo) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	/**
	 * Centraliza atualização de informações dos objetos que se relacionam com a mudança.
	 *
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void atualizaInformacoesRelacionamentos(DocumentHTML document) throws ServiceException, Exception {
		// informações dos ics relacionados

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ArrayList<ProblemaItemConfiguracaoDTO> listaICsRelacionados = (ArrayList<ProblemaItemConfiguracaoDTO>) getProblemaItemConfiguracaoService().findByIdProblema(problemaDto.getIdProblema());
		if (listaICsRelacionados != null) {
			for (ProblemaItemConfiguracaoDTO problemaItemConfiguracaoDTO : listaICsRelacionados) {
				ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
				itemConfiguracaoDTO = getItemConfiguracaoService().restoreByIdItemConfiguracao(problemaItemConfiguracaoDTO.getIdItemConfiguracao());
				if (itemConfiguracaoDTO != null) {
					problemaItemConfiguracaoDTO.setNomeItemConfiguracao(itemConfiguracaoDTO.getIdentificacao());
				}

			}
			problemaDto.setItensConfiguracaoRelacionadosSerializado(WebUtil.serializeObjects(listaICsRelacionados, document.getLanguage()));
		}

		ArrayList<SolicitacaoServicoProblemaDTO> listaSolicitacaoServico = (ArrayList<SolicitacaoServicoProblemaDTO>) getSolicitacaoServicoProblemaService().findByIdProblema(
				problemaDto.getIdProblema());

		if (listaSolicitacaoServico != null && listaSolicitacaoServico.size() > 0) {
			for (SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDto : listaSolicitacaoServico) {
				SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
				if (solicitacaoServicoProblemaDto.getIdSolicitacaoServico() != null) {
					solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoProblemaDto.getIdSolicitacaoServico());

				}

				if (solicitacaoServicoDto != null) {
					solicitacaoServicoProblemaDto.setNomeServico(solicitacaoServicoDto.getNomeServico());
				}

			}

			problemaDto.setSolicitacaoServicoSerializado(WebUtil.serializeObjects(listaSolicitacaoServico, document.getLanguage()));
		}

		ArrayList<ProblemaMudancaDTO> listaRequisicoesMudancasRelacionadas = (ArrayList<ProblemaMudancaDTO>) getProblemaMudancaService().findByIdProblema(problemaDto.getIdProblema());

		if (listaRequisicoesMudancasRelacionadas != null) {
			for (ProblemaMudancaDTO problemaMudanca : listaRequisicoesMudancasRelacionadas) {
				RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();
				if (problemaMudanca.getIdRequisicaoMudanca() != null) {
					requisicaoMudancaDto = getRequisicaoMudancaService().restoreAll(problemaMudanca.getIdRequisicaoMudanca());
				}

				if (requisicaoMudancaDto != null) {
					problemaMudanca.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
					problemaMudanca.setTitulo(requisicaoMudancaDto.getTitulo());
					problemaMudanca.setStatus(requisicaoMudancaDto.getStatus());
				}
			}
			problemaDto.setRequisicaoMudancaSerializado(WebUtil.serializeObjects(listaRequisicoesMudancasRelacionadas, document.getLanguage()));
		}

	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").show();

		problemaDto = (ProblemaDTO) document.getBean();

		UsuarioDTO usuarioDto = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);

		ArrayList<ProblemaItemConfiguracaoDTO> ics = (ArrayList<ProblemaItemConfiguracaoDTO>) WebUtil.deserializeCollectionFromRequest(ProblemaItemConfiguracaoDTO.class,
				"itensConfiguracaoRelacionadosSerializado", request);

		problemaDto.setListProblemaItemConfiguracaoDTO(ics);

		ArrayList<SolicitacaoServicoDTO> listIdSolicitacaoServico = (ArrayList<SolicitacaoServicoDTO>) WebUtil.deserializeCollectionFromRequest(SolicitacaoServicoDTO.class,
				"solicitacaoServicoSerializado", request);

		problemaDto.setListIdSolicitacaoServico(listIdSolicitacaoServico);

		ArrayList<ProblemaMudancaDTO> rdm = (ArrayList<ProblemaMudancaDTO>) WebUtil.deserializeCollectionFromRequest(ProblemaMudancaDTO.class, "RequisicaoMudancaSerializado", request);

		ArrayList<ProblemaDTO> problemaRelacionados = (ArrayList<ProblemaDTO>) WebUtil.deserializeCollectionFromRequest(ProblemaDTO.class, "colItensProblema_Serialize", request);
		if (problemaRelacionados != null) {
			problemaDto.setListProblemaRelacionadoDTO(problemaRelacionados);
		}

		problemaDto.setListProblemaMudancaDTO(rdm);

		problemaDto.setUsuarioDto(usuarioDto);

		problemaDto.setEnviaEmailCriacao("S");

		problemaDto.setEnviaEmailFinalizacao("S");

		problemaDto.setEnviaEmailPrazoSolucionarExpirou("S");

		ProblemaDTO problemaAuxDto = new ProblemaDTO();
		if (problemaDto.getIdProblema() != null) {
			problemaAuxDto.setIdProblema(problemaDto.getIdProblema());
			problemaAuxDto = (ProblemaDTO) this.getProblemaService(request).restore(problemaAuxDto);
			if (problemaAuxDto.getIdContrato() != null) {
				problemaDto.setIdContrato(problemaAuxDto.getIdContrato());
			}
			if (problemaAuxDto.getIdCategoriaProblema() != null) {
				problemaDto.setIdCategoriaProblema(problemaAuxDto.getIdCategoriaProblema());
			}
		}

		this.getProblemaService(request).deserializaInformacoesComplementares(problemaDto);

		/**
		 * Adicionado para salvar anexos
		 *
		 * @author mario.junior
		 * @since 31/10/2013 08:21
		 */
		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadRequisicaoProblemaGED");
		problemaDto.setColArquivosUpload(arquivosUpados);

		if (problemaDto.getIdProblema() == null) {

			problemaDto = (ProblemaDTO) getProblemaService(request).create(problemaDto);

			// Registra o email se tiver sido utilizado
			EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator.getInstance().getService(EmailSolicitacaoServicoService.class, null);
			if (problemaDto != null && problemaDto.getMessageId() != null && problemaDto.getMessageId().trim().length() > 0) {
				EmailSolicitacaoServicoDTO emailDto = new EmailSolicitacaoServicoDTO();
				emailDto.setIdSolicitacao(problemaDto.getIdProblema());
				emailDto.setIdMessage(problemaDto.getMessageId());
				emailDto.setOrigem(TipoOrigemLeituraEmail.PROBLEMA.toString());
				emailSolicitacaoServicoService.create(emailDto);
			}

			String comando = "mostraMensagemInsercao('" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>" + UtilI18N.internacionaliza(request, "problema.numero") + " <b><u>"
					+ problemaDto.getIdProblema() + "</u></b> " + UtilI18N.internacionaliza(request, "citcorpore.comum.criado") + ".<br><br>"
					+ UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": " + problemaDto.getPrioridade() + "' ";
			/* comando = comando + "',' "+problemaDto.getIdProblema()+" ')"; */
			comando = comando + ", " + problemaDto.getIdProblema() + " )";

			document.executeScript("verificaRelacionado(" + problemaDto.getIdProblema() + ")");

			document.executeScript(comando);
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			if (problemaDto != null && problemaDto.getIframeSolicitacao().equalsIgnoreCase("true")) {
				document.executeScript("parent.inserirProblemaNalista('" + problemaDto.getIdProblema() + "')");
			}

			return;

		} else {
			/**
			 * @author geber.costa se o problema for criado a aba de revisão não aparecerá, porém a partir do problema criado pode ser usada a aba de revisão
			 * */

			HTMLCheckbox checkbox = new HTMLCheckbox("acompanhamento", document);

			String verificaChechBox = document.getElementById("acompanhamento").getValue();

			if (problemaDto.getAcompanhamento() == null || (problemaDto.getAcompanhamento()).equals("N")) {

				problemaDto.setAcompanhamento("N");

			} else {

				problemaDto.setAcompanhamento("S");

			}

			getProblemaService(request).update(problemaDto);

			try {
				// Registra o email se tiver sido utilizado
				EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator.getInstance().getService(EmailSolicitacaoServicoService.class, null);
				if (problemaDto != null && problemaDto.getMessageId() != null && problemaDto.getMessageId().trim().length() > 0) {
					EmailSolicitacaoServicoDTO emailDto = emailSolicitacaoServicoService.getEmailByIdSolicitacaoAndOrigem(problemaDto.getIdSolicitacaoServico(),
							TipoOrigemLeituraEmail.PROBLEMA.toString());

					if (emailDto != null && emailDto.getIdEmail() != null) {
						emailDto.setIdMessage(problemaDto.getMessageId());
						emailDto.setOrigem(TipoOrigemLeituraEmail.PROBLEMA.toString());
						emailSolicitacaoServicoService.update(emailDto);
					} else {
						emailDto = new EmailSolicitacaoServicoDTO();
						emailDto.setIdSolicitacao(problemaDto.getIdProblema());
						emailDto.setIdMessage(problemaDto.getMessageId());
						emailDto.setOrigem(TipoOrigemLeituraEmail.PROBLEMA.toString());
						emailSolicitacaoServicoService.create(emailDto);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		limparFormularioConsiderandoFCKEditores(document, "form");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.executeScript("fechar('" + problemaDto.getIdProblema() + "');");
		problemaDto.getIdProblema();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		problemaDto = (ProblemaDTO) document.getBean();

		getProblemaService(request).deleteAll(problemaDto);

		if (problemaDto.getIdProblema() != null) {
			limparFormularioConsiderandoFCKEditores(document, "form");
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}
	}

	/**
	 * Atualiza as informações de nome de proprietario e nome de solicitante em uma requisicaoMudancaDto, caso haja.
	 *
	 * @param requisicaoMudancaDto
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void atribuirNomeProprietarioECriadorParaRequisicaoDto(ProblemaDTO problemaDto) throws ServiceException, Exception {
		if (problemaDto == null) {
			return;
		}

		Integer idProprietario = problemaDto.getIdProprietario();
		Integer idSolicitante = problemaDto.getIdSolicitante();

		if (idProprietario != null && idSolicitante != null) {

			// problemaDto.setNomeProprietario(usuarioDto.getNomeUsuario());
			problemaDto.setSolicitante(getEmpregadoService().restoreByIdEmpregado(idSolicitante).getNome());
		}
	}

	public void carregaServicosMulti(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
		if (problemaDto.getIdContrato() == null || problemaDto.getIdContrato().intValue() == 0) {
			problemaDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		if (problemaDto.getIdContrato() == null || problemaDto.getIdContrato().intValue() == 0) {
			return;
		}
		HTMLSelect idServico = document.getSelectById("idServico");
		idServico.removeAllOptions();

		if (problemaDto.getIdContrato() == null) {
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
			empregadoDto.setIdEmpregado(problemaDto.getIdProprietario());
			if (problemaDto.getIdProprietario() != null) {
				empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
				if (empregadoDto != null && empregadoDto.getIdUnidade() != null) {
					idUnidade = empregadoDto.getIdUnidade().intValue();
				}
			}
		}

		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		UnidadesAccServicosService unidadeAccService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
		idServico.removeAllOptions();
		Collection col = servicoService.findByIdTipoDemandaAndIdContrato(3, problemaDto.getIdContrato(), null);

		int cont = 0;
		Integer idServicoCasoApenas1 = null;
		if (col != null) {
			// this.verificaImpactoUrgencia(document, request, response);
			/* if (col.size() > 1) */

			idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

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
								idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
								idServicoCasoApenas1 = servicoDTO.getIdServico();
								cont++;
							}
						} else {
							idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
							idServicoCasoApenas1 = servicoDTO.getIdServico();
							cont++;
						}
					}
				}
			}
			// --- RETITRADO POR EMAURI EM 16/07 - TRATAMENTO DE DELETED --> idServico.addOptions(col, "idServico", "nomeServico", null);
		}
		if (cont == 1) { // Se for apenas um servico encontrado, ja executa o carrega contratos.
			problemaDto.setIdServico(idServicoCasoApenas1);
		}
	}

	/**
	 * form.clear não funciona para os FCKEditores. Esta função garante que todo o formulário será limpado.
	 *
	 * @param document
	 * @param nomeFormulario
	 * @author breno.guimaraes
	 */
	private void limparFormularioConsiderandoFCKEditores(DocumentHTML document, String nomeFormulario) {
		document.executeScript("limpar(document." + nomeFormulario + ")");
	}

	/**
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private ProblemaService getProblemaService(HttpServletRequest request) throws ServiceException, Exception {

		if (problemaService == null) {
			problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, br.com.centralit.citcorpore.util.WebUtil.getUsuarioSistema(request));
		}
		return problemaService;
	}

	/**
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	private SolicitacaoServicoProblemaService getSolicitacaoServicoProblemaService() throws ServiceException, Exception {
		if (solicitacaoServicoProblemaService == null) {
			solicitacaoServicoProblemaService = (SolicitacaoServicoProblemaService) ServiceLocator.getInstance().getService(SolicitacaoServicoProblemaService.class, null);
		}
		return solicitacaoServicoProblemaService;
	}

	private EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		if (usuarioService == null) {
			usuarioService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		}
		return usuarioService;
	}

	private CategoriaProblemaService getCategoriaProblemaService() throws ServiceException, Exception {
		if (categoriaProblemaService == null) {
			categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);
		}
		return categoriaProblemaService;
	}

	private ProblemaItemConfiguracaoService getProblemaItemConfiguracaoService() throws ServiceException, Exception {
		if (problemaItemConfiguracaoService == null) {
			problemaItemConfiguracaoService = (ProblemaItemConfiguracaoService) ServiceLocator.getInstance().getService(ProblemaItemConfiguracaoService.class, null);
		}
		return problemaItemConfiguracaoService;
	}

	private ProblemaMudancaService getProblemaMudancaService() throws ServiceException, Exception {
		if (problemaMudancaService == null) {
			problemaMudancaService = (ProblemaMudancaService) ServiceLocator.getInstance().getService(ProblemaMudancaService.class, null);
		}
		return problemaMudancaService;
	}

	private ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException, Exception {
		if (itemConfiguracaoService == null) {
			itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		}
		return itemConfiguracaoService;
	}

	private RequisicaoMudancaService getRequisicaoMudancaService() throws ServiceException, Exception {
		if (requisicaoMudancaService == null) {
			requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		}
		return requisicaoMudancaService;
	}

	private PastaService getPastaService() throws ServiceException, Exception {
		if (pastaService == null) {
			pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		}
		return pastaService;
	}

	public void preencherComboCausa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
		Collection colCausas = causaIncidenteService.listHierarquia();
		HTMLSelect comboCausa = document.getSelectById("idCausa");

		comboCausa.removeAllOptions();
		comboCausa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colCausas != null) {
			comboCausa.addOptions(colCausas, "idCausaIncidente", "descricaoCausaNivel", null);
		}
	}

	public void preencherComboCategoriaSolucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CategoriaSolucaoService categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
		Collection colCategSolucao = categoriaSolucaoService.listHierarquia();
		HTMLSelect idCategoriaSolucao = document.getSelectById("idCategoriaSolucao");
		idCategoriaSolucao.removeAllOptions();
		idCategoriaSolucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colCategSolucao != null) {
			idCategoriaSolucao.addOptions(colCategSolucao, "idCategoriaSolucao", "descricaoCategoriaNivel", null);
		}
	}

	public void restoreColaboradorSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		if (problemaDto.getIdSolicitante() != null) {
			empregadoDto.setIdEmpregado(problemaDto.getIdSolicitante());
			empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

			problemaDto.setSolicitante(empregadoDto.getNome());
			problemaDto.setNomeContato(empregadoDto.getNome());
			problemaDto.setTelefoneContato(empregadoDto.getTelefone());
			problemaDto.setRamal(empregadoDto.getRamal());
			problemaDto.setEmailContato(empregadoDto.getEmail().trim());
			problemaDto.setObservacao(empregadoDto.getObservacoes());
			problemaDto.setIdUnidade(empregadoDto.getIdUnidade());
			this.preencherComboLocalidade(document, request, response);
		}

		document.executeScript("$('#POPUP_SOLICITANTE').dialog('close')");

		HTMLForm form = document.getForm("form");
		// form.clear();
		form.setValues(problemaDto);
		form.setValueText("ramal", null, problemaDto.getRamal());
		form.setValueText("telefoneContato", null, problemaDto.getTelefoneContato());
		// form.setValueText("emailContato", null, problemaDto.getEmailContato());
		document.executeScript("fecharPopup(\"#POPUP_EMPREGADO\")");

		problemaDto = null;
	}

	public void preencherComboLocalidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();

		LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);

		LocalidadeDTO localidadeDto = new LocalidadeDTO();

		Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;

		Collection<LocalidadeDTO> listaIdlocalidade = null;

		String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

		HTMLSelect comboLocalidade = document.getSelectById("idLocalidade");
		comboLocalidade.removeAllOptions();
		if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
			if (problemaDto.getIdUnidade() != null) {
				listaIdlocalidadePorUnidade = localidadeUnidadeService.listaIdLocalidades(problemaDto.getIdUnidade());
			}
			if (listaIdlocalidadePorUnidade != null) {
				inicializarComboLocalidade(comboLocalidade, request);
				for (LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
					localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
					localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
					comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), localidadeDto.getNomeLocalidade());
				}

			}
		} else {
			listaIdlocalidade = localidadeService.listLocalidade();
			if (listaIdlocalidade != null) {
				inicializarComboLocalidade(comboLocalidade, request);
				for (LocalidadeDTO localidadeDTO : listaIdlocalidade) {
					localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
					localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
					comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), localidadeDto.getNomeLocalidade());
				}
			}

		}

	}

	private void inicializarComboLocalidade(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
		String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

		if (problemaDto.getIdProblema() != null && problemaDto.getIdProblema().intValue() > 0) {
			ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);

			contratoDtoAux.setIdContrato(problemaDto.getIdContrato());

			problemaDto = (ProblemaDTO) problemaService.restore(problemaDto);
		}

		if (problemaDto.getIdContrato() == null || problemaDto.getIdContrato().intValue() == 0) {
			problemaDto.setIdContrato(contratoDtoAux.getIdContrato());
		}

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		HTMLSelect comboUnidade = document.getSelectById("idUnidade");
		inicializarCombo(comboUnidade, request);
		if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
			Integer idContrato = problemaDto.getIdContrato();
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
					}

				}
			}
		} else {
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
					}
				}
			}
		}

		problemaDto = null;

	}

	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void preencherComboGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("idGrupo").removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<GrupoDTO> listGrupo = grupoService.listaGruposAtivos();

		document.getSelectById("idGrupo").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		document.getSelectById("idGrupo").addOptions(listGrupo, "idGrupo", "nome", null);

	}

	public void preencherComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrigemAtendimentoService origemService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		HTMLSelect idOrigem = document.getSelectById("idOrigemAtendimento");
		idOrigem.removeAllOptions();
		document.getSelectById("idOrigemAtendimento").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		Collection col = origemService.recuperaAtivos();
		String origemPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "").trim();
		if (col != null) {
			if (origemPadrao != null && !origemPadrao.equals("")) {
				idOrigem.addOptions(col, "idOrigem", "descricao", origemPadrao);
			} else {
				idOrigem.addOptions(col, "idOrigem", "descricao", null);
			}
		}
	}

	/**
	 *
	 * @param problemaDto
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa retorna uma lista com os registros de execução de um problema
	 */

	public String listInfoRegExecucaoProblema(ProblemaDTO problemaDto, HttpServletRequest request) throws ServiceException, Exception {

		JustificativaProblemaService justificativaProblemaService = (JustificativaProblemaService) ServiceLocator.getInstance().getService(JustificativaProblemaService.class, null);

		OcorrenciaProblemaService ocorrenciaProblemaService = (OcorrenciaProblemaService) ServiceLocator.getInstance().getService(OcorrenciaProblemaService.class, null);

		Collection<OcorrenciaProblemaDTO> col = ocorrenciaProblemaService.findByIdProblema(problemaDto.getIdProblema());

		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
		OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();

		String strBuffer = "<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>";
		strBuffer += "<tr>";
		strBuffer += "<th style='width:20%'>";
		strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.datahora");
		strBuffer += "</th>";
		strBuffer += "<th>";
		strBuffer += UtilI18N.internacionaliza(request, "solicitacaoServico.informacaoexecucao");
		strBuffer += "</th>";
		strBuffer += "</tr>";

		if (col != null) {

			for (OcorrenciaProblemaDTO ocorrenciaProblemaDto : col) {

				if (ocorrenciaProblemaDto.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaProblemaDto.getOcorrencia());
					ocorrenciaProblemaDto.setOcorrencia(source.getTextExtractor().toString());
				}

				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getOcorrencia());
				String descricao = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getDescricao());
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaProblemaDto.getInformacoesContato());
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br>");
				descricao = descricao.replaceAll("\n", "<br>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br>");
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", ""));
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", ""));
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", ""));
				strBuffer += "<tr>";
				strBuffer += "<td >";
				strBuffer += "<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ocorrenciaProblemaDto.getDataregistro(), WebUtil.getLanguage(request)) + " - "
						+ ocorrenciaProblemaDto.getHoraregistro();
				strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + ocorrenciaProblemaDto.getRegistradopor() + "</b>";
				strBuffer += "</td>";
				strBuffer += "<td style='word-wrap: break-word;overflow:hidden;'>";
				strBuffer += "<b>" + ocorrenciaProblemaDto.getDescricao() + "<br><br></b>";
				strBuffer += "<b>" + ocorrencia + "<br><br></b>";

				if (ocorrenciaProblemaDto.getCategoria() != null) {
					if (ocorrenciaProblemaDto.getCategoria().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
							|| ocorrenciaProblemaDto.getCategoria().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())) {
						JustificativaProblemaDTO justificativaSolicitacaoDTO = new JustificativaProblemaDTO();
						if (ocorrenciaProblemaDto.getIdJustificativa() != null) {
							justificativaSolicitacaoDTO.setIdJustificativaProblema(ocorrenciaProblemaDto.getIdJustificativa());
							justificativaSolicitacaoDTO = (JustificativaProblemaDTO) justificativaProblemaService.restore(justificativaSolicitacaoDTO);
							if (justificativaSolicitacaoDTO != null) {

								strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" + justificativaSolicitacaoDTO.getDescricaoProblema() + "<br><br></b>";
							}
						}
						if (!UtilStrings.nullToVazio(ocorrenciaProblemaDto.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
							strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaProblemaDto.getComplementoJustificativa()) + "<br><br></b>";
						}
					}

				}

				if (ocorrenciaProblemaDto.getOcorrencia() != null) {
					if (categoriaOcorrenciaDTO.getNome() != null && !categoriaOcorrenciaDTO.getNome().equals("")) {
						if (categoriaOcorrenciaDTO.getNome().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
								|| categoriaOcorrenciaDTO.getNome().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())) {
							JustificativaProblemaDTO justificativaSolicitacaoDTO = new JustificativaProblemaDTO();
							if (ocorrenciaProblemaDto.getIdJustificativa() != null) {

								justificativaSolicitacaoDTO.setIdJustificativaProblema(ocorrenciaProblemaDto.getIdJustificativa());
								justificativaSolicitacaoDTO = (JustificativaProblemaDTO) justificativaProblemaService.restore(justificativaSolicitacaoDTO);
								if (justificativaSolicitacaoDTO != null) {
									strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" + justificativaSolicitacaoDTO.getDescricaoProblema() + "<br><br></b>";
								}
							}
							if (!UtilStrings.nullToVazio(ocorrenciaProblemaDto.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
								strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaProblemaDto.getComplementoJustificativa()) + "<br><br></b>";
							}
						}
					}
				}

				strBuffer += "</td>";
				strBuffer += "</tr>";
			}
		}
		strBuffer += "</table>";
		categoriaOcorrenciaDTO = null;
		origemOcorrenciaDTO = null;

		return strBuffer;
	}

	public void restoreComboUnidade(ProblemaDTO problemaDto, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		if (problemaDto.getIdProblema() != null && problemaDto.getIdProblema().intValue() > 0) {
			String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

			if (request.getParameter("chamarTelaProblema") == null || request.getParameter("chamarTelaProblema").equalsIgnoreCase("")) {
				problemaDto = (ProblemaDTO) problemaService.restore(problemaDto);
			}

			if (problemaDto.getIdContrato() == null || problemaDto.getIdContrato().intValue() == 0) {
				problemaDto.setIdContrato(contratoDtoAux.getIdContrato());
			}

			String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
			if ((UNIDADE_AUTOCOMPLETE != null) && (!UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))) {
				if (problemaDto.getIdUnidade() != null) {
					HTMLSelect comboUnidade = document.getSelectById("idUnidade");
					inicializarCombo(comboUnidade, request);
					if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
						Integer idContrato = problemaDto.getIdContrato();
						ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
						if (unidades != null) {
							for (UnidadeDTO unidade : unidades) {
								if (unidade.getDataFim() == null) {
									comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
								}

							}
						}
					} else {
						ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
						if (unidades != null) {
							for (UnidadeDTO unidade : unidades) {
								if (unidade.getDataFim() == null) {
									comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
								}
							}
						}
					}
				}
			}
		}
	}

	public void restoreComboLocalidade(ProblemaDTO ProblemaDto, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (ProblemaDto.getIdProblema() != null && ProblemaDto.getIdProblema().intValue() > 0) {

			String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

			if (ProblemaDto.getIdContrato() == null || ProblemaDto.getIdContrato().intValue() == 0) {
				ProblemaDto.setIdContrato(contratoDtoAux.getIdContrato());
			}

			if (ProblemaDto.getIdLocalidade() != null) {

				LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);
				LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
				LocalidadeDTO localidadeDto = new LocalidadeDTO();
				Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;
				Collection<LocalidadeDTO> listaIdlocalidade = null;

				HTMLSelect comboLocalidade = document.getSelectById("idLocalidade");
				comboLocalidade.removeAllOptions();
				if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
					if (ProblemaDto.getIdUnidade() != null) {
						listaIdlocalidadePorUnidade = localidadeUnidadeService.listaIdLocalidades(ProblemaDto.getIdUnidade());
					}
					if (listaIdlocalidadePorUnidade != null) {
						inicializarComboLocalidade(comboLocalidade, request);
						for (LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
							localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
							localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
							comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), localidadeDto.getNomeLocalidade());
						}

					}
				} else {
					listaIdlocalidade = localidadeService.listLocalidade();
					if (listaIdlocalidade != null) {
						inicializarComboLocalidade(comboLocalidade, request);
						for (LocalidadeDTO localidadeDTO : listaIdlocalidade) {
							localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
							localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
							comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), localidadeDto.getNomeLocalidade());
						}
					}
				}
			}
		}
	}

	public void preencherComboOrigemAtendimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect comboOrigem = document.getSelectById("idOrigemAtendimento");

		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		Collection<OrigemAtendimentoDTO> listOrigem = origemAtendimentoService.list();

		comboOrigem.removeAllOptions();
		comboOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (listOrigem != null) {
			for (OrigemAtendimentoDTO origemDTO : listOrigem) {
				if (origemDTO.getIdOrigem() != null || origemDTO.getIdOrigem() > 0) {
					comboOrigem.addOption(origemDTO.getIdOrigem().toString(), StringEscapeUtils.escapeJavaScript(origemDTO.getDescricao()));
				}
			}
		}

	}

	public void restoreComboOrigemAtendimento(ProblemaDTO problemaDto, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (problemaDto.getIdProblema() != null && problemaDto.getIdProblema().intValue() > 0) {
			if (problemaDto.getIdOrigemAtendimento() != null && problemaDto.getIdOrigemAtendimento().intValue() > 0) {
				this.preencherComboOrigem(document, request, response);
			}
		}
	}

	/**
	 * Restaura Problemas na Grid.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void atualizaGridProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDTO = (ProblemaDTO) document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ConhecimentoProblemaService conhecimentoProblemaService = (ConhecimentoProblemaService) ServiceLocator.getInstance().getService(ConhecimentoProblemaService.class, null);

		ConhecimentoProblemaDTO erroConhecido = null;

		if (problemaDTO.getIdProblema() != null) {
			erroConhecido = conhecimentoProblemaService.getErroConhecidoByProblema(problemaDTO.getIdProblema());
			problemaDTO = (ProblemaDTO) problemaService.restore(problemaDTO);
		}

		HTMLTable tblErrosConhecidos = document.getTableById("tblErrosConhecidos");
		tblErrosConhecidos.deleteAllRows();

		if (erroConhecido != null) {
			tblErrosConhecidos.addRow(erroConhecido, new String[] { "", "titulo", "status", "" }, null, null, new String[] { "exibeIconesEditarBaseConhecimento" }, null, null);
			document.executeScript("$('#divBaseConhecimento').hide()");
		} else {
			document.executeScript("$('#divBaseConhecimento').show()");
		}

	}

	public void atualizaGridProblemaRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO bean = (ProblemaDTO) document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ProblemaDTO problemaDTO = new ProblemaDTO();

		problemaDTO.setIdProblema(bean.getIdProblemaRelacionado());

		problemaDTO = (ProblemaDTO) problemaService.restore(problemaDTO);
		if (problemaDTO == null) {
			return;
		}
		HTMLTable tblProblema = document.getTableById("tblProblema");

		if (problemaDTO.getSequenciaProblema() == null) {
			tblProblema.addRow(problemaDTO, new String[] { "idProblema", "titulo", "status", " " }, new String[] { "idProblema" }, "Problema já cadastrado!!", new String[] { "exibeIconesProblema" },
					null, null);
		} else {
			tblProblema.updateRow(problemaDTO, new String[] { "idProblema", "titulo", "status", " " }, new String[] { "idProblema" }, "Problema já cadastrado!!",
					new String[] { "exibeIconesProblema" }, "", null, problemaDTO.getSequenciaProblema());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
		document.executeScript("fecharModalProblema();");

		bean = null;
	}

	public void limparTabelas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLTable tblICs = document.getTableById("tblICs");
		tblICs.deleteAllRows();
		HTMLTable tblRDM = document.getTableById("tblRDM");
		tblRDM.deleteAllRows();
		HTMLTable tblSolicitacaoServico = document.getTableById("tblSolicitacaoServico");
		tblSolicitacaoServico.deleteAllRows();
	}

	/**
	 * preencher comobo de calendario
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboCalendario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
		HTMLSelect comboCalendario = document.getSelectById("idCalendario");
		Collection<CalendarioDTO> calendarioDTO = calendarioService.list();

		comboCalendario.removeAllOptions();

		if (calendarioDTO != null) {
			for (CalendarioDTO calendario : calendarioDTO) {

				comboCalendario.addOption(calendario.getIdCalendario().toString(), StringEscapeUtils.escapeJavaScript(calendario.getDescricao()));
			}
		}

	}

	/**
	 * Método responsável pela validação do avanço do fluxo. O fluxo só será válido e portanto só poderá avançar caso o problema em questão esteja associado a uma base de conhecimento.
	 *
	 * @autor thiago.monterio
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void validacaoAvancaFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProblemaDTO problemaDTO = (ProblemaDTO) document.getBean();

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);

		if (problemaDTO != null && problemaService != null) {
			if (problemaDTO.getStatus() != null && problemaDTO.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.RegistroErroConhecido.getDescricao())) {
				boolean avancarFluxo = problemaService.validacaoAvancaFluxo(problemaDTO, null);

				if (!avancarFluxo) {

					document.alert(UtilI18N.internacionaliza(request, "problema.mensagem.necessarioExistirErroConhecido"));

				} else {

					document.executeScript("gravar('form')");

				}
			} else {
				document.executeScript("gravar('form')");
			}

		}
	}

	/**
	 * Método responsável por enviar mensagens de notificação no e-mail do responsável (proprietário) e de cada um dos usuários que fazem parte do grupo executor na resolução de um problema.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @autor thiago.monteiro / thays.araujo
	 */
	public void notificarPrazoSolucionarProblemaExpirou(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, UsuarioDTO usuario) throws Exception {

		EmpregadoDTO empregadoDto = new EmpregadoDTO();

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);

		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		String enviarNotificacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NOTIFICAR_RESPONSAVEL_GRUPO_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO, "S");

		if (enviarNotificacao.equalsIgnoreCase("S")) {

			Collection<ProblemaDTO> listaProblemasComPrazoLimiteContornoExpirado = problemaService.listaProblemasComPrazoLimiteContornoExpirado(usuario);

			if (usuario != null) {
				if (usuario.getIdEmpregado() != null) {
					empregadoDto.setIdEmpregado(usuario.getIdEmpregado());
					empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
				}
			}

			if (problemaService != null && usuarioService != null) {

				if (listaProblemasComPrazoLimiteContornoExpirado != null) {

					String ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO = ParametroUtil.getValorParametroCitSmartHashMap(
							ParametroSistema.ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO, "38");

					Set emailsIntegrantesGrupoExecutor = new HashSet();

					Collection<String> emailsPorGrupo = null;

					for (ProblemaDTO problemaDto : listaProblemasComPrazoLimiteContornoExpirado) {

						if (problemaDto.getIdGrupo() != null) {

							emailsPorGrupo = grupoService.listarEmailsPorGrupo(problemaDto.getIdGrupo());

							if (emailsPorGrupo != null && !emailsPorGrupo.isEmpty()) {

								for (String email : emailsPorGrupo) {
									emailsIntegrantesGrupoExecutor.add(email);
								}

							}

						}
					}

					MensagemEmail mensagemEmail = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO.trim()), new IDto[] { problemaDto });

					mensagemEmail.envia(empregadoDto.getEmail(), StringUtils.remove(StringUtils.remove(emailsIntegrantesGrupoExecutor.toString(), "["), "]"),
							ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, "10"));
				}
			}
		}
	}

	/*	*//**
	 * Método responsável por enviar mensagens de notificação no e-mail do responsável (proprietário) e de cada um dos usuários que fazem parte do grupo de interessados na resolução de um
	 * problema.
	 *
	 * @autor thiago.monteiro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*
	 *
	 * // Chamar esse método no método load. public void notificarPrazoSolucionarProblemaExpirou(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	 *
	 * // Obtendo os dados do formulário. ProblemaDTO problemaDTO = (ProblemaDTO) document.getBean();
	 *
	 * // Verificando se o problema já foi cadastrado. if (problemaDTO != null && problemaDTO.getIdProblema() != null) {
	 *
	 * ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
	 *
	 * UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
	 *
	 * if (problemaService != null && usuarioService != null) {
	 *
	 * // Verificando se o usuário optou por ser notificado por e-mail quando o prazo para solucionar/contornar o problema houver expirado. if (problemaDTO.getEnviaEmailPrazoSolucionarExpirou() !=
	 * null && problemaDTO.getEnviaEmailPrazoSolucionarExpirou().equalsIgnoreCase("S") ) {
	 *
	 * // Verificando se a data hora limite existe. if (problemaDTO.getDataHoraLimiteStr() != null && !problemaDTO.getDataHoraLimiteStr().isEmpty() ) {
	 *
	 * // Determinando a hora limite. problemaDTO.setDataHoraLimite(UtilDatas.strToTimestamp(String.format("%s:%s", problemaDTO.getDataHoraLimiteStr(), "00") ) );
	 *
	 * // Calculando a diferença entre as datas limite para contorno do problema e a data atual. int diferenca = UtilDatas.dataDiff(UtilDatas.getDataHoraAtual(), problemaDTO.getDataHoraLimite() );
	 *
	 * // Sendo o resultado negativo o prazo para contornar o problema expirou. if (diferenca < 0) {
	 *
	 * // Notificar o proprietário (colaborador que capturou o problema) e o grupo de interessados (grupo executor) por e-mail.
	 *
	 * // Criar um modelo de e-mail para essa situação. String ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO = ParametroUtil.getValue(ParametroSistema.ID_MODELO_EMAIL_ALTERACAO_SENHA,
	 * "38");
	 *
	 * problemaDTO.setDescricao(problemaDTO.getDescricao().substring(0, problemaDTO.getDescricao().lastIndexOf(".") ) );
	 *
	 * problemaDTO.setUsuarioDto(new UsuarioDTO() );
	 *
	 * problemaDTO.getUsuarioDto().setIdUsuario(problemaDTO.getIdProprietario() );
	 *
	 * problemaDTO.setUsuarioDto( (UsuarioDTO) usuarioService.restore(problemaDTO.getUsuarioDto() ) );
	 *
	 * problemaDTO.setNomeProprietario(problemaDTO.getUsuarioDto().getNomeUsuario() );
	 *
	 * // Envio de e-mail para o grupo de interessados (grupo executor).
	 *
	 * String emailsIntegrantesGrupoExecutor = "";
	 *
	 * Collection<String> emailsPorGrupo = null;
	 *
	 * if (problemaDTO.getIdGrupo() != null && problemaDTO.getIdGrupo() > 0) {
	 *
	 * GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
	 *
	 * // OBS: Deve-se cadastrar os e-mails dos usuários na página de cadastro de grupo. emailsPorGrupo = grupoService.listarEmailsPorGrupo(problemaDTO.getIdGrupo() );
	 *
	 * // Iterando através dos e-mails dos integrantes do grupo. if (emailsPorGrupo != null && !emailsPorGrupo.isEmpty() ) {
	 *
	 * for (String email : emailsPorGrupo) {
	 *
	 * emailsIntegrantesGrupoExecutor += String.format("%s,", email); }
	 *
	 * // Retirando a última vírgula. emailsIntegrantesGrupoExecutor = emailsIntegrantesGrupoExecutor.substring(0, emailsIntegrantesGrupoExecutor.lastIndexOf(",") ); } }
	 *
	 * // Solicitante é um empregado ou colaborador. // Proprietário é a pessoa que capturou o problema para solucioná-lo.
	 *
	 * MensagemEmail mensagemEmail = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO), new IDto[] {problemaDTO} );
	 *
	 * // O destinatário é o responsável ou proprietário que nem sempre fará parte do grupo executor. mensagemEmail.envia(problemaDTO.getEmailContato(), emailsIntegrantesGrupoExecutor,
	 * ParametroUtil.getValue(ParametroSistema.RemetenteNotificacoesSolicitacao, "10") );
	 *
	 * } } } } } }
	 */

	public void gravarSolContorno(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
		SolucaoContornoDTO solucaoContornoDto = new SolucaoContornoDTO();
		SolucaoContornoService solucaoContornoService = (SolucaoContornoService) ServiceLocator.getInstance().getService(SolucaoContornoService.class, null);
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);

		if (problemaDto.getIdProblema() != null) {
			solucaoContornoDto.setIdProblema(problemaDto.getIdProblema());

			if (problemaDto.getIdSolucaoContorno() != null && problemaDto.getIdSolucaoContorno().intValue() > 0) {

				solucaoContornoDto.setIdSolucaoContorno(problemaDto.getIdSolucaoContorno());
				solucaoContornoDto = (SolucaoContornoDTO) solucaoContornoService.restore(solucaoContornoDto);

				if (problemaDto.getSolucaoContorno() != null && !problemaDto.getSolucaoContorno().equals("")) {
					solucaoContornoDto.setDescricao(problemaDto.getSolucaoContorno());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.descricaoObrigatorio"));
					return;
				}

				if (problemaDto.getTituloSolucaoContorno() != null && !problemaDto.getTituloSolucaoContorno().equals("")) {
					solucaoContornoDto.setTitulo(problemaDto.getTituloSolucaoContorno());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tituloObrigatorio"));
					return;
				}

				solucaoContornoService.update(solucaoContornoDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));

			} else {

				if (problemaDto.getSolucaoContorno() != null && !problemaDto.getSolucaoContorno().equals("")) {
					solucaoContornoDto.setDescricao(problemaDto.getSolucaoContorno());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.descricaoObrigatorio"));
					return;
				}

				if (problemaDto.getTituloSolucaoContorno() != null && !problemaDto.getTituloSolucaoContorno().equals("")) {
					solucaoContornoDto.setTitulo(problemaDto.getTituloSolucaoContorno());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.tituloObrigatorio"));
					return;
				}
				solucaoContornoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
				solucaoContornoDto = (SolucaoContornoDTO) solucaoContornoService.create(solucaoContornoDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));

			}
			problemaService.updateNotNull(problemaDto);
		} else {
			return;
		}
		this.montarTabela(solucaoContornoDto, request, response, document);
		document.executeScript("fecharSolContorno()");

	}

	public SolucaoContornoDTO verificaSolucaoContorno(HttpServletRequest request, HttpServletResponse response, DocumentHTML document, ProblemaDTO problema) throws ServiceException, Exception {
		SolucaoContornoDTO solucaoContorno = new SolucaoContornoDTO();
		SolucaoContornoService solucaoContornoService = (SolucaoContornoService) ServiceLocator.getInstance().getService(SolucaoContornoService.class, null);

		solucaoContorno.setIdProblema(problema.getIdProblema());
		solucaoContorno = solucaoContornoService.findByIdProblema(solucaoContorno);

		if (solucaoContorno != null) {
			this.montarTabela(solucaoContorno, request, response, document);
		}

		return solucaoContorno;
	}

	public void montarTabela(SolucaoContornoDTO solucaoContornoDto, HttpServletRequest request, HttpServletResponse response, DocumentHTML document) throws Exception {
		HTMLTable tblSolContorno = document.getTableById("tblSolContorno");
		tblSolContorno.deleteAllRows();
		if (solucaoContornoDto.getIdSolucaoContorno() != null) {
			solucaoContornoDto.setDataHoraCriacaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solucaoContornoDto.getDataHoraCriacao(), WebUtil.getLanguage(request)));
			tblSolContorno.addRow(solucaoContornoDto, new String[] { "titulo", "dataHoraCriacaoStr", "descricao" }, null, null, null, null, null);
			// document.executeScript("$('#divTblSolContorno').show();");
		}
	}

	public void gravarSolDefinitiva(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
		SolucaoDefinitivaDTO solucaoDefinitivaDto = new SolucaoDefinitivaDTO();
		SolucaoDefinitivaService solucaoDefinitivaService = (SolucaoDefinitivaService) ServiceLocator.getInstance().getService(SolucaoDefinitivaService.class, null);

		if (problemaDto.getIdProblema() != null) {
			solucaoDefinitivaDto.setIdProblema(problemaDto.getIdProblema());

			if (problemaDto.getIdSolucaoDefinitiva() != null && problemaDto.getIdSolucaoDefinitiva().intValue() > 0) {

				solucaoDefinitivaDto.setIdSolucaoDefinitiva(problemaDto.getIdSolucaoDefinitiva());
				solucaoDefinitivaDto = (SolucaoDefinitivaDTO) solucaoDefinitivaService.restore(solucaoDefinitivaDto);

				if (problemaDto.getSolucaoDefinitiva() != null && !problemaDto.getSolucaoDefinitiva().equals("")) {
					solucaoDefinitivaDto.setDescricao(problemaDto.getSolucaoDefinitiva());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.descricaoObrigatorio"));
					return;
				}

				if (problemaDto.getTituloSolucaoDefinitiva() != null && !problemaDto.getTituloSolucaoDefinitiva().equals("")) {
					solucaoDefinitivaDto.setTitulo(problemaDto.getTituloSolucaoDefinitiva());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.tituloObrigatorio"));
					return;
				}

				solucaoDefinitivaService.update(solucaoDefinitivaDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));

			} else {

				if (problemaDto.getSolucaoDefinitiva() != null && !problemaDto.getSolucaoDefinitiva().equals("")) {
					solucaoDefinitivaDto.setDescricao(problemaDto.getSolucaoDefinitiva());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.descricaoObrigatorio"));
					return;
				}

				if (problemaDto.getTituloSolucaoDefinitiva() != null && !problemaDto.getTituloSolucaoDefinitiva().equals("")) {
					solucaoDefinitivaDto.setTitulo(problemaDto.getTituloSolucaoDefinitiva());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.tituloObrigatorio"));
					return;
				}
				solucaoDefinitivaDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
				solucaoDefinitivaDto = (SolucaoDefinitivaDTO) solucaoDefinitivaService.create(solucaoDefinitivaDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));

			}
		} else {
			return;
		}

		this.montarTabela(solucaoDefinitivaDto, request, response, document);
		document.executeScript("fecharSolDefinitiva()");

	}

	public SolucaoDefinitivaDTO verificaSolucaoDefinitiva(HttpServletRequest request, HttpServletResponse response, DocumentHTML document, ProblemaDTO problema) throws ServiceException, Exception {
		SolucaoDefinitivaDTO solucaoDefinitiva = new SolucaoDefinitivaDTO();
		SolucaoDefinitivaService solucaoDefinitivaService = (SolucaoDefinitivaService) ServiceLocator.getInstance().getService(SolucaoDefinitivaService.class, null);

		solucaoDefinitiva.setIdProblema(problema.getIdProblema());
		solucaoDefinitiva = solucaoDefinitivaService.findByIdProblema(solucaoDefinitiva);

		if (solucaoDefinitiva != null) {
			this.montarTabela(solucaoDefinitiva, request, response, document);
		}

		return solucaoDefinitiva;
	}

	public void montarTabela(SolucaoDefinitivaDTO solucaoDefinitivaDto, HttpServletRequest request, HttpServletResponse response, DocumentHTML document) throws Exception {
		HTMLTable tblSolDefinitiva = document.getTableById("tblSolDefinitiva");
		tblSolDefinitiva.deleteAllRows();
		if (solucaoDefinitivaDto.getIdSolucaoDefinitiva() != null) {
			solucaoDefinitivaDto.setDataHoraCriacaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solucaoDefinitivaDto.getDataHoraCriacao(), WebUtil.getLanguage(request)));
			tblSolDefinitiva.addRow(solucaoDefinitivaDto, new String[] { "titulo", "dataHoraCriacaoStr", "descricao" }, null, null, null, null, null);
		}
	}

	/**
	 * @author geber.costa
	 * @param document
	 * @param idProblema
	 * @throws Exception
	 *             Traz uma lista para verificação de ultima data e hora da ocorrência, esses dados serão retornados na página do cliente.
	 */
	public void verificaUltimaAtualizacao(DocumentHTML document, HttpServletRequest request, int idProblema) throws Exception {

		OcorrenciaProblemaDAO ocorrenciaDao = new OcorrenciaProblemaDAO();
		List<OcorrenciaProblemaDTO> lista = (List<OcorrenciaProblemaDTO>) ocorrenciaDao.listByUltimaDataEHora(idProblema);

		Date data = null;
		String hora = "";
		String registradoPor = "";

		if (lista != null) {

			for (OcorrenciaProblemaDTO l : lista) {

				/**
				 *
				 * valida para pegar a ultima data de registro e a ultima hora, porém ele irá pegar a partiro do momento que a ocorrencia não for nula e vazia ou se ela tiver alguma das descrições
				 * setadas
				 */

				if (l.getOcorrencia() != null && !l.getOcorrencia().equalsIgnoreCase("") || l.getDescricao().equalsIgnoreCase("Encerramento da Solicitação")
						|| l.getDescricao().equalsIgnoreCase("Suspensão da Solicitação") || l.getDescricao().equalsIgnoreCase("Reativação da Solicitação")
						|| l.getDescricao().equalsIgnoreCase("Agendamento da Atividade") || l.getDescricao().equalsIgnoreCase("Registro de Execução")) {

					if (l.getDataregistro() != null) {
						data = l.getDataregistro();
					}

					if (!l.getHoraregistro().equalsIgnoreCase("")) {
						hora = l.getHoraregistro();
					}
					registradoPor = l.getRegistradopor();

				}

			}

			//
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(data);

			// String registradoPor = lista.get(lista.size()-1).getRegistradopor();

			document.getElementById("dataHoraUltimaAtualizacao").setInnerHTML(
					"<br/>" + UtilI18N.internacionaliza(request, "carteiraTrabalho.data") + ":<b>&nbsp;" + date + "&nbsp;&nbsp;&nbsp;</b>"
							+ UtilI18N.internacionaliza(request, "carteiraTrabalho.hora") + ":<b>&nbsp;" + hora + "&nbsp;&nbsp;&nbsp;</b>"
							+ UtilI18N.internacionaliza(request, "ocorrenciaProblema.registradopor") + ":<b>&nbsp;" + registradoPor + "&nbsp;&nbsp;</b>");
		}
	}

	public Integer obterIdTarefa(ProblemaDTO problema, HttpServletRequest request) throws ServiceException, Exception {
		int res = 0;
		ExecucaoProblemaService execucaoProblemaService = (ExecucaoProblemaService) ServiceLocator.getInstance().getService(ExecucaoProblemaService.class, null);
		usuario = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		TarefaFluxoDTO tarefaFluxo = execucaoProblemaService.recuperaTarefa(usuario.getLogin(), problema);
		if (tarefaFluxo != null) {
			res = tarefaFluxo.getIdItemTrabalho();
		}
		return res;
	}

	public void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
		carregaInformacoesComplementares(document, request, problemaDto);
		problemaDto = null;
	}

	private void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, ProblemaDTO problemaDto) throws Exception {

		TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
				br.com.centralit.citcorpore.util.WebUtil.getUsuarioSistema(request));

		// document.executeScript("exibirInformacoesAprovacao(\"" + getProblemaService(request).getUrlInformacoesComplementares(problemaDto) + "\");");
		document.executeScript("exibirInformacoesComplementares('" + getProblemaService(request).getUrlInformacoesComplementares(problemaDto) + "');");
		// document.executeScript("exibirInformacoesComplementares(localhost:8080/citsmart/pages/\"" + getProblemaService(request).getUrlInformacoesComplementares(problemaDto) + "\");");
		TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateProblema(problemaDto);

		if (templateDto != null) {
			/*
			 * if (templateDto.getScriptAposRecuperacao() != null) document.executeScript(templateDto.getScriptAposRecuperacao()); if (!templateDto.getHabilitaDirecionamento().equalsIgnoreCase("S"))
			 * document.executeScript("document.getElementById('divGrupoAtual').style.display = 'none';"); if (!templateDto.getHabilitaSituacao().equalsIgnoreCase("S"))
			 * document.executeScript("document.getElementById('divSituacao').style.display = 'none';"); if (!templateDto.getHabilitaSolucao().equalsIgnoreCase("S"))
			 * document.executeScript("document.getElementById('solucao').style.display = 'none';"); if (!templateDto.getHabilitaUrgenciaImpacto().equalsIgnoreCase("S")) {
			 * document.executeScript("document.getElementById('divUrgencia').style.display = 'none';"); document.executeScript("document.getElementById('divImpacto').style.display = 'none';"); } if
			 * (!templateDto.getHabilitaNotificacaoEmail().equalsIgnoreCase("S")) document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'none';"); if
			 * (!templateDto.getHabilitaProblema().equalsIgnoreCase("S")) document.executeScript("document.getElementById('divProblema').style.display = 'none';"); if
			 * (!templateDto.getHabilitaMudanca().equalsIgnoreCase("S")) document.executeScript("document.getElementById('divMudanca').style.display = 'none';"); if
			 * (!templateDto.getHabilitaItemConfiguracao().equalsIgnoreCase("S")) document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'none';"); if
			 * (!templateDto.getHabilitaSolicitacaoRelacionada().equalsIgnoreCase("S")) document.executeScript("document.getElementById('divSolicitacaoRelacionada').style.display = 'none';"); if
			 * (!templateDto.getHabilitaGravarEContinuar().equalsIgnoreCase("S") && problemaDto.getIdTarefa() != null)
			 * document.executeScript("document.getElementById('btnGravarEContinuar').style.display = 'none';");
			 */
			if (templateDto.getAlturaDiv() != null) {
				document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '" + templateDto.getAlturaDiv().intValue() + "px';");

			}

		}
		document.executeScript("escondeJanelaAguarde()");
	}

	public void restoreImpactoUrgenciaPorCategoriaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();

		CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();

		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);

		if (problemaDto.getIdCategoriaProblema() != null) {
			categoriaProblemaDto.setIdCategoriaProblema(problemaDto.getIdCategoriaProblema());
			categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaService.restore(categoriaProblemaDto);
		}

		if (categoriaProblemaDto != null) {
			problemaDto.setImpacto(categoriaProblemaDto.getImpacto());
			problemaDto.setUrgencia(categoriaProblemaDto.getUrgencia());

		}

		HTMLForm form = document.getForm("form");
		form.setValues(problemaDto);
		document.executeScript("atualizaPrioridade()");
	}

	public void verificarItensRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		ProblemaDTO RequisicaoMudancaDTO = (ProblemaDTO) document.getBean();

		ArrayList<SolicitacaoServicoDTO> listIdSolicitacaoServico = (ArrayList<SolicitacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SolicitacaoServicoDTO.class,
				"solicitacaoServicoSerializado", request);
		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(RequisicaoMudancaItemConfiguracaoDTO.class, "itensConfiguracaoRelacionadosSerializado", request);

		boolean existeItensRelaiconados = false;

		if (listIdSolicitacaoServico != null && listIdSolicitacaoServico.size() > 0) {
			existeItensRelaiconados = true;
		} else if (listRequisicaoMudancaItemConfiguracaoDTO != null && listRequisicaoMudancaItemConfiguracaoDTO.size() > 0) {
			existeItensRelaiconados = true;
		}

		if (existeItensRelaiconados) {
			document.executeScript("verificarItensRelacionados(false)");
		} else {
			this.validacaoAvancaFluxo(document, request, response);
		}
	}

	public void verificarParametroAnexos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if (DISKFILEUPLOAD_REPOSITORYPATH == null) {
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}
		if (DISKFILEUPLOAD_REPOSITORYPATH.equals("")) {
			throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
		}
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if (!pasta.exists()) {
			throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.pastaIndicadaNaoExiste"));
		}
	}

	public void restaurarItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		problemaDto = (ProblemaDTO) document.getBean();
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
		try {

			ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			problemaDto.getHiddenIdItemConfiguracao();
			if (problemaDto != null && problemaDto.getHiddenIdItemConfiguracao() != null && Integer.SIZE > 0) {
				itemConfiguracaoDTO.setIdItemConfiguracao(problemaDto.getHiddenIdItemConfiguracao());
				itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);
				document.getElementById("hiddenIdItemConfiguracao").setValue(itemConfiguracaoDTO.getIdItemConfiguracao().toString());

				document.executeScript("atualizarTabelaICs('" + itemConfiguracaoDTO.getIdItemConfiguracao() + "','" + itemConfiguracaoDTO.getIdentificacao() + "')");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Método necessário para os casos de PoppupManager, pois há uma função que quando a popup é fechada, é chamado um fireevent para carregar a combo, porém não tinha como acessar o metodo
	 * alimentaComboCategoriaProblema por ser private e era necessario ter um metodo que recebia os parametro (DocumentHTML, HttpServletRequest, HttpServletResponse)
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alimentaComboCatProblemaAposCadastro(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.alimentaComboCategoriaProblema(request, document);
	}
}
