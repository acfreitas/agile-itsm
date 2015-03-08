package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public interface ItemHistoricoFuncionalService extends CrudService {

    Collection findByIdItemHistorico(final Integer idHistorico) throws Exception;

    void inserirRegistroHistoricoAutomatico(final Integer idCandidato, final Integer idResponsavel, final String titulo, final String descricao, final TransactionControler tc)
            throws Exception;

    void inserirRegistroHistoricoAutomaticoClassificacao(final Integer idCurriculo, final Integer idResponsavel, final Integer idSolicitacao, final String classificacao)
            throws Exception;

}
