/**
 * Central IT - CITSmart
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * DTO de PerfilAcessoUsuario.
 * 
 * @author valdoilo.damasceno
 */
public class PerfilAcessoUsuarioDTO implements IDto {

    private static final long serialVersionUID = 4622694451305944947L;

    private Integer idPerfilAcesso;

    private Integer idUsuario;

    private Date dataInicio;

    private Date dataFim;

    /**
     * @return valor do atributo idPerfilAcesso.
     */
    public Integer getIdPerfilAcesso() {
	return idPerfilAcesso;
    }

    /**
     * Define valor do atributo idPerfilAcesso.
     * 
     * @param idPerfilAcesso
     */
    public void setIdPerfilAcesso(Integer idPerfilAcesso) {
	this.idPerfilAcesso = idPerfilAcesso;
    }

    /**
     * @return valor do atributo idUsuario.
     */
    public Integer getIdUsuario() {
	return idUsuario;
    }

    /**
     * Define valor do atributo idUsuario.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(Integer idUsuario) {
	this.idUsuario = idUsuario;
    }

    /**
     * @return valor do atributo dataInicio.
     */
    public Date getDataInicio() {
	return dataInicio;
    }

    /**
     * Define valor do atributo dataInicio.
     * 
     * @param dataInicio
     */
    public void setDataInicio(Date dataInicio) {
	this.dataInicio = dataInicio;
    }

    /**
     * @return valor do atributo dataFim.
     */
    public Date getDataFim() {
	return dataFim;
    }

    /**
     * Define valor do atributo dataFim.
     * 
     * @param dataFim
     */
    public void setDataFim(Date dataFim) {
	this.dataFim = dataFim;
    }

}
