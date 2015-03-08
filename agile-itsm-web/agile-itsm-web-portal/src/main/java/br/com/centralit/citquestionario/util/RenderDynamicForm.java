package br.com.centralit.citquestionario.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citquestionario.bean.ExecutaSQLDTO;
import br.com.centralit.citquestionario.integracao.ExecutaSQLDao;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class RenderDynamicForm {
	private static HashMap hashDynaFormsLoad = null;
	/**
	 * Renderiza o form dinamico.
	 * @param nameDynaForm
	 * @param type - XML ou DB
	 * @return
	 */
	public static String render(String nameDynaForm, String type, Integer idSolicitacaoServico, Integer idProfissinal){
		if (hashDynaFormsLoad == null){
			hashDynaFormsLoad = new HashMap();
		}
		if (type == null){
			type = "XML";
		}
		DynamicFormInfoBean dynaForm = null;
		if (type.equalsIgnoreCase("XML")){
			dynaForm = renderByXML(nameDynaForm);
		}
		if (type.equalsIgnoreCase("DB")){
			//Implementacao futura.
		}

		if (dynaForm != null){
			if (dynaForm.getType().equalsIgnoreCase("TABLE_IN_COLS")){
				return renderTableInCols(dynaForm, idSolicitacaoServico, idProfissinal);
			}
			if (dynaForm.getType().equalsIgnoreCase("NONE")){
				return renderNone(dynaForm, idSolicitacaoServico, idProfissinal);
			}
		}
		return "";
	}
	/**
	 * Imprime o form dinamico.
	 * @param nameDynaForm
	 * @param type - XML ou DB
	 * @return
	 */
	public static String print(String nameDynaForm, String type, Integer idSolicitacaoServico, Integer idProfissinal){
		if (hashDynaFormsLoad == null){
			hashDynaFormsLoad = new HashMap();
		}
		if (type == null){
			type = "XML";
		}
		DynamicFormInfoBean dynaForm = null;
		if (type.equalsIgnoreCase("XML")){
			dynaForm = renderByXML(nameDynaForm);
		}
		if (type.equalsIgnoreCase("DB")){
			//Implementacao futura.
		}

		if (dynaForm != null){
			return print(dynaForm, idSolicitacaoServico, idProfissinal);
		}
		return "";
	}
	public static DynamicFormInfoBean getDynamicFormInfoBean(String nameDynaForm){
		if (hashDynaFormsLoad == null){
			return null;
		}
		if (hashDynaFormsLoad.containsKey(nameDynaForm)){
			DynamicFormInfoBean dynaForm = (DynamicFormInfoBean)hashDynaFormsLoad.get(nameDynaForm);
			if (dynaForm != null){
				return dynaForm;
			}
		}
		return null;
	}
	/**
	 * Renderiza pelo XML.
	 * @param nameDynaForm
	 * @return
	 */
	private static DynamicFormInfoBean renderByXML(String nameDynaForm){
		if (hashDynaFormsLoad.containsKey(nameDynaForm)){
			DynamicFormInfoBean dynaForm = (DynamicFormInfoBean)hashDynaFormsLoad.get(nameDynaForm);
			if (dynaForm != null){
				return dynaForm;
			}
		}

		InputStream iisDynaForm = RenderDynamicForm.class.getClassLoader().getResourceAsStream(nameDynaForm + ".xml");
		if (iisDynaForm == null){
			try {
				iisDynaForm = new FileInputStream(CitAjaxUtil.CAMINHO_REAL_APP + "/dynamicForms/" + nameDynaForm + ".xml");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (iisDynaForm == null){
			iisDynaForm = ClassLoader.getSystemResourceAsStream(nameDynaForm + ".xml");
		}
		if (iisDynaForm == null){
			iisDynaForm = ClassLoader.getSystemClassLoader().getResourceAsStream(nameDynaForm + ".xml");
		}

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (iisDynaForm == null){
            	throw new Exception("ARQUIVO DYNAMICFORM: " + nameDynaForm + ".xml   NAO ENCONTRADO!!!!!!!!!");
            }
            doc = builder.parse(iisDynaForm);
            DynamicFormInfoBean dFormBean = load(doc, nameDynaForm);
            dFormBean.setClasseForm(prepareFormAssociate(dFormBean.getClazz()));
            hashDynaFormsLoad.put(nameDynaForm, dFormBean); //Coloca no HASH para nao ser mais necessario realizar parser.
            return dFormBean;
        } catch (Exception e) {
            e.printStackTrace();
        }

		return null;
	}

	private static DynamicFormInfoBean load(Document doc, String nameDynaForm){
		if (doc == null) return null;
		DynamicFormInfoBean dynamicFormInfoBean = null;
		Node noRoot = doc.getChildNodes().item(0);
		if(noRoot != null){
            NamedNodeMap map = noRoot.getAttributes();

            dynamicFormInfoBean = new DynamicFormInfoBean();
            dynamicFormInfoBean.setName(nameDynaForm);
            dynamicFormInfoBean.setType(getItemValue(map, "type"));
            dynamicFormInfoBean.setClazz(getItemValue(map, "class"));
            dynamicFormInfoBean.setDescription(getItemValue(map, "description"));

            dynamicFormInfoBean = getElements(dynamicFormInfoBean, noRoot);
		}
		return dynamicFormInfoBean;
	}

	private static DynamicFormInfoBean getElements(DynamicFormInfoBean dynamicFormInfoBean, Node noItem){
		if (noItem == null) return dynamicFormInfoBean;
		Collection colElementos = new ArrayList();
		Collection colJavaScripts = new ArrayList();
		if (noItem.getChildNodes() != null){
			for (int i = 0; i < noItem.getChildNodes().getLength(); i++){
				Node noListagemItem = noItem.getChildNodes().item(i);
				if(noListagemItem.getNodeName().equals("#text")) continue;

				if (noListagemItem.getNodeName().equals("html")) { //HTML
					DynamicFormElementBean dynamicFormElementBean = getElemento(dynamicFormInfoBean, noListagemItem);
					if (dynamicFormElementBean != null){
						colElementos.add(dynamicFormElementBean);
					}
				}
				if (noListagemItem.getNodeName().equals("javascript")) { //JAVASCRIPT
					colJavaScripts = getJavaScripts(dynamicFormInfoBean, noListagemItem);
				}
				if (noListagemItem.getNodeName().equals("column")) { //COLUNA DA TABELA
					DynamicFormElementBean dynamicFormElementBean = getElemento(dynamicFormInfoBean, noListagemItem);
					if (dynamicFormElementBean != null){
						colElementos.add(dynamicFormElementBean);
					}
				}
			}
		}
		dynamicFormInfoBean.setColElements(colElementos);
		dynamicFormInfoBean.setColJavaScripts(colJavaScripts);
		return dynamicFormInfoBean;
	}
	private static DynamicFormElementBean getElemento(DynamicFormInfoBean dynamicFormInfoBean, Node noListagemItem){
		if (noListagemItem.getNodeName().equals("html")) { //HTML
			DynamicFormElementBean dynamicFormElementBean = new DynamicFormElementBean();
			dynamicFormElementBean.setType("html");
			dynamicFormElementBean.setHtmlData(noListagemItem.getTextContent());
			return dynamicFormElementBean;
		}
		if (noListagemItem.getNodeName().equals("column")) { //COLUNA DA TABELA
			DynamicFormElementBean dynamicFormElementBean = new DynamicFormElementBean();
			dynamicFormElementBean.setType("column");

			NamedNodeMap map = noListagemItem.getAttributes();

			dynamicFormElementBean.setTitle(getItemValue(map, "title"));
			dynamicFormElementBean.setName(getItemValue(map, "name"));
			dynamicFormElementBean.setColumnType(getItemValue(map, "type"));
			dynamicFormElementBean.setFormat(getItemValue(map, "format"));
			dynamicFormElementBean.setValid(getItemValue(map, "valid"));

			dynamicFormElementBean.setOnkeydown(getItemValue(map, "onkeydown"));
			dynamicFormElementBean.setOnkeypress(getItemValue(map, "onkeypress"));
			dynamicFormElementBean.setOnkeyup(getItemValue(map, "onkeyup"));
			dynamicFormElementBean.setOnfocus(getItemValue(map, "onfocus"));
			dynamicFormElementBean.setOnblur(getItemValue(map, "onblur"));
			dynamicFormElementBean.setOnclick(getItemValue(map, "onclick"));

			if (dynamicFormElementBean.getColumnType() != null){
				//RADIO, CHECKBOX ou SELECT
				if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("radio") ||
						dynamicFormElementBean.getColumnType().equalsIgnoreCase("checkbox") ||
						dynamicFormElementBean.getColumnType().equalsIgnoreCase("select")){
					String nomeItem = dynamicFormElementBean.getColumnType();
					Collection colSubItens = new ArrayList();
					for (int c = 0; c < noListagemItem.getChildNodes().getLength(); c++){
            	        Node noSubItem = noListagemItem.getChildNodes().item(c);
            	        if(noSubItem.getNodeName().equals("#text")) continue;
            	        if (noSubItem.getNodeName().equals("optionSQL")) {
            	        	ExecutaSQLDao executaSqlDao = new ExecutaSQLDao();
            	        	try {
								Collection col = executaSqlDao.executaSQL(noSubItem.getTextContent());
								if (col != null){
									for(Iterator it = col.iterator(); it.hasNext();){
										ExecutaSQLDTO executaSQLDTO = (ExecutaSQLDTO)it.next();
			            	        	DynamicFormElementBean dynamicFormSubElementBean = new DynamicFormElementBean();
			            	        	dynamicFormSubElementBean.setType("option");
			            	        	dynamicFormSubElementBean.setValue("" + executaSQLDTO.getValue());
			            	        	dynamicFormSubElementBean.setDescription(executaSQLDTO.getDescription());

			            	        	colSubItens.add(dynamicFormSubElementBean);

									}
								}
							} catch (DOMException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
            	        }
            	        if (noSubItem.getNodeName().equals(nomeItem) || noSubItem.getNodeName().equals("option")) {
            	        	DynamicFormElementBean dynamicFormSubElementBean = new DynamicFormElementBean();
            	        	NamedNodeMap mapSubItem = noSubItem.getAttributes();
            	        	dynamicFormSubElementBean.setType("option");
            	        	dynamicFormSubElementBean.setValue(getItemValue(mapSubItem, "value"));
            	        	dynamicFormSubElementBean.setDescription(getItemValue(mapSubItem, "description"));

            	        	colSubItens.add(dynamicFormSubElementBean);
            	        }
					}
					dynamicFormElementBean.setColSubElements(colSubItens);
				}
				//TEXT
				if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("text")){
					dynamicFormElementBean.setSize(getItemValue(map, "size"));
					dynamicFormElementBean.setMaxlength(getItemValue(map, "maxlength"));
				}
				//TEXTAREA
				if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("textarea")){
					dynamicFormElementBean.setRows(getItemValue(map, "rows"));
					dynamicFormElementBean.setCols(getItemValue(map, "cols"));
				}
				//AGRUPADOR
				if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("group")){
					String nomeItem = dynamicFormElementBean.getColumnType();
					Collection colSubItens = new ArrayList();
					for (int c = 0; c < noListagemItem.getChildNodes().getLength(); c++){
            	        Node noSubItem = noListagemItem.getChildNodes().item(c);
            	        if(noSubItem.getNodeName().equals("#text")) continue;

            	        DynamicFormElementBean dynamicFormSubElementBean = getElemento(dynamicFormInfoBean, noSubItem);
           	        	colSubItens.add(dynamicFormSubElementBean);
					}
					dynamicFormElementBean.setColSubElements(colSubItens);
				}
			}
			return dynamicFormElementBean;
		}
		return null;
	}
	private static Collection getJavaScripts(DynamicFormInfoBean dynamicFormInfoBean, Node noItem){
		if (noItem == null) return null;
		Collection colJavaScripts = new ArrayList();

		if (noItem.getChildNodes() != null){
			for (int i = 0; i < noItem.getChildNodes().getLength(); i++){
				Node noListagemItem = noItem.getChildNodes().item(i);
				if(noListagemItem.getNodeName().equals("#text")) continue;

				DynamicFormJavascriptBean dynamicFormJavascriptBean = new DynamicFormJavascriptBean();
				dynamicFormJavascriptBean.setFunctionName(noListagemItem.getNodeName());
				dynamicFormJavascriptBean.setCorpo(noListagemItem.getTextContent());

				colJavaScripts.add(dynamicFormJavascriptBean);
			}
		}
		return colJavaScripts;
	}

	private static String getItemValue(NamedNodeMap map, String nomeItem){
		Node no = map.getNamedItem(nomeItem);
		if (no == null){
			return null;
		}
		String strValue = no.getNodeValue();
		if (strValue != null){
			strValue = strValue.replaceAll("\\#br\\#", "<br/>");
		}
		return strValue;
	}

	/**
	 * Renderiza no formato que vier de dentro do Ajaxform que trata o assunto
	 * @param dynamicFormInfoBean
	 * @return
	 */
	private static String renderNone(DynamicFormInfoBean dynamicFormInfoBean, Integer idSolicitacaoServico, Integer idProfissinal){
		if (dynamicFormInfoBean == null){
			return "";
		}
		String strBuffer = "";
		if (dynamicFormInfoBean.getClasseForm() != null){
			Object objeto = null;
			try {
				objeto = dynamicFormInfoBean.getClasseForm().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (objeto != null){
				Method mtd = null;
				//--------------------- beforeRestoreDynamicForm
				mtd = CitAjaxReflexao.findMethod("beforeRestoreDynamicForm", objeto);
				if (mtd == null){
					mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onBeforeRestoreDynamicForm", objeto);
				}
				if (mtd != null){
					try {
						String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
						strBuffer += strRetorno;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (dynamicFormInfoBean.getClasseForm() != null){
			Object objeto = null;
			try {
				objeto = dynamicFormInfoBean.getClasseForm().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (objeto != null){
				Method mtd = null;
				//--------------------- restoreDynamicForm
				mtd = CitAjaxReflexao.findMethod("restoreDynamicForm", objeto);
				if (mtd == null){
					mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onRestoreDynamicForm", objeto);
				}
				if (mtd != null){
					try {
						String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
						strBuffer += strRetorno;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (dynamicFormInfoBean.getClasseForm() != null){
			Object objeto = null;
			try {
				objeto = dynamicFormInfoBean.getClasseForm().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (objeto != null){
				Method mtd = null;
				//--------------------- afterRestoreDynamicForm
				mtd = CitAjaxReflexao.findMethod("afterRestoreDynamicForm", objeto);
				if (mtd == null){
					mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onAfterRestoreDynamicForm", objeto);
				}
				if (mtd != null){
					try {
						String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
						strBuffer += strRetorno;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		for(Iterator it = dynamicFormInfoBean.getColElements().iterator(); it.hasNext();){
			DynamicFormElementBean dynamicFormElementBean = (DynamicFormElementBean)it.next();
			if (dynamicFormElementBean.getType().equalsIgnoreCase("html")){ //HTML
				strBuffer += dynamicFormElementBean.getHtmlData();
			}
		}

		return strBuffer;
	}
	/**
	 * Renderiza em forma de tabela em colunas
	 * @param dynamicFormInfoBean
	 * @return
	 */
	private static String renderTableInCols(DynamicFormInfoBean dynamicFormInfoBean, Integer idSolicitacaoServico, Integer idProfissinal){
		if (dynamicFormInfoBean == null){
			return "";
		}
		String strBuffer = "";
		strBuffer += "<script>\n";
		strBuffer += "var " + dynamicFormInfoBean.getName() + "_ULTIMO_SEQUENCIAL = 0;\n";
		if(dynamicFormInfoBean.getColJavaScripts() != null){
			for(Iterator it = dynamicFormInfoBean.getColJavaScripts().iterator(); it.hasNext();){
				DynamicFormJavascriptBean dynamicFormJavascriptBean = (DynamicFormJavascriptBean)it.next();

				if (dynamicFormJavascriptBean.getFunctionName().equalsIgnoreCase("addItem_CallBack")){
					strBuffer += "function " + dynamicFormInfoBean.getName() + "_" + dynamicFormJavascriptBean.getFunctionName() + "(nomeFormDinamico, iQtdeColunas, classe, textoResposta){\n";
				}else{
					strBuffer += "function " + dynamicFormInfoBean.getName() + "_" + dynamicFormJavascriptBean.getFunctionName() + "(){\n";
				}
				strBuffer += dynamicFormJavascriptBean.getCorpo() + "\n";
				strBuffer += "}\n";
			}
		}
		strBuffer += "</script>\n";
		strBuffer += "<fieldset><legend><b>" + dynamicFormInfoBean.getDescription() + "</b></legend>";
		strBuffer += "<div style='display:none' id='div" + dynamicFormInfoBean.getName() + "_Control'>";
		strBuffer += "<input type='hidden' name='DYNAFORM_PRESENTE' value='" + dynamicFormInfoBean.getName() + "'/>";
		strBuffer += "<input type='hidden' name='" + dynamicFormInfoBean.getName() + "_ULTIMO_SEQUENCIAL_HIDDEN' value='0'/>";
		strBuffer += "</div>";
		if(dynamicFormInfoBean.getColElements() != null){
			Integer i = new Integer(0);
			int qtdeColunas = dynamicFormInfoBean.getColElements().size();
			for(Iterator it = dynamicFormInfoBean.getColElements().iterator(); it.hasNext();){
				DynamicFormElementBean dynamicFormElementBean = (DynamicFormElementBean)it.next();
				if (dynamicFormElementBean.getType().equalsIgnoreCase("column")){ //COLUNA
					i = new Integer(i.intValue() + 1);
					strBuffer += "<div style='display:none' id='div" + dynamicFormInfoBean.getName() + "Field_" + i + "'>";
					strBuffer += renderItem(dynamicFormInfoBean, dynamicFormElementBean, idSolicitacaoServico, idProfissinal, i);
					strBuffer += "</div>\n";
				}
			}

			strBuffer += "<table id='tblAux_" + dynamicFormInfoBean.getName() + "' width='100%'>";
			strBuffer += "<tr>";
			strBuffer += "<td>";
			strBuffer += "<input type='button' name='btnAdd" + dynamicFormInfoBean.getName() + "' value='Adicionar' onclick='adicionarRespostaTabelaFormDinamico(\"" + dynamicFormInfoBean.getName() + "\", " + qtdeColunas + ", \"" + dynamicFormInfoBean.getClazz() + "\", " + dynamicFormInfoBean.getName() + "_addItem_CallBack" + ")'/>";
			strBuffer += "</td>";
			strBuffer += "</tr>";
			strBuffer += "</table>\n";

			if (dynamicFormInfoBean.getClasseForm() != null){
				Object objeto = null;
				try {
					objeto = dynamicFormInfoBean.getClasseForm().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (objeto != null){
					Method mtd = null;
					//--------------------- beforeRestoreDynamicForm
					mtd = CitAjaxReflexao.findMethod("beforeRestoreDynamicForm", objeto);
					if (mtd == null){
						mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onBeforeRestoreDynamicForm", objeto);
					}
					if (mtd != null){
						try {
							String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
							strBuffer += strRetorno;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}


			strBuffer += "<table id='tbl_" + dynamicFormInfoBean.getName() + "' width='100%' border = '1' cellpadding='0' cellspacing='0'>";
			strBuffer += "<tr>";
			strBuffer += "<td bgcolor='#20b2aa'>&nbsp;</td>";
			for(Iterator it = dynamicFormInfoBean.getColElements().iterator(); it.hasNext();){
				DynamicFormElementBean dynamicFormElementBean = (DynamicFormElementBean)it.next();

				if (dynamicFormElementBean.getType().equalsIgnoreCase("column")){ //COLUNA
					strBuffer += "<td bgcolor='#20b2aa'>" + dynamicFormElementBean.getTitle() + "</td>";
				}
			}
			strBuffer += "</tr>";

			if (dynamicFormInfoBean.getClasseForm() != null){
				Object objeto = null;
				try {
					objeto = dynamicFormInfoBean.getClasseForm().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (objeto != null){
					Method mtd = null;
					//--------------------- restoreDynamicForm
					mtd = CitAjaxReflexao.findMethod("restoreDynamicForm", objeto);
					if (mtd == null){
						mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onRestoreDynamicForm", objeto);
					}
					if (mtd != null){
						try {
							String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
							strBuffer += strRetorno;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}

			strBuffer += "</table>";

			if (dynamicFormInfoBean.getClasseForm() != null){
				Object objeto = null;
				try {
					objeto = dynamicFormInfoBean.getClasseForm().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (objeto != null){
					Method mtd = null;
					//--------------------- afterRestoreDynamicForm
					mtd = CitAjaxReflexao.findMethod("afterRestoreDynamicForm", objeto);
					if (mtd == null){
						mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onAfterRestoreDynamicForm", objeto);
					}
					if (mtd != null){
						try {
							String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
							strBuffer += strRetorno;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}

			for(Iterator it = dynamicFormInfoBean.getColElements().iterator(); it.hasNext();){
				DynamicFormElementBean dynamicFormElementBean = (DynamicFormElementBean)it.next();
				if (dynamicFormElementBean.getType().equalsIgnoreCase("html")){ //HTML
					strBuffer += dynamicFormElementBean.getHtmlData();
				}
			}
		}
		strBuffer += "</fieldset>";

		return strBuffer;
	}

	private static String renderItem(DynamicFormInfoBean dynamicFormInfoBean, DynamicFormElementBean dynamicFormElementBean, Integer idSolicitacaoServico, Integer idProfissinal, Integer i){
		String strBuffer = "";

		String valid = dynamicFormElementBean.getValid();
		String format = dynamicFormElementBean.getFormat();
		String styleClass = "";
		String metodos = "";
		valid = UtilStrings.nullToVazio(valid);
		format = UtilStrings.nullToVazio(format);
		if (!valid.equalsIgnoreCase("") || !format.equalsIgnoreCase("")){
			styleClass += "#class#='";
			if (valid != null && !valid.equalsIgnoreCase("")){
				styleClass += "Valid[" + valid + "] ";
			}
			if (format != null && !format.equalsIgnoreCase("")){
				styleClass += "Format[" + format + "] ";
			}
			styleClass += "' ";
		}
		if (dynamicFormElementBean.getOnblur() != null && !dynamicFormElementBean.getOnblur().equalsIgnoreCase("")){
			metodos += " onblur=\"" + dynamicFormElementBean.getOnblur() + "\" ";
		}
		if (dynamicFormElementBean.getOnclick() != null && !dynamicFormElementBean.getOnclick().equalsIgnoreCase("")){
			metodos += " onclick=\"" + dynamicFormElementBean.getOnclick() + "\" ";
		}
		if (dynamicFormElementBean.getOnfocus() != null && !dynamicFormElementBean.getOnfocus().equalsIgnoreCase("")){
			metodos += " onfocus=\"" + dynamicFormElementBean.getOnfocus() + "\" ";
		}
		if (dynamicFormElementBean.getOnkeydown() != null && !dynamicFormElementBean.getOnkeydown().equalsIgnoreCase("")){
			metodos += " onkeydown=\"" + dynamicFormElementBean.getOnkeydown() + "\" ";
		}
		if (dynamicFormElementBean.getOnkeypress() != null && !dynamicFormElementBean.getOnkeypress().equalsIgnoreCase("")){
			metodos += " onkeypress=\"" + dynamicFormElementBean.getOnkeypress() + "\" ";
		}
		if (dynamicFormElementBean.getOnkeyup() != null && !dynamicFormElementBean.getOnkeyup().equalsIgnoreCase("")){
			metodos += " onkeyup=\"" + dynamicFormElementBean.getOnkeyup() + "\" ";
		}
		if (dynamicFormElementBean.getOnchange() != null && !dynamicFormElementBean.getOnchange().equalsIgnoreCase("")){
			metodos += " onchange=\"" + dynamicFormElementBean.getOnchange() + "\" ";
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("text")){
			String size = dynamicFormElementBean.getSize();
			String maxlength = dynamicFormElementBean.getMaxlength();
			if (size == null){
				size = "20";
			}
			if (maxlength == null){
				maxlength = "255";
			}
			strBuffer += "<input type='text' name='CAMPOFORMDYN_" + dynamicFormInfoBean.getName() + "." + dynamicFormElementBean.getName() + ".SUFIXO' size='" + size + "' maxlength='" + maxlength + "' " + styleClass + " " + metodos + "/>";
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("hidden")){
			strBuffer += "<input type='hidden' name='CAMPOFORMDYN_" + dynamicFormInfoBean.getName() + "." + dynamicFormElementBean.getName() + ".SUFIXO' " + styleClass + " " + metodos + "/>";
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("textarea")){
			String rows = dynamicFormElementBean.getRows();
			String cols = dynamicFormElementBean.getCols();
			if (rows == null){
				rows = "5";
			}
			if (cols == null){
				cols = "70";
			}
			strBuffer += "<textarea name='CAMPOFORMDYN_" + dynamicFormInfoBean.getName() + "." + dynamicFormElementBean.getName() + ".SUFIXO' cols='" + cols + "' rows='" + rows + "' " + styleClass + " " + metodos + "></textarea>";
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("group")){
			if (dynamicFormElementBean.getColSubElements() != null){
				for(Iterator itSub = dynamicFormElementBean.getColSubElements().iterator(); itSub.hasNext();){
					DynamicFormElementBean dynamicFormSubElementBean = (DynamicFormElementBean)itSub.next();
					strBuffer += renderItem(dynamicFormInfoBean, dynamicFormSubElementBean, idSolicitacaoServico, idProfissinal, i);
				}
			}
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("radio")){
			if (dynamicFormElementBean.getColSubElements() != null){
				for(Iterator itSub = dynamicFormElementBean.getColSubElements().iterator(); itSub.hasNext();){
					DynamicFormElementBean dynamicFormSubElementBean = (DynamicFormElementBean)itSub.next();
					strBuffer += "<input type='radio' name='CAMPOFORMDYN_" + dynamicFormInfoBean.getName() + "." + dynamicFormElementBean.getName() + ".SUFIXO' " + styleClass + " value='" + dynamicFormSubElementBean.getValue() + "' " + metodos + "/>" + dynamicFormSubElementBean.getDescription();
				}
			}
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("checkbox")){
			if (dynamicFormElementBean.getColSubElements() != null){
				for(Iterator itSub = dynamicFormElementBean.getColSubElements().iterator(); itSub.hasNext();){
					DynamicFormElementBean dynamicFormSubElementBean = (DynamicFormElementBean)itSub.next();
					strBuffer += "<input type='checkbox' name='CAMPOFORMDYN_" + dynamicFormInfoBean.getName() + "." + dynamicFormElementBean.getName() + ".SUFIXO' " + styleClass + " value='" + dynamicFormSubElementBean.getValue() + "' " + metodos + "/>" + dynamicFormSubElementBean.getDescription();
				}
			}
		}
		if (dynamicFormElementBean.getColumnType().equalsIgnoreCase("select")){
			if (dynamicFormElementBean.getColSubElements() != null){
				String size = dynamicFormElementBean.getSize();
				if (size == null){
					size = "1";
				}
				strBuffer += "<select name='CAMPOFORMDYN_" + dynamicFormInfoBean.getName() + "." + dynamicFormElementBean.getName() + ".SUFIXO' size='" + size + "' " + styleClass + " " + metodos + ">";
				strBuffer += "<option value=''>-- Selecione --</option>";
				for(Iterator itSub = dynamicFormElementBean.getColSubElements().iterator(); itSub.hasNext();){
					DynamicFormElementBean dynamicFormSubElementBean = (DynamicFormElementBean)itSub.next();
					strBuffer += "<option value='" + dynamicFormSubElementBean.getValue() + "'>" + dynamicFormSubElementBean.getDescription() + "</option>";
				}
				strBuffer += "</select>";
			}
		}

		return strBuffer;
	}

	private static Class prepareFormAssociate(String formName){
		Class classe = null;
		boolean bTentarLocalizarForm = true;
		int iCodigoTentativa = 1;
		while(bTentarLocalizarForm){
			try {
				//System.out.println("Form action: " + Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
				if (iCodigoTentativa == 1){
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
				}else{
					if (Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) == null || Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa).trim().equalsIgnoreCase("")){
						classe = null;
						bTentarLocalizarForm = false;
						break;
					}
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) + "." + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
				}
				if (classe != null){
					bTentarLocalizarForm = true;
					break;
				}
			} catch (ClassNotFoundException e) {
				iCodigoTentativa++;
				//throw new Exception("Form não encontrado: " + Util.convertePrimeiraLetra(name, "U"));
			}
		}
		if (classe == null){
			System.out.println("CITQUESTIONARIO -> DYNAMIC FORM -> Form não encontrado: " + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
			return null;
		}
		return classe;
	}

	/**
	 * Imprime no formato que vier de dentro do Ajaxform que trata o assunto
	 * @param dynamicFormInfoBean
	 * @return
	 */
	private static String print(DynamicFormInfoBean dynamicFormInfoBean, Integer idSolicitacaoServico, Integer idProfissinal){
		if (dynamicFormInfoBean == null){
			return "";
		}
		String strBuffer = "";
		if (dynamicFormInfoBean.getClasseForm() != null){
			Object objeto = null;
			try {
				objeto = dynamicFormInfoBean.getClasseForm().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (objeto != null){
				Method mtd = null;
				//--------------------- beforeRestoreDynamicForm
				mtd = CitAjaxReflexao.findMethod("beforePrintDynamicForm", objeto);
				if (mtd == null){
					mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onBeforePrintDynamicForm", objeto);
				}
				if (mtd != null){
					try {
						String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
						strBuffer += strRetorno;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (dynamicFormInfoBean.getClasseForm() != null){
			Object objeto = null;
			try {
				objeto = dynamicFormInfoBean.getClasseForm().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (objeto != null){
				Method mtd = null;
				//--------------------- restoreDynamicForm
				mtd = CitAjaxReflexao.findMethod("printDynamicForm", objeto);
				if (mtd == null){
					mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onPrintDynamicForm", objeto);
				}
				if (mtd != null){
					try {
						String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
						strBuffer += strRetorno;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (dynamicFormInfoBean.getClasseForm() != null){
			Object objeto = null;
			try {
				objeto = dynamicFormInfoBean.getClasseForm().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (objeto != null){
				Method mtd = null;
				//--------------------- afterRestoreDynamicForm
				mtd = CitAjaxReflexao.findMethod("afterPrintDynamicForm", objeto);
				if (mtd == null){
					mtd = CitAjaxReflexao.findMethod(dynamicFormInfoBean.getClazz() + "_onAfterPrintDynamicForm", objeto);
				}
				if (mtd != null){
					try {
						String strRetorno = (String)mtd.invoke(objeto, new Object[] {dynamicFormInfoBean.getName(), idSolicitacaoServico, idProfissinal} );
						strBuffer += strRetorno;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		for(Iterator it = dynamicFormInfoBean.getColElements().iterator(); it.hasNext();){
			DynamicFormElementBean dynamicFormElementBean = (DynamicFormElementBean)it.next();
			if (dynamicFormElementBean.getType().equalsIgnoreCase("htmlPrint")){ //HTML
				strBuffer += dynamicFormElementBean.getHtmlData();
			}
		}

		return strBuffer;
	}
}
