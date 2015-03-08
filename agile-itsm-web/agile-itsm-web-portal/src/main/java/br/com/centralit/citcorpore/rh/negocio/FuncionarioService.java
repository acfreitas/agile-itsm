package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public interface FuncionarioService extends CrudService {

    Collection findByNome(final String nome) throws Exception;

}
