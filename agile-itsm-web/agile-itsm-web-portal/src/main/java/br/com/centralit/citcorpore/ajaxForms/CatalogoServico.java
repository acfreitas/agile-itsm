package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ServContratoCatalogoServDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.CatalogoServicoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.InfoCatalogoServicoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * 
 * @author pedro
 * 
 */
@SuppressWarnings({ "unused", "unchecked" })
public class CatalogoServico extends AjaxFormAction {

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

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
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, WebUtil.getUsuarioSistema(request));

		/*	Collection<ServContratoCatalogoServDTO> colSerializeServicos = br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(ServContratoCatalogoServDTO.class, "servicoSerialize", request);
		if (colSerializeServicos != null) {

			if (colSerializeServicos != null) {

				catalogoServicoDTO.setColServicoContrato((List<ServContratoCatalogoServDTO>) colSerializeServicos);
			}

		} else {
			document.alert(String.format("%s: %s", UtilI18N.internacionaliza(request, "servico.servico"), UtilI18N.internacionaliza(request, "citcorpore.comum.campo_obrigatorio") ) );
			// document.alert("Selecione pelo menos 1 serviço!");
			return;
		}*/
		Collection<InfoCatalogoServicoDTO> colinfoCatServico = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(InfoCatalogoServicoDTO.class, "infoCatalogoServicoSerialize", request);
		//aspas simples e "\n" da erro nos serializes, este laço resolve o problema substituindo aspas simples e "\n"por
		//aspas duplas e espaço em branco, acontecia este problema na página: pages/portal2/portal2.load.
		if(colinfoCatServico != null){
			for (InfoCatalogoServicoDTO infoCatServ : colinfoCatServico ) {
				infoCatServ.setDescInfoCatalogoServico(infoCatServ.getDescInfoCatalogoServico().replaceAll("[\']","\""));
				infoCatServ.setDescInfoCatalogoServico(infoCatServ.getDescInfoCatalogoServico().replaceAll("[\\n]"," "));
			}
		}
		if (colinfoCatServico != null) {
			catalogoServicoDTO.setColInfoCatalogoServico((List<InfoCatalogoServicoDTO>) colinfoCatServico);
		}

		if (catalogoServicoDTO.getIdCatalogoServico() == null || catalogoServicoDTO.getIdCatalogoServico().intValue() == 0) {
			catalogoServicoDTO.setDataInicio(UtilDatas.getDataAtual());

			if (catalogoServicoService.verificaSeCatalogoExiste(catalogoServicoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}

			catalogoServicoService.create(catalogoServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			if (catalogoServicoService.verificaSeCatalogoExiste(catalogoServicoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}

			catalogoServicoService.update(catalogoServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		// form.clear();
		document.executeScript("limpar()");
		document.executeScript("limpar_LOOKUP_CATALOGOSERVICO()");
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @author pedro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, WebUtil.getUsuarioSistema(request));
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		FornecedorService fornService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, WebUtil.getUsuarioSistema(request));
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, WebUtil.getUsuarioSistema(request));
		catalogoServicoDTO = (CatalogoServicoDTO) catalogoServicoService.restore(catalogoServicoDTO);

		// Recupera nome do contrato
		if (catalogoServicoDTO != null && catalogoServicoDTO.getIdContrato() != null) {
			ContratoDTO contratosDTO = new ContratoDTO();
			FornecedorDTO fornecedorDTO = new FornecedorDTO();

			ClienteDTO clienteDTO = new ClienteDTO();
			if (catalogoServicoDTO != null) {
				contratosDTO.setIdContrato(catalogoServicoDTO.getIdContrato());
				contratosDTO = (ContratoDTO) contratoService.restore(contratosDTO);
			}
			if (contratosDTO != null) {
				clienteDTO.setIdCliente(contratosDTO.getIdCliente());
				clienteDTO = (ClienteDTO) clienteService.restore(clienteDTO);

				fornecedorDTO.setIdFornecedor(contratosDTO.getIdFornecedor());
				fornecedorDTO = (FornecedorDTO) fornService.restore(fornecedorDTO);
				document.executeScript("document.form.idContrato.value= " + contratosDTO.getIdContrato() + "");
				//document.executeScript("document.formServicoContrato.pesqLockupLOOKUP_CATALOGOSERVICOCONTRATO_IDCONTRATO.value= " + contratosDTO.getIdContrato() + "");

				catalogoServicoDTO.setNomeContrato(contratosDTO.getNumero() + " - " + clienteDTO.getNomeFantasia() + " - " + fornecedorDTO.getRazaoSocial());
			}
		}
		document.executeScript("deleteAllRows();");

		if (catalogoServicoDTO != null && catalogoServicoDTO.getColServicoContrato() != null) {

			for (ServContratoCatalogoServDTO servicos : catalogoServicoDTO.getColServicoContrato()) {
				if (servicos != null) {
					ServContratoCatalogoServDTO servContratoCatalogoServDTO = new ServContratoCatalogoServDTO();
					ServicoDTO servicoDTO = new ServicoDTO();
					servicoDTO.setIdServico(servicos.getIdServicoContrato());
					servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
					servicos.setNomeServico(servicoDTO.getNomeServico());
					servicos.setIdServicoContrato(servicoDTO.getIdServico());

					HTMLTable table;
					//table = document.getTableById("tblServicoContrato");
					//table.deleteAllRows();
					//table.addRowsByCollection(catalogoServicoDTO.getColServicoContrato(), new String[] { "", "idServicoContrato", "nomeServico", }, null, null, new String[] { "gerarButtonDelete" },
					//		null, null);

				}

			}
		}

		if (catalogoServicoDTO != null && catalogoServicoDTO.getColInfoCatalogoServico() != null) {
			HTMLTable table;
			table = document.getTableById("tblInfoCatalogoServico");
			table.deleteAllRows();
			table.addRowsByCollection(catalogoServicoDTO.getColInfoCatalogoServico(), new String[] { "", "idServicoCatalogo", "nomeServicoContrato", "nomeInfoCatalogoServico", "descInfoCatalogoServico" },  null, "",
					new String[] { "gerarButtonDelete2" }, "funcaoClickRow", null);


		}

		HTMLForm form = document.getForm("form");
		form.clear();

		if(catalogoServicoDTO != null){
			form.setValues(catalogoServicoDTO);
		}
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, WebUtil.getUsuarioSistema(request));

		if (catalogoServicoDTO.getIdCatalogoServico() != null) {
			catalogoServicoDTO.setDataFim(UtilDatas.getDataAtual());
			catalogoServicoService.update(catalogoServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.nome"));
			// document.alert("Selecione um catalogo!");
		}
		document.executeScript("deleteAllRows();");
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_CATALOGOSERVICO()");
	}

	public Class<CatalogoServicoDTO> getBeanClass() {
		return CatalogoServicoDTO.class;
	}

	public void restoreInfo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		InfoCatalogoServicoService infoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, WebUtil.getUsuarioSistema(request));

		InfoCatalogoServicoDTO infoCatalogoServicoDTO = new InfoCatalogoServicoDTO();

		if (catalogoServicoDTO.getIdInfoCatalogoServico() != null) {
			infoCatalogoServicoDTO.setIdInfoCatalogoServico(catalogoServicoDTO.getIdInfoCatalogoServico());
			infoCatalogoServicoDTO.setIdCatalogoServico(catalogoServicoDTO.getIdCatalogoServico());
			infoCatalogoServicoDTO = (InfoCatalogoServicoDTO) infoService.restore(infoCatalogoServicoDTO);

			if (infoCatalogoServicoDTO != null) {

				Integer id = infoCatalogoServicoDTO.getIdInfoCatalogoServico();
				String text = UtilStrings.nullToVazio(infoCatalogoServicoDTO.getDescInfoCatalogoServico());
				String nome = infoCatalogoServicoDTO.getNomeInfoCatalogoServico();
				Integer index = catalogoServicoDTO.getRowIndex();

				document.executeScript("setaInfo('" + nome + "', '" + text + "', '" + index + "');");
			}
		}

	}

	/**
	 * Adiciona/atualiza grid servico.
	 * 
	 * @author pedro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void adicionaGridServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		ServicoService servicoContratoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		ServicoDTO bean = new ServicoDTO();
		ServContratoCatalogoServDTO servContratoCatalogoServDTO = new ServContratoCatalogoServDTO();
		bean.setIdServico(catalogoServicoDTO.getIdServicoContrato());
		bean = (ServicoDTO) servicoContratoService.restore(bean);

		servContratoCatalogoServDTO.setIdServicoContrato(bean.getIdServico());
		servContratoCatalogoServDTO.setNomeServico(bean.getNomeServico());

		//HTMLTable tblServico = document.getTableById("tblServicoContrato");

		//tblServico.addRow(servContratoCatalogoServDTO, new String[] { "", "idServicoContrato", "nomeServico" }, new String[] { "idServicoContrato" }, "Serviço já selecionado!",
		//		new String[] { "gerarButtonDelete" }, null, null);
		//document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblServicoContrato', 'tblServicoContrato');");
	}

	/**
	 * Retorna o Conteudo de catálogo de serviço
	 * 
	 * @author pedro
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void conteudoInfoCatalogoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		InfoCatalogoServicoService infoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, WebUtil.getUsuarioSistema(request));

		InfoCatalogoServicoDTO infoCatalogoServicoDTO = new InfoCatalogoServicoDTO();
		if (catalogoServicoDTO.getIdInfoCatalogoServico() != null) {
			infoCatalogoServicoDTO.setIdInfoCatalogoServico(catalogoServicoDTO.getIdInfoCatalogoServico());
			infoCatalogoServicoDTO = (InfoCatalogoServicoDTO) infoService.restore(infoCatalogoServicoDTO);
			document.executeScript("$('#tituloCatalogo').text('" + infoCatalogoServicoDTO.getNomeInfoCatalogoServico() + "');" + "$('#POPUP_CONTEUDOCATALOGO').dialog('open');");
			HTMLElement m = document.getElementById("conteudoCatalogo");
			m.setInnerHTML(infoCatalogoServicoDTO.getDescInfoCatalogoServico());
		}
	}
	
	public void verificarContratoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogoServicoDTO catalogoServicoDTO = (CatalogoServicoDTO) document.getBean();
		InfoCatalogoServicoService infoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, WebUtil.getUsuarioSistema(request));
		infoService.findByContratoServico(catalogoServicoDTO.getIdContrato());
		if (infoService.findByContratoServico(catalogoServicoDTO.getIdContrato())) {
			document.executeScript("$('#POPUP_DETALHES').dialog('open');");
		}else{
			document.executeScript("validarContratoServico();");
		}
	}
}
