package br.com.centralit.citcorpore.metainfo.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.DataManagerFKRelatedDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DataBaseMetaDadosUtil {

    public String sincronizaObjNegDB(final String nomeTabela, final boolean messages) throws ServiceException, Exception {
        final VisaoDao visaoDao = new VisaoDao();
        final Connection con = visaoDao.getTransactionControler().getConnection();
        String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
        if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            DB_SCHEMA = null;
        } else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
            DB_SCHEMA = "citsmart";
        }
        final Collection colObsNegocio = this.readTables(con, DB_SCHEMA, DB_SCHEMA, nomeTabela, messages);
        con.close();

        String carregados = "";
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        for (final Iterator it = colObsNegocio.iterator(); it.hasNext();) {
            final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
            if (messages) {
                System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());
            }
            carregados += objetoNegocioDTO.getNomeTabelaDB() + ",";
            final Collection colObjs = objetoNegocioService.findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
            if (colObjs == null || colObjs.size() == 0) {
                if (messages) {
                    System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
                }
                objetoNegocioService.create(objetoNegocioDTO);
            } else {
                final ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO) ((List) colObjs).get(0);
                objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
                if (messages) {
                    System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB() + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
                }
                objetoNegocioService.update(objetoNegocioDTO);
            }
        }

        return carregados;
    }

    public Collection readTables(final Connection con, final String catalogo, final String esquema, final String tableName, final boolean messages) throws SQLException {
        final DatabaseMetaData dm = con.getMetaData();
        final String[] types = {"TABLE"};
        final ResultSet rsTables = dm.getTables(catalogo, esquema, null, types);

        final Collection colObjetosNegocio = new ArrayList();
        while (rsTables.next()) {
            final ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
            final String cat = rsTables.getString("TABLE_CAT");
            final String schema = rsTables.getString("TABLE_SCHEM");
            final String nomeTabela = rsTables.getString("TABLE_NAME");

            final String nomeTabelaAux = nomeTabela.toUpperCase();
            objetoNegocioDTO.setNomeTabelaDB(nomeTabelaAux);
            objetoNegocioDTO.setNomeObjetoNegocio(nomeTabelaAux);
            objetoNegocioDTO.setSituacao("A");

            if (tableName != null && !tableName.trim().equalsIgnoreCase("")) {
                if (!tableName.toUpperCase().equalsIgnoreCase(nomeTabelaAux)) {
                    continue;
                }
            }

            if (messages) {
                System.out.println(" ------::::::::::::::::> TABELA: " + nomeTabelaAux);
            }

            final ResultSet rsPKs = dm.getPrimaryKeys(cat, schema, nomeTabela);
            final ArrayList listaPKs = new ArrayList();
            while (rsPKs.next()) {
                final String nomeColuna = rsPKs.getString("COLUMN_NAME");
                listaPKs.add(nomeColuna);
            }
            rsPKs.close();
            final ResultSet rsFKs = dm.getImportedKeys(cat, schema, nomeTabela);
            while (rsFKs.next()) {
                final String nomeTabelaPK = rsFKs.getString("PKTABLE_NAME");
                final String nomeColunaPK = rsFKs.getString("PKCOLUMN_NAME");
                final String nomeTabelaFK = rsFKs.getString("FKTABLE_NAME");
                final String nomeColunaFK = rsFKs.getString("FKCOLUMN_NAME");
                final String campoFK = schema + "." + nomeTabelaFK + "." + nomeColunaFK;
                final String campoPK = schema + "." + nomeTabelaPK + "." + nomeColunaPK;
                final String join = "(and " + campoFK + " = " + campoPK + ")";
                if (messages) {
                    System.out.println(" join = " + join);
                }
            }
            rsFKs.close();

            ResultSet rsColunas = null;
            try {
                rsColunas = dm.getColumns(cat, schema, nomeTabela, null);
            } catch (final Exception e) {
                System.out.println("Problemas ao ler campos da tabela: " + nomeTabela + " --> " + e.getMessage());
            }
            if (rsColunas == null) {
                continue;
            }
            final Collection colCampos = new ArrayList();
            while (rsColunas.next()) {
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
                String nomeColunaBanco = rsColunas.getString("COLUMN_NAME");

                String nomeTipo = rsColunas.getString("TYPE_NAME");
                final int precisionDB = rsColunas.getInt("DECIMAL_DIGITS");
                final String isNullable = rsColunas.getString("IS_NULLABLE");
                final String seColunaPK = listaPKs.contains(nomeColunaBanco) ? "S" : "N";
                String seObrigatorio = null;
                if (isNullable != null && isNullable.trim().length() > 0) {
                    seObrigatorio = isNullable.trim().indexOf("YES") > -1 ? "N" : "S";
                }

                if (messages) {
                    System.out.println(" ------:::::::::::::::::::::::::::::::::::> COLUNA: " + nomeColunaBanco + " Tipo: " + nomeTipo + " PK: " + seColunaPK + " OBR: "
                            + seObrigatorio);
                }

                if (nomeTipo == null) {
                    nomeTipo = "";
                }
                nomeTipo = nomeTipo.toUpperCase();
                nomeColunaBanco = nomeColunaBanco.toUpperCase();

                camposObjetoNegocioDTO.setNomeDB(nomeColunaBanco);
                camposObjetoNegocioDTO.setNome(nomeColunaBanco);
                camposObjetoNegocioDTO.setPk(seColunaPK);
                camposObjetoNegocioDTO.setSequence(seColunaPK);
                camposObjetoNegocioDTO.setUnico(seColunaPK);
                camposObjetoNegocioDTO.setObrigatorio(seObrigatorio);
                camposObjetoNegocioDTO.setTipoDB(nomeTipo);
                camposObjetoNegocioDTO.setPrecisionDB(precisionDB);
                camposObjetoNegocioDTO.setSituacao("A");

                colCampos.add(camposObjetoNegocioDTO);
            }
            rsColunas.close();
            objetoNegocioDTO.setColCampos(colCampos);
            colObjetosNegocio.add(objetoNegocioDTO);
        }
        rsTables.close();
        return colObjetosNegocio;
    }

    public String getTabelaPaiByTableAndField(final String tableName, final String field, final boolean messages) throws Exception {
        final VisaoDao visaoDao = new VisaoDao();
        final Connection con = visaoDao.getTransactionControler().getConnection();
        String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
        if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            DB_SCHEMA = null;
        } else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
            DB_SCHEMA = "citsmart";
        }

        final DatabaseMetaData dm = con.getMetaData();
        final String[] types = {"TABLE"};
        String retorno = "";
        final ResultSet rsTables = dm.getTables(DB_SCHEMA, DB_SCHEMA, null, types);

        while (rsTables.next()) {
            final ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
            final String cat = rsTables.getString("TABLE_CAT");
            final String schema = rsTables.getString("TABLE_SCHEM");
            final String nomeTabela = rsTables.getString("TABLE_NAME");

            final String nomeTabelaAux = nomeTabela.toUpperCase();
            objetoNegocioDTO.setNomeTabelaDB(nomeTabelaAux);
            objetoNegocioDTO.setNomeObjetoNegocio(nomeTabelaAux);
            objetoNegocioDTO.setSituacao("A");

            if (tableName != null && !tableName.trim().equalsIgnoreCase("")) {
                if (!tableName.toUpperCase().equalsIgnoreCase(nomeTabelaAux)) {
                    continue;
                }
            }

            final ResultSet rsFKs = dm.getImportedKeys(cat, schema, nomeTabela);
            while (rsFKs.next()) {
                final String nomeTabelaPK = rsFKs.getString("PKTABLE_NAME");
                final String nomeColunaPK = rsFKs.getString("PKCOLUMN_NAME");

                if (field.equalsIgnoreCase(nomeColunaPK)) {
                    retorno = nomeTabelaPK;
                    break;
                }
            }
            rsFKs.close();
            break;
        }
        rsTables.close();
        con.close();

        return retorno;
    }

    public Collection readFK(final Connection con, final String catalogo, final String esquema, final String tableName) throws SQLException {
        final DatabaseMetaData dm = con.getMetaData();
        final String[] types = {"TABLE"};
        final ResultSet rsTables = dm.getTables(catalogo, esquema, null, types);

        final Collection colRetorno = new ArrayList();
        while (rsTables.next()) {
            final ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
            final String cat = rsTables.getString("TABLE_CAT");
            final String schema = rsTables.getString("TABLE_SCHEM");
            final String nomeTabela = rsTables.getString("TABLE_NAME");

            final String nomeTabelaAux = nomeTabela.toUpperCase();
            objetoNegocioDTO.setNomeTabelaDB(nomeTabelaAux);
            objetoNegocioDTO.setNomeObjetoNegocio(nomeTabelaAux);
            objetoNegocioDTO.setSituacao("A");

            if (tableName != null && !tableName.trim().equalsIgnoreCase("")) {
                if (!tableName.toUpperCase().equalsIgnoreCase(nomeTabelaAux)) {
                    continue;
                }
            }

            System.out.println(" ------::::::::::::::::> TABELA: " + nomeTabelaAux);

            final ResultSet rsPKs = dm.getPrimaryKeys(cat, schema, nomeTabela);
            final ArrayList listaPKs = new ArrayList();
            while (rsPKs.next()) {
                final String nomeColuna = rsPKs.getString("COLUMN_NAME");
                listaPKs.add(nomeColuna);
            }
            rsPKs.close();
            final ResultSet rsFKs = dm.getExportedKeys(cat, schema, nomeTabela);
            while (rsFKs.next()) {
                final DataManagerFKRelatedDTO dataManagerFKRelatedDTO = new DataManagerFKRelatedDTO();
                final String nomeTabelaPK = rsFKs.getString("PKTABLE_NAME");
                final String nomeColunaPK = rsFKs.getString("PKCOLUMN_NAME");
                final String nomeTabelaFK = rsFKs.getString("FKTABLE_NAME");
                final String nomeColunaFK = rsFKs.getString("FKCOLUMN_NAME");
                final String campoFK = nomeTabelaFK + "." + nomeColunaFK;
                final String campoPK = nomeTabelaPK + "." + nomeColunaPK;
                final String join = " " + campoFK + " = " + campoPK + " ";

                dataManagerFKRelatedDTO.setJoin(join);
                dataManagerFKRelatedDTO.setNomeTabelaRelacionada(nomeTabelaFK);
                dataManagerFKRelatedDTO.setPartChild(campoFK);
                dataManagerFKRelatedDTO.setPartParent(campoPK);
                System.out.println(" join = " + join);
                colRetorno.add(dataManagerFKRelatedDTO);
            }
            rsFKs.close();
        }
        rsTables.close();
        return colRetorno;
    }

}
