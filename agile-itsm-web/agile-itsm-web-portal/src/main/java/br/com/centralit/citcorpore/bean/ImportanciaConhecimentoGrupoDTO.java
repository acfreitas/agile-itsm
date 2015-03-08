/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class ImportanciaConhecimentoGrupoDTO implements IDto {

	private static final long serialVersionUID = -7333971112891270158L;

	private Integer idBaseConhecimento;

	private Integer idGrupo;

	private String grauImportanciaGrupo;

	private String grauImportancia;

	/**
	 * @return the idBaseConhecimento
	 */
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	/**
	 * @param idBaseConhecimento
	 *            the idBaseConhecimento to set
	 */
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	/**
	 * @return the idGrupo
	 */
	public Integer getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo
	 *            the idGrupo to set
	 */
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the grauImportanciaGrupo
	 */
	public String getGrauImportanciaGrupo() {
		return grauImportanciaGrupo;
	}

	/**
	 * @param grauImportanciaGrupo
	 *            the grauImportanciaGrupo to set
	 */
	public void setGrauImportanciaGrupo(String grauImportanciaGrupo) {
		this.grauImportanciaGrupo = grauImportanciaGrupo;
	}

	/**
	 * @return the grauImportancia
	 */
	public String getGrauImportancia() {
		return grauImportancia;
	}

	/**
	 * @param grauImportancia the grauImportancia to set
	 */
	public void setGrauImportancia(String grauImportancia) {
		this.grauImportancia = grauImportancia;
	}

}
