package br.com.centralit.bpm.servico;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface TipoFluxoService extends CrudService {

    Collection findByIdProcessoNegocio(final Integer idProcessoNegocio) throws Exception;

}
