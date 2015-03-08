package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface AtitudeCandidatoService extends CrudService {

    Collection findByIdEntrevista(final Integer parm) throws Exception;

    void deleteByIdEntrevista(final Integer parm) throws Exception;

}
