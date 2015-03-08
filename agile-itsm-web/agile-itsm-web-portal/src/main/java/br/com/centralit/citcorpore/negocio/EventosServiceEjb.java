package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.EventosDao;
import br.com.citframework.service.CrudServiceImpl;

public class EventosServiceEjb extends CrudServiceImpl implements EventosService {

    private EventosDao dao;

    @Override
    protected EventosDao getDao() {
        if (dao == null) {
            dao = new EventosDao();
        }
        return dao;
    }

}
