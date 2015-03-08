package br.com.centralit.citcorpore.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BIDashBoardDTO;
import br.com.centralit.citcorpore.bean.DataManagerObjectsDTO;
import br.com.centralit.citcorpore.negocio.BIDashBoardService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class ListagemDashBoardsObjects extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HashMap hashValores = this.getFormFields(request);
        String idStr = (String) hashValores.get("ID");
        int id = -1;
        try {
            if (idStr != null && !idStr.equalsIgnoreCase("-1")) {
                idStr = UtilStrings.apenasNumeros(idStr);
            }
            id = Integer.parseInt(idStr);
        } catch (final Exception e) {
            // e.printStackTrace();
        }
        final BIDashBoardService biDashService = (BIDashBoardService) ServiceLocator.getInstance().getService(BIDashBoardService.class, null);
        String strCab = "";
        if (id == -1) {
            strCab += "[{";
            strCab += "\"id\":-1,";
            strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "dashboard.dashboards") + "\",";
            strCab += "\"children\":[";
            final Collection colObjsNeg = biDashService.list();
            if (colObjsNeg != null) {
                boolean bPrim = true;
                for (final Iterator it = colObjsNeg.iterator(); it.hasNext();) {
                    final BIDashBoardDTO biDashDTO = (BIDashBoardDTO) it.next();
                    if (!bPrim) {
                        strCab += ",";
                    }
                    strCab += "{";
                    strCab += "\"id\":\"" + biDashDTO.getIdDashBoard() + "\",";
                    strCab += "\"text\":\"" + biDashDTO.getNomeDashBoard() + "\"";
                    // strCab += "\"state\":\"closed\"";
                    strCab += "}";
                    bPrim = false;
                }
            }
            strCab += "]";
            strCab += "}]";
        }

        request.setAttribute("json_retorno", strCab);
    }

    @Override
    public Class<DataManagerObjectsDTO> getBeanClass() {
        return DataManagerObjectsDTO.class;
    }

    private HashMap getFormFields(final HttpServletRequest req) {
        try {
            req.setCharacterEncoding("ISO-8859-1");
        } catch (final UnsupportedEncodingException e) {
            System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
            e.printStackTrace();
        }
        final HashMap formFields = new HashMap();
        final Enumeration en = req.getParameterNames();
        String[] strValores;
        while (en.hasMoreElements()) {
            final String nomeCampo = (String) en.nextElement();
            strValores = req.getParameterValues(nomeCampo);
            if (strValores.length == 0) {
                formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
            } else {
                if (strValores.length == 1) {
                    formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(strValores[0]));
                } else {
                    formFields.put(nomeCampo.toUpperCase(), strValores);
                }
            }
        }
        return formFields;
    }

}
