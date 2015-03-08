package br.com.centralit.citcorpore.negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.centralit.citcorpore.integracao.ExternalConnectionDao;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilNumbersAndDecimals;
import br.com.citframework.util.UtilStrings;

/**
 * @author thiago.borges
 *
 */
@SuppressWarnings("rawtypes")
public class ExternalConnectionServiceEjb extends CrudServiceImpl implements ExternalConnectionService {

    private ExternalConnectionDao dao;

    @Override
    protected ExternalConnectionDao getDao() {
        if (dao == null) {
            dao = new ExternalConnectionDao();
        }
        return dao;
    }

    @Override
    public Collection getTables(final Integer idExternalConnection) throws Exception {
        Collection colTables = null;
        ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
        externalConnectionDTO.setIdExternalConnection(idExternalConnection);
        externalConnectionDTO = (ExternalConnectionDTO) this.getDao().restore(externalConnectionDTO);
        if (externalConnectionDTO != null) {
            String url = UtilStrings.nullToVazio(externalConnectionDTO.getUrlJdbc()).trim();
            if (externalConnectionDTO.getJdbcDbName() != null) {
                url = url + UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDbName()).trim();
            }
            final String driver = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDriver());
            final String userName = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcUser());
            final String password = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcPassword());
            final String schemadb = UtilStrings.nullToVazio(externalConnectionDTO.getSchemaDb());

            Connection conn = null;
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url, userName, password);
                System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - " + url);

                final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
                colTables = dataBaseMetaDadosUtil.readTables(conn, schemadb, schemadb, null, false);

                System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - " + url);
            } catch (final Exception e) {
                System.out.println("CITSMART - ExternalConnectionServiceEjb - PROBLEM - Connected to the database - " + url);
                e.printStackTrace();
                throw e;
            } finally {
                conn.close();
            }
        }
        return colTables;
    }

    @Override
    public Collection getLocalTables() throws Exception {
        Collection colTables = null;
        final VisaoDao visaoDao = new VisaoDao();
        Connection con = null;
        try {
            con = visaoDao.getTransactionControler().getConnection();
            String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
            if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
                DB_SCHEMA = null;
            } else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
                DB_SCHEMA = "citsmart";
            }
            final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
            colTables = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, false);
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return colTables;
    }

    @Override
    public Collection getFieldsTable(final Integer idExternalConnection, final String tableName) throws Exception {
        Collection colTables = null;
        ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
        externalConnectionDTO.setIdExternalConnection(idExternalConnection);
        externalConnectionDTO = (ExternalConnectionDTO) this.getDao().restore(externalConnectionDTO);
        if (externalConnectionDTO != null) {
            String url = UtilStrings.nullToVazio(externalConnectionDTO.getUrlJdbc()).trim();
            if (externalConnectionDTO.getJdbcDbName() != null) {
                url = url + UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDbName()).trim();
            }
            final String driver = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDriver());
            final String userName = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcUser());
            final String password = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcPassword());
            final String schemadb = UtilStrings.nullToVazio(externalConnectionDTO.getSchemaDb());

            Connection conn = null;
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url, userName, password);
                System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - " + url);

                final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
                colTables = dataBaseMetaDadosUtil.readTables(conn, schemadb, schemadb, tableName, false);

                System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - " + url);
            } catch (final Exception e) {
                System.out.println("CITSMART - ExternalConnectionServiceEjb - PROBLEM - Connected to the database - " + url);
                e.printStackTrace();
                throw e;
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }
        if (colTables != null && colTables.size() > 0) {
            final ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) ((List) colTables).get(0);
            return objNegocioDto.getColCampos();
        }
        return null;
    }

    @Override
    public Collection getFieldsLocalTable(final String tableName) throws Exception {
        Collection colTables = null;
        final VisaoDao visaoDao = new VisaoDao();
        Connection con = null;
        try {
            con = visaoDao.getTransactionControler().getConnection();
            String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
            if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
                DB_SCHEMA = "citsmart";
            }
            final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
            colTables = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, tableName, false);

            if (colTables != null && colTables.size() > 0) {
                final ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) ((List) colTables).get(0);
                return objNegocioDto.getColCampos();
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public Connection getConnectionExternal(final Integer idExternalConnection) throws Exception {
        ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
        externalConnectionDTO.setIdExternalConnection(idExternalConnection);
        externalConnectionDTO = (ExternalConnectionDTO) this.getDao().restore(externalConnectionDTO);
        Connection conn = null;
        if (externalConnectionDTO != null) {
            String url = UtilStrings.nullToVazio(externalConnectionDTO.getUrlJdbc()).trim();
            if (externalConnectionDTO.getJdbcDbName() != null) {
                url = url + UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDbName()).trim();
            }
            final String driver = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDriver());
            final String userName = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcUser());
            final String password = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcPassword());

            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url, userName, password);
                System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - " + url);

                return conn;
            } catch (final Exception e) {
                System.out.println("CITSMART - ExternalConnectionServiceEjb - PROBLEM - Connected to the database - " + url);
                e.printStackTrace();
                throw e;
            }
        }
        return conn;
    }

    /**
     * Processa os dados cadastrados no ImportManager
     *
     */
    @Override
    public void processImport(final ImportManagerDTO importManagerDTO, final List colMatrizTratada) throws Exception {

        String sqlOrigem = "";
        String sqlDestino = "";
        String sqlDestinoValues = "";

        for (final Iterator it = colMatrizTratada.iterator(); it.hasNext();) {

            final HashMap mapItem = (HashMap) it.next();
            final String idOrigem = (String) mapItem.get("IDORIGEM");

            if (!idOrigem.equalsIgnoreCase("##AUTO##")) {
                if (!sqlOrigem.trim().equalsIgnoreCase("")) {
                    sqlOrigem += ",";
                }
                sqlOrigem += idOrigem;
            }

            final String idDestino = (String) mapItem.get("IDDESTINO");
            if (!sqlDestino.trim().equalsIgnoreCase("")) {
                sqlDestino += ",";
                sqlDestinoValues += ",";
            }
            sqlDestino += idDestino;
            sqlDestinoValues += "?";

        }

        sqlOrigem = "SELECT " + sqlOrigem + " FROM " + importManagerDTO.getTabelaOrigem();
        sqlDestino = "INSERT INTO " + importManagerDTO.getTabelaDestino() + " (" + sqlDestino + ") VALUES (" + sqlDestinoValues + ")";

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = this.getConnectionExternal(importManagerDTO.getIdExternalConnection());
            ps = conn.prepareStatement(sqlOrigem);
            rs = ps.executeQuery();
            synchronized (colMatrizTratada) {
                while (rs.next()) {

                    int i = 0;
                    final Object[] objs = new Object[colMatrizTratada.size()];
                    for (final Iterator it = colMatrizTratada.iterator(); it.hasNext();) {
                        Object o = null;
                        final HashMap mapItem = (HashMap) it.next();
                        final String idOrigem = (String) mapItem.get("IDORIGEM");
                        final String idDestino = (String) mapItem.get("IDDESTINO");
                        String script = (String) mapItem.get("SCRIPT");

                        if (idOrigem.equalsIgnoreCase("##AUTO##")) {
                            o = this.getDao().getLastValueFromDestino(importManagerDTO, idDestino);
                        } else {
                            o = rs.getObject(i + 1);
                            i++;
                        }
                        if (script != null && !script.trim().equalsIgnoreCase("")) {
                            final org.mozilla.javascript.Context cx = org.mozilla.javascript.Context.enter();
                            final org.mozilla.javascript.Scriptable scope = cx.initStandardObjects();

                            script = script.replaceAll("TEXTSEARCH", "utilStrings.generateNomeBusca");
                            script = script.replaceAll("GETFIELD", "hashMapUtil.getFieldInHash");

                            String compl = "var importNames = JavaImporter();\n";

                            compl += "importNames.importPackage(Packages.br.com.citframework.util);\n";
                            compl += "importNames.importPackage(Packages.br.com.centralit.citcorpore.metainfo.util);\n";

                            script = compl + "\n" + script;

                            scope.put("object", scope, o);
                            scope.put("mapItem", scope, mapItem);
                            scope.put("importManagerDTO", scope, importManagerDTO);
                            scope.put("utilStrings", scope, new UtilStrings());
                            scope.put("utilNumbersAndDecimals", scope, new UtilNumbersAndDecimals());
                            scope.put("utilDatas", scope, new UtilDatas());

                            o = importManagerDTO.getResult();
                        }
                        objs[i] = o;
                    }
                    this.getDao().executeSQLUpdate(sqlDestino, objs);
                }
            }

        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void deletarConexao(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final ExternalConnectionDTO conexoesDto = (ExternalConnectionDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);
            this.getDao().setTransactionControler(tc);
            tc.start();

            conexoesDto.setDeleted("S");
            this.getDao().update(model);
            document.alert(this.i18nMessage("MSG07"));
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public boolean consultarConexoesAtivas(final ExternalConnectionDTO obj) throws Exception {
        return this.getDao().consultarConexoesAtivas(obj);
    }

    @Override
    public Collection<ExternalConnectionDTO> seConexaoJaCadastrada(final ExternalConnectionDTO conexoesDTO) throws Exception {
        return this.getDao().seConexaoJaCadastrada(conexoesDTO);
    }

    @Override
    public Collection<ExternalConnectionDTO> listarAtivas() throws Exception {
        return this.getDao().listarAtivas();
    }
}
