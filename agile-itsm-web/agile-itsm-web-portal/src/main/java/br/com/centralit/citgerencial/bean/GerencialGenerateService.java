package br.com.centralit.citgerencial.bean;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public abstract class GerencialGenerateService {

    /**
     * O retorno deste metodo deve ser uma Lista onde cada linha da lista é uma array de Objetos. Exemplo: Object[] row
     *
     * @param parametersValues
     * @param paramtersDefinition
     * @return
     * @throws ParseException
     */
    public abstract List execute(final HashMap parametersValues, final Collection paramtersDefinition) throws ParseException;

    /**
     * Retorna a linguagem que foi passada no request que está em paramtersDefinition;
     *
     * @param paramtersDefinition
     * @return String - Language
     * @author valdoilo.damasceno
     * @since 06.02.2014
     */
    public String getLanguage(final Collection paramtersDefinition) {
        String language = UtilI18N.PORTUGUESE_SIGLA;

        for (final Iterator iterator = paramtersDefinition.iterator(); iterator.hasNext();) {
            final Object parametro = iterator.next();

            if (parametro != null && "org.apache.catalina.connector.RequestFacade".equals(parametro.getClass().getName())) {
                final HttpServletRequest request = (HttpServletRequest) parametro;

                final String aux = (String) request.getSession().getAttribute("locale");

                if (aux != null && StringUtils.isNotBlank(aux)) {
                    language = aux;
                }

                break;
            }
        }

        return language;
    }

}
