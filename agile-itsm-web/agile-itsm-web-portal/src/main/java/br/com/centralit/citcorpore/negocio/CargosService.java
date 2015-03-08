package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CargosService extends CrudService {

	/**
	 * Exclui cargo caso o mesmo não possua empregado associado.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void deletarCargo(IDto model, DocumentHTML document) throws ServiceException, Exception;

	/**
	 * Consultar Cargos Ativos
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public boolean consultarCargosAtivos(CargosDTO obj) throws Exception;
	public Collection<CargosDTO> seCargoJaCadastrado(CargosDTO cargosDTO) throws Exception;
	public Collection<CargosDTO> listarAtivos() throws Exception;
	public Collection findByNome(String nome) throws Exception;

}
