package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ServicoCorporeBIDTO;
import br.com.citframework.service.CrudService;

public interface ServicoCorporeBIService extends CrudService {

	Collection<ServicoCorporeBIDTO> findByNome(String consulta);

	ServicoCorporeBIDTO findById(Integer idServicoDe);

}
