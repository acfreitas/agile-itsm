package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface AtividadesOSService extends CrudService {

	public Collection findByIdOS(Integer parm) throws Exception;

	public void deleteByIdOS(Integer parm) throws Exception;

	/**
	 * Retorna a soma dos custos de atividades por id da OS.
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public Double retornarCustoAtividadeOSByIdOs(Integer idOs) throws Exception;

	/**
	 * Retorna a soma das glosas de atividades por id da OS.
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public Double retornarGlosaAtividadeOSByIdOs(Integer idOs) throws Exception;

	/**
	 * Retorna a quantidade de execução
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 * @author Thays
	 */
	public Double retornarQtdExecucao(Integer idOs) throws Exception;
	
	public Collection findByIdOsServicoContratoContabil(Integer idOs, Integer ServicoContratoContabil) throws Exception;
	
}
