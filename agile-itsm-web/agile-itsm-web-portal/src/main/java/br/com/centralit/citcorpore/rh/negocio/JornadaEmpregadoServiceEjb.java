package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.JornadaEmpregadoDao;
import br.com.citframework.service.CrudServiceImpl;

public class JornadaEmpregadoServiceEjb extends CrudServiceImpl implements JornadaEmpregadoService {

    private JornadaEmpregadoDao dao;

    @Override
    protected JornadaEmpregadoDao getDao() {
        if (dao == null) {
            dao = new JornadaEmpregadoDao();
        }
        return dao;
    }

}
