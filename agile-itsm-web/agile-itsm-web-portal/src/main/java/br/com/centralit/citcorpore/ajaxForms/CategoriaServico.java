/**
 * CentralIT - CITSmart
 */
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
import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class CategoriaServico extends AjaxFormAction {
	
	UsuarioDTO usuario;
	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		/*this.gerarComboCategoriaServicoSuperior(document);*/
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
		CategoriaServicoDTO categoriaServicoDTO = (CategoriaServicoDTO) document.getBean();
		CategoriaServicoService categoriaServicoService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);
	
		categoriaServicoDTO.setIdEmpresa(WebUtil.getIdEmpresa(request));
		if (categoriaServicoDTO.getIdCategoriaServico() == null) {
			categoriaServicoDTO.setDataInicio(UtilDatas.getDataAtual());
			categoriaServicoDTO.setIdEmpresa(WebUtil.getIdEmpresa(request));

			if (!categoriaServicoService.verificarSeCategoriaExiste(categoriaServicoDTO)) {
				
				if(categoriaServicoDTO.getIdCategoriaServicoPai() != null){
					Integer idCategoriaServico = 0;
					idCategoriaServico = categoriaServicoDTO.getIdCategoriaServicoPai();
					if(categoriaServicoDTO.getIdCategoriaServico() != null){
						if(categoriaServicoDTO.getIdCategoriaServico() == idCategoriaServico.intValue()){
							document.alert(UtilI18N.internacionaliza(request, "categoriaServico.registroPaiIgual"));
							return;
						}
					}
				}
				
				String nomeConcatenado = categoriaServicoDTO.getNomeCategoriaServicoConcatenado() + categoriaServicoDTO.getNomeCategoriaServico();
				categoriaServicoDTO.setNomeCategoriaServicoConcatenado(nomeConcatenado);
				categoriaServicoService.create(categoriaServicoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			}
		} else {
			if(categoriaServicoDTO.getIdCategoriaServicoPai()!= null){
				Integer idCategoriaServico = 0;
				idCategoriaServico = categoriaServicoDTO.getIdCategoriaServicoPai();
				if(categoriaServicoDTO.getIdCategoriaServico() == idCategoriaServico.intValue()){
					document.alert(UtilI18N.internacionaliza(request, "categoriaServico.registroPaiIgual"));
					return;
				}
				
				List<CategoriaServicoDTO> listCategoriaHierarquia = new ArrayList<CategoriaServicoDTO>();
				CategoriaServicoDTO beanCategoriaServico = new CategoriaServicoDTO();
				beanCategoriaServico.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServicoPai());
				beanCategoriaServico = (CategoriaServicoDTO) categoriaServicoService.restore(beanCategoriaServico);
				categoriaServicoService.listCategoriaHierarquia(beanCategoriaServico, listCategoriaHierarquia);
				categoriaServicoDTO.setNomeCategoriaServicoConcatenado(this.nomeConcatenado(listCategoriaHierarquia)+categoriaServicoDTO.getNomeCategoriaServico());
			}else{
				categoriaServicoDTO.setNomeCategoriaServicoConcatenado(categoriaServicoDTO.getNomeCategoriaServico());
			}

			categoriaServicoDTO.setIdEmpresa(WebUtil.getIdEmpresa(request));
			categoriaServicoService.update(categoriaServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_CATEGORIASERVICO()");
		document.setBean(new CategoriaServicoDTO());
	}

	/**
	 * Seta a data atual na data final ao excluir um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void atualizaData(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaServicoDTO categoriaServico = (CategoriaServicoDTO) document.getBean();
		
		CategoriaServicoService categoriaServicoService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);
		

		if (categoriaServico != null && categoriaServico.getIdCategoriaServico() != null) {
			if (categoriaServicoService.verificarSeCategoriaPossuiServicoOuSubCategoria(categoriaServico)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
			} else {
				categoriaServico.setIdEmpresa(WebUtil.getIdEmpresa(request));
				categoriaServico.setDataFim(UtilDatas.getDataAtual());
				categoriaServicoService.update(categoriaServico);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}

		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_CATEGORIASERVICO()");
	}
	
	
	
	/**
	 * Verificar se existe Hierarquia.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void verificaHierarquia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaServicoDTO categoriaServicoDTO = (CategoriaServicoDTO) document.getBean();
		List<CategoriaServicoDTO> listCategoriaHierarquia = new ArrayList<CategoriaServicoDTO>();
		CategoriaServicoService categoriaServicoService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);		
		CategoriaServicoDTO beanCategoriaServico = new CategoriaServicoDTO();
		beanCategoriaServico.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServicoPai());
		beanCategoriaServico = (CategoriaServicoDTO) categoriaServicoService.restore(beanCategoriaServico);
		categoriaServicoService.listCategoriaHierarquia(beanCategoriaServico, listCategoriaHierarquia);
		
		document.getElementById("nomeCatServicoConcatenado").setValue(this.nomeConcatenado(listCategoriaHierarquia));
	}
	
	 private String nomeConcatenado(List<CategoriaServicoDTO> listCategoriaHierarquia){
			String nomeConcatenado = "";
			
			for (int i = (listCategoriaHierarquia.size()-1); i >= 0 ; i--) {
				CategoriaServicoDTO categoriaServicoDTO = listCategoriaHierarquia.get(i);
				nomeConcatenado += categoriaServicoDTO.getNomeCategoriaServico()+" - ";
			}
			return nomeConcatenado;
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
		CategoriaServicoDTO categoriaServicoDTO = (CategoriaServicoDTO) document.getBean();
		CategoriaServicoService categoriaServicoService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);
		categoriaServicoDTO = (CategoriaServicoDTO) categoriaServicoService.restore(categoriaServicoDTO);
		
		CategoriaServicoDTO categoriaServicoSuperior = new CategoriaServicoDTO();
		
		if(categoriaServicoDTO.getIdCategoriaServicoPai() != null){
			categoriaServicoSuperior.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServicoPai());
			categoriaServicoSuperior = (CategoriaServicoDTO) categoriaServicoService.restore(categoriaServicoSuperior);
			if(categoriaServicoSuperior != null){
				categoriaServicoDTO.setNomeCategoriaServicoPai(categoriaServicoSuperior.getNomeCategoriaServico());
			}
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(categoriaServicoDTO);

		//gerarComboCategoriaServicoSuperior(document);

	}

	/**
	 * Gera Combo de Categoria de Serviço Superior.
	 * 
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void gerarComboCategoriaServicoSuperior(DocumentHTML document,HttpServletRequest request) throws ServiceException, Exception {
		CategoriaServicoDTO categoriaServicoDTO = (CategoriaServicoDTO) document.getBean();
		if (categoriaServicoDTO == null){
		    categoriaServicoDTO = new CategoriaServicoDTO();
		}
		CategoriaServicoService categoriaServicoService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);

		HTMLSelect comboCategoriaServicoSuperior = (HTMLSelect) document.getSelectById("idCategoriaServicoPai");
		comboCategoriaServicoSuperior.removeAllOptions();

		Collection<CategoriaServicoDTO> categoriasServicoSuperior = categoriaServicoService.listHierarquia();

		if(categoriaServicoDTO.getIdCategoriaServico() != null && categoriaServicoDTO.getIdCategoriaServico() != 0){
			for(CategoriaServicoDTO c : categoriasServicoSuperior){
				if(c.getIdCategoriaServico() == categoriaServicoDTO.getIdCategoriaServico().intValue()){
					categoriasServicoSuperior.remove(c);
					break;
				}
			}
		}
		
		if (categoriasServicoSuperior != null && !categoriasServicoSuperior.isEmpty()) {
			comboCategoriaServicoSuperior.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			comboCategoriaServicoSuperior.addOptions(categoriasServicoSuperior, "idCategoriaServico", "nomeCategoriaServicoNivel", null);
		} else {
			comboCategoriaServicoSuperior.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		}
	}

	public Class getBeanClass() {
		return CategoriaServicoDTO.class;
	}

}
