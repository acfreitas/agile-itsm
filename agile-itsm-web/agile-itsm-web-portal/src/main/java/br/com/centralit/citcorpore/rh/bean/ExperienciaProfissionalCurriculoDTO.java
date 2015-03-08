package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;


public class ExperienciaProfissionalCurriculoDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idExperienciaProfissional;
	private Collection<FuncaoExperienciaProfissionalCurriculoDTO> colFuncao;
	private String colFuncaoSerialize;
	
	private Integer idCurriculo;
	private String descricaoEmpresa;
	private String localidade;
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	
	public Integer getIdExperienciaProfissional() {
		return idExperienciaProfissional;
	}
	public void setIdExperienciaProfissional(Integer idExperienciaProfissional) {
		this.idExperienciaProfissional = idExperienciaProfissional;
	}
	
	public String getDescricaoEmpresa() {
		return descricaoEmpresa;
	}
	public void setDescricaoEmpresa(String descricaoEmpresa) {
		this.descricaoEmpresa = descricaoEmpresa;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public Collection<FuncaoExperienciaProfissionalCurriculoDTO> getColFuncao() {
		return colFuncao;
	}
	public void setColFuncao(
			Collection<FuncaoExperienciaProfissionalCurriculoDTO> colFuncao) {
		this.colFuncao = colFuncao;
	}
	public String getColFuncaoSerialize() {
		return colFuncaoSerialize;
	}
	public void setColFuncaoSerialize(String colFuncaoSerialize) {
		this.colFuncaoSerialize = colFuncaoSerialize;
	}
	public Date getDataInicial() {
		if(colFuncao != null && !colFuncao.isEmpty()) {
			return colFuncao.iterator().next().getInicioFuncao();
		}
		
		return null;
	}
	public String getPeriodo() {
		Date periodoInicial, periodoFinal;
		if(colFuncao != null && !colFuncao.isEmpty()) {
			// Periodo inicial
			periodoInicial = colFuncao.iterator().next().getInicioFuncao();
			for(FuncaoExperienciaProfissionalCurriculoDTO funcao : colFuncao) {
				if(periodoInicial.after(funcao.getInicioFuncao())) {
					periodoInicial = funcao.getInicioFuncao();
				}
			}
			
			// Periodo final
			periodoFinal = colFuncao.iterator().next().getFimFuncao();
			for(FuncaoExperienciaProfissionalCurriculoDTO funcao : colFuncao) {
				if(funcao.getFimFuncao() == null || (funcao.getFimFuncao()).equals("")) {
					periodoFinal = null;
					break;
				} else {
					periodoFinal = UtilDatas.getDataMaior(periodoFinal, funcao.getFimFuncao());
				}
			}
			
			String periodo = UtilDatas.dateToSTR(periodoInicial, "MM/yyyy") + " - " + (periodoFinal == null ? UtilI18N.internacionaliza(UtilI18N.getLocale(), "rh.atualmente") : UtilDatas.dateToSTR(periodoFinal, "MM/yyyy"));
			
			return periodo;
		}
		
		return "";
	}
}