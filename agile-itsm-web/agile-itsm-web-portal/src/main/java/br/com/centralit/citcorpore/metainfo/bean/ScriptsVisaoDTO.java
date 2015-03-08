package br.com.centralit.citcorpore.metainfo.bean;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ScriptsVisaoDTO implements IDto {
	public static ScriptEventDTO SCRIPT_LOAD = new ScriptEventDTO("LOAD", "visaoAdm.aoCarregarTela");
	public static ScriptEventDTO SCRIPT_VALIDADE = new ScriptEventDTO("VALIDADE", "itemRequisicaoProduto.validacao");
	public static ScriptEventDTO SCRIPT_ONCREATE = new ScriptEventDTO("ONCREATE", "visaoAdm.aoInserir");
	public static ScriptEventDTO SCRIPT_AFTERCREATE = new ScriptEventDTO("AFTERCREATE", "visaoAdm.aposInserir");
	public static ScriptEventDTO SCRIPT_ONUPDATE = new ScriptEventDTO("ONUPDATE", "visaoAdm.aoAtualizar");
	public static ScriptEventDTO SCRIPT_AFTERUPDATE = new ScriptEventDTO("AFTERUPDATE", "visaoAdm.aposAtualizar");
	public static ScriptEventDTO SCRIPT_ONDELETE = new ScriptEventDTO("ONDELETE", "visaoAdm.aoApagar");
	public static ScriptEventDTO SCRIPT_ONRESTORE = new ScriptEventDTO("ONRESTORE", "visaoAdm.aoRecuperar");
	public static ScriptEventDTO SCRIPT_ONSQLWHERESEARCH = new ScriptEventDTO("ONSQLWHERESEARCH", "visaoAdm.aoPrepararWhereBusca");
	public static ScriptEventDTO SCRIPT_ONSQLSEARCH = new ScriptEventDTO("ONSQLSEARCH", "visaoAdm.aoPrepararSQLBusca");
	
	public static String SCRIPT_EXECUTE_SERVER = "S";
	public static String SCRIPT_EXECUTE_CLIENT = "C";
	
	public static String JAVASCRIPT = "JAVASCRIPT";
	
	public static Collection<ScriptEventDTO> colScriptEvents = new ArrayList<ScriptEventDTO>();
	
	static {
		colScriptEvents.add(SCRIPT_LOAD);
		colScriptEvents.add(SCRIPT_VALIDADE);
		colScriptEvents.add(SCRIPT_ONCREATE);
		colScriptEvents.add(SCRIPT_AFTERCREATE);
		colScriptEvents.add(SCRIPT_ONUPDATE);
		colScriptEvents.add(SCRIPT_AFTERUPDATE);
		colScriptEvents.add(SCRIPT_ONDELETE);
		colScriptEvents.add(SCRIPT_ONRESTORE);
		colScriptEvents.add(SCRIPT_ONSQLWHERESEARCH);
		colScriptEvents.add(SCRIPT_ONSQLSEARCH);
	}
	
	private Integer idScriptsVisao;
	private Integer idVisao;
	private String typeExecute;
	private String scryptType;
	private String script;
	private String scriptLanguage;

	public Integer getIdScriptsVisao(){
		return this.idScriptsVisao;
	}
	public void setIdScriptsVisao(Integer parm){
		this.idScriptsVisao = parm;
	}

	public Integer getIdVisao(){
		return this.idVisao;
	}
	public void setIdVisao(Integer parm){
		this.idVisao = parm;
	}

	public String getTypeExecute(){
		return this.typeExecute;
	}
	public void setTypeExecute(String parm){
		this.typeExecute = parm;
	}

	public String getScryptType(){
		return this.scryptType;
	}
	public void setScryptType(String parm){
		this.scryptType = parm;
	}

	public String getScript(){
		return this.script;
	}
	public void setScript(String parm){
		this.script = parm;
	}
	public String getScriptLanguage() {
		return scriptLanguage;
	}
	public void setScriptLanguage(String scriptLanguage) {
		this.scriptLanguage = scriptLanguage;
	}

}
