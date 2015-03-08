package br.com.citframework.integracao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de testes para validação do comportamento de {@link ConnectionProvider}
 *
 * {@link #testGetConnection()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} <br>
 * {@link #testGetConnectionReadOnly()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} <br>
 * {@link #testGetConnectionJNDI()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} de {@link DataSource} publicado <br>
 * {@link #testGetConnectionJNDIWithoutPrefix()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} de {@link DataSource} publicado <br>
 * {@link #testGetConnectionJNDIReadOnly()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} de {@link DataSource} publicado <br>
 * {@link #testGetConnectionJNDINotFound()} - deve lançar uma {@link Exception}, pois o recurso não está publicado <br>
 * {@link #testGetConnectionJNDIReadOnlyNotFound()} - deve lançar uma {@link Exception}, pois o recurso não está publicado <br>
 * {@link #testGetConnectionWithClassURLUserPassword()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} <br>
 * {@link #testGetConnectionWithoutClass()} - deve lançar uma {@link Exception}, pois o class do driver é obrigatório <br>
 * {@link #testGetConnectionWithoutURL()} - deve lançar uma {@link Exception}, pois a URL de conexão é obrigatória <br>
 * {@link #testGetConnectionWithoutUser()} - deve lançar uma {@link Exception}, pois o usuário para conexão é obrigatório <br>
 * {@link #testGetConnectionWithoutPassword()} - deve lançar uma {@link Exception}, pois a senha para ocnexão é obrigatória <br>
 * {@link #testGetConnectionWithClassURLUserPasswordReadOnly()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} <br>
 * {@link #testGetConnectionFromJNDI()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} de {@link DataSource} publicado <br>
 * {@link #testGetConnectionFromJNDIReadOnly()} - deve {@link Assert#assertNotNull(Object)} em um {@link Connection} de {@link DataSource} publicado <br>
 * {@link #testGetConnectionFromJNDINotFound()} - deve lançar uma {@link Exception}, pois o recurso não está publicado <br>
 * {@link #testGetConnectionFromJNDIReadOnlyNotFound()} - deve lançar uma {@link Exception}, pois o recurso não está publicado
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 19/08/2014
 *
 */
public final class ConnectionProviderTest extends ConnectionProviderAbstractTest {

    @Test(expected = Exception.class)
    public void testGetConnection() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMPTY_STRING);
        Assert.assertNotNull(conn);
    }

    @Test(expected = Exception.class)
    public void testGetConnectionReadOnly() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMPTY_STRING, Boolean.TRUE);
        Assert.assertNotNull(conn);
    }

    @Test
    public void testGetConnectionJNDI() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(JNDI_DATASOURCE);
        Assert.assertNotNull(conn);
    }

    @Test
    public void testGetConnectionJNDIWithoutPrefix() throws Exception {
        final Connection conn = ConnectionProvider.getConnection("jdbc/testDS");
        Assert.assertNotNull(conn);
    }

    @Test
    public void testGetConnectionJNDIReadOnly() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(JNDI_DATASOURCE, Boolean.TRUE);
        Assert.assertNotNull(conn);
    }

    @Test(expected = Exception.class)
    public void testGetConnectionJNDINotFound() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(JNDI_DATASOURCE_NOTFOUND);
        Assert.assertNotNull(conn);
    }

    @Test(expected = Exception.class)
    public void testGetConnectionJNDIReadOnlyNotFound() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(JNDI_DATASOURCE_NOTFOUND, Boolean.TRUE);
        Assert.assertNotNull(conn);
    }

    @Test
    public void testGetConnectionWithClassURLUserPassword() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNotNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutClass() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMPTY_STRING, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutURL() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMPTY_STRING, EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutUser() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMPTY_STRING, EMBEDDED_JAVADB_JDBC_PASSWORD);
        Assert.assertNull(conn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithoutPassword() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER, EMPTY_STRING);
        Assert.assertNull(conn);
    }

    @Test
    public void testGetConnectionWithClassURLUserPasswordReadOnly() throws Exception {
        final Connection conn = ConnectionProvider.getConnection(EMBEDDED_JAVADB_JDBC_CLASS, EMBEDDED_JAVADB_JDBC_URL, EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD,
                Boolean.TRUE);
        Assert.assertNotNull(conn);
    }

    @Test
    public void testGetConnectionFromJNDI() throws Exception {
        final Connection conn = ConnectionProvider.getConnectionFromJNDI(JNDI_DATASOURCE);
        Assert.assertNotNull(conn);
    }

    @Test
    public void testGetConnectionFromJNDIReadOnly() throws Exception {
        final Connection conn = ConnectionProvider.getConnectionFromJNDI(JNDI_DATASOURCE, Boolean.TRUE);
        Assert.assertNotNull(conn);
    }

    @Test(expected = Exception.class)
    public void testGetConnectionFromJNDINotFound() throws Exception {
        final Connection conn = ConnectionProvider.getConnectionFromJNDI(JNDI_DATASOURCE_NOTFOUND);
        Assert.assertNotNull(conn);
    }

    @Test(expected = Exception.class)
    public void testGetConnectionFromJNDIReadOnlyNotFound() throws Exception {
        final Connection conn = ConnectionProvider.getConnectionFromJNDI(JNDI_DATASOURCE_NOTFOUND, Boolean.TRUE);
        Assert.assertNotNull(conn);
    }

}
