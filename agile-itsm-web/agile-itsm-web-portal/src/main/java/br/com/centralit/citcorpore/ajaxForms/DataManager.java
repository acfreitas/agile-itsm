package br.com.centralit.citcorpore.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.DataManagerDTO;
import br.com.centralit.citcorpore.bean.DataManagerFKRelatedDTO;
import br.com.centralit.citcorpore.bean.TabFederacaoDadosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.TabFederacaoDadosDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.negocio.DataBaseMetaDadosService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ImportInfoField;
import br.com.centralit.citcorpore.util.ImportInfoRecord;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilImportData;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DataManager extends AjaxFormAction {

    private ObjetoNegocioService objetoNegocioService;

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("uploadAnexos.refresh()");
    }

    @Override
    public Class getBeanClass() {
        return DataManagerDTO.class;
    }

    public void trataExport(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
        final CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        if (dataManagerDTO.getIdObjetoNegocio() == null) {
            return;
        }

        final Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(dataManagerDTO.getIdObjetoNegocio());
        String table = "";
        table += "<table><tr><td style='background-color:yellow'><input type='checkbox' value='S' name='excluirAoExportar' onclick='validaExcl(this)'>"
                + UtilI18N.internacionaliza(request, "dataManager.excluir") + "</td><td><input type='checkbox' value='S' name='exportarVinculos'>"
                + UtilI18N.internacionaliza(request, "dataManager.exportarvinculos") + "</td><td><input type='checkbox' value='S' name='exportarSql'>"
                + UtilI18N.internacionaliza(request, "dataManager.exportarSql") + "</td></tr></table><br>";
        table += "<table cellspacing='1' cellpadding='1' width='100%' border='1'>";
        table += "<tr>";
        table += "<td>";
        table += UtilI18N.internacionaliza(request, "dataManager.chave");
        table += "</td>";
        table += "<td>";
        table += UtilI18N.internacionaliza(request, "dataManager.campo");
        table += "</td>";
        table += "<td>";
        table += UtilI18N.internacionaliza(request, "dataManager.condicao");
        table += "</td>";
        table += "<td>";
        table += UtilI18N.internacionaliza(request, "dataManager.valor");
        table += "</td>";
        table += "</tr>";
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
                table += "<tr>";
                table += "<td>";
                if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
                    table += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/ok.png' border='0'/>";
                } else {
                    table += "&nbsp;";
                }
                table += "</td>";
                table += "<td>";
                table += (camposObjetoNegocioDto.getDescricao() == null ? camposObjetoNegocioDto.getNome() : camposObjetoNegocioDto.getDescricao()) + " ("
                        + camposObjetoNegocioDto.getTipoDB() + ")";
                table += "</td>";
                table += "<td>";
                table += "<select name='cond_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "' id='cond_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "'>";
                final boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
                final boolean isDateType = MetaUtil.isDateType(camposObjetoNegocioDto.getTipoDB());
                table += "<option value=''>-- " + UtilI18N.internacionaliza(request, "dataManager.semFiltro") + " --</option>";
                table += "<option value='='>=</option>";
                table += "<option value='1'>&lt;&gt;</option>";
                table += "<option value='2'>&gt;</option>";
                table += "<option value='3'>&lt;</option>";
                table += "<option value='IN'>IN</option>";
                table += "<option value='NOT IN'>NOT IN</option>";
                if (isDateType) {
                    table += "<option value='BETWEEN'>" + UtilI18N.internacionaliza(request, "dataManager.entre") + "</option>";
                }
                table += "<option value='IS NULL'>IS NULL</option>";
                if (isStringType) {
                    table += "<option value='LIKE'>" + UtilI18N.internacionaliza(request, "dataManager.queContenha") + "</option>";
                }
                table += "</select>";
                table += "</td>";
                table += "<td>";
                table += "<input type='text' name='valor_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "' id='valor_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio()
                        + "' size='30'/>";
                table += "</td>";
                table += "</tr>";
            }
        }
        table += "</table>";
        document.getElementById("divExport").setInnerHTML(table);

        document.executeScript("geraPopup()");
    }

    public void importar(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Collection colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED");
        if (colUploadsGED == null || colUploadsGED.size() == 0) {
            document.alert(UtilI18N.internacionaliza(request, "dataManager.naoHaArquivosImportar"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        final String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
        if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
            document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        final List colRecordsGeral = new ArrayList();
        for (final Iterator it = colUploadsGED.iterator(); it.hasNext();) {
            final UploadDTO uploadDTO = (UploadDTO) it.next();
            final Collection colRecords = UtilImportData.readXMLFile(uploadDTO.getPath());
            if (colRecords != null) {
                colRecordsGeral.addAll(colRecords);
            }
        }
        this.importarDados(document, request, response, colRecordsGeral, true, ORIGEM_SISTEMA);
    }

    public void importarDados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final List colRecordsGeral, boolean federarDados,
            final String ORIGEM_SISTEMA) throws Exception {
        long qtdRegInsert = 0;
        long qtdRegUpdate = 0;
        int count = 0;
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        final TabFederacaoDadosDao tabFederacaoDadosDao = new TabFederacaoDadosDao();
        for (final Iterator itRecords = colRecordsGeral.iterator(); itRecords.hasNext();) {
            final ImportInfoRecord importInfoRecord = (ImportInfoRecord) itRecords.next();

            System.out.println("\n TABELA " + importInfoRecord.getTableName() + " --- COUNT " + count);

            if (ORIGEM_SISTEMA.equalsIgnoreCase(importInfoRecord.getOrigem())) {
                federarDados = false;
            }
            String sqlWhere = "";
            final List lstParmsInsert = new ArrayList();
            final List lstParmsUpdate = new ArrayList();
            final List lstParmsWhere = new ArrayList();
            String sqlInsert = "";
            String sqlInsertValues = "";
            String sqlUpdate = "";
            String sqlSelect = "";
            String nomePrimeiroCampo = "";

            String chaveOrigem = "";
            String chaveFinal = "";

            for (final Iterator it = importInfoRecord.getColFields().iterator(); it.hasNext();) {
                final ImportInfoField importInfoField = (ImportInfoField) it.next();

                System.out.println(" " + importInfoField.getNameField() + " = " + importInfoField.getValueField());

                if (nomePrimeiroCampo.trim().equalsIgnoreCase("")) {
                    nomePrimeiroCampo = importInfoField.getNameField();
                }
                if (!sqlInsert.trim().equalsIgnoreCase("")) {
                    sqlInsert = sqlInsert + ",";
                    sqlInsertValues = sqlInsertValues + ",";
                }

                sqlInsert = sqlInsert + importInfoField.getNameField();
                sqlInsertValues = sqlInsertValues + "?";
                String strVal = "";
                if (importInfoField.isKey()) {
                    if (!sqlWhere.trim().equalsIgnoreCase("")) {
                        sqlWhere = sqlWhere + " AND ";
                    }
                    sqlWhere = sqlWhere + " " + importInfoField.getNameField() + " = ?";
                    if (federarDados) {
                        if (importInfoField.isSequence()) {
                            strVal = this.localizaOrigemTabelaChaveOrig(importInfoRecord.getOrigem(), importInfoRecord.getTableName(), importInfoField.getValueField());
                            if (strVal == null) {
                                strVal = this.generateChaveFederada(importInfoRecord.getTableName(), importInfoField.getNameField());
                            }
                            lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));

                            if (!chaveFinal.trim().equalsIgnoreCase("")) {
                                chaveFinal = chaveOrigem + "+";
                            }
                            chaveFinal = chaveFinal + strVal;
                        } else {
                            if (!chaveFinal.trim().equalsIgnoreCase("")) {
                                chaveFinal = chaveOrigem + "+";
                            }
                            // Tem que localizar a chave referenciada na base federada.
                            final String tabelaPai = dataBaseMetaDadosUtil.getTabelaPaiByTableAndField(importInfoRecord.getTableName(), importInfoField.getNameField(), true);
                            if (tabelaPai != null && !tabelaPai.trim().equalsIgnoreCase("")) {
                                strVal = this.localizaOrigemTabelaChaveOrig(importInfoRecord.getOrigem(), tabelaPai, importInfoField.getValueField());
                                if (strVal != null) {
                                    chaveFinal = chaveFinal + strVal;
                                } else {
                                    chaveFinal = chaveFinal + importInfoField.getValueField();
                                    strVal = importInfoField.getValueField();
                                }
                                lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));
                            } else {
                                chaveFinal = chaveFinal + importInfoField.getValueField();
                                lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), importInfoField.getValueField(), null, request));
                            }
                        }
                    } else {
                        strVal = importInfoField.getValueField();
                        lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));
                    }
                    if (!chaveOrigem.trim().equalsIgnoreCase("")) {
                        chaveOrigem = chaveOrigem + "+";
                    }
                    chaveOrigem = chaveOrigem + importInfoField.getValueField();
                } else {
                    if (!sqlUpdate.trim().equalsIgnoreCase("")) {
                        sqlUpdate = sqlUpdate + ",";
                    }
                    sqlUpdate = sqlUpdate + importInfoField.getNameField() + " = ?";
                    if (federarDados) {
                        // Tem que localizar a chave referenciada na base federada.
                        final String tabelaPai = dataBaseMetaDadosUtil.getTabelaPaiByTableAndField(importInfoRecord.getTableName(), importInfoField.getNameField(), true);
                        if (tabelaPai != null && !tabelaPai.trim().equalsIgnoreCase("")) {
                            strVal = this.localizaOrigemTabelaChaveOrig(importInfoRecord.getOrigem(), tabelaPai, importInfoField.getValueField());
                            if (strVal == null) {
                                strVal = importInfoField.getValueField();
                            }
                            lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));
                        } else {
                            strVal = importInfoField.getValueField();
                            if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
                                lstParmsUpdate.add(null);
                            } else {
                                lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));
                            }
                        }
                    } else {
                        strVal = importInfoField.getValueField();
                        if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
                            lstParmsUpdate.add(null);
                        } else {
                            lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));
                        }
                    }
                }
                if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
                    lstParmsInsert.add(null);
                } else {
                    lstParmsInsert.add(MetaUtil.convertType(importInfoField.getType(), strVal, null, request));
                }
            }
            if (federarDados) {
                final TabFederacaoDadosDTO tabFederacaoDadosDto = new TabFederacaoDadosDTO();
                tabFederacaoDadosDto.setOrigem(importInfoRecord.getOrigem());
                tabFederacaoDadosDto.setChaveOriginal(chaveOrigem);
                tabFederacaoDadosDto.setChaveFinal(chaveFinal);
                tabFederacaoDadosDto.setNomeTabela(importInfoRecord.getTableName());
                tabFederacaoDadosDto.setCriacao(UtilDatas.getDataHoraAtual());
                tabFederacaoDadosDto.setUltAtualiz(UtilDatas.getDataHoraAtual());
                final TabFederacaoDadosDTO tabFederacaoDadosAux = (TabFederacaoDadosDTO) tabFederacaoDadosDao.restore(tabFederacaoDadosDto);
                if (tabFederacaoDadosAux != null) {
                    tabFederacaoDadosDto.setCriacao(null);
                    tabFederacaoDadosDao.updateNotNull(tabFederacaoDadosDto);
                } else {
                    tabFederacaoDadosDao.create(tabFederacaoDadosDto);
                }
            }

            lstParmsUpdate.addAll(lstParmsWhere);
            sqlInsert = "INSERT INTO " + importInfoRecord.getTableName() + "(" + sqlInsert + ") VALUES (" + sqlInsertValues + ")";
            sqlUpdate = "UPDATE " + importInfoRecord.getTableName() + " SET " + sqlUpdate + " WHERE " + sqlWhere;
            sqlSelect = "SELECT " + nomePrimeiroCampo + " FROM " + importInfoRecord.getTableName() + " WHERE " + sqlWhere;

            final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
            try {
                final List lst = jdbcEngine.execSQL(sqlSelect, lstParmsWhere.toArray(), 0);
                if (lst == null || lst.size() == 0) {
                    jdbcEngine.execUpdate(sqlInsert, lstParmsInsert.toArray());
                    qtdRegInsert++;
                } else {
                    jdbcEngine.execUpdate(sqlUpdate, lstParmsUpdate.toArray());
                    qtdRegUpdate++;
                }
            } catch (final Exception e) {
                e.printStackTrace();
                System.out.println("O ERRO OCORREU NO REGISTRO " + count);
                System.err.println(sqlInsert + " - " + lstParmsInsert);
                System.err.println(sqlUpdate + " - " + lstParmsUpdate);
                System.err.println(sqlSelect + " - " + lstParmsWhere);
            }

            count++;
        }
        document.alert("Registros Inseridos: " + qtdRegInsert + ". Atualizados: " + qtdRegUpdate);
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("$('#divImport').dialog('close')");
        document.executeScript("$('#POPUP_EXPORTAR').dialog('close')");
        document.executeScript("limpar_upload()");
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public String generateChaveFederada(final String tableName, final String nomeField) throws PersistenceException {
        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        final String sql = "SELECT MAX(" + nomeField + ") FROM " + tableName + "";
        final List lst = jdbcEngine.execSQL(sql, null, 0);
        if (lst == null || lst.size() == 0) {
            return "1";
        }
        final Object[] obj = (Object[]) lst.get(0);
        long l = 0;
        try {
            l = Long.parseLong(obj[0].toString());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        l = l + 1;
        return "" + l;
    }

    public String localizaOrigemTabelaChaveOrig(final String origem, final String tableName, final String chaveOrig) throws Exception {
        final TabFederacaoDadosDao tabFederadaDao = new TabFederacaoDadosDao();
        final Collection col = tabFederadaDao.findByOrigemTabelaChaveOriginal(origem, tableName, chaveOrig);
        if (col != null && col.size() > 0) {
            final TabFederacaoDadosDTO tabFederacaoDadosDTO = (TabFederacaoDadosDTO) ((List) col).get(0);
            return tabFederacaoDadosDTO.getChaveFinal();
        }
        return null;
    }

    public void exportar(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
        if (dataManagerDTO.getIdObjetoNegocio() == null) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
        if (usuarioDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
        if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
            document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        String sqlDelete = "";
        String nomeTabela = "";
        final HashMap hashValores = this.getFormFields(request);
        final String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());
        final String exportarVinculos = (String) hashValores.get("exportarVinculos".toUpperCase());
        final String exportarSql = (String) hashValores.get("exportarSql".toUpperCase());
        StringBuilder strAux = null;
        if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S") || exportarVinculos != null && exportarVinculos.equalsIgnoreCase("S")) {
            if (exportarSql != null && exportarSql.equalsIgnoreCase("S")) {
                strAux = this.geraRecursiveExportObjetoNegocioSql(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
            } else {
                strAux = this.geraRecursiveExportObjetoNegocio(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
            }
        } else {
            if (exportarSql != null && exportarSql.equalsIgnoreCase("S")) {
                strAux = this.geraExportObjetoNegocioSql(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
            } else {
                strAux = this.geraExportObjetoNegocio(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
            }
        }
        nomeTabela = (String) hashValores.get("NOMETABELADB");
        sqlDelete = (String) hashValores.get("COMMANDDELETE");
        final String sqlExport = (String) hashValores.get("COMMAND");

        String strDateTime = new java.util.Date().toString();
        strDateTime = strDateTime.replaceAll(" ", "_");
        strDateTime = strDateTime.replaceAll(":", "_");
        String str = "";

        if (exportarSql != null && exportarSql.equalsIgnoreCase("S")) {
            str += "\n-- origem: " + ORIGEM_SISTEMA;
            str += "\n-- Data: " + UtilDatas.dateToSTRWithFormat(UtilDatas.getDataAtual(), "dd/MM/yyyy hh:MM:ss") + "\n";
            str += "\n" + strAux.toString();
            str += "\n-- END SQL";
            UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.CAMINHO_REAL_APP + "/exportSQL/export_data_" + strDateTime + ".sql", str);
        } else {
            str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + strAux.toString();
            str = "" + str + "\n</tables>";
            UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_" + strDateTime + ".smart", str);
        }

        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        LogDados logDados = new LogDados();
        logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual());
        logDados.setIdUsuario(usuarioDto.getIdUsuario());
        logDados.setDataInicio(UtilDatas.getDataAtual());
        logDados.setDataLog(UtilDatas.getDataHoraAtual());
        logDados.setNomeTabela(nomeTabela);
        logDados.setOperacao("Export");
        logDados.setLocalOrigem(usuarioDto.getNomeUsuario());
        logDados.setDados("Execute sql export... " + sqlExport);

        final LogDadosService lds = new LogDadosServiceBean();

        try {
            logDados = lds.create(logDados);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
            logDados.setOperacao("Delete after export");
            sqlDelete = sqlDelete + ";";
            final String[] strDel = sqlDelete.split(";");
            if (strDel != null) {
                for (final String element : strDel) {
                    logDados.setDados("Execute sql delete... " + element);
                    try {
                        logDados = lds.create(logDados);
                        try {
                            System.out.println("Executando sql... " + element);
                            jdbcEngine.execUpdate(element, null); // Antes de executar a operacao de exclusao, faz a
                            // gravacao do LOG.
                        } catch (final Exception e) {
                            e.printStackTrace();
                            logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual()); // Se falhar a execucao da operacao
                            // delete, gera informando que
                            // falhou!
                            logDados.setDataLog(UtilDatas.getDataHoraAtual());
                            logDados.setDados("FAILED! [Original Id Log: " + logDados.getIdlog() + "] Execute sql delete... " + element);
                            lds.create(logDados);
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        if (exportarSql != null && exportarSql.equalsIgnoreCase("S")) {
            document.executeScript("getFile('" + CITCorporeUtil.CAMINHO_REAL_APP + "/exportSQL/export_data_" + strDateTime + ".sql', 'export_data_" + strDateTime + ".sql')");
        } else {
            document.executeScript("getFile('" + CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_" + strDateTime + ".smart', 'export_data_" + strDateTime + ".smart')");
        }
    }

    public StringBuilder geraRecursiveExportObjetoNegocio(final Map hashValores, final Integer idObjetoNegocio, final String sqlDelete, String nomeTabela,
            final String filterAditional, final String order) throws ServiceException, Exception {
        final StringBuilder strAux = this.geraExportObjetoNegocio(hashValores, idObjetoNegocio, sqlDelete, nomeTabela, filterAditional, order);
        nomeTabela = (String) hashValores.get("NOMETABELADB");
        final String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
        final String keysProcessed = (String) hashValores.get("KEYS");
        final Collection colRelateds = this.getFks(nomeTabela);
        System.out.println("Tratando FKS da Tabelas : " + nomeTabela);
        if (colRelateds != null && colRelateds.size() > 0) {
            final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
            for (final Iterator it = colRelateds.iterator(); it.hasNext();) {
                final DataManagerFKRelatedDTO dataManagerFKRelatedDTO = (DataManagerFKRelatedDTO) it.next();
                System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada());

                final ObjetoNegocioDTO objNegocioDto = objetoNegocioService.getByNomeTabelaDB(dataManagerFKRelatedDTO.getNomeTabelaRelacionada());
                if (objNegocioDto != null) {
                    if (tabelasTratadas.indexOf(("'" + objNegocioDto.getNomeTabelaDB().trim() + "'").toUpperCase()) > -1) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Tabela ja processada: " + objNegocioDto.getNomeTabelaDB());
                        continue;
                    }
                    String filter = "";
                    if (keysProcessed != null && !keysProcessed.trim().equalsIgnoreCase("")) {
                        filter = dataManagerFKRelatedDTO.getPartChild() + " IN (" + keysProcessed + ")";
                        System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada() + " Processando... " + objNegocioDto.getNomeTabelaDB()
                                + " Filtro: " + filter);
                        final StringBuilder strAux2 = this.geraRecursiveExportObjetoNegocio(hashValores, objNegocioDto.getIdObjetoNegocio(), sqlDelete,
                                dataManagerFKRelatedDTO.getNomeTabelaRelacionada(), filter, "");
                        strAux.append("\n");
                        strAux.append(strAux2);
                    }
                }
            }
        }
        return strAux;
    }

    public Collection getFks(final String nomeTabela) throws Exception {
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        final VisaoDao visaoDao = new VisaoDao();
        final Connection con = visaoDao.getTransactionControler().getConnection();
        String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, " ");
        if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
            DB_SCHEMA = "citsmart";
        }
        final Collection colRelateds = dataBaseMetaDadosUtil.readFK(con, DB_SCHEMA, DB_SCHEMA, nomeTabela);
        con.close();
        return colRelateds;
    }

    public void exportarTudo(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        final HashMap hashValores = this.getFormFields(request);
        final StringBuilder str = new StringBuilder();

        final String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
        if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
            document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        final String nomeTabela = "";
        final Collection colObjsNeg = objetoNegocioService.listAtivos();
        if (colObjsNeg != null) {
            for (final Iterator it = colObjsNeg.iterator(); it.hasNext();) {
                final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                final String sqlDelete = "";
                final StringBuilder strAux = this.geraExportObjetoNegocio(hashValores, objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
                if (strAux != null) {
                    str.append(strAux);
                }
            }
        }

        String strAux = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + str.toString();
        strAux = "" + strAux + "\n</tables>";

        String strDateTime = new java.util.Date().toString();
        strDateTime = strDateTime.replaceAll(" ", "_");
        strDateTime = strDateTime.replaceAll(":", "_");
        UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_ALL_" + strDateTime + ".smart", strAux.toString());

        document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        document.executeScript("getFile('" + CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_ALL_" + strDateTime + ".smart', 'export_data_ALL_" + strDateTime
                + ".smart')");
    }

    public StringBuilder geraExportObjetoNegocio(final Map hashValores, final Integer idObjetoNegocio, String sqlDelete, final String nomeTabela, final String filterAditional,
            final String order) throws ServiceException, Exception {
        final CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
        objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
        objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
        final Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
        String sqlCondicao = "";
        String sqlCampos = "";

        final String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());

        // Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
        System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());
        Thread.sleep(1000); // Da uma sleep pra liberar cursores presos e dar um tempo pro GC passar.

        hashValores.put("NOMETABELADB", objetoNegocioDTO.getNomeTabelaDB());
        String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
        tabelasTratadas = UtilStrings.nullToVazio(tabelasTratadas);
        tabelasTratadas = tabelasTratadas + "'" + objetoNegocioDTO.getNomeTabelaDB() + "'";
        hashValores.put("TABELASTRATADAS", tabelasTratadas);
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
                if (!sqlCampos.trim().equalsIgnoreCase("")) {
                    sqlCampos += ",";
                }
                sqlCampos = sqlCampos + camposObjetoNegocioDto.getNomeDB();
                final String cond = (String) hashValores.get("COND_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
                String valor = (String) hashValores.get("VALOR_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
                if (!UtilStrings.nullToVazio(cond).trim().equalsIgnoreCase("") && !UtilStrings.nullToVazio(valor).trim().equalsIgnoreCase("")) {
                    sqlCondicao = sqlCondicao + " " + camposObjetoNegocioDto.getNomeDB();
                    if (cond != null && cond.equalsIgnoreCase("1")) {
                        sqlCondicao = sqlCondicao + " <> ";
                    } else if (cond != null && cond.equalsIgnoreCase("2")) {
                        sqlCondicao = sqlCondicao + " > ";
                    } else if (cond != null && cond.equalsIgnoreCase("3")) {
                        sqlCondicao = sqlCondicao + " < ";
                    } else {
                        sqlCondicao = sqlCondicao + " " + cond + " ";
                    }
                    final boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
                    if (isStringType) {
                        if (cond.equalsIgnoreCase("=") || cond.equalsIgnoreCase("1") || cond.equalsIgnoreCase("2") || cond.equalsIgnoreCase("3")) {
                            valor = valor.replaceAll("'", "");
                            valor = "'" + valor + "'";
                        }
                    }
                    if (cond != null && !cond.trim().equalsIgnoreCase("IS NULL")) {
                        sqlCondicao = sqlCondicao + valor;
                    }
                }
            }
        }
        String sqlFinal = "SELECT " + sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
        sqlDelete = "DELETE FROM " + objetoNegocioDTO.getNomeTabelaDB();
        if (!sqlCondicao.trim().equalsIgnoreCase("")) {
            if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
                sqlFinal = sqlFinal + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
                sqlDelete = sqlDelete + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
            } else {
                sqlFinal = sqlFinal + " WHERE " + sqlCondicao;
                sqlDelete = sqlDelete + " WHERE " + sqlCondicao;
            }
        } else {
            if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
                sqlFinal = sqlFinal + " WHERE (" + filterAditional + ")";
                sqlDelete = sqlDelete + " WHERE (" + filterAditional + ")";
            }
        }
        if (order != null && !order.trim().equalsIgnoreCase("")) {
            sqlFinal += " ORDER BY " + order;
        }

        String sqlDeleteAux = (String) hashValores.get("COMMANDDELETE");
        String sqlExportAux = (String) hashValores.get("COMMAND");

        if (!UtilStrings.nullToVazio(sqlDeleteAux).trim().equalsIgnoreCase("")) {
            sqlDeleteAux = sqlDelete + "; " + UtilStrings.nullToVazio(sqlDeleteAux);
        } else {
            sqlDeleteAux = sqlDelete;
        }
        if (!UtilStrings.nullToVazio(sqlExportAux).trim().equalsIgnoreCase("")) {
            sqlExportAux = sqlFinal + "; " + UtilStrings.nullToVazio(sqlExportAux);
        } else {
            sqlExportAux = sqlFinal;
        }

        hashValores.put("COMMANDDELETE", sqlDeleteAux);
        hashValores.put("COMMAND", sqlExportAux);
        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        List lst = null;
        try {
            lst = jdbcEngine.execSQL(sqlFinal, null, 0);
        } catch (final Exception e) {
            e.printStackTrace();
            return new StringBuilder("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
        }
        final StringBuilder strXML = new StringBuilder();
        strXML.append("<table name='" + objetoNegocioDTO.getNomeTabelaDB() + "'>\n");
        strXML.append("<command><![CDATA[" + sqlFinal + "]]></command>\n");
        if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
            strXML.append("<commandDelete><![CDATA[" + sqlDelete + "]]></commandDelete>\n");
        } else {
            strXML.append("<commandDelete>NONE</commandDelete>\n");
        }
        String keysProcessed = "";
        if (lst != null) {
            int j = 0;
            for (final Iterator itDados = lst.iterator(); itDados.hasNext();) {
                final Object[] obj = (Object[]) itDados.next();
                int i = 0;
                j++;
                strXML.append("<record number='" + j + "'>\n");
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
                    String key = "n";
                    final boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
                    if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
                        key = "y";
                        if (!keysProcessed.trim().equalsIgnoreCase("")) {
                            keysProcessed = keysProcessed + ",";
                        }
                        if (isStringType) {
                            keysProcessed = keysProcessed + "'" + obj[i] + "'";
                        } else {
                            keysProcessed = keysProcessed + "" + obj[i] + "";
                        }
                    }
                    String sequence = "n";
                    if (camposObjetoNegocioDto.getSequence() != null && camposObjetoNegocioDto.getSequence().equalsIgnoreCase("S")) {
                        sequence = "y";
                    }
                    strXML.append("<field name='" + camposObjetoNegocioDto.getNomeDB() + "' key='" + key + "' sequence='" + sequence + "' type='"
                            + camposObjetoNegocioDto.getTipoDB().trim() + "'>");
                    if (isStringType) {
                        strXML.append("<![CDATA[");
                    }
                    strXML.append(obj[i]);
                    if (isStringType) {
                        strXML.append("]]>");
                    }
                    strXML.append("</field>\n");
                    i++;
                }
                strXML.append("</record>\n");
            }
        }
        strXML.append("</table>\n");
        hashValores.put("KEYS", keysProcessed);

        return strXML;
    }

    public void limparUpload(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.alert(UtilI18N.internacionaliza(request, "dataManager.limparuploadsucesso"));
    }

    private HashMap getFormFields(final HttpServletRequest req) {
        try {
            req.setCharacterEncoding("ISO-8859-1");
        } catch (final UnsupportedEncodingException e) {
            System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
            e.printStackTrace();
        }
        final HashMap formFields = new HashMap();
        final Enumeration en = req.getParameterNames();
        String[] strValores;
        while (en.hasMoreElements()) {
            final String nomeCampo = (String) en.nextElement();
            strValores = req.getParameterValues(nomeCampo);
            if (strValores.length == 0) {
                formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
            } else {
                if (strValores.length == 1) {
                    formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(strValores[0]));
                } else {
                    formFields.put(nomeCampo.toUpperCase(), strValores);
                }
            }
        }
        return formFields;
    }

    public StringBuilder geraExportObjetoNegocioSql(final HashMap hashValores, final Integer idObjetoNegocio, String sqlDelete, final String nomeTabela,
            final String filterAditional, final String order) throws ServiceException, Exception {
        final CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
        objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
        objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
        final Collection<CamposObjetoNegocioDTO> col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
        String sqlCondicao = "";
        String sqlCampos = "";
        String sqlInsert = "";
        String camposInsert = "";

        // Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
        System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());

        hashValores.put("NOMETABELADB", objetoNegocioDTO.getNomeTabelaDB());
        String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
        tabelasTratadas = UtilStrings.nullToVazio(tabelasTratadas);
        tabelasTratadas = tabelasTratadas + "'" + objetoNegocioDTO.getNomeTabelaDB() + "'";
        hashValores.put("TABELASTRATADAS", tabelasTratadas);
        if (col != null) {
            for (final CamposObjetoNegocioDTO camposObjeto : col) {
                if (!sqlCampos.trim().equalsIgnoreCase("")) {
                    sqlCampos += ",";
                    camposInsert += ",";
                }
                sqlCampos = sqlCampos + camposObjeto.getNomeDB();
                camposInsert += "'" + camposObjeto.getNomeDB() + "'";
                final String cond = (String) hashValores.get("COND_" + camposObjeto.getIdCamposObjetoNegocio());
                String valor = (String) hashValores.get("VALOR_" + camposObjeto.getIdCamposObjetoNegocio());
                if (!UtilStrings.nullToVazio(cond).trim().equalsIgnoreCase("") && !UtilStrings.nullToVazio(valor).trim().equalsIgnoreCase("")) {
                    sqlCondicao = sqlCondicao + " " + camposObjeto.getNomeDB();
                    if (cond != null && cond.equalsIgnoreCase("1")) {
                        sqlCondicao = sqlCondicao + " <> ";
                    } else if (cond != null && cond.equalsIgnoreCase("2")) {
                        sqlCondicao = sqlCondicao + " > ";
                    } else if (cond != null && cond.equalsIgnoreCase("3")) {
                        sqlCondicao = sqlCondicao + " < ";
                    } else {
                        sqlCondicao = sqlCondicao + " " + cond + " ";
                    }
                    final boolean isStringType = MetaUtil.isStringType(camposObjeto.getTipoDB());
                    if (isStringType) {
                        if (cond.equalsIgnoreCase("=") || cond.equalsIgnoreCase("1") || cond.equalsIgnoreCase("2") || cond.equalsIgnoreCase("3")) {
                            valor = valor.replaceAll("'", "");
                            valor = "'" + valor + "'";
                        }
                    }
                    if (cond != null && !cond.trim().equalsIgnoreCase("IS NULL")) {
                        sqlCondicao = sqlCondicao + valor;
                    }
                }
            }
        }
        String sqlFinal = "SELECT " + sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
        sqlDelete = "DELETE FROM " + objetoNegocioDTO.getNomeTabelaDB();
        if (!sqlCondicao.trim().equalsIgnoreCase("")) {
            if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
                sqlFinal = sqlFinal + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
                sqlDelete = sqlDelete + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
            } else {
                sqlFinal = sqlFinal + " WHERE " + sqlCondicao;
                sqlDelete = sqlDelete + " WHERE " + sqlCondicao;
            }
        } else {
            if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
                sqlFinal = sqlFinal + " WHERE (" + filterAditional + ")";
                sqlDelete = sqlDelete + " WHERE (" + filterAditional + ")";
            }
        }
        if (order != null && !order.trim().equalsIgnoreCase("")) {
            sqlFinal += " ORDER BY " + order;
        }

        String sqlDeleteAux = (String) hashValores.get("COMMANDDELETE");
        String sqlExportAux = (String) hashValores.get("COMMAND");

        if (!UtilStrings.nullToVazio(sqlDeleteAux).trim().equalsIgnoreCase("")) {
            sqlDeleteAux = sqlDelete + "; " + UtilStrings.nullToVazio(sqlDeleteAux);
        } else {
            sqlDeleteAux = sqlDelete;
        }
        if (!UtilStrings.nullToVazio(sqlExportAux).trim().equalsIgnoreCase("")) {
            sqlExportAux = sqlFinal + "; " + UtilStrings.nullToVazio(sqlExportAux);
        } else {
            sqlExportAux = sqlFinal;
        }

        hashValores.put("COMMANDDELETE", sqlDeleteAux);
        hashValores.put("COMMAND", sqlExportAux);
        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        List lst = null;
        try {
            lst = jdbcEngine.execSQL(sqlFinal, null, 0);
        } catch (final Exception e) {
            e.printStackTrace();
            return new StringBuilder("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
        }

        final StringBuilder strSQL = new StringBuilder();
        strSQL.append("");

        sqlInsert = "INSERT INTO '" + objetoNegocioDTO.getNomeTabelaDB() + "' (" + camposInsert + ") VALUES (";

        String keysProcessed = "";
        if (lst != null) {
            for (final Iterator itDados = lst.iterator(); itDados.hasNext();) {
                strSQL.append(sqlInsert);
                final Object[] obj = (Object[]) itDados.next();
                int i = 0;
                for (final Object element : col) {
                    final CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) element;
                    final boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
                    if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
                        if (!keysProcessed.trim().equalsIgnoreCase("")) {
                            keysProcessed = keysProcessed + ",";
                        }
                        if (isStringType) {
                            keysProcessed = keysProcessed + "'" + obj[i] + "'";
                        } else {
                            keysProcessed = keysProcessed + "" + obj[i] + "";
                        }
                    }
                    if (i != 0) {
                        strSQL.append(",");
                    }
                    if (isStringType && obj[i] != null) {
                        strSQL.append("'");
                    }
                    strSQL.append(obj[i]);
                    if (isStringType && obj[i] != null) {
                        strSQL.append("'");
                    }
                    i++;
                }
                strSQL.append(");\n");
            }
        }

        hashValores.put("KEYS", keysProcessed);

        return strSQL;
    }

    public StringBuilder geraRecursiveExportObjetoNegocioSql(final HashMap hashValores, final Integer idObjetoNegocio, final String sqlDelete, String nomeTabela,
            final String filterAditional, final String order) throws ServiceException, Exception {
        final StringBuilder strAux = this.geraExportObjetoNegocioSql(hashValores, idObjetoNegocio, sqlDelete, nomeTabela, filterAditional, order);
        nomeTabela = (String) hashValores.get("NOMETABELADB");
        final String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
        final String keysProcessed = (String) hashValores.get("KEYS");
        final Collection colRelateds = this.getFks(nomeTabela);
        System.out.println("Tratando FKS da Tabelas : " + nomeTabela);
        if (colRelateds != null && colRelateds.size() > 0) {
            final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
            for (final Iterator it = colRelateds.iterator(); it.hasNext();) {
                final DataManagerFKRelatedDTO dataManagerFKRelatedDTO = (DataManagerFKRelatedDTO) it.next();
                System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada());

                final ObjetoNegocioDTO objNegocioDto = objetoNegocioService.getByNomeTabelaDB(dataManagerFKRelatedDTO.getNomeTabelaRelacionada());
                if (objNegocioDto != null) {
                    if (tabelasTratadas.indexOf(("'" + objNegocioDto.getNomeTabelaDB().trim() + "'").toUpperCase()) > -1) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Tabela ja processada: " + objNegocioDto.getNomeTabelaDB());
                        continue;
                    }
                    String filter = "";
                    if (keysProcessed != null && !keysProcessed.trim().equalsIgnoreCase("")) {
                        filter = dataManagerFKRelatedDTO.getPartChild() + " IN (" + keysProcessed + ")";
                        System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada() + " Processando... " + objNegocioDto.getNomeTabelaDB()
                                + " Filtro: " + filter);
                        final StringBuilder strAux2 = this.geraRecursiveExportObjetoNegocioSql(hashValores, objNegocioDto.getIdObjetoNegocio(), sqlDelete,
                                dataManagerFKRelatedDTO.getNomeTabelaRelacionada(), filter, "");
                        strAux.append("\n");
                        strAux.append(strAux2);
                    }
                }
            }
        }
        return strAux;
    }

    public void exportarTudoSql(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        final HashMap hashValores = this.getFormFields(request);
        final StringBuilder str = new StringBuilder();

        final String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
        if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
            document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        final String nomeTabela = "";
        final Collection colObjsNeg = objetoNegocioService.listAtivos();
        if (colObjsNeg != null) {
            for (final Iterator it = colObjsNeg.iterator(); it.hasNext();) {
                final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                final String sqlDelete = "";
                final StringBuilder strAux = this.geraExportObjetoNegocioSql(hashValores, objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
                if (strAux != null) {
                    str.append(strAux);
                }
            }
        }

        String strAux = "";
        strAux += "\n-- origem: " + ORIGEM_SISTEMA;
        strAux += "\n-- Data: " + UtilDatas.dateToSTRWithFormat(UtilDatas.getDataAtual(), "dd/MM/yyyy hh:MM:ss") + "\n";
        strAux += "\n" + str.toString();
        strAux += "\n-- END SQL";

        String strDateTime = new java.util.Date().toString();
        strDateTime = strDateTime.replaceAll(" ", "_");
        strDateTime = strDateTime.replaceAll(":", "_");
        UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.CAMINHO_REAL_APP + "/exportSQL/export_data_ALL_" + strDateTime + ".sql", strAux.toString());

        document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        document.executeScript("getFile('" + CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML/export_data_ALL_" + strDateTime + ".sql', 'export_data_ALL_" + strDateTime + ".sql')");
    }

    public void carregaMetaDados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DataBaseMetaDadosService data = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(DataBaseMetaDadosService.class, null);
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        final VisaoDao visaoDao = new VisaoDao();
        final Connection con = visaoDao.getTransactionControler().getConnection();
        String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
        if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
            DB_SCHEMA = "citsmart";
        }

        // Desabilitando as tabelas para garantir que as que não existam mais não fiquem ativas
        this.desabilitaTabelas();

        final Collection colObsNegocio = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, true);
        con.close();

        for (final Iterator it = colObsNegocio.iterator(); it.hasNext();) {
            final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();

            System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());

            final Collection colObjs = this.getObjetoNegocioService().findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
            if (colObjs == null || colObjs.size() == 0) {
                System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
                this.getObjetoNegocioService().create(objetoNegocioDTO);
            } else {
                final ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO) ((List) colObjs).get(0);
                objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
                System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB() + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
                this.getObjetoNegocioService().update(objetoNegocioDTO);
            }
        }

        data.corrigeTabelaComplexidade();
        data.corrigeTabelaSla();
        data.corrigeTabelaFluxoServico();

        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        this.load(document, request, response);
    }

    private ObjetoNegocioService getObjetoNegocioService() throws ServiceException, Exception {
        if (objetoNegocioService == null) {
            return (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        }
        return objetoNegocioService;
    }

    private void desabilitaTabelas() throws LogicException, ServiceException, Exception {
        final Collection<ObjetoNegocioDTO> listObjetoNegocio = this.getObjetoNegocioService().list();

        for (final ObjetoNegocioDTO objetoNegocioDTO : listObjetoNegocio) {
            objetoNegocioDTO.setSituacao("I");
            this.getObjetoNegocioService().updateDisable(objetoNegocioDTO);
        }
    }

}
