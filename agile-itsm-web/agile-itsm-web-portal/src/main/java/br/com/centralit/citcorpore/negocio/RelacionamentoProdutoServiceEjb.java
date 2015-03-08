package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RelacionamentoProdutoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class RelacionamentoProdutoServiceEjb extends CrudServiceImpl implements RelacionamentoProdutoService {

    private RelacionamentoProdutoDao dao;

    @Override
    protected RelacionamentoProdutoDao getDao() {
        if (dao == null) {
            dao = new RelacionamentoProdutoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdTipoProduto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTipoProduto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTipoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
