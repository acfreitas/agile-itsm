package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ManualFuncaoComplexidadeService extends CrudService {

    Collection listAtivos() throws Exception;

}
