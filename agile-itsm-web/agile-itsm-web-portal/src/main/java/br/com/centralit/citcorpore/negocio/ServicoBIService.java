package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ServicoBIDTO;
import br.com.citframework.service.CrudService;

public interface ServicoBIService extends CrudService {
	Collection<ServicoBIDTO> findByNomeEconexaoBI(String consulta, Integer idConexaoBI);
	ServicoBIDTO findByIdEconexaoBI(Integer idServicoDe, Integer idConexaoBI);
}
