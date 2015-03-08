package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Pedro
 *
 */
public class ModuloSistemaDTO implements IDto {

    private Integer idModuloSistema;
    private String nomeModuloSistema;
    
	public Integer getIdModuloSistema() {
		return idModuloSistema;
	}
	public void setIdModuloSistema(Integer idModuloSistema) {
		this.idModuloSistema = idModuloSistema;
	}
	public String getNomeModuloSistema() {
		return nomeModuloSistema;
	}
	public void setNomeModuloSistema(String nomeModuloSistema) {
		this.nomeModuloSistema = nomeModuloSistema;
	}

   

}