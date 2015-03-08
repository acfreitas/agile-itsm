package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author euler.ramos
 * Guarda as informações para a geração do gráfico de Gantt
 */
public class TendenciaGanttDTO implements IDto{

	private static final long serialVersionUID = -4822323995573414L;
	
	private Date data;
	private Integer qtde;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

}