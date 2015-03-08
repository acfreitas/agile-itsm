package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class LimiteAprovacaoDTO implements IDto {
	private Integer idLimiteAprovacao;
	private String identificacao;
	private String tipoLimitePorValor;
	private String abrangenciaCentroResultado;
	
	private Collection<ValorLimiteAprovacaoDTO> colValores;
	private Integer[] idProcessoNegocio;
	private Integer[] idNivelAutoridade;
	
	protected Double valorMensalUsoInterno = 0.0;
	protected Double valorAnualUsoInterno = 0.0;
	protected Double valorMensalAtendCliente = 0.0;
	protected Double valorAnualAtendCliente = 0.0;
	
	private Collection<ProcessoNegocioDTO> colProcessos;
	private boolean valido;

	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	public Integer getIdLimiteAprovacao(){
		return this.idLimiteAprovacao;
	}
	public void setIdLimiteAprovacao(Integer parm){
		this.idLimiteAprovacao = parm;
	}

	public String getTipoLimitePorValor(){
		return this.tipoLimitePorValor;
	}
	public void setTipoLimitePorValor(String parm){
		this.tipoLimitePorValor = parm;
	}

	public String getAbrangenciaCentroResultado(){
		return this.abrangenciaCentroResultado;
	}
	public void setAbrangenciaCentroResultado(String parm){
		this.abrangenciaCentroResultado = parm;
	}
	public Collection<ValorLimiteAprovacaoDTO> getColValores() {
		return colValores;
	}
	public void setColValores(Collection<ValorLimiteAprovacaoDTO> colValores) {
		this.colValores = colValores;
	}
	public Integer[] getIdProcessoNegocio() {
		return idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer[] idProcessoNegocio) {
		this.idProcessoNegocio = idProcessoNegocio;
	}
	public Integer[] getIdNivelAutoridade() {
		return idNivelAutoridade;
	}
	public void setIdNivelAutoridade(Integer[] idNivelAutoridade) {
		this.idNivelAutoridade = idNivelAutoridade;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public Collection<ProcessoNegocioDTO> getColProcessos() {
		return colProcessos;
	}
	public void setColProcessos(Collection<ProcessoNegocioDTO> colProcessos) {
		this.colProcessos = colProcessos;
	}
	public Double getValorMensalUsoInterno() {
		return valorMensalUsoInterno;
	}
	public void setValorMensalUsoInterno(Double valorMensalUsoInterno) {
		this.valorMensalUsoInterno = valorMensalUsoInterno;
	}
	public Double getValorAnualUsoInterno() {
		return valorAnualUsoInterno;
	}
	public void setValorAnualUsoInterno(Double valorAnualUsoInterno) {
		this.valorAnualUsoInterno = valorAnualUsoInterno;
	}
	public Double getValorMensalAtendCliente() {
		return valorMensalAtendCliente;
	}
	public void setValorMensalAtendCliente(Double valorMensalAtendCliente) {
		this.valorMensalAtendCliente = valorMensalAtendCliente;
	}
	public Double getValorAnualAtendCliente() {
		return valorAnualAtendCliente;
	}
	public void setValorAnualAtendCliente(Double valorAnualAtendCliente) {
		this.valorAnualAtendCliente = valorAnualAtendCliente;
	}
	public Double getValorMensal() {
		return valorMensalUsoInterno + valorMensalAtendCliente;
	}
	public Double getValorAnual() {
		return valorAnualUsoInterno + valorAnualAtendCliente;
	}

}
