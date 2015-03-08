package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;

public class RequisicaoPessoalDTO extends SolicitacaoServicoDTO {
	
	private static final long serialVersionUID = 8102487667761376785L;
	
	public static final String ACAO_CRIACAO = "C";
	public static final String ACAO_ANALISE = "A";
	public static final String ACAO_TRIAGEM = "T";
	public static final String ACAO_ENTREVISTA_RH = "R";
	public static final String ACAO_ENTREVISTA_GESTOR = "G";
	
	private Integer idCargo;
	private Integer idFuncao;
	private Integer vagas;
	private String confidencial;
	private String tipoContratacao;
	private String motivoContratacao;
	private Double salario;
	private String beneficios;
	private String horario;
	private Integer idCentroCusto;
	private Integer idProjeto;
	private String folgas;
	private String rejeitada;
	private Integer idParecerValidacao;
	private Integer idJustificativaValidacao;
	private String complemJustificativaValidacao;
	
	private Integer idLotacao;
	
	private Integer paginaSelecionada;
	
	private String atividades;
	private String observacoes;

	private String acao;
	
	private Integer idCidade;
	private Integer idPais;
	private Integer idUf;
	private Integer idJornada;	
	private String salarioACombinar;
	
	private Collection<RequisicaoFormacaoAcademicaDTO> colFormacaoAcademica;
	private Collection<RequisicaoCertificacaoDTO> colCertificacao;
	private Collection<RequisicaoCursoDTO> colCurso;
	private Collection<RequisicaoExperienciaInformaticaDTO> colExperienciaInformatica;
	private Collection<RequisicaoIdiomaDTO> colIdioma;
	private Collection<RequisicaoExperienciaAnteriorDTO> colExperienciaAnterior;
	private Collection<RequisicaoConhecimentoDTO> colConhecimento;
	private Collection<RequisicaoHabilidadeDTO> colHabilidade;
	private Collection<RequisicaoAtitudeIndividualDTO> colAtitudeIndividual;
	private Collection<TriagemRequisicaoPessoalDTO> colTriagem;
	
    private String serializeFormacaoAcademica;
    private String serializeCertificacao;
    private String serializeCurso;
    private String serializeExperienciaInformatica;
    private String serializeIdioma;
    private String serializeExperienciaAnterior;
    private String serializeConhecimento;
    private String serializeHabilidade;
    private String serializeAtitudeIndividual;
    private String serializeTriagem;
    private String colecaoCurriculo;
    
    
  
	private String chkIdioma;
	private String chkFormacao;
	private String chkCertificacao;
	private Date dataNascimento;
	private String sexo;
	private String cpf;
	private String preRequisitoEntrevistaGestor;
	
	//atributos para a pesquisa curriculo
		private String pesquisa_chave;
		private String pesquisa_cidade;
		private String pesquisa_formacao;
		private String pesquisa_certificacao;
		private String pesquisa_empresa;
		private String pesquisa_idiomas;
		
		private Integer idEntrevistaClassificacao;
		private String classificacaoEntrevista;
		private String classificacao;
		private String acaoManterGravarTarefa;
		private Integer qtdCandidatosAprovados;
		
		private String indiceCurriculoListaNegra;
		private String justificativaRejeicao;
	
	private String motivoDesistenciaCandidato;
		
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	private Integer idSolicitacaoServicoCurriculo;
	public String getChkIdioma() {
		return chkIdioma;
	}
	public void setChkIdioma(String chkIdioma) {
		this.chkIdioma = chkIdioma;
	}
	public String getChkFormacao() {
		return chkFormacao;
	}
	public void setChkFormacao(String chkFormacao) {
		this.chkFormacao = chkFormacao;
	}
	public String getChkCertificacao() {
		return chkCertificacao;
	}
	public void setChkCertificacao(String chkCertificacao) {
		this.chkCertificacao = chkCertificacao;
	}
	public Integer getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}
	public Integer getIdPais() {
		return idPais;
	}
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
	public Integer getIdUf() {
		return idUf;
	}
	public void setIdUf(Integer idUf) {
		this.idUf = idUf;
	}
	public Integer getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}
	public Integer getIdFuncao() {
		return idFuncao;
	}
	public void setIdFuncao(Integer idFuncao) {
		this.idFuncao = idFuncao;
	}
	public Integer getVagas() {
		return vagas;
	}
	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}
	public String getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(String confidencial) {
		this.confidencial = confidencial;
	}
	public String getTipoContratacao() {
		return tipoContratacao;
	}
	public void setTipoContratacao(String tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}
	
	public String getMotivoContratacao() {
		return motivoContratacao;
	}
	public void setMotivoContratacao(String motivoContratacao) {
		this.motivoContratacao = motivoContratacao;
	}
	public static String getAcaoCriacao() {
		return ACAO_CRIACAO;
	}
	public static String getAcaoAnalise() {
		return ACAO_ANALISE;
	}
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	public String getBeneficios() {
		return beneficios;
	}
	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public Integer getIdCentroCusto() {
		return idCentroCusto;
	}
	public void setIdCentroCusto(Integer idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public String getFolgas() {
		return folgas;
	}
	public void setFolgas(String folgas) {
		this.folgas = folgas;
	}
	public Collection<RequisicaoFormacaoAcademicaDTO> getColFormacaoAcademica() {
		return colFormacaoAcademica;
	}
	public void setColFormacaoAcademica(
			Collection<RequisicaoFormacaoAcademicaDTO> colFormacaoAcademica) {
		this.colFormacaoAcademica = colFormacaoAcademica;
	}
	public Collection<RequisicaoCertificacaoDTO> getColCertificacao() {
		return colCertificacao;
	}
	public void setColCertificacao(Collection<RequisicaoCertificacaoDTO> colCertificacao) {
		this.colCertificacao = colCertificacao;
	}
	public Collection<RequisicaoCursoDTO> getColCurso() {
		return colCurso;
	}
	public void setColCurso(Collection<RequisicaoCursoDTO> colCurso) {
		this.colCurso = colCurso;
	}
	public Collection<RequisicaoExperienciaInformaticaDTO> getColExperienciaInformatica() {
		return colExperienciaInformatica;
	}
	public void setColExperienciaInformatica(
			Collection<RequisicaoExperienciaInformaticaDTO> colExperienciaInformatica) {
		this.colExperienciaInformatica = colExperienciaInformatica;
	}
	public Collection<RequisicaoIdiomaDTO> getColIdioma() {
		return colIdioma;
	}
	public void setColIdioma(Collection<RequisicaoIdiomaDTO> colIdioma) {
		this.colIdioma = colIdioma;
	}
	public Collection<RequisicaoExperienciaAnteriorDTO> getColExperienciaAnterior() {
		return colExperienciaAnterior;
	}
	public void setColExperienciaAnterior(
			Collection<RequisicaoExperienciaAnteriorDTO> colExperienciaAnterior) {
		this.colExperienciaAnterior = colExperienciaAnterior;
	}
	public Collection<RequisicaoConhecimentoDTO> getColConhecimento() {
		return colConhecimento;
	}
	public void setColConhecimento(Collection<RequisicaoConhecimentoDTO> colConhecimento) {
		this.colConhecimento = colConhecimento;
	}
	public Collection<RequisicaoHabilidadeDTO> getColHabilidade() {
		return colHabilidade;
	}
	public void setColHabilidade(Collection<RequisicaoHabilidadeDTO> colHabilidade) {
		this.colHabilidade = colHabilidade;
	}
	public Collection<RequisicaoAtitudeIndividualDTO> getColAtitudeIndividual() {
		return colAtitudeIndividual;
	}
	public void setColAtitudeIndividual(
			Collection<RequisicaoAtitudeIndividualDTO> colAtitudeIndividual) {
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
	public Integer getIdParecerValidacao() {
		return idParecerValidacao;
	}
	public void setIdParecerValidacao(Integer idParecerValidacao) {
		this.idParecerValidacao = idParecerValidacao;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
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
	public Collection<TriagemRequisicaoPessoalDTO> getColTriagem() {
		return colTriagem;
	}
	public void setColTriagem(Collection<TriagemRequisicaoPessoalDTO> colTriagem) {
		this.colTriagem = colTriagem;
	}
	public String getSerializeTriagem() {
		return serializeTriagem;
	}
	public void setSerializeTriagem(String serializeTriagem) {
		this.serializeTriagem = serializeTriagem;
	}

	public String getRejeitada() {
		return rejeitada;
	}
	public void setRejeitada(String rejeitada) {
		this.rejeitada = rejeitada;
	}
	public Integer getIdJornada() {
		return idJornada;
	}
	public void setIdJornada(Integer idJornada) {
		this.idJornada = idJornada;
	}
	public String getSalarioACombinar() {
		return salarioACombinar;
	}
	public void setSalarioACombinar(String salarioACombinar) {
		this.salarioACombinar = salarioACombinar;
	}
	public Integer getIdSolicitacaoServicoCurriculo() {
		return idSolicitacaoServicoCurriculo;
	}
	public void setIdSolicitacaoServicoCurriculo(
			Integer idSolicitacaoServicoCurriculo) {
		this.idSolicitacaoServicoCurriculo = idSolicitacaoServicoCurriculo;
	}
	
	  public String getColecaoCurriculo() {
			return colecaoCurriculo;
		}
		public void setColecaoCurriculo(String colecaoCurriculo) {
			this.colecaoCurriculo = colecaoCurriculo;
		}
		public String getPesquisa_chave() {
			return pesquisa_chave;
		}
		public void setPesquisa_chave(String pesquisa_chave) {
			this.pesquisa_chave = pesquisa_chave;
		}
		
		public String getPesquisa_cidade() {
			return pesquisa_cidade;
		}
		public void setPesquisa_cidade(String pesquisa_cidade) {
			this.pesquisa_cidade = pesquisa_cidade;
		}
		public String getPesquisa_formacao() {
			return pesquisa_formacao;
		}
		public void setPesquisa_formacao(String pesquisa_formacao) {
			this.pesquisa_formacao = pesquisa_formacao;
		}
		
		public String getPesquisa_certificacao() {
			return pesquisa_certificacao;
		}
		public void setPesquisa_certificacao(String pesquisa_certificacao) {
			this.pesquisa_certificacao = pesquisa_certificacao;
		}
		public String getPesquisa_empresa() {
			return pesquisa_empresa;
		}
		public void setPesquisa_empresa(String pesquisa_empresa) {
			this.pesquisa_empresa = pesquisa_empresa;
		}
		public String getPesquisa_idiomas() {
			return pesquisa_idiomas;
		}
		public void setPesquisa_idiomas(String pesquisa_idiomas) {
			this.pesquisa_idiomas = pesquisa_idiomas;
		}
		public String getPreRequisitoEntrevistaGestor() {
			return preRequisitoEntrevistaGestor;
		}
		public void setPreRequisitoEntrevistaGestor(String preRequisitoEntrevistaGestor) {
			this.preRequisitoEntrevistaGestor = preRequisitoEntrevistaGestor;
		}
		public Integer getIdEntrevistaClassificacao() {
			return idEntrevistaClassificacao;
		}
		public void setIdEntrevistaClassificacao(Integer idEntrevistaClassificacao) {
			this.idEntrevistaClassificacao = idEntrevistaClassificacao;
		}
		public String getClassificacaoEntrevista() {
			return classificacaoEntrevista;
		}
		public void setClassificacaoEntrevista(String classificacaoEntrevista) {
			this.classificacaoEntrevista = classificacaoEntrevista;
		}
		public String getClassificacao() {
			return classificacao;
		}
		public void setClassificacao(String classificacao) {
			this.classificacao = classificacao;
		}
		public String getAcaoManterGravarTarefa() {
			return acaoManterGravarTarefa;
		}
		public void setAcaoManterGravarTarefa(String acaoManterGravarTarefa) {
			this.acaoManterGravarTarefa = acaoManterGravarTarefa;
		}
		public Integer getQtdCandidatosAprovados() {
			return qtdCandidatosAprovados;
		}
		public void setQtdCandidatosAprovados(Integer qtdCandidatosAprovados) {
			this.qtdCandidatosAprovados = qtdCandidatosAprovados;
		}
		public String getIndiceCurriculoListaNegra() {
			return indiceCurriculoListaNegra;
		}
		public void setIndiceCurriculoListaNegra(String indiceCurriculoListaNegra) {
			this.indiceCurriculoListaNegra = indiceCurriculoListaNegra;
		}
		public String getJustificativaRejeicao() {
			return justificativaRejeicao;
		}
		public void setJustificativaRejeicao(String justificativaRejeicao) {
			this.justificativaRejeicao = justificativaRejeicao;
		}
		public String getMotivoDesistenciaCandidato() {
			return motivoDesistenciaCandidato;
		}
		public void setMotivoDesistenciaCandidato(String motivoDesistenciaCandidato) {
			this.motivoDesistenciaCandidato = motivoDesistenciaCandidato;
		}
		public Integer getIdLotacao() {
			return idLotacao;
		}
		public void setIdLotacao(Integer idLotacao) {
			this.idLotacao = idLotacao;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public static String getAcaoTriagem() {
			return ACAO_TRIAGEM;
		}
		public static String getAcaoEntrevistaRh() {
			return ACAO_ENTREVISTA_RH;
		}
		public static String getAcaoEntrevistaGestor() {
			return ACAO_ENTREVISTA_GESTOR;
		}
		public Integer getPaginaSelecionada() {
			return paginaSelecionada;
		}
		public void setPaginaSelecionada(Integer paginaSelecionada) {
			this.paginaSelecionada = paginaSelecionada;
		}
		
}