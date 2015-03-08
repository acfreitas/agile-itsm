package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BIDashBoardSegurDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class BIDashBoardSegurServiceEjb extends CrudServiceImpl implements BIDashBoardSegurService {

    private BIDashBoardSegurDao dao;

    @Override
    protected BIDashBoardSegurDao getDao() {
        if (dao == null) {
            dao = new BIDashBoardSegurDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdPerfilSeguranca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPerfilSeguranca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPerfilSeguranca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPerfilSeguranca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
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

}
