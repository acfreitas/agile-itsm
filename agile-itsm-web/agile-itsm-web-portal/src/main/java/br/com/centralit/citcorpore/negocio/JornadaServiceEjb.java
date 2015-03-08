package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.rh.integracao.JornadaDao;
import br.com.citframework.service.CrudServiceImpl;

public class JornadaServiceEjb extends CrudServiceImpl implements JornadaService {

    private JornadaDao dao;

    @Override
    protected JornadaDao getDao() {
        if (dao == null) {
            dao = new JornadaDao();
        }
        return dao;
    }

}
