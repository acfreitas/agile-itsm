package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface CertificacaoService extends CrudService {

    Collection findByNome(final String nome) throws Exception;

    Collection findByNotIdFuncao(final Integer idFuncao) throws Exception;

}
