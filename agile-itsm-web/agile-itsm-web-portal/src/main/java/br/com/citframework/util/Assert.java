package br.com.citframework.util;

import org.apache.commons.lang.StringUtils;

/**
 * Utilitário pra facilitar asserções e validações
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 02/10/2014
 *
 */
public final class Assert {

    private Assert() {}

    private static final String DEFAULT_ASSERT_NULL_MESSAGE = "[ASSERTION FAILED] - The object must be null";
    private static final String DEFAULT_ASSERT_NOT_NUL_MESSAGE = "[ASSERTION FAILED] - The object must not be null";
    private static final String DEFATUL_ASSERT_TRUE_MESSAGE = "[ASSERTION FAILED] - This expression must be true";

    /**
     * Valida uma expressão booleana
     *
     * @param expression
     *            expressão a ser avaliada
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     * @throws IllegalArgumentException
     *             se a expressão é {@code false}
     */
    public static void isTrue(final boolean expression) {
        isTrue(expression, DEFATUL_ASSERT_TRUE_MESSAGE);
    }

    /**
     * Valida uma expressão booleana
     *
     * @param expression
     *            expressão a ser avaliada
     * @param message
     *            message de erro a ser exibida
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     * @throws IllegalArgumentException
     *             se a expressão é {@code false}
     */
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se um objeto é nulo
     *
     * @param object
     *            objeto para validação
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     * @throws IllegalArgumentException
     *             se o objeto não é nulo
     */
    public static void isNull(final Object object) {
        isNull(object, DEFAULT_ASSERT_NULL_MESSAGE);
    }

    /**
     * Valida se um objeto é nulo
     *
     * @param object
     *            objeto para validação
     * @param message
     *            message de erro a ser exibida
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     * @throws IllegalArgumentException
     *             se o objeto não é nulo
     */
    public static void isNull(final Object object, final String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se um objeto é não nulo
     *
     * @param object
     *            objeto para validação
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     * @throws IllegalArgumentException
     *             se o objeto é nulo
     */
    public static void notNull(final Object object) {
        notNull(object, DEFAULT_ASSERT_NOT_NUL_MESSAGE);
    }

    /**
     * Valida se um objeto é não nulo
     *
     * @param object
     *            objeto para validação
     * @param message
     *            message de erro a ser exibida
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     * @throws IllegalArgumentException
     *             se o objeto é nulo
     */
    public static void notNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se uma {@link String} é não nula e não vazia
     *
     * @param object
     *            objeto para validação
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     */
    public static void notNullAndNotEmpty(final Object object) {
        notNullAndNotEmpty(object, DEFAULT_ASSERT_NOT_NUL_MESSAGE);
    }

    /**
     * Valida se uma {@link String} é não nula e não vazia
     *
     * @param object
     *            objeto para validação
     * @param message
     *            message de erro a ser exibida
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     * @throws IllegalArgumentException
     *             se o objeto é nulo e, se {@link String}, também vazio
     */
    public static void notNullAndNotEmpty(final Object object, final String message) {
        notNull(object, message);
        if (object instanceof String) {
            final String strObject = (String) object;
            if (StringUtils.isBlank(strObject)) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
