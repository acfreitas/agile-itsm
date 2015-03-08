package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.rh.integracao.DepartamentoDao;
import br.com.citframework.service.CrudServiceImpl;

public class DepartamentoServiceEjb extends CrudServiceImpl implements DepartamentoService {

    private DepartamentoDao dao;

    @Override
    protected DepartamentoDao getDao() {
        if (dao == null) {
            dao = new DepartamentoDao();
        }
        return dao;
    }

}
