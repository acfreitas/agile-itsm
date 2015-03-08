package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface GrupoRequisicaoMudancaService extends CrudService {

    void deleteByIdRequisicaoMudanca(final Integer parm) throws Exception;

    Collection listByIdHistoricoMudanca(final Integer idHistoricoMudanca) throws Exception;

    Collection findByIdMudancaEDataFim(final Integer idRequisicaoMudanca) throws Exception;

    Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception;

    void deleteByIdGrupo(final Integer parm) throws Exception;

    Collection findByIdGrupo(final Integer parm) throws Exception;

    void deleteByIdGrupoRequisicaoMudanca(final Integer parm) throws Exception;

    Collection findByIdGrupoRequisicaoMudanca(final Integer parm) throws Exception;

}
