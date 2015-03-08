package br.com.citframework.util;

import javax.naming.Context;
import javax.resource.ResourceException;

/**
 * Interface para recuperação de recursos.
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 19/08/2014
 *
 */
public interface IContextFactory<E> {

    /**
     * Recupera um recurso de acordo com seu nome
     *
     * @param name
     *            nome do recurso a ser recuperado
     * @return {@link Object}
     * @throws ResourceException
     *             caso ocorra algum problema ao recuperar o recurso
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 19/08/2014
     */
    E getResource(final String name) throws ResourceException;

    /**
     * Faz bind de um recurso em um contexto
     *
     * @param object
     *            objeto a ser feito bind
     * @param name
     *            nome de referência do objeto no contexto
     * @param context
     *            contexto em que o objeto será armazenado
     * @return {@code true}, caso tenha tido sucesso ao fazer o bind. {@code false}, caso contrário
     * @throws ResourceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 19/08/2014
     */
    Boolean putResource(final Context context, final String name, final E object) throws ResourceException;

}
