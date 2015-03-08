package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @author breno.guimaraes
 *
 */
public interface OcorrenciaMudancaService extends CrudService {
    public Collection findByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws Exception;
}
