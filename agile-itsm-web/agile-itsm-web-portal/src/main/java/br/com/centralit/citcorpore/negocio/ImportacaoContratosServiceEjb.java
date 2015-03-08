package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.ImportacaoContratosDTO;
import br.com.centralit.citcorpore.integracao.ImportacaoContratosDao;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.util.ImportInfoField;
import br.com.centralit.citcorpore.util.ImportInfoRecord;
import br.com.centralit.citcorpore.util.UtilImportData;
import br.com.citframework.excecao.ConnectionException;
import br.com.citframework.excecao.TransactionOperationException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ImportacaoContratosServiceEjb extends CrudServiceImpl implements ImportacaoContratosService {

    private ImportacaoContratosDao dao;

    @Override
    protected ImportacaoContratosDao getDao() {
        if (dao == null) {
            dao = new ImportacaoContratosDao();
        }
        return dao;
    }

    /**
     * Executa a persistencia dos dados
     *
     * @param idContrato
     * @param xml
     * @return ImportacaoContratosDTO
     * @author rodrigo.acorse
     * @throws ConnectionException
     * @throws TransactionOperationException
     */
    @Override
    public ImportacaoContratosDTO persisteDados(final HttpServletRequest request, final Integer idContrato, final String xml) throws TransactionOperationException,
            ConnectionException, Exception {
        final ImportacaoContratosDTO importacaoContratosDto = new ImportacaoContratosDTO();
        importacaoContratosDto.setResultado(false);

        final Map<String, String> mapChanges = new HashMap<>();

        final List colRecordsGeral = new ArrayList();

        System.out.println("XML: " + xml);

        final Collection colRecords = UtilImportData.readXMLSource(xml);
        if (colRecords != null) {
            colRecordsGeral.addAll(colRecords);
        }

        // Utiliza o primeiro registro somente para fazer algumas validações básicas antes de entrar no loop
        ImportInfoRecord importInfoRecord = (ImportInfoRecord) colRecordsGeral.get(0);

        // Se um contrato foi selecionado na importação, verifica se a exportação foi feita utilizando um contrato.
        if (idContrato != null && !idContrato.equals("")) {
            if (importInfoRecord.getFilter() != null && !importInfoRecord.getFilter().equals("")) {
                // System.out.println("SUCESSO -> " + importInfoRecord.getTableName() +
                // " - Registro para atualização de ID - IDCONTRATO: " + importInfoRecord.getFilter() + " para " +
                // idContrato.toString());
                mapChanges.put("IDCONTRATO" + importInfoRecord.getFilter(), idContrato.toString());
            } else {
                importacaoContratosDto.setMensagem(UtilI18N.internacionaliza(request, "importacaoContratos.falhaImportacao") + " "
                        + UtilI18N.internacionaliza(request, "importacaoContratos.arquivoInvalidoSemContrato"));
                return importacaoContratosDto;
            }
        } else {
            if (importInfoRecord.getFilter() != null && !importInfoRecord.getFilter().equals("")) {
                importacaoContratosDto.setMensagem(UtilI18N.internacionaliza(request, "importacaoContratos.falhaImportacao") + " "
                        + UtilI18N.internacionaliza(request, "importacaoContratos.arquivoInvalidoComContrato"));
                return importacaoContratosDto;
            }
        }

        final TransactionControlerImpl transactionControler = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));
        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        jdbcEngine.setTransactionControler(transactionControler);

        transactionControler.start();

        for (final Iterator itRecords = colRecordsGeral.iterator(); itRecords.hasNext();) {
            importInfoRecord = (ImportInfoRecord) itRecords.next();

            String key = "";
            String keyValue = "";
            final String table = importInfoRecord.getTableName();
            String where = "";

            String sqlInsert = "";
            String sqlInsertValues = "";
            String sqlUpdate = "";

            final List lstParmsInsert = new ArrayList();
            final List lstParmsUpdate = new ArrayList();
            final List lstParmsWhere = new ArrayList();

            for (final Iterator it = importInfoRecord.getColFields().iterator(); it.hasNext();) {
                final ImportInfoField importInfoField = (ImportInfoField) it.next();

                System.out.println("Valor depois da leitura do xml: " + importInfoField.getValueField());

                final String typeDB = importInfoField.getType();

                if (importInfoField.isKey()) {
                    if (importInfoRecord.getType().equalsIgnoreCase("concrete")) {
                        key = importInfoField.getNameField();
                        keyValue = importInfoField.getValueField();
                    }
                }

                // Atualiza os ids gerados na exportação para os ids corretos da importação
                if (mapChanges.get(importInfoField.getNameField() + importInfoField.getValueField()) != null
                        && StringUtils.isNotBlank(mapChanges.get(importInfoField.getNameField() + importInfoField.getValueField()))) {
                    importInfoField.setValueField(mapChanges.get(importInfoField.getNameField() + importInfoField.getValueField()).toString());
                }

                // Se for CHECK, adiciona o WHERE para validação
                if (importInfoField.isCheck()) {
                    if (where.trim().equals("")) {
                        where = importInfoField.getNameField() + " = ?";
                    } else {
                        where += " AND " + importInfoField.getNameField() + " = ?";
                    }

                    if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
                        lstParmsWhere.add(null);
                    } else {
                        if (typeDB.startsWith("MONEY") || typeDB.startsWith("DOUBLE") || typeDB.startsWith("DECIMAL") || typeDB.startsWith("NUMERIC")
                                || typeDB.startsWith("NUMBER") || typeDB.startsWith("REAL") || typeDB.startsWith("FLOAT")) {
                            if (importInfoField.getValueField() instanceof String) {
                                lstParmsWhere.add(Double.parseDouble(importInfoField.getValueField()));
                            } else {
                                lstParmsWhere.add(importInfoField.getValueField());
                            }
                        } else {
                            if (typeDB.startsWith("TIMESTAMP") && importInfoField.getValueField().equalsIgnoreCase("0000-00-00 00:00:00")) {
                                lstParmsWhere.add(new Timestamp(System.currentTimeMillis()));
                            } else {
                                lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), importInfoField.getValueField(), null, null));
                            }
                        }
                    }
                }

                if (importInfoField.isExclusion()) {
                    String strExclusion = "";
                    if (importInfoField.getNameField().equalsIgnoreCase("deleted")) {
                        strExclusion = "(DELETED IS NULL OR DELETED = 'n' OR DELETED = 'N')";
                    } else if (importInfoField.getNameField().equalsIgnoreCase("datafim")) {
                        strExclusion = "(DATAFIM IS NULL)";
                    } else if (importInfoField.getNameField().equalsIgnoreCase("situacao")) {
                        strExclusion = "(SITUACAO = 'A')";
                    }

                    if (where.trim().equals("")) {
                        where = strExclusion;
                    } else {
                        where += " AND " + strExclusion;
                    }
                }

                // Adiciona campos para insert
                if (sqlInsert.trim().equals("")) {
                    sqlInsert = importInfoField.getNameField();
                } else {
                    sqlInsert += "," + importInfoField.getNameField();
                }

                if (sqlInsertValues.trim().equals("")) {
                    sqlInsertValues = "?";
                } else {
                    sqlInsertValues += "," + "?";
                }

                if (importInfoRecord.getType().equalsIgnoreCase("concrete") && importInfoField.isKey()) {
                    lstParmsInsert.add("<KEY>");
                } else {
                    if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
                        lstParmsInsert.add(null);
                    } else {
                        if (typeDB.startsWith("MONEY") || typeDB.startsWith("DOUBLE") || typeDB.startsWith("DECIMAL") || typeDB.startsWith("NUMERIC")
                                || typeDB.startsWith("NUMBER") || typeDB.startsWith("REAL") || typeDB.startsWith("FLOAT")) {
                            if (importInfoField.getValueField() instanceof String) {
                                lstParmsInsert.add(Double.parseDouble(importInfoField.getValueField()));
                            } else {
                                lstParmsInsert.add(importInfoField.getValueField());
                            }
                        } else {
                            if (typeDB.startsWith("TIMESTAMP") && importInfoField.getValueField().equalsIgnoreCase("0000-00-00 00:00:00")) {
                                lstParmsInsert.add(new Timestamp(System.currentTimeMillis()));
                            } else {
                                lstParmsInsert.add(MetaUtil.convertType(importInfoField.getType(), importInfoField.getValueField(), null, null));
                            }
                        }
                    }
                }

                // Adiciona campos para update
                if (importInfoRecord.getType().equalsIgnoreCase("concrete") && !importInfoField.isKey() || importInfoRecord.getType().equalsIgnoreCase("relation")) {
                    if (sqlUpdate.trim().equals("")) {
                        sqlUpdate = importInfoField.getNameField() + " = ?";
                    } else {
                        sqlUpdate += "," + importInfoField.getNameField() + " = ?";
                    }

                    if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
                        lstParmsUpdate.add(null);
                    } else {
                        if (typeDB.startsWith("MONEY") || typeDB.startsWith("DOUBLE") || typeDB.startsWith("DECIMAL") || typeDB.startsWith("NUMERIC")
                                || typeDB.startsWith("NUMBER") || typeDB.startsWith("REAL") || typeDB.startsWith("FLOAT")) {
                            if (importInfoField.getValueField() instanceof String) {
                                lstParmsUpdate.add(Double.parseDouble(importInfoField.getValueField()));
                            } else {
                                lstParmsUpdate.add(importInfoField.getValueField());
                            }
                        } else {
                            if (typeDB.startsWith("TIMESTAMP") && importInfoField.getValueField().equalsIgnoreCase("0000-00-00 00:00:00")) {
                                lstParmsUpdate.add(new Timestamp(System.currentTimeMillis()));
                            } else {
                                lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), importInfoField.getValueField(), null, null));
                            }
                        }
                    }
                }

            }

            // Execução do insert ou update
            String sqlMensagemErro = null;

            try {
                if (importInfoRecord.getType().equalsIgnoreCase("concrete")) {
                    String newKey = null;

                    if (!key.equals("") && !table.trim().equals("") && !where.equals("")) {
                        final List lst = jdbcEngine.execSQL("SELECT " + key + " FROM " + table + " WHERE " + where, lstParmsWhere.toArray(), 0);
                        if (lst == null || lst.size() == 0) {
                            if (!sqlInsert.trim().equals("") && !sqlInsertValues.trim().equals("")) {
                                sqlInsert = "INSERT INTO " + table + "(" + sqlInsert + ") VALUES (" + sqlInsertValues + ")";

                                final Integer nextKey = PersistenceEngine.getNextKey(this.getDao().getAliasDB(), importInfoRecord.getTableName(), key);
                                if (nextKey != null) {
                                    newKey = nextKey.toString();
                                    for (int i = 0; i < lstParmsInsert.size(); i++) {
                                        if (lstParmsInsert.get(i) != null && lstParmsInsert.get(i).equals("<KEY>")) {
                                            lstParmsInsert.set(i, nextKey);
                                        }
                                    }
                                }

                                sqlMensagemErro = "<b>Erro de insert</b>:<br/><b>Tabela:</b> " + importInfoRecord.getTableName() + "<br/><b>SQL:</b> " + sqlInsert
                                        + "<br/><b>Parâmetros:</b> " + lstParmsInsert;
                                jdbcEngine.execUpdate(sqlInsert, lstParmsInsert.toArray());
                            }
                        } else {
                            if (!sqlUpdate.trim().equals("")) {
                                final Object[] obj = (Object[]) lst.get(0);
                                if (obj[0] != null && !obj[0].toString().equals("")) {
                                    newKey = obj[0].toString();

                                    sqlUpdate = "UPDATE " + table + " SET " + sqlUpdate + " WHERE " + key + " = " + newKey;
                                    sqlMensagemErro = "<b>Erro de update</b>:<br/><b>Tabela:</b> " + importInfoRecord.getTableName() + "<br/><b>SQL:</b> " + sqlUpdate
                                            + "<br/><b>Parâmetros:</b> " + lstParmsUpdate;
                                    jdbcEngine.execUpdate(sqlUpdate, lstParmsUpdate.toArray());
                                }
                            }

                        }
                    }

                    // Registra a alteração dos ids gerados na exportação para os ids corretos da importação
                    if (importInfoRecord.getType().equalsIgnoreCase("concrete") && !key.equals("") && !keyValue.equals("")) {
                        mapChanges.put(key + keyValue, newKey);
                    }
                } else {
                    if (!table.trim().equals("") && !where.equals("")) {
                        final List lst = jdbcEngine.execSQL("SELECT * FROM " + table + " WHERE " + where, lstParmsWhere.toArray(), 0);
                        if (lst == null || lst.size() == 0) {
                            if (!sqlInsert.trim().equals("") && !sqlInsertValues.trim().equals("")) {
                                sqlInsert = "INSERT INTO " + table + "(" + sqlInsert + ") VALUES (" + sqlInsertValues + ")";
                                sqlMensagemErro = "<b>Erro de insert</b>:<br/><b>Tabela:</b> " + importInfoRecord.getTableName() + "<br/><b>SQL:</b> " + sqlInsert
                                        + "<br/><b>Parâmetros:</b> " + lstParmsInsert;
                                jdbcEngine.execUpdate(sqlInsert, lstParmsInsert.toArray());
                            }
                        } else {
                            if (!sqlUpdate.trim().equals("")) {
                                lstParmsUpdate.addAll(lstParmsWhere);
                                sqlUpdate = "UPDATE " + table + " SET " + sqlUpdate + " WHERE " + where;
                                sqlMensagemErro = "<b>Erro de update</b>:<br/><b>Tabela:</b> " + importInfoRecord.getTableName() + "<br/><b>SQL:</b> " + sqlUpdate
                                        + "<br/><b>Parâmetros:</b> " + lstParmsUpdate;
                                jdbcEngine.execUpdate(sqlUpdate, lstParmsUpdate.toArray());
                            }
                        }
                    }
                }
            } catch (final Exception e) {
                // transactionControler.rollback();
                e.printStackTrace();

                if (sqlMensagemErro != null) {
                    if (e.getCause() != null && e.getCause().getMessage() != null && !e.getCause().getMessage().equals("")) {
                        sqlMensagemErro += "<br/><b>Erro:</b> " + e.getCause().getMessage();
                    }
                    if (importacaoContratosDto.getMensagem() == null || importacaoContratosDto.getMensagem().equals("")) {
                        importacaoContratosDto.setMensagem(sqlMensagemErro);
                    } else {
                        importacaoContratosDto.setMensagem(importacaoContratosDto.getMensagem() + "<br/><br/>" + sqlMensagemErro);
                    }
                }
            }
        }

        transactionControler.commit();
        transactionControler.close();

        importacaoContratosDto.setResultado(true);
        return importacaoContratosDto;
    }

}
