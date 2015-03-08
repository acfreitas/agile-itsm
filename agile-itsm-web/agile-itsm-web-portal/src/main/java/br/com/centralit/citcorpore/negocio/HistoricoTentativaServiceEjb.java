package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.HistoricoTentativaDao;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoTentativaServiceEjb extends CrudServiceImpl implements HistoricoTentativaService {

    private HistoricoTentativaDao dao;

    @Override
    protected HistoricoTentativaDao getDao() {
        if (dao == null) {
            dao = new HistoricoTentativaDao();
        }
        return dao;
    }

}
