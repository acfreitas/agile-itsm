package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

public class HistManualFuncaoDTO extends MovimentacaoPessoalDTO {

	private Integer idhistManualFuncao;
	private Integer idManualFuncao;
	private String tituloCargo;
	private String tituloFuncao;
	private String resumoFuncao;
	private String codCBO;
	private String codigo;
	private String idFormacaoRA;
	private String idIdiomaRA;
	private String idNivelEscritaRA;
	private String idNivelLeituraRA;
	private String idNivelConversaRA;
	private String expAnteriorRA;
	private String idFormacaoRF;
	private String idIdiomaRF;
	private String idNivelEscritaRF;
	private String idNivelLeituraRF;
	private String idNivelConversaRF;
	private String expAnteriorRF;
	private String pesoComplexidade;
	private String pesoTecnica;
	private String pesoComportamental;
	private String pesoResultados;
	private Date dataAlteracao;
	private Timestamp horaAlteracao;
	private Integer idUsuarioAlteracao;
	private Double versao;
	//Coleções DTO
	private Collection<HistAtribuicaoResponsabilidadeDTO> colAtribuicaoResponsabilidadeDTO;
	private Collection<CertificacaoDTO> colCertificacaoDTORA;
	private Collection<CursoDTO> colCursoDTORA;
	private Collection<CertificacaoDTO> colCertificacaoDTORF;
	private Collection<CursoDTO> colCursoDTORF;
	private Collection<HistManualCompetenciaTecnicaDTO> colCompetenciaTecnicaDTO;
	private Collection<HistPerspectivaComportamentalDTO> colPerspectivaComportamentalDTO;

	public Integer getIdhistManualFuncao() {
		return idhistManualFuncao;
	}

	public void setIdhistManualFuncao(Integer idhistManualFuncao) {
		this.idhistManualFuncao = idhistManualFuncao;
	}

	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}

	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}

	public String getTituloCargo() {
		return tituloCargo;
	}

	public void setTituloCargo(String tituloCargo) {
		this.tituloCargo = tituloCargo;
	}

	public String getTituloFuncao() {
		return tituloFuncao;
	}

	public void setTituloFuncao(String tituloFuncao) {
		this.tituloFuncao = tituloFuncao;
	}

	public String getResumoFuncao() {
		return resumoFuncao;
	}

	public void setResumoFuncao(String resumoFuncao) {
		this.resumoFuncao = resumoFuncao;
	}

	public String getCodCBO() {
		return codCBO;
	}

	public void setCodCBO(String cBO) {
		codCBO = cBO;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdFormacaoRA() {
		return idFormacaoRA;
	}

	public void setIdFormacaoRA(String idFormacaoRA) {
		this.idFormacaoRA = idFormacaoRA;
	}

	public String getIdIdiomaRA() {
		return idIdiomaRA;
	}

	public void setIdIdiomaRA(String idIdiomaRA) {
		this.idIdiomaRA = idIdiomaRA;
	}

	public String getIdNivelEscritaRA() {
		return idNivelEscritaRA;
	}

	public void setIdNivelEscritaRA(String idNivelEscritaRA) {
		this.idNivelEscritaRA = idNivelEscritaRA;
	}

	public String getIdNivelLeituraRA() {
		return idNivelLeituraRA;
	}

	public void setIdNivelLeituraRA(String idNivelLeituraRA) {
		this.idNivelLeituraRA = idNivelLeituraRA;
	}

	public String getIdNivelConversaRA() {
		return idNivelConversaRA;
	}

	public void setIdNivelConversaRA(String idNivelConversaRA) {
		this.idNivelConversaRA = idNivelConversaRA;
	}

	public String getExpAnteriorRA() {
		return expAnteriorRA;
	}

	public void setExpAnteriorRA(String expAnteriorRA) {
		this.expAnteriorRA = expAnteriorRA;
	}

	public String getIdFormacaoRF() {
		return idFormacaoRF;
	}

	public void setIdFormacaoRF(String idFormacaoRF) {
		this.idFormacaoRF = idFormacaoRF;
	}

	public String getIdIdiomaRF() {
		return idIdiomaRF;
	}

	public void setIdIdiomaRF(String idIdiomaRF) {
		this.idIdiomaRF = idIdiomaRF;
	}

	public String getIdNivelEscritaRF() {
		return idNivelEscritaRF;
	}

	public void setIdNivelEscritaRF(String idNivelEscritaRF) {
		this.idNivelEscritaRF = idNivelEscritaRF;
	}

	public String getIdNivelLeituraRF() {
		return idNivelLeituraRF;
	}

	public void setIdNivelLeituraRF(String idNivelLeituraRF) {
		this.idNivelLeituraRF = idNivelLeituraRF;
	}

	public String getIdNivelConversaRF() {
		return idNivelConversaRF;
	}

	public void setIdNivelConversaRF(String idNivelConversaRF) {
		this.idNivelConversaRF = idNivelConversaRF;
	}

	public String getExpAnteriorRF() {
		return expAnteriorRF;
	}

	public void setExpAnteriorRF(String expAnteriorRF) {
		this.expAnteriorRF = expAnteriorRF;
	}

	public String getPesoComplexidade() {
		return pesoComplexidade;
	}

	public void setPesoComplexidade(String pesoComplexidade) {
		this.pesoComplexidade = pesoComplexidade;
	}

	public String getPesoTecnica() {
		return pesoTecnica;
	}

	public void setPesoTecnica(String pesoTecnica) {
		this.pesoTecnica = pesoTecnica;
	}

	public String getPesoComportamental() {
		return pesoComportamental;
	}

	public void setPesoComportamental(String pesoComportamental) {
		this.pesoComportamental = pesoComportamental;
	}

	public String getPesoResultados() {
		return pesoResultados;
	}

	public void setPesoResultados(String pesoResultados) {
		this.pesoResultados = pesoResultados;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Timestamp getHoraAlteracao() {
		return horaAlteracao;
	}

	public void setHoraAlteracao(Timestamp horaAlteracao) {
		this.horaAlteracao = horaAlteracao;
	}

	public Integer getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}

	public void setIdUsuarioAlteracao(Integer idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}

	public Double getVersao() {
		return versao;
	}

	public void setVersao(Double versao) {
		this.versao = versao;
	}

	public Collection<HistAtribuicaoResponsabilidadeDTO> getColAtribuicaoResponsabilidadeDTO() {
		return colAtribuicaoResponsabilidadeDTO;
	}

	public void setColAtribuicaoResponsabilidadeDTO(Collection<HistAtribuicaoResponsabilidadeDTO> colAtribuicaoResponsabilidadeDTO) {
		this.colAtribuicaoResponsabilidadeDTO = colAtribuicaoResponsabilidadeDTO;
	}

	public Collection<CertificacaoDTO> getColCertificacaoDTORA() {
		return colCertificacaoDTORA;
	}

	public void setColCertificacaoDTORA(Collection<CertificacaoDTO> colCertificacaoDTORA) {
		this.colCertificacaoDTORA = colCertificacaoDTORA;
	}

	public Collection<CursoDTO> getColCursoDTORA() {
		return colCursoDTORA;
	}

	public void setColCursoDTORA(Collection<CursoDTO> colCursoDTORA) {
		this.colCursoDTORA = colCursoDTORA;
	}

	public Collection<CertificacaoDTO> getColCertificacaoDTORF() {
		return colCertificacaoDTORF;
	}

	public void setColCertificacaoDTORF(Collection<CertificacaoDTO> colCertificacaoDTORF) {
		this.colCertificacaoDTORF = colCertificacaoDTORF;
	}

	public Collection<CursoDTO> getColCursoDTORF() {
		return colCursoDTORF;
	}

	public void setColCursoDTORF(Collection<CursoDTO> colCursoDTORF) {
		this.colCursoDTORF = colCursoDTORF;
	}

	public Collection<HistManualCompetenciaTecnicaDTO> getColCompetenciaTecnicaDTO() {
		return colCompetenciaTecnicaDTO;
	}

	public void setColCompetenciaTecnicaDTO(Collection<HistManualCompetenciaTecnicaDTO> colCompetenciaTecnicaDTO) {
		this.colCompetenciaTecnicaDTO = colCompetenciaTecnicaDTO;
	}

	public Collection<HistPerspectivaComportamentalDTO> getColPerspectivaComportamentalDTO() {
		return colPerspectivaComportamentalDTO;
	}

	public void setColPerspectivaComportamentalDTO(Collection<HistPerspectivaComportamentalDTO> colPerspectivaComportamentalDTO) {
		this.colPerspectivaComportamentalDTO = colPerspectivaComportamentalDTO;
	}

}