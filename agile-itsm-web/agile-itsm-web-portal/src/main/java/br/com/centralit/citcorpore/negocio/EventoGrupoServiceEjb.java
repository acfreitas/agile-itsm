package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.integracao.EventoGrupoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class EventoGrupoServiceEjb extends CrudServiceImpl implements EventoGrupoService {

    private EventoGrupoDao dao;

    @Override
    protected EventoGrupoDao getDao() {
        if (dao == null) {
            dao = new EventoGrupoDao();
        }
        return dao;
    }

    @Override
    public Collection<EventoGrupoDTO> listByEvento(final Integer idEvento) throws Exception {
        try {
            return this.getDao().listByEvento(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
