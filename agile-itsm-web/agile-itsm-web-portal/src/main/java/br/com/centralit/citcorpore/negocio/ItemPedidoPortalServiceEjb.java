package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ItemPedidoPortalDAO;
import br.com.citframework.service.CrudServiceImpl;

public class ItemPedidoPortalServiceEjb extends CrudServiceImpl implements ItemPedidoPortalService {

    private ItemPedidoPortalDAO dao;

    @Override
    protected ItemPedidoPortalDAO getDao() {
        if (dao == null) {
            dao = new ItemPedidoPortalDAO();
        }
        return dao;
    }

}
