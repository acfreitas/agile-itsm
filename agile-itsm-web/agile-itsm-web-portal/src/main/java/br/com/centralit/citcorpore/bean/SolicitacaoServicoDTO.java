package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;
import br.com.citframework.util.DateTimeAdapter;
import br.com.citframework.util.DtoAdapter;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

import com.google.gson.annotations.Expose;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
@XmlAccessorType(XmlAccessType.FIELD)
public class SolicitacaoServicoDTO extends ObjetoNegocioFluxoDTO {

	private Integer idFluxo;
	private String acaoFluxo;
	private String alterarSituacao;
	private String aprovacao;
	private String atendimentoPresencial;
	private double atrasoSLA;
	private BaseConhecimentoDTO beanBaseConhecimento;
	private String isIframe;

	@XmlElement(name = "AtrasoSLAStr")
	private String atrasoSLAStr;

	private String caracteristica;
	private Collection colArquivosUpload;
	private List<ItemConfiguracaoDTO> colItensICSerialize;
	private List<RequisicaoMudancaDTO> colItensMudanca;
	private List<BaseConhecimentoDTO> colItensBaseConhecimento;
	private Collection colItensProblema;
	private List<SolicitacaoServicoEvtMonDTO> colSolicitacaoServicoEvtMon;
	private String complementoJustificativa;
	private String unidadeDes;
	private Integer priorityorder;

	private Integer rowNumOracle;

	public Integer getRowNumOracle() {
		return rowNumOracle;
	}

	public void setRowNumOracle(Integer rowNumOracle) {
		this.rowNumOracle = rowNumOracle;
	}

	private String localidade;

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUnidadeDes() {
		return unidadeDes;
	}

	public void setUnidadeDes(String unidadeDes) {
		this.unidadeDes = unidadeDes;
	}

	/**
	 * Valor do TOP List
	 *
	 * @author thyen.chang
	 */
	private Integer topList;

	public Integer getTopList() {
		return topList;
	}

	public void setTopList(Integer topList) {
		this.topList = topList;
	}

	@XmlElement(name = "Contrato")
	private String contrato;

	@XmlElement(name = "DataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataFim;

	@XmlElement(name = "DataHora")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHora;

	@XmlElement(name = "DataHoraCaptura")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraCaptura;

	@XmlElement(name = "DataHoraFim")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraFim;

	@XmlElement(name = "DataHoraInicio")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraInicio;

	@XmlElement(name = "DataHoraInicioSLA")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraInicioSLA;

	@XmlElement(name = "DataHoraInicioSLAStr")
	private String dataHoraInicioSLAStr;

	@XmlElement(name = "DataHoraLimite")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraLimite;

	@XmlElement(name = "DataHoraLimiteStr")
	private String dataHoraLimiteStr;

	private String dataHoraLimiteToString;

	@XmlElement(name = "DataHoraReativacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraReativacao;

	@XmlElement(name = "DataHoraReativacaoSLA")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraReativacaoSLA;

	@XmlElement(name = "DataHoraSolicitacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraSolicitacao;

	@XmlElement(name = "DataHoraSolicitacaoStr")
	private String dataHoraSolicitacaoStr;

	private String dataHoraSolicitacaoToString;

	@XmlElement(name = "DataHoraSuspensao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraSuspensao;

	@XmlElement(name = "DataHoraSuspensaoSLA")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraSuspensaoSLA;

	@XmlElement(name = "DataInicio")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataInicio;
	private String demanda;

	@XmlElement(name = "Descricao")
	private String descricao;

	private String descricaoSemFormatacao;
	private String descricaoForTitle;

	@XmlElement(name = "DescrSituacao")
	private String descrSituacao;

	@XmlElement(name = "DetalhamentoCausa")
	private String detalhamentoCausa;

	private String editar;

	@XmlElement(name = "EmailContato")
	private String emailcontato;
	
	@XmlElement(name = "emailResponsavel")
	private String emailResponsavel;

	private String enviaEmailAcoes;
	private String enviaEmailCriacao;
	private String enviaEmailFinalizacao;
	private String escalar;
	private String exibirCampoDescricao;
	private Integer qtdefilhas;
	private Integer qtdeItensConfiguracaoRelacionados;

	public Integer getQtdefilhas() {
		return qtdefilhas;
	}

	public void setQtdefilhas(Integer qtdefilhas) {
		this.qtdefilhas = qtdefilhas;
	}

	public Integer getQtdeItensConfiguracaoRelacionados() {
		return qtdeItensConfiguracaoRelacionados;
	}

	public void setQtdeItensConfiguracaoRelacionados(Integer qtdeItensConfiguracaoRelacionados) {
		this.qtdeItensConfiguracaoRelacionados = qtdeItensConfiguracaoRelacionados;
	}

	@XmlElement(name = "FaseAtual")
	private String faseAtual;

	private String filtroADPesq;

	@XmlElement(name = "GrupoAtual")
	private String grupoAtual;

	@XmlElement(name = "GrupoNivel1")
	private String grupoNivel1;

	private String houveMudanca;
	private Integer idAcordoNivelServico;
	private Integer idBaseConhecimento;

	@XmlElement(name = "IdCalendario")
	private Integer idCalendario;

	private Integer idCategoriaServico;
	private Integer idCategoriaSolucao;
	private Integer idCausaIncidente;
	private Integer idContatoSolicitacaoServico;

	@XmlElement(name = "IdContrato")
	private Integer idContrato;

	@XmlElement(name = "IdFaseAtual")
	private Integer idFaseAtual;

	private Integer idGrupo1;
	private Integer idGrupoDestino;
	private Integer idGrupo;

	@XmlElement(name = "IdInstanciaFluxo")
	private Integer idInstanciaFluxo;

	private Integer idItemConfiguracao;
	private Integer idItemConfiguracaoFilho;
	private Integer idJustificativa;

	@XmlElement(name = "IdLocalidade")
	private Integer idLocalidade;

	@XmlElement(name = "IdOrigem")
	private Integer idOrigem;

	@XmlElement(name = "IdPrioridade")
	private Integer idPrioridade;

	private Integer idPrioridadeAuto1;
	private Integer idProblema;
	private Integer idRequisicaoMudanca;

	@XmlElement(name = "IdServico")
	private Integer idServico;

	@XmlElement(name = "IdServicoContrato")
	private Integer idServicoContrato;

	@XmlElement(name = "IdSolicitacaoPai")
	private Integer idSolicitacaoPai;

	private Integer idSolicitacaoRelacionada;

	@XmlElement(name = "IdSolicitacaoServico")
	private Integer idSolicitacaoServico;

	private Integer idSolicitacaoIndividual;
	private Timestamp dataInicioAtendimento;

	private Integer idSolicitacaoServicoPesquisa;

	@XmlElement(name = "IdSolicitante")
	private Integer idSolicitante;

	@XmlElement(name = "IdTarefa")
	private Integer idTarefa;

	private Integer idTipoDemandaServico;
	private Integer idTipoProblema;
	private Integer idTipoServico;

	@XmlElement(name = "IdUltimaAprovacao")
	private Integer idUltimaAprovacao;

	@XmlElement(name = "IdUnidade")
	private Integer idUnidade;
	private Integer idUsuarioDestino;

	@XmlElement(name = "IdTarefaEncerramento")
	private Integer idTarefaEncerramento;

	@XmlElement(name = "Impacto")
	private String impacto;

	@XmlElement(name = "InformacoesComplementares")
	@XmlJavaTypeAdapter(DtoAdapter.class)
	@Expose
	private IDto informacoesComplementares;

	private String informacoesComplementares_serialize;
	private String informacoesComplementaresHTML;
	private String itemConfiguracao;
	private String hashPesquisaSatisfacao;
	private String urlSistema;
	private String linkPesquisaSatisfacao;
	private String messageId;
	private String nomeCategoriaServico;

	@XmlElement(name = "NomeContato")
	private String nomecontato;

	private String nomeItemConfiguracao;

	@XmlElement(name = "NomeServico")
	private String nomeServico;

	@XmlElement(name = "NomeSolicitante")
	private String nomeSolicitante;

	private String nomeElementoFluxo;

	@XmlElement(name = "CodigoExterno")
	private String codigoExterno;

	private String nomeTarefa;

	// paginacao
	private Integer totalItens;
	private Integer totalPagina;
	private Integer idItemFluxoTrabalho;

	public Integer getIdItemFluxoTrabalho() {
		return idItemFluxoTrabalho;
	}

	public void setIdItemFluxoTrabalho(Integer idItemFluxoTrabalho) {
		this.idItemFluxoTrabalho = idItemFluxoTrabalho;
	}

	/**
	 * Atributo para mapear retorno de consulta. nomeTipoDemandaServico Ã© atributo de TipoDemandaServicoDTO.
	 */
	private String nomeTipoDemandaServico;
	private String nomeTipoServico;
	@XmlElement(name = "NomeUnidadeResponsavel")
	private String nomeUnidadeResponsavel;
	@XmlElement(name = "NomeUnidadeSolicitante")
	private String nomeUnidadeSolicitante;
	@XmlElement(name = "NomeUsuario")
	private String nomeUsuario;
	private Integer numeroRegistros;
	@XmlElement(name = "Observacao")
	private String observacao;
	private String ordenacao;
	@XmlElement(name = "Origem")
	private String origem;
	private String palavraChave;
	private Boolean possuiFilho;
	@XmlElement(name = "Prazo")
	private String prazo;
	private Integer prazoCapturaHH;
	private Integer prazoCapturaMM;
	private Integer prazoHH;
	private Integer prazohhAnterior;
	private Integer prazoMM;
	private Integer prazommAnterior;
	@XmlElement(name = "Prioridade")
	private String prioridade;
	private Integer quantidade;
	private String ramal;
	private String reclassificar;
	private String registradoPor;
	private String registroexecucao;
	private String responsavel;
	@XmlElement(name = "Resposta")
	private String resposta;
	private Integer seqReabertura;
	private Integer sequenciaSolicitacao;
	@XmlElement(name = "Servico")
	private String servico;
	private String siglaGrupo;
	@XmlElement(name = "SiglaGrupo")
	private String situacao;
	@XmlElement(name = "Situacao")
	private String situacaoSLA;
	private String slaACombinar;
	@XmlElement(name = "Solicitante")
	private String solicitante;
	private String solicitanteUnidade;
	private String solucaoTemporaria;
	private String tarefa;
	private String telefonecontato;
	private Integer tempoAtendimentoHH;
	private Integer tempoAtendimentoMM;
	private Integer tempoAtendimentoSS;
	private Integer tempoAtrasoHH;
	private Integer tempoAtrasoMM;
	private Double tempoAuto;
	private Integer tempoCapturaHH;
	private Integer tempoCapturaMM;
	private Integer tempoCapturaSS;
	private Integer tempoDecorridoHH;
	private Integer tempoDecorridoMM;
	private String tipoUsuario;
	private String urgencia;

	private String numero;
	private String nome;
	private String nomeUsu;
	private String sla;
	private String situacaoAprovacao;
	private String validaBaseConhecimento;

	private String descricaoProblema;
	private Collection<SolicitacaoServicoDTO> listaSolicitacaoServico;

	private Integer paginaSelecionada;
	private String nomeContrato;

	private List<Object> listaPorcentagemSla;
	private Map<String, Object> mapPorcentagemSla;

	@XmlElement(name = "DataRegistro")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataRegistro;

	private String horaRegistro;
	private String dataCompleta;
	private String classificacao;
	private String dadosSolicitacao;
	private String situacaoFinal;
	private Integer idOcorrencia;
	private String situacaoAtual;

	private String solicitacaoAtrasada;

	private TipoSolicitacaoServico tipoSolicitacao;
	private Integer idRequisicaoProduto;
	private Integer idRequisicaoViagem;
	private Integer idRequisicaoPessoal;
	// Atributo auxiliar, não é salvo em banco
	private String possuiAnexo;
	private String vencendo;
	private String criouProblemaAutomatico;
	private Integer idUsuarioResponsavelAtual;

	// Colunas auxiliares da tabela de ocorrência, não são salvas em banco
	private Date dataRegistroOcorrencia;
	private String dataRegistroOcorrenciaStr;
	private String horaRegistroOcorrencia;
	private String categoriaOcorrencia;

	// campo auxiliar para o relatorio de incidentes nao resolvidos
	private String responsavelAtual;
	private Integer qtdDiasAberto;
	private String nomeGrupo;
	private String tipoAtribuicao;
	private String identificacaoTemplate;

	private String dataHoraFimStr;	private String tituloEmail;
	
	private Integer qtdeAnexos;

	/**
	 * @return the descricaoProblema
	 */
	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	/**
	 * @param descricaoProblema
	 *            the descricaoProblema to set
	 */
	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeUsu() {
		return nomeUsu;
	}

	public void setNomeUsu(String nomeUsu) {
		this.nomeUsu = nomeUsu;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the solicitacaoAtrasada
	 */
	public String getSolicitacaoAtrasada() {
		return solicitacaoAtrasada;
	}

	/**
	 * @param solicitacaoAtrasada
	 *            the solicitacaoAtrasada to set
	 */
	public void setSolicitacaoAtrasada(String solicitacaoAtrasada) {
		this.solicitacaoAtrasada = solicitacaoAtrasada;
	}

	private SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDTO;

	// Para o Relatorio ProblemaIncidentes
	private String tituloProblema;

	private String tituloBaseConhecimento;

	public String getTituloBaseConhecimento() {
		return tituloBaseConhecimento;
	}

	public void setTituloBaseConhecimento(String tituloBaseConhecimento) {
		this.tituloBaseConhecimento = tituloBaseConhecimento;
	}

	private UsuarioDTO usuarioDto;

	// atributos grid base conhecimento
	Integer idItemBaseConhecimento;
	private List<ConhecimentoSolicitacaoDTO> colConhecimentoSolicitacaoSerialize;
	private String nomeUsuarioResponsavelAtual;

	public boolean aprovada() {
		return aprovacao != null && aprovacao.equalsIgnoreCase("A") || aprovacao.equalsIgnoreCase("S");
	}

	public boolean atendida() {
		return this.situacao != null
				&& (this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.name()) || this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name()) || this.situacao
						.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Fechada.name()));
	}

	public boolean emAtendimento() {
		return this.situacao != null
				&& (this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.EmAndamento.name()) || this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Reaberta.name()));
	}

	public boolean encerrada() {
		return this.situacao != null && (this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Fechada.name()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitacaoServicoDTO other = (SolicitacaoServicoDTO) obj;
		if (idServico == null) {
			if (other.idServico != null)
				return false;
		} else if (!idServico.equals(other.idServico))
			return false;
		return true;
	}

	public boolean escalada() {
		return getIdGrupoAtual() != null;
	}

	public boolean finalizada() {
		return this.situacao != null
				&& (this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.name()) || this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name()) || this.situacao
						.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Fechada.name()));
	}

	public String getAcaoFluxo() {
		return acaoFluxo;
	}

	public String getAlterarSituacao() {
		return alterarSituacao;
	}

	public String getAprovacao() {
		return aprovacao;
	}

	public String getAtendimentoPresencial() {
		return atendimentoPresencial;
	}

	public double getAtrasoSLA() {
		return atrasoSLA;
	}

	public String getAtrasoSLAStr() {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			return atrasoSLAStr;
		} else {
			return "--";
		}
	}

	/**
	 * @return the caracteristica
	 */
	public String getCaracteristica() {
		return caracteristica;
	}

	public Collection getColArquivosUpload() {
		return colArquivosUpload;
	}

	/**
	 * @return the colItensICSerialize
	 */
	public List<ItemConfiguracaoDTO> getColItensICSerialize() {
		return colItensICSerialize;
	}

	/**
	 * @return the colItensMudanca
	 */
	public Collection getColItensMudanca() {
		return colItensMudanca;
	}

	/**
	 * @return the colItensProblema
	 */
	public Collection getColItensProblema() {
		return colItensProblema;
	}

	public List<SolicitacaoServicoEvtMonDTO> getColSolicitacaoServicoEvtMon() {
		return colSolicitacaoServicoEvtMon;
	}

	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}

	public String getContrato() {
		return contrato;
	}

	public String getDadosStr() {
		StringBuilder str = new StringBuilder();
		if (getIdSolicitacaoServico() != null) {
			str.append("Número da solicitação: " + getIdSolicitacaoServico() + "\n");
			if (getDataHoraSolicitacaoStr() != null) {
				str.append("Criada em: " + getDataHoraSolicitacaoStr() + "\n");
			}
			if (getDescrSituacao() != null) {
				str.append("Situação: " + getDescrSituacao() + "\n");
			}
			if (getSituacaoSLA() != null) {
				str.append("Situação do SLA: " + SituacaoSLA.valueOf(getSituacaoSLA()).getDescricao() + "\n");
			}
			if (getSituacaoSLA() == null || !getSituacaoSLA().equalsIgnoreCase("S")) {
				if (getPrazoHH() != null) {
					str.append("Tempo SLA: " + getSLAStr() + "\n");
				}
				if (getDataHoraLimiteStr() != null) {
					str.append("Data hora limite: " + getDataHoraLimiteStr() + "\n");
				}
				if (getGrupoAtual() != null)
					str.append("Grupo atual: " + getGrupoAtual() + "\n");
			} else {
				str.append("Tempo decorrido: " + getTempoDecorridoStr() + "\n");
			}
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
			if (getIdPrioridade() != null) {
				str.append("Prioridade (Código): " + getIdPrioridade() + "\n");
			}
		}
		return str.toString();
	}

	public Date getDataFim() {
		return dataFim;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public Timestamp getDataHoraCaptura() {
		return dataHoraCaptura;
	}

	public Timestamp getDataHoraFim() {
		return this.dataHoraFim;
	}

	public Timestamp getDataHoraInicio() {
		return this.dataHoraInicio;
	}

	public Timestamp getDataHoraInicioSLA() {
		return dataHoraInicioSLA;
	}

	public String getDataHoraInicioSLAStr() {
		return dataHoraInicioSLAStr;
	}

	public Timestamp getDataHoraLimite() {
		return dataHoraLimite;
	}

	public String getDataHoraLimiteStr() {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			return dataHoraLimiteStr;
		} else {
			return "--";
		}
	}

	/**
	 * Retorna DataHoraLimite no formato String de acordo com a Linguagem do Usuário. (Ex. PT, EN)
	 *
	 * @param language
	 * @return dataHoraLimiteStr - No formato String de acordo com a Linguagem do Usuário.
	 * @author valdoilo.damasceno
	 * @since 04.02.2014
	 */
	public String obterDataHoraLimiteStrWithLanguage(String language) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {

			this.dataHoraLimiteStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, this.dataHoraLimite, language);

			return dataHoraLimiteStr;

		} else {
			return "--";
		}
	}

	public String getDataHoraLimiteToString() {
		if (dataHoraLimite == null)
			return "--";
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			if (dataHoraLimiteStr == null) {
				return "";
			}
			return dataHoraLimite.toString();
		} else {
			return "--";
		}
	}

	public boolean getAtrasada() {
		if (dataHoraLimite == null)
			return false;
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			if (dataHoraLimiteStr == null) {
				return false;
			}
			try {
				if (UtilDatas.getDataHoraAtual().after(dataHoraLimite)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean getFalta1Hora() {
		if (dataHoraLimite == null)
			return false;
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			if (dataHoraLimiteStr == null) {
				return false;
			}
			long tempo = 0;
			try {
				if (UtilDatas.getDataHoraAtual().after(dataHoraLimite)) {
					return false;
				} else {
					tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraLimite, UtilDatas.getDataHoraAtual());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			int hh = 0;
			int mm = 0;
			if (this.prazoHH != null) {
				hh = this.prazoHH;
			}
			if (this.prazoMM != null) {
				mm = this.prazoMM;
			}
			if (hh == 0 && mm == 0) {
				return false;
			}
			long aux = (60 * 60 * 1000); // 1 hora
			if (tempo < aux) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getTempoFaltante() {
		if (dataHoraLimite == null)
			return "0";
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			if (dataHoraLimiteStr == null) {
				return "0";
			}
			long tempo = 0;
			try {
				if (UtilDatas.getDataHoraAtual().after(dataHoraLimite)) {
					return "-1";
				} else {
					tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraLimite, UtilDatas.getDataHoraAtual());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			int hh = 0;
			int mm = 0;
			if (this.prazoHH != null) {
				hh = this.prazoHH;
			}
			if (this.prazoMM != null) {
				mm = this.prazoMM;
			}
			if (hh == 0 && mm == 0) {
				return "0";
			}
			long aux30min = (30 * 60 * 1000); // 60 min
			long aux60min = (60 * 60 * 1000); // 60 min
			long aux90min = (90 * 60 * 1000); // 120 min
			long aux120min = (120 * 60 * 1000); // 120 min
			if (tempo <= aux30min) {
				return "30";
			}
			if (tempo <= aux60min) {
				return "60";
			}
			if (tempo <= aux90min) {
				return "90";
			}
			if (tempo <= aux120min) {
				return "120";
			}
			return "*";
		} else {
			return "0";
		}
	}

	public Timestamp getDataHoraReativacao() {
		return dataHoraReativacao;
	}

	public Timestamp getDataHoraReativacaoSLA() {
		return dataHoraReativacaoSLA;
	}

	public Timestamp getDataHoraSolicitacao() {
		return this.dataHoraSolicitacao;
	}

	public String getDataHoraSolicitacaoStr() {
		return dataHoraSolicitacaoStr;
	}

	/**
	 * Retorna DataHoraSolicitacao de acordo com a Linguagem do Usuário (Ex: PT, EN).
	 *
	 * @param language
	 *            - Linguagem do usuário.
	 * @return dataHoraSolicitacao no formato String de acordo com a linguage do usuário.
	 * @author valdoilo.damasceno
	 * @since 04.02.20124
	 */
	public String obterDataHoraSolicitacaoStrWithLanguage(String language) {
		if (this.dataHoraSolicitacao != null) {

			this.dataHoraSolicitacaoStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, this.dataHoraSolicitacao, language);
		}

		return dataHoraSolicitacaoStr;
	}

	public String getDataHoraSolicitacaoToString() {
		if (dataHoraSolicitacaoStr == null) {
			return "";
		}
		return dataHoraSolicitacaoStr.toString();
	}

	public Timestamp getDataHoraSuspensao() {
		return dataHoraSuspensao;
	}

	public Timestamp getDataHoraSuspensaoSLA() {
		return dataHoraSuspensaoSLA;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public String getDemanda() {
		return Util.tratarAspasSimples(this.demanda);
	}

	public String getDescricao() {
		// if (this.descricao != null && !StringUtils.isBlank(this.descricao)) {
		// return StringEscapeUtils.escapeJavaScript(this.descricao);
		// } else {
		// return this.descricao;
		// }
		return descricao;
	}

	public String getDescricaoSemFormatacao() {

		if (descricaoSemFormatacao != null) {
			descricaoSemFormatacao = Jsoup.parse(descricaoSemFormatacao).text();
		} else {
			descricaoSemFormatacao = "";
		}
		return descricaoSemFormatacao;
	}

	public String getDescricaoForTitle() {
		return this.descricaoForTitle;
	}

	public void setDescricaoForTitle(String descricaoForTitle) {
		if (descricaoForTitle != null) {
			this.descricaoForTitle = Jsoup.clean(descricaoForTitle, Whitelist.none());
		}
	}

	public String getDescrSituacao() {
		return this.descrSituacao;
	}

	public String getDetalhamentoCausa() {
		return detalhamentoCausa;
	}

	public String getEditar() {
		return editar;
	}

	/**
	 * @return the emailcontato
	 */
	public String getEmailcontato() {
		return Util.tratarAspasSimples(this.emailcontato);
	}

	public String getEnviaEmailAcoes() {
		return enviaEmailAcoes;
	}

	public String getEnviaEmailCriacao() {
		return enviaEmailCriacao;
	}

	public String getEnviaEmailFinalizacao() {
		return enviaEmailFinalizacao;
	}

	public String getEscalar() {
		return escalar;
	}

	/**
	 * @return the exibirCampoDescricao
	 */
	public String getExibirCampoDescricao() {
		return exibirCampoDescricao;
	}

	public String getFaseAtual() {
		return faseAtual;
	}

	public String getFiltroADPesq() {
		return filtroADPesq;
	}

	public String getGrupoAtual() {
		return grupoAtual;
	}

	public String getGrupoNivel1() {
		return grupoNivel1;
	}

	public String getHouveMudanca() {
		return houveMudanca;
	}

	/**
	 * @return the idAcordoNivelServico
	 */
	public Integer getIdAcordoNivelServico() {
		return idAcordoNivelServico;
	}

	/**
	 * @return the idBaseConhecimento
	 */
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	public Integer getIdCalendario() {
		return idCalendario;
	}

	public Integer getIdCategoriaServico() {
		return idCategoriaServico;
	}

	public Integer getIdCategoriaSolucao() {
		return idCategoriaSolucao;
	}

	public Integer getIdCausaIncidente() {
		return idCausaIncidente;
	}

	/**
	 * @return the idContatoSolicitacaoServico
	 */
	public Integer getIdContatoSolicitacaoServico() {
		return idContatoSolicitacaoServico;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public Integer getIdFaseAtual() {
		return idFaseAtual;
	}

	public Integer getIdGrupo1() {
		return idGrupo1;
	}

	public Integer getIdGrupoDestino() {
		return idGrupoDestino;
	}

	public Integer getIdInstanciaFluxo() {
		return idInstanciaFluxo;
	}

	/**
	 * @return the idItemConfiguracao
	 */
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}

	/**
	 * @return the idItemConfiguracaoFilho
	 */
	public Integer getIdItemConfiguracaoFilho() {
		return idItemConfiguracaoFilho;
	}

	public Integer getIdJustificativa() {
		return idJustificativa;
	}

	/**
	 * @return the idLocalidade
	 */
	public Integer getIdLocalidade() {
		return idLocalidade;
	}

	public Integer getIdOrigem() {
		return this.idOrigem;
	}

	public Integer getIdPrioridade() {
		return this.idPrioridade;
	}

	public Integer getIdPrioridadeAuto1() {
		return idPrioridadeAuto1;
	}

	/**
	 * @return the idProblema
	 */
	public Integer getIdProblema() {
		return idProblema;
	}

	/**
	 * @return the idRequisicaoMudanca
	 */
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}

	public Integer getIdServico() {
		return idServico;
	}

	public Integer getIdServicoContrato() {
		return this.idServicoContrato;
	}

	public Integer getIdSolicitacaoPai() {
		return idSolicitacaoPai;
	}

	/**
	 * @return the idSolicitacaoRelacionada
	 */
	public Integer getIdSolicitacaoRelacionada() {
		return idSolicitacaoRelacionada;
	}

	public Integer getIdSolicitacaoServico() {
		return this.idSolicitacaoServico;
	}

	public Integer getIdSolicitacaoServicoPesquisa() {
		return idSolicitacaoServicoPesquisa;
	}

	public Integer getIdSolicitante() {
		return this.idSolicitante;
	}

	public Integer getIdTarefa() {
		return idTarefa;
	}

	public Integer getIdTipoDemandaServico() {
		return idTipoDemandaServico;
	}

	public Integer getIdTipoProblema() {
		return this.idTipoProblema;
	}

	/**
	 * @return the idTipoServico
	 */
	public Integer getIdTipoServico() {
		return idTipoServico;
	}

	public Integer getIdUltimaAprovacao() {
		return idUltimaAprovacao;
	}

	public Integer getIdUnidade() {
		return this.idUnidade;
	}

	public Integer getIdUsuarioDestino() {
		return idUsuarioDestino;
	}

	public String getImpacto() {
		return impacto;
	}

	public IDto getInformacoesComplementares() {
		return informacoesComplementares;
	}

	public String getInformacoesComplementares_serialize() {
		return informacoesComplementares_serialize;
	}

	public String getInformacoesComplementaresHTML() {
		return informacoesComplementaresHTML;
	}

	/**
	 * @return the itemConfiguracao
	 */
	public String getItemConfiguracao() {
		return itemConfiguracao;
	}

	public String getLinkPesquisaSatisfacao() {
		return linkPesquisaSatisfacao;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	public String getNomeCategoriaServico() {
		return nomeCategoriaServico;
	}

	/**
	 * @return the nomecontato
	 */
	public String getNomecontato() {
		return Util.tratarAspasSimples(this.nomecontato);
	}

	public String getNomeItemConfiguracao() {
		return nomeItemConfiguracao;
	}

	public String getNomeServico() {
		return nomeServico;
	}

	/**
	 * @return the nomeSolicitante
	 */
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public String getNomeTarefa() {
		return nomeTarefa;
	}

	/**
	 * @return valor do atributo nomeTipoDemandaServico.
	 */
	public String getNomeTipoDemandaServico() {
		return nomeTipoDemandaServico;
	}

	public String getNomeTipoServico() {
		return nomeTipoServico;
	}

	public String getNomeUsuarioResponsavelAtual() {
		return nomeUsuarioResponsavelAtual;
	}

	public String getNomeUnidadeResponsavel() {
		return nomeUnidadeResponsavel;
	}

	public String getNomeUnidadeSolicitante() {
		return nomeUnidadeSolicitante;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public Integer getNumeroRegistros() {
		return numeroRegistros;
	}

	/**
	 * @return the localizacaofisica
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @return the ordenacao
	 */
	public String getOrdenacao() {
		return ordenacao;
	}

	public String getOrigem() {
		return origem;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	/**
	 * @return the possuiFilho
	 */
	public Boolean getPossuiFilho() {
		return possuiFilho;
	}

	public String getPrazo() {
		return prazo;
	}

	public Integer getPrazoCapturaHH() {
		return prazoCapturaHH;
	}

	public Integer getPrazoCapturaMM() {
		return prazoCapturaMM;
	}

	public Integer getPrazoHH() {
		return this.prazoHH;
	}

	public Integer getPrazohhAnterior() {
		return prazohhAnterior;
	}

	public Integer getPrazoMM() {
		return this.prazoMM;
	}

	public Integer getPrazommAnterior() {
		return prazommAnterior;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @return the ramal
	 */
	public String getRamal() {
		return ramal;
	}

	public String getReclassificar() {
		return reclassificar;
	}

	public String getRegistradoPor() {
		return registradoPor;
	}

	public String getRegistroexecucao() {
		return registroexecucao;
	}

	public String getResponsavel() {
		if (responsavel != null) {
			responsavel = responsavel.trim();
		}
		return responsavel;
	}

	public String getResposta() {
		return resposta;
	}

	public Integer getSeqReabertura() {
		return seqReabertura;
	}

	/**
	 * @return the sequenciaSolicitacao
	 */
	public Integer getSequenciaSolicitacao() {
		return sequenciaSolicitacao;
	}

	public String getServico() {
		return servico;
	}

	public String getServicoBusca() {
		return nomeServico;
	}

	public String getSiglaGrupo() {
		return siglaGrupo;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public String getSituacaoSLA() {
		return situacaoSLA;
	}

	public String getSlaACombinar() {
		if (slaACombinar == null) {
			return "N";
		}
		return slaACombinar;
	}

	public String getSLAStr() {
		String str = "";
		if (this.prazoHH != null)
			str += this.prazoHH + "h ";
		if (this.prazoMM != null)
			str += this.prazoMM + "m ";
		return str;
	}

	public String getSolicitante() {
		if (this.solicitante == null) {
			return null;
		}
		return this.solicitante.replaceAll("\"", " ");
	}

	public String getSolicitanteUnidade() {
		if (solicitanteUnidade != null) {
			solicitanteUnidade = solicitanteUnidade.trim();
		}
		return solicitanteUnidade;
	}

	public String getSolucaoTemporaria() {
		return Util.tratarAspasSimples(this.solucaoTemporaria);
	}

	public String getTarefa() {
		return tarefa;
	}

	/**
	 * @return the telefonecontato
	 */
	public String getTelefonecontato() {
		return telefonecontato;
	}

	public Integer getTempoAtendimentoHH() {
		return tempoAtendimentoHH;
	}

	public Integer getTempoAtendimentoMM() {
		return tempoAtendimentoMM;
	}

	public Integer getTempoAtrasoHH() {
		return tempoAtrasoHH;
	}

	public Integer getTempoAtrasoMM() {
		return tempoAtrasoMM;
	}

	public Double getTempoAuto() {
		return tempoAuto;
	}

	public Integer getTempoCapturaHH() {
		return tempoCapturaHH;
	}

	public Integer getTempoCapturaMM() {
		return tempoCapturaMM;
	}

	public Integer getTempoDecorridoHH() {
		return tempoDecorridoHH;
	}

	public Integer getTempoDecorridoMM() {
		return tempoDecorridoMM;
	}

	public String getTempoDecorridoStr() {
		String str = "";
		if (this.tempoDecorridoHH != null)
			str += this.tempoDecorridoHH + "h ";
		if (this.tempoDecorridoMM != null)
			str += this.tempoDecorridoMM + "m ";
		return str;
	}

	public BaseConhecimentoDTO getBeanBaseConhecimento() {
		return beanBaseConhecimento;
	}

	public void setBeanBaseConhecimento(BaseConhecimentoDTO beanBaseConhecimento) {
		this.beanBaseConhecimento = beanBaseConhecimento;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public String getUrgencia() {
		return urgencia;
	}

	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idServico == null) ? 0 : idServico.hashCode());
		return result;
	}

	public boolean reclassificada() {
		return this.situacao != null && (this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.ReClassificada.name()));
	}

	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}

	public void setAlterarSituacao(String alterarSituacao) {
		this.alterarSituacao = alterarSituacao;
	}

	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}

	public void setAtendimentoPresencial(String atendimentoPresencial) {
		this.atendimentoPresencial = atendimentoPresencial;
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

	public void setAtrasoSLAStr(String atrasoSLAStr) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			this.atrasoSLAStr = atrasoSLAStr;
		} else {
			this.atrasoSLAStr = "--";
		}
	}

	/**
	 * @param caracteristica
	 *            the caracteristica to set
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public void setColArquivosUpload(Collection colArquivosUpload) {
		this.colArquivosUpload = colArquivosUpload;
	}

	/**
	 * @param colItensICSerialize
	 *            the colItensICSerialize to set
	 */
	public void setColItensICSerialize(List<ItemConfiguracaoDTO> colItensICSerialize) {
		this.colItensICSerialize = colItensICSerialize;
	}

	/**
	 * @param colItensMudanca
	 *            the colItensMudanca to set
	 */
	public void setColItensMudanca(List<RequisicaoMudancaDTO> colItensMudanca) {
		this.colItensMudanca = colItensMudanca;
	}

	/**
	 * @param colItensProblema
	 *            the colItensProblema to set
	 */
	public void setColItensProblema(Collection colItensProblema) {
		this.colItensProblema = colItensProblema;
	}

	public void setColSolicitacaoServicoEvtMon(List<SolicitacaoServicoEvtMonDTO> colSolicitacaoServicoEvtMon) {
		this.colSolicitacaoServicoEvtMon = colSolicitacaoServicoEvtMon;
	}

	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public void setDataHoraCaptura(Timestamp dataHoraCaptura) {
		this.dataHoraCaptura = dataHoraCaptura;
	}

	public void setDataHoraFim(Timestamp parm) {
		this.dataHoraFim = parm;
	}

	public void setDataHoraInicio(Timestamp parm) {
		this.dataHoraInicio = parm;
	}

	public void setDataHoraInicioSLA(Timestamp dataHoraInicioSLA) {
		this.dataHoraInicioSLA = dataHoraInicioSLA;
		if (dataHoraInicioSLA != null) {
			this.dataHoraInicioSLAStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, this.dataHoraInicioSLA, null);
		}
	}

	public void setDataHoraInicioSLAStr(String dataHoraInicioSLAStr) {
		this.dataHoraInicioSLAStr = dataHoraInicioSLAStr;
	}

	public void setDataHoraLimite(Timestamp dataHoraLimite) {
		this.dataHoraLimite = dataHoraLimite;
		if (dataHoraLimite != null) {
			this.dataHoraLimiteStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, this.dataHoraLimite, null);
		}
	}

	public void setDataHoraLimiteStr(String dataHoraLimiteStr) {
		if (slaACombinar == null || slaACombinar.equalsIgnoreCase("N")) {
			this.dataHoraLimiteStr = dataHoraLimiteStr;
		} else {
			this.dataHoraLimiteStr = "--";
		}
	}

	public void setDataHoraLimiteToString(String dataHoraLimiteToString) {
		this.dataHoraLimiteToString = this.getDataHoraLimiteToString();
	}

	public void setDataHoraReativacao(Timestamp dataHoraReativacao) {
		this.dataHoraReativacao = dataHoraReativacao;
	}

	public void setDataHoraReativacaoSLA(Timestamp dataHoraReativacaoSLA) {
		this.dataHoraReativacaoSLA = dataHoraReativacaoSLA;
	}

	public void setDataHoraSolicitacao(Timestamp parm) {
		this.dataHoraSolicitacao = parm;
		if (parm != null) {
			this.dataHoraSolicitacaoStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, this.dataHoraSolicitacao, null);
		}
	}

	public void setDataHoraSolicitacaoStr(String dataHoraSolicitacaoStr) {
		this.dataHoraSolicitacaoStr = dataHoraSolicitacaoStr;
	}

	public void setDataHoraSolicitacaoToString(String dataHoraSolicitacaoToString) {
		this.dataHoraSolicitacaoToString = this.getDataHoraSolicitacaoToString();
	}

	public void setDataHoraSuspensao(Timestamp dataHoraSuspensao) {
		this.dataHoraSuspensao = dataHoraSuspensao;
	}

	public void setDataHoraSuspensaoSLA(Timestamp dataHoraSuspensaoSLA) {
		this.dataHoraSuspensaoSLA = dataHoraSuspensaoSLA;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}

	public void setDescricao(String parm) {

		if (parm != null && !StringUtils.isBlank(parm)) {
			this.setDescricaoSemFormatacao(parm);
		}
		this.descricao = parm;
	}

	public void setDescricaoSemFormatacao(String descricaoSemFormatacao) {
		this.descricaoSemFormatacao = descricaoSemFormatacao;
	}

	public void setDescrSituacao(String descrSituacao) {
		this.descrSituacao = descrSituacao;
	}

	public void setDetalhamentoCausa(String detalhamentoCausa) {
		this.detalhamentoCausa = detalhamentoCausa;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

	/**
	 * @param emailcontato
	 *            the emailcontato to set
	 */
	public void setEmailcontato(String emailcontato) {
		this.emailcontato = tratarCaracteresEspeciais(emailcontato);
	}

	public void setEnviaEmailAcoes(String enviaEmailAcoes) {
		this.enviaEmailAcoes = enviaEmailAcoes;
	}

	public void setEnviaEmailCriacao(String enviaEmailCriacao) {
		this.enviaEmailCriacao = enviaEmailCriacao;
	}

	public void setEnviaEmailFinalizacao(String enviaEmailFinalizacao) {
		this.enviaEmailFinalizacao = enviaEmailFinalizacao;
	}

	public void setEscalar(String escalar) {
		this.escalar = escalar;
	}

	/**
	 * @param exibirCampoDescricao
	 *            the exibirCampoDescricao to set
	 */
	public void setExibirCampoDescricao(String exibirCampoDescricao) {
		this.exibirCampoDescricao = exibirCampoDescricao;
	}

	public void setFaseAtual(String faseAtual) {
		this.faseAtual = faseAtual;
	}

	public void setFiltroADPesq(String filtroADPesq) {
		this.filtroADPesq = filtroADPesq;
	}

	public void setGrupoAtual(String grupoAtual) {
		this.grupoAtual = grupoAtual;
	}

	public void setGrupoNivel1(String grupoNivel1) {
		this.grupoNivel1 = grupoNivel1;
	}

	public void setHouveMudanca(String houveMudanca) {
		this.houveMudanca = houveMudanca;
	}

	/**
	 * @param idAcordoNivelServico
	 *            the idAcordoNivelServico to set
	 */
	public void setIdAcordoNivelServico(Integer idAcordoNivelServico) {
		this.idAcordoNivelServico = idAcordoNivelServico;
	}

	/**
	 * @param idBaseConhecimento
	 *            the idBaseConhecimento to set
	 */
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}

	public void setIdCategoriaServico(Integer idCategoriaServico) {
		this.idCategoriaServico = idCategoriaServico;
	}

	public void setIdCategoriaSolucao(Integer idCategoriaSolucao) {
		this.idCategoriaSolucao = idCategoriaSolucao;
	}

	public void setIdCausaIncidente(Integer idCausaIncidente) {
		this.idCausaIncidente = idCausaIncidente;
	}

	/**
	 * @param idContatoSolicitacaoServico
	 *            the idContatoSolicitacaoServico to set
	 */
	public void setIdContatoSolicitacaoServico(Integer idContatoSolicitacaoServico) {
		this.idContatoSolicitacaoServico = idContatoSolicitacaoServico;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public void setIdFaseAtual(Integer idFaseAtual) {
		this.idFaseAtual = idFaseAtual;
	}

	public void setIdGrupo1(Integer idGrupo1) {
		this.idGrupo1 = idGrupo1;
	}

	public void setIdGrupoDestino(Integer idGrupoDestino) {
		this.idGrupoDestino = idGrupoDestino;
	}

	public void setIdInstanciaFluxo(Integer idInstanciaFluxo) {
		this.idInstanciaFluxo = idInstanciaFluxo;
	}

	/**
	 * @param idItemConfiguracao
	 *            the idItemConfiguracao to set
	 */
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}

	/**
	 * @param idItemConfiguracaoFilho
	 *            the idItemConfiguracaoFilho to set
	 */
	public void setIdItemConfiguracaoFilho(Integer idItemConfiguracaoFilho) {
		this.idItemConfiguracaoFilho = idItemConfiguracaoFilho;
	}

	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}

	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}

	public void setIdOrigem(Integer parm) {
		this.idOrigem = parm;
	}

	public void setIdPrioridade(Integer parm) {
		this.idPrioridade = parm;
	}

	public void setIdPrioridadeAuto1(Integer idPrioridadeAuto1) {
		this.idPrioridadeAuto1 = idPrioridadeAuto1;
	}

	/**
	 * @param idProblema
	 *            the idProblema to set
	 */
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}

	/**
	 * @param idRequisicaoMudanca
	 *            the idRequisicaoMudanca to set
	 */
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}

	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}

	public void setIdServicoContrato(Integer parm) {
		this.idServicoContrato = parm;
	}

	public void setIdSolicitacaoPai(Integer idSolicitacaoPai) {
		this.idSolicitacaoPai = idSolicitacaoPai;
	}

	/**
	 * @param idSolicitacaoRelacionada
	 *            the idSolicitacaoRelacionada to set
	 */
	public void setIdSolicitacaoRelacionada(Integer idSolicitacaoRelacionada) {
		this.idSolicitacaoRelacionada = idSolicitacaoRelacionada;
	}

	public void setIdSolicitacaoServico(Integer parm) {
		this.idSolicitacaoServico = parm;
	}

	public void setIdSolicitacaoServicoPesquisa(Integer idSolicitacaoServicoPesquisa) {
		this.idSolicitacaoServicoPesquisa = idSolicitacaoServicoPesquisa;
	}

	public void setIdSolicitante(Integer parm) {
		this.idSolicitante = parm;
	}

	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}

	public void setIdTipoDemandaServico(Integer idTipoDemandaServico) {
		this.idTipoDemandaServico = idTipoDemandaServico;
	}

	public void setIdTipoProblema(Integer parm) {
		this.idTipoProblema = parm;
	}

	/**
	 * @param idTipoServico
	 *            the idTipoServico to set
	 */
	public void setIdTipoServico(Integer idTipoServico) {
		this.idTipoServico = idTipoServico;
	}

	public void setIdUltimaAprovacao(Integer idUltimaAprovacao) {
		this.idUltimaAprovacao = idUltimaAprovacao;
	}

	public void setIdUnidade(Integer parm) {
		this.idUnidade = parm;
	}

	public void setIdUsuarioDestino(Integer idUsuarioDestino) {
		this.idUsuarioDestino = idUsuarioDestino;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	public void setInformacoesComplementares(IDto informacoesComplementares) {
		this.informacoesComplementares = informacoesComplementares;
	}

	public void setInformacoesComplementares_serialize(String informacoesComplementares_serialize) {
		this.informacoesComplementares_serialize = informacoesComplementares_serialize;
	}

	public void setInformacoesComplementaresHTML(String informacoesComplementaresHTML) {
		this.informacoesComplementaresHTML = informacoesComplementaresHTML;
	}

	/**
	 * @param itemConfiguracao
	 *            the itemConfiguracao to set
	 */
	public void setItemConfiguracao(String itemConfiguracao) {
		this.itemConfiguracao = itemConfiguracao;
	}

	public void setLinkPesquisaSatisfacao(String linkPesquisaSatisfacao) {
		this.linkPesquisaSatisfacao = linkPesquisaSatisfacao;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setNomeCategoriaServico(String nomeCategoriaServico) {
		this.nomeCategoriaServico = nomeCategoriaServico;
	}

	/**
	 * @param nomecontato
	 *            the nomecontato to set
	 */
	public void setNomecontato(String nomecontato) {
		this.nomecontato = tratarCaracteresEspeciais(nomecontato);
	}

	public void setNomeItemConfiguracao(String nomeItemConfiguracao) {
		this.nomeItemConfiguracao = nomeItemConfiguracao;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	/**
	 * @param nomeSolicitante
	 *            the nomeSolicitante to set
	 */
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	public void setNomeTarefa(String nomeTarefa) {
		this.nomeTarefa = nomeTarefa;
	}

	/**
	 * Define valor do atributo nomeTipoDemandaServico.
	 *
	 * @param nomeTipoDemandaServico
	 */
	public void setNomeTipoDemandaServico(String nomeTipoDemandaServico) {
		this.nomeTipoDemandaServico = nomeTipoDemandaServico;
	}

	public void setNomeTipoServico(String nomeTipoServico) {
		this.nomeTipoServico = nomeTipoServico;
	}

	public void setNomeUnidadeResponsavel(String nomeUnidadeResponsavel) {
		this.nomeUnidadeResponsavel = nomeUnidadeResponsavel;
	}

	public void setNomeUnidadeSolicitante(String nomeUnidadeSolicitante) {
		this.nomeUnidadeSolicitante = nomeUnidadeSolicitante;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public void setNumeroRegistros(Integer numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}

	/**
	 * @param observacao
	 *            the localizacaofisica to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = tratarCaracteresEspeciais(observacao);
	}

	/**
	 * @param ordenacao
	 *            the ordenacao to set
	 */
	public void setOrdenacao(String ordenacao) {
		this.ordenacao = ordenacao;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	/**
	 * @param possuiFilho
	 *            the possuiFilho to set
	 */
	public void setPossuiFilho(Boolean possuiFilho) {
		this.possuiFilho = possuiFilho;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public void setPrazoCapturaHH(Integer prazoCapturaHH) {
		this.prazoCapturaHH = prazoCapturaHH;
	}

	public void setPrazoCapturaMM(Integer prazoCapturaMM) {
		this.prazoCapturaMM = prazoCapturaMM;
	}

	public void setPrazoHH(Integer parm) {
		this.prazoHH = parm;
	}

	public void setPrazohhAnterior(Integer prazohhAnterior) {
		this.prazohhAnterior = prazohhAnterior;
	}

	public void setPrazoMM(Integer parm) {
		this.prazoMM = parm;
	}

	public void setPrazommAnterior(Integer prazommAnterior) {
		this.prazommAnterior = prazommAnterior;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @param ramal
	 *            the ramal to set
	 */
	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public void setReclassificar(String reclassificar) {
		this.reclassificar = reclassificar;
	}

	public void setRegistradoPor(String registradoPor) {
		this.registradoPor = registradoPor;
	}

	public void setRegistroexecucao(String registroexecucao) {
		this.registroexecucao = registroexecucao;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public void setNomeUsuarioResponsavelAtual(String nomeUsuarioResponsavelAtual) {
		this.nomeUsuarioResponsavelAtual = nomeUsuarioResponsavelAtual;
	}

	public void setResposta(String parm) {
		this.resposta = parm;
	}

	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
	}

	/**
	 * @param sequenciaSolicitacao
	 *            the sequenciaSolicitacao to set
	 */
	public void setSequenciaSolicitacao(Integer sequenciaSolicitacao) {
		this.sequenciaSolicitacao = sequenciaSolicitacao;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public void setServicoBusca(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public void setSiglaGrupo(String siglaGrupo) {
		this.siglaGrupo = siglaGrupo;
	}

	public void setSituacao(String parm) {
		this.descrSituacao = "";
		this.situacao = parm;
		try {
			if (this.situacao != null)
				this.descrSituacao = SituacaoSolicitacaoServico.valueOf(this.situacao.trim()).getDescricao();
		} catch (Exception e) {
		}
	}

	public void setSituacaoSLA(String situacaoSLA) {
		this.situacaoSLA = situacaoSLA;
	}

	public void setSlaACombinar(String slaACombinar) {
		this.slaACombinar = slaACombinar;
	}

	public void setSolicitante(String solicitante) {
		if (solicitante == null) {
			this.solicitante = null;
			return;
		}
		this.solicitante = solicitante.replaceAll("\"", " ");
	}

	public void setSolicitanteUnidade(String solicitanteUnidade) {
		this.solicitanteUnidade = solicitanteUnidade;
	}

	public void setSolucaoTemporaria(String solucaoTemporaria) {
		this.solucaoTemporaria = solucaoTemporaria;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	/**
	 * @param telefonecontato
	 *            the telefonecontato to set
	 */
	public void setTelefonecontato(String telefonecontato) {
		this.telefonecontato = telefonecontato;
	}

	public void setTempoAtendimentoHH(Integer tempoAtendimentoHH) {
		this.tempoAtendimentoHH = tempoAtendimentoHH;
	}

	public void setTempoAtendimentoMM(Integer tempoAtendimentoMM) {
		this.tempoAtendimentoMM = tempoAtendimentoMM;
	}

	public void setTempoAtrasoHH(Integer tempoAtrasoHH) {
		this.tempoAtrasoHH = tempoAtrasoHH;
	}

	public void setTempoAtrasoMM(Integer tempoAtrasoMM) {
		this.tempoAtrasoMM = tempoAtrasoMM;
	}

	public void setTempoAuto(Double tempoAuto) {
		this.tempoAuto = tempoAuto;
	}

	public void setTempoCapturaHH(Integer tempoCapturaHH) {
		this.tempoCapturaHH = tempoCapturaHH;
	}

	public void setTempoCapturaMM(Integer tempoCapturaMM) {
		this.tempoCapturaMM = tempoCapturaMM;
	}

	public void setTempoDecorridoHH(Integer tempoDecorridoHH) {
		this.tempoDecorridoHH = tempoDecorridoHH;
	}

	public void setTempoDecorridoMM(Integer tempoDecorridoMM) {
		this.tempoDecorridoMM = tempoDecorridoMM;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}

	public List<ConhecimentoSolicitacaoDTO> getColConhecimentoSolicitacaoSerialize() {
		return colConhecimentoSolicitacaoSerialize;
	}

	public void setColConhecimentoSolicitacaoSerialize(List<ConhecimentoSolicitacaoDTO> colConhecimentoSolicitacaoSerialize) {
		this.colConhecimentoSolicitacaoSerialize = colConhecimentoSolicitacaoSerialize;
	}

	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public boolean suspensa() {
		return this.situacao != null && (this.situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Suspensa.name()));
	}

	public Integer getIdItemBaseConhecimento() {
		return idItemBaseConhecimento;
	}

	public void setIdItemBaseConhecimento(Integer idItemBaseConhecimento) {
		this.idItemBaseConhecimento = idItemBaseConhecimento;
	}

	/**
	 * Tratar caracteres especiais.
	 *
	 * @param valor
	 * @return String
	 * @author Vadoilo Damasceno
	 */
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

	public SolicitacaoServicoQuestionarioDTO getSolicitacaoServicoQuestionarioDTO() {
		return solicitacaoServicoQuestionarioDTO;
	}

	public void setSolicitacaoServicoQuestionarioDTO(SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDTO) {
		this.solicitacaoServicoQuestionarioDTO = solicitacaoServicoQuestionarioDTO;
	}

	public Integer getIdTarefaEncerramento() {
		return idTarefaEncerramento;
	}

	public void setIdTarefaEncerramento(Integer idTarefaEncerramento) {
		this.idTarefaEncerramento = idTarefaEncerramento;
	}

	public String getTituloProblema() {
		return tituloProblema;
	}

	public void setTituloProblema(String tituloProblema) {
		this.tituloProblema = tituloProblema;
	}

	public Integer getTotalPagina() {
		return totalPagina;
	}

	public void setTotalPagina(Integer totalPagina) {
		this.totalPagina = totalPagina;
	}

	public Integer getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(Integer totalItens) {
		this.totalItens = totalItens;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}

	public String getSituacaoAprovacao() {
		return situacaoAprovacao;
	}

	public void setSituacaoAprovacao(String situacaoAprovacao) {
		this.situacaoAprovacao = situacaoAprovacao;
	}

	public String getValidaBaseConhecimento() {
		return validaBaseConhecimento;
	}

	public void setValidaBaseConhecimento(String validaBaseConhecimento) {
		this.validaBaseConhecimento = validaBaseConhecimento;
	}

	public String getNomeElementoFluxo() {
		return nomeElementoFluxo;
	}

	public void setNomeElementoFluxo(String nomeElementoFluxo) {
		this.nomeElementoFluxo = nomeElementoFluxo;
	}

	public Collection<SolicitacaoServicoDTO> getListaSolicitacaoServico() {
		return listaSolicitacaoServico;
	}

	public void setListaSolicitacaoServico(Collection<SolicitacaoServicoDTO> listaSolicitacaoServico) {
		this.listaSolicitacaoServico = listaSolicitacaoServico;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getHoraRegistro() {
		return horaRegistro;
	}

	public void setHoraRegistro(String horaRegistro) {
		this.horaRegistro = horaRegistro;
	}

	public String getDataCompleta() {
		return dataCompleta;
	}

	public void setDataCompleta(String dataCompleta) {
		this.dataCompleta = dataCompleta;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getPossuiAnexo() {
		return possuiAnexo;
	}

	public void setPossuiAnexo(String possuiAnexo) {
		this.possuiAnexo = possuiAnexo;
	}

	public String getDadosSolicitacao() {
		return dadosSolicitacao;
	}

	public void setDadosSolicitacao(String dadosSolicitacao) {
		this.dadosSolicitacao = dadosSolicitacao;
	}

	public String getSituacaoFinal() {
		return getDescrSituacao();
	}

	public Integer getIdOcorrencia() {
		return idOcorrencia;
	}

	public void setIdOcorrencia(Integer idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}

	public String getSituacaoAtual() {
		return situacaoAtual;
	}

	public void setSituacaoAtual(String situacaoAtual) {
		this.situacaoAtual = situacaoAtual;
	}

	public List<Object> getListaPorcentagemSla() {
		return listaPorcentagemSla;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setListaPorcentagemSla(List<Object> listaPorcentagemSla) {
		this.listaPorcentagemSla = listaPorcentagemSla;
	}

	public Map<String, Object> getMapPorcentagemSla() {
		return mapPorcentagemSla;
	}

	public void setMapPorcentagemSla(Map<String, Object> mapPorcentagemSla) {
		this.mapPorcentagemSla = mapPorcentagemSla;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public TipoSolicitacaoServico getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(TipoSolicitacaoServico tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public Integer getIdRequisicaoProduto() {
		return idRequisicaoProduto;
	}

	public void setIdRequisicaoProduto(Integer idRequisicaoProduto) {
		this.idRequisicaoProduto = idRequisicaoProduto;
	}

	public Integer getIdRequisicaoViagem() {
		return idRequisicaoViagem;
	}

	public void setIdRequisicaoViagem(Integer idRequisicaoViagem) {
		this.idRequisicaoViagem = idRequisicaoViagem;
	}

	public Integer getIdRequisicaoPessoal() {
		return idRequisicaoPessoal;
	}

	public void setIdRequisicaoPessoal(Integer idRequisicaoPessoal) {
		this.idRequisicaoPessoal = idRequisicaoPessoal;
	}

	public String getNomeContrato() {
		return nomeContrato;
	}

	public void setNomeContrato(String nomeContrato) {
		this.nomeContrato = nomeContrato;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getVencendo() {
		return vencendo;
	}

	public void setVencendo(String vencendo) {
		this.vencendo = vencendo;
	}

	/**
	 * @return the idUsuarioResponsavelAtual
	 */
	public Integer getIdUsuarioResponsavelAtual() {
		return idUsuarioResponsavelAtual;
	}

	/**
	 * @param idUsuarioResponsavelAtual
	 *            the idUsuarioResponsavelAtual to set
	 */
	public void setIdUsuarioResponsavelAtual(Integer idUsuarioResponsavelAtual) {
		this.idUsuarioResponsavelAtual = idUsuarioResponsavelAtual;
	}

	public String getCriouProblemaAutomatico() {
		return criouProblemaAutomatico;
	}

	public void setCriouProblemaAutomatico(String criouProblemaAutomatico) {
		this.criouProblemaAutomatico = criouProblemaAutomatico;
	}

	public List<BaseConhecimentoDTO> getColItensBaseConhecimento() {
		return colItensBaseConhecimento;
	}

	public void setColItensBaseConhecimento(List<BaseConhecimentoDTO> colItensBaseConhecimento) {
		this.colItensBaseConhecimento = colItensBaseConhecimento;
	}

	public Date getDataRegistroOcorrencia() {
		return dataRegistroOcorrencia;
	}

	public void setDataRegistroOcorrencia(Date dataRegistroOcorrencia) {
		this.dataRegistroOcorrencia = dataRegistroOcorrencia;
	}

	public String getHoraRegistroOcorrencia() {
		return horaRegistroOcorrencia;
	}

	public void setHoraRegistroOcorrencia(String horaRegistroOcorrencia) {
		this.horaRegistroOcorrencia = horaRegistroOcorrencia;
	}

	public String getCategoriaOcorrencia() {
		return categoriaOcorrencia;
	}

	public void setCategoriaOcorrencia(String categoriaOcorrencia) {
		this.categoriaOcorrencia = categoriaOcorrencia;
	}

	public String getDataRegistroOcorrenciaStr() {
		return dataRegistroOcorrenciaStr;
	}

	public void setDataRegistroOcorrenciaStr(String dataRegistroOcorrenciaStr) {
		this.dataRegistroOcorrenciaStr = dataRegistroOcorrenciaStr;
	}

	/**
	 * 25/09/2013 Trata a internacionalização de acordo com a situação Foi verificado que existem todos as situações internacionalizadas em citcorpore.comum.{situacao} sendo (situacao} em minusculo
	 *
	 * @param request
	 * @return String
	 * @author uelen.pereira
	 */
	public String obterSituacaoInternacionalizada(HttpServletRequest request) {

		return UtilI18N.internacionaliza(request, "citcorpore.comum." + this.situacao.trim().toLowerCase());
	}

	public String getIsIframe() {
		return isIframe;
	}

	public void setIsIframe(String isIframe) {
		this.isIframe = isIframe;
	}

	public String getResponsavelAtual() {
		return responsavelAtual;
	}

	public void setResponsavelAtual(String responsavelAtual) {
		this.responsavelAtual = responsavelAtual;
	}

	public Integer getQtdDiasAberto() {
		return qtdDiasAberto;
	}

	public void setQtdDiasAberto(Integer qtdDiasAberto) {
		this.qtdDiasAberto = qtdDiasAberto;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getDataHoraFimStr() {
		return this.dataHoraFimStr;
	}

	public void setDataHoraFimStr(String parm) {
		this.dataHoraFimStr = parm;
	}

	/**
	 * @return the tituloEmail
	 */
	public String getTituloEmail() {
		return tituloEmail;
	}

	/**
	 * @param tituloEmail the tituloEmail to set
	 */
	public void setTituloEmail(String tituloEmail) {
		this.tituloEmail = tituloEmail;
	}

	private Double latitude;
	private Double longitude;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	public Integer getIdSolicitacaoIndividual() {
		return idSolicitacaoIndividual;
	}

	public void setIdSolicitacaoIndividual(Integer idsolicitacaoIndividual) {
		this.idSolicitacaoIndividual = idsolicitacaoIndividual;
	}

	public Timestamp getDataInicioAtendimento() {
		return dataInicioAtendimento;
	}

	public void setDataInicioAtendimento(final Timestamp dataInicioAtendimento) {
		this.dataInicioAtendimento = dataInicioAtendimento;
	}

	/**
	 * @return the tipoAtribuicao
	 */
	public String getTipoAtribuicao() {
		return tipoAtribuicao;
	}

	/**
	 * @param tipoAtribuicao the tipoAtribuicao to set
	 */
	public void setTipoAtribuicao(String tipoAtribuicao) {
		this.tipoAtribuicao = tipoAtribuicao;
	}

	public Integer getPriorityorder() {
		return priorityorder;
	}

	public void setPriorityorder(Integer priorityorder) {
		this.priorityorder = priorityorder;
	}

	public String getIdentificacaoTemplate() {
		return identificacaoTemplate;
	}

	public void setIdentificacaoTemplate(String identificacaoTemplate) {
		this.identificacaoTemplate = identificacaoTemplate;
	}


	public Integer getIdFluxo() {
		return idFluxo;
	}

	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}


	public String getHashPesquisaSatisfacao() {
		return hashPesquisaSatisfacao;
	}

	public void setHashPesquisaSatisfacao(String hashPesquisaSatisfacao) {
		this.hashPesquisaSatisfacao = hashPesquisaSatisfacao;
	}

	public String getUrlSistema() {
		return urlSistema;
	}

	public void setUrlSistema(String urlSistema) {
		this.urlSistema = urlSistema;
	}

	/**
	 * @return the emailResponsavel
	 */
	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	/**
	 * @param emailResponsavel the emailResponsavel to set
	 */
	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public Integer getTempoCapturaSS() {
		return tempoCapturaSS;
	}

	public void setTempoCapturaSS(Integer tempoCapturaSS) {
		this.tempoCapturaSS = tempoCapturaSS;
	}

	public Integer getTempoAtendimentoSS() {
		return tempoAtendimentoSS;
	}

	public void setTempoAtendimentoSS(Integer tempoAtendimentoSS) {
		this.tempoAtendimentoSS = tempoAtendimentoSS;
	}

	/**
	 * @return the qtdeAnexos
	 */
	public Integer getQtdeAnexos() {
		return qtdeAnexos;
	}

	/**
	 * @param qtdeAnexos the qtdeAnexos to set
	 */
	public void setQtdeAnexos(Integer qtdeAnexos) {
		this.qtdeAnexos = qtdeAnexos;
	}
	
}