package br.com.centralit.citajax.framework;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class AjaxProcessEvent extends AjaxFacade {

    private static final Logger LOGGER = Logger.getLogger(AjaxProcessEvent.class);

    public Collection execute(final String formNameParm, final String name, String evento) throws Exception {
        Class<?> classe = null;
        boolean bTentarLocalizarForm = true;
        String formName = formNameParm;
        if (formName == null) {
            formName = "";
        }
        formName = formName.replaceAll("\\.load", "");
        int iCodigoTentativa = 1;
        while (bTentarLocalizarForm) {
            try {
                if (iCodigoTentativa == 1) {
                    classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
                } else {
                    if (StringUtils.isBlank(Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa))) {
                        classe = null;
                        bTentarLocalizarForm = false;
                        break;
                    }
                    classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) + "." + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
                }
                if (classe != null) {
                    bTentarLocalizarForm = true;
                    break;
                }
            } catch (final ClassNotFoundException | StringIndexOutOfBoundsException e) {
                iCodigoTentativa++;
            }
        }
        if (classe == null) {
            LOGGER.warn("Form não encontrado: " + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
            return null;
        }
        final Object objeto = classe.newInstance();

        final HttpServletRequest request = this.getRequest();
        final HttpServletResponse response = this.getResponse();

        // Pega a classe Bean associada ao FormAction
        final Method mtdGetBeanClass = CitAjaxReflexao.findMethod("getBeanClass", objeto);
        final Object objClassBean = mtdGetBeanClass.invoke(objeto);
        // Instancia o Bean
        final Object objBean = ((Class<?>) objClassBean).newInstance();
        // Passa os valores do request para o bean
        final ParserRequest parser = new ParserRequest();
        final Map<String, Object> hashValores = parser.getFormFields(request);

        /** Incluído por valdoilo.damasceno */
        String language = UtilI18N.PORTUGUESE_SIGLA;
        if (request != null && request.getSession() != null && request.getSession().getAttribute("locale") != null) {
            language = (String) request.getSession().getAttribute("locale");
        }

        parser.converteValoresRequestToBean(hashValores, objBean, language);

        // Cria o document e associa o bean.
        final DocumentHTML document = new DocumentHTML();
        document.setBean(objBean);
        document.setValuesForm(hashValores);

        /** Incluído por valdoilo.damasceno */
        document.setLanguage(language);

        document.setIgnoreNextMethod(false);

        String eventoOrig = evento;
        Method mtd = null;
        mtd = CitAjaxReflexao.findMethod(evento, objeto);
        if (mtd == null) {
            evento = evento.toLowerCase();
            mtd = CitAjaxReflexao.findMethod(name + "_on" + evento, objeto);
        }

        eventoOrig = CitAjaxUtil.convertePrimeiraLetra(eventoOrig, "U");

        if (mtd != null) {
            mtd.invoke(objeto, new Object[] {document, request, response});
            return document.getAllScripts();
        } else {
            mtd = CitAjaxReflexao.findMethod(name + "_on" + CitAjaxUtil.convertePrimeiraLetra(evento, "U"), objeto);
            if (mtd != null) {
                mtd.invoke(objeto, new Object[] {document, request, response});
                return document.getAllScripts();
            }
        }

        return null;
    }

}
