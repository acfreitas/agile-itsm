package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.citframework.dto.IDto;

public class EntrevistaCandidatoDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idEntrevista;
	private Integer idCurriculo;
	private Integer idEntrevistadorRH;
	private Integer idEntrevistadorGestor;
	private Integer idTriagem;
	private Integer idProjeto;
	private Timestamp dataHora;
	private String caracteristicas;
	private String candidato;
	private String trabalhoEmEquipe;
	private String possuiOutraAtividade;
	private String outraAtividade;
	private String concordaExclusividade;
	private Double salarioAtual;
	private Double pretensaoSalarial;
	private Date dataDisponibilidade;
	private String competencias;
	private String observacoes;
	private String resultado;
	private String cargoPretendido;
	private String idade;
	private String planoCarreira;
	private String tipoEntrevista;
	private Integer idSolicitacaoServico;
	private String metodosAdicionais;
	private Double notaAvaliacao;
	private Double notaGestor;
	private String caminhoFoto;
	private String classificacao;
	private Boolean adimitido;
	private Integer qtdCandidatosAprovados;
	private String observacaoGestor;
	private String preRequisitoEntrevistaGestor;
	
	private Collection<UploadDTO> anexos;
    
	public String getPlanoCarreira() {
		return planoCarreira;
	}
	public void setPlanoCarreira(String planoCarreira) {
		this.planoCarreira = planoCarreira;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	public String getCargoPretendido() {
		return cargoPretendido;
	}
	public void setCargoPretendido(String cargoPretendido) {
		this.cargoPretendido = cargoPretendido;
	}
	private String serializeAtitudes;
	
	private Collection<AtitudeCandidatoDTO> colAtitudes;
	
	public String getCandidato() {
		return candidato;
	}
	public void setCandidato(String candidato) {
		this.candidato = candidato;
	}
	public Integer getIdEntrevista() {
		return idEntrevista;
	}
	public void setIdEntrevista(Integer idEntrevista) {
		this.idEntrevista = idEntrevista;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdEntrevistadorRH() {
		return idEntrevistadorRH;
	}
	public void setIdEntrevistadorRH(Integer idEntrevistadorRH) {
		this.idEntrevistadorRH = idEntrevistadorRH;
	}
	public Integer getIdEntrevistadorGestor() {
		return idEntrevistadorGestor;
	}
	public void setIdEntrevistadorGestor(Integer idEntrevistadorGestor) {
		this.idEntrevistadorGestor = idEntrevistadorGestor;
	}
	public Integer getIdTriagem() {
		return idTriagem;
	}
	public void setIdTriagem(Integer idTriagem) {
		this.idTriagem = idTriagem;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public String getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public String getTrabalhoEmEquipe() {
		return trabalhoEmEquipe;
	}
	public void setTrabalhoEmEquipe(String trabalhoEmEquipe) {
		this.trabalhoEmEquipe = trabalhoEmEquipe;
	}
	public String getPossuiOutraAtividade() {
		return possuiOutraAtividade;
	}
	public void setPossuiOutraAtividade(String possuiOutraAtividade) {
		this.possuiOutraAtividade = possuiOutraAtividade;
	}
	public String getCaminhoFoto() {
		return caminhoFoto;
	}
	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	public String getOutraAtividade() {
		return outraAtividade;
	}
	public void setOutraAtividade(String outraAtividade) {
		this.outraAtividade = outraAtividade;
	}
	public String getConcordaExclusividade() {
		return concordaExclusividade;
	}
	public void setConcordaExclusividade(String concordaExclusividade) {
		this.concordaExclusividade = concordaExclusividade;
	}
	public Double getSalarioAtual() {
		return salarioAtual;
	}
	public void setSalarioAtual(Double salarioAtual) {
		this.salarioAtual = salarioAtual;
	}
	public Double getPretensaoSalarial() {
		return pretensaoSalarial;
	}
	public void setPretensaoSalarial(Double pretensaoSalarial) {
		this.pretensaoSalarial = pretensaoSalarial;
	}
	public Date getDataDisponibilidade() {
		return dataDisponibilidade;
	}
	public void setDataDisponibilidade(Date dataDisponibilidade) {
		this.dataDisponibilidade = dataDisponibilidade;
	}
	public String getCompetencias() {
		return competencias;
	}
	public void setCompetencias(String competencias) {
		this.competencias = competencias;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public Collection<AtitudeCandidatoDTO> getColAtitudes() {
		return colAtitudes;
	}
	public void setColAtitudes(Collection<AtitudeCandidatoDTO> colAtitudes) {
		this.colAtitudes = colAtitudes;
	}
	public String getSerializeAtitudes() {
		return serializeAtitudes;
	}
	public void setSerializeAtitudes(String serializeAtitudes) {
		this.serializeAtitudes = serializeAtitudes;
	}
	public String getTipoEntrevista() {
		return tipoEntrevista;
	}
	public void setTipoEntrevista(String tipoEntrevista) {
		this.tipoEntrevista = tipoEntrevista;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Collection<UploadDTO> getAnexos() {
        return anexos;
    }
    public void setAnexos(Collection<UploadDTO> anexos) {
        this.anexos = anexos;
    }
	public String getMetodosAdicionais() {
		return metodosAdicionais;
	}
	public void setMetodosAdicionais(String metodosAdicionais) {
		this.metodosAdicionais = metodosAdicionais;
	}
	public Double getNotaAvaliacao() {
		return notaAvaliacao;
	}
	public void setNotaAvaliacao(Double notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}
	public Double getNotaGestor() {
		return notaGestor;
	}
	public void setNotaGestor(Double notaGestor) {
		this.notaGestor = notaGestor;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	public Boolean getAdimitido() {
		return adimitido;
	}
	public void setAdimitido(Boolean adimitido) {
		this.adimitido = adimitido;
	}
	public Integer getQtdCandidatosAprovados() {
		return qtdCandidatosAprovados;
	}
	public void setQtdCandidatosAprovados(Integer qtdCandidatosAprovados) {
		this.qtdCandidatosAprovados = qtdCandidatosAprovados;
	}
	public String getObservacaoGestor() {
		return observacaoGestor;
	}
	public void setObservacaoGestor(String observacaoGestor) {
		this.observacaoGestor = observacaoGestor;
	}
	public String getPreRequisitoEntrevistaGestor() {
		return preRequisitoEntrevistaGestor;
	}
	public void setPreRequisitoEntrevistaGestor(String preRequisitoEntrevistaGestor) {
		this.preRequisitoEntrevistaGestor = preRequisitoEntrevistaGestor;
	}
	
	
}
