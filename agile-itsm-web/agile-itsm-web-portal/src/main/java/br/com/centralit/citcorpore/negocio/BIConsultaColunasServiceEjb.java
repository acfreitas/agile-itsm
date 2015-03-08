package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BIConsultaColunasDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class BIConsultaColunasServiceEjb extends CrudServiceImpl implements BIConsultaColunasService {

    private BIConsultaColunasDao dao;

    @Override
    protected BIConsultaColunasDao getDao() {
        if (dao == null) {
            dao = new BIConsultaColunasDao();
        }
        return dao;
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

    @Override
    public Collection findByOrdem(final Integer parm) throws Exception {
        try {
            return this.getDao().findByOrdem(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByOrdem(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByOrdem(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
