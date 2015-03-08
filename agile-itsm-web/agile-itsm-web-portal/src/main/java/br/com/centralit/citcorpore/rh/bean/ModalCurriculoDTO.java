package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModalCurriculoDTO implements IDto {

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
	private String cidadeNatal;
	private Integer idNaturalidade;
	private String observacoes;
	private Integer estadoCivil;
	private String filhos;
	private String estadoCivilExtenso;
	
	private String extensaoFoto;
	private Collection<UploadDTO> anexos;
	
	private String observacao;
	private String observacoesEntrevista;
	
	private String portadorNecessidadeEspecial;
	private Integer idItemListaTipoDeficiencia;
	
	private String colTelefones_Serialize;
	private String colEnderecos_Serialize;
	private String colEmail_Serialize;
	private String colFormacao_Serialize;
	private String colExperienciaProfissional_Serialize;
	private String colCertificacao_Serialize;
	private String colIdioma_Serialize;

	private UploadDTO foto;
	private String caminhoFoto;
	private String dataNascimentoStr;
	
	private String cpfFormatado;
	private String listaNegra;
	
	private Double notaAvaliacaoEntrevista;
	private Integer idEntrevistaCurriculo;
	private String classificacaoCandidato;
	private Integer idSolicitacao;

	private String naturalidade;
	
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
	public String getCidadeNatal() {
		return cidadeNatal;
	}
	public void setCidadeNatal(String cidadeNatal) {
		this.cidadeNatal = cidadeNatal;
	}
	public Integer getIdNaturalidade() {
		return idNaturalidade;
	}
	public void setIdNaturalidade(Integer idNaturalidade) {
		this.idNaturalidade = idNaturalidade;
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
	private Collection<TelefoneDTO> colTelefones;
	private Collection<EnderecoCurriculoDTO> colEnderecos;
	private Collection<FormacaoCurriculoDTO> colFormacao;
	private Collection<EmailCurriculoDTO> colEmail;
	private Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaProfissional;
	private Collection<CertificacaoCurriculoDTO> colCertificacao;
	private Collection<IdiomaCurriculoDTO> colIdioma;
	private Collection<CompetenciaCurriculoDTO> colCompetencias;

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
	
}