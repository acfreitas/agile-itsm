package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.integracao.LinguaDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LinguaServiceEjb extends CrudServiceImpl implements LinguaService {

    private LinguaDao dao;

    @Override
    protected LinguaDao getDao() {
        if (dao == null) {
            dao = new LinguaDao();
        }
        return dao;
    }

    @Override
    public boolean consultarLinguaAtivas(final LinguaDTO obj) throws ServiceException {
        try {
            return this.getDao().consultarLinguaAtivas(obj);
        } catch (final PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LinguaDTO getIdLingua(final LinguaDTO obj) throws ServiceException {
        try {
            return this.getDao().getIdLingua(obj);
        } catch (final PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<LinguaDTO> listarAtivos() throws ServiceException {
        try {
            return this.getDao().listarAtivos();
        } catch (final PersistenceException e) {
            throw new ServiceException(e);
        }
    }

}
