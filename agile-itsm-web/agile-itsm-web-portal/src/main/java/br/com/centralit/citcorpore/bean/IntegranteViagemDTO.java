package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.citframework.dto.IDto;


public class IntegranteViagemDTO  implements IDto {
	
	private static final long serialVersionUID = 1L; 
	
	private Integer idIntegranteViagem;
	private Integer idSolicitacaoServico;
	private Integer idEmpregado;
	private String nome;
	private String nomeNaoFuncionario;
	private String email;
	private String tipoMovimentacao;
	private Integer idRespPrestacaoContas;
	private String respPrestacaoContas;
	private String integranteFuncionario;	
	private String remarcacao;
	private Integer idItemTrabalho;
	private Integer idTarefa;
	private String emPrestacaoContas;
	private String infoNaoFuncionario;
	private String estado;
	//Define se o Integrante da Viagem é um funcionario(S) ou não(N)
	
	//atributos abaixo usados apenas para auxiliar tela de remarcação
	private Integer idSolicitacao;
	private String eOu;
	private Date dataInicio;
	private Date dataInicioAux;
	private Date dataFim;
	private Date dataFimAux;
	private Integer origem;
	private Integer destino;
	private String NomeOrigem;
	private String NomeDestino;
	private String NomeEmpregado;
	private Date ida;
	private Date volta;
	private Integer idTipo;
	private Double valor;
	private Integer quantidade;
	private String colDespesaViagemSerialize;
	private String remarcarRoteiro;
	private Integer idRoteiro;
	
	
	/**
	 * @return the idSolicitacaoServico
	 */
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	/**
	 * @param idSolicitacaoServico the idSolicitacaoServico to set
	 */
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	/**
	 * @return the idEmpregado
	 */
	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	/**
	 * @param idEmpregado the idEmpregado to set
	 */
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdRespPrestacaoContas() {
		return idRespPrestacaoContas;
	}

	public void setIdRespPrestacaoContas(Integer idRespPrestacaoContas) {
		this.idRespPrestacaoContas = idRespPrestacaoContas;
	}

	public String getRespPrestacaoContas() {
		return respPrestacaoContas;
	}

	public void setRespPrestacaoContas(String respPrestacaoContas) {
		this.respPrestacaoContas = respPrestacaoContas;
	}

	public String getIntegranteFuncionario() {
		return integranteFuncionario;
	}

	public void setIntegranteFuncionario(String integranteFuncionario) {
		this.integranteFuncionario = integranteFuncionario;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIdIntegranteViagem() {
		return idIntegranteViagem;
	}

	public void setIdIntegranteViagem(Integer idIntegranteViagem) {
		this.idIntegranteViagem = idIntegranteViagem;
	}

	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public String getRemarcacao() {
		return remarcacao;
	}

	public void setRemarcacao(String remarcacao) {
		this.remarcacao = remarcacao;
	}

	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}

	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}

	public String getEmPrestacaoContas() {
		return emPrestacaoContas;
	}

	public void setEmPrestacaoContas(String emPrestacaoContas) {
		this.emPrestacaoContas = emPrestacaoContas;
	}

	public String getNomeNaoFuncionario() {
		return nomeNaoFuncionario;
	}

	public void setNomeNaoFuncionario(String nomeNaoFuncionario) {
		this.nomeNaoFuncionario = nomeNaoFuncionario;
	}

	public Integer getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getInfoNaoFuncionario() {
		return infoNaoFuncionario;
	}

	public void setInfoNaoFuncionario(String infoNaoFuncionario) {
		this.infoNaoFuncionario = infoNaoFuncionario;
	}

	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public String getEOu() {
		return eOu;
	}

	public void setEOu(String eOu) {
		this.eOu = eOu;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getDataInicioAux() {
		return dataInicioAux;
	}

	public void setDataInicioAux(Date dataInicioAux) {
		this.dataInicioAux = dataInicioAux;
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

	public Date getDataFimAux() {
		return dataFimAux;
	}

	public void setDataFimAux(Date dataFimAux) {
		this.dataFimAux = dataFimAux;
	}

	public Integer getOrigem() {
		return origem;
	}

	public void setOrigem(Integer origem) {
		this.origem = origem;
	}

	public Integer getDestino() {
		return destino;
	}

	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	public String getNomeOrigem() {
		return NomeOrigem;
	}

	public void setNomeOrigem(String nomeOrigem) {
		NomeOrigem = nomeOrigem;
	}

	public String getNomeDestino() {
		return NomeDestino;
	}

	public void setNomeDestino(String nomeDestino) {
		NomeDestino = nomeDestino;
	}

	public Date getIda() {
		return ida;
	}

	public void setIda(Date ida) {
		this.ida = ida;
	}

	public Date getVolta() {
		return volta;
	}

	public void setVolta(Date volta) {
		this.volta = volta;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Double getTotal() {
		Double total = 0.0;
		
		if(this.valor != null && this.quantidade != null) {
			total = this.valor * this.quantidade;
		}
		
		return total;
	}
	
	public String getTotalFormatado() {
		Double total = 0.0;
		
		if(this.valor != null && this.quantidade != null) {
			total = this.valor * this.quantidade;
		}
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		
		DecimalFormat decimal = (DecimalFormat) nf;
		
		decimal.applyPattern("#,##0.00");
		
		return decimal.format(total);
	}
	
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getColDespesaViagemSerialize() {
		return colDespesaViagemSerialize;
	}

	public void setColDespesaViagemSerialize(String colDespesaViagemSerialize) {
		this.colDespesaViagemSerialize = colDespesaViagemSerialize;
	}

	public String getRemarcarRoteiro() {
		return remarcarRoteiro;
	}

	public void setRemarcarRoteiro(String remarcarRoteiro) {
		this.remarcarRoteiro = remarcarRoteiro;
	}

	public Integer getIdRoteiro() {
		return idRoteiro;
	}

	public void setIdRoteiro(Integer idRoteiro) {
		this.idRoteiro = idRoteiro;
	}

	public String getNomeEmpregado() {
		return NomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		NomeEmpregado = nomeEmpregado;
	}
}