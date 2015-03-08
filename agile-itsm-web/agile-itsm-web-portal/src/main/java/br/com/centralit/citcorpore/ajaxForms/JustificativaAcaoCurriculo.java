package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.JustificativaAcaoCurriculoDTO;
import br.com.centralit.citcorpore.negocio.JustificativaAcaoCurriculoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings("rawtypes")
public class JustificativaAcaoCurriculo extends AjaxFormAction {

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

    }


	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JustificativaAcaoCurriculoDTO justificativaAcaoCurriculoDTO = (JustificativaAcaoCurriculoDTO) document.getBean();
    	JustificativaAcaoCurriculoService justificativaAcaoCurriculoService = (JustificativaAcaoCurriculoService) ServiceLocator.getInstance().getService(JustificativaAcaoCurriculoService.class, null);

    	Collection comandoJaCadastrado = justificativaAcaoCurriculoService.find(justificativaAcaoCurriculoDTO);
    	if (comandoJaCadastrado != null && comandoJaCadastrado.size() > 0 ) {
			document.alert(UtilI18N.internacionaliza(request, "MSE01") );
			return;
		}

    		if (justificativaAcaoCurriculoDTO.getIdJustificativaAcaoCurriculo() == null || justificativaAcaoCurriculoDTO.getIdJustificativaAcaoCurriculo().intValue() == 0) {

    			if (comandoJaCadastrado != null && !comandoJaCadastrado.isEmpty() ) {
    				document.alert(UtilI18N.internacionaliza(request, "MSE01") );
    			} else {
    				justificativaAcaoCurriculoService.create(justificativaAcaoCurriculoDTO);
            		document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    			}
        	} else {

        		justificativaAcaoCurriculoService.update(justificativaAcaoCurriculoDTO);
        		document.alert(UtilI18N.internacionaliza(request, "MSG06") );
        	}

        	HTMLForm form = document.getForm("form");
        	form.clear();

    }

    public void restore(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	JustificativaAcaoCurriculoDTO justificativaAcaoCurriculoDTO = (JustificativaAcaoCurriculoDTO) document.getBean();
	JustificativaAcaoCurriculoService justificativaAcaoCurriculoService = (JustificativaAcaoCurriculoService) ServiceLocator.getInstance().getService(
		JustificativaAcaoCurriculoService.class, null);

	justificativaAcaoCurriculoDTO = (JustificativaAcaoCurriculoDTO) justificativaAcaoCurriculoService.restore(justificativaAcaoCurriculoDTO);

	HTMLForm form = document.getForm("form");
	form.clear();
	form.setValues(justificativaAcaoCurriculoDTO);
    }

    public Class getBeanClass() {
	return JustificativaAcaoCurriculoDTO.class;
    }
}
