package br.com.centralit.citajax.framework;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class ParserRequest {

    private static final Logger LOGGER = Logger.getLogger(ParserRequest.class.getName());

    /**
     * Processa todos os elementos do request e gera um HashMap
     *
     * @param req
     * @return
     */
    public Map<String, Object> getFormFields(final HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (final UnsupportedEncodingException e) {
            LOGGER.log(Level.WARNING, "PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]" + e.getMessage(), e);
        }
        final Map<String, Object> formFields = new HashMap<>();
        final Enumeration<String> en = req.getParameterNames();
        String[] strValores;
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

    /**
     * Passa os valores do Hash para o Bean.
     *
     * @param valores
     *            - HashMap
     * @param bean
     *            - Object (DTO)
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public void converteValoresRequestToBean(final Map valores, final Object bean, final String language) throws Exception {
        final List<String> metodos = CitAjaxReflexao.findSets(bean);
        Object valorAtributo = null;
        Class<?>[] classParametro;
        Method mtd;
        Object retorno;
        for (int i = 0; i < metodos.size(); i++) {
            valorAtributo = valores.get(metodos.get(i).toUpperCase());
            if (valorAtributo == null) {
                continue;
            }
            if (valorAtributo instanceof String && StringUtils.isNotBlank((String) valorAtributo)) {
                mtd = CitAjaxReflexao.getSetter(bean, metodos.get(i));
                classParametro = mtd.getParameterTypes();

                try {
                    if (Integer.class.isAssignableFrom(classParametro[0])) {
                        retorno = Integer.valueOf((String) valorAtributo);
                    } else if (Integer[].class.isAssignableFrom(classParametro[0])) {
                        retorno = new Integer[] {Integer.parseInt((String) valorAtributo)};
                    } else if (Long.class.isAssignableFrom(classParametro[0])) {
                        retorno = Long.valueOf((String) valorAtributo);
                    } else if (String.class.isAssignableFrom(classParametro[0])) {
                        retorno = UtilStrings.decodeCaracteresEspeciais((String) valorAtributo);
                    } else if (Short.class.isAssignableFrom(classParametro[0])) {
                        retorno = Short.valueOf((String) valorAtributo);
                    } else if (Double.class.isAssignableFrom(classParametro[0])) {
                        /** Alterado por thyen.chang: substitui os caracteres "," por "." para fazer o parsing */
                    	String valor = ((String) valorAtributo).replaceAll("\\.", "");
                    	valor = valor.replaceAll(",", ".");
                        retorno = Double.valueOf(valor);
                    } else if (BigDecimal.class.isAssignableFrom(classParametro[0])) {
                        /** Alterado por thyen.chang: substitui os caracteres "," por "." para fazer o parsing */
                    	String valor = ((String) valorAtributo).replaceAll("\\.", "");
                    	valor = valor.replaceAll(",", ".");
                        retorno = new BigDecimal(valor);
                    } else if (Date.class.isAssignableFrom(classParametro[0])) {
                        if (valorAtributo == null || ((String) valorAtributo).equalsIgnoreCase("")) {
                            retorno = null;
                        } else {
                            retorno = UtilDatas.strToSQLDate((String) valorAtributo);
                        }
                    } else if (Timestamp.class.isAssignableFrom(classParametro[0])) {
                        retorno = CitAjaxUtil.strToTimestampWithLanguage((String) valorAtributo, language);
                    } else if (Boolean.class.isAssignableFrom(classParametro[0])) {
                        retorno = new Boolean((String) valorAtributo);
                    } else {
                        /** Adicionado por valdoilo.damasceno */
                        if (classParametro[0] != null && classParametro[0] == java.sql.Date.class && valorAtributo != null && !((String) valorAtributo).trim().equalsIgnoreCase("")) {
                            retorno = CitAjaxUtil.strToSqlDateWithLanguage((String) valorAtributo, language);
                        } else if (classParametro[0] != null && classParametro[0] == java.sql.Timestamp.class && valorAtributo != null
                                && !((String) valorAtributo).trim().equalsIgnoreCase("")) {
                            retorno = CitAjaxUtil.strToTimestampWithLanguage((String) valorAtributo, language);
                        } else {
                            retorno = CitAjaxReflexao.converteTipo((String) valorAtributo, classParametro[0]);
                        }
                    }
                } catch (final Exception e) {
                    final String message = "Erro ao converter o valor '" + valorAtributo + "' do atributo " + metodos.get(i) + " para " + classParametro[0];
                    LOGGER.log(Level.SEVERE, message, e);
                    throw new Exception(message);
                }
                try {
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), retorno);
                } catch (final Exception e) {
                    final String message = "Erro ao setar o valor '" + valorAtributo + "' no atributo " + metodos.get(i) + " do tipo " + classParametro[0];
                    LOGGER.log(Level.SEVERE, message, e);
                    throw new Exception(message);
                }
                // ELSE IF PARA ARRAY - O QUE VEIO NO REQUEST EH ARRAY!
            } else if (valorAtributo instanceof String[]) {
                mtd = CitAjaxReflexao.getSetter(bean, metodos.get(i));
                classParametro = mtd.getParameterTypes();

                if (Integer[].class.isAssignableFrom(classParametro[0])) {
                    final Integer[] arrayInteiros = new Integer[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Integer inteiro = null;
                        try {
                            inteiro = new Integer(((String[]) valorAtributo)[j]);
                        } catch (final Exception e) {
                            LOGGER.log(Level.WARNING, e.getMessage(), e);
                            inteiro = null;
                        }
                        arrayInteiros[j] = inteiro;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayInteiros);
                } else if (Long[].class.isAssignableFrom(classParametro[0])) {
                    final Long[] arrayLongos = new Long[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Long longo = null;
                        try {
                            longo = new Long(((String[]) valorAtributo)[j]);
                        } catch (final Exception e) {
                            LOGGER.log(Level.WARNING, e.getMessage(), e);
                            longo = null;
                        }
                        arrayLongos[j] = longo;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayLongos);
                } else if (String[].class.isAssignableFrom(classParametro[0])) {
                    final String[] strAux = (String[]) valorAtributo;
                    if (strAux != null) {
                        for (int iAux = 0; iAux < strAux.length; iAux++) {
                            strAux[iAux] = UtilStrings.decodeCaracteresEspeciais(strAux[iAux]);
                        }
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), strAux);
                } else if (Short[].class.isAssignableFrom(classParametro[0])) {
                    final Short[] arrayShorts = new Short[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Short curto;
                        curto = new Short(((String[]) valorAtributo)[j]);
                        arrayShorts[j] = curto;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayShorts);
                } else if (Double[].class.isAssignableFrom(classParametro[0])) {
                    final Double[] arrayDuplo = new Double[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Double duplo;

                        String aux = ((String[]) valorAtributo)[j];
                        aux = aux.replaceAll("\\.", "");
                        aux = aux.replaceAll("\\,", "\\.");

                        if (aux == null || aux.equalsIgnoreCase("")) {
                            duplo = null;
                        } else {
                            try {
                                duplo = new Double(Double.parseDouble(aux));
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                duplo = null;
                            }
                        }
                        arrayDuplo[j] = duplo;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayDuplo);
                } else if (BigDecimal[].class.isAssignableFrom(classParametro[0])) {
                    final BigDecimal[] arrayBigDec = new BigDecimal[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        BigDecimal bigDec = null;

                        String aux = ((String[]) valorAtributo)[j];
                        aux = aux.replaceAll("\\.", "");
                        aux = aux.replaceAll("\\,", "\\.");

                        try {
                            bigDec = new BigDecimal(Double.parseDouble(aux));
                        } catch (final Exception e) {
                            LOGGER.log(Level.WARNING, e.getMessage(), e);
                            bigDec = null;
                        }
                        arrayBigDec[j] = bigDec;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBigDec);
                } else if (Date[].class.isAssignableFrom(classParametro[0])) {
                    final Date[] arrayData = new Date[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Date data = null;
                        try {
                            data = new Date(UtilDatas.strToSQLDate(((String[]) valorAtributo)[j]).getTime());
                        } catch (final Exception e) {
                            LOGGER.log(Level.WARNING, e.getMessage(), e);
                            data = null;
                        }
                        arrayData[j] = data;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayData);
                } else if (Timestamp[].class.isAssignableFrom(classParametro[0])) {
                    final Timestamp[] arrayData = new Timestamp[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Timestamp data = null;
                        try {
                            data = new Timestamp(UtilDatas.strToSQLDate(((String[]) valorAtributo)[j]).getTime());
                        } catch (final Exception e) {
                            LOGGER.log(Level.WARNING, e.getMessage(), e);
                            data = null;
                        }
                        arrayData[j] = data;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayData);
                } else if (Boolean[].class.isAssignableFrom(classParametro[0])) {
                    final Boolean[] arrayBool = new Boolean[((String[]) valorAtributo).length];
                    for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                        Boolean bool;
                        bool = new Boolean(((String[]) valorAtributo)[j]);
                        arrayBool[j] = bool;
                    }
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBool);
                }
                // ELSE
            } else {
                mtd = CitAjaxReflexao.getSetter(bean, metodos.get(i));
                classParametro = mtd.getParameterTypes();
                if (classParametro[0].isInstance(valorAtributo)) {
                    CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), valorAtributo);
                }
            }
        }
    }

    public Collection converteValoresRequestToInternalBean(final Map valores, final Class<?> classe, final String nomeInternalBean) throws Exception {
        Object beanGetMethodList = null;
        try {
            beanGetMethodList = classe.newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        if (beanGetMethodList == null) {
            return null;
        }
        final List<String> metodos = CitAjaxReflexao.findSets(beanGetMethodList);
        Object valorAtributo = null;
        Class<?>[] classParametro;
        Method mtd;
        Object retorno;

        final Collection<Object> col = new ArrayList<>();

        final String str = (String) valores.get(nomeInternalBean.toUpperCase() + "_ULTIMO_SEQUENCIAL_HIDDEN");
        int iQtde = 0;
        if (str != null && !str.equalsIgnoreCase("")) {
            iQtde = Integer.parseInt(str);
        }
        for (int x = 0; x <= iQtde; x++) {
            Object bean = null;
            try {
                bean = classe.newInstance();
            } catch (final InstantiationException | IllegalAccessException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
            if (bean == null) {
                continue;
            }
            boolean somenteNulos = true;
            for (int i = 0; i < metodos.size(); i++) {
                valorAtributo = valores.get(nomeInternalBean.toUpperCase() + "." + metodos.get(i).toUpperCase() + "." + x);
                if (valorAtributo == null) {
                    continue;
                }
                somenteNulos = false;
                if (valorAtributo instanceof String) {
                    mtd = CitAjaxReflexao.getSetter(bean, metodos.get(i));
                    classParametro = mtd.getParameterTypes();

                    try {
                        if (Integer[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE INTEIROS
                            retorno = new Integer[] {new Integer((String) valorAtributo)};
                        } else if (Long[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE LONGOS
                            retorno = new Long[] {new Long((String) valorAtributo)};
                        } else if (String[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE STRINGS
                            retorno = new String[] {new String(UtilStrings.decodeCaracteresEspeciais((String) valorAtributo))};
                        } else if (Short[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE SHORTS
                            retorno = new Short[] {new Short((String) valorAtributo)};
                        } else if (Double[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE DOUBLES
                            String aux = (String) valorAtributo;
                            aux = aux.replaceAll("\\,", "\\.");

                            Double duplo = null;
                            try {
                                duplo = new Double(Double.parseDouble(aux));
                                retorno = new Double[] {new Double(duplo.doubleValue())};
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                duplo = null;
                                retorno = new Double[] {null};
                            }

                        } else if (BigDecimal[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE BIGDECIMAL
                            String aux = (String) valorAtributo;
                            aux = aux.replaceAll("\\,", "\\.");

                            BigDecimal big = null;
                            try {
                                big = new BigDecimal(Double.parseDouble(aux));
                                retorno = new BigDecimal[] {new BigDecimal(big.doubleValue())};
                            } catch (final Exception e) {
                                retorno = new BigDecimal[] {null};
                            }

                        } else if (Date[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE DATES
                            Date data = null;

                            if (valorAtributo == null || ((String) valorAtributo).equalsIgnoreCase("")) {
                                data = null;
                                retorno = new Date[] {null};
                            } else {
                                data = new Date(UtilDatas.strToSQLDate((String) valorAtributo).getTime());
                                retorno = new Date[] {new Date(data.getTime())};
                            }
                        } else if (Boolean[].class.isAssignableFrom(classParametro[0])) { // ARRAY DE BOOLEANS
                            retorno = new Boolean[] {new Boolean((String) valorAtributo)};
                        } else { // NAO EH ARRAY
                            retorno = CitAjaxReflexao.converteTipo((String) valorAtributo, classParametro[0]);
                        }
                    } catch (final Exception e) {
                        final String message = "Erro ao converter o valor '" + valorAtributo + "' do atributo " + metodos.get(i) + " para " + classParametro[0];
                        LOGGER.log(Level.SEVERE, message, e);
                        throw new Exception(message);
                    }
                    try {
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), retorno);
                    } catch (final Exception e) {
                        final String message = "Erro ao setar o valor '" + valorAtributo + "' no atributo " + metodos.get(i) + " do tipo " + classParametro[0];
                        LOGGER.log(Level.SEVERE, message, e);
                        throw new Exception(message);

                    }
                } else if (valorAtributo instanceof String[]) {
                    mtd = CitAjaxReflexao.getSetter(bean, metodos.get(i));
                    classParametro = mtd.getParameterTypes();

                    if (Integer[].class.isAssignableFrom(classParametro[0])) {
                        final Integer[] arrayInteiros = new Integer[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            Integer inteiro = null;
                            try {
                                inteiro = Integer.valueOf(((String[]) valorAtributo)[j]);
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                inteiro = null;
                            }
                            arrayInteiros[j] = inteiro;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayInteiros);
                    } else if (Long[].class.isAssignableFrom(classParametro[0])) {
                        final Long[] arrayLongos = new Long[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            Long longo = null;
                            try {
                                longo = Long.valueOf(((String[]) valorAtributo)[j]);
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                longo = null;
                            }
                            arrayLongos[j] = longo;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayLongos);
                    } else if (String[].class.isAssignableFrom(classParametro[0])) {
                        final String[] strAux = (String[]) valorAtributo;
                        if (strAux != null) {
                            for (int iAux = 0; iAux < strAux.length; iAux++) {
                                strAux[iAux] = UtilStrings.decodeCaracteresEspeciais(strAux[iAux]);
                            }
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), strAux);
                    } else if (Short[].class.isAssignableFrom(classParametro[0])) {
                        final Short[] arrayShorts = new Short[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            Short curto;
                            curto = Short.valueOf(((String[]) valorAtributo)[j]);
                            arrayShorts[j] = curto;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayShorts);
                    } else if (Double[].class.isAssignableFrom(classParametro[0])) {
                        final Double[] arrayDuplo = new Double[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            Double duplo;

                            String aux = ((String[]) valorAtributo)[j];
                            aux = aux.replaceAll("\\,", "\\.");

                            if (aux == null || aux.equalsIgnoreCase("")) {
                                duplo = null;
                            } else {
                                try {
                                    duplo = Double.valueOf(aux);
                                } catch (final Exception e) {
                                    LOGGER.log(Level.WARNING, e.getMessage(), e);
                                    duplo = null;
                                }
                            }
                            arrayDuplo[j] = duplo;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayDuplo);
                    } else if (BigDecimal[].class.isAssignableFrom(classParametro[0])) {
                        final BigDecimal[] arrayBigDec = new BigDecimal[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            BigDecimal bigDec = null;

                            String aux = ((String[]) valorAtributo)[j];
                            aux = aux.replaceAll("\\,", "\\.");

                            try {
                                bigDec = new BigDecimal(aux);
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                bigDec = null;
                            }
                            arrayBigDec[j] = bigDec;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBigDec);
                    } else if (Date[].class.isAssignableFrom(classParametro[0])) {
                        final Date[] arrayData = new Date[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            Date data = null;
                            try {
                                data = new Date(UtilDatas.strToSQLDate(((String[]) valorAtributo)[j]).getTime());
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                data = null;
                            }
                            arrayData[j] = data;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayData);
                    } else if (Boolean[].class.isAssignableFrom(classParametro[0])) {
                        final Boolean[] arrayBool = new Boolean[((String[]) valorAtributo).length];
                        for (int j = 0; j < ((String[]) valorAtributo).length; j++) {
                            Boolean bool;
                            bool = Boolean.valueOf(((String[]) valorAtributo)[j]);
                            arrayBool[j] = bool;
                        }
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBool);
                    }
                } else {
                    mtd = CitAjaxReflexao.getSetter(bean, metodos.get(i));
                    classParametro = mtd.getParameterTypes();
                    if (classParametro[0].isInstance(valorAtributo)) {
                        CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), valorAtributo);
                    }
                }
            }
            if (!somenteNulos) {
                col.add(bean);
            }
        }
        return col;
    }

}
