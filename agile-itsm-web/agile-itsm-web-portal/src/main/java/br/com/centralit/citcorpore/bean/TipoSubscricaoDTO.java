package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Pedro
 *
 */
public class TipoSubscricaoDTO implements IDto {

    private Integer idTipoSubscricao;
    private String nomeTipoSubscricao;
    
	public Integer getIdTipoSubscricao() {
		return idTipoSubscricao;
	}
	public void setIdTipoSubscricao(Integer idTipoSubscricao) {
		this.idTipoSubscricao = idTipoSubscricao;
	}
	public String getNomeTipoSubscricao() {
		return nomeTipoSubscricao;
	}
	public void setNomeTipoSubscricao(String nomeTipoSubscricao) {
		this.nomeTipoSubscricao = nomeTipoSubscricao;
	}




}