package br.com.citframework.util;

import org.junit.Test;

/**
 * Classe de testes para validação do comportamento de {@link Assert}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 02/10/2014
 *
 */
public final class AssertTest {

    @Test
    public void testIsTrue() {
        Assert.isTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsTrueWhenIsFalse() {
        Assert.isTrue(false);
    }

    @Test
    public void testIsNull() {
        Assert.isNull(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsNullWhenIsNotNull() {
        Assert.isNull(new Object());
    }

    @Test
    public void testNotNull() {
        Assert.notNull(new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotNullWhenIsNull() {
        Assert.notNull(null);
    }

    @Test
    public void testNotNullAndNotEmpty() {
        Assert.notNullAndNotEmpty("Has value!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotNullAndNotEmptyWhenIsNull() {
        Assert.notNullAndNotEmpty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotNullAndNotEmptyWhenIsEmpty() {
        Assert.notNullAndNotEmpty("");
    }

}
