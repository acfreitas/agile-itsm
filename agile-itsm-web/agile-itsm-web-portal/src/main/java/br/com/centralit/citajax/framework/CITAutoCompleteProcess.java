package br.com.centralit.citajax.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.autocomplete.AutoCompleteReturn;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class CITAutoCompleteProcess {

    /**
     * Processa o objeto passado como parametro e retorna uma string javascript
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String process(final String path, final ServletContext ctx, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        String facadeName = this.getObjectName(path);
        facadeName = UtilStrings.convertePrimeiraLetra(facadeName, "U");
        final String ext = this.getObjectExt(path);
        if (facadeName == null) {
            return null;
        }

        String metodo = null;
        Class<?> classe = null;
        if ("complete".equalsIgnoreCase(ext)) {
            classe = Class.forName(Constantes.getValue("FRAMEWORK_LOCATION_AUTOCOMPLETE") + "." + facadeName);
            metodo = "process";
        }
        if (classe != null) {
            final Object objeto = classe.newInstance();

            final int iParmCount = 1;
            Object parmReals[] = null;
            final Method mtd = CitAjaxReflexao.findMethod(metodo, objeto);
            final String parms[] = new String[iParmCount];
            parms[0] = UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm1"));
            if (parms[0] == null) {
                parms[0] = UtilStrings.decodeCaracteresEspeciais((String) request.getAttribute("parm1"));
            }
            parmReals = new Object[iParmCount];
            final Class<?>[] parmTypes = mtd.getParameterTypes();
            for (int i = 0; i < parmTypes.length; i++) {
                parmReals[i] = CitAjaxReflexao.converteTipo(parms[i], parmTypes[i]);
            }

            final Method mtdRequest = CitAjaxReflexao.findMethod("setRequest", objeto);
            final Method mtdResponse = CitAjaxReflexao.findMethod("setResponse", objeto);

            mtdRequest.invoke(objeto, new Object[] {request});
            mtdResponse.invoke(objeto, new Object[] {response});

            final Object retorno = mtd.invoke(objeto, parmReals);

            String mensagem = request.getParameter("parm2");
            if (mensagem == null) {
                mensagem = (String) request.getAttribute("parm2");
            }
            mensagem = UtilStrings.decodeCaracteresEspeciais(mensagem);
            String classTextoautocomplete = request.getParameter("parm3");
            if (classTextoautocomplete == null) {
                classTextoautocomplete = (String) request.getAttribute("parm3");
            }
            String funcaoTratarSelecaoDoAutoComplete = request.getParameter("parm4");
            if (classTextoautocomplete == null) {
                funcaoTratarSelecaoDoAutoComplete = (String) request.getAttribute("parm4");
            }
            if (funcaoTratarSelecaoDoAutoComplete == null || funcaoTratarSelecaoDoAutoComplete.trim().equalsIgnoreCase("")) {
                funcaoTratarSelecaoDoAutoComplete = "selecionaAutoComplete";
            }
            classTextoautocomplete = UtilStrings.decodeCaracteresEspeciais(classTextoautocomplete);
            return this.trataRespostaAutoComplete(retorno, facadeName, mensagem, classTextoautocomplete, funcaoTratarSelecaoDoAutoComplete);
        }

        return null;
    }

    public String trataRespostaAutoComplete(final Object retorno, final String facadeName, final String mensagem, final String classTextoautocomplete,
            final String funcaoTratarSelecaoDoAutoComplete) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (retorno == null) {
            return "";
        }
        final AutoCompleteReturn ret = (AutoCompleteReturn) retorno;
        final List lst = ret.getLstRetorno();
        if (lst == null || lst.size() == 0) {
            return "";
        }
        if (ret.getColumnsReturn() == null || ret.getColumnsReturn().length == 0) {
            return "";
        }
        Object obj = null;

        String strRetorno = "";
        for (int i = 0; i < lst.size(); i++) {
            obj = lst.get(i);
            final Method mtdId = CitAjaxReflexao.findMethod(ret.getColumnId(), obj);
            final Object retornoId = mtdId.invoke(obj);
            final Method mtdDesc = CitAjaxReflexao.findMethod(ret.getColumnDescription(), obj);
            Object retornoDesc = mtdDesc.invoke(obj);
            if (retornoDesc instanceof String) {
                retornoDesc = ((String) retornoDesc).replaceAll("\"", "&quot;").replaceAll("'", "&#180;");
            }

            strRetorno += "<tr class='" + classTextoautocomplete + "' style='cursor:pointer' onclick='" + funcaoTratarSelecaoDoAutoComplete + "(\"" + facadeName + "\", "
                    + retornoId + ", \"" + retornoDesc + "\")'>";
            for (int j = 0; j < ret.getColumnsReturn().length; j++) {
                final Method mtd = CitAjaxReflexao.findMethod(ret.getColumnsReturn()[j], obj);
                if (mtd != null) {
                    strRetorno += "<td>";
                    final Object retornoProp = mtd.invoke(obj);

                    strRetorno += (String) retornoProp;
                    strRetorno += "</td>";
                }
            }
            strRetorno += "</tr>";
        }
        String trMsg = "";
        if (mensagem != null && !mensagem.trim().equalsIgnoreCase("")) {
            trMsg = "<tr><td colspan='20'>" + mensagem + "</td></tr>";
        }

        return "<table>" + trMsg + strRetorno + "</table>";
    }

    public String getObjectName(final String path) {
        String strResult = "";
        boolean b = false;
        for (int i = path.length() - 1; i >= 0; i--) {
            if (b) {
                if (path.charAt(i) == '/') {
                    return strResult;
                } else {
                    strResult = path.charAt(i) + strResult;
                }
            } else {
                if (path.charAt(i) == '.') {
                    b = true;
                }
            }
        }
        return strResult;
    }

    public String getObjectExt(final String path) {
        String strResult = "";
        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '.') {
                return strResult;
            } else {
                strResult = path.charAt(i) + strResult;
            }
        }
        return strResult;
    }

}
