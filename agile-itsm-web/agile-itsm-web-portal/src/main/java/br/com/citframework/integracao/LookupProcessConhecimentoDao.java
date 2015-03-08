package br.com.citframework.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.LookupDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Campo;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.LookupFieldUtil;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

/**
 *
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class LookupProcessConhecimentoDao extends LookupProcessDefaultDao {

	public List processLookup(LookupDTO lookupObject) throws LogicException, Exception {

		StringBuilder sql = new StringBuilder();
		String camposDesejados = "";
		String where = "";

		LookupFieldUtil lookUpField = new LookupFieldUtil();
		Collection colCamposRet = lookUpField.getCamposRetorno(lookupObject.getNomeLookup());
		Iterator itRet = colCamposRet.iterator();
		Campo cp;

		//Os valores que podem ser consultados sao os referentes a:
		while(itRet.hasNext()){
			cp = (Campo)itRet.next();
			if (!camposDesejados.equalsIgnoreCase("")){
				camposDesejados = camposDesejados + ",";
			}
			camposDesejados = camposDesejados + cp.getNomeFisico();
		}

		//Montando sql de busca
		sql.append("SELECT " + camposDesejados + " ");
		sql.append("FROM " + lookUpField.getTabela(lookupObject.getNomeLookup()) + " ");
		sql.append("INNER JOIN ");
		sql.append("(SELECT MAX(idbaseconhecimento) AS idbaseconhecimento, MAX(versao), CASE WHEN versao = '1.0' ");
		if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")){
			sql.append("THEN titulo ELSE SUBSTR(titulo,1, LENGTH(titulo) - 7) END FROM baseconhecimento WHERE datafim IS NULL AND STATUS = 'S' ");
			sql.append("GROUP BY CASE WHEN versao = '1.0' THEN titulo ELSE SUBSTR(titulo,1, LENGTH(titulo) - 7) END) AS aux ");
		}else{
			sql.append("THEN titulo ELSE SUBSTRING(titulo,1, LENGTH(titulo) - 7) END FROM baseconhecimento WHERE datafim IS NULL AND STATUS = 'S' ");
			sql.append("GROUP BY CASE WHEN versao = '1.0' THEN titulo ELSE SUBSTRING(titulo,1, length(titulo) - 7) END) AS aux ");
		}
		sql.append("ON baseconhecimento.idbaseconhecimento = aux.idbaseconhecimento ");

		Collection colCamposPesq = lookUpField.getCamposPesquisa(lookupObject.getNomeLookup());
		Iterator itPesq = colCamposPesq.iterator();
		String obj = null;
		int count = 1;
		while(itPesq.hasNext()){
			cp = (Campo)itPesq.next();
			obj = null;
			obj = this.getValueParmLookup(lookupObject, count);
			if (obj != null){
				String[] trataGetNomeFisico = cp.getNomeFisico().split("\\.");

				String nomeFisico = cp.getNomeFisico();

				if(trataGetNomeFisico.length > 1){
					cp.setNomeFisico(trataGetNomeFisico[1]);
					nomeFisico = trataGetNomeFisico[0] + "." + trataGetNomeFisico[1];
				}
				if (!obj.equalsIgnoreCase("")){
					if (!where.equalsIgnoreCase("")){
						where = where + " AND ";
					}
					if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) || cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())){
						String func = Constantes.getValue("FUNCAO_CONVERTE_MAIUSCULO");
						if (func != null && !func.trim().equalsIgnoreCase("")){

							if(nomeFisico.equalsIgnoreCase("TITULO")){
								where = where + func + "(baseconhecimento.titulo)";
							} else if(nomeFisico.equalsIgnoreCase("CONTEUDO")){
								where = where + func + "(baseconhecimento.conteudo)";
							} else {
								where = where + func + "(" + nomeFisico + ")";
							}
						}else{
							where = where + cp.getNomeFisico();
						}
						where = where + " LIKE '%";
					}else{
						if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())){
							where = where + cp.getNomeFisico();
							where = where + " IN (";
						}else if (cp.getType().equalsIgnoreCase("DATE")){
							where = where + cp.getNomeFisico();
							where = where + " = '";
						}else{
							where = where + cp.getNomeFisico();
							where = where + " = ";
						}
					}

					if (StringUtils.contains(obj, "'")){
						obj = StringUtils.replace(obj, "'", "\\'");
					}

					where = where + obj;
					if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) ||
							cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())){
						where = where + "%'";
					}else if (cp.getType().equalsIgnoreCase("DATE")){
						where = where + "'";
					}else if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_COMBO").trim())){
						where = where + ")";
					}
				}
			}
			count++;
		}

		String strAux;
		if (!where.equalsIgnoreCase("")){
			sql.append(" WHERE " + where);
			strAux = lookUpField.getWhere(lookupObject.getNomeLookup());
			if (!strAux.equalsIgnoreCase("")){
			    sql.append(" AND ");
			    sql.append(strAux);
			}
		}else{
		    strAux = lookUpField.getWhere(lookupObject.getNomeLookup());
			if (!strAux.equalsIgnoreCase("")){
				sql.append(" WHERE " + strAux);
			}
		}

		Collection colCamposOrd = lookUpField.getCamposOrdenacao(lookupObject.getNomeLookup());
		Iterator itOrd = colCamposOrd.iterator();
		String ordem = "";
		while(itOrd.hasNext()){
			cp = (Campo)itOrd.next();
			if (!ordem.equalsIgnoreCase("")){
				ordem = ordem + ",";
			}
			ordem = ordem + cp.getNomeFisico();
		}

		if (!ordem.equalsIgnoreCase("")){
			sql.append(" ORDER BY " + ordem);
		}

		//sql.append(" LIMIT 0,400");

		String sqlFinal = sql.toString().toUpperCase();

		List lista = execSQL(sqlFinal,null);
		if(lista== null || lista.size()==0){
			TransactionControler tc = this.getTransactionControler();
			if (tc != null){
				tc.close();
			}

			return null;
		}

		//Processa o resultado.
		List result = new ArrayList();
		if(lista== null || lista.size()==0){
			TransactionControler tc = this.getTransactionControler();
			if (tc != null){
				tc.close();
			}

			return result;
		}
		if (lista.size() > 400){
			TransactionControler tc = this.getTransactionControler();
			if (tc != null){
				tc.close();
			}

			throw new LogicException("A consulta retornou mais de 400 registros, por favor, especifique melhor a consulta!");
		}
		Iterator it = lista.iterator();
		Campo campoAux;
		int i;
		Collection colAux;
		Object auxObj;
		while(it.hasNext()){
			Object[] row = (Object[])it.next();
			itRet = colCamposRet.iterator();
			i = 0;
			campoAux = null;
			colAux = new ArrayList();
			while(itRet.hasNext()){
				cp = (Campo)itRet.next();
				campoAux = new Campo(cp.getNomeFisico(),cp.getDescricao(),cp.isObrigatorio(),cp.getType(),cp.getTamanho());
				if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXT").trim()) ||
						cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_TEXTAREA").trim())){
					if(row[i]==null){
						auxObj = new String("");
					}else{
					    String str = new String(row[i].toString());
						auxObj = str.replaceAll("\"", "&quot;").
						        replaceAll("'", "&#180;");
					}
					campoAux.setObjValue(auxObj);
				} else if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_DATE").trim())) {
					if (row[i]==null){
						campoAux.setObjValue(null);
					}else{
						auxObj = row[i];
						if ((auxObj instanceof java.sql.Date)) {
							campoAux.setObjValue(UtilDatas.dateToSTR((java.sql.Date) auxObj));
						}else if ((auxObj instanceof java.sql.Timestamp)) {
							campoAux.setObjValue(UtilDatas.dateToSTR((java.sql.Timestamp) auxObj));
						}else{
							campoAux.setObjValue(auxObj.toString());
						}
					}
				} else if (cp.getType().equalsIgnoreCase(Constantes.getValue("FIELDTYPE_MOEDA").trim())) {
					if (row[i]==null){
						campoAux.setObjValue(null);
					}else{
						auxObj = row[i];
						String valorTransf = null;
						if ((auxObj instanceof Double)) {
							valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double)auxObj).doubleValue()), 2);
						}else if ((auxObj instanceof BigDecimal)) {
							valorTransf = UtilFormatacao.formatBigDecimal(((BigDecimal)auxObj), 2);
						}else{
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
		if (tc != null){
			tc.close();
		}

		return result;

	}

}
