package br.com.citframework.integracao;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provider/pool de conexões apenas read only ({@code {@link Connection#setReadOnly(boolean)} == true}), mantendo apenas uma por usuário e senha ou JNDI
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 20/08/2014
 *
 */
public final class ConnectionReadOnlyProvider {

    private ConnectionReadOnlyProvider() {} // static only

    private static final Map<String, Connection> connections = new ConcurrentHashMap<>();

    /**
     * Recupera uma {@link Connection} {@code READ ONLY} de um recurso JNDI e atribui a um pool local
     *
     * @param jndiName
     *            nome do recurso publicado na JNDI
     * @return {@link Connection}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @throws Exception
     * @since 20/08/2014
     * @see ConnectionProvider#getConnectionFromJNDI(String, Boolean)
     */
    public static Connection getConnection(final String jndiName) throws Exception {
        Connection connection = connections.get(jndiName);
        if (connection == null || connection.isClosed()) {
            connection = ConnectionProvider.getConnectionFromJNDI(jndiName, Boolean.TRUE);
            connections.put(jndiName, connection);
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
     *            usuário para conexão
     * @param password
     *            senha do usuário para conexão
     * @return {@link Connection}
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 20/08/2014
     * @see ConnectionProvider#getConnection(String, String, String, String)
     */
    public static Connection getConnection(final String clazz, final String url, final String user, final String password) throws Exception {
        final String identifier = constructIdentifier(clazz, url, user);
        Connection connection = connections.get(identifier);
        if (connection == null || connection.isClosed()) {
            connection = ConnectionProvider.getConnection(clazz, url, user, password, Boolean.TRUE);
            connections.put(identifier, connection);
        }
        return connection;
    }

    private static String constructIdentifier(final String clazz, final String url, final String user) {
        final StringBuilder identifier = new StringBuilder();
        identifier.append(clazz).append(":").append(url).append(":").append(user);
        return identifier.toString();
    }

}
