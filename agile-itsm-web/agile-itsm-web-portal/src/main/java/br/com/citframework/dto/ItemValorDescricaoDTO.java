package br.com.citframework.dto;

public class ItemValorDescricaoDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6041027505586125015L;
	private String valor;
	private String descricao;
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
