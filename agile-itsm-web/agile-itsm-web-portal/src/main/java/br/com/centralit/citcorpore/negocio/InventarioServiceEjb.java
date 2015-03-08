package br.com.centralit.citcorpore.negocio;

import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Maycon.Fernandes
 *
 */
public class InventarioServiceEjb extends CrudServiceImpl implements InventarioService {

    @Override
    protected CrudDAO getDao() {
        return null;
    }

}
