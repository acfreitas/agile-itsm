package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface RequisicaoCertificacaoService extends CrudService {

    Collection findByIdSolicitacaoServico(final Integer parm) throws Exception;

    void deleteByIdSolicitacaoServico(final Integer parm) throws Exception;

}
