package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface HistoricoRespCentroResultadoService extends CrudService {
	public Collection findByIdCentroResultado(Integer parm) throws Exception;
}
