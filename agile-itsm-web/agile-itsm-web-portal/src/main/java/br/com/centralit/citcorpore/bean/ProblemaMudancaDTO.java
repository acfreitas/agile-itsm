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
@XmlRootElement(name = "ProblemaMudanca") 
public class ProblemaMudancaDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idProblemaMudanca;
	private Integer idProblema;
	private Integer idRequisicaoMudanca;
	
	private String titulo;
	private String status;
	
	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataFim;
	
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getIdProblemaMudanca(){
		return this.idProblemaMudanca;
	}
	public void setIdProblemaMudanca(Integer parm){
		this.idProblemaMudanca = parm;
	}

	public Integer getIdProblema(){
		return this.idProblema;
	}
	public void setIdProblema(Integer parm){
		this.idProblema = parm;
	}

	public Integer getIdRequisicaoMudanca(){
		return this.idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer parm){
		this.idRequisicaoMudanca = parm;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
