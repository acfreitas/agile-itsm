package br.com.centralit.citcorpore.ajaxForms;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.DespesaViagemService;
import br.com.centralit.citcorpore.negocio.ItemPrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ParecerService;
import br.com.centralit.citcorpore.negocio.PrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes"})
public class ConferenciaViagem extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		prestacaoContasViagemDto.setIdEmpregado(this.recuperaEmpregado(prestacaoContasViagemDto));
		
		prestacaoContasViagemDto = this.recuperaPrestacaoContas(prestacaoContasViagemDto);
		
		if(prestacaoContasViagemDto != null && prestacaoContasViagemDto.getIdEmpregado() != null){		
			restore(document, request, response, prestacaoContasViagemDto);
			this.geraGridItemsPrestacaoContas(document, request, response, prestacaoContasViagemDto);
			this.montaIntegrante(document, request, response);
			this.montaValores(document, request, response, usuario);
		}
		
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
		ParecerDTO parecerDto = new ParecerDTO();
		ParecerService parecerService = (ParecerService) ServiceLocator.getInstance().getService(ParecerService.class, null);
		
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		
		prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemService.restore(prestacaoContasViagemDto);	
		
		if (prestacaoContasViagemDto.getIdAprovacao() != null){
			parecerDto.setIdParecer(prestacaoContasViagemDto.getIdAprovacao());
			parecerDto = (ParecerDTO) parecerService.restore(parecerDto);
			if (parecerDto != null){
				this.preencherComboJustificativaAutorizacao(document, request, response);
				prestacaoContasViagemDto.setAprovado(parecerDto.getAprovado());
				prestacaoContasViagemDto.setIdJustificativaAutorizacao(parecerDto.getIdJustificativa());
				prestacaoContasViagemDto.setComplemJustificativaAutorizacao(parecerDto.getComplementoJustificativa());
			}
		}
				
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(prestacaoContasViagemDto);
		
		this.restauraDadosGeraisSolicitacao(document, request, response, prestacaoContasViagemDto);
		
		if (prestacaoContasViagemDto != null && prestacaoContasViagemDto.getAprovado() != null && !prestacaoContasViagemDto.getAprovado().equals("")){
			if (prestacaoContasViagemDto.getAprovado().equalsIgnoreCase("S")){
				document.executeScript("$('#divJustificativa').hide();");
			}else{
				document.executeScript("$('#divJustificativa').show();");
			}
		}
	}
	
	/**
	 * Recupe os dados do integrante e mostra na tela do usuario
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void montaIntegrante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		
		RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		CidadesDTO origem = null;
		CidadesDTO destino = null;

		IntegranteViagemDTO integranteViagemDTO = new IntegranteViagemDao().findByIdSolicitacaoServicoIdTarefa(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdTarefa());
		if(integranteViagemDTO != null){
			roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integranteViagemDTO.getIdIntegranteViagem());
			origem = (CidadesDTO) ((List) cidadesService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
			destino = (CidadesDTO) ((List) cidadesService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);
			StringBuilder html = new StringBuilder();
			html.append(integranteViagemDTO.getNome() + 
					" - ida " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()) + 
					" - volta " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()) +
					" - " + origem.getNomeCidade() + "/" + origem.getNomeUf() + 
					" - " + destino.getNomeCidade() + "/" + destino.getNomeUf());
			
			document.getElementById("nomeIntegranteViagem").setInnerHTML(html.toString());	
		}
	}
	
	/**
	 * Recupera os valores informados na prestação de contas como "Total da Prestação de Contas", "Total Lançamentos" e "Diferença"
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param usuario
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void montaValores(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, UsuarioDTO usuario) throws Exception{
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		ItemPrestacaoContasViagemService itemPrestacaoContasViagemService = (ItemPrestacaoContasViagemService) ServiceLocator.getInstance().getService(ItemPrestacaoContasViagemService.class, null);
		Double totalItens = 0.0;
		
		prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemService.restore(prestacaoContasViagemDto);
		Collection<ItemPrestacaoContasViagemDTO> collection = itemPrestacaoContasViagemService.recuperaItensPrestacao(prestacaoContasViagemDto);
		if(collection != null && collection.size() > 0){
			for(ItemPrestacaoContasViagemDTO item: collection){
				totalItens = totalItens + item.getValor();
			}
		}
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormat decimal = (DecimalFormat) nf;
		decimal.applyPattern("#,##0.00");
		
		Double totalPrestacaoContas = despesaViagemService.getTotalDespesaViagemPrestacaoContas(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado());
		Double totalDiferenca = totalPrestacaoContas - totalItens;
		
		document.getElementById("totalPrestacaoContas").setValue(decimal.format(totalPrestacaoContas));
		document.getElementById("totalLancamentos").setValue(decimal.format(totalItens));
		document.getElementById("valorDiferenca").setValue(decimal.format(totalDiferenca));
	}
	
	/**
	 * Gera a grid com os itens informados em prestação de contas
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param prestacaoContasViagemDto
	 * @throws ServiceException
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void geraGridItemsPrestacaoContas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception{
		
		ItemPrestacaoContasViagemService itemService = (ItemPrestacaoContasViagemService) ServiceLocator.getInstance().getService(ItemPrestacaoContasViagemService.class, null);
		Collection<ItemPrestacaoContasViagemDTO> colItens = itemService.recuperaItensPrestacao(prestacaoContasViagemDto);
		
		if(colItens != null){
			HTMLTable tabelaItemPrestacaoContasViagem;
			tabelaItemPrestacaoContasViagem = document.getTableById("tabelaItemPrestacaoContasViagem");
			tabelaItemPrestacaoContasViagem.deleteAllRows();
			tabelaItemPrestacaoContasViagem.addRowsByCollection(colItens, new String[]{"numeroDocumento","data","nomeFornecedor","valor","descricao"}, null, null, null, null, null);
		}
		
	}
	
	/**
	 * Retorna a prestação de contas atual e suas informações
	 * 
	 * @param prestacaoContasViagemDto
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author thiago.borges
	 */
	private PrestacaoContasViagemDTO recuperaPrestacaoContas(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception{
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		
		
		prestacaoContasViagemDto.setIdPrestacaoContasViagem(prestacaoContasViagemService.recuperaIdPrestacaoSeExistir(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado()));
		
		try {
			prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemService.restore(prestacaoContasViagemDto);
			return prestacaoContasViagemDto;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Retorna o empregado ligado a prestação de contas atual
	 * 
	 * @param prestacaoContasViagemDto
	 * @return
	 * @throws Exception
	 * @author thiago.borges
	 */
	private Integer recuperaEmpregado(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContasAux = new PrestacaoContasViagemDTO();
		//verifica se ja existe um prestação de contas para associada a essa tarefa
		prestacaoContasAux = dao.findByTarefaAndSolicitacao(prestacaoContasViagemDto.getIdTarefa(), prestacaoContasViagemDto.getIdSolicitacaoServico());
		if(prestacaoContasAux != null){
			return prestacaoContasAux.getIdEmpregado();
		}
		//se nao existir uma prestação, busca uma sem associação e associa a tarefa atual
		List list = dao.findBySolicitacao(prestacaoContasViagemDto.getIdSolicitacaoServico());
		if(list != null){
			prestacaoContasAux = (PrestacaoContasViagemDTO) list.get(0);
			if(prestacaoContasAux != null && prestacaoContasAux.getIdEmpregado() != null){
				prestacaoContasAux.setIdItemTrabalho(prestacaoContasViagemDto.getIdTarefa());
				dao.update(prestacaoContasAux);
				return prestacaoContasAux.getIdEmpregado();
			}		
		}
		return null;
	}

	@Override
	public Class getBeanClass() {
		return PrestacaoContasViagemDTO.class;
	}
	
	/**
	 * Preenche a combo de justificativa de não autorização da prestação de contas
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void preencherComboJustificativaAutorizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
		
	    Collection colJustificativas = justificativaService.listAplicaveisRequisicao();
	    
		HTMLSelect comboJustificativaAutorizacao = (HTMLSelect) document.getSelectById("idJustificativaAutorizacao");
		
		document.getSelectById("idJustificativaAutorizacao").removeAllOptions();
		
		comboJustificativaAutorizacao.removeAllOptions();
		comboJustificativaAutorizacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if (colJustificativas != null){
			comboJustificativaAutorizacao.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}

	/**
	 * Restaura os dados da solicitação com cidade de origem e destino, data de ida e volta.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param prestacaoContasViagemDto
	 * @throws Exception
	 * @author thiago.borges
	 */
	private void restauraDadosGeraisSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
		
		requisicaoViagemDTO = requisicaoViagemDAO.findByIdSolicitacao(prestacaoContasViagemDto.getIdSolicitacaoServico());
		
		if(requisicaoViagemDTO != null){
			this.preencherComboCentroResultado(document, request, response);
			this.preencherComboProjeto(document, request, response, requisicaoViagemDTO);
			this.preencherComboJustificativa(document, request, response);
			
			requisicaoViagemDTO.setNomeCidadeOrigem(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeOrigem()));
			requisicaoViagemDTO.setNomeCidadeDestino(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeDestino()));
			
			document.getElementById("nomeCidadeOrigem").setValue(requisicaoViagemDTO.getNomeCidadeOrigem());
			document.getElementById("nomeCidadeDestino").setValue(requisicaoViagemDTO.getNomeCidadeDestino());
			document.getElementById("dataInicioViagem").setValue(requisicaoViagemDTO.getDataInicioViagem().toString());
			document.getElementById("dataFimViagem").setValue(requisicaoViagemDTO.getDataFimViagem().toString());
			document.getElementById("qtdeDias").setValue(requisicaoViagemDTO.getQtdeDias().toString());
			document.getElementById("descricaoMotivo").setValue(requisicaoViagemDTO.getDescricaoMotivo());
			document.getElementById("idCentroCusto").setValue(requisicaoViagemDTO.getIdCentroCusto().toString());
			document.getElementById("idProjeto").setValue(requisicaoViagemDTO.getIdProjeto().toString());
			document.getElementById("idMotivoViagem").setValue(requisicaoViagemDTO.getIdMotivoViagem().toString());
		}
	}

	/**
	 * Retorna a cidade conforme idcidade passado
	 * 
	 * @param idCidade
	 * @return
	 * @throws Exception
	 * @author thiago.borges
	 */
	public String recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto  = new CidadesDTO();
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		if (idCidade != null) {
			cidadeDto = (CidadesDTO) cidadesService.findCidadeUF(idCidade);
			return cidadeDto.getNomeCidade() + " - " + cidadeDto.getNomeUf();
		}
		return null;
	}
	
	/**
	 * Preenche a combo de 'Centro Resultado' do formulário HTML
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thiago.Borges
	 */
	public void preencherComboCentroResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
		
        HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        
        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        if(colCCusto != null && !colCCusto.isEmpty()){
        	 idCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
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
	 * @author Thiago.Borges
	 */
	public void preencherComboProjeto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoViagemDTO requisicaoViagemDTO) throws Exception{
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		
		idProjeto.removeAllOptions();
		idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if (prestacaoContasViagemDto.getIdContrato() != null) {
		    ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		    ContratoDTO contratoDto = new ContratoDTO();
		    contratoDto.setIdContrato(prestacaoContasViagemDto.getIdContrato());
		    contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		    if (contratoDto != null) {
		        ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
		        Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
		        if(colProjetos != null && !colProjetos.isEmpty()) 
		            idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
		    }
		}
	}
	
	/**
	 * Preenche combo de 'justificativa solicitação'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thiago.Borges
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService)ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		
		Collection<JustificativaSolicitacaoDTO> colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idMotivoViagem");
		document.getSelectById("idMotivoViagem").removeAllOptions();
		
		comboJustificativa.removeAllOptions();
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if (colJustificativas != null){
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}
}
