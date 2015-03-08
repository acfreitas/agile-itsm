package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoMudanca;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.util.DateAdapter;
import br.com.citframework.util.DateTimeAdapter;
import br.com.citframework.util.UtilDatas;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequisicaoMudanca")
public class RequisicaoMudancaDTO extends ObjetoNegocioFluxoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String NIVEL_IMPACTO_ALTO = "Alto";
	public final static String NIVEL_IMPACTO_BAIXO = "Baixo";
	public final static String NIVEL_IMPACTO_MEDIO = "Médio";
	public final static String NIVEL_URGENCIA_ALTA = "Alta";
	public final static String NIVEL_URGENCIA_BAIXA = "Baixa";
	public final static String NIVEL_URGENCIA_MEDIA = "Média";
	private String acaoFluxo;
	private String alterarSituacao;
	private String analiseImpacto;
	private double atraso;
	private String atrasoStr;
	private String categoria;
	private String classificacao;
	private Collection colArquivosUpload;
	private String complementoJustificativa;
	private String conhecimento;
	private String conhecimentos;
	private String faseAtual;
	private String fecharItensRelacionados;
	private String[] colProblemaCheckado = null;
	private String unidadeDes;

	@XmlElement(name = "dataAceitacao")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataAceitacao;

	private String dataConclusaoStr;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataFim;

	@XmlElement(name = "dataHoraCaptura")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraCaptura;

	@XmlElement(name = "dataHoraConclusao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraConclusao;

	@XmlElement(name = "dataHoraInicio")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraInicio;

	@XmlElement(name = "dataHoraInicioAgendada")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraInicioAgendada;

	private String dataHoraInicioStr;
	private String dataHoraInicioToString;
	private String dataHoraLimiteToString;

	@XmlElement(name = "dataHoraReativacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraReativacao;

	@XmlElement(name = "dataHoraSolicitacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraSolicitacao;

	private String dataHoraSolicitacaoStr;

	@XmlElement(name = "dataHoraSuspensao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraSuspensao;

	@XmlElement(name = "dataHoraTermino")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraTermino;

	@XmlElement(name = "dataHoraTerminoAgendada")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraTerminoAgendada;

	private String dataHoraTerminoStr;

	@XmlElement(name = "dataInicio")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Date dataInicio;

	private String dataInicioStr;
	private String dataTerminoStr;

	@XmlElement(name = "dataVotacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Date dataVotacao;

	private String descricao;
	private String descrSituacao;
	private String editar;
	private String emailSolicitante;
	private String enviaEmailAcoes;
	private String enviaEmailCriacao;
	private String enviaEmailFinalizacao;
	private String enviaEmailGrupoComite;
	private String escalar;
	private Double estimativaCusto;
	private String exibirQuadroMudancas;
	private String fase;
	private String fechamento;
	private Integer idBaseConhecimento;
	private Integer idCalendario;
	private Integer idContatoRequisicaoMudanca;
	private Integer idContrato;
	private Integer idGrupoComite;
	private Integer idGrupoNivel;
	private Integer idJustificativa;
	private Integer IdLocalidade;
	private Integer idProprietario;
	private Integer idRequisicaoMudanca;
	private Integer idSolicitacaoServico;
	private Integer idSolicitante;
	private Integer idTarefa;
	private String horaAgendamentoFinal;
	private String horaAgendamentoInicial;
	private Integer idCategoriaSolucao;

	// private Integer idCategoriaMudanca;
	private Integer idTipoMudanca;
	private Integer idUnidade;
	private String itensConfiguracao;
	private String incidente;
	private String incidentes;
	private String itemConfiguracao;
	private String itensConfiguracaoRelacionadosSerializado;
	private List<AprovacaoMudancaDTO> listAprovacaoMudancaDTO;
	private List<AprovacaoPropostaDTO> listAprovacaoPropostaDTO;
	private List<SolicitacaoServicoDTO> listIdSolicitacaoServico;
	private List<ProblemaMudancaDTO> listProblemaMudancaDTO;
	private List<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO;
	private List<RequisicaoMudancaServicoDTO> listRequisicaoMudancaServicoDTO;
	private String motivo;
	private String nivelImpacto;
	private String nivelImportanciaNegocio;
	private String nivelUrgencia;
	private String nomeCategoriaMudanca;
	private String nomeContato;
	private String nomeGrupoAtual;
	private String nomeGrupoNivel1;
	private String nomeProprietario;
	private String nomeServico;
	private String nomeSolicitante;
	private String nomeTarefa;
	private String observacao;
	private String planoReversao;
	private Integer prazoHH;
	private Integer prazoMM;
	private Integer prioridade;
	private String problema;
	private String problemas;
	private String problemaSerializado;
	private Integer quantidade;
	private Integer quantidadeImpacto;
	private Integer quantidadeMudancaPorPeriodo;
	private Integer quantidadeMudancaSemAprovacaoPorPeriodo;
	private Integer quantidadeProprietario;
	private Integer quantidadeSolicitante;
	private Integer quantidadeStatus;
	private Integer quantidadeUrgencia;
	private String ramal;
	private String registroexecucao;
	private String risco;
	private Integer seqReabertura;
	private Integer sequenciaMudanca;
	private String serializados;
	private String servico;
	private String servicos;
	private String servicosRelacionadosSerializado;
	private String solicitacaoServicoSerializado;
	private String status;
	private String telefoneContato;
	private Integer tempoAtendimentoHH;
	private Integer tempoAtendimentoMM;
	private Integer tempoAtrasoHH;
	private Integer tempoAtrasoMM;
	private Integer tempoCapturaHH;
	private Integer tempoCapturaMM;
	private Integer tempoDecorridoHH;
	private Integer tempoDecorridoMM;
	private String tipo;
	private String titulo;
	private UsuarioDTO usuarioDto;
	private boolean ehProposta;
	private String ehPropostaAux;
	private boolean votacaoPropostaAprovada;
	private String votacaoPropostaAprovadaAux;
	private Integer idEmpresa;
	private String vencendo;
	private String linkPesquisaSatisfacao;

	public String getIframeSolicitacao() {
		return iframeSolicitacao;
	}

	public void setIframeSolicitacao(String iframeSolicitacao) {
		this.iframeSolicitacao = iframeSolicitacao;
	}

	private String iframeSolicitacao;

	// Campos Relacionar Grupos
	private List<GrupoRequisicaoMudancaDTO> listGrupoRequisicaoMudancaDTO;
	private String grupoMudanca;
	private String grupoMudancaSerializado;

	// Questionário
	private Integer idTipoRequisicao;

	private String hiddenDescricaoItemConfiguracao;

	private Integer hiddenIdItemConfiguracao;

	private Integer idTipoAba;

	private Collection<RequisicaoMudancaResponsavelDTO> colResponsaveis;

	// private Integer idCategoriaMudanca;

	private String razaoMudanca;

	private String riscoSerializado;

	private List<RequisicaoMudancaRiscoDTO> listRequisicaoMudancaRiscoDTO;

	private List<RequisicaoMudancaLiberacaoDTO> listRequisicaoMudancaLiberacaoDTO;

	private String liberacoesRelacionadosSerializado;

	// campos para reuniao reunião
	private String localReuniao;
	private String horaInicio;
	private Integer duracaoEstimada;

	private String situacaoLiberacao;

	private List<RequisicaoMudancaDTO> listRequisicaoMudancaDTO;
	private Integer idLiberacao;

	private List<LiberacaoMudancaDTO> listLiberacaoMudancaDTO;

	// campo para cadastrar agendamento direto da jsp requisicaoMudanca
	private Integer idGrupoAtvPeriodica;

	// coleção de planos de reversão
	private Collection colUploadPlanoDeReversaoGED;

	// campo auxiliar que não é salvo no banco
	private String responsavelAtual;
	private Double tempoAuto;
	private Integer idPrioridadeAuto1;
	private Integer idGrupo1;
	private String emailContato;
	private String colAllLOOKUP_PROBLEMA;

	public Integer getIdContatoRequisicaoMudanca() {
		return idContatoRequisicaoMudanca;
	}

	public void setIdContatoRequisicaoMudanca(Integer idContatoRequisicaoMudanca) {
		this.idContatoRequisicaoMudanca = idContatoRequisicaoMudanca;
	}

	public Integer getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getEscalar() {
		return escalar;
	}

	public void setEscalar(String escalar) {
		this.escalar = escalar;
	}

	public String getAlterarSituacao() {
		return alterarSituacao;
	}

	public void setAlterarSituacao(String alterarSituacao) {
		this.alterarSituacao = alterarSituacao;
	}

	/*
	 * public Integer getIdCategoriaMudanca() { return idCategoriaMudanca; }
	 * 
	 * public void setIdCategoriaMudanca(Integer idCategoriaMudanca) { this.idCategoriaMudanca = idCategoriaMudanca; }
	 */
	public String getItensConfiguracaoRelacionadosSerializado() {
		return itensConfiguracaoRelacionadosSerializado;
	}

	public void setItensConfiguracaoRelacionadosSerializado(String itensConfiguracaoRelacionadosSerializado) {
		this.itensConfiguracaoRelacionadosSerializado = itensConfiguracaoRelacionadosSerializado;
	}

	public String getServicosRelacionadosSerializado() {
		return servicosRelacionadosSerializado;
	}

	public void setServicosRelacionadosSerializado(String servicosRelacionadosSerializado) {
		this.servicosRelacionadosSerializado = servicosRelacionadosSerializado;
	}

	public String getEnviaEmailCriacao() {
		return enviaEmailCriacao;
	}

	public void setEnviaEmailCriacao(String enviaEmailCriacao) {
		this.enviaEmailCriacao = enviaEmailCriacao;
	}

	public String getEnviaEmailFinalizacao() {
		return enviaEmailFinalizacao;
	}

	public void setEnviaEmailFinalizacao(String enviaEmailFinalizacao) {
		this.enviaEmailFinalizacao = enviaEmailFinalizacao;
	}

	public String getEnviaEmailAcoes() {
		return enviaEmailAcoes;
	}

	public void setEnviaEmailAcoes(String enviaEmailAcoes) {
		this.enviaEmailAcoes = enviaEmailAcoes;
	}

	public String getNomeProprietario() {
		return nomeProprietario;
	}

	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}

	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	public String getSerializados() {
		return serializados;
	}

	public void setSerializados(String serializados) {
		this.serializados = serializados;
	}

	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}

	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNivelImportanciaNegocio() {
		return nivelImportanciaNegocio;
	}

	public void setNivelImportanciaNegocio(String nivelImportanciaNegocio) {
		this.nivelImportanciaNegocio = nivelImportanciaNegocio;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getNivelImpacto() {
		return nivelImpacto;
	}

	public void setNivelImpacto(String nivelImpacto) {
		this.nivelImpacto = nivelImpacto;
	}

	public String getAnaliseImpacto() {
		return analiseImpacto;
	}

	public void setAnaliseImpacto(String analiseImpacto) {
		this.analiseImpacto = analiseImpacto;
	}

	public Integer getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(Integer idProprietario) {
		this.idProprietario = idProprietario;
	}

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public Date getDataAceitacao() {
		return dataAceitacao;
	}

	public void setDataAceitacao(Date dataAceitacao) {
		this.dataAceitacao = dataAceitacao;
	}

	public Date getDataVotacao() {
		return dataVotacao;
	}

	public void setDataVotacao(Date dataVotacao) {
		this.dataVotacao = dataVotacao;
	}

	public String getNumberAndTitulo() {
		return "#" + this.idRequisicaoMudanca + " - " + titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getRisco() {
		return risco;
	}

	public void setRisco(String risco) {
		this.risco = risco;
	}

	public Double getEstimativaCusto() {
		return estimativaCusto;
	}

	public void setEstimativaCusto(Double estimativaCusto) {
		this.estimativaCusto = estimativaCusto;
	}

	public String getPlanoReversao() {
		return planoReversao;
	}

	public void setPlanoReversao(String planoReversao) {
		this.planoReversao = planoReversao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		try {
			if (this.status != null)
				this.descrSituacao = SituacaoRequisicaoMudanca.valueOf(this.status.trim()).getDescricao();
		} catch (Exception e) {
		}
	}

	public String getDataHoraInicioStr() {
		return dataHoraInicioStr;
	}

	public void setDataHoraInicioStr(String dataHoraInicioStr) {
		this.dataHoraInicioStr = dataHoraInicioStr;
	}

	public String getDescrSituacao() {
		return descrSituacao;
	}

	public void setDescrSituacao(String descrSituacao) {
		this.descrSituacao = descrSituacao;
	}

	public String getAtrasoStr() {
		return atrasoStr;
	}

	public void setAtrasoStr(String atrasoStr) {
		this.atrasoStr = atrasoStr;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public String getExibirQuadroMudancas() {
		return exibirQuadroMudancas;
	}

	public void setExibirQuadroMudancas(String exibirQuadroMudancas) {
		this.exibirQuadroMudancas = exibirQuadroMudancas;
	}

	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public Integer getSeqReabertura() {
		return seqReabertura;
	}

	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
	}

	public String getNomeTarefa() {
		return nomeTarefa;
	}

	public void setNomeTarefa(String nomeTarefa) {
		this.nomeTarefa = nomeTarefa;
	}

	public String getNomeGrupoAtual() {
		return nomeGrupoAtual;
	}

	public void setNomeGrupoAtual(String nomeGrupoAtual) {
		this.nomeGrupoAtual = nomeGrupoAtual;
	}

	public String getNomeGrupoNivel1() {
		return nomeGrupoNivel1;
	}

	public void setNomeGrupoNivel1(String nomeGrupoNivel1) {
		this.nomeGrupoNivel1 = nomeGrupoNivel1;
	}

	public Timestamp getDataHoraConclusao() {
		return dataHoraConclusao;
	}

	public void setDataHoraConclusao(Timestamp dataHoraConclusao) {
		this.dataHoraConclusao = dataHoraConclusao;
		if (dataHoraConclusao != null) {
			this.dataConclusaoStr = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, dataHoraConclusao, null);
		}
	}

	public Timestamp getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraSolicitacao = dataHoraInicio;
		if (dataHoraInicio != null) {
			this.dataHoraInicioStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, dataHoraInicio, null);
			this.dataHoraSolicitacaoStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, dataHoraInicio, null);
		}
	}

	public Timestamp getDataHoraTermino() {
		return dataHoraTermino;
	}

	public void setDataHoraTermino(Timestamp dataHoraTermino) {
		this.dataHoraTermino = dataHoraTermino;
		if (dataHoraTermino != null) {
			this.dataHoraTerminoStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, dataHoraTermino, null);
		}
	}

	public String getEmailSolicitante() {
		return emailSolicitante;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}

	public boolean suspensa() {
		return this.status != null && (this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Suspensa.name()));
	}

	public Integer getTempoDecorridoHH() {
		return tempoDecorridoHH;
	}

	public void setTempoDecorridoHH(Integer tempoDecorridoHH) {
		this.tempoDecorridoHH = tempoDecorridoHH;
	}

	public Integer getTempoDecorridoMM() {
		return tempoDecorridoMM;
	}

	public void setTempoDecorridoMM(Integer tempoDecorridoMM) {
		this.tempoDecorridoMM = tempoDecorridoMM;
	}

	public Timestamp getDataHoraSuspensao() {
		return dataHoraSuspensao;
	}

	public void setDataHoraSuspensao(Timestamp dataHoraSuspensao) {
		this.dataHoraSuspensao = dataHoraSuspensao;
	}

	public Timestamp getDataHoraReativacao() {
		return dataHoraReativacao;
	}

	public void setDataHoraReativacao(Timestamp dataHoraReativacao) {
		this.dataHoraReativacao = dataHoraReativacao;
	}

	public Integer getIdJustificativa() {
		return idJustificativa;
	}

	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}

	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}

	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}

	public Integer getPrazoHH() {
		return prazoHH;
	}

	public void setPrazoHH(Integer prazoHH) {
		this.prazoHH = prazoHH;
	}

	public Integer getPrazoMM() {
		return prazoMM;
	}

	public void setPrazoMM(Integer prazoMM) {
		this.prazoMM = prazoMM;
	}

	public Integer getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}

	public Integer getTempoAtendimentoHH() {
		return tempoAtendimentoHH;
	}

	public void setTempoAtendimentoHH(Integer tempoAtendimentoHH) {
		this.tempoAtendimentoHH = tempoAtendimentoHH;
	}

	public Integer getTempoAtendimentoMM() {
		return tempoAtendimentoMM;
	}

	public void setTempoAtendimentoMM(Integer tempoAtendimentoMM) {
		this.tempoAtendimentoMM = tempoAtendimentoMM;
	}

	public Integer getTempoAtrasoHH() {
		return tempoAtrasoHH;
	}

	public void setTempoAtrasoHH(Integer tempoAtrasoHH) {
		this.tempoAtrasoHH = tempoAtrasoHH;
	}

	public Integer getTempoAtrasoMM() {
		return tempoAtrasoMM;
	}

	public void setTempoAtrasoMM(Integer tempoAtrasoMM) {
		this.tempoAtrasoMM = tempoAtrasoMM;
	}

	public Timestamp getDataHoraCaptura() {
		return dataHoraCaptura;
	}

	public void setDataHoraCaptura(Timestamp dataHoraCaptura) {
		this.dataHoraCaptura = dataHoraCaptura;
	}

	public Integer getTempoCapturaHH() {
		return tempoCapturaHH;
	}

	public void setTempoCapturaHH(Integer tempoCapturaHH) {
		this.tempoCapturaHH = tempoCapturaHH;
	}

	public Integer getTempoCapturaMM() {
		return tempoCapturaMM;
	}

	public void setTempoCapturaMM(Integer tempoCapturaMM) {
		this.tempoCapturaMM = tempoCapturaMM;
	}

	public String getAcaoFluxo() {
		return acaoFluxo;
	}

	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getDataHoraLimiteToString() {
		if (dataHoraTermino == null) {
			return "";
		}
		return dataHoraTermino.toString();
	}

	public void setDataHoraLimiteToString(String dataHoraLimiteToString) {
		this.dataHoraLimiteToString = this.getDataHoraLimiteToString();
	}

	public String getDataHoraInicioToString() {
		if (dataHoraInicioStr == null) {
			return "";
		}
		return dataHoraInicioStr.toString();
	}

	public void setDataHoraInicioToString(String dataHoraInicioToString) {
		this.dataHoraInicioToString = this.getDataHoraInicioToString();
	}

	public String getDataHoraLimiteStr() {
		return dataHoraTerminoStr;
	}

	public String getDataHoraTerminoStr() {
		return dataHoraTerminoStr;
	}

	public void setDataHoraTerminoStr(String dataHoraTerminoStr) {
		this.dataHoraTerminoStr = dataHoraTerminoStr;
	}

	public double getAtraso() {
		return atraso;
	}

	public void setAtraso(double atraso) {
		this.atraso = atraso;
		this.atrasoStr = Util.getHoraFmtStr(atraso / 3600);
	}

	public String getDataHoraSolicitacaoStr() {
		return dataHoraSolicitacaoStr;
	}

	public void setDataHoraSolicitacaoStr(String dataHoraSolicitacaoStr) {
		this.dataHoraSolicitacaoStr = dataHoraSolicitacaoStr;
	}

	public Timestamp getDataHoraSolicitacao() {
		return dataHoraSolicitacao;
	}

	public void setDataHoraSolicitacao(Timestamp dataHoraSolicitacao) {
		this.dataHoraSolicitacao = dataHoraSolicitacao;
	}

	/**
	 * @return the nivelUrgencia
	 */
	public String getNivelUrgencia() {
		return nivelUrgencia;
	}

	/**
	 * @param nivelUrgencia
	 *            the nivelUrgencia to set
	 */
	public void setNivelUrgencia(String nivelUrgencia) {
		this.nivelUrgencia = nivelUrgencia;
	}

	/**
	 * @return the idGrupoNivel
	 */
	public Integer getIdGrupoNivel() {
		return idGrupoNivel;
	}

	/**
	 * @param idGrupoNivel
	 *            the idGrupoNivel to set
	 */
	public void setIdGrupoNivel(Integer idGrupoNivel) {
		this.idGrupoNivel = idGrupoNivel;
	}

	/**
	 * @return the sequenciaMudanca
	 */
	public Integer getSequenciaMudanca() {
		return sequenciaMudanca;
	}

	/**
	 * @param sequenciaMudanca
	 *            the sequenciaMudanca to set
	 */
	public void setSequenciaMudanca(Integer sequenciaMudanca) {
		this.sequenciaMudanca = sequenciaMudanca;
	}

	/**
	 * @return the idSolicitacaoServico
	 */
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	/**
	 * @param idSolicitacaoServico
	 *            the idSolicitacaoServico to set
	 */
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	/**
	 * @return the solicitacaoServicoSerializado
	 */
	public String getSolicitacaoServicoSerializado() {
		return solicitacaoServicoSerializado;
	}

	/**
	 * @param solicitacaoServicoSerializado
	 *            the solicitacaoServicoSerializado to set
	 */
	public void setSolicitacaoServicoSerializado(String solicitacaoServicoSerializado) {
		this.solicitacaoServicoSerializado = solicitacaoServicoSerializado;
	}

	/**
	 * @return the listIdSolicitacaoServico
	 */
	public List<SolicitacaoServicoDTO> getListIdSolicitacaoServico() {
		return listIdSolicitacaoServico;
	}

	/**
	 * @param listIdSolicitacaoServico
	 *            the listIdSolicitacaoServico to set
	 */
	public void setListIdSolicitacaoServico(List<SolicitacaoServicoDTO> listIdSolicitacaoServico) {
		this.listIdSolicitacaoServico = listIdSolicitacaoServico;
	}

	/**
	 * @return the nomeServico
	 */
	public String getNomeServico() {
		return nomeServico;
	}

	/**
	 * @param nomeServico
	 *            the nomeServico to set
	 */
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Integer getIdLocalidade() {
		return IdLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade) {
		IdLocalidade = idLocalidade;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	public Integer getIdTipoMudanca() {
		return idTipoMudanca;
	}

	public void setIdTipoMudanca(Integer idTipoMudanca) {
		this.idTipoMudanca = idTipoMudanca;
	}

	/**
	 * @return the nomeCategoriaMudanca
	 */
	public String getNomeCategoriaMudanca() {
		return nomeCategoriaMudanca;
	}

	/**
	 * @param nomeCategoriaMudanca
	 *            the nomeCategoriaMudanca to set
	 */
	public void setNomeCategoriaMudanca(String nomeCategoriaMudanca) {
		this.nomeCategoriaMudanca = nomeCategoriaMudanca;
	}

	/**
	 * @return the listRequisicaoMudancaServicoDTO
	 */
	public List<RequisicaoMudancaServicoDTO> getListRequisicaoMudancaServicoDTO() {
		return listRequisicaoMudancaServicoDTO;
	}

	/**
	 * @param listRequisicaoMudancaServicoDTO
	 *            the listRequisicaoMudancaServicoDTO to set
	 */
	public void setListRequisicaoMudancaServicoDTO(List<RequisicaoMudancaServicoDTO> listRequisicaoMudancaServicoDTO) {
		this.listRequisicaoMudancaServicoDTO = listRequisicaoMudancaServicoDTO;
	}

	/**
	 * @return the listRequisicaoMudancaItemConfiguracaoDTO
	 */
	public List<RequisicaoMudancaItemConfiguracaoDTO> getListRequisicaoMudancaItemConfiguracaoDTO() {
		return listRequisicaoMudancaItemConfiguracaoDTO;
	}

	/**
	 * @param listRequisicaoMudancaItemConfiguracaoDTO
	 *            the listRequisicaoMudancaItemConfiguracaoDTO to set
	 */
	public void setListRequisicaoMudancaItemConfiguracaoDTO(List<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO) {
		this.listRequisicaoMudancaItemConfiguracaoDTO = listRequisicaoMudancaItemConfiguracaoDTO;
	}

	/**
	 * @return the idGrupoComite
	 */
	public Integer getIdGrupoComite() {
		return idGrupoComite;
	}

	/**
	 * @param idGrupoComite
	 *            the idGrupoComite to set
	 */
	public void setIdGrupoComite(Integer idGrupoComite) {
		this.idGrupoComite = idGrupoComite;
	}

	/**
	 * @return the listAprovacaoMudancaDTO
	 */
	public List<AprovacaoMudancaDTO> getListAprovacaoMudancaDTO() {
		return listAprovacaoMudancaDTO;
	}

	/**
	 * @param listAprovacaoMudancaDTO
	 *            the listAprovacaoMudancaDTO to set
	 */
	public void setListAprovacaoMudancaDTO(List<AprovacaoMudancaDTO> listAprovacaoMudancaDTO) {
		this.listAprovacaoMudancaDTO = listAprovacaoMudancaDTO;
	}

	public String getRegistroexecucao() {
		return registroexecucao;
	}

	public void setRegistroexecucao(String registroexecucao) {
		this.registroexecucao = registroexecucao;
	}

	/**
	 * @return the enviaEmailGrupoComite
	 */
	public String getEnviaEmailGrupoComite() {
		return enviaEmailGrupoComite;
	}

	/**
	 * @param enviaEmailGrupoComite
	 *            the enviaEmailGrupoComite to set
	 */
	public void setEnviaEmailGrupoComite(String enviaEmailGrupoComite) {
		this.enviaEmailGrupoComite = enviaEmailGrupoComite;
	}

	public String getDadosStr() {
		StringBuilder str = new StringBuilder();
		if (getIdRequisicaoMudanca() != null) {
			str.append("Número da requisição: " + getIdRequisicaoMudanca() + "\n");
			if (getDataHoraInicio() != null) {
				str.append("Criada em: " + getDataHoraInicio() + "\n");
			}
			if (getDescrSituacao() != null) {
				str.append("Situação: " + getDescrSituacao() + "\n");
			}
			if (!suspensa()) {
				if (getPrazoHH() != null) {
					str.append("Prazo atual: " + getPrazoHH() + "\n");
				}
				if (getDataHoraLimiteStr() != null) {
					str.append("Data hora limite: " + getDataHoraLimiteStr() + "\n");
				}
				if (getNomeGrupoAtual() != null)
					str.append("Grupo atual: " + getNomeGrupoAtual() + "\n");
			} else {
				str.append("Tempo decorrido: " + getTempoCapturaMM() + "\n");
			}
			if (getNivelImpacto() != null) {
				String imp = "";
				if (getNivelImpacto().equalsIgnoreCase("B")) {
					imp = "Baixo";
				}
				if (getNivelImpacto().equalsIgnoreCase("M")) {
					imp = "Médio";
				}
				if (getNivelImpacto().equalsIgnoreCase("A")) {
					imp = "Alto";
				}
				str.append("Impacto: " + imp + "\n");
			}
			if (getNivelUrgencia() != null) {
				String imp = "";
				if (getNivelUrgencia().equalsIgnoreCase("B")) {
					imp = "Baixa";
				}
				if (getNivelUrgencia().equalsIgnoreCase("M")) {
					imp = "Média";
				}
				if (getNivelUrgencia().equalsIgnoreCase("A")) {
					imp = "Alta";
				}
				str.append("Urgência: " + imp + "\n");
			}
			if (getPrioridade() != null) {
				str.append("Prioridade (Código): " + getPrioridade() + "\n");
			}
		}
		return str.toString();
	}

	/**
	 * @return the dataInicioStr
	 */
	public String getDataInicioStr() {
		return dataInicioStr;
	}

	/**
	 * @param dataInicioStr
	 *            the dataInicioStr to set
	 */
	public void setDataInicioStr(String dataInicioStr) {
		this.dataInicioStr = dataInicioStr;
	}

	/**
	 * @return the dataTerminoStr
	 */
	public String getDataTerminoStr() {
		return dataTerminoStr;
	}

	/**
	 * @param dataTerminoStr
	 *            the dataTerminoStr to set
	 */
	public void setDataTerminoStr(String dataTerminoStr) {
		this.dataTerminoStr = dataTerminoStr;
	}

	/**
	 * @return the dataHoraInicioAgendada
	 */
	public Timestamp getDataHoraInicioAgendada() {
		return dataHoraInicioAgendada;
	}

	/**
	 * @param dataHoraInicioAgendada
	 *            the dataHoraInicioAgendada to set
	 */
	public void setDataHoraInicioAgendada(Timestamp dataHoraInicioAgendada) {
		this.dataHoraInicioAgendada = dataHoraInicioAgendada;
		if (dataHoraInicioAgendada != null) {
			SimpleDateFormat fData = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInicioStr = fData.format(dataHoraInicioAgendada);
		}
	}

	/**
	 * @return the dataHoraTerminoAgendada
	 */
	public Timestamp getDataHoraTerminoAgendada() {
		return dataHoraTerminoAgendada;
	}

	/**
	 * @param dataHoraTerminoAgendada
	 *            the dataHoraTerminoAgendada to set
	 */
	public void setDataHoraTerminoAgendada(Timestamp dataHoraTerminoAgendada) {
		this.dataHoraTerminoAgendada = dataHoraTerminoAgendada;
		if (dataHoraTerminoAgendada != null) {
			SimpleDateFormat fData = new SimpleDateFormat("dd/MM/yyyy");
			this.dataTerminoStr = fData.format(dataHoraTerminoAgendada);
		}
	}

	public String getDataConclusaoStr() {
		return dataConclusaoStr;
	}

	public void setDataConclusaoStr(String dataConclusaoStr) {
		this.dataConclusaoStr = dataConclusaoStr;
	}

	public Collection getColArquivosUpload() {
		return colArquivosUpload;
	}

	public void setColArquivosUpload(Collection colArquivosUpload) {
		this.colArquivosUpload = colArquivosUpload;
	}

	public String getProblemaSerializado() {
		return problemaSerializado;
	}

	public void setProblemaSerializado(String problemaSerializado) {
		this.problemaSerializado = problemaSerializado;
	}

	public List<ProblemaMudancaDTO> getListProblemaMudancaDTO() {
		return listProblemaMudancaDTO;
	}

	public void setListProblemaMudancaDTO(List<ProblemaMudancaDTO> problemaMudancaDTO) {
		this.listProblemaMudancaDTO = problemaMudancaDTO;
	}

	public String getFechamento() {
		return fechamento;
	}

	public void setFechamento(String fechamento) {
		this.fechamento = fechamento;
	}

	public String getEditar() {
		return editar;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

	public String getRazaoMudanca() {
		return razaoMudanca;
	}

	public void setRazaoMudanca(String razaoMudanca) {
		this.razaoMudanca = razaoMudanca;
	}

	public List<RequisicaoMudancaRiscoDTO> getListRequisicaoMudancaRiscoDTO() {
		return listRequisicaoMudancaRiscoDTO;
	}

	public void setListRequisicaoMudancaRiscoDTO(List<RequisicaoMudancaRiscoDTO> listRequisicaoMudancaRiscoDTO) {
		this.listRequisicaoMudancaRiscoDTO = listRequisicaoMudancaRiscoDTO;
	}

	public String getRiscoSerializado() {
		return riscoSerializado;
	}

	public void setRiscoSerializado(String riscoSerializado) {
		this.riscoSerializado = riscoSerializado;
	}

	public String getLiberacoesRelacionadosSerializado() {
		return liberacoesRelacionadosSerializado;
	}

	public void setLiberacoesRelacionadosSerializado(String liberacoesRelacionadosSerializado) {
		this.liberacoesRelacionadosSerializado = liberacoesRelacionadosSerializado;
	}

	public List<RequisicaoMudancaLiberacaoDTO> getListRequisicaoMudancaLiberacaoDTO() {
		return listRequisicaoMudancaLiberacaoDTO;
	}

	public void setListRequisicaoMudancaLiberacaoDTO(List<RequisicaoMudancaLiberacaoDTO> listRequisicaoMudancaLiberacaoDTO) {
		this.listRequisicaoMudancaLiberacaoDTO = listRequisicaoMudancaLiberacaoDTO;
	}

	public String getLocalReuniao() {
		return localReuniao;
	}

	public void setLocalReuniao(String localReuniao) {
		this.localReuniao = localReuniao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Integer getDuracaoEstimada() {
		return duracaoEstimada;
	}

	public void setDuracaoEstimada(Integer duracaoEstimada) {
		this.duracaoEstimada = duracaoEstimada;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getConhecimento() {
		return conhecimento;
	}

	public void setConhecimento(String conhecimento) {
		this.conhecimento = conhecimento;
	}

	public String getConhecimentos() {
		return conhecimentos;
	}

	public void setConhecimentos(String conhecimentos) {
		this.conhecimentos = conhecimentos;
	}

	public String getItensConfiguracao() {
		return itensConfiguracao;
	}

	public void setItensConfiguracao(String itensConfiguracao) {
		this.itensConfiguracao = itensConfiguracao;
	}

	public String getIncidente() {
		return incidente;
	}

	public void setIncidente(String incidente) {
		this.incidente = incidente;
	}

	public String getIncidentes() {
		return incidentes;
	}

	public void setIncidentes(String incidentes) {
		this.incidentes = incidentes;
	}

	public String getItemConfiguracao() {
		return itemConfiguracao;
	}

	public void setItemConfiguracao(String itemConfiguracao) {
		this.itemConfiguracao = itemConfiguracao;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public String getProblemas() {
		return problemas;
	}

	public void setProblemas(String problemas) {
		this.problemas = problemas;
	}

	public Integer getQuantidadeImpacto() {
		return quantidadeImpacto;
	}

	public void setQuantidadeImpacto(Integer quantidadeImpacto) {
		this.quantidadeImpacto = quantidadeImpacto;
	}

	public Integer getQuantidadeMudancaPorPeriodo() {
		return quantidadeMudancaPorPeriodo;
	}

	public void setQuantidadeMudancaPorPeriodo(Integer quantidadeMudancaPorPeriodo) {
		this.quantidadeMudancaPorPeriodo = quantidadeMudancaPorPeriodo;
	}

	public Integer getQuantidadeMudancaSemAprovacaoPorPeriodo() {
		return quantidadeMudancaSemAprovacaoPorPeriodo;
	}

	public void setQuantidadeMudancaSemAprovacaoPorPeriodo(Integer quantidadeMudancaSemAprovacaoPorPeriodo) {
		this.quantidadeMudancaSemAprovacaoPorPeriodo = quantidadeMudancaSemAprovacaoPorPeriodo;
	}

	public Integer getQuantidadeProprietario() {
		return quantidadeProprietario;
	}

	public void setQuantidadeProprietario(Integer quantidadeProprietario) {
		this.quantidadeProprietario = quantidadeProprietario;
	}

	public Integer getQuantidadeSolicitante() {
		return quantidadeSolicitante;
	}

	public void setQuantidadeSolicitante(Integer quantidadeSolicitante) {
		this.quantidadeSolicitante = quantidadeSolicitante;
	}

	public Integer getQuantidadeStatus() {
		return quantidadeStatus;
	}

	public void setQuantidadeStatus(Integer quantidadeStatus) {
		this.quantidadeStatus = quantidadeStatus;
	}

	public Integer getQuantidadeUrgencia() {
		return quantidadeUrgencia;
	}

	public void setQuantidadeUrgencia(Integer quantidadeUrgencia) {
		this.quantidadeUrgencia = quantidadeUrgencia;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getServicos() {
		return servicos;
	}

	public void setServicos(String servicos) {
		this.servicos = servicos;
	}

	public boolean atendida() {
		return this.status != null
				&& (this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Resolvida.name()) || this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Cancelada.name()) || this.status
						.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Concluida.name()));
	}

	public boolean emAtendimento() {
		return this.status != null
				&& (this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Registrada.name()) || this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Aprovada.name())
						|| this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.EmExecucao.name()) || this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Reaberta.name()));
	}

	public boolean encerrada() {
		return this.status != null && (this.status.equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Concluida.name()));
	}

	public boolean escalada() {
		return getIdGrupoAtual() != null;
	}

	public String getSituacaoLiberacao() {
		return situacaoLiberacao;
	}

	public void setSituacaoLiberacao(String situacaoLiberacao) {
		this.situacaoLiberacao = situacaoLiberacao;
	}

	public boolean getEhProposta() {
		return ehProposta;
	}

	public boolean ehProposta() {
		if (this.ehPropostaAux.equalsIgnoreCase("S"))
			return true;
		else
			return false;
	}

	public void setEhProposta(boolean ehProposta) {
		this.ehProposta = ehProposta;
	}

	public boolean getVotacaoPropostaAprovada() {
		return votacaoPropostaAprovada;
	}

	public void setVotacaoPropostaAprovada(boolean votacaoPropostaAprovada) {
		this.votacaoPropostaAprovada = votacaoPropostaAprovada;
	}

	public boolean votacaoPropostaAprovada() {
		if (this.getVotacaoPropostaAprovadaAux().equalsIgnoreCase("S"))
			return true;
		else
			return false;
	}

	public String getEhPropostaAux() {
		return ehPropostaAux;
	}

	public void setEhPropostaAux(String ehPropostaAux) {
		this.ehPropostaAux = ehPropostaAux;
	}

	public List<AprovacaoPropostaDTO> getListAprovacaoPropostaDTO() {
		return listAprovacaoPropostaDTO;
	}

	public void setListAprovacaoPropostaDTO(List<AprovacaoPropostaDTO> listAprovacaoPropostaDTO) {
		this.listAprovacaoPropostaDTO = listAprovacaoPropostaDTO;
	}

	public String getVotacaoPropostaAprovadaAux() {
		return votacaoPropostaAprovadaAux;
	}

	public void setVotacaoPropostaAprovadaAux(String votacaoPropostaAprovadaAux) {
		this.votacaoPropostaAprovadaAux = votacaoPropostaAprovadaAux;
	}

	public List<RequisicaoMudancaDTO> getListRequisicaoMudancaDTO() {
		return listRequisicaoMudancaDTO;
	}

	public void setListRequisicaoMudancaDTO(List<RequisicaoMudancaDTO> listRequisicaoMudancaDTO) {
		this.listRequisicaoMudancaDTO = listRequisicaoMudancaDTO;
	}

	public Integer getIdLiberacao() {
		return idLiberacao;
	}

	public void setIdLiberacao(Integer idLiberacao) {
		this.idLiberacao = idLiberacao;
	}

	public List<LiberacaoMudancaDTO> getListLiberacaoMudancaDTO() {
		return listLiberacaoMudancaDTO;
	}

	public void setListLiberacaoMudancaDTO(List<LiberacaoMudancaDTO> listLiberacaoMudancaDTO) {
		this.listLiberacaoMudancaDTO = listLiberacaoMudancaDTO;
	}

	public Collection<RequisicaoMudancaResponsavelDTO> getColResponsaveis() {
		return colResponsaveis;
	}

	public void setColResponsaveis(Collection<RequisicaoMudancaResponsavelDTO> colResponsaveis) {
		this.colResponsaveis = colResponsaveis;
	}

	public String getHoraAgendamentoFinal() {
		return horaAgendamentoFinal;
	}

	public void setHoraAgendamentoFinal(String horaAgendamentoFinal) {
		this.horaAgendamentoFinal = horaAgendamentoFinal;
	}

	public String getHoraAgendamentoInicial() {
		return horaAgendamentoInicial;
	}

	public void setHoraAgendamentoInicial(String horaAgendamentoInicial) {
		this.horaAgendamentoInicial = horaAgendamentoInicial;
	}

	public Integer getIdTipoAba() {
		return idTipoAba;
	}

	public void setIdTipoAba(Integer idTipoAba) {
		this.idTipoAba = idTipoAba;
	}

	public String getHiddenDescricaoItemConfiguracao() {
		return hiddenDescricaoItemConfiguracao;
	}

	public void setHiddenDescricaoItemConfiguracao(String hiddenDescricaoItemConfiguracao) {
		this.hiddenDescricaoItemConfiguracao = hiddenDescricaoItemConfiguracao;
	}

	public Integer getIdGrupoAtvPeriodica() {
		return idGrupoAtvPeriodica;
	}

	public void setIdGrupoAtvPeriodica(Integer idGrupoAtvPeriodica) {
		this.idGrupoAtvPeriodica = idGrupoAtvPeriodica;
	}

	public Integer getIdTipoRequisicao() {
		return idTipoRequisicao;
	}

	public void setIdTipoRequisicao(Integer idTipoRequisicao) {
		this.idTipoRequisicao = idTipoRequisicao;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdCategoriaSolucao() {
		return idCategoriaSolucao;
	}

	public void setIdCategoriaSolucao(Integer idCategoriaSolucao) {
		this.idCategoriaSolucao = idCategoriaSolucao;
	}

	public Collection getColUploadPlanoDeReversaoGED() {
		return colUploadPlanoDeReversaoGED;
	}

	public void setColUploadPlanoDeReversaoGED(Collection colUploadPlanoDeReversaoGED) {
		this.colUploadPlanoDeReversaoGED = colUploadPlanoDeReversaoGED;
	}

	public String getResponsavelAtual() {
		return responsavelAtual;
	}

	public void setResponsavelAtual(String responsavelAtual) {
		this.responsavelAtual = responsavelAtual;
	}

	public String getFaseAtual() {
		return faseAtual;
	}

	public void setFaseAtual(String faseAtual) {
		this.faseAtual = faseAtual;
	}

	public String getFecharItensRelacionados() {
		return fecharItensRelacionados;
	}

	public void setFecharItensRelacionados(String fecharItensRelacionados) {
		this.fecharItensRelacionados = fecharItensRelacionados;
	}

	public List<GrupoRequisicaoMudancaDTO> getListGrupoRequisicaoMudancaDTO() {
		return listGrupoRequisicaoMudancaDTO;
	}

	public void setListGrupoRequisicaoMudancaDTO(List<GrupoRequisicaoMudancaDTO> listGrupoRequisicaoMudancaDTO) {
		this.listGrupoRequisicaoMudancaDTO = listGrupoRequisicaoMudancaDTO;
	}

	public String getGrupoMudanca() {
		return grupoMudanca;
	}

	public void setGrupoMudanca(String grupoMudanca) {
		this.grupoMudanca = grupoMudanca;
	}

	public String getGrupoMudancaSerializado() {
		return grupoMudancaSerializado;
	}

	public void setGrupoMudancaSerializado(String grupoMudancaSerializado) {
		this.grupoMudancaSerializado = grupoMudancaSerializado;
	}

	public Double getTempoAuto() {
		return tempoAuto;
	}

	public void setTempoAuto(Double tempoAuto) {
		this.tempoAuto = tempoAuto;
	}

	public Integer getIdPrioridadeAuto1() {
		return idPrioridadeAuto1;
	}

	public void setIdPrioridadeAuto1(Integer idPrioridadeAuto1) {
		this.idPrioridadeAuto1 = idPrioridadeAuto1;
	}

	public Integer getIdGrupo1() {
		return idGrupo1;
	}

	public void setIdGrupo1(Integer idGrupo1) {
		this.idGrupo1 = idGrupo1;
	}

	public String getVencendo() {
		return vencendo;
	}

	public void setVencendo(String vencendo) {
		this.vencendo = vencendo;
	}

	public String getLinkPesquisaSatisfacao() {
		return linkPesquisaSatisfacao;
	}

	public void setLinkPesquisaSatisfacao(String linkPesquisaSatisfacao) {
		this.linkPesquisaSatisfacao = linkPesquisaSatisfacao;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = tratarCaracteresEspeciais(emailContato);
	}

	private String tratarCaracteresEspeciais(String valor) {
		if (valor != null && !StringUtils.isEmpty(valor)) {
			if (StringUtils.contains(valor, "'")) {
				return StringUtils.replace(valor, "'", "");
			} else {
				if (StringUtils.contains(valor, "\"")) {
					return StringUtils.replace(valor, "\"", "");
				} else {
					return valor;
				}
			}

		} else {

			return valor;
		}
	}

	public String[] getColProblemaCheckado() {
		return colProblemaCheckado;
	}

	public void setColProblemaCheckado(String checkados) {
		if (checkados != null)
			this.colProblemaCheckado = checkados.split(";");
		else
			this.colProblemaCheckado = new String[] {};
	}

	public String getColAllLOOKUP_PROBLEMA() {
		return colAllLOOKUP_PROBLEMA;
	}

	public void setColAllLOOKUP_PROBLEMA(String colAllLOOKUP_PROBLEMA) {
		this.colAllLOOKUP_PROBLEMA = colAllLOOKUP_PROBLEMA;
	}

	public Integer getHiddenIdItemConfiguracao() {
		return hiddenIdItemConfiguracao;
	}

	public void setHiddenIdItemConfiguracao(Integer hiddenIdItemConfiguracao) {
		this.hiddenIdItemConfiguracao = hiddenIdItemConfiguracao;
	}

	public String getUnidadeDes() {
		return unidadeDes;
	}

	public void setUnidadeDes(String unidadeDes) {
		this.unidadeDes = unidadeDes;
	}

}
