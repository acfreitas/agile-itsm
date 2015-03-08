package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class CamposObjetoNegocioDTO implements IDto {
	
	private Integer idCamposObjetoNegocio;
	private Integer idObjetoNegocio;
	private String nome;
	private String nomeDB;
	private String pk;
	private String sequence;
	private String unico;
	private String tipoDB;
	private Integer precisionDB;
	private String tipoNegocio;
	private String obrigatorio;
	private String situacao;
	private String filtro;
	private String descricao;
	private String order;
	private String formula;
	private String nomeTabelaDB;
	private ReturnLookupDTO returnLookupDTO;
	private Object value;

	public Integer getIdCamposObjetoNegocio(){
		return this.idCamposObjetoNegocio;
	}
	public void setIdCamposObjetoNegocio(Integer parm){
		this.idCamposObjetoNegocio = parm;
	}

	public Integer getIdObjetoNegocio(){
		return this.idObjetoNegocio;
	}
	public void setIdObjetoNegocio(Integer parm){
		this.idObjetoNegocio = parm;
	}

	public String getNome(){
		return this.nome;
	}
	public void setNome(String parm){
		this.nome = parm;
	}

	public String getNomeDB(){
		return this.nomeDB;
	}
	public void setNomeDB(String parm){
		this.nomeDB = parm;
	}

	public String getPk(){
		return this.pk;
	}
	public void setPk(String parm){
		this.pk = parm;
	}

	public String getSequence(){
		return this.sequence;
	}
	public void setSequence(String parm){
		this.sequence = parm;
	}

	public String getTipoDB(){
		return this.tipoDB;
	}
	public void setTipoDB(String parm){
		this.tipoDB = parm;
	}

	public String getObrigatorio(){
		return this.obrigatorio;
	}
	public void setObrigatorio(String parm){
		this.obrigatorio = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public String getUnico() {
		return unico;
	}
	public void setUnico(String unico) {
		this.unico = unico;
	}
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public ReturnLookupDTO getReturnLookupDTO() {
		return returnLookupDTO;
	}
	public void setReturnLookupDTO(ReturnLookupDTO returnLookupDTO) {
		this.returnLookupDTO = returnLookupDTO;
	}
	public String getNomeTabelaDB() {
		return nomeTabelaDB;
	}
	public void setNomeTabelaDB(String nomeTabelaDB) {
		this.nomeTabelaDB = nomeTabelaDB;
	}
	public String getTipoNegocio() {
		return tipoNegocio;
	}
	public void setTipoNegocio(String tipoNegocio) {
		this.tipoNegocio = tipoNegocio;
	}
	/**
	 * @return the precisionDB
	 */
	public Integer getPrecisionDB() {
		return precisionDB;
	}
	/**
	 * @param precisionDB the precisionDB to set
	 */
	public void setPrecisionDB(Integer precisionDB) {
		this.precisionDB = precisionDB;
	}
}
