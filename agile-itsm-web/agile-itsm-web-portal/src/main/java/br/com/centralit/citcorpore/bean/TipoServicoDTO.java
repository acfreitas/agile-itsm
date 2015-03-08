package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author leandro.viana
 * 
 */
public class TipoServicoDTO implements IDto {

    private static final long serialVersionUID = -7848776827100833523L;
    
    //Valores devem refletir obrigatoriamente os IDs da tabela TIPOSERVICO
    public static Integer A_DEFINIR = 1;
	public static Integer ROTINEIRA = 2;
	public static Integer SUPORTE = 3;
	public static Integer DEMANDA = 4;

    private Integer idTipoServico;
    private Integer idEmpresa;
    private String nomeTipoServico;
    private String situacao;

    public static long getSerialversionuid() {
    	return serialVersionUID;
    }

    /**
     * @return valor do atributo idTipoServico.
     */
    public Integer getIdTipoServico() {
    	return idTipoServico;
    }

    /**
     * Define valor do atributo idTipoServico.
     * 
     * @param idTipoServico
     */
    public void setIdTipoServico(Integer idTipoServico) {
    	this.idTipoServico = idTipoServico;
    }

    /**
     * @return valor do atributo idEmpresa.
     */
    public Integer getIdEmpresa() {
    	return idEmpresa;
    }

    /**
     * Define valor do atributo idEmpresa.
     * 
     * @param idEmpresa
     */
    public void setIdEmpresa(Integer idEmpresa) {
    	this.idEmpresa = idEmpresa;
    }

    /**
     * @return valor do atributo nomeTipoServico.
     */
    public String getNomeTipoServico() {
    	return nomeTipoServico;
    }

    /**
     * Define valor do atributo nomeTipoServico.
     * 
     * @param nomeTipoServico
     */
    public void setNomeTipoServico(String nomeTipoServico) {
    	this.nomeTipoServico = nomeTipoServico;
    }

    /**
     * @return valor do atributo situacao.
     */
    public String getSituacao() {
    	return situacao;
    }

    /**
     * Define valor do atributo situacao.
     * 
     * @param situacao
     */
    public void setSituacao(String situacao) {
    	this.situacao = situacao;
    }
    
    public static String getNomeSituacao(Integer idTipoSituacao){
    	String resp = "";
    	
    	switch (idTipoSituacao) {
			case 1:
				resp = "A DEFINIR";
				break;
			case 2:
				resp = "ROTINEIRA";
				break;
			case 3:
				resp = "SUPORTE";
				break;
			case 4:
				resp = "DEMANDA";
				break;
			default:
				break;
		}
    	
    	return resp;
    	
    }

}
