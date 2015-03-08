package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.NagiosConexaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class NagiosConexaoServiceEjb extends CrudServiceImpl implements NagiosConexaoService {

    private NagiosConexaoDao dao;

    @Override
    protected NagiosConexaoDao getDao() {
        if (dao == null) {
            dao = new NagiosConexaoDao();
        }
        return dao;
    }

}
