package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class DescricaoCargoDTO implements IDto {
	public static final String ACAO_CRIACAO = "C";
	public static final String ACAO_ANALISE = "A";
	
	private Integer idJustificativaValidacao;
	private String complemJustificativaValidacao;
	
	private String acao;
	
	private Integer idDescricaoCargo;
	private Integer idSolicitacaoServico;
	private String nomeCargo;
	private Integer idCbo;
	private Date dataSolicitacao;
	private String atividades;
	private String observacoes;
	private String situacao;
	
	private Collection<CargoFormacaoAcademicaDTO> colFormacaoAcademica;
	private Collection<CargoCertificacaoDTO> colCertificacao;
	private Collection<CargoCursoDTO> colCurso;
	private Collection<CargoExperienciaInformaticaDTO> colExperienciaInformatica;
	private Collection<CargoIdiomaDTO> colIdioma;
	private Collection<CargoExperienciaAnteriorDTO> colExperienciaAnterior;
	private Collection<CargoConhecimentoDTO> colConhecimento;
	private Collection<CargoHabilidadeDTO> colHabilidade;
	private Collection<CargoAtitudeIndividualDTO> colAtitudeIndividual;
	
    private String serializeFormacaoAcademica;
    private String serializeCertificacao;
    private String serializeCurso;
    private String serializeExperienciaInformatica;
    private String serializeIdioma;
    private String serializeExperienciaAnterior;
    private String serializeConhecimento;
    private String serializeHabilidade;
    private String serializeAtitudeIndividual;
    
    private Integer idParecerValidacao;
    
    
    
    public Integer getIdParecerValidacao() {
		return idParecerValidacao;
	}
	public void setIdParecerValidacao(Integer idParecerValidacao) {
		this.idParecerValidacao = idParecerValidacao;
	}
	public Integer getIdJustificativaValidacao() {
		return idJustificativaValidacao;
	}
	public void setIdJustificativaValidacao(Integer idJustificativaValidacao) {
		this.idJustificativaValidacao = idJustificativaValidacao;
	}
	public String getComplemJustificativaValidacao() {
		return complemJustificativaValidacao;
	}
	public void setComplemJustificativaValidacao(
			String complemJustificativaValidacao) {
		this.complemJustificativaValidacao = complemJustificativaValidacao;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public static String getAcaoCriacao() {
		return ACAO_CRIACAO;
	}
	public static String getAcaoAnalise() {
		return ACAO_ANALISE;
	}
	public Integer getIdDescricaoCargo() {
		return idDescricaoCargo;
	}
	public void setIdDescricaoCargo(Integer idDescricaoCargo) {
		this.idDescricaoCargo = idDescricaoCargo;
	}

	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getNomeCargo() {
		return nomeCargo;
	}
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}
	public Integer getIdCbo() {
		return idCbo;
	}
	public void setIdCbo(Integer idCbo) {
		this.idCbo = idCbo;
	}
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public String getAtividades() {
		return atividades;
	}
	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Collection<CargoFormacaoAcademicaDTO> getColFormacaoAcademica() {
		return colFormacaoAcademica;
	}
	public void setColFormacaoAcademica(
			Collection<CargoFormacaoAcademicaDTO> colFormacaoAcademica) {
		this.colFormacaoAcademica = colFormacaoAcademica;
	}
	public Collection<CargoCertificacaoDTO> getColCertificacao() {
		return colCertificacao;
	}
	public void setColCertificacao(Collection<CargoCertificacaoDTO> colCertificacao) {
		this.colCertificacao = colCertificacao;
	}
	public Collection<CargoCursoDTO> getColCurso() {
		return colCurso;
	}
	public void setColCurso(Collection<CargoCursoDTO> colCurso) {
		this.colCurso = colCurso;
	}
	public Collection<CargoExperienciaInformaticaDTO> getColExperienciaInformatica() {
		return colExperienciaInformatica;
	}
	public void setColExperienciaInformatica(
			Collection<CargoExperienciaInformaticaDTO> colExperienciaInformatica) {
		this.colExperienciaInformatica = colExperienciaInformatica;
	}
	public Collection<CargoIdiomaDTO> getColIdioma() {
		return colIdioma;
	}
	public void setColIdioma(Collection<CargoIdiomaDTO> colIdioma) {
		this.colIdioma = colIdioma;
	}
	public Collection<CargoExperienciaAnteriorDTO> getColExperienciaAnterior() {
		return colExperienciaAnterior;
	}
	public void setColExperienciaAnterior(
			Collection<CargoExperienciaAnteriorDTO> colExperienciaAnterior) {
		this.colExperienciaAnterior = colExperienciaAnterior;
	}
	public Collection<CargoConhecimentoDTO> getColConhecimento() {
		return colConhecimento;
	}
	public void setColConhecimento(Collection<CargoConhecimentoDTO> colConhecimento) {
		this.colConhecimento = colConhecimento;
	}
	public Collection<CargoHabilidadeDTO> getColHabilidade() {
		return colHabilidade;
	}
	public void setColHabilidade(Collection<CargoHabilidadeDTO> colHabilidade) {
		this.colHabilidade = colHabilidade;
	}
	public Collection<CargoAtitudeIndividualDTO> getColAtitudeIndividual() {
		return colAtitudeIndividual;
	}
	public void setColAtitudeIndividual(
			Collection<CargoAtitudeIndividualDTO> colAtitudeIndividual) {
		this.colAtitudeIndividual = colAtitudeIndividual;
	}
	public String getSerializeFormacaoAcademica() {
		return serializeFormacaoAcademica;
	}
	public void setSerializeFormacaoAcademica(String serializeFormacaoAcademica) {
		this.serializeFormacaoAcademica = serializeFormacaoAcademica;
	}
	public String getSerializeCertificacao() {
		return serializeCertificacao;
	}
	public void setSerializeCertificacao(String serializeCertificacao) {
		this.serializeCertificacao = serializeCertificacao;
	}
	public String getSerializeCurso() {
		return serializeCurso;
	}
	public void setSerializeCurso(String serializeCurso) {
		this.serializeCurso = serializeCurso;
	}
	public String getSerializeExperienciaInformatica() {
		return serializeExperienciaInformatica;
	}
	public void setSerializeExperienciaInformatica(
			String serializeExperienciaInformatica) {
		this.serializeExperienciaInformatica = serializeExperienciaInformatica;
	}
	public String getSerializeIdioma() {
		return serializeIdioma;
	}
	public void setSerializeIdioma(String serializeIdioma) {
		this.serializeIdioma = serializeIdioma;
	}
	public String getSerializeExperienciaAnterior() {
		return serializeExperienciaAnterior;
	}
	public void setSerializeExperienciaAnterior(String serializeExperienciaAnterior) {
		this.serializeExperienciaAnterior = serializeExperienciaAnterior;
	}
	public String getSerializeConhecimento() {
		return serializeConhecimento;
	}
	public void setSerializeConhecimento(String serializeConhecimento) {
		this.serializeConhecimento = serializeConhecimento;
	}
	public String getSerializeHabilidade() {
		return serializeHabilidade;
	}
	public void setSerializeHabilidade(String serializeHabilidade) {
		this.serializeHabilidade = serializeHabilidade;
	}
	public String getSerializeAtitudeIndividual() {
		return serializeAtitudeIndividual;
	}
	public void setSerializeAtitudeIndividual(String serializeAtitudeIndividual) {
		this.serializeAtitudeIndividual = serializeAtitudeIndividual;
	}
	
}