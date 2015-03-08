package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.NagiosNDOObjectDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class NagiosNDOObjectServiceEjb extends CrudServiceImpl implements NagiosNDOObjectService {

    private NagiosNDOObjectDao dao;

    @Override
    protected NagiosNDOObjectDao getDao() {
        if (dao == null) {
            dao = new NagiosNDOObjectDao();
        }
        return dao;
    }

    @Override
    public Collection findByName1(final String parm) throws Exception {
        try {
            return this.getDao().findByName1(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByName1(final String parm) throws Exception {
        try {
            this.getDao().deleteByName1(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByName2(final String parm) throws Exception {
        try {
            return this.getDao().findByName2(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByName2(final String parm) throws Exception {
        try {
            this.getDao().deleteByName2(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
