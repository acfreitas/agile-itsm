package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;

public class ProblemaDTO extends ObjetoNegocioFluxoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idProblema;
	private String status;
	private Integer prioridade;
	private Integer idCriador;
	private Integer idProprietario;
	private String titulo;
	private String descricao;
	private Integer idCategoriaProblema;
	private String impacto;
	private String urgencia;
	private String severidade;
	private String proativoReativo;
	private String msgErroAssociada;
	private Integer idProblemaItemConfiguracao;
	private Integer idErrosConhecidos;
	private Integer idProblemaMudanca;
	private Integer idProblemaIncidente;
	private Date dataHoraLimiteSolucionar;
	private Timestamp dataHoraInicio;
	private Timestamp dataHoraFim;
	private String solucaoDefinitiva;
	private String fecharItensRelacionados;
	private Integer idProblemaRelacionado;
	private String messageId;

	private Integer idSolicitacaoServico;

	private Integer idServico;
	private Integer idContrato;
	private Integer idServicoContrato;

	private Integer idGrupo;

	private String servico;

	private String nomeGrupoAtual;
	private String contrato;

	// base conhecimento
	private String causaRaiz;
	private String solucaoContorno;
	private String adicionarBDCE;

	// propriedades que não estão no banco
	private String nomeCriador;
	private String nomeProprietario;
	private String itensConfiguracaoRelacionadosSerializado;
	private String solicitacaoServicoSerializado;
	private String nomeServico;
	private List<SolicitacaoServicoDTO> listIdSolicitacaoServico;

	// propriedades da baseConhecimento
	private Integer idBaseConhecimento;
	private String statusBaseConhecimento;
	private Integer idPastaBaseConhecimento;
	private Integer sequenciaProblema;
	private Integer quantidade;

	private Integer prazoHH;
	private Integer prazoMM;
	private String slaACombinar;

	private Timestamp dataHoraLimite;
	private Integer idPrioridade;
	private Timestamp dataHoraSolicitacao;

	private String dataHoraLimiteStr;

	private String requisicaoMudancaSerializado;

	private List<ProblemaMudancaDTO> listProblemaMudancaDTO;
	private List<ProblemaDTO> listProblemaRelacionadoDTO;

	private String origem;

	// adicionado por david.junior
	// para a execucao dos fluxos de problema
	private String nomeUnidadeSolicitante;
	private String nomeUnidadeResponsavel;
	private String solicitante;
	private String solicitanteUnidade;
	private String responsavel;
	private Timestamp dataHoraInicioSLA;
	private String dataHoraInicioSLAStr;
	private UsuarioDTO usuarioDto;
	private double atrasoSLA;
	private String atrasoSLAStr;
	private String acaoFluxo;
	private Integer idFaseAtual;
	private String linkPesquisaSatisfacao;
	private Integer seqReabertura;
	private String enviaEmailCriacao;
	private String enviaEmailFinalizacao;
	private String enviaEmailAcoes;
	private String nomeTarefa;
	private Timestamp dataHoraCaptura;
	private Integer idInstanciaFluxo;
	private Integer idProblemaPai;
	private Boolean possuiFilho;

	private String emailContato;
	private Integer tempoCapturaHH;
	private Integer tempoCapturaMM;
	private Integer idCalendario;
	private Timestamp dataHoraReativacao;
	private Integer tempoDecorridoHH;
	private Integer tempoDecorridoMM;
	private Integer tempoAtendimentoHH;
	private Integer tempoAtendimentoMM;
	private Integer tempoAtrasoHH;
	private Integer tempoAtrasoMM;
	private Integer idCausaIncidente;
	private String resposta;
	private Timestamp dataHoraSuspensao;
	private String situacaoSLA;
	private Timestamp dataHoraSuspensaoSLA;
	private Timestamp dataHoraReativacaoSLA;
	private Integer idSolicitante;

	private String nomeContato;
	private String telefoneContato;
	private String ramal;
	private String observacao;
	private Integer idUnidade;
	private Integer idJustificativaProblema;
	private String complementoJustificativa;
	private Integer idTarefa;
	private Integer idOrigemAtendimento;
	private String diagnostico;
	private String dataHoraCapturaStr;
	private Integer idContatoProblema;
	private Integer idLocalidade;

	// Adicionado por geber.costa
	// Atributos para Revisão de Problemas graves

	private String acoesCorretas;
	private String acoesIncorretas;
	private String melhoriasFuturas;
	private String recorrenciaProblema;
	private String responsabilidadeTerceiros;
	private String acompanhamento;

	// Adicionado por riubbe.oliveira
	// Atributos para Categoria Problema
	private String confirmaSolucaoContorno;
	private Integer idCategoriaSolucao;
	private String fechamento;

	private List<ProblemaItemConfiguracaoDTO> listProblemaItemConfiguracaoDTO;

	private String registroexecucao;

	// add by david.junior para problema.java
	//
	private String escalar;
	private String alterarSituacao;
	private String fase;
	private String editar;

	private String grave;
	private String precisaMudanca;
	private String precisaSolucaoContorno;

	private String resolvido;
	private String tituloSolucaoContorno;
	private Integer idSolucaoContorno;

	// Flag (S/N) que indica se deve ser enviado um e-mail
	// quando o prazo para solucionar um problema expirou.
	// por padrão é S no banco de dados.
	private String enviaEmailPrazoSolucionarExpirou;

	private Integer idCausa;

	private String tituloSolCon;
	private String descSolCon;

	private String tituloSolucaoDefinitiva;
	private Integer idSolucaoDefinitiva;
	private String tituloSolDefinitiva;

	private String iframeSolicitacao;

	private String descSolDefinitiva;
	private String unidadeDes;
	

	public String getIframeSolicitacao() {
		return iframeSolicitacao;
	}

	public void setIframeSolicitacao(String iframeSolicitacao) {
		this.iframeSolicitacao = iframeSolicitacao;
	}

	private String chamarTelaProblema;

	// Serialização das informações complementares
	private String informacoesComplementares_serialize;
	private IDto informacoesComplementares;

	private Integer hiddenIdItemConfiguracao;

	// Mário Júnior - Anexo
	private Collection colArquivosUpload;

	public Integer getIdProblema() {
		return idProblema;
	}

	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getIdCriador() {
		return idCriador;
	}

	public void setIdCriador(Integer idCriador) {
		this.idCriador = idCriador;
	}

	public Integer getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(Integer idProprietario) {
		this.idProprietario = idProprietario;
	}

	public String getNumberAndTitulo() {
		return "#" + this.idProblema + " - " + titulo;
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

	public Integer getIdCategoriaProblema() {
		return idCategoriaProblema;
	}

	public void setIdCategoriaProblema(Integer idCategoriaProblema) {
		this.idCategoriaProblema = idCategoriaProblema;
	}

	public String getImpacto() {
		return impacto;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	public String getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}

	public String getSeveridade() {
		return severidade;
	}

	public void setSeveridade(String severidade) {
		this.severidade = severidade;
	}

	public String getProativoReativo() {
		return proativoReativo;
	}

	public void setProativoReativo(String proativoReativo) {
		this.proativoReativo = proativoReativo;
	}

	public String getMsgErroAssociada() {
		return msgErroAssociada;
	}

	public void setMsgErroAssociada(String msgErroAssociada) {
		this.msgErroAssociada = msgErroAssociada;
	}

	public Integer getIdProblemaItemConfiguracao() {
		return idProblemaItemConfiguracao;
	}

	public void setIdProblemaItemConfiguracao(Integer idProblemaItemConfiguracao) {
		this.idProblemaItemConfiguracao = idProblemaItemConfiguracao;
	}

	public Integer getIdErrosConhecidos() {
		return idErrosConhecidos;
	}

	public void setIdErrosConhecidos(Integer idErrosConhecidos) {
		this.idErrosConhecidos = idErrosConhecidos;
	}

	public Integer getIdProblemaMudanca() {
		return idProblemaMudanca;
	}

	public void setIdProblemaMudanca(Integer idProblemaMudanca) {
		this.idProblemaMudanca = idProblemaMudanca;
	}

	public Integer getIdProblemaIncidente() {
		return idProblemaIncidente;
	}

	public void setIdProblemaIncidente(Integer idProblemaIncidente) {
		this.idProblemaIncidente = idProblemaIncidente;
	}

	public Date getDataHoraLimiteSolucionar() {
		return dataHoraLimiteSolucionar;
	}

	public void setDataHoraLimiteSolucionar(Date dataHoraLimiteSolucionar) {
		this.dataHoraLimiteSolucionar = dataHoraLimiteSolucionar;
	}

	public Timestamp getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Timestamp getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Timestamp dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public String getSolucaoDefinitiva() {
		return solucaoDefinitiva;
	}

	public void setSolucaoDefinitiva(String solucaoDefinitiva) {
		this.solucaoDefinitiva = solucaoDefinitiva;
	}

	public String getNomeCriador() {
		return nomeCriador;
	}

	public void setNomeCriador(String nomeCriador) {
		this.nomeCriador = nomeCriador;
	}

	public String getNomeProprietario() {
		return nomeProprietario;
	}

	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}

	public String getItensConfiguracaoRelacionadosSerializado() {
		return itensConfiguracaoRelacionadosSerializado;
	}

	public void setItensConfiguracaoRelacionadosSerializado(String itensConfiguracaoRelacionadosSerializado) {
		this.itensConfiguracaoRelacionadosSerializado = itensConfiguracaoRelacionadosSerializado;
	}

	public String getCausaRaiz() {
		return causaRaiz;
	}

	public void setCausaRaiz(String causaRaiz) {
		this.causaRaiz = causaRaiz;
	}

	public String getSolucaoContorno() {
		return solucaoContorno;
	}

	public void setSolucaoContorno(String solucaoContorno) {
		this.solucaoContorno = solucaoContorno;
	}

	public String getAdicionarBDCE() {
		return adicionarBDCE;
	}

	public void setAdicionarBDCE(String adicionarBDCE) {
		this.adicionarBDCE = adicionarBDCE;
	}

	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	public String getStatusBaseConhecimento() {
		return statusBaseConhecimento;
	}

	public void setStatusBaseConhecimento(String statusBaseConhecimento) {
		this.statusBaseConhecimento = statusBaseConhecimento;
	}

	public Integer getIdPastaBaseConhecimento() {
		return idPastaBaseConhecimento;
	}

	public void setIdPastaBaseConhecimento(Integer idPastaBaseConhecimento) {
		this.idPastaBaseConhecimento = idPastaBaseConhecimento;
	}

	/**
	 * @return the sequenciaProblema
	 */
	public Integer getSequenciaProblema() {
		return sequenciaProblema;
	}

	/**
	 * @param sequenciaProblema
	 *            the sequenciaProblema to set
	 */
	public void setSequenciaProblema(Integer sequenciaProblema) {
		this.sequenciaProblema = sequenciaProblema;
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getIdServico() {
		return idServico;
	}

	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}

	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
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

	public String getSlaACombinar() {
		return slaACombinar;
	}

	public void setSlaACombinar(String slaACombinar) {
		this.slaACombinar = slaACombinar;
	}

	public Timestamp getDataHoraLimite() {
		return dataHoraLimite;
	}

	public void setDataHoraLimite(Timestamp dataHoraLimite) {
		this.dataHoraLimite = dataHoraLimite;
		if (dataHoraLimite != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.dataHoraLimiteStr = format.format(dataHoraLimite);
		}
	}

	public String getDataHoraLimiteStr() {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			return dataHoraLimiteStr;
		} else {
			return "--";
		}
	}

	public void setDataHoraLimiteStr(String dataHoraLimiteStr) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			this.dataHoraLimiteStr = dataHoraLimiteStr;
		} else {
			this.dataHoraLimiteStr = "--";
		}
	}

	public Integer getIdPrioridade() {
		return idPrioridade;
	}

	public void setIdPrioridade(Integer idPrioridade) {
		this.idPrioridade = idPrioridade;
	}

	public Timestamp getDataHoraSolicitacao() {
		return dataHoraSolicitacao;
	}

	public void setDataHoraSolicitacao(Timestamp dataHoraSolicitacao) {
		this.dataHoraSolicitacao = dataHoraSolicitacao;
	}

	public String getRequisicaoMudancaSerializado() {
		return requisicaoMudancaSerializado;
	}

	public void setRequisicaoMudancaSerializado(String requisicaoMudancaSerializado) {
		this.requisicaoMudancaSerializado = requisicaoMudancaSerializado;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public List<ProblemaMudancaDTO> getListProblemaMudancaDTO() {
		return listProblemaMudancaDTO;
	}

	public void setListProblemaMudancaDTO(List<ProblemaMudancaDTO> listProblemaMudancaDTO) {
		this.listProblemaMudancaDTO = listProblemaMudancaDTO;
	}

	public String getNomeUnidadeSolicitante() {
		return nomeUnidadeSolicitante;
	}

	public void setNomeUnidadeSolicitante(String nomeUnidadeSolicitante) {
		this.nomeUnidadeSolicitante = nomeUnidadeSolicitante;
	}

	public String getNomeUnidadeResponsavel() {
		return nomeUnidadeResponsavel;
	}

	public void setNomeUnidadeResponsavel(String nomeUnidadeResponsavel) {
		this.nomeUnidadeResponsavel = nomeUnidadeResponsavel;
	}

	public String getSolicitante() {
		if (this.solicitante == null) {
			return null;
		}
		return this.solicitante.replaceAll("\"", " ");
	}

	public void setSolicitante(String solicitante) {
		if (solicitante == null) {
			this.solicitante = null;
			return;
		}
		this.solicitante = solicitante.replaceAll("\"", " ");
	}

	public String getSolicitanteUnidade() {
		return solicitanteUnidade;
	}

	public void setSolicitanteUnidade(String solicitanteUnidade) {
		this.solicitanteUnidade = solicitanteUnidade;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Timestamp getDataHoraInicioSLA() {
		return dataHoraInicioSLA;
	}

	public void setDataHoraInicioSLA(Timestamp dataHoraInicioSLA) {
		this.dataHoraInicioSLA = dataHoraInicioSLA;
		if (dataHoraInicioSLA != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.setDataHoraInicioSLAStr(format.format(dataHoraInicioSLA));
		}
	}

	public String getDataHoraInicioSLAStr() {
		return dataHoraInicioSLAStr;
	}

	public void setDataHoraInicioSLAStr(String dataHoraInicioSLAStr) {
		this.dataHoraInicioSLAStr = dataHoraInicioSLAStr;
	}

	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public boolean atendida() {
		return this.status != null
				&& (this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Resolvida.name()) || this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Cancelada.name()) || this.status
						.equalsIgnoreCase(Enumerados.SituacaoProblema.Fechada.name()));
	}

	public boolean finalizada() {
		return this.status != null
				&& (this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Resolvida.name()) || this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Cancelada.name()) || this.status
						.equalsIgnoreCase(Enumerados.SituacaoProblema.Fechada.name()));
	}

	public boolean encerrada() {
		return this.status != null && (this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Fechada.name()));
	}

	public boolean emAtendimento() {
		return this.status != null && (this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.EmAndamento.name()) || this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Reaberta.name()));
	}

	public boolean reclassificada() {
		return this.status != null && (this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.ReClassificada.name()));
	}

	public boolean escalada() {
		return getIdGrupo() != null;
	}

	public boolean suspensa() {
		return this.status != null && (this.status.equalsIgnoreCase(Enumerados.SituacaoProblema.Suspensa.name()));
	}

	public double getAtrasoSLA() {
		return atrasoSLA;
	}

	public void setAtrasoSLA(double atrasoSLA) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			this.atrasoSLA = atrasoSLA;
			this.atrasoSLAStr = Util.getHoraFmtStr(atrasoSLA / 3600);
		} else {
			this.atrasoSLA = 0;
			this.atrasoSLAStr = "--";
		}
	}

	public String getAtrasoSLAStr() {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			return atrasoSLAStr;
		} else {
			return "--";
		}
	}

	public void setAtrasoSLAStr(String atrasoSLAStr) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			this.atrasoSLAStr = atrasoSLAStr;
		} else {
			this.atrasoSLAStr = "--";
		}
	}

	public String getAcaoFluxo() {
		return acaoFluxo;
	}

	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}

	public Integer getIdFaseAtual() {
		return idFaseAtual;
	}

	public void setIdFaseAtual(Integer idFaseAtual) {
		this.idFaseAtual = idFaseAtual;
	}

	public String getLinkPesquisaSatisfacao() {
		return linkPesquisaSatisfacao;
	}

	public void setLinkPesquisaSatisfacao(String linkPesquisaSatisfacao) {
		this.linkPesquisaSatisfacao = linkPesquisaSatisfacao;
	}

	public Integer getSeqReabertura() {
		return seqReabertura;
	}

	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
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

	public String getNomeTarefa() {
		return nomeTarefa;
	}

	public void setNomeTarefa(String nomeTarefa) {
		this.nomeTarefa = nomeTarefa;
	}

	public Timestamp getDataHoraCaptura() {
		return dataHoraCaptura;
	}

	public void setDataHoraCaptura(Timestamp dataHoraCaptura) {
		this.dataHoraCaptura = dataHoraCaptura;
		if (dataHoraCaptura != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.dataHoraCapturaStr = format.format(dataHoraCaptura);
		}
	}

	public Integer getIdInstanciaFluxo() {
		return idInstanciaFluxo;
	}

	public void setIdInstanciaFluxo(Integer idInstanciaFluxo) {
		this.idInstanciaFluxo = idInstanciaFluxo;
	}

	public Integer getIdProblemaPai() {
		return idProblemaPai;
	}

	public void setIdProblemaPai(Integer idProblemaPai) {
		this.idProblemaPai = idProblemaPai;
	}

	public Boolean getPossuiFilho() {
		return possuiFilho;
	}

	public void setPossuiFilho(Boolean possuiFilho) {
		this.possuiFilho = possuiFilho;
	}

	public String getEmailContato() {
		return Util.tratarAspasSimples(this.emailContato);
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

	public Integer getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}

	public Timestamp getDataHoraReativacao() {
		return dataHoraReativacao;
	}

	public void setDataHoraReativacao(Timestamp dataHoraReativacao) {
		this.dataHoraReativacao = dataHoraReativacao;
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

	public Integer getTempoAtrasoMM() {
		return tempoAtrasoMM;
	}

	public void setTempoAtrasoMM(Integer tempoAtrasoMM) {
		this.tempoAtrasoMM = tempoAtrasoMM;
	}

	public Integer getTempoAtrasoHH() {
		return tempoAtrasoHH;
	}

	public void setTempoAtrasoHH(Integer tempoAtrasoHH) {
		this.tempoAtrasoHH = tempoAtrasoHH;
	}

	public Integer getIdCausaIncidente() {
		return idCausaIncidente;
	}

	public void setIdCausaIncidente(Integer idCausaIncidente) {
		this.idCausaIncidente = idCausaIncidente;
	}

	public String getResposta() {
		return Util.tratarAspasSimples(this.resposta);
	}

	public void setResposta(String parm) {
		this.resposta = parm;
	}

	public Timestamp getDataHoraSuspensao() {
		return dataHoraSuspensao;
	}

	public void setDataHoraSuspensao(Timestamp dataHoraSuspensao) {
		this.dataHoraSuspensao = dataHoraSuspensao;
	}

	public String getSituacaoSLA() {
		return situacaoSLA;
	}

	public void setSituacaoSLA(String situacaoSLA) {
		this.situacaoSLA = situacaoSLA;
	}

	public Timestamp getDataHoraSuspensaoSLA() {
		return dataHoraSuspensaoSLA;
	}

	public void setDataHoraSuspensaoSLA(Timestamp dataHoraSuspensaoSLA) {
		this.dataHoraSuspensaoSLA = dataHoraSuspensaoSLA;
	}

	public Timestamp getDataHoraReativacaoSLA() {
		return dataHoraReativacaoSLA;
	}

	public void setDataHoraReativacaoSLA(Timestamp dataHoraReativacaoSLA) {
		this.dataHoraReativacaoSLA = dataHoraReativacaoSLA;
	}

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
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

	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}

	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}

	/**
	 * 
	 * @author geber.costa
	 */
	public String getDadosStr() {
		StringBuilder str = new StringBuilder();
		if (getIdProblema() != null) {
			str.append("Número do problema: " + getIdProblema() + "\n");
			if (getDataHoraInicio() != null) {
				str.append("Criada em: " + getDataHoraInicio() + "\n");
			}
			if (getDescricao() != null) {
				str.append("Situação: " + getDescricao() + "\n");
			}
			// if (!suspensa()) {
			if (getPrazoHH() != null) {
				str.append("Prazo atual: " + getPrazoHH() + "\n");
			}
			if (getDataHoraLimiteStr() != null) {
				str.append("Data hora limite: " + getDataHoraLimiteStr() + "\n");
			}
			if (getNomeGrupoAtual() != null)
				str.append("Grupo atual: " + getNomeGrupoAtual() + "\n");
			// } else {
			// str.append("Tempo decorrido: " + getTempoCapturaMM() + "\n");
			// }
			if (getImpacto() != null) {
				String imp = "";
				if (getImpacto().equalsIgnoreCase("B")) {
					imp = "Baixo";
				}
				if (getImpacto().equalsIgnoreCase("M")) {
					imp = "Médio";
				}
				if (getImpacto().equalsIgnoreCase("A")) {
					imp = "Alto";
				}
				str.append("Impacto: " + imp + "\n");
			}
			if (getUrgencia() != null) {
				String imp = "";
				if (getUrgencia().equalsIgnoreCase("B")) {
					imp = "Baixa";
				}
				if (getUrgencia().equalsIgnoreCase("M")) {
					imp = "Média";
				}
				if (getUrgencia().equalsIgnoreCase("A")) {
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

	public Integer getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Integer getIdOrigemAtendimento() {
		return idOrigemAtendimento;
	}

	public void setIdOrigemAtendimento(Integer idOrigemAtendimento) {
		this.idOrigemAtendimento = idOrigemAtendimento;
	}

	public String getDataHoraCapturaStr() {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			return dataHoraCapturaStr;
		} else {
			return "--";
		}
	}

	public void setDataHoraCapturaStr(String dataHoraCapturaStr) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			this.dataHoraCapturaStr = dataHoraCapturaStr;
		} else {
			this.dataHoraCapturaStr = "--";
		}
	}

	public Integer getIdContatoProblema() {
		return idContatoProblema;
	}

	public void setIdContatoProblema(Integer idContatoProblema) {
		this.idContatoProblema = idContatoProblema;
	}

	public Integer getIdLocalidade() {
		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}

	public String getAcoesCorretas() {
		return acoesCorretas;
	}

	public void setAcoesCorretas(String acoesCorretas) {
		this.acoesCorretas = acoesCorretas;
	}

	public String getAcoesIncorretas() {
		return acoesIncorretas;
	}

	public void setAcoesIncorretas(String acoesIncorretas) {
		this.acoesIncorretas = acoesIncorretas;
	}

	public String getMelhoriasFuturas() {
		return melhoriasFuturas;
	}

	public void setMelhoriasFuturas(String melhoriasFuturas) {
		this.melhoriasFuturas = melhoriasFuturas;
	}

	public String getRecorrenciaProblema() {
		return recorrenciaProblema;
	}

	public void setRecorrenciaProblema(String recorrenciaProblema) {
		this.recorrenciaProblema = recorrenciaProblema;
	}

	public String getResponsabilidadeTerceiros() {
		return responsabilidadeTerceiros;
	}

	public void setResponsabilidadeTerceiros(String responsabilidadeTerceiros) {
		this.responsabilidadeTerceiros = responsabilidadeTerceiros;
	}

	public String getAcompanhamento() {
		return acompanhamento;
	}

	public void setAcompanhamento(String acompanhamento) {
		this.acompanhamento = acompanhamento;
	}

	public Integer getIdCategoriaSolucao() {
		return idCategoriaSolucao;
	}

	public void setIdCategoriaSolucao(Integer idCategoriaSolucao) {
		this.idCategoriaSolucao = idCategoriaSolucao;
	}

	public String getConfirmaSolucaoContorno() {
		return confirmaSolucaoContorno;
	}

	public void setConfirmaSolucaoContorno(String confirmaSolucaoContorno) {
		this.confirmaSolucaoContorno = confirmaSolucaoContorno;
	}

	public String getFechamento() {
		return fechamento;
	}

	public void setFechamento(String fechamento) {
		this.fechamento = fechamento;
	}

	public String getNomeGrupoAtual() {
		return nomeGrupoAtual;
	}

	public void setNomeGrupoAtual(String nomeGrupoAtual) {
		this.nomeGrupoAtual = nomeGrupoAtual;
	}

	public List<ProblemaItemConfiguracaoDTO> getListProblemaItemConfiguracaoDTO() {
		return listProblemaItemConfiguracaoDTO;
	}

	public void setListProblemaItemConfiguracaoDTO(List<ProblemaItemConfiguracaoDTO> listProblemaItemConfiguracaoDTO) {
		this.listProblemaItemConfiguracaoDTO = listProblemaItemConfiguracaoDTO;
	}

	public String getRegistroexecucao() {
		return registroexecucao;
	}

	public void setRegistroexecucao(String registroexecucao) {
		this.registroexecucao = registroexecucao;
	}

	public Integer getIdJustificativaProblema() {
		return idJustificativaProblema;
	}

	public void setIdJustificativaProblema(Integer idJustificativaProblema) {
		this.idJustificativaProblema = idJustificativaProblema;
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

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getEditar() {
		return editar;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

	public String getGrave() {
		return grave;
	}

	public void setGrave(String grave) {
		this.grave = grave;
	}

	public String getPrecisaMudanca() {
		return precisaMudanca;
	}

	public void setPrecisaMudanca(String precisaMudanca) {
		this.precisaMudanca = precisaMudanca;
	}

	public String getPrecisaSolucaoContorno() {
		return precisaSolucaoContorno;
	}

	public void setPrecisaSolucaoContorno(String precisaSolucaoContorno) {
		this.precisaSolucaoContorno = precisaSolucaoContorno;
	}

	public String getResolvido() {
		return resolvido;
	}

	public void setResolvido(String resolvido) {
		this.resolvido = resolvido;
	}

	public String getEnviaEmailPrazoSolucionarExpirou() {
		return enviaEmailPrazoSolucionarExpirou;
	}

	public void setEnviaEmailPrazoSolucionarExpirou(String enviaEmailPrazoSolucionarExpirou) {
		this.enviaEmailPrazoSolucionarExpirou = enviaEmailPrazoSolucionarExpirou;
	}

	public Integer getIdCausa() {
		return idCausa;
	}

	public void setIdCausa(Integer idCausa) {
		this.idCausa = idCausa;
	}

	public String getTituloSolucaoContorno() {
		return tituloSolucaoContorno;
	}

	public void setTituloSolucaoContorno(String tituloSolucaoContorno) {
		this.tituloSolucaoContorno = tituloSolucaoContorno;
	}

	public Integer getIdSolucaoContorno() {
		return idSolucaoContorno;
	}

	public void setIdSolucaoContorno(Integer idSolucaoContorno) {
		this.idSolucaoContorno = idSolucaoContorno;
	}

	public String getTituloSolCon() {
		return tituloSolCon;
	}

	public void setTituloSolCon(String tituloSolCon) {
		this.tituloSolCon = tituloSolCon;
	}

	public String getDescSolCon() {
		return descSolCon;
	}

	public void setDescSolCon(String descSolCon) {
		this.descSolCon = descSolCon;
	}

	public String getTituloSolucaoDefinitiva() {
		return tituloSolucaoDefinitiva;
	}

	public void setTituloSolucaoDefinitiva(String tituloSolucaoDefinitiva) {
		this.tituloSolucaoDefinitiva = tituloSolucaoDefinitiva;
	}

	public Integer getIdSolucaoDefinitiva() {
		return idSolucaoDefinitiva;
	}

	public void setIdSolucaoDefinitiva(Integer idSolucaoDefinitiva) {
		this.idSolucaoDefinitiva = idSolucaoDefinitiva;
	}

	public String getTituloSolDefinitiva() {
		return tituloSolDefinitiva;
	}

	public void setTituloSolDefinitiva(String tituloSolDefinitiva) {
		this.tituloSolDefinitiva = tituloSolDefinitiva;
	}

	public String getDescSolDefinitiva() {
		return descSolDefinitiva;
	}

	public void setDescSolDefinitiva(String descSolDefinitiva) {
		this.descSolDefinitiva = descSolDefinitiva;
	}

	public String getChamarTelaProblema() {
		return chamarTelaProblema;
	}

	public void setChamarTelaProblema(String chamarTelaProblema) {
		this.chamarTelaProblema = chamarTelaProblema;
	}

	public String getInformacoesComplementares_serialize() {
		return informacoesComplementares_serialize;
	}

	public void setInformacoesComplementares_serialize(String informacoesComplementares_serialize) {
		this.informacoesComplementares_serialize = informacoesComplementares_serialize;
	}

	public IDto getInformacoesComplementares() {
		return informacoesComplementares;
	}

	public void setInformacoesComplementares(IDto informacoesComplementares) {
		this.informacoesComplementares = informacoesComplementares;
	}

	public String getFecharItensRelacionados() {
		return fecharItensRelacionados;
	}

	public void setFecharItensRelacionados(String fecharItensRelacionados) {
		this.fecharItensRelacionados = fecharItensRelacionados;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Collection getColArquivosUpload() {
		return colArquivosUpload;
	}

	public void setColArquivosUpload(Collection colArquivosUpload) {
		this.colArquivosUpload = colArquivosUpload;
	}

	public Integer getHiddenIdItemConfiguracao() {
		return hiddenIdItemConfiguracao;
	}

	public void setHiddenIdItemConfiguracao(Integer hiddenIdItemConfiguracao) {
		this.hiddenIdItemConfiguracao = hiddenIdItemConfiguracao;
	}

	/**
	 * @return the idProblemaRelacionado
	 */
	public Integer getIdProblemaRelacionado() {
		return idProblemaRelacionado;
	}

	/**
	 * @param idProblemaRelacionado the idProblemaRelacionado to set
	 */
	public void setIdProblemaRelacionado(Integer idProblemaRelacionado) {
		this.idProblemaRelacionado = idProblemaRelacionado;
	}

	/**
	 * @return the listProblemaRelacionadoDTO
	 */
	public List<ProblemaDTO> getListProblemaRelacionadoDTO() {
		return listProblemaRelacionadoDTO;
	}

	/**
	 * @param listProblemaRelacionadoDTO the listProblemaRelacionadoDTO to set
	 */
	public void setListProblemaRelacionadoDTO(List<ProblemaDTO> listProblemaRelacionadoDTO) {
		this.listProblemaRelacionadoDTO = listProblemaRelacionadoDTO;
	}

	public String getUnidadeDes() {
		return unidadeDes;
	}

	public void setUnidadeDes(String unidadeDes) {
		this.unidadeDes = unidadeDes;
	}

}