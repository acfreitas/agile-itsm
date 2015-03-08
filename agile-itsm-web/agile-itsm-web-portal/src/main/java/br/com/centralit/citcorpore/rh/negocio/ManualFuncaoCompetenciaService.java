package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ManualFuncaoCompetenciaService extends CrudService {

    Collection listAtivos() throws Exception;

}
