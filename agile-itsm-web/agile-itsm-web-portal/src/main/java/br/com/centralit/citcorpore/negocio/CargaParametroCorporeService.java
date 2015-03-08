package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaParametroCorporeDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CargaParametroCorporeService extends CrudService {
	
	public List<CargaParametroCorporeDTO> gerarCarga(File carga ,Integer idEmpresa)throws ServiceException, Exception;

}
