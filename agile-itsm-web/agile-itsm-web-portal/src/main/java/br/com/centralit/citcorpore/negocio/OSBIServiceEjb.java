package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.OSBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class OSBIServiceEjb extends CrudServiceImpl implements OSBIService {

    private OSBIDao dao;

    @Override
    protected OSBIDao getDao() {
        if (dao == null) {
            dao = new OSBIDao();
        }
        return dao;
    }

}
