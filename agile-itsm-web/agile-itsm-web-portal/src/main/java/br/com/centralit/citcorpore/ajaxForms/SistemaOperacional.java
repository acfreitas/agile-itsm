package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.SistemaOperacionalDTO;
import br.com.centralit.citcorpore.negocio.SistemaOperacionalService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings("rawtypes")
public class SistemaOperacional extends AjaxFormAction {

    @Override
    public void load(DocumentHTML arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {

    }

    public void SistemaOperacional_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SistemaOperacionalDTO soDTO = (SistemaOperacionalDTO) document.getBean();
		SistemaOperacionalService soService = (SistemaOperacionalService) ServiceLocator.getInstance().getService(SistemaOperacionalService.class, null);
		
		// Verificando a existência do objeto de serviço.
		if (soService != null) {
			// Inserindo o SO.
			if (soDTO.getId() == null || soDTO.getId().intValue() == 0) {
				Collection soJaCadastrado = soService.find(soDTO);
				
				// Verificando se o SO já foi cadastrado.
				if (soJaCadastrado != null && !soJaCadastrado.isEmpty() ) {
					// Se verdadeiro, então alerta o usuário e pede para tentar outro SO.
					document.alert(UtilI18N.internacionaliza(request, "MSE01") );
				} else { // Inserindo o SO.
					soService.create(soDTO);
				    document.alert(UtilI18N.internacionaliza(request, "MSG05") );
				}			    
			} else { // Atualizando o SO
			    soService.update(soDTO);
			    document.alert(UtilI18N.internacionaliza(request, "MSG06") );
			}
			
			HTMLForm form = document.getForm("form");
			form.clear();
		}
    }

    public void SistemaOperacional_onrestore(DocumentHTML document,
	    javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	SistemaOperacionalDTO sistema = (SistemaOperacionalDTO) document.getBean();
	SistemaOperacionalService sistemaService = (SistemaOperacionalService) ServiceLocator
		.getInstance().getService(SistemaOperacionalService.class, null);

	sistema = (SistemaOperacionalDTO) sistemaService.restore(sistema);

	HTMLForm form = document.getForm("form");
	form.clear();
	form.setValues(sistema);
    }

    
    /**
     * Deleção lógica.
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void updateDataFim(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	SistemaOperacionalDTO sistema = (SistemaOperacionalDTO) document.getBean();
	SistemaOperacionalService tipoUnidadeService = (SistemaOperacionalService) ServiceLocator.getInstance()
		.getService(SistemaOperacionalService.class, null);

	if (sistema.getId() != null && sistema.getId() != 0) {
	   // sistema.set (UtilDatas.getDataAtual());
	    tipoUnidadeService.update(sistema);

	    HTMLForm form = document.getForm("form");
	    form.clear();
	    document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}
    }
    
    
    
	public Class getBeanClass() {
	return SistemaOperacionalDTO.class;
    }
}
