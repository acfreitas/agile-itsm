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

public class TableSearchVinc extends AjaxFormAction {

    private static boolean DEBUG = true;

    @Override
    public Class<TableSearchDTO> getBeanClass() {
        return TableSearchDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ParserRequest parser = new ParserRequest();
        final Map<String, Object> hashValores = parser.getFormFields(request);
        if (DEBUG) {
            this.debugValuesFromRequest(hashValores);
        }

        final TableSearchService tableSearchService = (TableSearchService) ServiceLocator.getInstance().getService(TableSearchService.class, null);

        final TableSearchDTO tableSearchDTO = (TableSearchDTO) document.getBean();
        String retorno = "";
        if (tableSearchDTO.getLoad() == null || !tableSearchDTO.getLoad().equalsIgnoreCase("false")) {
            if (tableSearchDTO.getJsonData() != null) {
                if (tableSearchDTO.getMatriz() == null || !tableSearchDTO.getMatriz().equalsIgnoreCase("true")) {
                    retorno = tableSearchService.findItens(tableSearchDTO, true, hashValores, request);
                } else {
                    retorno = tableSearchService.getInfoMatriz(tableSearchDTO, true, hashValores, request);
                    retorno = "[" + retorno + "]";
                }
            } else {
                if (tableSearchDTO.getMatriz() != null && tableSearchDTO.getMatriz().equalsIgnoreCase("true")) {
                    retorno = tableSearchService.getInfoMatriz(tableSearchDTO, true, hashValores, request);
                    retorno = "[" + retorno + "]";
                }
            }
        }

        if (retorno.trim().equalsIgnoreCase("")) {
            retorno = "{\"total\": \"0\",\"rows\":[]}";
        }

        request.setAttribute("json_response", "" + retorno + "");
    }

}
