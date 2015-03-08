package br.com.citframework.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

/**
 * Classe que encapsula a recuperação de valores do {@link Properties} {@code Constantes.properties}
 */
public final class Constantes {

    private static final Logger LOGGER = Logger.getLogger(Constantes.class.getName());

    private static final Properties props = new Properties();
    private static final String CONTANTES_FILE_NAME = "Constantes.properties";

    public static String SERVER_ADDRESS = "";

    static {
        final ClassLoader load = Constantes.class.getClassLoader();

        try (InputStream is = load.getResourceAsStream(CONTANTES_FILE_NAME)) {
            if (is == null) {
                throw new RuntimeException("Arquivo de recursos nao encontrado: " + CONTANTES_FILE_NAME);
            }
            props.load(is);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    /**
     * Recupera, diretamente do arquivo de propriedades, valor associado à chave passada como argumento
     *
     * @param key
     *            chave a ser pesquisada no arquivo de propriedades
     * @return valor associado à key, caso encontrado, {@code null}, caso contrário
     */
    public static String getValue(final String key) {
        String result = null;
        if (key.equalsIgnoreCase("SERVER_ADDRESS") && StringUtils.isNotBlank(SERVER_ADDRESS)) {
            result = SERVER_ADDRESS;
        }

        result = getValueFromProperties(key);
        return result;
    }

    /**
     * Recupera, diretamente do arquivo de propriedades, valor associado à chave passada como argumento
     *
     * @param key
     *            chave a ser pesquisada no arquivo de propriedades
     * @param defaultValue
     *            valor padrão de uma chave, caso a chave não seja encontrada, seja nula ou vazia
     * @return valor associado à key, caso encontrado, {@code null}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     */
    public static String getValue(final String key, final String defaultValue) {
        final String keyValue = getValue(key);
        return StringUtils.isBlank(keyValue) ? defaultValue : keyValue;
    }

    /**
     * Recupera, diretamente do arquivo de propriedades, valor associado à chave passada como argumento
     *
     * @param key
     *            chave a ser pesquisada no arquivo de propriedades
     * @return valor associado à key, caso encontrado, {@code null}, caso contrário
     */
    public static String getValueFromProperties(final String key) {
        if (props == null) {
            return null;
        }
        return props.getProperty(key);
    }

    /**
     * Recupera, diretamente do arquivo de propriedades, valor associado à chave passada como argumento
     *
     * @param key
     *            chave a ser pesquisada no arquivo de propriedades
     * @param defaultValue
     *            valor padrão de uma chave, caso a chave não seja encontrada, seja nula ou vazia
     * @return valor associado à key, caso encontrado, {@code null}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     */
    public static String getValueFromProperties(final String key, final String defaultValue) {
        final String keyValue = getValueFromProperties(key);
        return StringUtils.isBlank(keyValue) ? defaultValue : keyValue;
    }

}
