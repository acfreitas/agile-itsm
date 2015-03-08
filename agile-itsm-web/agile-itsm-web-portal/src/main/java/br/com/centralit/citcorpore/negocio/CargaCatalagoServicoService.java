package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaCatalagoServicoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CargaCatalagoServicoService extends CrudService {

    List<CargaCatalagoServicoDTO> gerarCarga(final String[] carga) throws ServiceException, Exception;

    List<CargaCatalagoServicoDTO> gerarCarga(final File carga, final Integer idempresa) throws ServiceException, Exception;

}
