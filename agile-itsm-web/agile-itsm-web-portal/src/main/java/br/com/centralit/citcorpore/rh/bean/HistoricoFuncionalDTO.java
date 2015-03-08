package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author david.silva
 *
 */
public class HistoricoFuncionalDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idHistoricoFuncional;
	private Integer idCandidato;
	private Integer idCurriculo;
	private Date dtCriacao;
	private String detalhamentoTabela01;
	private String detalhamentoTabela02;
	private String detalhamentoTabela03;
	private String nome;
	private String cpf;
	private String btnPaginacao;
	private String tipo;
	private String chkBlackList;
	private Integer PaginaSelecionada;
	
	public String getChkBlackList() {
		return chkBlackList;
	}
	public void setChkBlackList(String chkBlackList) {
		this.chkBlackList = chkBlackList;
	}
	private Collection<ItemHistoricoFuncionalDTO> listaItemHistoricoFuncional;
	
	public Integer getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	public String getDetalhamentoTabela01() {
		return detalhamentoTabela01;
	}
	public void setDetalhamentoTabela01(String detalhamentoTabela01) {
		this.detalhamentoTabela01 = detalhamentoTabela01;
	}
	public String getDetalhamentoTabela02() {
		return detalhamentoTabela02;
	}
	public void setDetalhamentoTabela02(String detalhamentoTabela02) {
		this.detalhamentoTabela02 = detalhamentoTabela02;
	}
	public String getDetalhamentoTabela03() {
		return detalhamentoTabela03;
	}
	public void setDetalhamentoTabela03(String detalhamentoTabela03) {
		this.detalhamentoTabela03 = detalhamentoTabela03;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Integer getIdHistoricoFuncional() {
		return idHistoricoFuncional;
	}
	public void setIdHistoricoFuncional(Integer idHistoricoFuncional) {
		this.idHistoricoFuncional = idHistoricoFuncional;
	}
	public Collection<ItemHistoricoFuncionalDTO> getListaItemHistoricoFuncional() {
		return listaItemHistoricoFuncional;
	}
	public void setListaItemHistoricoFuncional(
			Collection<ItemHistoricoFuncionalDTO> listaItemHistoricoFuncional) {
		this.listaItemHistoricoFuncional = listaItemHistoricoFuncional;
	}
	public Integer getPaginaSelecionada() {
		return PaginaSelecionada;
	}
	public void setPaginaSelecionada(Integer paginaSelecionada) {
		PaginaSelecionada = paginaSelecionada;
	}
	public String getBtnPaginacao() {
		return btnPaginacao;
	}
	public void setBtnPaginacao(String btnPaginacao) {
		this.btnPaginacao = btnPaginacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
