package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequisicaoMudancaRisco") 
public class RequisicaoMudancaRiscoDTO implements IDto{
	
	private Integer idRequisicaoMudancaRisco;
	private Integer idRequisicaoMudanca;
	private Integer idRisco;
	
	private String nomeRisco;
	private String detalhamento;
	
	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataFim;
	
	public Integer getIdRequisicaoMudancaRisco() {
		return idRequisicaoMudancaRisco;
	}
	public void setIdRequisicaoMudancaRisco(Integer idRequisicaoMudancaRisco) {
		this.idRequisicaoMudancaRisco = idRequisicaoMudancaRisco;
	}
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public Integer getIdRisco() {
		return idRisco;
	}
	public void setIdRisco(Integer idRisco) {
		this.idRisco = idRisco;
	}
	public String getNomeRisco() {
		return nomeRisco;
	}
	public void setNomeRisco(String nomeRisco) {
		this.nomeRisco = nomeRisco;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
