package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface CursoService extends CrudService {

    Collection findByNome(final String nome) throws Exception;

    Collection findByNotIdFuncao(final Integer idFuncao) throws Exception;

}
