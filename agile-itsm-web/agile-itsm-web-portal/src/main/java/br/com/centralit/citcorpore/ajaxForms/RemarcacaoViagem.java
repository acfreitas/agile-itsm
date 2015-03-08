package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.FormaPagamentoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.DespesaViagemService;
import br.com.centralit.citcorpore.negocio.FormaPagamentoService;
import br.com.centralit.citcorpore.negocio.IntegranteViagemService;
import br.com.centralit.citcorpore.negocio.MoedaService;
import br.com.centralit.citcorpore.negocio.ParceiroService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.negocio.TipoMovimFinanceiraViagemService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;


@SuppressWarnings({"rawtypes","unchecked"})
public class RemarcacaoViagem extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;
	}

	@Override
	public Class getBeanClass() {
		return IntegranteViagemDTO.class;
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{		
		if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;	
	}	
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoViagemDTO requisicaoViagemDto) throws ServiceException, Exception {

	}
	
	/**
	 * Utilizado para remarcar viagem.
	 * Adiciona um novo roteiro e itens despesas ao integrante.
	 * Altera o status da viagem e do integrante para remarcado.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void remarcarViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IntegranteViagemDTO integranteViagemDto = (IntegranteViagemDTO) document.getBean();
		IntegranteViagemDTO integranteViagemDtoAux = new IntegranteViagemDTO(); 
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		RequisicaoViagemService requisicaoViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, WebUtil.getUsuarioSistema(request));
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, WebUtil.getUsuarioSistema(request));
		RoteiroViagemDTO roteiroViagemDto = new RoteiroViagemDTO();
		RoteiroViagemDTO roteiroViagemDtoAux = new RoteiroViagemDTO();
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, WebUtil.getUsuarioSistema(request));
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, WebUtil.getUsuarioSistema(request));
		Collection<DespesaViagemDTO> colDespesaDel = new ArrayList<DespesaViagemDTO>();

		Collection<DespesaViagemDTO> colDespesa = br.com.citframework.util.WebUtil.deserializeCollectionFromString(DespesaViagemDTO.class, integranteViagemDto.getColDespesaViagemSerialize());
		if(colDespesa == null || colDespesa.isEmpty()){
			 document.alert(UtilI18N.internacionaliza(request, "requisicaoViagem.adicionarItemRemarcar"));
			 return;
		}
		
		if(integranteViagemDto.getRemarcarRoteiro() != null && integranteViagemDto.getRemarcarRoteiro().equalsIgnoreCase("S")){
			roteiroViagemDtoAux = roteiroViagemService.findByIdIntegrante(integranteViagemDto.getIdIntegranteViagem());
			roteiroViagemDtoAux.setDataFim(UtilDatas.getDataAtual());
			roteiroViagemService.update(roteiroViagemDtoAux);
			
			roteiroViagemDto.setDataInicio(UtilDatas.getDataAtual());
			roteiroViagemDto.setIdSolicitacaoServico(integranteViagemDto.getIdSolicitacao());
			roteiroViagemDto.setIdIntegrante(integranteViagemDto.getIdIntegranteViagem());
			roteiroViagemDto.setOrigem(integranteViagemDto.getOrigem());
			roteiroViagemDto.setDestino(integranteViagemDto.getDestino());
			roteiroViagemDto.setIda(integranteViagemDto.getIda());
			roteiroViagemDto.setVolta(integranteViagemDto.getVolta());
			
			roteiroViagemDtoAux = new RoteiroViagemDTO();
			roteiroViagemDtoAux = (RoteiroViagemDTO) roteiroViagemService.create(roteiroViagemDto);
			
			colDespesaDel = despesaViagemService.findDespesaViagemByIdRoteiro(integranteViagemDto.getIdRoteiro());
			if(colDespesaDel != null && colDespesaDel.size() > 0){
				for(DespesaViagemDTO dto: colDespesaDel){
					dto.setDataFim(UtilDatas.getDataAtual());
					despesaViagemService.update(dto);
				}
			}
			
			if(colDespesa != null && colDespesa.size() > 0){
				for(DespesaViagemDTO despesaViagemDTO: colDespesa){
					despesaViagemDTO.setDataInicio(UtilDatas.getDataAtual());
					despesaViagemDTO.setIdRoteiro(roteiroViagemDtoAux.getIdRoteiroViagem());
					despesaViagemDTO.setSituacao("Remarcacao");
					
					TipoMovimFinanceiraViagemService tipoMovimentacaoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
					
					TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraDto = new TipoMovimFinanceiraViagemDTO();
					
					tipoMovimFinanceiraDto.setIdtipoMovimFinanceiraViagem(despesaViagemDTO.getIdTipo());
					tipoMovimFinanceiraDto = (TipoMovimFinanceiraViagemDTO) tipoMovimentacaoService.restore(tipoMovimFinanceiraDto);
					
					if(tipoMovimFinanceiraDto != null && tipoMovimFinanceiraDto.getExigeDataHoraCotacao().equalsIgnoreCase("S"))
						despesaViagemDTO.setValidade(Timestamp.valueOf(despesaViagemDTO.getPrazoCotacaoAux() + " " + Time.valueOf(despesaViagemDTO.getHoraCotacaoAux() + ":00")));
					
					despesaViagemDTO.setOriginal("N");
					despesaViagemDTO.setIdSolicitacaoServico(integranteViagemDto.getIdSolicitacao());
					despesaViagemService.create(despesaViagemDTO);
				}
			}
			
			requisicaoViagemDTO.setIdSolicitacaoServico(integranteViagemDto.getIdSolicitacao());
			requisicaoViagemDTO = (RequisicaoViagemDTO) requisicaoViagemService.restore(requisicaoViagemDTO);
			requisicaoViagemDTO.setEstado(RequisicaoViagemDTO.REMARCADO);
			requisicaoViagemService.update(requisicaoViagemDTO);
			
			integranteViagemDtoAux = (IntegranteViagemDTO) integranteViagemService.restore(integranteViagemDto);
			integranteViagemDtoAux.setRemarcacao("S");
			integranteViagemDtoAux.setEstado(RequisicaoViagemDTO.REMARCADO);
			integranteViagemService.update(integranteViagemDtoAux);
		}else{
			if(colDespesa != null && colDespesa.size() > 0){
				colDespesaDel = despesaViagemService.findDespesaViagemByIdRoteiro(integranteViagemDto.getIdRoteiro());
				if(colDespesaDel != null && colDespesaDel.size() > 0){
					for(DespesaViagemDTO dto: colDespesaDel){
						dto.setDataFim(UtilDatas.getDataAtual());
						despesaViagemService.update(dto);
					}
				}
				
				if(colDespesa != null && colDespesa.size() > 0){
					for(DespesaViagemDTO despesaViagemDTO: colDespesa){
						despesaViagemDTO.setDataInicio(UtilDatas.getDataAtual());
						despesaViagemDTO.setIdRoteiro(integranteViagemDto.getIdRoteiro());
						despesaViagemDTO.setSituacao("Remarcacao");
						
						TipoMovimFinanceiraViagemService tipoMovimentacaoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
						
						TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraDto = new TipoMovimFinanceiraViagemDTO();
						
						tipoMovimFinanceiraDto.setIdtipoMovimFinanceiraViagem(despesaViagemDTO.getIdTipo());
						tipoMovimFinanceiraDto = (TipoMovimFinanceiraViagemDTO) tipoMovimentacaoService.restore(tipoMovimFinanceiraDto);
						
						if(tipoMovimFinanceiraDto != null && tipoMovimFinanceiraDto.getExigeDataHoraCotacao().equalsIgnoreCase("S"))
							despesaViagemDTO.setValidade(Timestamp.valueOf(despesaViagemDTO.getPrazoCotacaoAux() + " " + Time.valueOf(despesaViagemDTO.getHoraCotacaoAux() + ":00")));
						
						despesaViagemDTO.setOriginal("N");
						despesaViagemDTO.setIdSolicitacaoServico(integranteViagemDto.getIdSolicitacao());
						despesaViagemService.create(despesaViagemDTO);
					}
				}
			}else{
				 document.alert(UtilI18N.internacionaliza(request, "requisicaoViagem.integranteNaoPossuiAlteracao"));
				 return;
			}
			
			requisicaoViagemDTO.setIdSolicitacaoServico(integranteViagemDto.getIdSolicitacao());
			requisicaoViagemDTO = (RequisicaoViagemDTO) requisicaoViagemService.restore(requisicaoViagemDTO);
			requisicaoViagemDTO.setEstado(RequisicaoViagemDTO.REMARCADO);
			requisicaoViagemService.update(requisicaoViagemDTO);
			
			integranteViagemDtoAux = (IntegranteViagemDTO) integranteViagemService.restore(integranteViagemDto);
			integranteViagemDtoAux.setRemarcacao("S");
			integranteViagemDtoAux.setEstado(RequisicaoViagemDTO.REMARCADO);
			integranteViagemService.update(integranteViagemDtoAux);
		}
		
		document.alert(UtilI18N.internacionaliza(request, "remarcaoViagem.efetuadaSucesso"));
		document.executeScript("limparFormulario()");
		document.executeScript("$('.tabsbar a[href="+"#tab1-3"+"]').tab('show');");
	}
	
	/**
	 * Busca integrantes de viagem que podem ser remarcados
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void pesquisaRequisicoesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	        IntegranteViagemDTO integranteViagemDTO = (IntegranteViagemDTO) document.getBean();
	        IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, WebUtil.getUsuarioSistema(request));
	        RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
	        RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, WebUtil.getUsuarioSistema(request));

	        
	        UsuarioDTO usuario = WebUtil.getUsuario(request);
	        if (usuario == null) {
	            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
	            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	            return;
	        }
	       
	        HTMLTable tblRequisicoesViagem = document.getTableById("tblRequisicoesViagem");
	        tblRequisicoesViagem.deleteAllRows();
	        
	        //metodo que recupera a coleção dos itens da pagina referenciada
	        Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemService.recuperaIntegrantesRemarcacao(integranteViagemDTO, integranteViagemDTO.getEOu());
	        CidadesService cidadesService = (CidadesService)ServiceLocator.getInstance().getService(CidadesService.class, null);
	        CidadesDTO cidadesDTO = new CidadesDTO();
	        
	        Collection<IntegranteViagemDTO> colIntegrantesAux = new ArrayList<IntegranteViagemDTO>();
	        
	        if(colIntegrantes != null && !colIntegrantes.isEmpty())
		        for(IntegranteViagemDTO dto: colIntegrantes){
		        	dto = (IntegranteViagemDTO) integranteViagemService.restore(dto);
		        	roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(dto.getIdIntegranteViagem());
		        	cidadesDTO = cidadesService.findCidadeUF(roteiroViagemDTO.getOrigem());
		        	dto.setNomeOrigem(cidadesDTO.getNomeCidade()+ " - " + cidadesDTO.getNomeUf());
		        	cidadesDTO = cidadesService.findCidadeUF(roteiroViagemDTO.getDestino());
		        	dto.setNomeDestino(cidadesDTO.getNomeCidade()+ " - " + cidadesDTO.getNomeUf());
		        	dto.setIda(roteiroViagemDTO.getIda());
		        	dto.setVolta(roteiroViagemDTO.getVolta());
		        	colIntegrantesAux.add(dto);
		        }
	       
	        if (colIntegrantesAux != null && !colIntegrantesAux.isEmpty()){
				tblRequisicoesViagem.addRowsByCollection(colIntegrantesAux, new String[]{"idSolicitacaoServico","ida","volta","nome", "nomeOrigem", "nomeDestino", "estado", ""}, null, null, new String[]{"gerarImg"}, "addItens", null);
	        }else{
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
				 tblRequisicoesViagem.deleteAllRows();
	        }	
	}
	
	/**
	 * Busca as informações do integrante selecionado para remarcação.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void recuperaInformacoesIntegrante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		IntegranteViagemDTO integranteViagemDTO = (IntegranteViagemDTO) document.getBean();
		document.getElementById("idSolicitacao").setValue(integranteViagemDTO.getIdSolicitacao().toString());
		
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService)ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		IntegranteViagemService integranteViagemService = (IntegranteViagemService)ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
		CidadesService cidadesService = (CidadesService)ServiceLocator.getInstance().getService(CidadesService.class, null);
        CidadesDTO cidadesDTO = new CidadesDTO();
		RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
		
		roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integranteViagemDTO.getIdIntegranteViagem());
		integranteViagemDTO = (IntegranteViagemDTO) integranteViagemService.restore(integranteViagemDTO);
		
		if(roteiroViagemDTO != null){
			document.getElementById("idRoteiro").setValue(roteiroViagemDTO.getIdRoteiroViagem().toString());
			
			cidadesDTO = cidadesService.findCidadeUF(roteiroViagemDTO.getOrigem());
			document.getElementById("nomeCidadeOrigem").setValue(cidadesDTO.getNomeCidade()+ " - " + cidadesDTO.getNomeUf());
			document.getElementById("idCidadeOrigemAux").setValue(cidadesDTO.getIdCidade().toString());
			document.getElementById("origem").setValue(cidadesDTO.getIdCidade().toString());
			
			cidadesDTO = cidadesService.findCidadeUF(roteiroViagemDTO.getDestino());
			document.getElementById("nomeCidadeDestino").setValue(cidadesDTO.getNomeCidade()+ " - " + cidadesDTO.getNomeUf());	
			document.getElementById("idCidadeDestinoAux").setValue(cidadesDTO.getIdCidade().toString());
			document.getElementById("destino").setValue(cidadesDTO.getIdCidade().toString());
			
			document.getElementById("ida").setValue(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()));
			document.getElementById("volta").setValue(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()));
			document.getElementById("idaAux").setValue(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()));
			document.getElementById("voltaAux").setValue(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()));
			
			document.executeScript("calcularQuantidadeDias()");
		}
		
		document.getElementById("nomeIntegrante").setValue(integranteViagemDTO.getNome() + " ("+integranteViagemDTO.getIdSolicitacaoServico().toString()+")");
		document.getElementById("nomeIntegrante").setDisabled(true);
		
		this.atualizaGridItensControleHist(document, request, response, integranteViagemDTO);
		this.atualizaGridItensMarcacaoOriginal(document, request, response, integranteViagemDTO);
	}
	
	/**
	 * Carrega informações da popup de item de despesa
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carregaPopup(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		this.preencherComboTipoDespesa(document, request, response);
		this.preencherComboMoeda(document, request, response);
		this.preencherComboFormaPagamento(document, request, response);
	}
	
	/**
	 * Carrega a grid com o historico de itens de despesa de remarcações do integrante
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param integranteViagemDTO
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void atualizaGridItensControleHist(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, IntegranteViagemDTO integranteViagemDTO) throws ServiceException, Exception{		
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		MoedaDTO moedaDTO = new MoedaDTO();
		
		ParceiroService parceiroService = (ParceiroService) ServiceLocator.getInstance().getService(ParceiroService.class, null);
		ParceiroDTO parceiroDTO = new ParceiroDTO();
		
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
		
		TipoMovimFinanceiraViagemService tipoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		TipoMovimFinanceiraViagemDTO movimFinanceiraViagemDTO = new TipoMovimFinanceiraViagemDTO();
		
		DespesaViagemDTO despesaViagemDTO = new DespesaViagemDTO();
		Collection<DespesaViagemDTO> colDespesaViagemDTOs = new ArrayList<DespesaViagemDTO>();
		Collection<DespesaViagemDTO> colDespesaViagemDtoAux = new ArrayList<DespesaViagemDTO>();

		
		Collection<RoteiroViagemDTO> colRoteiros = roteiroViagemService.findByIdIntegranteTodos(integranteViagemDTO.getIdIntegranteViagem());	
		if(colRoteiros != null && !colRoteiros.isEmpty()){
			for(RoteiroViagemDTO roteiroViagemDTO: colRoteiros){
				colDespesaViagemDtoAux = despesaViagemService.findDespesaViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());
				
				if(colDespesaViagemDtoAux != null && colDespesaViagemDtoAux.size() > 0){
					for(DespesaViagemDTO dto: colDespesaViagemDtoAux ){
						if(dto != null && !dto.getOriginal().equalsIgnoreCase("S")){
							moedaDTO.setIdMoeda(dto.getIdMoeda());
							moedaDTO = (MoedaDTO) moedaService.restore(moedaDTO);
							
							formaPagamentoDTO.setIdFormaPagamento(dto.getIdFormaPagamento());
							formaPagamentoDTO = (FormaPagamentoDTO) formaPagamentoService.restore(formaPagamentoDTO);
							
							movimFinanceiraViagemDTO.setIdtipoMovimFinanceiraViagem(dto.getIdTipo());
							movimFinanceiraViagemDTO = (TipoMovimFinanceiraViagemDTO) tipoService.restore(movimFinanceiraViagemDTO);
							
							parceiroDTO.setIdParceiro(dto.getIdFornecedor());
							parceiroDTO = (ParceiroDTO) parceiroService.restore(parceiroDTO);
							
							dto.setTipoDespesa(movimFinanceiraViagemDTO.getClassificacao().trim() + " (" + (movimFinanceiraViagemDTO.getNome()).trim() + ")");
							dto.setNomeFornecedor(parceiroDTO.getRazaoSocial()+"  -  "+parceiroDTO.getNome());
							dto.setNomeFormaPagamento(formaPagamentoDTO.getNomeFormaPagamento());
							dto.setNomeMoeda(moedaDTO.getNomeMoeda());
							dto.setValorTotal(dto.getValor() * dto.getQuantidade());
							
							colDespesaViagemDTOs.add(dto);
						}
					}
				}
			}
		}
		
		if(colDespesaViagemDTOs != null && !colDespesaViagemDTOs.isEmpty()){
			HTMLTable tblDespesaHist;
			tblDespesaHist = document.getTableById("tblDespesaHist");
			tblDespesaHist.deleteAllRows();	
			tblDespesaHist.addRowsByCollection(colDespesaViagemDTOs,  new String[]{"dataInicio","tipoDespesa", "nomeFornecedor", "quantidade", "valorTotal", "nomeFormaPagamento", "nomeMoeda"}, null , null, null, null, null);
		}
 	}	
	
	/**
	 * Carrega a grid de itens de despesa originais do integrante
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param integranteViagemDTO
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void atualizaGridItensMarcacaoOriginal(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, IntegranteViagemDTO integranteViagemDTO) throws ServiceException, Exception{		
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		MoedaDTO moedaDTO = new MoedaDTO();
		
		ParceiroService parceiroService = (ParceiroService) ServiceLocator.getInstance().getService(ParceiroService.class, null);
		ParceiroDTO parceiroDTO = new ParceiroDTO();
		
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();

		
		TipoMovimFinanceiraViagemService tipoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		TipoMovimFinanceiraViagemDTO movimFinanceiraViagemDTO = new TipoMovimFinanceiraViagemDTO();
		
		DespesaViagemDTO despesaViagemDTO = new DespesaViagemDTO();
		Collection<DespesaViagemDTO> colDespesaViagemDTOs = new ArrayList<DespesaViagemDTO>();
		Collection<DespesaViagemDTO> colDespesaViagemDtoAux = new ArrayList<DespesaViagemDTO>();
		
		Collection<RoteiroViagemDTO> colRoteiros = roteiroViagemService.findByIdIntegranteTodos(integranteViagemDTO.getIdIntegranteViagem());	
		if(colRoteiros != null && !colRoteiros.isEmpty()){
			for(RoteiroViagemDTO roteiroViagemDTO: colRoteiros){
				colDespesaViagemDtoAux = despesaViagemService.findDespesaViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());
				
				if(colDespesaViagemDtoAux != null && colDespesaViagemDtoAux.size() > 0){
					for(DespesaViagemDTO dto: colDespesaViagemDtoAux ){
						if(dto != null && dto.getOriginal().equalsIgnoreCase("S")){
							moedaDTO.setIdMoeda(dto.getIdMoeda());
							moedaDTO = (MoedaDTO) moedaService.restore(moedaDTO);
							
							formaPagamentoDTO.setIdFormaPagamento(dto.getIdFormaPagamento());
							formaPagamentoDTO = (FormaPagamentoDTO) formaPagamentoService.restore(formaPagamentoDTO);
							
							movimFinanceiraViagemDTO.setIdtipoMovimFinanceiraViagem(dto.getIdTipo());
							movimFinanceiraViagemDTO = (TipoMovimFinanceiraViagemDTO) tipoService.restore(movimFinanceiraViagemDTO);
							
							parceiroDTO.setIdParceiro(dto.getIdFornecedor());
							parceiroDTO = (ParceiroDTO) parceiroService.restore(parceiroDTO);
							
							dto.setTipoDespesa(movimFinanceiraViagemDTO.getClassificacao().trim() + " (" + (movimFinanceiraViagemDTO.getNome()).trim() + ")");
							dto.setNomeFornecedor(parceiroDTO.getRazaoSocial()+"  -  "+parceiroDTO.getNome());
							dto.setNomeFormaPagamento(formaPagamentoDTO.getNomeFormaPagamento());
							dto.setNomeMoeda(moedaDTO.getNomeMoeda());
							dto.setValorTotal(dto.getValor() * dto.getQuantidade());
							
							colDespesaViagemDTOs.add(dto);
						}
					}
				}
			}
		}
		
		if(colDespesaViagemDTOs != null && !colDespesaViagemDTOs.isEmpty()){
			HTMLTable tblItemDespesaOriginal;
			tblItemDespesaOriginal = document.getTableById("tblItemDespesaOriginal");
			tblItemDespesaOriginal.deleteAllRows();	
			tblItemDespesaOriginal.addRowsByCollection(colDespesaViagemDTOs,  new String[]{"dataInicio","tipoDespesa", "nomeFornecedor", "quantidade", "valorTotal", "nomeFormaPagamento", "nomeMoeda"}, null , null, null, null, null);
		}
 	}
	
	/**
	 * Preenche combo de 'Tipo de despesa'.
	 * 
	 * @p{			ram document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void preencherComboTipoDespesa(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		TipoMovimFinanceiraViagemService tipoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		
		String classificacao = "";
		List<TipoMovimFinanceiraViagemDTO> listaTipoMovimentacaoFinanceiraViagem = new ArrayList<TipoMovimFinanceiraViagemDTO>();
		
		HTMLSelect tipoDespesaSelect = document.getSelectById("tipoDespesa");
		
		tipoDespesaSelect.removeAllOptions();
		tipoDespesaSelect.addOption("", "" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "");
		
		for(Enumerados.ClassificacaoMovFinViagem classificacaoItem : Enumerados.ClassificacaoMovFinViagem.values()){
			classificacao = UtilStrings.removeCaracteresEspeciais(classificacaoItem.getDescricao());
			
			listaTipoMovimentacaoFinanceiraViagem =  tipoService.listByClassificacao(classificacao);
			
			for(TipoMovimFinanceiraViagemDTO tipoMov : listaTipoMovimentacaoFinanceiraViagem) {
				tipoDespesaSelect.addOption(tipoMov.getIdtipoMovimFinanceiraViagem().toString(), classificacao.trim() + " (" + (tipoMov.getNome()).trim() + ")");
			}
		}
	}
	
	/**
	 * Preenche combo de 'Moeda'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void preencherComboMoeda(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
        HTMLSelect Moeda = (HTMLSelect) document.getSelectById("moeda");
        
        Moeda.removeAllOptions();
        Moeda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        
        Collection colMoedas = moedaService.findAllAtivos();
        if(colMoedas != null && !colMoedas.isEmpty()){
        	Moeda.addOptions(colMoedas, "idMoeda", "nomeMoeda", null);
        }
        
        document.getElementById("idMoeda").setValue("1");
        document.getElementById("nomeMoeda").setValue("Real");
        Moeda.setValue("1");
        Moeda.setDisabled(true);
	}
	
	/**
	 * Preenche combo de 'Forma de pagamento'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thiago.borges
	 */
	public void preencherComboFormaPagamento(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		
		HTMLSelect idFormaPagamentoAux = document.getSelectById("idFormaPagamento");
		
		idFormaPagamentoAux.removeAllOptions();
		idFormaPagamentoAux.addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		Collection colTipoMovimentacaoFinanceira = formaPagamentoService.list();
		if(colTipoMovimentacaoFinanceira!=null){
			document.getSelectById("idFormaPagamento").addOptions(colTipoMovimentacaoFinanceira, "idFormaPagamento", "nomeFormaPagamento", null);
		}
	}
	
	/**
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa
	 * 
	 * Faz o tratamento do tipo da movimentação financeira.
	 * Se a Classificação for igual a diária então o adiantamento = valorUnitário * (quantidade + 1), se a classificação for qualquer outro diferente
	 * então adiantamento = valorUnitário * diária
	 * O tratamento para o adiantamento é feito , ele calcula e seta o valor na tela automaticamente.
	 * Esse método também faz o tratamento para casas decimais 
	 * 
	 */
	public void tratarValoresTipoMovimentacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws ServiceException,Exception{
		IntegranteViagemDTO integranteViagemDTO = (IntegranteViagemDTO) document.getBean();
		
		if(integranteViagemDTO.getIdTipo() != null && !integranteViagemDTO.getIdTipo().equals("")) {
			TipoMovimFinanceiraViagemService tipoMovimentacaoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
			
			TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraDto = new TipoMovimFinanceiraViagemDTO();
			
			tipoMovimFinanceiraDto.setIdtipoMovimFinanceiraViagem(integranteViagemDTO.getIdTipo());
			tipoMovimFinanceiraDto = (TipoMovimFinanceiraViagemDTO) tipoMovimentacaoService.restore(tipoMovimFinanceiraDto);
			
			if(tipoMovimFinanceiraDto != null) {
				String classificacao = "";
				
				classificacao = UtilStrings.removeCaracteresEspeciais(tipoMovimFinanceiraDto.getClassificacao());
				
				if(integranteViagemDTO.getValor() == null && tipoMovimFinanceiraDto.getValorPadrao() != null) {
					NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
					DecimalFormat decimalFormat = (DecimalFormat) nf;
					decimalFormat.applyPattern("#,##0.00");
					String valorUnit = decimalFormat.format(tipoMovimFinanceiraDto.getValorPadrao());
					integranteViagemDTO.setValor(tipoMovimFinanceiraDto.getValorPadrao());
					document.getSelectById("valor").setValue(valorUnit);
				} else {
					document.getSelectById("valor").setValue("");
				}
				
				if(classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Diaria.toString())) {
					RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
					RequisicaoViagemService requisicaoService =  (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
					
					requisicaoViagemDto.setIdSolicitacaoServico(integranteViagemDTO.getIdSolicitacao());
					
					requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoService.restore(requisicaoViagemDto);
				} 
				
				document.getElementById("valorAdiantamento").setValue(integranteViagemDTO.getTotalFormatado());
				
				if(tipoMovimFinanceiraDto.getExigeDataHoraCotacao().equalsIgnoreCase("S")) {
					document.executeScript("$('#labelPrazoCotacao, #labelHoraCotacao').addClass('campoObrigatorio');");
					document.getElementById("prazoCotacao").setDisabled(false);
					document.getElementById("horaCotacao").setDisabled(false);
				} else {
					document.executeScript("$('#labelPrazoCotacao, #labelHoraCotacao').removeClass('campoObrigatorio');");
					document.getElementById("prazoCotacao").setValue("");
					document.getElementById("horaCotacao").setValue("");
					document.getElementById("prazoCotacao").setDisabled(true);
					document.getElementById("horaCotacao").setDisabled(true);
				}
			}
		} else {
			document.getElementById("quantidade").setValue("");
			document.getElementById("valor").setValue("");
			document.getElementById("valorAdiantamento").setValue(integranteViagemDTO.getTotalFormatado());
		}
	}
	
	/**
	 * Realiza o calculo da quantidade vezes o valor e retorna para a tela do usuario
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void calcularTotal(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws ServiceException,Exception{
		IntegranteViagemDTO integranteViagemDTO = (IntegranteViagemDTO) document.getBean();
		
		document.getElementById("valorAdiantamento").setValue(integranteViagemDTO.getTotalFormatado());
	}
	
}