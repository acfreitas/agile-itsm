package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.citframework.excecao.ConnectionException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

/**
 * Implementação básica de {@link ConnectionControler}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 25/08/2014
 *
 */
public class ConnectionControlerImpl implements ConnectionControler {

    private static final Logger LOGGER = Logger.getLogger(ConnectionControlerImpl.class.getName());

    private Boolean readOnly = false;
    protected Connection connection;
    protected String dataBaseAlias = Constantes.getValue("DATABASE_ALIAS");

    protected static final String CLOSE = "Close";
    private static final String READONLY = "ReadOnly";

    public ConnectionControlerImpl(final String dataBaseAlias) {
        this(dataBaseAlias, false);
    }

    public ConnectionControlerImpl(final String dataBaseAlias, final Boolean readOnly) {
        this.dataBaseAlias = dataBaseAlias;
        this.readOnly = readOnly;
    }

    @Override
    public void setReadOnly(final boolean readOnly) throws PersistenceException {
        this.doConnectionValidation(READONLY);

        try {
            connection.setReadOnly(readOnly);
        } catch (final SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    @Override
    public boolean isReadOnly() throws PersistenceException {
        this.doConnectionValidation(READONLY);

        boolean isReadOnly = false;

        try {
            isReadOnly = connection.isReadOnly();
        } catch (final SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        return isReadOnly;
    }

    @Override
    public String getDataBaseAlias() {
        return dataBaseAlias;
    }

    @Override
    public void setDataBaseAlias(final String dataBaseAlias) {
        this.dataBaseAlias = dataBaseAlias;
    }

    @Override
    public Connection getConnection() throws ConnectionException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = ConnectionProvider.getConnection(this.getDataBaseAlias(), readOnly);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return connection;
    }

    @Override
    public void close() throws PersistenceException {
        this.doConnectionValidation(CLOSE);

        try {
            connection.close();
        } catch (final SQLException e) {
            LOGGER.log(Level.WARNING, "CITFramework -> Close operation Failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean closeQuietly() {
        try {
            this.close();
            return true;
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Realiza validações para a {@link Connection} de acordo com o necessário
     *
     * @param operation
     *            nome da operação, apenas para log
     * @throws PersistenceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 25/08/2014
     */
    protected void doConnectionValidation(final String operation) throws PersistenceException {
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException(String.format("'%s' operation failed: connection is null or closed,", operation));
            }
        } catch (final SQLException e) {
            final String message = "Problema ao realizar validação de conexão: " + e.getMessage();
            LOGGER.log(Level.WARNING, message, e);
            throw new PersistenceException(message);
        }
    }

}
