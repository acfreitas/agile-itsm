package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.InspecaoPedidoCompraDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class InspecaoPedidoCompraServiceEjb extends CrudServiceImpl implements InspecaoPedidoCompraService {

    private InspecaoPedidoCompraDao dao;

    @Override
    protected InspecaoPedidoCompraDao getDao() {
        if (dao == null) {
            dao = new InspecaoPedidoCompraDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdPedido(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPedido(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPedido(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPedido(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
