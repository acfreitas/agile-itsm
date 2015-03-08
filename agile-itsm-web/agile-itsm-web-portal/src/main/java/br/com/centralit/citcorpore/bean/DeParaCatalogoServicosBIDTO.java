package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class DeParaCatalogoServicosBIDTO implements IDto {

	private static final long serialVersionUID = 3776156239250249864L;
	
	private Integer idServicoDe;
	private Integer idServicoPara;
	private Integer idConexaoBI;
	private String nomeServicoDe;
	private String nomeServicoPara;
	
	public Integer getIdServicoDe() {
		return idServicoDe;
	}
	public void setIdServicoDe(Integer idServicoDe) {
		this.idServicoDe = idServicoDe;
	}
	public Integer getIdServicoPara() {
		return idServicoPara;
	}
	public void setIdServicoPara(Integer idServicoPara) {
		this.idServicoPara = idServicoPara;
	}
	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}
	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	public String getNomeServicoDe() {
		return nomeServicoDe;
	}
	public void setNomeServicoDe(String nomeServicoDe) {
		this.nomeServicoDe = nomeServicoDe;
	}
	public String getNomeServicoPara() {
		return nomeServicoPara;
	}
	public void setNomeServicoPara(String nomeServicoPara) {
		this.nomeServicoPara = nomeServicoPara;
	}

}
