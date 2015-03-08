package br.com.centralit.citcorpore.bean;

import java.math.BigDecimal;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class ComplexidadeDTO implements IDto {

	private static final long serialVersionUID = -4685023069025051625L;
	
	private Integer idContrato;
	
	private String complexidade;
	
	private BigDecimal valorComplexidade;
	
	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(String complexidade) {
		this.complexidade = complexidade;
	}

	public BigDecimal getValorComplexidade() {
		return valorComplexidade;
	}

	public void setValorComplexidade(BigDecimal valorComplexidade) {
		this.valorComplexidade = valorComplexidade;
	}
	
}
