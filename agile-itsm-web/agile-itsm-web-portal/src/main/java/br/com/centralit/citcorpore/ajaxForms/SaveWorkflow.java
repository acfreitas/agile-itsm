package br.com.centralit.citcorpore.ajaxForms;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SaveWorkflowDTO;

public class SaveWorkflow extends AjaxFormAction {

    private static boolean DEBUG = true;

    @Override
    public Class<SaveWorkflowDTO> getBeanClass() {
        return SaveWorkflowDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ParserRequest parser = new ParserRequest();
        final Map<String, Object> hashValores = parser.getFormFields(request);
        if (DEBUG) {
            this.debugValuesFromRequest(hashValores);
        }
    }

}
