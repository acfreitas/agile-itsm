package br.com.citframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

/**
 * Helper que representa um arquivo {@code .properties}
 *
 * @author Bruno César Ribeiro e Silva - <a href="mailto:bruno@brunocesar.com">bruno@brunocesar.com</a>
 */
public final class PropertyFile {

    private Properties properties;

    private final String fileName;

    private static final String UNDEFINED_KEY = "???";

    private static final ConcurrentMap<String, PropertyFile> PROPERTIES = new ConcurrentHashMap<>();

    /**
     * Constrói um arquivo de propriedades, de acordo com o nome do arquivo
     *
     * @param fileName
     *            nome do arquivo, sem extensão
     * @return {@link PropertyFile}
     */
    public static PropertyFile getPropertyFile(final String fileName) {
        PropertyFile pFile = PROPERTIES.get(fileName);
        if (pFile == null) {
            pFile = new PropertyFile(fileName);
            PROPERTIES.put(fileName, pFile);
        }
        return pFile;
    }

    /**
     * Constrói uma configuração, de acordo com o nome do arquivo
     *
     * @param fileName
     *            nome do arquivo, sem extensão
     */
    private PropertyFile(final String fileName) {
        this.fileName = fileName;
        this.loadConfiguration();
    }

    /**
     * Recupera o arquivo de propriedades da instância de {@link PropertyFile}
     * 
     * @return {@link Properties}
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Recupera de um {@code .properties} o valor da propriedade correspondente à chave informada
     *
     * @param key
     *            a chave do valor recuperado
     * @return o valor correspondente à chave ou <code>key</code> se a chave não for encontrada.
     */
    public String getProperty(final String key) {
        return this.getProperty(key, null);
    }

    /**
     * Recupera de um {@code .properties} o valor da propriedade correspondente à chave informada
     *
     * @param key
     *            a chave do valor recuperado
     * @param defaultValue
     *            valor padrão caso não exista a chave no arquivo de propriedades
     * @return o valor correspondente à chave ou <code>defaultValue</code> se a chave não for encontrada.
     */
    public String getProperty(final String key, final String defaultValue) {
        String property = properties.getProperty(key);
        if (StringUtils.isBlank(property) && StringUtils.isBlank(defaultValue)) {
            property = UNDEFINED_KEY + key + UNDEFINED_KEY;
        } else if (StringUtils.isBlank(property) && StringUtils.isNotBlank(defaultValue)) {
            property = defaultValue;
        }
        return property;
    }

    /**
     * Recupera o nome do arquivo de configuração, já com a extensão {@code .properties}
     *
     * @return {@link String} nome do arquivo de configuração
     */
    private String getConfigurationFileName() {
        return "/" + fileName + ".properties";
    }

    /**
     * Carrega do classpath o arquivo de configuração
     */
    private void loadConfiguration() {
        properties = new Properties();
        try (final InputStream is = PropertyFile.class.getResourceAsStream(this.getConfigurationFileName())) {
            if (is == null) {
                throw new IOException("Arquivo de configuração " + this.getConfigurationFileName() + " não pôde ser encontrado.");
            }
            properties.load(is);
        } catch (final IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
