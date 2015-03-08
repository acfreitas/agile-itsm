package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.bean.BlackListDTO;
import br.com.citframework.service.CrudService;

/**
 * @author david.silva
 *
 */
public interface BlackListService extends CrudService {

    boolean isCandidatoBlackList(final Integer idCandidato) throws Exception;

    BlackListDTO retornaBlackList(final Integer idCandidato) throws Exception;

    void inserirRegistroHistorico(final Integer idCandidato, final Integer idResponsavel, final boolean listaNegra) throws Exception;

}
