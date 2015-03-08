package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.citframework.util.JNDIFactory;
import br.com.citframework.util.ReflectionUtils;

public abstract class ConnectionProviderAbstractTest {

    protected static final String EMPTY_STRING = "";

    private static final String JNDI_JAVA = "java:";
    private static final String JNDI_JAVA_JDBC = JNDI_JAVA.concat("/jdbc");

    protected static final String JNDI_DATASOURCE = JNDI_JAVA_JDBC.concat("/testDS");
    protected static final String JNDI_DATASOURCE_NOTFOUND = JNDI_JAVA_JDBC.concat("/notFoundTestDS");

    protected static final String EMBEDDED_JAVADB_JDBC_USER = "root";
    protected static final String EMBEDDED_JAVADB_JDBC_PASSWORD = "root";
    protected static final String EMBEDDED_JAVADB_JDBC_CLASS = "org.h2.Driver";
    protected static final String EMBEDDED_JAVADB_JDBC_URL = "jdbc:h2:~/test";

    private static Context context;

    private static Connection connection;

    private static Connection openConnection() throws Exception {
        Class.forName(EMBEDDED_JAVADB_JDBC_CLASS);
        return DriverManager.getConnection(EMBEDDED_JAVADB_JDBC_URL);
    }

    protected static Connection getConnection() throws Exception {
        if (connection == null) {
            connection = openConnection();
        }
        return connection;
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (getConnection().isClosed()) {
            connection = openConnection();
        }
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(String.format("create user if not exists %s password '%s'", EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD));
            stmt.executeUpdate(String.format("alter user %s set password '%s'", EMBEDDED_JAVADB_JDBC_USER, EMBEDDED_JAVADB_JDBC_PASSWORD));
            stmt.executeUpdate(String.format("alter user %s admin true", EMBEDDED_JAVADB_JDBC_USER));
        }

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");

        context = new InitialContext();

        context.createSubcontext(JNDI_JAVA);
        context.createSubcontext(JNDI_JAVA_JDBC);

        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(EMBEDDED_JAVADB_JDBC_URL);
        ds.setUser(EMBEDDED_JAVADB_JDBC_USER);
        ds.setPassword(EMBEDDED_JAVADB_JDBC_PASSWORD);

        final JNDIFactory jndiFactory = new JNDIFactory();

        ReflectionUtils.setField(jndiFactory, "context", context);
        jndiFactory.putResource(context, JNDI_DATASOURCE, ds);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        context.unbind(JNDI_DATASOURCE);

        context.destroySubcontext(JNDI_JAVA_JDBC);
        context.destroySubcontext(JNDI_JAVA);

        context.close();

        System.clearProperty(Context.INITIAL_CONTEXT_FACTORY);

        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(String.format("drop user if exists %s", EMBEDDED_JAVADB_JDBC_USER));
        }

        if (!getConnection().isClosed()) {
            getConnection().close();
        }
    }

}
