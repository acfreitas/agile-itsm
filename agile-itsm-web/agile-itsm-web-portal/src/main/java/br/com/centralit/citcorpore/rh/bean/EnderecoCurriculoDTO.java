package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilStrings;

public class EnderecoCurriculoDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEndereco;
	private String logradouro;
	private String cep;
	private String complemento;
	private Integer idTipoEndereco;
	private String nomeCidade;
	private String nomeBairro;
	private Integer idUf;
	private String nomeUF;
	private Integer enderecoIdUF;
	private Integer numero;
	private String nomePais;
	private String descricaoEndereco;

	private String descricaoTipoEndereco;
	private String principal;
	private Integer idCurriculo;
	private String siglaUf;

	private Integer idCidade;

	public String getSiglaUf() {
		return siglaUf;
	}

	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}

	public Integer getIdCurriculo() {
		return idCurriculo;
	}

	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
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

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Integer getIdTipoEndereco() {
		return idTipoEndereco;
	}

	public void setIdTipoEndereco(Integer idTipoEndereco) {
		this.idTipoEndereco = idTipoEndereco;
		if (idTipoEndereco != null) {
			if (idTipoEndereco.intValue() == 1) {
				this.setDescricaoTipoEndereco("Residencial");
			} else {
				this.setDescricaoTipoEndereco("Comercial");
			}
		} else {
			this.setDescricaoTipoEndereco("");
		}
	}

	public Integer getEnderecoIdUF() {
		return enderecoIdUF;
	}

	public void setEnderecoIdUF(Integer enderecoIdUF) {
		this.enderecoIdUF = enderecoIdUF;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getDescricaoTipoEndereco() {
		return descricaoTipoEndereco;
	}

	public void setDescricaoTipoEndereco(String descricaoTipoEndereco) {
		this.descricaoTipoEndereco = descricaoTipoEndereco;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getDetalhamentoEndereco() {
		String strDetalhamento = "<table id='tblIdEndereco_" + this.getIdEndereco() + "' width='100%'><tr><td class='celulaGrid'><b>Endereço: </b>" + UtilStrings.nullToVazio(this.getLogradouro())
				+ ' ' + UtilStrings.nullToVazio(this.getComplemento()) + "</td></tr>";
		strDetalhamento = strDetalhamento + "<tr><td class='celulaGrid'><b>Bairro:</b> " + UtilStrings.nullToVazio(this.getNomeBairro()) + " <b>Cidade:</b> "
				+ UtilStrings.nullToVazio(this.getNomeCidade()) + " - " + UtilStrings.nullToVazio(this.getSiglaUf()) + "</td></tr>";
		strDetalhamento = strDetalhamento + "<tr><td class='celulaGrid'><b>CEP:</b> " + UtilStrings.nullToVazio(this.getCep()) + "</td></tr>";
		strDetalhamento = strDetalhamento + "</table>";

		return strDetalhamento;
	}

	public String getNomeUF() {
		return nomeUF;
	}

	public void setNomeUF(String nomeUF) {
		this.nomeUF = nomeUF;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getDescricaoEndereco() {
		return descricaoEndereco;
	}

	public void setDescricaoEndereco(String descricaoEndereco) {
		this.descricaoEndereco = descricaoEndereco;
	}

	public Integer getIdUf() {
		return idUf;
	}

	public void setIdUf(Integer idUf) {
		this.idUf = idUf;
	}

}