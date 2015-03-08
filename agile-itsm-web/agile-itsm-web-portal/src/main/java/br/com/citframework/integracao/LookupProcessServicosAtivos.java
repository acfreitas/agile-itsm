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
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.LookupDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Campo;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.LookupFieldUtil;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class LookupProcessServicosAtivos extends LookupProcessDefaultDao {

    @Override
    public List processLookup(final LookupDTO lookupObject, final HttpServletRequest request) throws LogicException, Exception {

        if (strSGBDPrincipal == null) {
            strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
            strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
        }

        StringBuilder sql = new StringBuilder();
        String camposDesejados = "";
        String where = "";

        final LookupFieldUtil lookUpField = new LookupFieldUtil();
        final Collection colCamposRet = lookUpField.getCamposRetorno(lookupObject.getNomeLookup());
        Iterator itRet = colCamposRet.iterator();
        Campo campo;

        String camposDesejados2 = "";
        final Collection camposOracle = new LinkedList();

        // Os valores que podem ser consultados sao os referentes a:
        while (itRet.hasNext()) {
            campo = (Campo) itRet.next();

            if (strSGBDPrincipal.equalsIgnoreCase("ORACLE") || strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                if (!camposDesejados.toString().equalsIgnoreCase("") && !camposOracle.contains(campo.getNomeFisico().trim())) {
                    camposDesejados = camposDesejados + ",";
                }
                if (camposOracle.contains(campo.getNomeFisico().trim())) {
                    camposDesejados2 = "," + campo.getNomeFisico().trim();
                } else {
                    camposOracle.add(campo.getNomeFisico().trim());
                    camposDesejados = camposDesejados + campo.getNomeFisico();
                }
            } else {
                if (!camposDesejados.toString().equalsIgnoreCase("")) {
                    camposDesejados = camposDesejados + ",";
                }
                camposDesejados = camposDesejados + campo.getNomeFisico();
            }

        }

        sql.append("SELECT  " + camposDesejados + " ");
        sql.append(" FROM " + lookUpField.getTabela(lookupObject.getNomeLookup()) + " ");

        final Collection colCamposPesq = lookUpField.getCamposPesquisa(lookupObject.getNomeLookup());
        final Iterator itPesq = colCamposPesq.iterator();
        String obj = null;
        int count = 1;

        while (itPesq.hasNext()) {
            campo = (Campo) itPesq.next();
            obj = null;
            obj = this.getValueParmLookup(lookupObject, count);
            if (obj != null) {
                final String[] trataGetNomeFisico = campo.getNomeFisico().split("\\.");
                String nomeFisico = campo.getNomeFisico();
                if (trataGetNomeFisico.length > 1) {
                    campo.setNomeFisico(trataGetNomeFisico[1]);
                    nomeFisico = trataGetNomeFisico[0] + "." + trataGetNomeFisico[1];
                }
                if (!obj.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where = where + " AND ";
                    }
                    if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim())
                            || campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())) {
                        final String func = Constantes.getValue("FUNCAO_CONVERTE_MAIUSCULO");
                        if (func != null && !func.trim().equalsIgnoreCase("")) {
                            where = where + func + "(" + nomeFisico + ")";
                        } else {
                            where = where + campo.getNomeFisico();
                        }
                        where = where + " LIKE '%";
                    } else {
                        if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
                            where = where + campo.getNomeFisico();
                            where = where + " IN (";
                        } else if (campo.getType().equalsIgnoreCase("DATE")) {
                            where = where + campo.getNomeFisico();
                            where = where + " = '";
                        } else {
                            if (campo.getNomeFisico().equalsIgnoreCase("IDCONTRATO")) {
                                where = where + " servico.idservico NOT IN  ( SELECT idservico FROM servicocontrato WHERE  ((servicocontrato.idcontrato) ";
                                where = where + " = ";
                            } else {
                                where = where + nomeFisico;
                                where = where + " = ";
                            }

                        }
                    }
                    if (campo.isSomenteBusca()) {
                        obj = obj.trim();
                        obj = obj.toUpperCase();
                        obj = Normalizer.normalize(obj, Normalizer.Form.NFD);
                        obj = obj.replaceAll("[^\\p{ASCII}]", "");
                    }

                    if (StringUtils.contains(obj, "'") && !campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
                        obj = StringEscapeUtils.escapeSql(obj);
                    }

                    where = where + obj;
                    if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim())
                            || campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())) {
                        where = where + "%'";
                    } else if (campo.getType().equalsIgnoreCase("DATE")) {
                        where = where + "'";
                    } else if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())) {
                        where = where + ")";
                    } else if (campo.getNomeFisico().equalsIgnoreCase("IDCONTRATO")) {
                        where = where + " or (servicocontrato.idcontrato) IS NULL))";
                        where = where + " AND (servico.deleted IS NULL or servico.deleted = 'N') ";
                    }
                }
            }
            count++;
        }

        String strAux;
        if (!where.equalsIgnoreCase("")) {
            sql.append(" WHERE " + where);
            strAux = lookUpField.getWhere(lookupObject.getNomeLookup());
            if (!strAux.equalsIgnoreCase("")) {
                sql.append(" AND ");
                sql.append(strAux);
            }
        } else {
            strAux = lookUpField.getWhere(lookupObject.getNomeLookup());
            if (!strAux.equalsIgnoreCase("")) {
                sql.append(" WHERE " + strAux);
            }
        }

        // ordernação
        final Collection colCamposOrd = lookUpField.getCamposOrdenacao(lookupObject.getNomeLookup());
        final Iterator itOrd = colCamposOrd.iterator();
        String ordem = "";
        while (itOrd.hasNext()) {
            campo = (Campo) itOrd.next();
            if (!ordem.equalsIgnoreCase("")) {
                ordem = ordem + ",";
            }
            ordem = ordem + campo.getNomeFisico();
        }

        if (!ordem.equalsIgnoreCase("") && !strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
            sql.append(" ORDER BY " + ordem + " ");
        }

        // Paginação

        Integer totalPag = 1;
        Integer pagAtual = 0;
        Integer pagAtualAux = 0;

        final Integer quantidadePaginator = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));

        if (lookupObject.getPaginacao().equalsIgnoreCase(quantidadePaginator.toString())) {
            pagAtual = quantidadePaginator;
        } else if (new Integer(lookupObject.getPaginacao()) == 1) {
            pagAtual = new Integer(request.getSession(true).getAttribute("pagAtual_" + lookupObject.getNomeLookup()).toString()) + quantidadePaginator;
            pagAtualAux = new Integer(request.getSession(true).getAttribute("pagAtualAux_" + lookupObject.getNomeLookup()).toString()) + 1;
            if (pagAtual >= new Integer(request.getSession(true).getAttribute("totalItens_" + lookupObject.getNomeLookup()).toString())) {
                pagAtual = new Integer(request.getSession(true).getAttribute("pagAtual_" + lookupObject.getNomeLookup()).toString());
            }
            if (pagAtualAux >= new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString())) {
                pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString());
            }
        } else if (new Integer(lookupObject.getPaginacao()) < 0) {
            pagAtual = new Integer(request.getSession(true).getAttribute("pagAtual_" + lookupObject.getNomeLookup()).toString()) - quantidadePaginator;
            pagAtualAux = new Integer(request.getSession(true).getAttribute("pagAtualAux_" + lookupObject.getNomeLookup()).toString()) - 1;
            if (pagAtual < 1) {
                pagAtual = 0;
                pagAtualAux = 1;
            }
        } else if (new Integer(lookupObject.getPaginacao()) == 0) {
            pagAtual = 0;
            pagAtualAux = 1;
        } else {
            pagAtualAux = new Integer(request.getSession(true).getAttribute("totalPag_" + lookupObject.getNomeLookup()).toString()) + 1;
            final Integer modulo = new Integer(request.getSession(true).getAttribute("totalItens_" + lookupObject.getNomeLookup()).toString()) % quantidadePaginator;
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
                if (camposDesejados.contains(".") || camposDesejados2.contains(".")) {
                    camposDesejados = camposDesejados.replaceAll("[A-Za-z ]*\\.", "table2_.");
                    camposDesejados2 = camposDesejados2.replaceAll("[A-Za-z ]*\\.", "table2_.");
                    limit = " select " + camposDesejados + camposDesejados2 + " from (select table_.*, rownum rownum_ from (select count(*) over() as totalRowCount,"
                            + sql.substring(6, sql.length()) + ") table_ where rownum<= " + quantidadePaginator2 + " ) table2_ where rownum_ > " + pagAtual;
                } else {
                    limit = " select " + camposDesejados + camposDesejados2 + " from (select table_.*, rownum rownum_ from (select count(*) over() as totalRowCount,"
                            + sql.substring(6, sql.length()) + ") table_ where rownum<= " + quantidadePaginator2 + " ) where rownum_ > " + pagAtual;
                }
            } else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                Integer quantidadePaginator2 = new Integer(0);
                String orderBy = "";
                camposDesejados = camposDesejados.replaceAll("[A-Za-z ]*\\.", "");
                camposDesejados2 = camposDesejados2.replaceAll("[A-Za-z ]*\\.", "");
                quantidadePaginator2 = quantidadePaginator + pagAtual;
                if (pagAtual != 1) {
                    pagAtual++;
                }
                if (!ordem.equalsIgnoreCase("")) {
                    orderBy += " ORDER BY " + ordem + " ";
                } else {
                    orderBy += "ORDER BY  (SELECT 1)";
                }
                limit = " select " + camposDesejados + camposDesejados2 + " from (select ROW_NUMBER() OVER(" + orderBy + ") as rownum_, " + sql.substring(6, sql.length())
                        + ")  as table_ where table_.rownum_ between " + pagAtual + " and " + quantidadePaginator2;
            }
        }

        final List listaTotal = this.execSQL(sql.toString(), null);
        if (listaTotal != null) {
            request.getSession(true).setAttribute("totalItens_" + lookupObject.getNomeLookup(), listaTotal.size());
            if (listaTotal.size() > quantidadePaginator) {
                totalPag = listaTotal.size() / quantidadePaginator;
                if (listaTotal.size() % quantidadePaginator != 0) {
                    totalPag = totalPag + 1;
                }
            } else {
                totalPag = 1;
            }
        }

        request.getSession(true).setAttribute("totalPag_" + lookupObject.getNomeLookup(), totalPag);
        if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
            sql = new StringBuilder();
            sql.append(limit);
        } else {
            if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                sql = new StringBuilder();
                sql.append(limit);

            } else {
                sql.append(limit);
            }
        }

        final String sqlFinal = sql.toString().toUpperCase();

        final List lista = this.execSQL(sqlFinal, null);
        if (lista == null || lista.size() == 0) {
            final TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }

            return null;
        }

        // Processa o resultado.
        final List result = new ArrayList();
        if (lista == null || lista.size() == 0) {
            final TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }

            return result;
        }

        final Iterator it = lista.iterator();
        Campo campoAux;
        int i;
        Collection colAux;
        Object auxObj;
        while (it.hasNext()) {
            final Object[] row = (Object[]) it.next();
            itRet = colCamposRet.iterator();
            i = 0;
            campoAux = null;
            colAux = new ArrayList();
            while (itRet.hasNext()) {
                campo = (Campo) itRet.next();
                campoAux = new Campo(campo.getNomeFisico(), campo.getDescricao(), campo.isObrigatorio(), campo.getType(), campo.getTamanho());
                if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim())
                        || campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())) {
                    if (row[i] == null) {
                        auxObj = new String("");
                    } else {
                        final String str = new String(row[i].toString());
                        auxObj = str.replaceAll("\"", "&quot;").replaceAll("'", "&#180;");
                    }
                    campoAux.setObjValue(auxObj);
                } else if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_DATE").trim())) {
                    if (row[i] == null) {
                        campoAux.setObjValue(null);
                    } else {
                        auxObj = row[i];
                        if (auxObj instanceof java.sql.Date) {
                            campoAux.setObjValue(UtilDatas.dateToSTR((java.sql.Date) auxObj));
                        } else if (auxObj instanceof java.sql.Timestamp) {
                            campoAux.setObjValue(UtilDatas.dateToSTR((java.sql.Timestamp) auxObj));
                        } else {
                            campoAux.setObjValue(auxObj.toString());
                        }
                    }
                } else if (campo.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_MOEDA").trim())) {
                    if (row[i] == null) {
                        campoAux.setObjValue(null);
                    } else {
                        auxObj = row[i];
                        String valorTransf = null;
                        if (auxObj instanceof Double) {
                            valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double) auxObj).doubleValue()), 2);
                        } else if (auxObj instanceof BigDecimal) {
                            valorTransf = UtilFormatacao.formatBigDecimal((BigDecimal) auxObj, 2);
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

        final TransactionControler tc = this.getTransactionControler();
        if (tc != null) {
            tc.close();
        }

        return result;
    }

}
