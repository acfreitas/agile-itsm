package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ValidacaoRequisicaoProblemaDTO;

/**
 * 
 * @author geber.costa
 *
 */
public interface ValidacaoRequisicaoProblemaService extends ComplemInfProblemaServicoService {
	
	public ValidacaoRequisicaoProblemaDTO recuperaTemplateRequisicaoProblema(ProblemaDTO problemaDto) throws Exception;
	
	/**
	 * retorna as informações de validação requisição problema de acordo com o problema passado.
	 * @param parm
	 * @return ValidacaoRequisicaoProblemaDTO
	 * @throws Exception
	 * @author thays.araujo
	 */
	public ValidacaoRequisicaoProblemaDTO findByIdProblema(Integer parm) throws Exception;
	
	
	//public void deleteByIdSolicitacaoServico(Integer parm) throws Exception;
}
