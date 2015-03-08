package br.com.centralit.citcorpore.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BICategoriasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.bean.DataManagerObjectsDTO;
import br.com.centralit.citcorpore.negocio.BICategoriasService;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class ListagemConsultasObjects extends AjaxFormAction {

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
        final BICategoriasService biCategoriasService = (BICategoriasService) ServiceLocator.getInstance().getService(BICategoriasService.class, null);
        final BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
        String strCab = "";
        if (id == -1) {
            strCab += "[{";
            strCab += "\"id\":-1,";
            strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "listagemConsultas.consultas") + "\",";
            strCab += "\"children\":[";
            final Collection colObjsNeg = biCategoriasService.findSemPai();
            if (colObjsNeg != null) {
                boolean bPrim = true;
                for (final Iterator it = colObjsNeg.iterator(); it.hasNext();) {
                    final BICategoriasDTO biCategoriasDTO = (BICategoriasDTO) it.next();
                    if (!bPrim) {
                        strCab += ",";
                    }
                    strCab += "{";
                    strCab += "\"id\":\"G" + biCategoriasDTO.getIdCategoria() + "\",";
                    strCab += "\"text\":\"" + biCategoriasDTO.getNomeCategoria() + "\",";
                    strCab += "\"state\":\"closed\"";
                    strCab += "}";
                    bPrim = false;
                }
            }
            strCab += "]";
            strCab += "}]";
        } else {
            Collection col = biCategoriasService.findByIdCategoriaPai(id);
            Collection col2 = biConsultaService.findByIdCategoria(id);
            if (col != null || col2 != null) {
                if (col == null) {
                    col = new ArrayList();
                }
                if (col2 == null) {
                    col2 = new ArrayList();
                }
                strCab += "[";
                boolean bPrim = true;
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    if (!bPrim) {
                        strCab += ",";
                    }
                    final BICategoriasDTO biCategoriasDTO = (BICategoriasDTO) it.next();
                    // String strTam = "";
                    strCab += "{";
                    strCab += "\"id\":\"G" + biCategoriasDTO.getIdCategoria() + "\",";
                    strCab += "\"text\":\"" + biCategoriasDTO.getNomeCategoria() + "\"";
                    strCab += "}";
                    bPrim = false;
                }
                bPrim = true;
                for (final Iterator it = col2.iterator(); it.hasNext();) {
                    if (!bPrim) {
                        strCab += ",";
                    }
                    final BIConsultaDTO biConsultaDTO = (BIConsultaDTO) it.next();
                    // String strTam = "";
                    strCab += "{";
                    strCab += "\"id\":\"" + biConsultaDTO.getTipoConsulta() + biConsultaDTO.getIdConsulta() + "\",";
                    strCab += "\"text\":\"" + biConsultaDTO.getNomeConsulta() + "\"";
                    strCab += "}";
                    bPrim = false;
                }
                strCab += "]";
            }
        }

        request.setAttribute("json_retorno", strCab);
    }

    @Override
    public Class getBeanClass() {
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
