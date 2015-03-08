package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Pedro
 *
 */
public class ControleContratoModuloSistemaDTO implements IDto {

    private Integer idModuloSistema;
    private Integer idControleContrato;
    
	public Integer getIdModuloSistema() {
		return idModuloSistema;
	}
	public void setIdModuloSistema(Integer idModuloSistema) {
		this.idModuloSistema = idModuloSistema;
	}
	public Integer getIdControleContrato() {
		return idControleContrato;
	}
	public void setIdControleContrato(Integer idControleContrato) {
		this.idControleContrato = idControleContrato;
	}
    
    

   

}