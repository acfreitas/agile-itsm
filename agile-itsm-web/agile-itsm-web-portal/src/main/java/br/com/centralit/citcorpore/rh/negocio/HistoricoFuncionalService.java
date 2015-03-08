package br.com.centralit.citcorpore.rh.negocio;

import java.sql.Date;

import br.com.citframework.service.CrudService;

/**
 * @author david.silva
 *
 */
public interface HistoricoFuncionalService extends CrudService {

    Date getUltimaAtualizacao(final Integer idCurriculo) throws Exception;

}
