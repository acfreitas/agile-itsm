package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ProdutoContratoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ProdutoContratoServiceEjb extends CrudServiceImpl implements ProdutoContratoService {

    private ProdutoContratoDao dao;

    @Override
    protected ProdutoContratoDao getDao() {
        if (dao == null) {
            dao = new ProdutoContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getProdutosByIdMarcoPagamentoPrj(final Integer idMarcoPagamentoPrjParm, final Integer idLinhaBaseProjetoParm) throws Exception {
        try {
            return this.getDao().getProdutosByIdMarcoPagamentoPrj(idMarcoPagamentoPrjParm, idLinhaBaseProjetoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
