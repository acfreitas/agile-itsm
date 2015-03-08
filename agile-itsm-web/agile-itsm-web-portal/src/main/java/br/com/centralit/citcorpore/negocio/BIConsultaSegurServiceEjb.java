package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BIConsultaSegurDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class BIConsultaSegurServiceEjb extends CrudServiceImpl implements BIConsultaSegurService {

    private BIConsultaSegurDao dao;

    @Override
    protected BIConsultaSegurDao getDao() {
        if (dao == null) {
            dao = new BIConsultaSegurDao();
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
