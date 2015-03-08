package br.com.centralit.citcorpore.bean;


import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoValorDTO extends ValorDTO implements IDto {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6847076405909756057L;

	private Integer idHistoricoValor;
    private Integer idHistoricoIC;
    private Timestamp dataHoraAlteracao;    
    private Integer idAutorAlteracao;    
	private String baseLine;
	private String nomeCaracteristica;
	
	public Integer getIdHistoricoValor() {
		return idHistoricoValor;
	}

	public void setIdHistoricoValor(Integer idHistoricoValor) {
		this.idHistoricoValor = idHistoricoValor;
	}

	public Timestamp getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao(Timestamp dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	public Integer getIdAutorAlteracao() {
		return idAutorAlteracao;
	}

	public void setIdAutorAlteracao(Integer idAutorAlteracao) {
		this.idAutorAlteracao = idAutorAlteracao;
	}

	public String getBaseLine() {
		return baseLine;
	}

	public void setBaseLine(String baseLine) {
		this.baseLine = baseLine;
	}

	public Integer getIdHistoricoIC() {
		return idHistoricoIC;
	}

	public void setIdHistoricoIC(Integer idHistoricoIC) {
		this.idHistoricoIC = idHistoricoIC;
	}
	
	public String getNomeCaracteristica() {
		return nomeCaracteristica;
	}

	public void setNomeCaracteristica(String nomeCaracteristica) {
		this.nomeCaracteristica = nomeCaracteristica;
	}

    
	
}
