/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CategoriaPostDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaPostService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class CategoriaPost extends AjaxFormAction {

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		gerarComboCategoria(document,request);
	}

	/**
	 * Inclui um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaPostDTO categoriaServicoDTO = (CategoriaPostDTO) document.getBean();
		CategoriaPostService categoriaServicoService = (CategoriaPostService) ServiceLocator.getInstance().getService(CategoriaPostService.class, null);

		if (categoriaServicoDTO.getIdCategoriaPost() == null) {
			categoriaServicoDTO.setDataInicio(UtilDatas.getDataAtual());

			if (!categoriaServicoService.verificarSeCategoriaExiste(categoriaServicoDTO)) {
				categoriaServicoService.create(categoriaServicoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			}
		} else {
			categoriaServicoService.update(categoriaServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.setBean(new CategoriaPostDTO());
	}

	/**
	 * Seta a data atual na data final ao excluir um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaPostDTO categoriaPost = (CategoriaPostDTO) document.getBean();
		CategoriaPostService categoriaServicoService = (CategoriaPostService) ServiceLocator.getInstance().getService(CategoriaPostService.class, null);

		if (categoriaPost != null && categoriaPost.getIdCategoriaPost() != null) 
		{
			if (categoriaServicoService.verificarSeCategoriaPossuiServicoOuSubCategoria(categoriaPost)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
			} else {
				categoriaPost.setDataFim(UtilDatas.getDataAtual());
				categoriaServicoService.update(categoriaPost);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}

		}
		HTMLForm form = document.getForm("form");
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
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaPostDTO categoriaServicoDTO = (CategoriaPostDTO) document.getBean();
		CategoriaPostService categoriaServicoService = (CategoriaPostService) ServiceLocator.getInstance().getService(CategoriaPostService.class, null);
		categoriaServicoDTO = (CategoriaPostDTO) categoriaServicoService.restore(categoriaServicoDTO);
		
		CategoriaPostDTO categoriaServicoSuperior = new CategoriaPostDTO();
		
		if(categoriaServicoDTO.getIdCategoriaPostPai() != null){
			categoriaServicoSuperior.setIdCategoriaPost(categoriaServicoDTO.getIdCategoriaPostPai());
			categoriaServicoSuperior = (CategoriaPostDTO) categoriaServicoService.restore(categoriaServicoSuperior);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(categoriaServicoDTO);

		//gerarComboCategoria(document);

	}

	/**
	 * Gera Combo de Categoria de Serviço Superior.
	 * 
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void gerarComboCategoria(DocumentHTML document,HttpServletRequest request) throws ServiceException, Exception {
		CategoriaPostDTO categoriaServicoDTO = (CategoriaPostDTO) document.getBean();
		if (categoriaServicoDTO == null){
		    categoriaServicoDTO = new CategoriaPostDTO();
		}
		CategoriaPostService categoriaServicoService = (CategoriaPostService) ServiceLocator.getInstance().getService(CategoriaPostService.class, null);

		HTMLSelect comboCategoriaServicoSuperior = (HTMLSelect) document.getSelectById("idCategoriaPostPai");
		comboCategoriaServicoSuperior.removeAllOptions();

		Collection<CategoriaPostDTO> categoriasServicoSuperior = categoriaServicoService.listCategoriasAtivas();

		if(categoriaServicoDTO.getIdCategoriaPost() != null && categoriaServicoDTO.getIdCategoriaPost() != 0){
			for(CategoriaPostDTO c : categoriasServicoSuperior){
				if(c.getIdCategoriaPost() == categoriaServicoDTO.getIdCategoriaPost().intValue()){
					categoriasServicoSuperior.remove(c);
					break;
				}
			}
		}
		if (categoriasServicoSuperior != null && !categoriasServicoSuperior.isEmpty()) {		
			comboCategoriaServicoSuperior.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			comboCategoriaServicoSuperior.addOptions(categoriasServicoSuperior, "idCategoriaPost", "nomeCategoria", null);
		} else {
			comboCategoriaServicoSuperior.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		}
	}

	public Class getBeanClass() {
		return CategoriaPostDTO.class;
	}

}
