package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ClienteBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class ClienteBIServiceEjb extends CrudServiceImpl implements ClienteBIService {

    private ClienteBIDao dao;

    @Override
    protected ClienteBIDao getDao() {
        if (dao == null) {
            dao = new ClienteBIDao();
        }
        return dao;
    }

}
