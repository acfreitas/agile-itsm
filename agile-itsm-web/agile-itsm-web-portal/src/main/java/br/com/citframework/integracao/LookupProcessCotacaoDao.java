package br.com.citframework.integracao;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.LookupDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Campo;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.LookupFieldUtil;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

public class LookupProcessCotacaoDao extends LookupProcessDefaultDao {

	public static String strSGBDPrincipal = null;

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List processLookup(LookupDTO lookupObject, HttpServletRequest request) throws LogicException, Exception {
        if (strSGBDPrincipal == null) {
            strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
            strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
        }
        String sql = "";
        String camposRetorno = "";
        String where = "";
        LookupFieldUtil lookUpField = new LookupFieldUtil();
        Collection colCamposRet = lookUpField.getCamposRetorno(lookupObject.getNomeLookup());
        Iterator itRet = colCamposRet.iterator();
        Campo cp;
        String camposRetorno2 = "";
        Collection camposOracle = new LinkedList();
        while (itRet.hasNext()) {
            cp = (Campo) itRet.next();
            if (strSGBDPrincipal.equalsIgnoreCase("ORACLE") || strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                if (!sql.equalsIgnoreCase("") && !camposOracle.contains(cp.getNomeFisico().trim())) {
                    sql = sql + ",";
                }
                if (camposOracle.contains(cp.getNomeFisico().trim())) {
                    camposRetorno2 = "," + cp.getNomeFisico().trim();
                } else {
                    camposOracle.add(cp.getNomeFisico().trim());
                    sql = sql + cp.getNomeFisico().trim();
                }
            } else {
                if (!sql.equalsIgnoreCase("")) {
                    sql = sql + ",";
                }
                sql = sql + cp.getNomeFisico();
            }
        }
        camposRetorno = sql;
        sql = "SELECT " + sql + " FROM " + lookUpField.getTabela(lookupObject.getNomeLookup());
        Collection colCamposPesq = lookUpField.getCamposPesquisa(lookupObject.getNomeLookup());
        Iterator itPesq = colCamposPesq.iterator();
        String obj = null;
        int count = 1;
        while (itPesq.hasNext()) {
            cp = (Campo) itPesq.next();
            obj = null;
            obj = this.getValueParmLookup(lookupObject, count);
            if (obj != null) {
                /*
                 * String[] trataGetNomeFisico = cp.getNomeFisico().split("\\."); String nomeFisico = cp.getNomeFisico(); if (trataGetNomeFisico.length > 1) { cp.setNomeFisico(trataGetNomeFisico[1]);
                 * nomeFisico = trataGetNomeFisico[0] + "." + trataGetNomeFisico[1]; }
                 */

                if (!obj.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where = where + " AND ";
                    }
                    if (cp.getNomeFisico().equalsIgnoreCase("idsolicitacao")) {
                    	where += " exists (select 1 from itemcotacao ic inner join itemrequisicaoproduto ir on ir.iditemcotacao = ic.iditemcotacao where ";
                    	where += " ir.idsolicitacaoservico in ("+obj+") and ic.idcotacao = cotacao.idcotacao) ";
                    }else{
	                    if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())) {
	                        String func = Constantes.getValue("FUNCAO_CONVERTE_MAIUSCULO");
	                        if (func != null && !func.trim().equalsIgnoreCase("")) {
	                            if (cp.isSomenteBusca() && (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES"))) {
	                                where = where + func + "(remove_acento(" + cp.getNomeFisico() + "))";
	                            } else {
	                                where = where + func + "(" + cp.getNomeFisico() + ")";
	                            }
	                        } else {
	                            where = where + cp.getNomeFisico();
	                        }
	                        where = where + " LIKE '%";
	                        obj = StringEscapeUtils.escapeSql(obj);
	                    } else {
	                        if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
	                            where = where + cp.getNomeFisico();
	                            where = where + " IN (";
	                        } else if (cp.getType().equalsIgnoreCase("DATE")) {
	                            where = where + cp.getNomeFisico();
	                            try {
	                                if(strSGBDPrincipal.equalsIgnoreCase("ORACLE")){
	                                    where = where + " = ";
	                                    obj = "to_date('"+obj+"')";
	                                }else{
	                                    where = where + " = '";
	                                    obj = (UtilDatas.strToSQLDate(obj)).toString();
	                                }
	                            } catch (LogicException e) {
	                                throw new LogicException("Data Inválida");
	                            }
	                        } else {
	                            where = where + cp.getNomeFisico();
	                            where = where + " = ";
	                        }
	                    }
                    
	                    if (cp.isSomenteBusca()) {
	                        obj = obj.trim();
	                        obj = obj.toUpperCase();
	                        obj = Normalizer.normalize(obj, Normalizer.Form.NFD);
	                        obj = obj.replaceAll("[^\\p{ASCII}]", "");
	                    }
	
	                    where = where + obj;
	
	                    if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())) {
	                        where = where + "%'";
	
	                        if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
	                            where = where + " COLLATE Latin1_General_CI_AI";
	                        }
	
	                    } else if (cp.getType().equalsIgnoreCase("DATE")) {
	                        if(!strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
	                            where = where + "'";
	                        }
	                    } else if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
	                        where = where + ")";
	                    }
                    }
                }
            }
            count++;
        }

        String strAux;
        if (!where.equalsIgnoreCase("")) {
            sql = sql + " WHERE " + where;
            strAux = lookUpField.getWhere(lookupObject.getNomeLookup());
            if (!strAux.equalsIgnoreCase("")) {
                sql = sql + " AND ( ";
                sql = sql + strAux + " )";
            }
        } else {
            strAux = lookUpField.getWhere(lookupObject.getNomeLookup());
            if (!strAux.equalsIgnoreCase("")) {
                sql = sql + " WHERE " + strAux;
            }
        }

        Collection colCamposOrd = lookUpField.getCamposOrdenacao(lookupObject.getNomeLookup());
        Iterator itOrd = colCamposOrd.iterator();
        String ordem = "";
        while (itOrd.hasNext()) {
            cp = (Campo) itOrd.next();
            if (!ordem.equalsIgnoreCase("")) {
                ordem = ordem + ",";
            }

            if (cp != null && cp.getType() != null && cp.getType().equalsIgnoreCase("text")) {
                if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                    ordem = ordem + " CAST(" + cp.getNomeFisico() + " AS NVARCHAR(MAX)) ";
                } else if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
                    ordem = ordem + " CAST(" + cp.getNomeFisico() + " as VARCHAR2(4000)) ";
                } else {
                    ordem = ordem + cp.getNomeFisico();
                }
            } else {
                ordem = ordem + cp.getNomeFisico();
            }
        }

        if (!ordem.equalsIgnoreCase("") && !strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
            sql = sql + " ORDER BY " + ordem + " ";
        }
        Integer totalPag = 1;
        Integer pagAtual = 0;
        Integer pagAtualAux = 0;

        Integer quantidadePaginator = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
        if (lookupObject.getNomeLookup().equalsIgnoreCase("LOOKUP_SERVICOCONTRATO")) {
            if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
                sql = sql.replace("sc.datafim >= '2000-01-01'", "to_char(sc.datafim, 'yyy-MM-dd') > '" + UtilDatas.getDataAtual().toString() + "'");
            } else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                sql = sql.replace("sc.datafim >= '2000-01-01'", "convert(VARCHAR,sc.datafim, 120) > '" + UtilDatas.getDataAtual().toString() + "'");
            } else {
                sql = sql.replace("2000-01-01", UtilDatas.getDataAtual().toString());
            }

        }
        if (lookupObject.getPaginacao().equalsIgnoreCase(quantidadePaginator.toString())) {
            pagAtual = quantidadePaginator;
        } else if (new Integer(lookupObject.getPaginacao()) == 1) {
            pagAtual = (new Integer(request.getSession(true).getAttribute("pagAtual_" + lookupObject.getNomeLookup()).toString()) + quantidadePaginator);
            pagAtualAux = (new Integer(request.getSession(true).getAttribute("pagAtualAux_" + lookupObject.getNomeLookup()).toString()) + 1);
            if (pagAtual >= new Integer(request.getSession(true).getAttribute("totalItens_" + lookupObject.getNomeLookup()).toString())) {
                pagAtual = new Integer(request.getSession(true).getAttribute("pagAtual_" + lookupObject.getNomeLookup()).toString());
            }
            if (pagAtualAux >= new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString())) {
                pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString());
            }
        } else if (new Integer(lookupObject.getPaginacao()) < 0) {
            pagAtual = (new Integer(request.getSession(true).getAttribute("pagAtual_" + lookupObject.getNomeLookup()).toString()) - quantidadePaginator);
            pagAtualAux = (new Integer(request.getSession(true).getAttribute("pagAtualAux_" + lookupObject.getNomeLookup()).toString()) - 1);
            if (pagAtual < 1) {
                pagAtual = 0;
                pagAtualAux = 1;
            }
        } else if (new Integer(lookupObject.getPaginacao()) == 0) {
            pagAtual = 0;
            pagAtualAux = 1;
        } else {
            pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString()) + 1;
            Integer modulo = (new Integer(request.getSession(true).getAttribute("totalItens_" + lookupObject.getNomeLookup()).toString()) % quantidadePaginator);
            if (modulo.intValue() == quantidadePaginator.intValue() || modulo.intValue() == 0) {
                pagAtual = new Integer(lookupObject.getPaginacao()) - quantidadePaginator;
            } else {
                pagAtual = new Integer(lookupObject.getPaginacao()) - modulo;
            }
            if (pagAtualAux > new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString())) {
                pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString());
            }
        }
        request.getSession(true).setAttribute("pagAtual_" + lookupObject.getNomeLookup(), pagAtual);
        request.getSession(true).setAttribute("pagAtualAux_" + lookupObject.getNomeLookup(), pagAtualAux);
        String limit = " LIMIT ";
        if (quantidadePaginator != null) {
            if (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES")) {
                limit += quantidadePaginator + " OFFSET " + pagAtual + " ";
            } else if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")) {
                limit += pagAtual + " ," + quantidadePaginator + " ";
            } else if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
                Integer quantidadePaginator2 = new Integer(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;
                if (camposRetorno.contains(".") || camposRetorno2.contains(".")) {
                    camposRetorno = camposRetorno.replaceAll("[A-Za-z ]*\\.", "table2_.");
                    camposRetorno2 = camposRetorno2.replaceAll("[A-Za-z ]*\\.", "table2_.");

                    //TRATAMENTO PARA CAMPOSRETORNO
                    String[] camposRetornoArray = null;
                    if(camposRetorno.contains(",")){
                        camposRetornoArray = new String[20];
                        camposRetornoArray = camposRetorno.split(",");
                    }
                    if(camposRetornoArray != null){
                        camposRetorno = "";
                        int contador = 1;
                        for (String string : camposRetornoArray) {
                            String contadorStr = contador + "";
                            camposRetorno = camposRetorno + string + contadorStr ;
                            if(camposRetornoArray.length > contador ){
                                camposRetorno += ",";
                            }
                            contador ++;
                        }
                    }


                    //TRATAMENTO PARA CAMPOSRETORNO2
                    String[] camposRetornoArray2 = null;
                    if(camposRetorno2.contains(",")){
                        camposRetornoArray2 = new String[20];
                        camposRetornoArray2 = camposRetorno2.split(",");
                    }
                    if(camposRetornoArray2 != null){
                        camposRetorno2 = ",";
                        int contador = 1;
                        for (String string : camposRetornoArray2) {
                            if(string != null && !string.isEmpty()){
                                String contadorStr = contador + "";
                                camposRetorno2 = camposRetorno2 + string + contadorStr ;
                                if(camposRetornoArray2.length > contador + 1 ){
                                    camposRetorno2 += ",";
                                }
                                contador ++;
                            }
                        }
                    }

                    //TRATAMENTO PARA O SQL.SUBSTRING
                    String sqlFinal = sql.substring(6, sql.length());
                    int i = sql.indexOf("FROM");
                    String campos = sql.substring(6, i - 1);

                    String[] camposRetornoArray3 = null;
                    if(campos.contains(",")){
                        camposRetornoArray3 = new String[20];
                        camposRetornoArray3 = campos.split(",");
                    }

                    String camposTratadosComApelido = "";

                    int contador = 1;
                    if(camposRetornoArray3 != null){
                        for (String string : camposRetornoArray3) {
                            String contadorStr = contador + "";
                            String stringAux = string.replaceAll("[A-Za-z ]*\\.", "");
                            camposTratadosComApelido = camposTratadosComApelido + string + " AS " + stringAux + contadorStr ;
                            if(camposRetornoArray.length > contador ){
                                camposTratadosComApelido += ",";
                            }
                            contador ++;
                        }
                    }

                    sqlFinal = sqlFinal.replace(campos, camposTratadosComApelido);

                    limit = " select " + camposRetorno + camposRetorno2 + " from (select table_.*, rownum rownum_ from (select count(*) over() as totalRowCount," + sqlFinal
                            + ") table_ where rownum<= " + quantidadePaginator2 + " ) table2_ where rownum_ > " + pagAtual;

                } else {
                    limit = " select " + camposRetorno + camposRetorno2 + " from (select table_.*, rownum rownum_ from (select count(*) over() as totalRowCount," + sql.substring(6, sql.length())
                            + ") table_ where rownum<= " + quantidadePaginator2 + " ) where rownum_ > " + pagAtual;
                }
            } else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                Integer quantidadePaginator2 = new Integer(0);
                String orderBy = "";
                camposRetorno = camposRetorno.replaceAll("[A-Za-z ]*\\.", "");
                camposRetorno2 = camposRetorno2.replaceAll("[A-Za-z ]*\\.", "");
                quantidadePaginator2 = quantidadePaginator + pagAtual;
                if (pagAtual != 1) {
                    pagAtual++;
                }
                if (!ordem.equalsIgnoreCase("")) {
                    orderBy += " ORDER BY " + ordem + " ";
                } else {
                    orderBy += "ORDER BY  (SELECT 1)";
                }

                //tratamento especifico para a lookup de parametros
                if(camposRetorno.contains("idparametrocorpore,valor,valor") && camposRetorno2.contains(",idparametrocorpore")){

                    //TRATAMENTO PARA CAMPOSRETORNO
                    String[] camposRetornoArray = null;
                    if(camposRetorno.contains(",")){
                        camposRetornoArray = new String[20];
                        camposRetornoArray = camposRetorno.split(",");
                    }
                    if(camposRetornoArray != null){
                        camposRetorno = "";
                        int contador = 1;
                        for (String string : camposRetornoArray) {
                            String contadorStr = contador + "";
                            camposRetorno = camposRetorno + string + contadorStr ;
                            if(camposRetornoArray.length > contador ){
                                camposRetorno += ",";
                            }
                            contador ++;
                        }
                    }


                    //TRATAMENTO PARA CAMPOSRETORNO2
                    String[] camposRetornoArray2 = null;
                    if(camposRetorno2.contains(",")){
                        camposRetornoArray2 = new String[20];
                        camposRetornoArray2 = camposRetorno2.split(",");
                    }
                    if(camposRetornoArray2 != null){
                        camposRetorno2 = ",";
                        int contador = 1;
                        for (String string : camposRetornoArray2) {
                            if(string != null && !string.isEmpty()){
                                String contadorStr = contador + "";
                                camposRetorno2 = camposRetorno2 + string + contadorStr ;
                                if(camposRetornoArray2.length > contador + 1 ){
                                    camposRetorno2 += ",";
                                }
                                contador ++;
                            }
                        }
                    }

                    //TRATAMENTO PARA O SQL.SUBSTRING
                    String sqlFinal = sql.substring(6, sql.length());
                    int i = sql.indexOf("FROM");
                    String campos = sql.substring(6, i - 1);

                    String[] camposRetornoArray3 = null;
                    if(campos.contains(",")){
                        camposRetornoArray3 = new String[20];
                        camposRetornoArray3 = campos.split(",");
                    }

                    String camposTratadosComApelido = "";

                    int contador = 1;
                    if(camposRetornoArray3 != null){
                        for (String string : camposRetornoArray3) {
                            String contadorStr = contador + "";
                            String stringAux = string.replaceAll("[A-Za-z ]*\\.", "");
                            camposTratadosComApelido = camposTratadosComApelido + string + " AS " + stringAux + contadorStr ;
                            if(camposRetornoArray.length > contador ){
                                camposTratadosComApelido += ",";
                            }
                            contador ++;
                        }
                    }

                    sqlFinal = sqlFinal.replace(campos, camposTratadosComApelido);

                    limit = " select " + camposRetorno + camposRetorno2 + " from (select ROW_NUMBER() OVER(" + orderBy + ") as rownum_, " + sqlFinal
                            + ")  as table_ where table_.rownum_ between " + pagAtual + " and " + quantidadePaginator2;
                } else {
                    limit = " select " + camposRetorno + camposRetorno2 + " from (select ROW_NUMBER() OVER(" + orderBy + ") as rownum_, " + sql.substring(6, sql.length())
                            + ")  as table_ where table_.rownum_ between " + pagAtual + " and " + quantidadePaginator2;
                }
            }
        }
        if (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES")) {
            sql = sql.replaceAll("LIKE", "ILIKE");
        }
        // Ignorando acentos na pesquisa alterando o parametro nls_sort
        if (strSGBDPrincipal.equalsIgnoreCase("ORACLE") || strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
            // verifica se os parametros já foram alterados na sessão para não alterar várias vezes sem necessidade - melhoria de performace
            String sql1 = "alter session set nls_comp = linguistic";
            try {
                execSQL(sql1, null);
            } catch (Exception e) {
                // e.printStackTrace();
            }
            String sql2 = "alter session set nls_sort = binary_ai";
            try {
                execSQL(sql2, null);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        List listaTotal = execSQL(sql, null);
        if (listaTotal != null) {
            request.getSession(true).setAttribute("totalItens_" + lookupObject.getNomeLookup(), (listaTotal.size()));
            if (listaTotal.size() > quantidadePaginator) {
                totalPag = ((listaTotal.size() / quantidadePaginator));
                if (listaTotal.size() % quantidadePaginator != 0) {
                    totalPag = totalPag + 1;
                }
            } else {
                totalPag = 1;
            }
        }
        request.getSession(true).setAttribute("totalPag_" + lookupObject.getNomeLookup(), totalPag);
        if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
            sql = limit;
        } else {
            if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                sql = limit;

            } else {
                sql = sql + limit;
            }
        }
        List lista = execSQL(sql, null);
        if (lista == null || lista.size() == 0) {
            TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }

            return null;
        }

        // Processa o resultado.
        List result = new ArrayList();
        if (lista == null || lista.size() == 0) {
            TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }

            return result;
        }

        Iterator it = lista.iterator();
        Campo campoAux;
        int i;
        Collection colAux;
        Object auxObj;
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();
            itRet = colCamposRet.iterator();
            i = 0;
            campoAux = null;
            colAux = new ArrayList();
            while (itRet.hasNext()) {
                cp = (Campo) itRet.next();
                campoAux = new Campo(cp.getNomeFisico(), cp.getDescricao(), cp.isObrigatorio(), cp.getType(), cp.getTamanho());
                if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())) {
                    if (row[i] == null) {
                        auxObj = new String("");
                    } else {
                        String str = new String(row[i].toString());
                        auxObj = str.replaceAll("\"", "&quot;").replaceAll("'", "&#180;");
                    }
                    campoAux.setObjValue(auxObj);
                } else if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_DATE").trim())) {
                    if (row[i] == null) {
                        campoAux.setObjValue(null);
                    } else {
                        auxObj = row[i];
                        if ((auxObj instanceof java.sql.Date)) {
                            campoAux.setObjValue(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, (java.sql.Date) auxObj, WebUtil.getLanguage(request)));
                        } else if ((auxObj instanceof java.sql.Timestamp)) {
                            campoAux.setObjValue(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, (java.sql.Timestamp) auxObj, WebUtil.getLanguage(request)));
                        } else {
                            campoAux.setObjValue(auxObj.toString());
                        }
                    }
                } else if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_MOEDA").trim())) {
                    if (row[i] == null) {
                        campoAux.setObjValue(null);
                    } else {
                        auxObj = row[i];
                        String valorTransf = null;
                        if ((auxObj instanceof Double)) {
                            valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double) auxObj).doubleValue()), 2);
                        } else if ((auxObj instanceof BigDecimal)) {
                            valorTransf = UtilFormatacao.formatBigDecimal(((BigDecimal) auxObj), 2);
                        } else {
                            valorTransf = auxObj.toString();
                        }
                        campoAux.setObjValue(valorTransf);
                    }
                }
                colAux.add(campoAux);
                i++;
            }
            result.add(colAux);
        }

        TransactionControler tc = this.getTransactionControler();
        if (tc != null) {
            tc.close();
        }

        return result;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class getBean() {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection getFields() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

}
