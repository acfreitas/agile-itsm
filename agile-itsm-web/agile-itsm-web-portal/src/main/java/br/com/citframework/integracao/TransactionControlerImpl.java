package br.com.citframework.integracao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.TransactionOperationException;

public class TransactionControlerImpl extends ConnectionControlerImpl implements TransactionControler {

    private static final String COMMIT = "Commit";
    private static final String ROLLBACK = "Rollback";
    private static final String SAVEPOINT = "Savepoint";

    public TransactionControlerImpl(final String dataBaseAlias) {
        super(dataBaseAlias);
    }

    @Override
    public void setReadOnly(final boolean readOnly) throws PersistenceException {
        throw new IllegalStateException("Não é possível uma conexão com transação ser READONLY. Considere usar ConnectionControler.");
    }

    @Override
    public boolean isStarted() {
        boolean started = false;
        try {
            started = connection != null && !connection.isClosed() ? !connection.getAutoCommit() : false;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return started;
    }

    @Override
    public void start() throws PersistenceException {
        if (this.isStarted()) {
            throw new IllegalStateException("Start operation failed: transaction already started.");
        }

        try {
            connection = this.getConnection();

            connection.setAutoCommit(false);

            /**
             * Alterado em 10.01.2014 por valdoilo.damasceno
             *
             * Essa validação não representava a situação real da conexão. O Citsmart pode utilizar vários BD portanto a obtenção de qual está sendo utilizado deverá ser feita
             * através da conexão ativa
             * no start da transaction.
             */
            if ("MYSQL".equalsIgnoreCase(connection.getMetaData().getDatabaseProductName())) {
                final String sqlSetAutocommit = "SET autocommit = 0;";
                try (final PreparedStatement ps = connection.prepareStatement(sqlSetAutocommit)) {
                    ps.execute();
                }
            }
        } catch (final SQLException e) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (final SQLException ex) {
                throw new TransactionOperationException("Start operation Failed", ex);
            }
        }
    }

    @Override
    public void commit() throws PersistenceException {
        this.doConnectionValidation(COMMIT);

        try {
            connection.commit();
        } catch (final SQLException e) {
            final String message = "Commit operation Failed: " + e.getMessage();
            throw new TransactionOperationException(message, e);
        }
    }

    @Override
    public void rollback() throws PersistenceException {
        this.doConnectionValidation(ROLLBACK);

        try {
            connection.rollback();
        } catch (final SQLException e) {
            final String message = "CITFramework -> Rollback operation Failed: " + e.getMessage();
            throw new PersistenceException(message, e);
        }
    }

    @Override
    public void rollback(final Savepoint savepoint) throws PersistenceException {
        this.doConnectionValidation(ROLLBACK);

        try {
            connection.rollback(savepoint);
        } catch (final SQLException e) {
            final String message = String.format("CITFramework -> Rollback operation Failed for savepoint %s: ", savepoint) + e.getMessage();
            throw new PersistenceException(message, e);
        }
    }

    @Override
    public void close() throws PersistenceException {
        this.doConnectionValidation(CLOSE);
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (final SQLException e) {
            final String message = "CITFramework -> Close operation Failed: " + e.getMessage();
            throw new PersistenceException(message, e);
        }
    }

    @Override
    public Savepoint savepoint() throws PersistenceException {
        this.doConnectionValidation(SAVEPOINT);

        try {
            return connection.setSavepoint();
        } catch (final SQLException e) {
            final String message = "Problema ao setar savepoint: " + e.getMessage();
            throw new PersistenceException(message);
        }
    }

    @Override
    public Savepoint savepoint(final String name) throws PersistenceException {
        this.doConnectionValidation(SAVEPOINT);

        try {
            return connection.setSavepoint(name);
        } catch (final SQLException e) {
            final String message = String.format("Problema ao setar savepoint nomeado '%s': ", name) + e.getMessage();
            throw new PersistenceException(message, e);
        }
    }

    @Override
    public void releaseSavepoint(final Savepoint savepoint) throws PersistenceException {
        this.doConnectionValidation(SAVEPOINT);

        String spName = "no-name";

        try {
            spName = savepoint.getSavepointName();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        try {
            connection.releaseSavepoint(savepoint);
        } catch (final SQLException e) {
            final String message = String.format("Problema ao liberar savepoint nomeado '%s': ", spName) + e.getMessage();
            throw new PersistenceException(message);
        }
    }

    @Override
    protected void doConnectionValidation(final String operation) throws PersistenceException {
        if (!this.isStarted()) {
            // throw new IllegalStateException(String.format("'%s' operation failed: transaction is not started.", operation));
            // this.start(); // TODO retirar e lançar exceção assim que, tanto framework quanto CITSMart, estejam trabalhando corretamente com transações e connections e usar
        }
    }

}
