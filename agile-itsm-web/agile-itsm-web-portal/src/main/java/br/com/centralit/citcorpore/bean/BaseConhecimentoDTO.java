/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;

import br.com.citframework.dto.IDto;

/**
 * DTO de BaseConhecimento.
 * 
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings("rawtypes")
public class BaseConhecimentoDTO implements IDto {

	private static final long serialVersionUID = 3913360778332921835L;

	public static Integer CONHECIMENTO = 1;

	public static Integer EVENTO = 2;

	public static Integer MUDANCA = 3;

	public static Integer INCIDENTE = 4;

	public static Integer SERVICO = 5;

	public static Integer PROBLEMA = 6;

	public static String CONFIDENCIAL = "C";

	public static String PUBLICO = "P";

	public static String INTERNO = "I";

	public static String EMDESENHO = "DS";

	public static String EMREVISAO = "ERV";

	public static String REVISADO = "RV";

	public static String EMAVALIACAO = "EAV";

	public static String AVALIADO = "AV";

	private Integer idBaseConhecimento;

	private Integer idPasta;

	private Date dataInicio;

	private Date dataFim;

	private String titulo;

	private String conteudo;

	private String status;

	private Integer idBaseConhecimentoPai;

	private Date dataExpiracao;

	private Integer contadorCliques;

	private String linkDaPastaBaseConhecimento;

	private String palavrasChave;

	private List comentarios;

	private String media;

	private String votos;

	private String termoPesquisa;

	private String versao;

	private String termoPesquisaNota;

	private String nomePasta;

	private String semComentarios;

	private String acessado;

	private String ultimaVersao;

	private Integer idUsuarioAutor;

	private Integer idUsuarioAprovador;

	private String autor;

	private String aprovador;

	private Integer idUsuarioAcesso;

	private String nomeUsuarioAcesso;

	private String fonteReferencia;

	private String ultimoAcesso;

	private Timestamp dataHoraAcesso;

	private Date dataPublicacao;

	private Collection<ImportanciaConhecimentoUsuarioDTO> listImportanciaConhecimentoUsuario;

	private Collection<ImportanciaConhecimentoGrupoDTO> listImportanciaConhecimentoGrupo;

	private Collection<BaseConhecimentoRelacionadoDTO> listBaseConhecimentoRelacionado;

	private String justificativaObservacao;

	private Integer idConhecimentoRelacionado;

	private Integer idNotificacao;

	private String tituloNotificacao;

	private String tipoNotificacao;

	private ArrayList<NotificacaoUsuarioDTO> listaDeUsuarioNotificacao;

	private ArrayList<NotificacaoGrupoDTO> listaDeGrupoNotificacao;

	private String faq;

	private String origem;

	private Integer idUsuarioAutorPesquisa;

	private Integer idUsuarioAprovadorPesquisa;

	private Date dataInicioPesquisa;

	private Date dataPublicacaoPesquisa;

	private Integer grauImportancia;

	private String arquivado;

	private Integer idHistoricoBaseConhecimento;

	private String privacidade;
	
	private String situacao;
	

	private String ocultarConteudo;
	
	private Integer idItemConfiguracao;

	private Integer idProblema;

	private Integer idRequisicaoMudanca;
	
	private Integer idSolicitacaoServico;

	private Integer sequenciaBaseConhecimento;
	
	private String iframe;

	private String amDoc;

	private String gerenciamentoDisponibilidade;
	private String direitoAutoral;
	private String legislacao;

	private List<ItemConfiguracaoDTO> colItensICSerialize;
	private List<RequisicaoMudancaDTO> colItensMudanca;
	private List<ProblemaDTO> colItensProblema;
	private List<SolicitacaoServicoDTO> colItensIncidentes;
	private List<RequisicaoLiberacaoDTO> colItensLiberacao;

	private ArrayList<EventoMonitConhecimentoDTO> listEventoMonitoramento;

	private Date dataInicioPublicacao;
	private Date dataFimPublicacao;
	private String conteudoSemFormatacao;

	private Date dataInicioExpiracao;
	private Date dataFimExpiracao;
	
	private Date dataInicioAcesso;
	private Date dataFimAcesso;

	// Atributos para Relatório
	private Integer qtdPublicados = 0;
	private Integer qtdNaoPublicados = 0;
	private Integer qtdAcessados = 0;
	private Integer qtdAvaliados = 0;
	private Integer qtdExcluidos = 0;
	private Integer qtdArquivados = 0;
	private Integer qtdAtualizados = 0;
	private Integer qtdRestaurados = 0;
	private Integer tipoFaq = 0;
	private Integer qtdDocumentos = 0;
	private Integer qtdErroConhecido = 0;
	private String nomeUsuario = "";
	private Integer qtdConhecimentoPorUsuario = 0;
	private String nomeAprovador = "";
	private Integer qtdConhecimentoPorAprovador = 0;
	private String nomeOrigem = "";
	private Integer qtdConhecimentoPublicadoPorOrigem = 0;
	private Integer qtdConhecimentoNaoPublicadoPorOrigem = 0;
	private String vinculaConhecimentoServico = "";
	private String identificacao = "";
	private Integer idSolicitacaoServicoIncidente = 0;
	private Integer idSolicitacaoServicoRequisicao = 0;

	private List<BaseConhecimentoDTO> listaIncidente;
	private List<BaseConhecimentoDTO> listaRequisitos;
	private List<BaseConhecimentoDTO> listaProblema;
	private List<BaseConhecimentoDTO> listaMudanca;
	private List<BaseConhecimentoDTO> listaIC;
	private List<BaseConhecimentoDTO> listaServico;
	
	private String erroConhecido;
	
	//atributos liberação
	private Integer idRequisicaoLiberacao;
	
	
	/**
	 * @return the colItensICSerialize
	 */
	public List<ItemConfiguracaoDTO> getColItensICSerialize() {
		return colItensICSerialize;
	}

	/**
	 * @param colItensICSerialize
	 *            the colItensICSerialize to set
	 */
	public void setColItensICSerialize(List<ItemConfiguracaoDTO> colItensICSerialize) {
		this.colItensICSerialize = colItensICSerialize;
	}

	/**
	 * @return the colItensMudanca
	 */
	public List<RequisicaoMudancaDTO> getColItensMudanca() {
		return colItensMudanca;
	}

	/**
	 * @param colItensMudanca
	 *            the colItensMudanca to set
	 */
	public void setColItensMudanca(List<RequisicaoMudancaDTO> colItensMudanca) {
		this.colItensMudanca = colItensMudanca;
	}

	/**
	 * @return the colItensProblema
	 */
	public List<ProblemaDTO> getColItensProblema() {
		return colItensProblema;
	}

	/**
	 * @param colItensProblema
	 *            the colItensProblema to set
	 */
	public void setColItensProblema(List<ProblemaDTO> colItensProblema) {
		this.colItensProblema = colItensProblema;
	}

	/**
	 * @return the colItensIncidentes
	 */
	public List<SolicitacaoServicoDTO> getColItensIncidentes() {
		return colItensIncidentes;
	}

	/**
	 * @param colItensIncidentes
	 *            the colItensIncidentes to set
	 */
	public void setColItensIncidentes(List<SolicitacaoServicoDTO> colItensIncidentes) {
		this.colItensIncidentes = colItensIncidentes;
	}

	/**
	 * @return the votos
	 */
	public String getVotos() {
		return votos;
	}

	/**
	 * @param votos
	 *            the votos to set
	 */
	public void setVotos(String votos) {
		this.votos = votos;
	}

	/**
	 * @return valor do atributo idBaseConhecimento.
	 */
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	/**
	 * Define valor do atributo idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 */
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	/**
	 * @return valor do atributo idPasta.
	 */
	public Integer getIdPasta() {
		return idPasta;
	}

	/**
	 * Define valor do atributo idPasta.
	 * 
	 * @param idPasta
	 */
	public void setIdPasta(Integer idPasta) {
		this.idPasta = idPasta;
	}

	/**
	 * @return valor do atributo dataInicio.
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * Define valor do atributo dataInicio.
	 * 
	 * @param dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return valor do atributo dataFim.
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * Define valor do atributo dataFim.
	 * 
	 * @param dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return valor do atributo titulo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Define valor do atributo titulo.
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return valor do atributo conteudo.
	 */
	public String getConteudo() {
		return conteudo;
	}

	/**
	 * Define valor do atributo conteudo.
	 * 
	 * @param conteudo
	 */
	public void setConteudo(String conteudo) {

		if (conteudo != null && !StringUtils.isBlank(conteudo)) {
			this.setConteudoSemFormatacao(conteudo);
		}

		this.conteudo = conteudo;
	}

	/**
	 * @return valor do atributo status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Define valor do atributo status.
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return valor do atributo idBaseConhecimentoPai.
	 */
	public Integer getIdBaseConhecimentoPai() {
		return idBaseConhecimentoPai;
	}

	/**
	 * Define valor do atributo idBaseConhecimentoPai.
	 * 
	 * @param idBaseConhecimentoPai
	 */
	public void setIdBaseConhecimentoPai(Integer idBaseConhecimentoPai) {
		this.idBaseConhecimentoPai = idBaseConhecimentoPai;
	}

	/**
	 * @return valor do atributo dataExpiracao.
	 */
	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	/**
	 * Define valor do atributo dataExpiracao.
	 * 
	 * @param dataExpiracao
	 */
	public void setDataExpiracao(Date dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	/**
	 * @return valor do atributo contadorCliques.
	 */
	public Integer getContadorCliques() {
		return contadorCliques;
	}

	/**
	 * Define valor do atributo contadorCliques.
	 * 
	 * @param contadorCliques
	 */
	public void setContadorCliques(Integer contadorCliques) {
		this.contadorCliques = contadorCliques;
	}

	/**
	 * @return valor do atributo linkDaPastaBaseConhecimento.
	 */
	public String getLinkDaPastaBaseConhecimento() {
		return linkDaPastaBaseConhecimento;
	}

	/**
	 * Define valor do atributo linkDaPastaBaseConhecimento.
	 * 
	 * @param linkDaPastaBaseConhecimento
	 */
	public void setLinkDaPastaBaseConhecimento(String linkDaPastaBaseConhecimento) {
		this.linkDaPastaBaseConhecimento = linkDaPastaBaseConhecimento;
	}

	public String getPalavrasChave() {
		return palavrasChave;
	}

	public void setPalavrasChave(String palavrasChave) {
		this.palavrasChave = palavrasChave;
	}

	/**
	 * @return valor do atributo comentarios.
	 */
	public List getComentarios() {
		return comentarios;
	}

	/**
	 * Define valor do atributo comentarios.
	 * 
	 * @param comentarios
	 */
	public void setComentarios(List comentarios) {
		this.comentarios = comentarios;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getTermoPesquisaNota() {
		return termoPesquisaNota;
	}

	public void setTermoPesquisaNota(String termoPesquisaNota) {
		this.termoPesquisaNota = termoPesquisaNota;
	}

	/**
	 * @return the nomePasta
	 */
	public String getNomePasta() {
		return nomePasta;
	}

	/**
	 * @param nomePasta
	 *            the nomePasta to set
	 */
	public void setNomePasta(String nomePasta) {
		this.nomePasta = nomePasta;
	}

	/**
	 * @return the semComentarios
	 */
	public String getSemComentarios() {
		return semComentarios;
	}

	/**
	 * @param semComentarios
	 *            the semComentarios to set
	 */
	public void setSemComentarios(String semComentarios) {
		this.semComentarios = semComentarios;
	}

	/**
	 * @return the acessado
	 */
	public String getAcessado() {
		return acessado;
	}

	/**
	 * @param acessado
	 *            the acessado to set
	 */
	public void setAcessado(String acessado) {
		this.acessado = acessado;
	}

	/**
	 * @return the ultimaVersao
	 */
	public String getUltimaVersao() {
		return ultimaVersao;
	}

	/**
	 * @param ultimaVersao
	 *            the ultimaVersao to set
	 */
	public void setUltimaVersao(String ultimaVersao) {
		this.ultimaVersao = ultimaVersao;
	}

	/**
	 * @return the idUsuarioAutor
	 */
	public Integer getIdUsuarioAutor() {
		return idUsuarioAutor;
	}

	/**
	 * @param idUsuarioAutor
	 *            the idUsuarioAutor to set
	 */
	public void setIdUsuarioAutor(Integer idUsuarioAutor) {
		this.idUsuarioAutor = idUsuarioAutor;
	}

	/**
	 * @return the idUsuarioAprovador
	 */
	public Integer getIdUsuarioAprovador() {
		return idUsuarioAprovador;
	}

	/**
	 * @param idUsuarioAprovador
	 *            the idUsuarioAprovador to set
	 */
	public void setIdUsuarioAprovador(Integer idUsuarioAprovador) {
		this.idUsuarioAprovador = idUsuarioAprovador;
	}

	/**
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @param autor
	 *            the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @return the aprovadaPor
	 */
	public String getAprovador() {
		return aprovador;
	}

	/**
	 * @param aprovador
	 *            the aprovadaPor to set
	 */
	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
	}

	/**
	 * @return the idUsuarioAcesso
	 */
	public Integer getIdUsuarioAcesso() {
		return idUsuarioAcesso;
	}

	/**
	 * @param idUsuarioAcesso
	 *            the idUsuarioAcesso to set
	 */
	public void setIdUsuarioAcesso(Integer idUsuarioAcesso) {
		this.idUsuarioAcesso = idUsuarioAcesso;
	}

	/**
	 * @return the nomeUsuarioAcesso
	 */
	public String getNomeUsuarioAcesso() {
		return nomeUsuarioAcesso;
	}

	/**
	 * @param nomeUsuarioAcesso
	 *            the nomeUsuarioAcesso to set
	 */
	public void setNomeUsuarioAcesso(String nomeUsuarioAcesso) {
		this.nomeUsuarioAcesso = nomeUsuarioAcesso;
	}

	/**
	 * @return the fonteReferencia
	 */
	public String getFonteReferencia() {
		return fonteReferencia;
	}

	/**
	 * @param fonteReferencia
	 *            the fonteReferencia to set
	 */
	public void setFonteReferencia(String fonteReferencia) {
		this.fonteReferencia = fonteReferencia;
	}

	/**
	 * @return the ultimoAcesso
	 */
	public String getUltimoAcesso() {
		return ultimoAcesso;
	}

	/**
	 * @param ultimoAcesso
	 *            the ultimoAcesso to set
	 */
	public void setUltimoAcesso(String ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public Timestamp getDataHoraAcesso() {
		return dataHoraAcesso;
	}

	public void setDataHoraAcesso(Timestamp dataHoraAcesso) {
		this.dataHoraAcesso = dataHoraAcesso;
	}

	/**
	 * @return the dataPublicacao
	 */
	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	/**
	 * @param dataPublicacao
	 *            the dataPublicacao to set
	 */
	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	/**
	 * @return the idNotificacao
	 */
	public Integer getIdNotificacao() {
		return idNotificacao;
	}

	/**
	 * @param idNotificacao
	 *            the idNotificacao to set
	 */
	public void setIdNotificacao(Integer idNotificacao) {
		this.idNotificacao = idNotificacao;
	}

	/**
	 * @return the listImportanciaConhecimentoUsuario
	 */
	public Collection<ImportanciaConhecimentoUsuarioDTO> getListImportanciaConhecimentoUsuario() {
		return listImportanciaConhecimentoUsuario;
	}

	/**
	 * @param listImportanciaConhecimentoUsuario
	 *            the listImportanciaConhecimentoUsuario to set
	 */
	public void setListImportanciaConhecimentoUsuario(Collection<ImportanciaConhecimentoUsuarioDTO> listImportanciaConhecimentoUsuario) {
		this.listImportanciaConhecimentoUsuario = listImportanciaConhecimentoUsuario;
	}

	/**
	 * @return the listImportanciaConhecimentoGrupo
	 */
	public Collection<ImportanciaConhecimentoGrupoDTO> getListImportanciaConhecimentoGrupo() {
		return listImportanciaConhecimentoGrupo;
	}

	/**
	 * @param listImportanciaConhecimentoGrupo
	 *            the listImportanciaConhecimentoGrupo to set
	 */
	public void setListImportanciaConhecimentoGrupo(Collection<ImportanciaConhecimentoGrupoDTO> listImportanciaConhecimentoGrupo) {
		this.listImportanciaConhecimentoGrupo = listImportanciaConhecimentoGrupo;
	}

	/**
	 * @return the justificativaObservacao
	 */
	public String getJustificativaObservacao() {
		return justificativaObservacao;
	}

	/**
	 * @param justificativaObservacao
	 *            the justificativaObservacao to set
	 */
	public void setJustificativaObservacao(String justificativaObservacao) {
		this.justificativaObservacao = justificativaObservacao;
	}

	/**
	 * @return the listBaseConhecimentoRelacionado
	 */
	public Collection<BaseConhecimentoRelacionadoDTO> getListBaseConhecimentoRelacionado() {
		return listBaseConhecimentoRelacionado;
	}

	/**
	 * @param listBaseConhecimentoRelacionado
	 *            the listBaseConhecimentoRelacionado to set
	 */
	public void setListBaseConhecimentoRelacionado(Collection<BaseConhecimentoRelacionadoDTO> listBaseConhecimentoRelacionado) {
		this.listBaseConhecimentoRelacionado = listBaseConhecimentoRelacionado;
	}

	/**
	 * @return the idConhecimentoRelacionado
	 */
	public Integer getIdConhecimentoRelacionado() {
		return idConhecimentoRelacionado;
	}

	/**
	 * @param idConhecimentoRelacionado
	 *            the idConhecimentoRelacionado to set
	 */
	public void setIdConhecimentoRelacionado(Integer idConhecimentoRelacionado) {
		this.idConhecimentoRelacionado = idConhecimentoRelacionado;
	}

	/**
	 * @return the tituloNotificacao
	 */
	public String getTituloNotificacao() {
		return tituloNotificacao;
	}

	/**
	 * @param tituloNotificacao
	 *            the tituloNotificacao to set
	 */
	public void setTituloNotificacao(String tituloNotificacao) {
		this.tituloNotificacao = tituloNotificacao;
	}

	/**
	 * @return the tipoNotificacao
	 */
	public String getTipoNotificacao() {
		return tipoNotificacao;
	}

	/**
	 * @param tipoNotificacao
	 *            the tipoNotificacao to set
	 */
	public void setTipoNotificacao(String tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	/**
	 * @return the listaDeUsuarioNotificacao
	 */
	public ArrayList<NotificacaoUsuarioDTO> getListaDeUsuarioNotificacao() {
		return listaDeUsuarioNotificacao;
	}

	/**
	 * @param listaDeUsuarioNotificacao
	 *            the listaDeUsuarioNotificacao to set
	 */
	public void setListaDeUsuarioNotificacao(ArrayList<NotificacaoUsuarioDTO> listaDeUsuarioNotificacao) {
		this.listaDeUsuarioNotificacao = listaDeUsuarioNotificacao;
	}

	/**
	 * @return the listaDeGrupoNotificacao
	 */
	public ArrayList<NotificacaoGrupoDTO> getListaDeGrupoNotificacao() {
		return listaDeGrupoNotificacao;
	}

	/**
	 * @param listaDeGrupoNotificacao
	 *            the listaDeGrupoNotificacao to set
	 */
	public void setListaDeGrupoNotificacao(ArrayList<NotificacaoGrupoDTO> listaDeGrupoNotificacao) {
		this.listaDeGrupoNotificacao = listaDeGrupoNotificacao;
	}

	/**
	 * @return the faq
	 */
	public String getFaq() {
		return faq;
	}

	/**
	 * @param faq
	 *            the faq to set
	 */
	public void setFaq(String faq) {
		this.faq = faq;
	}

	/**
	 * @return the origem
	 */
	public String getOrigem() {
		return origem;
	}

	/**
	 * @param origem
	 *            the origem to set
	 */
	public void setOrigem(String origem) {
		this.origem = origem;
	}

	/**
	 * @return the idUsuarioAutorPesquisa
	 */
	public Integer getIdUsuarioAutorPesquisa() {
		return idUsuarioAutorPesquisa;
	}

	/**
	 * @param idUsuarioAutorPesquisa
	 *            the idUsuarioAutorPesquisa to set
	 */
	public void setIdUsuarioAutorPesquisa(Integer idUsuarioAutorPesquisa) {
		this.idUsuarioAutorPesquisa = idUsuarioAutorPesquisa;
	}

	/**
	 * @return the idUsuarioAprovadorPesquisa
	 */
	public Integer getIdUsuarioAprovadorPesquisa() {
		return idUsuarioAprovadorPesquisa;
	}

	/**
	 * @param idUsuarioAprovadorPesquisa
	 *            the idUsuarioAprovadorPesquisa to set
	 */
	public void setIdUsuarioAprovadorPesquisa(Integer idUsuarioAprovadorPesquisa) {
		this.idUsuarioAprovadorPesquisa = idUsuarioAprovadorPesquisa;
	}

	/**
	 * @return the dataInicioPesquisa
	 */
	public Date getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	/**
	 * @param dataInicioPesquisa
	 *            the dataInicioPesquisa to set
	 */
	public void setDataInicioPesquisa(Date dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	/**
	 * @return the dataPublicacaoPesquisa
	 */
	public Date getDataPublicacaoPesquisa() {
		return dataPublicacaoPesquisa;
	}

	/**
	 * @param dataPublicacaoPesquisa
	 *            the dataPublicacaoPesquisa to set
	 */
	public void setDataPublicacaoPesquisa(Date dataPublicacaoPesquisa) {
		this.dataPublicacaoPesquisa = dataPublicacaoPesquisa;
	}

	/**
	 * @return the arquivado
	 */
	public String getArquivado() {
		return arquivado;
	}

	/**
	 * @param arquivado
	 *            the arquivado to set
	 */
	public void setArquivado(String arquivado) {
		this.arquivado = arquivado;
	}

	/**
	 * @return the grauImportancia
	 */
	public Integer getGrauImportancia() {
		return grauImportancia;
	}

	/**
	 * @param grauImportancia
	 *            the grauImportancia to set
	 */
	public void setGrauImportancia(Integer grauImportancia) {
		this.grauImportancia = grauImportancia;
	}

	/**
	 * @return the idHistoricoBaseConhecimento
	 */
	public Integer getIdHistoricoBaseConhecimento() {
		return idHistoricoBaseConhecimento;
	}

	/**
	 * @param idHistoricoBaseConhecimento
	 *            the idHistoricoBaseConhecimento to set
	 */
	public void setIdHistoricoBaseConhecimento(Integer idHistoricoBaseConhecimento) {
		this.idHistoricoBaseConhecimento = idHistoricoBaseConhecimento;
	}

	/**
	 * @return the privacidade
	 */
	public String getPrivacidade() {
		return privacidade;
	}

	/**
	 * @param privacidade
	 *            the privacidade to set
	 */
	public void setPrivacidade(String privacidade) {
		this.privacidade = privacidade;
	}

	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao
	 *            the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the ocultarConteudo
	 */
	public String getOcultarConteudo() {
		return ocultarConteudo;
	}

	/**
	 * @param ocultarConteudo
	 *            the ocultarConteudo to set
	 */
	public void setOcultarConteudo(String ocultarConteudo) {
		this.ocultarConteudo = ocultarConteudo;
	}

	/**
	 * @return the idItemConfiguracao
	 */
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}

	/**
	 * @param idItemConfiguracao
	 *            the idItemConfiguracao to set
	 */
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}

	/**
	 * @return the idProblema
	 */
	public Integer getIdProblema() {
		return idProblema;
	}

	/**
	 * @param idProblema
	 *            the idProblema to set
	 */
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}

	/**
	 * @return the idRequisicaoMudanca
	 */
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}

	/**
	 * @param idRequisicaoMudanca
	 *            the idRequisicaoMudanca to set
	 */
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
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

	public Integer getSequenciaBaseConhecimento() {
		return sequenciaBaseConhecimento;
	}

	public void setSequenciaBaseConhecimento(Integer sequenciaBaseConhecimento) {
		this.sequenciaBaseConhecimento = sequenciaBaseConhecimento;
	}

	/**
	 * @return the listEventoMonitoramento
	 */
	public ArrayList<EventoMonitConhecimentoDTO> getListEventoMonitoramento() {
		return listEventoMonitoramento;
	}

	/**
	 * @param listEventoMonitoramento
	 *            the listEventoMonitoramento to set
	 */
	public void setListEventoMonitoramento(ArrayList<EventoMonitConhecimentoDTO> listEventoMonitoramento) {
		this.listEventoMonitoramento = listEventoMonitoramento;
	}

	public String getIframe() {
		return iframe;
	}

	public void setIframe(String iframe) {
		this.iframe = iframe;
	}

	public String getAmDoc() {
		return amDoc;
	}

	public void setAmDoc(String amDoc) {
		this.amDoc = amDoc;
	}

	public String getGerenciamentoDisponibilidade() {
		return gerenciamentoDisponibilidade;
	}

	public void setGerenciamentoDisponibilidade(String gerenciamentoDisponibilidade) {
		this.gerenciamentoDisponibilidade = gerenciamentoDisponibilidade;
	}

	public String getDireitoAutoral() {
		return direitoAutoral;
	}

	public void setDireitoAutoral(String direitoAutoral) {
		this.direitoAutoral = direitoAutoral;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	/**
	 * @return the dataInicioPublicacao
	 */
	public Date getDataInicioPublicacao() {
		return dataInicioPublicacao;
	}

	/**
	 * @param dataInicioPublicacao
	 *            the dataInicioPublicacao to set
	 */
	public void setDataInicioPublicacao(Date dataInicioPublicacao) {
		this.dataInicioPublicacao = dataInicioPublicacao;
	}

	/**
	 * @return the dataFimPublicacao
	 */
	public Date getDataFimPublicacao() {
		return dataFimPublicacao;
	}

	/**
	 * @param dataFimPublicacao
	 *            the dataFimPublicacao to set
	 */
	public void setDataFimPublicacao(Date dataFimPublicacao) {
		this.dataFimPublicacao = dataFimPublicacao;
	}

	/**
	 * @return the conteudoSemFormatacao
	 */
	public String getConteudoSemFormatacao() {

		if (conteudoSemFormatacao != null && !StringUtils.isBlank(conteudoSemFormatacao)) {
			Source source = new Source(conteudoSemFormatacao);
			this.conteudoSemFormatacao = source.getTextExtractor().toString();
			return conteudoSemFormatacao;
		}

		return conteudoSemFormatacao;
	}

	/**
	 * @param conteudoSemFormatacao
	 *            the conteudoSemFormatacao to set
	 */
	public void setConteudoSemFormatacao(String conteudoSemFormatacao) {
		this.conteudoSemFormatacao = conteudoSemFormatacao;
	}
	
	public String getErroConhecido() {
		return erroConhecido;
	}

	public void setErroConhecido(String erroConhecido) {
		this.erroConhecido = erroConhecido;
	}	

	public Integer getQtdPublicados() {
		return qtdPublicados;
	}

	public void setQtdPublicados(Integer qtdPublicados) {
		this.qtdPublicados = qtdPublicados;
	}

	public Integer getQtdNaoPublicados() {
		return qtdNaoPublicados;
	}

	public void setQtdNaoPublicados(Integer qtdNaoPublicados) {
		this.qtdNaoPublicados = qtdNaoPublicados;
	}

	public Integer getQtdAcessados() {
		return qtdAcessados;
	}

	public void setQtdAcessados(Integer qtdAcessados) {
		this.qtdAcessados = qtdAcessados;
	}

	public Integer getQtdAvaliados() {
		return qtdAvaliados;
	}

	public void setQtdAvaliados(Integer qtdAvaliados) {
		this.qtdAvaliados = qtdAvaliados;
	}

	public Integer getQtdExcluidos() {
		return qtdExcluidos;
	}

	public void setQtdExcluidos(Integer qtdExcluidos) {
		this.qtdExcluidos = qtdExcluidos;
	}

	public Integer getQtdArquivados() {
		return qtdArquivados;
	}

	public void setQtdArquivados(Integer qtdArquivados) {
		this.qtdArquivados = qtdArquivados;
	}

	public Integer getQtdAtualizados() {
		return qtdAtualizados;
	}

	public void setQtdAtualizados(Integer qtdAtualizados) {
		this.qtdAtualizados = qtdAtualizados;
	}

	public Integer getQtdRestaurados() {
		return qtdRestaurados;
	}

	public void setQtdRestaurados(Integer qtdRestaurados) {
		this.qtdRestaurados = qtdRestaurados;
	}

	public Integer getTipoFaq() {
		return tipoFaq;
	}

	public void setTipoFaq(Integer tipoFaq) {
		this.tipoFaq = tipoFaq;
	}

	public Integer getQtdDocumentos() {
		return qtdDocumentos;
	}

	public void setQtdDocumentos(Integer qtdDocumentos) {
		this.qtdDocumentos = qtdDocumentos;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Integer getQtdConhecimentoPorUsuario() {
		return qtdConhecimentoPorUsuario;
	}

	public void setQtdConhecimentoPorUsuario(Integer qtdConhecimentoPorUsuario) {
		this.qtdConhecimentoPorUsuario = qtdConhecimentoPorUsuario;
	}

	public Integer getQtdConhecimentoPorAprovador() {
		return qtdConhecimentoPorAprovador;
	}

	public void setQtdConhecimentoPorAprovador(Integer qtdConhecimentoPorAprovador) {
		this.qtdConhecimentoPorAprovador = qtdConhecimentoPorAprovador;
	}

	public String getNomeAprovador() {
		return nomeAprovador;
	}

	public void setNomeAprovador(String nomeAprovador) {
		this.nomeAprovador = nomeAprovador;
	}

	public Date getDataInicioExpiracao() {
		return dataInicioExpiracao;
	}

	public void setDataInicioExpiracao(Date dataInicioExpiracao) {
		this.dataInicioExpiracao = dataInicioExpiracao;
	}

	public Date getDataFimExpiracao() {
		return dataFimExpiracao;
	}

	public void setDataFimExpiracao(Date dataFimExpiracao) {
		this.dataFimExpiracao = dataFimExpiracao;
	}

	public Integer getQtdConhecimentoPublicadoPorOrigem() {
		return qtdConhecimentoPublicadoPorOrigem;
	}

	public void setQtdConhecimentoPublicadoPorOrigem(Integer qtdConhecimentoPublicadoPorOrigem) {
		this.qtdConhecimentoPublicadoPorOrigem = qtdConhecimentoPublicadoPorOrigem;
	}

	public Integer getQtdConhecimentoNaoPublicadoPorOrigem() {
		return qtdConhecimentoNaoPublicadoPorOrigem;
	}

	public void setQtdConhecimentoNaoPublicadoPorOrigem(Integer qtdConhecimentoNaoPublicadoPorOrigem) {
		this.qtdConhecimentoNaoPublicadoPorOrigem = qtdConhecimentoNaoPublicadoPorOrigem;
	}

	public String getNomeOrigem() {
		return nomeOrigem;
	}

	public void setNomeOrigem(String nomeOrigem) {
		this.nomeOrigem = nomeOrigem;
	}

	public String getVinculaConhecimentoServico() {
		return vinculaConhecimentoServico;
	}

	public void setVinculaConhecimentoServico(String vinculaConhecimentoServico) {
		this.vinculaConhecimentoServico = vinculaConhecimentoServico;
	}

	public Integer getIdSolicitacaoServicoIncidente() {
		return idSolicitacaoServicoIncidente;
	}

	public void setIdSolicitacaoServicoIncidente(Integer idSolicitacaoServicoIncidente) {
		this.idSolicitacaoServicoIncidente = idSolicitacaoServicoIncidente;
	}

	public Integer getIdSolicitacaoServicoRequisicao() {
		return idSolicitacaoServicoRequisicao;
	}

	public void setIdSolicitacaoServicoRequisicao(Integer idSolicitacaoServicoRequisicao) {
		this.idSolicitacaoServicoRequisicao = idSolicitacaoServicoRequisicao;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public List<BaseConhecimentoDTO> getListaIncidente() {
		return listaIncidente;
	}

	public void setListaIncidente(List<BaseConhecimentoDTO> listaIncidente) {
		this.listaIncidente = listaIncidente;
	}

	public List<BaseConhecimentoDTO> getListaRequisitos() {
		return listaRequisitos;
	}

	public void setListaRequisitos(List<BaseConhecimentoDTO> listaRequisitos) {
		this.listaRequisitos = listaRequisitos;
	}

	public List<BaseConhecimentoDTO> getListaProblema() {
		return listaProblema;
	}

	public void setListaProblema(List<BaseConhecimentoDTO> listaProblema) {
		this.listaProblema = listaProblema;
	}

	public List<BaseConhecimentoDTO> getListaMudanca() {
		return listaMudanca;
	}

	public void setListaMudanca(List<BaseConhecimentoDTO> listaMudanca) {
		this.listaMudanca = listaMudanca;
	}

	public List<BaseConhecimentoDTO> getListaIC() {
		return listaIC;
	}

	public void setListaIC(List<BaseConhecimentoDTO> listaIC) {
		this.listaIC = listaIC;
	}

	public List<BaseConhecimentoDTO> getListaServico() {
		return listaServico;
	}

	public void setListaServico(List<BaseConhecimentoDTO> listaServico) {
		this.listaServico = listaServico;
	}

	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}

	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}

	public List<RequisicaoLiberacaoDTO> getColItensLiberacao() {
		return colItensLiberacao;
	}

	public void setColItensLiberacao(List<RequisicaoLiberacaoDTO> colItensLiberacao) {
		this.colItensLiberacao = colItensLiberacao;
	}

	public Integer getQtdErroConhecido() {
		return qtdErroConhecido;
	}

	public void setQtdErroConhecido(Integer qtdConhecimentoPorErroConhecido) {
		this.qtdErroConhecido = qtdConhecimentoPorErroConhecido;
	}
	
	public Date getDataInicioAcesso() {
		return dataInicioAcesso;
	}

	public void setDataInicioAcesso(Date dataInicioAcesso) {
		this.dataInicioAcesso = dataInicioAcesso;
	}

	public Date getDataFimAcesso() {
		return dataFimAcesso;
	}

	public void setDataFimAcesso(Date dataFimAcesso) {
		this.dataFimAcesso = dataFimAcesso;
	}

	/**
	 * Uma Base de conhecimento ativa é uma base publicada, não arquivada e não excluída.
	 * @Author euler.ramos
	 */
	public boolean ativa(){
		//Base de conhecimento publicada, não arquivada e não excluída.
		boolean resultado;
		String publicada = (this.getStatus() == null ? "N" : this.getStatus());
		String arquivada = (this.getArquivado() == null ? "N" : this.getArquivado());
		resultado = (publicada.equalsIgnoreCase("S") && arquivada.equalsIgnoreCase("N") && this.getDataFim()==null);
		return resultado;
	}

}
