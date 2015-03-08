/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaSmartDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author Vadoilo Damasceno
 * 
 */
public interface CargaSmartService extends CrudService {

	List<CargaSmartDTO> gerarCarga(File arquivo, Integer idEmpresa) throws ServiceException, Exception;
	

}
