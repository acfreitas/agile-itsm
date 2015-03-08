package br.com.centralit.citcorpore.bean;

import java.util.ArrayList;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class PrioridadeSolicitacoesDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6247455071658402160L;
	
	private Integer idImpacto;
	private String nivelImpacto;
	private Integer idUrgencia;
	private String nivelUrgencia;
	private Integer idMatrizPrioridade;
	
	private Integer valorPrioridade;
	private Integer idContrato;
	
	private String nivelImpactoSerelializado;
	private String nivelUrgenciaSerelializado;
	private String matrizPrioridadeSelializado;
	
	private ArrayList<ImpactoDTO> listaImpacto;
	private ArrayList<UrgenciaDTO> listaUrgencia;
	private ArrayList<MatrizPrioridadeDTO> listaMatrizPrioridade;
	
	/**
	 * @return the idImpacto
	 */
	public Integer getIdImpacto() {
		return idImpacto;
	}
	/**
	 * @param idImpacto the idImpacto to set
	 */
	public void setIdImpacto(Integer idImpacto) {
		this.idImpacto = idImpacto;
	}
	/**
	 * @return the nivelImpacto
	 */
	public String getNivelImpacto() {
		return nivelImpacto;
	}
	/**
	 * @param nivelImpacto the nivelImpacto to set
	 */
	public void setNivelImpacto(String nivelImpacto) {
		this.nivelImpacto = nivelImpacto;
	}
	/**
	 * @return the idUrgencia
	 */
	public Integer getIdUrgencia() {
		return idUrgencia;
	}
	/**
	 * @param idUrgencia the idUrgencia to set
	 */
	public void setIdUrgencia(Integer idUrgencia) {
		this.idUrgencia = idUrgencia;
	}
	/**
	 * @return the nivelUrgencia
	 */
	public String getNivelUrgencia() {
		return nivelUrgencia;
	}
	/**
	 * @param nivelUrgencia the nivelUrgencia to set
	 */
	public void setNivelUrgencia(String nivelUrgencia) {
		this.nivelUrgencia = nivelUrgencia;
	}
	/**
	 * @return the idMatrizPrioridade
	 */
	public Integer getIdMatrizPrioridade() {
		return idMatrizPrioridade;
	}
	/**
	 * @param idMatrizPrioridade the idMatrizPrioridade to set
	 */
	public void setIdMatrizPrioridade(Integer idMatrizPrioridade) {
		this.idMatrizPrioridade = idMatrizPrioridade;
	}
	/**
	 * @return the valorPrioridade
	 */
	public Integer getValorPrioridade() {
		return valorPrioridade;
	}
	/**
	 * @param valorPrioridade the valorPrioridade to set
	 */
	public void setValorPrioridade(Integer valorPrioridade) {
		this.valorPrioridade = valorPrioridade;
	}
	/**
	 * @return the idContrato
	 */
	public Integer getIdContrato() {
		return idContrato;
	}
	/**
	 * @param idContrato the idContrato to set
	 */
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	/**
	 * @return the impactosSerelializados
	 */
	public String getImpactosSerelializados() {
		return nivelImpactoSerelializado;
	}
	/**
	 * @param impactosSerelializados the impactosSerelializados to set
	 */
	public void setImpactosSerelializados(String impactosSerelializados) {
		this.nivelImpactoSerelializado = impactosSerelializados;
	}
	/**
	 * @return the urgenciaSerelializados
	 */
	public String getUrgenciaSerelializados() {
		return nivelUrgenciaSerelializado;
	}
	/**
	 * @param urgenciaSerelializados the urgenciaSerelializados to set
	 */
	public void setUrgenciaSerelializados(String urgenciaSerelializados) {
		this.nivelUrgenciaSerelializado = urgenciaSerelializados;
	}
	/**
	 * @return the matrizPrioridadeSelializado
	 */
	public String getMatrizPrioridadeSelializado() {
		return matrizPrioridadeSelializado;
	}
	/**
	 * @param matrizPrioridadeSelializado the matrizPrioridadeSelializado to set
	 */
	public void setMatrizPrioridadeSelializado(String matrizPrioridadeSelializado) {
		this.matrizPrioridadeSelializado = matrizPrioridadeSelializado;
	}
	/**
	 * @return the listaImpacto
	 */
	public ArrayList<ImpactoDTO> getListaImpacto() {
		return listaImpacto;
	}
	/**
	 * @param listaImpacto the listaImpacto to set
	 */
	public void setListaImpacto(ArrayList<ImpactoDTO> listaImpacto) {
		this.listaImpacto = listaImpacto;
	}
	/**
	 * @return the listaUrgencia
	 */
	public ArrayList<UrgenciaDTO> getListaUrgencia() {
		return listaUrgencia;
	}
	/**
	 * @param listaUrgencia the listaUrgencia to set
	 */
	public void setListaUrgencia(ArrayList<UrgenciaDTO> listaUrgencia) {
		this.listaUrgencia = listaUrgencia;
	}
	/**
	 * @return the listaMatrizPrioridade
	 */
	public ArrayList<MatrizPrioridadeDTO> getListaMatrizPrioridade() {
		return listaMatrizPrioridade;
	}
	/**
	 * @param listaMatrizPrioridade the listaMatrizPrioridade to set
	 */
	public void setListaMatrizPrioridade(ArrayList<MatrizPrioridadeDTO> listaMatrizPrioridade) {
		this.listaMatrizPrioridade = listaMatrizPrioridade;
	}
	
}
