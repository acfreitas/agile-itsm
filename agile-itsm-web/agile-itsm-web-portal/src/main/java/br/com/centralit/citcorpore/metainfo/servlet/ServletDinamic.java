package br.com.centralit.citcorpore.metainfo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.citframework.util.UtilStrings;

public class ServletDinamic extends HttpServlet {

    private static final long serialVersionUID = -8097455253987831315L;

    private static final Logger LOGGER = Logger.getLogger(ServletDinamic.class);

    /**
     * Processa as requisicoes.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String pathInfo = request.getRequestURI();
        String ext = "";

        try {
            if (pathInfo != null) {
                // Executa um acao
                ext = this.getObjectExt(pathInfo);
                ext = ext.replaceAll("#", ""); // Evita problemas com href="#"

                // Operacoes de CRUD - Manipulacao de dados
                if ("extern".equalsIgnoreCase(ext)) {
                    final String className = request.getParameter("className").replaceAll(".class", "");

                    final Class<?> classe = Class.forName(className);
                    final Object objeto = classe.newInstance();

                    final Method mtd = CitAjaxReflexao.findMethod("execute", objeto);
                    final Object parmReals[] = new Object[2];

                    parmReals[0] = request;
                    parmReals[1] = response;

                    final Map<String, Object> map = this.getValuesFromRequest(request);
                    this.debugValuesFromRequest(map);

                    mtd.invoke(objeto, parmReals);
                    return;
                }
            }
        } catch (final Exception e) {
            try {
                final PrintWriter out = response.getWriter();
                e.printStackTrace(out);
            } catch (final Exception eX) {
                LOGGER.warn(eX.getMessage(), eX);
            }

            LOGGER.error(e.getMessage(), e);

            response.setContentType("text/html; charset=UTF-8");
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
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

    public Map<String, Object> getValuesFromRequest(final HttpServletRequest req) {
        final Enumeration<String> en = req.getParameterNames();
        String[] strValores;
        final Map<String, Object> formFields = new HashMap<>();
        while (en.hasMoreElements()) {
            final String nomeCampo = en.nextElement();
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

    private void debugValuesFromRequest(final Map<String, Object> hashValores) {
        final Set<Entry<String, Object>> set = hashValores.entrySet();
        final Iterator<Entry<String, Object>> i = set.iterator();

        LOGGER.debug("------- ServletDinamic ------ VALORES DO REQUEST: -------");
        while (i.hasNext()) {
            final Entry<String, Object> me = i.next();
            LOGGER.debug("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]");
        }
    }

}
