package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;
import java.util.List;

import br.com.citframework.dto.IDto;

public class AcordoNivelServicoDTO implements IDto {

	private static final long serialVersionUID = -8242462699115061652L;

	private Integer idAcordoNivelServico;
	private Integer idServicoContrato;
	private Integer idPrioridadePadrao;
	private String situacao;
	private String tituloSLA;
	private Double disponibilidade;
	private String descricaoSLA;
	private String escopoSLA;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private java.sql.Date avaliarEm;
	private String tipo;
	private String permiteMudarImpUrg;
	private String impacto;
	private String urgencia;
	private String contatos;
	private String deleted;

	private Double valorLimite;
	private String detalheGlosa;
	private String detalheLimiteGlosa;
	private String unidadeValorLimite;

	private Double tempoAuto;
	private Integer idPrioridadeAuto1;
	private Integer idGrupo1;

	private Integer idFormula;

	private Timestamp criadoEm;
	private String criadoPor;
	private Timestamp modificadoEm;
	private String modificadoPor;

	private Integer[] hhCaptura = new Integer[5];
	private Integer[] hhResolucao = new Integer[5];
	private Integer[] mmCaptura = new Integer[5];
	private Integer[] mmResolucao = new Integer[5];

	private List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidade;
	private List<PrioridadeServicoUsuarioDTO> listaPrioridadeUsuario;
	private List<SlaRequisitoSlaDTO> listaslaRequisitoSlaDTO;
	private List<RevisarSlaDTO> listaRevisarSlaDTO;

	private Integer idEmail;

	public Integer getIdAcordoNivelServico() {
		return this.idAcordoNivelServico;
	}

	public void setIdAcordoNivelServico(Integer parm) {
		this.idAcordoNivelServico = parm;
	}

	public Integer getIdServicoContrato() {
		return this.idServicoContrato;
	}

	public void setIdServicoContrato(Integer parm) {
		this.idServicoContrato = parm;
	}

	public Integer getIdPrioridadePadrao() {
		return this.idPrioridadePadrao;
	}

	public void setIdPrioridadePadrao(Integer parm) {
		this.idPrioridadePadrao = parm;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String parm) {
		this.situacao = parm;
	}

	public String getTituloSLA() {
		return this.tituloSLA;
	}

	public void setTituloSLA(String parm) {
		this.tituloSLA = parm;
	}

	public Double getDisponibilidade() {
		return this.disponibilidade;
	}

	public void setDisponibilidade(Double parm) {
		this.disponibilidade = parm;
	}

	public String getDescricaoSLA() {
		return this.descricaoSLA;
	}

	public void setDescricaoSLA(String parm) {
		this.descricaoSLA = parm;
	}

	public String getEscopoSLA() {
		return this.escopoSLA;
	}

	public void setEscopoSLA(String parm) {
		this.escopoSLA = parm;
	}

	public java.sql.Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(java.sql.Date parm) {
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(java.sql.Date parm) {
		this.dataFim = parm;
	}

	public java.sql.Date getAvaliarEm() {
		return this.avaliarEm;
	}

	public void setAvaliarEm(java.sql.Date parm) {
		this.avaliarEm = parm;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String parm) {
		this.tipo = parm;
	}

	public String getDeleted() {
		if (deleted == null || deleted.trim().equalsIgnoreCase("")) {
			return "N";
		}
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Double getValorLimite() {
		return valorLimite;
	}

	public void setValorLimite(Double valorLimite) {
		this.valorLimite = valorLimite;
	}

	public String getDetalheGlosa() {
		return detalheGlosa;
	}

	public void setDetalheGlosa(String detalheGlosa) {
		this.detalheGlosa = detalheGlosa;
	}

	public String getDetalheLimiteGlosa() {
		return detalheLimiteGlosa;
	}

	public void setDetalheLimiteGlosa(String detalheLimiteGlosa) {
		this.detalheLimiteGlosa = detalheLimiteGlosa;
	}

	public String getUnidadeValorLimite() {
		return unidadeValorLimite;
	}

	public void setUnidadeValorLimite(String unidadeValorLimite) {
		this.unidadeValorLimite = unidadeValorLimite;
	}

	public String getPermiteMudarImpUrg() {
		return permiteMudarImpUrg;
	}

	public void setPermiteMudarImpUrg(String permiteMudarImpUrg) {
		this.permiteMudarImpUrg = permiteMudarImpUrg;
	}

	public String getImpacto() {
		return impacto;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	public String getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}

	public Integer getIdFormula() {
		return idFormula;
	}

	public void setIdFormula(Integer idFormula) {
		this.idFormula = idFormula;
	}

	public Timestamp getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Timestamp criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public Timestamp getModificadoEm() {
		return modificadoEm;
	}

	public void setModificadoEm(Timestamp modificadoEm) {
		this.modificadoEm = modificadoEm;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Double getTempoAuto() {
		return tempoAuto;
	}

	public void setTempoAuto(Double tempoAuto) {
		this.tempoAuto = tempoAuto;
	}

	public Integer getIdPrioridadeAuto1() {
		return idPrioridadeAuto1;
	}

	public void setIdPrioridadeAuto1(Integer idPrioridadeAuto1) {
		this.idPrioridadeAuto1 = idPrioridadeAuto1;
	}

	public Integer getIdGrupo1() {
		return idGrupo1;
	}

	public void setIdGrupo1(Integer idGrupo1) {
		this.idGrupo1 = idGrupo1;
	}

	public Integer[] getHhCaptura() {
		return hhCaptura;
	}

	public void setHhCaptura(Integer[] hhCaptura) {
		this.hhCaptura = hhCaptura;
	}

	public Integer[] getHhResolucao() {
		return hhResolucao;
	}

	public void setHhResolucao(Integer[] hhResolucao) {
		this.hhResolucao = hhResolucao;
	}

	public Integer[] getMmCaptura() {
		return mmCaptura;
	}

	public void setMmCaptura(Integer[] mmCaptura) {
		this.mmCaptura = mmCaptura;
	}

	public Integer[] getMmResolucao() {
		return mmResolucao;
	}

	public void setMmResolucao(Integer[] mmResolucao) {
		this.mmResolucao = mmResolucao;
	}

	public List<PrioridadeAcordoNivelServicoDTO> getListaPrioridadeUnidade() {
		return listaPrioridadeUnidade;
	}

	public void setListaPrioridadeUnidade(List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidade) {
		this.listaPrioridadeUnidade = listaPrioridadeUnidade;
	}

	public List<PrioridadeServicoUsuarioDTO> getListaPrioridadeUsuario() {
		return listaPrioridadeUsuario;
	}

	public void setListaPrioridadeUsuario(List<PrioridadeServicoUsuarioDTO> listaPrioridadeUsuario) {
		this.listaPrioridadeUsuario = listaPrioridadeUsuario;
	}

	public List<SlaRequisitoSlaDTO> getListaSlaRequisitoSlaDTO() {
		return listaslaRequisitoSlaDTO;
	}

	public void setListaSlaRequisitoSlaDTO(List<SlaRequisitoSlaDTO> listaslaRequisitoSlaDTO) {
		this.listaslaRequisitoSlaDTO = listaslaRequisitoSlaDTO;
	}

	public List<RevisarSlaDTO> getListaRevisarSlaDTO() {
		return listaRevisarSlaDTO;
	}

	public void setListaRevisarSlaDTO(List<RevisarSlaDTO> listaRevisarSlaDTO) {
		this.listaRevisarSlaDTO = listaRevisarSlaDTO;
	}

	public String getContatos() {
		return contatos;
	}

	public void setContatos(String contatos) {
		this.contatos = contatos;
	}

	public Integer getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}

}
