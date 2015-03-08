package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface DescricaoAtribuicaoResponsabilidadeService extends CrudService {

    Collection listAtivos() throws Exception;

    Collection findByNome(final String nome) throws Exception;

}
