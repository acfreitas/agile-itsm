package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.Savepoint;

import br.com.citframework.excecao.PersistenceException;

public interface TransactionControler extends ConnectionControler {

    /**
     * Verifica se a tras��o est� iniciada
     *
     * @return {@code true}, caso trasa��o esteja iniciada. {@code false}, caso contr�rio
     */
    boolean isStarted();

    /**
     * Inicia a transa��o
     *
     * @throws PersistenceException
     *             caso algum problema ao iniciar a transa��o aconte�a, como {@link Connection} j� fechada
     */
    void start() throws PersistenceException;

    /**
     * Commita as altera��es na transa��o
     *
     * @throws PersistenceException
     *             caso algum problema ao iniciar a transa��o aconte�a, como {@link Connection} j� fechada
     */
    void commit() throws PersistenceException;

    /**
     * Realiza��o rollback de todas as altera��es ainda n�o commitadas na transa��o
     *
     * @throws PersistenceException
     *             caso algum problema ao iniciar a transa��o aconte�a, como {@link Connection} j� fechada
     */
    void rollback() throws PersistenceException;

    /**
     * Realiza rollback na transa��o at� um ponto de marca��o
     *
     * @param savepoint
     *            ponto at� o qual deve ser feito o rollback
     * @throws PersistenceException
     *             caso algum problema ao dar rollback na transa��o aconte�a, como {@link Connection} j� fechada
     */
    void rollback(final Savepoint savepoint) throws PersistenceException;

    /**
     * Cria um {@link Savepoint} na transa��o
     *
     * @return {@link Savepoint}
     * @throws PersistenceException
     */
    Savepoint savepoint() throws PersistenceException;

    /**
     * Cria um {@link Savepoint} nomeado na transa��o
     *
     * @param name
     *            nome para identifica��o do ponto de marca��o
     * @return {@link Savepoint}
     * @throws PersistenceException
     */
    Savepoint savepoint(final String name) throws PersistenceException;

    /**
     * Libera um {@link Savepoint} previamente marcado na linha de transa��es
     *
     * @param savepoint
     *            savepoint a ser liberado na transa��o
     * @throws PersistenceException
     */
    void releaseSavepoint(final Savepoint savepoint) throws PersistenceException;

}
