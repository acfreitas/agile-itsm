package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.HistoricoRespCentroResultadoDao;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoRespCentroResultadoServiceEjb extends CrudServiceImpl implements HistoricoRespCentroResultadoService {

    private HistoricoRespCentroResultadoDao dao;

    @Override
    protected HistoricoRespCentroResultadoDao getDao() {
        if (dao == null) {
            dao = new HistoricoRespCentroResultadoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCentroResultado(final Integer parm) throws Exception {
        return this.getDao().findByIdCentroResultado(parm);
    }

}
