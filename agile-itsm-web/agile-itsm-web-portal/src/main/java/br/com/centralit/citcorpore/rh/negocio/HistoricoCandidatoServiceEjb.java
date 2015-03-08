package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.HistoricoCandidatoDao;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoCandidatoServiceEjb extends CrudServiceImpl implements HistoricoCandidatoService {

    private HistoricoCandidatoDao dao;

    @Override
    protected HistoricoCandidatoDao getDao() {
        if (dao == null) {
            dao = new HistoricoCandidatoDao();
        }
        return dao;
    }

}
