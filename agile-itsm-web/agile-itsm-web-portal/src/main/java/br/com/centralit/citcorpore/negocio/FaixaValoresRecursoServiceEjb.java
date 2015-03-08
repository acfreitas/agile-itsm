package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.FaixaValoresRecursoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class FaixaValoresRecursoServiceEjb extends CrudServiceImpl implements FaixaValoresRecursoService {

    private FaixaValoresRecursoDao dao;

    @Override
    protected FaixaValoresRecursoDao getDao() {
        if (dao == null) {
            dao = new FaixaValoresRecursoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdRecurso(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRecurso(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdRecurso(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRecurso(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
