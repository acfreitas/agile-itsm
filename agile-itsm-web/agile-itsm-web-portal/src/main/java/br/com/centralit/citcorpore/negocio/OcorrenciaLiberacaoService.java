package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @author breno.guimaraes
 *
 */
public interface OcorrenciaLiberacaoService extends CrudService {
    public Collection findByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws Exception;
}
