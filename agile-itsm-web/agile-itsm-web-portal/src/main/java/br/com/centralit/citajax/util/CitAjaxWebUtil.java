package br.com.centralit.citajax.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CitAjaxWebUtil {

    private static final Logger LOGGER = Logger.getLogger(CitAjaxWebUtil.class);

    /**
     * Deserializa um objeto obtendo os valores do request
     *
     * @param classe
     * @param name
     * @param req
     * @return
     * @throws Exception
     */
    public static Collection deserializeCollectionFromRequest(final Class classe, final String name, final HttpServletRequest req) throws Exception {
        final String strParser = req.getParameter(name);
        if (strParser == null) {
            return null;
        }
        return deserializeCollectionFromString(classe, strParser);
    }

    /**
     * Deserializa uma colecao de objetos atraves do valor passado como parametro.
     *
     * @param classe
     * @param valor
     * @return
     * @throws Exception
     */
    public static Collection deserializeCollectionFromString(final Class classe, final String valor) throws Exception {
        final Collection col = new ArrayList();
        final String[] strArray = separaObjetos(valor, '\3'); // Esta string representa a colecao de objetos serializados
        if (strArray == null) {
            return null;
        }
        for (final String element : strArray) {
            final Object obj = deserializeObject(classe, element);
            if (obj != null) {
                col.add(obj);
            }
        }
        return col;
    }

    /**
     * Recebe a classe que deve ser deserializada e a string contendo o objeto serializado
     * Exemplo: deserializeObject(Lotacao.class, "idFuncao\47\6idCargo\49\6....");
     * Onde isso representa: idFuncao=7;idCargo=9;
     *
     * @param classe
     * @param value
     * @return
     * @throws Exception
     */
    public static Object deserializeObject(final Class classe, final String value) throws Exception {
        if (value == null) {
            return null;
        }
        final String[] str = separaObjetos(value, '\6'); // Quebra os atributos
        Object obj = null;
        try {
            obj = classe.newInstance();
        } catch (final InstantiationException e1) {
            throw new Exception("Erro ao instanciar a classe (1)!");
        } catch (final IllegalAccessException e1) {
            throw new Exception("Erro ao instanciar a classe (2)!");
        }
        // Faz o tratamento dos pares propriedade=valor
        String[] propriedadesValores;
        String aux;
        if (str != null) {
            for (final String element : str) {
                propriedadesValores = separaByToken(element, '\4');
                try {
                    aux = decodificaAspasApostrofe(decodificaEnter(propriedadesValores[1]));
                    CitAjaxReflexao.setPropertyValueFromString(obj, aux, propriedadesValores[0]);
                } catch (final Exception e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }
        }
        return obj;
    }

    public static String[] separaObjetos(final String str, final char token) {
        final Collection col = new ArrayList();
        String obj = null;
        boolean bIniciou = false;
        int qtdeChaveAberta = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == token && qtdeChaveAberta == 0) {
                if (obj != null) {
                    col.add(obj);
                }
                obj = new String("");
            } else {
                if (str.charAt(i) == '\5') {
                    qtdeChaveAberta--;
                }
                if (bIniciou) {
                    if (obj != null && qtdeChaveAberta > 0) {
                        obj += str.charAt(i);
                    }
                }
                if (str.charAt(i) == '\2') {
                    bIniciou = true;
                    qtdeChaveAberta++;
                }
            }
        }
        if (obj != null) {
            col.add(obj);
        }
        String[] ret = null;
        if (col.size() > 0) {
            ret = new String[col.size()];
        }
        int i = 0;
        for (final Iterator it = col.iterator(); it.hasNext(); i++) {
            ret[i] = (String) it.next();
        }
        return ret;
    }

    /**
     * Esta funcao quebra os tokens de objetos.
     * Ele deve ser usada no lugar do Split pois podem existir objetos dentro de objetos.
     *
     * @param str
     * @param token
     * @return
     */
    public static String[] separaByToken(final String str, final char token) {
        String propriedade = "";
        String valor = "";
        boolean bProp = true;
        boolean bIniciou = false;
        int qtdeChaveAberta = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == token) {
                bProp = false;
            }
            if (bProp) {
                propriedade += str.charAt(i);
            } else {
                if (str.charAt(i) == '\5') {
                    qtdeChaveAberta--;
                    if (qtdeChaveAberta == 0) {
                        break;
                    }
                }
                if (bIniciou && qtdeChaveAberta > 0) {
                    valor += str.charAt(i);
                }
                if (str.charAt(i) == '\2') {
                    bIniciou = true;
                    qtdeChaveAberta++;
                }
            }
        }
        return new String[] {propriedade, valor};
    }

    /**
     * Este metodo foi mantido para suportar utilizacoes anteriores.
     * Recebe valores separados por = e propriedades separadas por ;
     *
     * @param classe
     * @param name
     * @param req
     * @return
     * @throws Exception
     */
    public static Collection getValuesCollectionRequest(final Class classe, final String name, final HttpServletRequest req) throws Exception {
        final Collection col = new ArrayList();
        final String[] strParser = req.getParameterValues(name);

        for (final String element : strParser) {
            final String[] str = element.split(";");
            String[] propriedadesValores;

            Object obj;
            try {
                obj = classe.newInstance();
            } catch (final InstantiationException e1) {
                throw new Exception("Erro ao instanciar a classe (1)!");
            } catch (final IllegalAccessException e1) {
                throw new Exception("Erro ao instanciar a classe (2)!");
            }
            // Faz o tratamento dos pares propriedade=valor
            for (final String element2 : str) {
                propriedadesValores = element2.split("=");
                try {
                    CitAjaxReflexao.setPropertyValueFromString(obj, propriedadesValores[1], propriedadesValores[0]);
                } catch (final Exception e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }
            col.add(obj);
        }
        return col;
    }

    /**
     * Serializa uma coleção de objetos tratando os campos de Data de acordo coma linguagem do usuário passado por parâmetro.
     *
     * @param language
     *            - String com a linguagem do usuário.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 11.02.2014
     */
    public static String serializeObjects(final Collection col, final boolean naoCodificaApostrofeAspas, final String language) throws Exception {
        if (col == null) {
            return "";
        }
        String result = "";
        Object obj;
        for (final Iterator it = col.iterator(); it.hasNext();) {
            obj = it.next();
            result = result + "\3\2";
            result = result + serializeObject(obj, naoCodificaApostrofeAspas, language);
            result = result + "\5";
        }
        return result;
    }

    /**
     * Serializa um objeto em String. Os campos do tipo data são tratados de acordo com a linguagem passada por parâmetro.
     *
     * @param objeto
     *            - Objeto a ser serializado.
     * @param naoCodificaApostrofeAspas
     * @param language
     *            - String coma linguagem do usuário.
     * @return String do objeto serializado.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 11.02.2014
     */
    public static String serializeObject(final Object objeto, final boolean naoCodificaApostrofeAspas, final String language) throws Exception {
        if (objeto == null) {
            return "";
        }
        String strResult = "";
        String propriedade;
        Object value;
        String valueStr;
        final List lstGets = CitAjaxReflexao.findGets(objeto);
        for (int i = 0; i < lstGets.size(); i++) {
            propriedade = (String) lstGets.get(i);
            if (!propriedade.equalsIgnoreCase("class")) {
                value = Reflexao.getPropertyValue(objeto, propriedade);
                if (value != null) {
                    valueStr = "";

                    if (Date.class.isInstance(value)) {
                        valueStr = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, (Date) value, language);
                    } else if (java.util.Date.class.isInstance(value)) {
                        valueStr = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, (java.util.Date) value, language);
                    } else if (Double.class.isInstance(value)) {
                        Integer qtdeCasasDec = new Integer(2);
                        final Method m = Reflexao.findMethod(propriedade + "_casasDecimais", objeto);
                        if (m != null) {
                            qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
                        }
                        valueStr = UtilFormatacao.formatDouble((Double) value, qtdeCasasDec.intValue());
                    } else if (BigDecimal.class.isInstance(value)) {
                        Integer qtdeCasasDec = new Integer(2);
                        final Method m = Reflexao.findMethod(propriedade + "_casasDecimais", objeto);
                        if (m != null) {
                            qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
                        }
                        valueStr = UtilFormatacao.formatBigDecimal((BigDecimal) value, qtdeCasasDec.intValue());
                    } else {
                        valueStr = value.toString();
                    }
                    strResult = strResult + "\6\2";
                    if (naoCodificaApostrofeAspas) {
                        strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + codificaEnter(valueStr) + "\5";
                    } else {
                        strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + JavaScriptUtil.escapeJavaScript(codificaEnter(valueStr)) + "\5";
                    }
                    strResult = strResult + "\5";
                } else {
                    strResult = strResult + "\6\2";
                    strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + "\5";
                    strResult = strResult + "\5";
                }
            }
        }
        return strResult;
    }

    public static String codificaEnter(final String str) {
        if (str == null) {
            return "";
        }
        final String x = str.replaceAll("\r", "#10#");
        return x.replaceAll("\n", "#13#");
    };

    public static String codificaAspasApostrofe(final String str) {
        if (str == null) {
            return "";
        }
        final String x = str.replaceAll("\"", "#32#");
        return x.replaceAll("\'", "#33#");
    }

    public static String decodificaEnter(final String str) {
        if (str == null) {
            return "";
        }
        final String x = str.replaceAll("#10#", "\r");
        return x.replaceAll("#13#", "\n");
    };

    public static String decodificaAspasApostrofe(final String str) {
        if (str == null) {
            return "";
        }
        final String x = str.replaceAll("#32#", "\"");
        return x.replaceAll("#33#", "\'");
    }

    public static String codificaEnterByChar(final String str, final String charCode) {
        if (str == null) {
            return "";
        }
        final String x = str.replaceAll("\r", charCode);
        return x.replaceAll("\n", charCode);
    };

    /**
     * Retorna Linguagem do session do request.
     *
     * @param request
     *            - HttpServletRequest
     * @return language - String
     * @author valdoilo.damasceno
     * @since 04.02.2014
     */
    public static String getLanguage(final HttpServletRequest request) {
        String language = UtilI18N.PORTUGUESE_SIGLA;
        if (request != null && request.getSession() != null && request.getSession().getAttribute("locale") != null) {
            language = (String) request.getSession().getAttribute("locale");
        }
        return language.trim();
    }

}
