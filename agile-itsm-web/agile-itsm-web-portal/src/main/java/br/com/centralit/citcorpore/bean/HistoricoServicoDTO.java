package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class HistoricoServicoDTO implements IDto {
	private Integer idHistoricoServico;
	private Integer idServico;
	private Integer idCategoriaServico;
	private Integer idSituacaoServico;
	private Integer idTipoServico;
	private Integer idImportanciaNegocio;
	private Integer idEmpresa;
	private Integer idTipoEventoServico;
	private Integer idTipoDemandaServico;
	private Integer idLocalExecucaoServico;
	private String nomeServico;
	private String detalheServico;
	private String objetivo;
	private String passosServico;
	private java.sql.Date dataInicio;
	private String linkProcesso;
	private String descricaoProcesso;
	private String tipoDescProcess;
	private String dispPortal;
	private String quadroOrientPortal;
	private String siglaAbrev;
	private String deleted;
	private Integer idBaseconhecimento;
	
	private Integer idTemplateSolicitacao;
    private Integer idTemplateAcompanhamento;

    private String nomeCategoriaServico;
    private String nomeTipoServico;
    
    private java.sql.Date modificadoEm;
    private String modificadoPor;
    private String conteudodados;
	private java.sql.Date criadoEm;
	private String criadoPor;
    
	public Integer getIdServico(){
		return this.idServico;
	}
	public void setIdServico(Integer parm){
		this.idServico = parm;
	}

	public Integer getIdCategoriaServico(){
		return this.idCategoriaServico;
	}
	public void setIdCategoriaServico(Integer parm){
		this.idCategoriaServico = parm;
	}

	public Integer getIdSituacaoServico(){
		return this.idSituacaoServico;
	}
	public void setIdSituacaoServico(Integer parm){
		this.idSituacaoServico = parm;
	}

	public Integer getIdTipoServico(){
		return this.idTipoServico;
	}
	public void setIdTipoServico(Integer parm){
		this.idTipoServico = parm;
	}

	public Integer getIdImportanciaNegocio(){
		return this.idImportanciaNegocio;
	}
	public void setIdImportanciaNegocio(Integer parm){
		this.idImportanciaNegocio = parm;
	}

	public Integer getIdEmpresa(){
		return this.idEmpresa;
	}
	public void setIdEmpresa(Integer parm){
		this.idEmpresa = parm;
	}

	public Integer getIdTipoEventoServico(){
		return this.idTipoEventoServico;
	}
	public void setIdTipoEventoServico(Integer parm){
		this.idTipoEventoServico = parm;
	}

	public Integer getIdTipoDemandaServico(){
		return this.idTipoDemandaServico;
	}
	public void setIdTipoDemandaServico(Integer parm){
		this.idTipoDemandaServico = parm;
	}

	public Integer getIdLocalExecucaoServico(){
		return this.idLocalExecucaoServico;
	}
	public void setIdLocalExecucaoServico(Integer parm){
		this.idLocalExecucaoServico = parm;
	}

	public String getNomeServico(){
		return this.nomeServico;
	}
	public void setNomeServico(String parm){
		this.nomeServico = parm;
	}

	public String getDetalheServico(){
		return this.detalheServico;
	}
	public void setDetalheServico(String parm){
		this.detalheServico = parm;
	}

	public String getObjetivo(){
		return this.objetivo;
	}
	public void setObjetivo(String parm){
		this.objetivo = parm;
	}

	public String getPassosServico(){
		return this.passosServico;
	}
	public void setPassosServico(String parm){
		this.passosServico = parm;
	}

	public java.sql.Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(java.sql.Date parm){
		this.dataInicio = parm;
	}

	public String getLinkProcesso(){
		return this.linkProcesso;
	}
	public void setLinkProcesso(String parm){
		this.linkProcesso = parm;
	}

	public String getDescricaoProcesso(){
		return this.descricaoProcesso;
	}
	public void setDescricaoProcesso(String parm){
		this.descricaoProcesso = parm;
	}

	public String getTipoDescProcess(){
		return this.tipoDescProcess;
	}
	public void setTipoDescProcess(String parm){
		this.tipoDescProcess = parm;
	}

	public String getDispPortal(){
		return this.dispPortal;
	}
	public void setDispPortal(String parm){
		this.dispPortal = parm;
	}

	public String getQuadroOrientPortal(){
		return this.quadroOrientPortal;
	}
	public void setQuadroOrientPortal(String parm){
		this.quadroOrientPortal = parm;
	}

	public String getDeleted(){
		return this.deleted;
	}
	public void setDeleted(String parm){
		this.deleted = parm;
	}
	public String getSiglaAbrev() {
		return siglaAbrev;
	}
	public void setSiglaAbrev(String siglaAbrev) {
		this.siglaAbrev = siglaAbrev;
	}
	public Integer getIdBaseconhecimento() {
	    return idBaseconhecimento;
	}
	public void setIdBaseconhecimento(Integer idBaseconhecimento) {
	    this.idBaseconhecimento = idBaseconhecimento;
	}
    public Integer getIdTemplateSolicitacao() {
        return idTemplateSolicitacao;
    }
    public void setIdTemplateSolicitacao(Integer idTemplateSolicitacao) {
        this.idTemplateSolicitacao = idTemplateSolicitacao;
    }
    public Integer getIdTemplateAcompanhamento() {
        return idTemplateAcompanhamento;
    }
    public void setIdTemplateAcompanhamento(Integer idTemplateAcompanhamento) {
        this.idTemplateAcompanhamento = idTemplateAcompanhamento;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idServico == null) ? 0 : idServico.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoServicoDTO other = (HistoricoServicoDTO) obj;
		if (idServico == null) {
			if (other.idServico != null)
				return false;
		} else if (!idServico.equals(other.idServico))
			return false;
		return true;
	}
	public String getNomeCategoriaServico() {
		return nomeCategoriaServico;
	}
	public String getNomeTipoServico() {
		return nomeTipoServico;
	}
	public void setNomeTipoServico(String nomeTipoServico) {
		this.nomeTipoServico = nomeTipoServico;
	}
	public void setNomeCategoriaServico(String nomeCategoriaServico) {
		this.nomeCategoriaServico = nomeCategoriaServico;
	}
	public java.sql.Date getModificadoEm() {
		return modificadoEm;
	}
	public void setModificadoEm(java.sql.Date modificadoEm) {
		this.modificadoEm = modificadoEm;
	}
	public String getModificadoPor() {
		return modificadoPor;
	}
	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}
	public String getConteudodados() {
		return conteudodados;
	}
	public void setConteudodados(String conteudodados) {
		this.conteudodados = conteudodados;
	}
	public Integer getIdHistoricoServico() {
		return idHistoricoServico;
	}
	public void setIdHistoricoServico(Integer idHistoricoServico) {
		this.idHistoricoServico = idHistoricoServico;
	}
	public java.sql.Date getCriadoEm() {
		return criadoEm;
	}
	public void setCriadoEm(java.sql.Date criadoEm) {
		this.criadoEm = criadoEm;
	}
	public String getCriadoPor() {
		return criadoPor;
	}
	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}
}
