package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.HistoricoGEDDao;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoGEDServiceEjb extends CrudServiceImpl implements HistoricoGEDService {

    private HistoricoGEDDao dao;

    @Override
    protected HistoricoGEDDao getDao() {
        if (dao == null) {
            dao = new HistoricoGEDDao();
        }
        return dao;
    }

}
