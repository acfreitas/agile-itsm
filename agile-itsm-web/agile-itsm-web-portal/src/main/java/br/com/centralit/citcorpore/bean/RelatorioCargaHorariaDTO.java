package br.com.centralit.citcorpore.bean;


import br.com.citframework.dto.IDto;

public class RelatorioCargaHorariaDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idServico;
	private String nomeGrupo;
	private String nomeUsuario;
	private Double soma;
	private String total;
	
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Double getSoma() {
		return soma;
	}
	public void setSoma(Double soma) {
		this.soma = soma;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
