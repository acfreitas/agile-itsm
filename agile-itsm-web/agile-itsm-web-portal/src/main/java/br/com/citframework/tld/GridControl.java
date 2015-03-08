package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class GridControl extends BodyTagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7469224032227151783L;
	private String name;
	private String width;
	private String height;
	private String internalWidth;
	private String columnTitles;
	private String columnReferences;
	private String columnWidths;
	private String columnTypes;
	private String collectionCombos;
	private String collection;
	private String formName;
	private String numLinhas;
	
	public int doStartTag() throws JspException {
		StringBuilder strBuff = new StringBuilder();
		String[] titulos;
		String[] widths;
		String[] types;
		int i;
		String valorDefault = "&nbsp;";
		
		strBuff.append("<script>\n");
		strBuff.append("var "+getName()+"_isMozilla = true;\n");
		strBuff.append("var "+getName()+"_isIE = (navigator.userAgent.indexOf('MSIE') != -1);\n");
		strBuff.append("if ("+getName()+"_isIE) "+getName()+"_isMozilla = false;\n");
		strBuff.append("</script>\n");
		
		strBuff.append("<div id='grid"+getName()+"' style='overflow:auto;position:absolute; width:"+getWidth()+"; height:"+getHeight()+"'>\n");
		strBuff.append("    <input type='hidden' name='"+getName()+"controle' id='"+getName()+"controle' disabled>\n");
		strBuff.append("    <input type='hidden' name='"+getName()+"numeroColuna' id='"+getName()+"numeroColuna' disabled>\n");
		strBuff.append("    <input type='hidden' name='"+getName()+"numLinhas' id='"+getName()+"numLinhas' value='"+getNumLinhas()+"'>\n");
		strBuff.append("	<table border=1 width='"+getInternalWidth()+"'>\n");
		strBuff.append("		<tr>\n");
		strBuff.append("			<td align='center'>\n");
		strBuff.append("				<b>Exc?</b>\n");
		strBuff.append("			</td>\n");	

		if (this.getColumnTitles().indexOf(";")>0){
			titulos = this.getColumnTitles().split(";");
			widths = this.getColumnWidths().split(";");
			types = this.getColumnTypes().split(";");
			if (titulos.length != widths.length){
				throw new JspException("Numero de Parametros em [columnTitles] é diferente de [columnWidths]");
			}
			if (titulos.length != types.length){
				throw new JspException("Numero de Parametros em [columnTitles] é diferente de [columnTypes]");
			}
		}else{
			titulos = new String[1];
			titulos[0] = this.getColumnTitles();
			widths = new String[1];
			widths[0] = this.getColumnWidths();
			types = new String[1];
			types[0] = this.getColumnTypes();	
		}
		
		for(i = 0; i < titulos.length; i++){
			strBuff.append("			<td>\n");
			strBuff.append("				<b>"+titulos[i]+"</b>\n");
			strBuff.append("			</td>\n");	
		}
		strBuff.append("		</tr>\n");
		
		for(i = 0; i < titulos.length; i++){
			strBuff.append("		<input type='hidden' name='"+getName()+"columnType_"+(i+1)+"' id='"+getName()+"columnType_"+(i+1)+"' disabled value='"+types[i]+"'>\n");
		}
		int numLinhasAux = Integer.parseInt("0" + getNumLinhas());
		for(int x =0; x < numLinhasAux; x++){
			strBuff.append("	<tr id='"+getName()+"tr_"+x+"'>\n");
			strBuff.append("		<td align='center' id='"+getName()+"td0_excluir' style='cursor:hand' onclick=\""+getName()+"excluir('"+getName()+"tr_"+x+"')\">X</td>\n");
			for(int j=0; j < titulos.length; j++){
				if (types[j].equalsIgnoreCase("COMBO")){
					strBuff.append("<input type='hidden' name='"+getName()+"gridSelect_"+getName()+"td"+(j+1)+"_"+x+"' id='"+getName()+"gridSelect_"+getName()+"td"+(j+1)+"_"+x+"' disabled>\n");
				}
				strBuff.append("		<td id='"+getName()+"td"+(j+1)+"_"+x+"' onclick=\""+getName()+"edita(this, '"+types[j]+"', '"+getName()+"tr_"+x+"', "+(j+1)+")\" width='"+widths[j]+"'>"+valorDefault+"</td>\n");
			}
			strBuff.append("	</tr>\n");	
		}
		strBuff.append("</table>\n");	
			
		strBuff.append("	<div id='"+getName()+"divTexto' style='display:none;position:absolute'>\n");
		strBuff.append("		<input type='text' name='"+getName()+"campoTexto' id='"+getName()+"campoTexto' size='100' onkeyup=\""+getName()+"setValorCelula('TEXT');\">\n");
		strBuff.append("	</div>\n");
		strBuff.append("	<div id='"+getName()+"divData' style='display:none;position:absolute'>\n");
		strBuff.append("		<input type='text' name='"+getName()+"campoData' id='"+getName()+"campoData' size='10' maxlength='10' onkeypress=\"return formataCampo(document."+getFormName()+", '"+getName()+"campoData', '99/99/9999', event);\" onkeyup=\""+getName()+"setValorCelula('DATE');\">\n");
		strBuff.append("	</div>\n");
		strBuff.append("	<div id='"+getName()+"divNumber' style='display:none;position:absolute'>\n");  
		strBuff.append("		<input type='text' name='"+getName()+"campoNumber' id='"+getName()+"campoNumber' size='10' onkeypress=\"return bloqueia_nao_numerico(event)\" onkeyup=\""+getName()+"setValorCelula('NUMBER');\">\n");
		strBuff.append("	</div>\n");
		strBuff.append("	<div id='"+getName()+"divMoney' style='display:none;position:absolute'>\n");  
		strBuff.append("		<input type='text' name='"+getName()+"campoMoney' id='"+getName()+"campoMoney' size='10' onkeypress=\"bloqueia_nao_numerico_curr(this.value);\" onkeyup=\""+getName()+"setValorCelula('MONEY');\">\n");
		strBuff.append("	</div>\n");

		for(int j=0; j < titulos.length; j++){
			if (types[j].equalsIgnoreCase("COMBO")){
				strBuff.append("	<div id='"+getName()+"divSelect"+(j+1)+"' style='display:none;position:absolute'>\n");
				strBuff.append("	<select name='"+getName()+"campoSelect"+(j+1)+"' id='"+getName()+"campoSelect"+(j+1)+"' onchange=\""+getName()+"setValorCelula('COMBO');\">\n");
				strBuff.append("		<option value='1'>Select1</option>\n");
				strBuff.append("		<option value='2'>Select2</option>\n");
				strBuff.append("		<option value='3'>Select3</option>\n");
				strBuff.append("	</select>\n");
				strBuff.append("	</div>\n");		
			}
		}
		strBuff.append("\n");
		strBuff.append("<script>\n");
		strBuff.append("	function "+getName()+"edita(obj, tipo, tr, numColuna){\n");
		strBuff.append("		var div = '';\n");
		strBuff.append("		var campo = '';\n");
		strBuff.append("		var trObj = document.getElementById(tr);\n");
		strBuff.append("		//Verifica se esta excluida a linha\n");
		strBuff.append("		if (trObj.bgColor == '#FF0000' || trObj.bgColor == '#ff0000'){\n");
		strBuff.append("			return;\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'TEXT'){\n");
		strBuff.append("			div = '"+getName()+"divTexto';\n");
		strBuff.append("			campo = '"+getName()+"campoTexto';\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'COMBO'){\n");
		strBuff.append("			div = '"+getName()+"divSelect'+numColuna;\n");
		strBuff.append("			campo = '"+getName()+"campoSelect'+numColuna;\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'DATE'){\n");
		strBuff.append("			div = '"+getName()+"divData';\n");
		strBuff.append("			campo = '"+getName()+"campoData';\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'NUMBER'){\n");
		strBuff.append("			div = '"+getName()+"divNumber';\n");
		strBuff.append("			campo = '"+getName()+"campoNumber';\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'MONEY'){\n");
		strBuff.append("			div = '"+getName()+"divMoney';\n");
		strBuff.append("			campo = '"+getName()+"campoMoney';\n");
		strBuff.append("		}\n");
		strBuff.append("		document.getElementById('"+getName()+"divTexto').style.display='none';\n");
		for(int j=0; j < titulos.length; j++){
			if (types[j].equalsIgnoreCase("COMBO")){
				strBuff.append("		document.getElementById('"+getName()+"divSelect"+(j+1)+"').style.display='none';\n");
			}
		}
		strBuff.append("		document.getElementById('"+getName()+"divData').style.display='none';\n");
		strBuff.append("		document.getElementById('"+getName()+"divNumber').style.display='none';\n");
		strBuff.append("		document.getElementById('"+getName()+"divMoney').style.display='none';\n");
									   					   					
		strBuff.append("		document.getElementById(div).style.left = obj.offsetLeft;\n");
		strBuff.append("		document.getElementById(div).style.top = obj.offsetTop;\n");
		strBuff.append("		document.getElementById(div).style.width = obj.offsetWidth;\n");
		strBuff.append("		document.getElementById(div).style.width = obj.offsetWidth;\n");
		strBuff.append("		campo = document.getElementById(campo);\n");
		strBuff.append("		campo.style.width = obj.offsetWidth;\n");		
		strBuff.append("		if (tipo == 'COMBO'){\n");
		strBuff.append("			var campoHidSelect = '"+getName()+"gridSelect_' + obj.id;\n");	   							
		strBuff.append("			campoHidSelect = document.getElementById(campoHidSelect);\n");
		strBuff.append("			campo.selectedIndex = "+getName()+"pegaIndiceComboGrid(campo, campoHidSelect.value);\n");
		strBuff.append("		}else{\n");
		strBuff.append("			campo.value = "+getName()+"pegaValorTexto(obj);\n");
		strBuff.append("		}\n");
		strBuff.append("		document."+getFormName()+"."+getName()+"controle.value = obj.id;\n");
		strBuff.append("		document."+getFormName()+"."+getName()+"numeroColuna.value = numColuna;\n");
		strBuff.append("		document.getElementById(div).style.display='block';\n");
		strBuff.append("		campo.focus();\n");
		strBuff.append("		if (tipo == 'DATE'){\n");
		strBuff.append("			campo.select();\n");
		strBuff.append("		}\n");
		strBuff.append("	}\n");
		
		strBuff.append("	function "+getName()+"setValorCelula(tipo){\n");
		strBuff.append("		var obj = document.getElementById(document."+getFormName()+"."+getName()+"controle.value);\n");
		strBuff.append("		var objNumColuna = document."+getFormName()+"."+getName()+"numeroColuna;\n");
		strBuff.append("		if (tipo == 'TEXT'){\n");
		strBuff.append("			"+getName()+"setaValorTexto(obj, document."+getFormName()+"."+getName()+"campoTexto.value);\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'COMBO'){\n");
		strBuff.append("			var campoSelect = '"+getName()+"campoSelect'+objNumColuna.value;\n");
		strBuff.append("			campoSelect = document.getElementById(campoSelect);\n");
		strBuff.append("			\n");
		strBuff.append("			"+getName()+"setaValorTexto(obj, campoSelect.options[campoSelect.selectedIndex].text);\n");
		strBuff.append("			var campo = '"+getName()+"gridSelect_' + obj.id;\n");
		strBuff.append("			campo = document.getElementById(campo);\n");
		strBuff.append("			campo.value = campoSelect.options[campoSelect.selectedIndex].value;\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'DATE'){\n");
		strBuff.append("			"+getName()+"setaValorTexto(obj, document."+getFormName()+"."+getName()+"campoData.value);\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'NUMBER'){\n");
		strBuff.append("			"+getName()+"setaValorTexto(obj, document."+getFormName()+"."+getName()+"campoNumber.value);\n");
		strBuff.append("		}\n");
		strBuff.append("		if (tipo == 'MONEY'){\n");
		strBuff.append("			"+getName()+"setaValorTexto(obj, document."+getFormName()+"."+getName()+"campoMoney.value);\n");
		strBuff.append("		}\n");
		strBuff.append("	}\n");
		
		strBuff.append("	function "+getName()+"_getResultado(){\n");
		strBuff.append("		var obj;\n");
		strBuff.append("		var result = '';\n");
		strBuff.append("		var trObj;\n");
		strBuff.append("		var campo;\n");
		strBuff.append("		var coluna;\n");
		strBuff.append("		var idUltLinhaComValor = "+getName()+"getUltLinhaComValor();\n");
		strBuff.append("		for (var i = 0; i <= idUltLinhaComValor; i++){\n");
		strBuff.append("			trObj = document.getElementById('"+getName()+"tr_'+i);\n");
		strBuff.append("			if (trObj.bgColor == '#FF0000' || trObj.bgColor == '#ff0000'){\n");
		strBuff.append("				continue;\n");
		strBuff.append("			}\n");	   					
		strBuff.append("			linha = '';\n");
		strBuff.append("			for (var j = 1; j <= 6; j++){\n");
		strBuff.append("				td = '"+getName()+"td' + j + '_' + i;\n");
		strBuff.append("				coluna = '"+getName()+"columnType_' + j;\n");
		strBuff.append("				coluna = document.getElementById(coluna);\n");
							
		strBuff.append("				if (coluna.value == 'COMBO'){\n");
		strBuff.append("					campo = '"+getName()+"gridSelect_' + td;\n");
		strBuff.append("					campo = document.getElementById(campo);\n");
		strBuff.append("					result = result + campo.value + '##';\n");
		strBuff.append("				}else{\n");
		strBuff.append("					obj = document.getElementById(td);\n");
		strBuff.append("					result = result + "+getName()+"pegaValorTexto(obj) + '##';\n");
		strBuff.append("				}\n");
		strBuff.append("			}\n");
		strBuff.append("			result = result + '||';\n");
		strBuff.append("		}\n");
		strBuff.append("		alert(result);\n");
		strBuff.append("	}\n");

		strBuff.append("	function "+getName()+"getUltLinhaComValor(){\n");
		strBuff.append("		var linha = '';\n");
		strBuff.append("		var obj;\n");
		strBuff.append("		var idUltLinhaComValor = 0;\n");
		strBuff.append("		for (var i = 0; i < "+getNumLinhas()+"-1; i++){\n");
		strBuff.append("			linha = '';\n");
		strBuff.append("			for (var j = 1; j <= 6; j++){\n");
		strBuff.append("				td = '"+getName()+"td' + j + '_' + i;\n");
		strBuff.append("				obj = document.getElementById(td);\n");
		strBuff.append("				linha = linha + "+getName()+"pegaValorTexto(obj);\n");
		strBuff.append("			}\n");
		strBuff.append("			if (verifica_branco(linha)){\n");
		strBuff.append("				idUltLinhaComValor = idUltLinhaComValor + 1;\n");
		strBuff.append("			}\n");
		strBuff.append("		}\n");
		strBuff.append("		return idUltLinhaComValor;\n");
		strBuff.append("	}\n");
		
		strBuff.append("	function "+getName()+"excluir(tr){\n");
		strBuff.append("		var trObj = document.getElementById(tr);\n");
		strBuff.append("		trObj.bgColor = '#FF0000';\n");
		strBuff.append("	}\n");
		strBuff.append("	function "+getName()+"pegaIndiceComboGrid(combo, valor){\n");
		strBuff.append("		for(var i = 0; i < combo.options.length; i++){\n");
		strBuff.append("			if (combo.options[i].value == valor){\n");
		strBuff.append("				return i;\n");
		strBuff.append("			}\n");
		strBuff.append("		}\n");
		strBuff.append("		return -1;\n");
		strBuff.append("	}\n");
		strBuff.append("	function "+getName()+"pegaValorTexto(obj){\n");
		strBuff.append("		if ("+getName()+"_isMozilla){\n");
		strBuff.append("			return obj.textContent;");
		strBuff.append("		}else{\n");
		strBuff.append("			return obj.innerText;");
		strBuff.append("		}\n");
		strBuff.append("	}\n");
		strBuff.append("	function "+getName()+"setaValorTexto(obj,valor){\n");
		strBuff.append("		if ("+getName()+"_isMozilla){\n");
		strBuff.append("			obj.textContent = valor;");
		strBuff.append("		}else{\n");
		strBuff.append("			obj.innerText = valor;");
		strBuff.append("		}\n");
		strBuff.append("	}\n");
		
		strBuff.append("</script>\n");
		strBuff.append("</div>\n");		
		
		try {
			pageContext.getOut().println(strBuff.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}		
		return SKIP_BODY;
	}
	
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getCollectionCombos() {
		return collectionCombos;
	}
	public void setCollectionCombos(String collectionCombos) {
		this.collectionCombos = collectionCombos;
	}
	public String getColumnReferences() {
		return columnReferences;
	}
	public void setColumnReferences(String columnReferences) {
		this.columnReferences = columnReferences;
	}
	public String getColumnTitles() {
		return columnTitles;
	}
	public void setColumnTitles(String columnTitles) {
		this.columnTitles = columnTitles;
	}
	public String getColumnTypes() {
		return columnTypes;
	}
	public void setColumnTypes(String columnTypes) {
		this.columnTypes = columnTypes;
	}
	public String getColumnWidths() {
		return columnWidths;
	}
	public void setColumnWidths(String columnWidths) {
		this.columnWidths = columnWidths;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getInternalWidth() {
		return internalWidth;
	}
	public void setInternalWidth(String internalWidth) {
		this.internalWidth = internalWidth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getNumLinhas() {
		return numLinhas;
	}

	public void setNumLinhas(String numLinhas) {
		this.numLinhas = numLinhas;
	}
}
