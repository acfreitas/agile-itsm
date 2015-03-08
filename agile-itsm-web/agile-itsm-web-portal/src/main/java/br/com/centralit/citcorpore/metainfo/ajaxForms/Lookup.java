package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.centralit.citcorpore.metainfo.negocio.LookupService;
import br.com.citframework.service.ServiceLocator;

public class Lookup extends AjaxFormAction {

    private static boolean DEBUG = true;

    @Override
    public Class<LookupDTO> getBeanClass() {
        return LookupDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ParserRequest parser = new ParserRequest();
        final Map<String, Object> hashValores = parser.getFormFields(request);
        if (DEBUG) {
            this.debugValuesFromRequest(hashValores);
        }

        final LookupService lookupService = (LookupService) ServiceLocator.getInstance().getService(LookupService.class, null);

        final LookupDTO lookupDto = (LookupDTO) document.getBean();
        lookupDto.setTermoPesquisa(lookupDto.getQ());
        final String retorno = lookupService.findSimpleString(lookupDto);

        request.setAttribute("json_response", "[" + retorno + "]");
    }

}
