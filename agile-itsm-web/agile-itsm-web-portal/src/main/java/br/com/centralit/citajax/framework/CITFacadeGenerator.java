package br.com.centralit.citajax.framework;

import java.util.List;

import javax.servlet.ServletContext;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.Constantes;

public class CITFacadeGenerator {

    /**
     * Processa o objeto passado como parametro e retorna uma string javascript
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String process(final String path, final ServletContext ctx) throws Exception {
        String strAux = "";
        final String javaScriptObject = this.getObjectName(path);
        if (javaScriptObject == null) {
            return null;
        }
        final Class<?> classe = Class.forName(Constantes.getValue("BEAN_LOCATION_PACKAGE") + "." + javaScriptObject);
        if (classe != null) {
            final Object objeto = classe.newInstance();
            final List<String> listaGets = CitAjaxReflexao.findGets(objeto);

            strAux = "function " + javaScriptObject + "(";
            String strAux2 = "";
            for (int i = 0; i < listaGets.size(); i++) {
                if (listaGets.get(i).equalsIgnoreCase("class")) {
                    continue;
                }
                strAux2 += "\tthis." + CitAjaxUtil.convertePrimeiraLetra(listaGets.get(i), "L") + " = function(){;\n";
                strAux2 += "\t\t";
                strAux2 += "\t}";
            }
            strAux2 += "\tthis.idControleCITFramework = null;\n";

            strAux += "){\n";
            strAux += strAux2;
            strAux += "} ";
        }

        return strAux;
    }

    public String getObjectName(final String path) {
        if (path.length() - 3 <= 0) {
            return "";
        }
        String strResult = "";
        for (int i = path.length() - 4; i >= 0; i--) {
            if (path.charAt(i) == '/') {
                return strResult;
            } else {
                strResult = path.charAt(i) + strResult;
            }
        }
        return strResult;
    }

}
