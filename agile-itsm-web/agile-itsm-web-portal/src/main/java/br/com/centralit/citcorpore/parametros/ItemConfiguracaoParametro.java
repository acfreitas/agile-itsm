package br.com.centralit.citcorpore.parametros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.util.UtilStrings;

public class ItemConfiguracaoParametro implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020072954654596095L;
	
	public static final String SIM_NAO = "SIM_NAO";
	public static final String COMBO = "COMBO";
	public static final String TEXT = "TEXT";
	public static final String TEXTAREA = "TEXTAREA";
	
	private String name;
	private String grupoName;
	private String grupoDescription;
	private String modulo;
	private String description;
	private String valorDefault;
	private String type;
	private String size;
	
	private Collection colItens;
	
	public ItemConfiguracaoParametro(){
		colItens = new ArrayList();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValorDefault() {
		return valorDefault;
	}
	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getGrupoDescription() {
		return grupoDescription;
	}
	public void setGrupoDescription(String grupoDescription) {
		this.grupoDescription = grupoDescription;
	}
	public String getGrupoName() {
		return grupoName;
	}
	public void setGrupoName(String grupoName) {
		this.grupoName = grupoName;
	}
	
	public String renderField(HttpServletRequest request){
		if (this.getType() == null){
			return "";
		}
		String valor = getValueFromRequest(request, "parametro#" + this.getModulo() + "#" + this.getName());
		if (valor == null){
			valor = "";
		}
		if (this.getType().equalsIgnoreCase(SIM_NAO)){
		    if(!UtilStrings.isNotVazio(valor)) valor = valorDefault;
			String strCheckedSim = "";
			String strCheckedNao = "";
			if (valor.equalsIgnoreCase("S")){
				strCheckedSim = " checked ";
			}
			if (valor.equalsIgnoreCase("N")){
				strCheckedNao = " checked ";
			}
			return "<input type='radio' name='parametro#" + this.getModulo() + "#" + this.getName() + "' value='S' " + strCheckedSim + "/>Sim " +
			"<input type='radio' name='parametro#" + this.getModulo() + "#" + this.getName() + "' value='N' " + strCheckedNao + "/>Não ";
		}
		if (this.getType().equalsIgnoreCase(COMBO)){
			String str = "<select name='parametro#" + this.getModulo() + "#" + this.getName() + "'>";
			if (this.getColItens() != null && this.getColItens().size() > 0){
				for(Iterator it = this.getColItens().iterator(); it.hasNext();){
					ItemComboParametro itemCombo = (ItemComboParametro)it.next();
					String strChecked = "";
					if (valor.equalsIgnoreCase(itemCombo.getValue())){
						strChecked = " selected ";
					}
					str += "<option value='" + itemCombo.getValue() + "' "+ strChecked +">" + itemCombo.getDescription() + "</option>";
				}
			}
			str += "</select>";
			return str;
		}
		if (this.getType().equalsIgnoreCase(TEXT)){
			return "<input type='text' name='parametro#" + this.getModulo() + "#" + this.getName() + "' size='" + this.getSize() + "' value='" + valor + "'/>";
		}
		if (this.getType().equalsIgnoreCase(TEXTAREA)){
            return "<textarea rows=\"4\" cols=\"80\" name='parametro#" + this.getModulo() + "#" + this.getName() + "'>" + valor + "</textarea>";
        }
		return "";
	}
	private String getValueFromRequest(HttpServletRequest request, String nameField){
		String valor = (String) request.getAttribute(nameField);
		if (valor == null){
			valor = "";
		}
		return valor;
	}
	public Collection getColItens() {
		return colItens;
	}
	public void setColItens(Collection colItens) {
		this.colItens = colItens;
	}
}
