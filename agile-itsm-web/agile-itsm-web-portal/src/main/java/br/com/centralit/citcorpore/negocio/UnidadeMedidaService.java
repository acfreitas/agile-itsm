package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.citframework.service.CrudService;

public interface UnidadeMedidaService extends CrudService {

	public boolean consultarUnidadesMedidas(UnidadeMedidaDTO unidadeMedidaDTO) throws Exception;
}
