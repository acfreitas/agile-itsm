package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CurriculoDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idCurriculo;
	
	private String nome;
	private Date dataNascimento;
	private String sexo;
	private String cpf;
	private Integer qtdeFilhos;
	private String rg;
	private String orgExpedidor;
	private String nomeProcura;
	private Integer idCidadeNatal;
	private Integer idEstadoNatal;
	private Integer idNacionalidade;
	private String nacionalidade;
	private String observacoes;
	private Integer estadoCivil;
	private String filhos;
	private String estadoCivilExtenso;
	private Double pretensaoSalarial;
	
	private String cidadeNatal;
	
	private Integer idUsuarioSessao;
	
	private String extensaoFoto;
	private Collection<UploadDTO> anexos;
	
	private String observacao;
	private String observacoesEntrevista;
	
	private Integer idCurso;
	
	private String portadorNecessidadeEspecial;
	private Integer idItemListaTipoDeficiencia;
	
	private String colTelefones_Serialize;
	private String colEnderecos_Serialize;
	private String colEmail_Serialize;
	private String colFormacao_Serialize;
	private String colExperienciaProfissional_Serialize;
	private String colCertificacao_Serialize;
	private String colIdioma_Serialize;
	private String colTreinamento_Serialize;

	private UploadDTO foto;
	private String caminhoFoto;
	private String dataNascimentoStr;
	
	private String cpfFormatado;
	private String listaNegra;
	
	private Double notaAvaliacaoEntrevista;
//	private Double notaRh;
//	private Double notaGestor;
	private Integer idEntrevistaCurriculo;
	private String classificacaoCandidato;
	private Integer idSolicitacao;
	
	private Integer idCandidato;

	private Integer idResponsavel;
	
	private String naturalidade;
	
	private EnderecoCurriculoDTO enderecoCurriculoDto;
//	---------------------Coleções------------------------
	
	private Collection<TelefoneDTO> colTelefones;
	private Collection<EnderecoCurriculoDTO> colEnderecos;
	private Collection<FormacaoCurriculoDTO> colFormacao;
	private Collection<EmailCurriculoDTO> colEmail;
	private Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaProfissional;
	private Collection<CertificacaoCurriculoDTO> colCertificacao;
	private Collection<IdiomaCurriculoDTO> colIdioma;
	private Collection<CompetenciaCurriculoDTO> colCompetencias;
	private Collection<TreinamentoCurriculoDTO> colTreinamentos;

	
//	-----------Info Endereço -----------------------------
	
	private Integer idEndereco;
	private Integer auxEnderecoPrincipal;
	private String logradouro;
	private String cep;
	private String complemento;
	private Integer idTipoEndereco;
	private String principal;
	private Integer idCidade;
	private String nomeCidade;
	private String nomeBairro;
	private Integer idUf;
	private String nomeUF;
	private Integer enderecoIdUF;
	private Integer numero;
	
	private String descricaoTipoEndereco;
	private String siglaUf;
	
//	-----------Info Email -----------------------------	
	
	private EmailCurriculoDTO emailCurriculoDto;

	private Integer auxEmailPrincipal;
	private Integer idEmail;
	private String descricaoEmail;
//	-----------Fim Info Email -----------------------------
	
	
//	-----------Info formação -----------------------------
	private FormacaoCurriculoDTO formacaoCurriculoDto;
	
	private String instituicao;
	private String descricaoTipoFormacao;
	private String decricaoSituacao;
	
	
//	-----------Fim Info formação -----------------------------	
	
//	-----------Info Idiomas -----------------------------
	private IdiomaCurriculoDTO idiomaCurriculoDto;
	
	private String  descIdNivelLeitura;
	private String  descIdNivelEscrita;
	private String  descIdNivelConversa;
	private String descricaoIdioma;
	
	
//	-----------Fim Info Idiomas -----------------------------	
	
//	-----------Info Experiencias -----------------------------
	private ExperienciaProfissionalCurriculoDTO experienciaProfissionalCurriculoDto;
	
	private Collection<FuncaoExperienciaProfissionalCurriculoDTO> colFuncao;
	private String descricaoEmpresa;
	private String localidade;
	
//	-----------Fim Info Experiencias -----------------------------	

//	-----------Info certificações -----------------------------
	private CertificacaoCurriculoDTO certificacaoCurriculoDto;
	
	private String descricao;
	private String versao;
	private Integer validade;
	
	
//	-----------Fim Info certificações -----------------------------	
	
//	-----------Info competencia -----------------------------
	private CompetenciaCurriculoDTO competenciaCurriculoDto;
	
	
	private String descricaoCompetencia;
	private Integer nivelCompetencia;
	private String nivelCompetenciaDesc;
	
	
//	-----------Fim Info certificações -----------------------------	
	
	
	
	public String getCpfFormatado() {
		return cpfFormatado;
	}
	public void setCpfFormatado(String cpfFormatado) {
		this.cpfFormatado = cpfFormatado;
	}
	public Collection<UploadDTO> getAnexos() {
		return anexos;
	}
	public void setAnexos(Collection<UploadDTO> anexos) {
		this.anexos = anexos;
	}
	public String getEstadoCivilExtenso() {
		return this.estadoCivilExtenso;
	}
	public String getObservacoesEntrevista() {
		return observacoesEntrevista;
	}
	public void setObservacoesEntrevista(String observacoesEntrevista) {
		this.observacoesEntrevista = observacoesEntrevista;
	}
	public String getExtensaoFoto() {
		return extensaoFoto;
	}
	public void setExtensaoFoto(String extensaoFoto) {
		this.extensaoFoto = extensaoFoto;
	}
	public Integer getIdCidadeNatal() {
		return idCidadeNatal;
	}
	public void setIdCidadeNatal(Integer idCidadeNatal) {
		this.idCidadeNatal = idCidadeNatal;
	}
	public Integer getIdNacionalidade() {
		return idNacionalidade;
	}
	public void setIdNacionalidade(Integer idNacionalidade) {
		this.idNacionalidade = idNacionalidade;
	}
	public String getFilhos() {
		return filhos;
	}
	public void setFilhos(String filhos) {
		this.filhos = filhos;
	}
	public Integer getQtdeFilhos() {
		return qtdeFilhos;
	}
	public void setQtdeFilhos(Integer qtdeFilhos) {
		this.qtdeFilhos = qtdeFilhos;
	}
	public Collection getColIdioma() {
		return colIdioma;
	}
	public void setColIdioma(Collection colIdioma) {
		this.colIdioma = colIdioma;
	}
	public String getNomeProcura() {
		return nomeProcura;
	}
	public void setNomeProcura(String nomeProcura) {
		this.nomeProcura = nomeProcura;
	}		
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
		if (this.dataNascimento != null)
			dataNascimentoStr = UtilDatas.dateToSTR(this.dataNascimento);
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Integer getIdEntrevistaCurriculo() {
		return idEntrevistaCurriculo;
	}
	public void setIdEntrevistaCurriculo(Integer idEntrevistaCurriculo) {
		this.idEntrevistaCurriculo = idEntrevistaCurriculo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
		if (this.cpf != null) {
			this.cpfFormatado = UtilFormatacao.formataCpf(cpf);
		}
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getOrgExpedidor() {
		return orgExpedidor;
	}
	public void setOrgExpedidor(String orgExpedidor) {
		this.orgExpedidor = orgExpedidor;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Integer getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(Integer estadoCivil) {
		this.estadoCivil = estadoCivil;
		this.estadoCivilExtenso = "";
		if (this.estadoCivilExtenso == null)
			return;
		switch(this.estadoCivil){
		case 1:
			this.estadoCivilExtenso = "Solteiro(a)";break;
		case 2:
			this.estadoCivilExtenso = "Casado(a)";break;
		case 3:
			this.estadoCivilExtenso = "Companheiro(a)";break;
		case 4:
			this.estadoCivilExtenso = "União estável";break;
		case 5:
			this.estadoCivilExtenso = "Separado(a)";break;
		case 6:
			this.estadoCivilExtenso = "Divorciado(a)";break;
		case 7:
			this.estadoCivilExtenso = "Viúvo(a)";break;
		}
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getPortadorNecessidadeEspecial() {
		return portadorNecessidadeEspecial;
	}
	public void setPortadorNecessidadeEspecial(String portadorNecessidadeEspecial) {
		this.portadorNecessidadeEspecial = portadorNecessidadeEspecial;
	}
	public Integer getIdItemListaTipoDeficiencia() {
		return idItemListaTipoDeficiencia;
	}
	public void setIdItemListaTipoDeficiencia(Integer idItemListaTipoDeficiencia) {
		this.idItemListaTipoDeficiencia = idItemListaTipoDeficiencia;
	}
	public Collection getColTelefones() {
		return colTelefones;
	}
	
	public void setColTelefones(Collection colTelefones) {
		this.colTelefones = colTelefones;
	}
	public Collection getColEnderecos() {
		return colEnderecos;
	}
	public void setColEnderecos(Collection colEnderecos) {
		this.colEnderecos = colEnderecos;
	}
	public Collection getColFormacao() {
		return colFormacao;
	}
	public void setColFormacao(Collection colFormacao) {
		this.colFormacao = colFormacao;
	}
	public Collection getColEmail() {
		return colEmail;
	}
	public void setColEmail(Collection<EmailCurriculoDTO> colEmail) {
		this.colEmail = colEmail;
	}
	public Collection getColExperienciaProfissional() {
		return colExperienciaProfissional;
	}
	public void setColExperienciaProfissional(Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaProfissional) {
		this.colExperienciaProfissional = colExperienciaProfissional;
	}

	public String getColIdioma_Serialize() {
		return colIdioma_Serialize;
	}
	public void setColIdioma_Serialize(String colIdioma_Serialize) {
		this.colIdioma_Serialize = colIdioma_Serialize;
	}
	public String getColCertificacao_Serialize() {
		return colCertificacao_Serialize;
	}
	public void setColCertificacao_Serialize(String colCertificacao_Serialize) {
		this.colCertificacao_Serialize = colCertificacao_Serialize;
	}
	public Collection getColCertificacao() {
		return colCertificacao;
	}
	public void setColCertificacao(Collection colCertificacao) {
		this.colCertificacao = colCertificacao;
	}
	public String getColTelefones_Serialize() {
		return colTelefones_Serialize;
	}
	public void setColTelefones_Serialize(String colTelefones_Serialize) {
		this.colTelefones_Serialize = colTelefones_Serialize;
	}
	public String getColEnderecos_Serialize() {
		return colEnderecos_Serialize;
	}
	public void setColEnderecos_Serialize(String colEnderecos_Serialize) {
		this.colEnderecos_Serialize = colEnderecos_Serialize;
	}
	public String getColEmail_Serialize() {
		return colEmail_Serialize;
	}
    public void setColEmail_Serialize(String colEmail_Serialize) {
		this.colEmail_Serialize = colEmail_Serialize;
	}
	public String getColFormacao_Serialize() {
		return colFormacao_Serialize;
	}
	public void setColFormacao_Serialize(String colFormacao_Serialize) {
		this.colFormacao_Serialize = colFormacao_Serialize;
	}
	public String getColExperienciaProfissional_Serialize() {
		return colExperienciaProfissional_Serialize;
	}
	public void setColExperienciaProfissional_Serialize(
			String colExperienciaProfissional_Serialize) {
		this.colExperienciaProfissional_Serialize = colExperienciaProfissional_Serialize;
	}
	public Collection getColCompetencias() {
		return colCompetencias;
	}
	public void setColCompetencias(Collection colCompetencias) {
		this.colCompetencias = colCompetencias;
	}
	public UploadDTO getFoto() {
		return foto;
	}
	public void setFoto(UploadDTO foto) {
		this.foto = foto;
	}
	public String getCaminhoFoto() {
		return caminhoFoto;
	}
	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	public void setEstadoCivilExtenso(String estadoCivilExtenso) {
		this.estadoCivilExtenso = estadoCivilExtenso;
	}
	public String getDataNascimentoStr() {
		return dataNascimentoStr;
	}
	public void setDataNascimentoStr(String dataNascimentoStr) {
		this.dataNascimentoStr = dataNascimentoStr;
	}
	public String getListaNegra() {
		return listaNegra;
	}
	public void setListaNegra(String listaNegra) {
		this.listaNegra = listaNegra;
	}
	public Double getNotaAvaliacaoEntrevista() {
		return notaAvaliacaoEntrevista;
	}
	public void setNotaAvaliacaoEntrevista(Double notaAvaliacaoEntrevista) {
		this.notaAvaliacaoEntrevista = notaAvaliacaoEntrevista;
	}
//	public Double getNotaRh() {
//		return notaRh;
//	}
//	public void setNotaRh(Double notaRh) {
//		this.notaRh = notaRh;
//	}
//	public Double getNotaGestor() {
//		return notaGestor;
//	}
//	public void setNotaGestor(Double notaGestor) {
//		this.notaGestor = notaGestor;
//	}
	public String getClassificacaoCandidato() {
		return classificacaoCandidato;
	}
	public void setClassificacaoCandidato(String classificacaoCandidato) {
		this.classificacaoCandidato = classificacaoCandidato;
	}
	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}
	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	
	public Integer getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}
	
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	
	
//	--------------------Endereco----------------------------
	
	public EnderecoCurriculoDTO getEnderecoCurriculoDto() {
		return enderecoCurriculoDto;
	}
	public void setEnderecoCurriculoDto(EnderecoCurriculoDTO enderecoCurriculoDto) {
		this.enderecoCurriculoDto = enderecoCurriculoDto;
		this.setIdEndereco(enderecoCurriculoDto.getIdEndereco());
		this.setNumero(enderecoCurriculoDto.getNumero());
		this.setLogradouro(enderecoCurriculoDto.getLogradouro());
		this.setCep(enderecoCurriculoDto.getCep());
		this.setComplemento(enderecoCurriculoDto.getComplemento());
		this.setIdTipoEndereco(enderecoCurriculoDto.getIdTipoEndereco());
		this.setPrincipal(enderecoCurriculoDto.getPrincipal());
		this.setNomeCidade(enderecoCurriculoDto.getNomeCidade());
		this.setNomeBairro(enderecoCurriculoDto.getNomeBairro());
		this.setNomeUF(enderecoCurriculoDto.getNomeUF());
		this.setEnderecoIdUF(enderecoCurriculoDto.getEnderecoIdUF());
		this.setDescricaoTipoEndereco(enderecoCurriculoDto.getDescricaoTipoEndereco());
		this.setSiglaUf(enderecoCurriculoDto.getSiglaUf());
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public Integer getIdTipoEndereco() {
		return idTipoEndereco;
	}
	public void setIdTipoEndereco(Integer idTipoEndereco) {
		this.idTipoEndereco = idTipoEndereco;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public String getNomeBairro() {
		return nomeBairro;
	}
	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}
	public String getNomeUF() {
		return nomeUF;
	}
	public void setNomeUF(String nomeUF) {
		this.nomeUF = nomeUF;
	}
	public Integer getEnderecoIdUF() {
		return enderecoIdUF;
	}
	public void setEnderecoIdUF(Integer enderecoIdUF) {
		this.enderecoIdUF = enderecoIdUF;
	}
	public String getDescricaoTipoEndereco() {
		return descricaoTipoEndereco;
	}
	public void setDescricaoTipoEndereco(String descricaoTipoEndereco) {
		this.descricaoTipoEndereco = descricaoTipoEndereco;
	}
	public String getSiglaUf() {
		return siglaUf;
	}
	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	//------------email--------------------------------------------------
	public EmailCurriculoDTO getEmailCurriculoDto() {
		return emailCurriculoDto;
	}
	public void setEmailCurriculoDto(EmailCurriculoDTO emailCurriculoDto) {
		this.emailCurriculoDto = emailCurriculoDto;
		this.setDescricaoEmail(emailCurriculoDto.getDescricaoEmail());
		this.setIdEmail(emailCurriculoDto.getIdEmail());
	}
	public Integer getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}
	public String getDescricaoEmail() {
		return descricaoEmail;
	}
	public void setDescricaoEmail(String descricaoEmail) {
		this.descricaoEmail = descricaoEmail;
	}
	
	
	//-------------Formação -------------------------
	public FormacaoCurriculoDTO getFormacaoCurriculoDto() {
		return formacaoCurriculoDto;
	}
	public void setFormacaoCurriculoDto(FormacaoCurriculoDTO formacaoCurriculoDto) {
		this.formacaoCurriculoDto = formacaoCurriculoDto;
		this.setInstituicao(formacaoCurriculoDto.getInstituicao());
		this.setDescricaoTipoFormacao(formacaoCurriculoDto.getDescricaoTipoFormacao());
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public String getDescricaoTipoFormacao() {
		return descricaoTipoFormacao;
	}
	public void setDescricaoTipoFormacao(String descricaoTipoFormacao) {
		this.descricaoTipoFormacao = descricaoTipoFormacao;
	}
	public String getDecricaoSituacao() {
		return decricaoSituacao;
	}
	public void setDecricaoSituacao(String decricaoSituacao) {
		this.decricaoSituacao = decricaoSituacao;
	}
	
	//-------------Idiomas------------------------
	public IdiomaCurriculoDTO getIdiomaCurriculoDto() {
		return idiomaCurriculoDto;
	}
	public void setIdiomaCurriculoDto(IdiomaCurriculoDTO idiomaCurriculoDto) {
		this.idiomaCurriculoDto = idiomaCurriculoDto;
		this.setDescIdNivelLeitura(idiomaCurriculoDto.getDescIdNivelLeitura());
		this.setDescIdNivelEscrita(idiomaCurriculoDto.getDescIdNivelEscrita());
		this.setDescIdNivelConversa(idiomaCurriculoDto.getDescIdNivelConversa());
		this.setDescricaoIdioma(idiomaCurriculoDto.getDescricaoIdioma());
	}
	public String getDescIdNivelLeitura() {
		return descIdNivelLeitura;
	}
	public void setDescIdNivelLeitura(String descIdNivelLeitura) {
		this.descIdNivelLeitura = descIdNivelLeitura;
	}
	public String getDescIdNivelEscrita() {
		return descIdNivelEscrita;
	}
	public void setDescIdNivelEscrita(String descIdNivelEscrita) {
		this.descIdNivelEscrita = descIdNivelEscrita;
	}
	public String getDescIdNivelConversa() {
		return descIdNivelConversa;
	}
	public void setDescIdNivelConversa(String descIdNivelConversa) {
		this.descIdNivelConversa = descIdNivelConversa;
	}
	public String getDescricaoIdioma() {
		return descricaoIdioma;
	}
	public void setDescricaoIdioma(String descricaoIdioma) {
		this.descricaoIdioma = descricaoIdioma;
	}
	
	//---------------------Experiencia----------------------
	public ExperienciaProfissionalCurriculoDTO getExperienciaProfissionalCurriculoDto() {
		return experienciaProfissionalCurriculoDto;
	}
	public void setExperienciaProfissionalCurriculoDto(ExperienciaProfissionalCurriculoDTO experienciaProfissionalCurriculoDto) {
		this.experienciaProfissionalCurriculoDto = experienciaProfissionalCurriculoDto;
		this.setColFuncao(experienciaProfissionalCurriculoDto.getColFuncao());
		this.setDescricaoEmpresa(experienciaProfissionalCurriculoDto.getDescricaoEmpresa());
		this.setLocalidade(experienciaProfissionalCurriculoDto.getLocalidade());
	}
	public Collection<FuncaoExperienciaProfissionalCurriculoDTO> getColFuncao() {
		return colFuncao;
	}
	public void setColFuncao(
			Collection<FuncaoExperienciaProfissionalCurriculoDTO> colFuncao) {
		this.colFuncao = colFuncao;
	}
	public String getDescricaoEmpresa() {
		return descricaoEmpresa;
	}
	public void setDescricaoEmpresa(String descricaoEmpresa) {
		this.descricaoEmpresa = descricaoEmpresa;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	//------------------certificações-----------------------
	public CertificacaoCurriculoDTO getCertificacaoCurriculoDto() {
		return certificacaoCurriculoDto;
	}
	public void setCertificacaoCurriculoDto(CertificacaoCurriculoDTO certificacaoCurriculoDto) {
		this.certificacaoCurriculoDto = certificacaoCurriculoDto;
		this.setDescricao(certificacaoCurriculoDto.getDescricao());
		this.setVersao(certificacaoCurriculoDto.getVersao());
		this.setValidade(certificacaoCurriculoDto.getValidade());
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public Integer getValidade() {
		return validade;
	}
	public void setValidade(Integer validade) {
		this.validade = validade;
	}
	
	//------------competencia-----------
	public CompetenciaCurriculoDTO getCompetenciaCurriculoDto() {
		return competenciaCurriculoDto;
	}
	public void setCompetenciaCurriculoDto(CompetenciaCurriculoDTO competenciaCurriculoDto) {
		this.competenciaCurriculoDto = competenciaCurriculoDto;
		this.setDescricaoCompetencia(competenciaCurriculoDto.getDescricaoCompetencia());
		this.setNivelCompetencia(competenciaCurriculoDto.getNivelCompetencia());
		this.setNivelCompetenciaDesc(competenciaCurriculoDto.getNivelCompetenciaDesc());
	}
	public String getDescricaoCompetencia() {
		return descricaoCompetencia;
	}
	public void setDescricaoCompetencia(String descricaoCompetencia) {
		this.descricaoCompetencia = descricaoCompetencia;
	}
	public Integer getNivelCompetencia() {
		return nivelCompetencia;
	}
	public void setNivelCompetencia(Integer nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
	}
	public String getNivelCompetenciaDesc() {
		return nivelCompetenciaDesc;
	}
	public void setNivelCompetenciaDesc(String nivelCompetenciaDesc) {
		this.nivelCompetenciaDesc = nivelCompetenciaDesc;
	}
	public Double getPretensaoSalarial() {
		return pretensaoSalarial;
	}
	public void setPretensaoSalarial(Double pretensaoSalarial) {
		this.pretensaoSalarial = pretensaoSalarial;
	}
	public Integer getIdEstadoNatal() {
		return idEstadoNatal;
	}
	public void setIdEstadoNatal(Integer idEstadoNatal) {
		this.idEstadoNatal = idEstadoNatal;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	public Integer getAuxEmailPrincipal() {
		return auxEmailPrincipal;
	}
	public void setAuxEmailPrincipal(Integer auxEmailPrincipal) {
		this.auxEmailPrincipal = auxEmailPrincipal;
	}
	public Integer getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}
	public Integer getIdUf() {
		return idUf;
	}
	public void setIdUf(Integer idUf) {
		this.idUf = idUf;
	}
	public Integer getAuxEnderecoPrincipal() {
		return auxEnderecoPrincipal;
	}
	public void setAuxEnderecoPrincipal(Integer auxEnderecoPrincipal) {
		this.auxEnderecoPrincipal = auxEnderecoPrincipal;
	}
	public String getColTreinamento_Serialize() {
		return colTreinamento_Serialize;
	}
	public void setColTreinamento_Serialize(String colTreinamento_Serialize) {
		this.colTreinamento_Serialize = colTreinamento_Serialize;
	}
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	public Collection<TreinamentoCurriculoDTO> getColTreinamentos() {
		return colTreinamentos;
	}
	public void setColTreinamentos(Collection<TreinamentoCurriculoDTO> colTreinamentos) {
		this.colTreinamentos = colTreinamentos;
	}
	public Integer getIdUsuarioSessao() {
		return idUsuarioSessao;
	}
	public void setIdUsuarioSessao(Integer idUsuarioSessao) {
		this.idUsuarioSessao = idUsuarioSessao;
	}
	public String getCidadeNatal() {
		return cidadeNatal;
	}
	public void setCidadeNatal(String cidadeNatal) {
		this.cidadeNatal = cidadeNatal;
	}
	
}