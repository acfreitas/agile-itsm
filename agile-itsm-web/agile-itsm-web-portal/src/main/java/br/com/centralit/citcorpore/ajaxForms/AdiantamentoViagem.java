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
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.DespesaViagemService;
import br.com.centralit.citcorpore.negocio.IntegranteViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ParceiroService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.negocio.TipoMovimFinanceiraViagemService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes"})
public class AdiantamentoViagem extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		
		if(despesaViagemDTO.getIdSolicitacaoServico() != null) {
			this.restoreInfoViagem(document, request, response);
			this.restoreTreeIntegrantesViagem(document, request, response, false);
		}
	}
	
	/**
	 * Restaura as informações da requisição de viagem
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void restoreInfoViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		requisicaoViagemDTO.setIdSolicitacaoServico(despesaViagemDTO.getIdSolicitacaoServico());
		
		Double valorTotalViagem = 0.0;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormat decimal = (DecimalFormat) nf;
		decimal.applyPattern("#,##0.00");
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		requisicaoViagemDTO = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDTO);
		
		requisicaoViagemDTO.setIdContrato(despesaViagemDTO.getIdContrato());
		
		if(requisicaoViagemDTO.getIdSolicitacaoServico()!=null){
			if(requisicaoViagemDTO != null){
				valorTotalViagem = despesaViagemService.buscarDespesaTotalViagem(requisicaoViagemDTO.getIdSolicitacaoServico());
				
				if(requisicaoViagemDTO.getIdCidadeOrigem() != null) {
					requisicaoViagemDTO.setNomeCidadeOrigem(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeOrigem()));
				}
				
				if(requisicaoViagemDTO.getIdCidadeDestino() != null) {
					requisicaoViagemDTO.setNomeCidadeDestino(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeDestino()));
				}
			}
		}
		
		this.preencherComboCentroResultado(document, request, response);
		this.preencherComboProjeto(document, request, response);
		this.preencherComboJustificativa(document, request, response);
		
		HTMLForm form = document.getForm("form");
	    form.clear();   
	    form.setValues(requisicaoViagemDTO);
	    
	    document.getElementById("nomeCidadeOrigem").setDisabled(true);
	    document.getElementById("nomeCidadeDestino").setDisabled(true);
	    document.getElementById("dataInicioViagem").setDisabled(true);
	    document.getElementById("dataFimViagem").setDisabled(true);
	    document.getElementById("qtdeDias").setDisabled(true);
	    document.getElementById("idCentroCusto").setDisabled(true);
	    document.getElementById("idProjeto").setDisabled(true);
	    document.getElementById("idMotivoViagem").setDisabled(true);
	    document.getElementById("descricaoMotivo").setDisabled(true);
	    
	    document.getElementById("valorTotalViagem").setValue("<b>Custo total da viagem:</b> R$ " + decimal.format(valorTotalViagem));
	}
	
	/**
	 * Restaura os integrante gerando o HTML para a view
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param collapsed
	 * @throws ServiceException
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void restoreTreeIntegrantesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Boolean collapsed) throws ServiceException, Exception {
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		CidadesService cidadeService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
		ParceiroService parceiroService = (ParceiroService) ServiceLocator.getInstance().getService(ParceiroService.class, null);
		
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		Collection<RoteiroViagemDTO> colRoteiroViagemDTO = null;
		TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraViagemDTO = null;
		CidadesDTO origem = null;
		CidadesDTO destino = null;
		ParceiroDTO parceiroDTO = new ParceiroDTO();
		
		Collection<IntegranteViagemDTO> colIntegrantes =  integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_ADIANTAMENTO);
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormat decimal = (DecimalFormat) nf;
		decimal.applyPattern("#,##0.00");
		
		DecimalFormat quantidade = (DecimalFormat) nf.clone();
		quantidade.applyPattern("##00");
		
		StringBuilder despesaViagemHTML = new StringBuilder();
		StringBuilder roteiroViagemHTML = new StringBuilder();
		
		if(colIntegrantes != null){
			for(IntegranteViagemDTO integrante: colIntegrantes){
				colRoteiroViagemDTO = roteiroViagemService.findByIdIntegranteTodos(integrante.getIdIntegranteViagem());
				
				if(colRoteiroViagemDTO != null && !colRoteiroViagemDTO.isEmpty()) {
					roteiroViagemHTML.append("<div class='despesa-viagem-item'>");
					roteiroViagemHTML.append("	<ul class='filetree treeview browser'>");
					roteiroViagemHTML.append("		<li>");
					roteiroViagemHTML.append("			<div class='hitarea collapsable-hitarea lastCollapsable-hitarea'></div>");
					
					Double totalASerConfirmado = 0.0,
							totalJaConfirmado = 0.0;
					int contador = 0;
					
					for(RoteiroViagemDTO roteiroViagemDTO : colRoteiroViagemDTO) {
						if(roteiroViagemDTO.getDataFim() == null || roteiroViagemDTO.getDataFim().equals("")) {
							origem = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
							destino = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);
							
							roteiroViagemHTML.append("			<span class='folder'>" + integrante.getNome() +
									" - "+UtilI18N.internacionaliza(request, "si.comum.ida")+" " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()) + 
									" - "+UtilI18N.internacionaliza(request, "si.comum.volta")+" " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()) +
									" - " + origem.getNomeCidade() + "/" + origem.getNomeUf() + 
									" - " + destino.getNomeCidade() + "/" + destino.getNomeUf() + "</span>");
							
							roteiroViagemHTML.append("			<ul class='filetree treeview filetree-inner'>");
						}
						
						Collection<DespesaViagemDTO> colDespesaViagem = despesaViagemService.findDespesaViagemByIdRoteiroAndPermiteAdiantamento(roteiroViagemDTO.getIdRoteiroViagem(), "S");
						
						if(colDespesaViagem != null && !colDespesaViagem.isEmpty()) {
							for(DespesaViagemDTO despesaViagem : colDespesaViagem) {
								tipoMovimFinanceiraViagemDTO = tipoMovimFinanceiraViagemService.findByMovimentacao(Long.parseLong(despesaViagem.getIdTipo().toString()));
								if(tipoMovimFinanceiraViagemDTO.getExigePrestacaoConta().equalsIgnoreCase("S")){
									parceiroDTO.setIdParceiro(despesaViagem.getIdFornecedor());
									parceiroDTO = (ParceiroDTO) parceiroService.restore(parceiroDTO);
									
									despesaViagemHTML.append("				<li class='expandable'>");
									despesaViagemHTML.append("					<div class='hitarea collapsable-hitarea lastCollapsable-hitarea'></div>");
									despesaViagemHTML.append("					<span class='folder '><span class='glyphicons " + tipoMovimFinanceiraViagemDTO.getImagem() + "'><i></i>&nbsp;</span>" + tipoMovimFinanceiraViagemDTO.getClassificacao() + " - " + (despesaViagem.getOriginal() != null && despesaViagem.getOriginal().equalsIgnoreCase("N") ? "Remarcação" : "Original") + "/" + (despesaViagem.getSituacao() != null && despesaViagem.getSituacao().equalsIgnoreCase("Adiantado") ? "Confirmando" : "Não confirmado") + "</span>");
									despesaViagemHTML.append("					<ul style='display: none;'>");
									despesaViagemHTML.append("						<li class='file'>");
									despesaViagemHTML.append("							<p>" + quantidade.format(despesaViagem.getQuantidade()) + " <strong>" + tipoMovimFinanceiraViagemDTO.getClassificacao() + "</strong> no total de R$ " + despesaViagem.getTotalFormatado() + " - <strong>" + parceiroDTO.getNome() + "</strong></p>");
									
									if(despesaViagem.getObservacoes() != "" && despesaViagem.getObservacoes() != null) {
										despesaViagemHTML.append("							<p><strong>"+UtilI18N.internacionaliza(request, "citcorpore.comum.observacoes")+": </strong><br />" + despesaViagem.getObservacoes() + "</p>");
									}
									
									despesaViagemHTML.append("						</li>");
									despesaViagemHTML.append("					</ul>");
									despesaViagemHTML.append("				</li>");
									
									if((despesaViagem.getSituacao() == null || !despesaViagem.getSituacao().equalsIgnoreCase("Adiantado"))) {
										totalASerConfirmado += despesaViagem.getTotal();
									} else {
										totalJaConfirmado += despesaViagem.getTotal();
									}
								}
							}
						}
						
						contador++;
					}
					
					if(contador > 0) {
						despesaViagemHTML.append("				<li>");
						despesaViagemHTML.append("					<strong>Valor total a ser confirmado:</strong> R$ " + decimal.format(totalASerConfirmado) + " - <strong>Valor total já confirmado:</strong> R$ " + decimal.format(totalJaConfirmado));
						despesaViagemHTML.append("				</li>");
					} else {
						despesaViagemHTML.append("				<li>"+UtilI18N.internacionaliza(request, "requisicaoViagem.integranteViagemNaoAdiantamento")+"!</li>");
					}
					
					roteiroViagemHTML.append(despesaViagemHTML.toString());
					
					despesaViagemHTML = new StringBuilder();
					
					roteiroViagemHTML.append("			</ul>");
					roteiroViagemHTML.append("		</li>");
					roteiroViagemHTML.append("	</ul>");
					roteiroViagemHTML.append("</div><!-- .despesa-viagem-item -->");
				}
			}
			
			document.getElementById("despesa-viagem-items-container").setInnerHTML(roteiroViagemHTML.toString());
			document.executeScript("$('.browser').treeview({collapsed: " + collapsed + "});");
		}else{
			Collection<IntegranteViagemDTO> colIntegrantesAux =  integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS);
			if(colIntegrantesAux == null){
				document.executeScript("window.top.document.getElementById('modal_novaSolicitacao').children[0].children[0].click();");
				document.alert(UtilI18N.internacionaliza(request, "remarcaoViagem.integrantesRemarcados"));
			}
		}
	}
	
	/**
	 * Preenche a combo de 'Centro Resultado' do formulário HTML
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboCentroResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        
		HTMLSelect comboCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        
		comboCentroCusto.removeAllOptions();
		comboCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        
        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        
        if(colCCusto != null && !colCCusto.isEmpty()){
        	comboCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
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
	 * @author renato.jesus
	 */
	public void preencherComboProjeto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		
		HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		
		comboProjeto.removeAllOptions();
		comboProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if (despesaViagemDTO.getIdContrato() != null) {
		    ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		    
		    ContratoDTO contratoDto = new ContratoDTO();
		    
		    contratoDto.setIdContrato(despesaViagemDTO.getIdContrato());
		    contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		    
		    if (contratoDto != null) {
		        ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
		        Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
		        if(colProjetos != null && !colProjetos.isEmpty()) { 
		        	comboProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
		        }
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
	 * @author renato.jesus
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService)ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		
		Collection colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idMotivoViagem");
		
		comboJustificativa.removeAllOptions();
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if (colJustificativas != null){
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}
	
	/**
	 * Retorna o nome da cidade e o estado com base no 'idCidade' passado
	 * 
	 * @param idCidade
	 * @return
	 * @throws Exception
	 * @author renato.jesus
	 */
	public String recuperaCidade(Integer idCidade) throws Exception {
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		
		CidadesDTO cidadeDto = new CidadesDTO();
		
		if (idCidade != null) {
			cidadeDto = (CidadesDTO) cidadesService.findCidadeUF(idCidade);
			return cidadeDto.getNomeCidade() + " - " + cidadeDto.getNomeUf();
		}
		
		return null;
	}
	
	@Override
	public Class getBeanClass() {
		return DespesaViagemDTO.class;
	}
}