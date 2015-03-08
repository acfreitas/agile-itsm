package br.com.citframework.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.util.AdaptacaoBD;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.ParametroDTO;
import br.com.citframework.integracao.ParametroDao;

public final class SQLConfig {

    private static final Logger LOGGER = Logger.getLogger(SQLConfig.class);

    private SQLConfig() {}

    public static String SGBD_PRINCIPAL = null;
    public static final String MYSQL = "MYSQL";
    public static final String ORACLE = "ORACLE";
    public static final String DB2 = "DB2";
    public static final String SQLSERVER = "SQLSERVER";
    public static final String POSTGRESQL = "POSTGRES";

    public static String traduzSQL(final String databaseAlias, final String sql) {
        String sqlAux = sql;

        if (databaseAlias.equalsIgnoreCase("jdbc/bi_citsmart")) {
            // Se o executor da query for o database do BI Citsmart, realiza somente os tratamentos para SQL Server (BI Corpore é somente utilizado no SQL Server).
            sqlAux = sqlAux.replaceAll("LENGTH", "LEN");
            sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
        } else {
            // Se o executor da query for o database principal, realiza os tratamentos para o banco que estiver sendo utilizado.
            if (SGBD_PRINCIPAL == null) {
                if (CITCorporeUtil.SGBD_PRINCIPAL == null || CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("")) {
                    AdaptacaoBD.getBancoUtilizado(); // este metodo atualizada o valor de CITCorporeUtil.SGBD_PRINCIPAL
                }
                SGBD_PRINCIPAL = CITCorporeUtil.SGBD_PRINCIPAL;
                if (SGBD_PRINCIPAL != null) {
                    SGBD_PRINCIPAL = SGBD_PRINCIPAL.toUpperCase();
                }
                SGBD_PRINCIPAL = UtilStrings.nullToVazio(SGBD_PRINCIPAL).trim();
                LOGGER.info("CIT Framework - Identificando o SGBD: " + SGBD_PRINCIPAL);
            }
            if (StringUtils.isBlank(SGBD_PRINCIPAL)) {
                SGBD_PRINCIPAL = ORACLE;
                LOGGER.info("CIT Framework - Identificando o SGBD: " + SGBD_PRINCIPAL);
            }
            // --- INFORMACOES PARA DB2 ----
            if (SGBD_PRINCIPAL.equalsIgnoreCase(DB2)) {
                sqlAux = sqlAux.replaceAll("UPPER", "UCASE");
                sqlAux = sqlAux.replaceAll("\\{DATAATUAL\\}", "CURRENT_DATE");
                sqlAux = sqlAux.replaceAll(" \\_AS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll(" \\_as\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll(" \\_As\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll(" \\_aS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
            }

            // --- INFORMACOES PARA ORACLE ----
            if (SGBD_PRINCIPAL.equalsIgnoreCase(ORACLE)) {
                sqlAux = sqlAux.toUpperCase();
                sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
                sqlAux = sqlAux.replaceAll("\\{DATAATUAL\\}", "CURRENT_DATE");
                sqlAux = sqlAux.replaceAll("'9999-12-31'", "TO_DATE('31/12/9999')");
                if (sqlAux.contains(" AS VARCHAR2(4000)")) {
                    sqlAux = sqlAux.replaceAll(" AS ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                } else {
                    sqlAux = sqlAux.replaceAll(" AS ", " ");
                }
                sqlAux = sqlAux.replaceAll(" \\_AS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll("\"", "'");
                sqlAux = sqlAux.replaceAll("SUBSTRING", "SUBSTR");
                sqlAux = sqlAux.replaceAll("!=", "<>"); // TRATA A SINTAXE DE DIFERENTE
                if (sqlAux.indexOf("FETCH FIRST 1 ROWS ONLY") > -1) {
                    sqlAux = sqlAux.replaceAll(" FETCH FIRST 1 ROWS ONLY ", " "); // TIRA A PALAVRA AS DOS SQLs
                    if (sqlAux.indexOf(" ORDER ") > -1) {
                        if (sqlAux.indexOf(" WHERE ") > -1) {
                            sqlAux = sqlAux.replaceAll(" ORDER ", " AND ROWNUM <= 1 ORDER "); // TIRA A PALAVRA AS DOS SQLs
                        } else {
                            sqlAux = sqlAux.replaceAll(" ORDER ", " WHERE ROWNUM <= 1 ORDER "); // TIRA A PALAVRA AS DOS SQLs
                        }
                    } else {
                        if (sqlAux.indexOf(" WHERE ") > -1) {
                            sqlAux = sqlAux + " AND ROWNUM <= 1";
                        } else {
                            sqlAux = sqlAux + " WHERE ROWNUM <= 1";
                        }
                    }
                }

                if (!StringUtils.contains(sqlAux, "'DELETED'")) {
                    final Pattern pat = Pattern.compile("[\\w*\\.]*DELETED");
                    final Matcher mat = pat.matcher(sqlAux);
                    if (mat.find() && StringUtils.contains(sqlAux, "SELECT")) {
                        sqlAux = sqlAux.replaceAll("[\\w*\\.]*DELETED", "UPPER(" + mat.group() + ")");
                    }
                }
                sqlAux = sqlAux.toUpperCase();
            }

            // --- INFORMACOES PARA POSTGRESQL ----
            if (SGBD_PRINCIPAL.equalsIgnoreCase(POSTGRESQL)) {
                sqlAux = sqlAux.replaceAll("SYSDATE", "now()");
                sqlAux = sqlAux.replaceAll(" \\_AS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll(" \\_as\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll(" \\_As\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll(" \\_aS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
                sqlAux = sqlAux.replaceAll("\\{DATAATUAL\\}", "now()");
                sqlAux = sqlAux.replaceAll("TRUNC", "");
                sqlAux = sqlAux.replaceAll("\"", "'");
                sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
                sqlAux = sqlAux.replaceAll("'9999-12-31'", "TO_DATE('31/12/9999')");

                if (!StringUtils.contains(sqlAux, "'DELETED'")) {
                    final Pattern pat = Pattern.compile("[\\w*\\.]*DELETED");
                    final Matcher mat = pat.matcher(sqlAux);
                    if (mat.find() && !StringUtils.contains(sqlAux, "UPDATE") && !StringUtils.contains(sqlAux, "INSERT")) {
                        sqlAux = sqlAux.replaceAll("[\\w*\\.]*DELETED", "UPPER(" + mat.group() + ")");
                    }
                }
            }

            // --- INFORMACOES PARA SQLSERVER ----
            if (SGBD_PRINCIPAL.equalsIgnoreCase(SQLSERVER)) {
                sqlAux = sqlAux.replaceAll("LENGTH", "LEN");
                sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
            }

            if (SGBD_PRINCIPAL.equalsIgnoreCase(MYSQL)) {
                sqlAux = sqlAux.toLowerCase();
            }

            // Converte Elementos configurados conforme o banco de dados.
            final XmlReadDBItemConvertion xmlReadDB = XmlReadDBItemConvertion.getInstance(SGBD_PRINCIPAL);
            if (xmlReadDB != null) {
                final Collection<DBItemConvertion> col = xmlReadDB.getItens();
                if (col != null) {
                    for (final DBItemConvertion dbItemConvertion : col) {
                        sqlAux = sqlAux.replaceAll(dbItemConvertion.getNameToBeConverted().toUpperCase(), dbItemConvertion.getNameAfterConversion());
                    }
                }
            }

            final ParametroDao parametroDAO = new ParametroDao();

            while (sqlAux.toUpperCase().indexOf("{GETPARAMETER") > -1) {
                String valorParametro = "NULL";
                final int pos = sqlAux.toUpperCase().indexOf("{GETPARAMETER");
                final int ini = sqlAux.indexOf("(", pos);
                final int fim = sqlAux.indexOf(")", ini);
                final int sep = sqlAux.indexOf(",", ini);
                final String nomeModulo = sqlAux.substring(ini + 1, sep);
                final String nomeParametro = sqlAux.substring(sep + 1, fim);
                try {
                    final ParametroDTO parametro = parametroDAO.getValue(nomeModulo, nomeParametro, new Integer(Constantes.getValue("ID_EMPRESA_PROC_BATCH")));
                    if (StringUtils.isNotBlank(parametro.getValor())) {
                        valorParametro = parametro.getValor();
                        valorParametro = valorParametro.replaceAll("\n", "");
                        valorParametro = valorParametro.replaceAll("\r", "");
                        valorParametro = valorParametro.replaceAll("\\\n", "");
                        valorParametro = valorParametro.replaceAll("\\\r", "");
                    }
                } catch (final Exception e) {
                    LOGGER.warn(e.getMessage(), e);
                }
                sqlAux = sqlAux.substring(0, pos) + valorParametro + sqlAux.substring(fim + 2, sqlAux.length());
            }

            sqlAux = sqlAux.replaceAll("\\{OWNER\\}", Constantes.getValue("OWNER_DB"));
            sqlAux = sqlAux.replaceAll("\\{OWNER_BD\\}", Constantes.getValue("OWNER_DB"));
        }

        return sqlAux;
    }

}
