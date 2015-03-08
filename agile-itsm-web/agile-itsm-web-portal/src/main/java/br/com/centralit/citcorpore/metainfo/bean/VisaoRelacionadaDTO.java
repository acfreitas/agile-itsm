package br.com.centralit.citcorpore.metainfo.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class VisaoRelacionadaDTO implements IDto {
	public static String VINC_ABA_AO_LADO = "1";
	public static String VINC_ABA_FILHA = "2";
	
	public static String ACAO_RECUPERAR_PRINCIPAL = "RP";
	public static String ACAO_RECUPERAR_REGISTROS_VINCULADOS = "RV";
	public static String ACAO_SEM_ACAO = "XX";
	
	public static String PREFIXO_SISTEMA_TABELA_VINCULADA = "TABLE_SEARCH_";
	
	private Integer idVisaoRelacionada;
	private Integer idVisaoPai;
	private Integer idVisaoFilha;
	private Integer ordem;
	private String titulo;
	private String situacao;
	private String tipoVinculacao;
	private String acaoEmSelecaoPesquisa;
	private Integer idObjetoNegocioNN;
	
	private VisaoDTO visaoFilhaDto;
	
	private Collection colVinculosVisao;
	
	private String identificacaoVisaoFilha;
	private String nomeDBNegocioNN;

	public Integer getIdVisaoRelacionada(){
		return this.idVisaoRelacionada;
	}
	public void setIdVisaoRelacionada(Integer parm){
		this.idVisaoRelacionada = parm;
	}

	public Integer getIdVisaoPai(){
		return this.idVisaoPai;
	}
	public void setIdVisaoPai(Integer parm){
		this.idVisaoPai = parm;
	}

	public Integer getIdVisaoFilha(){
		return this.idVisaoFilha;
	}
	public void setIdVisaoFilha(Integer parm){
		this.idVisaoFilha = parm;
	}

	public Integer getOrdem(){
		return this.ordem;
	}
	public void setOrdem(Integer parm){
		this.ordem = parm;
	}

	public String getTitulo(){
		return this.titulo;
	}
	public void setTitulo(String parm){
		this.titulo = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public String getTipoVinculacao() {
		return tipoVinculacao;
	}
	public void setTipoVinculacao(String tipoVinculacao) {
		this.tipoVinculacao = tipoVinculacao;
	}
	public VisaoDTO getVisaoFilhaDto() {
		return visaoFilhaDto;
	}
	public void setVisaoFilhaDto(VisaoDTO visaoFilhaDto) {
		this.visaoFilhaDto = visaoFilhaDto;
	}
	public String getAcaoEmSelecaoPesquisa() {
		return acaoEmSelecaoPesquisa;
	}
	public void setAcaoEmSelecaoPesquisa(String acaoEmSelecaoPesquisa) {
		this.acaoEmSelecaoPesquisa = acaoEmSelecaoPesquisa;
	}
	public Integer getIdObjetoNegocioNN() {
		return idObjetoNegocioNN;
	}
	public void setIdObjetoNegocioNN(Integer idObjetoNegocioNN) {
		this.idObjetoNegocioNN = idObjetoNegocioNN;
	}
	public Collection getColVinculosVisao() {
		return colVinculosVisao;
	}
	public void setColVinculosVisao(Collection colVinculosVisao) {
		this.colVinculosVisao = colVinculosVisao;
	}
	public String getIdentificacaoVisaoFilha() {
	    return identificacaoVisaoFilha;
	}
	public void setIdentificacaoVisaoFilha(String identificacaoVisaoFilha) {
	    this.identificacaoVisaoFilha = identificacaoVisaoFilha;
	}
	public String getNomeDBNegocioNN() {
	    return nomeDBNegocioNN;
	}
	public void setNomeDBNegocioNN(String nomeDBNegocioNN) {
	    this.nomeDBNegocioNN = nomeDBNegocioNN;
	}

}
