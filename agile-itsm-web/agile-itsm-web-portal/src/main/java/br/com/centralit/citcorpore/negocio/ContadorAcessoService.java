package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ContadorAcessoDTO;
import br.com.citframework.service.CrudService;

public interface ContadorAcessoService extends CrudService {
	
	/**
	 * Retorna veradeiro ou falso caso base conhecimento ja foi gravado ha mas de 1 hora 
	 * @param contadorDto
	 * @return
	 * @throws Exception
	 */
	public boolean verificarDataHoraDoContadorDeAcesso(ContadorAcessoDTO contadorDto) throws Exception;
	
	/**
	 * Retorna a quantidade de cliques por baseConhecimento
	 * @param baseConhecimentoDTO
	 * @return
	 * @throws Exception
	 */
	public Integer quantidadesDeAcessoPorBaseConhecimnto(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;

	
	public Integer quantidadesDeAcessoPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
		
}
