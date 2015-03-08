package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

public class HistoricoItemConfiguracaoDTO extends ItemConfiguracaoDTO {

	private static final long serialVersionUID = 5116065110323539062L;

	private Integer idHistoricoIC;
	private Timestamp dataHoraAlteracao;
	private Integer idAutorAlteracao;
	private String baseLine;
	private Integer restauracao;
	private Double historicoVersao;
	private String nomeProprietario;
	private transient String nomeTipoItemConfiguracao;
	private String nomeCaracteristica;
	private String valorstr;
	private String origem;
	private Integer idOrigemModificacao;

	public Timestamp getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao(Timestamp dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	public Integer getRestauracao() {
		return restauracao;
	}

	public void setRestauracao(Integer restauracao) {
		this.restauracao = restauracao;
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

	public String getNomeProprietario() {
		return nomeProprietario;
	}

	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}

	public String getNomeTipoItemConfiguracao() {
		return nomeTipoItemConfiguracao;
	}

	public void setNomeTipoItemConfiguracao(String nomeTipoItemConfiguracao) {
		this.nomeTipoItemConfiguracao = nomeTipoItemConfiguracao;
	}

	public String getNomeCaracteristica() {
		return nomeCaracteristica;
	}

	public void setNomeCaracteristica(String nomeCaracteristica) {
		this.nomeCaracteristica = nomeCaracteristica;
	}

	public String getValorstr() {
		return valorstr;
	}

	public void setValorstr(String valorstr) {
		this.valorstr = valorstr;
	}

	public Double getHistoricoVersao() {
		return historicoVersao;
	}

	public void setHistoricoVersao(Double historicoVersao) {
		this.historicoVersao = historicoVersao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Integer getIdOrigemModificacao() {
		return idOrigemModificacao;
	}

	public void setIdOrigemModificacao(Integer idOrigemModificacao) {
		this.idOrigemModificacao = idOrigemModificacao;
	}

	
}