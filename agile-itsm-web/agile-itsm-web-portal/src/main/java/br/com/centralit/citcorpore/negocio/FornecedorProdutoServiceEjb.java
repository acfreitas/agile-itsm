package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.centralit.citcorpore.integracao.FornecedorProdutoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class FornecedorProdutoServiceEjb extends CrudServiceImpl implements FornecedorProdutoService {

    private FornecedorProdutoDao dao;

    @Override
    protected FornecedorProdutoDao getDao() {
        if (dao == null) {
            dao = new FornecedorProdutoDao();
        }
        return dao;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection findByIdTipoProduto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public FornecedorProdutoDTO findByIdTipoProdutoAndFornecedor(final Integer parm, final Integer parm2) throws Exception {
        try {
            return this.getDao().findByIdTipoProdutoAndFornecedor(parm, parm2);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
