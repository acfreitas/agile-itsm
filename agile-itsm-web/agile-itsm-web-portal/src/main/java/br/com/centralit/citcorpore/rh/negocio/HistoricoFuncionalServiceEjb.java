package br.com.centralit.citcorpore.rh.negocio;

import java.sql.Date;

import br.com.centralit.citcorpore.rh.integracao.HistoricoFuncionalDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
public class HistoricoFuncionalServiceEjb extends CrudServiceImpl implements HistoricoFuncionalService {

    private HistoricoFuncionalDao dao;

    @Override
    protected HistoricoFuncionalDao getDao() {
        if (dao == null) {
            dao = new HistoricoFuncionalDao();
        }
        return dao;
    }

    @Override
    public Date getUltimaAtualizacao(final Integer idCurriculo) throws Exception {
        return this.getDao().getUltimaAtualizacao(idCurriculo);
    }

}
