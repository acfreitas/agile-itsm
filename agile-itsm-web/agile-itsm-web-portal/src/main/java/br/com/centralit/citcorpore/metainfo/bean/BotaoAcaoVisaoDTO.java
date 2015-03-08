package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class BotaoAcaoVisaoDTO implements IDto {
	public static String ACAO_GRAVAR = "1";
	public static String ACAO_LIMPAR = "2";
	public static String ACAO_EXCLUIR = "3";
	public static String ACAO_SCRIPT = "4";
	
	private Integer idBotaoAcaoVisao;
	private Integer idVisao;
	private String texto;
	private String acao;
	private String script;
	private String hint;
	private String icone;
	private Integer ordem;

	public Integer getIdBotaoAcaoVisao(){
		return this.idBotaoAcaoVisao;
	}
	public void setIdBotaoAcaoVisao(Integer parm){
		this.idBotaoAcaoVisao = parm;
	}

	public Integer getIdVisao(){
		return this.idVisao;
	}
	public void setIdVisao(Integer parm){
		this.idVisao = parm;
	}

	public String getTexto(){
		return this.texto;
	}
	public void setTexto(String parm){
		this.texto = parm;
	}

	public String getAcao(){
		return this.acao;
	}
	public void setAcao(String parm){
		this.acao = parm;
	}

	public String getScript(){
		return this.script;
	}
	public void setScript(String parm){
		this.script = parm;
	}

	public String getHint(){
		return this.hint;
	}
	public void setHint(String parm){
		this.hint = parm;
	}

	public String getIcone(){
		return this.icone;
	}
	public void setIcone(String parm){
		this.icone = parm;
	}

	public Integer getOrdem(){
		return this.ordem;
	}
	public void setOrdem(Integer parm){
		this.ordem = parm;
	}

}
