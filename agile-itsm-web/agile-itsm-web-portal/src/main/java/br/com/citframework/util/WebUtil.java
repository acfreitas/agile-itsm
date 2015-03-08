package br.com.citframework.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.dto.Usuario;

public class WebUtil {

    private static final Logger LOGGER = Logger.getLogger(WebUtil.class);

    public static void setUsuario(final Usuario usuario, final HttpServletRequest req) {
        req.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO"), usuario);
    }

    /**
     * Obtem o valor do identificador que está na requisição.
     *
     * @param request
     *            -> Objeto HttpServletRequest
     * @param identificador
     *            -> nome do indentificador a ser recuperado
     * @return
     */
    public static String getStringRequest(final HttpServletRequest request, final String identificador) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em getAttribute.
            aux = request.getParameter(identificador);
        }
        if (aux == null) {
            aux = "";
        }
        return aux;
    }

    /**
     * Obtem o valor do identificador que está na requisição para o Indice informado
     *
     * @param request
     *            -> Objeto HttpServletRequest
     * @param identificador
     *            -> nome do indentificador a ser recuperado
     * @param i
     *            -> indice a ser retornado o valor.
     * @param caracterSeparador
     *            -> caracter separador de separação do atributo que está na requisição.
     * @return
     */
    public static String getStringRequest(final HttpServletRequest request, final String identificador, final int i, final String caracterSeparador) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em getAttribute.
            aux = request.getParameter(identificador);
        }
        if (aux == null) {
            aux = "";
        }

        final String[] arrayReq = aux.split(caracterSeparador);
        if (arrayReq != null) {
            if (arrayReq.length > i) {
                return arrayReq[i];
            }
        }
        return null;
    }

    public static String getInfoIfChecked(final HttpServletRequest request, final String identificador, final String value) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em getAttribute.
            aux = request.getParameter(identificador);
        }
        if (aux == null) {
            aux = "";
        }

        if (aux.equalsIgnoreCase(value)) {
            return " checked ";
        }

        return "";
    }

    public static Usuario getUsuario(final HttpServletRequest req) {
        final Usuario user = (Usuario) req.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
        if (user == null) {
            req.getSession().invalidate();
        }
        return user;
    }

    /**
     * Deserializa um objeto obtendo os valores do request
     *
     * @param classe
     * @param name
     * @param req
     * @return
     * @throws Exception
     */
    public static Collection deserializeCollectionFromRequest(final Class<?> classe, final String name, final HttpServletRequest request) throws Exception {
        final String strParser = request.getParameter(name);
        if (strParser == null) {
            return null;
        }

        /** Alterado por valdoilo.damasceno para chamar método em que eu passe o request. */
        return deserializeCollectionFromString(classe, strParser, WebUtil.getLanguage(request));
    }

    /**
     * Deserializa uma coleção de objetos serializados em uma String. Para os campos do tipo Data é considerado a linguagem do usuário informada via parâmetro.
     *
     * @param classe
     * @param valor
     * @return Collection de objetos deserializado.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 22.02.2014
     */
    public static Collection deserializeCollectionFromString(final Class<?> classe, final String valor, final String language) throws Exception {
        final Collection col = new ArrayList<>();

        final String[] strArray = separaObjetos(valor, '\3'); // Esta string representa a colecao de objetos serializados

        if (strArray == null) {
            return null;
        }

        for (final String element : strArray) {
            final Object obj = deserializeObject(classe, element, language);
            if (obj != null) {
                col.add(obj);
            }
        }

        return col;
    }

    /**
     * Deserializa uma colecao de objetos atraves do valor passado como parametro.
     *
     * @param classe
     * @param valor
     * @return
     * @throws Exception
     */
    public static Collection deserializeCollectionFromString(final Class<?> classe, final String valor) throws Exception {
        final Collection col = new ArrayList<>();
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
    public static Object deserializeObject(final Class<?> classe, final String value) throws Exception {
        if (value == null) {
            return null;
        }
        final String[] str = separaObjetos(value, '\6'); // Quebra os atributos
        Object obj = null;
        try {
            obj = classe.newInstance();
        } catch (final Exception e) {
            LOGGER.error("Erro ao criar instancia do tipo: " + classe);
            throw e;
        }
        // Faz o tratamento dos pares propriedade=valor
        String[] propriedadesValores;
        String aux;
        if (str != null) {
            for (final String element : str) {
                propriedadesValores = separaByToken(element, '\4');
                try {
                    aux = UtilStrings.decodeCaracteresEspeciais(decodificaEnter(propriedadesValores[1]));
                    if (UtilStrings.nullToVazio(aux).equalsIgnoreCase("null")) {
                        aux = null;
                    }
                    Reflexao.setPropertyValueFromString(obj, aux, propriedadesValores[0].trim());
                } catch (final Exception e) {
                    try {
                        System.out.println("Falha ao definir atributo: " + propriedadesValores[0] + " " + e.getMessage());
                    } catch (final Exception ex) {}
                }
            }
        }
        return obj;
    }

    /**
     * Recebe a classe que deve ser deserializada e a string contendo o objeto serializado Exemplo: deserializeObject(Lotacao.class, "idFuncao\47\6idCargo\49\6...."); Onde isso
     * representa:
     * idFuncao=7;idCargo=9; Para os campos Data é considerado a linguagem do usuário logado passada por parâmetro.
     *
     * @param classe
     * @param value
     * @return Object - Objeto deserializado.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 11.02.2014
     */
    public static Object deserializeObject(final Class<?> classe, final String value, final String language) throws Exception {
        if (value == null) {
            return null;
        }

        final String[] str = separaObjetos(value, '\6'); // Quebra os atributos

        Object obj = null;

        try {
            obj = classe.newInstance();
        } catch (final Exception e) {
            LOGGER.error("Erro ao criar instancia do tipo: " + classe);
            throw e;
        }
        // Faz o tratamento dos pares propriedade=valor
        String[] propriedadesValores;
        String aux;
        if (str != null) {

            for (final String element : str) {
                propriedadesValores = separaByToken(element, '\4');

                try {
                    aux = UtilStrings.decodeCaracteresEspeciais(decodificaEnter(propriedadesValores[1]));
                    if (UtilStrings.nullToVazio(aux).equalsIgnoreCase("null")) {
                        aux = null;
                    }

                    Reflexao.setPropertyValueFromString(obj, aux, propriedadesValores[0].trim(), language);

                } catch (final Exception e) {
                    try {
                        System.out.println("Falha ao definir atributo: " + propriedadesValores[0] + " " + e.getMessage());
                    } catch (final Exception ex) {}
                }
            }
        }

        return obj;
    }

    public static String[] separaObjetos(final String str, final char token) {
        final Collection col = new ArrayList<>();
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
        final String[] strRetorno = new String[] {propriedade, valor};
        return strRetorno;
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
    public static Collection getValuesCollectionRequest(final Class<?> classe, final String name, final HttpServletRequest req) throws Exception {
        final Collection col = new ArrayList<>();
        final String[] strParser = req.getParameterValues(name);
        if (strParser == null) {
            return null;
        }
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
                    Reflexao.setPropertyValueFromString(obj, propriedadesValores[1], propriedadesValores[0]);
                } catch (final Exception e) {
                    e.printStackTrace();
                    // Deixa passar... fica sem setar este valor
                }
            }
            col.add(obj);
        }
        return col;
    }

    /**
     * Serializa uma coleção de objetos tratando os campos de Data de acordo coma linguagem do usuário passado por parâmetro. Este método também está em String
     * br.com.citframework.util.WebUtil.serializeObjects(Collection col, String language) throws Exception
     *
     * @param language
     *            - String com a linguagem do usuário.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 17.02.2014
     */
    public static String serializeObjects(final Collection col, final String language) throws Exception {
        if (col == null) {
            return "";
        }
        String result = "";
        Object obj;
        for (final Iterator it = col.iterator(); it.hasNext();) {
            obj = it.next();
            result = result + "\3\2";
            result = result + serializeObject(obj, language);
            result = result + "\5";
        }
        return result;
    }

    /**
     * Serializa um objeto em String. Os campos do tipo data são tratados de acordo com a linguagem passada por parâmetro. Este método também está em String
     * br.com.centralit.citajax.util.CitAjaxWebUtil.serializeObject(Object objeto, boolean naoCodificaApostrofeAspas, String language) throws Exception
     *
     * @param objeto
     *            - Objeto a ser serializado.
     * @param naoCodificaApostrofeAspas
     * @param language
     *            - String coma linguagem do usuário.
     * @return String do objeto serializado.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 17.02.2014
     */
    public static String serializeObject(final Object objeto, final String language) throws Exception {
        if (objeto == null) {
            return "";
        }
        String strResult = "";
        String propriedade;
        Object value;
        String valueStr;
        final List lstGets = Reflexao.findGets(objeto);
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
                        final Method m = Reflexao.findMethod("get" + propriedade + "_casasDecimais", objeto);
                        if (m != null) {
                            qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
                        }
                        valueStr = UtilFormatacao.formatDouble((Double) value, qtdeCasasDec.intValue());
                    } else if (BigDecimal.class.isInstance(value)) {
                        Integer qtdeCasasDec = new Integer(2);
                        final Method m = Reflexao.findMethod("get" + propriedade + "_casasDecimais", objeto);
                        if (m != null) {
                            qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
                        }
                        valueStr = UtilFormatacao.formatBigDecimal((BigDecimal) value, qtdeCasasDec.intValue());
                    } else {
                        valueStr = value.toString();
                    }
                    strResult = strResult + "\6\2";
                    strResult = strResult + UtilStrings.convertePrimeiraLetra(propriedade, "L") + "\4\2" + codificaEnter(valueStr) + "\5";
                    strResult = strResult + "\5";
                } else {
                    strResult = strResult + "\6\2";
                    strResult = strResult + UtilStrings.convertePrimeiraLetra(propriedade, "L") + "\4\2" + "\5";
                    strResult = strResult + "\5";
                }
            }
        }
        return strResult;
    }

    /**
     * Serializa um objeto.
     *
     * @throws Exception
     */
    public static String serializeObjects(final Collection col) throws Exception {
        if (col == null) {
            return "";
        }
        String result = "";
        Object obj;
        for (final Iterator it = col.iterator(); it.hasNext();) {
            obj = it.next();
            result = result + "\3\2";
            result = result + serializeObject(obj);
            result = result + "\5";
        }
        return result;
    }

    /*
     * Serializa um objeto em string para envio ao servidor.
     */
    public static String serializeObject(final Object objeto) throws Exception {
        if (objeto == null) {
            return "";
        }
        String strResult = "";
        String propriedade;
        Object value;
        String valueStr;
        final List lstGets = Reflexao.findGets(objeto);
        for (int i = 0; i < lstGets.size(); i++) {
            propriedade = (String) lstGets.get(i);
            if (!propriedade.equalsIgnoreCase("class")) {
                value = Reflexao.getPropertyValue(objeto, propriedade);
                if (value != null) {
                    valueStr = "";
                    if (Date.class.isInstance(value)) {
                        valueStr = UtilDatas.dateToSTR((Date) value);
                    } else if (java.util.Date.class.isInstance(value)) {
                        valueStr = UtilDatas.dateToSTR((java.util.Date) value);
                    } else if (Double.class.isInstance(value)) {
                        Integer qtdeCasasDec = new Integer(2);
                        final Method m = Reflexao.findMethod("get" + propriedade + "_casasDecimais", objeto);
                        if (m != null) {
                            qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
                        }
                        valueStr = UtilFormatacao.formatDouble((Double) value, qtdeCasasDec.intValue());
                    } else if (BigDecimal.class.isInstance(value)) {
                        Integer qtdeCasasDec = new Integer(2);
                        final Method m = Reflexao.findMethod("get" + propriedade + "_casasDecimais", objeto);
                        if (m != null) {
                            qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
                        }
                        valueStr = UtilFormatacao.formatBigDecimal((BigDecimal) value, qtdeCasasDec.intValue());
                    } else {
                        valueStr = value.toString();
                    }
                    strResult = strResult + "\6\2";
                    strResult = strResult + UtilStrings.convertePrimeiraLetra(propriedade, "L") + "\4\2" + codificaEnter(valueStr) + "\5";
                    strResult = strResult + "\5";
                } else {
                    strResult = strResult + "\6\2";
                    strResult = strResult + UtilStrings.convertePrimeiraLetra(propriedade, "L") + "\4\2" + "\5";
                    strResult = strResult + "\5";
                }
            }
        }
        return strResult;
    }

    public static String codificaEnter(final String str) {
        final String x = str.replaceAll("\r", "#10#");
        return x.replaceAll("\n", "#13#");
    }

    public static String decodificaEnter(final String str) {
        final String x = str.replaceAll("#10#", "\r");
        return x.replaceAll("#13#", "\n");
    }

    /**
     * Retorna Linguagem do session do request.
     *
     * @param request
     * @return String - language
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

    /**
     * Recupera da request a URL de chamada. Por exemplo, {@code "http://localhost:8080/path1/path2"} irá retornar {@code "http://localhost:8080"}
     *
     * @param request
     *            {@link HttpServletRequest} de onde será recuperada a URL
     * @return URL recuperada
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 20/11/2014
     */
    public static String getURLFromRequest(final HttpServletRequest request) {
        Assert.notNull(request, "Request must not be null");
        final String requestURL = request.getRequestURL().toString();
        Assert.notNullAndNotEmpty(requestURL, "Request URL must not be null or empty");
        final int index = requestURL.indexOf("/", 8);
        return requestURL.substring(0, index);
    }

}
