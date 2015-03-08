package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.DuplicateUniqueException;
import br.com.citframework.excecao.InvalidParameterException;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.MandatoryParameterNotFound;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Mensagens;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.Util;
import br.com.citframework.util.UtilStrings;

public class JdbcEngine {

    private static final Logger LOGGER = Logger.getLogger(JdbcEngine.class.getName());

    private String dataBaseAlias;

    protected Usuario usuario;
    protected TransactionControler transactionControler;

    public JdbcEngine(final String dataBaseAlias, final Usuario usuario) {
        this.usuario = usuario;
        this.dataBaseAlias = dataBaseAlias;
    }

    public JdbcEngine(final TransactionControler transactionControler, final Usuario usuario) {
        this(transactionControler.getDataBaseAlias(), usuario);
        this.transactionControler = transactionControler;
    }

    public TransactionControler getTransactionControler() {
        if (transactionControler == null || !transactionControler.isStarted()) {
            transactionControler = new TransactionControlerImpl(dataBaseAlias);
        }
        return transactionControler;
    }

    public void setTransactionControler(final TransactionControler transactionControler) {
        this.transactionControler = transactionControler;
    }

    protected List execSQL(final String sql, final Object[] parametros) throws PersistenceException {
        return this.execSQL(sql, parametros, 0);
    }

    public List execSQL(final String sql, final Object[] parametros, final int maxRows) throws PersistenceException {
        if (StringUtils.isBlank(sql)) {
            throw new MandatoryParameterNotFound("SQL IS MANDATORY");
        }

        final List<Object> result = new ArrayList<>();

        final StringBuilder params = new StringBuilder();
        if (parametros != null) {
            for (final Object valor : parametros) {
                params.append(valor).append(",");
            }
        }

        final TransactionControler tc = this.getTransactionControler();
        boolean begin = true;

        if (!tc.isStarted()) {
            begin = false;
            tc.start();
        }

        try (final PreparedStatement ps = this.getPreparedStatement(tc.getConnection(), sql, parametros); final ResultSet rs = ps.executeQuery()) {
            int colunas = 0;
            if (rs != null) {
                colunas = rs.getMetaData().getColumnCount();

                while (rs.next()) {
                    Object o = null;
                    final Object[] row = new Object[colunas];
                    for (int i = 0; i < colunas; i++) {
                        if (rs.getMetaData().getColumnType(i + 1) != Types.TIMESTAMP) {
                            o = rs.getObject(i + 1);
                        } else {
                            try {
                                o = rs.getTimestamp(i + 1);
                            } catch (final Exception e) {
                                LOGGER.log(Level.WARNING, e.getMessage(), e);
                                try {
                                    o = rs.getObject(i + 1);
                                } catch (final Exception e1) {
                                    LOGGER.log(Level.WARNING, e1.getMessage(), e1);
                                }
                            }
                        }

                        if (o != null) {
                            final String className = o.getClass().getName();
                            final String classNameUpper = className.toUpperCase();
                            final int classNameCLOB = classNameUpper.indexOf("CLOB");
                            final int classNameVB = classNameUpper.indexOf("VB");

                            if ("com.ibm.db2.jcc.c.bs".equalsIgnoreCase(className) || classNameCLOB != -1 || classNameVB != -1) {
                                row[i] = rs.getString(i + 1);
                            } else if ("com.ibm.db2.jcc.am.ie".equalsIgnoreCase(className) || classNameCLOB != -1 || classNameVB != -1) {
                                row[i] = rs.getString(i + 1);
                            } else if ("com.ibm.db2.jcc.b.cc".equalsIgnoreCase(className) || classNameCLOB != -1 || classNameVB != -1) {
                                row[i] = rs.getString(i + 1);
                            } else {
                                row[i] = o;
                            }
                        }
                    }

                    result.add(row);
                }
            }

            if (!begin) {
                tc.commit();
            }

            return result;
        } catch (final Exception ex) {
            try {
                if (!begin) {
                    tc.rollback();
                }
            } catch (final Exception exRollback) {}

            final String message = "SQL ERROR: " + SQLConfig.traduzSQL(this.getDataBaseAlias(), sql) + "\n\t parameters: " + params + " \n\t*** ERROR: " + ex.getMessage();

            LOGGER.log(Level.SEVERE, message, ex);

            throw new PersistenceException(message, ex);
        } finally {
            if (!begin) {
                tc.closeQuietly();
            }
        }
    }

    public int execUpdate(final String sql, final Object[] parametros) throws PersistenceException {
        if (sql == null || sql.length() == 0) {
            throw new MandatoryParameterNotFound("SQL IS MANDATORY");
        }

        final StringBuilder params = new StringBuilder();
        if (parametros != null) {
            for (final Object valor : parametros) {
                params.append(valor).append(",");
            }
        }

        final String sqlExecutar = SQLConfig.traduzSQL(this.getDataBaseAlias(), sql);

        final TransactionControler tc = this.getTransactionControler();

        boolean begin = true;
        if (!tc.isStarted()) {
            begin = false;
            tc.start();
        }

        String strSGBDPrincipal = null;
        if (strSGBDPrincipal == null) {
            strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
            strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
        }

        try (final PreparedStatement ps = this.getPreparedStatement(tc.getConnection(), sqlExecutar, parametros)) {
            final int result = ps.executeUpdate();

            if (!begin) {
                tc.commit();
            }

            return result;
        } catch (final Exception ex) {
            if (!begin) {
                tc.rollback();
            }

            final String message = "SQL ERROR: " + sqlExecutar + "\n\t parameters: " + params + " \n\t*** ERROR: " + ex.getMessage();
            throw new PersistenceException(message, ex);
        } finally {
            if (!begin) {
                tc.closeQuietly();
            }
        }
    }

    public PreparedStatement getPreparedStatement(final Connection con, final String sql, final Object[] parametros) throws Exception {
        if (con == null || con.isClosed()) {
            throw new IllegalArgumentException("A conexão não pode ser nula ou estar fechada.");
        }

        PreparedStatement ps = null;

        final String sqlExecutar = SQLConfig.traduzSQL(this.getDataBaseAlias(), sql);

        try {
            ps = con.prepareStatement(sqlExecutar);

            String sAux;
            final StringBuilder parametro = new StringBuilder();

            if (parametros != null) {
                for (int i = 0; i < parametros.length; i++) {
                    final Object valor = parametros[i];
                    if (i > 0) {
                        parametro.append(",");
                    }
                    parametro.append(valor);

                    try {
                        if (valor == null) {
                            ps.setObject(i + 1, null);
                        } else {
                            if (valor instanceof Integer) {
                                ps.setInt(i + 1, ((Integer) valor).intValue());
                            } else if (valor instanceof String) {
                                sAux = (String) valor;
                                ps.setString(i + 1, sAux);
                            } else {
                                ps.setObject(i + 1, valor);
                            }
                        }
                    } catch (final Exception e) {
                        final String message = "Wrong Parameter " + (i + 1) + ". SQL: " + sql + " " + e.getMessage();
                        LOGGER.log(Level.WARNING, message, e);
                        throw new InvalidParameterException(message);
                    }
                }
            }

            if (Constantes.getValue("DEBUG_PERSISTENCE") != null && Constantes.getValue("DEBUG_PERSISTENCE").trim().equalsIgnoreCase("true")) {
                System.out.println("[DEBUG PERSISTENCE] SQL: " + sql);
                System.out.println("[DEBUG PERSISTENCE] PARAMS: " + parametro);
            }
        } catch (final SQLException e1) {
            throw new PersistenceException(e1);
        }

        return ps;
    }

    public List listConvertion(final Class<?> classe, final List dados, final List fields) throws PersistenceException {
        if (dados == null || dados.size() == 0) {
            return new ArrayList<>(0);
        }

        final List<Object> result = new ArrayList<>(dados.size());
        final Iterator<Object> it = dados.iterator();

        while (it.hasNext()) {
            try {
                final Object obj = classe.newInstance();
                final Object[] row = (Object[]) it.next();
                for (int i = 0; i < fields.size(); i++) {

                    String atributoClasse = "";
                    if (fields.get(i) instanceof Field) {
                        atributoClasse = ((Field) fields.get(i)).getFieldClass();
                    } else {
                        atributoClasse = fields.get(i).toString();
                    }

                    Reflexao.setPropertyValue(obj, atributoClasse, row[i]);
                }
                result.add(obj);
            } catch (final Exception e) {
                throw new PersistenceException(e);
            }
        }
        return result;
    }

    protected void validRelationship(final String nomeTabela, final String[] campos, final Object[] valores, final String aliasTabela) throws PersistenceException, LogicException {
        final StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(nomeTabela);

        for (int i = 0; i < campos.length; i++) {
            if (i == 0) {
                sql.append(" where ");
            } else {
                sql.append(" and ");
            }

            sql.append(campos[i]).append(" = ? ");
        }

        final StringBuilder params = new StringBuilder();
        if (valores != null) {
            for (final Object valor : valores) {
                params.append(valor).append(",");
            }
        }

        final TransactionControler tc = this.getTransactionControler();
        final Connection con = tc.getConnection();

        try (final PreparedStatement ps = this.getPreparedStatement(con, sql.toString(), valores); final ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                final String message = Mensagens.getValue("MSG08") + " " + aliasTabela;
                LOGGER.log(Level.WARNING, message);
                throw new LogicException(message);
            }
        } catch (final Exception e) {
            final String message = "SQL ERROR: " + SQLConfig.traduzSQL(this.getDataBaseAlias(), sql.toString()) + "\n\t parameters: " + params + " \n\t*** ERROR: "
                    + e.getMessage();
            LOGGER.log(Level.WARNING, message, e);
            throw new PersistenceException(message);
        }
    }

    protected void validUniqueKey(final String nomeTabela, final String campo, final String msgRetorno, final Object valor, final List<Field> camposChave,
            final List<Object> valoresChave) throws Exception {
        if (valor == null) {
            return;
        }

        final StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(nomeTabela).append(" where ");

        final List<Object> params = new ArrayList<>();

        if (valor instanceof String) {
            sql.append(Util.comparacaoSQLString(campo, "=", valor.toString(), params));
        } else {
            sql.append(campo).append(" = ? ");
            params.add(valor);
        }

        if (camposChave != null && valoresChave != null) {
            for (int i = 0; i < camposChave.size(); i++) {
                final Field cmp = camposChave.get(i);
                if (valoresChave.get(i) != null) {
                    sql.append(" and ").append(cmp.getFieldDB()).append(" <> ? ");
                    params.add(valoresChave.get(i));
                }
            }
        }

        final TransactionControler tc = this.getTransactionControler();
        final Connection con = tc.getConnection();

        try (final PreparedStatement ps = this.getPreparedStatement(con, sql.toString(), params.toArray()); final ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                if (StringUtils.isNotBlank(msgRetorno)) {
                    throw new DuplicateUniqueException(Mensagens.getValue("MSG12") + " " + msgRetorno);
                }
                throw new DuplicateUniqueException(Mensagens.getValue("MSG12") + " " + campo);
            }
        } catch (final SQLException exSql) {
            final String message = "SQL ERROR: " + sql;
            LOGGER.log(Level.SEVERE, message);
            throw new Exception(message);
        }
    }

    protected String getDataBaseAlias() {
        return dataBaseAlias;
    }

    protected void setDataBaseAlias(final String dataBaseAlias) {
        this.dataBaseAlias = dataBaseAlias;
    }

}
