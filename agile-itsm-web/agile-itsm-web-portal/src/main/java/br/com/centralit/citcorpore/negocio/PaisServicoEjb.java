package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.PaisDao;
import br.com.citframework.service.CrudServiceImpl;

public class PaisServicoEjb extends CrudServiceImpl implements PaisServico {

    private PaisDao dao;

    @Override
    protected PaisDao getDao() {
        if (dao == null) {
            dao = new PaisDao();
        }
        return dao;
    }

}
