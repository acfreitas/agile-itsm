package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RecursoProjetoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RecursoProjetoServiceEjb extends CrudServiceImpl implements RecursoProjetoService {

    private RecursoProjetoDao dao;

    @Override
    protected RecursoProjetoDao getDao() {
        if (dao == null) {
            dao = new RecursoProjetoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProjeto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProjeto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdEmpregado(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEmpregado(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
