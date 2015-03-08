package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.HabilidadeDao;
import br.com.citframework.service.CrudServiceImpl;

public class HabilidadeServiceEjb extends CrudServiceImpl implements HabilidadeService {

    private HabilidadeDao dao;

    @Override
    protected HabilidadeDao getDao() {
        if (dao == null) {
            dao = new HabilidadeDao();
        }
        return dao;
    }

}
