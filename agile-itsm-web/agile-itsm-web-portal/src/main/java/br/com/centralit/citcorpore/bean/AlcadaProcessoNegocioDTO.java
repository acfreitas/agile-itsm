package br.com.centralit.citcorpore.bean;

import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.util.Enumerados.MotivoRejeicaoAlcada;
import br.com.citframework.dto.IDto;

public class AlcadaProcessoNegocioDTO implements IDto {
	private CentroResultadoDTO centroResultadoDto;
	private EmpregadoDTO empregadoDto;
	private List<ProcessoNegocioDTO> processosNegocio;
	private HashMap<String, GrupoEmpregadoDTO> mapGruposEmpregado;
	
	private boolean alcadaRejeitada;
	private boolean delegacao;
	private AlcadaProcessoNegocioDTO alcadaOrigemDto;
	private MotivoRejeicaoAlcada motivoRejeicao;
	private String complementoRejeicao;
	
	private String chaveOrdenacao;
	
	public HashMap<String, GrupoEmpregadoDTO> getMapGruposEmpregado() {
		return mapGruposEmpregado;
	}
	public void setMapGruposEmpregado(
			HashMap<String, GrupoEmpregadoDTO> mapGruposEmpregado) {
		this.mapGruposEmpregado = mapGruposEmpregado;
	}
	public CentroResultadoDTO getCentroResultadoDto() {
		return centroResultadoDto;
	}
	public void setCentroResultadoDto(CentroResultadoDTO centroResultadoDto) {
		this.centroResultadoDto = centroResultadoDto;
	}
	public EmpregadoDTO getEmpregadoDto() {
		return empregadoDto;
	}
	public void setEmpregadoDto(EmpregadoDTO empregadoDto) {
		this.empregadoDto = empregadoDto;
	}
	public List<ProcessoNegocioDTO> getProcessosNegocio() {
		return processosNegocio;
	}
	public void setProcessosNegocio(List<ProcessoNegocioDTO> processosNegocio) {
		this.processosNegocio = processosNegocio;
	}
	public MotivoRejeicaoAlcada getMotivoRejeicao() {
		return motivoRejeicao;
	}
	public void setMotivoRejeicao(MotivoRejeicaoAlcada motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}
	public boolean isDelegacao() {
		return delegacao;
	}
	public void setDelegacao(boolean delegacao) {
		this.delegacao = delegacao;
	}
	public AlcadaProcessoNegocioDTO getAlcadaOrigemDto() {
		return alcadaOrigemDto;
	}
	public void setAlcadaOrigemDto(AlcadaProcessoNegocioDTO alcadaOrigemDto) {
		this.alcadaOrigemDto = alcadaOrigemDto;
	}
	public boolean isAlcadaRejeitada() {
		return alcadaRejeitada;
	}
	public void setAlcadaRejeitada(boolean alcadaRejeitada) {
		this.alcadaRejeitada = alcadaRejeitada;
	}
	public String getComplementoRejeicao() {
		return complementoRejeicao;
	}
	public void setComplementoRejeicao(String complementoRejeicao) {
		this.complementoRejeicao = complementoRejeicao;
	}
	public String getChaveOrdenacao() {
		return chaveOrdenacao;
	}
	public void setChaveOrdenacao(String chaveOrdenacao) {
		this.chaveOrdenacao = chaveOrdenacao;
	}
	
	public int recuperaHierarquiaNivelAutoridade() {
		int result = 0;
		if (this.getProcessosNegocio() != null) {
			if (this.getProcessosNegocio().get(0).getNivelAutoridadeDto() != null)
				result = this.getProcessosNegocio().get(0).getNivelAutoridadeDto().getHierarquia();
		}
		return result;
	}
}
