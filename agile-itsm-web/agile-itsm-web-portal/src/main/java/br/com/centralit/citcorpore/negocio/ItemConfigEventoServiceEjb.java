package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfigEventoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ItemConfigEventoServiceEjb extends CrudServiceImpl implements ItemConfigEventoService {

    private ItemConfigEventoDao dao;

    @Override
    protected ItemConfigEventoDao getDao() {
        if (dao == null) {
            dao = new ItemConfigEventoDao();
        }
        return dao;
    }

    @Override
    public Collection<ItemConfigEventoDTO> listByIdEvento(final Integer idEvento) throws ServiceException {
        try {
            return this.getDao().listByIdEvento(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ItemConfigEventoDTO> verificarDataHoraEvento() throws ServiceException {
        try {
            return this.getDao().verificaDataHoraEvento();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
