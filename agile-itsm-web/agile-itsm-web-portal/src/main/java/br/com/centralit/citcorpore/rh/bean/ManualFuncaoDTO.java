package br.com.centralit.citcorpore.rh.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ManualFuncaoDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idManualFuncao;
	private Integer idRequisicaoFuncao;
	private Integer idCargo;

	private String tituloCargo;
	private String tituloFuncao;
	private String resumoFuncao;
	private String codCBO;
	private String codigo;

	// aba Perspectiva tecnica
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

	// atributos auxiliares PerspectivaComplexidade
	// Utilizar a classe AtribuicaoResponsabilidade
	private String descricaoPerspectivaComplexidade;
	private Integer idNivel;

	// aba Competencia Técnica
	private String competencia;
	private String idCompetenciaAcesso;
	private String idCompetenciaFuncao;

	// aba perspectiva comportamental
	private String cmbCompetenciaComportamental;
	private String comportamento;

	// aba peso das perspectivas
	private String pesoComplexidade;
	private String pesoTecnica;
	private String pesoComportamental;
	private String pesoResultados;

	// Coleções serializadas
	private String colResponsabilidades_Serialize;
	private String colCertificacoes_Serialize;
	private String colCursos_Serialize;
	private String colCertificacoesRF_Serialize;
	private String colCursosRF_Serialize;
	private String colCompetencias_Serialize;
	private String colPerspectivaComportamental_Serialize;

	// Coleções DTO
	private Collection<AtribuicaoResponsabilidadeDTO> colAtribuicaoResponsabilidadeDTO;
	private Collection<CertificacaoDTO> colCertificacaoDTORA;
	private Collection<CursoDTO> colCursoDTORA;
	private Collection<CertificacaoDTO> colCertificacaoDTORF;
	private Collection<CursoDTO> colCursoDTORF;
	private Collection<ManualCompetenciaTecnicaDTO> colCompetenciaTecnicaDTO;
	private Collection<AtitudeIndividualDTO> colPerspectivaComportamentalDTO;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricaoPerspectivaComplexidade() {
		return descricaoPerspectivaComplexidade;
	}

	public void setDescricaoPerspectivaComplexidade(String descricaoPerspectivaComplexidade) {
		this.descricaoPerspectivaComplexidade = descricaoPerspectivaComplexidade;
	}

	public Integer getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
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

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public String getIdCompetenciaAcesso() {
		return idCompetenciaAcesso;
	}

	public void setIdCompetenciaAcesso(String idCompetenciaAcesso) {
		this.idCompetenciaAcesso = idCompetenciaAcesso;
	}

	public String getIdCompetenciaFuncao() {
		return idCompetenciaFuncao;
	}

	public void setIdCompetenciaFuncao(String idCompetenciaFuncao) {
		this.idCompetenciaFuncao = idCompetenciaFuncao;
	}

	public String getCmbCompetenciaComportamental() {
		return cmbCompetenciaComportamental;
	}

	public void setCmbCompetenciaComportamental(String cmbCompetenciaComportamental) {
		this.cmbCompetenciaComportamental = cmbCompetenciaComportamental;
	}

	public String getComportamento() {
		return comportamento;
	}

	public void setComportamento(String comportamento) {
		this.comportamento = comportamento;
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

	public String getColResponsabilidades_Serialize() {
		return colResponsabilidades_Serialize;
	}

	public void setColResponsabilidades_Serialize(String colResponsabilidades_Serialize) {
		this.colResponsabilidades_Serialize = colResponsabilidades_Serialize;
	}

	public String getColCertificacoes_Serialize() {
		return colCertificacoes_Serialize;
	}

	public void setColCertificacoes_Serialize(String colCertificacoes_Serialize) {
		this.colCertificacoes_Serialize = colCertificacoes_Serialize;
	}

	public String getColCursos_Serialize() {
		return colCursos_Serialize;
	}

	public void setColCursos_Serialize(String colCursos_Serialize) {
		this.colCursos_Serialize = colCursos_Serialize;
	}

	public String getColCertificacoesRF_Serialize() {
		return colCertificacoesRF_Serialize;
	}

	public void setColCertificacoesRF_Serialize(String colCertificacoesRF_Serialize) {
		this.colCertificacoesRF_Serialize = colCertificacoesRF_Serialize;
	}

	public String getColCursosRF_Serialize() {
		return colCursosRF_Serialize;
	}

	public void setColCursosRF_Serialize(String colCursosRF_Serialize) {
		this.colCursosRF_Serialize = colCursosRF_Serialize;
	}

	public String getColCompetencias_Serialize() {
		return colCompetencias_Serialize;
	}

	public void setColCompetencias_Serialize(String colCompetencias_Serialize) {
		this.colCompetencias_Serialize = colCompetencias_Serialize;
	}

	public String getColPerspectivaComportamental_Serialize() {
		return colPerspectivaComportamental_Serialize;
	}

	public void setColPerspectivaComportamental_Serialize(String colPerspectivaComportamental_Serialize) {
		this.colPerspectivaComportamental_Serialize = colPerspectivaComportamental_Serialize;
	}

	public Collection<AtribuicaoResponsabilidadeDTO> getColAtribuicaoResponsabilidadeDTO() {
		return colAtribuicaoResponsabilidadeDTO;
	}

	public void setColAtribuicaoResponsabilidadeDTO(Collection<AtribuicaoResponsabilidadeDTO> colAtribuicaoResponsabilidadeDTO) {
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

	public Collection<ManualCompetenciaTecnicaDTO> getColCompetenciaTecnicaDTO() {
		return colCompetenciaTecnicaDTO;
	}

	public void setColCompetenciaTecnicaDTO(Collection<ManualCompetenciaTecnicaDTO> colCompetenciaTecnicaDTO) {
		this.colCompetenciaTecnicaDTO = colCompetenciaTecnicaDTO;
	}

	public Collection<AtitudeIndividualDTO> getColPerspectivaComportamentalDTO() {
		return colPerspectivaComportamentalDTO;
	}

	public void setColPerspectivaComportamentalDTO(Collection<AtitudeIndividualDTO> colPerspectivaComportamentalDTO) {
		this.colPerspectivaComportamentalDTO = colPerspectivaComportamentalDTO;
	}

	public String getCodCBO() {
		return codCBO;
	}

	public void setCodCBO(String codCBO) {
		this.codCBO = codCBO;
	}

	public Integer getIdRequisicaoFuncao() {
		return idRequisicaoFuncao;
	}

	public void setIdRequisicaoFuncao(Integer idRequisicaoFuncao) {
		this.idRequisicaoFuncao = idRequisicaoFuncao;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

}
