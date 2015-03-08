package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.centralit.citcorpore.integracao.EventoItemConfigRelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class EventoItemConfigRelServiceEjb extends CrudServiceImpl implements EventoItemConfigRelService {

    private EventoItemConfigRelDao dao;

    @Override
    protected EventoItemConfigRelDao getDao() {
        if (dao == null) {
            dao = new EventoItemConfigRelDao();
        }
        return dao;
    }

    @Override
    public Collection<EventoItemConfigRelDTO> listByEvento(final Integer idEvento) throws Exception {
        try {
            return this.getDao().listByEvento(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
