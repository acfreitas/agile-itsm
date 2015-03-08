package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.metainfo.bean.TableSearchDTO;
import br.com.centralit.citcorpore.metainfo.negocio.TableSearchService;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TableSearch extends AjaxFormAction {

    private static boolean DEBUG = true;

    @Override
    public Class getBeanClass() {
        return TableSearchDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ParserRequest parser = new ParserRequest();
        final Map hashValores = parser.getFormFields(request);
        if (DEBUG) {
            this.debugValuesFromRequest(hashValores);
        }

        final TableSearchService tableSearchService = (TableSearchService) ServiceLocator.getInstance().getService(TableSearchService.class, null);

        final TableSearchDTO tableSearchDTO = (TableSearchDTO) document.getBean();
        hashValores.put("IDISPLAYLENGTH", "" + tableSearchDTO.getRows());
        final String str = tableSearchService.findItens(tableSearchDTO, false, hashValores, request);

        request.setAttribute("json_response", str);
    }

}
