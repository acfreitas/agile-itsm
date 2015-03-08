package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ProdutoTarefaLinBaseProjDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ProdutoTarefaLinBaseProjServiceEjb extends CrudServiceImpl implements ProdutoTarefaLinBaseProjService {

    private ProdutoTarefaLinBaseProjDao dao;

    @Override
    protected ProdutoTarefaLinBaseProjDao getDao() {
        if (dao == null) {
            dao = new ProdutoTarefaLinBaseProjDao();
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
    public Collection findByIdProdutoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProdutoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProdutoContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProdutoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
