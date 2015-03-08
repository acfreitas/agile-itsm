package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoModuloSistemaDTO;
import br.com.centralit.citcorpore.bean.ControleContratoOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ControleContratoPagamentoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoTreinamentoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoVersaoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ModuloSistemaDTO;
import br.com.centralit.citcorpore.bean.TipoSubscricaoDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ControleContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ModuloSistemaService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoSubscricaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author pedro
 *
 */
@SuppressWarnings({"unused","unchecked"})
public class ControleContrato extends AjaxFormAction {
	
	public Class<ControleContratoDTO> getBeanClass() {
		return ControleContratoDTO.class;
	}
	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModuloSistemaService moduloSistemaService = (ModuloSistemaService) ServiceLocator.getInstance().getService(ModuloSistemaService.class,  WebUtil.getUsuarioSistema(request));
		TipoSubscricaoService tipoSubscricaoService = (TipoSubscricaoService) ServiceLocator.getInstance().getService(TipoSubscricaoService.class, null);

		/************* CARREGA OS MODULOS DO SISTEMA **************/
		
		Collection<ModuloSistemaDTO> colModulos = moduloSistemaService.list();
		if(colModulos != null && colModulos.size() >0){
		HTMLTable resultado = document.getTableById("tblModulosAtivos");
			if (colModulos != null && !colModulos.isEmpty() && colModulos.size() > 0) {
				resultado.addRowsByCollection(colModulos, new String[] {"", "nomeModuloSistema"}, null, "", new String[] {"geraCheckButt"}, null, null);	
			} 
		}
		
		/************* CARREGA OS TIPOS DE SUBSCRICAO **************/
		HTMLSelect comboSubscricao = (HTMLSelect) document.getSelectById("tipoSubscricao");
		ArrayList<TipoSubscricaoDTO> tipos = (ArrayList) tipoSubscricaoService.list();

		if(tipos != null && tipos.size() >0){
			for (TipoSubscricaoDTO tipo : tipos){
				comboSubscricao.addOption(tipo.getIdTipoSubscricao().toString(), tipo.getNomeTipoSubscricao());
			}
		} 
	}
	
	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ControleContratoDTO controleContratoDTO = (ControleContratoDTO) document.getBean();
		ControleContratoService controleContratoService = (ControleContratoService) ServiceLocator.getInstance().getService(ControleContratoService.class, WebUtil.getUsuarioSistema(request));
		
		Collection<ControleContratoPagamentoDTO> colPagamento = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ControleContratoPagamentoDTO.class, "pagamentoSerialize", request);
		Collection<ControleContratoTreinamentoDTO> colTreinamento = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ControleContratoTreinamentoDTO.class, "treinamentoSerialize", request);
		Collection<ControleContratoOcorrenciaDTO> colOcorrencia = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ControleContratoOcorrenciaDTO.class, "ocorrenciaSerialize", request);
		Collection<ControleContratoVersaoDTO> colVersao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ControleContratoVersaoDTO.class, "versaoSerialize", request);
			
		if(colPagamento != null){
			controleContratoDTO.setLstPagamento((List<ControleContratoPagamentoDTO>) colPagamento);
		}
		if(colTreinamento != null){
			controleContratoDTO.setLstTreinamento((List<ControleContratoTreinamentoDTO>) colTreinamento);
		}
		if(colOcorrencia != null){
			controleContratoDTO.setLstOcorrencia((List<ControleContratoOcorrenciaDTO>) colOcorrencia);
		}
		if(colVersao != null){
			controleContratoDTO.setLstVersao((List<ControleContratoVersaoDTO>) colVersao);
		}
			
		List listAux = new ArrayList<String>();	
		if(!controleContratoDTO.getLstModulos().equals("")){
			String[] colAux = controleContratoDTO.getLstModulos().split(";");
			if(colAux != null){
				for(int i=0; i< colAux.length; i++){
					listAux.add(colAux[i]);	
				}
			}
			controleContratoDTO.setLstModulosAtivos(listAux);
		}
	
		if (controleContratoDTO.getIdControleContrato() == null || controleContratoDTO.getIdControleContrato().intValue() == 0) {
			controleContratoDTO.setDataInicio(UtilDatas.getDataAtual());
			/*if(catalogoServicoService.verificaSeCatalogoExiste(catalogoServicoDTO)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}*/
			controleContratoService.create(controleContratoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			/*if(catalogoServicoService.verificaSeCatalogoExiste(catalogoServicoDTO)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}*/
				controleContratoService.update(controleContratoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		document.executeScript("deleteAllRows();");
		HTMLForm form = document.getForm("form");
		form.clear();
	//	document.executeScript("limpar()");
	//	document.executeScript("limpar_LOOKUP_CATALOGOSERVICO()");
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * @author pedro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ControleContratoDTO controleContratoDTO = (ControleContratoDTO) document.getBean();
		ControleContratoService catalogoServicoService = (ControleContratoService) ServiceLocator.getInstance().getService(ControleContratoService.class, WebUtil.getUsuarioSistema(request));
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, WebUtil.getUsuarioSistema(request));
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		FornecedorService fornService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class,  WebUtil.getUsuarioSistema(request));
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class,  WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		controleContratoDTO = (ControleContratoDTO) catalogoServicoService.restore(controleContratoDTO);
	
		//Recupera nome do contrato
		if(controleContratoDTO != null && controleContratoDTO.getIdContrato() != null){
			ContratoDTO contratosDTO = new ContratoDTO();
			FornecedorDTO fornecedorDTO = new FornecedorDTO();
			
			ClienteDTO clienteDTO = new ClienteDTO();
			if(controleContratoDTO != null){
				contratosDTO.setIdContrato(controleContratoDTO.getIdContrato());
				contratosDTO = (ContratoDTO) contratoService.restore(contratosDTO);
			}
			if(contratosDTO != null){
				clienteDTO.setIdCliente(contratosDTO.getIdCliente());
				clienteDTO = (ClienteDTO) clienteService.restore(clienteDTO);
			}
			if(contratosDTO != null){
				fornecedorDTO.setIdFornecedor(contratosDTO.getIdFornecedor());
				fornecedorDTO = (FornecedorDTO) fornService.restore(fornecedorDTO);
			}
			if(contratosDTO != null && contratosDTO.getIdContrato() != null){
			document.executeScript("document.form.idContrato.value= "+contratosDTO.getIdContrato()+"");
			}
//	/		document.executeScript("document.formServicoContrato.pesqLockupLOOKUP_CATALOGOSERVICOCONTRATO_IDCONTRATO.value= "+contratosDTO.getIdContrato()+"");
			
			if(contratosDTO != null && clienteDTO != null && fornecedorDTO != null){
				controleContratoDTO.setNomeContrato(contratosDTO.getNumero() +" - " +clienteDTO.getNomeFantasia()+" - " +fornecedorDTO.getRazaoSocial());
			}
		}
	document.executeScript("deleteAllRows();");
		
		if(controleContratoDTO != null && controleContratoDTO.getLstVersao() != null){
			for(ControleContratoVersaoDTO versoes : (List<ControleContratoVersaoDTO>)controleContratoDTO.getLstVersao()){
				if(versoes != null){
					/*controleContratoDTO.setNomeVersao(versoes.getNomeCcVersao());
					controleContratoDTO.setIdVersao(versoes.getIdCcVersao());*/
					HTMLTable table;
					table = document.getTableById("tblVersao");
					table.deleteAllRows();
					table.addRowsByCollection(controleContratoDTO.getLstVersao(), new String[] {"", "nomeCcVersao"}, null, null, new String[] {"gerarButtonDeleteVersao"}, "funcaoClickRowVersao", null);
				}
			}
		}
		if(controleContratoDTO != null && controleContratoDTO.getLstPagamento() != null){
			
			for (ControleContratoPagamentoDTO pagamento : (List<ControleContratoPagamentoDTO>)controleContratoDTO.getLstPagamento() ){
				if(pagamento != null){
			
					HTMLTable table;
					table = document.getTableById("tblPagamento");
					table.deleteAllRows();
					table.addRowsByCollection(controleContratoDTO.getLstPagamento(), new String[] {"", "dataCcPagamento", "parcelaCcPagamento", "dataAtrasoCcPagamento"}, null, null, new String[] {"gerarButtonDeletePagamento"}, "funcaoClickRowPagamento", null);
				}
			}
		}
		if(controleContratoDTO != null && controleContratoDTO.getLstTreinamento() != null){
			
			for(ControleContratoTreinamentoDTO treinamento : (List<ControleContratoTreinamentoDTO>) controleContratoDTO.getLstTreinamento()){
				if(treinamento != null){
					
					if(treinamento.getIdEmpregadoTreinamento() != null){
						EmpregadoDTO dto = new EmpregadoDTO();
						dto.setIdEmpregado(treinamento.getIdEmpregadoTreinamento());
						dto = (EmpregadoDTO) empregadoService.restore(dto);
						treinamento.setNomeInstrutorCcTreinamento(dto.getNome());
						treinamento.setIdEmpregadoTreinamento(dto.getIdEmpregado());
					}
					
					
					HTMLTable table;
					table = document.getTableById("tblTreinamento");
					table.deleteAllRows();
					table.addRowsByCollection(controleContratoDTO.getLstTreinamento(), new String[] {"", "dataCcTreinamento", "nomeCcTreinamento", "nomeInstrutorCcTreinamento"}, null, null, new String[] {"gerarButtonDeleteTreinamento"}, "funcaoClickRowTreinamento", null);
				}
			}
		}
		if(controleContratoDTO != null && controleContratoDTO.getLstOcorrencia() != null){
			
			for (ControleContratoOcorrenciaDTO ocorrencia : (List<ControleContratoOcorrenciaDTO>) controleContratoDTO.getLstOcorrencia() ){
				
				if(ocorrencia != null){
					if(ocorrencia.getIdEmpregadoOcorrencia() != null){
						EmpregadoDTO dto = new EmpregadoDTO();
						dto.setIdEmpregado(ocorrencia.getIdEmpregadoOcorrencia());
						dto = (EmpregadoDTO) empregadoService.restore(dto);
						ocorrencia.setEmpregadoCcOcorrencia(dto.getNome());
						controleContratoDTO.setIdUsuarioOcorrencia(dto.getIdEmpregado());
					}
					
					HTMLTable table;
					table = document.getTableById("tblOcorrencia");
					table.deleteAllRows();
					table.addRowsByCollection(controleContratoDTO.getLstOcorrencia(), new String[] {"", "dataCcOcorrencia", "assuntoCcOcorrencia", "empregadoCcOcorrencia"}, null, null, new String[] {"gerarButtonDeleteOcorrencia"}, "funcaoClickRowOcorrencia", null);
				}
			}
		}
		
		/** CARREGA LISTA DE INCDENTES  **/
		/*Collection colIncidentesContrato = solicitacaoServicoService.incidentesPorContrato(controleContratoDTO.getIdContrato());
		
		if(colIncidentesContrato != null){
			
			HTMLTable table;
			table = document.getTableById("tblIncidente");
			table.deleteAllRows();
			table.addRowsByCollection(colIncidentesContrato, new String[] {"", "idSolicitacaoServico", "nomeServico", "numero","nome","nomeUsu"}, null, null, null, null, null);
		}*/
 		
		HTMLForm form = document.getForm("form");
		form.clear();
		
		form.setValues(controleContratoDTO);
			if(controleContratoDTO != null && controleContratoDTO.getLstModulosAtivos() != null){
			
			for (ControleContratoModuloSistemaDTO modulos : (List<ControleContratoModuloSistemaDTO>)controleContratoDTO.getLstModulosAtivos()) {
				String checked = "moduloAtivo"+modulos.getIdModuloSistema();
				document.getCheckboxById(checked).setChecked(true);
			}
			
		

		}
			document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ControleContratoDTO controleContratoDTO = (ControleContratoDTO) document.getBean();
		ControleContratoService controleContratoService = (ControleContratoService) ServiceLocator.getInstance().getService(ControleContratoService.class, WebUtil.getUsuarioSistema(request));
			
			if(controleContratoDTO.getIdControleContrato() != null){
				controleContratoDTO.setDataFim(UtilDatas.getDataAtual());
				controleContratoService.update(controleContratoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}else{
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.nome"));
				//document.alert("Selecione um catalogo!");
			}
		document.executeScript("deleteAllRows();");
		HTMLForm form = document.getForm("form");
		form.clear();

	}
	
	
	
/*	public void restoreInfo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		InfoCatalogoServicoService infoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, WebUtil.getUsuarioSistema(request));
		
		InfoCatalogoServicoDTO infoCatalogoServicoDTO = new InfoCatalogoServicoDTO();
		
			if(catalogoServicoDTO.getIdInfoCatalogoServico() != null){
				infoCatalogoServicoDTO.setIdInfoCatalogoServico(catalogoServicoDTO.getIdInfoCatalogoServico());
				infoCatalogoServicoDTO.setIdCatalogoServico(catalogoServicoDTO.getIdCatalogoServico());
				infoCatalogoServicoDTO = (InfoCatalogoServicoDTO) infoService.restore(infoCatalogoServicoDTO);
				
				Integer id = infoCatalogoServicoDTO.getIdInfoCatalogoServico();
				String text = UtilStrings.nullToVazio(infoCatalogoServicoDTO.getDescInfoCatalogoServico());
				String nome = infoCatalogoServicoDTO.getNomeInfoCatalogoServico();
				Integer index = catalogoServicoDTO.getRowIndex();
					if(infoCatalogoServicoDTO != null){
					
						document.executeScript("setaInfo('" + nome + "', '" + text + "', '"+index+"');");
					}
			}
		
	}*/
	
	/**
	 * Adiciona/atualiza grid servico.
	 * @author pedro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
/*	public void adicionaGridServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		ServicoService servicoContratoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		ServicoDTO bean = new ServicoDTO();
		ServContratoCatalogoServDTO servContratoCatalogoServDTO = new ServContratoCatalogoServDTO();
		bean.setIdServico(catalogoServicoDTO.getIdServicoContrato());
		bean = (ServicoDTO) servicoContratoService.restore(bean);
		
		servContratoCatalogoServDTO.setIdServicoContrato(bean.getIdServico());
		servContratoCatalogoServDTO.setNomeServico(bean.getNomeServico());		

		HTMLTable tblServico = document.getTableById("tblServicoContrato");

		tblServico.addRow(servContratoCatalogoServDTO, new String[] {"", "idServicoContrato", "nomeServico" }, new String[] {"idServicoContrato"}, "Serviço já selecionado!", new String[] {"gerarButtonDelete"}, null, null);
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblServicoContrato', 'tblServicoContrato');");
	}*/
	
	/**
	 * Retorna o Conteudo de catálogo de serviço
	 * @author pedro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
/*	public void conteudoInfoCatalogoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		InfoCatalogoServicoService infoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, WebUtil.getUsuarioSistema(request));
		
		InfoCatalogoServicoDTO infoCatalogoServicoDTO = new InfoCatalogoServicoDTO();		
		if(catalogoServicoDTO.getIdInfoCatalogoServico() != null){
			infoCatalogoServicoDTO.setIdInfoCatalogoServico(catalogoServicoDTO.getIdInfoCatalogoServico());
			infoCatalogoServicoDTO = (InfoCatalogoServicoDTO) infoService.restore(infoCatalogoServicoDTO);
			document.executeScript("$('#tituloCatalogo').text('"+infoCatalogoServicoDTO.getNomeInfoCatalogoServico()+"');" +
					"$('#POPUP_CONTEUDOCATALOGO').dialog('open');");
			HTMLElement m = document.getElementById("conteudoCatalogo");
			m.setInnerHTML(infoCatalogoServicoDTO.getDescInfoCatalogoServico());
		}
	}*/
	


}
