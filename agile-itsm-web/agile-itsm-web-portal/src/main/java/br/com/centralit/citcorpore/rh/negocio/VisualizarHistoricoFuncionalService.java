package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.bean.ItemHistoricoFuncionalDTO;
import br.com.citframework.service.CrudService;

/**
 * @author david.silva
 *
 */
public interface VisualizarHistoricoFuncionalService extends CrudService {

    ItemHistoricoFuncionalDTO restoreUsuario(final Integer id) throws Exception;

}
