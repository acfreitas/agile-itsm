package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface AtitudeIndividualService extends CrudService {

    Collection findByNome(final String nome) throws Exception;

    Collection findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception;

}
