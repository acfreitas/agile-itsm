package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.citframework.service.CrudService;

/**
 * @author rosana.godinho
 *
 */
public interface EmpresaService extends CrudService {
	boolean jaExisteRegistroComMesmoNome(EmpresaDTO empresa);
}
