package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ServContratoCatalogoServDao;
import br.com.citframework.service.CrudServiceImpl;

public class ServContratoCatalogoServServiceEjb extends CrudServiceImpl implements ServContratoCatalogoServService {

    private ServContratoCatalogoServDao dao;

    @Override
    protected ServContratoCatalogoServDao getDao() {
        if (dao == null) {
            dao = new ServContratoCatalogoServDao();
        }
        return dao;
    }

}
