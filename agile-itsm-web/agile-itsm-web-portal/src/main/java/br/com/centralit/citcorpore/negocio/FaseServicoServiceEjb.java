package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.FaseServicoDao;
import br.com.citframework.service.CrudServiceImpl;

public class FaseServicoServiceEjb extends CrudServiceImpl implements FaseServicoService {

    private FaseServicoDao dao;

    @Override
    protected FaseServicoDao getDao() {
        if (dao == null) {
            dao = new FaseServicoDao();
        }
        return dao;
    }

}
