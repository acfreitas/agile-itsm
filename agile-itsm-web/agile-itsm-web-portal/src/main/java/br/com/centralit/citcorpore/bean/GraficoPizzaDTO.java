/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author breno.guimaraes
 * 
 */
public class GraficoPizzaDTO implements IDto {

    /**
     * 
     */
    private static final long serialVersionUID = 6988415536736065332L;
    private String campo;
    private Double valor;

    public String getCampo() {
	return campo;
    }

    public void setCampo(String campo) {
	this.campo = campo;
    }

    public Double getValor() {
	return valor;
    }

    public void setValor(Double valor) {
	this.valor = valor;
    }

}
