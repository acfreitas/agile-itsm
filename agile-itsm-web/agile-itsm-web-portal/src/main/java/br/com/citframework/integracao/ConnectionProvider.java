package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.resource.ResourceException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.AdaptacaoBD;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.JNDIFactory;

/**
 *
 * @author Ney Pellegrini
 *
 */
public final class ConnectionProvider {

    private static final Logger LOGGER = Logger.getLogger(ConnectionProvider.class.getName());

    private static final String POSTGRESQL = "POSTGRES";
    private static final String SQLSERVER = "SQLSERVER";

    private ConnectionProvider() {}

    /**
     * Recupera uma conexão publicada como recurso na JNDI ou recuperando configurações de usuário, url, etc. do arquivo {@code Constantes.properties}
     *
     * @param jndiName
     *            nome do recurso JNDI a ser recuperado, caso seja JNDI
     *
     * @return {@link Connection}
     * @throws Exception
     * @see ConnectionProvider#getConnection(String, Boolean)
     */
    // TODO refatorar para usar os métodos corretamente, isso aqui não faz sentido quando não é JNDI
    public static Connection getConnection(final String jndiName) throws Exception {
        Connection con = null;
        try {
            // Verifica de que maneira a Conexão será recuperada
            final String recuperacao = Constantes.getValue("OBTECAO_CONEXAO", "JNDI").trim();

            if (recuperacao.equalsIgnoreCase("JDBC")) {
                final String usuario = Constantes.getValue("USUARIO_CONEXAO");
                final String senha = Constantes.getValue("SENHA_CONEXAO");
                final String classe = Constantes.getValue("CLASSE_CONEXAO");
                final String url = Constantes.getValue("URL_CONEXAO");
                con = getConnection(classe, url, usuario, senha);
            } else {
                if (StringUtils.isBlank(jndiName)) {
                    throw new IllegalArgumentException("Resource JNDI não deve ser vazio ou nulo");
                }
                con = getConnectionFromJNDI(jndiName);
            }

            setTransactionIsolation(con);
        } catch (final Exception e) {
            final String mensagem = String.format("Problema ao recuperar conexão tipo=%s: ", jndiName) + e.getMessage();
            LOGGER.log(Level.WARNING, mensagem, e);
            throw new Exception(mensagem, e);
        }
        return con;
    }

    /**
     * Recupera uma conexão publicada como recurso na JNDI ou recuperando configurações de usuário, url, etc. do arquivo {@code Constantes.properties}, setando explicitamente se a
     * conexão é ou não apenas leitura
     *
     * @param jndiName
     *            nome do recurso JNDI a ser recuperado, caso seja JNDI
     * @param readOnly
     *            se a conexão deve ser ou não apenas leitura
     * @return {@link Connection}
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 19/08/2014
     * @see ConnectionProvider#getConnection(String)
     */
    // TODO refatorar para usar os métodos corretamente, isso aqui não faz sentido quando não é JNDI
    public static Connection getConnection(final String jndiName, final Boolean readOnly) throws Exception {
        final Connection connection = getConnection(jndiName);
        try {
            connection.setReadOnly(readOnly);
        } catch (final SQLException e) {
            final String mensagem = "Problema ao setar conexão como read only: " + e.getMessage();
            LOGGER.log(Level.WARNING, mensagem, e);
            throw new Exception(mensagem, e);
        }
        return connection;
    }

    /**
     * Recupera uma conexão de acordo com oas parâmetros informados
     *
     * @param clazz
     *            classe do driver para conexão
     * @param url
     *            url de conexão
     * @param user
     *            uusári para conexão
     * @param password
     *            senha do usuário para conexão
     * @return {@link Connection}
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 19/08/2014
     * @see ConnectionProvider#getConnection(String, String, String, String, Boolean)
     */
    public static Connection getConnection(final String clazz, final String url, final String user, final String password) throws Exception {
        if (StringUtils.isBlank(user)) {
            throw new IllegalArgumentException("A constante USUARIO_CONEXAO deve ser preenchida corretamente");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("A constante SENHA_CONEXAO deve ser preenchida corretamente");
        }
        if (StringUtils.isBlank(clazz)) {
            throw new IllegalArgumentException("A constante CLASSE_CONEXAO deve ser preenchida corretamente");
        }
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("A constante URL_CONEXAO deve ser preenchida corretamente");
        }
        Class.forName(clazz);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Recupera uma conexão de acordo com oas parâmetros informados
     *
     * @param clazz
     *            classe do driver para conexão
     * @param url
     *            url de conexão
     * @param user
     *            usuário para conexão
     * @param password
     *            senha do usuário para conexão
     * @param readOnly
     *            se a conexão deve ser ou não apenas leitura
     * @return {@link Connection}
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 19/08/2014
     * @see ConnectionProvider#getConnection(String, String, String, String)
     */
    public static Connection getConnection(final String clazz, final String url, final String user, final String password, final Boolean readOnly) throws Exception {
        final Connection connection = getConnection(clazz, url, user, password);
        try {
            connection.setReadOnly(readOnly);
        } catch (final SQLException e) {
            final String mensagem = "Problema ao setar conexão como read only: " + e.getMessage();
            LOGGER.log(Level.WARNING, mensagem, e);
            throw new Exception(mensagem, e);
        }
        return connection;
    }

    private static final JNDIFactory jndiContext = new JNDIFactory();

    /**
     * Recupera instância de {@link Connection} de um recurso JNDI
     *
     * @param jndiName
     *            nome do recurso publicado na JNDI
     * @return {@link Connection}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @throws Exception
     * @since 19/08/2014
     * @see ConnectionProvider#getConnectionFromJNDI(String, Boolean)
     */
    public static Connection getConnectionFromJNDI(final String jndiName) throws Exception {
        Connection connection = null;
        try {
            connection = ((DataSource) jndiContext.getResource(jndiName)).getConnection();
        } catch (final ResourceException e) {
            final String mensagem = "Problema ao recuperar conexão da JNDI: " + e.getMessage();
            LOGGER.log(Level.SEVERE, mensagem, e);
            throw new Exception(mensagem, e);
        }
        return connection;
    }

    /**
     * Recupera instância de {@link Connection} de um recurso JNDI
     *
     * @param jndiName
     *            nome do recurso publicado na JNDI
     * @param se
     *            a conexão deve ser ou não apenas leitura
     * @return {@link Connection}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @throws Exception
     *             caso ocorra algum problema ao informar o {@code readOnly}
     * @since 19/08/2014
     * @see ConnectionProvider#getConnectionFromJNDI(String)
     */
    public static Connection getConnectionFromJNDI(final String jndiName, final Boolean readOnly) throws Exception {
        final Connection connection = getConnectionFromJNDI(jndiName);
        try {
            connection.setReadOnly(readOnly);
        } catch (final SQLException e) {
            final String mensagem = "Problema ao setar conexão como read only: " + e.getMessage();
            LOGGER.log(Level.WARNING, mensagem, e);
            throw new Exception(mensagem, e);
        }
        return connection;
    }

    private static void setTransactionIsolation(final Connection con) throws SQLException {
        final String strSGBDPrincipal = AdaptacaoBD.getBancoUtilizadoByDBProductName(con.getMetaData().getDatabaseProductName());
        if (!strSGBDPrincipal.equalsIgnoreCase(POSTGRESQL)) {
            if (strSGBDPrincipal.equalsIgnoreCase(SQLSERVER)) {
                con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            } else {
                con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            }
        }
    }

}
