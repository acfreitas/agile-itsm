package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ServicoContratoBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class ServicoContratoBIServiceEjb extends CrudServiceImpl implements ServicoContratoBIService {

    private ServicoContratoBIDao dao;

    @Override
    protected ServicoContratoBIDao getDao() {
        if (dao == null) {
            dao = new ServicoContratoBIDao();
        }
        return dao;
    }

}
