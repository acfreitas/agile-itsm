package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.PedidoPortalDAO;
import br.com.citframework.service.CrudServiceImpl;

public class PedidoPortalServiceEjb extends CrudServiceImpl implements PedidoPortalService {

    private PedidoPortalDAO dao;

    @Override
    public PedidoPortalDAO getDao() {
        if (dao == null) {
            dao = new PedidoPortalDAO();
        }
        return dao;
    }

}
