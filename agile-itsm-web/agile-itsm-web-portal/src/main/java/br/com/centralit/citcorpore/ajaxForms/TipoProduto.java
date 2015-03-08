package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.RelacionamentoProdutoDTO;
import br.com.centralit.citcorpore.bean.TipoProdutoDTO;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProdutoService;
import br.com.centralit.citcorpore.negocio.FornecedorProdutoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.MarcaService;
import br.com.centralit.citcorpore.negocio.ProdutoService;
import br.com.centralit.citcorpore.negocio.RelacionamentoProdutoService;
import br.com.centralit.citcorpore.negocio.TipoProdutoService;
import br.com.centralit.citcorpore.negocio.UnidadeMedidaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author cleon.junior
 *
 */
public class TipoProduto extends AjaxFormAction {
	
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
		TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) document.getBean();
		TipoProdutoService tipoProdutoService = (TipoProdutoService) ServiceLocator.getInstance().getService(TipoProdutoService.class, WebUtil.getUsuarioSistema(request));
		
		
		if(tipoProdutoDTO.getSituacao()== null || tipoProdutoDTO.getAceitaRequisicao() == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		
		if (tipoProdutoDTO.getIdTipoProduto() == null || tipoProdutoDTO.getIdTipoProduto().intValue() == 0) {
			if (tipoProdutoService.consultarTiposProdutos(tipoProdutoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			tipoProdutoDTO = (TipoProdutoDTO) tipoProdutoService.create(tipoProdutoDTO, request);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			tipoProdutoService.update(tipoProdutoDTO, request);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		
		HTMLForm form = document.getForm("form");
		document.executeScript("deleteAllRows();");
		form.clear();
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) document.getBean();
		TipoProdutoDTO tipoProdutoDTO2 = new TipoProdutoDTO();
		FornecedorDTO fornecedorDTO = new FornecedorDTO();
		MarcaDTO marcaDTO = new MarcaDTO();
		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		UnidadeMedidaDTO unidadeMedidaDTO = new UnidadeMedidaDTO();
		Collection<RelacionamentoProdutoDTO> colRelacionamentoProdutoDTOs = new ArrayList();
		Collection<FornecedorProdutoDTO> colFornecedorProdutoDTOs = new ArrayList();
		
		RelacionamentoProdutoService relacionamentoProdutoService = (RelacionamentoProdutoService) ServiceLocator.getInstance().getService(RelacionamentoProdutoService.class, null);
		FornecedorProdutoService fornecedorProdutoService = (FornecedorProdutoService) ServiceLocator.getInstance().getService(FornecedorProdutoService.class, null);
		MarcaService marcaService = (MarcaService) ServiceLocator.getInstance().getService(MarcaService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		TipoProdutoService tipoProdutoService = (TipoProdutoService) ServiceLocator.getInstance().getService(TipoProdutoService.class, null);
		CategoriaProdutoService categoriaProdutoService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);
		UnidadeMedidaService unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, null);
		
		tipoProdutoDTO = (TipoProdutoDTO) tipoProdutoService.restore(tipoProdutoDTO);
		
		unidadeMedidaDTO.setIdUnidadeMedida(tipoProdutoDTO.getIdUnidadeMedida());
		if(unidadeMedidaDTO.getIdUnidadeMedida() != null){
			unidadeMedidaDTO = (UnidadeMedidaDTO) unidadeMedidaService.restore(unidadeMedidaDTO);
		}
		
		categoriaProdutoDTO.setIdCategoria(tipoProdutoDTO.getIdCategoria());
		categoriaProdutoDTO = (CategoriaProdutoDTO) categoriaProdutoService.restore(categoriaProdutoDTO);
		
		tipoProdutoDTO.setNomeUnidadeMedida(unidadeMedidaDTO.getNomeUnidadeMedida());
		tipoProdutoDTO.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
		
		HTMLForm form = document.getForm("form");
		document.executeScript("deleteAllRows();");
		form.clear();
		
		colRelacionamentoProdutoDTOs = relacionamentoProdutoService.findByIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
		
		colFornecedorProdutoDTOs = fornecedorProdutoService.findByIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
		
		if (colRelacionamentoProdutoDTOs != null && !colRelacionamentoProdutoDTOs.isEmpty()){
			int i = 1;
			for (RelacionamentoProdutoDTO relacionamento : colRelacionamentoProdutoDTOs){
				tipoProdutoDTO2.setIdTipoProduto(relacionamento.getIdTipoProdutoRelacionado());
				tipoProdutoDTO2 = (TipoProdutoDTO) tipoProdutoService.restore(tipoProdutoDTO2);
				document.executeScript("addLinhaTabelaTipoProdutoRelacionado(" + tipoProdutoDTO2.getIdTipoProduto() + ", '" + StringEscapeUtils.escapeJavaScript(tipoProdutoDTO2.getNomeProduto()) + "', " + false + ");");
					document.executeScript("atribuirChecked('"+ relacionamento.getTipoRelacionamento() + "',"+ i +");");
					i++;
			}
		}
		
		if (colFornecedorProdutoDTOs != null && !colFornecedorProdutoDTOs.isEmpty()){
			int i = 1;
			for (FornecedorProdutoDTO fornecedorProdutoDTO : colFornecedorProdutoDTOs){
				fornecedorDTO.setIdFornecedor(fornecedorProdutoDTO.getIdFornecedor());
				fornecedorDTO = (FornecedorDTO) fornecedorService.restore(fornecedorDTO);
				document.executeScript("addLinhaTabelaFornecedor(" + fornecedorProdutoDTO.getIdFornecedor() + ",'" + StringEscapeUtils.escapeJavaScript(fornecedorDTO.getRazaoSocial()) + "',"+ false + ");");
				if (fornecedorProdutoDTO.getIdMarca() != null){
					marcaDTO.setIdMarca(fornecedorProdutoDTO.getIdMarca());
					marcaDTO = (MarcaDTO) marcaService.restore(marcaDTO);
					document.executeScript("setarMarca("+marcaDTO.getIdMarca() +",'" + StringEscapeUtils.escapeJavaScript(marcaDTO.getNomeMarca()) + "'," + i +");");
				}
				document.executeScript("setarIdFornecedorProduto("+fornecedorProdutoDTO.getIdFornecedorProduto() +"," + i +");");
				i++;
				}
		}
		
		
		
		
		
		form.setValues(tipoProdutoDTO);
	}
	
	public Class<TipoProdutoDTO> getBeanClass() {
		return TipoProdutoDTO.class;
	}
	
	public void deleteTipoProdutoRelacionado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) document.getBean();
		RelacionamentoProdutoDTO relacionamentoProdutoDTO = new RelacionamentoProdutoDTO();
		
		RelacionamentoProdutoService relacionamentoProdutoService = (RelacionamentoProdutoService) ServiceLocator.getInstance().getService(RelacionamentoProdutoService.class, null);
		
		String idTipoProdutoRelacionadoAux = request.getParameter("relacionamentoProduto");

		Integer idTipoProdutoRelacionado = Integer.parseInt(idTipoProdutoRelacionadoAux);
		relacionamentoProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
		relacionamentoProdutoDTO.setIdTipoProdutoRelacionado(idTipoProdutoRelacionado);

		if (tipoProdutoDTO.getIdTipoProduto() != null && tipoProdutoDTO.getIdTipoProduto().intValue() > 0) {
			relacionamentoProdutoService.delete(relacionamentoProdutoDTO);
		}
		
		
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}
	
	@SuppressWarnings("unchecked")
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoProdutoDTO tipoProduto = (TipoProdutoDTO) document.getBean();
		
		ProdutoDTO produtoDTO = new ProdutoDTO();
		Collection<ProdutoDTO> listProduto = new ArrayList();
		Collection<FornecedorProdutoDTO> colFornecedorProdutoDTOs = new ArrayList();
		
		RelacionamentoProdutoService relacionamentoProdutoService = (RelacionamentoProdutoService) ServiceLocator.getInstance().getService(RelacionamentoProdutoService.class, null);
		FornecedorProdutoService fornecedorProdutoService = (FornecedorProdutoService) ServiceLocator.getInstance().getService(FornecedorProdutoService.class, null);
		ProdutoService produtoService = (ProdutoService) ServiceLocator.getInstance().getService(ProdutoService.class, WebUtil.getUsuarioSistema(request));
		TipoProdutoService tipoProdutoService = (TipoProdutoService) ServiceLocator.getInstance().getService(TipoProdutoService.class, WebUtil.getUsuarioSistema(request));
		
		produtoDTO.setIdTipoProduto(tipoProduto.getIdTipoProduto());
		listProduto = produtoService.findByIdTipoProduto(tipoProduto.getIdTipoProduto());
		if (listProduto == null){

			if (tipoProduto.getIdTipoProduto().intValue() > 0) {
				colFornecedorProdutoDTOs = fornecedorProdutoService.findByIdTipoProduto(tipoProduto.getIdTipoProduto());
				if (colFornecedorProdutoDTOs != null && !colFornecedorProdutoDTOs.isEmpty()){
					for (FornecedorProdutoDTO fornecedorProduto : colFornecedorProdutoDTOs){
						fornecedorProduto.setDataFim(UtilDatas.getDataAtual());
						fornecedorProdutoService.update(fornecedorProduto);
					}
				}
				relacionamentoProdutoService.deleteByIdTipoProduto(tipoProduto.getIdTipoProduto());
				tipoProdutoService.delete(tipoProduto);
			}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}
		else{
			document.alert(UtilI18N.internacionaliza(request, "MSG08"));
		}

		HTMLForm form = document.getForm("form");
		document.executeScript("deleteAllRows();");
		form.clear();
	}
	
	public void deleteFornecedor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) document.getBean();
		FornecedorProdutoDTO fornecedorProdutoDTO = new FornecedorProdutoDTO();
		
		FornecedorProdutoService fornecedorProdutoService = (FornecedorProdutoService) ServiceLocator.getInstance().getService(FornecedorProdutoService.class, null);
		
		String idFornecedorAux = request.getParameter("fornecedorProduto");
		
		if (!idFornecedorAux.isEmpty()){
			Integer idFornecedor = Integer.parseInt(idFornecedorAux);
			fornecedorProdutoDTO.setIdFornecedorProduto(idFornecedor);
		}
		
		if (tipoProdutoDTO.getIdTipoProduto() != null && tipoProdutoDTO.getIdTipoProduto().intValue() > 0) {
			fornecedorProdutoDTO = (FornecedorProdutoDTO) fornecedorProdutoService.restore(fornecedorProdutoDTO);
			fornecedorProdutoDTO.setDataFim(UtilDatas.getDataAtual());
			fornecedorProdutoService.update(fornecedorProdutoDTO);
		}
		
		
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

}
