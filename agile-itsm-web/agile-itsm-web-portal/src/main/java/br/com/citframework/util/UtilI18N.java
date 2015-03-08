package br.com.citframework.util;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.citframework.excecao.ServiceException;

/**
 * @author CentralIT
 */
public class UtilI18N {

    private static final String KEY_MUST_NOT_BE_NULL_OR_EMPTY = "Key must not be null or empty.";
    private static final String LANGUAGE_MUST_NOT_BE_NULL = "Language/Locale must not be null.";
    private static final String REQUEST_MUST_NOT_BE_NULL = "Request must not be null.";

    public static final String ENGLISH_SIGLA = "en";
    public static final String PORTUGUESE_SIGLA = "pt";
    public static final String SPANISH_SIGLA = "es";

    private static final String MENSAGENS_PREFFIX_NAME = "Mensagens";

    private static String locale = PORTUGUESE_SIGLA;

    private static final ConcurrentMap<String, String> ENGLISH_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> PORTUGUESE_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> SPANISH_MAP = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, ConcurrentMap<String, String>> LANGUAGE_MAPS = new ConcurrentHashMap<>();

    static {
        LANGUAGE_MAPS.put(ENGLISH_SIGLA, ENGLISH_MAP);
        LANGUAGE_MAPS.put(PORTUGUESE_SIGLA, PORTUGUESE_MAP);
        LANGUAGE_MAPS.put(SPANISH_SIGLA, SPANISH_MAP);
    }

    /**
     *
     * Iniciliza o mapa de valores em português, por ser o padrão. Os outros serão carregados on demmand
     *
     */
    public static void initialize() {
        loadValuesOnMap(PORTUGUESE_SIGLA);
    }

    /**
     * @param request
     *            É necessario para idenficar o locale na sessão
     * @param key
     *            Parametro para identificar arquivo no arquivo properties
     * @return String da mensagem.
     */
    public static String internacionaliza(final HttpServletRequest request, final String key) {
        Assert.notNull(request, REQUEST_MUST_NOT_BE_NULL);
        Assert.notNullAndNotEmpty(key, KEY_MUST_NOT_BE_NULL_OR_EMPTY);

        String localKey = key.trim();

        // FIXME não deve ser tratado aqui, refazer o método internacionalizaString
        if (!localKey.startsWith("$(")) {
            localKey = key.replaceAll("\\$", "");
        }

        final HttpSession session = request.getSession(true);
        final Object sessionLocale = session.getAttribute("locale");
        if (sessionLocale != null && StringUtils.isNotBlank(sessionLocale.toString())) {
            locale = sessionLocale.toString().trim();
        }

        return getValueBySiglaLinguaAndKey(locale, localKey);
    }

    /**
     * @param locale
     *            identificador do pais ex: en (Inglês)
     * @param Key
     *            parametro de busca arquivo properties
     * @return String da mensagem.
     * @throws Exception
     * @throws ServiceException
     */
    public static String internacionaliza(final String locale, final String key) {
        Assert.notNull(locale, LANGUAGE_MUST_NOT_BE_NULL);
        Assert.notNullAndNotEmpty(key, KEY_MUST_NOT_BE_NULL_OR_EMPTY);

        final String localLocale = StringUtils.isBlank(locale) ? UtilI18N.PORTUGUESE_SIGLA : locale;

        final String value = getValueBySiglaLinguaAndKey(localLocale, key);

        return value.replaceAll("\n", "");
    }

    /**
     * Busca e formata uma mensagem em um resource
     *
     * @param locale
     *            locale
     * @param key
     *            chave de busca
     * @param params
     *            parâmetros para formatação
     * @return mensagem, se encontrada no resource, formatada com os parâmetros
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 30/09/2014
     */
    public static String internacionaliza(final String locale, final String key, final Object... params) {
        final String keyValue = internacionaliza(locale, key);
        String result = keyValue;
        if (!keyValue.contains("???")) {
            result = MessageFormat.format(keyValue, params);
        }
        return result;
    }

    public static String internacionalizaString(final String strParm, final HttpServletRequest request) {
        if (strParm == null) {
            return "";
        }
        final StringBuilder retorno = new StringBuilder();
        String strTrata = new String(strParm);
        boolean continua = true;
        while (continua) {
            final int indice = strTrata.indexOf("$");

            if (strTrata.trim().equals("")) {
                continua = false;
            }

            if (indice > -1 && !strTrata.trim().equals("")) {
                if (indice > 0) {
                    retorno.append(strTrata.substring(0, indice));
                }
                final String palavra = pegaAteFinalToken(strTrata.substring(indice));
                retorno.append(UtilI18N.internacionaliza(request, palavra));
                strTrata = strTrata.substring(indice + palavra.length());
            } else {
                continua = false;
                retorno.append(strTrata);
            }
        }
        return retorno.toString().trim();
    }

    public static ConcurrentMap<String, String> getMapLanguage(final String language) {
        final ConcurrentMap<String, String> map = LANGUAGE_MAPS.get(language);
        if (map != null && map.isEmpty()) {
            loadValuesOnMap(language);
        }
        return map;
    }

    private static String getValueBySiglaLinguaAndKey(final String siglaLingua, final String key) {
        String value = key;

        final ConcurrentMap<String, String> map = getMapLanguage(siglaLingua);
        if (map != null) {
            value = map.get(key);
            // FIXME não deve ser tratado aqui, refazer o método internacionalizaString
            if (StringUtils.isBlank(value)) {
                map.put(key, key);
                value = map.get(key);
            }
        }

        return value;
    }

    private static String pegaAteFinalToken(final String strParm) {
        if (strParm == null) {
            return "";
        }
        String palavraFormada = "";
        for (int i = 0; i < strParm.length(); i++) {
            if (strParm.charAt(i) != ' ' && strParm.charAt(i) != ':' && strParm.charAt(i) != '<' && strParm.charAt(i) != '\n' && strParm.charAt(i) != '\r'
                    && strParm.charAt(i) != '\t' && strParm.charAt(i) != '\"' && strParm.charAt(i) != '\'' && strParm.charAt(i) != '[' && strParm.charAt(i) != ']') {
                palavraFormada += strParm.charAt(i);
            } else {
                break;
            }
        }
        return palavraFormada;
    }

    private static void loadValuesOnMap(final String language) {
        final ConcurrentMap<String, String> map = LANGUAGE_MAPS.get(language);

        final PropertyFile pFile = getPropertyFile(language);

        final Properties pLanguage = pFile.getProperties();

        final Set<String> propertyKeys = pLanguage.stringPropertyNames();
        for (final String key : propertyKeys) {
            map.put(key, pFile.getProperty(key));
        }
    }

    private static PropertyFile getPropertyFile(final String language) {
        String fileName = MENSAGENS_PREFFIX_NAME;

        if (StringUtils.isNotBlank(language) && !language.trim().equalsIgnoreCase("pt")) {
            fileName = fileName + "_" + language.toLowerCase().trim();
        }

        return PropertyFile.getPropertyFile(fileName);
    }

    public static String getLocale() {
        return locale;
    }

}
