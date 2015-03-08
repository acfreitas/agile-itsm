package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.Savepoint;

import br.com.citframework.excecao.PersistenceException;

public interface TransactionControler extends ConnectionControler {

    /**
     * Verifica se a trasção está iniciada
     *
     * @return {@code true}, caso trasação esteja iniciada. {@code false}, caso contrário
     */
    boolean isStarted();

    /**
     * Inicia a transação
     *
     * @throws PersistenceException
     *             caso algum problema ao iniciar a transação aconteça, como {@link Connection} já fechada
     */
    void start() throws PersistenceException;

    /**
     * Commita as alterações na transação
     *
     * @throws PersistenceException
     *             caso algum problema ao iniciar a transação aconteça, como {@link Connection} já fechada
     */
    void commit() throws PersistenceException;

    /**
     * Realização rollback de todas as alterações ainda não commitadas na transação
     *
     * @throws PersistenceException
     *             caso algum problema ao iniciar a transação aconteça, como {@link Connection} já fechada
     */
    void rollback() throws PersistenceException;

    /**
     * Realiza rollback na transação até um ponto de marcação
     *
     * @param savepoint
     *            ponto até o qual deve ser feito o rollback
     * @throws PersistenceException
     *             caso algum problema ao dar rollback na transação aconteça, como {@link Connection} já fechada
     */
    void rollback(final Savepoint savepoint) throws PersistenceException;

    /**
     * Cria um {@link Savepoint} na transação
     *
     * @return {@link Savepoint}
     * @throws PersistenceException
     */
    Savepoint savepoint() throws PersistenceException;

    /**
     * Cria um {@link Savepoint} nomeado na transação
     *
     * @param name
     *            nome para identificação do ponto de marcação
     * @return {@link Savepoint}
     * @throws PersistenceException
     */
    Savepoint savepoint(final String name) throws PersistenceException;

    /**
     * Libera um {@link Savepoint} previamente marcado na linha de transações
     *
     * @param savepoint
     *            savepoint a ser liberado na transação
     * @throws PersistenceException
     */
    void releaseSavepoint(final Savepoint savepoint) throws PersistenceException;

}
