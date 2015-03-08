package br.com.citframework.integracao;

import java.sql.Connection;

import br.com.citframework.excecao.PersistenceException;

/**
 * Controlador de comportamento de conex�es que n�o posuem transa��o
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 25/08/2014
 *
 */
public interface ConnectionControler extends AutoCloseable {

    /**
     * Coloca uma conex�o em estado {@code read-only}, n�o pode ser transacional, n�o executa DML de altera��o
     *
     * @param readOnly
     * @throws PersistenceException
     * @since 01/09/2014
     * @see Connection#setReadOnly(boolean)
     */
    void setReadOnly(final boolean readOnly) throws PersistenceException;

    /**
     * Verifica se a conex�o � ou n�o apenas leitura
     *
     * @return {@code true}, caso a conex�o seja read only. {@code false}, caso contr�rio
     * @throws PersistenceException
     * @since 01/09/2014
     */
    boolean isReadOnly() throws PersistenceException;;

    /**
     * Recupera o alias da base de dados. Normalmente, o resource JNDI do pool
     *
     * @return {@link String}
     */
    String getDataBaseAlias();

    /**
     * Seta o alias da base de dados. Normalmente, o resource JNDI do pool
     *
     * @param dataBaseAlias
     *            alias da base de dados
     */
    void setDataBaseAlias(final String dataBaseAlias);

    /**
     * Retorna a conex�o relacionada ao Transaction Controler
     *
     * <p>
     * {@code IMPORTANTE}: esta trasa��o pode n�o estar com a transa��o iniciada ({@code {@link Connection#getAutoCommit()} == false}). Voc� deve chamar {@link #start()} caso
     * queira comportamento transacional
     * <p>
     *
     * @return {@link Connection}
     */
    Connection getConnection() throws PersistenceException;

    /**
     * Commita e fecha a transa��o
     *
     * @throws PersistenceException
     *             caso algum problema ao fechar a transa��o aconte�a, como {@link Connection} j� fechada
     * @see {@link Connection#close()}
     */
    @Override
    void close() throws PersistenceException;

    /**
     * Commit e fecha a conex�o sem levantar excec�es
     *
     * @return {@code true}, caso obtenha sucesso em fechar a conex�o. {@code false}, caso contr�rio
     * @see {@link #close()}
     */
    boolean closeQuietly();

}
