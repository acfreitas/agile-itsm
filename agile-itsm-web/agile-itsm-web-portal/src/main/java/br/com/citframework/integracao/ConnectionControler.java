package br.com.citframework.integracao;

import java.sql.Connection;

import br.com.citframework.excecao.PersistenceException;

/**
 * Controlador de comportamento de conexões que não posuem transação
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 25/08/2014
 *
 */
public interface ConnectionControler extends AutoCloseable {

    /**
     * Coloca uma conexão em estado {@code read-only}, não pode ser transacional, não executa DML de alteração
     *
     * @param readOnly
     * @throws PersistenceException
     * @since 01/09/2014
     * @see Connection#setReadOnly(boolean)
     */
    void setReadOnly(final boolean readOnly) throws PersistenceException;

    /**
     * Verifica se a conexão é ou não apenas leitura
     *
     * @return {@code true}, caso a conexão seja read only. {@code false}, caso contrário
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
     * Retorna a conexão relacionada ao Transaction Controler
     *
     * <p>
     * {@code IMPORTANTE}: esta trasação pode não estar com a transação iniciada ({@code {@link Connection#getAutoCommit()} == false}). Você deve chamar {@link #start()} caso
     * queira comportamento transacional
     * <p>
     *
     * @return {@link Connection}
     */
    Connection getConnection() throws PersistenceException;

    /**
     * Commita e fecha a transação
     *
     * @throws PersistenceException
     *             caso algum problema ao fechar a transação aconteça, como {@link Connection} já fechada
     * @see {@link Connection#close()}
     */
    @Override
    void close() throws PersistenceException;

    /**
     * Commit e fecha a conexão sem levantar excecões
     *
     * @return {@code true}, caso obtenha sucesso em fechar a conexão. {@code false}, caso contrário
     * @see {@link #close()}
     */
    boolean closeQuietly();

}
