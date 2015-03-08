package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ImportanciaNegocioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ImportanciaNegocioService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class ImportanciaNegocio extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.focusInFirstActivateField(null);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ImportanciaNegocioDTO importanciaNegocio = (ImportanciaNegocioDTO) document.getBean();
		ImportanciaNegocioService importanciaNegocioService = (ImportanciaNegocioService) ServiceLocator.getInstance().getService(ImportanciaNegocioService.class, null);

		if (!importanciaNegocioService.existeRegistro(importanciaNegocio.getNomeImportanciaNegocio())) {
			if (importanciaNegocio.getIdImportanciaNegocio() == null || importanciaNegocio.getIdImportanciaNegocio().intValue() == 0) {
				importanciaNegocio.setIdEmpresa(WebUtil.getIdEmpresa(request));
				importanciaNegocio.setSituacao("A");

				importanciaNegocioService.create(importanciaNegocio);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				importanciaNegocioService.update(importanciaNegocio);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ImportanciaNegocioDTO importanciaNegocio = (ImportanciaNegocioDTO) document.getBean();
		ImportanciaNegocioService importanciaNegocioService = (ImportanciaNegocioService) ServiceLocator.getInstance().getService(ImportanciaNegocioService.class, null);
		importanciaNegocio = (ImportanciaNegocioDTO) importanciaNegocioService.restore(importanciaNegocio);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(importanciaNegocio);
	}

	/**
	 * Metodo responsavel por remover uma importancia de negocio.
	 * 
	 * <p> foi realizada uma alteração no dia 25 de novembro de 2014 para adequar um tratamento de exclusão de importancia de negocio vinculada a um cadastro de serviço, de acordo com a solicitação 159456</p>
	 * 
	 * <p> Altor da alteração Ezequiel</p>
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * @date 2014-11-25
	 * 
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ImportanciaNegocioDTO importanciaNegocio = (ImportanciaNegocioDTO) document.getBean();
		
		ImportanciaNegocioService importanciaNegocioService = (ImportanciaNegocioService) ServiceLocator.getInstance().getService(ImportanciaNegocioService.class, null);

		try{
		
			importanciaNegocioService.existeVinculoCadastroServico(importanciaNegocio.getIdImportanciaNegocio());
			
			importanciaNegocio.setSituacao("I");
			
			importanciaNegocioService.update(importanciaNegocio);
			
			HTMLForm form = document.getForm("form");
			
			form.clear();
			
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			
		}catch (Exception ex){
			
			document.alert(UtilI18N.internacionaliza(request, ex.getMessage()));
		}	
	}

	

	public Class getBeanClass() {
		return ImportanciaNegocioDTO.class;
	}
}
