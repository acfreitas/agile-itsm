package br.com.centralit.citcorpore.ajaxForms;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.DespesaViagemService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.IntegranteViagemService;
import br.com.centralit.citcorpore.negocio.ItemPrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.PrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unchecked" })
public class PrestacaoContasViagem extends AjaxFormAction {
	
	public Class getBeanClass() {
		return PrestacaoContasViagemDTO.class;
	}

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		RequisicaoViagemService requisicaoViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		
		PrestacaoContasViagemDTO prestacaoContasViagemDTO = (PrestacaoContasViagemDTO) document.getBean();
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		
		requisicaoViagemDTO.setIdSolicitacaoServico(prestacaoContasViagemDTO.getIdSolicitacaoServico());
		requisicaoViagemDTO = (RequisicaoViagemDTO) requisicaoViagemService.restore(requisicaoViagemDTO);
		
		if(requisicaoViagemDTO != null){
			requisicaoViagemDTO.setIdTarefa(prestacaoContasViagemDTO.getIdTarefa());
			
			prestacaoContasViagemDTO.setIntegranteViagemDto(integranteViagemService.getIntegranteByIdSolicitacaoAndTarefa(prestacaoContasViagemDTO.getIdSolicitacaoServico(), prestacaoContasViagemDTO.getIdTarefa()));
		}
		
		DecimalFormat decimal = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		decimal.applyPattern("#,##0.00");
		Double totalPrestacaoContas = 0.0;
		
		if(prestacaoContasViagemDTO.getIntegranteViagemDto() != null) {
			totalPrestacaoContas = despesaViagemService.getTotalDespesaViagemPrestacaoContas(prestacaoContasViagemDTO.getIdSolicitacaoServico(), prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado());
		}
		
		prestacaoContasViagemDTO.setValorDiferenca(totalPrestacaoContas);
		prestacaoContasViagemDTO.setTotalLancamentos(0.0);
		prestacaoContasViagemDTO.setTotalPrestacaoContas(totalPrestacaoContas);
		
		if(prestacaoContasViagemDTO.getIntegranteViagemDto() != null){
			RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
			CidadesDTO origem = null;
			CidadesDTO destino = null;
			
			RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
			CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
			
			StringBuilder textoNomeIntegranteViagem = new StringBuilder();
			
			roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdIntegranteViagem());
			origem = (CidadesDTO) ((List) cidadesService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
			destino = (CidadesDTO) ((List) cidadesService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);
			
			// Verifica se o funcionario tem um responsavel pela prestacao de contas
			if(!prestacaoContasViagemDTO.getIntegranteViagemDto().getIdRespPrestacaoContas().equals(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado())) {
				EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
				
				EmpregadoDTO empregadoDTO = new EmpregadoDTO();
				empregadoDTO.setIdEmpregado(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdRespPrestacaoContas());
				empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
				
				textoNomeIntegranteViagem.append(empregadoDTO.getNome() + " em nome de " + prestacaoContasViagemDTO.getIntegranteViagemDto().getNome());
			} else {
				textoNomeIntegranteViagem.append(prestacaoContasViagemDTO.getIntegranteViagemDto().getNome());
			}
			
			textoNomeIntegranteViagem.append(" - ida " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()));
			textoNomeIntegranteViagem.append(" - volta " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()));
			textoNomeIntegranteViagem.append(" - " + origem.getNomeCidade() + "/" + origem.getNomeUf());
			textoNomeIntegranteViagem.append(" - " + destino.getNomeCidade() + "/" + destino.getNomeUf());
			
			document.getElementById("nomeIntegranteViagem").setInnerHTML(textoNomeIntegranteViagem.toString());
			
			prestacaoContasViagemDTO.setIdEmpregado(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado());
			prestacaoContasViagemDTO.setIdResponsavel(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdRespPrestacaoContas());
			prestacaoContasViagemDTO.setIdIntegrante(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdIntegranteViagem());
			
			Integer idPrestacaoContasViagem = prestacaoContasViagemService.recuperaIdPrestacaoSeExistir(prestacaoContasViagemDTO.getIdSolicitacaoServico(), prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado());
			prestacaoContasViagemDTO.setIdPrestacaoContasViagem(idPrestacaoContasViagem);
		}
		
		if(prestacaoContasViagemDTO.getIdPrestacaoContasViagem() != null) {
			this.restore(document, request, response, prestacaoContasViagemDTO);
		} else {
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(prestacaoContasViagemDTO);
		}
		
//		document.getElementById("valorDiferencaAux").setValue(decimal.format(prestacaoContasViagemDTO.getValorDiferenca()));
//		document.getElementById("valorDiferenca").setValue(prestacaoContasViagemDTO.getValorDiferenca().toString());
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param prestacaoContasViagemDto 
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDTO) throws Exception {
		ItemPrestacaoContasViagemService itemPrestacaoContasViagemService = (ItemPrestacaoContasViagemService) ServiceLocator.getInstance().getService(ItemPrestacaoContasViagemService.class, null);
		
		Collection<ItemPrestacaoContasViagemDTO> colItemPrestacaoContasViagemDTO = itemPrestacaoContasViagemService.recuperaItensPrestacao(prestacaoContasViagemDTO);
		
		if(colItemPrestacaoContasViagemDTO != null && !colItemPrestacaoContasViagemDTO.isEmpty()) {
			Double totalLancamentos = 0.0;
			
			for(ItemPrestacaoContasViagemDTO itemPrestacaoContasViagemDTO : colItemPrestacaoContasViagemDTO) {
				totalLancamentos += itemPrestacaoContasViagemDTO.getValor();
			}
			
			prestacaoContasViagemDTO.setTotalLancamentos(totalLancamentos);
			prestacaoContasViagemDTO.setValorDiferenca(prestacaoContasViagemDTO.getTotalPrestacaoContas() - totalLancamentos);
			prestacaoContasViagemDTO.setIdIntegrante(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdIntegranteViagem());
			
			HTMLTable tabelaItemPrestacaoContasViagem = document.getTableById("tabelaItemPrestacaoContasViagem");
			
			tabelaItemPrestacaoContasViagem.deleteAllRows();
			tabelaItemPrestacaoContasViagem.addRowsByCollection(
					colItemPrestacaoContasViagemDTO, 
					new String[] {"numeroDocumento", "data", "nomeFornecedor", "valor", "descricao", ""}, 
					new String[] {"numeroDocumento"}, 
					UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaAdicionado"), 
					new String[] {"gerarButtonsTable"}, 
					null, 
					null);
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(prestacaoContasViagemDTO);
	}
	
	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		
		prestacaoContasViagemDto.setListaItemPrestacaoContasViagemDTO((ArrayList<ItemPrestacaoContasViagemDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemPrestacaoContasViagemDTO.class, "listItens", request));

		if (prestacaoContasViagemDto.getIdPrestacaoContasViagem() == null || prestacaoContasViagemDto.getIdPrestacaoContasViagem().intValue() == 0) {
			prestacaoContasViagemService.create(prestacaoContasViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			prestacaoContasViagemService.update(prestacaoContasViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.executeScript("deleteAllRows()");
	}
	
	/**
	 * Calcula e atualiza os valores dos campos "Total Prestação de Contas", "Total Lançamentos" e "Valor Diferença"
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void atualizarValores(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		
		Double valor = 0.0;
		
		DecimalFormat decimal = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		decimal.applyPattern("#,##0.00");
		
		// Verifica se é para retirar ou acrescentar
		if(request.getParameter("retirarValor").equals("S")) {
			valor = -prestacaoContasViagemDto.getValor();
			
			document.getElementById("retirarValor").setValue("");
			document.getElementById("valor").setValue("");
		} else {
			valor = prestacaoContasViagemDto.getValor();
		}
		
		Double totalLancamentos = prestacaoContasViagemDto.getTotalLancamentos() + valor;
		Double valorDiferenca = prestacaoContasViagemDto.getValorDiferenca() - valor;
		
		prestacaoContasViagemDto.setTotalLancamentos(totalLancamentos);
		prestacaoContasViagemDto.setValorDiferenca(valorDiferenca);
		prestacaoContasViagemDto.setValor(null);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(prestacaoContasViagemDto);
		
		document.executeScript("limparCamposFormulario();");
	}
	
	/**
	 * Adiciona o item a grid de itens de prestação de contas e verifica se a nota fiscal foi emitida há mais de 3 meses.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void adicionarItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		
		RoteiroViagemDTO roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(prestacaoContasViagemDto.getIdIntegrante());
		
		if(roteiroViagemDTO != null && prestacaoContasViagemDto.getData() != null && !prestacaoContasViagemDto.getData().equals("")){
			Date dataViagem = UtilDatas.alteraData(roteiroViagemDTO.getIda(), -3, Calendar.MONTH ) ;
			Date dataPrestacao = UtilDatas.convertStringToDate(TipoDate.FORMAT_DATABASE , prestacaoContasViagemDto.getData().toString(), UtilI18N.getLocale());
			
			if(dataViagem.compareTo(dataPrestacao) > 0){
				document.alert("Nota fiscal emitida há mais de 3 meses!");
				document.getElementById("data").setValue("");
				document.getElementById("data").setFocus();
				return;
			}
		}
		document.executeScript("adicionarItem()");
	}
}