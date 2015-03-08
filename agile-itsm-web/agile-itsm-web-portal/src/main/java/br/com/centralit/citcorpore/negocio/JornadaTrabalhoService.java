package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface JornadaTrabalhoService extends CrudService {

	/**
	 * Exclui jornada caso a mesma não esteja sendo utilizada no calendário.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	public String deletarJornada(IDto model) throws ServiceException, Exception;

	/**
	 * Consultar jornadas ativas
	 * 
	 * @return retorna a lista de jornadas que estão ativas
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	
	public Collection listarJornadasAtivas() throws Exception;
	
	/**
	 * Verifica jornada existente
	 * 
	 * @param JornadaTrabalhoDTO
	 * @return retorna true se caso exista jornada já cadastrada
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public boolean verificaJornadaExistente(JornadaTrabalhoDTO jornadaTrabalho) throws Exception;
		
}
