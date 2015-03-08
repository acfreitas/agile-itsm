package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.CheckinDeniedDao;
import br.com.citframework.service.CrudServiceImpl;

public class CheckinDeniedServiceEjb extends CrudServiceImpl implements CheckinDeniedService {

    private CheckinDeniedDao checkinDeniedDao;

    @Override
    protected CheckinDeniedDao getDao() {
        if (checkinDeniedDao == null) {
            checkinDeniedDao = new CheckinDeniedDao();
        }
        return checkinDeniedDao;
    }

}
