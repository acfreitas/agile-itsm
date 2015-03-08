package br.com.centralit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;

/**
 * Utilitários a serem usados nos testes utilitários
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 *
 */
public class UnitTestUtils {

    /**
     * {@link Assert#assertEquals(Object, Object)} "in two ways" {@link Assert#assertEquals(Object, Object)} dos {@code hashCode()} dos objetos
     *
     * @param first
     *            first object to assert
     * @param second
     *            second object to assert
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 01/10/2014
     */
    public static void assertEqualsAndHashcode(final Object first, final Object second) {
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
    }

    /**
     * {@link Assert#assertFalse(boolean)} de {@code equals()} "in two ways" e {@link Assert#assertFalse(boolean)} do {@code hashCode()} dos objetos
     *
     * @param first
     *            first object to assert
     * @param second
     *            second object to assert
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 01/10/2014
     */
    public static void assertNotEqualsAndHashcode(final Object first, final Object second) {
        assertFalse(first.equals(second));
        assertFalse(second.equals(first));
        assertFalse(first.hashCode() == second.hashCode());
    }

}
