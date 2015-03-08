package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.EventoEmpregadoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class EventoEmpregadoServiceEjb extends CrudServiceImpl implements EventoEmpregadoService {

    private EventoEmpregadoDao dao;

    @Override
    protected EventoEmpregadoDao getDao() {
        if (dao == null) {
            dao = new EventoEmpregadoDao();
        }
        return dao;
    }

    @Override
    public Collection<EventoEmpregadoDTO> listByIdEvento(final Integer idEvento) throws ServiceException {
        try {
            return this.getDao().listByIdEvento(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EventoEmpregadoDTO> listByIdEventoGrupo(final Integer idEvento) throws ServiceException {
        try {
            return this.getDao().listByIdEventoGrupo(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EventoEmpregadoDTO> listByIdEventoUnidade(final Integer idEvento) throws ServiceException {
        try {
            return this.getDao().listByIdEventoUnidade(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EventoEmpregadoDTO> listByIdEventoEmpregado(final Integer idEvento) throws ServiceException {
        try {
            return this.getDao().listByIdEventoEmpregado(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
