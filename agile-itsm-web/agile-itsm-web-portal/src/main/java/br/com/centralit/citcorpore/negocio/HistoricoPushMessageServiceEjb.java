package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.HistoricoPushMessageDAO;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoPushMessageServiceEjb extends CrudServiceImpl implements HistoricoPushMessageService {

    private HistoricoPushMessageDAO dao;

    @Override
    protected HistoricoPushMessageDAO getDao() {
        if (dao == null) {
            dao = new HistoricoPushMessageDAO();
        }
        return dao;
    }

}
