package br.com.centralit.citcorpore.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilStrings;

/**
 * @author fernando.gom
 */
public class AdaptacaoBD {

    /**
     * Arquivo de configura��o das adapta��es.
     */
    private static Properties prop = null;

    /**
     * M�todo construtor.
     */
    protected AdaptacaoBD() {}

    /**
     * @return adapta��es configuradas.
     */
    private static Properties getProp() {
        if (prop == null) {
            prop = new Properties();
            try {
                InputStream inADPDB = null;
                try {
                    inADPDB = AdaptacaoBD.class.getResourceAsStream("/WEB-INF/classes/AdaptacaoBancos.properties");
                } catch (final Exception e) {}
                if (inADPDB == null) {
                    try {
                        inADPDB = new FileInputStream(CitAjaxUtil.CAMINHO_REAL_APP + "/WEB-INF/classes/AdaptacaoBancos.properties");
                    } catch (final Exception e) {}
                }
                prop.load(inADPDB);
            } catch (final Exception e) {
                e.printStackTrace();
                throw new Error("Arquivo de configura��o " + "'AdaptacaoBancos.properties' n�o encontrado.");
            }
        }
        return prop;
    }

    /**
     * @param ds
     *            - DataSource que cont�m os dados de conex�o.
     * @return identificador do banco de dados obtido do DataSource.
     */
    public static String getBancoUtilizado(final DataSource ds) {
        if (ds != null) {
            try (Connection conn = ds.getConnection();) {
                return getBancoUtilizadoByDBProductName(conn.getMetaData().getDatabaseProductName());
            } catch (final Exception e) {
                e.printStackTrace();
                System.out.println("CITSMART -> atencao: N�o � poss�vel obter a URL de conex�o do DataSource! Assumindo padr�o: ORACLE!");
                return "oracle"; // retorna o padrao.
            }
        }
        return "oracle"; // retorna o padrao.
    }

    public static String getBancoUtilizado() {
        final String nomeDatabaseAlias = Constantes.getValue("DATABASE_ALIAS");
        return getBancoUtilizado(nomeDatabaseAlias);
    }

    public static String getBancoUtilizado(final String nomeDatabaseAlias) {
        try (Connection conn = ConnectionProvider.getConnection(nomeDatabaseAlias);) {
            return getBancoUtilizadoByDBProductName(conn.getMetaData().getDatabaseProductName());
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("CITSMART -> atencao: N�o � poss�vel obter a URL de conex�o do DataSource! Assumindo padr�o: ORACLE!");
            return "oracle"; // retorna o padrao.
        }
    }

    public static String getBancoUtilizadoByDBProductName(final String databaseProductName) {
        String db = databaseProductName.replaceAll(" ", "").toLowerCase();

        if (db.indexOf("sqlserver") >= 0) {
            db = "sqlserver";
        } else if (db.indexOf("oracle") >= 0) {
            db = "oracle";
        } else if (db.indexOf("db2") >= 0) {
            db = "db2";
        } else if (db.indexOf("PostgreSQL".toLowerCase()) >= 0) {
            db = "postgres";
        } else if (db.indexOf("Derby".toLowerCase()) >= 0) {
            db = "derby";
        } else if (db.indexOf("mysql") >= 0) {
            db = "mysql";
        }

        return db;
    }

    /**
     * Retorna o comando SQL para obter a data.
     *
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getDate() {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "date";
        return getProp().getProperty(p);
    }

    /**
     * Retorna o comando SQL para obter o timestamp.
     *
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getTimestamp() {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "timestamp";
        return getProp().getProperty(p);
    }

    /**
     * Retorna o comando SQL para obter a hora.
     *
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getTime() {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "time";
        return getProp().getProperty(p);
    }

    /**
     * Retorna o comando SQL para obter o dia de uma data.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou a pr�pria data a ser convertida.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getDay(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "day";
        String adap = getProp().getProperty(p);

        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }

        return adap;
    }

    public static String getUpperFunction(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "upper";
        String adap = getProp().getProperty(p);

        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            adap = "upper(" + sourceData + ")";
        }

        return adap;
    }

    /**
     * Retorna o comando SQL para obter o dia da semana de uma data.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou a pr�pria data a ser convertida.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getDayOfWeek(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "dayofweek";
        String adap = getProp().getProperty(p);
        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }
        return adap;
    }

    /**
     * Retorna o comando SQL para obter o n�mero do m�s de uma data.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou a pr�pria data a ser convertida.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getMonth(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "month";
        String adap = getProp().getProperty(p);

        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }

        return adap;
    }

    /**
     * Retorna o comando SQL para obter o ano de uma data.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou a pr�pria data a ser convertida.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getYear(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "year";
        String adap = getProp().getProperty(p);

        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }

        return adap;
    }

    /**
     * Retorna o comando SQL para setar o formato padr�o de datas.
     *
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getSetDateFormat() {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "setdateformat";
        return getProp().getProperty(p);
    }

    /**
     * Retorna o comando SQL para convers�o de algum campo para varchar.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou a pr�pria data a ser convertida.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getCastToChar(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "casttochar";
        String adap = getProp().getProperty(p);

        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }

        return adap;
    }

    /**
     * Retorna o comando SQL para convers�o de campos CLOB's em varchar.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou a pr�pria data a ser convertida.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getClobToVarchar(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "clobtovarchar";
        String adap = getProp().getProperty(p);

        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }

        return adap;
    }

    /**
     * Ajusta o SQL para retornar apenas a quantidade de registros desejada.
     *
     * @param select
     *            - script SQL que ser� ajustado.
     * @param qtd
     *            - quantidade de linhas que ser�o retornadas.
     * @param orderBy
     *            - condi��o ORDER BY para ajuste do SQL. N�o � obrigat�rio.
     * @return SQL ajustada.
     */
    public static String getFetchFirst(final String select, final Integer qtd, final String orderBy) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "fetchfirst";
        String adap = getProp().getProperty(p);
        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("[{][0][0][}]", qtd.toString());
            adap = adap.replaceAll("[{][0][1][}]", select);
            adap = adap.replaceAll("[{][0][2][}]", orderBy);
        }
        return adap;
    }

    /**
     * Retorna o comando SQL para obter as horas e minutos a partir de um campo que armazena data e hora. O formato retornado � <i>HH:mm</i>.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou o pr�prio timestamp a ser convertido.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getConvertDatetimeToHHMMChar(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "convertdatetimetohhmmchar";
        String adap = getProp().getProperty(p);
        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }
        return adap;
    }

    /**
     * Retorna o comando SQL para obter a data a partir de um campo que armazena data e hora. O formato retornado � <i>DD/MM/YYYY</i>.
     *
     * @param sourceData
     *            - Fonte da informa��o a ser convertida. Pode ser o nome de um campo ou o pr�prio timestamp a ser convertido.
     * @return adapta��o referente ao banco de dados acessado pelo DataSource.
     */
    public static String getConvertDateToDDMMYYYYChar(final String sourceData) {
        String p = CITCorporeUtil.SGBD_PRINCIPAL;
        if (p != null && !p.equalsIgnoreCase("")) {
            p += ".";
        }
        p += "convertdatetoddmmyyyychar";
        String adap = getProp().getProperty(p);
        if (UtilStrings.isNotVazio(adap)) {
            adap = adap.replaceAll("x", sourceData);
        }
        return adap;
    }

}
