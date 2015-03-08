package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GrupoEmpregadoDTO implements IDto {

	private static final long serialVersionUID = -2802341677119032913L;

	private Integer idGrupo;

	private Integer idEmpregado;

	private String sigla;

	private String enviaEmail;

	private String nomeEmpregado;

	public String getEnviaEmail() {
		return enviaEmail;
	}

	public void setEnviaEmail(String enviaEmail) {
		this.enviaEmail = enviaEmail;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmpregado == null) ? 0 : idEmpregado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoEmpregadoDTO other = (GrupoEmpregadoDTO) obj;
		if (idEmpregado == null) {
			if (other.idEmpregado != null)
				return false;
		} else if (!idEmpregado.equals(other.idEmpregado))
			return false;
		return true;
	}

}
