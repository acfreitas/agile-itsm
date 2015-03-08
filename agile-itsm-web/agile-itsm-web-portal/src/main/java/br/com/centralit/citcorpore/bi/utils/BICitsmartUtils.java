package br.com.centralit.citcorpore.bi.utils;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringEscapeUtils;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ImportInfoField;
import br.com.centralit.citcorpore.util.ImportInfoRecord;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilImportData;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BICitsmartUtils {

    /**
     * Autentica o usuário utilizando JSON
     *
     * @param url
     * @param usuario
     * @param senha
     * @return BICitsmartResultRotinaDTO
     * @throws Exception
     */
    public static BICitsmartResultRotinaDTO autenticacaoComJSON(String url, String usuario, String senha)
            throws Exception {
        String input = "{\"userName\":\"" + usuario + "\",\"password\":\"" + senha + "\"}";
        BICitsmartResultRotinaDTO resultRotina = new BICitsmartResultRotinaDTO();
        resultRotina.setResultado(false);

        try {
            ClientRequest request = new ClientRequest(url + "services/login");

            request.accept(MediaType.APPLICATION_JSON);
            request.body(MediaType.APPLICATION_JSON, input);

            ClientResponse<String> response = request.post(String.class);

            try {
                CtLoginResp resp = new Gson().fromJson(response.getEntity(), CtLoginResp.class);

                if (response != null && response.getStatus() != 200) {
                    if (resp != null && resp.getError().getDescription() != null
                            && !resp.getError().getDescription().equals("")) {
                        resultRotina.concatMensagem("- Estabelecimento da conexão: " + response.getStatus() + " - "
                                + resp.getError().getDescription() + " (Falha) ");
                    } else {
                        resultRotina.concatMensagem("- Estabelecimento da conexão HTTP (Falha): Erro "
                                + response.getStatus());
                    }
                } else {
                    resultRotina.concatMensagem("- Autenticação do usuário (OK)");
                    resultRotina.setResultado(true);
                    resultRotina.setSessionID(resp.getSessionID());
                }
            } catch (JsonSyntaxException e) {
                resultRotina.concatMensagem("- Estabelecimento da conexão HTTP (Falha)");
            }

            return resultRotina;
        } catch (ConnectException e) {
            System.out.println("- Estabelecimento da conexão (Falha).");
            return resultRotina;
        }
    }

    /**
     * Recupera o objeto negocio utilizando o nome da tabela
     *
     * @param name
     * @return ObjetoNegocioDTO
     * @throws Exception
     */
    protected static ObjetoNegocioDTO restoreByName(String name) throws Exception {
        ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(
                ObjetoNegocioService.class, null);
        return objetoNegocioService.findByNomeObjetoNegocio(name);
    }

    /**
     * Recupera o xml do objeto negocio indicado
     *
     * @param dbName
     * @param filterAditional
     * @return String
     * @throws Exception
     * @author rodrigo.acorse
     */
    protected static String exportDB(String dbName, String filterAditional, boolean xmlComIdConexao) throws Exception {
        ObjetoNegocioDTO objetoNegocioDto = BICitsmartUtils.restoreByName(dbName);
        if (objetoNegocioDto != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("excluirAoExportar", "N");
            map.put("exportarVinculos", "N");

            StringBuilder result = BICitsmartUtils.geraExportObjetoNegocio(map, objetoNegocioDto.getIdObjetoNegocio(),
                    "", "", filterAditional, "", xmlComIdConexao);
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * Gera um IN em SQL com os IDs do xml indicado
     *
     * @param xml
     * @param id
     * @return String
     * @throws Exception
     * @author rodrigo.acorse
     */
    protected static String generateSQLIn(String xml, String id) throws Exception {
        List colRecordsGeral = new ArrayList();
        String glue = "";
        String generatedIn = "";
        Integer count = 0;
        xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='0'>\n" + xml + "\n</tables>";

        Collection colRecords = UtilImportData.readXMLSource(xml);
        if (colRecords != null) {
            colRecordsGeral.addAll(colRecords);
        }

        if (!colRecordsGeral.isEmpty()) {
            for (Iterator itRecords = colRecordsGeral.iterator(); itRecords.hasNext();) {
                ImportInfoRecord importInfoRecord = (ImportInfoRecord) itRecords.next();
                for (Iterator it = importInfoRecord.getColFields().iterator(); it.hasNext();) {
                    ImportInfoField importInfoField = (ImportInfoField) it.next();
                    if (importInfoField.getNameField().equalsIgnoreCase(id)) {
                        if (count == 0 && generatedIn.equals("")) {
                            generatedIn += id + " IN ( "; // Se é o primeiro item e a váriavel generatedIn for vazia,
                            // simplesmente abre o IN.
                        } else if (count == 0 && !generatedIn.equals("")) {
                            generatedIn += " OR " + id + " IN ( "; // Se é o primeiro item mas a váriavel generatedIn
                            // não for vazia, abre o IN adicionando OR no início.
                        }

                        generatedIn += glue + importInfoField.getValueField(); // Adiciona o item ao IN
                        glue = ", "; // Marca a vírgula para o próximo item
                        count++; // Soma +1 para o número de itens

                        if (count == 900) { // Se a soma for igual a 900
                            generatedIn += " ) "; // Fecha o IN
                            glue = ""; // Esvazia a vírgula
                            count = 0; // Zera a contagem
                        }
                    }
                }
            }
            if (count != 0 && !generatedIn.equals("") && !generatedIn.endsWith(" ) ")) {
                generatedIn += " ) "; // Fecha o IN
            }
        }

        if (!generatedIn.equals("")) {
            return " ( " + generatedIn + " ) ";
        } else {
            return "";
        }
    }

    /**
     * Retorna o xml de todas as tabelas utilizadas no BI do Citsmart
     *
     * @return String
     * @throws Exception
     * @author rodrigo.acorse
     */
    public static String recuperaXmlTabelasBICitsmart(boolean xmlComIdConexao) throws Exception {
        try {
            HashMap<String, String> chavesTabelas = new HashMap<String, String>();
            HashMap<String, String> xmlTabelas = new HashMap<String, String>();
            String db;

            // Inicia a string que receberá o xml final
            StringBuilder contentXml = new StringBuilder();

            // Tabela de Moedas
            xmlTabelas.put("moedas", BICitsmartUtils.exportDB("moedas", "", xmlComIdConexao) + "\n");

            // Tabela de Clientes
            xmlTabelas.put("clientes", BICitsmartUtils.exportDB("clientes", "", xmlComIdConexao) + "\n");

            // Tabela de Contratos
            db = BICitsmartUtils.exportDB("contratos", "", xmlComIdConexao);

            if (!db.equalsIgnoreCase("")) {
                xmlTabelas.put("contratos", db + "\n");
                chavesTabelas.put("idcontrato", generateSQLIn(db, "IDCONTRATO"));
            }

            // Tabela de OS
            db = BICitsmartUtils.exportDB("os", chavesTabelas.get("idcontrato"), xmlComIdConexao);

            if (!db.equalsIgnoreCase("")) {
                xmlTabelas.put("os", db + "\n");
                chavesTabelas.put("idos", generateSQLIn(db, "IDOS"));
            }

            // Tabela de Glosa OS
            xmlTabelas.put("glosaos", BICitsmartUtils.exportDB("glosaos", chavesTabelas.get("idos"), xmlComIdConexao)
                    + "\n");

            // Tabela de Fatura
            db = BICitsmartUtils.exportDB("fatura", chavesTabelas.get("idcontrato"), xmlComIdConexao);

            if (!db.equalsIgnoreCase("")) {
                xmlTabelas.put("fatura", db + "\n");
                chavesTabelas.put("idfatura", generateSQLIn(db, "IDFATURA"));
            }

            // Tabela de Fatura OS
            if (chavesTabelas.get("idfatura") != null && !chavesTabelas.get("idfatura").equals("")
                    && chavesTabelas.get("idos") != null && !chavesTabelas.get("idos").equals("")) {
                xmlTabelas.put(
                        "faturaos",
                        BICitsmartUtils.exportDB("faturaos",
                                chavesTabelas.get("idfatura") + " AND " + chavesTabelas.get("idos"), xmlComIdConexao)
                                + "\n");
            } else {
                if (chavesTabelas.get("idfatura") != null && !chavesTabelas.get("idfatura").equals("")) {
                    xmlTabelas
                    .put("faturaos",
                            BICitsmartUtils.exportDB("faturaos", chavesTabelas.get("idfatura"), xmlComIdConexao)
                            + "\n");
                } else if (chavesTabelas.get("idos") != null && !chavesTabelas.get("idos").equals("")) {
                    xmlTabelas.put("faturaos",
                            BICitsmartUtils.exportDB("faturaos", chavesTabelas.get("idos"), xmlComIdConexao) + "\n");
                }
            }

            // Tabela de Fatura Apuração Ans
            xmlTabelas.put("faturaapuracaoans",
                    BICitsmartUtils.exportDB("faturaapuracaoans", chavesTabelas.get("idfatura"), xmlComIdConexao)
                    + "\n");

            // Tabela de Atividades OS
            xmlTabelas.put("atividadesos",
                    BICitsmartUtils.exportDB("atividadesos", chavesTabelas.get("idos"), xmlComIdConexao) + "\n");

            // Tabela de Serviço Contrato
            if (chavesTabelas.get("idcontrato") != null) {
                db = BICitsmartUtils.exportDB("servicocontrato", " IDSERVICO IN (SELECT IDSERVICO FROM SERVICO) AND "
                        + chavesTabelas.get("idcontrato"), xmlComIdConexao);
            } else {
                db = BICitsmartUtils.exportDB("servicocontrato", " IDSERVICO IN (SELECT IDSERVICO FROM SERVICO) ",
                        xmlComIdConexao);
            }

            if (!db.equalsIgnoreCase("")) {
                xmlTabelas.put("servicocontrato", db + "\n");
                chavesTabelas.put("idservicocontrato", generateSQLIn(db, "IDSERVICOCONTRATO"));
                chavesTabelas.put("idservico", generateSQLIn(db, "IDSERVICO"));
            }

            // Tabela de Atividades Serviço Contrato
            xmlTabelas.put(
                    "atividadesservicocontrato",
                    BICitsmartUtils.exportDB("atividadesservicocontrato", chavesTabelas.get("idservicocontrato"),
                            xmlComIdConexao) + "\n");

            // Tabela de Serviço
            xmlTabelas.put("servico",
                    BICitsmartUtils.exportDB("servico", chavesTabelas.get("idservico"), xmlComIdConexao) + "\n");

            // Monta XML final
            contentXml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='0'>\n");
            contentXml.append(xmlTabelas.get("moedas"));
            contentXml.append(xmlTabelas.get("clientes"));
            contentXml.append(xmlTabelas.get("contratos"));
            contentXml.append(xmlTabelas.get("servico"));
            contentXml.append(xmlTabelas.get("servicocontrato"));
            contentXml.append(xmlTabelas.get("os"));
            contentXml.append(xmlTabelas.get("glosaos"));
            contentXml.append(xmlTabelas.get("fatura"));
            contentXml.append(xmlTabelas.get("faturaos"));
            contentXml.append(xmlTabelas.get("faturaapuracaoans"));
            contentXml.append(xmlTabelas.get("atividadesservicocontrato"));
            contentXml.append(xmlTabelas.get("atividadesos"));
            contentXml.append("\n</tables>");

            return StringEscapeUtils.escapeHtml(contentXml.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gera o XML do objeto negocio indicado
     *
     * @param hashValores
     * @param idObjetoNegocio
     * @param sqlDelete
     * @param nomeTabela
     * @param filterAditional
     * @param order
     * @param xmlComIdConexao
     * @return StringBuilder
     * @throws ServiceException
     * @throws Exception
     */
    protected static StringBuilder geraExportObjetoNegocio(HashMap hashValores, Integer idObjetoNegocio,
            String sqlDelete, String nomeTabela, String filterAditional, String order, boolean xmlComIdConexao)
                    throws ServiceException, Exception {
        CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator
                .getInstance().getService(CamposObjetoNegocioService.class, null);
        ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(
                ObjetoNegocioService.class, null);
        ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
        objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
        objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
        Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
        String sqlCondicao = "";
        String sqlCampos = "";

        String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());

        // Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
        DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
        System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());

        hashValores.put("NOMETABELADB", objetoNegocioDTO.getNomeTabelaDB());
        String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
        tabelasTratadas = UtilStrings.nullToVazio(tabelasTratadas);
        tabelasTratadas = tabelasTratadas + "'" + objetoNegocioDTO.getNomeTabelaDB() + "'";
        hashValores.put("TABELASTRATADAS", tabelasTratadas);
        // nomeTabela = objetoNegocioDTO.getNomeTabelaDB();
        if (col != null) {
            for (Iterator it = col.iterator(); it.hasNext();) {
                CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
                if (!sqlCampos.trim().equalsIgnoreCase("")) {
                    sqlCampos += ",";
                }
                sqlCampos = sqlCampos + camposObjetoNegocioDto.getNomeDB();
                String cond = (String) hashValores.get("COND_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
                String valor = (String) hashValores.get("VALOR_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
                if (!UtilStrings.nullToVazio(cond).trim().equalsIgnoreCase("")
                        && !UtilStrings.nullToVazio(valor).trim().equalsIgnoreCase("")) {
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
                    boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
                    if (isStringType) {
                        if (cond.equalsIgnoreCase("=") || cond.equalsIgnoreCase("1") || cond.equalsIgnoreCase("2")
                                || cond.equalsIgnoreCase("3")) {
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
        JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        List lst = null;
        try {
            lst = jdbcEngine.execSQL(sqlFinal, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new StringBuilder("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
        }
        StringBuilder strXML = new StringBuilder();
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
            for (Iterator itDados = lst.iterator(); itDados.hasNext();) {
                Object[] obj = (Object[]) itDados.next();
                int i = 0;
                j++;
                strXML.append("<record number='" + j + "'>\n");
                for (Iterator it = col.iterator(); it.hasNext();) {
                    CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
                    String key = "n";
                    boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
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
                    if (camposObjetoNegocioDto.getSequence() != null
                            && camposObjetoNegocioDto.getSequence().equalsIgnoreCase("S")) {
                        sequence = "y";
                    }
                    strXML.append("<field name='" + camposObjetoNegocioDto.getNomeDB() + "' key='" + key
                            + "' sequence='" + sequence + "' type='" + camposObjetoNegocioDto.getTipoDB().trim() + "'>");
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

                if (xmlComIdConexao) {
                    // Utilizar o parametro do sistema
                    Integer idConexaoBI = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(
                            Enumerados.ParametroSistema.BICITSMART_ID_CONEXAO, null));
                    strXML.append("<field name='IDCONEXAOBI' key='y' sequence='n' type='NUMBER'>" + idConexaoBI
                            + "</field>\n");
                }

                strXML.append("</record>\n");
            }
        }
        strXML.append("</table>\n");
        hashValores.put("KEYS", keysProcessed);

        return strXML;
    }
}
