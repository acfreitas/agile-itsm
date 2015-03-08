package br.com.centralit.citcorpore.quartz.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;
import br.com.citframework.util.UtilZip;

public class ExecutaBackupLogDados implements Job {

    private String keysProcessed = "";
    private Collection<LogDados> logDados;
    private final int quantidadeDeDadosPorArquivo = CITCorporeUtil.QUANTIDADE_BACKUPLOGDADOS;
    private boolean existeDadosNaTabela;

    private CamposObjetoNegocioService camposObjetoNegocioService = null;
    private ObjetoNegocioService objetoNegocioService = null;

    @Override
    public void execute(final JobExecutionContext arg0) throws JobExecutionException {
        String ORIGEM_SISTEMA;
        try {
            String INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS = ParametroUtil.getValorParametroCitSmartHashMap(
                    Enumerados.ParametroSistema.INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS, "");
            ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, "");
            if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
                System.out.println("Origem do sistema vazio");
                return;
            }
            if (INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS == null || INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS.trim().equalsIgnoreCase("")) {
                INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS = CITCorporeUtil.CAMINHO_REAL_APP + "/exportXML";
            }
            ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();

            objetoNegocioDTO = getObjetoNegocioService().findByNomeObjetoNegocio("LOGDADOS");
            String sqlDelete = "";
            String nomeTabela = "";
            final String excluirAoExportar = "S";
            StringBuilder strAux = null;
            String extensaoArquivo = ".smart";
            int contador = 0;

            existeDadosNaTabela = true;
            while (existeDadosNaTabela) {
            	contador++;
                strAux = geraRecursiveExportObjetoNegocio(objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "");
                if (strAux == null) {
                    strAux = new StringBuilder();
                }
                nomeTabela = "LOGDADOS";

                String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + strAux.toString();
                str = "" + str + "\n</tables>";

                String strDateTime = new java.util.Date().toString();
                strDateTime = strDateTime.replaceAll(" ", "_");
                strDateTime = strDateTime.replaceAll(":", "_");
                UtilTratamentoArquivos.geraFileTxtFromString(INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS + "/export_data_" + strDateTime + extensaoArquivo, str);

                final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);

                if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
                    if (logDados != null && !logDados.isEmpty()) {
                        for (final LogDados log : logDados) {
                            if (log != null) {
                                sqlDelete += "DELETE FROM LOGDADOS WHERE IDLOG =";
                                sqlDelete = sqlDelete + log.getIdlog() + ";";
                            }
                        }

                        TransactionControler tc = getTransactionControler();
                        try {
                            final String[] strDel = sqlDelete.split(";");
                            if (strDel != null) {
                                jdbcEngine.setTransactionControler(tc);
                                System.out.println("Executando rotina de exclusão da tabela LOGDADOS "+contador);
                                for (int i = 0; i < strDel.length; i++) {
                                    try {
                                        if (tc.isStarted()) {
                                            jdbcEngine.execUpdate(strDel[i], null);
                                            if (i != 0 && i % 4000 == 0) {
                                                tc.commit();
                                            }
                                            if (!tc.isStarted()) {
                                               tc.start();
                                            }
                                        }
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                System.out.println("Rotina de exclusão "+contador+" finalizada.");
                                tc.commit();
                            }
                        } catch (final Exception e) {
                            tc.rollback();
                        } finally {
                            try {
                                tc.close();
                            } catch (final Exception e) {}
                        }
                        sqlDelete = "";
                        logDados = null;
                        strAux = null;
                        keysProcessed = "";
                    }
                }
            }
            compactaArquivosLogDados(INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS + "/BackUpLogDados_"+UtilDatas.getDataAtual()+".zip",INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS, extensaoArquivo);
            
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuilder geraRecursiveExportObjetoNegocio(final Integer idObjetoNegocio, final String sqlDelete, final String nomeTabela, final String filterAditional)
            throws ServiceException, Exception {
        final StringBuilder strAux = geraExportObjetoNegocio(idObjetoNegocio, sqlDelete, nomeTabela, filterAditional);
        return strAux;
    }

    public StringBuilder geraExportObjetoNegocio(final Integer idObjetoNegocio, String sqlDelete, final String nomeTabela, final String filterAditional) throws ServiceException,
            Exception {
        ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
        objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
        objetoNegocioDTO = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objetoNegocioDTO);
        final Collection col = getCamposObjetoNegocioService().findByIdObjetoNegocio(idObjetoNegocio);
        final String sqlCondicao = "";
        String sqlCampos = "";

        final String excluirAoExportar = "S";

        // Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
        System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());

//		if (col != null) {
//			for (Iterator it = col.iterator(); it.hasNext();) {
//				CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
//				if (!sqlCampos.trim().equalsIgnoreCase("")) {
//					sqlCampos += ",";
//				}
//				sqlCampos = sqlCampos + camposObjetoNegocioDto.getNomeDB();
//
//			}
//		}
        String sqlFinal = "SELECT ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sqlFinal = sqlFinal + " top " + quantidadeDeDadosPorArquivo + " ";
        }
		sqlCampos = " IDLOG,DTATUALIZACAO,OPERACAO,DADOS,IDUSUARIO,LOCALORIGEM,NOMETABELA,LOGDADOSCOL,DATALOG ";
        sqlFinal += sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
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

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sqlFinal = sqlFinal + " ORDER BY IDLOG ASC LIMIT " + quantidadeDeDadosPorArquivo;
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sqlFinal = sqlFinal + " ORDER BY IDLOG ASC ";
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            if (!sqlFinal.contains("WHERE")) {
                sqlFinal = sqlFinal + " WHERE ROWNUM <= " + quantidadeDeDadosPorArquivo + "  ORDER BY IDLOG ASC ";
            } else {
                sqlFinal = sqlFinal + " ROWNUM <= " + quantidadeDeDadosPorArquivo + "  ORDER BY IDLOG ASC ";
            }
        }

        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        List lst = null;
        try {
            lst = jdbcEngine.execSQL(sqlFinal, null, 0);
        } catch (final Exception e) {
            e.printStackTrace();
            return new StringBuilder("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
        }

        if (lst.size() < quantidadeDeDadosPorArquivo) {
            existeDadosNaTabela = false;
        }
        if (!lst.isEmpty()) {
            final List<String> listRetorno = new ArrayList<String>();
            listRetorno.add("idlog");
            logDados = new ArrayList<LogDados>();
            logDados = jdbcEngine.listConvertion(LogDados.class, lst, listRetorno);
        }

        final StringBuilder strXML = new StringBuilder();
        strXML.append("<table name='" + objetoNegocioDTO.getNomeTabelaDB() + "'>\n");
        strXML.append("<command><![CDATA[" + sqlFinal + "]]></command>\n");
        if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
            strXML.append("<commandDelete><![CDATA[" + sqlDelete + "]]></commandDelete>\n");
        } else {
            strXML.append("<commandDelete>NONE</commandDelete>\n");
        }
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

        return strXML;
    }
    
    public void compactaArquivosLogDados(String nomeArquivoGravado, String CaminhoArquivo,String extensaoArquivo) throws Exception{
    	try{
    		UtilZip.zipDirectoryComExtensao(nomeArquivoGravado, CaminhoArquivo,extensaoArquivo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    

    private TransactionControler transactionControler;

    private TransactionControler getTransactionControler() throws PersistenceException {
        if (transactionControler == null) {
            transactionControler = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));
        }
        if (!transactionControler.isStarted()) {
            transactionControler.start();
        }
        return transactionControler;
    }

    private CamposObjetoNegocioService getCamposObjetoNegocioService() throws Exception {
        if (camposObjetoNegocioService == null) {
            camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        }
        return camposObjetoNegocioService;
    }

    private ObjetoNegocioService getObjetoNegocioService() throws Exception {
        if (objetoNegocioService == null) {
            objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        }
        return objetoNegocioService;
    }

}
