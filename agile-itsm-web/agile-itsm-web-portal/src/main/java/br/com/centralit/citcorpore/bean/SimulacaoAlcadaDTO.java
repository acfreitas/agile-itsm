package br.com.centralit.citcorpore.bean;


public class SimulacaoAlcadaDTO extends SolicitacaoServicoDTO {
	private Integer idCentroResultado;
	private Integer idTipoFluxo;
	private Double valor;
	private Double valorOutrasAlcadas;
	private Double valorMensal;
	private String finalidade;

	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}
	public Integer getIdCentroResultado() {
		return idCentroResultado;
	}
	public void setIdCentroResultado(Integer idCentroResultado) {
		this.idCentroResultado = idCentroResultado;
	}
	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}
	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getValorOutrasAlcadas() {
		return valorOutrasAlcadas;
	}
	public void setValorOutrasAlcadas(Double valorOutrasAlcadas) {
		this.valorOutrasAlcadas = valorOutrasAlcadas;
	}
	public Double getValorMensal() {
		return valorMensal;
	}
	public void setValorMensal(Double valorMensal) {
		this.valorMensal = valorMensal;
	}

}
