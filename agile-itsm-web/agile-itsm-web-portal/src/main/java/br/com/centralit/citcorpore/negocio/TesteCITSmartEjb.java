package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.TesteCITSmartDao;
import br.com.citframework.service.CrudServiceImpl;

public class TesteCITSmartEjb extends CrudServiceImpl implements TesteCITSMartService {

    private TesteCITSmartDao dao;

    @Override
    protected TesteCITSmartDao getDao() {
        if (dao == null) {
            dao = new TesteCITSmartDao();
        }
        return dao;
    }

}
