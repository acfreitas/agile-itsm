package br.com.centralit.citajax.html;

import java.util.Collection;

import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citajax.util.JavaScriptUtil;

@SuppressWarnings({"rawtypes"})
public class HTMLTable extends HTMLElement {
	public HTMLTable(String idParm, DocumentHTML documentParm) {
		super(idParm, documentParm);
	}
	public String getType() {
		return TABLE;
	}
	/**
	 * Adiciona um linha em uma tabela
	 * @param obj
	 * @param propertyNamesAddTable
	 * @param propertyNamesVerifDuplAddTable
	 * @param mensagemDuplicacaoCasoOcorra
	 * @param arFuncoesExec
	 * @param funcaoClick
	 * @param funcaoVerificacao
	 * @throws Exception
	 */
	public void addRow(Object obj, 
			String[] propertyNamesAddTable, 
			String[] propertyNamesVerifDuplAddTable, 
			String mensagemDuplicacaoCasoOcorra,
			String[] arFuncoesExec, 
			String funcaoClick, 
			String funcaoVerificacao) throws Exception{

		//--
		String strColunas = "";
		if (propertyNamesAddTable != null){
			for(int i = 0; i < propertyNamesAddTable.length; i++){
				if (!strColunas.equalsIgnoreCase("")){
					strColunas += ",";
				}
				strColunas = strColunas + "'" + propertyNamesAddTable[i] + "'";
			}
		}
		if (strColunas.equalsIgnoreCase("")){
			strColunas = "null";
		}else{
			strColunas = "[" + strColunas + "]";
		}
		
		//--
		String strColunasVerifDup = "";
		if (propertyNamesVerifDuplAddTable != null){
			for(int i = 0; i < propertyNamesVerifDuplAddTable.length; i++){
				if (!strColunasVerifDup.equalsIgnoreCase("")){
					strColunasVerifDup += ",";
				}
				strColunasVerifDup = strColunasVerifDup + "'" + propertyNamesVerifDuplAddTable[i] + "'";
			}
		}
		if (strColunasVerifDup.equalsIgnoreCase("")){
			strColunasVerifDup = "null";
		}else{
			strColunasVerifDup = "[" + strColunasVerifDup + "]";
		}	
		
		//--	
		String strFuncoesExec = "";
		if (arFuncoesExec != null){
			for(int i = 0; i < arFuncoesExec.length; i++){
				if (!strFuncoesExec.equalsIgnoreCase("")){
					strFuncoesExec += ",";
				}
				strFuncoesExec = strFuncoesExec + arFuncoesExec[i];
			}
		}
		if (strFuncoesExec.equalsIgnoreCase("")){
			strFuncoesExec = "null";
		}else{
			strFuncoesExec = "[" + strFuncoesExec + "]";
		}
		
		//--
		String strFuncaoClick = "null";
		if (funcaoClick != null){
			strFuncaoClick = funcaoClick;
		}
		
		//--
		String strFuncaoVerificacao = "null";
		if (funcaoVerificacao != null){
			strFuncaoVerificacao = funcaoVerificacao;
		}
		
		String objSerializado = CitAjaxWebUtil.serializeObject(obj, false, this.getDocument().getLanguage());
		
		setCommandExecute("HTMLUtils.addRow('" + this.getId() + "', null, null, ObjectUtils.deserializeObject('" + objSerializado + "'), " + strColunas + ", " + strColunasVerifDup + ", '" + JavaScriptUtil.escapeJavaScript(mensagemDuplicacaoCasoOcorra) + "', " + strFuncoesExec + ", " + strFuncaoClick + ", " + strFuncaoVerificacao + ", false)");
	}
	/**
	 * Atualiza uma linha da tabela pelo indice.
	 * @param obj
	 * @param propertyNamesAddTable
	 * @param propertyNamesVerifDuplAddTable
	 * @param mensagemDuplicacaoCasoOcorra
	 * @param arFuncoesExec
	 * @param funcaoClick
	 * @param funcaoVerificacao
	 * @param indexItem
	 * @throws Exception
	 */
	public void updateRow(Object obj, 
			String[] propertyNamesAddTable, 
			String[] propertyNamesVerifDuplAddTable, 
			String mensagemDuplicacaoCasoOcorra,
			String[] arFuncoesExec, 
			String funcaoClick, 
			String funcaoVerificacao,
			int indexItem) throws Exception{
		//--
		String strColunas = "";
		if (propertyNamesAddTable != null){
			for(int i = 0; i < propertyNamesAddTable.length; i++){
				if (!strColunas.equalsIgnoreCase("")){
					strColunas += ",";
				}
				strColunas = strColunas + "'" + propertyNamesAddTable[i] + "'";
			}
		}
		if (strColunas.equalsIgnoreCase("")){
			strColunas = "null";
		}else{
			strColunas = "[" + strColunas + "]";
		}
		
		//--
		String strColunasVerifDup = "";
		if (propertyNamesVerifDuplAddTable != null){
			for(int i = 0; i < propertyNamesVerifDuplAddTable.length; i++){
				if (!strColunasVerifDup.equalsIgnoreCase("")){
					strColunasVerifDup += ",";
				}
				strColunasVerifDup = strColunasVerifDup + "'" + propertyNamesVerifDuplAddTable[i] + "'";
			}
		}
		if (strColunasVerifDup.equalsIgnoreCase("")){
			strColunasVerifDup = "null";
		}else{
			strColunasVerifDup = "[" + strColunasVerifDup + "]";
		}	
		
		//--	
		String strFuncoesExec = "";
		if (arFuncoesExec != null){
			for(int i = 0; i < arFuncoesExec.length; i++){
				if (!strFuncoesExec.equalsIgnoreCase("")){
					strFuncoesExec += ",";
				}
				strFuncoesExec = strFuncoesExec + arFuncoesExec[i];
			}
		}
		if (strFuncoesExec.equalsIgnoreCase("")){
			strFuncoesExec = "null";
		}else{
			strFuncoesExec = "[" + strFuncoesExec + "]";
		}
		
		//--
		String strFuncaoClick = "null";
		if (funcaoClick != null){
			strFuncaoClick = funcaoClick;
		}
		
		//--
		String strFuncaoVerificacao = "null";
		if (funcaoVerificacao != null){
			strFuncaoVerificacao = funcaoVerificacao;
		}
		
		String objSerializado = CitAjaxWebUtil.serializeObject(obj, false, this.getDocument().getLanguage());
		
		setCommandExecute("HTMLUtils.updateRow('" + this.getId() + "', null, null, ObjectUtils.deserializeObject('" + objSerializado + "'), " + strColunas + ", " + strColunasVerifDup + ", '" + JavaScriptUtil.escapeJavaScript(mensagemDuplicacaoCasoOcorra) + "', " + strFuncoesExec + ", " + strFuncaoClick + ", " + strFuncaoVerificacao + ", " + indexItem + ")");		
	}
	/**
	 * Apaga uma linha da tabela
	 * @param indice
	 */
	public void deleteRow(int indice){
		setCommandExecute("HTMLUtils.deleteRow('" + this.getId() + "', " + indice + ")");
	}
	/**
	 * Apaga todas linhas da tabela
	 *
	 */
	public void deleteAllRows(){
		setCommandExecute("HTMLUtils.deleteAllRows('" + this.getId() + "')");
	}
	/**
	 * Adiciona uma colecao a uma tabela
	 * @param col
	 * @param propertyNamesAddTable
	 * @param propertyNamesVerifDuplAddTable
	 * @param mensagemDuplicacaoCasoOcorra
	 * @param arFuncoesExec
	 * @param funcaoClick
	 * @param funcaoVerificacao
	 * @throws Exception
	 */
	public void addRowsByCollection(Collection col,
			String[] propertyNamesAddTable, 
			String[] propertyNamesVerifDuplAddTable, 
			String mensagemDuplicacaoCasoOcorra,
			String[] arFuncoesExec, 
			String funcaoClick, 
			String funcaoVerificacao) throws Exception{
		//--
		String strColunas = "";
		if (propertyNamesAddTable != null){
			for(int i = 0; i < propertyNamesAddTable.length; i++){
				if (!strColunas.equalsIgnoreCase("")){
					strColunas += ",";
				}
				strColunas = strColunas + "'" + propertyNamesAddTable[i] + "'";
			}
		}
		if (strColunas.equalsIgnoreCase("")){
			strColunas = "null";
		}else{
			strColunas = "[" + strColunas + "]";
		}
		
		//--
		String strColunasVerifDup = "";
		if (propertyNamesVerifDuplAddTable != null){
			for(int i = 0; i < propertyNamesVerifDuplAddTable.length; i++){
				if (!strColunasVerifDup.equalsIgnoreCase("")){
					strColunasVerifDup += ",";
				}
				strColunasVerifDup = strColunasVerifDup + "'" + propertyNamesVerifDuplAddTable[i] + "'";
			}
		}
		if (strColunasVerifDup.equalsIgnoreCase("")){
			strColunasVerifDup = "null";
		}else{
			strColunasVerifDup = "[" + strColunasVerifDup + "]";
		}	
		
		//--	
		String strFuncoesExec = "";
		if (arFuncoesExec != null){
			for(int i = 0; i < arFuncoesExec.length; i++){
				if (!strFuncoesExec.equalsIgnoreCase("")){
					strFuncoesExec += ",";
				}
				strFuncoesExec = strFuncoesExec + arFuncoesExec[i];
			}
		}
		if (strFuncoesExec.equalsIgnoreCase("")){
			strFuncoesExec = "null";
		}else{
			strFuncoesExec = "[" + strFuncoesExec + "]";
		}
		
		//--
		String strFuncaoClick = "null";
		if (funcaoClick != null){
			strFuncaoClick = funcaoClick;
		}
		
		//--
		String strFuncaoVerificacao = "null";
		if (funcaoVerificacao != null){
			strFuncaoVerificacao = funcaoVerificacao;
		}
		
		String strObjsSerializados = CitAjaxWebUtil.serializeObjects(col, false, this.getDocument().getLanguage());
		
		//setCommandExecute("var objetos = ObjectUtils.deserializeCollectionFromString(ObjectUtils.decodificaAspasApostrofe('" + strObjsSerializados + "'))");
		//setCommandExecute("HTMLUtils.addRowsByCollection('" + this.getId() + "', null, null, objetos, " + strColunas + ", " + strColunasVerifDup + ", '" + JavaScriptUtil.escapeJavaScript(mensagemDuplicacaoCasoOcorra) + "', " + strFuncoesExec + ", " + strFuncaoClick + ", " + strFuncaoVerificacao + ")");
		
		//setCommandExecute("var objetos = ObjectUtils.deserializeCollectionFromString(ObjectUtils.decodificaAspasApostrofe('" + strObjsSerializados + "'))");
		setCommandExecute("HTMLUtils.addRowsByCollection('" + this.getId() + "', null, null, ObjectUtils.deserializeCollectionFromString(ObjectUtils.decodificaAspasApostrofe('" + strObjsSerializados + "')), " + strColunas + ", " + strColunasVerifDup + ", '" + JavaScriptUtil.escapeJavaScript(mensagemDuplicacaoCasoOcorra) + "', " + strFuncoesExec + ", " + strFuncaoClick + ", " + strFuncaoVerificacao + ")");		
	}
	
	public void applyStyleClassInAllCells(String classNameParm){
		setCommandExecute("HTMLUtils.applyStyleClassInAllCells('" + this.getId() + "','" + classNameParm + "')");
	}
}
