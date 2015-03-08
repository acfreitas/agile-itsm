package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class InformacoesContratoPerfSegDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1037812758934980332L;
	private Integer idInformacoesContratoConfig;
	private Integer idPerfilSeguranca;
	
    public Integer getIdInformacoesContratoConfig() {
        return idInformacoesContratoConfig;
    }
    public void setIdInformacoesContratoConfig(Integer idInformacoesContratoConfig) {
        this.idInformacoesContratoConfig = idInformacoesContratoConfig;
    }
    public Integer getIdPerfilSeguranca() {
        return idPerfilSeguranca;
    }
    public void setIdPerfilSeguranca(Integer idPerfilSeguranca) {
        this.idPerfilSeguranca = idPerfilSeguranca;
    }
}
