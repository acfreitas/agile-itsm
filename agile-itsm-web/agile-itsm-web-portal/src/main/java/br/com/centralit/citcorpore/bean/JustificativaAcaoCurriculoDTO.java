package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author ygor.magalhaes
 *
 */
public class JustificativaAcaoCurriculoDTO implements IDto {

    private Integer idJustificativaAcaoCurriculo;
    private String nomeJustificativaAcaoCurriculo;
    
	public Integer getIdJustificativaAcaoCurriculo() {
		return idJustificativaAcaoCurriculo;
	}
	public void setIdJustificativaAcaoCurriculo(Integer idJustificativaAcaoCurriculo) {
		this.idJustificativaAcaoCurriculo = idJustificativaAcaoCurriculo;
	}
	public String getNomeJustificativaAcaoCurriculo() {
		return nomeJustificativaAcaoCurriculo;
	}
	public void setNomeJustificativaAcaoCurriculo(
			String nomeJustificativaAcaoCurriculo) {
		this.nomeJustificativaAcaoCurriculo = nomeJustificativaAcaoCurriculo;
	}


}