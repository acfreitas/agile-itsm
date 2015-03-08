package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface ExperienciaInformaticaService extends CrudService {

    Collection findByNotIdFuncao(final Integer idFuncao) throws Exception;

}
