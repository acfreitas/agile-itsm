package br.com.citframework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Classe de testes para validação do comportamento de {@link UtilI18N}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 04/02/2015
 *
 */
@RunWith(value = MockitoJUnitRunner.class)
public class UtilI18NTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private static final String LOCALE = "locale";

    private static final String PROPERTY_KEY = "citcorpore.comum.ano";
    private static final String PROPERTY_NOTFOUND_KEY = "property.notfound.key";

    private static final String EXPECTED_PROPERTY_VALUE_EN = "Year";
    private static final String EXPECTED_PROPERTY_VALUE_ES = "Año";
    private static final String EXPECTED_PROPERTY_VALUE_PT = "Ano";
    private static final String EXPECTED_PROPERTY_NOTFOUND_VALUE = "property.notfound.key";

    private static final String UNKNOWN_SIGLA = "UK";

    @Test
    public void testInternacionalizaLocaleStringEN() {
        final String value = UtilI18N.internacionaliza(UtilI18N.ENGLISH_SIGLA, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_EN, value);
    }

    @Test
    public void testInternacionalizaLocaleStringES() {
        final String value = UtilI18N.internacionaliza(UtilI18N.SPANISH_SIGLA, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_ES, value);
    }

    @Test
    public void testInternacionalizaLocaleStringPT() {
        final String value = UtilI18N.internacionaliza(UtilI18N.PORTUGUESE_SIGLA, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_PT, value);
    }

    @Test
    public void testInternacionalizaEmptyLocaleString() {
        final String value = UtilI18N.internacionaliza("", PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_PT, value);
    }

    @Test
    public void testInternacionalizaUnknownLocaleString() {
        final String value = UtilI18N.internacionaliza(UNKNOWN_SIGLA, PROPERTY_KEY);
        Assert.assertEquals(PROPERTY_KEY, value);
    }

    @Test
    public void testInternacionalizaLocaleRequestEN() {
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(session.getAttribute(LOCALE)).thenReturn(UtilI18N.ENGLISH_SIGLA);

        final String value = UtilI18N.internacionaliza(request, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_EN, value);
    }

    @Test
    public void testInternacionalizaLocaleRequestES() {
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(session.getAttribute(LOCALE)).thenReturn(UtilI18N.SPANISH_SIGLA);

        final String value = UtilI18N.internacionaliza(request, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_ES, value);
    }

    @Test
    public void testInternacionalizaLocaleRequestPT() {
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(session.getAttribute(LOCALE)).thenReturn(UtilI18N.PORTUGUESE_SIGLA);

        final String value = UtilI18N.internacionaliza(request, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_PT, value);
    }

    @Test
    public void testInternacionalizaEmptyLocaleRequest() {
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(session.getAttribute(LOCALE)).thenReturn("");

        final String value = UtilI18N.internacionaliza(request, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_PT, value);
    }

    @Test
    public void testInternacionalizaUnknownLocaleRequest() {
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(session.getAttribute(LOCALE)).thenReturn(UNKNOWN_SIGLA);

        final String value = UtilI18N.internacionaliza(request, PROPERTY_KEY);
        Assert.assertEquals(PROPERTY_KEY, value);
    }

    @Test
    public void testInternacionalizaNullLocaleRequest() {
        Mockito.when(request.getSession(true)).thenReturn(session);

        final String value = UtilI18N.internacionaliza(request, PROPERTY_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_VALUE_PT, value);
    }

    @Test
    public void testInternacionalizaNotFoundKey() {
        final String value = UtilI18N.internacionaliza(UtilI18N.PORTUGUESE_SIGLA, PROPERTY_NOTFOUND_KEY);
        Assert.assertEquals(EXPECTED_PROPERTY_NOTFOUND_VALUE, value);
    }

    @Test
    public void testInternacionalizaWithParams() {
        final String expected = "1 is not a valid value for latitude.";
        final String value = UtilI18N.internacionaliza(UtilI18N.ENGLISH_SIGLA, "geographic.latitude.invalid", 1);
        Assert.assertEquals(expected, value);
    }

    @Test
    public void testInternacionalizaNotFoundKeyWithParams() {
        final String value = UtilI18N.internacionaliza(UtilI18N.ENGLISH_SIGLA, PROPERTY_NOTFOUND_KEY, 1);
        Assert.assertEquals(EXPECTED_PROPERTY_NOTFOUND_VALUE, value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInternacionalizLocaleStringKeyIsNull() {
        UtilI18N.internacionaliza(UtilI18N.PORTUGUESE_SIGLA, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInternacionalizLocaleRequestKeyIsNull() {
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(session.getAttribute(LOCALE)).thenReturn(UtilI18N.SPANISH_SIGLA);

        UtilI18N.internacionaliza(request, null);
    }

}
