package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspecitivaComplexidadeManualFuncaoDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidadeDto;

	public AtribuicaoResponsabilidadeDTO getAtribuicaoResponsabilidadeDto() {
		return atribuicaoResponsabilidadeDto;
	}

	public void setAtribuicaoResponsabilidadeDto(
			AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidadeDto) {
		this.atribuicaoResponsabilidadeDto = atribuicaoResponsabilidadeDto;
	}
	
	
	
}
