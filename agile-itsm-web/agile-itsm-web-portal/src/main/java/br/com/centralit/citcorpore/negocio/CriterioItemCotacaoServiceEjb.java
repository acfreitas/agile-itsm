package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.CriterioItemCotacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class CriterioItemCotacaoServiceEjb extends CrudServiceImpl implements CriterioItemCotacaoService {

    private CriterioItemCotacaoDao dao;

    @Override
    protected CriterioItemCotacaoDao getDao() {
        if (dao == null) {
            dao = new CriterioItemCotacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdItemCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdItemCotacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdItemCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
