package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RecursoTarefaLinBaseProjDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RecursoTarefaLinBaseProjServiceEjb extends CrudServiceImpl implements RecursoTarefaLinBaseProjService {

    private RecursoTarefaLinBaseProjDao dao;

    @Override
    protected RecursoTarefaLinBaseProjDao getDao() {
        if (dao == null) {
            dao = new RecursoTarefaLinBaseProjDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdTarefaLinhaBaseProjeto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTarefaLinhaBaseProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTarefaLinhaBaseProjeto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTarefaLinhaBaseProjeto(parm);
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
