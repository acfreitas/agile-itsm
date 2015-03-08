package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.MatrizVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class MatrizVisaoServiceEjb extends CrudServiceImpl implements MatrizVisaoService {

    private MatrizVisaoDao dao;

    @Override
    protected MatrizVisaoDao getDao() {
        if (dao == null) {
            dao = new MatrizVisaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdVisao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdVisao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdVisao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdVisao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
