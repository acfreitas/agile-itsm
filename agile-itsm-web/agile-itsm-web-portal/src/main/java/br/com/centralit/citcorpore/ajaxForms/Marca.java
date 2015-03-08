package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.MarcaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class Marca extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
	}


	@Override
	public Class getBeanClass() {
		
		return MarcaDTO.class;
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MarcaDTO marca = (MarcaDTO) document.getBean();

		MarcaService marcaService = (MarcaService) ServiceLocator.getInstance().getService(MarcaService.class, null);
		
		if(marca.getSituacao()== null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		
		if (marca.getIdMarca() == null || marca.getIdMarca() == 0) {
			if (marcaService.consultarMarcas(marca)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			marcaService.create(marca);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (marcaService.consultarMarcas(marca)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			marcaService.update(marca);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *          
	 *             Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MarcaDTO marca = (MarcaDTO) document.getBean();

		MarcaService marcaService = (MarcaService) ServiceLocator.getInstance().getService(MarcaService.class, WebUtil.getUsuarioSistema(request));

		if (marca.getIdMarca().intValue() > 0) {
			marcaService.delete(marca);
		}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *             Metodo para restaura os campos.
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MarcaDTO marca = (MarcaDTO) document.getBean();
		FornecedorDTO fornecedorDTO = new FornecedorDTO();
		
		MarcaService marcaService = (MarcaService) ServiceLocator.getInstance().getService(MarcaService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		
		marca = (MarcaDTO) marcaService.restore(marca);
		if(marca.getIdFabricante()!=null){
			fornecedorDTO.setIdFornecedor(marca.getIdFabricante());
			fornecedorDTO = (FornecedorDTO) fornecedorService.restore(fornecedorDTO);
		}
		
		
		
		HTMLForm form = document.getForm("form");
		form.clear();
		marca.setNomeFabricante(fornecedorDTO.getRazaoSocial());
		form.setValues(marca);

	}


}
