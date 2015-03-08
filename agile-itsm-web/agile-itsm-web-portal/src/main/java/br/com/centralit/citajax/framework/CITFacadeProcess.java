package br.com.centralit.citajax.framework;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes"})
public class CITFacadeProcess {

    public static final String CARACTER_SEPARADOR = "\5";

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
        String parmCount = "0";
        Class classe = null;
        if ("load".equalsIgnoreCase(ext)) {
            classe = AjaxProcessLoad.class;
            metodo = "execute";
            parmCount = "1";
            request.setAttribute("parm1", facadeName);
        } else if ("save".equalsIgnoreCase(ext)) {
            classe = AjaxProcessEvent.class;
            metodo = "execute";
            parmCount = "3";
            request.setAttribute("parm1", facadeName);
            request.setAttribute("parm2", facadeName);
            request.setAttribute("parm3", "save");
        } else if ("restore".equalsIgnoreCase(ext)) {
            classe = AjaxProcessEvent.class;
            metodo = "execute";
            parmCount = "3";
            request.setAttribute("parm1", facadeName);
            request.setAttribute("parm2", facadeName);
            request.setAttribute("parm3", "restore");
        } else if ("event".equalsIgnoreCase(ext)) {
            classe = AjaxProcessEvent.class;
            metodo = "execute";
            parmCount = "3";
            request.setAttribute("parm1", facadeName);
            request.setAttribute("parm2", facadeName);
            request.setAttribute("parm3", "event");
        } else {
            boolean bTentarLocalizarForm = true;
            int iCodigoTentativa = 1;
            while (bTentarLocalizarForm) {
                try {
                    if (iCodigoTentativa == 1) {
                        classe = Class.forName(Constantes.getValue("FRAMEWORK_LOCATION_FACADE") + "." + facadeName);
                    } else {
                        if (Constantes.getValue("FRAMEWORK_LOCATION_FACADE" + iCodigoTentativa) == null) {
                            classe = null;
                            bTentarLocalizarForm = false;
                            break;
                        }
                        classe = Class.forName(Constantes.getValue("FRAMEWORK_LOCATION_FACADE" + iCodigoTentativa) + "." + facadeName);
                    }
                } catch (final ClassNotFoundException e) {
                    iCodigoTentativa++;
                }
            }
            if (classe == null) {
                System.out.println("Classe: " + facadeName);
                return null;
            }
            metodo = request.getParameter("method");
            parmCount = request.getParameter("parmCount");
        }

        if (classe != null) {
            final Object objeto = classe.newInstance();

            if (parmCount == null) {
                parmCount = "0";
            }

            int iParmCount = 0;
            iParmCount = Integer.parseInt(parmCount);
            Object parmReals[] = null;
            final Method mtd = CitAjaxReflexao.findMethod(metodo, objeto);
            if (iParmCount > 0) {
                final String parms[] = new String[iParmCount];
                for (int i = 1; i <= iParmCount; i++) {
                    try {
                        parms[i - 1] = null;
                        parms[i - 1] = request.getParameter("parm" + i);
                    } catch (final Exception e) {
                        // TODO: handle exception
                        // não é para printar pois este tratamento é para o jboss7
                    }

                    if (parms[i - 1] == null) {
                        parms[i - 1] = (String) request.getAttribute("parm" + i);
                    }
                }
                parmReals = new Object[iParmCount];

                final Class[] parmTypes = mtd.getParameterTypes();
                for (int i = 0; i < parmTypes.length; i++) {
                    parmReals[i] = CitAjaxReflexao.converteTipo(parms[i], parmTypes[i]);
                }
            }
            final Method mtdRequest = CitAjaxReflexao.findMethod("setRequest", objeto);
            final Method mtdResponse = CitAjaxReflexao.findMethod("setResponse", objeto);

            mtdRequest.invoke(objeto, new Object[] {request});
            mtdResponse.invoke(objeto, new Object[] {response});

            final Object retorno = mtd.invoke(objeto, parmReals);
            if (retorno instanceof Collection) {
                return CitAjaxWebUtil.serializeObjects((Collection) retorno, true, CitAjaxWebUtil.getLanguage(request));
            } else if (retorno instanceof String) {
                return (String) retorno;
            } else if (retorno instanceof Integer) {
                final Integer retornoInteger = (Integer) retorno;
                return retornoInteger.toString();
            } else if (retorno instanceof Long) {
                final Long retornoLong = (Long) retorno;
                return retornoLong.toString();
            } else if (retorno instanceof Double) {
                final Double retornoDouble = (Double) retorno;
                return retornoDouble.toString();
            } else if (retorno instanceof Float) {
                final Float retornoFloat = (Float) retorno;
                return retornoFloat.toString();
            }
            return CitAjaxWebUtil.serializeObject(retorno, true, CitAjaxWebUtil.getLanguage(request));
        }

        return null;
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
