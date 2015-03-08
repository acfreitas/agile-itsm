package br.com.centralit.citcorpore.rh.bean;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;

public class RequisicaoFuncaoDTO extends SolicitacaoServicoDTO {
	private static final long serialVersionUID = 1L;

	private String nomeFuncao;
	private Integer idCargo;

	// Não persistido;
	private String nomeCargo;

	private Integer numeroPessoas;
	private String possuiSubordinados;

	private String fase;

	private Integer idFormacaoAcademica;
	private Integer idCertificacao;
	private Integer idCurso;
	private Integer idIdioma;
	private Integer idConhecimento;
	private Integer idAtitudeIndividual;

	private Integer idJustificativaFuncao;
	private String justificativaFuncao;
	private String resumoAtividades;
	private String requisicaoValida;
	private Integer idJustificativaValidacao;
	private String justificativaValidacao;
	private String complementoJustificativaValidacao;
	private String cargo;
	private String funcao;
	private String resumoFuncao;

	private String descricaoPerspectivaComplexidade;
	private Integer nivelPerspectivaComplexidade;
	private Collection<PerspectivaComplexidadeDTO> colPerspectivaComplexidade;
	private String colPerspectivaComplexidadeSerialize;

	private String descricaoCompetenciasTecnicas;
	private Integer nivelCompetenciasTecnicas;
	private Collection<CompetenciasTecnicasDTO> colCompetenciasTecnicas;
	private String colCompetenciasTecnicasSerialize;

	private String descricaoPerspectivaTecnica;
	private Integer nivelPerspectivaTecnica;
	private Collection<PerspectivaTecnicaDTO> colPerspectivaTecnica;

	private String descricaoPerspectivaComportamental;
	private Integer detalhePerspectivaComportamental;
	private Collection<PerspectivaComportamentalFuncaoDTO> colPerspectivaComportamental;
	private String colPerspectivaComportamentalSerialize;

	private String descricaoFormacaoAcademica;
	private String detalheFormacaoAcademica;
	private String obrigatorioFormacao;
	private Collection<PerspectivaTecnicaFormacaoAcademicaDTO> colPerspectivaTecnicaFormacaoAcademica;
	private String colPerspectivaTecnicaFormacaoAcademicaSerialize;

	private String descricaoCertificacao;
	private String detalheCertificacaoCertificacao;
	private String obrigatorioCertificacao;
	private Collection<PerspectivaTecnicaCertificacaoDTO> colPerspectivaTecnicaCertificacao;
	private String colPerspectivaTecnicaCertificacaoSerialize;

	private String descricaoCurso;
	private String detalheCurso;
	private String obrigatorioCurso;
	private Collection<PerspectivaTecnicaCursoDTO> colPerspectivaTecnicaCurso;
	private String colPerspectivaTecnicaCursoSerialize;

	private String descricaoIdioma;
	private String detalheIdioma;
	private String obrigatorioIdioma;
	private Collection<PerspectivaTecnicaIdiomaDTO> colPerspectivaTecnicaIdioma;
	private String colPerspectivaTecnicaIdiomaSerialize;

	private String descricaoExperiencia;
	private String detalheExperiencia;
	private String obrigatorioExperiencia;
	private Collection<PerspectivaTecnicaExperienciaDTO> colPerspectivaTecnicaExperiencia;
	private String colPerspectivaTecnicaExperienciaSerialize;

	private String descricaoValida;
	private String justificativaDescricaoFuncao;
	private String complementoJustificativaDescricaoFuncao;

	public String getNomeFuncao() {
		return nomeFuncao;
	}

	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}

	public Integer getNumeroPessoas() {
		return numeroPessoas;
	}

	public void setNumeroPessoas(Integer numeroPessoas) {
		this.numeroPessoas = numeroPessoas;
	}

	public String getPossuiSubordinados() {
		return possuiSubordinados;
	}

	public void setPossuiSubordinados(String possuiSubordinados) {
		this.possuiSubordinados = possuiSubordinados;
	}

	public Integer getIdJustificativaFuncao() {
		return idJustificativaFuncao;
	}

	public void setIdJustificativaFuncao(Integer idJustificativaFuncao) {
		this.idJustificativaFuncao = idJustificativaFuncao;
	}

	public String getJustificativaFuncao() {
		return justificativaFuncao;
	}

	public void setJustificativaFuncao(String justificativaFuncao) {
		this.justificativaFuncao = justificativaFuncao;
	}

	public String getResumoAtividades() {
		return resumoAtividades;
	}

	public void setResumoAtividades(String resumoAtividades) {
		this.resumoAtividades = resumoAtividades;
	}

	public String getRequisicaoValida() {
		return requisicaoValida;
	}

	public void setRequisicaoValida(String requisicaoValida) {
		this.requisicaoValida = requisicaoValida;
	}

	public Integer getIdJustificativaValidacao() {
		return idJustificativaValidacao;
	}

	public void setIdJustificativaValidacao(Integer idJustificativaValidacao) {
		this.idJustificativaValidacao = idJustificativaValidacao;
	}

	public String getJustificativaValidacao() {
		return justificativaValidacao;
	}

	public void setJustificativaValidacao(String justificativaValidacao) {
		this.justificativaValidacao = justificativaValidacao;
	}

	public String getComplementoJustificativaValidacao() {
		return complementoJustificativaValidacao;
	}

	public void setComplementoJustificativaValidacao(String complementoJustificativaValidacao) {
		this.complementoJustificativaValidacao = complementoJustificativaValidacao;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getResumoFuncao() {
		return resumoFuncao;
	}

	public void setResumoFuncao(String resumoFuncao) {
		this.resumoFuncao = resumoFuncao;
	}

	public String getDescricaoPerspectivaComplexidade() {
		return descricaoPerspectivaComplexidade;
	}

	public void setDescricaoPerspectivaComplexidade(String descricaoPerspectivaComplexidade) {
		this.descricaoPerspectivaComplexidade = descricaoPerspectivaComplexidade;
	}

	public Integer getNivelPerspectivaComplexidade() {
		return nivelPerspectivaComplexidade;
	}

	public void setNivelPerspectivaComplexidade(Integer nivelPerspectivaComplexidade) {
		this.nivelPerspectivaComplexidade = nivelPerspectivaComplexidade;
	}

	public Collection<PerspectivaComplexidadeDTO> getColPerspectivaComplexidade() {
		return colPerspectivaComplexidade;
	}

	public void setColPerspectivaComplexidade(Collection<PerspectivaComplexidadeDTO> colPerspectivaComplexidade) {
		this.colPerspectivaComplexidade = colPerspectivaComplexidade;
	}

	public String getDescricaoCompetenciasTecnicas() {
		return descricaoCompetenciasTecnicas;
	}

	public void setDescricaoCompetenciasTecnicas(String descricaoCompetenciasTecnicas) {
		this.descricaoCompetenciasTecnicas = descricaoCompetenciasTecnicas;
	}

	public Integer getNivelCompetenciasTecnicas() {
		return nivelCompetenciasTecnicas;
	}

	public void setNivelCompetenciasTecnicas(Integer nivelCompetenciasTecnicas) {
		this.nivelCompetenciasTecnicas = nivelCompetenciasTecnicas;
	}

	public Collection<CompetenciasTecnicasDTO> getColCompetenciasTecnicas() {
		return colCompetenciasTecnicas;
	}

	public void setColCompetenciasTecnicas(Collection<CompetenciasTecnicasDTO> colCompetenciasTecnicas) {
		this.colCompetenciasTecnicas = colCompetenciasTecnicas;
	}

	public String getDescricaoPerspectivaTecnica() {
		return descricaoPerspectivaTecnica;
	}

	public void setDescricaoPerspectivaTecnica(String descricaoPerspectivaTecnica) {
		this.descricaoPerspectivaTecnica = descricaoPerspectivaTecnica;
	}

	public Integer getNivelPerspectivaTecnica() {
		return nivelPerspectivaTecnica;
	}

	public void setNivelPerspectivaTecnica(Integer nivelPerspectivaTecnica) {
		this.nivelPerspectivaTecnica = nivelPerspectivaTecnica;
	}

	public Collection<PerspectivaTecnicaDTO> getColPerspectivaTecnica() {
		return colPerspectivaTecnica;
	}

	public void setColPerspectivaTecnica(Collection<PerspectivaTecnicaDTO> colPerspectivaTecnica) {
		this.colPerspectivaTecnica = colPerspectivaTecnica;
	}

	public String getDescricaoPerspectivaComportamental() {
		return descricaoPerspectivaComportamental;
	}

	public void setDescricaoPerspectivaComportamental(String descricaoPerspectivaComportamental) {
		this.descricaoPerspectivaComportamental = descricaoPerspectivaComportamental;
	}

	public Collection<PerspectivaComportamentalFuncaoDTO> getColPerspectivaComportamental() {
		return colPerspectivaComportamental;
	}

	public void setColPerspectivaComportamental(Collection<PerspectivaComportamentalFuncaoDTO> colPerspectivaComportamental) {
		this.colPerspectivaComportamental = colPerspectivaComportamental;
	}

	public String getDescricaoValida() {
		return descricaoValida;
	}

	public void setDescricaoValida(String descricaoValida) {
		this.descricaoValida = descricaoValida;
	}

	public String getJustificativaDescricaoFuncao() {
		return justificativaDescricaoFuncao;
	}

	public void setJustificativaDescricaoFuncao(String justificativaDescricaoFuncao) {
		this.justificativaDescricaoFuncao = justificativaDescricaoFuncao;
	}

	public String getComplementoJustificativaDescricaoFuncao() {
		return complementoJustificativaDescricaoFuncao;
	}

	public void setComplementoJustificativaDescricaoFuncao(String complementoJustificativaDescricaoFuncao) {
		this.complementoJustificativaDescricaoFuncao = complementoJustificativaDescricaoFuncao;
	}

	public Integer getIdFormacaoAcademica() {
		return idFormacaoAcademica;
	}

	public void setIdFormacaoAcademica(Integer idFormacaoAcademica) {
		this.idFormacaoAcademica = idFormacaoAcademica;
	}

	public Integer getIdCertificacao() {
		return idCertificacao;
	}

	public void setIdCertificacao(Integer idCertificacao) {
		this.idCertificacao = idCertificacao;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public Integer getIdIdioma() {
		return idIdioma;
	}

	public void setIdIdioma(Integer idIdioma) {
		this.idIdioma = idIdioma;
	}

	public Integer getIdConhecimento() {
		return idConhecimento;
	}

	public void setIdConhecimento(Integer idConhecimento) {
		this.idConhecimento = idConhecimento;
	}

	public Integer getIdAtitudeIndividual() {
		return idAtitudeIndividual;
	}

	public void setIdAtitudeIndividual(Integer idAtitudeIndividual) {
		this.idAtitudeIndividual = idAtitudeIndividual;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getColPerspectivaComportamentalSerialize() {
		return colPerspectivaComportamentalSerialize;
	}

	public void setColPerspectivaComportamentalSerialize(String colPerspectivaComportamentalSerialize) {
		this.colPerspectivaComportamentalSerialize = colPerspectivaComportamentalSerialize;
	}

	public Integer getDetalhePerspectivaComportamental() {
		return detalhePerspectivaComportamental;
	}

	public void setDetalhePerspectivaComportamental(Integer detalhePerspectivaComportamental) {
		this.detalhePerspectivaComportamental = detalhePerspectivaComportamental;
	}

	public String getColPerspectivaComplexidadeSerialize() {
		return colPerspectivaComplexidadeSerialize;
	}

	public void setColPerspectivaComplexidadeSerialize(String colPerspectivaComplexidadeSerialize) {
		this.colPerspectivaComplexidadeSerialize = colPerspectivaComplexidadeSerialize;
	}

	public String getDescricaoFormacaoAcademica() {
		return descricaoFormacaoAcademica;
	}

	public void setDescricaoFormacaoAcademica(String descricaoFormacaoAcademica) {
		this.descricaoFormacaoAcademica = descricaoFormacaoAcademica;
	}

	public Collection<PerspectivaTecnicaFormacaoAcademicaDTO> getColPerspectivaTecnicaFormacaoAcademica() {
		return colPerspectivaTecnicaFormacaoAcademica;
	}

	public void setColPerspectivaTecnicaFormacaoAcademica(Collection<PerspectivaTecnicaFormacaoAcademicaDTO> colPerspectivaTecnicaFormacaoAcademica) {
		this.colPerspectivaTecnicaFormacaoAcademica = colPerspectivaTecnicaFormacaoAcademica;
	}

	public String getDetalheFormacaoAcademica() {
		return detalheFormacaoAcademica;
	}

	public void setDetalheFormacaoAcademica(String detalheFormacaoAcademica) {
		this.detalheFormacaoAcademica = detalheFormacaoAcademica;
	}

	public String getColPerspectivaTecnicaFormacaoAcademicaSerialize() {
		return colPerspectivaTecnicaFormacaoAcademicaSerialize;
	}

	public void setColPerspectivaTecnicaFormacaoAcademicaSerialize(String colPerspectivaTecnicaFormacaoAcademicaSerialize) {
		this.colPerspectivaTecnicaFormacaoAcademicaSerialize = colPerspectivaTecnicaFormacaoAcademicaSerialize;
	}

	public String getObrigatorioFormacao() {
		return obrigatorioFormacao;
	}

	public void setObrigatorioFormacao(String obrigatorioFormacao) {
		this.obrigatorioFormacao = obrigatorioFormacao;
	}

	public String getDescricaoCertificacao() {
		return descricaoCertificacao;
	}

	public void setDescricaoCertificacao(String descricaoCertificacao) {
		this.descricaoCertificacao = descricaoCertificacao;
	}

	public String getDetalheCertificacaoCertificacao() {
		return detalheCertificacaoCertificacao;
	}

	public void setDetalheCertificacaoCertificacao(String detalheCertificacaoCertificacao) {
		this.detalheCertificacaoCertificacao = detalheCertificacaoCertificacao;
	}

	public String getObrigatorioCertificacao() {
		return obrigatorioCertificacao;
	}

	public void setObrigatorioCertificacao(String obrigatorioCertificacao) {
		this.obrigatorioCertificacao = obrigatorioCertificacao;
	}

	public Collection<PerspectivaTecnicaCertificacaoDTO> getColPerspectivaTecnicaCertificacao() {
		return colPerspectivaTecnicaCertificacao;
	}

	public void setColPerspectivaTecnicaCertificacao(Collection<PerspectivaTecnicaCertificacaoDTO> colPerspectivaTecnicaCertificacao) {
		this.colPerspectivaTecnicaCertificacao = colPerspectivaTecnicaCertificacao;
	}

	public String getColPerspectivaTecnicaCertificacaoSerialize() {
		return colPerspectivaTecnicaCertificacaoSerialize;
	}

	public void setColPerspectivaTecnicaCertificacaoSerialize(String colPerspectivaTecnicaCertificacaoSerialize) {
		this.colPerspectivaTecnicaCertificacaoSerialize = colPerspectivaTecnicaCertificacaoSerialize;
	}

	public String getDescricaoCurso() {
		return descricaoCurso;
	}

	public void setDescricaoCurso(String descricaoCurso) {
		this.descricaoCurso = descricaoCurso;
	}

	public String getDetalheCurso() {
		return detalheCurso;
	}

	public void setDetalheCurso(String detalheCurso) {
		this.detalheCurso = detalheCurso;
	}

	public String getObrigatorioCurso() {
		return obrigatorioCurso;
	}

	public void setObrigatorioCurso(String obrigatorioCurso) {
		this.obrigatorioCurso = obrigatorioCurso;
	}

	public Collection<PerspectivaTecnicaCursoDTO> getColPerspectivaTecnicaCurso() {
		return colPerspectivaTecnicaCurso;
	}

	public void setColPerspectivaTecnicaCurso(Collection<PerspectivaTecnicaCursoDTO> colPerspectivaTecnicaCurso) {
		this.colPerspectivaTecnicaCurso = colPerspectivaTecnicaCurso;
	}

	public String getColPerspectivaTecnicaCursoSerialize() {
		return colPerspectivaTecnicaCursoSerialize;
	}

	public void setColPerspectivaTecnicaCursoSerialize(String colPerspectivaTecnicaCursoSerialize) {
		this.colPerspectivaTecnicaCursoSerialize = colPerspectivaTecnicaCursoSerialize;
	}

	public String getDetalheIdioma() {
		return detalheIdioma;
	}

	public void setDetalheIdioma(String detalheIdioma) {
		this.detalheIdioma = detalheIdioma;
	}

	public String getDescricaoIdioma() {
		return descricaoIdioma;
	}

	public void setDescricaoIdioma(String descricaoIdioma) {
		this.descricaoIdioma = descricaoIdioma;
	}

	public String getColPerspectivaTecnicaIdiomaSerialize() {
		return colPerspectivaTecnicaIdiomaSerialize;
	}

	public void setColPerspectivaTecnicaIdiomaSerialize(String colPerspectivaTecnicaIdiomaSerialize) {
		this.colPerspectivaTecnicaIdiomaSerialize = colPerspectivaTecnicaIdiomaSerialize;
	}

	public Collection<PerspectivaTecnicaIdiomaDTO> getColPerspectivaTecnicaIdioma() {
		return colPerspectivaTecnicaIdioma;
	}

	public void setColPerspectivaTecnicaIdioma(Collection<PerspectivaTecnicaIdiomaDTO> colPerspectivaTecnicaIdioma) {
		this.colPerspectivaTecnicaIdioma = colPerspectivaTecnicaIdioma;
	}

	public String getObrigatorioIdioma() {
		return obrigatorioIdioma;
	}

	public void setObrigatorioIdioma(String obrigatorioIdioma) {
		this.obrigatorioIdioma = obrigatorioIdioma;
	}

	public String getDescricaoExperiencia() {
		return descricaoExperiencia;
	}

	public void setDescricaoExperiencia(String descricaoExperiencia) {
		this.descricaoExperiencia = descricaoExperiencia;
	}

	public String getObrigatorioExperiencia() {
		return obrigatorioExperiencia;
	}

	public void setObrigatorioExperiencia(String obrigatorioExperiencia) {
		this.obrigatorioExperiencia = obrigatorioExperiencia;
	}

	public String getDetalheExperiencia() {
		return detalheExperiencia;
	}

	public void setDetalheExperiencia(String detalheExperiencia) {
		this.detalheExperiencia = detalheExperiencia;
	}

	public Collection<PerspectivaTecnicaExperienciaDTO> getColPerspectivaTecnicaExperiencia() {
		return colPerspectivaTecnicaExperiencia;
	}

	public void setColPerspectivaTecnicaExperiencia(Collection<PerspectivaTecnicaExperienciaDTO> colPerspectivaTecnicaExperiencia) {
		this.colPerspectivaTecnicaExperiencia = colPerspectivaTecnicaExperiencia;
	}

	public String getColPerspectivaTecnicaExperienciaSerialize() {
		return colPerspectivaTecnicaExperienciaSerialize;
	}

	public void setColPerspectivaTecnicaExperienciaSerialize(String colPerspectivaTecnicaExperienciaSerialize) {
		this.colPerspectivaTecnicaExperienciaSerialize = colPerspectivaTecnicaExperienciaSerialize;
	}

	public String getColCompetenciasTecnicasSerialize() {
		return colCompetenciasTecnicasSerialize;
	}

	public void setColCompetenciasTecnicasSerialize(String colCompetenciasTecnicasSerialize) {
		this.colCompetenciasTecnicasSerialize = colCompetenciasTecnicasSerialize;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public String getNomeCargo() {
		return nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

}
