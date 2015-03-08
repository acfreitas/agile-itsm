package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilI18N;


public class CompetenciaCurriculoDTO implements IDto {
	private Integer idCompetencia;
	private String descricaoCompetencia;
	private Integer nivelCompetencia;
	private String nivelCompetenciaDesc;
	private Integer idCurriculo;
	
	private String imagemEmailprincipal;
	
	
	public String getImagemEmailprincipal() {
		return imagemEmailprincipal;
	}
	public void setImagemEmailprincipal(String imagemEmailprincipal) {
		this.imagemEmailprincipal = imagemEmailprincipal;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(Integer idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getDescricaoCompetencia() {
		return descricaoCompetencia;
	}
	public void setDescricaoCompetencia(String descricaoCompetencia) {
		this.descricaoCompetencia = descricaoCompetencia;
	}
	public Integer getNivelCompetencia() {
		return nivelCompetencia;
	}
	public void setNivelCompetencia(Integer nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
		if(this.nivelCompetencia != null) {
			switch(this.nivelCompetencia) {
				case 1:
					this.nivelCompetenciaDesc = UtilI18N.internacionaliza(UtilI18N.getLocale(), "curriculo.idiomaBasico");
				break;
				case 2:
					this.nivelCompetenciaDesc = UtilI18N.internacionaliza(UtilI18N.getLocale(), "curriculo.idiomaIntermediario");
				break;
				case 3:
					this.nivelCompetenciaDesc = UtilI18N.internacionaliza(UtilI18N.getLocale(), "curriculo.idiomaAvancado");
				break;
			}
		} else {
			this.nivelCompetenciaDesc = UtilI18N.internacionaliza(UtilI18N.getLocale(), "rh.naoTem");
		}
	}
	public String getNivelCompetenciaDesc() {
		return nivelCompetenciaDesc;
	}
	public void setNivelCompetenciaDesc(String nivelCompetenciaDesc) {
		this.nivelCompetenciaDesc = nivelCompetenciaDesc;
	}
	
}