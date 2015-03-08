package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.FaturaBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class FaturaBIServiceEjb extends CrudServiceImpl implements FaturaBIService {

    private FaturaBIDao dao;

    @Override
    protected FaturaBIDao getDao() {
        if (dao == null) {
            dao = new FaturaBIDao();
        }
        return dao;
    }

}
