package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.StartDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author flavio.santana
 *
 */
public class StartServiceEjb extends CrudServiceImpl implements StartService {

    private StartDAO dao;

    @Override
    protected StartDAO getDao() {
        if (dao == null) {
            dao = new StartDAO();
        }
        return dao;
    }

}
