package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.InventarioXMLDAO;
import br.com.citframework.service.CrudServiceImpl;

public class InventarioXMLServiceEjb extends CrudServiceImpl implements InventarioXMLService {

    private InventarioXMLDAO dao;

    @Override
    protected InventarioXMLDAO getDao() {
        if (dao == null) {
            dao = new InventarioXMLDAO();
        }
        return dao;
    }

    @Override
    public boolean inventarioAtualizado(final String ip, final java.util.Date dataExpiracao) throws Exception {
        return this.getDao().inventarioAtualizado(ip, dataExpiracao);
    }

}
