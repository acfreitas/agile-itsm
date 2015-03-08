package br.com.centralit.citcorpore.rh.bean;


public class TriagemRequisicaoPessoalDTO extends CurriculoDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer idTriagem;
	private Integer idSolicitacaoServico;
	private Integer idItemTrabalhoEntrevistaRH;
	private Integer idItemTrabalhoEntrevistaGestor;
	private String tipoEntrevista;
	private String existeEntrevistaRH;
	private Integer idSolicitacaoServicoCurriculo;
	
	public Integer getIdSolicitacaoServicoCurriculo() {
		return idSolicitacaoServicoCurriculo;
	}
	public void setIdSolicitacaoServicoCurriculo(
			Integer idSolicitacaoServicoCurriculo) {
		this.idSolicitacaoServicoCurriculo = idSolicitacaoServicoCurriculo;
	}
	public Integer getIdTriagem(){
		return this.idTriagem;
	}
	public void setIdTriagem(Integer parm){
		this.idTriagem = parm;
	}

	public Integer getIdSolicitacaoServico(){
		return this.idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer parm){
		this.idSolicitacaoServico = parm;
	}
	public Integer getIdItemTrabalhoEntrevistaRH() {
		return idItemTrabalhoEntrevistaRH;
	}
	public void setIdItemTrabalhoEntrevistaRH(Integer idItemTrabalhoEntrevistaRH) {
		this.idItemTrabalhoEntrevistaRH = idItemTrabalhoEntrevistaRH;
	}
	public Integer getIdItemTrabalhoEntrevistaGestor() {
		return idItemTrabalhoEntrevistaGestor;
	}
	public void setIdItemTrabalhoEntrevistaGestor(
			Integer idItemTrabalhoEntrevistaGestor) {
		this.idItemTrabalhoEntrevistaGestor = idItemTrabalhoEntrevistaGestor;
	}
	public String getTipoEntrevista() {
		return tipoEntrevista;
	}
	public void setTipoEntrevista(String tipoEntrevista) {
		this.tipoEntrevista = tipoEntrevista;
	}
	public String getExisteEntrevistaRH() {
		return existeEntrevistaRH;
	}
	public void setExisteEntrevistaRH(String existeEntrevistaRH) {
		this.existeEntrevistaRH = existeEntrevistaRH;
	}


}
