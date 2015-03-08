package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BIItemDashBoardDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class BIItemDashBoardServiceEjb extends CrudServiceImpl implements BIItemDashBoardService {

    private BIItemDashBoardDao dao;

    @Override
    protected BIItemDashBoardDao getDao() {
        if (dao == null) {
            dao = new BIItemDashBoardDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdDashBoard(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdDashBoard(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdDashBoard(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdDashBoard(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdConsulta(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdConsulta(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdConsulta(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdConsulta(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
