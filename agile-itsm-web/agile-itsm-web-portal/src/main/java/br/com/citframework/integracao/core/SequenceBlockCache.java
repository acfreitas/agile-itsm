package br.com.citframework.integracao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import br.com.citframework.integracao.ConnectionProvider;

/**
 * {@code THREAD-SAFE} - {@link SequenceBlockCache} gerencia um número arbitrário de sequeências utilizadas em relações em DBs, utilizando o padrão SEQUENCE BLOCK.
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @date 18/08/2014
 */
public final class SequenceBlockCache {

    private String alias;
    private Connection connection;

    private static final Logger LOGGER = Logger.getLogger(SequenceBlockCache.class);

    private static final int DEFAULT_SEQUENCE_BLOCK_INCREMENT = 50;

    private static final String LOGGER_BLOCK_INCREMENT_CONST = ", blockIncrement=";
    private static final String LOGGER_CALLED_CONST = ") called.";
    private static final String LOGGER_COULD_NOT_CREATE_NEW_SEQUENCE_RECORD = ": Could not create new sequence record ('";
    private static final String LOGGER_GET_NEXTID_CONST = ".getNextId('";
    private static final String LOGGER_THREAD_CONST = "Thread @";

    private static final String SQL_SELECT_CURRENT_ID = "SELECT MAX(%s) FROM %s";
    private static final String SQL_SELECT_CONTROLLER = "SELECT last_id FROM sequence_block_controller WHERE UPPER(sequence_name) = UPPER(?)";
    private static final String SQL_INSERT_CONTROLLER = "INSERT INTO sequence_block_controller (sequence_name, last_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_CONTROLLER = "UPDATE sequence_block_controller SET last_id = ? WHERE UPPER(sequence_name) = UPPER(?) AND last_id = ? AND id > 0";

    private final Map<String, SequenceBlock> cachedSequenceBlocksMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Object> sequenceLockMap = new ConcurrentHashMap<>();

    SequenceBlockCache() {}

    /**
     * Constrói um {@link SequenceBlockCache} inicializando {@link SequenceBlockCache#connection}
     */
    public SequenceBlockCache(final String alias) {
        this.alias = alias;
    }

    /**
     * Recupera objeto para sincronizar apenas a {@link SequenceBlock} que está sendo executada pela thread, para não bloquear todo o cache<br>
     *
     * @param sequenceName
     *            nome que reprensenta o {@link SequenceBlock}
     * @return objeto para sincronização
     */
    private Object getSequenceLockObject(final String sequenceName) {
        Object lockObject = sequenceLockMap.get(sequenceName);
        if (lockObject == null) {
            lockObject = new Object();
            final Object prevLockObject = sequenceLockMap.putIfAbsent(sequenceName, lockObject);
            if (prevLockObject != null) {
                lockObject = prevLockObject;
            }
        }
        return lockObject;
    }

    /**
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @return Long
     */
    private Long getCurrentSequenceNumberFromDb(final String tableName, final String fieldName, final Connection conn) {
        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + ".getCurrentSequenceNumberFromDb('"
                + tableName + "'" + LOGGER_CALLED_CONST);
        Long res = null;

        final String sequenceName = tableName + "_" + fieldName;

        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_CONTROLLER);) {
            stmt.setString(1, sequenceName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    res = rs.getLong(1);
                }
            }
        } catch (final SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }

        return res;
    }

    /**
     * Constrói um novo {@link SequenceBlock} para {@code tableName} e {@code fieldName}
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @param lastUsedValue
     *            último valor usado do {@link SequenceBlock}
     * @param blockIncrement
     *            incremente a ser considerado no bloco
     * @return SequenceBlock
     */
    private SequenceBlock reserveNextSequenceBlock(final String tableName, final String fieldName, final long lastUsedValue, final int blockIncrement, final Connection conn) {
        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + ".reserveNextSequenceBlock('"
                + tableName + "', lastUsedValue=" + lastUsedValue + LOGGER_BLOCK_INCREMENT_CONST + blockIncrement + LOGGER_CALLED_CONST);

        long updCount = 0;
        SequenceBlock res = null;
        final String sequenceName = tableName + "_" + fieldName;

        final int localBlockIncrement = blockIncrement < 1 ? DEFAULT_SEQUENCE_BLOCK_INCREMENT : blockIncrement;

        final long startOfBlock = lastUsedValue + 1;
        final long endOfBlock = startOfBlock + localBlockIncrement;

        try {
            try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_CONTROLLER)) {
                stmt.setLong(1, endOfBlock - 1);
                stmt.setString(2, sequenceName);
                stmt.setLong(3, lastUsedValue);

                updCount = stmt.executeUpdate();
            }
        } catch (final SQLException ex) {
            throw new RuntimeException(ex);
        }

        if (updCount == 1) {
            res = new SequenceBlock(startOfBlock, endOfBlock);
        }
        return res;
    }

    /**
     * Retorna o {@link SequenceBlock} correspondente a tabela {@code tableName} e {@code fieldName}. Se necessário, recupera no DB
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @param blockIncrement
     *            incremente a ser considerado no bloco
     * @return SequenceBlock
     */
    private SequenceBlock reserveNextSequenceBlock(final String tableName, final String fieldName, final int blockIncrement) {
        SequenceBlock sequenceBlock = null;

        final String sequenceName = tableName + "_" + fieldName;

        try (final Connection conn = this.getConnection()) {
            while (sequenceBlock == null) {
                Long currentValue = this.getCurrentSequenceNumberFromDb(tableName, fieldName, conn);
                if (currentValue == null) {
                    LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": Creating new sequence record for sequenceName='" + sequenceName
                            + "'.");
                    currentValue = this.createAndGetCurrentSequenceValue(tableName, fieldName, conn);
                    if (currentValue == null) {
                        throw new RuntimeException(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": The sequence '" + sequenceName
                                + "' could neither be created nor updated (unknown server error).");
                    }
                }
                final long lastUsedValue = currentValue;
                if (lastUsedValue < 0) {
                    throw new IllegalStateException(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": Found negative value for sequence '"
                            + sequenceName + "' - check database SEQUENCE_BLOCK_CONTROLLER!");
                }
                sequenceBlock = this.reserveNextSequenceBlock(tableName, fieldName, currentValue, blockIncrement, conn);
                if (sequenceBlock == null) {
                    LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode())
                            + ": chosen as victim during concurrent block reservation - going to try again ...");
                }
            }
        } catch (final SQLException ex) {
            LOGGER.warn(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + LOGGER_COULD_NOT_CREATE_NEW_SEQUENCE_RECORD + sequenceName + "').", ex);
        }
        return sequenceBlock;
    }

    /**
     * Creates the new sequence and returns the current value of the sequence afterwards.
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @return
     */
    private Long createAndGetCurrentSequenceValue(final String tableName, final String fieldName, final Connection conn) {
        final String sequenceName = tableName + "_" + fieldName;

        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + ".createAndGetCurrentSequenceValue("
                + sequenceName + LOGGER_CALLED_CONST);

        long maxValue = 0;
        final String sql = String.format(SQL_SELECT_CURRENT_ID, fieldName, tableName);

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                maxValue = rs.getLong(1);
            }
        } catch (final SQLException ex) {
            LOGGER.warn(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + LOGGER_COULD_NOT_CREATE_NEW_SEQUENCE_RECORD + sequenceName + "').", ex);
        }

        try {
            try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_CONTROLLER)) {
                stmt.setString(1, sequenceName);
                stmt.setLong(2, maxValue);
                stmt.executeUpdate();
            }
        } catch (final SQLException ex) {
            LOGGER.warn(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + LOGGER_COULD_NOT_CREATE_NEW_SEQUENCE_RECORD + sequenceName + "').", ex);
        }

        return this.getCurrentSequenceNumberFromDb(tableName, fieldName, conn);
    }

    /**
     * {@code THREAD-SAFE} - Retorna o {@link SequenceBlock} correspondente a tabela {@code tableName} e {@code fieldName}
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @param blockIncrement
     *            incremente a ser considerado no bloco
     * @return SequenceBlock
     */
    private SequenceBlock getSequenceBlock(final String tableName, final String fieldName, final int blockIncrement) {
        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + ".getSequenceBlock('" + tableName
                + "'" + LOGGER_BLOCK_INCREMENT_CONST + blockIncrement + LOGGER_CALLED_CONST);

        final String sequenceName = tableName + "_" + fieldName;

        SequenceBlock sequenceBlock = cachedSequenceBlocksMap.get(sequenceName);
        if (sequenceBlock == null || sequenceBlock.isExhausted()) {
            // não sincroniza globalmente, mas por sequence block!
            synchronized (this.getSequenceLockObject(sequenceName)) {
                LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": Performing lookup in sequence block cache");
                sequenceBlock = cachedSequenceBlocksMap.get(sequenceName);
                if (sequenceBlock == null || sequenceBlock.isExhausted()) {
                    LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": New sequence block required!");
                    sequenceBlock = this.reserveNextSequenceBlock(tableName, fieldName, blockIncrement);
                    cachedSequenceBlocksMap.put(sequenceName, sequenceBlock);
                } else {
                    LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": Found existing sequence block, no need for db-access.");
                }
            }
        }
        return sequenceBlock;
    }

    /**
     * Retorna o próximo id para o {@code fieldName} na {@code tableName}
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @param blockIncrement
     *            incremente a ser considerado no bloco
     * @return long próximo id
     */
    public long getNextId(final String tableName, final String fieldName, int blockIncrement) {
        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + LOGGER_GET_NEXTID_CONST + tableName
                + "'" + LOGGER_BLOCK_INCREMENT_CONST + blockIncrement + LOGGER_CALLED_CONST);

        if (blockIncrement < 1) {
            blockIncrement = DEFAULT_SEQUENCE_BLOCK_INCREMENT;
        }
        long res = -1;
        while (res < 0) {
            res = this.getSequenceBlock(tableName, fieldName, blockIncrement).getNextId();
        }

        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + LOGGER_GET_NEXTID_CONST + tableName
                + "'" + LOGGER_BLOCK_INCREMENT_CONST + blockIncrement + ") return " + res + " .");

        return res;
    }

    /**
     * Retorna o próximo id para o {@code fieldName} na {@code tableName}
     *
     * @param tableName
     *            nome da tabela a ser recuperado o próximo id
     * @param fieldName
     *            nome do campo na tabela a ser recuperado o próximo id
     * @return long próximo id
     */
    public long getNextId(final String tableName, final String fieldName) {
        LOGGER.debug(LOGGER_THREAD_CONST + Integer.toHexString(Thread.currentThread().hashCode()) + ": " + this.getClass().getSimpleName() + LOGGER_GET_NEXTID_CONST + tableName
                + "'" + LOGGER_CALLED_CONST);
        return this.getNextId(tableName, fieldName, DEFAULT_SEQUENCE_BLOCK_INCREMENT);
    }

    /**
     * Recupera a {@link Connection} usada para recuperar o maior id nas relações
     *
     * @return {@link Connection}
     * @throws SQLException
     */
    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = ConnectionProvider.getConnection(alias);
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return connection;
    }

    @Override
    protected void finalize() throws Throwable {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        super.finalize();
    }

}
