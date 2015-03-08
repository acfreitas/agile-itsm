package br.com.centralit.citajax.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class AjaxProcessLoad extends AjaxFacade {

    public Collection execute(final String name) throws Exception {
        Class<?> classe = null;
        boolean bTentarLocalizarForm = true;
        int iCodigoTentativa = 1;
        while (bTentarLocalizarForm) {
            try {
                // System.out.println("Form action: " + Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
                if (iCodigoTentativa == 1) {
                    classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
                } else {
                    if (Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) == null
                            || Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa).trim().equalsIgnoreCase("")) {
                        classe = null;
                        bTentarLocalizarForm = false;
                        break;
                    }
                    classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
                }
                if (classe != null) {
                    bTentarLocalizarForm = true;
                    break;
                }
            } catch (final ClassNotFoundException e) {
                iCodigoTentativa++;
            }
        }
        if (classe == null) {
            return null;
        }

        final Object objeto = classe.newInstance();

        // Identifica os metodos a serem tratados, existentes no form.
        final Collection<Method> col1 = CitAjaxReflexao.findMethodByPalavra("onclick", objeto);
        final Collection<Method> col2 = CitAjaxReflexao.findMethodByPalavra("onClick", objeto);
        final Collection<Method> col3 = CitAjaxReflexao.findMethodByPalavra("onchange", objeto);
        final Collection<Method> col4 = CitAjaxReflexao.findMethodByPalavra("onChange", objeto);
        final Collection<Method> col5 = CitAjaxReflexao.findMethodByPalavra("onblur", objeto);
        final Collection<Method> col6 = CitAjaxReflexao.findMethodByPalavra("onBlur", objeto);

        final Collection<Method> colMetodosTratar = new ArrayList<>();
        colMetodosTratar.addAll(col1);
        colMetodosTratar.addAll(col2);
        colMetodosTratar.addAll(col3);
        colMetodosTratar.addAll(col4);
        colMetodosTratar.addAll(col5);
        colMetodosTratar.addAll(col6);

        final DocumentHTML document = new DocumentHTML();
        document.setMetodosTratamentoByMethods(colMetodosTratar);

        // Verifica se existe o metodo load, caso exista entao executa.
        final Method mtd = CitAjaxReflexao.findMethod("load", objeto);

        // Passa os valores do request para o bean
        // Pega a classe Bean associada ao FormAction
        final Method mtdGetBeanClass = CitAjaxReflexao.findMethod("getBeanClass", objeto);
        final Object objClassBean = mtdGetBeanClass.invoke(objeto);
        // Instancia o Bean
        final Object objBean = ((Class<?>) objClassBean).newInstance();
        final ParserRequest parser = new ParserRequest();
        final Map<String, Object> hashValores = parser.getFormFields(request);

        final HttpServletRequest request = this.getRequest();
        final HttpServletResponse response = this.getResponse();

        /** Incluído por valdoilo.damasceno */
        String language = UtilI18N.PORTUGUESE_SIGLA;
        if (request != null && request.getSession() != null && request.getSession().getAttribute("locale") != null) {
            language = (String) request.getSession().getAttribute("locale");
        }

        parser.converteValoresRequestToBean(hashValores, objBean, language);

        document.setBean(objBean);
        document.setValuesForm(hashValores);

        /** Incluído por valdoilo.damasceno */
        document.setLanguage(language);

        document.setIgnoreNextMethod(false);

        if (mtd != null) { // Pode ser que o form nao tenha o metodo load.
            mtd.invoke(objeto, new Object[] {document, request, response});
        }
        return document.getAllScripts();
    }

}
