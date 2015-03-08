package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ContratoBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class ContratoBIServiceEjb extends CrudServiceImpl implements ContratoBIService {

    private ContratoBIDao dao;

    @Override
    protected ContratoBIDao getDao() {
        if (dao == null) {
            dao = new ContratoBIDao();
        }
        return dao;
    }

}
