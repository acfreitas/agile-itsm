package br.com.citframework.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de testes para validação do comportamento de {@link PropertyFile}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 03/02/2015
 *
 */
public class PropertyFileTest {

    private static final String PROPERTY_FILE_NAME = "property-file";
    private static final String NOT_FOUND_PROPERTY_FILE_NAME = "property";

    private static final String PROPERTY_EMPTY = "property.empty";
    private static final String PROPERTY_DEFAULT_VALUE = "DefaultValue";
    private static final String PROPERTY_WITH_CONTENT = "property.with.content";
    private static final String PROPERTY_WITH_CONTENT_VALUE = "HasContent";

    private static final String UNDEFINED_KEY = "???";

    @Test
    public void testGetPropertyFound() {
        final PropertyFile pFile = PropertyFile.getPropertyFile(PROPERTY_FILE_NAME);
        Assert.assertEquals(pFile.getProperty(PROPERTY_WITH_CONTENT), PROPERTY_WITH_CONTENT_VALUE);
    }

    @Test
    public void testGetPropertyNotFound() {
        final PropertyFile pFile = PropertyFile.getPropertyFile(PROPERTY_FILE_NAME);
        Assert.assertEquals(pFile.getProperty(PROPERTY_EMPTY), UNDEFINED_KEY + PROPERTY_EMPTY + UNDEFINED_KEY);
    }

    @Test
    public void testGetPropertyNotFoundDefault() {
        final PropertyFile pFile = PropertyFile.getPropertyFile(PROPERTY_FILE_NAME);
        Assert.assertEquals(pFile.getProperty(PROPERTY_EMPTY, PROPERTY_DEFAULT_VALUE), PROPERTY_DEFAULT_VALUE);
    }

    @Test(expected = RuntimeException.class)
    public void testNotFoundPropertyFile() {
        PropertyFile.getPropertyFile(NOT_FOUND_PROPERTY_FILE_NAME);
    }

}
