package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TemplateImpressaoDTO implements IDto {
	private Integer idTemplateImpressao;
	private String nomeTemplate;
	private String htmlCabecalho;
	private String htmlCorpo;
	private String htmlRodape;
	private Integer idTipoTemplateImp;
	private Integer tamCabecalho;
	private Integer tamRodape;

	public Integer getIdTemplateImpressao(){
		return this.idTemplateImpressao;
	}
	public void setIdTemplateImpressao(Integer parm){
		this.idTemplateImpressao = parm;
	}

	public String getNomeTemplate(){
		return this.nomeTemplate;
	}
	public void setNomeTemplate(String parm){
		this.nomeTemplate = parm;
	}

	public String getHtmlCabecalho(){
		return this.htmlCabecalho;
	}
	public void setHtmlCabecalho(String parm){
		this.htmlCabecalho = parm;
	}

	public String getHtmlCorpo(){
		return this.htmlCorpo;
	}
	public void setHtmlCorpo(String parm){
		this.htmlCorpo = parm;
	}

	public String getHtmlRodape(){
		return this.htmlRodape;
	}
	public void setHtmlRodape(String parm){
		this.htmlRodape = parm;
	}

	public Integer getIdTipoTemplateImp(){
		return this.idTipoTemplateImp;
	}
	public void setIdTipoTemplateImp(Integer parm){
		this.idTipoTemplateImp = parm;
	}

	public Integer getTamCabecalho(){
		return this.tamCabecalho;
	}
	public void setTamCabecalho(Integer parm){
		this.tamCabecalho = parm;
	}

	public Integer getTamRodape(){
		return this.tamRodape;
	}
	public void setTamRodape(Integer parm){
		this.tamRodape = parm;
	}

}
