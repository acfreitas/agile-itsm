package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.AvaliacaoColetaPrecoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class AvaliacaoColetaPrecoServiceEjb extends CrudServiceImpl implements AvaliacaoColetaPrecoService {

    private AvaliacaoColetaPrecoDao dao;

    @Override
    protected AvaliacaoColetaPrecoDao getDao() {
        if (dao == null) {
            dao = new AvaliacaoColetaPrecoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdColetaPreco(final Integer idColetaPreco) throws Exception {
        try {
            return this.getDao().findByIdColetaPreco(idColetaPreco);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdColetaPreco(final Integer idColetaPreco) throws Exception {
        try {
            this.getDao().deleteByIdColetaPreco(idColetaPreco);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
