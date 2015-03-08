package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface CompetenciaTecnicaService extends CrudService {

    Collection findByNome(final String nome) throws Exception;

}
