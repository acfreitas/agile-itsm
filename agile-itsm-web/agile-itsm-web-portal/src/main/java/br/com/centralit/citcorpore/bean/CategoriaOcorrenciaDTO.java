package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/** 
 * @author thiago.monteiro
 * Classe de objetos de categoria de ocorrência.
 * Um objeto dessa classe é um DTO (Data Transfer Object) e pertence a camada de MODELO (MODEL).
 * Um objeto dessa classe é responsável por armazenar os dados que trafegam entre as camadas de VISÃO (VIEW) e de PERSISTÊNCIA (PERSISTENCE) bidirecionalmente.
 */
public class CategoriaOcorrenciaDTO implements IDto {	
	private static final long serialVersionUID = -1128815777881092914L;
	
	private Integer idCategoriaOcorrencia;
	private String nome;
	private Date dataInicio;
	private Date dataFim;

	/** 
	 * @return idCategoriaOcorrencia
	 */
	public Integer getIdCategoriaOcorrencia() {
		return idCategoriaOcorrencia;
	}
	
	/**
	 * @param id
	 */
	public void setIdCategoriaOcorrencia(Integer idCategoriaOcorrencia) {
		this.idCategoriaOcorrencia = idCategoriaOcorrencia;
	}
	
	/**
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
}
