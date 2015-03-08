package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * @author thays.araujo
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RequisicaoViagem extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		this.preencherComboCentroResultado(document, request, response);
		this.preencherComboProjeto(document, request, response, requisicaoViagemDto);
		this.preencherComboJustificativa(document, request, response);

		if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
			restore(document, request, response, requisicaoViagemDto);
		}

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoViagemDTO requisicaoViagemDto) throws ServiceException, Exception {

		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);

		if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
			requisicaoViagemDto = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDto);

			requisicaoViagemDto.setNomeCidadeOrigem(this.recuperaCidade(requisicaoViagemDto.getIdCidadeOrigem()));
			requisicaoViagemDto.setNomeCidadeDestino(this.recuperaCidade(requisicaoViagemDto.getIdCidadeDestino()));
		}
		this.montarGridIntegrateViagem(document, request, response);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(requisicaoViagemDto);

	}

	@Override
	public Class getBeanClass() {
		return RequisicaoViagemDTO.class;
	}

	/**
	 * Preenche a combo de 'Centro Resultado' do formulário HTML
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboCentroResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
		HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
		//RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		idCentroCusto.removeAllOptions();
		
		/*
        if(request.getSession().getAttribute("idSolicitante") != null){
        	requisicaoViagemDto.setIdSolicitante((Integer) request.getSession().getAttribute("idSolicitante"));
        } else {
        	requisicaoViagemDto.setIdSolicitante(0);
        }*/	
        
		idCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		//Collection colCentroCusto = centroResultadoService.listAtivosVinculados(requisicaoViagemDto.getIdSolicitante(), "Viagem");
		Collection colCentroCusto = centroResultadoService.getHierarquiaCentroDeCustoAtivo(false, false, true);
		
		if (colCentroCusto != null && !colCentroCusto.isEmpty()) {
			idCentroCusto.addOptions(colCentroCusto, "idCentroResultado", "nomeHierarquizado", null);
		}
	}

	/**
	 * Preenche a combo de 'Projeto' do formulário HTML
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param requisicaoViagemDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboProjeto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		idProjeto.removeAllOptions();
		idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (requisicaoViagemDto.getIdContrato() != null) {
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
			ContratoDTO contratoDto = new ContratoDTO();
			contratoDto.setIdContrato(requisicaoViagemDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			if (contratoDto != null) {
				ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
				Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
				if (colProjetos != null && !colProjetos.isEmpty())
					idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
			}
		}
	}

	/**
	 * Preenche as combos de 'Cidade Origem' e Cidade Destino.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

		HTMLSelect comboCidadeOrigem = (HTMLSelect) document.getSelectById("idCidadeOrigem");
		HTMLSelect comboCidadeDestino = (HTMLSelect) document.getSelectById("idCidadeDestino");

		ArrayList<CidadesDTO> listCidade = (ArrayList) cidadesService.list();

		this.inicializaCombo(comboCidadeOrigem, request);
		this.inicializaCombo(comboCidadeDestino, request);
		if (listCidade != null) {
			comboCidadeOrigem.addOptions(listCidade, "idCidade", "nomeCidade", null);
			comboCidadeDestino.addOptions(listCidade, "idCidade", "nomeCidade", null);
		}
	}

	/**
	 * Preenche combo de 'justificativa solicitação'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);

		Collection<JustificativaSolicitacaoDTO> colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();

		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idMotivoViagem");
		document.getSelectById("idMotivoViagem").removeAllOptions();
		inicializaCombo(comboJustificativa, request);
		if (colJustificativas != null) {
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}

	/**
	 * Executa uma inicialização padrão para as combos. Basicamente deleta todas as opções, caso haja, e insere aprimeira linha com o valor "-- Selecione --".
	 * 
	 * @param componenteCombo
	 * @param request
	 */
	public void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	/**
	 * Recupera cidade conforme idcidade passado
	 * 
	 * @param idCidade
	 * @return
	 * @throws Exception
	 */
	public String recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto = new CidadesDTO();
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		if (idCidade != null) {
			cidadeDto = (CidadesDTO) cidadesService.findCidadeUF(idCidade);
			return cidadeDto.getNomeCidade() + " - " + cidadeDto.getNomeUf();
		}
		return null;
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public void informacoesItemControleFinanceiroPorIntegrateViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();

		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);

		Collection<IntegranteViagemDTO> colIntegrantes = reqViagemService.recuperaIntegrantesViagemBySolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());

		if (colIntegrantes != null) {

			HTMLTable tblControleFinaceiro;
			tblControleFinaceiro = document.getTableById("tblControleFinaceiro");
			tblControleFinaceiro.deleteAllRows();
			tblControleFinaceiro.addRowsByCollection(colIntegrantes, new String[] { "", "idEmpregado", "nome" }, new String[] { "idEmpregado" },
					UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaAdicionado"), new String[] { "gerarImg" }, "addItemIntegrante", null);
		}
	}

	/**
	 * Preenche combo de 'justificativa solicitação ' para autozização.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboJustificativaAutorizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));

		Collection colJustificativas = justificativaService.listAplicaveisRequisicao();

		HTMLSelect comboJustificativaAutorizacao = (HTMLSelect) document.getSelectById("idJustificativaAutorizacao");

		document.getSelectById("idJustificativaAutorizacao").removeAllOptions();

		inicializaCombo(comboJustificativaAutorizacao, request);

		if (colJustificativas != null) {
			comboJustificativaAutorizacao.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}

	/**
	 * Restaura a grid de integrantes
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void montarGridIntegrateViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();

		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);

		Collection<IntegranteViagemDTO> colIntegrantes = reqViagemService.recuperaIntegrantesViagemBySolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());

		if (colIntegrantes != null) {
			HTMLTable tblIntegranteViagem;
			tblIntegranteViagem = document.getTableById("tblIntegranteViagem");
			tblIntegranteViagem.deleteAllRows();
			tblIntegranteViagem.addRowsByCollection(colIntegrantes, new String[] { "nome", "integranteFuncionario", "respPrestacaoContas", "nomeNaoFuncionario", "" }, null, null, new String[] { "gerarButtonDelete" }, null, null);
		}
	}

	/**
	 * Verifica se o empregado esta no grupo que permite a atribuição de contas a outro empregado, se sim, habilita a div com os campos para atribuição, caso contrario esconde a div
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void validaAtribuicao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		Integer idGrupo;

		if (requisicaoViagemDto != null && requisicaoViagemDto.getIdEmpregado() != null) {

			try {
				idGrupo = Integer.parseInt(ParametroUtil.getValor(ParametroSistema.GRUPO_PERMISSAO_DELEGAR_PRESTACAO_VIAGEM));
			} catch (Exception e) {
				document.executeScript("$('#divResponsavelEmpregado').hide();");
				return;
			}

			if (idGrupo == null) {
				document.executeScript("$('#divResponsavelEmpregado').hide();");
				return;
			}

			GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

			if (grupoEmpregadoService.grupoempregado(requisicaoViagemDto.getIdEmpregado(), idGrupo))
				document.executeScript("$('#divResponsavelEmpregado').show();");
			else
				document.executeScript("$('#divResponsavelEmpregado').hide();");

		} else
			document.executeScript("$('#divResponsavelEmpregado').hide();");
	}
	
	/**
	 * Metodo cria noa funcionario na tabela de empregados caso não encontre uma referencia no autocomplete
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createNaoFuncionario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		
		
		empregadoDto.setNome(requisicaoViagemDto.getNomeNaoFuncionario());
		empregadoDto.setObservacoes(requisicaoViagemDto.getInfoNaoFuncionario());
		empregadoDto.setTipo("N");
		empregadoDto.setIdSituacaoFuncional(1);
		
		if(requisicaoViagemDto.getIdEmpregado() == null || requisicaoViagemDto.getIdEmpregado().equals("")){
			empregadoDto = (EmpregadoDTO) empregadoService.create(empregadoDto);
		}else{
			empregadoDto.setIdEmpregado(requisicaoViagemDto.getIdEmpregado());
			empregadoService.update(empregadoDto);
			document.getElementById("nomeNaoFuncionario").setValue(empregadoDto.getNome());
			document.getElementById("nomeNaoFuncionarioAux").setValue(empregadoDto.getNome());
			document.getElementById("infoNaoFuncionario").setValue(empregadoDto.getObservacoes());
			document.getElementById("infoNaoFuncionarioAux").setValue(empregadoDto.getObservacoes());
		}
		
		document.getElementById("idEmpregado").setValue(empregadoDto.getIdEmpregado().toString());
		document.executeScript("adicionarEmpregado()");
		
		document.executeScript("decodificaTextarea();");
	}
	
	/**
	 * Restaura as informações do não funcionario caso tenha informações
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restoreInfNaoFuncionario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		
		empregadoDto = (EmpregadoDTO) empregadoService.restoreByIdEmpregado(requisicaoViagemDto.getIdEmpregado());
		
		document.getElementById("infoNaoFuncionario").setValue(empregadoDto.getObservacoes().toString());
		document.getElementById("infoNaoFuncionarioAux").setValue(empregadoDto.getObservacoes().toString());
		
		document.executeScript("decodificaTextarea();");
	}
}
