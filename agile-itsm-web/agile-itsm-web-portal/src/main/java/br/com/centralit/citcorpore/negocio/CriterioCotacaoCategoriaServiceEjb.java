package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.CriterioCotacaoCategoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class CriterioCotacaoCategoriaServiceEjb extends CrudServiceImpl implements CriterioCotacaoCategoriaService {

    private CriterioCotacaoCategoriaDao dao;

    @Override
    protected CriterioCotacaoCategoriaDao getDao() {
        if (dao == null) {
            dao = new CriterioCotacaoCategoriaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCategoria(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCategoria(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
