package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class FaturaBIDTO implements IDto {

	private static final long serialVersionUID = -6417037963383545156L;
	public static String EM_CRIACAO = "1";
	public static String AGUARDANDO_APROVACAO = "2";
	public static String APROVADAS = "3";
	public static String EM_RECEBIMENTO = "4";
	public static String RECEBIDAS = "5";
	public static String REJEITADAS = "6";
	public static String CANCELADA = "7";

	private Integer idFatura;
	private Integer idContrato;
	private java.sql.Date dataInicial;
	private java.sql.Date dataFinal;
	private String descricaoFatura;
	private Double valorCotacaoMoeda;
	private java.sql.Date dataCriacao;
	private java.sql.Date dataUltModificacao;
	private Double valorPrevistoSomaOS;
	private Double valorSomaGlosasOS;
	private Double valorExecutadoSomaOS;
	private String observacao;
	private String aprovacaoGestor;
	private String aprovacaoFiscal;
	private Double saldoPrevisto;
	private String situacaoFatura;

	private Integer qtdeOS;

	private Collection colItens;

	private Integer[] idOSFatura;
	private Integer idOSExcluir;

	private Integer idANS;
	private Integer seqANS;
	private String fieldANS;

	private Integer idConexaoBI;
	
	public Integer getIdFatura() {
		return this.idFatura;
	}

	public void setIdFatura(Integer parm) {
		this.idFatura = parm;
	}

	public Integer getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Integer parm) {
		this.idContrato = parm;
	}

	public java.sql.Date getDataInicial() {
		return this.dataInicial;
	}

	public void setDataInicial(java.sql.Date parm) {
		this.dataInicial = parm;
	}

	public java.sql.Date getDataFinal() {
		return this.dataFinal;
	}

	public void setDataFinal(java.sql.Date parm) {
		this.dataFinal = parm;
	}

	public String getDescricaoFatura() {
		return this.descricaoFatura;
	}

	public void setDescricaoFatura(String parm) {
		this.descricaoFatura = parm;
	}

	public Double getValorCotacaoMoeda() {
		return this.valorCotacaoMoeda;
	}

	public void setValorCotacaoMoeda(Double parm) {
		this.valorCotacaoMoeda = parm;
	}

	public java.sql.Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(java.sql.Date parm) {
		this.dataCriacao = parm;
	}

	public java.sql.Date getDataUltModificacao() {
		return this.dataUltModificacao;
	}

	public void setDataUltModificacao(java.sql.Date parm) {
		this.dataUltModificacao = parm;
	}

	public Double getValorReceberOS() {
		double valorexec = 0;
		double valorglosas = 0;
		if (this.getValorExecutadoSomaOS() != null) {
			valorexec = this.getValorExecutadoSomaOS();
		}
		if (this.getValorSomaGlosasOS() != null) {
			valorglosas = this.getValorSomaGlosasOS();
		}
		return valorexec - valorglosas;
	}

	public Double getValorPrevistoSomaOS() {
		return this.valorPrevistoSomaOS;
	}

	public void setValorPrevistoSomaOS(Double parm) {
		this.valorPrevistoSomaOS = parm;
	}

	public Double getValorSomaGlosasOS() {
		return this.valorSomaGlosasOS;
	}

	public void setValorSomaGlosasOS(Double parm) {
		this.valorSomaGlosasOS = parm;
	}

	public Double getValorExecutadoSomaOS() {
		return this.valorExecutadoSomaOS;
	}

	public void setValorExecutadoSomaOS(Double parm) {
		this.valorExecutadoSomaOS = parm;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String parm) {
		this.observacao = parm;
	}

	public String getAprovacaoGestor() {
		return this.aprovacaoGestor;
	}

	public void setAprovacaoGestor(String parm) {
		this.aprovacaoGestor = parm;
	}

	public String getAprovacaoFiscal() {
		return this.aprovacaoFiscal;
	}

	public void setAprovacaoFiscal(String parm) {
		this.aprovacaoFiscal = parm;
	}

	public Double getSaldoPrevisto() {
		return this.saldoPrevisto;
	}

	public void setSaldoPrevisto(Double parm) {
		this.saldoPrevisto = parm;
	}

	public String getSituacaoFatura() {
		return situacaoFatura;
	}

	public void setSituacaoFatura(String situacaoFatura) {
		this.situacaoFatura = situacaoFatura;
	}

	public String getDescricaoSituacaoFatura(HttpServletRequest request) {
		if (this.situacaoFatura == null) {
			return "";
		}
		if (this.situacaoFatura.equalsIgnoreCase("1")) {
			return UtilI18N.internacionaliza(request, "perfil.criacao");
		}
		if (this.situacaoFatura.equalsIgnoreCase("2")) {
			return UtilI18N.internacionaliza(request, "perfil.aguardandoAprovacao");
		}
		if (this.situacaoFatura.equalsIgnoreCase("3")) {
			return UtilI18N.internacionaliza(request, "perfil.aprovada");
		}
		if (this.situacaoFatura.equalsIgnoreCase("4")) {
			return UtilI18N.internacionaliza(request, "perfil.rejeitada");
		}
		if (this.situacaoFatura.equalsIgnoreCase("5")) {
			return UtilI18N.internacionaliza(request, "perfil.recebimento");
		}
		if (this.situacaoFatura.equalsIgnoreCase("6")) {
			return UtilI18N.internacionaliza(request, "perfil.recebida");
		}
		if (this.situacaoFatura.equalsIgnoreCase("7")) {
			return UtilI18N.internacionaliza(request, "perfil.cancelada");
		}
		return "";
	}

	public Integer getQtdeOS() {
		return qtdeOS;
	}

	public void setQtdeOS(Integer qtdeOS) {
		this.qtdeOS = qtdeOS;
	}

	public Collection getColItens() {
		return colItens;
	}

	public void setColItens(Collection colItens) {
		this.colItens = colItens;
	}

	public Integer[] getIdOSFatura() {
		return idOSFatura;
	}

	public void setIdOSFatura(Integer[] idOSFatura) {
		this.idOSFatura = idOSFatura;
	}

	public Integer getIdOSExcluir() {
		return idOSExcluir;
	}

	public void setIdOSExcluir(Integer idOSExcluir) {
		this.idOSExcluir = idOSExcluir;
	}

	public Integer getIdANS() {
		return idANS;
	}

	public void setIdANS(Integer idANS) {
		this.idANS = idANS;
	}

	public Integer getSeqANS() {
		return seqANS;
	}

	public void setSeqANS(Integer seqANS) {
		this.seqANS = seqANS;
	}

	public String getFieldANS() {
		return fieldANS;
	}

	public void setFieldANS(String fieldANS) {
		this.fieldANS = fieldANS;
	}

	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}

	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	
}
