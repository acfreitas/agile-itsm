package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.JustificativaListaNegraDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
public class JustificativaListaNegraServiceEjb extends CrudServiceImpl implements JustificativaListaNegraService {

    private JustificativaListaNegraDao dao;

    @Override
    protected JustificativaListaNegraDao getDao() {
        if (dao == null) {
            dao = new JustificativaListaNegraDao();
        }
        return dao;
    }

}
