package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.FaturaOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class FaturaOSServiceEjb extends CrudServiceImpl implements FaturaOSService {

    private FaturaOSDao dao;

    @Override
    protected FaturaOSDao getDao() {
        if (dao == null) {
            dao = new FaturaOSDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdFatura(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFatura(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdFatura(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdFatura(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdOs(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdOs(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdOs(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdOs(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
