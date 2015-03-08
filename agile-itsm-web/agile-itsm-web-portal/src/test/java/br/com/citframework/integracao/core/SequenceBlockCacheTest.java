package br.com.citframework.integracao.core;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.citframework.util.ReflectionUtils;

/**
 * Classe de testes para validação do comportamento de {@link SequenceBlockCache}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 18/08/2014
 *
 */
public final class SequenceBlockCacheTest {

    private static Connection connection = null;

    private static volatile SequenceBlockCache globalCache;

    private final int INCREMENT = 10;
    private final int NEGATIVE_INCREMENT = -1;

    private final long INTERACTIONS = 500;

    private static final String TRUNCATE_TABLE = "truncate table %s";
    private static final String DROP_TABLE_IF_EXISTS = "drop table if exists %s";

    private static final String TABLE_CONTROLLER = "sequence_block_controller";

    private static final String TABLE_ONE = "table_one";
    private static final String TABLE_ONE_COLUMN_ONE = "id_one_table_one";
    private static final String TABLE_TWO = "table_two";
    private static final String TABLE_TWO_COLUMN_ONE = "id_one_table_two";
    private static final String TABLE_TWO_COLUMN_TWO = "id_two_table_two";

    private static final String EMBEDDED_JAVADB_JDBC_CLASS = "org.h2.Driver";
    private static final String EMBEDDED_JAVADB_JDBC_URL = "jdbc:h2:~/test";

    private static String getDDLSequenceController() {
        final StringBuilder sql = new StringBuilder();
        sql.append("create table sequence_block_controller (");
        sql.append("  id identity primary key,");
        sql.append("  sequence_name varchar(60) not null,");
        sql.append("  last_id bigint not null");
        sql.append(");");
        sql.append("alter table sequence_block_controller add constraint sequence_block_controller_key unique(sequence_name);");
        return sql.toString();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        final Constructor<SequenceBlockCache> construct = SequenceBlockCache.class.getDeclaredConstructor();
        construct.setAccessible(true);
        globalCache = construct.newInstance();

        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(String.format(DROP_TABLE_IF_EXISTS, TABLE_ONE));
            stmt.executeUpdate(String.format(DROP_TABLE_IF_EXISTS, TABLE_TWO));
            stmt.executeUpdate(String.format(DROP_TABLE_IF_EXISTS, TABLE_CONTROLLER));
        }

        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(String.format("create table %s (%s int)", TABLE_ONE, TABLE_ONE_COLUMN_ONE));
            stmt.executeUpdate(String.format("create table %s (%s int, %s int)", TABLE_TWO, TABLE_TWO_COLUMN_ONE, TABLE_TWO_COLUMN_TWO));
            stmt.executeUpdate(getDDLSequenceController());
        }
    }

    @Before
    public void setUp() throws Exception {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(String.format(TRUNCATE_TABLE, TABLE_ONE));
            stmt.executeUpdate(String.format(TRUNCATE_TABLE, TABLE_TWO));
            stmt.executeUpdate(String.format(TRUNCATE_TABLE, TABLE_CONTROLLER));
        }
    }

    @Test
    public void testSequenceBlockCacheNextIdTableOne() throws Exception {
        for (long i = 1; i <= INTERACTIONS; i++) {
            final long nextId = this.nextID(TABLE_ONE, TABLE_ONE_COLUMN_ONE);
            this.insertIntoTable(nextId);
            Assert.assertEquals(nextId, i);
        }
    }

    @Test
    public void testSequenceBlockCacheNextIdTableTwo() throws Exception {
        for (long i = 1; i <= INTERACTIONS; i++) {
            final long nextIdColumnOne = this.nextID(TABLE_TWO, TABLE_TWO_COLUMN_ONE);
            final long nextIdColumnTwo = this.nextID(TABLE_TWO, TABLE_TWO_COLUMN_TWO);
            this.insertIntoTableTwo(nextIdColumnOne, nextIdColumnTwo);
            Assert.assertEquals(nextIdColumnOne, i);
            Assert.assertEquals(nextIdColumnTwo, i);
        }
    }

    @Test
    public void testSequenceBlockCacheNextIdWithIncrementTableOne() throws Exception {
        for (long i = 1; i <= INTERACTIONS; i++) {
            final long nextId = this.nextID(TABLE_ONE, TABLE_ONE_COLUMN_ONE, INCREMENT);
            this.insertIntoTable(nextId);
            Assert.assertEquals(nextId, i);
        }
    }

    @Test
    public void testSequenceBlockCacheNextIdWithIncrementTableTwo() throws Exception {
        for (long i = 1; i <= INTERACTIONS; i++) {
            final long nextIdColumnOne = this.nextID(TABLE_TWO, TABLE_TWO_COLUMN_ONE, INCREMENT);
            final long nextIdColumnTwo = this.nextID(TABLE_TWO, TABLE_TWO_COLUMN_TWO, INCREMENT);
            this.insertIntoTableTwo(nextIdColumnOne, nextIdColumnTwo);
            Assert.assertEquals(nextIdColumnOne, i);
            Assert.assertEquals(nextIdColumnTwo, i);
        }
    }

    @Test
    public void testSequenceBlockCacheNextIdWithNegativeIncrementTableOne() throws Exception {
        for (long i = 1; i <= INTERACTIONS; i++) {
            final long nextId = this.nextID(TABLE_ONE, TABLE_ONE_COLUMN_ONE, NEGATIVE_INCREMENT);
            this.insertIntoTable(nextId);
            Assert.assertEquals(nextId, i);
        }
    }

    @Test
    public void testSequenceBlockCacheNextIdWithNegativeIncrementTableTwo() throws Exception {
        for (long i = 1; i <= INTERACTIONS; i++) {
            final long nextIdColumnOne = this.nextID(TABLE_TWO, TABLE_TWO_COLUMN_ONE, NEGATIVE_INCREMENT);
            final long nextIdColumnTwo = this.nextID(TABLE_TWO, TABLE_TWO_COLUMN_TWO, NEGATIVE_INCREMENT);
            this.insertIntoTableTwo(nextIdColumnOne, nextIdColumnTwo);
            Assert.assertEquals(nextIdColumnOne, i);
            Assert.assertEquals(nextIdColumnTwo, i);
        }
    }

    private void insertIntoTable(final Long valueColumnOne) throws SQLException {
        final String sql = String.format("insert into %s (%s) values (?)", TABLE_ONE, TABLE_ONE_COLUMN_ONE);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, valueColumnOne.intValue());
            stmt.execute();
        }
    }

    private void insertIntoTableTwo(final Long valueColumnOne, final Long valueColumnTwo) throws SQLException {
        final String sql = String.format("insert into %s (%s, %s) values (?, ?)", TABLE_TWO, TABLE_TWO_COLUMN_ONE, TABLE_TWO_COLUMN_TWO);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, valueColumnOne.intValue());
            stmt.setInt(2, valueColumnTwo.intValue());
            stmt.execute();
        }
    }

    private long nextID(final String tableName, final String columnName) {
        getConnection();
        return globalCache.getNextId(tableName, columnName);
    }

    private long nextID(final String tableName, final String columnName, final int increment) {
        getConnection();
        return globalCache.getNextId(tableName, columnName, increment);
    }

    private static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(EMBEDDED_JAVADB_JDBC_CLASS);
                connection = DriverManager.getConnection(EMBEDDED_JAVADB_JDBC_URL);
                ReflectionUtils.setField(globalCache, "connection", connection);
            }
        } catch (final Exception e) {}
        return connection;
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (getConnection() != null && !getConnection().isClosed()) {
            getConnection().close();
        }
    }

}
