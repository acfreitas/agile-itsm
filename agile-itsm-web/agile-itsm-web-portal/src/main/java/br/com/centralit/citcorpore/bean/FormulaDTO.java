package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class FormulaDTO implements IDto {
	public static String FORMULA_INVENTORY_PROCESS_SAVE = "INVENTORY_PROCESS_SAVE";
	public static String FORMULA_INVENTORY_PROCESS_BEFORE_CAPTURE = "INVENTORY_PROCESS_BEFORE_CAPTURE";
	
	private Integer idFormula;
	private String identificador;
	private String nome;
	private String conteudo;
	private java.sql.Date datacriacao;

	public Integer getIdFormula(){
		return this.idFormula;
	}
	public void setIdFormula(Integer parm){
		this.idFormula = parm;
	}

	public String getIdentificador(){
		return this.identificador;
	}
	public void setIdentificador(String parm){
		this.identificador = parm;
	}

	public String getNome(){
		return this.nome;
	}
	public void setNome(String parm){
		this.nome = parm;
	}

	public String getConteudo(){
		return this.conteudo;
	}
	public void setConteudo(String parm){
		this.conteudo = parm;
	}

	public java.sql.Date getDatacriacao(){
		return this.datacriacao;
	}
	public void setDatacriacao(java.sql.Date parm){
		this.datacriacao = parm;
	}

}
