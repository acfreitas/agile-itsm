package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

public class AdmissaoEmpregadoDTO extends MovimentacaoPessoalDTO {
	
	private Integer idEntrevista;
	private String nome;
	private Date dataNascimento;
	private String sexo;
	private String cargo;
	private String cpf;
	private String rg;
	private Date dataEmissaoRG;
	private String orgExpedidor;
	private Integer idUFOrgExpedidor;
	private String pai;
	private String mae;
	private String conjuge;
	private String observacoes;
	private Integer estadoCivil;
	private Date dataCadastro;
	private String fumante;
	private String ctpsNumero;
	private String ctpsSerie;
	private Integer ctpsIdUf;
	private Date ctpsDataEmissao;
	private String nit;
	private Date dataAdmissao;
	private Date dataFim;
	private Integer idSituacaoFuncional;
	private Date dataDemissao;
	private String tipo;
	private Double custoPorHora;
	private Double custoTotalMes;
	private Double valorSalario;
	private Double valorProdutividadeMedia;
	private Double valorPlanoSaudeMedia;
	private Double valorVTraMedia;
	private Double valorVRefMedia;
	private String agencia;
	private String contaSalario;
	private String enviaEmail;
	private Integer idEmpregado;
	private Integer idCargo;
	private Integer idResponsavel;
	
	
	public Integer getIdEntrevista() {
		return idEntrevista;
	}
	public void setIdEntrevista(Integer idEntrevista) {
		this.idEntrevista = idEntrevista;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public Date getDataEmissaoRG() {
		return dataEmissaoRG;
	}
	public void setDataEmissaoRG(Date dataEmissaoRG) {
		this.dataEmissaoRG = dataEmissaoRG;
	}
	public String getOrgExpedidor() {
		return orgExpedidor;
	}
	public void setOrgExpedidor(String orgExpedidor) {
		this.orgExpedidor = orgExpedidor;
	}
	public Integer getIdUFOrgExpedidor() {
		return idUFOrgExpedidor;
	}
	public void setIdUFOrgExpedidor(Integer idUFOrgExpedidor) {
		this.idUFOrgExpedidor = idUFOrgExpedidor;
	}
	public String getPai() {
		return pai;
	}
	public void setPai(String pai) {
		this.pai = pai;
	}
	public String getMae() {
		return mae;
	}
	public void setMae(String mae) {
		this.mae = mae;
	}
	public String getConjuge() {
		return conjuge;
	}
	public void setConjuge(String conjuge) {
		this.conjuge = conjuge;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Integer getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(Integer estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getFumante() {
		return fumante;
	}
	public void setFumante(String fumante) {
		this.fumante = fumante;
	}
	public String getCtpsNumero() {
		return ctpsNumero;
	}
	public void setCtpsNumero(String ctpsNumero) {
		this.ctpsNumero = ctpsNumero;
	}
	public String getCtpsSerie() {
		return ctpsSerie;
	}
	public void setCtpsSerie(String ctpsSerie) {
		this.ctpsSerie = ctpsSerie;
	}
	public Integer getCtpsIdUf() {
		return ctpsIdUf;
	}
	public void setCtpsIdUf(Integer ctpsIdUf) {
		this.ctpsIdUf = ctpsIdUf;
	}
	public Date getCtpsDataEmissao() {
		return ctpsDataEmissao;
	}
	public void setCtpsDataEmissao(Date ctpsDataEmissao) {
		this.ctpsDataEmissao = ctpsDataEmissao;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Integer getIdSituacaoFuncional() {
		return idSituacaoFuncional;
	}
	public void setIdSituacaoFuncional(Integer idSituacaoFuncional) {
		this.idSituacaoFuncional = idSituacaoFuncional;
	}
	public Date getDataDemissao() {
		return dataDemissao;
	}
	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getCustoPorHora() {
		return custoPorHora;
	}
	public void setCustoPorHora(Double custoPorHora) {
		this.custoPorHora = custoPorHora;
	}
	public Double getCustoTotalMes() {
		return custoTotalMes;
	}
	public void setCustoTotalMes(Double custoTotalMes) {
		this.custoTotalMes = custoTotalMes;
	}
	public Double getValorSalario() {
		return valorSalario;
	}
	public void setValorSalario(Double valorSalario) {
		this.valorSalario = valorSalario;
	}
	public Double getValorProdutividadeMedia() {
		return valorProdutividadeMedia;
	}
	public void setValorProdutividadeMedia(Double valorProdutividadeMedia) {
		this.valorProdutividadeMedia = valorProdutividadeMedia;
	}
	public Double getValorPlanoSaudeMedia() {
		return valorPlanoSaudeMedia;
	}
	public void setValorPlanoSaudeMedia(Double valorPlanoSaudeMedia) {
		this.valorPlanoSaudeMedia = valorPlanoSaudeMedia;
	}
	public Double getValorVTraMedia() {
		return valorVTraMedia;
	}
	public void setValorVTraMedia(Double valorVTraMedia) {
		this.valorVTraMedia = valorVTraMedia;
	}
	public Double getValorVRefMedia() {
		return valorVRefMedia;
	}
	public void setValorVRefMedia(Double valorVRefMedia) {
		this.valorVRefMedia = valorVRefMedia;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getContaSalario() {
		return contaSalario;
	}
	public void setContaSalario(String contaSalario) {
		this.contaSalario = contaSalario;
	}
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
	public Integer getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	
	
}