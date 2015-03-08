package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilFormatacao;

public class CandidatoDTO implements IDto {

	/**
	 * @author thiago.borges
	 */
	private static final long serialVersionUID = 1L;
	private Integer idCandidato;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private String tipo;
	private String situacao;
	private Date dataInicio;
	private Date dataFim;
	private String autenticado;
	private String hashID;

	private Integer idHistoricoFuncional;
	private Integer idResponsavel;

	private String cpfFormatado;

	private String detalhamentoTabela01;
	private String detalhamentoTabela02;
	private String detalhamentoTabela03;
	private String detalhamentoTabela04;

	private Integer idListaNegra;
	private String constaListaNegra;
	private String candidatoNaListaNegra;
	
	private Integer idEmpregado;
	
	private String metodoAutenticacao;
	
	//-------------metodos gets and sets-----------------
		public Integer getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
		if (this.cpf != null) {
			this.cpf = cpf.replaceAll("[^0-9]*", "");
			this.cpfFormatado = UtilFormatacao.formataCpf(cpf);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getIdHistoricoFuncional() {
		return idHistoricoFuncional;
	}

	public void setIdHistoricoFuncional(Integer idHistoricoFuncional) {
		this.idHistoricoFuncional = idHistoricoFuncional;
	}

	public String getCpfFormatado() {
		return cpfFormatado;
	}

	public void setCpfFormatado(String cpfFormatado) {
		this.cpfFormatado = cpfFormatado;
	}

	public String getDetalhamentoTabela01() {
		return detalhamentoTabela01;
	}

	public void setDetalhamentoTabela01(String detalhamentoTabela01) {
		this.detalhamentoTabela01 = detalhamentoTabela01;
	}

	public String getDetalhamentoTabela02() {
		return detalhamentoTabela02;
	}

	public void setDetalhamentoTabela02(String detalhamentoTabela02) {
		this.detalhamentoTabela02 = detalhamentoTabela02;
	}

	public String getDetalhamentoTabela03() {
		return detalhamentoTabela03;
	}

	public void setDetalhamentoTabela03(String detalhamentoTabela03) {
		this.detalhamentoTabela03 = detalhamentoTabela03;
	}

	public String getDetalhamentoTabela04() {
		return detalhamentoTabela04;
	}

	public void setDetalhamentoTabela04(String detalhamentoTabela04) {
		this.detalhamentoTabela04 = detalhamentoTabela04;
	}

	public Integer getIdListaNegra() {
		return idListaNegra;
	}

	public void setIdListaNegra(Integer idListaNegra) {
		this.idListaNegra = idListaNegra;
	}

	public String getConstaListaNegra() {
		return constaListaNegra;
	}

	public void setConstaListaNegra(String constaListaNegra) {
		this.constaListaNegra = constaListaNegra;
	}

	public String getCandidatoNaListaNegra() {
		return candidatoNaListaNegra;
	}

	public void setCandidatoNaListaNegra(String candidatoNaListaNegra) {
		this.candidatoNaListaNegra = candidatoNaListaNegra;
	}

	public Integer getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getAutenticado() {
		return autenticado;
	}

	public void setAutenticado(String autenticado) {
		this.autenticado = autenticado;
	}

	public String getHashID() {
		return hashID;
	}

	public void setHashID(String hashID) {
		this.hashID = hashID;
	}
	public String getMetodoAutenticacao() {
		return metodoAutenticacao;
	}
	public void setMetodoAutenticacao(String metodoAutenticacao) {
		this.metodoAutenticacao = metodoAutenticacao;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	
	}
