package br.com.citframework.integracao;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.citframework.util.ReflectionUtils;

/**
 * Classe de testes para validação do comportamento de {@link ConnectionReadOnlyProvider}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 20/08/2014
 *
 */
public final class ConnectionReadOnlyProviderTest extends ConnectionProviderAbstractTest {

    private final Map<String, Connection> connections = new ConcurrentHashMap<>();

    @Before
    public void setUp() throws Exception {
        ReflectionUtils.setFieldOnStatic(ConnectionReadOnlyProvider.class, "connections", connections);
    }

    @Test
    public void testGetConnectionWithClassURLUserPassword() throws Exception {
        final Connection conn = ConnectionReadOnlyProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER,
                EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNotNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutClass() throws Exception {
        final Connection conn = ConnectionReadOnlyProvider.getConnection(EMPTY_STRING, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutURL() throws Exception {
        final Connection conn = ConnectionReadOnlyProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMPTY_STRING, EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutUser() throws Exception {
        final Connection conn = ConnectionReadOnlyProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMPTY_STRING, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutPassword() throws Exception {
        final Connection conn = ConnectionReadOnlyProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER, EMPTY_STRING);
        Assert.assertNull(conn);
    }

    @Test
    public void testGetConnectionFromJNDI() throws Exception {
        final Connection conn = ConnectionReadOnlyProvider.getConnection(JNDI_DATASOURCE);
        Assert.assertNotNull(conn);
        final Connection connection = ConnectionReadOnlyProvider.getConnection(JNDI_DATASOURCE);
        Assert.assertSame(conn, connection);
    }

}
